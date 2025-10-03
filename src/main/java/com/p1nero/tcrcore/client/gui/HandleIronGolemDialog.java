package com.p1nero.tcrcore.client.gui;

import com.p1nero.dialog_lib.client.screen.DialogueScreenBuilder;
import com.p1nero.tcrcore.mixin.IronGolemMixin;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.npc.Villager;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class HandleIronGolemDialog {

    public static void openDialogScreen(IronGolem self, LocalPlayer player, CompoundTag serverData) {
        DialogueScreenBuilder treeBuilder = new DialogueScreenBuilder(self);
        treeBuilder.start(0)
                .addFinalChoice(0, 1)
                .addFinalChoice(1);
        Minecraft.getInstance().setScreen(treeBuilder.build());
    }

}
