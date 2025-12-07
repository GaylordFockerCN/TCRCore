package com.p1nero.tcrcore.datagen;

import com.github.L_Ender.cataclysm.Cataclysm;
import com.github.L_Ender.cataclysm.init.ModItems;
import com.p1nero.tcrcore.TCRCoreMod;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.critereon.*;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.ForgeAdvancementProvider;
import net.p1nero.ss.item.SwordSoaringItems;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class TCRAdvancementData extends ForgeAdvancementProvider {

    public static final String PRE = "advancement." + TCRCoreMod.MOD_ID + ".";
    public TCRAdvancementData(PackOutput output, CompletableFuture<HolderLookup.Provider> registries, ExistingFileHelper helper) {
        super(output, registries, helper, List.of(new TCRAdvancements()));

    }

    public static class TCRAdvancements implements AdvancementGenerator {
        private Consumer<Advancement> consumer;
        private ExistingFileHelper helper;

        @SuppressWarnings("unused")
        @Override
        public void generate(HolderLookup.@NotNull Provider provider, @NotNull Consumer<Advancement> consumer, @NotNull ExistingFileHelper existingFileHelper) {
            this.consumer = consumer;
            this.helper = existingFileHelper;

            Advancement root = Advancement.Builder.advancement()
                    .display(ModItems.STORM_EYE.get(),
                            Component.translatable(PRE + TCRCoreMod.MOD_ID),
                            Component.translatable(PRE + TCRCoreMod.MOD_ID + ".desc"),
                            ResourceLocation.fromNamespaceAndPath(Cataclysm.MODID,"textures/block/azure_seastone_bricks.png"),
                            FrameType.TASK, false, false, false)
                    .addCriterion(TCRCoreMod.MOD_ID, new ImpossibleTrigger.TriggerInstance())
                    .save(consumer, ResourceLocation.fromNamespaceAndPath(TCRCoreMod.MOD_ID, TCRCoreMod.MOD_ID), existingFileHelper);

            Advancement kill_pillager = registerAdvancement(root, "kill_pillager", FrameType.CHALLENGE, Items.GOAT_HORN, true, true, true, KilledTrigger.TriggerInstance.playerKilledEntity(EntityPredicate.Builder.entity().of(EntityTypeTags.RAIDERS)));
            Advancement mark_map = registerAdvancement(kill_pillager, "mark_map", FrameType.TASK, Items.MAP, true, true, true, new ImpossibleTrigger.TriggerInstance());

//            Advancement storm_eye = registerAdvancement(mark_map, "storm_eye", FrameType.CHALLENGE, ModItems.MUSIC_DISC_SCYLLA.get(), true, true, false, InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.STORM_EYE.get()));
//            Advancement abyss_eye = registerAdvancement(mark_map, "abyss_eye", FrameType.CHALLENGE, ModItems.ABYSS_EYE.get(), true, true, false, InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.ABYSS_EYE.get()));
//            Advancement flame_eye = registerAdvancement(mark_map, "flame_eye", FrameType.CHALLENGE, ModItems.FLAME_EYE.get(), true, true, false, InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.FLAME_EYE.get()));
//            Advancement desert_eye = registerAdvancement(mark_map, "desert_eye", FrameType.CHALLENGE, ModItems.REMNANT_SKULL.get(), true, true, false, InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.DESERT_EYE.get()));
//            Advancement cursed_eye = registerAdvancement(mark_map, "cursed_eye", FrameType.CHALLENGE, AquamiraeItems.SHIP_GRAVEYARD_ECHO.get(), true, true, false, InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.CURSED_EYE.get()));

            Advancement vatansever = Advancement.Builder.advancement()
                    .display(SwordSoaringItems.VATANSEVER.get(),
                            SwordSoaringItems.VATANSEVER.get().getDescription(),
                            Component.literal(""),
                            null,
                            FrameType.CHALLENGE, true, true, false)
                    .addCriterion(TCRCoreMod.MOD_ID, InventoryChangeTrigger.TriggerInstance.hasItems(SwordSoaringItems.VATANSEVER.get()))
                    .save(consumer, ResourceLocation.fromNamespaceAndPath(TCRCoreMod.MOD_ID, "vatansever"), existingFileHelper);

//            Advancement flame_kill = registerAdvancement(flame_eye, "flame_kill", FrameType.CHALLENGE, ModItems.THE_INCINERATOR.get(), true, true, false, KilledTrigger.TriggerInstance.playerKilledEntity(EntityPredicate.Builder.entity().of(ModEntities.IGNIS.get())));
//            Advancement abyss_kill = registerAdvancement(abyss_eye, "abyss_kill", FrameType.CHALLENGE, ModItems.TIDAL_CLAWS.get(), true, true, false, KilledTrigger.TriggerInstance.playerKilledEntity(EntityPredicate.Builder.entity().of(ModEntities.THE_LEVIATHAN.get())));
//            Advancement storm_kill = registerAdvancement(storm_eye, "storm_kill", FrameType.CHALLENGE, ModItems.CERAUNUS.get(), true, true, false, KilledTrigger.TriggerInstance.playerKilledEntity(EntityPredicate.Builder.entity().of(ModEntities.SCYLLA.get())));
//            Advancement desert_kill = registerAdvancement(desert_eye, "desert_kill", FrameType.CHALLENGE, ModItems.WRATH_OF_THE_DESERT.get(), true, true, false, KilledTrigger.TriggerInstance.playerKilledEntity(EntityPredicate.Builder.entity().of(ModEntities.ANCIENT_REMNANT.get())));
//            Advancement cursed_kill = registerAdvancement(cursed_eye, "cursed_kill", FrameType.CHALLENGE, ModItems.SOUL_RENDER.get(), true, true, false, KilledTrigger.TriggerInstance.playerKilledEntity(EntityPredicate.Builder.entity().of(ModEntities.MALEDICTUS.get())));

            Advancement dragonRide = registerAdvancement(root, "dragon_tame", FrameType.CHALLENGE, net.saksolm.monsterexpansion.item.ModItems.SKRYTHE_WING_MEMBRANE.get(), true, true, false, TameAnimalTrigger.TriggerInstance.tamedAnimal(EntityPredicate.Builder.entity().of(net.saksolm.monsterexpansion.entity.ModEntities.SKRYTHE.get()).build()));

            Advancement stage1 = registerAdvancement(root, "stage1", FrameType.CHALLENGE, Items.ANVIL, false, false, true);
            Advancement stage2 = registerAdvancement(root, "stage2", FrameType.CHALLENGE, Items.ANVIL, false, false, true);
            Advancement stage3 = registerAdvancement(root, "stage3", FrameType.CHALLENGE, Items.ANVIL, false, false, true);
            Advancement stage4 = registerAdvancement(root, "stage4", FrameType.CHALLENGE, Items.ANVIL, false, false, true);
            Advancement stage5 = registerAdvancement(root, "stage5", FrameType.CHALLENGE, Items.ANVIL, false, false, true);
//            Advancement weaponRoot = Advancement.Builder.advancement()
//                    .display(ModItems.THE_INCINERATOR.get(),
//                            Component.translatable(PRE + TCRCoreMod.MOD_ID + "_weapon"),
//                            Component.translatable(PRE + TCRCoreMod.MOD_ID + "_weapon" + ".desc"),
//                            ResourceLocation.fromNamespaceAndPath(Cataclysm.MODID, "textures/block/obsidian_bricks.png"),
//                            FrameType.GOAL, false, false, false)
//                    .addCriterion(TCRCoreMod.MOD_ID, new ImpossibleTrigger.TriggerInstance())
//                    .save(consumer, ResourceLocation.fromNamespaceAndPath(TCRCoreMod.MOD_ID, TCRCoreMod.MOD_ID + "_weapon"), existingFileHelper);
//
//            //海灵物语
//            Advancement ship = registerAdvancement(weaponRoot, "aquamirae_weapon", FrameType.GOAL, AquamiraeItems.RUNE_OF_THE_STORM.get(), true, true, false, new ImpossibleTrigger.TriggerInstance());
//
//            Advancement d = registerItemAdvancement(ship, AquamiraeItems.DIVIDER.get(), true);
//            Advancement mz = registerItemAdvancement(d, AquamiraeItems.MAZE_ROSE.get(), true);
//            Advancement pc = registerItemAdvancement(mz, AquamiraeItems.POISONED_CHAKRA.get(), true);
//            Advancement ws = registerItemAdvancement(pc, AquamiraeItems.WHISPER_OF_THE_ABYSS.get(), true);
//
//            Advancement dg = registerItemAdvancement(ship, AquamiraeItems.DAGGER_OF_GREED.get(), true);
//            Advancement pb = registerItemAdvancement(dg, AquamiraeItems.POISONED_BLADE.get(), true);
//            Advancement fc = registerItemAdvancement(pb, AquamiraeItems.FIN_CUTTER.get(), true);
//            Advancement ts = registerItemAdvancement(fc, AquamiraeItems.TERRIBLE_SWORD.get(), true);
//            Advancement cl = registerItemAdvancement(ts, AquamiraeItems.CORAL_LANCE.get(), true);
//
//            //灾变
//            Advancement cataclysm_weapon = registerAdvancement(weaponRoot, "cataclysm_weapon", FrameType.GOAL, ModItems.STRANGE_KEY.get(), true, true, false, new ImpossibleTrigger.TriggerInstance());
//            Advancement athame = registerItemAdvancement(cataclysm_weapon, ModItems.ATHAME.get(), true);
//            Advancement co = registerItemAdvancement(athame, ModItems.CORAL_SPEAR.get(), true);
//            Advancement cob = registerItemAdvancement(co, ModItems.CORAL_BARDICHE.get(), true);
//            Advancement bss = registerItemAdvancement(cob, ModItems.BLACK_STEEL_SWORD.get(), true);
//            Advancement kh = registerItemAdvancement(bss, ModItems.KHOPESH.get(), true);
//            Advancement as = registerItemAdvancement(kh, ModItems.ANCIENT_SPEAR.get(), true);
//
//            //传说
//            Advancement legend_weapon = registerAdvancement(weaponRoot, "legend_weapon", FrameType.GOAL, ModItems.BURNING_ASHES.get(), true, true, false, new ImpossibleTrigger.TriggerInstance());
//
//            Advancement vt = registerItemAdvancement(legend_weapon, SwordSoaringItems.VATANSEVER.get());
//            Advancement nds = registerItemAdvancement(vt, EFNItem.Aetherial_Dusk_DualSword.get());
//            Advancement ymd = registerItemAdvancement(nds, EFNItem.YAMATO_DMC4.get());
//
//            Advancement the = registerItemAdvancement(legend_weapon, ModItems.THE_INCINERATOR.get());
//            Advancement wt = registerItemAdvancement(the, ModItems.WRATH_OF_THE_DESERT.get());
//            Advancement sr = registerItemAdvancement(wt, ModItems.SOUL_RENDER.get());
//            Advancement cer = registerItemAdvancement(sr, ModItems.CERAUNUS.get());
//            Advancement tid = registerItemAdvancement(cer, ModItems.TIDAL_CLAWS.get());
//
//            //史诗战斗原版
//            Advancement ef_legacy = registerAdvancement(weaponRoot, "ef_legacy", FrameType.GOAL, EpicFightItems.UCHIGATANA.get(), true, true, false, new ImpossibleTrigger.TriggerInstance());
//
//            Advancement id = registerItemAdvancement(ef_legacy, EpicFightItems.IRON_DAGGER.get());
//            Advancement is = registerItemAdvancement(id, Items.IRON_SWORD);
//            Advancement il = registerItemAdvancement(is, EpicFightItems.IRON_LONGSWORD.get());
//            Advancement it = registerItemAdvancement(il, EpicFightItems.IRON_TACHI.get());
//            Advancement isp = registerItemAdvancement(it, EpicFightItems.IRON_SPEAR.get());
//            Advancement ig = registerItemAdvancement(isp, EpicFightItems.IRON_GREATSWORD.get());
//
//            Advancement ingredient = Advancement.Builder.advancement()
//                    .display(Items.AMETHYST_SHARD,
//                            Component.translatable(PRE + TCRCoreMod.MOD_ID + "_ingredient"),
//                            Component.translatable(PRE + TCRCoreMod.MOD_ID + "_ingredient" + ".desc"),
//                            ResourceLocation.fromNamespaceAndPath(Cataclysm.MODID, "textures/block/purpur_tile_pillar.png"),
//                            FrameType.GOAL, false, false, false)
//                    .addCriterion(TCRCoreMod.MOD_ID, new ImpossibleTrigger.TriggerInstance())
//                    .save(consumer, ResourceLocation.fromNamespaceAndPath(TCRCoreMod.MOD_ID, TCRCoreMod.MOD_ID + "_ingredient"), existingFileHelper);
//
//            Advancement skp = registerItemAdvancement(ingredient, EpicSkillsItems.ABILIITY_STONE.get(), true);
//            Advancement sga = registerItemAdvancement(ingredient, AquamiraeItems.SHIP_GRAVEYARD_ECHO.get(), true);
//            Advancement ash = registerItemAdvancement(ingredient, Items.AMETHYST_SHARD, true);
//            Advancement mc = registerItemAdvancement(ash, FAItems.MOON_CRYSTAL.get(), true);
//            Advancement abs = registerItemAdvancement(ash, AquamiraeItems.ABYSSAL_AMETHYST.get(), true);
//            Advancement ir = registerItemAdvancement(ingredient, Items.IRON_INGOT, true);
//            Advancement gi = registerItemAdvancement(ingredient, Items.GOLD_INGOT, true);
//            Advancement dim = registerItemAdvancement(ingredient, Items.DIAMOND, true);
//            Advancement ni = registerItemAdvancement(ingredient, Items.NETHERITE_INGOT, true);

        }

        public Advancement registerItemAdvancement(Advancement parent, ItemLike display, boolean specialDesc) {
            if(!specialDesc) {
                return registerItemAdvancement(parent, display);
            }
            String disc = "item." + display.asItem();
            ItemStack itemStack = display.asItem().getDefaultInstance();
            return Advancement.Builder.advancement()
                    .parent(parent)
                    .display(display,
                            display.asItem().getName(itemStack),
                            Component.translatable(itemStack.getDescriptionId() + ".adv_desc"),
                            null,
                            FrameType.GOAL, true, true, false)
                    .addCriterion(disc, InventoryChangeTrigger.TriggerInstance.hasItems(itemStack.getItem()))
                    .save(consumer, ResourceLocation.fromNamespaceAndPath(TCRCoreMod.MOD_ID, disc), helper);
        }

        public Advancement registerItemAdvancement(Advancement parent, ItemLike display) {
            String disc = "item." + display.asItem();
            ItemStack itemStack = display.asItem().getDefaultInstance();
            MutableComponent desc = Component.empty();
            List<Component> descList = new ArrayList<>();
            itemStack.getItem().appendHoverText(itemStack, null, descList, TooltipFlag.NORMAL);
            for(Component component : descList){
                desc.append("\n").append(component);
            }
            return Advancement.Builder.advancement()
                    .parent(parent)
                    .display(display,
                            display.asItem().getName(itemStack),
                            desc,
                            null,
                            FrameType.GOAL, true, true, false)
                    .addCriterion(disc, InventoryChangeTrigger.TriggerInstance.hasItems(itemStack.getItem()))
                    .save(consumer, ResourceLocation.fromNamespaceAndPath(TCRCoreMod.MOD_ID, disc), helper);
        }

        public Advancement registerAdvancement(Advancement parent, String name, FrameType type, ItemLike display, boolean showToast, boolean announceToChat, boolean hidden) {
            return Advancement.Builder.advancement()
                    .parent(parent)
                    .display(display,
                            Component.translatable(PRE + name),
                            Component.translatable(PRE + name + ".desc"),
                            null,
                            type, showToast, announceToChat, hidden)
                    .addCriterion(name, new ImpossibleTrigger.TriggerInstance())
                    .save(consumer, ResourceLocation.fromNamespaceAndPath(TCRCoreMod.MOD_ID, name), helper);
        }

        public Advancement registerAdvancement(Advancement parent, String name, FrameType type, ItemLike display, boolean showToast, boolean announceToChat, boolean hidden, CriterionTriggerInstance triggerInstance) {
            return Advancement.Builder.advancement()
                    .parent(parent)
                    .display(display,
                            Component.translatable(PRE + name),
                            Component.translatable(PRE + name + ".desc"),
                            null,
                            type, showToast, announceToChat, hidden)
                    .addCriterion(name, triggerInstance)
                    .save(consumer, ResourceLocation.fromNamespaceAndPath(TCRCoreMod.MOD_ID, name), helper);
        }

        public Advancement registerAdvancement(Advancement parent, String name, FrameType type, ItemLike display, CriterionTriggerInstance instance) {
            return Advancement.Builder.advancement()
                    .parent(parent)
                    .display(display,
                            Component.translatable(PRE + name),
                            Component.translatable(PRE + name + ".desc"),
                            null,
                            type, true, true, true)
                    .addCriterion(name, instance)
                    .save(consumer, ResourceLocation.fromNamespaceAndPath(TCRCoreMod.MOD_ID, name), helper);
        }

        public Advancement registerAdvancement(Advancement parent, String name, FrameType type, ItemLike display) {
            return registerAdvancement(parent, name, type, display, true, true, true);
        }

    }

    public static void finishAdvancement(Advancement advancement, ServerPlayer serverPlayer) {
        AdvancementProgress progress = serverPlayer.getAdvancements().getOrStartProgress(advancement);
        if (!progress.isDone()) {
            for (String criteria : progress.getRemainingCriteria()) {
                serverPlayer.getAdvancements().award(advancement, criteria);
            }
        }
    }

    public static void finishAdvancement(String name, ServerPlayer serverPlayer) {
        Advancement advancement = serverPlayer.server.getAdvancements().getAdvancement(ResourceLocation.fromNamespaceAndPath(TCRCoreMod.MOD_ID, name));
        if (advancement == null) {
            TCRCoreMod.LOGGER.error("advancement:\"{}\" is null!", name);
            return;
        }
        finishAdvancement(advancement, serverPlayer);
    }

    public static boolean isDone(String name, ServerPlayer serverPlayer) {
        Advancement advancement = serverPlayer.server.getAdvancements().getAdvancement(ResourceLocation.fromNamespaceAndPath(TCRCoreMod.MOD_ID, name));
        if (advancement == null) {
            TCRCoreMod.LOGGER.info("advancement:\"{}\" is null!", name);
            return false;
        }
        return isDone(advancement, serverPlayer);
    }

    public static boolean isDone(Advancement advancement, ServerPlayer serverPlayer) {
        AdvancementProgress advancementProgress = serverPlayer.getAdvancements().getOrStartProgress(advancement);
        return advancementProgress.isDone();
    }

}
