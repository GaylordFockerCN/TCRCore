package com.p1nero.tcrcore.mixin;

import com.p1nero.tcrcore.TCRCoreMod;
import com.talhanation.smallships.world.entity.ship.Ship;
import net.minecraft.ChatFormatting;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Ship.class)
public abstract class ShipMixin extends Boat {
    public ShipMixin(EntityType<? extends Boat> p_38290_, Level p_38291_) {
        super(p_38290_, p_38291_);
    }

    @Shadow
    public abstract void destroy(@NotNull DamageSource damageSource);

    @Inject(method = "hurt", at = @At("HEAD"))
    private void tcr$hurt(DamageSource damageSource, float f, CallbackInfoReturnable<Boolean> cir) {
        if(damageSource.getEntity() instanceof Player player) {
            player.displayClientMessage(TCRCoreMod.getInfo("shift_to_pic").withStyle(ChatFormatting.GOLD), true);
            if(!player.level().isClientSide) {
                if(player.isShiftKeyDown()) {
                    this.destroy(damageSource);
                }
            }
        }
    }
}
