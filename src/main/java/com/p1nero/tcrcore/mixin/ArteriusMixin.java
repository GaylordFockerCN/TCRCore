package com.p1nero.tcrcore.mixin;

import com.github.L_Ender.cataclysm.init.ModItems;
import com.hm.efn.registries.EFNItem;
import com.p1nero.dialog_lib.DialogueLib;
import com.p1nero.tcrcore.TCRCoreMod;
import com.p1nero.tcrcore.capability.PlayerDataManager;
import com.p1nero.tcrcore.capability.TCRCapabilityProvider;
import com.p1nero.tcrcore.capability.TCRTaskManager;
import com.p1nero.tcrcore.utils.ItemUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.shelmarow.nightfall_invade.entity.NFIEntities;
import net.shelmarow.nightfall_invade.entity.spear_knight.Arterius;
import net.shelmarow.nightfall_invade.entity.spear_knight.ArteriusPatch;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;

@Mixin(Arterius.class)
public abstract class ArteriusMixin extends PathfinderMob {
    @Shadow(remap = false)
    private boolean inBattle;

    @Shadow(remap = false)
    public abstract void resetBossStatus(boolean resetHealth);

    @Shadow(remap = false)
    public abstract void setInBattle(boolean inBattle);

    @Shadow(remap = false)
    public abstract void backToHomePos();

    protected ArteriusMixin(EntityType<? extends PathfinderMob> p_21683_, Level p_21684_) {
        super(p_21683_, p_21684_);
    }

    @Inject(method = "mobInteract", at = @At("HEAD"))
    private void tcr$mobInteract(Player pPlayer, InteractionHand pHand, CallbackInfoReturnable<InteractionResult> cir) {
        if(pPlayer instanceof ServerPlayer serverPlayer) {
            if(!PlayerDataManager.cursedEyeBlessed.get(pPlayer)) {
                pPlayer.displayClientMessage(TCRCoreMod.getInfo("can_not_do_this_too_early"), true);
                return;
            }
            if(!this.inBattle && serverPlayer.isAlive()) {
                TCRTaskManager.FIND_ARTERIUS.finish(serverPlayer);
                TCRCapabilityProvider.getTCRPlayer(serverPlayer).setCurrentTalkingEntity(this);
                CompoundTag tag = new CompoundTag();
                tag.putBoolean("can_get_invite", PlayerDataManager.canGetInvite(serverPlayer));
                tag.putBoolean("invite_get", PlayerDataManager.letterGet.get(pPlayer));
                tag.putBoolean("arterius_killed", PlayerDataManager.arteriusKilled.get(pPlayer));
                DialogueLib.sendDialog(this, tag, serverPlayer);
            }
        }
    }

    @Inject(method = "die", at = @At("HEAD"), cancellable = true)
    private void tcr$die(DamageSource cause, CallbackInfo ci) {
        if(cause.isCreativePlayer()) {
            return;
        }
        if(!level().isClientSide) {
            this.backToHomePos();
            this.resetBossStatus(true);
            this.setInBattle(false);
            EpicFightCapabilities.getUnparameterizedEntityPatch(this, ArteriusPatch.class).ifPresent(arteriusPatch -> {
                arteriusPatch.playAnimation(Animations.GREATSWORD_GUARD_BREAK, 3);
            });
            Vec3 center = this.position();
            level().getEntitiesOfClass(ServerPlayer.class, (new AABB(center, center)).inflate(30)).forEach(player -> {
                if(!PlayerDataManager.flameEyeTraded.get(player) && PlayerDataManager.cursedEyeBlessed.get(player)) {
                    ItemUtil.addItemEntity(player, ModItems.FLAME_EYE.get(), 1, ChatFormatting.RED.getColor().intValue());
                }
                player.displayClientMessage(TCRCoreMod.getInfo("kill_arterius", NFIEntities.ARTERIUS.get().getDescription().copy().withStyle(ChatFormatting.RED), EFNItem.DUSKFIRE_INGOT.get().getDescription()), false);
                ItemUtil.addItemEntity(player, EFNItem.DUSKFIRE_INGOT.get(), 2 + this.getRandom().nextInt(3), ChatFormatting.RED.getColor());
                if(!PlayerDataManager.arteriusKilled.get(player)) {
                    PlayerDataManager.arteriusKilled.put(player, true);
                }
            });
        }
        ci.cancel();
    }
}
