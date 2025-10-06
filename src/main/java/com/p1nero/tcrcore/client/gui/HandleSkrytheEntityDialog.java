package com.p1nero.tcrcore.client.gui;

import com.p1nero.dialog_lib.client.screen.DialogueScreenBuilder;
import net.alp.monsterexpansion.entity.custom.SkrytheEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class HandleSkrytheEntityDialog {

    public static void openDialogScreen(SkrytheEntity self, LocalPlayer player, CompoundTag serverData) {
        DialogueScreenBuilder treeBuilder = new DialogueScreenBuilder(self);
        treeBuilder.start(0)
                .addChoice(0, 1)
                .addFinalChoice(1, 1);
        Minecraft.getInstance().setScreen(treeBuilder.build());
    }

}
