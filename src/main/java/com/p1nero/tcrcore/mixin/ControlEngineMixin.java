package com.p1nero.tcrcore.mixin;

import com.p1nero.tcrcore.TCRCoreMod;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.client.event.InputEvent;
import org.apache.commons.lang3.mutable.MutableBoolean;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import yesman.epicfight.client.events.engine.ControlEngine;
import yesman.epicfight.client.input.EpicFightKeyMappings;
import yesman.epicfight.client.world.capabilites.entitypatch.player.LocalPlayerPatch;
import yesman.epicfight.config.ClientConfig;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.skill.SkillSlots;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;

/**
 * 临时修原版挖掘模式无法交互
 */
@Mixin(ControlEngine.Events.class)
public class ControlEngineMixin {

//    @Inject(method = "interactionEvent", at = @At("HEAD"), cancellable = true, remap = false)
//    private static void tcr$interactionEvent(InputEvent.InteractionKeyMappingTriggered event, CallbackInfo ci) {
//        if (Minecraft.getInstance().player == null) {
//            ci.cancel();
//        }
//
//        if (
//                event.getKeyMapping() == Minecraft.getInstance().options.keyAttack &&
//                        EpicFightKeyMappings.ATTACK.getKey() == Minecraft.getInstance().options.keyAttack.getKey() &&
//                        Minecraft.getInstance().hitResult.getType() == HitResult.Type.BLOCK &&
//                        ClientConfig.combatPreferredItems.contains(Minecraft.getInstance().player.getMainHandItem().getItem())
//        ) {
//            BlockPos bp = ((BlockHitResult) Minecraft.getInstance().hitResult).getBlockPos();
//            BlockState bs = Minecraft.getInstance().level.getBlockState(bp);
//
//            // Cancel digging when the player swings combat preferred items
//            if (!Minecraft.getInstance().player.getMainHandItem().getItem().canAttackBlock(bs, Minecraft.getInstance().player.level(), bp, Minecraft.getInstance().player) || Minecraft.getInstance().player.getMainHandItem().getDestroySpeed(bs) <= 1.0F) {
//                event.setSwingHand(false);
//                event.setCanceled(true);
//            }
//        }
//
//        if (
//                event.getKeyMapping() == Minecraft.getInstance().options.keyUse &&
//                        event.getKeyMapping().getKey().equals(EpicFightKeyMappings.GUARD.getKey())
//        ) {
//            MutableBoolean canGuard = new MutableBoolean();
//            MutableBoolean vanillaMode = new MutableBoolean();
//
//            EpicFightCapabilities.getUnparameterizedEntityPatch(Minecraft.getInstance().player, LocalPlayerPatch.class).ifPresent(playerpatch -> {
//                SkillContainer skillcontainer = playerpatch.getSkill(SkillSlots.GUARD);
//                if(playerpatch.getPlayerMode() == PlayerPatch.PlayerMode.VANILLA) {
//                    vanillaMode.setValue(true);
//                }
//                if (skillcontainer.getSkill() != null && skillcontainer.getSkill().canExecute(skillcontainer)) {
//                    canGuard.setValue(true);
//                }
//            });
//
//            if(!vanillaMode.getValue()) {
//                if (Minecraft.getInstance().hitResult.getType() == HitResult.Type.MISS) {
//                    if (canGuard.booleanValue() && ClientConfig.keyConflictResolveScope.cancelItemUse()) {
//                        event.setSwingHand(false);
//                        event.setCanceled(true);
//                    }
//                } else {
//                    if (canGuard.booleanValue() && Minecraft.getInstance().hitResult.getType() == HitResult.Type.BLOCK) {
//                        event.setSwingHand(false);
//                        event.setCanceled(true);
////                        Minecraft.getInstance().player.displayClientMessage(TCRCoreMod.getInfo("weapon_no_interact", EpicFightKeyMappings.SWITCH_MODE.getTranslatedKeyMessage()).withStyle(ChatFormatting.GOLD), true);
//                    }
//                }
//            }
//        }
//        ci.cancel();
//    }

}
