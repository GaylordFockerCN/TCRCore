package com.p1nero.tcrcore.client.gui;

import com.p1nero.dialog_lib.client.screen.DialogueScreenBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.npc.Villager;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class HandleVillagerDialog {

    public static void openDialogScreen(Villager self, LocalPlayer player, CompoundTag serverData) {
        DialogueScreenBuilder treeBuilder = new DialogueScreenBuilder(self);
        if (serverData.getBoolean("from_trade")) {
            treeBuilder.start(-1)
                    .addFinalChoice(-1);
        } else if (serverData.getBoolean("wrong_place")) {
            treeBuilder.start(-2)
                    .addFinalChoice(-2);
        } else {
            treeBuilder.start(self.getRandom().nextInt(8))
                    .addFinalChoice(self.getRandom().nextInt(3))
                    .addFinalChoice(-3);
        }
        Minecraft.getInstance().setScreen(treeBuilder.build());
    }

}
