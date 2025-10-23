package com.p1nero.tcrcore.events;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.util.ITeleporter;

import java.util.function.Function;

public class SafeNetherTeleporter implements ITeleporter {
    @Override
    public Entity placeEntity(Entity entity, ServerLevel currentWorld, ServerLevel destWorld, float yaw, Function<Boolean, Entity> repositionEntity) {
        Entity newEntity = repositionEntity.apply(false); // 获取传送到新维度后的实体对象
        if (newEntity == null) {
            return null;
        }

        BlockPos safePos = newEntity.blockPosition().atY(63);
        //防卡墙或岩浆
        while (!(destWorld.getBlockState(safePos).isAir() && destWorld.getBlockState(safePos.above()).isAir())) {
            safePos = safePos.east();
        }
        // 确保脚下是固体方块，否则放置一个平台（这里用下界岩）
        if (destWorld.getBlockState(safePos.below()).isAir()) {
            // 在安全位置的下方放置一个3x3的平台以防虚空
            for (int dx = -1; dx <= 1; dx++) {
                for (int dz = -1; dz <= 1; dz++) {
                    BlockPos platformPos = safePos.below().offset(dx, 0, dz);
                    if (destWorld.isEmptyBlock(platformPos)) {
                        destWorld.setBlockAndUpdate(platformPos, Blocks.NETHERRACK.defaultBlockState());
                    }
                }
            }
            // 将实体传送到这个安全平台的上方
            newEntity.teleportTo(safePos.getX() + 0.5, safePos.getY(), safePos.getZ() + 0.5);
        } else {
            // 如果位置安全，则直接使用计算出的安全位置
            newEntity.teleportTo(safePos.getX() + 0.5, safePos.getY(), safePos.getZ() + 0.5);
        }
        
        return newEntity;
    }
}