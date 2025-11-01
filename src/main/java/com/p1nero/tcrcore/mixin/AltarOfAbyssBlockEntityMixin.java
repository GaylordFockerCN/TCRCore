package com.p1nero.tcrcore.mixin;

import com.github.L_Ender.cataclysm.blockentities.AltarOfAbyss_Block_Entity;
import com.github.L_Ender.cataclysm.init.ModItems;
import com.p1nero.tcrcore.TCRCoreMod;
import com.p1nero.tcrcore.utils.EntityUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AltarOfAbyss_Block_Entity.class)
public abstract class AltarOfAbyssBlockEntityMixin extends BaseContainerBlockEntity {

    protected AltarOfAbyssBlockEntityMixin(BlockEntityType<?> p_155076_, BlockPos p_155077_, BlockState p_155078_) {
        super(p_155076_, p_155077_, p_155078_);
    }

    @Inject(method = "setItem", at = @At("HEAD"), remap = false, cancellable = true)
    private void tcr$setItem(int index, ItemStack stack, CallbackInfo ci) {
        if(!stack.is(ModItems.ABYSSAL_SACRIFICE.get()) && this.level != null) {
            EntityUtil.nearPlayerDo(this.level, this.getBlockPos().getCenter(), 5, (player -> {
                player.displayClientMessage(TCRCoreMod.getInfo("require_item_to_wake", ModItems.ABYSSAL_SACRIFICE.get().getDescription()).withStyle(ChatFormatting.LIGHT_PURPLE), true);
            }));
            Block.popResource(this.level, this.getBlockPos(), stack.copy());
            ci.cancel();
        }
    }
}
