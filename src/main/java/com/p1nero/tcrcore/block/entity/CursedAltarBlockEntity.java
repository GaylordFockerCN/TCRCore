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

public class CursedAltarBlockEntity extends AbstractAltarBlockEntity {
    public CursedAltarBlockEntity(BlockPos pos, BlockState blockState) {
        super(TCRBlockEntities.CURSED_ALTAR_BLOCK_ENTITY.get(), pos, blockState, ModItems.CURSED_EYE.get());
    }

    @Override
    protected void onActive(Player pPlayer, ItemStack mainHandItem, ServerLevel pLevel, BlockPos pPos) {
        super.onActive(pPlayer, mainHandItem, pLevel, pPos);
        TCRMainLevelSaveData.get(pLevel).setCursedFinish(true);
    }

    @Override
    protected ParticleOptions getSpawnerParticle() {
        return ModParticle.CURSED_FLAME.get();
    }

    @Override
    public boolean checkBossKilled(Player player) {
        return PlayerDataManager.cursedEyeKilled.get(player);
    }

    @Override
    public int getColor() {
        return ChatFormatting.DARK_GREEN.getColor();
    }
}
