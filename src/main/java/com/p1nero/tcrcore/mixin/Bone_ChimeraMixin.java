package com.p1nero.tcrcore.mixin;

import com.github.dodo.dodosmobs.entity.InternalAnimationMonster.IABossMonsters.Bone_Chimera_Entity;
import com.github.dodo.dodosmobs.entity.InternalAnimationMonster.IABossMonsters.IABoss_monster;
import com.p1nero.tcrcore.TCRCoreMod;
import com.p1nero.tcrcore.utils.EntityUtil;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * 受伤才开始苏醒
 */
@Mixin(Bone_Chimera_Entity.class)
public class Bone_ChimeraMixin extends IABoss_monster {

    @Unique
    private boolean tcr$hurtMark;

    public Bone_ChimeraMixin(EntityType entity, Level world) {
        super(entity, world);
    }

    @Inject(method = "tick", at = @At("HEAD"), cancellable = true)
    private void tcr$baseTick(CallbackInfo ci) {
        if(!tcr$hurtMark) {
            EntityUtil.nearPlayerDo(this, 30, (player -> {
                player.displayClientMessage(TCRCoreMod.getInfo("attack_to_restart"), true);
            }));
            ci.cancel();
        }
    }

    @Inject(method = "aiStep", at = @At("HEAD"), cancellable = true)
    private void tcr$aiStep(CallbackInfo ci) {
        if(!tcr$hurtMark) {
            EntityUtil.nearPlayerDo(this, 30, (player -> {
                player.displayClientMessage(TCRCoreMod.getInfo("attack_to_restart"), true);
            }));
            ci.cancel();
        }
    }

    @Inject(method = "hurt", at = @At("HEAD"), cancellable = true)
    private void tcr$hurt(DamageSource damagesource, float amount, CallbackInfoReturnable<Boolean> cir) {
        if(this.tickCount < 100) {
            cir.setReturnValue(false);
        } else {
            tcr$hurtMark = true;
        }
    }

}
