package com.p1nero.tcrcore.block.entity;

import com.p1nero.tcrcore.TCRCoreMod;
import com.p1nero.tcrcore.block.TCRBlocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class TCRBlockEntities {

    public static final DeferredRegister<BlockEntityType<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, TCRCoreMod.MOD_ID);

    public static final RegistryObject<BlockEntityType<StormAltarBlockEntity>> STORM_ALTAR_BLOCK_ENTITY =
            REGISTRY.register("storm_altar_block_entity", () -> BlockEntityType.Builder.of(StormAltarBlockEntity::new, TCRBlocks.STORM_ALTAR_BLOCK.get()).build(null));
    public static final RegistryObject<BlockEntityType<FlameAltarBlockEntity>> FLAME_ALTAR_BLOCK_ENTITY =
            REGISTRY.register("flame_altar_block_entity", () -> BlockEntityType.Builder.of(FlameAltarBlockEntity::new, TCRBlocks.FLAME_ALTAR_BLOCK.get()).build(null));
    public static final RegistryObject<BlockEntityType<AbyssAltarBlockEntity>> ABYSS_ALTAR_BLOCK_ENTITY =
            REGISTRY.register("abyss_altar_block_entity", () -> BlockEntityType.Builder.of(AbyssAltarBlockEntity::new, TCRBlocks.ABYSS_ALTAR_BLOCK.get()).build(null));
    public static final RegistryObject<BlockEntityType<DesertAltarBlockEntity>> DESERT_ALTAR_BLOCK_ENTITY =
            REGISTRY.register("desert_altar_altar_block_entity", () -> BlockEntityType.Builder.of(DesertAltarBlockEntity::new, TCRBlocks.DESERT_ALTAR_BLOCK.get()).build(null));
    public static final RegistryObject<BlockEntityType<CursedAltarBlockEntity>> CURSED_ALTAR_BLOCK_ENTITY =
            REGISTRY.register("cursed_altar_block_entity", () -> BlockEntityType.Builder.of(CursedAltarBlockEntity::new, TCRBlocks.CURSED_ALTAR_BLOCK.get()).build(null));
    public static final RegistryObject<BlockEntityType<MechAltarBlockEntity>> MECH_ALTAR_BLOCK_ENTITY =
            REGISTRY.register("mech_altar_block_entity", () -> BlockEntityType.Builder.of(MechAltarBlockEntity::new, TCRBlocks.MECH_ALTAR_BLOCK.get()).build(null));
    public static final RegistryObject<BlockEntityType<VoidAltarBlockEntity>> VOID_ALTAR_BLOCK_ENTITY =
            REGISTRY.register("void_altar_block_entity", () -> BlockEntityType.Builder.of(VoidAltarBlockEntity::new, TCRBlocks.VOID_ALTAR_BLOCK.get()).build(null));
    public static final RegistryObject<BlockEntityType<MonstAltarBlockEntity>> MONST_ALTAR_BLOCK_ENTITY =
            REGISTRY.register("monst_altar_block_entity", () -> BlockEntityType.Builder.of(MonstAltarBlockEntity::new, TCRBlocks.MONST_ALTAR_BLOCK.get()).build(null));

}
