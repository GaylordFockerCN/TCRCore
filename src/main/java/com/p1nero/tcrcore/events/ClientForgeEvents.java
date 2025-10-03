package com.p1nero.tcrcore.events;

import com.p1nero.dialog_lib.client.screen.DialogueScreen;
import com.p1nero.dialog_lib.events.ClientNpcEntityDialogueEvent;
import com.p1nero.tcrcore.TCRCoreMod;
import com.p1nero.tcrcore.client.gui.HandleIronGolemDialog;
import com.p1nero.tcrcore.client.gui.HandleVillagerDialog;
import com.p1nero.tcrcore.mixin.IronGolemMixin;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.npc.Villager;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGuiEvent;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

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
    }

    @SubscribeEvent
    public static void onDialogSend(ClientNpcEntityDialogueEvent event) {
        if(event.getSelf() instanceof Villager villager) {
            HandleVillagerDialog.openDialogScreen(villager, event.getLocalPlayer(), event.getServerData());
        }
        if(event.getSelf() instanceof IronGolem ironGolem) {
            HandleIronGolemDialog.openDialogScreen(ironGolem, event.getLocalPlayer(), event.getServerData());
        }
    }

}
