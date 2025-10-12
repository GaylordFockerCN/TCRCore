package com.p1nero.tcrcore.datagen.loot;

import com.github.L_Ender.cataclysm.init.ModEntities;
import com.p1nero.tcrcore.entity.TCREntities;
import com.p1nero.tcrcore.item.TCRItems;
import com.yesman.epicskills.registry.entry.EpicSkillsItems;
import net.minecraft.data.loot.EntityLootSubProvider;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootTableReference;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

import java.util.stream.Stream;

public class TCREntityLootTables extends EntityLootSubProvider {

    protected TCREntityLootTables() {
        super(FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    public void generate() {
        this.add(TCREntities.GUIDER.get(), emptyLootTable());
        this.add(TCREntities.GIRL.get(), emptyLootTable());
        this.add(TCREntities.TUTORIAL_GOLEM.get(), emptyLootTable());
//        this.add(ModEntities.THE_LEVIATHAN.get(), LootTable.lootTable().withPool(
//                        LootPool.lootPool()
//                                .setRolls(UniformGenerator.between(1.0F, 2.0F))
//                                .add(LootItem.lootTableItem(TCRItems.ABYSS_CORE.get()))
//                )
//        );
//        this.add(ModEntities.ENDER_GUARDIAN.get(), LootTable.lootTable().withPool(
//                        LootPool.lootPool()
//                                .setRolls(UniformGenerator.between(1.0F, 2.0F))
//                                .add(LootItem.lootTableItem(TCRItems.VOID_CORE.get()))
//                )
//        );
    }

    public LootTable.Builder emptyLootTable() {
        return LootTable.lootTable();
    }

    public LootTable.Builder fromEntityLootTable(EntityType<?> parent) {
        return LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootTableReference.lootTableReference(parent.getDefaultLootTable())));
    }

    private static LootTable.Builder sheepLootTableBuilderWithDrop(ItemLike wool) {
        return LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1)).add(LootItem.lootTableItem(wool))).withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1)).add(LootTableReference.lootTableReference(EntityType.SHEEP.getDefaultLootTable())));
    }

    @Override
    protected @NotNull Stream<EntityType<?>> getKnownEntityTypes() {
        return TCREntities.REGISTRY.getEntries().stream().map(RegistryObject::get);
    }
}