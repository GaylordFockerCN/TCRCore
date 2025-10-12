package com.p1nero.tcrcore.mixin;

import com.github.L_Ender.cataclysm.init.ModItems;
import com.p1nero.dialog_lib.DialogueLib;
import com.p1nero.tcrcore.TCRCoreMod;
import com.p1nero.tcrcore.capability.PlayerDataManager;
import com.p1nero.tcrcore.capability.TCRCapabilityProvider;
import com.p1nero.tcrcore.gameassets.TCRSkills;
import com.p1nero.tcrcore.utils.ItemUtil;
import com.p1nero.tcrcore.utils.WorldUtil;
import com.yesman.epicskills.skilltree.SkillTree;
import com.yesman.epicskills.world.capability.SkillTreeProgression;
import com.yungnickyoung.minecraft.yungsapi.criteria.SafeStructureLocationPredicate;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.npc.VillagerData;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Objects;

@Mixin(Villager.class)
public abstract class VillagerMixin extends AbstractVillager {

    @Shadow public abstract VillagerData getVillagerData();

    public VillagerMixin(EntityType<? extends AbstractVillager> p_35267_, Level p_35268_) {
        super(p_35267_, p_35268_);
    }

    @Inject(method = "mobInteract", at = @At("HEAD"), cancellable = true)
    private void tcr$mobInteract(Player player, InteractionHand hand, CallbackInfoReturnable<InteractionResult> cir) {
        if (player instanceof ServerPlayer serverPlayer && hand == InteractionHand.MAIN_HAND) {
            this.playAmbientSound();
            TCRCapabilityProvider.getTCRPlayer(serverPlayer).setCurrentTalkingEntity(this);
            ItemStack mainHand = player.getMainHandItem();
            if (mainHand.is(Items.EMERALD)) {
                CommandSourceStack commandSourceStack = serverPlayer.createCommandSourceStack().withPermission(2).withSuppressedOutput();
                if(!PlayerDataManager.waterAvoidUnlocked.get(serverPlayer) && WorldUtil.isInStructure(serverPlayer, WorldUtil.COVES)) {
                    Objects.requireNonNull(serverPlayer.getServer()).getCommands().performPrefixedCommand(commandSourceStack, "/skilltree unlock @s dodge_parry_reward:passive tcrcore:water_avoid true");
                    serverPlayer.displayClientMessage(TCRCoreMod.getInfo("unlock_new_skill", Component.translatable(TCRSkills.WATER_AVOID.getTranslationKey()).withStyle(ChatFormatting.AQUA)), false);
                    level().playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.UI_TOAST_CHALLENGE_COMPLETE, SoundSource.PLAYERS, 1.0F, 1.0F);
                    PlayerDataManager.waterAvoidUnlocked.put(serverPlayer, true);
                } else if(!PlayerDataManager.swordSoaringUnlocked.get(serverPlayer) && WorldUtil.isInStructure(serverPlayer, WorldUtil.SKY_ISLAND)) {
                    Objects.requireNonNull(serverPlayer.getServer()).getCommands().performPrefixedCommand(commandSourceStack, "/skilltree unlock @s sword_soaring:sword_soaring_skills");
                    serverPlayer.displayClientMessage(TCRCoreMod.getInfo("unlock_new_skill_page").withStyle(ChatFormatting.AQUA), false);
                    level().playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.UI_TOAST_CHALLENGE_COMPLETE, SoundSource.PLAYERS, 1.0F, 1.0F);
                    PlayerDataManager.swordSoaringUnlocked.put(serverPlayer, true);
                } else {
                    return;
                }
                mainHand.shrink(1);
                CompoundTag tag = new CompoundTag();
                tag.putBoolean("from_trade", true);
                DialogueLib.sendDialog(this, tag, serverPlayer);
                cir.setReturnValue(InteractionResult.SUCCESS);
            } else {
                DialogueLib.sendDialog(this, serverPlayer);
                cir.setReturnValue(InteractionResult.SUCCESS);
            }
        }
        cir.setReturnValue(InteractionResult.PASS);
    }
}
