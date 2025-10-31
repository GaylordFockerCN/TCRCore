package com.p1nero.tcrcore.utils;

import com.google.common.collect.ImmutableList;
import com.p1nero.tcrcore.TCRCoreMod;
import com.p1nero.tcrcore.entity.custom.CustomColorItemEntity;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.List;
import java.util.function.Predicate;

/**
 * @author P1nero
 * 做一些通用的物品栏处理
*/
public class ItemUtil {

    public static List<NonNullList<ItemStack>> getCompartments(Player player){
        return ImmutableList.of(player.getInventory().items, player.getInventory().armor, player.getInventory().offhand);
    };

    /**
     * 判断是否全穿了
     */
    public static boolean isFullSets(Entity entity, ObjectArrayList<?> objects){
        return isFullSets(entity, objects, 4);
    }

    /**
     * 判断穿了几件
     * @param need 集齐了几套
     */
    public static boolean isFullSets(Entity entity, ObjectArrayList<?> objects, int need){
        int cnt = 0;
        for (ItemStack stack : entity.getArmorSlots()) {
            if (stack.isEmpty()){
                continue;
            }
            if(objects.contains(stack.getItem())){
                cnt++;
            }
        }
        return cnt >= need;
    }

    /**
     * 添加物品，失败则掉落
     */
    public static void addItem(Player player, Item item, int count){
        addItem(player, item, count, false);
    }

    public static void addItem(Player player, ItemStack item){
        addItem(player, item, false);
    }

    public static void addItem(Player player, ItemStack itemStack, boolean showTip){
        if(!player.level().isClientSide && showTip) {
            player.displayClientMessage(TCRCoreMod.getInfo("add_item_tip", itemStack.getDisplayName(), itemStack.getCount()), false);
            player.level().playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ITEM_PICKUP, player.getSoundSource(), 1.0F, 1.0F);
        }
        if(!player.addItem(itemStack)){
            addItemEntity(player, itemStack);
        }
//        player.getInventory().placeItemBackInInventory(itemStack);
    }

    /**
     * 是否是需要加倍翻倍的奖励
     */
    public static void addItem(Player player, Item item, int count, boolean showTip){
        if(!player.level().isClientSide && showTip) {
            player.displayClientMessage(TCRCoreMod.getInfo("add_item_tip", item.getDefaultInstance().getDisplayName(), count), false);
            player.level().playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ITEM_PICKUP, player.getSoundSource(), 1.0F, 1.0F);
        }
        int maxStackSize = item.getDefaultInstance().getMaxStackSize();
        if(!player.addItem(item.getDefaultInstance().copyWithCount(count))){
            if(maxStackSize < count){
                for(int i = 0; i < count / maxStackSize; i++){
                    addItemEntity(player, item, maxStackSize);
                }
                addItemEntity(player, item, count % maxStackSize);
            } else {
                addItemEntity(player, item, count);
            }
        }
    }

    public static CustomColorItemEntity addItemEntity(Entity spawnOn, Item item, int count, int color){
        CustomColorItemEntity itemEntity = addItemEntity(spawnOn, item, count);
        itemEntity.setTeamColor(color);
        return itemEntity;
    }

    public static CustomColorItemEntity addItemEntity(Entity spawnOn, ItemStack item, int count, int color){
        CustomColorItemEntity itemEntity = addItemEntity(spawnOn, item, count);
        itemEntity.setTeamColor(color);
        return itemEntity;
    }

    public static CustomColorItemEntity addItemEntity(Entity spawnOn, Item item, int count){
        CustomColorItemEntity itemEntity = new CustomColorItemEntity(spawnOn.level(), spawnOn.getX(), spawnOn.getY(), spawnOn.getZ(), item.getDefaultInstance().copyWithCount(count));
        itemEntity.setPickUpDelay(40);
        spawnOn.level().addFreshEntity(itemEntity);
        return itemEntity;
    }

    public static CustomColorItemEntity addItemEntity(Entity spawnOn, ItemStack item, int count){
        CustomColorItemEntity itemEntity = new CustomColorItemEntity(spawnOn.level(), spawnOn.getX(), spawnOn.getY(), spawnOn.getZ(), item.copyWithCount(count));
        itemEntity.setPickUpDelay(40);
        spawnOn.level().addFreshEntity(itemEntity);
        return itemEntity;
    }

    public static CustomColorItemEntity addItemEntity(Entity spawnOn, ItemStack item){
        CustomColorItemEntity itemEntity = new CustomColorItemEntity(spawnOn.level(), spawnOn.getX(), spawnOn.getY(), spawnOn.getZ(), item);
        itemEntity.setPickUpDelay(40);
        spawnOn.level().addFreshEntity(itemEntity);
        return itemEntity;
    }
    public static CustomColorItemEntity addItemEntity(ServerLevel level, BlockPos pos, ItemStack item){
        CustomColorItemEntity itemEntity = new CustomColorItemEntity(level, pos.getX(), pos.getY(), pos.getZ(), item);
        itemEntity.setPickUpDelay(40);
        level.addFreshEntity(itemEntity);
        return itemEntity;
    }
    public static CustomColorItemEntity addItemEntity(ServerLevel level, double x, double y, double z, ItemStack item){
        CustomColorItemEntity itemEntity = new CustomColorItemEntity(level, x, y, z, item);
        itemEntity.setPickUpDelay(40);
        level.addFreshEntity(itemEntity);
        return itemEntity;
    }

    /**
     * 递归搜索并消耗物品栏物品
     * @param need 需要消耗的个数
     * @return 返回找到的数量，此数值小于等于need
     */
    public static int searchAndConsumeItem(Player player, Item item, int need){
        int total = 0;
        ItemStack stack = ItemStack.EMPTY;
        if(item == player.getMainHandItem().getItem()){
            stack = player.getMainHandItem();
        }else if(item == player.getOffhandItem().getItem()){
            stack = player.getOffhandItem();
        }else {
            for (int i = 0; i < player.getInventory().items.size(); i++) {
                ItemStack teststack = player.getInventory().items.get(i);
                if (teststack.getItem() == item) {
                    stack = teststack;
                    break;
                }
            }
        }

        if (stack != ItemStack.EMPTY) {
            if (stack.getCount() >= need) {
                stack.shrink(need);
                return need;
            } else {
                int cnt = stack.getCount();
                stack.shrink(cnt);
                total += cnt;
                total += searchAndConsumeItem(player,item,need - cnt);
                return total;
            }
        }else{
            return 0;
        }
    }

    public static int searchAndConsumeItem(Player player, Predicate<Item> itemPredicate, int need){
        int total = 0;
        ItemStack stack = ItemStack.EMPTY;
        if(itemPredicate.test(player.getMainHandItem().getItem())){
            stack = player.getMainHandItem();
        }else if(itemPredicate.test(player.getOffhandItem().getItem())){
            stack = player.getOffhandItem();
        }else {
            for (int i = 0; i < player.getInventory().items.size(); i++) {
                ItemStack teststack = player.getInventory().items.get(i);
                if (itemPredicate.test(teststack.getItem())) {
                    stack = teststack;
                    break;
                }
            }
        }

        if (stack != ItemStack.EMPTY) {
            if (stack.getCount() >= need) {
                stack.shrink(need);
                return need;
            } else {
                int cnt = stack.getCount();
                stack.shrink(cnt);
                total += cnt;
                total += searchAndConsumeItem(player,itemPredicate,need - cnt);
                return total;
            }
        }else{
            return 0;
        }
    }

    /**
     * 搜索第一个物品所在的物品栈
     * @return 返回物品栈
     */
    public static ItemStack searchItemStack(Player player, Item item) {
        ItemStack stack = ItemStack.EMPTY;
        if (item == player.getMainHandItem().getItem()) {
            stack = player.getMainHandItem();
        } else if (item == player.getOffhandItem().getItem()) {
            stack = player.getOffhandItem();
        } else {
            for (int i = 0; i < player.getInventory().items.size(); i++) {
                ItemStack teststack = player.getInventory().items.get(i);
                if (teststack.getItem() == item) {
                    stack = teststack;
                    break;
                }
            }
        }
        return stack;
    }

    public static ItemStack searchItemStack(Player player, Predicate<Item> itemPredicate) {
        ItemStack stack = ItemStack.EMPTY;
        if (itemPredicate.test(player.getMainHandItem().getItem())) {
            stack = player.getMainHandItem();
        } else if (itemPredicate.test(player.getOffhandItem().getItem())) {
            stack = player.getOffhandItem();
        } else {
            for (int i = 0; i < player.getInventory().items.size(); i++) {
                ItemStack teststack = player.getInventory().items.get(i);
                if (itemPredicate.test(teststack.getItem())) {
                    stack = teststack;
                    break;
                }
            }
        }
        return stack;
    }

    public static void clearItem(Player player, Item item){
        ItemStack stack;
        while((stack = searchItemStack(player, item)) != ItemStack.EMPTY){
            stack.setCount(0);
        }
    }

}
