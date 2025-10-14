package com.p1nero.tcrcore.events;

import com.p1nero.dialog_lib.client.screen.DialogueScreen;
import com.p1nero.dialog_lib.events.ClientNpcEntityDialogueEvent;
import com.p1nero.tcrcore.TCRCoreMod;
import com.p1nero.tcrcore.client.gui.*;
import net.alp.monsterexpansion.entity.custom.AbstractLargeMonster;
import net.alp.monsterexpansion.entity.custom.SkrytheEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.npc.Villager;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGuiEvent;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.shelmarow.nightfall_invade.entity.spear_knight.Arterius;

@Mod.EventBusSubscriber(modid = TCRCoreMod.MOD_ID, value = Dist.CLIENT)
public class ClientForgeEvents {

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

}
