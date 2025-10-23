package com.p1nero.tcrcore.mixin;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.obscuria.aquamirae.common.items.weapon.TerribleSwordItem;
import dev.shadowsoffire.attributeslib.api.ALObjects.Attributes;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.UUID;

@Mixin(TerribleSwordItem.class)
public class TerribleSwordItemMixin extends SwordItem {
    public TerribleSwordItemMixin(Tier p_43269_, int p_43270_, float p_43271_, Properties p_43272_) {
        super(p_43269_, p_43270_, p_43271_, p_43272_);
    }

    @Inject(method = "getDefaultAttributeModifiers", at = @At("HEAD"), cancellable = true)
    private void tcr$getDefaultAttributeModifiers(@NotNull EquipmentSlot slot, CallbackInfoReturnable<Multimap<Attribute, AttributeModifier>> cir) {
        Multimap<Attribute, AttributeModifier> multimap = super.getDefaultAttributeModifiers(slot);
        if (slot == EquipmentSlot.MAINHAND) {
            ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
            builder.putAll(multimap);
            builder.put(Attributes.CRIT_CHANCE.get(), new AttributeModifier(UUID.fromString("AB3F55D3-645C-4F38-A497-9C13A33DB5CF"), "Weapon modifier", 0.1F, AttributeModifier.Operation.ADDITION));
            builder.put(Attributes.CRIT_DAMAGE.get(), new AttributeModifier(UUID.fromString("AA3F55D3-645C-4F38-A497-9C13A33DB5CF"), "Weapon modifier", 1.5F, AttributeModifier.Operation.ADDITION));
            cir.setReturnValue(builder.build());
        } else {
            cir.setReturnValue(multimap);
        }
    }
}
