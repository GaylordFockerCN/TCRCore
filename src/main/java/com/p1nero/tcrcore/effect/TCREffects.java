package com.p1nero.tcrcore.effect;

import com.p1nero.tcrcore.TCRCoreMod;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class TCREffects {
    public static final DeferredRegister<MobEffect> REGISTRY = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, TCRCoreMod.MOD_ID);
    public static final RegistryObject<MobEffect> INVULNERABLE = REGISTRY.register("invulnerable", () -> new SimpleEffect(MobEffectCategory.BENEFICIAL, 0X6c6a5c));
}
