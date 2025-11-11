package com.p1nero.tcrcore.mixin;

import com.merlin204.avalon.epicfight.animations.AvalonAttackAnimation;
import com.merlin204.sg.item.SGItems;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import yesman.epicfight.api.animation.types.DynamicAnimation;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.item.CapabilityItem;

@Mixin(AvalonAttackAnimation.class)
public class AvalonAttackAnimationMixin {

    @Inject(method = "getPlaySpeed", at = @At("RETURN"), cancellable = true, remap = false)
    private void tcr$getPlaySpeed(LivingEntityPatch<?> entityPatch, DynamicAnimation animation, CallbackInfoReturnable<Float> cir) {
        float max = 1.5F;
        ItemStack itemStack = entityPatch.getOriginal().getMainHandItem();
        CapabilityItem capabilityItem = EpicFightCapabilities.getItemStackCapabilityOr(itemStack, CapabilityItem.EMPTY);
        if(!capabilityItem.isEmpty()) {
            if(capabilityItem.getWeaponCategory().equals(CapabilityItem.WeaponCategories.UCHIGATANA)
                    || capabilityItem.getWeaponCategory().equals(CapabilityItem.WeaponCategories.SWORD)) {
                max = 1.3F;
            }
            if(capabilityItem.getWeaponCategory().equals(CapabilityItem.WeaponCategories.UCHIGATANA)) {
                max = 1.05F;
            }
            if(itemStack.is(SGItems.GOLEM_HEART.get())) {
                max = 1.2F;
            }
        }
        cir.setReturnValue(Math.min(max, cir.getReturnValue()));
    }

}
