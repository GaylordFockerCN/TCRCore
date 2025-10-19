package com.p1nero.tcrcore.events;

import com.github.L_Ender.cataclysm.entity.AnimationMonster.BossMonsters.Ender_Guardian_Entity;
import com.github.L_Ender.cataclysm.entity.AnimationMonster.BossMonsters.Ignis_Entity;
import com.github.L_Ender.cataclysm.entity.AnimationMonster.BossMonsters.The_Harbinger_Entity;
import com.github.L_Ender.cataclysm.entity.AnimationMonster.BossMonsters.The_Leviathan.The_Leviathan_Entity;
import com.github.L_Ender.cataclysm.entity.InternalAnimationMonster.IABossMonsters.Ancient_Remnant.Ancient_Remnant_Entity;
import com.github.L_Ender.cataclysm.entity.InternalAnimationMonster.IABossMonsters.Maledictus.Maledictus_Entity;
import com.github.L_Ender.cataclysm.entity.InternalAnimationMonster.IABossMonsters.NewNetherite_Monstrosity.Netherite_Monstrosity_Entity;
import com.github.L_Ender.cataclysm.entity.InternalAnimationMonster.IABossMonsters.Scylla.Scylla_Entity;
import com.github.L_Ender.cataclysm.init.ModItems;
import com.github.dodo.dodosmobs.entity.InternalAnimationMonster.IABossMonsters.Bone_Chimera_Entity;
import com.hm.efn.registries.EFNItem;
import com.merlin204.sg.item.SGItems;
import com.obscuria.aquamirae.Aquamirae;
import com.obscuria.aquamirae.AquamiraeUtils;
import com.obscuria.aquamirae.common.entities.CaptainCornelia;
import com.obscuria.aquamirae.registry.AquamiraeItems;
import com.p1nero.cataclysm_dimension.worldgen.CataclysmDimensions;
import com.p1nero.dialog_lib.events.ServerNpcEntityInteractEvent;
import com.p1nero.entityrespawner.EntityRespawnerMod;
import com.p1nero.tcrcore.TCRCoreMod;
import com.p1nero.tcrcore.capability.PlayerDataManager;
import com.p1nero.tcrcore.capability.TCRCapabilityProvider;
import com.p1nero.tcrcore.capability.TCRPlayer;
import com.p1nero.tcrcore.capability.TCRTaskManager;
import com.p1nero.tcrcore.client.sound.CorneliaMusicPlayer;
import com.p1nero.tcrcore.client.sound.WraithonMusicPlayer;
import com.p1nero.tcrcore.gameassets.TCRSkills;
import com.p1nero.tcrcore.item.TCRItems;
import com.p1nero.tcrcore.save_data.TCRDimSaveData;
import com.p1nero.tcrcore.utils.EntityUtil;
import com.p1nero.tcrcore.utils.ItemUtil;
import com.p1nero.tcrcore.utils.WorldUtil;
import net.kenddie.fantasyarmor.item.FAItems;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.protocol.game.ClientboundSetTitleTextPacket;
import net.minecraft.network.protocol.game.ClientboundSoundPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.monster.Pillager;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.Tags;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.p1nero.ss.item.SwordSoaringItems;
import net.shelmarow.nightfall_invade.entity.spear_knight.Arterius;
import net.sonmok14.fromtheshadows.server.entity.mob.BulldrogiothEntity;
import net.unusual.blockfactorysbosses.entity.SwordWaveEntity;
import net.unusual.blockfactorysbosses.entity.UnderworldKnightEntity;
import net.unusual.blockfactorysbosses.init.BlockFactorysBossesModEntities;
import net.unusual.blockfactorysbosses.init.BlockFactorysBossesModItems;
import org.merlin204.wraithon.entity.wraithon.WraithonEntity;
import org.merlin204.wraithon.worldgen.WraithonDimensions;
import yesman.epicfight.api.animation.AnimationPlayer;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Mod.EventBusSubscriber(modid = TCRCoreMod.MOD_ID)
public class LivingEntityEventListeners {

    public static final String TRIGGERED = "death_triggered";

    @SubscribeEvent
    public static void onLivingAttack(LivingAttackEvent event){
        LivingEntity entity = event.getEntity();
        if(event.getSource().getEntity() instanceof ServerPlayer serverPlayer) {
            if(serverPlayer.getMainHandItem().is(BlockFactorysBossesModItems.KNIGHT_SWORD.get()) && !serverPlayer.getCooldowns().isOnCooldown(BlockFactorysBossesModItems.KNIGHT_SWORD.get())) {
                ServerLevel serverLevel = serverPlayer.serverLevel();
                AbstractArrow entityToSpawn = new SwordWaveEntity(BlockFactorysBossesModEntities.SWORD_WAVE.get(), serverLevel);
                entityToSpawn.setOwner(serverPlayer);
                entityToSpawn.setBaseDamage(5);
                entityToSpawn.setKnockback(0);
                entityToSpawn.setSilent(true);
                entityToSpawn.setPierceLevel((byte) 8);
                entityToSpawn.setPos(serverPlayer.getX(), serverPlayer.getEyeY() - 0.1, serverPlayer.getZ());
                entityToSpawn.shoot(serverPlayer.getLookAngle().x, serverPlayer.getLookAngle().y, serverPlayer.getLookAngle().z, 2.0F, 0.0F);
                serverLevel.addFreshEntity(entityToSpawn);
                serverPlayer.getCooldowns().addCooldown(BlockFactorysBossesModItems.KNIGHT_SWORD.get(), 80);
            }
            EpicFightCapabilities.getUnparameterizedEntityPatch(serverPlayer, ServerPlayerPatch.class).ifPresent(serverPlayerPatch -> {
                if(serverPlayerPatch.isVanillaMode()) {
                    event.setCanceled(true);
                    serverPlayer.displayClientMessage(TCRCoreMod.getInfo("press_to_open_battle_mode"), true);
                }
            });

            if(!serverPlayer.isCreative()) {
                if(!PlayerDataManager.stormEyeBlessed.get(serverPlayer) && entity instanceof BulldrogiothEntity) {
                    serverPlayer.displayClientMessage(TCRCoreMod.getInfo("can_not_do_this_too_early"), true);
                    event.setCanceled(true);
                    return;
                }
                if(!PlayerDataManager.abyssEyeBlessed.get(serverPlayer) && entity instanceof Bone_Chimera_Entity) {
                    serverPlayer.displayClientMessage(TCRCoreMod.getInfo("can_not_do_this_too_early"), true);
                    event.setCanceled(true);
                    return;
                }
                if(!PlayerDataManager.desertEyeBlessed.get(serverPlayer) && entity instanceof CaptainCornelia) {
                    serverPlayer.displayClientMessage(TCRCoreMod.getInfo("can_not_do_this_too_early"), true);
                    event.setCanceled(true);
                    return;
                }
                if(!PlayerDataManager.cursedEyeBlessed.get(serverPlayer) && entity instanceof Arterius arterius) {
                    if(!arterius.isInBattle()) {
                        event.setCanceled(true);
                        return;
                    }
                    serverPlayer.displayClientMessage(TCRCoreMod.getInfo("can_not_do_this_too_early"), true);
                    event.setCanceled(true);
                }
            }

        }

        if(event.getEntity() instanceof Bone_Chimera_Entity boneChimeraEntity) {
            if(boneChimeraEntity.isDeadOrDying()) {
                event.setCanceled(true);
            }
        }

        if(event.getEntity() instanceof UnderworldKnightEntity underworldKnight) {
            if(underworldKnight.isDeadOrDying()) {
                event.setCanceled(true);
            }
        }

        if(event.getEntity() instanceof ServerPlayer serverPlayer) {
            if(event.getSource().getEntity() instanceof UnderworldKnightEntity underworldKnight) {
                if(!underworldKnight.getPersistentData().getBoolean("hurt_mark")) {
                    event.setCanceled(true);
                }
            }

            EpicFightCapabilities.getUnparameterizedEntityPatch(serverPlayer, ServerPlayerPatch.class).ifPresent(serverPlayerPatch -> {
                AnimationPlayer player = serverPlayerPatch.getAnimator().getPlayerFor(null);
                //激流期间无敌
                if(player != null && player.getAnimation() == Animations.TSUNAMI_REINFORCED) {
                    event.setCanceled(true);
                }
            });

        }

    }

    @SubscribeEvent
    public static void onEntityDrop(LivingDropsEvent event) {
        LivingEntity entity = event.getEntity();
        if(!entity.level().isClientSide) {

            if(entity instanceof IronGolem ironGolem && WorldUtil.isInStructure(ironGolem, WorldUtil.SKY_ISLAND)) {
                event.setCanceled(true);
            }

            if(entity instanceof Pillager) {
                if(entity.getRandom().nextFloat() < 0.2F) {
                    ItemUtil.addItemEntity(entity, Items.GOLD_INGOT, 1, 0xc000ff);
                }
                if(entity.getRandom().nextFloat() < 0.1F) {
                    ItemUtil.addItemEntity(entity, Items.DIAMOND, 1, 0xc000ff);
                }
                if(entity.getRandom().nextFloat() < 0.01F) {
                    ItemUtil.addItemEntity(entity, Items.NETHERITE_INGOT, 1, 0xc000ff);
                }
                if(!event.getEntity().getLootTable().toString().endsWith("captain") && entity.hasGlowingTag()) {
                    ItemUtil.addItemEntity(entity, AquamiraeItems.SHELL_HORN.get(), 1, ChatFormatting.GOLD.getColor());
                }
            } else if(entity instanceof Enemy) {
                if(entity.getRandom().nextFloat() < 0.1F) {
                    ItemUtil.addItemEntity(entity, Items.IRON_INGOT, 1, 0xc000ff);
                }
                if(entity.getRandom().nextFloat() < 0.03F) {
                    ItemUtil.addItemEntity(entity, Items.AMETHYST_SHARD, 1, 0xc000ff);
                } else if(entity.getRandom().nextFloat() < 0.01F) {
                    ItemUtil.addItemEntity(entity, FAItems.MOON_CRYSTAL.get(), 1, 0xc000ff);
                }
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onLivingDeath(LivingDeathEvent event){
        LivingEntity livingEntity = event.getEntity();
        if(event.isCanceled() || livingEntity.getPersistentData().getBoolean(TRIGGERED)) {
            return;
        }
        livingEntity.getPersistentData().putBoolean(TRIGGERED, true);
        Vec3 center = livingEntity.position();
        livingEntity.level().getEntitiesOfClass(ServerPlayer.class, (new AABB(center, center)).inflate(30)).forEach(player -> {

            if (livingEntity instanceof Scylla_Entity) {
                PlayerDataManager.stormEyeKilled.put(player, true);
            }

            if (livingEntity instanceof Ignis_Entity) {
                PlayerDataManager.flameEyeKilled.put(player, true);
            }

            if (livingEntity instanceof The_Leviathan_Entity) {
                PlayerDataManager.abyssEyeKilled.put(player, true);
            }

            if (livingEntity instanceof Maledictus_Entity) {
                PlayerDataManager.cursedEyeKilled.put(player, true);
            }

            if (livingEntity instanceof Ancient_Remnant_Entity) {
                PlayerDataManager.desertEyeKilled.put(player, true);
            }

            if (livingEntity instanceof Ender_Guardian_Entity) {
                PlayerDataManager.voidEyeKilled.put(player, true);
                if(PlayerDataManager.canGetInviteTip(player) && !PlayerDataManager.letterGet.get(player)) {
                    TCRTaskManager.FIND_ARTERIUS.start(player);
                }
            }

            if (livingEntity instanceof Netherite_Monstrosity_Entity) {
                PlayerDataManager.monstEyeKilled.put(player, true);
                if(PlayerDataManager.canGetInviteTip(player) && !PlayerDataManager.letterGet.get(player)) {
                    TCRTaskManager.FIND_ARTERIUS.start(player);
                }
            }

            if (livingEntity instanceof The_Harbinger_Entity) {
                PlayerDataManager.mechEyeKilled.put(player, true);
                if(PlayerDataManager.canGetInviteTip(player) && !PlayerDataManager.letterGet.get(player)) {
                    TCRTaskManager.FIND_ARTERIUS.start(player);
                }
            }

            if(livingEntity instanceof EnderDragon && !PlayerDataManager.voidEyeTraded.get(player)) {
                ItemUtil.addItemEntity(player, ModItems.VOID_EYE.get(), 1, ChatFormatting.LIGHT_PURPLE.getColor().intValue());
            }

            if(livingEntity instanceof WitherBoss && !PlayerDataManager.mechEyeTraded.get(player)) {
                ItemUtil.addItemEntity(player, ModItems.MECH_EYE.get(), 1, ChatFormatting.DARK_RED.getColor().intValue());
            }

            if(event.getSource().getEntity() instanceof Player && livingEntity.getType().is(EntityTypeTags.RAIDERS) && !PlayerDataManager.pillagerKilled.get(player)) {
                ItemStack itemStack = TCRItems.ANCIENT_ORACLE_FRAGMENT.get().getDefaultInstance();
                itemStack.getOrCreateTag().putString(TCRPlayer.PLAYER_NAME, player.getGameProfile().getName());
                ItemUtil.addItemEntity(player, itemStack, 1, ChatFormatting.LIGHT_PURPLE.getColor().intValue());
                TCRTaskManager.KILL_PILLAGER.finish(player);
                PlayerDataManager.pillagerKilled.put(player, true);
            }

            if(livingEntity instanceof IronGolem && WorldUtil.isInStructure(livingEntity, WorldUtil.SKY_ISLAND)) {
                if(!PlayerDataManager.stormEyeTraded.get(player)) {
                    ItemUtil.addItemEntity(player, ModItems.STORM_EYE.get(), 1, ChatFormatting.AQUA.getColor().intValue());
                    player.displayClientMessage(TCRCoreMod.getInfo("kill_boss1"), false);
                    player.connection.send(new ClientboundSetTitleTextPacket(TCRCoreMod.getInfo("you_pass")));
                    player.connection.send(new ClientboundSoundPacket(BuiltInRegistries.SOUND_EVENT.wrapAsHolder(SoundEvents.UI_TOAST_CHALLENGE_COMPLETE), SoundSource.PLAYERS, player.getX(), player.getY(), player.getZ(), 1.0F, 1.0F, player.getRandom().nextInt()));
                    //看PlayerPickUp那边
                }
            }

            if(livingEntity instanceof BulldrogiothEntity
                    && PlayerDataManager.stormEyeBlessed.get(player)
                    && !PlayerDataManager.abyssEyeTraded.get(player)) {
                ItemUtil.addItemEntity(player, ModItems.ABYSS_EYE.get(), 1, ChatFormatting.BLUE.getColor().intValue());
                player.displayClientMessage(TCRCoreMod.getInfo("kill_boss3"), false);
            }


            if(livingEntity instanceof Bone_Chimera_Entity && !PlayerDataManager.desertEyeTraded.get(player) && PlayerDataManager.abyssEyeBlessed.get(player)) {
                ItemUtil.addItemEntity(player, ModItems.DESERT_EYE.get(), 1, ChatFormatting.YELLOW.getColor().intValue());
                player.displayClientMessage(TCRCoreMod.getInfo("kill_boss5"), false);

                CommandSourceStack commandSourceStack = player.createCommandSourceStack().withPermission(2).withSuppressedOutput();
                if(!PlayerDataManager.fireAvoidUnlocked.get(player)) {
                    Objects.requireNonNull(player.getServer()).getCommands().performPrefixedCommand(commandSourceStack, "/skilltree unlock @s dodge_parry_reward:passive tcrcore:fire_avoid true");
                    player.displayClientMessage(TCRCoreMod.getInfo("unlock_new_skill", Component.translatable(TCRSkills.FIRE_AVOID.getTranslationKey()).withStyle(ChatFormatting.AQUA)), false);
                    player.level().playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.UI_TOAST_CHALLENGE_COMPLETE, SoundSource.PLAYERS, 1.0F, 1.0F);
                    PlayerDataManager.fireAvoidUnlocked.put(player, true);
                }
            }

            if(livingEntity instanceof CaptainCornelia && !PlayerDataManager.cursedEyeTraded.get(player) && PlayerDataManager.desertEyeBlessed.get(player)) {
                ItemUtil.addItemEntity(player, ModItems.CURSED_EYE.get(), 1, ChatFormatting.DARK_GREEN.getColor().intValue());
                player.displayClientMessage(TCRCoreMod.getInfo("kill_boss4"), false);
            }

        });

        if(livingEntity.level() instanceof ServerLevel serverLevel) {

            if (CataclysmDimensions.LEVELS.contains(serverLevel.dimension()) && livingEntity.getType().is(Tags.EntityTypes.BOSSES)) {
                TCRDimSaveData.get(serverLevel).setBossKilled(true);
                //回城
                MutableComponent clickToReturn = TCRCoreMod.getInfo("click_to_return");
                clickToReturn.setStyle(Style.EMPTY
                        .applyFormat(ChatFormatting.GREEN)
                        .withBold(true)
                        .withClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/execute in minecraft:overworld as @s run tp " + WorldUtil.START_POS.getX() + " " + WorldUtil.START_POS.getY() + " " + WorldUtil.START_POS.getZ()))
                );

                serverLevel.players().forEach((serverPlayer -> {
                    serverPlayer.displayClientMessage(TCRCoreMod.getInfo("boss_killed_ready_return"), false);
                    serverPlayer.displayClientMessage(clickToReturn, false);
                }));

            }

            //保险措施，一般到不了
            if(livingEntity instanceof Arterius arterius) {
                arterius.resetBossStatus(true);
                arterius.backToHomePos();
                arterius.setInBattle(false);
                event.setCanceled(true);
            }

            if(livingEntity instanceof WraithonEntity wraithonEntity && !wraithonEntity.isDead()) {
                serverLevel.players().forEach(serverPlayer -> {
//                    serverPlayer.displayClientMessage(TCRCoreMod.getInfo("wraithon_end_tip"), false);
                    TCRCapabilityProvider.getTCRPlayer(serverPlayer).setTickAfterBossDieLeft(200);
                });
            }

            if(livingEntity instanceof IronGolem ironGolem && WorldUtil.isInStructure(livingEntity, WorldUtil.SKY_ISLAND) && !livingEntity.getPersistentData().getBoolean("already_respawn")) {
                //秽土转生
                EntityRespawnerMod.addToRespawn(ironGolem, 60, true);
                ItemUtil.addItemEntity(livingEntity, SGItems.GOLEM_HEART.get(), 1, ChatFormatting.GOLD.getColor().intValue());
                livingEntity.getPersistentData().putBoolean("already_respawn", true);
            }

            if(livingEntity instanceof Bone_Chimera_Entity boneChimeraEntity && WorldUtil.isInStructure(livingEntity, WorldUtil.SAND) && !livingEntity.getPersistentData().getBoolean("already_respawn")) {
                //偷懒，直接秽土转生
                EntityRespawnerMod.addToRespawn(boneChimeraEntity, 200, true);
                livingEntity.getPersistentData().putBoolean("already_respawn", true);
            }

            if(livingEntity instanceof BulldrogiothEntity bulldrogiothEntity && WorldUtil.isInStructure(livingEntity, WorldUtil.COVES) && !livingEntity.getPersistentData().getBoolean("already_respawn")) {
                //秽土转生
                EntityRespawnerMod.addToRespawn(bulldrogiothEntity, 300, true);
                livingEntity.getPersistentData().putBoolean("already_respawn", true);
            }

//            if(livingEntity instanceof UnderworldKnightEntity underworldKnight && WorldUtil.isInStructure(livingEntity, WorldUtil.FIRE) && !livingEntity.getPersistentData().getBoolean("already_respawn")) {
//                //偷懒，直接秽土转生
//                EntityRespawnerMod.addToRespawn(underworldKnight, 100, true);
//                livingEntity.getPersistentData().putBoolean("already_respawn", true);
//            }

            if (livingEntity instanceof Maledictus_Entity) {
                ItemUtil.addItemEntity(livingEntity, TCRItems.DUAL_BOKKEN.get(), 1, ChatFormatting.LIGHT_PURPLE.getColor());
            }

        }

        if(livingEntity instanceof CaptainCornelia) {
            if(livingEntity.level().isClientSide) {
                CorneliaMusicPlayer.stopBossMusic(livingEntity);
            } else {
                ItemUtil.addItemEntity(livingEntity, EFNItem.DEEPDARK_HEART.get(), 1, ChatFormatting.LIGHT_PURPLE.getColor().intValue());
            }
        }

        if(livingEntity instanceof WraithonEntity wraithonEntity && !wraithonEntity.isDead()) {
            if(livingEntity.level().isClientSide) {
                WraithonMusicPlayer.stopBossMusic(livingEntity);
            } else {
                ItemUtil.addItemEntity(livingEntity, SwordSoaringItems.VATANSEVER.get(), 1, ChatFormatting.LIGHT_PURPLE.getColor().intValue());
                ServerLevel wraithonLevel = wraithonEntity.getServer().getLevel(WraithonDimensions.SANCTUM_OF_THE_WRAITHON_LEVEL_KEY);
                TCRDimSaveData.get(wraithonLevel).setBossSummoned(false);
            }
        }

        if(livingEntity instanceof ServerPlayer serverPlayer && !event.isCanceled()) {
            serverPlayer.displayClientMessage(TCRCoreMod.getInfo("death_info"), false);
            ServerLevel wraithonLevel = serverPlayer.server.getLevel(WraithonDimensions.SANCTUM_OF_THE_WRAITHON_LEVEL_KEY);
            if(wraithonLevel.players().isEmpty()) {
                wraithonLevel.getAllEntities().forEach(Entity::discard);
                TCRDimSaveData.get(wraithonLevel).setBossSummoned(false);
            }
            //多人则重置出生点
            if(!serverPlayer.server.isSingleplayer()) {
                serverPlayer.setRespawnPosition(Level.OVERWORLD, new BlockPos(WorldUtil.START_POS), 0, true, false);
            }

            if(EntityUtil.getNearByPlayers(serverPlayer, 30).isEmpty()) {
                if(event.getSource().getEntity() instanceof LivingEntity living) {
                    living.setHealth(living.getMaxHealth());
                    living.removeAllEffects();
                    if(living instanceof WraithonEntity wraithonEntity) {
                        wraithonEntity.setPhase(WraithonEntity.START_PHASE);
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void onLivingHurt(LivingHurtEvent event) {
        if(TCRCoreMod.hasCheatMod()) {
            event.getEntity().setHealth(0);
        }
    }

    @SubscribeEvent
    public static void onLivingDialog(ServerNpcEntityInteractEvent event) {
        if(event.getSelf() instanceof Villager) {
            TCRCapabilityProvider.getTCRPlayer(event.getServerPlayer()).setCurrentTalkingEntity(null);
        }
    }

    /**
     * 减少呼吸消耗
     */
    @SubscribeEvent
    public static void onLivingBreath(LivingBreatheEvent event){
        LivingEntity living = event.getEntity();
        if(living instanceof Player player) {
            if(EpicFightCapabilities.getEntityPatch(player, PlayerPatch.class).getSkillCapability().isEquipping(TCRSkills.WATER_AVOID)) {
                event.setCanBreathe(true);
                event.setCanRefillAir(true);
                event.setConsumeAirAmount(0);
                event.setRefillAirAmount(5);
            }
        }
    }

    /**
     * 出生地防刷怪
     */
    @SubscribeEvent
    public static void onLivingSpawn(MobSpawnEvent.PositionCheck event){
        if(WorldUtil.inMainLand(event.getEntity()) && event.getEntity() instanceof Enemy) {
            event.setResult(Event.Result.DENY);
        }
    }

    public static Set<EntityType<?>> illegalEntityTypes = new HashSet<>();

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onMobSpawn(MobSpawnEvent.FinalizeSpawn event){
        if(AquamiraeUtils.isInIceMaze(event.getEntity())) {
            if(event.getEntity() instanceof Pillager pillager && event.getEntity().getRandom().nextFloat() < 0.2) {
                pillager.getPersistentData().putString("DeathLootTable", Aquamirae.MODID + ":entities/maze_captain");
                pillager.setGlowingTag(true);
            }
            if(event.getEntity().getLootTable().toString().endsWith("captain")) {
                event.getEntity().setGlowingTag(true);
            }
        }
    }

    @SubscribeEvent
    public static void onLivingJoin(EntityJoinLevelEvent event){

        if(event.getEntity().level().isClientSide) {
            return;
        }

        if(event.getEntity() instanceof Arterius arterius) {
            arterius.setInBattle(false);
        }

        ServerLevel serverLevel = (ServerLevel) event.getEntity().level();

        if(event.getEntity() instanceof BulldrogiothEntity bulldrogiothEntity) {
            if(WorldUtil.isInStructure(bulldrogiothEntity, WorldUtil.COVES)) {
                bulldrogiothEntity.setGlowingTag(true);
            }
        }

        if(event.getEntity() instanceof ItemEntity itemEntity) {
            if(PlayerEventListeners.illegalItems.contains(itemEntity.getItem().getItem())){
                event.setCanceled(true);
                return;
            }
        }

        if(illegalEntityTypes.contains(event.getEntity().getType())) {
            event.setCanceled(true);
            return;
        }
        UUID uuid = UUID.fromString("d4c3b2a1-f6e5-8b7a-0d9c-cba987654321");
        if(event.getEntity() instanceof IronGolem ironGolem) {
            if(WorldUtil.isInStructure(ironGolem, WorldUtil.SKY_ISLAND)){
                ironGolem.setCustomName(TCRCoreMod.getInfo("iron_golem_name"));
                ironGolem.setCustomNameVisible(true);
                ironGolem.setGlowingTag(true);
            }
        }

        if(event.getEntity() instanceof EnderDragon enderDragon) {
            enderDragon.getAttribute(Attributes.MAX_HEALTH).removeModifier(uuid);
            AttributeModifier healthBoost = new AttributeModifier(uuid, "Dragon Health Boost", 2, AttributeModifier.Operation.MULTIPLY_BASE);
            enderDragon.getAttribute(Attributes.MAX_HEALTH).addPermanentModifier(healthBoost);
            enderDragon.setHealth(enderDragon.getMaxHealth());
        }

        if(event.getEntity() instanceof WitherBoss witherBoss) {
            witherBoss.getAttribute(Attributes.MAX_HEALTH).removeModifier(uuid);
            AttributeModifier healthBoost = new AttributeModifier(uuid, "Wither Health Boost", 1, AttributeModifier.Operation.MULTIPLY_BASE);
            witherBoss.getAttribute(Attributes.MAX_HEALTH).addPermanentModifier(healthBoost);
            witherBoss.setHealth(witherBoss.getMaxHealth());
        }

        if(event.getEntity() instanceof LivingEntity livingEntity
                && !(livingEntity instanceof Player)
                && !(livingEntity instanceof WraithonEntity)) {
            ServerLevel end = serverLevel.getServer().getLevel(Level.END);
            if(end != null && end.getDragonFight() != null && end.getDragonFight().hasPreviouslyKilledDragon()) {
                livingEntity.getAttribute(Attributes.MAX_HEALTH).removeModifier(uuid);
                AttributeModifier healthBoost = new AttributeModifier(uuid, "Health Boost After Dragon Killed", 1, AttributeModifier.Operation.MULTIPLY_BASE);
                livingEntity.getAttribute(Attributes.MAX_HEALTH).addPermanentModifier(healthBoost);
                livingEntity.setHealth(livingEntity.getMaxHealth());
            }
        }
    }

    /**
     * 保险
     */
    @SubscribeEvent
    public static void onLivingDespawn(MobSpawnEvent.AllowDespawn event){
        if(event.getEntity() instanceof BulldrogiothEntity bulldrogiothEntity && WorldUtil.isInStructure(bulldrogiothEntity, WorldUtil.COVES)) {
            event.setResult(Event.Result.DENY);
        }
    }

}
