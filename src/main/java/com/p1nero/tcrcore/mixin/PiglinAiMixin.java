package com.p1nero.tcrcore.mixin;

import com.github.L_Ender.cataclysm.init.ModItems;
import net.minecraft.world.entity.monster.piglin.Piglin;
import net.minecraft.world.entity.monster.piglin.PiglinAi;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Collections;
import java.util.List;

@Mixin(PiglinAi.class)
public abstract class PiglinAiMixin {

    @Invoker("throwItems")
    public static void throwItems(Piglin p_34861_, List<ItemStack> p_34862_) {
        throw new AssertionError();
    }

    @Inject(method = "stopHoldingOffHandItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/monster/piglin/PiglinAi;throwItems(Lnet/minecraft/world/entity/monster/piglin/Piglin;Ljava/util/List;)V"), locals = LocalCapture.CAPTURE_FAILSOFT, cancellable = true)
    private static void stopHoldingOffHandItem(Piglin piglin, boolean p_34869_, CallbackInfo ci, ItemStack itemstack) {
        if (itemstack.getItem() == ModItems.MONSTROUS_HORN.get()) {
            throwItems(piglin, Collections.singletonList(ModItems.MONSTROUS_EYE.get().getDefaultInstance()));
            ci.cancel();
        }
    }
}
