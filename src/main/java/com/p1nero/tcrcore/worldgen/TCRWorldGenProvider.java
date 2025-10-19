package com.p1nero.tcrcore.worldgen;

import com.p1nero.tcrcore.TCRCoreMod;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.DatapackBuiltinEntriesProvider;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class TCRWorldGenProvider extends DatapackBuiltinEntriesProvider {

    public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
            .add(Registries.NOISE_SETTINGS, TCRNoiseSettings::bootstrap)
            .add(Registries.BIOME, TCRBiomes::boostrap)
            .add(Registries.DIMENSION_TYPE, TCRDimensions::bootstrapType)
            .add(Registries.LEVEL_STEM, TCRDimensions::bootstrapStem);

    public TCRWorldGenProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, BUILDER, Set.of(TCRCoreMod.MOD_ID));
    }
}