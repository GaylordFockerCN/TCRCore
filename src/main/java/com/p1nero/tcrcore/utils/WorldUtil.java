package com.p1nero.tcrcore.utils;

import com.yungnickyoung.minecraft.yungsapi.criteria.SafeStructureLocationPredicate;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.BaseCommandBlock;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import yesman.epicfight.api.utils.math.Vec2i;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WorldUtil {
    public static final Vec3i GIRL_POS = new Vec3i(80, 74, -133);
    public static final Vec3i GIRL_PORTAL_POS = new Vec3i(80, 73, -138);
    public static final Vec3i START_POS = new Vec3i(-3, 75, -190);
    public static final Vec3 GOLEM_CENTER_POS_VEC3 = new Vec3(78, 75, -190);
    public static final Vec3 CENTER_POS = new Vec3(-19, 75, -79);
    public static final Vec3 GUIDER_POS = new Vec3(-186, 85, -191);
    public static final Vec3i GUIDER_BLOCK_POS = new Vec3i(-186, 85, -191);
    public static final String COVES = "trek:overworld/very_rare/coves";
    public static final String SKY_ISLAND = "trek:overworld/very_rare/floating_farm_large";
    public static final String SAND = "dodosmobs:jungle_prison";
    public static final String WATER = "aquamirae:ship";
    public static final String FIRE = "block_factorys_bosses:underworld_arena";
    public static Vec2i storm, flame, abyss, cursed, desert;

    private static final Pattern LOCATE_PATTERN = Pattern.compile(".*?\\[\\s*(-?\\d+)\\s*,\\s*~\\s*,\\s*(-?\\d+)\\s*\\].*");

    public static boolean inMainLand(Entity entity) {
        return entity.level().dimension() == Level.OVERWORLD && entity.position().subtract(CENTER_POS).horizontalDistance() < 250;
    }

    public static boolean isInStructure(LivingEntity entity, String structure) {
        if(entity.level().isClientSide) {
            return false;
        }
        return new SafeStructureLocationPredicate(ResourceKey.create(Registries.STRUCTURE, ResourceLocation.parse(structure))).matches(((ServerLevel) entity.level()), entity.getX(), entity.getY(), entity.getZ());
    }

    public static boolean isInStructure(ServerLevel serverLevel, Vec3 pos, String structure) {
        return new SafeStructureLocationPredicate(ResourceKey.create(Registries.STRUCTURE, ResourceLocation.parse(structure))).matches(serverLevel, pos.x(), pos.y(), pos.z());
    }

    /**
     * 获取结构位置
     */
    @Nullable
    public static Vec2i getNearbyStructurePos(ServerPlayer serverPlayer, String structureId) {
        String output = getCommandOutput(serverPlayer.serverLevel(), serverPlayer.position(), "/locate structure " + structureId);
        Matcher matcher = LOCATE_PATTERN.matcher(output);
        if(matcher.find()) {
            try {
                String xStr = matcher.group(1).trim();
                String zStr = matcher.group(2).trim();
                return new Vec2i(Integer.parseInt(xStr), Integer.parseInt(zStr));
            } catch (NumberFormatException ignored) {
            }
        }
        return null;
    }

    public static String getCommandOutput(ServerLevel serverLevel, @Nullable Vec3 vec, String command) {
        BaseCommandBlock baseCommandBlock = new BaseCommandBlock() {
            @Override
            public @NotNull ServerLevel getLevel() {
                return serverLevel;
            }

            @Override
            public void onUpdated() {
            }

            @Override
            public @NotNull Vec3 getPosition() {
                return Objects.requireNonNullElseGet(vec, () -> new Vec3(0, 0, 0));
            }

            @Override
            public @NotNull CommandSourceStack createCommandSourceStack() {
                return new CommandSourceStack(this, getPosition(), Vec2.ZERO, serverLevel, 2, "dev", Component.literal("dev"), serverLevel.getServer(), null);
            }

            @Override
            public boolean isValid() {
                return true;
            }

            @Override
            public boolean performCommand(Level pLevel) {
                if (!pLevel.isClientSide) {
                    this.setSuccessCount(0);
                    MinecraftServer server = this.getLevel().getServer();
                    try {
                        this.setLastOutput(null);
                        CommandSourceStack commandSourceStack = this.createCommandSourceStack().withCallback((context, success, i) -> {
                            if (success) {
                                this.setSuccessCount(this.getSuccessCount() + 1);
                            }
                        });
                        server.getCommands().performPrefixedCommand(commandSourceStack, this.getCommand());
                    } catch (Throwable ignored) {
                    }
                }
                return true;
            }
        };

        baseCommandBlock.setCommand(command);
        baseCommandBlock.setTrackOutput(true);
        baseCommandBlock.performCommand(serverLevel);

        return baseCommandBlock.getLastOutput().getString();
    }
}
