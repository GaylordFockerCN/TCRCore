package com.p1nero.tcrcore.events;

import com.github.L_Ender.cataclysm.init.ModEntities;
import com.github.L_Ender.cataclysm.init.ModItems;
import com.hm.efn.registries.EFNItem;
import com.p1nero.tcrcore.TCRCoreMod;
import com.p1nero.tcrcore.block.TCRBlocks;
import com.p1nero.tcrcore.block.client.AltarBlockRenderer;
import com.p1nero.tcrcore.block.entity.TCRBlockEntities;
import com.p1nero.tcrcore.client.gui.BlockTooltipHandler;
import com.p1nero.tcrcore.client.item_renderer.RenderDualBokken;
import com.p1nero.tcrcore.entity.TCREntities;
import com.p1nero.tcrcore.entity.custom.girl.GirlGeoRenderer;
import com.p1nero.tcrcore.entity.custom.guider.GuiderGeoRenderer;
import com.p1nero.tcrcore.entity.custom.tutorial_golem.TutorialGolemRenderer;
import net.minecraft.ChatFormatting;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.entity.ItemEntityRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import yesman.epicfight.api.client.forgeevent.PatchedRenderersEvent;
import yesman.epicfight.client.renderer.patched.entity.PIronGolemRenderer;

@Mod.EventBusSubscriber(modid = TCRCoreMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientModEvents {

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {

        EntityRenderers.register(TCREntities.CUSTOM_COLOR_ITEM.get(), ItemEntityRenderer::new);
        EntityRenderers.register(TCREntities.GUIDER.get(), GuiderGeoRenderer::new);
        EntityRenderers.register(TCREntities.GIRL.get(), GirlGeoRenderer::new);
        EntityRenderers.register(TCREntities.TUTORIAL_GOLEM.get(), TutorialGolemRenderer::new);

        BlockTooltipHandler.registerBlockTooltip(TCRBlocks.ABYSS_ALTAR_BLOCK,
                TCRCoreMod.getInfo("altar_dim_info"),
                TCRCoreMod.getInfo("related_loot", ModEntities.THE_LEVIATHAN.get().getDescription().copy().withStyle(ChatFormatting.BLUE), ModItems.TIDAL_CLAWS.get().getDescription().copy().withStyle(ChatFormatting.GOLD)),
                TCRCoreMod.getInfo("related_loot", ModEntities.CORALSSUS.get().getDescription(), EFNItem.NF_CLAW.get().getDescription().copy().withStyle(ChatFormatting.LIGHT_PURPLE)));
        BlockTooltipHandler.registerBlockTooltip(TCRBlocks.CURSED_ALTAR_BLOCK,
                TCRCoreMod.getInfo("altar_dim_info"),
                TCRCoreMod.getInfo("related_loot", ModEntities.MALEDICTUS.get().getDescription().copy().withStyle(ChatFormatting.DARK_GREEN), ModItems.SOUL_RENDER.get().getDescription().copy().withStyle(ChatFormatting.GOLD)));
        BlockTooltipHandler.registerBlockTooltip(TCRBlocks.DESERT_ALTAR_BLOCK,
                TCRCoreMod.getInfo("altar_dim_info"),
                TCRCoreMod.getInfo("related_loot", ModEntities.ANCIENT_REMNANT.get().getDescription().copy().withStyle(ChatFormatting.YELLOW), ModItems.WRATH_OF_THE_DESERT.get().getDescription().copy().withStyle(ChatFormatting.GOLD)),
                TCRCoreMod.getInfo("related_loot", ModEntities.KOBOLEDIATOR.get().getDescription(), EFNItem.EXSILIUMGLADIUS.get().getDescription().copy().withStyle(ChatFormatting.LIGHT_PURPLE)));
        BlockTooltipHandler.registerBlockTooltip(TCRBlocks.FLAME_ALTAR_BLOCK,
                TCRCoreMod.getInfo("altar_dim_info"),
                TCRCoreMod.getInfo("related_loot", ModEntities.IGNIS.get().getDescription().copy().withStyle(ChatFormatting.RED), ModItems.THE_INCINERATOR.get().getDescription().copy().withStyle(ChatFormatting.GOLD)));
        BlockTooltipHandler.registerBlockTooltip(TCRBlocks.STORM_ALTAR_BLOCK,
                TCRCoreMod.getInfo("altar_dim_info"),
                TCRCoreMod.getInfo("related_loot", ModEntities.SCYLLA.get().getDescription().copy().withStyle(ChatFormatting.AQUA), ModItems.CERAUNUS.get().getDescription().copy().withStyle(ChatFormatting.GOLD)));
        BlockTooltipHandler.registerBlockTooltip(TCRBlocks.VOID_ALTAR_BLOCK,
                TCRCoreMod.getInfo("altar_dim_info"),
                TCRCoreMod.getInfo("related_loot", "???", "???"));
        BlockTooltipHandler.registerBlockTooltip(TCRBlocks.MECH_ALTAR_BLOCK,
                TCRCoreMod.getInfo("altar_dim_info"),
                TCRCoreMod.getInfo("related_loot", "???", "???"));
        BlockTooltipHandler.registerBlockTooltip(TCRBlocks.MONST_ALTAR_BLOCK,
                TCRCoreMod.getInfo("altar_dim_info"),
                TCRCoreMod.getInfo("related_loot", "???", "???"));
    }
    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public static void onRenderPatched(PatchedRenderersEvent.Add event) {
        EntityRendererProvider.Context context = event.getContext();
        event.addPatchedEntityRenderer(TCREntities.TUTORIAL_GOLEM.get(), (entityType) -> new PIronGolemRenderer(context, entityType).initLayerLast(context, entityType));
    }

    @SubscribeEvent
    public static void onRendererSetup(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(TCRBlockEntities.ABYSS_ALTAR_BLOCK_ENTITY.get(), AltarBlockRenderer::new);
        event.registerBlockEntityRenderer(TCRBlockEntities.DESERT_ALTAR_BLOCK_ENTITY.get(), AltarBlockRenderer::new);
        event.registerBlockEntityRenderer(TCRBlockEntities.STORM_ALTAR_BLOCK_ENTITY.get(), AltarBlockRenderer::new);
        event.registerBlockEntityRenderer(TCRBlockEntities.FLAME_ALTAR_BLOCK_ENTITY.get(), AltarBlockRenderer::new);
        event.registerBlockEntityRenderer(TCRBlockEntities.CURSED_ALTAR_BLOCK_ENTITY.get(), AltarBlockRenderer::new);
        event.registerBlockEntityRenderer(TCRBlockEntities.MONST_ALTAR_BLOCK_ENTITY.get(), AltarBlockRenderer::new);
        event.registerBlockEntityRenderer(TCRBlockEntities.VOID_ALTAR_BLOCK_ENTITY.get(), AltarBlockRenderer::new);
        event.registerBlockEntityRenderer(TCRBlockEntities.MECH_ALTAR_BLOCK_ENTITY.get(), AltarBlockRenderer::new);
    }


    @SubscribeEvent
    public static void registerItemRenderer(PatchedRenderersEvent.RegisterItemRenderer event) {
        event.addItemRenderer(ResourceLocation.fromNamespaceAndPath(TCRCoreMod.MOD_ID, "dual_bokken"), RenderDualBokken::new);
    }
}
