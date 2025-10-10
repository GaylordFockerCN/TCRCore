package com.p1nero.tcrcore.block.entity;

import com.github.L_Ender.cataclysm.init.ModItems;
import com.github.L_Ender.cataclysm.init.ModParticle;
import com.p1nero.tcrcore.TCRCoreMod;
import com.p1nero.tcrcore.capability.PlayerDataManager;
import com.p1nero.tcrcore.save_data.TCRMainLevelSaveData;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;

public class VoidAltarBlockEntity extends AbstractAltarBlockEntity {
    public VoidAltarBlockEntity(BlockPos pos, BlockState blockState) {
        super(TCRBlockEntities.VOID_ALTAR_BLOCK_ENTITY.get(), pos, blockState, ModItems.VOID_EYE.get());
    }

    @Override
    protected ParticleOptions getSpawnerParticle() {
        return ParticleTypes.DRAGON_BREATH;
    }

    @Override
    public boolean checkBossKilled(Player player) {
        return PlayerDataManager.voidEyeKilled.get(player);
    }

    @Override
    public boolean checkEyeFound(Player player) {
        return PlayerDataManager.voidEyeTraded.get(player);
    }

    protected void playUseEyeTip(Player player) {
        player.displayClientMessage(TCRCoreMod.getInfo("use_true_eye_tip", Component.literal("???").withStyle(ChatFormatting.GOLD)), true);
    }

    @Override
    public int getColor() {
        return ChatFormatting.LIGHT_PURPLE.getColor();
    }
}
