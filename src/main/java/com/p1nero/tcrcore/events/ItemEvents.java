package com.p1nero.tcrcore.events;


import com.github.L_Ender.cataclysm.init.ModItems;
import com.p1nero.tcrcore.TCRCoreMod;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Mod.EventBusSubscriber(modid = TCRCoreMod.MOD_ID)
public class ItemEvents {

    public static Set<Item> items = new HashSet<>();
    @SubscribeEvent
    public static void onItemDesc(ItemTooltipEvent event) {
        if (List.of(ModItems.MONSTROUS_EYE.get(), ModItems.VOID_EYE.get(), ModItems.MECH_EYE.get(), ModItems.ABYSS_EYE.get(), ModItems.STORM_EYE.get(), ModItems.CURSED_EYE.get(), ModItems.FLAME_EYE.get(), ModItems.DESERT_EYE.get()).contains(event.getItemStack().getItem())) {
            event.getToolTip().add(TCRCoreMod.getInfo("time_to_altar").withStyle(ChatFormatting.GRAY));
        }
        if (List.of(ModItems.ABYSS_EYE.get(), ModItems.STORM_EYE.get(), ModItems.CURSED_EYE.get(), ModItems.DESERT_EYE.get()).contains(event.getItemStack().getItem())) {
            event.getToolTip().add(TCRCoreMod.getInfo("time_to_ask_godness_statue"));
        }
        //TODO delete
//        event.getToolTip().add(1, Component.literal(BuiltInRegistries.ITEM.getKey(event.getItemStack().getItem()).toString()));
        if(items.contains(event.getItemStack().getItem())) {
            event.getToolTip().add(1, Component.translatable(event.getItemStack().getItem().getDescriptionId() + ".tcr_info"));
        }

        if(PlayerEventListeners.illegalItems.contains(event.getItemStack().getItem())) {
            event.getToolTip().add(1, TCRCoreMod.getInfo("illegal_item_tip"));
        }

    }

}
