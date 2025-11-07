package com.p1nero.tcrcore.events;

import com.p1nero.tcrcore.save_data.TCRMainLevelSaveData;
import com.p1nero.tcrcore.utils.WorldUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.common.util.ITeleporter;
import yesman.epicfight.api.utils.math.Vec2i;

import java.util.function.Function;

public class OverworldVillageTeleporter implements ITeleporter {
    public static final String VILLAGE = "#tcrcore:villages_to_spawn";
    @Override
    public Entity placeEntity(Entity entity, ServerLevel currentWorld, ServerLevel destWorld, float yaw, Function<Boolean, Entity> repositionEntity) {
        Entity newEntity = repositionEntity.apply(false);
        if (newEntity == null) {
            return null;
        }

        BlockPos targetPos = BlockPos.ZERO;
        if(TCRMainLevelSaveData.get(destWorld).getVillagePos().equals(BlockPos.ZERO)){
            Vec2i villagePos = WorldUtil.getNearbyStructurePos(destWorld, newEntity.position(), VILLAGE);
            if(villagePos != null) {
                targetPos = WorldUtil.getSurfaceBlockPos(destWorld, villagePos.x, villagePos.y);
                TCRMainLevelSaveData.get(destWorld).setVillagePos(targetPos);
            }
        } else {
            targetPos = TCRMainLevelSaveData.get(destWorld).getVillagePos();
        }

        if(targetPos.equals(BlockPos.ZERO)) {
            targetPos = newEntity.blockPosition().atY(300);
            //从上往下搜
            while (destWorld.getBlockState(targetPos).isAir()) {
                targetPos = targetPos.below();
            }
        }
        destWorld.setDefaultSpawnPos(targetPos, 0);
        newEntity.teleportTo(targetPos.getX() + 0.5, targetPos.getY(), targetPos.getZ() + 0.5);
        return newEntity;
    }
}