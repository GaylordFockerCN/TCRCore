package com.p1nero.tcrcore.mixin;

import com.p1nero.dialog_lib.DialogueLib;
import com.p1nero.tcrcore.capability.TCRCapabilityProvider;
import com.p1nero.tcrcore.utils.WorldUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.AbstractGolem;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(IronGolem.class)
public class IronGolemMixin extends AbstractGolem {

    protected IronGolemMixin(EntityType<? extends AbstractGolem> p_27508_, Level p_27509_) {
        super(p_27508_, p_27509_);
    }

    @Inject(method = "mobInteract", at = @At("HEAD"))
    private void tcr$interact(Player player, InteractionHand hand, CallbackInfoReturnable<InteractionResult> cir) {
        if(this.getTarget() == null && player instanceof ServerPlayer serverPlayer && serverPlayer.isAlive() && WorldUtil.isInStructure(player, WorldUtil.SKY_ISLAND)) {
            TCRCapabilityProvider.getTCRPlayer(serverPlayer).setCurrentTalkingEntity(this);
            CompoundTag tag = new CompoundTag();
            DialogueLib.sendDialog((IronGolem)(Object)this, tag, serverPlayer);
        }
    }

    @Inject(method = "hurt", at = @At("HEAD"), cancellable = true)
    private void tcr$hurt(DamageSource p_28848_, float p_28849_, CallbackInfoReturnable<Boolean> cir){
        if(this.tickCount < 20) {
            cir.setReturnValue(false);
        }
    }

}
