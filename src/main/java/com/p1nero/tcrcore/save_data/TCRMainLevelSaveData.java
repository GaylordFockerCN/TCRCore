package com.p1nero.tcrcore.save_data;

import com.p1nero.tcrcore.TCRCoreMod;
import com.p1nero.tcrcore.worldgen.TCRDimensions;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundSetTitleTextPacket;
import net.minecraft.network.protocol.game.ClientboundSoundPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.storage.DimensionDataStorage;
import org.jetbrains.annotations.NotNull;

public class TCRMainLevelSaveData extends SavedData {
    private BlockPos villagePos = BlockPos.ZERO;
    private BlockPos abyssPos = BlockPos.ZERO;
    private BlockPos desertPos = BlockPos.ZERO;
    private BlockPos stormPos = BlockPos.ZERO;
    private BlockPos cursedPos = BlockPos.ZERO;
    private BlockPos flamePos = BlockPos.ZERO;
    private boolean girlPlaced;
    private boolean stormFinish;
    private boolean desertFinish;
    private boolean cursedFinish;
    private boolean flameFinish;
    private boolean abyssFinish;
    private int progressCount;
    public static final String NAME = "tcr_level_save_data";
    private Level level;

    public void setLevel(Level level) {
        this.level = level;
    }

    public boolean isAllFinish() {
        return isAbyssFinish() && isStormFinish() && isCursedFinish() && isDesertFinish() && isFlameFinish();
    }

    public int getProgressCount() {
        return progressCount;
    }

    private void updateProgressCount() {
        progressCount = 0;
        if (stormFinish) progressCount++;
        if (desertFinish) progressCount++;
        if (cursedFinish) progressCount++;
        if (flameFinish) progressCount++;
        if (abyssFinish) progressCount++;

        if(isAllFinish()) {
            if(level instanceof ServerLevel serverLevel) {
                serverLevel.getServer().getPlayerList().getPlayers().forEach(player -> {
                    player.connection.send(new ClientboundSoundPacket(BuiltInRegistries.SOUND_EVENT.wrapAsHolder(SoundEvents.END_PORTAL_SPAWN), SoundSource.PLAYERS, player.getX(), player.getY(), player.getZ(), 1.0F, 1.0F, player.getRandom().nextInt()));
                    player.connection.send(new ClientboundSetTitleTextPacket(TCRCoreMod.getInfo("finish_all_eye")));
                });
            }
        }
    }

    private static TCRMainLevelSaveData create() {
        return new TCRMainLevelSaveData();
    }

    public void setVillagePos(BlockPos villagePos) {
        this.villagePos = villagePos;
    }

    public BlockPos getVillagePos() {
        return villagePos;
    }

    public void setAbyssPos(BlockPos abyssPos) {
        this.abyssPos = abyssPos;
    }
    public void setDesertPos(BlockPos desertPos) {
        this.desertPos = desertPos;
        setDirty();
    }

    public BlockPos getDesertPos() {
        return desertPos;
    }

    public void setStormPos(BlockPos stormPos) {
        this.stormPos = stormPos;
        setDirty();
    }

    public BlockPos getStormPos() {
        return stormPos;
    }

    public void setCursedPos(BlockPos cursedPos) {
        this.cursedPos = cursedPos;
        setDirty();
    }

    public BlockPos getCursedPos() {
        return cursedPos;
    }

    public void setFlamePos(BlockPos flamePos) {
        this.flamePos = flamePos;
        setDirty();
    }

    public BlockPos getFlamePos() {
        return flamePos;
    }
    public BlockPos getAbyssPos() {
        return abyssPos;
    }

    public boolean isGirlPlaced() {
        return girlPlaced;
    }

    public void setGirlPlaced(boolean girlPlaced) {
        this.girlPlaced = girlPlaced;
        setDirty();
    }

    public boolean isStormFinish() {
        return stormFinish;
    }

    public boolean isAbyssFinish() {
        return abyssFinish;
    }

    public boolean isCursedFinish() {
        return cursedFinish;
    }

    public boolean isDesertFinish() {
        return desertFinish;
    }

    public boolean isFlameFinish() {
        return flameFinish;
    }

    public void setAbyssFinish(boolean abyssFinish) {
        this.abyssFinish = abyssFinish;
        updateProgressCount(); // 更新进度计数
        setDirty(); // 标记数据需要保存
    }

    public void setCursedFinish(boolean cursedFinish) {
        this.cursedFinish = cursedFinish;
        updateProgressCount(); // 更新进度计数
        setDirty(); // 标记数据需要保存
    }

    public void setDesertFinish(boolean desertFinish) {
        this.desertFinish = desertFinish;
        updateProgressCount(); // 更新进度计数
        setDirty(); // 标记数据需要保存
    }

    public void setFlameFinish(boolean flameFinish) {
        this.flameFinish = flameFinish;
        updateProgressCount(); // 更新进度计数
        setDirty(); // 标记数据需要保存
    }

    public void setStormFinish(boolean stormFinish) {
        this.stormFinish = stormFinish;
        updateProgressCount(); // 更新进度计数
        setDirty(); // 标记数据需要保存
    }

    @Override
    public @NotNull CompoundTag save(@NotNull CompoundTag pCompoundTag) {
        pCompoundTag.putInt("covesPosX", abyssPos.getX());
        pCompoundTag.putInt("covesPosY", abyssPos.getY());
        pCompoundTag.putInt("covesPosZ", abyssPos.getZ());
        pCompoundTag.putInt("villagePosX", villagePos.getX());
        pCompoundTag.putInt("villagePosY", villagePos.getY());
        pCompoundTag.putInt("villagePosZ", villagePos.getZ());

        pCompoundTag.putInt("desertPosX", desertPos.getX());
        pCompoundTag.putInt("desertPosY", desertPos.getY());
        pCompoundTag.putInt("desertPosZ", desertPos.getZ());

        pCompoundTag.putInt("stormPosX", stormPos.getX());
        pCompoundTag.putInt("stormPosY", stormPos.getY());
        pCompoundTag.putInt("stormPosZ", stormPos.getZ());

        pCompoundTag.putInt("cursedPosX", cursedPos.getX());
        pCompoundTag.putInt("cursedPosY", cursedPos.getY());
        pCompoundTag.putInt("cursedPosZ", cursedPos.getZ());

        pCompoundTag.putInt("flamePosX", flamePos.getX());
        pCompoundTag.putInt("flamePosY", flamePos.getY());
        pCompoundTag.putInt("flamePosZ", flamePos.getZ());

        pCompoundTag.putBoolean("girlPlaced", girlPlaced);
        pCompoundTag.putBoolean("stormFinish", stormFinish);
        pCompoundTag.putBoolean("desertFinish", desertFinish);
        pCompoundTag.putBoolean("cursedFinish", cursedFinish);
        pCompoundTag.putBoolean("flameFinish", flameFinish);
        pCompoundTag.putBoolean("abyssFinish", abyssFinish);
        pCompoundTag.putInt("progressCount", progressCount); // 保存进度计数
        return pCompoundTag;
    }

    public void load(CompoundTag nbt) {
        this.villagePos = new BlockPos(
                nbt.getInt("villagePosX"),
                nbt.getInt("villagePosY"),
                nbt.getInt("vZ")
        );
        this.abyssPos = new BlockPos(
                nbt.getInt("covesPosX"),
                nbt.getInt("covesPosY"),
                nbt.getInt("covesPosZ")
        );
        this.desertPos = new BlockPos(
                nbt.getInt("desertPosX"),
                nbt.getInt("desertPosY"),
                nbt.getInt("desertPosZ")
        );
        this.stormPos = new BlockPos(
                nbt.getInt("stormPosX"),
                nbt.getInt("stormPosY"),
                nbt.getInt("stormPosZ")
        );
        this.cursedPos = new BlockPos(
                nbt.getInt("cursedPosX"),
                nbt.getInt("cursedPosY"),
                nbt.getInt("cursedPosZ")
        );
        this.flamePos = new BlockPos(
                nbt.getInt("flamePosX"),
                nbt.getInt("flamePosY"),
                nbt.getInt("flamePosZ")
        );
        this.girlPlaced = nbt.getBoolean("girlPlaced");
        this.stormFinish = nbt.getBoolean("stormFinish");
        this.desertFinish = nbt.getBoolean("desertFinish");
        this.cursedFinish = nbt.getBoolean("cursedFinish");
        this.flameFinish = nbt.getBoolean("flameFinish");
        this.abyssFinish = nbt.getBoolean("abyssFinish");
        this.progressCount = nbt.getInt("progressCount"); // 加载进度计数
    }

    public static TCRMainLevelSaveData decode(CompoundTag tag){
        TCRMainLevelSaveData saveData = TCRMainLevelSaveData.create();
        saveData.load(tag);
        return saveData;
    }

    public static TCRMainLevelSaveData get(ServerLevel worldIn) {
        ServerLevel world = worldIn.getServer().getLevel(TCRDimensions.SANCTUM_LEVEL_KEY);
        DimensionDataStorage dataStorage = world.getDataStorage();
        TCRMainLevelSaveData levelSaveData = dataStorage.computeIfAbsent(TCRMainLevelSaveData::decode, TCRMainLevelSaveData::create, TCRMainLevelSaveData.NAME);
        levelSaveData.setLevel(world);
        return levelSaveData;
    }
}