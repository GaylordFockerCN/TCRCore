package com.p1nero.tcrcore.item;

import com.p1nero.tcrcore.TCRCoreMod;
import com.p1nero.tcrcore.item.custom.OracleItem;
import com.p1nero.tcrcore.item.custom.SimpleDescriptionBlockItem;
import com.p1nero.tcrcore.item.custom.SimpleDescriptionItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tiers;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class TCRItems {
    public static final DeferredRegister<Item> REGISTRY = DeferredRegister.create(ForgeRegistries.ITEMS, TCRCoreMod.MOD_ID);
    public static final RegistryObject<Item> ANCIENT_ORACLE_FRAGMENT = REGISTRY.register("ancient_oracle_fragment", () -> new OracleItem(new Item.Properties().stacksTo(1).rarity(Rarity.EPIC).fireResistant()));
    public static final RegistryObject<Item> ARTIFACT_TICKET = REGISTRY.register("artifact_ticket", () -> new SimpleDescriptionItem(new Item.Properties().stacksTo(1).rarity(Rarity.RARE).fireResistant(), true));
    public static final RegistryObject<Item> RARE_ARTIFACT_TICKET = REGISTRY.register("rare_artifact_ticket", () -> new SimpleDescriptionItem(new Item.Properties().stacksTo(1).rarity(Rarity.EPIC).fireResistant(), true));
}
