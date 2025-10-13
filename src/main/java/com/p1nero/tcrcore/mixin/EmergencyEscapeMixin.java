package com.p1nero.tcrcore.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import yesman.epicfight.skill.SkillCategories;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.skill.passive.EmergencyEscapeSkill;
import yesman.epicfight.world.entity.eventlistener.SkillCastEvent;

@Mixin(EmergencyEscapeSkill.class)
public class EmergencyEscapeMixin {

    @Inject(method = "lambda$onInitiate$0", at = @At("HEAD"), cancellable = true, remap = false)
    private void tcr$onInit(SkillContainer container, SkillCastEvent event, CallbackInfo ci){
        if (event.getSkillContainer().getSkill().getCategory() == SkillCategories.DODGE) {
            if(container.getExecutor().getOriginal().isInWater()) {
                ci.cancel();
            }
        }
    }
}
