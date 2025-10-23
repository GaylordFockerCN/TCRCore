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
import com.p1nero.tcrcore.worldgen.TCRBiomes;
import com.yesman.epicskills.registry.entry.EpicSkillsItems;
import net.alp.monsterexpansion.entity.ModEntities;
import net.minecraft.data.PackOutput;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Items;
import net.shelmarow.nightfall_invade.entity.NFIEntities;
import net.sonmok14.fromtheshadows.server.utils.registry.ItemRegistry;
import net.unusual.blockfactorysbosses.init.BlockFactorysBossesModItems;

public class TCRZHLangGenerator extends TCRLangProvider {
    public TCRZHLangGenerator(PackOutput output) {
        super(output, "zh_cn");
    }

    @Override
    protected void addTranslations() {

        this.add("epicfight.skill_slot.passive4", "被动4");
        this.add("epicfight.skill_slot.passive5", "被动5");
        this.add("travelerstitles.tcrcore.sanctum", "梦之领域");
        this.addBiome(TCRBiomes.AIR, "虚无之地");

        this.addTask(TCRTaskManager.KILL_PILLAGER, "击杀掠夺者");
        this.addTask(TCRTaskManager.GIVE_ORACLE_TO_KEEPER, "将§6[神谕残卷]§r交给守望者");
        this.addTask(TCRTaskManager.BACK_TO_KEEPER, "回去找守望者");
        this.addTask(TCRTaskManager.FIND_GODNESS_STATUE, "将§6[神之眼]§r供奉于女神像");
        this.addTask(TCRTaskManager.FIND_ARTERIUS, "回去找阿尔特留斯");
        this.addTask(TCRTaskManager.LIGHT_ALL_ALTAR, "将§6[神之眼]§r置于祭坛上");
        this.addTask(TCRTaskManager.GO_TO_OVERWORLD, "前往主世界");

        this.addTCRItemInfo(ItemRegistry.BOTTLE_OF_BLOOD.get(), "使用§d[尼赫莫斯]§r掉落物§c[晶化血髓]§r酿造获取");
        this.addTCRItemInfo(EFNItem.DEEPDARK_HEART.get(), "击败§2[监守者]§r或§2[可妮莉亚船长]§r获取");
        this.addTCRItemInfo(BlockFactorysBossesModItems.DRAGON_SKULL.get(), "击败§c[冥界骑士]§r或§4[炼狱魔龙]§r获取");
        this.addTCRItemInfo(ModItems.CORAL_CHUNK.get(), "于§d利维坦幻境§r击败§a[珊瑚巨像]§r获取");
        this.addTCRItemInfo(BlockFactorysBossesModItems.DRAGON_BONE.get(), "击败§c[冥界骑士]§r或§4[炼狱魔龙]§r获取");
        this.addTCRItemInfo(com.github.dodo.dodosmobs.init.ModItems.CHIERA_CLAW.get(), "击败§e[骸骨奇美拉]§r获取");
        this.addTCRItemInfo(ModItems.CHITIN_CLAW.get(), "于§3斯库拉幻境§r击败§3巨钳守卫§r获取");
        this.addTCRItemInfo(BlockFactorysBossesModItems.KNIGHT_SWORD.get(), "击败§c[冥界骑士]§r获取");
        this.addTCRItemInfo(Items.DRAGON_EGG, "于§d末地§r击败§d[末影龙]§r获取");
        this.addTCRItemInfo(EpicSkillsItems.ABILIITY_STONE.get(), "右键使用以获取技能点");

        this.add("itemGroup.tcr.items", "远梦之棺 —— 核心 物品");
        this.add("key.categories." + TCRCoreMod.MOD_ID, "远梦之棺 —— 核心");
        this.addKeyMapping(KeyMappings.RIPTIDE, "激流");
        this.addKeyMapping(KeyMappings.SHOW_TASK, "隐藏/显示指引");

        this.add("skill_tree.sword_soaring.unlock_tip", "与§6[天空岛]§r村民用绿宝石交易解锁");
        this.add("unlock_tip.tcrcore.battleborn.water_avoid", "使用绿宝石向§6[隐秘水湾]§r的村民交易习得");
        this.add("unlock_tip.tcrcore.battleborn.fire_avoid", "击败§6[骸骨奇美拉]§r习得");
        this.add("unlock_tip.tcrcore.get_vatansever", "获得§d「卫疆刃」§f后解锁");
        this.addSkill("water_avoid", "避水咒", "可在水下自由呼吸！");
        this.addSkill("fire_avoid", "避火咒", "免疫火焰伤害！");
        this.addSkill("perfect_dodge", "闪避特效", "完美闪避时将有帅气的动作！");

        this.add(TCRItems.PROOF_OF_ADVENTURE.get(), "冒险之证");
        this.addItemUsageInfo(TCRItems.PROOF_OF_ADVENTURE.get(), "此证乃是用所有败于你剑下的强敌之名铸就。你的冒险已抵达终点，你的勇气已化为传奇。");
        this.add(TCRItems.DUAL_BOKKEN.get(), "双持木棍");
        this.addItemUsageInfo(TCRItems.DUAL_BOKKEN.get(), "我或许能力不足，但绝不缺乏奉献精神，难道你缺少奉献精神吗？");
        this.add(TCRItems.VOID_CORE.get(), "虚空精华");
        this.addItemUsageInfo(TCRItems.VOID_CORE.get(), "击败[末影守卫]掉落");
        this.add(TCRItems.ABYSS_CORE.get(), "深渊精华");
        this.addItemUsageInfo(TCRItems.ABYSS_CORE.get(), "击败[利维坦]掉落");
        this.add(TCRItems.ARTIFACT_TICKET.get(), "饰品精华");
        this.addItemUsageInfo(TCRItems.ARTIFACT_TICKET.get(), "通过任务书某些任务获取。可在§d[圣殿港口]§r的§3[摆渡人]§r处提炼饰品");
        this.add(TCRItems.RARE_ARTIFACT_TICKET.get(), "稀有饰品精华");
        this.addItemUsageInfo(TCRItems.RARE_ARTIFACT_TICKET.get(), "通过任务书某些任务获取。可在§d[圣殿港口]§r的§3[摆渡人]§r处提炼稀有饰品");
        this.add(TCRItems.ANCIENT_ORACLE_FRAGMENT.get(), "神谕残卷");
        this.addItemUsageInfo(TCRItems.ANCIENT_ORACLE_FRAGMENT.get(), "上面写着古老的神谕，暗示了火种散落的地方。回主城给守望者看看吧，说不定对冒险有帮助！");
        this.addItemUsageInfo(TCRItems.ANCIENT_ORACLE_FRAGMENT.get(), "§c多人模式请勿占据他人任务道具！每人都需各自提交！", 2);

        this.addInfo("weapon_no_interact", "武器禁止交互！请使用其他物品或按[%s]切换非战斗模式");
        this.addInfo("tudigong_gift", "[见面礼]");
        this.addInfo("tudigong_gift_get", "§6[土地公]§f: 老夫不中用矣，此宝具赠予你罢！");
        this.addInfo("need_to_unlock_waystone", "仍有传送石碑未激活！");
        this.addInfo("nether_unlock", "地狱之门已解锁！");
        this.addInfo("end_unlock", "末地之门已解锁！");
        this.addInfo("dim_max_4_players", "§6幻境最多容纳4人！");
        this.addInfo("can_not_enter_before_finish", "§6当前没有进入幻镜的命运。");
        this.addInfo("can_not_do_this_too_early", "§6当前没有做此事的命运。");
        this.addInfo("after_heal_stop_attack", "§6停止攻击以取消傀儡仇恨");
        this.addInfo("cloud_follow_me", "§6[仙气]：§f嗨伙计，跟着我！");
        this.addInfo("shift_to_pic", "潜行时攻击以收回");
        this.addInfo("no_place_to_ship", "空间不足，无法摆放船只！");
        this.addInfo("boss_killed_ready_return", "§6幻境英灵已击败！已解锁方块交互！");
        this.addInfo("click_to_return", "§a[点击返回主城]");
        this.addInfo("cs_warning", "§c§l警告！Compute Shader未启用！建议在史诗战斗设置中开启以获得更流畅的体验！");
        this.addInfo("wraithon_start_tip", "§d[Wraithon]: §6外来之人，你的旅途到此结束！");
        this.addInfo("wraithon_end_tip", "§d[Wraithon]: §6这...不可能...");
        this.addInfo("dim_block_no_interact", "§cBoss未击败！暂时无法与幻境方块交互！");
        this.addInfo("altar_dim_info", "幻境信息：");
        this.addInfo("related_loot", "魔物： [%s] | 相关战利品：[%s]");
        this.add(TCRBlocks.CURSED_ALTAR_BLOCK.get(), "诅咒祭坛");
        this.add(TCRBlocks.ABYSS_ALTAR_BLOCK.get(), "深渊祭坛");
        this.add(TCRBlocks.STORM_ALTAR_BLOCK.get(), "风暴祭坛");
        this.add(TCRBlocks.FLAME_ALTAR_BLOCK.get(), "烈焰祭坛");
        this.add(TCRBlocks.DESERT_ALTAR_BLOCK.get(), "沙漠祭坛");
        this.add(TCRBlocks.MONST_ALTAR_BLOCK.get(), "恶兽祭坛");
        this.add(TCRBlocks.VOID_ALTAR_BLOCK.get(), "虚空祭坛");
        this.add(TCRBlocks.MECH_ALTAR_BLOCK.get(), "机械祭坛");

        this.addInfo("nothing_happen_after_bless", "§d无事发生...看来该[神之眼]已经祈福过了");
        this.addInfo("attack_to_restart", "§c攻击Boss以再次发起挑战");
        this.addInfo("captain_start_heal", "§c可妮莉亚船长开始回血！增大伤害击败她！");
        this.addInfo("illegal_item_tip", "§c检测到非法物品！");
        this.addInfo("illegal_item_tip2", "§6当前没有使用此物品的命运。");

        this.addInfo("dim_demending", "§6幻境重铸中...请等待[%d§6]秒");
        this.addInfo("to_be_continue", "[P1nero]: §6感谢游玩！恭喜你体验完测试版的全部内容，未完待续！");

        this.addInfo("second_after_boss_die_left", "将在 %d 秒后返回主世界");

        this.addInfo("press_to_open_battle_mode", "§c请开启战斗模式!§r");
        this.addInfo("unlock_new_dim_girl", "§6摆渡人处已解锁新选项!§r");
        this.addInfo("unlock_new_dim", "§c[地狱]§d[末地]§6已解锁!§r");
        this.addInfo("iron_golem_name", "天空岛之守卫");

        this.addInfo("get_mimic_invite", "[%s]: 异界之人，我果然没看错你！这封§6[%s§6]§f，你收下罢！");
        this.addInfo("kill_arterius", "[%s]: 异界之人，果然有几分本事！看来预言是对的！这几块[%s§f]，赠予你罢！");
        this.addInfo("kill_boss1", "§d[不知何处的声音]：§r捍卫…天空岛…扫除…黑潮…");
        this.addInfo("kill_boss2", "§c[不知何处的声音]：§r捍卫…龙裔…扫除…黑潮…");
        this.addInfo("kill_boss3", "§3[不知何处的声音]：§r…快逃…");
        this.addInfo("kill_boss4", "§a[不知何处的声音]：§r…还…给我…我还要…");
        this.addInfo("kill_boss5", "§e[不知何处的声音]：§r哈哈哈，吾自由矣！");

        this.addInfo("finish_all_eye", "§d众祭坛已点亮！§r");
        this.addInfo("time_to_altar", "失散火种已寻，该回去点亮祭坛了...");
        this.addInfo("time_to_ask_godness_statue", "§d*可在女神像处祈求祝福");
        this.addInfo("time_to_end", "所有祭坛已点亮，该找守卫者举行仪式了...");

        this.addInfo("can_not_enter_dim", "看来当前还未能达到神之认可，无法进入...§6继续收集火种§r以获取更多神谕吧！");
        this.addInfo("reset_when_no_player", "当幻境内没有玩家存在时，离开幻境过久，幻境将会重置！");
        this.addInfo("on_full_set", "套装效果");
        this.addInfo("unlock_new_ftb_page", "解锁了新的任务界面，请打开§6[任务书]§r查看");
        this.addInfo("unlock_new_skill_page", "解锁了新的技能书界面，按§6[K]§r查看");
        this.addInfo("unlock_new_skill", "解锁了[%s]! 按§6[K]§r查看");
        this.addInfo("hit_barrier", "前面的区域，以后再来探索吧！");

        this.addInfo("death_info", "§6敌人过于强大时，可以尝试搭配不同技能组合！");
        this.addInfo("enter_dimension_tip", "右键祭坛核心以进入英灵幻境");
        this.addInfo("use_true_eye_tip", "请使用 [%s] 右键祭坛核心");

        this.addInfo("add_item_tip", "获得新物品：%s × %d！");
        this.addInfo("skill_point_lack", "释放该技能需 %d 技能点");
        this.addInfo("press_to_open_portal_screen2", "点击物品栏中的§6[卷轴]§r以回到曾经点亮过的石碑！");
        this.addInfo("press_to_show_progress", "按下§6[L]键§f以查看指引！");
        this.addInfo("press_to_skill_tree", "经验充足，按下§6[K]键§f以进行技能加点！");
        this.addInfo("lock_tutorial", "按下§6[%s§6]§r以锁定目标");
        this.addInfo("lock_tutorial_sub", "§c晃动鼠标以切换锁定目标！再次按下以解除锁定！");
        this.addInfo("riptide_tutorial", "在水中按下§6[%s§6]键§f以释放§b激流");
        this.addInfo("dodge_tutorial", "按下§6[%s§6]§f以释放闪避技能");
        this.addInfo("weapon_innate_tutorial", "按下§6[%s§6]键§f以释放武器技能");
        this.addInfo("weapon_innate_charge_tutorial", "§6[完美闪避]§c或§6[完美招架]§c可以对部分武器进行充能！");
        this.addInfo("perfect_dodge_tutorial", "§c抓住时机闪避以释放完美闪避！");
        this.addInfo("hurt_damage", "造成[ %s ]点伤害！");
        this.addInfo("parry_tutorial", "按下§6[%s§6]§f以进行格挡");
        this.addInfo("perfect_parry_tutorial", "§c抓住时机格挡以触发完美招架！");
        this.addInfo("you_pass", "§6你过关！！");

        this.addInfo("press_to_open_map", "按下§6[M]键§f以查看地图");

        this.addInfo("storm_pos", "风暴回响之所在：天空岛");
        this.addInfo("cursed_pos", "诅咒回响之所在：冰冻深海");
        this.addInfo("desert_pos", "沙漠回响之所在：奇美拉之监牢");
        this.addInfo("flame_pos", "烈焰回响之所在：幽冥竞技场");
        this.addInfo("abyss_pos", "深渊回响之所在：隐秘水湾");

        this.addAdvancement("dragon_tame", "驯龙高手", "驯服斯克里兹");
        this.addAdvancement(TCRCoreMod.MOD_ID, "远梦之棺", "梦开始的地方，前往主城寻找守护者。");
        this.addAdvancement(TCRCoreMod.MOD_ID + "_weapon", "王之宝库", "所有可获得的且进行过适配的武器或材料，可通过 [JEI] 查看获取方式及详细信息");
        this.addAdvancement("find_ymsw", "隐秘水湾", "抵达隐秘水湾");
        this.addAdvancement("aquamirae_weapon", "海灵物语-武器", "");
        this.addAdvancement("cataclysm_weapon", "灾变-武器", "");
        this.addAdvancement("legend_weapon", "传奇武器", "");
        this.addAdvancement("ef_legacy", "史诗战斗-武器", "皆可通过合成获取，拥有不同的武器技能和不同的动作模板，可在JEI查看合成方式及技能信息");
        this.addAdvancement("kill_pillager", "投名状", "任务已经完成，该回去找守护神了。");
        this.addAdvancement("mark_map", "标记地点", "守护神帮你标出了神之眼散落的地方，现在动身去夺回它们吧！");
        this.addAdvancement("storm_eye", "风暴之眼", "§a§o当战火撕裂云层，她以风暴为阶梯，为子民筑起悬空净土");
        this.addAdvancement("abyss_eye", "深渊之眼", "§a§o深渊吞噬陆地时，祂将自己缝进海床，血肉化成气泡之城");
        this.addAdvancement("flame_eye", "烈焰之眼", "§a§o岩浆奔涌之地，祂剜出心脏，铸成永不熄灭的烽火台");
        this.addAdvancement("desert_eye", "沙漠之眼", "§a§o守卫者不是怪物，它们是子民自愿化身的活体墓碑");
        this.addAdvancement("cursed_eye", "诅咒之眼", "§a§o当背叛者刺穿她的脊柱，冻泪瞬间冰封三千幽灵船");

        this.addAdvancement("flame_kill", "伊格尼斯(Ignis)之魂", "击败伊格尼斯(Ignis)，获得炎葬");
        this.addAdvancement("storm_kill", "斯库拉(Scylla)之魂", "击败斯库拉(Scylla)，获得庭浪锚戟");
        this.addAdvancement("abyss_kill", "利维坦(Leviathan)之魂", "击败利维坦(Leviathan)，获得潮汐利爪");
        this.addAdvancement("desert_kill", "远古遗魂(Ancient Remnant)之魂", "击败远古遗魂(Ancient Remnant)，获得沙暴之怒");
        this.addAdvancement("cursed_kill", "玛莱迪克特斯(Maledictus)之魂", "击败玛莱迪克特斯(Maledictus)，获得断魂战戟");

        this.addAdvancement("stage1", "阶段1","");
        this.addAdvancement("stage2", "阶段2","");
        this.addAdvancement("stage3", "阶段3","");
        this.addAdvancement("stage4", "阶段4","");
        this.addAdvancement("stage5", "阶段5","");

        this.add(TCREntities.GUIDER.get(), "守望者");
        this.add(TCREntities.GIRL.get(), "摆渡人");
        this.add(TCREntities.TUTORIAL_GOLEM.get(), "训练傀儡");

        BanPortalScreenHandler.onGenerateZH(this);

        this.addDialogAnswer(ModEntities.SKRYTHE.get(), 0, "救世主，吾恭候已久。");
        this.addDialogAnswer(ModEntities.SKRYTHE.get(), 1, "吾名斯克里兹，乃神之坐骑。黑潮一役，吾身负重伤，沉睡至今。苏醒之时，圣殿似有复苏之息，遂归。守望者见吾，告知救世主一事，命吾于此恭候。");
        this.addDialogOption(ModEntities.SKRYTHE.get(), 0, "你是何人？");
        this.addDialogOption(ModEntities.SKRYTHE.get(), 1, "驯服");
        this.addDialogAnswer(ModEntities.RHYZA.get(), 0, "救世主，吾恭候已久。");
        this.addDialogAnswer(ModEntities.RHYZA.get(), 1, "吾名莱萨，乃神之坐骑。黑潮一役，吾身负重伤，沉睡至今。苏醒之时，圣殿似有复苏之息，遂归。守望者见吾，告知救世主一事，命吾于此恭候。");
        this.addDialogOption(ModEntities.RHYZA.get(), 0, "你是何人？");
        this.addDialogOption(ModEntities.RHYZA.get(), 1, "驯服");

        this.addDialogAnswer(EntityType.IRON_GOLEM, 0, "人类，准备好接受试炼了？");
        this.addDialogOption(EntityType.IRON_GOLEM, 0, "确定");
        this.addDialogOption(EntityType.IRON_GOLEM, 1, "再等等");
        this.addDialogAnswer(EntityType.VILLAGER, -2, "曼波？");
        this.addDialogAnswer(EntityType.VILLAGER, -1, "！！！");
        this.addDialogAnswer(EntityType.VILLAGER, 0, "曼波，曼波，哦嘛吉利，曼波~");
        this.addDialogAnswer(EntityType.VILLAGER, 1, "砸布砸布~");
        this.addDialogAnswer(EntityType.VILLAGER, 2, "瓦一夏~曼波~");
        this.addDialogAnswer(EntityType.VILLAGER, 3, "南北绿豆~阿西噶阿西~");
        this.addDialogAnswer(EntityType.VILLAGER, 4, "哈基米南北绿豆~阿西噶阿西~");
        this.addDialogAnswer(EntityType.VILLAGER, 5, "叮咚鸡~叮咚鸡~");
        this.addDialogAnswer(EntityType.VILLAGER, 6, "有哒有哒~");
        this.addDialogAnswer(EntityType.VILLAGER, 7, "阿西噶哈雅酷那路~ wow~");
        this.addDialogOption(EntityType.VILLAGER, -3, "[或许可以试试绿宝石？]");
        this.addDialogOption(EntityType.VILLAGER, -2, "[这位村民对该物品并没有兴趣...]");
        this.addDialogOption(EntityType.VILLAGER, -1, "[收下]");
        this.addDialogOption(EntityType.VILLAGER, 0, "[？？？]");
        this.addDialogOption(EntityType.VILLAGER, 1, "[看来，当地的居民被侵蚀的不轻！]");
        this.addDialogOption(EntityType.VILLAGER, 2, "[叽里咕噜说什么呢？]");
        this.addDialogOption(EntityType.VILLAGER, 3, "[为什么和村民就语言不通了...]");
        this.addDialogOption(TCREntities.GUIDER.get(), 0, "返回");
        this.addDialogOption(TCREntities.GUIDER.get(), 1, "你是何人？");
        this.addDialogOption(TCREntities.GUIDER.get(), 2, "什么海底捞？");
        this.addDialogOption(TCREntities.GUIDER.get(), 3, "这个世界怎么一片汪洋？");
        this.addDialogOption(TCREntities.GUIDER.get(), 4, "我该如何帮助你们？");
        this.addDialogOption(TCREntities.GUIDER.get(), 5, "标记地点");
        this.addDialogOption(TCREntities.GUIDER.get(), 6, "适才相戏耳！");
        this.addDialogOption(TCREntities.GUIDER.get(), 7, "§a我已经击败过掠夺者了§f");
        this.addDialogOption(TCREntities.GUIDER.get(), 8, "我去，你怎么变成美少女了");
        this.addDialogOption(TCREntities.GUIDER.get(), 9, "揭晓神谕");
        this.addDialogAnswer(TCREntities.GUIDER.get(), 0, "所以说…你们是从世界之外…漂流来的？");
        this.addDialogAnswer(TCREntities.GUIDER.get(), 1, "我是此世界的守护神，那日天象异常，雷声四起，你们便降临此地。想必你们就是古老预言中的天外勇者！");
        this.addDialogAnswer(TCREntities.GUIDER.get(), 2, "曾经，世界充满生机，英灵们守护着天地。直到有一天，§d「黑潮」§f降临世间，万物受到侵蚀，甚至部分村民黑化为灾厄村民。而众神不敌§d「黑潮」§f，化为英灵，世界毁灭，一片汪洋。依照古老预言所示，我将他们残存的部分力量封印于此，§6但它们仍有部分火种，散落至各地。§f我受到诅咒而无法离开此地，因此只能默默等待一位救世主降临...");
        this.addDialogAnswer(TCREntities.GUIDER.get(), 3, "世界虽然毁灭，但依靠摆渡人的力量，踏入原始海洋即可重现往昔！古老预言所示，待你从往昔世界中寻回所有失散的火种，便可重建神庙，举行仪式，清洗§d「黑潮」§f重启世界！不过在这之前，先§6击杀一位灾厄村民（掠夺者）§f再§f来找我吧。");
        this.addDialogAnswer(TCREntities.GUIDER.get(), 4, "看来阁下真是预言中的救世主！阁下冒险途中是否收集了§d『神谕残卷』§f？将§d『神谕残卷』§f与我，我将为你揭示神谕所记载的火种方位！待你点亮所有火种，吾便启动§d「黑潮」§f清洗仪式！");

        this.addDialogAnswer(TCREntities.GUIDER.get(), 5, "阁下何故攻击我？");
        this.addDialogAnswer(TCREntities.GUIDER.get(), 6, "既然你已经证明了你的实力，我便卸下伪装，以真面目相待。");
        this.addDialogAnswer(TCREntities.GUIDER.get(), 7, "§d『神谕残卷！』§f将它交给我吧，吾将为你揭示它所记载往昔世界火种散落的位置！待你踏入记忆中的主世界后，吾便为你标记神谕揭示的线索！");

        this.addDialogAnswer(TCREntities.GUIDER.get(), 8, "预言中的救世主啊，有何困惑？");
        this.addDialogOption(TCREntities.GUIDER.get(), 10, "我该如何获得这世界最强的力量？");
        this.addDialogOption(TCREntities.GUIDER.get(), 11, "我们接下来要做什么？");

        this.addDialogOption(TCREntities.GUIDER.get(), 16, "长廊其他几个未知的祭坛是怎么回事？");

        this.addDialogOption(TCREntities.GUIDER.get(), 12, "[我已点亮所有祭坛，启动仪式吧！]");
        this.addDialogOption(TCREntities.GUIDER.get(), 13, "[我不明白...]");
        this.addDialogOption(TCREntities.GUIDER.get(), 14, "[你到底是谁？]");
        this.addDialogOption(TCREntities.GUIDER.get(), 15, "[继续]");//拉入结界
        this.addDialogOption(TCREntities.GUIDER.get(), 17, "[被发现了嘿嘿]");
        this.addDialogOption(TCREntities.GUIDER.get(), 18, "我该如何获取神谕残卷？");
        this.addDialogAnswer(TCREntities.GUIDER.get(), 9, "位于终界异空间内的§d终界龙§f，它与§d「黑潮」§f颇有渊源。击败它后，它诞下的精华可铸成阎魔刀，乃来自异世之力，不可估量。");
        this.addDialogAnswer(TCREntities.GUIDER.get(), 10, "阁下只需将§d『神谕残卷』§f与我，待你踏入记忆中的主世界后，吾将为你揭示神谕所记载的火种方位！随后阁下便可前往吾在地图上所标注之处，击败魔物，夺回火种，§6并将它们供奉在长廊之祭坛之上§f。待寻回所有火种即可启动仪式，净化§d「黑潮」§f！");
        this.addDialogAnswer(TCREntities.GUIDER.get(), 11, "外来之人...你不会真以为自己是什么救世主吧...哈哈哈哈哈，你不过是孤的夺回力量的傀儡罢了！");
        this.addDialogAnswer(TCREntities.GUIDER.get(), 12, "你所击败的一切，它们才是守护世界的神明眷属，正是他们阻碍了孤的大业！此地乃吾栖息之所，只要他们消失，损失几个灾厄村民对孤来说不算什么，更何况，你所带回的魔神火种可助孤重铸肉身。");
        this.addDialogAnswer(TCREntities.GUIDER.get(), 13, "我是谁？你作为祭品无权得知！哪有什么愚蠢的仪式，吸收了你的力量，世界将为孤所统治！受死吧！");

        this.addDialogAnswer(TCREntities.GUIDER.get(), 14, "这...吾曾与§d「黑潮军团」§f在此地大战，部分祭坛封印的英灵已失去了响应，永远不会再回来了...但残存的英灵，已足以启动仪式。");

        this.addDialogAnswer(TCREntities.GUIDER.get(), 15, "§b" +
                "风暴之火种§f...我感受到它就散落在§6[%s]§f，击败%s夺回它吧！吾便在此地等候。");
        this.addDialogAnswer(TCREntities.GUIDER.get(), 19, "§6烈焰之火种§f...我感受到它就散落在§6[%s]§f，击败%s夺回它吧！吾便在此地等候。");
        this.addDialogAnswer(TCREntities.GUIDER.get(), 16, "§3深渊之火种§f...我感受到它就散落在§6[%s]§f，击败%s夺回它吧！吾便在此地等候。");
        this.addDialogAnswer(TCREntities.GUIDER.get(), 17, "§2诅咒之火种§f...我感受到它就散落在§6[%s]§f击败%s夺回它吧！吾便在此地等候。");
        this.addDialogAnswer(TCREntities.GUIDER.get(), 18, "§e沙漠之火种§f...我感受到它就散落在§6[%s]§f击败%s夺回它吧！吾便在此地等候。");
        this.addDialogAnswer(TCREntities.GUIDER.get(), 20, "正常走流程怎么会多出神谕残卷？你是不是偷了别人的？");
        this.addDialogAnswer(TCREntities.GUIDER.get(), 21, "既然你已点亮所有祭坛，待你§6入内击败英灵，吸收众英灵之力后§f，我们再开始仪式吧！");
        this.addDialogAnswer(TCREntities.GUIDER.get(), 22, "前往吾指引之处，取回§d[神之眼]§f，将其供奉予§6[圣殿中心庭院之女神像所在]§f，女神像将为你揭晓神谕！");

        this.addDialogAnswer(TCREntities.GIRL.get(), 0, "阁下，好久不见！");
        this.addDialogAnswer(TCREntities.GIRL.get(), 1, "阁下忘了我么？我是圣殿摆渡人，为众人指点迷津。阁下如有奇珍异宝，可与我瞧瞧，小女子可提取忆质，将其化为宝具！当你踏足过§c地狱§f或§d末地§f后，我也可以送你一程。");
        this.addDialogAnswer(TCREntities.GIRL.get(), 2, "阁下若是经验充足，可打开技能面板学习技能。在§6技能树界面右上角点击经验球，即可将经验化为技能点。§f技能加点十分重要，建议学习生命提升等提升生存能力的技能！");
        this.addDialogAnswer(TCREntities.GIRL.get(), 3, "我是圣殿摆渡人，为众人指点迷津。阁下如有奇珍异宝，可与我瞧瞧，小女子可提取忆质，将其化为宝具！当你踏足过§c地狱§f或§d末地§f后，我也可以送你一程。初次见面，此宝具赠与阁下，按下§d[%s]§f可开风帆，行万里！");
        this.addDialogAnswer(TCREntities.GIRL.get(), 4, "阁下，确定要前往吗？我无法将您送回来...请确保传送石带在身上了");
        this.addDialogAnswer(TCREntities.GIRL.get(), 5, "§d[%s]§f可通过遗迹宝箱或完成某些任务获取。阁下若是拥有§6[%s]§f，可按§d[%s]§f打开技能面板学习技能。技能加点十分重要，建议学习生命提升等提升生存能力的技能！");

        this.addDialogOption(TCREntities.GIRL.get(), 0, "返回");
        this.addDialogOption(TCREntities.GIRL.get(), 1, "你是何人？");
        this.addDialogOption(TCREntities.GIRL.get(), 2, "神兵萃取");
        this.addDialogOption(TCREntities.GIRL.get(), 3, "甲胄提炼");
        this.addDialogOption(TCREntities.GIRL.get(), 4, "秘技学习");
        this.addDialogOption(TCREntities.GIRL.get(), 5, "打开技能树");
        this.addDialogOption(TCREntities.GIRL.get(), 6, "前往地狱");
        this.addDialogOption(TCREntities.GIRL.get(), 7, "前往末地");
        this.addDialogOption(TCREntities.GIRL.get(), 8, "确定");
        this.addDialogOption(TCREntities.GIRL.get(), 9, "饰品提取");
        this.addDialogOption(TCREntities.GIRL.get(), 10, "§a前往主世界§f");

        this.addDialogAnswer(NFIEntities.ARTERIUS.get(), 0, "异界之人，你为何来此？");
        this.addDialogAnswer(NFIEntities.ARTERIUS.get(), 1, "哈哈哈，守望者将烈焰之眼托付于我，为的是避免落入不义之人手中。即使是她老人家亲自来了，也得过我这关！我倒是要看看，你有没有这个能耐！");
        this.addDialogAnswer(NFIEntities.ARTERIUS.get(), 2, "异界之人，好久不见！");
        this.addDialogAnswer(NFIEntities.ARTERIUS.get(), 3, "准备好了吗？");
        this.addDialogAnswer(NFIEntities.ARTERIUS.get(), 4, "位于地狱的§6[猪灵一族]§f，在黑潮时期，曾造出[%s]来抵御黑潮。可惜他们败了，失去了神智。但他们仍然认得[%s§f§f]，使用[%s§f§f]与他们交易，便可揭开远古战争机器的面纱。");
        this.addDialogAnswer(NFIEntities.ARTERIUS.get(), 5, "[%s]和[%s]，则分别守护着[%s]和[%s]的回响。");
        this.addDialogAnswer(NFIEntities.ARTERIUS.get(), 6, "待你寻回他们的回响后，再来找我吧，我将带你见识§4§l[遗忘之海]§f§f的力量！");
        this.addDialogOption(NFIEntities.ARTERIUS.get(), 0, "取回烈焰之眼");
        this.addDialogOption(NFIEntities.ARTERIUS.get(), 1, "取你性命");
        this.addDialogOption(NFIEntities.ARTERIUS.get(), 2, "发起挑战");
        this.addDialogOption(NFIEntities.ARTERIUS.get(), 3, "再等等");
        this.addDialogOption(NFIEntities.ARTERIUS.get(), 4, "切磋切磋");
        this.addDialogOption(NFIEntities.ARTERIUS.get(), 5, "打探消息");
        this.addDialogOption(NFIEntities.ARTERIUS.get(), 6, "继续");
        this.addDialogOption(NFIEntities.ARTERIUS.get(), 7, "告辞");
        this.addDialogOption(NFIEntities.ARTERIUS.get(), 8, "§a我已寻回他们的回响了");

    }
}
