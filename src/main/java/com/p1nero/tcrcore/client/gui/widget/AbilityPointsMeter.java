package com.p1nero.tcrcore.client.gui.widget;

import com.p1nero.tcrcore.mixin.SkillTreeScreenAccessor;
import com.yesman.epicskills.client.gui.screen.SkillTreeScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
    public class AbilityPointsMeter extends AbstractWidget {
        private static final ResourceLocation ABILITY_POINTS_ICON = ResourceLocation.fromNamespaceAndPath("epicskills", "textures/gui/widget/ability_points.png");
        private static final Component ABILITY_POINTS_TOOLTIP = Component.translatable("gui.epicskills.abilitypoints.tooltip");
        private final SkillTreeScreen screen;
        public AbilityPointsMeter(int x, int y, SkillTreeScreen screen) {
            super(x, y, 0, 14, Component.empty());
            this.setTooltip(Tooltip.create(ABILITY_POINTS_TOOLTIP));
            this.screen = screen;
        }

        protected void renderWidget(GuiGraphics pGuiGraphics, int mouseX, int mouseY, float pPartialTick) {
            pGuiGraphics.blit(ABILITY_POINTS_ICON, this.getX(), this.getY(), 0, 0.0F, 0.0F, 16, 16, 16, 16);
            String abilityPoints = String.valueOf(((SkillTreeScreenAccessor)screen).getAbilityPoints().getAbilityPoints());
            this.width = Minecraft.getInstance().font.width(abilityPoints) + 24;
            pGuiGraphics.drawString(Minecraft.getInstance().font, abilityPoints, this.getX() + 24, this.getY() + 4, -1);
        }

        protected void updateWidgetNarration(NarrationElementOutput pNarrationElementOutput) {
            this.defaultButtonNarrationText(pNarrationElementOutput);
        }
    }
