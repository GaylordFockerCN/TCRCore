package com.p1nero.tcrcore.client.gui;

import com.github.L_Ender.cataclysm.init.ModEntities;
import com.hm.efn.registries.EFNItem;
import com.p1nero.dialog_lib.api.component.DialogNode;
import com.p1nero.dialog_lib.api.component.DialogueComponentBuilder;
import com.p1nero.dialog_lib.client.screen.DialogueScreenBuilder;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.shelmarow.nightfall_invade.entity.spear_knight.Arterius;

@OnlyIn(Dist.CLIENT)
public class HandleArteriusDialog {

    public static void openDialogScreen(Arterius self, LocalPlayer player, CompoundTag serverData) {
        boolean arteriusKilled = serverData.getBoolean("arterius_killed");
        DialogueScreenBuilder treeBuilder = new DialogueScreenBuilder(self);
        DialogueComponentBuilder componentBuilder = treeBuilder.getComponentBuildr();
        DialogNode root;
        if(arteriusKilled) {
            root = treeBuilder.newNode(2)
                    .addChild(treeBuilder.newNode(3, 4)
                            .addChild(treeBuilder.newFinalNode(2, 1))
                            .addChild(treeBuilder.newFinalNode(3))
                    )
                    .addChild(new DialogNode(componentBuilder.ans(4,
                            ModEntities.NETHERITE_MONSTROSITY.get().getDescription().copy().withStyle(ChatFormatting.RED),
                            EFNItem.DUSKFIRE_INGOT.get().getDescription().copy().withStyle(ChatFormatting.GOLD),
                            EFNItem.DUSKFIRE_INGOT.get().getDescription().copy().withStyle(ChatFormatting.GOLD)), componentBuilder.opt(5))
                            .addChild(new DialogNode(componentBuilder.ans(5,
                                    EntityType.ENDER_DRAGON.getDescription().copy().withStyle(ChatFormatting.LIGHT_PURPLE),
                                    EntityType.WITHER.getDescription().copy().withStyle(ChatFormatting.RED),
                                    ModEntities.ENDER_GUARDIAN.get().getDescription().copy().withStyle(ChatFormatting.LIGHT_PURPLE),
                                    ModEntities.THE_HARBINGER.get().getDescription().copy().withStyle(ChatFormatting.DARK_RED)), componentBuilder.opt(6))
                                    .addLeaf(componentBuilder.opt(7)))
                    )

            ;
        } else {
            root = treeBuilder.newNode(0)
                    .addChild(treeBuilder.newNode(1, 0)
                            .addChild(treeBuilder.newFinalNode(2, 1))
                            .addChild(treeBuilder.newFinalNode(3)))
                    .addChild(treeBuilder.newNode(1, 1)
                            .addChild(treeBuilder.newFinalNode(2, 1))
                            .addChild(treeBuilder.newFinalNode(3)));
        }

        treeBuilder.setRoot(root);
        Minecraft.getInstance().setScreen(treeBuilder.build());
    }

}
