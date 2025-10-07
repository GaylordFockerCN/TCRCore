package com.p1nero.tcrcore.events;

import com.github.L_Ender.cataclysm.entity.AnimationMonster.BossMonsters.Ignis_Entity;
import com.github.L_Ender.cataclysm.entity.AnimationMonster.BossMonsters.The_Leviathan.The_Leviathan_Entity;
import com.github.L_Ender.cataclysm.entity.InternalAnimationMonster.IABossMonsters.Ancient_Remnant.Ancient_Remnant_Entity;
import com.github.L_Ender.cataclysm.entity.InternalAnimationMonster.IABossMonsters.Maledictus.Maledictus_Entity;
import com.github.L_Ender.cataclysm.entity.InternalAnimationMonster.IABossMonsters.Scylla.Scylla_Entity;
import com.github.L_Ender.cataclysm.init.ModItems;
import com.github.dodo.dodosmobs.entity.InternalAnimationMonster.IABossMonsters.Bone_Chimera_Entity;
import com.github.dodo.dodosmobs.init.ModEntities;
import com.hm.efn.registries.EFNItem;
import com.merlin204.sg.item.SGItems;
import com.obscuria.aquamirae.common.entities.CaptainCornelia;
import com.p1nero.cataclysm_dimension.worldgen.CataclysmDimensions;
import com.p1nero.dialog_lib.events.ServerNpcEntityInteractEvent;
import com.p1nero.fast_tpa.network.PacketRelay;
import com.p1nero.tcrcore.TCRCoreMod;
import com.p1nero.tcrcore.capability.PlayerDataManager;
import com.p1nero.tcrcore.capability.TCRCapabilityProvider;
import com.p1nero.tcrcore.client.sound.CorneliaMusicPlayer;
import com.p1nero.tcrcore.client.sound.WraithonMusicPlayer;
import com.p1nero.tcrcore.gameassets.TCRSkills;
import com.p1nero.tcrcore.item.TCRItems;
import com.p1nero.tcrcore.mixin.UnderworldKnightEntityMixin;
import com.p1nero.tcrcore.network.TCRPacketHandler;
import com.p1nero.tcrcore.network.packet.clientbound.PlayItemPickupParticlePacket;
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
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.AbstractIllager;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.monster.Pillager;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.Tags;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.p1nero.ss.item.SwordSoaringItems;
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
import yesman.epicfight.world.item.EpicFightItems;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Mod.EventBusSubscriber(modid = TCRCoreMod.MOD_ID)
public class LivingEntityEventListeners {

    @SubscribeEvent
    public static void onLivingAttack(LivingAttackEvent event){
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

        //防止摔死
        if(event.getEntity() instanceof BulldrogiothEntity && event.getSource().is(DamageTypes.FALL)) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void onEntityDrop(LivingDropsEvent event) {
        LivingEntity entity = event.getEntity();
        if(!entity.level().isClientSide) {

            if (entity instanceof Scylla_Entity) {
                ItemUtil.addItemEntity(entity, ModItems.CERAUNUS.get(), 1, 0xfff66d);
            }

            if (entity instanceof Ignis_Entity) {
                event.getDrops().add(ItemUtil.addItemEntity(entity, ModItems.THE_INCINERATOR.get(), 1, 0xfff66d));
            }
            if (entity instanceof The_Leviathan_Entity) {
                ItemUtil.addItemEntity(entity, ModItems.TIDAL_CLAWS.get(), 1, 0xfff66d);
            }
            if (entity instanceof Maledictus_Entity) {
                ItemUtil.addItemEntity(entity, ModItems.SOUL_RENDER.get(), 1, 0xfff66d);
            }
            if (entity instanceof Ancient_Remnant_Entity) {
                ItemUtil.addItemEntity(entity, ModItems.WRATH_OF_THE_DESERT.get(), 1, 0xfff66d);
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

    public static void giveOracle(ServerPlayer player) {
        ItemUtil.addItemEntity(player, TCRItems.ANCIENT_ORACLE_FRAGMENT.get(), 1, ChatFormatting.LIGHT_PURPLE.getColor().intValue());
        PacketRelay.sendToPlayer(TCRPacketHandler.INSTANCE, new PlayItemPickupParticlePacket(TCRItems.ANCIENT_ORACLE_FRAGMENT.get().getDefaultInstance()), player);
        player.connection.send(new ClientboundSoundPacket(BuiltInRegistries.SOUND_EVENT.wrapAsHolder(SoundEvents.TOTEM_USE), SoundSource.PLAYERS, player.getX(), player.getY(), player.getZ(), 1.0F, 1.0F, player.getRandom().nextInt()));
        player.connection.send(new ClientboundSoundPacket(BuiltInRegistries.SOUND_EVENT.wrapAsHolder(SoundEvents.UI_TOAST_CHALLENGE_COMPLETE), SoundSource.PLAYERS, player.getX(), player.getY(), player.getZ(), 1.0F, 1.0F, player.getRandom().nextInt()));
    }

    @SubscribeEvent
    public static void onLivingDeath(LivingDeathEvent event){
        LivingEntity livingEntity = event.getEntity();

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

            if(livingEntity instanceof AbstractIllager && !PlayerDataManager.pillagerKilled.get(player)) {
                    LivingEntityEventListeners.giveOracle(player);
                    PlayerDataManager.pillagerKilled.put(player, true);
            }

            if(livingEntity instanceof IronGolem ironGolem && WorldUtil.isInStructure(livingEntity, WorldUtil.SKY_ISLAND)) {
                if(!PlayerDataManager.stormEyeTraded.get(player)) {
                    ItemUtil.addItemEntity(player, ModItems.STORM_EYE.get(), 1, ChatFormatting.AQUA.getColor().intValue());
                    player.displayClientMessage(TCRCoreMod.getInfo("kill_boss1"), false);
                    player.displayClientMessage(TCRCoreMod.getInfo("time_to_altar"), true);
                    giveOracle(player);
                    player.connection.send(new ClientboundSetTitleTextPacket(TCRCoreMod.getInfo("you_pass")));
                    player.connection.send(new ClientboundSoundPacket(BuiltInRegistries.SOUND_EVENT.wrapAsHolder(SoundEvents.UI_TOAST_CHALLENGE_COMPLETE), SoundSource.PLAYERS, player.getX(), player.getY(), player.getZ(), 1.0F, 1.0F, player.getRandom().nextInt()));

                    PlayerDataManager.stormEyeTraded.put(player, true);
                }
            }

            if(livingEntity instanceof BulldrogiothEntity
                    && PlayerDataManager.stormEyeTraded.get(player)
                    && !PlayerDataManager.abyssEyeTraded.get(player)) {
                ItemUtil.addItemEntity(player, ModItems.ABYSS_EYE.get(), 1, ChatFormatting.BLUE.getColor().intValue());
                player.displayClientMessage(TCRCoreMod.getInfo("kill_boss3"), false);
                player.displayClientMessage(TCRCoreMod.getInfo("time_to_altar"), true);
                giveOracle(player);
                PlayerDataManager.abyssEyeTraded.put(player, true);
            }

            if(livingEntity instanceof UnderworldKnightEntity && !PlayerDataManager.flameEyeTraded.get(player)) {
                ItemUtil.addItemEntity(player, ModItems.FLAME_EYE.get(), 1, ChatFormatting.RED.getColor().intValue());
                player.displayClientMessage(TCRCoreMod.getInfo("kill_boss2"), false);
                player.displayClientMessage(TCRCoreMod.getInfo("time_to_altar"), true);
                PlayerDataManager.flameEyeTraded.put(player, true);
            }

            if(livingEntity instanceof CaptainCornelia && !PlayerDataManager.cursedEyeTraded.get(player)) {
                ItemUtil.addItemEntity(player, ModItems.CURSED_EYE.get(), 1, ChatFormatting.DARK_GREEN.getColor().intValue());
                player.displayClientMessage(TCRCoreMod.getInfo("kill_boss4"), false);
                player.displayClientMessage(TCRCoreMod.getInfo("time_to_altar"), true);
                giveOracle(player);
                PlayerDataManager.cursedEyeTraded.put(player, true);
            }
            if(livingEntity instanceof Bone_Chimera_Entity && !PlayerDataManager.desertEyeTraded.get(player)) {
                ItemUtil.addItemEntity(player, ModItems.DESERT_EYE.get(), 1, ChatFormatting.YELLOW.getColor().intValue());
                player.displayClientMessage(TCRCoreMod.getInfo("kill_boss5"), false);
                player.displayClientMessage(TCRCoreMod.getInfo("time_to_altar"), true);
                giveOracle(player);
                PlayerDataManager.desertEyeTraded.put(player, true);

                CommandSourceStack commandSourceStack = player.createCommandSourceStack().withPermission(2).withSuppressedOutput();
                if(!PlayerDataManager.fireAvoidUnlocked.get(player)) {
                    Objects.requireNonNull(player.getServer()).getCommands().performPrefixedCommand(commandSourceStack, "/skilltree unlock @s dodge_parry_reward:passive tcrcore:fire_avoid true");
                    player.displayClientMessage(TCRCoreMod.getInfo("unlock_new_skill", Component.translatable(TCRSkills.FIRE_AVOID.getTranslationKey()).withStyle(ChatFormatting.AQUA)), false);
                    player.level().playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.UI_TOAST_CHALLENGE_COMPLETE, SoundSource.PLAYERS, 1.0F, 1.0F);
                    PlayerDataManager.fireAvoidUnlocked.put(player, true);
                }
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

            if(livingEntity instanceof WraithonEntity wraithonEntity && !wraithonEntity.isDead()) {
                serverLevel.players().forEach(serverPlayer -> {
//                    serverPlayer.displayClientMessage(TCRCoreMod.getInfo("wraithon_end_tip"), false);
                    TCRCapabilityProvider.getTCRPlayer(serverPlayer).setTickAfterBossDieLeft(200);
                });
            }

            if(livingEntity instanceof IronGolem ironGolem && WorldUtil.isInStructure(livingEntity, WorldUtil.SKY_ISLAND)) {
                //秽土转生
                EntityType.IRON_GOLEM.spawn(serverLevel, ironGolem.getOnPos().above(), MobSpawnType.MOB_SUMMONED);
                ItemUtil.addItemEntity(livingEntity, SGItems.GOLEM_HEART.get(), 1, ChatFormatting.GOLD.getColor().intValue());
            }

            if(livingEntity instanceof Bone_Chimera_Entity boneChimeraEntity && WorldUtil.isInStructure(livingEntity, WorldUtil.SAND)) {
                //偷懒，直接秽土转生
                ModEntities.BONE_CHIMERA.get().spawn(serverLevel, boneChimeraEntity.getOnPos().above(), MobSpawnType.MOB_SUMMONED);
            }

            if(livingEntity instanceof UnderworldKnightEntity underworldKnight && WorldUtil.isInStructure(livingEntity, WorldUtil.FIRE)) {
                //偷懒，直接秽土转生
                BlockFactorysBossesModEntities.UNDERWORLD_KNIGHT.get().spawn(serverLevel, underworldKnight.getOnPos().above(), MobSpawnType.MOB_SUMMONED);
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

    @SubscribeEvent
    public static void onLivingJoin(EntityJoinLevelEvent event){
        if(event.getEntity().level().isClientSide) {
            return;
        }

        if(event.getEntity() instanceof ItemEntity itemEntity) {
            if(itemEntity.getItem().is(net.p3pp3rf1y.sophisticatedbackpacks.init.ModItems.FEEDING_UPGRADE.get())
                || itemEntity.getItem().is(net.p3pp3rf1y.sophisticatedbackpacks.init.ModItems.ADVANCED_FEEDING_UPGRADE.get())) {
                event.setCanceled(true);
                return;
            }
        }

        if(event.getEntity() instanceof Skeleton skeleton) {
            if(skeleton.getMainHandItem().is(Items.BOW) && skeleton.getRandom().nextFloat() < 0.8) {
                skeleton.setItemInHand(InteractionHand.MAIN_HAND, EpicFightItems.IRON_GREATSWORD.get().getDefaultInstance());
            }
        }

        if(event.getEntity() instanceof BulldrogiothEntity bulldrogiothEntity) {
            if(!EntityUtil.getNearByEntities(event.getLevel(), bulldrogiothEntity.position(), 50, BulldrogiothEntity.class).isEmpty()) {
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
            AttributeModifier healthBoost = new AttributeModifier(uuid, "Dragon Health Boost", 4, AttributeModifier.Operation.MULTIPLY_BASE);
            enderDragon.getAttribute(Attributes.MAX_HEALTH).addPermanentModifier(healthBoost);
            enderDragon.setHealth(enderDragon.getMaxHealth());
        }

        if(event.getEntity() instanceof WitherBoss witherBoss) {
            witherBoss.getAttribute(Attributes.MAX_HEALTH).removeModifier(uuid);
            AttributeModifier healthBoost = new AttributeModifier(uuid, "Wither Health Boost", 1, AttributeModifier.Operation.MULTIPLY_BASE);
            witherBoss.getAttribute(Attributes.MAX_HEALTH).addPermanentModifier(healthBoost);
            witherBoss.setHealth(witherBoss.getMaxHealth());
        }

        if(event.getEntity().level() instanceof ServerLevel serverLevel && event.getEntity() instanceof LivingEntity livingEntity
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

//    @SubscribeEvent
//    public static void onPlayerPickupItem(EntityItemPickupEvent event) {
//
//    }
}
