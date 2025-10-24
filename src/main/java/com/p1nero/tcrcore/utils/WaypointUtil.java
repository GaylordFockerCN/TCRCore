package com.p1nero.tcrcore.utils;

import com.p1nero.fast_tpa.network.PacketRelay;
import com.p1nero.tcrcore.network.TCRPacketHandler;
import com.p1nero.tcrcore.network.packet.clientbound.AddWaypointPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;
import xaero.common.XaeroMinimapSession;
import xaero.common.core.IXaeroMinimapClientPlayNetHandler;
import xaero.common.minimap.waypoints.Waypoint;
import xaero.common.minimap.waypoints.WaypointVisibilityType;
import xaero.common.minimap.waypoints.WaypointsManager;
import xaero.hud.minimap.waypoint.WaypointColor;
import xaero.hud.minimap.waypoint.WaypointPurpose;
import xaero.minimap.XaeroMinimap;

import java.io.IOException;
import java.util.ArrayList;

@SuppressWarnings("deprecation")
public class WaypointUtil {

    public static void sendWaypoint(ServerPlayer player, String key, BlockPos pos, WaypointColor color) {
        PacketRelay.sendToPlayer(TCRPacketHandler.INSTANCE, new AddWaypointPacket(key, pos, color), player);
    }

    @OnlyIn(Dist.CLIENT)
    public static void addWayPoint(BlockPos pos, String name, @Nullable WaypointColor color){
        if (Minecraft.getInstance().player != null && Minecraft.getInstance().level != null) {
            ArrayList<Waypoint> waypoints = getWaypoints(Minecraft.getInstance().player);
            if(waypoints.stream().anyMatch((waypoint -> isEqualWaypoint(waypoint, name, pos)))){
                return;
            }
            Waypoint instant = new Waypoint(pos.getX(), pos.getY() + 2, pos.getZ(), name, name.substring(0, 1), color == null ? WaypointColor.getRandom() : color , WaypointPurpose.NORMAL, false);
            instant.setVisibility(WaypointVisibilityType.LOCAL);
            waypoints.add(instant);
            save(Minecraft.getInstance().player);
        }
    }

    @OnlyIn(Dist.CLIENT)
    public static void removeWayPoint(BlockPos pos, String name){
        if (Minecraft.getInstance().player != null && Minecraft.getInstance().level != null) {
            getWaypoints(Minecraft.getInstance().player).removeIf(waypoint -> isEqualWaypoint(waypoint, name, pos));
            save(Minecraft.getInstance().player);
        }
    }

    public static boolean isEqualWaypoint(Waypoint waypoint, String name, BlockPos pos) {
        return waypoint.getName().equals(name) && waypoint.getX() == pos.getX() && waypoint.getY() == pos.getY() + 2 && waypoint.getZ() == pos.getZ();
    }

    @OnlyIn(Dist.CLIENT)
    public static ArrayList<Waypoint> getWaypoints(LocalPlayer localPlayer){
        IXaeroMinimapClientPlayNetHandler clientLevel = (IXaeroMinimapClientPlayNetHandler) (localPlayer.connection);
        XaeroMinimapSession session = clientLevel.getXaero_minimapSession();
        WaypointsManager waypointsManager = session.getWaypointsManager();
        return waypointsManager.getWaypoints().getList();
    }

    @OnlyIn(Dist.CLIENT)
    public static WaypointsManager getWaypointManager(LocalPlayer localPlayer){
        IXaeroMinimapClientPlayNetHandler clientLevel = (IXaeroMinimapClientPlayNetHandler) (localPlayer.connection);
        XaeroMinimapSession session = clientLevel.getXaero_minimapSession();
        return session.getWaypointsManager();
    }

    @OnlyIn(Dist.CLIENT)
    public static void save(LocalPlayer localPlayer) {
        try {
            XaeroMinimap.instance.getSettings().saveWaypoints(getWaypointManager(localPlayer).getCurrentWorld());
        } catch (IOException ignored) {

        }
    }

}
