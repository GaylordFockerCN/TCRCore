package com.p1nero.tcrcore.events;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.util.ITeleporter;

import java.util.function.Function;

public class SafeOverworldTeleporter implements ITeleporter {
    @Override
    public Entity placeEntity(Entity entity, ServerLevel currentWorld, ServerLevel destWorld, float yaw, Function<Boolean, Entity> repositionEntity) {
        Entity newEntity = repositionEntity.apply(false); // 获取传送到新维度后的实体对象
        if (newEntity == null) {
            return null;
        }

        BlockPos safePos = newEntity.blockPosition().atY(300);
        //从上往下搜
        while (destWorld.getBlockState(safePos).isAir()) {
            safePos = safePos.below();
        }
        newEntity.teleportTo(safePos.getX() + 0.5, safePos.getY(), safePos.getZ() + 0.5);
        return newEntity;
    }
}