package com.p1nero.tcrcore.block.client;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.p1nero.tcrcore.block.entity.AbstractAltarBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BeaconRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BeaconBlockEntity;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix3f;
import org.joml.Matrix4f;

import java.util.List;

import static net.minecraft.client.renderer.blockentity.BeaconRenderer.BEAM_LOCATION;

public class AltarBlockRenderer <T extends AbstractAltarBlockEntity> implements BlockEntityRenderer<T> {
    private ItemStack stack = null;
    public AltarBlockRenderer(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public void render(@NotNull AbstractAltarBlockEntity altarBlockEntity, float partialTick, @NotNull PoseStack poseStack, @NotNull MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        LocalPlayer localPlayer = Minecraft.getInstance().player;
        if(localPlayer == null) {
            return;
        }
        if(altarBlockEntity.isActivated()) {
            if(stack == null) {
                stack = altarBlockEntity.getItemInnate().getDefaultInstance();
            }
            poseStack.pushPose();
            poseStack.translate(0.5F, 0.35F, 0.5F);
            float time = (altarBlockEntity.getLevel().getGameTime() + partialTick) * 0.05F;
            float floatOffset = (float) Math.sin(time * 2.0F) * 0.1F;
            poseStack.translate(0.0F, floatOffset, 0.0F);
            poseStack.mulPose(Axis.YP.rotation(time));
            float scale = 1.4F + (float) Math.sin(time * 3.0F) * 0.1F;
            poseStack.scale(scale, scale, scale);
            BakedModel model = Minecraft.getInstance().getItemRenderer().getItemModelShaper().getItemModel(stack);
            Minecraft.getInstance().getItemRenderer().render(stack, ItemDisplayContext.GROUND,false,poseStack, bufferSource, LightTexture.FULL_BRIGHT, OverlayTexture.NO_OVERLAY, model);
            poseStack.popPose();

            if(altarBlockEntity.checkBossKilled(localPlayer)) {
                long i = altarBlockEntity.getLevel().getGameTime();
                int color = altarBlockEntity.getColor();
                float r = (float)(color >> 16 & 255) / 255.0F;
                float g = (float)(color >> 8 & 255) / 255.0F;
                float b = (float)(color & 255) / 255.0F;
                float[] colorArr = new float[] {r, g, b};
                poseStack.pushPose();
                poseStack.translate(0, 1, 0);
                renderBeaconBeam(poseStack, bufferSource, partialTick, i, 0, 1024, colorArr);
                poseStack.popPose();
            }

        }
    }

    private static void renderBeaconBeam(PoseStack p_112177_, MultiBufferSource p_112178_, float p_112179_, long p_112180_, int p_112181_, int p_112182_, float[] p_112183_) {
        BeaconRenderer.renderBeaconBeam(p_112177_, p_112178_, BEAM_LOCATION, p_112179_, 1.0F, p_112180_, p_112181_, p_112182_, p_112183_, 0.2F, 0.25F);
    }

    @Override
    public boolean shouldRenderOffScreen(@NotNull AbstractAltarBlockEntity entity) {
        return true;
    }

    @Override
    public int getViewDistance() {
        return 256;
    }

    @Override
    public boolean shouldRender(AbstractAltarBlockEntity p_173531_, Vec3 p_173532_) {
        return Vec3.atCenterOf(p_173531_.getBlockPos()).multiply(1.0D, 0.0D, 1.0D).closerThan(p_173532_.multiply(1.0D, 0.0D, 1.0D), this.getViewDistance());
    }
}
