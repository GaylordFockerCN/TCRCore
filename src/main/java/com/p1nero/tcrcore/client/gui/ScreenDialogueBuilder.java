package com.p1nero.tcrcore.client.gui;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ScreenDialogueBuilder {
    private final String name, modId;
    private final Set<ChatFormatting> defaultAnsFormats = new HashSet<>();
    private final Set<ChatFormatting> defaultOptFormats = new HashSet<>();

    public ScreenDialogueBuilder(String modId, String name) {
        this.name = name;
        this.modId = modId;
    }

    public void setDefaultAnsFormat(ChatFormatting... formatting) {
        defaultAnsFormats.addAll(List.of(formatting));
    }

    public void setDefaultOptFormat(ChatFormatting... formatting) {
        defaultOptFormats.addAll(List.of(formatting));
    }

    public Component ans(int id) {
        Component ans = screenAns(modId, name, id);
        if (!defaultAnsFormats.isEmpty()) {
            return ans.copy().withStyle(defaultAnsFormats.toArray(new ChatFormatting[]{}));
        }
        return ans;
    }

    public Component opt(int id) {
        Component opt = screenOpt(modId, name, id);
        if (!defaultOptFormats.isEmpty()) {
            return opt.copy().withStyle(defaultOptFormats.toArray(new ChatFormatting[]{}));
        }
        return opt;
    }

    public Component name() {
        return screen(modId, name);
    }


    public static Component screen(String modId, String name) {
        return Component.translatable("screen." + modId + "." + name);
    }

    public static Component screenAns(String modId, String name, int id) {
        return Component.translatable("screen." + modId + ".ans." + name + "_" + id);
    }

    public static Component screenOpt(String modId, String name, int id) {
        return Component.translatable("screen." + modId + ".opt." + name + "_" + id);
    }
}