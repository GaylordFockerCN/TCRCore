package com.p1nero.tcrcore.mixin;

import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import yesman.epicfight.api.animation.types.EntityState;
import yesman.epicfight.skill.dodge.DodgeSkill;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;

@Mixin(DodgeSkill.class)
public class DodgeSkillMixin {

    @Inject(method = "isExecutableState", at = @At("HEAD"), remap = false, cancellable = true)
    private void tcr$isExecutableState(PlayerPatch<?> executor, CallbackInfoReturnable<Boolean> cir) {
        EntityState playerState = executor.getEntityState();
        Level level  = executor.getOriginal().level();
        BlockState blockState = level.getBlockState(executor.getOriginal().getOnPos().below());
        boolean toReturn = !executor.isInAir()
                && playerState.canUseSkill()
                && !(executor.getOriginal().isInWater() && (blockState.isAir() || blockState.is(Blocks.WATER) || blockState.is(Blocks.LAVA)))
                && !executor.getOriginal().onClimbable()
                && executor.getOriginal().getVehicle() == null;
        cir.setReturnValue(toReturn);
    }

}
