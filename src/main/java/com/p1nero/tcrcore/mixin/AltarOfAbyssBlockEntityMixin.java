package com.p1nero.tcrcore.mixin;

import com.github.L_Ender.cataclysm.blockentities.AltarOfAbyss_Block_Entity;
import com.github.L_Ender.cataclysm.init.ModItems;
import com.p1nero.tcrcore.utils.EntityUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AltarOfAbyss_Block_Entity.class)
public class AltarOfAbyssBlockEntityMixin {

    @Shadow(remap = false)
    public boolean summoningthis;

    @Shadow(remap = false)
    public int tickCount;

    @Inject(method = "tick", at = @At("HEAD"), remap = false)
    private void tcr$tick(Level level, BlockState state, BlockPos pos, CallbackInfo ci) {
        if(!summoningthis) {
            if(tickCount % 30 == 0){
                EntityUtil.nearPlayerDo(level, pos.getCenter(), 3, (player -> {
                    player.displayClientMessage(Component.literal("[").append(ModItems.ABYSSAL_SACRIFICE.get().getDescription().copy().append("]...")).withStyle(ChatFormatting.LIGHT_PURPLE), true);
                }));
            }
        }
    }
}
