package com.p1nero.tcrcore.mixin;

import com.p1nero.dialog_lib.DialogueLib;
import com.p1nero.tcrcore.capability.PlayerDataManager;
import com.p1nero.tcrcore.capability.TCRCapabilityProvider;
import com.p1nero.tcrcore.capability.TCRTaskManager;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.shelmarow.nightfall_invade.entity.spear_knight.Arterius;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Arterius.class)
public class ArteriusMixin extends PathfinderMob {
    @Shadow(remap = false)
    private boolean inBattle;

    protected ArteriusMixin(EntityType<? extends PathfinderMob> p_21683_, Level p_21684_) {
        super(p_21683_, p_21684_);
    }

    @Inject(method = "mobInteract", at = @At("HEAD"))
    private void tcr$mobInteract(Player pPlayer, InteractionHand pHand, CallbackInfoReturnable<InteractionResult> cir) {
        if(!this.inBattle && pPlayer instanceof ServerPlayer serverPlayer) {
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
