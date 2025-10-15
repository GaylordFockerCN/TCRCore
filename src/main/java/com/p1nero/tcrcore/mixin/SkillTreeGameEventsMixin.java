package com.p1nero.tcrcore.mixin;

import com.obscuria.aquamirae.Aquamirae;
import com.yesman.epicskills.event.GameEvents;
import com.yesman.epicskills.registry.entry.EpicSkillsItems;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraftforge.event.LootTableLoadEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameEvents.class)
public class SkillTreeGameEventsMixin {

    @Inject(method = "epicskills$lootTableLoad", at = @At("HEAD"), cancellable = true, remap = false)
    private static void tcr$epicskills$lootTableLoad(LootTableLoadEvent event, CallbackInfo ci) {
        // Low chance - many rolls
        if (
                event.getName().equals(BuiltInLootTables.SIMPLE_DUNGEON) ||
                        event.getName().equals(BuiltInLootTables.UNDERWATER_RUIN_SMALL) ||
                        event.getName().equals(BuiltInLootTables.UNDERWATER_RUIN_BIG) ||
                        event.getName().equals(BuiltInLootTables.ABANDONED_MINESHAFT) ||
                        event.getName().equals(BuiltInLootTables.NETHER_BRIDGE) ||
                        event.getName().equals(BuiltInLootTables.RUINED_PORTAL) ||
                        event.getName().equals(BuiltInLootTables.SHIPWRECK_SUPPLY) ||
                        event.getName().equals(BuiltInLootTables.SHIPWRECK_MAP) ||
                        event.getName().equals(BuiltInLootTables.BASTION_OTHER)
        ) {
            event
                    .getTable()
                    .addPool(
                            LootPool.lootPool()
                                    .setRolls(UniformGenerator.between(1.0F, 3.0F))
                                    .add(LootItem.lootTableItem(EpicSkillsItems.ABILIITY_STONE.get()))
                                    .when(LootItemRandomChanceCondition.randomChance(0.03F))
                                    .build()
                    );
        }

        // Middle chance - modest rolls
        if (
                event.getName().equals(BuiltInLootTables.ANCIENT_CITY) ||
                        event.getName().equals(BuiltInLootTables.END_CITY_TREASURE) ||
                        event.getName().equals(BuiltInLootTables.BASTION_BRIDGE) ||
                        event.getName().equals(BuiltInLootTables.BASTION_HOGLIN_STABLE) ||
                        event.getName().equals(BuiltInLootTables.DESERT_PYRAMID) ||
                        event.getName().equals(BuiltInLootTables.PILLAGER_OUTPOST) ||
                        event.getName().equals(BuiltInLootTables.JUNGLE_TEMPLE_DISPENSER) ||
                        event.getName().equals(BuiltInLootTables.SHIPWRECK_TREASURE) ||
                        event.getName().equals(BuiltInLootTables.STRONGHOLD_CORRIDOR) ||
                        event.getName().equals(BuiltInLootTables.STRONGHOLD_CROSSING) ||
                        event.getName().getNamespace().equals("dungeons_arise") ||
                        event.getName().getNamespace().equals("ati_structures")||
                        event.getName().getNamespace().equals("trek")||
                        (event.getName().getNamespace().equals(Aquamirae.MODID) && event.getName().getPath().contains("ship"))
        ) {
            event
                    .getTable()
                    .addPool(
                            LootPool.lootPool()
                                    .setRolls(UniformGenerator.between(1.0F, 2.0F))
                                    .add(LootItem.lootTableItem(EpicSkillsItems.ABILIITY_STONE.get()))
                                    .when(LootItemRandomChanceCondition.randomChance(0.05F))
                                    .build()
                    );
        }

        // High chance - one roll
        if (
                event.getName().equals(BuiltInLootTables.BURIED_TREASURE) ||
                        event.getName().equals(BuiltInLootTables.JUNGLE_TEMPLE) ||
                        event.getName().equals(BuiltInLootTables.STRONGHOLD_LIBRARY) ||
                        event.getName().equals(BuiltInLootTables.BASTION_TREASURE) ||
                        event.getName().equals(BuiltInLootTables.WOODLAND_MANSION) ||
                        (event.getName().getNamespace().equals(Aquamirae.MODID) && event.getName().getPath().contains("frozen_chest"))

        ) {
            event
                    .getTable()
                    .addPool(
                            LootPool.lootPool()
                                    .setRolls(ConstantValue.exactly(1.0F))
                                    .add(LootItem.lootTableItem(EpicSkillsItems.ABILIITY_STONE.get()))
                                    .when(LootItemRandomChanceCondition.randomChance(0.1F))
                                    .build()
                    );
        }

        // High chance - one roll
        if (
                        (event.getName().getNamespace().equals(Aquamirae.MODID) && event.getName().getPath().contains("frozen_chest"))
        ) {
            event
                    .getTable()
                    .addPool(
                            LootPool.lootPool()
                                    .setRolls(ConstantValue.exactly(1.0F))
                                    .add(LootItem.lootTableItem(EpicSkillsItems.ABILIITY_STONE.get()))
                                    .when(LootItemRandomChanceCondition.randomChance(0.8F))
                                    .build()
                    );
        }
        ci.cancel();
    }
}
