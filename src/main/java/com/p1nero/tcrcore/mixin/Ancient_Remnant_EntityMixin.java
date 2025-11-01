package com.p1nero.tcrcore.mixin;

import com.github.L_Ender.cataclysm.entity.InternalAnimationMonster.IABossMonsters.Ancient_Remnant.Ancient_Remnant_Entity;
import com.github.L_Ender.cataclysm.entity.InternalAnimationMonster.IABossMonsters.IABoss_monster;
import com.github.L_Ender.cataclysm.init.ModItems;
import com.p1nero.tcrcore.TCRCoreMod;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Ancient_Remnant_Entity.class)
public abstract class Ancient_Remnant_EntityMixin extends IABoss_monster {

    public Ancient_Remnant_EntityMixin(EntityType entity, Level world) {
        super(entity, world);
    }

    @Shadow(remap = false) public abstract boolean getNecklace();

    /**
     * 添加提示
     */
    @Inject(method = "mobInteract" , at = @At("HEAD"))
    private void tcr$mobInteract(Player player, InteractionHand hand, CallbackInfoReturnable<InteractionResult> cir) {
        if(!this.getNecklace() && level().isClientSide) {
            player.displayClientMessage(TCRCoreMod.getInfo("require_item_to_wake", ModItems.NECKLACE_OF_THE_DESERT.get().getDescription()), true);
        }
    }

}
