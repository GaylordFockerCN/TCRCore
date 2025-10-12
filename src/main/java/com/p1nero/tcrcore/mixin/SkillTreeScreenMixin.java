package com.p1nero.tcrcore.mixin;

import com.p1nero.tcrcore.client.gui.widget.AbilityPointsMeter;
import com.yesman.epicskills.client.gui.screen.BackgroundRenderableScreen;
import com.yesman.epicskills.client.gui.screen.SkillTreeScreen;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import org.apache.commons.lang3.mutable.MutableInt;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

@Mixin(SkillTreeScreen.class)
public abstract class SkillTreeScreenMixin extends Screen implements BackgroundRenderableScreen {

    @Shadow(remap = false)
    private boolean backgroundMode;

    @Shadow(remap = false)
    @Final
    private Map<Integer, SkillTreeScreen.TreeSelectButton> skillTreeButtons;

    @Shadow(remap = false)
    @Final
    private Button scaleUpButton;

    @Shadow(remap = false)
    @Final
    private Button scaleDownButton;

    protected SkillTreeScreenMixin(Component p_96550_) {
        super(p_96550_);
    }

    @Inject(method = "init", at = @At("HEAD"), cancellable = true)
    private void tcr$init(CallbackInfo ci) {
        MutableInt posY = new MutableInt(10);
        this.backgroundMode = false;
        this.skillTreeButtons.values().forEach((button) -> {
            button.setY(posY.intValue());
            this.addRenderableWidget(button);
            posY.add(36);
        });
        this.addRenderableWidget(this.scaleUpButton);
        this.addRenderableWidget(this.scaleDownButton);
        this.addRenderableOnly(new AbilityPointsMeter(this.width - 116, 10, (SkillTreeScreen) (Object) this));
        ci.cancel();
    }

}
