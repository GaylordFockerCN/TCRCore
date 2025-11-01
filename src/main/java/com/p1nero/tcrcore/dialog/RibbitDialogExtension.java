package com.p1nero.tcrcore.dialog;

import com.p1nero.dialog_lib.api.entity.EntityDialogueExtension;
import com.p1nero.dialog_lib.api.entity.IEntityDialogueExtension;
import com.p1nero.dialog_lib.client.screen.DialogueScreen;
import com.p1nero.dialog_lib.client.screen.builder.StreamDialogueScreenBuilder;
import com.p1nero.tcrcore.TCRCoreMod;
import com.p1nero.tcrcore.capability.PlayerDataManager;
import com.p1nero.tcrcore.gameassets.TCRSkills;
import com.yungnickyoung.minecraft.ribbits.entity.RibbitEntity;
import com.yungnickyoung.minecraft.ribbits.module.EntityTypeModule;
import net.minecraft.ChatFormatting;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Objects;

@EntityDialogueExtension(modId = TCRCoreMod.MOD_ID)
public class RibbitDialogExtension implements IEntityDialogueExtension<RibbitEntity> {

    @Override
    public EntityType<RibbitEntity> getEntityType() {
        return EntityTypeModule.RIBBIT.get();
    }

    @Override
    public boolean canInteractWith(Player player, RibbitEntity ribbitEntity) {
        return !PlayerDataManager.waterAvoidUnlocked.get(player);
    }

    @Override
    public InteractionResult shouldCancelInteract(Player player, RibbitEntity currentTalking, InteractionHand hand) {
        return (player.getMainHandItem().is(Items.AMETHYST_BLOCK)) ? InteractionResult.sidedSuccess(player.level().isClientSide) : null;
    }

    @Override
    public CompoundTag getServerData(ServerPlayer player, RibbitEntity currentTalking, InteractionHand hand, CompoundTag senderData) {
        if(player.getMainHandItem().is(Items.AMETHYST_BLOCK)) {
            senderData.putBoolean("hasAmethyst", true);
        }
        return senderData;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public DialogueScreen getDialogScreen(StreamDialogueScreenBuilder streamDialogueScreenBuilder, LocalPlayer localPlayer, RibbitEntity ribbitEntity, CompoundTag compoundTag) {
        if(compoundTag.getBoolean("hasAmethyst")) {
            streamDialogueScreenBuilder.start(0).addFinalOption(0, 1);
        }
        return streamDialogueScreenBuilder.build();
    }

    @Override
    public void handleNpcInteraction(RibbitEntity ribbitEntity, ServerPlayer player, int i) {
        //解锁避水咒
        if(i == 1) {
            if(!PlayerDataManager.waterAvoidUnlocked.get(player)) {
                CommandSourceStack commandSourceStack = player.createCommandSourceStack().withPermission(2).withSuppressedOutput();
                player.getMainHandItem().shrink(1);
                Objects.requireNonNull(player.getServer()).getCommands().performPrefixedCommand(commandSourceStack, "/skilltree unlock @s dodge_parry_reward:passive tcrcore:water_avoid true");
                player.displayClientMessage(TCRCoreMod.getInfo("unlock_new_skill", Component.translatable(TCRSkills.WATER_AVOID.getTranslationKey()).withStyle(ChatFormatting.AQUA)), false);
                player.level().playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.UI_TOAST_CHALLENGE_COMPLETE, SoundSource.PLAYERS, 1.0F, 1.0F);
                PlayerDataManager.waterAvoidUnlocked.put(player, true);
            }
        }
    }
}
