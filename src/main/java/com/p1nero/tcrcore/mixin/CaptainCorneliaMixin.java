package com.p1nero.tcrcore.mixin;

import com.obscuria.aquamirae.common.entities.CaptainCornelia;
import com.obscuria.aquamirae.common.items.weapon.DividerItem;
import com.obscuria.aquamirae.common.items.weapon.WhisperOfTheAbyssItem;
import com.obscuria.aquamirae.registry.AquamiraeSounds;
import com.p1nero.tcrcore.client.sound.CorneliaMusicPlayer;
import net.minecraft.client.Minecraft;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * 取消中毒和回血，和自定义战利品
 */
@Mixin(CaptainCornelia.class)
public abstract class CaptainCorneliaMixin extends Monster {
    @Shadow(remap = false) protected abstract boolean doHurtTarget(LivingEntity entity, DamageSource source, float amount);

    protected CaptainCorneliaMixin(EntityType<? extends Monster> p_33002_, Level p_33003_) {
        super(p_33002_, p_33003_);
    }

    @Redirect(method = "baseTick", at = @At(value = "INVOKE", target = "Lcom/obscuria/aquamirae/common/entities/CaptainCornelia;heal(F)V"))
    private void tcr$baseTick$heal(CaptainCornelia instance, float v) {
        this.heal(0.8F);
    }

    @Inject(method = "baseTick", at = @At("TAIL"))
    private void tcr$baseTick(CallbackInfo ci) {
        if (this.level().isClientSide) {
            //附近再播放
            if(this.isAlive()) {
                CorneliaMusicPlayer.playBossMusic(this, AquamiraeSounds.MUSIC_FORSAKEN_DROWNAGE.get(), 32);
            }
        }
    }

    @Inject(method = "doHurtTarget(Lnet/minecraft/world/entity/Entity;)Z", at = @At("HEAD"), cancellable = true)
    private void tcr$doHurtTarget(Entity entity, CallbackInfoReturnable<Boolean> cir) {
        if (entity instanceof LivingEntity living) {
            Item item = this.getMainHandItem().getItem();

            if (item instanceof WhisperOfTheAbyssItem) {
                living.setLastHurtByMob(this);
                cir.setReturnValue(this.doHurtTarget(living, this.damageSources().mobAttack(this), 8.0F));
            }

            if (item instanceof DividerItem) {
                living.setLastHurtByMob(this);
                cir.setReturnValue(this.doHurtTarget(living, this.damageSources().mobAttack(this), Math.max(4.0F, living.getHealth() / 2.0F)));
            }

        }
        cir.setReturnValue(super.doHurtTarget(entity));
    }

    @Inject(method = "dropEquipment", at = @At("HEAD"), cancellable = true)
    private void tcr$dropEquipment(CallbackInfo ci) {

        ci.cancel();
    }

}
