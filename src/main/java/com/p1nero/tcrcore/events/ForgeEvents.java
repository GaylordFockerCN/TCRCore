package com.p1nero.tcrcore.events;

import com.p1nero.cataclysm_dimension.CataclysmDimensionMod;
import com.p1nero.cataclysm_dimension.worldgen.CataclysmDimensions;
import com.p1nero.dialog_lib.events.ServerNpcEntityInteractEvent;
import com.p1nero.tcrcore.TCRCoreMod;
import com.p1nero.tcrcore.save_data.TCRDimSaveData;
import com.p1nero.tcrcore.utils.WorldUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityTravelToDimensionEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.merlin204.wraithon.util.PositionTeleporter;

import java.util.Iterator;

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
    }

    @SubscribeEvent
    public static void onLevelTick(TickEvent.LevelTickEvent event) {
        if(event.phase == TickEvent.Phase.START) {
            if(event.level instanceof ServerLevel serverLevel && CataclysmDimensions.LEVELS.contains(event.level.dimension())) {
                TCRDimSaveData dimSaveData = TCRDimSaveData.get(serverLevel);
                dimSaveData.tickResetting();
                if(dimSaveData.isResetting() || CataclysmDimensionMod.RESOURCE_LOCATION_INTEGER_MAP.getOrDefault(serverLevel.dimension().location(), 0) > 0) {
                    Iterator<ServerPlayer> iterator = serverLevel.players().iterator();
                    while (iterator.hasNext()) {
                        ServerPlayer serverPlayer = iterator.next();
                        serverPlayer.changeDimension(serverLevel.getServer().getLevel(Level.OVERWORLD), new PositionTeleporter(new BlockPos(WorldUtil.START_POS)));
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
