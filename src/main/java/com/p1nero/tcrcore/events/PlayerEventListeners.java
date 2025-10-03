package com.p1nero.tcrcore.events;


import com.hm.efn.registries.EFNItem;
import com.p1nero.cataclysm_dimension.worldgen.CataclysmDimensions;
import com.p1nero.fast_tpa.network.PacketRelay;
import com.p1nero.tcrcore.TCRCoreMod;
import com.p1nero.tcrcore.capability.PlayerDataManager;
import com.p1nero.tcrcore.capability.TCRCapabilityProvider;
import com.p1nero.tcrcore.datagen.TCRAdvancementData;
import com.p1nero.tcrcore.effect.TCREffects;
import com.p1nero.tcrcore.network.TCRPacketHandler;
import com.p1nero.tcrcore.network.packet.clientbound.CSTipPacket;
import com.p1nero.tcrcore.network.packet.clientbound.PlayTitlePacket;
import com.p1nero.tcrcore.save_data.TCRDimSaveData;
import com.p1nero.tcrcore.save_data.TCRLevelSaveData;
import com.p1nero.tcrcore.utils.EntityUtil;
import com.p1nero.tcrcore.utils.ItemUtil;
import com.p1nero.tcrcore.utils.WorldUtil;
import com.p1nero.tudigong.item.TDGItemTabs;
import com.p1nero.tudigong.item.TDGItems;
import com.yesman.epicskills.world.capability.AbilityPoints;
import net.blay09.mods.waystones.block.ModBlocks;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.protocol.game.ClientboundSetTitleTextPacket;
import net.minecraft.network.protocol.game.ClientboundSoundPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityTravelToDimensionEvent;
import net.minecraftforge.event.entity.player.AdvancementEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerXpEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.p3pp3rf1y.sophisticatedbackpacks.init.ModItems;
import net.sonmok14.fromtheshadows.server.entity.mob.BulldrogiothEntity;
import net.sonmok14.fromtheshadows.server.utils.registry.EntityRegistry;
import org.merlin204.wraithon.worldgen.WraithonDimensions;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;

import java.util.Objects;

@Mod.EventBusSubscriber(modid = TCRCoreMod.MOD_ID)
public class PlayerEventListeners {

    /**
     * 提示加点
     */
    @SubscribeEvent
    public static void onPlayerLevelUp(PlayerXpEvent.XpChange event) {
        event.getEntity().getCapability(AbilityPoints.ABILITY_POINTS).ifPresent(abilityPoints -> {
            if(event.getEntity().totalExperience < abilityPoints.getRequiredExp() && event.getEntity().totalExperience + event.getAmount() > abilityPoints.getRequiredExp()) {
                event.getEntity().displayClientMessage(TCRCoreMod.getInfo("press_to_skill_tree"), false);
            }
        });
    }

    @SubscribeEvent
    public static void onPlayerAdvancementEarn(AdvancementEvent.AdvancementEarnEvent event) {
        if(event.getEntity() instanceof ServerPlayer player) {
            String path = event.getAdvancement().getId().getPath();
            String namespace = event.getAdvancement().getId().getNamespace();
            if(namespace.equals(TCRCoreMod.MOD_ID)) {
                if(path.equals("kill_pillager") && !PlayerDataManager.pillagerKilled.get(player)) {
                    LivingEntityEventListeners.giveOracle(player);
                    PlayerDataManager.pillagerKilled.put(player, true);
                }
                if(path.equals("stage3")) {
                    player.displayClientMessage(TCRCoreMod.getInfo("unlock_new_dim_girl"), false);
                    player.connection.send(new ClientboundSetTitleTextPacket(TCRCoreMod.getInfo("unlock_new_dim")));
                    player.connection.send(new ClientboundSoundPacket(BuiltInRegistries.SOUND_EVENT.wrapAsHolder(SoundEvents.END_PORTAL_SPAWN), SoundSource.PLAYERS, player.getX(), player.getY(), player.getZ(), 1.0F, 1.0F, player.getRandom().nextInt()));
                }
//                player.displayClientMessage(TCRCoreMod.getInfo("press_to_show_progress"), false);
            }

            if(namespace.equals("minecraft") && path.equals("recipes/transportation/oak_boat")) {
                PacketRelay.sendToPlayer(TCRPacketHandler.INSTANCE, new PlayTitlePacket(4), player);
            }

        }
    }

    @SubscribeEvent
    public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        Player player = event.getEntity();
        if(player instanceof ServerPlayer serverPlayer) {
            if(!PlayerDataManager.firstJoint.get(serverPlayer)) {
                TCRAdvancementData.finishAdvancement(TCRCoreMod.MOD_ID, serverPlayer);
                CommandSourceStack commandSourceStack = serverPlayer.createCommandSourceStack().withPermission(2).withSuppressedOutput();
                Objects.requireNonNull(serverPlayer.getServer()).getCommands().performPrefixedCommand(commandSourceStack, "/gamerule keepInventory true");
                Objects.requireNonNull(serverPlayer.getServer()).getCommands().performPrefixedCommand(commandSourceStack, "/gamerule mobGriefing false");
                Objects.requireNonNull(serverPlayer.getServer()).getCommands().performPrefixedCommand(commandSourceStack, "/skilltree unlock @s epicskills:battleborn efn:efn_step true");
                Objects.requireNonNull(serverPlayer.getServer()).getCommands().performPrefixedCommand(commandSourceStack, "/skilltree unlock @s epicskills:battleborn efn:efn_dodge true");
                Objects.requireNonNull(serverPlayer.getServer()).getCommands().performPrefixedCommand(commandSourceStack, "/skilltree unlock @s epicskills:battleborn epicfight:parrying true");
                Objects.requireNonNull(serverPlayer.getServer()).getCommands().performPrefixedCommand(commandSourceStack, "/skilltree unlock @s dodge_parry_reward:passive dodge_parry_reward:stamina1 true");
                Objects.requireNonNull(serverPlayer.getServer()).getCommands().performPrefixedCommand(commandSourceStack, "/epicfight skill add @s dodge efn:efn_dodge");
                Objects.requireNonNull(serverPlayer.getServer()).getCommands().performPrefixedCommand(commandSourceStack, "/epicfight skill add @s guard epicfight:parrying");
                Objects.requireNonNull(serverPlayer.getServer()).getCommands().performPrefixedCommand(commandSourceStack, "/epicfight skill add @s passive1 dodge_parry_reward:stamina1");
                ItemUtil.addItem(serverPlayer, Items.IRON_SWORD, 1);
                ItemUtil.addItem(serverPlayer, ModItems.BACKPACK.get(), 1);
                ItemUtil.addItem(serverPlayer, Items.BREAD, 32);
                ItemUtil.addItem(serverPlayer, TDGItems.TUDI_COMMAND_SPELL.get(), 1);
                serverPlayer.setItemSlot(EquipmentSlot.CHEST, EFNItem.RUINFIGHTER_CHESTPLATE.get().getDefaultInstance());

                PlayerDataManager.firstJoint.put(serverPlayer, true);
            }

            if(TCRCoreMod.hasCheatMod()) {
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

        if(event.getEntity() instanceof ServerPlayer serverPlayer) {

            //第一次交互给传送石和提示
            if (event.getLevel().getBlockState(event.getPos()).is(ModBlocks.waystone)) {
                if(!PlayerDataManager.wayStoneInteracted.get(serverPlayer)) {
                    serverPlayer.displayClientMessage(TCRCoreMod.getInfo("press_to_open_portal_screen"), true);
                    ItemUtil.addItem(serverPlayer, net.blay09.mods.waystones.item.ModItems.warpStone, 1, true);
                    PlayerDataManager.wayStoneInteracted.put(serverPlayer, true);
                }
            }

            if(CataclysmDimensions.LEVELS.contains(serverPlayer.serverLevel().dimension())) {
                boolean isChest = event.getLevel().getBlockState(event.getPos()).is(Blocks.CHEST) || event.getLevel().getBlockState(event.getPos()).is(noobanidus.mods.lootr.init.ModBlocks.CHEST.get());
                if(isChest && !TCRDimSaveData.get(serverPlayer.serverLevel()).isBossKilled()) {
                    serverPlayer.displayClientMessage(TCRCoreMod.getInfo("dim_block_no_interact"), true);
                    event.setCanceled(true);
                }
            }
        }

    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if(event.phase == TickEvent.Phase.START) {
            if(event.player.isLocalPlayer() && WorldUtil.inMainLand(event.player)) {
                if(isNearBarrier(event.player)) {
                    event.player.displayClientMessage(TCRCoreMod.getInfo("hit_barrier"), true);
                }
            }
            if(event.player instanceof ServerPlayer serverPlayer) {
                if(!serverPlayer.isInvulnerable()) {
                    if(serverPlayer.hasEffect(TCREffects.INVULNERABLE.get())) {
                        serverPlayer.setInvulnerable(true);
                    }
                } else {
                    if(!serverPlayer.hasEffect(TCREffects.INVULNERABLE.get()) && !serverPlayer.isCreative() && !serverPlayer.isSpectator()) {
                        serverPlayer.setInvulnerable(false);
                    }
                }
                if(!serverPlayer.serverLevel().isLoaded(serverPlayer.getOnPos())) {
                    return;
                }
                if(PlayerDataManager.stormEyeTraded.get(event.player) && !PlayerDataManager.bllSummoned.get(event.player) && WorldUtil.isInStructure(event.player, WorldUtil.COVES)) {
                    //定点生
                    BlockPos pos = TCRLevelSaveData.get(serverPlayer.serverLevel()).getCoversPos();
                    if(pos.equals(BlockPos.ZERO)) {
                        pos = event.player.getOnPos();
                    }
                    //保险措施
                    if(EntityUtil.getNearByEntities(serverPlayer.serverLevel(), pos.getCenter(), 100, BulldrogiothEntity.class).isEmpty()) {
                        BulldrogiothEntity entity = EntityRegistry.BULLDROGIOTH.get().spawn(serverPlayer.serverLevel(), pos, MobSpawnType.SPAWNER);
                        entity.setGlowingTag(true);
                        PlayerDataManager.bllSummoned.put(event.player, true);
                    }
                }
                if(WorldUtil.inMainLand(serverPlayer) && serverPlayer.isSprinting()) {
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
        if(player instanceof ServerPlayer serverPlayer) {
            TCRCapabilityProvider.syncPlayerDataToClient(serverPlayer);
            player.setHealth(player.getMaxHealth());
            EpicFightCapabilities.getUnparameterizedEntityPatch(player, ServerPlayerPatch.class).ifPresent(serverPlayerPatch -> {
                serverPlayerPatch.setStamina(serverPlayerPatch.getMaxStamina());
            });
        }
    }

    @SubscribeEvent
    public static void onPlayerEnterDim(EntityTravelToDimensionEvent event) {
        if(event.getEntity() instanceof ServerPlayer serverPlayer && (event.getDimension() == Level.END || event.getDimension() == Level.NETHER)) {
            if(PlayerDataManager.stage.getInt(serverPlayer) < 3) {
                event.setCanceled(true);
                serverPlayer.displayClientMessage(TCRCoreMod.getInfo("can_not_enter_dim"), true);
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerEnterDim(PlayerEvent.PlayerChangedDimensionEvent event) {
        if(event.getEntity() instanceof ServerPlayer serverPlayer) {
            if(event.getFrom() == WraithonDimensions.SANCTUM_OF_THE_WRAITHON_LEVEL_KEY) {
                ServerLevel wraithonLevel = serverPlayer.server.getLevel(WraithonDimensions.SANCTUM_OF_THE_WRAITHON_LEVEL_KEY);
                if(wraithonLevel.players().isEmpty()) {
                    wraithonLevel.getAllEntities().forEach(Entity::discard);
                    TCRDimSaveData.get(wraithonLevel).setBossSummoned(false);
                }
            }
            if(CataclysmDimensions.LEVELS.contains(event.getTo())) {
                serverPlayer.displayClientMessage(TCRCoreMod.getInfo("reset_when_no_player").withStyle(ChatFormatting.RED, ChatFormatting.BOLD), false);
                if(serverPlayer.serverLevel().players().size() <= 1) {
                    TCRDimSaveData.get(serverPlayer.getServer().getLevel(event.getTo())).setBossKilled(false);
                }
            }
        }
    }

}
