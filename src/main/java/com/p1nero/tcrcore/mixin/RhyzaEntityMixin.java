package com.p1nero.tcrcore.mixin;

import com.p1nero.tcrcore.utils.WorldUtil;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.SitWhenOrderedToGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.saksolm.monsterexpansion.entity.ai.*;
import net.saksolm.monsterexpansion.entity.ai.rhyza.*;
import net.saksolm.monsterexpansion.entity.custom.AbstractLargeMonster;
import net.saksolm.monsterexpansion.entity.custom.RhyzaEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RhyzaEntity.class)
public abstract class RhyzaEntityMixin extends AbstractLargeMonster<RhyzaEntity.Ability, RhyzaEntity.StaggerState>{
    protected RhyzaEntityMixin(EntityType<? extends AbstractLargeMonster> entityType, Level world, Class<RhyzaEntity.Ability> abilityClass, Class<RhyzaEntity.StaggerState> staggerClass) {
        super(entityType, world, abilityClass, staggerClass);
    }

    @Inject(method = "registerGoals", at = @At("HEAD"), cancellable = true)
    private void tcr$registerGoal(CallbackInfo ci) {
        this.goalSelector.addGoal(0, new RhyzaStaggerGoal((RhyzaEntity)(Object) this));
        this.goalSelector.addGoal(0, new RhyzaToppleGoal((RhyzaEntity)(Object) this));
        this.goalSelector.addGoal(0, new RhyzaTailCutGoal((RhyzaEntity)(Object) this));
        //取消在主城时主动攻击玩家
        if (!this.isTame()) {
            this.targetSelector.addGoal(2, new NearestAttackableTargetGoal(this, Player.class, true, (living -> !WorldUtil.inMainLand(this))));
        }

        if (!this.isTame() || this.isTame() && this.getTamedStance() == AbstractLargeMonster.TamedStance.WANDERING) {
            this.goalSelector.addGoal(0, new LargeMonsterWanderGoal(this, 1.0F));
        }

        if (!this.isTame() || this.isTame() && this.getTamedBehavior() != AbstractLargeMonster.TamedBehavior.PASSIVE) {
            this.goalSelector.addGoal(1, new RhyzaEnrageGoal((RhyzaEntity)(Object) this));
            this.goalSelector.addGoal(1, new RhyzaSlashGoal((RhyzaEntity)(Object) this));
            this.goalSelector.addGoal(1, new RhyzaRightSlashGoal((RhyzaEntity)(Object) this));
            this.goalSelector.addGoal(1, new RhyzaBackslashGoal((RhyzaEntity)(Object) this));
            this.goalSelector.addGoal(1, new RhyzaTailswipeGoal((RhyzaEntity)(Object) this));
            this.goalSelector.addGoal(1, new RhyzaRushGoal((RhyzaEntity)(Object) this));
            this.goalSelector.addGoal(1, new RhyzaSlamGoal((RhyzaEntity)(Object) this));
            this.goalSelector.addGoal(1, new RhyzaDashGoal((RhyzaEntity)(Object) this));
            this.goalSelector.addGoal(1, new RhyzaInvisibilityGoal((RhyzaEntity)(Object) this));
            this.goalSelector.addGoal(1, new RhyzaLeapGoal((RhyzaEntity)(Object) this));
            this.goalSelector.addGoal(1, new RhyzaFlashGoal((RhyzaEntity)(Object) this));
            this.goalSelector.addGoal(1, new RhyzaFrenzyGoal((RhyzaEntity)(Object) this));
            this.targetSelector.addGoal(2, new HealPunishGoal((RhyzaEntity)(Object) this));
            this.targetSelector.addGoal(3, new HurtByTargetGoal(this));
            this.targetSelector.addGoal(4, new HighestThreatTargetGoal((RhyzaEntity)(Object) this));
            this.targetSelector.addGoal(5, new SwitchTargetGoal((RhyzaEntity)(Object) this));
            this.targetSelector.addGoal(1, new OwnerHurtTargetGoal((RhyzaEntity)(Object) this));
            this.targetSelector.addGoal(2, new OwnerHurtByTargetGoal((RhyzaEntity)(Object) this));
        }

        if (this.isTame() && this.getTamedBehavior() == AbstractLargeMonster.TamedBehavior.AGGRESSIVE) {
            this.targetSelector.addGoal(3, new LargeMonsterNearestAttackableTargetGoal(this, LivingEntity.class, true, (entity) -> entity != this));
        }

        if (this.isTame() && this.getTamedStance() == AbstractLargeMonster.TamedStance.SITTING) {
            this.goalSelector.addGoal(1, new SitWhenOrderedToGoal((RhyzaEntity)(Object) this));
        }

        if (this.isTame() && this.getTamedStance() == AbstractLargeMonster.TamedStance.FOLLOWING) {
            this.goalSelector.addGoal(2, new LargeMonsterFollowOwnerGoal(this, (double)1.0F, 10.0F, 5.0F));
        }
        ci.cancel();
    }
}
