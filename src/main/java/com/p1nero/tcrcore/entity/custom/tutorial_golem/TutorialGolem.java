package com.p1nero.tcrcore.entity.custom.tutorial_golem;

import com.p1nero.fast_tpa.network.PacketRelay;
import com.p1nero.tcrcore.TCRCoreMod;
import com.p1nero.tcrcore.capability.PlayerDataManager;
import com.p1nero.tcrcore.network.TCRPacketHandler;
import com.p1nero.tcrcore.network.packet.clientbound.PlayTitlePacket;
import com.p1nero.tcrcore.utils.WorldUtil;
import com.p1nero.tudigong.entity.XianQiEntity;
import net.minecraft.ChatFormatting;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.protocol.game.ClientboundSetTitleTextPacket;
import net.minecraft.network.protocol.game.ClientboundSoundPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.ResetUniversalAngerTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class TutorialGolem extends IronGolem {
    public TutorialGolem(EntityType<? extends IronGolem> entityType, Level level) {
        super(entityType, level);
    }

    public static AttributeSupplier setAttributes() {
        return Animal.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 20.0f)
                .add(Attributes.ATTACK_DAMAGE, 0.01f)
                .add(Attributes.ATTACK_SPEED, 1.0F)
                .add(Attributes.MOVEMENT_SPEED, 0.3f)
                .add(Attributes.KNOCKBACK_RESISTANCE, 114514f)
                .build();
    }

    @Override
    public boolean hurt(@NotNull DamageSource source, float value) {
        if(source.getEntity() instanceof ServerPlayer serverPlayer) {
            serverPlayer.displayClientMessage(TCRCoreMod.getInfo("hurt_damage", value).withStyle(ChatFormatting.RED), false);
        }
        return source.isCreativePlayer();
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.0D, true));
        this.goalSelector.addGoal(2, new MoveTowardsTargetGoal(this, 0.9D, 32.0F));
        this.goalSelector.addGoal(2, new MoveBackToVillageGoal(this, 0.6D, false));
        this.goalSelector.addGoal(4, new GolemRandomStrollInVillageGoal(this, 0.6D));
        this.goalSelector.addGoal(5, new OfferFlowerGoal(this));
        this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(0, new NearestAttackableTargetGoal<>(this, Player.class, 10, true, false, this::shouldAttack));
        this.targetSelector.addGoal(4, new ResetUniversalAngerTargetGoal<>(this, false));
    }

    /**
     * 完成试炼了才不会追着玩家
     */
    private boolean shouldAttack(LivingEntity living) {
        if(living instanceof ServerPlayer serverPlayer) {
            return !PlayerDataManager.dodged.get(serverPlayer) ||
                    !PlayerDataManager.parried.get(serverPlayer) ||
//                    !PlayerDataManager.weapon_innate_used.get(serverPlayer)||
                    !PlayerDataManager.locked.get(serverPlayer);
        }
        return false;
    }

    /**
     * 没完成就不断生提示
     */
    @Override
    public void baseTick() {
        super.baseTick();
        if(this.getTarget() instanceof ServerPlayer serverPlayer && this.tickCount % 60 == 0) {
            if(!PlayerDataManager.dodged.get(serverPlayer)) {
                PacketRelay.sendToPlayer(TCRPacketHandler.INSTANCE, new PlayTitlePacket(1), serverPlayer);
            } else if(!PlayerDataManager.parried.get(serverPlayer)) {
                PacketRelay.sendToPlayer(TCRPacketHandler.INSTANCE, new PlayTitlePacket(2), serverPlayer);
//            } else if(!PlayerDataManager.weapon_innate_used.get(serverPlayer)) {
//                serverPlayer.connection.send(new ClientboundSetTitleTextPacket(TCRCoreMod.getInfo("weapon_innate_tutorial")));
//                serverPlayer.displayClientMessage(TCRCoreMod.getInfo("weapon_innate_charge_tutorial"), true);
            } else if(!PlayerDataManager.locked.get(serverPlayer)) {
                PacketRelay.sendToPlayer(TCRPacketHandler.INSTANCE, new PlayTitlePacket(3), serverPlayer);
            } else {
                serverPlayer.connection.send(new ClientboundSetTitleTextPacket(TCRCoreMod.getInfo("you_pass")));
                serverPlayer.connection.send(new ClientboundSoundPacket(BuiltInRegistries.SOUND_EVENT.wrapAsHolder(SoundEvents.UI_TOAST_CHALLENGE_COMPLETE), SoundSource.PLAYERS, serverPlayer.getX(), serverPlayer.getY(), serverPlayer.getZ(), 1.0F, 1.0F, serverPlayer.getRandom().nextInt()));
                this.setTarget(null);
                //引导玩家去守望者处
                XianQiEntity xianQiEntity = new XianQiEntity(level(), WorldUtil.GUIDER_POS, serverPlayer, null);
                level().addFreshEntity(xianQiEntity);
            }
        }
    }
}
