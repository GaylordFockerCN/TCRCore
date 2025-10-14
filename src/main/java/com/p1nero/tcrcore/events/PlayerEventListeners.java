package com.p1nero.tcrcore.events;

import com.p1nero.cataclysm_dimension.worldgen.CataclysmDimensions;
import com.p1nero.fast_tpa.network.PacketRelay;
import com.p1nero.tcrcore.TCRCoreMod;
import com.p1nero.tcrcore.capability.PlayerDataManager;
import com.p1nero.tcrcore.capability.TCRCapabilityProvider;
import com.p1nero.tcrcore.capability.TCRPlayer;
import com.p1nero.tcrcore.datagen.TCRAdvancementData;
import com.p1nero.tcrcore.effect.TCREffects;
import com.p1nero.tcrcore.item.TCRItems;
import com.p1nero.tcrcore.network.TCRPacketHandler;
import com.p1nero.tcrcore.network.packet.clientbound.CSTipPacket;
import com.p1nero.tcrcore.network.packet.clientbound.PlayItemPickupParticlePacket;
import com.p1nero.tcrcore.network.packet.clientbound.PlayTitlePacket;
import com.p1nero.tcrcore.save_data.TCRDimSaveData;
import com.p1nero.tcrcore.save_data.TCRMainLevelSaveData;
import com.p1nero.tcrcore.utils.EntityUtil;
import com.p1nero.tcrcore.utils.ItemUtil;
import com.p1nero.tcrcore.utils.WorldUtil;
import com.p1nero.tudigong.entity.XianQiEntity;
import com.p1nero.tudigong.item.TDGItems;
import com.yesman.epicskills.registry.entry.EpicSkillsItems;
import com.yesman.epicskills.skilltree.SkillTree;
import com.yesman.epicskills.world.capability.SkillTreeProgression;
import net.blay09.mods.waystones.block.ModBlocks;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.protocol.game.ClientboundSoundPacket;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityTravelToDimensionEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.AdvancementEvent;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.p1nero.ss.SwordSoaringMod;
import net.p1nero.ss.gameassets.skills.SwordControllerSkills;
import net.p3pp3rf1y.sophisticatedbackpacks.init.ModItems;
import net.sonmok14.fromtheshadows.server.entity.mob.BulldrogiothEntity;
import net.sonmok14.fromtheshadows.server.utils.registry.EntityRegistry;
import org.merlin204.wraithon.worldgen.WraithonDimensions;
import top.theillusivec4.curios.api.event.CurioEquipEvent;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Mod.EventBusSubscriber(modid = TCRCoreMod.MOD_ID)
public class PlayerEventListeners {

    @SubscribeEvent
    public static void onPlayerAdvancementEarn(AdvancementEvent.AdvancementEarnEvent event) {
        if (event.getEntity() instanceof ServerPlayer serverPlayer) {
            String path = event.getAdvancement().getId().getPath();
            String namespace = event.getAdvancement().getId().getNamespace();
            if (namespace.equals(TCRCoreMod.MOD_ID)) {
                if (path.equals("vatansever")) {
                    serverPlayer.getCapability(SkillTreeProgression.SKILL_TREE_PROGRESSION).ifPresent(skillTreeProgression -> {
                        ResourceKey<SkillTree> resourceKey = ResourceKey.create(SkillTree.SKILL_TREE_REGISTRY_KEY, ResourceLocation.fromNamespaceAndPath(SwordSoaringMod.MOD_ID, "sword_soaring_skills"));
                        skillTreeProgression.unlockTree(resourceKey, serverPlayer);
                        skillTreeProgression.unlockNode(resourceKey, SwordControllerSkills.RAIN_SWORD, serverPlayer);
                        skillTreeProgression.unlockNode(resourceKey, SwordControllerSkills.SCREEN_SWORD, serverPlayer);
                        skillTreeProgression.unlockNode(resourceKey, SwordControllerSkills.KILL_AURA_1, serverPlayer);
                        skillTreeProgression.unlockNode(resourceKey, SwordControllerSkills.KILL_AURA_2, serverPlayer);
                    });

                    serverPlayer.displayClientMessage(TCRCoreMod.getInfo("unlock_new_skill", SwordControllerSkills.RAIN_SWORD.getDisplayName()), false);
                    serverPlayer.displayClientMessage(TCRCoreMod.getInfo("unlock_new_skill", SwordControllerSkills.SCREEN_SWORD.getDisplayName()), false);
                    serverPlayer.displayClientMessage(TCRCoreMod.getInfo("unlock_new_skill", SwordControllerSkills.KILL_AURA_1.getDisplayName()), false);
                    serverPlayer.displayClientMessage(TCRCoreMod.getInfo("unlock_new_skill", SwordControllerSkills.KILL_AURA_2.getDisplayName()), false);
                }
            }

            if (namespace.equals("minecraft") && path.equals("recipes/transportation/oak_boat")) {
                PacketRelay.sendToPlayer(TCRPacketHandler.INSTANCE, new PlayTitlePacket(4), serverPlayer);
            }

        }
    }

    @SubscribeEvent
    public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        Player player = event.getEntity();
        if (player instanceof ServerPlayer serverPlayer) {
            if (!PlayerDataManager.firstJoint.get(serverPlayer)) {
                TCRAdvancementData.finishAdvancement(TCRCoreMod.MOD_ID, serverPlayer);
                CommandSourceStack commandSourceStack = serverPlayer.createCommandSourceStack().withPermission(2).withSuppressedOutput();
                Objects.requireNonNull(serverPlayer.getServer()).getCommands().performPrefixedCommand(commandSourceStack, "/gamerule keepInventory true");
                Objects.requireNonNull(serverPlayer.getServer()).getCommands().performPrefixedCommand(commandSourceStack, "/gamerule mobGriefing false");
                Objects.requireNonNull(serverPlayer.getServer()).getCommands().performPrefixedCommand(commandSourceStack, "/skilltree unlock @s epicskills:battleborn efn:efn_step true");
                Objects.requireNonNull(serverPlayer.getServer()).getCommands().performPrefixedCommand(commandSourceStack, "/skilltree unlock @s epicskills:battleborn efn:efn_dodge true");
                Objects.requireNonNull(serverPlayer.getServer()).getCommands().performPrefixedCommand(commandSourceStack, "/skilltree unlock @s dodge_parry_reward:passive dodge_parry_reward:stamina1 true");
                Objects.requireNonNull(serverPlayer.getServer()).getCommands().performPrefixedCommand(commandSourceStack, "/epicfight skill add @s dodge efn:efn_dodge");
                Objects.requireNonNull(serverPlayer.getServer()).getCommands().performPrefixedCommand(commandSourceStack, "/epicfight skill add @s guard epicfight:parrying");
                Objects.requireNonNull(serverPlayer.getServer()).getCommands().performPrefixedCommand(commandSourceStack, "/epicfight skill add @s passive1 dodge_parry_reward:stamina1");
                ItemUtil.addItem(serverPlayer, Items.IRON_SWORD, 1);
                ItemUtil.addItem(serverPlayer, ModItems.BACKPACK.get(), 1);
                ItemUtil.addItem(serverPlayer, Items.BREAD, 32);
                ItemUtil.addItem(serverPlayer, TDGItems.TUDI_COMMAND_SPELL.get(), 1);
                ItemUtil.addItem(serverPlayer, EpicSkillsItems.ABILIITY_STONE.get(), 1);
                //引导玩家去守望者处
                XianQiEntity xianQiEntity = new XianQiEntity(serverPlayer.serverLevel(), WorldUtil.GUIDER_POS, serverPlayer, null);
                serverPlayer.displayClientMessage(TCRCoreMod.getInfo("cloud_follow_me"), false);
                serverPlayer.serverLevel().addFreshEntity(xianQiEntity);
                PlayerDataManager.firstJoint.put(serverPlayer, true);
            }

            if (TCRCoreMod.hasCheatMod()) {
                serverPlayer.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 9999, 9999));
                serverPlayer.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 9999, 9999));
                serverPlayer.addEffect(new MobEffectInstance(MobEffects.LUCK, 9999, 9999));
            }

            TCRCapabilityProvider.syncPlayerDataToClient(serverPlayer);

            PacketRelay.sendToPlayer(TCRPacketHandler.INSTANCE, new CSTipPacket(), serverPlayer);
        }
    }

    @SubscribeEvent
    public static void onPlayerInteractBlock(PlayerInteractEvent.RightClickBlock event) {

        if (event.getEntity() instanceof ServerPlayer serverPlayer) {

            BlockState blockState = event.getLevel().getBlockState(event.getPos());
            //第一次交互给传送石和提示
            if (blockState.is(ModBlocks.waystone)) {
                if (!PlayerDataManager.wayStoneInteracted.get(serverPlayer)) {
                    serverPlayer.displayClientMessage(TCRCoreMod.getInfo("press_to_open_portal_screen"), true);
                    ItemUtil.addItem(serverPlayer, net.blay09.mods.waystones.item.ModItems.warpStone, 1, true);
                    PlayerDataManager.wayStoneInteracted.put(serverPlayer, true);
                }
            }

            if (blockState.is(com.github.L_Ender.cataclysm.init.ModBlocks.GODDESS_STATUE.get())) {
                TCRPlayer tcrPlayer = TCRCapabilityProvider.getTCRPlayer(serverPlayer);
                ServerLevel serverLevel = serverPlayer.serverLevel();
                BlockPos blessPos = event.getPos();
                serverLevel.playSound(null, blessPos, SoundEvents.BEACON_AMBIENT,
                        SoundSource.AMBIENT, 0.7F, 0.5F + serverLevel.random.nextFloat() * 0.3F);

                tcrPlayer.setTickAfterBless(100);
                tcrPlayer.setBlessPos(event.getPos());
            }

            //击败boss前禁止交互
            if (CataclysmDimensions.LEVELS.contains(serverPlayer.serverLevel().dimension())) {
                boolean isChest = event.getLevel().getBlockState(event.getPos()).is(Blocks.CHEST) || event.getLevel().getBlockState(event.getPos()).is(noobanidus.mods.lootr.init.ModBlocks.CHEST.get());
                if (isChest && !TCRDimSaveData.get(serverPlayer.serverLevel()).isBossKilled()) {
                    serverPlayer.displayClientMessage(TCRCoreMod.getInfo("dim_block_no_interact"), true);
                    event.setCanceled(true);
                }
            }
        }

    }

    public static final Set<Item> illegalItems = new HashSet<>();

    @SubscribeEvent
    public static void onCurioEquip(CurioEquipEvent event) {
        if (illegalItems.contains(event.getStack().getItem())) {
            event.setResult(Event.Result.DENY);
            if (event.getEntity() instanceof Player player) {
                player.displayClientMessage(TCRCoreMod.getInfo("illegal_item_tip"), true);
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase == TickEvent.Phase.START) {

            if (event.player.isLocalPlayer() && WorldUtil.inMainLand(event.player)) {
                if (isNearBarrier(event.player)) {
                    event.player.displayClientMessage(TCRCoreMod.getInfo("hit_barrier"), true);
                }
            }
            if (event.player instanceof ServerPlayer serverPlayer) {
                ItemStack mainHandItem = serverPlayer.getMainHandItem();
                if (illegalItems.contains(mainHandItem.getItem())) {
                    event.player.drop(mainHandItem.copy(), true);
                    mainHandItem.shrink(1);
                    event.player.displayClientMessage(TCRCoreMod.getInfo("illegal_item_tip"), true);
                }

                if (!serverPlayer.isInvulnerable()) {
                    if (serverPlayer.hasEffect(TCREffects.INVULNERABLE.get())) {
                        serverPlayer.setInvulnerable(true);
                    }
                } else {
                    if (!serverPlayer.hasEffect(TCREffects.INVULNERABLE.get()) && !serverPlayer.isCreative() && !serverPlayer.isSpectator()) {
                        serverPlayer.setInvulnerable(false);
                    }
                }
                if (!serverPlayer.serverLevel().isLoaded(serverPlayer.getOnPos())) {
                    return;
                }
                if (PlayerDataManager.stormEyeTraded.get(event.player) && serverPlayer.tickCount % 200 == 0 && WorldUtil.isInStructure(event.player, WorldUtil.COVES)) {
                    //定点生
                    BlockPos pos = TCRMainLevelSaveData.get(serverPlayer.serverLevel()).getAbyssPos();
                    if (!serverPlayer.serverLevel().isLoaded(pos)) {
                        return;
                    }
                    if (pos.equals(BlockPos.ZERO)) {
                        Vec3 targetPos = event.player.position().add(event.player.getViewVector(1.0F).scale(10));
                        pos = new BlockPos((int) targetPos.x, (int) (event.player.getY() + 5), (int) targetPos.z);
                    }
                    //保险措施
                    if (EntityUtil.getNearByEntities(serverPlayer.serverLevel(), pos.getCenter(), 150, BulldrogiothEntity.class).isEmpty()) {
                        BulldrogiothEntity entity = EntityRegistry.BULLDROGIOTH.get().spawn(serverPlayer.serverLevel(), pos, MobSpawnType.SPAWNER);
                        entity.setGlowingTag(true);
                    }
                }
                if (WorldUtil.inMainLand(serverPlayer) && serverPlayer.isSprinting()) {
                    serverPlayer.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 10, 2, false, false, true));
                }
            }

        }

    }

    public static boolean isNearBarrier(Player player) {
        if (player.noPhysics) {
            return false;
        } else {
            float f = player.getDimensions(player.getPose()).width * 1.5F;
            AABB aabb = AABB.ofSize(player.getEyePosition(), f, 1.0E-6D, f);
            return BlockPos.betweenClosedStream(aabb).anyMatch((p_201942_) -> {
                BlockState blockstate = player.level().getBlockState(p_201942_);
                return blockstate.is(Blocks.BARRIER);
            });
        }
    }

    @SubscribeEvent
    public static void onPlayerRespawn(PlayerEvent.PlayerRespawnEvent event) {
        Player player = event.getEntity();
        if (player instanceof ServerPlayer serverPlayer) {
            TCRCapabilityProvider.syncPlayerDataToClient(serverPlayer);
            player.setHealth(player.getMaxHealth());
            EpicFightCapabilities.getUnparameterizedEntityPatch(player, ServerPlayerPatch.class).ifPresent(serverPlayerPatch -> {
                serverPlayerPatch.setStamina(serverPlayerPatch.getMaxStamina());
            });
        }
    }

    @SubscribeEvent
    public static void onPlayerEnterDim(EntityTravelToDimensionEvent event) {

        if (event.getEntity() instanceof ServerPlayer serverPlayer) {
            if (!serverPlayer.isCreative()) {
                if (event.getDimension() == Level.NETHER) {
                    if (!PlayerDataManager.canEnterNether.get(serverPlayer)) {
                        event.setCanceled(true);
                        serverPlayer.displayClientMessage(TCRCoreMod.getInfo("can_not_enter_dim"), true);
                    }
                }

                if (event.getDimension() == Level.END) {
                    if (!PlayerDataManager.canEnterEnd.get(serverPlayer)) {
                        event.setCanceled(true);
                        serverPlayer.displayClientMessage(TCRCoreMod.getInfo("can_not_enter_dim"), true);
                    }
                }
            }

            if (CataclysmDimensions.LEVELS.contains(event.getDimension())) {
                ServerLevel targetLevel = serverPlayer.server.getLevel(event.getDimension());
                if (targetLevel != null && targetLevel.players().size() >= 4) {
                    event.setCanceled(true);
                    serverPlayer.displayClientMessage(TCRCoreMod.getInfo("dim_max_4_players"), false);
                }
            }

            if ((event.getDimension() == CataclysmDimensions.CATACLYSM_SANCTUM_FALLEN_LEVEL_KEY && !PlayerDataManager.stormEyeTraded.get(serverPlayer))
                    || (event.getDimension() == CataclysmDimensions.CATACLYSM_INFERNOS_MAW_LEVEL_KEY && !PlayerDataManager.flameEyeTraded.get(serverPlayer))
                    || (event.getDimension() == CataclysmDimensions.CATACLYSM_ETERNAL_FROSTHOLD_LEVEL_KEY && !PlayerDataManager.cursedEyeTraded.get(serverPlayer))
                    || (event.getDimension() == CataclysmDimensions.CATACLYSM_PHARAOHS_BANE_LEVEL_KEY && !PlayerDataManager.desertEyeTraded.get(serverPlayer))
                    || (event.getDimension() == CataclysmDimensions.CATACLYSM_ABYSSAL_DEPTHS_LEVEL_KEY && !PlayerDataManager.abyssEyeTraded.get(serverPlayer))) {
                serverPlayer.displayClientMessage(TCRCoreMod.getInfo("can_not_enter_before_finish"), false);
                event.setCanceled(true);
            }
        }

    }

    @SubscribeEvent
    public static void onPlayerEnterDim(PlayerEvent.PlayerChangedDimensionEvent event) {
        if (event.getEntity() instanceof ServerPlayer serverPlayer) {
            if (event.getFrom() == WraithonDimensions.SANCTUM_OF_THE_WRAITHON_LEVEL_KEY) {
                ServerLevel wraithonLevel = serverPlayer.server.getLevel(WraithonDimensions.SANCTUM_OF_THE_WRAITHON_LEVEL_KEY);
                if (wraithonLevel.players().isEmpty()) {
                    wraithonLevel.getAllEntities().forEach(Entity::discard);
                    TCRDimSaveData.get(wraithonLevel).setBossSummoned(false);
                }
            }
            if (CataclysmDimensions.LEVELS.contains(event.getTo())) {
                serverPlayer.displayClientMessage(TCRCoreMod.getInfo("reset_when_no_player").withStyle(ChatFormatting.RED, ChatFormatting.BOLD), false);
                if (serverPlayer.serverLevel().players().size() <= 1) {
                    TCRDimSaveData.get(serverPlayer.getServer().getLevel(event.getTo())).setBossKilled(false);
                }
            }
            if (event.getTo() == Level.NETHER) {
                if (!PlayerDataManager.netherEntered.get(serverPlayer)) {
                    serverPlayer.displayClientMessage(TCRCoreMod.getInfo("unlock_new_dim_girl"), false);
                    PlayerDataManager.netherEntered.put(serverPlayer, true);
                }
            }
            if (event.getTo() == Level.END) {
                if (!PlayerDataManager.endEntered.get(serverPlayer)) {
                    serverPlayer.displayClientMessage(TCRCoreMod.getInfo("unlock_new_dim_girl"), false);
                    PlayerDataManager.endEntered.put(serverPlayer, true);
                }
            }
            updateHealth(serverPlayer, event.getFrom());
            updateHealth(serverPlayer, event.getTo());
        }
    }

    /**
     * 动态改变多人血量
     */
    public static void updateHealth(ServerPlayer serverPlayer, ResourceKey<Level> levelResourceKey) {
        if (CataclysmDimensions.LEVELS.contains(levelResourceKey)) {
            ServerLevel targetLevel = serverPlayer.server.getLevel(levelResourceKey);
            if (targetLevel != null) {
                int playerCnt = targetLevel.players().size();
                double healthMultiplier = 1.0;
                if (playerCnt == 2) {
                    healthMultiplier = 1.6;
                } else if (playerCnt == 3) {
                    healthMultiplier = 2.0;
                } else if (playerCnt >= 4) {
                    healthMultiplier = 2.4;
                }

                final double finalMultiplier = healthMultiplier;
                final UUID HEALTH_MODIFIER_UUID = UUID.fromString("11451419-1981-0234-1234-123456789abc");

                targetLevel.getAllEntities().forEach(entity -> {
                    if (entity instanceof LivingEntity living && living.isAlive() && !(living instanceof Player)) {
                        float preHealth = living.getHealth();
                        float preMaxHealth = living.getMaxHealth();
                        AttributeInstance maxHealthAttr = living.getAttribute(Attributes.MAX_HEALTH);
                        if (maxHealthAttr != null) {
                            maxHealthAttr.removeModifier(HEALTH_MODIFIER_UUID);
                            if (playerCnt > 1) {
                                AttributeModifier healthModifier = new AttributeModifier(
                                        HEALTH_MODIFIER_UUID,
                                        "team_health_boost",
                                        finalMultiplier - 1,
                                        AttributeModifier.Operation.MULTIPLY_TOTAL
                                );
                                maxHealthAttr.addPermanentModifier(healthModifier);
                                living.setHealth(preHealth * living.getMaxHealth() / preMaxHealth);
                            }
                        }
                    }
                });
            }
        }
    }

    /**
     * 未完成前置不能捡起
     */
    @SubscribeEvent
    public static void onPlayerPickupItem(EntityItemPickupEvent event) {
        if(event.getEntity() instanceof ServerPlayer player) {
            if(!PlayerDataManager.stormEyeBlessed.get(player) && event.getItem().getItem().is(com.github.L_Ender.cataclysm.init.ModItems.ABYSS_EYE.get())) {
                player.displayClientMessage(TCRCoreMod.getInfo("can_not_do_this_too_early"), true);
                event.setCanceled(true);
            }
            if(!PlayerDataManager.abyssEyeBlessed.get(player) && event.getItem().getItem().is(com.github.L_Ender.cataclysm.init.ModItems.DESERT_EYE.get())) {
                player.displayClientMessage(TCRCoreMod.getInfo("can_not_do_this_too_early"), true);
                event.setCanceled(true);
            }
            if(!PlayerDataManager.desertEyeBlessed.get(player) && event.getItem().getItem().is(com.github.L_Ender.cataclysm.init.ModItems.CURSED_EYE.get())) {
                player.displayClientMessage(TCRCoreMod.getInfo("can_not_do_this_too_early"), true);
                event.setCanceled(true);
            }
            if(!PlayerDataManager.cursedEyeBlessed.get(player) && event.getItem().getItem().is(com.github.L_Ender.cataclysm.init.ModItems.FLAME_EYE.get())) {
                player.displayClientMessage(TCRCoreMod.getInfo("can_not_do_this_too_early"), true);
                event.setCanceled(true);
            }
        }

    }

    /**
     * 捡起物品后才视为成功获取，才爆特效，而非用击杀判定
     * {@link LivingEntityEventListeners#onLivingDeath(LivingDeathEvent)}
     */
    @SubscribeEvent
    public static void onItemPickup(PlayerEvent.ItemPickupEvent event) {
        ItemStack itemStack = event.getStack();
        if (event.getEntity() instanceof ServerPlayer player) {
            if (itemStack.is(TCRItems.ANCIENT_ORACLE_FRAGMENT.get()) && itemStack.getOrCreateTag().getString(TCRPlayer.PLAYER_NAME).equals(player.getGameProfile().getName())) {
                giveOracleEffect(player, TCRItems.ANCIENT_ORACLE_FRAGMENT.get());
            }

            if (!PlayerDataManager.stormEyeTraded.get(player) && itemStack.is(com.github.L_Ender.cataclysm.init.ModItems.STORM_EYE.get())) {
                player.displayClientMessage(TCRCoreMod.getInfo("time_to_altar"), true);
                giveOracleEffect(player, com.github.L_Ender.cataclysm.init.ModItems.STORM_EYE.get());
                PlayerDataManager.stormEyeTraded.put(player, true);
            }

            if (!PlayerDataManager.abyssEyeTraded.get(player) && itemStack.is(com.github.L_Ender.cataclysm.init.ModItems.ABYSS_EYE.get())) {
                player.displayClientMessage(TCRCoreMod.getInfo("time_to_altar"), true);
                giveOracleEffect(player, com.github.L_Ender.cataclysm.init.ModItems.ABYSS_EYE.get());
                PlayerDataManager.abyssEyeTraded.put(player, true);
            }

            if (itemStack.is(com.github.L_Ender.cataclysm.init.ModItems.FLAME_EYE.get()) && !PlayerDataManager.flameEyeTraded.get(player)) {
                player.displayClientMessage(TCRCoreMod.getInfo("time_to_altar"), true);
                PlayerDataManager.flameEyeTraded.put(player, true);
            }

            if (itemStack.is(com.github.L_Ender.cataclysm.init.ModItems.CURSED_EYE.get()) && !PlayerDataManager.cursedEyeTraded.get(player)) {
                player.displayClientMessage(TCRCoreMod.getInfo("time_to_altar"), true);
                giveOracleEffect(player, com.github.L_Ender.cataclysm.init.ModItems.CURSED_EYE.get());
                PlayerDataManager.cursedEyeTraded.put(player, true);
            }

            if (itemStack.is(com.github.L_Ender.cataclysm.init.ModItems.DESERT_EYE.get()) && !PlayerDataManager.desertEyeTraded.get(player)) {
                player.displayClientMessage(TCRCoreMod.getInfo("time_to_altar"), true);
                giveOracleEffect(player, com.github.L_Ender.cataclysm.init.ModItems.DESERT_EYE.get());
                PlayerDataManager.desertEyeTraded.put(player, true);
            }

            if (itemStack.is(com.github.L_Ender.cataclysm.init.ModItems.VOID_EYE.get()) && !PlayerDataManager.voidEyeTraded.get(player)) {
                player.displayClientMessage(TCRCoreMod.getInfo("time_to_altar"), true);
                giveOracleEffect(player, com.github.L_Ender.cataclysm.init.ModItems.VOID_EYE.get());
                PlayerDataManager.voidEyeTraded.put(player, true);
            }

            if (itemStack.is(com.github.L_Ender.cataclysm.init.ModItems.MECH_EYE.get()) && !PlayerDataManager.mechEyeTraded.get(player)) {
                player.displayClientMessage(TCRCoreMod.getInfo("time_to_altar"), true);
                giveOracleEffect(player, com.github.L_Ender.cataclysm.init.ModItems.MECH_EYE.get());
                PlayerDataManager.mechEyeTraded.put(player, true);
            }

            if (itemStack.is(com.github.L_Ender.cataclysm.init.ModItems.MONSTROUS_EYE.get()) && !PlayerDataManager.monstEyeTraded.get(player)) {
                player.displayClientMessage(TCRCoreMod.getInfo("time_to_altar"), true);
                giveOracleEffect(player, com.github.L_Ender.cataclysm.init.ModItems.MONSTROUS_EYE.get());
                PlayerDataManager.monstEyeTraded.put(player, true);
            }

        }

    }

    public static void giveOracleEffect(ServerPlayer player, Item toDisplay) {
        //改和女神兑换
//        ItemUtil.addItemEntity(player, TCRItems.ANCIENT_ORACLE_FRAGMENT.get(), 1, ChatFormatting.LIGHT_PURPLE.getColor().intValue());
        PacketRelay.sendToPlayer(TCRPacketHandler.INSTANCE, new PlayItemPickupParticlePacket(toDisplay.getDefaultInstance()), player);
        player.connection.send(new ClientboundSoundPacket(BuiltInRegistries.SOUND_EVENT.wrapAsHolder(SoundEvents.TOTEM_USE), SoundSource.PLAYERS, player.getX(), player.getY(), player.getZ(), 1.0F, 1.0F, player.getRandom().nextInt()));
        player.connection.send(new ClientboundSoundPacket(BuiltInRegistries.SOUND_EVENT.wrapAsHolder(SoundEvents.UI_TOAST_CHALLENGE_COMPLETE), SoundSource.PLAYERS, player.getX(), player.getY(), player.getZ(), 1.0F, 1.0F, player.getRandom().nextInt()));
    }

}
