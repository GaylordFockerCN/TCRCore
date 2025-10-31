package com.p1nero.tcrcore.client.gui.widget;

import com.yesman.epicskills.EpicSkills;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import yesman.epicfight.client.gui.screen.SkillEditScreen;
import yesman.epicfight.world.capabilities.skill.CapabilitySkill;
import yesman.epicfight.world.item.EpicFightItems;

@OnlyIn(Dist.CLIENT)
public class OpenSkillEditorButton extends AbstractButton {
        private final CapabilitySkill playerSkills;
		public OpenSkillEditorButton(int x, int y, int width, int height, CapabilitySkill playerSkills) {
			super(x, y, width, height, Component.empty());
			this.setTooltip(Tooltip.create(Component.translatable(EpicSkills.format("gui.%s.openskilleditor.tooltip"))));
            this.playerSkills = playerSkills;
		}
		
		@Override
		protected void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
			super.renderWidget(guiGraphics, mouseX, mouseY, partialTick);
			
			guiGraphics.renderItem(EpicFightItems.SKILLBOOK.get().getDefaultInstance(), this.getX() + 2, this.getY() + 2);
		}
		
		@Override
		protected void updateWidgetNarration(NarrationElementOutput narrationElementOutput) {
			this.defaultButtonNarrationText(narrationElementOutput);
		}
		
		@Override
		public void onPress() {
			Minecraft.getInstance().setScreen(new SkillEditScreen(Minecraft.getInstance().player, playerSkills));
		}
	}