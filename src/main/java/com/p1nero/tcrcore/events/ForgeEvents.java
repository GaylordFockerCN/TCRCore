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
        if(event.level instanceof ServerLevel serverLevel && CataclysmDimensions.LEVELS.contains(event.level.dimension())) {
            TCRDimSaveData dimSaveData = TCRDimSaveData.get(serverLevel);
            dimSaveData.tickResetting();
            if(dimSaveData.isResetting()) {
                for(ServerPlayer serverPlayer : serverLevel.players()) {
                    serverPlayer.changeDimension(serverLevel.getServer().getLevel(Level.OVERWORLD), new PositionTeleporter(new BlockPos(WorldUtil.START_POS)));
                    serverPlayer.displayClientMessage(TCRCoreMod.getInfo("dim_demending", dimSaveData.getResetCooldown() / 20), true);
                }
            }
            if(serverLevel.players().isEmpty() && !dimSaveData.isResetting() && !CataclysmDimensionMod.RESOURCE_KEY_BOOLEAN_MAP.get(serverLevel.dimension().location())){
                dimSaveData.setResetCooldown(200);
            }
        }
    }

    @SubscribeEvent
    public static void onEntityJoinDim(EntityTravelToDimensionEvent event) {
        if(event.getEntity().level() instanceof ServerLevel serverLevel && CataclysmDimensions.LEVELS.contains(event.getDimension())) {
            TCRDimSaveData dimSaveData = TCRDimSaveData.get(serverLevel);
            dimSaveData.tickResetting();
            if(dimSaveData.isResetting()) {
                event.setCanceled(true);
                if(event.getEntity() instanceof ServerPlayer serverPlayer) {
                    serverPlayer.displayClientMessage(TCRCoreMod.getInfo("dim_demending", dimSaveData.getResetCooldown() / 20), true);
                }
            }
        }
    }
}
