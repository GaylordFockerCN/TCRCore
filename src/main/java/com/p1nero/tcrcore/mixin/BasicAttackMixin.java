package com.p1nero.tcrcore.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import yesman.epicfight.skill.BasicAttack;
import yesman.epicfight.skill.SkillContainer;

@Mixin(BasicAttack.class)
public class BasicAttackMixin {

    /**
     * Fuck Stamina
     */
    @Inject(method = "checkConsumption", at = @At("HEAD"), cancellable = true, remap = false)
    private void tcr$checkConsumption(SkillContainer container, boolean dash, boolean air, CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(true);
    }
}
