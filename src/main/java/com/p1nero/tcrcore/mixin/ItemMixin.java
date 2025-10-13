package com.p1nero.tcrcore.mixin;

import com.github.L_Ender.cataclysm.init.ModItems;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(Item.class)
public abstract class ItemMixin {

    @Shadow
    public abstract String toString();

    @Inject(method = "getName", at = @At("RETURN"), cancellable = true)
    private void tcr$getDesc(ItemStack itemStack, CallbackInfoReturnable<Component> cir){
        if(List.of(ModItems.MONSTROUS_EYE.get(), ModItems.VOID_EYE.get(), ModItems.MECH_EYE.get(), ModItems.ABYSS_EYE.get(), ModItems.STORM_EYE.get(), ModItems.CURSED_EYE.get(), ModItems.FLAME_EYE.get(), ModItems.DESERT_EYE.get())
                .contains((Item) (Object) this)){
            cir.setReturnValue(cir.getReturnValue().copy().withStyle(ChatFormatting.GOLD));
        }
    }
}
