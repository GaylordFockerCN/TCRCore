package com.p1nero.tcrcore.entity.custom.aine_iris;

import com.p1nero.tcrcore.TCRCoreMod;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;
import software.bernie.geckolib.model.data.EntityModelData;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

@OnlyIn(Dist.CLIENT)
public class AineIrisRenderer extends GeoEntityRenderer<AineIrisEntity> {
    public AineIrisRenderer(EntityRendererProvider.Context context) {
        super(context, new DefaultedEntityGeoModel<>(ResourceLocation.fromNamespaceAndPath(TCRCoreMod.MOD_ID, "aine_iris")) {
            @Override
            public void setCustomAnimations(AineIrisEntity animatable, long instanceId, AnimationState<AineIrisEntity> animationState) {
                CoreGeoBone head = getAnimationProcessor().getBone("Head");

                if (head != null) {
                    EntityModelData entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);

                    head.setRotX(entityData.headPitch() * Mth.DEG_TO_RAD);
                    head.setRotY(entityData.netHeadYaw() * Mth.DEG_TO_RAD);
                }
            }
        });
    }

}
