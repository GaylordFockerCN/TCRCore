package com.p1nero.tcrcore.client;

import com.p1nero.tcrcore.TCRCoreMod;
import com.p1nero.tcrcore.capability.TCRPlayer;
import com.p1nero.tcrcore.skills.TCRWeaponInnateSkillBase;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.glfw.GLFW;
import yesman.epicfight.client.ClientEngine;
import yesman.epicfight.client.world.capabilites.entitypatch.player.LocalPlayerPatch;
import yesman.epicfight.network.EpicFightNetworkManager;
import yesman.epicfight.network.client.CPExecuteSkill;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.skill.SkillSlots;
import yesman.epicfight.world.entity.eventlistener.SkillExecuteEvent;

@Mod.EventBusSubscriber(modid = TCRCoreMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = {Dist.CLIENT})
public class KeyMappings {

	public static final KeyMapping SKILL_1 = new KeyMapping(buildKey("skill_1"), GLFW.GLFW_KEY_1, "key.categories.tcr");
	public static final KeyMapping SKILL_2 = new KeyMapping(buildKey("skill_2"), GLFW.GLFW_KEY_2, "key.categories.tcr");
	public static final KeyMapping SKILL_3 = new KeyMapping(buildKey("skill_3"), GLFW.GLFW_KEY_3, "key.categories.tcr");

	public static String buildKey(String name){
		return "key.tcr." + name;
	}

	@SubscribeEvent
	public static void registerKeyMappings(RegisterKeyMappingsEvent event) {
		event.register(SKILL_1);
		event.register(SKILL_2);
		event.register(SKILL_3);
	}

	@Mod.EventBusSubscriber(modid = TCRCoreMod.MOD_ID, value = Dist.CLIENT)
	public static class KeyPressHandler {

		@SubscribeEvent
		public static void onClientTick(TickEvent.ClientTickEvent event) {
			if(event.phase.equals(TickEvent.Phase.END)){
				LocalPlayerPatch localPlayerPatch = ClientEngine.getInstance().getPlayerPatch();
				if(localPlayerPatch != null && localPlayerPatch.getSkill(SkillSlots.WEAPON_INNATE).getSkill() instanceof TCRWeaponInnateSkillBase && TCRPlayer.isValidWeapon(localPlayerPatch.getOriginal().getMainHandItem())) {
					while (SKILL_1.consumeClick()){
						if (Minecraft.getInstance().player != null && Minecraft.getInstance().screen == null && !Minecraft.getInstance().isPaused()) {
							sendExecuteRequest(localPlayerPatch, 1);
						}
					}
					while (SKILL_2.consumeClick()){
						if (Minecraft.getInstance().player != null && Minecraft.getInstance().screen == null && !Minecraft.getInstance().isPaused()) {
							sendExecuteRequest(localPlayerPatch, 2);
						}
					}
					while (SKILL_3.consumeClick()){
						if (Minecraft.getInstance().player != null && Minecraft.getInstance().screen == null && !Minecraft.getInstance().isPaused()) {
							sendExecuteRequest(localPlayerPatch, 3);
						}
					}
				}
			}
		}

		public static SkillExecuteEvent sendExecuteRequest(LocalPlayerPatch executor, int skillId) {
			SkillContainer container = executor.getSkill(SkillSlots.WEAPON_INNATE);
			SkillExecuteEvent event = new SkillExecuteEvent(executor, container);
			if (container.canExecute(executor, event)) {
				CPExecuteSkill packet = new CPExecuteSkill(container.getSlotId());
				packet.getBuffer().writeInt(skillId);
				EpicFightNetworkManager.sendToServer(packet);
			}
			return event;
		}

	}

}
