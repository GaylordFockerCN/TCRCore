package com.p1nero.tcrcore.item.custom;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SimpleDescriptionItem extends Item {
    boolean foil;
    public SimpleDescriptionItem(Properties properties) {
        this(properties, false);
    }
    public SimpleDescriptionItem(Properties properties, boolean foil) {
        super(properties);
        this.foil = foil;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, @NotNull List<Component> list, @NotNull TooltipFlag flag) {
        super.appendHoverText(itemStack, level, list, flag);
        list.add(Component.translatable(this.getDescriptionId() + ".usage").withStyle(ChatFormatting.GRAY));
    }

    @Override
    public boolean isFoil(@NotNull ItemStack itemStack) {
        return foil;
    }

}
