package com.p1nero.tcrcore.events;

import com.github.L_Ender.cataclysm.Cataclysm;
import com.mojang.blaze3d.systems.RenderSystem;
import com.obscuria.obscureapi.api.BossBarsRenderManager;
import com.p1nero.dialog_lib.client.screen.DialogueScreen;
import com.p1nero.dialog_lib.events.ClientNpcEntityDialogueEvent;
import com.p1nero.tcrcore.TCRCoreMod;
import com.p1nero.tcrcore.client.gui.*;
import net.alp.monsterexpansion.entity.custom.AbstractLargeMonster;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FastColor;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.npc.Villager;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.CustomizeGuiOverlayEvent;
import net.minecraftforge.client.event.RenderGuiEvent;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.shelmarow.nightfall_invade.entity.spear_knight.Arterius;

import java.util.Optional;

import static net.minecraft.client.gui.components.BossHealthOverlay.GUI_BARS_LOCATION;

@Mod.EventBusSubscriber(modid = TCRCoreMod.MOD_ID, value = Dist.CLIENT)
public class ClientForgeEvents {

    public static final ResourceLocation BACKGROUND_LOCATION = ResourceLocation.fromNamespaceAndPath(Cataclysm.MODID,"textures/block/azure_seastone_bricks.png");

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event){
        if(Minecraft.getInstance().player != null) {
            CustomGuiderRenderer.tick(Minecraft.getInstance().player);
        }
    }

    /**
     * FIXME 官方要改的话记得删
     */
    @SubscribeEvent
    public static void onRenderBossBar(CustomizeGuiOverlayEvent.BossEventProgress event){
        Optional<BossBarsRenderManager.Style> style = BossBarsRenderManager.getStyle(event.getBossEvent().getName());
        if (style.isPresent()) {
            Component component = event.getBossEvent().getName();
            if (style.get().shouldRenderBar()) {
                RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
                RenderSystem.setShaderTexture(0, GUI_BARS_LOCATION);
            }
            Minecraft minecraft = Minecraft.getInstance();
            style.get().getFunction().render(minecraft, event.getGuiGraphics(), event.getX(), event.getY(), event.getBossEvent(), component);
            if (style.get().shouldRenderName()) {
                int x = event.getWindow().getGuiScaledWidth() / 2 - minecraft.font.width(component) / 2;
                int y = event.getY() - 9;
                event.getGuiGraphics().drawString(minecraft.font, component, x, y, 16777215);
            }

            event.setIncrement(style.get().getIncrement(minecraft));
        }
    }

    @SubscribeEvent
    public static void onRenderOverlay(RenderGuiOverlayEvent.Pre event){
        if(Minecraft.getInstance().screen instanceof DialogueScreen) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void onRenderGui(RenderGuiEvent.Pre event) {
        if(!Minecraft.getInstance().isPaused() && Minecraft.getInstance().screen == null && Minecraft.getInstance().player != null) {
            CustomGuiderRenderer.render(Minecraft.getInstance().player, event.getGuiGraphics(), event.getWindow(), event.getPartialTick());
        }
    }

    @SubscribeEvent
    public static void onDialogSend(ClientNpcEntityDialogueEvent event) {
        if(event.getSelf() instanceof Villager villager) {
            HandleVillagerDialog.openDialogScreen(villager, event.getLocalPlayer(), event.getServerData());
        }
        if(event.getSelf() instanceof IronGolem ironGolem) {
            HandleIronGolemDialog.openDialogScreen(ironGolem, event.getLocalPlayer(), event.getServerData());
        }
        if(event.getSelf() instanceof AbstractLargeMonster<?, ?> abstractLargeMonster) {
            HandleSkrytheEntityDialog.openDialogScreen(abstractLargeMonster, event.getLocalPlayer(), event.getServerData());
        }
        if(event.getSelf() instanceof Arterius arterius) {
            HandleArteriusDialog.openDialogScreen(arterius, event.getLocalPlayer(), event.getServerData());
        }
    }

    @SubscribeEvent
    public static void onRenderBackground(ScreenEvent.BackgroundRendered event) {
        if(Minecraft.getInstance().level == null) {
            event.getGuiGraphics().blit(BACKGROUND_LOCATION, 0, 0, 0, 0.0F, 0.0F, event.getScreen().width, event.getScreen().height, 32, 32);
//            event.getGuiGraphics().fill(0, 0, event.getScreen().width, event.getScreen().height, FastColor.ABGR32.color(255, 255, 255, 255));
        }
    }

}
