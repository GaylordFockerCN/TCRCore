package com.p1nero.tcrcore.mixin;

import com.p1nero.dialog_lib.DialogueLib;
import com.p1nero.tcrcore.capability.TCRCapabilityProvider;
import com.p1nero.tcrcore.utils.WorldUtil;
import net.alp.monsterexpansion.entity.custom.AbstractLargeMonster;
import net.alp.monsterexpansion.entity.custom.SkrytheEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractLargeMonster.class)
public abstract class AbstractLargeMonsterMixin extends TamableAnimal {

    @Shadow(remap = false)
    public abstract boolean isAttacking();

    protected AbstractLargeMonsterMixin(EntityType<? extends TamableAnimal> p_21803_, Level p_21804_) {
        super(p_21803_, p_21804_);
    }

    @Inject(method = "mobInteract", at = @At("HEAD"), cancellable = true)
    private void tcr$mobInteract(Player player, InteractionHand pHand, CallbackInfoReturnable<InteractionResult> cir) {
        if(!this.isAggressive() && !this.isAttacking() && this.getTarget() == null && player instanceof ServerPlayer serverPlayer && serverPlayer.isAlive() && !this.isTame() && WorldUtil.inMainLand(this)) {
            TCRCapabilityProvider.getTCRPlayer(serverPlayer).setCurrentTalkingEntity(this);
            CompoundTag tag = new CompoundTag();
            DialogueLib.sendDialog(this, tag, serverPlayer);
            cir.setReturnValue(InteractionResult.SUCCESS);
        }
    }

}
