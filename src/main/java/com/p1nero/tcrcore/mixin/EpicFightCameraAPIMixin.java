package com.p1nero.tcrcore.mixin;

import com.p1nero.tcrcore.capability.PlayerDataManager;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import yesman.epicfight.api.client.camera.EpicFightCameraAPI;

@Mixin(EpicFightCameraAPI.class)
public class EpicFightCameraAPIMixin {

    @Inject(method = "toggleLockOn", at = @At("HEAD"), remap = false)
    private void tcr$setLockOn(CallbackInfo ci) {
        if(Minecraft.getInstance().player != null) {
            PlayerDataManager.locked.put(Minecraft.getInstance().player, true);
        }
    }
}
