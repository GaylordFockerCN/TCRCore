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
import com.yungnickyoung.minecraft.ribbits.module.EntityTypeModule;
import net.alp.monsterexpansion.entity.ModEntities;
import net.minecraft.data.PackOutput;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Items;
import net.shelmarow.nightfall_invade.entity.NFIEntities;
import net.sonmok14.fromtheshadows.server.utils.registry.ItemRegistry;
import net.unusual.blockfactorysbosses.init.BlockFactorysBossesModItems;

public class TCRJPLangGenerator extends TCRLangProvider {
    public TCRJPLangGenerator(PackOutput output) {
        super(output, "ja_jp");
    }

    @Override
    protected void addTranslations() {

        this.add("epicfight.skill_slot.passive4", "パッシブスキル 4");
        this.add("epicfight.skill_slot.passive5", "パッシブスキル 5");
        this.add("travelerstitles.tcrcore.sanctum", "夢の領域");
        this.addBiome(TCRBiomes.AIR, "奈落");

        this.addTask(TCRTaskManager.KILL_PILLAGER, "邪悪な村人を殺す");
        this.addTask(TCRTaskManager.GIVE_ORACLE_TO_KEEPER, "§6[神託の欠片]§rを聖域のキーパーに渡す");
        this.addTask(TCRTaskManager.BACK_TO_KEEPER, "聖域のキーパーに戻る");
        this.addTask(TCRTaskManager.FIND_GODNESS_STATUE, "§6[神の目]§rを女神の像供える");
        this.addTask(TCRTaskManager.FIND_ARTERIUS, "アテリュスに戻る");
        this.addTask(TCRTaskManager.LIGHT_ALL_ALTAR, "祭壇を全て点火する");
        this.addTask(TCRTaskManager.GO_TO_OVERWORLD, "オーバーワールドへ行く");

        this.addTCRItemInfo(net.blay09.mods.waystones.item.ModItems.warpStone, "§6[転送巻]§rをインベントリに押すと前で発見された転送石へ戻れるよ！");
        this.addTCRItemInfo("§c警告：このアイテムで重要なものをバックパックに送ったら、使えなくなる可能性があるよ！", net.p3pp3rf1y.sophisticatedbackpacks.init.ModItems.ADVANCED_MAGNET_UPGRADE.get(), net.p3pp3rf1y.sophisticatedbackpacks.init.ModItems.MAGNET_UPGRADE.get());
        this.addTCRItemInfo(ItemRegistry.BOTTLE_OF_BLOOD.get(), "§c[結晶骨髄]§rで作ったポーション, または§d[ネヘモス]§rから入手");
        this.addTCRItemInfo(EFNItem.DEEPDARK_HEART.get(), "§2[ヴォーデン]§rと§2[コネリア船長]§rから入手");
        this.addTCRItemInfo(BlockFactorysBossesModItems.DRAGON_SKULL.get(), "§c[地下騎士]§rと§4[地獄ドラゴン]§rから入手");
        this.addTCRItemInfo(ModItems.CORAL_CHUNK.get(), "§dレヴァイアサンの幻§rの§a[コーラル巨像]§rから入手");
        this.addTCRItemInfo(BlockFactorysBossesModItems.DRAGON_BONE.get(), "§c[地下騎士]§rと§4[地獄ドラゴン]§rから入手");
        this.addTCRItemInfo(com.github.dodo.dodosmobs.init.ModItems.CHIERA_CLAW.get(), "§e[骸骨キメラ]§rから入手");
        this.addTCRItemInfo(ModItems.CHITIN_CLAW.get(), "§dスキュラの幻§rの§3[巨爪衛兵]§rから入手");
        this.addTCRItemInfo(BlockFactorysBossesModItems.KNIGHT_SWORD.get(), "§c[地下騎士]§rから入手");
        this.addTCRItemInfo(Items.DRAGON_EGG, "§dジ・エンド§rの§d[エンダードラゴン]§rから入手");
        this.addTCRItemInfo(EpicSkillsItems.ABILIITY_STONE.get(), "右クリックでスキルポイントもらえる");

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
        this.addItemUsageInfo(TCRItems.ARTIFACT_TICKET.get(), "任務書のある任務から入手. §d[聖人の港湾]§rの§3[渡し守の少女]§rでアーチファクトを交易できる");
        this.add(TCRItems.RARE_ARTIFACT_TICKET.get(), "金のアーチファクト真髄");
        this.addItemUsageInfo(TCRItems.RARE_ARTIFACT_TICKET.get(), "任務書のある任務から入手. §d[聖人の港湾]§rの§3[渡し守の少女]§rでレアーアーチファクトを交易できる");
        this.add(TCRItems.ANCIENT_ORACLE_FRAGMENT.get(), "神託の欠片");
        this.addItemUsageInfo(TCRItems.ANCIENT_ORACLE_FRAGMENT.get(), "神様の指示を書いている。聖域キーパに渡すと、炎の種の位置を分かるようになる");
        this.addItemUsageInfo(TCRItems.ANCIENT_ORACLE_FRAGMENT.get(), "§c他人のクエストアイテムを盗まないでください", 2);

        this.addInfo("require_item_to_wake", "[%s]が必要だそうだ");
        this.addInfo("weapon_no_interact", "インタラクトできません！[%s]をお押しください。または他の物をお使いください。");
        this.addInfo("tudigong_gift", "[初対面のお礼]");
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
        this.addInfo("illegal_item_tip2", "§6これを使う時はまだ来なかったようだ。");

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
        this.addInfo("unlock_new_dim_girl", "§6新しいものは渡し守の少女に解放された！§r");
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
        this.addAdvancement("cursed_eye", "呪われた目", "§a§o裏切り者は彼女の背骨を刺した時、凍結された涙は三千本の船を氷で閉じ込められた。");

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
        this.add(TCREntities.GIRL.get(), "渡し守の少女");
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
        this.addDialogAnswer(TCREntities.GUIDER.get(), 2, "昔、この世界の生命力は強かったです。英雄の魂は天界と土地を守りました。ある日、§d「黒潮」§f は世界に現れました。世の全ては侵食されました。村人は悪者になってしまいました。神様たちは、「黒潮」を防げれなかったです。そのため、神様たちは精霊になり、世界は滅ばせました。今は海しかありません。神様の指示から、神様たち残った力をここに封印しました。§6しかし、ある力は世界の他の場所に残りました。§f 呪われた私は、ここから離れません。救世主を待つしかできませんでした...");
        this.addDialogAnswer(TCREntities.GUIDER.get(), 3, "この世界は滅ばせましたが、渡し守の少女の力から、原初の海へ足を踏むと、過去へ行けるようになります。神様の指示から、神様たち残った力をここに無事で戻ると、この聖域を再建きます。儀式を始め、「黒潮」を浄化せます。世界を復活できます。その前に、§6邪悪の村人を倒してくれませんか§f。倒したらここにお戻りください。");
        this.addDialogAnswer(TCREntities.GUIDER.get(), 4, "あなたはやっぱりこの世界の救世主様です！§d『神託の欠片』§fを見つかりましたか。それを私に渡したら。神様たち残った力の位置を解読いたし、マークいたします。全ての炎を点火したら、「黒潮」を浄化する術式を始めます。");
        this.addDialogAnswer(TCREntities.GUIDER.get(), 5, "なぜ私を攻撃したの？");
        this.addDialogAnswer(TCREntities.GUIDER.get(), 6, "救世主様の力を拝見させくれましたので、偽装を抜けました。");
        this.addDialogAnswer(TCREntities.GUIDER.get(), 7, "それは§d『神託の欠片』§fですね。ぜひ解読させてください！記録された世界の神様たち残った力をマークさせてください！");

        this.addDialogAnswer(TCREntities.GUIDER.get(), 8, "世界を救う様、なにかお困りですか。");
        this.addDialogOption(TCREntities.GUIDER.get(), 10, "世界の最強の力はどうやって手に入れるの？");
        this.addDialogOption(TCREntities.GUIDER.get(), 11, "次はなにをすべきか。");

        this.addDialogOption(TCREntities.GUIDER.get(), 16, "廊下の他の祭壇はどういうこと？");

        this.addDialogOption(TCREntities.GUIDER.get(), 12, "[全ての祭壇を点火した！術式を始め！]");
        this.addDialogOption(TCREntities.GUIDER.get(), 13, "[ちょっとわからない...]");
        this.addDialogOption(TCREntities.GUIDER.get(), 14, "[お前は誰か？？]");
        this.addDialogOption(TCREntities.GUIDER.get(), 15, "[続く]"); // Pull into the barrier
        this.addDialogOption(TCREntities.GUIDER.get(), 17, "[しまった、ｗ]");
        this.addDialogOption(TCREntities.GUIDER.get(), 18, "『神託の欠片』はどうやって手に入れるの？");
        this.addDialogAnswer(TCREntities.GUIDER.get(), 9, "§dエンダードラゴン§fは、 ジ・エンドに住んでいます。§d「黒潮」§fと淵源があります。倒したら、その残片はヤマトを作れます。この世界の外からの力ので、ただの話で説明できません。");
        this.addDialogAnswer(TCREntities.GUIDER.get(), 10, "『神託の欠片』を渡してくれば十分です。記録された世界に入ると、 神様たち残った力をマークいたします。マークした場所を見つけ、悪魔を倒して、 神様たち残った力を取り返し、§6廊下の祭壇をご点火ください。§f全ての祭壇を点火したら、「黒潮」を浄化の術式を始められます！");
        this.addDialogAnswer(TCREntities.GUIDER.get(), 11, "他所者よ... まさか救世主って童謡を信じているか... ハハハ！お前はただ俺の力を取り返す傀儡だ！");
        this.addDialogAnswer(TCREntities.GUIDER.get(), 12, "全てを倒した敵は、この世界の守護者だった！俺の最終プランを止まった者よ！ここは俺を束縛された場所だった。守護者の死のために、少しの村人を失ったのは俺にとって最高だ！君のおかけで、悪魔たちの力から肉体を回復された！");
        this.addDialogAnswer(TCREntities.GUIDER.get(), 13, "俺は誰？犠牲するための傀儡は分かる権利がない！術式？何にかそれ？君の力を俺になったら、この世界は俺の法則を従うしかない！死ね！");

        this.addDialogAnswer(TCREntities.GUIDER.get(), 14, "これは... ここで§d「黒潮の軍隊」§fと戦いました。祭壇に封印された精霊たちはもう戻ってくれません... でも、残った精霊たちは儀式を始めるのは、十分です。");

        this.addDialogAnswer(TCREntities.GUIDER.get(), 15, "§b暴風の目§f... §6[%s]§fで感じられた、それを倒し、取り返せ！ここに待ってるよ。");
        this.addDialogAnswer(TCREntities.GUIDER.get(), 19, "§6火炎の目§f... §6[%s]§fで感じられた、それを倒し、取り返せ！ここに待ってるよ。");
        this.addDialogAnswer(TCREntities.GUIDER.get(), 16, "§3アビスの目§f... §6[%s]§fで感じられた、それを倒し、取り返せ！ここに待ってるよ。");
        this.addDialogAnswer(TCREntities.GUIDER.get(), 17, "§2呪われた目§f... §6[%s]§fで感じられた、それを倒し、取り返せ！ここに待ってるよ。");
        this.addDialogAnswer(TCREntities.GUIDER.get(), 18, "§e砂漠の目§f... §6[%s]§fで感じられた、それを倒し、取り返せ！ここに待ってるよ。");
        this.addDialogAnswer(TCREntities.GUIDER.get(), 20, "正規ルートなのに、なぜもう一つの『神託の欠片』を手に入れますか。他の人から盗ましたか。");
        this.addDialogAnswer(TCREntities.GUIDER.get(), 21, "全てのの祭壇を点火してくれましたので、§6中の精霊を倒し、力を吸収しましたら§f、術式を始められます！");
        this.addDialogAnswer(TCREntities.GUIDER.get(), 22, "マークされた場所へ、§d[神の目]§fを取り返し、§6[庭の中の女神様の像をお供えください]§f。女神様は『神託の欠片』を差し上げるよ！");

        this.addDialogAnswer(TCREntities.GIRL.get(), 0, "お久しぶりです。");
        this.addDialogAnswer(TCREntities.GIRL.get(), 1, "私をお忘れましたか。私は聖域の渡し守の少女と申します。迷った子を案内いたします。宝物があったら、ぜひ私に拝見させてください。それの記憶をアーチファクトに鍛造いたします。§c地獄§fまたは§dジ・エンド§fへご旅行する時、足を助けいたします。");
        this.addDialogAnswer(TCREntities.GIRL.get(), 2, "経験値は十分でしたら、スキルツリーでスキルをご習得できます。§6右上で経験値をスキルポイントにお引き換えます§f。スキルをよくアンロックください。生存率を上げるスキルはおすすめいたします。例えば、生命力を上げるスキルはおすすめいたします。");
        this.addDialogAnswer(TCREntities.GIRL.get(), 3, "私は聖域の渡し守の少女と申します。迷った子を案内いたします。宝物があったら、ぜひ私に拝見させてください。それの記憶をアーチファクトに鍛造いたします。§c地獄§fまたは§dジ・エンド§fへご旅行する時、足を助けいたします。初対面のお礼ので、これをお取りください。§d[%s]§fを押すと帆を開い、出航できます！");
        this.addDialogAnswer(TCREntities.GIRL.get(), 4, "どこかご探索したいところがありますか。力不足ので、ここに戻る魔法は把握できません。転送石をお持ちください。");
        this.addDialogAnswer(TCREntities.GIRL.get(), 5, "§d[%s]§fはレリックの箱、または任務からご入手できます。§d[%s]§fがあれば、§6[%s]§fを押すとスキルをご習得できます。生存率を上げるために、スキルをご習得はおすすめいたします。");

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

        this.addDialogAnswer(NFIEntities.ARTERIUS.get(), 0, "他所者よ、なぜここに来る？");
        this.addDialogAnswer(NFIEntities.ARTERIUS.get(), 1, "ハハハッ、 聖域のキーパーは火炎の目を悪者に手に入れないため俺に頼まれた。彼女はここに来ても、俺を倒すしかない！お主の力をみせてくれ！");
        this.addDialogAnswer(NFIEntities.ARTERIUS.get(), 2, "他所者よ、久しぶりだ！");
        this.addDialogAnswer(NFIEntities.ARTERIUS.get(), 3, "アー・ユウ・レーディ？");
        this.addDialogAnswer(NFIEntities.ARTERIUS.get(), 4, "「黒潮」の時、地獄にいる§6[ピグリンクラン]§fは暗いを防ぐために[%s]を作った。残念だから、彼たちは負けた。意識も混乱になった。しかし、彼たちは[%s§f§f]を覚えている。[%s§f§f]を使って交易を始められる。昔の戦争機械の秘密を分かる。");
        this.addDialogAnswer(NFIEntities.ARTERIUS.get(), 5, "[%s]と[%s]は[%s]と[%s]のエッコをそれぞれで守っている。");
        this.addDialogAnswer(NFIEntities.ARTERIUS.get(), 6, "それを取り返すと、俺を見つけてくれ！その時に§4§l[レシアンの海]§f§fの力を見せてやる！");
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
