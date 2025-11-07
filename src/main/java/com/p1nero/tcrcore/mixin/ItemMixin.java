package com.p1nero.tcrcore.mixin;

import com.p1nero.tcrcore.events.ItemEvents;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Item.class)
public abstract class ItemMixin {

    @Shadow
    public abstract String toString();

    @Inject(method = {"getName", "m_7626_"}, at = @At("RETURN"), cancellable = true, remap = false)
    private void tcr$getDesc(ItemStack itemStack, CallbackInfoReturnable<Component> cir){
        if(ItemEvents.eyes.contains((Item) (Object) this)){
            cir.setReturnValue(cir.getReturnValue().copy().withStyle(ChatFormatting.GOLD));
        }
    }
}
