package com.p1nero.tcrcore.entity.custom;

import com.github.L_Ender.cataclysm.init.ModItems;
import com.p1nero.tcrcore.TCRCoreMod;
import com.p1nero.tcrcore.entity.TCREntities;
import com.p1nero.tcrcore.item.TCRItems;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class CustomColorItemEntity extends ItemEntity {
    private static final EntityDataAccessor<Integer> COLOR = SynchedEntityData.defineId(CustomColorItemEntity.class, EntityDataSerializers.INT);
    public CustomColorItemEntity(EntityType<? extends ItemEntity> p_31991_, Level p_31992_) {
        super(p_31991_, p_31992_);
    }

    public CustomColorItemEntity(Level p_32001_, double p_32002_, double p_32003_, double p_32004_, ItemStack p_32005_) {
        this(p_32001_, p_32002_, p_32003_, p_32004_, p_32005_, p_32001_.random.nextDouble() * 0.2D - 0.1D, 0.2D, p_32001_.random.nextDouble() * 0.2D - 0.1D);
    }

    public CustomColorItemEntity(Level p_149663_, double p_149664_, double p_149665_, double p_149666_, ItemStack p_149667_, double p_149668_, double p_149669_, double p_149670_) {
        super(TCREntities.CUSTOM_COLOR_ITEM.get(), p_149663_);
        this.setPos(p_149664_, p_149665_, p_149666_);
        this.setDeltaMovement(p_149668_, p_149669_, p_149670_);
        this.setItem(p_149667_);
        p_149667_.getItem();
        this.lifespan = p_149667_.getEntityLifespan(p_149663_);
    }

    @Override
    public boolean hurt(@NotNull DamageSource damageSource, float value) {
        if(!damageSource.isCreativePlayer()
                && List.of(TCRItems.ANCIENT_ORACLE_FRAGMENT.get(),
                ModItems.MONSTROUS_EYE.get(),
                ModItems.VOID_EYE.get(),
                ModItems.MECH_EYE.get(),
                ModItems.ABYSS_EYE.get(),
                ModItems.STORM_EYE.get(),
                ModItems.CURSED_EYE.get(),
                ModItems.FLAME_EYE.get(),
                ModItems.DESERT_EYE.get())
                .contains(getItem().getItem())) {
            return false;
        }
        return super.hurt(damageSource, value);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.getEntityData().define(COLOR, 16777215);
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        this.setTeamColor(tag.getInt("color_id"));
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putInt("color_id", this.getTeamColor());
    }

    @Override
    public boolean isCurrentlyGlowing() {
        return true;
    }

    public void setTeamColor(int color){
        this.getEntityData().set(COLOR, color);
    }

    @Override
    public int getTeamColor() {
        return getEntityData().get(COLOR);
    }
}
