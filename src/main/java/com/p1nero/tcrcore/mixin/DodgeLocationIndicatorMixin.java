package com.p1nero.tcrcore.mixin;

import com.p1nero.p1nero_ec.capability.PECPlayer;
import com.p1nero.p1nero_ec.network.PECPacketHandler;
import com.p1nero.p1nero_ec.network.PECPacketRelay;
import com.p1nero.p1nero_ec.network.packet.clientbound.AddAvlEntityAfterImageParticle;
import com.p1nero.tcrcore.capability.PlayerDataManager;
import net.minecraft.network.protocol.game.ClientboundSoundPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import yesman.epicfight.gameasset.EpicFightSounds;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.skill.SkillSlots;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;
import yesman.epicfight.world.damagesource.EpicFightDamageTypeTags;
import yesman.epicfight.world.entity.DodgeLocationIndicator;

/**
 * 完美闪避的一些全局设定
 */
@Mixin(value = DodgeLocationIndicator.class, priority = 10000)
public abstract class DodgeLocationIndicatorMixin extends LivingEntity {

    @Shadow(remap = false) private LivingEntityPatch<?> entitypatch;

    protected DodgeLocationIndicatorMixin(EntityType<? extends LivingEntity> p_20966_, Level p_20967_) {
        super(p_20966_, p_20967_);
    }

    @Inject(method = "hurt", at = @At("HEAD"), cancellable = true)
    private void tcr$hurt(DamageSource damageSource, float amount, CallbackInfoReturnable<Boolean> cir){
        if(this.entitypatch == null) {
            return;
        }
        if(!this.entitypatch.isLogicalClient()) {
            if(this.entitypatch instanceof ServerPlayerPatch serverPlayerPatch) {
                ServerPlayer serverPlayer = serverPlayerPatch.getOriginal();
                if(!PlayerDataManager.dodged.get(serverPlayer)) {
                    PlayerDataManager.dodged.put(serverPlayer, true);
                }
                if(!PECPlayer.isValidWeapon(serverPlayerPatch.getOriginal().getMainHandItem())) {
                    SkillContainer weaponInnate = serverPlayerPatch.getSkill(SkillSlots.WEAPON_INNATE);
                    if(weaponInnate.hasSkill()) {
                        weaponInnate.getSkill().setStackSynchronize(weaponInnate, weaponInnate.getStack() + 1);
                    }
                    PECPacketRelay.sendToPlayer(PECPacketHandler.INSTANCE, new AddAvlEntityAfterImageParticle(serverPlayer.getId()), serverPlayer);
                    serverPlayerPatch.getOriginal().connection.send(new ClientboundSoundPacket(EpicFightSounds.ENTITY_MOVE.getHolder().orElseThrow(), SoundSource.PLAYERS, serverPlayer.getX(), serverPlayer.getY(), serverPlayer.getZ(), 1.0F, 1.0F, serverPlayer.getRandom().nextInt()));
                }
            }
            if (!damageSource.is(EpicFightDamageTypeTags.BYPASS_DODGE)) {
                this.entitypatch.onDodgeSuccess(damageSource, this.getBoundingBox().getCenter());
            }
            this.discard();
            cir.setReturnValue(false);
        }
    }
}
