package com.p1nero.tcrcore.events;

import com.p1nero.cataclysm_dimension.CataclysmDimensionMod;
import com.p1nero.cataclysm_dimension.worldgen.CataclysmDimensions;
import com.p1nero.dialog_lib.events.ServerNpcEntityInteractEvent;
import com.p1nero.tcrcore.TCRCoreMod;
import com.p1nero.tcrcore.capability.PlayerDataManager;
import com.p1nero.tcrcore.capability.TCRCapabilityProvider;
import com.p1nero.tcrcore.capability.TCRPlayer;
import com.p1nero.tcrcore.save_data.TCRDimSaveData;
import com.p1nero.tcrcore.utils.ItemUtil;
import com.p1nero.tcrcore.utils.WorldUtil;
import com.p1nero.tcrcore.worldgen.TCRDimensions;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.protocol.game.ClientboundSoundPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityTravelToDimensionEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.saksolm.monsterexpansion.entity.custom.AbstractLargeMonster;
import net.shelmarow.nightfall_invade.entity.NFIEntities;
import net.shelmarow.nightfall_invade.entity.spear_knight.Arterius;
import org.merlin204.mimic.item.MimicItems;
import org.merlin204.wraithon.util.PositionTeleporter;

@Mod.EventBusSubscriber(modid = TCRCoreMod.MOD_ID)
public class ForgeEvents {

    @SubscribeEvent
    public static void onDialogSend(ServerNpcEntityInteractEvent event) {
        if(event.getSelf() instanceof IronGolem ironGolem) {
            if(event.getInteractId() == 1) {
                ironGolem.setPlayerCreated(false);
                ironGolem.setTarget(event.getServerPlayer());
            }
        }

        if(event.getSelf() instanceof Arterius arterius) {
            if(event.getInteractId() == 1) {
                arterius.resetBossStatus(true);
                TCRPlayer tcrPlayer = TCRCapabilityProvider.getTCRPlayer(event.getServerPlayer());
                tcrPlayer.setArterius(arterius);
                tcrPlayer.setTickAfterStartArterius(62);
            }
            if(event.getInteractId() == 2) {
                if(!PlayerDataManager.letterGet.get(event.getServerPlayer())) {
                    ItemUtil.addItemEntity(event.getServerPlayer(), MimicItems.MIMIC_INVITATION.get(), 1, ChatFormatting.RED.getColor());
                    event.getServerPlayer().displayClientMessage(TCRCoreMod.getInfo("get_mimic_invite", NFIEntities.ARTERIUS.get().getDescription().copy().withStyle(ChatFormatting.RED), MimicItems.MIMIC_INVITATION.get().getDescription()), false);
                    PlayerDataManager.letterGet.put(event.getServerPlayer(), true);
                }
            }
        }

        if(event.getSelf() instanceof AbstractLargeMonster<?, ?> abstractLargeMonster) {
            if(event.getInteractId() == 1) {
                ServerPlayer player = event.getServerPlayer();
                abstractLargeMonster.tame(player);
                player.connection.send(new ClientboundSoundPacket(BuiltInRegistries.SOUND_EVENT.wrapAsHolder(SoundEvents.UI_TOAST_CHALLENGE_COMPLETE), SoundSource.PLAYERS, player.getX(), player.getY(), player.getZ(), 1.0F, 1.0F, player.getRandom().nextInt()));
            }
        }
    }

    @SubscribeEvent
    public static void onLevelTick(TickEvent.LevelTickEvent event) {
        if(event.phase == TickEvent.Phase.START) {
            if(event.level instanceof ServerLevel serverLevel && CataclysmDimensions.LEVELS.contains(event.level.dimension())) {
                TCRDimSaveData dimSaveData = TCRDimSaveData.get(serverLevel);
                dimSaveData.tickResetting();
                if(dimSaveData.isResetting() || CataclysmDimensionMod.RESOURCE_LOCATION_INTEGER_MAP.getOrDefault(serverLevel.dimension().location(), 0) > 0) {
                    for (ServerPlayer serverPlayer : serverLevel.players()) {
                        serverPlayer.changeDimension(serverLevel.getServer().getLevel(TCRDimensions.SANCTUM_LEVEL_KEY), new PositionTeleporter(new BlockPos(WorldUtil.START_POS)));
                        serverPlayer.displayClientMessage(TCRCoreMod.getInfo("dim_demending", dimSaveData.getResetCooldown() / 20), false);
                        break;
                    }
                }
                if(serverLevel.players().isEmpty() && !dimSaveData.isResetting()
                        && CataclysmDimensionMod.RESOURCE_LOCATION_INTEGER_MAP.getOrDefault(serverLevel.dimension().location(), 0) > 0){
                    dimSaveData.setResetCooldown(300);
                }
            }
        }
    }

    @SubscribeEvent
    public static void onEntityJoinDim(EntityTravelToDimensionEvent event) {
        if(event.getEntity().level() instanceof ServerLevel serverLevel && CataclysmDimensions.LEVELS.contains(event.getDimension())) {
            ServerLevel targetLevel = serverLevel.getServer().getLevel(event.getDimension());
            TCRDimSaveData dimSaveData = TCRDimSaveData.get(targetLevel);
            if(dimSaveData.isResetting() || CataclysmDimensionMod.RESOURCE_LOCATION_INTEGER_MAP.getOrDefault(targetLevel.dimension().location(), 0) > 0){
                if(event.getEntity() instanceof ServerPlayer serverPlayer) {
                    serverPlayer.displayClientMessage(TCRCoreMod.getInfo("dim_demending", dimSaveData.getResetCooldown() / 20), false);
                }
                event.setCanceled(true);
                return;
            }
            if(event.getEntity() instanceof ServerPlayer serverPlayer && serverPlayer.tickCount < 300) {
                event.setCanceled(true);
                serverPlayer.displayClientMessage(TCRCoreMod.getInfo("dim_demending", (300 - serverPlayer.tickCount) / 20), false);
            }
        }
    }
}
