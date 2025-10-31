package com.p1nero.tcrcore.mixin;

import com.yesman.epicskills.client.gui.screen.SkillTreeScreen;
import com.yesman.epicskills.world.capability.AbilityPoints;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(SkillTreeScreen.class)
public interface SkillTreeScreenAccessor {
    @Accessor(value = "playerAbilityPoints", remap = false)
    AbilityPoints getAbilityPoints();
}
