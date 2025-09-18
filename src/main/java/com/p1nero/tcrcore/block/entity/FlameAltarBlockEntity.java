package com.p1nero.tcrcore.block.entity;

import com.github.L_Ender.cataclysm.init.ModItems;
import com.p1nero.tcrcore.save_data.TCRLevelSaveData;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;

public class FlameAltarBlockEntity extends AbstractAltarBlockEntity {
    public FlameAltarBlockEntity(BlockPos pos, BlockState blockState) {
        super(TCRBlockEntities.FLAME_ALTAR_BLOCK_ENTITY.get(), pos, blockState, ModItems.FLAME_EYE.get());
    }

    @Override
    protected void onActive(Player pPlayer, ItemStack mainHandItem, ServerLevel pLevel, BlockPos pPos) {
        super.onActive(pPlayer, mainHandItem, pLevel, pPos);
        TCRLevelSaveData.get(pLevel).setFlameFinish(true);
    }
}
