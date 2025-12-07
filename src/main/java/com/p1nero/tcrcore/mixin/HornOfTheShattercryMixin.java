package com.p1nero.tcrcore.mixin;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.saksolm.monsterexpansion.item.HornOfTheShattercryItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(HornOfTheShattercryItem.class)
public class HornOfTheShattercryMixin {

    @Inject(method = "getUseAnimation", at = @At("HEAD"), cancellable = true)
    private void tcr$useAnim(ItemStack stack, CallbackInfoReturnable<UseAnim> cir) {
        cir.setReturnValue(UseAnim.SPEAR);
    }
}
