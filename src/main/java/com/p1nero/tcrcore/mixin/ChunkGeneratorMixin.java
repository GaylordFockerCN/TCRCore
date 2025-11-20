package com.p1nero.tcrcore.mixin;

import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import it.unimi.dsi.fastutil.objects.ObjectArraySet;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.SectionPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.chunk.ChunkGeneratorStructureState;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.placement.ConcentricRingsStructurePlacement;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadStructurePlacement;
import net.minecraft.world.level.levelgen.structure.placement.StructurePlacement;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Mixin(ChunkGenerator.class)
public abstract class ChunkGeneratorMixin {

    @Shadow
    @Nullable
    protected abstract Pair<BlockPos, Holder<Structure>> getNearestGeneratedStructure(Set<Holder<Structure>> p_223182_, ServerLevel p_223183_, StructureManager p_223184_, BlockPos p_223185_, boolean p_223186_, ConcentricRingsStructurePlacement p_223187_);

    @Shadow
    @Nullable
    private static Pair<BlockPos, Holder<Structure>> getNearestGeneratedStructure(Set<Holder<Structure>> p_223189_, LevelReader p_223190_, StructureManager p_223191_, int p_223192_, int p_223193_, int p_223194_, boolean p_223195_, long p_223196_, RandomSpreadStructurePlacement p_223197_) {
        return null;
    }

    @Inject(method = "findNearestMapStructure", at = @At("HEAD"), cancellable = true)
    private void tcr$findNearestMapStructure(ServerLevel serverLevel, HolderSet<Structure> structureHolderSet, BlockPos pos, int p_223041_, boolean p_223042_, CallbackInfoReturnable<Pair<BlockPos, Holder<Structure>>> cir){
        ChunkGeneratorStructureState chunkgeneratorstructurestate = serverLevel.getChunkSource().getGeneratorState();
        Map<StructurePlacement, Set<Holder<Structure>>> map = new Object2ObjectArrayMap<>();

        for(Holder<Structure> holder : structureHolderSet) {
            for(StructurePlacement structureplacement : chunkgeneratorstructurestate.getPlacementsForStructure(holder)) {
                map.computeIfAbsent(structureplacement, (p_223127_) -> new ObjectArraySet<>()).add(holder);
            }
        }

        if (map.isEmpty()) {
            cir.setReturnValue(null);
        } else {
            Pair<BlockPos, Holder<Structure>> pair2 = null;
            double d2 = Double.MAX_VALUE;
            StructureManager structuremanager = serverLevel.structureManager();
            List<Map.Entry<StructurePlacement, Set<Holder<Structure>>>> list = new ArrayList<>(map.size());

            for(Map.Entry<StructurePlacement, Set<Holder<Structure>>> entry : map.entrySet()) {
                StructurePlacement structureplacement1 = entry.getKey();
                if (structureplacement1 instanceof ConcentricRingsStructurePlacement concentricringsstructureplacement) {
                    Pair<BlockPos, Holder<Structure>> pair = this.getNearestGeneratedStructure(entry.getValue(), serverLevel, structuremanager, pos, p_223042_, concentricringsstructureplacement);
                    if (pair != null) {
                        BlockPos blockpos = pair.getFirst();
                        double d0 = pos.distSqr(blockpos);
                        if (d0 < d2) {
                            d2 = d0;
                            pair2 = pair;
                        }
                    }
                } else if (structureplacement1 instanceof RandomSpreadStructurePlacement) {
                    list.add(entry);
                }
            }

            if (!list.isEmpty()) {
                int i = SectionPos.blockToSectionCoord(pos.getX());
                int j = SectionPos.blockToSectionCoord(pos.getZ());

                for(int k = 0; k <= p_223041_; ++k) {
                    boolean flag = false;

                    for(Map.Entry<StructurePlacement, Set<Holder<Structure>>> entry1 : list) {
                        RandomSpreadStructurePlacement randomspreadstructureplacement = (RandomSpreadStructurePlacement)entry1.getKey();
                        Pair<BlockPos, Holder<Structure>> pair1 = getNearestGeneratedStructure(entry1.getValue(), serverLevel, structuremanager, i, j, k, p_223042_, chunkgeneratorstructurestate.getLevelSeed(), randomspreadstructureplacement);
                        if (pair1 != null) {
                            flag = true;
                            double d1 = pos.distSqr(pair1.getFirst());
                            if (d1 < d2) {
                                d2 = d1;
                                pair2 = pair1;
                            }
                        }
                    }

                    if (flag) {
                        cir.setReturnValue(pair2);
                    }
                }
            }

            cir.setReturnValue(pair2);
        }
        cir.setReturnValue(null);
    }

}
