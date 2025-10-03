package com.p1nero.tcrcore.mixin;

import com.p1nero.tcrcore.client.sound.TCRSounds;
import com.p1nero.tcrcore.client.sound.WraithonMusicPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import org.merlin204.wraithon.entity.wraithon.WraithonEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import yesman.epicfight.world.entity.ai.attribute.EpicFightAttributes;

@Mixin(WraithonEntity.class)
public class WraithonEntityMixin extends PathfinderMob {
    protected WraithonEntityMixin(EntityType<? extends PathfinderMob> p_21683_, Level p_21684_) {
        super(p_21683_, p_21684_);
    }

    @Inject(method = "getDefaultAttribute", at = @At("HEAD"), cancellable = true, remap = false)
    private static void tcr$getDefaultAttribute(CallbackInfoReturnable<AttributeSupplier> cir) {
        cir.setReturnValue(Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 500)
                .add(Attributes.ATTACK_DAMAGE, 30.0F)
                .add(Attributes.FOLLOW_RANGE, 72.0F)
                .add(Attributes.KNOCKBACK_RESISTANCE, 1000.0F)
                .add(EpicFightAttributes.MAX_STRIKES.get(), 50.0F).build());
    }

    @Inject(method = "tick", at = @At("TAIL"))
    private void tcr$tick(CallbackInfo ci) {
        if(this.level().isClientSide) {
            WraithonMusicPlayer.playBossMusic(this, TCRSounds.BOSS_FIGHT_BGM.get(), 400);
        }
    }
}
