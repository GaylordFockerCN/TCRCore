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
import com.yungnickyoung.minecraft.リビットs.module.EntityTypeModule;
import net.alp.monsterexpansion.entity.ModEntities;
import net.minecraft.data.PackOutput;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Items;
import net.shelmarow.nightfall_invade.entity.NFIEntities;
import net.sonmok14.fromtheshadows.server.utils.registry.ItemRegistry;
import net.unusual.blockfactorysbosses.init.BlockFactorysBossesModItems;

public class TCRENLangGenerator extends TCRLangProvider {
    public TCRENLangGenerator(PackOutput output) {
        super(output, "ja_JP");
    }

    @Override
    protected void addTranslations() {

        this.add("epicfight.skill_slot.passive4", "パッシブスキル 4");
        this.add("epicfight.skill_slot.passive5", "パッシブスキル 5");
        this.add("travelerstitles.tcrcore.sanctum", "夢の領域");
        this.addBiome(TCRBiomes.AIR, "奈落");

        this.addTask(TCRTaskManager.KILL_PILLAGER, "邪悪な村人を殺す");
        this.addTask(TCRTaskManager.GIVE_ORACLE_TO_KEEPER, "§6[神様の指示の一部]§rを聖域のキーパーに渡す");
        this.addTask(TCRTaskManager.BACK_TO_KEEPER, "聖域のキーパーに戻る");
        this.addTask(TCRTaskManager.FIND_GODNESS_STATUE, "§6[神の目]§rを女神の像供える");
        this.addTask(TCRTaskManager.FIND_ARTERIUS, "アテリュスに戻る");
        this.addTask(TCRTaskManager.LIGHT_ALL_ALTAR, "祭壇を全て点火する");
        this.addTask(TCRTaskManager.GO_TO_OVERWORLD, "オーバーワールドへ行く");

        this.addTCRItemInfo(net.blay09.mods.waystones.item.ModItems.warpStone, "§6[転送巻]§rをインベントリに押すと前で発見された転送石へ戻れるよ！");
        this.addTCRItemInfo("§c警告：このアイテムで重要なものをバックパックに送ったら、使えなくなる可能性があるよ！", net.p3pp3rf1y.sophisticatedbackpacks.init.ModItems.ADVANCED_MAGNET_UPGRADE.get(), net.p3pp3rf1y.sophisticatedbackpacks.init.ModItems.MAGNET_UPGRADE.get());
        this.addTCRItemInfo(ItemRegistry.BOTTLE_OF_BLOOD.get(), "§c[結晶骨髄]§rで作ったポーション, または§d[ネヘモス]§rのドロップだ");
        this.addTCRItemInfo(EFNItem.DEEPDARK_HEART.get(), "§2[ヴォーデン]§rと§2[コネリア船長]§rから入手");
        this.addTCRItemInfo(BlockFactorysBossesModItems.DRAGON_SKULL.get(), "§c[地下騎士]§rと§4[地獄ドラゴン]§rから入手");
        this.addTCRItemInfo(ModItems.CORAL_CHUNK.get(), "§dレヴァイアサンの幻§rの§a[コーラル巨像]§rから入手");
        this.addTCRItemInfo(BlockFactorysBossesModItems.DRAGON_BONE.get(), "§c[地下騎士]§rと§4[地獄ドラゴン]§rから入手");
        this.addTCRItemInfo(com.github.dodo.dodosmobs.init.ModItems.CHIERA_CLAW.get(), "§e[骸骨キメラ]§rから入手");
        this.addTCRItemInfo(ModItems.CHITIN_CLAW.get(), "§dスキュラの幻§rの§3[巨爪衛兵]§rから入手");
        this.addTCRItemInfo(BlockFactorysBossesModItems.KNIGHT_SWORD.get(), "§c[地下騎士]§rから入手");
        this.addTCRItemInfo(Items.DRAGON_EGG, "§dジ・エンド§rの§d[エンダードラゴン]§rから入手");
        this.addTCRItemInfo(EpicSkillsItems.ABILIITY_STONE.get(), "右クリックでスキルレベルもらえる");

        this.add("itemGroup.tcr.items", "The Casket of Reveries — コアアイテム");
        this.add("key.categories." + TCRCoreMod.MOD_ID, "The Casket of Reveries — コア");
        this.addKeyMapping(KeyMappings.RIPTIDE, "激流");
        this.addKeyMapping(KeyMappings.SHOW_TASK, "任務ディスプレイオン・オフ");

        this.add("skill_tree.sword_soaring.unlock_tip", "エメラルドで§6[天空の島]§rの村人の交易から習得");
        this.add("unlock_tip.tcrcore.battleborn.water_avoid1", "§d[アメシストブロック]§fで§6[リビット]§fの交易から習得");
        this.add("unlock_tip.tcrcore.battleborn.fire_avoid", "§6[骸骨キメラ]§rを倒したら習得");
        this.add("unlock_tip.tcrcore.get_vatansever", "§d[衞彊の刃]§fを入手したら習得");
        this.addSkill("water_avoid", "水退治呪文", "水中呼吸できるようになる");
        this.addSkill("fire_avoid", "炎退治呪文", "火炎免疫のようになる");
        this.addSkill("perfect_dodge", "回避エフェクト", "完璧回避と格好良くなる");

        this.add(TCRItems.PROOF_OF_ADVENTURE.get(), "冒険の証");
        this.addItemUsageInfo(TCRItems.PROOF_OF_ADVENTURE.get(), "刃の血で鍛造したもの。旅はようやく終わった。君の勇気は伝説になった。");
        this.add(TCRItems.DUAL_BOKKEN.get(), "ヌンチャク");
        this.addItemUsageInfo(TCRItems.DUAL_BOKKEN.get(), "スキルイッシュがあるけど、いいメンタルを大事に持っているよ！");
        this.add(TCRItems.VOID_CORE.get(), "奈落の真髄");
        this.addItemUsageInfo(TCRItems.VOID_CORE.get(), "[エンダー守護者]から入手");
        this.add(TCRItems.ABYSS_CORE.get(), "アビス真髄");
        this.addItemUsageInfo(TCRItems.ABYSS_CORE.get(), "[レヴァイアサン]から入手");
        this.add(TCRItems.ARTIFACT_TICKET.get(), "アーチファクト真髄");
        this.addItemUsageInfo(TCRItems.ARTIFACT_TICKET.get(), "任務書のある任務から入手. §d[聖人の港湾]§rの§3[フェリーの女の子]§rでアーチファクトを交易できる");
        this.add(TCRItems.RARE_ARTIFACT_TICKET.get(), "金のアーチファクト真髄");
        this.addItemUsageInfo(TCRItems.RARE_ARTIFACT_TICKET.get(), "任務書のある任務から入手. §d[聖人の港湾]§rの§3[フェリーの女の子]§rでレアーアーチファクトを交易できる");
        this.add(TCRItems.ANCIENT_ORACLE_FRAGMENT.get(), "神様の指示の一部");
        this.addItemUsageInfo(TCRItems.ANCIENT_ORACLE_FRAGMENT.get(), "神様の指示を書いている。聖域キーパに渡すと、炎の種の位置を分かるようになる");
        this.addItemUsageInfo(TCRItems.ANCIENT_ORACLE_FRAGMENT.get(), "§c他人のクエストアイテムを盗まないでください", 2);

        this.addInfo("require_item_to_wake", "[%s]が必要だそうだ");
        this.addInfo("weapon_no_interact", "インタラクトできません！[%s]をお押しください。または他の物をお使いください。");
        this.addInfo("tudigong_gift", "[初見のお礼]");
        this.addInfo("tudigong_gift_get", "§6[土地神様]§f:　これはワシを一生懸命守った宝物だ。君に大事に使うなら良い！");
        this.addInfo("need_to_unlock_waystone", "起動されていない転送石がまだあるそうだ");
        this.addInfo("nether_unlock", "地獄が解放された！");
        this.addInfo("end_unlock", "ジ・エンドを解放された！");
        this.addInfo("nothing_happen_after_bless", "§d何も起こっていない。この[神の目]もう使ったそうだ");
        this.addInfo("dim_max_4_players", "§6幻で四人以上入らないようだ");
        this.addInfo("can_not_enter_before_finish", "§6幻に入る時ではないようだ");
        this.addInfo("can_not_do_this_too_early", "§6私にとって早い過ぎだ");
        this.addInfo("captain_start_heal", "§cコネリアーは回復している！火力を全開しないと");
        this.addInfo("illegal_item_tip", "§c禁止されたアイテム");
        this.addInfo("illegal_item_tip2", "§6これを使う時はまだだようだ。");

        this.addInfo("shift_to_pic", "しゃがんで攻撃すると回収できる");
        this.addInfo("no_place_to_ship", "置く場がないようだ");
        this.addInfo("boss_killed_ready_return", "§6ボスを倒した！ブロックを壊せるようになった");
        this.addInfo("click_to_return", "§a[クリックで戻れる]");
        this.addInfo("cs_warning", "§c§l 警告：Compute Shaderは無効になっている。Epic Fightのコンフィグでオンにすると、もっといい経験になるよ！");
        this.addInfo("wraithon_start_tip", "§d[Wraithon]: §6他所者、君の旅はここまでだ！");
        this.addInfo("wraithon_end_tip", "§d[Wraithon]: §6不...可...能...だ...");
        this.addInfo("dim_block_no_interact", "§cボスを倒していないので、幻のブロックをインタラクトできないようだ");
        this.addInfo("altar_dim_info", "幻のメッセージ:");
        this.addInfo("related_loot", "怪物: [%s] | ドロップアイテム: [%s]");
        this.add(TCRBlocks.CURSED_ALTAR_BLOCK.get(), "呪われた祭壇");
        this.add(TCRBlocks.ABYSS_ALTAR_BLOCK.get(), "アビス祭壇");
        this.add(TCRBlocks.STORM_ALTAR_BLOCK.get(), "暴風祭壇");
        this.add(TCRBlocks.FLAME_ALTAR_BLOCK.get(), "火炎祭壇");
        this.add(TCRBlocks.DESERT_ALTAR_BLOCK.get(), "砂漠祭壇");
        this.add(TCRBlocks.MONST_ALTAR_BLOCK.get(), "悪獣祭壇");
        this.add(TCRBlocks.VOID_ALTAR_BLOCK.get(), "奈落祭壇");
        this.add(TCRBlocks.MECH_ALTAR_BLOCK.get(), "機械祭壇");

        this.addInfo("attack_to_restart", "§c攻撃すると再挑戦できるようだ");
        this.addInfo("after_heal_stop_attack", "§6攻撃を止めると安定になるようだ");
        this.addInfo("cloud_follow_me", "§6[魔法雲]: §fこんにちは！こちらにいらっしゃってください！");
        this.addInfo("dim_demending", "§6幻は回復中のようだ[%d§6]s");
        this.addInfo("to_be_continue2", "[P1nero]: §6プレイいただき、誠にありがとうございます！ボスをたくさん作っていますから少々お待ち下さい！");
        this.addInfo("second_after_boss_die_left", "%d秒でオーバーワールドに戻れる");
        this.addInfo("press_to_open_battle_mode", "§c[%s]を押すとバトルモードになる§r");
        this.addInfo("unlock_new_dim_girl", "§6新しいものはフェーリの女の子に解放された！§r");
        this.addInfo("unlock_new_dim", "§c[地獄]と§d[ジ・エンド]§6を解放された！§r");
        this.addInfo("iron_golem_name", "天空の島守護者");

        this.addInfo("get_mimic_invite", "[%s]: 他所者よ、 あなたについて正しかった！§6[%s§6]§fをお受け取りください！");
        this.addInfo("kill_arterius", "[%s]: 他所者よ、 君はやっぱりすごいですね！予言は真実のようだ！[%s]を君に差し上げるよ！");
        this.addInfo("kill_boss1", "§d[どこかの声]:§r マモル... テンクウノシマ... タオス... コクチョウ...");
        this.addInfo("kill_boss2", "§c[どこかの声]:§r マモル... ドラゴンボーン...　タオス... コクチョウ...");
        this.addInfo("kill_boss3", "§3[どこかの声]:§r ...ニゲロウ...");
        this.addInfo("kill_boss4", "§a[どこかの声]:§r ...カイシテ... クレ... モットホシイ...");
        this.addInfo("kill_boss5", "§e[どこかの声]:§r ハハハ、ジユウダ！");

        this.addInfo("finish_all_eye", "§d全ての祭壇を起動された！§r");
        this.addInfo("time_to_altar", "失った炎の種を見つかったようだ。戻って祭壇をきどうしよう！");
        this.addInfo("time_to_ask_godness_statue", "§d＊女神のところで使えるそうだ");
        this.addInfo("time_to_end", "全ての祭壇を起動されたね。 聖域のキーパーと話そう！");

        this.addInfo("can_not_enter_dim", "許可を受け取っていないようだ... §6神様の指示§rをもっと手に入れよう！");
        this.addInfo("reset_when_no_player", "幻に誰もいないと、幻は全回復になるようだ。");
        this.addInfo("on_full_set", "装備セットボーナス");
        this.addInfo("unlock_new_ftb_page", "任務書に新しいページを解放された。§6[任務書]§rをご確認ください。");
        this.addInfo("unlock_new_skill_page", "新しいスキルページを解放された。§6[K]§rをお押し、ご確認ください。");
        this.addInfo("unlock_new_skill", "[%s]を解放された。§6[K]§rをお押し、ご確認ください。");
        this.addInfo("hit_barrier", "この先は、後で探索しよう！");

        this.addInfo("death_info", "§6敵は強すぎなら、異なるスキルを組み合わせよう！");
        this.addInfo("enter_dimension_tip", "右クリックで幻に入れる。");
        this.addInfo("use_true_eye_tip", "[%s]を祭壇の核にお使いください。");

        this.addInfo("add_item_tip", "新しいアイテムを入手された: %s × %d!");
        this.addInfo("skill_point_lack", "このスキルは%dポイントが必要だ");
        this.addInfo("press_to_open_portal_screen2", "§6[転送巻]§rをインベントリに押すと前で発見された転送石へ戻れるよ！");
        this.addInfo("press_to_show_progress", "§6[L]§fを押すとガイダンスを見られるよ！");
        this.addInfo("press_to_skill_tree", "経験値は十分だ。§6[K]§fで新しいスキルを解放しよう！");
        this.addInfo("lock_tutorial", "§6[[%s§6]§rでロックオン");
        this.addInfo("lock_tutorial_sub", "§cマウスを移動すると、ターゲットを変える。");
        this.addInfo("riptide_tutorial", "§6[[%s§6]§fで§b激流§fを使える");
        this.addInfo("dodge_tutorial", "§6[[%s§6]§fで回避できる");
        this.addInfo("weapon_innate_tutorial", "§6[[%s§6]§fで武器のスキルを使える");
        this.addInfo("weapon_innate_charge_tutorial", "§6[完璧回避]§cまたは§6[完璧パーリー]§cである武器をチャージできるよ！");
        this.addInfo("perfect_dodge_tutorial", "§c完璧回避のタイミングを学ぼう！");
        this.addInfo("hurt_damage", "[ %s ]のダメージを与えた！");
        this.addInfo("parry_tutorial", "§6[[%s§6]§fでパーリーできる");
        this.addInfo("perfect_parry_tutorial", "§c完璧パーリーのタイミングを学ぼう！");
        this.addInfo("you_pass", "§6合格です!おめでとうございます！");

        this.addInfo("press_to_open_map", "§6[M]§fでマップを見れる");

        this.addInfo("godness_statue_pos", "女神様の像");
        this.addInfo("storm_pos", "暴風のエッコの位置：天空の島");
        this.addInfo("cursed_pos", "呪われたエッコの位置：氷の迷宮");
        this.addInfo("desert_pos", "砂漠のエッコの位置：キメラの牢獄");
        this.addInfo("flame_pos", "火炎のエッコの位置：地下闘技場");
        this.addInfo("abyss_pos", "アビスのエッコの位置：リビット村");

        this.addAdvancement("dragon_tame", "ドラゴンテーマ", "スクリーフを仲間になる");
        this.addAdvancement(TCRCoreMod.MOD_ID, "The Casket of Reveries", "夢を始まる場所。 聖域で聖域のキーパーを探そう。");
        this.addAdvancement(TCRCoreMod.MOD_ID + "_weapon", "王様の宝物", "全ての武器や材料です。[JEI]で入手方法や説明をご確認ください。");
        this.addAdvancement("find_ymsw", "リビット村", "リビット村へ届ける");
        this.addAdvancement("aquamirae_weapon", "海の霊の物語　ー　武器", "");
        this.addAdvancement("cataclysm_weapon", "大災害　ー　武器", "");
        this.addAdvancement("legend_weapon", "伝説の武器", "");
        this.addAdvancement("ef_legacy", "Epic Fight　ー　武器", "全てクラフトできます。様々なスキルやアニメーションをついています。JEIでレシピをご確認ください。");
        this.addAdvancement("kill_pillager", "ウォーロード", "任務完了。聖域のキーパーに戻ろう");
        this.addAdvancement("mark_map", "位置をマーク", "聖域のキーパーは失った神の目の位置をマークしてくれた。神の目を探そう！");
        this.addAdvancement("storm_eye", "暴風の目", "§a§o戦争で雲を割れた時、彼女は暴風を階段になり、子民のためにパラダイスを作った。");
        this.addAdvancement("abyss_eye", "アビスの目", "§a§oアビスを土地を食う時、彼は海の床と自分を縫った。体はバブルの城になった。");
        this.addAdvancement("flame_eye", "火炎の目", "§a§oマグマは世界を覆う時、彼は自分の心臓を取って、消えないビーコンを作った。");
        this.addAdvancement("desert_eye", "砂漠の目", "§a§o守護者たちは化け物ではなかった。彼たちは子民の願いのだ。");
        this.addAdvancement("cursed_eye", "呪われた目", "§a§o裏切り者は彼女の背骨を刺した時、凍結された涙は三千本の船を氷で閉じ込めた。");

        this.addAdvancement("flame_kill", "イグニスの魂", "イグニスを倒して、火葬を入手。");
        this.addAdvancement("storm_kill", "スキュラの魂", "スキュラを倒して、庭波戟を入手。");
        this.addAdvancement("abyss_kill", "レヴァイアサンの魂", "レヴァイアサンを倒して、波の爪を入手。");
        this.addAdvancement("desert_kill", "昔に残った魂", "昔に残った魂を倒して、暴風の激怒を入手。");
        this.addAdvancement("cursed_kill", "マレディクタスの魂", "マレディクタスを倒して魂殺しを入手。");

        this.addAdvancement("stage1", "ステージ１", "");
        this.addAdvancement("stage2", "ステージ２", "");
        this.addAdvancement("stage3", "ステージ３", "");
        this.addAdvancement("stage4", "ステージ４", "");
        this.addAdvancement("stage5", "ステージ５", "");

        this.add(TCREntities.GUIDER.get(), "聖域のキーパー");
        this.add(TCREntities.GIRL.get(), "フェーリの女の子");
        this.add(TCREntities.TUTORIAL_GOLEM.get(), "訓練用のゴーレム");

        BanPortalScreenHandler.onGenerateEN(this);

        this.addDialogAnswer(ModEntities.SKRYTHE.get(), 0, "選ばれた者よ、ずっと待っていた。");
        this.addDialogAnswer(ModEntities.SKRYTHE.get(), 1, "吾はスクリーフ、ディバインスティードだ。黒潮の時、傷のために今まで長眠した。聖域の復活を感じられたので、ここに戻った。聖域のキーパーから事情を聞いた。ここに君を待つのを命令された。");
        this.addDialogOption(ModEntities.SKRYTHE.get(), 0, "あなたは誰？");
        this.addDialogOption(ModEntities.SKRYTHE.get(), 1, "仲間になる");

        this.addDialogAnswer(ModEntities.RHYZA.get(), 0, "選ばれた者よ、ずっと待っていた。");
        this.addDialogAnswer(ModEntities.RHYZA.get(), 1, "吾はライザ、ディバインスティードだ。黒潮の時、傷のために今まで長眠した。聖域の復活を感じられたので、ここに戻った。聖域のキーパーから事情を聞いた。ここに君を待つのを命令された。");
        this.addDialogOption(ModEntities.RHYZA.get(), 0, "あなたは誰？");
        this.addDialogOption(ModEntities.RHYZA.get(), 1, "仲間になる");

        this.addDialogAnswer(EntityType.IRON_GOLEM, 0, "メン、アー・ユウ・レーディ？");
        this.addDialogOption(EntityType.IRON_GOLEM, 0, "イェス");
        this.addDialogOption(EntityType.IRON_GOLEM, 1, "ウェイト");
        this.addDialogAnswer(EntityType.VILLAGER, -2, "マンボ？");
        this.addDialogAnswer(EntityType.VILLAGER, -1, "！！！");
        this.addDialogAnswer(EntityType.VILLAGER, 0, "マンボ、 マンボ、 オマツリ、 マンボ〜");
        this.addDialogAnswer(EntityType.VILLAGER, 1, "ザブ　ザブ〜");
        this.addDialogAnswer(EntityType.VILLAGER, 2, "ワ　イ　シャ〜 マンボ〜");
        this.addDialogAnswer(EntityType.VILLAGER, 3, "ナンベイ　リゥドウ〜　アシガアシ〜");
        this.addDialogAnswer(EntityType.VILLAGER, 4, "ハキミ　ナンベイ　リゥドウ〜 アシガアシ〜");
        this.addDialogAnswer(EntityType.VILLAGER, 5, "ディング　ドング　ジ〜　ディング　ドング　ジ〜");
        this.addDialogAnswer(EntityType.VILLAGER, 6, "ヨウ　ダ　ヨウ　ダ〜");
        this.addDialogAnswer(EntityType.VILLAGER, 7, "アシ　ガ　ハヤク　ナル〜　ワウ〜");
        this.addDialogOption(EntityType.VILLAGER, -3, "[エメラルドを試してみよ]");
        this.addDialogOption(EntityType.VILLAGER, -2, "[村人には興味ないね...]");
        this.addDialogOption(EntityType.VILLAGER, -1, "[もらう]");
        this.addDialogOption(EntityType.VILLAGER, 0, "[？？？]");
        this.addDialogOption(EntityType.VILLAGER, 1, "[皆も黒潮で深刻に影響されているようだ。]");
        this.addDialogOption(EntityType.VILLAGER, 2, "[なにかぶつぶつしているの？]");
        this.addDialogOption(EntityType.VILLAGER, 3, "[なんで村人の言語を理解できない...]");

        this.addDialogOption(TCREntities.GUIDER.get(), 0, "戻る");
        this.addDialogOption(TCREntities.GUIDER.get(), 1, "あなたは誰？");
        this.addDialogOption(TCREntities.GUIDER.get(), 2, "海で発見されたってなに？");
        this.addDialogOption(TCREntities.GUIDER.get(), 3, "なんでこの世界は海しかないの？");
        this.addDialogOption(TCREntities.GUIDER.get(), 4, "どうやって手伝うか。");
        this.addDialogOption(TCREntities.GUIDER.get(), 5, "位置をマーク");
        this.addDialogOption(TCREntities.GUIDER.get(), 6, "さっきのは冗談よ！");
        this.addDialogOption(TCREntities.GUIDER.get(), 7, "§a邪悪な村人を倒した§f");
        this.addDialogOption(TCREntities.GUIDER.get(), 8, "なんで綺麗な女の子になっているの？");
        this.addDialogOption(TCREntities.GUIDER.get(), 9, "神様の指示を解読");
        this.addDialogAnswer(TCREntities.GUIDER.get(), 0, "つまり、他の世界から来たの？");
        this.addDialogAnswer(TCREntities.GUIDER.get(), 1, "私はこの世界の法則の守護者です。その日、天国はおかしい現象が起こったり、雷をしたりしました時、君はこの世界にいらっしゃいました。あなたは他の世界からの勇者と間違えません！");
        this.addDialogAnswer(TCREntities.GUIDER.get(), 2, "昔、この世界は the world was full of vitality, and heroic spirits protected the heavens and earth. Until one day, the §d「Black Tide」§f descended upon the world, everything was corrupted, and some villagers even turned into illagers. The gods, unable to resist the §d「Black Tide」§f, became spirits, the world was destroyed, leaving only a vast ocean. According to the ancient prophecy, I sealed their remaining power here, §6but some of their sparks have scattered to various places.§f Cursed, I cannot leave this place, so I can only wait silently for a savior to appear...");
        this.addDialogAnswer(TCREntities.GUIDER.get(), 3, "Though the world is destroyed, with the ferryman's power, stepping into the Primordial Ocean can restore the past! The ancient prophecy says that once you retrieve all the lost sparks from the world of the past, you can rebuild the temple, perform the ritual, cleanse the §d「Black Tide」§f, and restart the world! But before that, first §6defeat an illager (pillager)§f and then §fcome back to me.");
        this.addDialogAnswer(TCREntities.GUIDER.get(), 4, "It seems you truly are the savior foretold in the prophecy! Have you collected any §d『Oracle Fragments』§f during your journey? Give the §d『Oracle Fragments』§f to me, and I will reveal the locations of the sparks recorded in the oracle! Once you light all the sparks, I will initiate the §d「Black Tide」§f cleansing ritual!");
        this.addDialogAnswer(TCREntities.GUIDER.get(), 5, "Why did you attack me?");
        this.addDialogAnswer(TCREntities.GUIDER.get(), 6, "Since you have proven your strength, I shall shed my disguise and show you my true form.");
        this.addDialogAnswer(TCREntities.GUIDER.get(), 7, "§d『Oracle Fragments!』§f Give them to me, and I will reveal the locations of the sparks scattered in the world of the past as recorded in the oracle! Once you step into the memory of the Overworld, I will mark the clues revealed by the oracle for you!");

        this.addDialogAnswer(TCREntities.GUIDER.get(), 8, "O prophesied savior, what troubles you?");
        this.addDialogOption(TCREntities.GUIDER.get(), 10, "How can I obtain the most powerful strength in this world?");
        this.addDialogOption(TCREntities.GUIDER.get(), 11, "What should we do next?");

        this.addDialogOption(TCREntities.GUIDER.get(), 16, "What are the other unknown altars in the long corridor?");

        this.addDialogOption(TCREntities.GUIDER.get(), 12, "[I have lit all the altars, begin the ritual!]");
        this.addDialogOption(TCREntities.GUIDER.get(), 13, "[I don't understand...]");
        this.addDialogOption(TCREntities.GUIDER.get(), 14, "[Who are you, really?]");
        this.addDialogOption(TCREntities.GUIDER.get(), 15, "[Continue]"); // Pull into the barrier
        this.addDialogOption(TCREntities.GUIDER.get(), 17, "[You caught me, hehe]");
        this.addDialogOption(TCREntities.GUIDER.get(), 18, "How can I obtain Oracle Fragments?");
        this.addDialogAnswer(TCREntities.GUIDER.get(), 9, "The §dEnder Dragon§f, located within the End's alien space, has a deep connection to the §d「Black Tide」§f. After defeating it, the essence it gives birth to can be forged into the Yamato, a power from another world, immeasurable.");
        this.addDialogAnswer(TCREntities.GUIDER.get(), 10, "You simply need to give the §d『Oracle Fragments』§f to me. Once you step into the memory of the Overworld, I will reveal the locations of the sparks recorded in the oracle! Then you can go to the places I mark on the map, defeat the demons, reclaim the sparks, §6and offer them at the altars in the long corridor§f. Once all sparks are retrieved, you can initiate the ritual to purify the §d「Black Tide」§f!");
        this.addDialogAnswer(TCREntities.GUIDER.get(), 11, "Outsider... You don't actually believe you're some savior, do you... Hahahaha, you are merely a puppet I used to reclaim my power!");
        this.addDialogAnswer(TCREntities.GUIDER.get(), 12, "Everything you defeated, they were the divine guardians of this world, the very ones who stood in the way of my grand plan! This place is my dwelling. As long as they are gone, losing a few illagers means nothing to me. Moreover, the demon god sparks you brought back can help me rebuild my physical form.");
        this.addDialogAnswer(TCREntities.GUIDER.get(), 13, "Who am I? You, as a sacrificial offering, have no right to know! What foolish ritual? After absorbing your power, the world shall be under my rule! Die!");

        this.addDialogAnswer(TCREntities.GUIDER.get(), 14, "This... I once battled the §d「Black Tide Legion」§f here. Some of the spirits sealed in the altars have lost their response and will never return... But the remaining spirits are enough to initiate the ritual.");

        this.addDialogAnswer(TCREntities.GUIDER.get(), 15, "§bThe Eye of Storm§f... I sense it scattered in §6[%s]§f, defeat %s and reclaim it! I will wait here.");
        this.addDialogAnswer(TCREntities.GUIDER.get(), 19, "§6The Eye of Blaze§f... I sense it scattered in §6[%s]§f, defeat %s and reclaim it! I will wait here.");
        this.addDialogAnswer(TCREntities.GUIDER.get(), 16, "§3The Eye of Abyss§f... I sense it scattered in §6[%s]§f, defeat %s and reclaim it! I will wait here.");
        this.addDialogAnswer(TCREntities.GUIDER.get(), 17, "§2The Eye of Curse§f... I sense it scattered in §6[%s]§f defeat %s and reclaim it! I will wait here.");
        this.addDialogAnswer(TCREntities.GUIDER.get(), 18, "§eThe Eye of Desert§f... I sense it scattered in §6[%s]§f defeat %s and reclaim it! I will wait here.");
        this.addDialogAnswer(TCREntities.GUIDER.get(), 20, "Following the normal process, how could there be extra Oracle Fragments? Did you steal them from someone?");
        this.addDialogAnswer(TCREntities.GUIDER.get(), 21, "Since you have lit all the altars, after you §6enter and defeat the spirits, absorbing the power of all the spirits§f, we shall begin the ritual!");
        this.addDialogAnswer(TCREntities.GUIDER.get(), 22, "Go to the place I guide you, retrieve the §d[Eye of God]§f, and offer it to §6[the goddess statue in the central courtyard of the temple]§f. The goddess statue will reveal the oracle to you!");

        this.addDialogAnswer(TCREntities.GIRL.get(), 0, "Long time no see, esteemed one!");
        this.addDialogAnswer(TCREntities.GIRL.get(), 1, "Have you forgotten me? I am the Ferry girl of the Saint Temple, here to guide the lost. If you have any rare treasures, show them to me. I can extract their memoria and transform them into artifacts! When you can set foot in §cthe Nether§f or §dthe End§f, I can also give you a ride.");
        this.addDialogAnswer(TCREntities.GIRL.get(), 2, "If you have sufficient experience, open the skill panel to learn skills. §6Click the experience orb in the top right corner of the skill tree interface to convert experience into skill points.§f Skill allocation is crucial; consider learning skills that enhance survivability, like Health Boost!");
        this.addDialogAnswer(TCREntities.GIRL.get(), 3, "I am the Ferry girl of the Saint Temple, here to guide the lost. If you have any rare treasures, show them to me. I can extract their memoria and transform them into artifacts! When you can set foot in §cthe Nether§f or §dthe End§f, I can also give you a ride. As a first meeting gift, take this artifact. Press §d[%s]§f to deploy the sail and travel thousands of miles!");
        this.addDialogAnswer(TCREntities.GIRL.get(), 4, "Esteemed one, are you sure you wish to go? I cannot bring you back... Please ensure you have the Waystone with you.");
        this.addDialogAnswer(TCREntities.GIRL.get(), 5, "§d[%s]§f can be obtained through relic chests or by completing certain quests. If you possess §d[%s]§f, press §6[%s]§f to open the skill panel and learn skills. Skill allocation is very important—it is recommended to learn skills like Health Boost and others that enhance survivability!");

        this.addDialogOption(TCREntities.GIRL.get(), 0, "戻る");
        this.addDialogOption(TCREntities.GIRL.get(), 1, "あなたは誰？");
        this.addDialogOption(TCREntities.GIRL.get(), 2, "武器精錬");
        this.addDialogOption(TCREntities.GIRL.get(), 3, "防具精錬");
        this.addDialogOption(TCREntities.GIRL.get(), 4, "スキルを学ぶ");
        this.addDialogOption(TCREntities.GIRL.get(), 5, "スキルツリーを見る");
        this.addDialogOption(TCREntities.GIRL.get(), 6, "地獄へ行く");
        this.addDialogOption(TCREntities.GIRL.get(), 7, "ジ・エンドへ行く");
        this.addDialogOption(TCREntities.GIRL.get(), 8, "確認");
        this.addDialogOption(TCREntities.GIRL.get(), 9, "アーチファクト精錬");
        this.addDialogOption(TCREntities.GIRL.get(), 10, "§aオーバーワールドへ行く§f");

        this.addDialogAnswer(NFIEntities.ARTERIUS.get(), 0, "Otherworldly one, why have you come here?");
        this.addDialogAnswer(NFIEntities.ARTERIUS.get(), 1, "Hahaha, the Sanctuary Keeper entrusted the Eye of Flame to me to prevent it from falling into the hands of the unworthy. Even if she were to come herself, she would have to get through me! Let me see if you have what it takes!");
        this.addDialogAnswer(NFIEntities.ARTERIUS.get(), 2, "Otherworldly one, long time no see!");
        this.addDialogAnswer(NFIEntities.ARTERIUS.get(), 3, "Are you ready?");
        this.addDialogAnswer(NFIEntities.ARTERIUS.get(), 4, "During the Black Tide, the §6[Piglin clan]§f§f of the Nether created [%s] to resist the darkness. Unfortunately, they were defeated and lost their sanity. However, they still recognize [%s§f§f]. Use [%s§f§f] to trade with them, and you may unveil the secrets of the ancient war machines.");
        this.addDialogAnswer(NFIEntities.ARTERIUS.get(), 5, "[%s] and [%s] guard the echoes of [%s] and [%s], respectively.");
        this.addDialogAnswer(NFIEntities.ARTERIUS.get(), 6, "After you retrieve them, come find me again, and I will show you the power of §4§l[The Lethean Sea]§f§f!");
        this.addDialogOption(NFIEntities.ARTERIUS.get(), 0, "火炎の目を取り返す");
        this.addDialogOption(NFIEntities.ARTERIUS.get(), 1, "お前を殺す");
        this.addDialogOption(NFIEntities.ARTERIUS.get(), 2, "準備できた");
        this.addDialogOption(NFIEntities.ARTERIUS.get(), 3, "少し待って");
        this.addDialogOption(NFIEntities.ARTERIUS.get(), 4, "力を比べよう！");
        this.addDialogOption(NFIEntities.ARTERIUS.get(), 5, "詳しく聞かせて");
        this.addDialogOption(NFIEntities.ARTERIUS.get(), 6, "続ける");
        this.addDialogOption(NFIEntities.ARTERIUS.get(), 7, "離れる");
        this.addDialogOption(NFIEntities.ARTERIUS.get(), 8, "§a彼たちのエッコを全て見つかった。");

        this.addDialogAnswer(EntityTypeModule.リビット.get(), 0, "ググガガ！");
        this.addDialogOption(EntityTypeModule.リビット.get(), 0, "もらう");
    }
}
