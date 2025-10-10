package com.p1nero.tcrcore.item;

import com.p1nero.tcrcore.TCRCoreMod;
import com.p1nero.tcrcore.block.TCRBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class TCRItemTabs {

    public static final DeferredRegister<CreativeModeTab> REGISTRY = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, TCRCoreMod.MOD_ID);
    public static final RegistryObject<CreativeModeTab> ITEMS = REGISTRY.register("items",
            () -> CreativeModeTab.builder().title(Component.translatable("itemGroup.tcr.items")).icon(() -> new ItemStack(TCRItems.ANCIENT_ORACLE_FRAGMENT.get())).displayItems((params, output) -> {
                output.accept(TCRBlocks.CURSED_ALTAR_BLOCK.get());
                output.accept(TCRBlocks.ABYSS_ALTAR_BLOCK.get());
                output.accept(TCRBlocks.DESERT_ALTAR_BLOCK.get());
                output.accept(TCRBlocks.FLAME_ALTAR_BLOCK.get());
                output.accept(TCRBlocks.STORM_ALTAR_BLOCK.get());
                output.accept(TCRBlocks.VOID_ALTAR_BLOCK.get());
                output.accept(TCRBlocks.MECH_ALTAR_BLOCK.get());
                output.accept(TCRBlocks.MONST_ALTAR_BLOCK.get());
                output.accept(TCRItems.ANCIENT_ORACLE_FRAGMENT.get());
                output.accept(TCRItems.ARTIFACT_TICKET.get());
                output.accept(TCRItems.RARE_ARTIFACT_TICKET.get());
            }).build());
}
