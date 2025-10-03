package com.p1nero.tcrcore.events;

import com.p1nero.cataclysm_dimension.worldgen.CataclysmDimensions;
import com.p1nero.tcrcore.TCRCoreMod;
import com.p1nero.tcrcore.save_data.TCRDimSaveData;
import net.minecraft.server.level.ServerLevel;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = TCRCoreMod.MOD_ID)
public class BlockEvents {
    @SubscribeEvent
    public static void onBreakBlock(BlockEvent.BreakEvent event) {
        if(event.getPlayer() == null || event.getPlayer().isCreative()) {
            return;
        }
        if(event.getPlayer().level() instanceof ServerLevel serverLevel) {
            if(CataclysmDimensions.LEVELS.contains(event.getPlayer().level().dimension())) {
                //利维坦得挖进去
                if(event.getPlayer().level().dimension() == CataclysmDimensions.CATACLYSM_ABYSSAL_DEPTHS_LEVEL_KEY) {
                    return;
                }
                if(!TCRDimSaveData.get(serverLevel).isBossKilled()) {
                    event.getPlayer().displayClientMessage(TCRCoreMod.getInfo("dim_block_no_interact"), true);
                    event.setCanceled(true);
                }
            }
        }
    }

}
