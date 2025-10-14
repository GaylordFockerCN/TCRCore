package com.p1nero.tcrcore.item.custom;

import com.p1nero.tcrcore.capability.TCRPlayer;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class OracleItem extends SimpleDescriptionItem{
    public OracleItem(Properties properties) {
        super(properties, true);
    }

    @Override
    public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, @NotNull List<Component> list, @NotNull TooltipFlag flag) {
        super.appendHoverText(itemStack, level, list, flag);
        if(level != null && level.isClientSide){
            if(!Minecraft.getInstance().isSingleplayer()) {
                list.add(Component.translatable(this.getDescriptionId() + ".usage2").withStyle(ChatFormatting.GRAY));
            }
        }
        if(itemStack.hasTag() && itemStack.getOrCreateTag().contains(TCRPlayer.PLAYER_NAME)) {
            list.add(Component.literal("——" + itemStack.getOrCreateTag().getString(TCRPlayer.PLAYER_NAME)).withStyle(ChatFormatting.AQUA));
        }
    }
}
