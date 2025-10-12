package com.p1nero.tcrcore.mixin;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.shelmarow.nightfall_invade.entity.spear_knight.Arterius;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Arterius.class)
public class ArteriusMixin {
    @Inject(method = "mobInteract", at = @At("HEAD"))
    private void tcr$mobInteract(Player pPlayer, InteractionHand pHand, CallbackInfoReturnable<InteractionResult> cir) {
        if(pPlayer instanceof ServerPlayer serverPlayer) {
            
        }
    }
}
