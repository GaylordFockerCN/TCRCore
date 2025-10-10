package com.p1nero.tcrcore.block;

import com.p1nero.tcrcore.TCRCoreMod;
import com.p1nero.tcrcore.block.custom.*;
import com.p1nero.tcrcore.item.TCRItems;
import com.p1nero.tcrcore.item.custom.SimpleDescriptionBlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class TCRBlocks {

    public static final DeferredRegister<Block> REGISTRY =
            DeferredRegister.create(ForgeRegistries.BLOCKS, TCRCoreMod.MOD_ID);

    public static final RegistryObject<Block> STORM_ALTAR_BLOCK = registerBlock("storm_altar_block",
            () -> new StormAltarBlock(BlockBehaviour.Properties.copy(Blocks.BEDROCK).noOcclusion().noLootTable()));
    public static final RegistryObject<Block> FLAME_ALTAR_BLOCK = registerBlock("flame_altar_block",
            () -> new FlameAltarBlock(BlockBehaviour.Properties.copy(Blocks.BEDROCK).noOcclusion().noLootTable()));
    public static final RegistryObject<Block> CURSED_ALTAR_BLOCK = registerBlock("cursed_altar_block",
            () -> new CursedAltarBlock(BlockBehaviour.Properties.copy(Blocks.BEDROCK).noOcclusion().noLootTable()));
    public static final RegistryObject<Block> ABYSS_ALTAR_BLOCK = registerBlock("abyss_altar_block",
            () -> new AbyssAltarBlock(BlockBehaviour.Properties.copy(Blocks.BEDROCK).noOcclusion().noLootTable()));
    public static final RegistryObject<Block> DESERT_ALTAR_BLOCK = registerBlock("desert_altar_block",
            () -> new DesertAltarBlock(BlockBehaviour.Properties.copy(Blocks.BEDROCK).noOcclusion().noLootTable()));
    public static final RegistryObject<Block> MECH_ALTAR_BLOCK = registerBlock("mech_altar_block",
            () -> new MechAltarBlock(BlockBehaviour.Properties.copy(Blocks.BEDROCK).noOcclusion().noLootTable()));
    public static final RegistryObject<Block> VOID_ALTAR_BLOCK = registerBlock("void_altar_block",
            () -> new VoidAltarBlock(BlockBehaviour.Properties.copy(Blocks.BEDROCK).noOcclusion().noLootTable()));
    public static final RegistryObject<Block> MONST_ALTAR_BLOCK = registerBlock("monst_altar_block",
            () -> new MonstAltarBlock(BlockBehaviour.Properties.copy(Blocks.BEDROCK).noOcclusion().noLootTable()));

    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = REGISTRY.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block) {
        return TCRItems.REGISTRY.register(name, () -> new SimpleDescriptionBlockItem(block.get(), new Item.Properties()));
    }

}
