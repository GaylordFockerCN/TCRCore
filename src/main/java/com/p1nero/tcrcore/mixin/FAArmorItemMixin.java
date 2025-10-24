package com.p1nero.tcrcore.mixin;

import com.p1nero.tcrcore.TCRCoreMod;
import net.kenddie.fantasyarmor.item.armor.FAArmorItem;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(FAArmorItem.class)
public abstract class FAArmorItemMixin extends ArmorItem {
    @Shadow(remap = false)
    public abstract List<MobEffectInstance> getFullSetEffects();

    public FAArmorItemMixin(ArmorMaterial p_40386_, Type p_266831_, Properties p_40388_) {
        super(p_40386_, p_266831_, p_40388_);
    }

    @Inject(method = "appendHoverText", at = @At("HEAD"))
    private void tcr$appendHoverText(ItemStack stack, @Nullable Level world, List<Component> tooltip, TooltipFlag flag, CallbackInfo ci) {
        tooltip.add(TCRCoreMod.getInfo("on_full_set").withStyle(ChatFormatting.GREEN));
        this.getFullSetEffects().forEach(mobEffectInstance -> {
            tooltip.add(Component.translatable(mobEffectInstance.getDescriptionId()).append(" : " + (mobEffectInstance.getAmplifier() + 1)).withStyle(ChatFormatting.GREEN));
        });
    }
}
