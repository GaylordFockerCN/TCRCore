package com.p1nero.tcrcore.save_data;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.storage.DimensionDataStorage;
import org.jetbrains.annotations.NotNull;

public class TCRDimSaveData extends SavedData {
    private Level level;

    private boolean bossKilled;
    private boolean bossSummoned;
    private int resetCooldown;

    public boolean isResetting() {
        return resetCooldown > 0;
    }

    public void tickResetting() {
        if(resetCooldown > 0) {
            resetCooldown--;
        }
    }

    public int getResetCooldown() {
        return resetCooldown;
    }

    public void setResetCooldown(int resetCooldown) {
        this.resetCooldown = resetCooldown;
    }

    public boolean isBossSummoned() {
        return bossSummoned;
    }

    public void setBossSummoned(boolean bossSummoned) {
        this.bossSummoned = bossSummoned;
        setDirty();
    }

    public void setBossKilled(boolean bossKilled) {
        this.bossKilled = bossKilled;
    }

    public boolean isBossKilled() {
        return bossKilled;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public static TCRDimSaveData create() {
        return new TCRDimSaveData();
    }

    @Override
    public @NotNull CompoundTag save(@NotNull CompoundTag pCompoundTag) {
        pCompoundTag.putBoolean("bossSummoned", bossSummoned);
        return pCompoundTag;
    }

    public void load(CompoundTag nbt) {
        bossSummoned = nbt.getBoolean("bossSummoned");
    }

    public static TCRDimSaveData decode(CompoundTag tag){
        TCRDimSaveData saveData = TCRDimSaveData.create();
        saveData.load(tag);
        return saveData;
    }

    public static TCRDimSaveData get(ServerLevel worldIn) {
        DimensionDataStorage dataStorage = worldIn.getDataStorage();
        TCRDimSaveData levelSaveData = dataStorage.computeIfAbsent(TCRDimSaveData::decode, TCRDimSaveData::create, "tcr_dim_data" + worldIn.dimension().location().getPath());
        levelSaveData.setLevel(worldIn);
        return levelSaveData;
    }
}