package com.p1nero.tcrcore.client.gui;

import com.p1nero.dialog_lib.client.screen.builder.StreamDialogueScreenBuilder;
import com.p1nero.tcrcore.TCRCoreMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.npc.Villager;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class HandleVillagerDialog {

    public static void openDialogScreen(Villager self, LocalPlayer player, CompoundTag serverData) {
        StreamDialogueScreenBuilder treeBuilder = new StreamDialogueScreenBuilder(self, TCRCoreMod.MOD_ID);
        if (serverData.getBoolean("from_trade")) {
            treeBuilder.start(-1)
                    .addFinalOption(-1);
        } else if (serverData.getBoolean("wrong_place")) {
            treeBuilder.start(-2)
                    .addFinalOption(-2);
        } else {
            treeBuilder.start(self.getRandom().nextInt(8))
                    .addFinalOption(self.getRandom().nextInt(3))
                    .addFinalOption(-3);
        }
        Minecraft.getInstance().setScreen(treeBuilder.build());
    }

}
