package com.p1nero.tcrcore.block.entity;

import com.github.L_Ender.cataclysm.init.ModItems;
import com.github.L_Ender.cataclysm.init.ModParticle;
import com.p1nero.tcrcore.capability.PlayerDataManager;
import com.p1nero.tcrcore.save_data.TCRMainLevelSaveData;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;

public class StormAltarBlockEntity extends AbstractAltarBlockEntity {
    public StormAltarBlockEntity(BlockPos pos, BlockState blockState) {
        super(TCRBlockEntities.STORM_ALTAR_BLOCK_ENTITY.get(), pos, blockState, ModItems.STORM_EYE.get());
    }

    @Override
    protected void onActive(Player pPlayer, ItemStack mainHandItem, ServerLevel pLevel, BlockPos pPos) {
        super.onActive(pPlayer, mainHandItem, pLevel, pPos);
        TCRMainLevelSaveData.get(pLevel).setStormFinish(true);
    }

    @Override
    protected ParticleOptions getSpawnerParticle() {
        return ModParticle.SPARK.get();
    }

    @Override
    public boolean checkBossKilled(Player player) {
        return PlayerDataManager.stormEyeKilled.get(player);
    }

    @Override
    public int getColor() {
        return ChatFormatting.AQUA.getColor();
    }

}
