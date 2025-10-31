package com.p1nero.tcrcore;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = TCRCoreMod.MOD_ID)
public class TCRClientConfig {
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec.IntValue TASK_UI_X = BUILDER.comment("Task UI X").defineInRange("task_ui_x_offset", 0, Integer.MIN_VALUE, Integer.MAX_VALUE);
    public static final ForgeConfigSpec.IntValue TASK_UI_Y = BUILDER.comment("Task UI Y").defineInRange("task_ui_y_offset", 0, Integer.MIN_VALUE, Integer.MAX_VALUE);
    public static final ForgeConfigSpec SPEC = BUILDER.build();

}
