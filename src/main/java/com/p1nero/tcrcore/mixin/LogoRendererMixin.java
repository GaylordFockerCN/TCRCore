package com.p1nero.tcrcore.mixin;

import com.p1nero.tcrcore.TCRCoreMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.LogoRenderer;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LogoRenderer.class)
public class LogoRendererMixin {
    @Unique
    private static final ResourceLocation MY_LOGO = ResourceLocation.fromNamespaceAndPath(TCRCoreMod.MOD_ID, "textures/gui/title/title_zh.png");
    private static final ResourceLocation MY_LOGO_EN = ResourceLocation.fromNamespaceAndPath(TCRCoreMod.MOD_ID, "textures/gui/title/title_en.png");
    @Shadow @Final private boolean keepLogoThroughFade;

    @Inject(method = "renderLogo(Lnet/minecraft/client/gui/GuiGraphics;IFI)V", at = @At("HEAD"), cancellable = true)
    private void tcr$renderLogo(GuiGraphics p_281856_, int p_281512_, float p_281290_, int y, CallbackInfo ci) {
        p_281856_.setColor(1.0F, 1.0F, 1.0F, this.keepLogoThroughFade ? 1.0F : p_281290_);
        if(Minecraft.getInstance().getLanguageManager().getSelected().contains("zh")) {
            int i = p_281512_ / 2 - 170;
            p_281856_.blit(MY_LOGO, i, y, 339,72, 0.0F, 0.0F, 1272, 273, 1272, 273);
        } else {
            int i = p_281512_ / 2 - 152;
            p_281856_.blit(MY_LOGO_EN, i, y, 304, 70, 0.0F, 0.0F, 1140, 262, 1140, 262);
        }
        p_281856_.setColor(1.0F, 1.0F, 1.0F, 1.0F);
        ci.cancel();
    }
}
