package com.p1nero.tcrcore.network.packet.serverbound;

//import com.brass_amber.ba_bt.init.BTExtras;
import com.p1nero.dialog_lib.network.packet.BasePacket;
import com.p1nero.tcrcore.gameassets.TCRAnimations;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;

public record ExecuteRiptidePacket() implements BasePacket {

    @Override
    public void encode(FriendlyByteBuf buf) {
    }

    public static ExecuteRiptidePacket decode(FriendlyByteBuf buf){
        return new ExecuteRiptidePacket();
    }

    @Override
    public void execute(Player player) {
        if(player instanceof ServerPlayer serverPlayer){
            //TODO 高塔加进来后加回来
//            if(serverPlayer.hasEffect(BTExtras.DEPTH_DROPPER_EFFECT.get())){
//                return;
//            }
            ServerPlayerPatch serverPlayerPatch = EpicFightCapabilities.getEntityPatch(serverPlayer, ServerPlayerPatch.class);
            if(serverPlayerPatch != null && serverPlayer.isUnderWater() && serverPlayerPatch.getEntityState().canUseSkill()) {
                serverPlayerPatch.playAnimationSynchronized(TCRAnimations.TSUNAMI, -0.15F);
            }
        }
    }
}
