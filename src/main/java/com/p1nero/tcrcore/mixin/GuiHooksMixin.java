package com.p1nero.tcrcore.mixin;

import com.aetherteam.aether.client.event.hooks.GuiHooks;
import net.minecraft.client.gui.layouts.GridLayout;
import net.minecraft.client.gui.screens.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * 删恐鸟皮肤按钮
 */
@Mixin(GuiHooks.class)
public class GuiHooksMixin {

    @Inject(method = "createSkinsButton", at = @At("HEAD"), cancellable = true, remap = false)
    private static void tcr$createSkinsButton(Screen screen, GridLayout gridLayout, GridLayout.RowHelper rowHelper, CallbackInfo ci) {
        ci.cancel();
    }
}
