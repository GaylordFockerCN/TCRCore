package com.p1nero.tcrcore.client.gui;

import com.p1nero.dialog_lib.client.screen.builder.StreamDialogueScreenBuilder;
import com.p1nero.tcrcore.TCRCoreMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class HandleIronGolemDialog {

    public static void openDialogScreen(IronGolem self, LocalPlayer player, CompoundTag serverData) {
        StreamDialogueScreenBuilder treeBuilder = new StreamDialogueScreenBuilder(self, TCRCoreMod.MOD_ID);
        treeBuilder.start(0)
                .addFinalOption(0, 1)
                .addFinalOption(1);
        Minecraft.getInstance().setScreen(treeBuilder.build());
    }

}
