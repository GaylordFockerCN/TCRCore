package com.p1nero.tcrcore.mixin;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.obscuria.aquamirae.common.items.weapon.MazeRoseItem;
import dev.shadowsoffire.attributeslib.api.ALObjects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.UUID;

@Mixin(MazeRoseItem.class)
public abstract class MazeRoseMixin extends TieredItem implements Vanishable {

    public MazeRoseMixin(Tier p_43308_, Properties p_43309_) {
        super(p_43308_, p_43309_);
    }

    @Inject(method = "getAttributeModifiers", at = @At("HEAD"), cancellable = true, remap = false)
    private void tcr$getDefaultAttributeModifiers(EquipmentSlot slot, ItemStack stack, CallbackInfoReturnable<Multimap<Attribute, AttributeModifier>> cir) {
        Multimap<Attribute, AttributeModifier> multimap = super.getAttributeModifiers(slot, stack);
        if (slot == EquipmentSlot.OFFHAND) {
            ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
            builder.putAll(multimap);
            builder.put(ALObjects.Attributes.CRIT_CHANCE.get(), new AttributeModifier(UUID.fromString("A14F51D3-645C-4F38-A497-9C13A33DB5CF"), "Weapon modifier", 0.15, AttributeModifier.Operation.MULTIPLY_BASE));
            cir.setReturnValue(builder.build()) ;
        } else {
            cir.setReturnValue(multimap);
        }
    }
}