package com.p1nero.tcrcore.network.packet.clientbound;

import com.p1nero.dialog_lib.network.packet.BasePacket;
import com.p1nero.tcrcore.utils.WaypointUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.Nullable;
import xaero.hud.minimap.waypoint.WaypointColor;

public record AddWaypointPacket(String name, BlockPos pos, @Nullable WaypointColor color) implements BasePacket {
    @Override
    public void encode(FriendlyByteBuf buf) {
        buf.writeUtf(name);
        buf.writeBlockPos(pos);
        if(color == null) {
            buf.writeUtf("null");
        } else {
            buf.writeUtf(color.name());
        }
    }

    public static AddWaypointPacket decode(FriendlyByteBuf buf) {
        String name = buf.readUtf();
        BlockPos blockPos = buf.readBlockPos();
        String color = buf.readUtf();
        return new AddWaypointPacket(name, blockPos, color.equals("null") ? null : WaypointColor.valueOf(color));
    }

    @Override
    public void execute(@Nullable Player playerEntity) {
        if (Minecraft.getInstance().player != null && Minecraft.getInstance().level != null) {
            WaypointUtil.addWayPoint(pos, name, color);
        }
    }
}
