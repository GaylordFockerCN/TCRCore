package com.p1nero.tcrcore.events;

import com.github.L_Ender.cataclysm.init.ModBlocks;
import com.p1nero.cataclysm_dimension.worldgen.CataclysmDimensions;
import com.p1nero.tcrcore.TCRCoreMod;
import com.p1nero.tcrcore.save_data.TCRDimSaveData;
import com.p1nero.tcrcore.utils.WorldUtil;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
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
            //主城不可破坏
            if(WorldUtil.inMainLand(event.getPlayer())) {
                event.setCanceled(true);
            }
            //不可破坏神像
            if(event.getState().is(ModBlocks.GODDESS_STATUE.get()) && WorldUtil.inMainLand(event.getPlayer())) {
                EntityType.LIGHTNING_BOLT.spawn(serverLevel, event.getPos(), MobSpawnType.MOB_SUMMONED);
                event.setCanceled(true);
            }
            if(CataclysmDimensions.LEVELS.contains(event.getPlayer().level().dimension())) {
                //利维坦和末影守卫得挖进去
                if(event.getPlayer().level().dimension() == CataclysmDimensions.CATACLYSM_ABYSSAL_DEPTHS_LEVEL_KEY) {
                    return;
                }
                if(event.getPlayer().level().dimension() == CataclysmDimensions.CATACLYSM_BASTION_LOST_LEVEL_KEY) {
                    return;
                }
                if(!TCRDimSaveData.get(serverLevel).isBossKilled()) {
                    event.getPlayer().displayClientMessage(TCRCoreMod.getInfo("dim_block_no_interact"), true);
                    event.setCanceled(true);
                }
            }
        }
    }

    @SubscribeEvent
    public static void onBlockPlace(BlockEvent.EntityPlaceEvent event) {
        //主城保护
        if(event.getEntity() != null && WorldUtil.inMainLand(event.getEntity())) {
            event.setCanceled(true);
        }
    }

}
