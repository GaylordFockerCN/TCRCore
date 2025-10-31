package com.p1nero.tcrcore.capability;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

import java.util.ArrayList;
import java.util.List;

public class TCRTaskManager {
    public static final int NO_TASK = 0;
    public static final String PRE = "tcrcore.task_desc.";
    public static final List<Task> TASKS = new ArrayList<>();
    public static int id = 0;
    public static Task EMPTY;
    public static Task KILL_PILLAGER;
    public static Task GIVE_ORACLE_TO_KEEPER;
    public static Task BACK_TO_KEEPER;
    public static Task FIND_GODNESS_STATUE;
    public static Task FIND_ARTERIUS;
    public static Task LIGHT_ALL_ALTAR;
    public static Task GO_TO_OVERWORLD;
    public static void init() {
        TASKS.clear();
        EMPTY = createTask("empty");//不知道为嘛默认0改不了= =
        KILL_PILLAGER = createTask("kill_pillager");
        GIVE_ORACLE_TO_KEEPER = createTask("give_oracle_to_keeper");
        BACK_TO_KEEPER = createTask("back_to_keeper");
        FIND_GODNESS_STATUE = createTask("find_godness_statue");
        FIND_ARTERIUS = createTask("find_arterius");
        LIGHT_ALL_ALTAR = createTask("light_all_altar");
        GO_TO_OVERWORLD = createTask("go_to_overworld");
    }

    public static Task createTask(String desc) {
        Task task = new Task(id++, PRE + desc);
        TASKS.add(task);
        return task;
    }

    public static boolean hasTask(Player player) {
        return PlayerDataManager.currentTaskId.get(player) != NO_TASK;
    }

    public static Component getCurrentTaskDesc(Player player) {
        if(!hasTask(player)) {
            return Component.empty();
        }
        int id = PlayerDataManager.currentTaskId.getInt(player);
        if(id >= 0 && id < TASKS.size()) {
            return TASKS.get(id).componentDesc;
        }
        return Component.empty();
    }

    public static void clearTask(ServerPlayer player) {
        PlayerDataManager.currentTaskId.put(player, NO_TASK);
    }

    public static class Task {

        private final int id;
        private final String desc;
        private final Component componentDesc;

        public Task(int id, String desc) {
            this.id = id;
            this.desc = desc;
            componentDesc = Component.translatable(desc);
        }

        public void start(ServerPlayer player) {
            TCRTaskManager.clearTask(player);
            PlayerDataManager.currentTaskId.put(player, id);
        }

        public void finish(ServerPlayer player) {
            if(PlayerDataManager.currentTaskId.get(player) == id) {
                TCRTaskManager.clearTask(player);
            }
        }

        public Component getComponentDesc() {
            return componentDesc;
        }

        public String getDesc() {
            return desc;
        }

        public int getId() {
            return id;
        }
    }
}
