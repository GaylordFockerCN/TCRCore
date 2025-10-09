package com.p1nero.tcrcore.utils;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;

import java.util.List;
import java.util.function.Consumer;

public class EntityUtil {
    /**
     * 获取视线和目标位置连线的夹角
     */
    public static double getDegree(Entity target, Entity self){
        return getDegree(target.position(), self);
    }

    /**
     * 获取视线和目标位置连线的夹角
     */
    public static double getDegree(Vec3 target, Entity self){
        Vec3 targetToBoss = target.subtract(self.position());
        Vec2 targetToBossV2 = new Vec2(((float) targetToBoss.x), ((float) targetToBoss.z));
        Vec3 view = self.getViewVector(1.0F);
        Vec2 viewV2 = new Vec2(((float) view.x), ((float) view.z));
        double angleRadians = Math.acos(targetToBossV2.dot(viewV2)/(targetToBossV2.length() * viewV2.length()));
        return Math.toDegrees(angleRadians);
    }

    /**
     * 获取视线和目标视线的夹角
     */
    public static double getViewDegree(Entity target, Entity self){
        Vec3 targetView = target.getViewVector(1.0F);
        Vec2 targetViewV2 = new Vec2(((float) targetView.x), ((float) targetView.z));
        Vec3 view = self.getViewVector(1.0F);
        Vec2 viewV2 = new Vec2(((float) view.x), ((float) view.z));
        double angleRadians = Math.acos(targetViewV2.dot(viewV2)/(targetViewV2.length() * viewV2.length()));
        return Math.toDegrees(angleRadians);
    }

    /**
     * 返回一个范围
     * @param pos 中心位置
     * @param offset 半径
     * @return 以pos为中心offset的两倍为边长的一个正方体
     */
    public static AABB getPlayerAABB(BlockPos pos, int offset){
        return new AABB(pos.offset(offset,offset,offset),pos.offset(-offset,-offset,-offset));
    }

    public static boolean isInFront(Entity target, Entity self, double degree){
        return Math.abs(getDegree(target, self)) <= degree;
    }

    /**
     * 啥比Mojang获取附近实体非要视线对着才算
     */
    public static List<Entity> getNearByEntities(Entity self, int offset){
        return self.level().getEntities(self, getPlayerAABB(self.getOnPos(), offset), entity -> entity.distanceTo(self) < offset);
    }

    public static <T extends Entity> List<T> getNearByEntities(Level level, Vec3 center, int radius, Class<T> entityClass){
        return level.getEntitiesOfClass(entityClass, (new AABB(center, center)).inflate(radius));
    }

    /**
     * 获取附近的玩家
     */
    public static List<Player> getNearByPlayers(LivingEntity self, int offset){
        return self.level().getNearbyPlayers(TargetingConditions.forNonCombat(), self, getPlayerAABB(self.getOnPos(), offset));
    }

    public static <T extends LivingEntity> List<T> getNearByEntities(Class<T> aClass, LivingEntity self, int offset){
        return self.level().getNearbyEntities(aClass, TargetingConditions.forNonCombat(), self, getPlayerAABB(self.getOnPos(), offset));
    }

    public static void nearPlayerDo(LivingEntity self, int radius, Consumer<Player> consumer) {
        Vec3 center = self.position();
        self.level().getEntitiesOfClass(Player.class, (new AABB(center, center)).inflate(radius)).forEach(consumer);
    }

    public static void nearPlayerDo(Level level, Vec3 center, int radius, Consumer<Player> consumer) {
        level.getEntitiesOfClass(Player.class, (new AABB(center, center)).inflate(radius)).forEach(consumer);
    }

}
