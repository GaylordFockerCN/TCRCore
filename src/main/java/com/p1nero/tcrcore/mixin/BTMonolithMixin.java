package com.p1nero.tcrcore.mixin;

import com.brass_amber.ba_bt.entity.block.BTMonolith;
import com.brass_amber.ba_bt.init.BTEntityType;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 *  取消前置眼睛限制
 */
@Mixin(value = BTMonolith.class, remap = false)
public abstract class BTMonolithMixin extends Entity {

    @Shadow
    @Final
    private EntityType<?> monolithType;

    @Shadow
    @Final
    private Item correctMonolithKey;

    @Shadow
    public abstract int getKeyCountInEntity();

    @Shadow
    protected abstract void increaseKeyCount(Player player, InteractionHand hand);

    @Shadow
    public abstract void setEyeSlotDisplayed();

    public BTMonolithMixin(EntityType<?> p_19870_, Level p_19871_) {
        super(p_19870_, p_19871_);
    }

    @Inject(method = "interact", at = @At("HEAD"), cancellable = true)
    private void tcr$interact(Player player, InteractionHand hand, CallbackInfoReturnable<InteractionResult> cir) {
        if (this.monolithType != null) {
            Item itemInHand = player.getItemInHand(hand).getItem();
            if (itemInHand.equals(this.correctMonolithKey)) {
                int keysNeeded = this.monolithType.equals(BTEntityType.LAND_MONOLITH.get()) ? 3 : 2;
                if (this.getKeyCountInEntity() < keysNeeded) {
                    if(!this.monolithType.equals(BTEntityType.LAND_MONOLITH.get()) && this.getKeyCountInEntity() == 1) {
                        this.increaseKeyCount(player, hand);
                        this.setEyeSlotDisplayed();
                    }
                    this.increaseKeyCount(player, hand);
                    cir.setReturnValue(InteractionResult.sidedSuccess(this.getCommandSenderWorld().isClientSide()));
                }
            }
        }
        cir.setReturnValue(super.interact(player, hand));
    }
}
