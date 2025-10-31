package com.p1nero.tcrcore.mixin;

import com.p1nero.tcrcore.TCRCoreMod;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.InputEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import yesman.epicfight.client.events.engine.ControlEngine;
import yesman.epicfight.client.input.EpicFightKeyMappings;

/**
 * 临时修原版挖掘模式无法交互
 */
@Mixin(ControlEngine.Events.class)
public class ControlEngineMixin {

    @Inject(method = "interactionEvent", at = @At("RETURN"), remap = false)
    private static void tcr$interactionEvent(InputEvent.InteractionKeyMappingTriggered event, CallbackInfo ci) {
        if(event.isCanceled()) {
            Minecraft.getInstance().player.displayClientMessage(TCRCoreMod.getInfo("weapon_no_interact", EpicFightKeyMappings.SWITCH_MODE.getTranslatedKeyMessage()).withStyle(ChatFormatting.GOLD), true);
        }
    }

}
