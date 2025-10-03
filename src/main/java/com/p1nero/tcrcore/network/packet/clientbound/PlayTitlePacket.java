package com.p1nero.tcrcore.network.packet.clientbound;

import com.p1nero.dialog_lib.network.packet.BasePacket;
import com.p1nero.tcrcore.TCRCoreMod;
import com.p1nero.tcrcore.client.KeyMappings;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import yesman.epicfight.client.input.EpicFightKeyMappings;

public record PlayTitlePacket(int id) implements BasePacket {

    @Override
    public void encode(FriendlyByteBuf buf) {
        buf.writeInt(id);
    }

    public static PlayTitlePacket decode(FriendlyByteBuf buf){
        return new PlayTitlePacket(buf.readInt());
    }

    @Override
    public void execute(Player player) {
        if(Minecraft.getInstance().player != null && Minecraft.getInstance().level != null){
            switch (id) {
                case 1 -> {
                    Minecraft.getInstance().gui.setTitle(TCRCoreMod.getInfo("dodge_tutorial", EpicFightKeyMappings.DODGE.getTranslatedKeyMessage().copy().withStyle(ChatFormatting.GOLD)));
                    Minecraft.getInstance().gui.setSubtitle(TCRCoreMod.getInfo("perfect_dodge_tutorial"));
                }
                case 2 -> {
                    Minecraft.getInstance().gui.setTitle(TCRCoreMod.getInfo("parry_tutorial", EpicFightKeyMappings.GUARD.getTranslatedKeyMessage().copy().withStyle(ChatFormatting.GOLD)));
                    Minecraft.getInstance().gui.setSubtitle(TCRCoreMod.getInfo("perfect_parry_tutorial"));
                }
                case 3 -> {
                    Minecraft.getInstance().gui.setTitle(TCRCoreMod.getInfo("lock_tutorial", EpicFightKeyMappings.LOCK_ON.getTranslatedKeyMessage().copy().withStyle(ChatFormatting.GOLD)));
                    Minecraft.getInstance().gui.setSubtitle(TCRCoreMod.getInfo("lock_tutorial_sub"));
                }
                case 4 -> {
                    Minecraft.getInstance().gui.setTitle(TCRCoreMod.getInfo("riptide_tutorial", KeyMappings.RIPTIDE.getTranslatedKeyMessage().copy().withStyle(ChatFormatting.GOLD)));
                }
            }
        }
    }
}
