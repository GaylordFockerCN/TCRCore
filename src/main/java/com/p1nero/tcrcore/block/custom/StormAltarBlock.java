package com.p1nero.tcrcore.block.custom;

import com.p1nero.tcrcore.block.entity.StormAltarBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class StormAltarBlock extends AbstractAltarBlock {
    public StormAltarBlock(Properties properties) {
        super(properties);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState blockState) {
        return new StormAltarBlockEntity(pos, blockState);
    }
}
