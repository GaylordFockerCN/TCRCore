package com.p1nero.tcrcore.client;

import com.p1nero.fast_tpa.network.PacketRelay;
import com.p1nero.tcrcore.TCRCoreMod;
import com.p1nero.tcrcore.network.TCRPacketHandler;
import com.p1nero.tcrcore.network.packet.serverbound.ExecuteRiptidePacket;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.glfw.GLFW;

@Mod.EventBusSubscriber(modid = TCRCoreMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = {Dist.CLIENT})
public class KeyMappings {

	public static final KeyMapping RIPTIDE = new KeyMapping(buildKey("riptide"), GLFW.GLFW_KEY_LEFT_ALT, "key.categories." + TCRCoreMod.MOD_ID);
    public static final KeyMapping SHOW_TASK = new KeyMapping(buildKey("show_task"), GLFW.GLFW_KEY_J, "key.categories." + TCRCoreMod.MOD_ID);

	public static String buildKey(String name){
		return "key." + TCRCoreMod.MOD_ID + "." + name;
	}

	@SubscribeEvent
	public static void registerKeyMappings(RegisterKeyMappingsEvent event) {
		event.register(RIPTIDE);
	}

	@Mod.EventBusSubscriber(modid = TCRCoreMod.MOD_ID, value = Dist.CLIENT)
	public static class KeyPressHandler {

		@SubscribeEvent
		public static void onClientTick(TickEvent.ClientTickEvent event) {
			if(event.phase == TickEvent.Phase.END) {
                while (RIPTIDE.consumeClick()) {
                    if(Minecraft.getInstance().player != null && Minecraft.getInstance().player.isUnderWater()) {
                        PacketRelay.sendToServer(TCRPacketHandler.INSTANCE, new ExecuteRiptidePacket());
                    }
                }
			}
		}

	}

}
