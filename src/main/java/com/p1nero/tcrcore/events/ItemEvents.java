package com.p1nero.tcrcore.events;

import com.p1nero.tcrcore.TCRCoreMod;
import com.p1nero.tcrcore.capability.PlayerDataManager;
import com.p1nero.tcrcore.capability.TCRPlayer;
import net.genzyuro.uniqueaccessories.registry.UAItems;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.HashSet;
import java.util.Set;

@Mod.EventBusSubscriber(modid = TCRCoreMod.MOD_ID)
public class ItemEvents {

    public static Set<Item> additionalInfoItems = new HashSet<>();
    public static Set<Item> eyes = new HashSet<>();

    @SubscribeEvent
    public static void onItemDesc(ItemTooltipEvent event) {
        if (eyes.contains(event.getItemStack().getItem()) && event.getEntity() != null) {
            event.getToolTip().add(TCRCoreMod.getInfo("time_to_altar").withStyle(ChatFormatting.GRAY));
            event.getToolTip().add(TCRCoreMod.getInfo("time_to_ask_godness_statue"));
            if(event.getItemStack().hasTag() && event.getItemStack().getOrCreateTag().contains(TCRPlayer.PLAYER_NAME)) {
                String playerName = event.getItemStack().getOrCreateTag().getString(TCRPlayer.PLAYER_NAME);
                event.getToolTip().add(Component.literal("——" + playerName).withStyle(ChatFormatting.AQUA));
            }
        }
        //TODO delete
//        event.getToolTip().add(1, Component.literal(BuiltInRegistries.ITEM.getKey(event.getItemStack().getItem()).toString()));
        if(additionalInfoItems.contains(event.getItemStack().getItem())) {
            event.getToolTip().add(1, Component.translatable(event.getItemStack().getItem().getDescriptionId() + ".tcr_info"));
        }

        if((!PlayerDataManager.wraithonKilled.get(event.getEntity()) || event.getItemStack().is(UAItems.STARVED_WOLF_SKULL.get())) && PlayerEventListeners.illegalItems.contains(event.getItemStack().getItem())) {
            event.getToolTip().add(1, TCRCoreMod.getInfo("illegal_item_tip2"));
        }

    }

}
