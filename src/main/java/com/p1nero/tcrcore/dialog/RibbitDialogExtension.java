package com.p1nero.tcrcore.dialog;

import com.p1nero.dialog_lib.api.EntityDialogueExtension;
import com.p1nero.dialog_lib.api.IEntityDialogueExtension;
import com.p1nero.dialog_lib.client.screen.DialogueScreenBuilder;
import com.p1nero.tcrcore.TCRCoreMod;
import com.p1nero.tcrcore.capability.PlayerDataManager;
import com.p1nero.tcrcore.gameassets.TCRSkills;
import com.p1nero.tcrcore.utils.WorldUtil;
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
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;

import java.util.Objects;

@EntityDialogueExtension
public class RibbitDialogExtension implements IEntityDialogueExtension<RibbitEntity> {

    @Override
    public EntityType<RibbitEntity> getEntityType() {
        return EntityTypeModule.RIBBIT.get();
    }

    @Override
    public boolean canInteractWith(Player player, RibbitEntity ribbitEntity) {
        return true;
    }

    @Override
    public boolean shouldCancelInteract(Player player, RibbitEntity currentTalking) {
//        return player.getItemInHand(); TODO 更新api后，判断物品是什么再说
        return true;
    }

    @Override
    public DialogueScreenBuilder getDialogBuilder(DialogueScreenBuilder dialogueScreenBuilder, LocalPlayer localPlayer, RibbitEntity ribbitEntity, CompoundTag compoundTag) {
        return null;//TODO 咕咕嘎嘎，解锁避水咒
    }

    @Override
    public void handleNpcInteraction(RibbitEntity ribbitEntity, ServerPlayer player, int i) {
        //解锁避水咒
        if(i == 1) {
            CommandSourceStack commandSourceStack = player.createCommandSourceStack().withPermission(2).withSuppressedOutput();
            if(!PlayerDataManager.waterAvoidUnlocked.get(player) && WorldUtil.isInStructure(player, WorldUtil.COVES)) {
                Objects.requireNonNull(player.getServer()).getCommands().performPrefixedCommand(commandSourceStack, "/skilltree unlock @s dodge_parry_reward:passive tcrcore:water_avoid true");
                player.displayClientMessage(TCRCoreMod.getInfo("unlock_new_skill", Component.translatable(TCRSkills.WATER_AVOID.getTranslationKey()).withStyle(ChatFormatting.AQUA)), false);
                player.level().playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.UI_TOAST_CHALLENGE_COMPLETE, SoundSource.PLAYERS, 1.0F, 1.0F);
                PlayerDataManager.waterAvoidUnlocked.put(player, true);
            }
        }
    }
}
