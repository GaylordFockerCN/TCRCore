package com.p1nero.tcrcore.mixin;

import com.p1nero.tcrcore.TCRCoreMod;
import com.p1nero.tcrcore.capability.PlayerDataManager;
import com.p1nero.tcrcore.utils.EntityUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.shelmarow.nightfall_invade.entity.NFIEntities;
import net.shelmarow.nightfall_invade.entity.spear_knight.Arterius;
import net.unusual.blockfactorysbosses.entity.UnderworldKnightEntity;
import net.unusual.blockfactorysbosses.init.BlockFactorysBossesModBlocks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(UnderworldKnightEntity.class)
public abstract class UnderworldKnightEntityMixin extends Monster {

    @Unique
    private boolean tcr$hurtMark;

    protected UnderworldKnightEntityMixin(EntityType<? extends Monster> p_33002_, Level p_33003_) {
        super(p_33002_, p_33003_);
    }

    @Inject(method = "baseTick", at = @At("HEAD"))
    private void tcr$baseTick(CallbackInfo ci) {
        if(level().dimension() == Level.OVERWORLD && level() instanceof ServerLevel serverLevel) {
            if(EntityUtil.getNearByEntities(serverLevel, this.position(), 50, Arterius.class).isEmpty()) {
                Arterius arterius = NFIEntities.ARTERIUS.get().spawn(serverLevel, this.getOnPos().above(3), MobSpawnType.MOB_SUMMONED);
                if(arterius != null) {
                    arterius.setInBattle(false);
                }
            }
            if (level().getBlockState(BlockPos.containing(Math.floor(this.getX()), Math.floor(this.getY()), Math.floor(this.getZ()))).getBlock() == BlockFactorysBossesModBlocks.BOSS_SPAWNER.get()) {
                level().setBlock(BlockPos.containing(Math.floor(this.getX()), Math.floor(this.getY()), Math.floor(this.getZ())), Blocks.AIR.defaultBlockState(), 3);
            }
            this.discard();
        }
    }

    @Inject(method = "tick", at = @At("HEAD"))
    private void tcr$tick(CallbackInfo ci) {
        if(level().dimension() != Level.OVERWORLD && !tcr$hurtMark) {
            EntityUtil.nearPlayerDo(this, 30, (player -> {
                if(PlayerDataManager.flameEyeTraded.get(player)) {
                    player.displayClientMessage(TCRCoreMod.getInfo("attack_to_restart"), true);
                }
            }));
            this.setTarget(null);
        }
    }

    @Inject(method = "hurt", at = @At("HEAD"), cancellable = true)
    private void tcr$hurt(DamageSource damagesource, float amount, CallbackInfoReturnable<Boolean> cir) {
        if(this.tickCount < 100) {
            cir.setReturnValue(false);
        } else {
            tcr$hurtMark = true;
            this.getPersistentData().putBoolean("hurt_mark", true);
        }
    }

}
