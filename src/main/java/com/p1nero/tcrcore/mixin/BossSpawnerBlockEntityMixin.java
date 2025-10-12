package com.p1nero.tcrcore.mixin;

import com.p1nero.tcrcore.utils.EntityUtil;
import com.p1nero.tcrcore.utils.WorldUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.shelmarow.nightfall_invade.entity.NFIEntities;
import net.shelmarow.nightfall_invade.entity.spear_knight.Arterius;
import net.unusual.blockfactorysbosses.block.entity.BossSpawnerBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BossSpawnerBlockEntity.class)
public abstract class BossSpawnerBlockEntityMixin extends RandomizableContainerBlockEntity implements WorldlyContainer {

    protected BossSpawnerBlockEntityMixin(BlockEntityType<?> p_155629_, BlockPos p_155630_, BlockState p_155631_) {
        super(p_155629_, p_155630_, p_155631_);
    }

    @Inject(method = "load", at = @At("TAIL"))
    private void load(CompoundTag compound, CallbackInfo ci) {
        if(level instanceof ServerLevel serverLevel && WorldUtil.isInStructure(serverLevel, this.getBlockPos().getCenter(), WorldUtil.FIRE)) {
            if(EntityUtil.getNearByEntities(serverLevel, this.getBlockPos().getCenter(), 50, Arterius.class).isEmpty()) {
                Arterius arterius = NFIEntities.ARTERIUS.get().spawn(serverLevel, this.getBlockPos().above(5), MobSpawnType.STRUCTURE);
                if(arterius != null) {
                    arterius.setInBattle(false);
                    serverLevel.setBlock(this.getBlockPos(), Blocks.AIR.defaultBlockState(), 3);
                }
            }
        }
    }
}
