package com.p1nero.tcrcore.capability;

import com.p1nero.fast_tpa.network.PacketRelay;
import com.p1nero.tcrcore.network.TCRPacketHandler;
import com.p1nero.tcrcore.network.packet.clientbound.PersistentBoolDataSyncPacket;
import com.p1nero.tcrcore.network.packet.clientbound.PersistentDoubleDataSyncPacket;
import com.p1nero.tcrcore.network.packet.clientbound.PersistentStringDataSyncPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

import java.util.HashSet;
import java.util.Set;

public class PlayerDataManager {
    private final static Set<String> EXISTING_ID = new HashSet<>();
    public static DoubleData stage = new DoubleData("stage", 0);
    public static BoolData boatGet = new BoolData("boat_get", false);
    public static BoolData swordSoaringUnlocked = new BoolData("sword_soaring_avoid_unlocked", false);
    public static BoolData fireAvoidUnlocked = new BoolData("fire_avoid_unlocked", false);
    public static BoolData waterAvoidUnlocked = new BoolData("water_avoid_unlocked", false);
    public static BoolData bllSummoned = new BoolData("bll_summoned", false);
    public static BoolData firstJoint = new BoolData("first_joint", false);
    public static BoolData wayStoneInteracted = new BoolData("way_stone_interacted", false);
    public static BoolData dodged = new BoolData("dodged", false);
    public static BoolData parried = new BoolData("parried", false);
    public static BoolData locked = new BoolData("locked", false);
    public static BoolData tutorial_passed = new BoolData("tutorial_passed", false);
    public static BoolData weapon_innate_used = new BoolData("weapon_innate_used", false);
    public static BoolData pillagerKilled = new BoolData("pillager_killed", false);//初始任务
    public static BoolData mapMarked = new BoolData("map_marked", false);//是否标记过地图了，省的二次搜索地图浪费
    public static BoolData stormEyeTraded = new BoolData("storm_eye_traded", false);
    public static BoolData flameEyeTraded = new BoolData("flame_eye_traded", false);
    public static BoolData abyssEyeTraded = new BoolData("abyss_eye_traded", false);
    public static BoolData cursedEyeTraded = new BoolData("cursed_eye_traded", false);
    public static BoolData desertEyeTraded = new BoolData("desert_eye_traded", false);

    public static BoolData stormEyeKilled = new BoolData("storm_eye_killed", false);
    public static BoolData flameEyeKilled = new BoolData("flame_eye_killed", false);
    public static BoolData abyssEyeKilled = new BoolData("abyss_eye_killed", false);
    public static BoolData cursedEyeKilled = new BoolData("cursed_eye_killed", false);
    public static BoolData desertEyeKilled = new BoolData("desert_eye_killed", false);

    public static boolean isAllEyeGet(Player player) {
        return stormEyeTraded.get(player)
                &&flameEyeTraded.get(player)
                &&abyssEyeTraded.get(player)
                &&cursedEyeTraded.get(player)
                &&desertEyeTraded.get(player);
    }

    public static boolean isAllAltarKilled(Player player) {
        return stormEyeKilled.get(player)
                && flameEyeKilled.get(player)
                && abyssEyeKilled.get(player)
                && cursedEyeKilled.get(player)
                && desertEyeKilled.get(player);
    }

    public static void putData(Player player, String key, double value) {
        getTCRPlayer(player).putDouble(key, value);
    }

    public static void putData(Player player, String key, String value) {
        getTCRPlayer(player).putString(key, value);
    }

    public static void putData(Player player, String key, boolean value) {
        getTCRPlayer(player).putBoolean(key, value);
    }

    public static boolean getBool(Player player, String key) {
        return getTCRPlayer(player).getBoolean(key);
    }

    public static double getDouble(Player player, String key) {
        return getTCRPlayer(player).getDouble(key);
    }

    public static String getString(Player player, String key) {
        return getTCRPlayer(player).getString(key);
    }

    public static TCRPlayer getTCRPlayer(Player player) {
        return TCRCapabilityProvider.getTCRPlayer(player);
    }


    public abstract static class Data<T> {

        protected String key;
        protected boolean isLocked = false;//增加一个锁，用于初始化数据用
        protected int id;

        public Data(String key) {
            if(EXISTING_ID.contains(key)) {
                throw new IllegalArgumentException(key + " is already exist!");
            }
            this.key = key;
            EXISTING_ID.add(key);
        }

        public String getKey() {
            return key;
        }

        public void init(Player player) {
            isLocked = getTCRPlayer(player).getBoolean(key + "isLocked");

        }

        public boolean isLocked(Player player) {
            return getTCRPlayer(player).getBoolean(key + "isLocked");
        }

        public boolean isLocked(CompoundTag playerData) {
            return playerData.getBoolean(key + "isLocked");
        }

        public void lock(Player player) {
            getTCRPlayer(player).putBoolean(key + "isLocked", true);
            isLocked = true;
        }

        public void unLock(Player player) {
            getTCRPlayer(player).putBoolean(key + "isLocked", false);
            LocalPlayer localPlayer = Minecraft.getInstance().player;
            isLocked = false;
        }

        public abstract T get(Player player);

        public abstract void put(Player player, T data);

    }

    public static class StringData extends Data<String> {

        protected boolean isLocked = false;//增加一个锁
        protected String defaultString = "";

        public StringData(String key, String defaultString) {
            super(key);
            this.defaultString = defaultString;
        }

        @Override
        public void init(Player player) {
            put(player, defaultString);
        }

        @Override
        public void put(Player player, String value) {
            if (!isLocked(player)) {
                getTCRPlayer(player).putString(key, value);
                if (player instanceof ServerPlayer serverPlayer) {
                    PacketRelay.sendToPlayer(TCRPacketHandler.INSTANCE, new PersistentStringDataSyncPacket(key, isLocked, value), serverPlayer);
                }
            }
        }

        @Override
        public String get(Player player) {
            return getTCRPlayer(player).getString(key);
        }

        public String get(CompoundTag playerData) {
            return playerData.getString(key);
        }

    }

    public static class DoubleData extends Data<Double> {

        private double defaultValue = 0;

        public DoubleData(String key, double defaultValue) {
            super(key);
            this.defaultValue = defaultValue;
        }

        public void init(Player player) {
            isLocked = getTCRPlayer(player).getBoolean(key + "isLocked");
            put(player, defaultValue);
        }

        @Override
        public void put(Player player, Double value) {
            if (!isLocked(player)) {
                getTCRPlayer(player).putDouble(key, value);
                if (player instanceof ServerPlayer serverPlayer) {
                    PacketRelay.sendToPlayer(TCRPacketHandler.INSTANCE, new PersistentDoubleDataSyncPacket(key, isLocked, value), serverPlayer);
                }
            }
        }

        public int getInt(Player player) {
            return get(player).intValue();
        }

        @Override
        public Double get(Player player) {
            return getTCRPlayer(player).getDouble(key);
        }

        public double get(CompoundTag playerData) {
            return playerData.getDouble(key);
        }

    }

    public static class BoolData extends Data<Boolean> {

        boolean defaultBool;

        public BoolData(String key, boolean defaultBool) {
            super(key);
            this.defaultBool = defaultBool;
        }

        public void init(Player player) {
            isLocked = getTCRPlayer(player).getBoolean(key + "isLocked");
            put(player, defaultBool);
        }

        @Override
        public void put(Player player, Boolean value) {
            if (isLocked(player))
                return;

            getTCRPlayer(player).putBoolean(key, value);
            if (player instanceof ServerPlayer serverPlayer) {
                PacketRelay.sendToPlayer(TCRPacketHandler.INSTANCE, new PersistentBoolDataSyncPacket(key, isLocked, value), serverPlayer);
            } else {
                PacketRelay.sendToServer(TCRPacketHandler.INSTANCE, new PersistentBoolDataSyncPacket(key, isLocked, value));
            }
        }

        @Override
        public Boolean get(Player player) {
            return getTCRPlayer(player).getBoolean(key);
        }

        public boolean get(CompoundTag playerData) {
            return playerData.getBoolean(key);
        }

    }

}
