package com.p1nero.tcrcore.mixin;

import com.hm.efn.registries.EFNItem;
import net.minecraft.world.entity.monster.piglin.PiglinAi;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.extensions.IForgeItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(IForgeItem.class)
public interface IForgetItemMixin {

    /**
     * @author P1nero
     * @reason 添加猪灵交易
     */
    @Overwrite(remap = false)
    default boolean isPiglinCurrency(ItemStack stack) {
        Item item = stack.getItem();
        return item == PiglinAi.BARTERING_ITEM || item == EFNItem.DUSKFIRE_INGOT.get();
    }

}