package com.p1nero.tcrcore.mixin;

import com.github.L_Ender.cataclysm.init.ModItems;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * 移除物品耐久度，弓除外
 */
@Mixin(ItemStack.class)
public abstract class ItemStackMixin {

    @Shadow
    public abstract Item getItem();

    @Inject(method = "isDamageableItem", at = @At("HEAD"), cancellable = true)
    private void tcr$damageable(CallbackInfoReturnable<Boolean> cir) {
        if(this.getItem() instanceof BowItem || this.getItem() == ModItems.WRATH_OF_THE_DESERT.get() || this.getItem() == ModItems.CURSED_BOW.get()) {
            cir.setReturnValue(true);
        } else {
            cir.setReturnValue(false);
        }
    }
}
