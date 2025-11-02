package com.p1nero.tcrcore.mixin;

import com.p1nero.tcrcore.utils.EntityUtil;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.MobPatch;
import yesman.epicfight.world.entity.ai.goal.AnimatedAttackGoal;

import java.util.List;

/**
 * 简单群怪优化
 */
@Mixin(value = AnimatedAttackGoal.class)
public abstract class AnimatedAttackGoalMixin <T extends MobPatch<?>> extends Goal {
    @Shadow(remap = false) @Final protected T mobpatch;

    @Inject(method = "tick", at = @At("HEAD"), cancellable = true)
    private void tcr$tick(CallbackInfo ci) {
        List<Entity> list = EntityUtil.getNearByEntities(this.mobpatch.getOriginal(), 3);
        if(list.stream().anyMatch(entity -> {
            if(entity instanceof Enemy) {
                LivingEntityPatch<?> livingEntityPatch = EpicFightCapabilities.getEntityPatch(entity, LivingEntityPatch.class);
                return livingEntityPatch != null && livingEntityPatch.getEntityState().inaction();
            }
            return false;
        })) {
            this.mobpatch.getOriginal().move(MoverType.SELF, this.mobpatch.getOriginal().getViewVector(1.0F).subtract(Vec3.ZERO).normalize().scale(0.1F));
            ci.cancel();
        }
    }

    @Inject(method = "canUse", at = @At("HEAD"), cancellable = true)
    private void tcr$canUse(CallbackInfoReturnable<Boolean> cir) {
        List<Entity> list = EntityUtil.getNearByEntities(this.mobpatch.getOriginal(), 3);
        if(list.stream().anyMatch(entity -> {
            if(entity instanceof Enemy) {
                LivingEntityPatch<?> livingEntityPatch = EpicFightCapabilities.getEntityPatch(entity, LivingEntityPatch.class);
                return livingEntityPatch != null && livingEntityPatch.getEntityState().inaction();
            }
            return false;
        })) {
            this.mobpatch.getOriginal().move(MoverType.SELF, this.mobpatch.getOriginal().getViewVector(1.0F).subtract(Vec3.ZERO).normalize().scale(0.1F));
            cir.setReturnValue(false);
        }
    }

}
