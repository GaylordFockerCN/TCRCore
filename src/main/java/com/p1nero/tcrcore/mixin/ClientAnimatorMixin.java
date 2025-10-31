package com.p1nero.tcrcore.mixin;

import com.yesman.epicparcool.ParcoolLivingMotions;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import yesman.epicfight.api.animation.Animator;
import yesman.epicfight.api.animation.LivingMotion;
import yesman.epicfight.api.animation.LivingMotions;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.asset.AssetAccessor;
import yesman.epicfight.api.client.animation.ClientAnimator;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

@Mixin(ClientAnimator.class)
public abstract class ClientAnimatorMixin extends Animator {

    public ClientAnimatorMixin(LivingEntityPatch<?> entitypatch) {
        super(entitypatch);
    }

    @Inject(method = "getLivingMotion", at = @At("HEAD"), cancellable = true, remap = false)
    private void tcr$getLiving(LivingMotion motion, CallbackInfoReturnable<AssetAccessor<? extends StaticAnimation>> cir) {
        if(motion.isSame(ParcoolLivingMotions.FAST_RUN)) {
            cir.setReturnValue(this.livingAnimations.getOrDefault(LivingMotions.RUN, this.livingAnimations.get(LivingMotions.IDLE)));
        }
    }

}
