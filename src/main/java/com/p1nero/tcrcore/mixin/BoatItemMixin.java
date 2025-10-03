package com.p1nero.tcrcore.mixin;

import com.p1nero.tcrcore.TCRCoreMod;
import net.minecraft.ChatFormatting;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.BoatItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.function.Predicate;

@Mixin(BoatItem.class)
public abstract class BoatItemMixin extends Item {

    public BoatItemMixin(Properties p_41383_) {
        super(p_41383_);
    }

    @Shadow
    protected abstract Boat getBoat(Level p_220017_, HitResult p_220018_);

    @Shadow
    @Final
    private Boat.Type type;

    @Shadow
    @Final
    private static Predicate<Entity> ENTITY_PREDICATE;

    @Inject(method = "use", at = @At("HEAD"), cancellable = true)
    private void tcr$use(Level p_40622_, Player player, InteractionHand p_40624_, CallbackInfoReturnable<InteractionResultHolder<ItemStack>> cir) {
        ItemStack itemstack = player.getItemInHand(p_40624_);
        HitResult hitresult = getPlayerPOVHitResult(p_40622_, player, ClipContext.Fluid.ANY);
        if (hitresult.getType() == HitResult.Type.MISS) {
            cir.setReturnValue(InteractionResultHolder.pass(itemstack));
        } else {
            Vec3 vec3 = player.getViewVector(1.0F);
            double d0 = 5.0D;
            List<Entity> list = p_40622_.getEntities(player, player.getBoundingBox().expandTowards(vec3.scale(5.0D)).inflate(1.0D), ENTITY_PREDICATE);
            if (!list.isEmpty()) {
                Vec3 vec31 = player.getEyePosition();

                for(Entity entity : list) {
                    AABB aabb = entity.getBoundingBox().inflate((double)entity.getPickRadius());
                    if (aabb.contains(vec31)) {
                        player.displayClientMessage(TCRCoreMod.getInfo("no_place_to_ship").withStyle(ChatFormatting.RED), true);
                        cir.setReturnValue(InteractionResultHolder.pass(itemstack));
                    }
                }
            }

            if (hitresult.getType() == HitResult.Type.BLOCK) {
                Boat boat = this.getBoat(p_40622_, hitresult);
                boat.setVariant(this.type);
                boat.setYRot(player.getYRot());
                if (!p_40622_.noCollision(boat, boat.getBoundingBox())) {
                    player.displayClientMessage(TCRCoreMod.getInfo("no_place_to_ship").withStyle(ChatFormatting.RED), true);
                    cir.setReturnValue(InteractionResultHolder.fail(itemstack));
                } else {
                    if (!p_40622_.isClientSide) {
                        p_40622_.addFreshEntity(boat);
                        p_40622_.gameEvent(player, GameEvent.ENTITY_PLACE, hitresult.getLocation());
                        if (!player.getAbilities().instabuild) {
                            itemstack.shrink(1);
                        }
                    }

                    player.awardStat(Stats.ITEM_USED.get(this));
                    cir.setReturnValue(InteractionResultHolder.sidedSuccess(itemstack, p_40622_.isClientSide()));
                }
            } else {
                cir.setReturnValue(InteractionResultHolder.pass(itemstack));
            }
        }
        cir.setReturnValue(InteractionResultHolder.pass(itemstack));
    }
}
