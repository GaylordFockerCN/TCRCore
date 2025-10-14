package com.p1nero.tcrcore.client.gui;

import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.systems.RenderSystem;
import com.p1nero.tcrcore.TCRCoreMod;
import com.p1nero.tcrcore.capability.TCRTaskManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class CustomGuiderRenderer {
    private static long fadeStartTime = 0;
    private static boolean hasTask = false;
    private static boolean lastHasTask = false;
    private static float alpha = 0.0f;
    private static final int FADE_DURATION = 30; // 30 ticks = 1.5 seconds
    private static Component lastTaskDesc = Component.empty();

    public static final ResourceLocation TASK_ICON = ResourceLocation.fromNamespaceAndPath(TCRCoreMod.MOD_ID, "textures/gui/task_icon.png");

    public static void render(LocalPlayer localPlayer, GuiGraphics guiGraphics, Window window, float partialTick) {
        Minecraft minecraft = Minecraft.getInstance();
        long currentTime = localPlayer.level().getGameTime();

        hasTask = TCRTaskManager.hasTask(localPlayer);
        // Handle state changes
        if (hasTask != lastHasTask) {
            fadeStartTime = currentTime;
            if (hasTask) {
                // Update task description when task appears
                lastTaskDesc = TCRTaskManager.getCurrentTaskDesc(localPlayer);
            }
        }

        // Calculate alpha based on game time with partialTick interpolation
        long timeSinceStateChange = currentTime - fadeStartTime;
        float interpolatedTime = timeSinceStateChange + partialTick;

        if (hasTask) {
            // Fade in
            if (interpolatedTime < FADE_DURATION) {
                alpha = interpolatedTime / FADE_DURATION;
            } else {
                alpha = 1.0f;
            }
        } else {
            // Fade out
            if (interpolatedTime < FADE_DURATION) {
                alpha = 1.0f - (interpolatedTime / FADE_DURATION);
            } else {
                alpha = 0.0f;
            }
        }

        lastHasTask = hasTask;

        // Only render if there's something to show and alpha > 0
        if (alpha <= 0.0f || lastTaskDesc == null || lastTaskDesc.getString().isEmpty()) {
            return;
        }

        // Calculate position (golden ratio - left side, about 38.2% from top)
        int screenWidth = window.getGuiScaledWidth();
        int screenHeight = window.getGuiScaledHeight();
        int goldenRatioY = (int) (screenHeight * 0.382f);
        int x = 10; // 10 pixels from left edge
        int y = goldenRatioY;

        guiGraphics.pose().pushPose();
        guiGraphics.pose().scale(0.8F, 0.8F, 0.8F);
        // Set up alpha for rendering
        RenderSystem.enableBlend();

        // Draw icon (16x16)
        guiGraphics.setColor(1.0f, 1.0f, 1.0f, alpha);
        guiGraphics.blit(TASK_ICON, x, y, 0, 0, 16, 16, 16, 16);

        // Draw text with shadow
        int textX = x + 20; // 4 pixels spacing after icon
        int textY = y + 4; // Vertically center with 16px icon

        // Get font from Minecraft instance
        var font = minecraft.font;

        // Draw text with shadow and alpha
        int textColor = (int) (alpha * 255) << 24 | 0xFFFFFF; // White text with alpha
        int shadowColor = (int) (alpha * 128) << 24; // Black shadow with half alpha

        // Draw main text
        guiGraphics.drawString(font, lastTaskDesc, textX, textY, textColor, true);

        // Reset color
        guiGraphics.setColor(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.disableBlend();
        guiGraphics.pose().popPose();
    }

    // Helper method to reset state when needed (e.g., when GUI is closed)
    public static void reset() {
        fadeStartTime = 0;
        alpha = 0.0f;
        hasTask = false;
        lastHasTask = false;
        lastTaskDesc = Component.empty();
    }
}