package com.p1nero.tcrcore.utils;

import com.mojang.logging.LogUtils;
import com.p1nero.tcrcore.TCRCoreMod;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.loading.FMLPaths;
import org.slf4j.Logger;
import yesman.epicfight.api.exception.AssetLoadingException;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.stream.Stream;

public class DimensionResourceCopier {
    private static final String MOD_ID = TCRCoreMod.MOD_ID;
    private static final String DIMENSION_PATH = "sanctum";
    private static final Logger LOGGER = LogUtils.getLogger();

    public static void copyDimensionToSaves(MinecraftServer server) {
        Path gameDir = FMLPaths.GAMEDIR.get();
        Path savesDir = gameDir.resolve("saves");

        if(server.isDedicatedServer()) {
            copyToDimSave(gameDir.resolve(server.getWorldData().getLevelName()));
        } else {
            try (Stream<Path> saveFolders = Files.list(savesDir)) {
                saveFolders.filter(Files::isDirectory).forEach(DimensionResourceCopier::copyToDimSave);
            } catch (IOException e) {
                LOGGER.error("TCR Core： 遍历存档目录失败: {}", e.getMessage());
            }
        }
    }

    private static void copyToDimSave(Path saveFolder) {
        Path targetDir = saveFolder.resolve("dimensions")
                .resolve(MOD_ID)
                .resolve(DIMENSION_PATH);

        // 跳过已存在的目录，以region更好，以防提前生了data文件夹
        if (Files.exists(targetDir.resolve("region"))) {
            return;
        }

        try {
            Files.createDirectories(targetDir);
            copyUsingModClassLoader(targetDir);
            LOGGER.info("TCR Core： {} 复制完成。", targetDir);
        } catch (IOException e) {
            LOGGER.error("TCR Core： 复制到存档失败: {} - {}", saveFolder.getFileName(), e.getMessage());
        }
    }

    private static void copyToSave(Path saveFolder) {
        Path markDir = saveFolder.resolve("mark");
        if (Files.exists(markDir)) {
            return;
        }

        try {
            Files.createDirectories(saveFolder);
            copyUsingModClassLoader(saveFolder);
            Files.createDirectories(markDir);
            LOGGER.info("TCR Core： {} 复制完成。", saveFolder);
        } catch (IOException e) {
            LOGGER.error("TCR Core： 复制到存档失败: {} - {}", saveFolder.getFileName(), e.getMessage());
        }
    }

    private static void copyUsingModClassLoader(Path targetDir) {
        Class<?> modClass = ModList.get().getModObjectById(MOD_ID).orElseThrow(() -> new AssetLoadingException("找不到模组")).getClass();
        String resourcePath = "/mainland";

        try {
            copyResourceDirectory(modClass, resourcePath, targetDir);
        } catch (IOException | URISyntaxException e) {
            LOGGER.error("TCR Core： 复制资源失败: {}", e.getMessage());
        }
    }

    private static void copyResourceDirectory(Class<?> modClass, String resourcePath, Path targetDir) throws IOException, URISyntaxException {
        URL resourceUrl = modClass.getResource(resourcePath);
        if (resourceUrl == null) {
            throw new IOException("TCR Core： 资源目录不存在: " + resourcePath);
        }

        // 处理JAR资源
        if ("jar".equals(resourceUrl.getProtocol())) {
            copyJarResources(resourceUrl, resourcePath, targetDir);
        }
        // 处理文件系统资源（开发环境）
        else {
            Path sourceDir = Paths.get(resourceUrl.toURI());
            Files.walk(sourceDir)
                    .forEach(source -> copyFile(source, sourceDir, targetDir));
        }
    }

    private static void copyJarResources(URL resourceUrl, String resourcePath, Path targetDir) throws IOException {
        String jarPath = resourceUrl.getPath().substring(5, resourceUrl.getPath().indexOf("!"));
        jarPath = URLDecoder.decode(jarPath, StandardCharsets.UTF_8);

        try (FileSystem jarFs = FileSystems.newFileSystem(Path.of(jarPath), (ClassLoader) null)) {
            Path resourceRoot = jarFs.getPath(resourcePath);
            Files.walk(resourceRoot)
                    .forEach(source -> copyFile(source, resourceRoot, targetDir));
        }
    }

    private static void copyFile(Path source, Path sourceRoot, Path targetRoot) {
        try {
            Path relative = sourceRoot.relativize(source);
            Path target = targetRoot.resolve(relative.toString());

            if (Files.isDirectory(source)) {
                Files.createDirectories(target);
            } else {
                Files.createDirectories(target.getParent());
                Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING);
                LOGGER.info("TCR Core： 从 {} 复制到 {} ", source, target);
            }
        } catch (IOException e) {
            LOGGER.error("TCR Core： 复制文件失败: {} - {}", source, e.getMessage());
        }
    }
}