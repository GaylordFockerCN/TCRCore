package com.p1nero.tcrcore.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.api.animation.types.DynamicAnimation;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

@Mixin(AttackAnimation.class)
public class AttackAnimationMixin {

    @Inject(method = "getPlaySpeed", at = @At("RETURN"), cancellable = true, remap = false)
    private void tcr$getPlaySpeed(LivingEntityPatch<?> entitypatch, DynamicAnimation animation, CallbackInfoReturnable<Float> cir) {
        cir.setReturnValue(Math.min(1.5F, cir.getReturnValue()));
    }

}
