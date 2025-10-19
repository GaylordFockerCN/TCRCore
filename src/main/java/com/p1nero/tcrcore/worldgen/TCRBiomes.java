package com.p1nero.tcrcore.worldgen;

import com.p1nero.tcrcore.TCRCoreMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.*;

public class TCRBiomes {

    public static final ResourceKey<Biome> AIR = register("air");
    public static ResourceKey<Biome> register(String name){
        return ResourceKey.create(Registries.BIOME, ResourceLocation.fromNamespaceAndPath(TCRCoreMod.MOD_ID, name));
    }

    public static void boostrap(BootstapContext<Biome> context) {
        MobSpawnSettings.Builder spawnBuilder = new MobSpawnSettings.Builder();
        BiomeGenerationSettings.Builder biomeBuilder = new BiomeGenerationSettings.Builder(context.lookup(Registries.PLACED_FEATURE), context.lookup(Registries.CONFIGURED_CARVER));
        context.register(AIR, new Biome.BiomeBuilder()
                .hasPrecipitation(false)
                .downfall(0.5F)
                .temperature(0.5F)
                .generationSettings(biomeBuilder.build())
                .mobSpawnSettings(spawnBuilder.build())
                .specialEffects((new BiomeSpecialEffects.Builder())
                        .waterColor(4159204)
                        .waterFogColor(329011)
                        .fogColor(12638463)
                        .skyColor(8103167)
                        .build())
                .build());
    }

}
