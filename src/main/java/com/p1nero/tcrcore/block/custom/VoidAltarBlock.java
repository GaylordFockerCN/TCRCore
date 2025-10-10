package com.p1nero.tcrcore.block.custom;

import com.p1nero.tcrcore.block.entity.AbyssAltarBlockEntity;
import com.p1nero.tcrcore.block.entity.VoidAltarBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class VoidAltarBlock extends AbstractAltarBlock {
    public VoidAltarBlock(Properties properties) {
        super(properties);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState blockState) {
        return new VoidAltarBlockEntity(pos, blockState);
    }
}
