package com.p1nero.tcrcore.datagen.lang;

import com.github.L_Ender.cataclysm.init.ModItems;
import com.hm.efn.registries.EFNItem;
import com.p1nero.tcrcore.TCRCoreMod;
import com.p1nero.tcrcore.block.TCRBlocks;
import com.p1nero.tcrcore.capability.TCRTaskManager;
import com.p1nero.tcrcore.client.KeyMappings;
import com.p1nero.tcrcore.client.gui.BanPortalScreenHandler;
import com.p1nero.tcrcore.entity.TCREntities;
import com.p1nero.tcrcore.item.TCRItems;
import com.yesman.epicskills.registry.entry.EpicSkillsItems;
import net.alp.monsterexpansion.entity.ModEntities;
import net.minecraft.data.PackOutput;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Items;
import net.shelmarow.nightfall_invade.entity.NFIEntities;
import net.sonmok14.fromtheshadows.server.utils.registry.ItemRegistry;
import net.unusual.blockfactorysbosses.init.BlockFactorysBossesModItems;

public class TCRENLangGenerator extends TCRLangProvider {
    public TCRENLangGenerator(PackOutput output) {
        super(output, "en_us");
    }

    @Override
    protected void addTranslations() {
        this.addTask(TCRTaskManager.KILL_PILLAGER, "Kill illager.");
        this.addTask(TCRTaskManager.GIVE_ORACLE_TO_KEEPER, "Give §6[Oracle]§r to The Keeper");
        this.addTask(TCRTaskManager.FIND_GODNESS_STATUE, "Offer §6[Eye]§r to Godness Statue");

        this.addTCRItemInfo(ItemRegistry.BOTTLE_OF_BLOOD.get(), "Brewed using §c[Crystallized Blood Marrow]§r, a drop from §d[Nehemoth]§r");
        this.addTCRItemInfo(EFNItem.DEEPDARK_HEART.get(), "Obtained by defeating the §2[Warden]§r or §2[Captain Cornelia]§r");
        this.addTCRItemInfo(BlockFactorysBossesModItems.DRAGON_SKULL.get(), "Obtained by defeating the §c[Underworld Knight]§r or §4[Infernal Dragon]§r");
        this.addTCRItemInfo(ModItems.CORAL_CHUNK.get(), "Obtained by defeating the §a[Coral Colossus]§r in the §dcloudland of The Leviathan§r");
        this.addTCRItemInfo(BlockFactorysBossesModItems.DRAGON_BONE.get(), "Obtained by defeating the §c[Underworld Knight]§r or §4[Infernal Dragon]§r");
        this.addTCRItemInfo(com.github.dodo.dodosmobs.init.ModItems.CHIERA_CLAW.get(), "Obtained by defeating the §e[Bone Chimera]§r");
        this.addTCRItemInfo(ModItems.CHITIN_CLAW.get(), "Obtained by defeating the §3[Giant Claw Guard]§r in the §3cloudland of Scylla§r");
        this.addTCRItemInfo(BlockFactorysBossesModItems.KNIGHT_SWORD.get(), "Obtained by defeating the §c[Underworld Knight]§r");
        this.addTCRItemInfo(Items.DRAGON_EGG, "Obtained by defeating the §d[Ender Dragon]§r in the §dEnd§r");
        this.addTCRItemInfo(EpicSkillsItems.ABILIITY_STONE.get(), "Right-click to use and gain skill points");

        this.add("itemGroup.tcr.items", "The Casket of Reveries — Core Items");
        this.add("key.categories." + TCRCoreMod.MOD_ID, "The Casket of Reveries — Core");
        this.addKeyMapping(KeyMappings.RIPTIDE, "Riptide");
        this.addKeyMapping(KeyMappings.SHOW_TASK, "Show/Hide Task");

        this.add("skill_tree.sword_soaring.unlock_tip", "Unlocked by trading with villagers in §6[Sky Island]§r using emeralds");
        this.add("unlock_tip.tcrcore.battleborn.water_avoid", "Learned by trading with villagers in §6[Coves]§r using emeralds");
        this.add("unlock_tip.tcrcore.battleborn.fire_avoid", "Learned by defeating the §6[Bone Chimera]§r");
        this.add("unlock_tip.tcrcore.get_vatansever", "Unlocks after acquiring the §d[Vatansever]§f");
        this.addSkill("water_avoid", "Water Avoidance Charm", "Allows free breathing underwater!");
        this.addSkill("fire_avoid", "Fire Avoidance Charm", "Immunity to fire damage!");
        this.addSkill("perfect_dodge", "Dodge Effect", "Play a cool animation when perfect dodge!");

        this.add(TCRItems.DUAL_BOKKEN.get(), "Bokeen");
        this.addItemUsageInfo(TCRItems.DUAL_BOKKEN.get(), "I may have skill issue but I m not lacking on dedication so do you lacking in dedication?");
        this.add(TCRItems.VOID_CORE.get(), "Void Essence");
        this.addItemUsageInfo(TCRItems.VOID_CORE.get(), "Dropped by [Ender Guardian] when defeated");
        this.add(TCRItems.ABYSS_CORE.get(), "Abyss Essence");
        this.addItemUsageInfo(TCRItems.ABYSS_CORE.get(), "Dropped by [The Leviathan] when defeated");
        this.add(TCRItems.ARTIFACT_TICKET.get(), "Artifact Essence");
        this.addItemUsageInfo(TCRItems.ARTIFACT_TICKET.get(), "Obtained from certain quests in the quest book. Can be used to refine artifact with the §3[Ferry girl]§r at the §d[Saint Harbor]§r");
        this.add(TCRItems.RARE_ARTIFACT_TICKET.get(), "Golden Artifact Essence");
        this.addItemUsageInfo(TCRItems.RARE_ARTIFACT_TICKET.get(), "Obtained from certain quests in the quest book. Can be used to refine rare artifact with the §3[Ferry girl]§r at the §d[Saint Harbor]§r");
        this.add(TCRItems.ANCIENT_ORACLE_FRAGMENT.get(), "Oracle Fragment");
        this.addItemUsageInfo(TCRItems.ANCIENT_ORACLE_FRAGMENT.get(), "It bears an ancient oracle hinting at the locations of scattered embers. Show it to the The Sanctuary Keeper in the The Sanctuary; it might aid your adventure!");
        this.addItemUsageInfo(TCRItems.ANCIENT_ORACLE_FRAGMENT.get(), "§cIn multiplayer mode, do not occupy other players'! Everyone must submit their own!", 2);

        this.addInfo("nether_unlock", "Nether Unlocked!");
        this.addInfo("end_unlock", "End Unlocked!");
        this.addInfo("nothing_happen_after_bless", "§dNothing happen...");
        this.addInfo("dim_max_4_players", "§6Cloudland can only contain 4 players!");
        this.addInfo("can_not_enter_before_finish", "§6You are not destined to enter the cloudland.");
        this.addInfo("can_not_do_this_too_early", "§6You are not destined to do this.");
        this.addInfo("captain_start_heal", "§cCornelia start healing! Increase your damage!");
        this.addInfo("illegal_item_tip", "§cIllegal Item!");
        this.addInfo("shift_to_pic", "Attack when pressing Shift to take");
        this.addInfo("no_place_to_ship", "No Space for ship!");
        this.addInfo("boss_killed_ready_return", "§6Boss has been defeated! Block interaction unlocked!");
        this.addInfo("click_to_return", "§a[Click to return]");
        this.addInfo("cs_warning", "§c§l WARNING！Compute Shader is inactive now! You could enable it in Epic Fight config to get a better experience!");
        this.addInfo("wraithon_start_tip", "§d[Wraithon]: §6Outsider, your journey ends here!");
        this.addInfo("wraithon_end_tip", "§d[Wraithon]: §6This... is impossible...");
        this.addInfo("dim_block_no_interact", "§cBoss not defeated! Cannot interact with the cloudland block yet!");
        this.addInfo("altar_dim_info", "Cloudland Info:");
        this.addInfo("related_loot", "Monster: [%s] | Related Loot: [%s]");
        this.add(TCRBlocks.CURSED_ALTAR_BLOCK.get(), "Cursed Altar");
        this.add(TCRBlocks.ABYSS_ALTAR_BLOCK.get(), "Abyss Altar");
        this.add(TCRBlocks.STORM_ALTAR_BLOCK.get(), "Storm Altar");
        this.add(TCRBlocks.FLAME_ALTAR_BLOCK.get(), "Flame Altar");
        this.add(TCRBlocks.DESERT_ALTAR_BLOCK.get(), "Desert Altar");
        this.add(TCRBlocks.MONST_ALTAR_BLOCK.get(), "Monst Altar");
        this.add(TCRBlocks.VOID_ALTAR_BLOCK.get(), "Void Altar");
        this.add(TCRBlocks.MECH_ALTAR_BLOCK.get(), "Mech Altar");

        this.addInfo("attack_to_restart", "§cAttack to restart");
        this.addInfo("after_heal_stop_attack", "§6Stop attack to clear anger.");
        this.addInfo("cloud_follow_me", "§6[Magic Cloud]: §fHi, follow me!");
        this.addInfo("dim_demending", "§6Rebuilding... Wait[%d§6]s");
        this.addInfo("to_be_continue", "[P1nero]: §6Thank you for playing! Congratulations on experiencing all the content of the beta version. To be continued!");
        this.addInfo("second_after_boss_die_left", "Returning to the Overworld in %d seconds");
        this.addInfo("press_to_open_battle_mode", "§cPlease enable Battle Mode!§r");
        this.addInfo("unlock_new_dim_girl", "§6New options unlocked at the Ferry girl!§r");
        this.addInfo("unlock_new_dim", "§c[Nether]§d[End]§6 unlocked!§r");
        this.addInfo("iron_golem_name", "Sky Island Guardian");

        this.addInfo("get_mimic_invite", "[%s]: Otherworldly one, I knew I was right about you! Take this §6[%s§6]§f!");
        this.addInfo("kill_arterius", "[%s]: Otherworlder, you are indeed formidable! It seems the prophecy is true! Then, I shall bestow these pieces of [%s] upon you!");
        this.addInfo("kill_boss1", "§d[Voice from unknown origin]:§r Defend... Sky Island... Eliminate... the Black Tide...");
        this.addInfo("kill_boss2", "§c[Voice from unknown origin]:§r Defend... the Dragonborn... Eliminate... the Black Tide...");
        this.addInfo("kill_boss3", "§3[Voice from unknown origin]:§r ...Run...");
        this.addInfo("kill_boss4", "§a[Voice from unknown origin]:§r ...Give it... back... I want more...");
        this.addInfo("kill_boss5", "§e[Voice from unknown origin]:§r Hahaha, I am free at last!");

        this.addInfo("finish_all_eye", "§dAll altars are lit!§r");
        this.addInfo("time_to_altar", "The scattered embers have been found. It's time to return and light the altars...");
        this.addInfo("time_to_ask_godness_statue", "§d*This item can be used at the statue of the Goddess to request an oracle.");
        this.addInfo("time_to_end", "All altars are lit. It's time to find the The Sanctuary Keeper to perform the ritual...");

        this.addInfo("can_not_enter_dim", "It seems you have not yet gained the divine approval to enter... §6Continue collecting embers§r to receive more oracles!");
        this.addInfo("reset_when_no_player", "If no players remain in the Cloudland, leaving for too long will reset the Cloudland!");
        this.addInfo("on_full_set", "Full Set Effect");
        this.addInfo("unlock_new_ftb_page", "A new quest page has been unlocked. Please open the §6[Quest Book]§r to check");
        this.addInfo("unlock_new_skill_page", "A new skill book interface has been unlocked. Press §6[K]§r to check");
        this.addInfo("unlock_new_skill", "Unlocked [%s]! Press §6[K]§r to check");
        this.addInfo("hit_barrier", "This area is not available yet. Come back later!");

        this.addInfo("death_info", "§6When enemies are too powerful, try combining different skills!");
        this.addInfo("enter_dimension_tip", "Right-click the altar core to enter the Cloudland");
        this.addInfo("use_true_eye_tip", "Please use [%s] to right-click the altar core");

        this.addInfo("add_item_tip", "New item obtained: %s × %d!");
        this.addInfo("skill_point_lack", "This skill requires %d skill points to cast");
        this.addInfo("press_to_open_portal_screen", "Hold §6[Right-click]§r on the Waystone to return to previously activated stones!");
        this.addInfo("press_to_show_progress", "Press §6[L]§f to view guidance!");
        this.addInfo("press_to_skill_tree", "Sufficient EXP available. Press §6[K]§f to allocate skill points!");
        this.addInfo("lock_tutorial", "§6[[%s§6]§r to lock on");
        this.addInfo("lock_tutorial_sub", "§cMove the mouse to switch targets!");
        this.addInfo("riptide_tutorial", "§6[[%s§6]§f to §bRiptide");
        this.addInfo("dodge_tutorial", "§6[[%s§6]§f to dodge");
        this.addInfo("weapon_innate_tutorial", "§6[[%s§6]§f to weapon skill");
        this.addInfo("weapon_innate_charge_tutorial", "§6[Perfect Dodge]§c or §6[Perfect Parry]§c can charge certain weapons!");
        this.addInfo("perfect_dodge_tutorial", "§cDodge in time to Perfect Dodge!");
        this.addInfo("hurt_damage", "Dealt [ %.1f ] damage!");
        this.addInfo("parry_tutorial", "§6[[%s§6]§f to guard");
        this.addInfo("perfect_parry_tutorial", "§cBlock in time to Perfect Parry!");
        this.addInfo("you_pass", "§6You passed!!");

        this.addInfo("press_to_open_map", "§6[M]§f to view the map");

        this.addInfo("storm_pos", "Location of the Storm Echo: Sky Island");
        this.addInfo("cursed_pos", "Location of the Cursed Echo: Ice Maze");
        this.addInfo("desert_pos", "Location of the Desert Echo: Chimera's Prison");
        this.addInfo("flame_pos", "Location of the Flame Echo: Underworld Arena");
        this.addInfo("abyss_pos", "Location of the Abyss Echo: Coves");

        this.addAdvancement("dragon_tame", "Dragon Tamer", "Tame Skrythe");
        this.addAdvancement(TCRCoreMod.MOD_ID, "The Casket of Reveries", "Where the dream begins. Head to the The Sanctuary to find the The Sanctuary Keeper.");
        this.addAdvancement(TCRCoreMod.MOD_ID + "_weapon", "King's Treasury", "All obtainable and adapted weapons or materials. Check [JEI] for acquisition methods and details.");
        this.addAdvancement("find_ymsw", "Coves", "Reach the Coves");
        this.addAdvancement("aquamirae_weapon", "Sea Spirit Tale - Weapons", "");
        this.addAdvancement("cataclysm_weapon", "Cataclysm - Weapons", "");
        this.addAdvancement("legend_weapon", "Legendary Weapons", "");
        this.addAdvancement("ef_legacy", "Epic Fight - Weapons", "All can be obtained through crafting, featuring different weapon skills and animation templates. Check JEI for recipes and skill info.");
        this.addAdvancement("kill_pillager", "Proof of Allegiance", "The task is complete. Time to return to the The Sanctuary Keeper.");
        this.addAdvancement("mark_map", "Marking Locations", "The The Sanctuary Keeper has marked the locations of the scattered Divine Eyes on your map. Now, go reclaim them!");
        this.addAdvancement("storm_eye", "Eye of the Storm", "§a§oWhen war tore the clouds, she used the storm as a stairway, building a suspended paradise for her people.");
        this.addAdvancement("abyss_eye", "Eye of the Abyss", "§a§oWhen the abyss devoured the land, He sewed Himself into the seabed, His flesh becoming a city of bubbles.");
        this.addAdvancement("flame_eye", "Eye of the Flame", "§a§oWhere magma surged, He gouged out His heart, forging an eternal beacon.");
        this.addAdvancement("desert_eye", "Eye of the Desert", "§a§oThe guardians are not monsters; they are living tombstones willingly transformed by the people.");
        this.addAdvancement("cursed_eye", "Cursed Eye", "§a§oWhen the betrayer pierced her spine, frozen tears instantly sealed three thousand ghost ships.");

        this.addAdvancement("flame_kill", "Soul of Ignis", "Defeat Ignis and obtain Cremation Burial.");
        this.addAdvancement("storm_kill", "Soul of Scylla", "Defeat Scylla and obtain Courtyard Wave Anchor Halberd.");
        this.addAdvancement("abyss_kill", "Soul of The Leviathan", "Defeat The Leviathan and obtain Tide Claw.");
        this.addAdvancement("desert_kill", "Soul of Ancient Remnant", "Defeat Ancient Remnant and obtain Sandstorm's Wrath.");
        this.addAdvancement("cursed_kill", "Soul of Maledictus", "Defeat Maledictus and obtain Soul-Severing War Halberd.");

        this.addAdvancement("stage1", "Stage 1", "");
        this.addAdvancement("stage2", "Stage 2", "");
        this.addAdvancement("stage3", "Stage 3", "");
        this.addAdvancement("stage4", "Stage 4", "");
        this.addAdvancement("stage5", "Stage 5", "");

        this.add(TCREntities.GUIDER.get(), "The Sanctuary Keeper");
        this.add(TCREntities.GIRL.get(), "Ferry girl");
        this.add(TCREntities.TUTORIAL_GOLEM.get(), "Training Golem");

        BanPortalScreenHandler.onGenerateEN(this);

        this.addDialogAnswer(ModEntities.SKRYTHE.get(), 0, "Chosen One, I have long awaited your arrival.");
        this.addDialogAnswer(ModEntities.SKRYTHE.get(), 1, "I am Skrythe, the divine steed. During the Black Tide War, I suffered grave injuries and have slumbered until now. Upon awakening, I sensed signs of revival within the Sanctuary, thus I returned. The Sanctuary Keeper, upon seeing me, informed me of the matter regarding the Chosen One and commanded me to await you here.");
        this.addDialogOption(ModEntities.SKRYTHE.get(), 0, "Who are you?");
        this.addDialogOption(ModEntities.SKRYTHE.get(), 1, "Tame");

        this.addDialogAnswer(EntityType.IRON_GOLEM, 0, "Man, are you ready？");
        this.addDialogOption(EntityType.IRON_GOLEM, 0, "Yes");
        this.addDialogOption(EntityType.IRON_GOLEM, 1, "Wait");
        this.addDialogAnswer(EntityType.VILLAGER, -2, "Mambo?");
        this.addDialogAnswer(EntityType.VILLAGER, -1, "!!!");
        this.addDialogAnswer(EntityType.VILLAGER, 0, "Mambo, mambo, oh my gilly, mambo~");
        this.addDialogAnswer(EntityType.VILLAGER, 1, "Zabu zabu~");
        this.addDialogAnswer(EntityType.VILLAGER, 2, "Wa i sha~ Mambo~");
        this.addDialogAnswer(EntityType.VILLAGER, 3, "Nanbei ludou~ Axi ga axi~");
        this.addDialogAnswer(EntityType.VILLAGER, 4, "Hakimi nanbei ludou~ Axi ga axi~");
        this.addDialogAnswer(EntityType.VILLAGER, 5, "Ding dong ji~ Ding dong ji~");
        this.addDialogAnswer(EntityType.VILLAGER, 6, "You da you da~");
        this.addDialogAnswer(EntityType.VILLAGER, 7, "Axi ga hayaku naru~ wow~");
        this.addDialogOption(EntityType.VILLAGER, -3, "[Try Emeralds?]");
        this.addDialogOption(EntityType.VILLAGER, -2, "[This villager shows no interest...]");
        this.addDialogOption(EntityType.VILLAGER, -1, "[Accept]");
        this.addDialogOption(EntityType.VILLAGER, 0, "[???]");
        this.addDialogOption(EntityType.VILLAGER, 1, "[It seems the local residents are heavily corrupted!]");
        this.addDialogOption(EntityType.VILLAGER, 2, "[What are you mumbling about?]");
        this.addDialogOption(EntityType.VILLAGER, 3, "[Why can't I understand the villagers' language...]");
        this.addDialogOption(TCREntities.GUIDER.get(), 0, "Back");
        this.addDialogOption(TCREntities.GUIDER.get(), 1, "Who are you? Why did you save me?");
        this.addDialogOption(TCREntities.GUIDER.get(), 2, "What?");
        this.addDialogOption(TCREntities.GUIDER.get(), 3, "What happened to this world?");
        this.addDialogOption(TCREntities.GUIDER.get(), 4, "How can I help you?");
        this.addDialogOption(TCREntities.GUIDER.get(), 5, "Mark Locations");
        this.addDialogOption(TCREntities.GUIDER.get(), 6, "I was just joking earlier!");
        this.addDialogOption(TCREntities.GUIDER.get(), 7, "§aAlready defeated pillagers§f");
        this.addDialogOption(TCREntities.GUIDER.get(), 8, "Whoa, a beautiful girl?");
        this.addDialogOption(TCREntities.GUIDER.get(), 9, "Reveal the Oracle");
        this.addDialogAnswer(TCREntities.GUIDER.get(), 0, "So... you drifted here from beyond this world? As you prepared to land, an unfamiliar deity pulled you into a barrier, and then you lost consciousness?");
        this.addDialogAnswer(TCREntities.GUIDER.get(), 1, "I am the The Sanctuary Keeper of this world. That day, the heavens were abnormal, thunder roared, and a meteor fell into the sea. I rescued you from the depths. You must be the celestial warriors foretold in the ancient prophecy.");
        this.addDialogAnswer(TCREntities.GUIDER.get(), 2, "Once, there was a glorious kingdom here, protected by heroic spirits. Until one day, the §d「Black Tide」§f descended upon the world, corrupting all things. Even some villagers were darkened into illagers. The gods, unable to resist the §d「Black Tide」§f, became spirits. As the ancient prophecy foretold, I sealed their remaining power here. §6However, fragments of their embers are still scattered across the world.§f I am cursed and cannot leave this place, so I can only wait silently for a savior to arrive...");
        this.addDialogAnswer(TCREntities.GUIDER.get(), 3, "The ancient prophecy states that once you ignite all the embers, you can rebuild the temple, obtain the power of the spirits, and perform the ritual to cleanse the §d「Black Tide」§f! But before that, first §6defeat an illager§f and then§f return to me.");
        this.addDialogAnswer(TCREntities.GUIDER.get(), 4, "It seems you truly are the prophesied savior! Have you collected any §d『Oracle Fragments』§f during your journey? Give the §d『Oracle Fragments』§f to me, and I will reveal the locations of the embers recorded in the oracle! Once you ignite all the embers, I will initiate the §d「Black Tide」§f cleansing ritual!");

        this.addDialogAnswer(TCREntities.GUIDER.get(), 5, "Why did you attack me?");
        this.addDialogAnswer(TCREntities.GUIDER.get(), 6, "Since you have proven your strength, I will drop my disguise and show you my true form.");
        this.addDialogAnswer(TCREntities.GUIDER.get(), 7, "§d『Oracle Fragment!』§f Give it to me, and I will reveal the ember locations it records, along with the blessings bestowed by the oracle!");

        this.addDialogAnswer(TCREntities.GUIDER.get(), 8, "O prophesied savior, what troubles you?");
        this.addDialogOption(TCREntities.GUIDER.get(), 10, "The most powerful strength?");
        this.addDialogOption(TCREntities.GUIDER.get(), 11, "What should we do next?");

        this.addDialogOption(TCREntities.GUIDER.get(), 16, "Other damaged altars in the hall?");

        this.addDialogOption(TCREntities.GUIDER.get(), 12, "[I have lit all the altars!]");
        this.addDialogOption(TCREntities.GUIDER.get(), 13, "[I don't understand...]");
        this.addDialogOption(TCREntities.GUIDER.get(), 14, "[Who are you really?]");
        this.addDialogOption(TCREntities.GUIDER.get(), 15, "[Continue]"); // Pulled into the barrier
        this.addDialogOption(TCREntities.GUIDER.get(), 17, "[Busted, hehe]");
        this.addDialogOption(TCREntities.GUIDER.get(), 18, "How to get Oracle?");
        this.addDialogAnswer(TCREntities.GUIDER.get(), 9, "The §dEnder Dragon§f, located in the End's alien space, has deep connections to the §d「Black Tide」§f. After defeating it, the essence it births can be forged into the Yamato, a power from another world beyond measure. However, be warned: after the extradimensional dragon vanishes, the world affected by the §d「Black Tide」§f will cause monsters' health to double!");
        this.addDialogAnswer(TCREntities.GUIDER.get(), 10, "Simply give the §d『Oracle Fragments』§f to me, and I will reveal the ember locations recorded in the oracle! Then you can head to the places I mark on your map, defeat the monsters, reclaim the embers, §6and offer them at the altars in the hall§f. Once all embers are gathered, we can initiate the ritual to purify the §d「Black Tide」§f!");
        this.addDialogAnswer(TCREntities.GUIDER.get(), 11, "Outsider... You didn't really think you were some savior, did you... Hahahaha, you are merely a puppet I used to reclaim my power!");
        this.addDialogAnswer(TCREntities.GUIDER.get(), 12, "The Underworld Knight you defeated, the puppet of Sky Island, the Chimera in the prison, and the cursed crew under the Captain—they were the true divine guardians of this world, obstructing my grand plan! Their disappearance matters little, even if it costs a few illagers. Moreover, the deity embers you brought back will help me rebuild my physical form.");
        this.addDialogAnswer(TCREntities.GUIDER.get(), 13, "Who am I? A sacrifice like you has no right to know! There is no foolish ritual! After absorbing your power, the world shall be under my rule! Die!");

        this.addDialogAnswer(TCREntities.GUIDER.get(), 14, "This... I once battled the §d「Black Tide Legion」§f here. The spirits sealed by the damaged altars will never return... But the remaining spirits are enough to initiate the exorcism ritual.");

        this.addDialogAnswer(TCREntities.GUIDER.get(), 15, "§bThe Storm Ember§f... I sense it is scattered in §6[%s]§f. Defeat %s to reclaim it! I have marked the location of §6[%s§6]§f on your map. I will wait here. ");
        this.addDialogAnswer(TCREntities.GUIDER.get(), 19, "§6The Flame Ember§f... I sense it is scattered in §6[%s]§f. Defeat %s to reclaim it! I have marked the location of §6[%s§6]§f on your map. I will wait here. ");
        this.addDialogAnswer(TCREntities.GUIDER.get(), 16, "§3The Abyss Ember§f... I sense it is scattered in §6[%s]§f. Defeat %s to reclaim it! I have marked the location of §6[%s§6]§f on your map. I will wait here. ");
        this.addDialogAnswer(TCREntities.GUIDER.get(), 17, "§2The Cursed Ember§f... I sense it is scattered in §6[%s]§f. Defeat %s to reclaim it! I have marked the location of §6[%s§6]§f on your map. I will wait here. ");
        this.addDialogAnswer(TCREntities.GUIDER.get(), 18, "§eThe Desert Ember§f... I sense it is scattered in §6[%s]§f. Defeat %s to reclaim it! I have marked the location of §6[%s§6]§f on your map. I will wait here. ");
        this.addDialogAnswer(TCREntities.GUIDER.get(), 20, "How did you get extra Oracle Fragments by following the normal流程? Did you steal them from someone?");
        this.addDialogAnswer(TCREntities.GUIDER.get(), 21, "Since you have lit all the altars, once you §6enter, defeat the spirits, and infuse yourself with the power of the spirits,§f we shall commence the ritual!");
        this.addDialogAnswer(TCREntities.GUIDER.get(), 22, "Go to the place I guide you, retrieve the §d[Eye of God]§f, and offer it to the §6[Sanctuary of the Central Courtyard where the Goddess Statue resides]§f. The Goddess Statue will reveal the divine prophecy to you!");

        this.addDialogAnswer(TCREntities.GIRL.get(), 0, "Long time no see, esteemed one!");
        this.addDialogAnswer(TCREntities.GIRL.get(), 1, "Have you forgotten me? I am the Ferry girl of the Saint Temple, here to guide the lost. If you have any rare treasures, show them to me. I can extract their memoria and transform them into artifacts! When you can set foot in §cthe Nether§f or §dthe End§f, I can also give you a ride.");
        this.addDialogAnswer(TCREntities.GIRL.get(), 2, "If you have sufficient experience, open the skill panel to learn skills. §6Click the experience orb in the top right corner of the skill tree interface to convert experience into skill points.§f Skill allocation is crucial; consider learning skills that enhance survivability, like Health Boost!");
        this.addDialogAnswer(TCREntities.GIRL.get(), 3, "I am the Ferry girl of the Saint Temple, here to guide the lost. If you have any rare treasures, show them to me. I can extract their memoria and transform them into artifacts! When you can set foot in §cthe Nether§f or §dthe End§f, I can also give you a ride. As a first meeting gift, take this artifact. Press §d[%s]§f to deploy the sail and travel thousands of miles!");
        this.addDialogAnswer(TCREntities.GIRL.get(), 4, "Esteemed one, are you sure you wish to go? I cannot bring you back... Please ensure you have the Waystone with you.");
        this.addDialogAnswer(TCREntities.GIRL.get(), 5, "§d[%s]§f can be obtained through relic chests or by completing certain quests. If you possess §d[%s]§f, press §6[%s]§f to open the skill panel and learn skills. Skill allocation is very important—it is recommended to learn skills like Health Boost and others that enhance survivability!");

        this.addDialogOption(TCREntities.GIRL.get(), 0, "Back");
        this.addDialogOption(TCREntities.GIRL.get(), 1, "Who are you?");
        this.addDialogOption(TCREntities.GIRL.get(), 2, "Weapon Extraction");
        this.addDialogOption(TCREntities.GIRL.get(), 3, "Armor Refining");
        this.addDialogOption(TCREntities.GIRL.get(), 4, "Learn Skills");
        this.addDialogOption(TCREntities.GIRL.get(), 5, "Open Skill Tree");
        this.addDialogOption(TCREntities.GIRL.get(), 6, "Go to the Nether");
        this.addDialogOption(TCREntities.GIRL.get(), 7, "Go to the End");
        this.addDialogOption(TCREntities.GIRL.get(), 8, "Confirm");
        this.addDialogOption(TCREntities.GIRL.get(), 9, "Artifact Extraction");

        this.addDialogAnswer(NFIEntities.ARTERIUS.get(), 0, "Otherworldly one, why have you come here?");
        this.addDialogAnswer(NFIEntities.ARTERIUS.get(), 1, "Hahaha, the Sanctuary Keeper entrusted the Eye of Flame to me to prevent it from falling into the hands of the unworthy. Even if she were to come herself, she would have to get through me! Let me see if you have what it takes!");
        this.addDialogAnswer(NFIEntities.ARTERIUS.get(), 2, "Otherworldly one, long time no see!");
        this.addDialogAnswer(NFIEntities.ARTERIUS.get(), 3, "Are you ready?");
        this.addDialogAnswer(NFIEntities.ARTERIUS.get(), 4, "During the Black Tide, the §6[Piglin clan]§f§f of the Nether created [%s] to resist the darkness. Unfortunately, they were defeated and lost their sanity. However, they still recognize [%s§f§f]. Use [%s§f§f] to trade with them, and you may unveil the secrets of the ancient war machines.");
        this.addDialogAnswer(NFIEntities.ARTERIUS.get(), 5, "[%s] and [%s] guard the echoes of [%s] and [%s], respectively.");
        this.addDialogAnswer(NFIEntities.ARTERIUS.get(), 6, "After you retrieve them, come find me again, and I will show you the power of §4§l[Proteus]§f§f!");
        this.addDialogOption(NFIEntities.ARTERIUS.get(), 0, "Retrieve the Eye of Flame");
        this.addDialogOption(NFIEntities.ARTERIUS.get(), 1, "Take your life");
        this.addDialogOption(NFIEntities.ARTERIUS.get(), 2, "I'm ready");
        this.addDialogOption(NFIEntities.ARTERIUS.get(), 3, "Wait a moment");
        this.addDialogOption(NFIEntities.ARTERIUS.get(), 4, "Spar with me");
        this.addDialogOption(NFIEntities.ARTERIUS.get(), 5, "Ask information");
        this.addDialogOption(NFIEntities.ARTERIUS.get(), 6, "Continue");
        this.addDialogOption(NFIEntities.ARTERIUS.get(), 7, "Take my leave");
        this.addDialogOption(NFIEntities.ARTERIUS.get(), 8, "§aFinish");
    }
}
