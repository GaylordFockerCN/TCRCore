package com.p1nero.tcrcore.mixin;

import com.chaosthedude.explorerscompass.ExplorersCompass;
import com.chaosthedude.naturescompass.NaturesCompass;
import com.p1nero.dialog_lib.api.IEntityNpc;
import com.p1nero.dialog_lib.client.screen.DialogueScreenBuilder;
import com.p1nero.tcrcore.TCRCoreMod;
import com.p1nero.tcrcore.capability.PlayerDataManager;
import com.p1nero.tcrcore.utils.ItemUtil;
import com.p1nero.tudigong.entity.TudiGongEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(TudiGongEntity.class)
public abstract class TuDiGongMixin implements IEntityNpc {

    @Shadow(remap = false)
    public abstract boolean canInteract();

    @Shadow(remap = false)
    public abstract void setMarkRemoved();

    @Inject(method = "mobInteract", at = @At("HEAD"), cancellable = true)
    private void tcr$mobInteract(Player player, InteractionHand hand, CallbackInfoReturnable<InteractionResult> cir) {
        if (this.canInteract() && player instanceof ServerPlayer serverPlayer) {
            CompoundTag data = new CompoundTag();
            data.putBoolean("giftGet", PlayerDataManager.tudigongGiftGet.get(player));
            this.sendDialogTo(serverPlayer, data);
        }
        cir.setReturnValue(InteractionResult.CONSUME);
    }

    @OnlyIn(Dist.CLIENT)
    @Inject(method = "getDialogueBuilder", at = @At("HEAD"), remap = false, cancellable = true)
    private void tcr$getDialogBuilder(CompoundTag compoundTag, CallbackInfoReturnable<DialogueScreenBuilder> cir) {
        if(!compoundTag.getBoolean("giftGet")) {
            DialogueScreenBuilder builder = new DialogueScreenBuilder((TudiGongEntity)(Object)this);
            builder.start(1)
                    .addFinalChoice(TCRCoreMod.getInfo("tudigong_gift"), 111);
            cir.setReturnValue(builder);
        }
    }

    @Inject(method = "handleNpcInteraction", at = @At("HEAD"), remap = false)
    private void tcr$handle(ServerPlayer serverPlayer, int i, CallbackInfo ci) {
        if(i == 111) {
            if(!PlayerDataManager.tudigongGiftGet.get(serverPlayer)) {
                ItemUtil.addItemEntity(serverPlayer, new ItemStack(ExplorersCompass.explorersCompass));
                ItemUtil.addItemEntity(serverPlayer, new ItemStack(NaturesCompass.naturesCompass));
                serverPlayer.displayClientMessage(TCRCoreMod.getInfo("tudigong_gift_get"), false);
                PlayerDataManager.tudigongGiftGet.put(serverPlayer, true);
                this.setMarkRemoved();
            }
        }
    }
}
