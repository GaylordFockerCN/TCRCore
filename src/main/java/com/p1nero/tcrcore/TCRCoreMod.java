package com.p1nero.tcrcore;

import com.github.L_Ender.cataclysm.init.ModEntities;
import com.github.L_Ender.cataclysm.init.ModItems;
import com.google.common.collect.MoreCollectors;
import com.hm.efn.registries.EFNItem;
import com.mojang.logging.LogUtils;
import com.obscuria.aquamirae.registry.AquamiraeItems;
import com.p1nero.tcrcore.block.TCRBlocks;
import com.p1nero.tcrcore.block.entity.TCRBlockEntities;
import com.p1nero.tcrcore.capability.TCRTaskManager;
import com.p1nero.tcrcore.client.sound.TCRSounds;
import com.p1nero.tcrcore.effect.TCREffects;
import com.p1nero.tcrcore.entity.TCREntities;
import com.p1nero.tcrcore.events.ItemEvents;
import com.p1nero.tcrcore.events.LivingEntityEventListeners;
import com.p1nero.tcrcore.events.PlayerEventListeners;
import com.p1nero.tcrcore.gameassets.TCRSkillSlots;
import com.p1nero.tcrcore.item.TCRItemTabs;
import com.p1nero.tcrcore.item.TCRItems;
import com.p1nero.tcrcore.network.TCRPacketHandler;
import com.yesman.epicskills.registry.entry.EpicSkillsItems;
import net.genzyuro.uniqueaccessories.registry.UAItems;
import net.mcreator.morecritters.init.MoreCrittersModEntities;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.PathPackResources;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Items;
import net.minecraftforge.event.AddPackFindersEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.sonmok14.fromtheshadows.server.utils.registry.ItemRegistry;
import net.unusual.blockfactorysbosses.init.BlockFactorysBossesModEntities;
import net.unusual.blockfactorysbosses.init.BlockFactorysBossesModItems;
import org.merlin204.wraithon.WraithonConfig;
import org.slf4j.Logger;
import yesman.epicfight.skill.SkillSlot;

import java.util.List;
import java.util.Locale;

@Mod(TCRCoreMod.MOD_ID)
public class TCRCoreMod {

    public static final String MOD_ID = "tcrcore";
    public static final Logger LOGGER = LogUtils.getLogger();
    private static boolean isCheatMod = false;

    public TCRCoreMod(FMLJavaModLoadingContext context) {
        SkillSlot.ENUM_MANAGER.registerEnumCls(TCRCoreMod.MOD_ID, TCRSkillSlots.class);
        IEventBus bus = context.getModEventBus();
        bus.addListener(this::commonSetup);
        bus.addListener(this::addPackFindersEvent);
        TCRSounds.REGISTRY.register(bus);
        TCREntities.REGISTRY.register(bus);
        TCRBlocks.REGISTRY.register(bus);
        TCRBlockEntities.REGISTRY.register(bus);
        TCRItems.REGISTRY.register(bus);
        TCRItemTabs.REGISTRY.register(bus);
        TCREffects.REGISTRY.register(bus);
        context.registerConfig(ModConfig.Type.COMMON, TCRConfig.SPEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        TCRPacketHandler.register();
        TCRTaskManager.init();
//        List<String> cheatModList = List.of("tacz", "projecte", "enchantmentlevelbreak");
//        cheatModList.forEach(s -> {
//            if(ModList.get().isLoaded(s)){
//                isCheatMod = true;
//            }
//        });

        PlayerEventListeners.illegalItems.add(UAItems.STARVED_WOLF_SKULL.get());
        PlayerEventListeners.illegalItems.add(artifacts.registry.ModItems.VAMPIRIC_GLOVE.get());
        PlayerEventListeners.illegalItems.add(UAItems.BURNING_SOUL.get());
        PlayerEventListeners.illegalItems.add(AquamiraeItems.DIVIDER.get());
        PlayerEventListeners.illegalItems.add(net.p3pp3rf1y.sophisticatedbackpacks.init.ModItems.FEEDING_UPGRADE.get());
        PlayerEventListeners.illegalItems.add(net.p3pp3rf1y.sophisticatedbackpacks.init.ModItems.ADVANCED_FEEDING_UPGRADE.get());
        PlayerEventListeners.illegalItems.add(artifacts.registry.ModItems.SCARF_OF_INVISIBILITY.get());

        if(WraithonConfig.ARACHNOPHOBIA_MODE.get()) {
            LivingEntityEventListeners.illegalEntityTypes.add(MoreCrittersModEntities.SNOWFLAKE_SPIDER.get());
        }
        LivingEntityEventListeners.illegalEntityTypes.addAll(List.of(
                ModEntities.URCHINKIN.get(),
                ModEntities.KOBOLETON.get(),
                ModEntities.ELITE_DRAUGR.get(),
                ModEntities.ROYAL_DRAUGR.get(),
                ModEntities.DRAUGR.get(),
                ModEntities.LIONFISH.get(),
                ModEntities.DEEPLING_BRUTE.get(),
                ModEntities.DEEPLING.get(),
                BlockFactorysBossesModEntities.FLAMING_SKELETON_GUARD_FIREBALL.get()
        ));
        ItemEvents.items.addAll(List.of(
                ItemRegistry.BOTTLE_OF_BLOOD.get(),
                BlockFactorysBossesModItems.DRAGON_SKULL.get(),
                BlockFactorysBossesModItems.DRAGON_BONE.get(),
                com.github.dodo.dodosmobs.init.ModItems.CHIERA_CLAW.get(),
                ModItems.CHITIN_CLAW.get(),
                ModItems.CORAL_CHUNK.get(),
                Items.DRAGON_EGG,
                BlockFactorysBossesModItems.KNIGHT_SWORD.get(),
                EFNItem.DEEPDARK_HEART.get(),
                EpicSkillsItems.ABILIITY_STONE.get()
        ));

        ItemEvents.eyes.addAll(List.of(ModItems.MONSTROUS_EYE.get(), ModItems.VOID_EYE.get(), ModItems.MECH_EYE.get(), ModItems.ABYSS_EYE.get(), ModItems.STORM_EYE.get(), ModItems.CURSED_EYE.get(), ModItems.FLAME_EYE.get(), ModItems.DESERT_EYE.get()));
    }

    public static boolean hasCheatMod() {
        return isCheatMod;
    }

    private void addPackFindersEvent(AddPackFindersEvent event) {
        if (event.getPackType() == PackType.CLIENT_RESOURCES) {
            String name = "tcr_assets";
            var resourcePath = ModList.get().getModFileById(MOD_ID).getFile().findResource("packs/" + name);
            var pack = Pack.readMetaAndCreate(name, Component.literal("The Casket of Reveries Assets - Override"), true,
                    (path) -> new PathPackResources(path, resourcePath, false), PackType.CLIENT_RESOURCES, Pack.Position.TOP, PackSource.BUILT_IN);
            event.addRepositorySource((packConsumer) -> packConsumer.accept(pack));
        }
        if (event.getPackType() == PackType.SERVER_DATA) {
            String name = "tcr_data";
            var resourcePath = ModList.get().getModFileById(MOD_ID).getFile().findResource("packs/" + name);
            var pack = Pack.readMetaAndCreate(name, Component.literal("The Casket of Reveries Data - Override"), true,
                    (path) -> new PathPackResources(path, resourcePath, false), PackType.SERVER_DATA, Pack.Position.TOP, PackSource.WORLD);
            event.addRepositorySource((packConsumer) -> packConsumer.accept(pack));

        }
    }

    public static MutableComponent getInfo(String key) {
        return Component.translatable("info.tcr." + key);
    }

    public static String getInfoKey(String key) {
        return "info.tcr." + key;
    }

    public static MutableComponent getInfo(String key, Object... objects) {
        return Component.translatable("info.tcr." + key, objects);
    }

    public static ResourceLocation prefix(String name) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, name.toLowerCase(Locale.ROOT));
    }

}
