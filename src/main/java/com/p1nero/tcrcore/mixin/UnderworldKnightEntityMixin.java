package com.p1nero.tcrcore.mixin;

import com.p1nero.tcrcore.TCRCoreMod;
import com.p1nero.tcrcore.capability.PlayerDataManager;
import com.p1nero.tcrcore.utils.EntityUtil;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import net.unusual.blockfactorysbosses.entity.UnderworldKnightEntity;
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

    @Inject(method = "tick", at = @At("HEAD"))
    private void tcr$baseTick(CallbackInfo ci) {
        if(level().dimension() == Level.OVERWORLD) {
            this.discard();
        }
        if(!tcr$hurtMark) {
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
