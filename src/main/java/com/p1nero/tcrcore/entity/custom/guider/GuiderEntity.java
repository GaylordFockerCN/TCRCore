package com.p1nero.tcrcore.entity.custom.guider;

import com.github.dodo.dodosmobs.init.ModEntities;
import com.obscuria.aquamirae.registry.AquamiraeEntities;
import com.p1nero.dialog_lib.api.IEntityNpc;
import com.p1nero.dialog_lib.api.component.DialogueComponentBuilder;
import com.p1nero.dialog_lib.api.component.DialogNode;
import com.p1nero.dialog_lib.api.goal.LookAtConservingPlayerGoal;
import com.p1nero.dialog_lib.client.screen.DialogueScreenBuilder;
import com.p1nero.tcrcore.TCRCoreMod;
import com.p1nero.tcrcore.capability.PlayerDataManager;
import com.p1nero.tcrcore.capability.TCRCapabilityProvider;
import com.p1nero.tcrcore.capability.TCRPlayer;
import com.p1nero.tcrcore.capability.TCRTaskManager;
import com.p1nero.tcrcore.datagen.TCRAdvancementData;
import com.p1nero.tcrcore.item.TCRItems;
import com.p1nero.tcrcore.save_data.TCRDimSaveData;
import com.p1nero.tcrcore.save_data.TCRMainLevelSaveData;
import com.p1nero.tcrcore.utils.ItemUtil;
import com.p1nero.tcrcore.utils.WaypointUtil;
import com.p1nero.tcrcore.utils.WorldUtil;
import com.p1nero.tudigong.util.StructureUtils;
import dev.ftb.mods.ftbquests.item.FTBQuestsItems;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundSetTitleTextPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.ForgeRegistries;
import net.sonmok14.fromtheshadows.server.utils.registry.EntityRegistry;
import net.sonmok14.fromtheshadows.server.utils.registry.ItemRegistry;
import net.unusual.blockfactorysbosses.init.BlockFactorysBossesModEntities;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.merlin204.wraithon.WraithonMod;
import org.merlin204.wraithon.entity.WraithonEntities;
import org.merlin204.wraithon.entity.wraithon.WraithonEntity;
import org.merlin204.wraithon.entity.wraithon.WraithonEntityPatch;
import org.merlin204.wraithon.epicfight.animation.WraithonAnimations;
import org.merlin204.wraithon.util.WraithonFieldTeleporter;
import org.merlin204.wraithon.worldgen.WraithonDimensions;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;
import xaero.hud.minimap.waypoint.WaypointColor;
import yesman.epicfight.api.utils.math.Vec2i;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;

public class GuiderEntity extends PathfinderMob implements IEntityNpc, GeoEntity {

    protected static final RawAnimation IDLE = RawAnimation.begin().thenLoop("animation.god_girl.idle2");
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private Vec3 from = Vec3.ZERO;
    private Vec3 dir = Vec3.ZERO;
    private int spawnParticleTimer = 0;
    private final int particleCount = 20;
    @Nullable
    private Player conversingPlayer;

    public GuiderEntity(EntityType<? extends PathfinderMob> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    public void startSeenByPlayer(@NotNull ServerPlayer serverPlayer) {
        super.startSeenByPlayer(serverPlayer);
        TCRCapabilityProvider.syncPlayerDataToClient(serverPlayer);//保险
    }

    @Override
    public void tick() {
        super.tick();
        if(level() instanceof ServerLevel serverLevel) {
            if(conversingPlayer != null && (conversingPlayer.isRemoved() || conversingPlayer.isDeadOrDying() || conversingPlayer.distanceTo(this) > 5)) {
                conversingPlayer = null;
            }
            if(tickCount % 100 == 0) {
                BlockPos myPos = this.getOnPos();
                if(myPos.getX() != WorldUtil.GUIDER_BLOCK_POS.getX() || myPos.getZ() != WorldUtil.GUIDER_BLOCK_POS.getZ()) {
                    this.setPos(new BlockPos(WorldUtil.GUIDER_BLOCK_POS).getCenter());
                }
            }
            double step = 5.0 / particleCount;
            if(spawnParticleTimer > 0) {
                spawnParticleTimer--;
                for (int i = particleCount - spawnParticleTimer; i <= particleCount; i++) {
                    ParticleOptions particle = ParticleTypes.END_ROD;
                    double distance = i * step;
                    Vec3 particlePos = from.add(dir.scale(distance).add(0, i * 0.1, 0));
                    serverLevel.sendParticles(
                            particle,
                            particlePos.x,
                            particlePos.y,
                            particlePos.z,
                            0,
                            dir.x, dir.y, dir.z,
                            0.1f
                    );
                }
            }
        }
    }

    @Override
    public boolean hurt(@NotNull DamageSource source, float value) {
        if(source.getEntity() instanceof Player player && player.isCreative()) {
            player.displayClientMessage(Component.translatable("/summon " + ForgeRegistries.ENTITY_TYPES.getKey(this.getType())), false);
            this.discard();
        }
        if (source.getEntity() instanceof ServerPlayer serverPlayer) {
            //彩蛋对话
            if (this.getConversingPlayer() == null) {
                CompoundTag compoundTag = new CompoundTag();
                compoundTag.putBoolean("from_hurt", true);
                this.sendDialogTo(serverPlayer, compoundTag);
                this.setConversingPlayer(serverPlayer);
            } else {
                return false;
            }
            source.getEntity().hurt(this.damageSources().indirectMagic(this, this), value * 0.5F);
            EntityType.LIGHTNING_BOLT.spawn(serverPlayer.serverLevel(), serverPlayer.getOnPos(), MobSpawnType.MOB_SUMMONED);
        }
        return source.isCreativePlayer();
    }

    public static AttributeSupplier setAttributes() {
        return Animal.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 20.0f)
                .add(Attributes.ATTACK_DAMAGE, 1.0f)
                .add(Attributes.ATTACK_SPEED, 2.0f)
                .add(Attributes.MOVEMENT_SPEED, 0.3f)
                .add(Attributes.KNOCKBACK_RESISTANCE, 114514f)
                .build();
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new LookAtConservingPlayerGoal<>(this));
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(2, new LookAtPlayerGoal(this, Player.class, 8.0F));
    }

    @Override
    public boolean isPushable() {
        return false;
    }

    @Override
    public boolean removeWhenFarAway(double p_21542_) {
        return false;
    }

    @Override
    protected @NotNull InteractionResult mobInteract(@NotNull Player player, @NotNull InteractionHand hand) {
        if (player instanceof ServerPlayer serverPlayer) {
            CompoundTag tag = new CompoundTag();
            tag.putInt("stage", PlayerDataManager.stage.getInt(player));
            tag.putBoolean("finished", TCRMainLevelSaveData.get(serverPlayer.serverLevel()).isAllFinish());
            tag.putBoolean("map_mark", PlayerDataManager.mapMarked.get(serverPlayer));
            tag.putBoolean("pillager_kill", PlayerDataManager.pillagerKilled.get(serverPlayer));
            tag.putBoolean("fire_eye_get", PlayerDataManager.flameEyeTraded.get(serverPlayer));
            tag.putBoolean("finish_all_eye_boss", PlayerDataManager.isAllEyeGet(serverPlayer));
            tag.putBoolean("finish_all_altar_boss", PlayerDataManager.isAllAltarKilled(serverPlayer));
            ItemStack itemStack = player.getItemInHand(hand);
            if (itemStack.is(TCRItems.ANCIENT_ORACLE_FRAGMENT.get())
                    && (serverPlayer.isCreative()
                        || (itemStack.hasTag() && itemStack.getOrCreateTag().getString(TCRPlayer.PLAYER_NAME).equals(player.getGameProfile().getName())))) {
                tag.putBoolean("is_oracle", true);
            }
            this.sendDialogTo(serverPlayer, tag);
        }
        return InteractionResult.sidedSuccess(level().isClientSide);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public DialogueScreenBuilder getDialogueBuilder(CompoundTag compoundTag) {
        int stage = compoundTag.getInt("stage");
        DialogueScreenBuilder treeBuilder = new DialogueScreenBuilder(this);
        DialogueComponentBuilder dBuilder = new DialogueComponentBuilder(this);
        if (compoundTag.getBoolean("from_hurt")) {
            treeBuilder.start(5).addFinalChoice(6);
            return treeBuilder;
        }
        if(compoundTag.getBoolean("finished") && compoundTag.getBoolean("finish_all_eye_boss")) {
            if(compoundTag.getBoolean("finish_all_altar_boss")) {
                treeBuilder.start(8)
                        .addChoice(12, 11)
                        .thenExecute(4)
                        .thenExecute((dialogueScreen -> GuiderGeoRenderer.useRedModel = true))
                        .addChoice(13, 12)
                        .addChoice(14, 13)
                        .addFinalChoice(15, 3);
            } else {
                treeBuilder.start(21).addFinalChoice(0);
            }
            return treeBuilder;
        }

        if (compoundTag.getBoolean("is_oracle")) {
            switch (stage) {
                case 0 -> treeBuilder.start(7)
                        .addChoice(dBuilder.optWithBrackets(9),
                                dBuilder.ans(15,
                                        Component.literal(StructureUtils.getPrettyStructureName(ResourceLocation.parse(WorldUtil.SKY_ISLAND))).withStyle(ChatFormatting.AQUA),
                                        TCRCoreMod.getInfo("iron_golem_name").withStyle(ChatFormatting.GOLD),
                                        Component.literal(StructureUtils.getPrettyStructureName(ResourceLocation.parse(WorldUtil.SKY_ISLAND))).withStyle(ChatFormatting.AQUA)))
                        .thenExecute(2)
                        .addFinalChoice(dBuilder.optWithBrackets(5), 1);

                case 1 -> treeBuilder.start(7)
                        .addChoice(dBuilder.optWithBrackets(9),
                                dBuilder.ans(16,
                                        Component.literal(StructureUtils.getPrettyStructureName(ResourceLocation.parse(WorldUtil.COVES))).withStyle(ChatFormatting.BLUE),
                                        EntityRegistry.BULLDROGIOTH.get().getDescription().copy().withStyle(ChatFormatting.GOLD),
                                        Component.literal(StructureUtils.getPrettyStructureName(ResourceLocation.parse(WorldUtil.COVES))).withStyle(ChatFormatting.BLUE),
                                        ItemRegistry.CRIMSON_SHELL.get().getDescription().copy().withStyle(ChatFormatting.RED)))
                        .thenExecute(2)
                        .addFinalChoice(dBuilder.optWithBrackets(5), 1);

                case 3 -> treeBuilder.start(7)
                        .addChoice(dBuilder.optWithBrackets(9),
                                dBuilder.ans(17,
                                        Component.literal(StructureUtils.getPrettyStructureName(ResourceLocation.parse(WorldUtil.CURSED))).withStyle(ChatFormatting.DARK_GREEN),
                                        AquamiraeEntities.CAPTAIN_CORNELIA.get().getDescription().copy().withStyle(ChatFormatting.GOLD),
                                        Component.literal(StructureUtils.getPrettyStructureName(ResourceLocation.parse(WorldUtil.CURSED))).withStyle(ChatFormatting.DARK_GREEN)))
                        .thenExecute(2)
                        .addFinalChoice(dBuilder.optWithBrackets(5), 1);

                case 2 -> treeBuilder.start(7)
                        .addChoice(dBuilder.optWithBrackets(9),
                                dBuilder.ans(18,
                                        Component.literal(StructureUtils.getPrettyStructureName(ResourceLocation.parse(WorldUtil.SAND))).withStyle(ChatFormatting.YELLOW),
                                        ModEntities.BONE_CHIMERA.get().getDescription().copy().withStyle(ChatFormatting.GOLD),
                                        Component.literal(StructureUtils.getPrettyStructureName(ResourceLocation.parse(WorldUtil.SAND))).withStyle(ChatFormatting.YELLOW)))
                        .thenExecute(2)
                        .addFinalChoice(dBuilder.optWithBrackets(5), 1);

                case 4 -> treeBuilder.start(7)
                        .addChoice(dBuilder.optWithBrackets(9),
                                dBuilder.ans(19,
                                        Component.literal(StructureUtils.getPrettyStructureName(ResourceLocation.parse(WorldUtil.FIRE))).withStyle(ChatFormatting.RED),
                                        BlockFactorysBossesModEntities.UNDERWORLD_KNIGHT.get().getDescription().copy().withStyle(ChatFormatting.RED),
                                        Component.literal(StructureUtils.getPrettyStructureName(ResourceLocation.parse(WorldUtil.FIRE))).withStyle(ChatFormatting.RED)))
                        .thenExecute(2)
                        .addFinalChoice(dBuilder.optWithBrackets(5), 1);
                default -> treeBuilder.start(20).addFinalChoice(17);
            }
        }
        //正式起航，改变一下对话
        else if(compoundTag.getBoolean("map_mark")) {
            DialogNode root = new DialogNode(dBuilder.ans(8), dBuilder.optWithBrackets(0));//开场白 | 返回

            //接下来干鸟
            DialogNode ans1 = new DialogNode(dBuilder.ans(9), dBuilder.optWithBrackets(10))
                    .addChild(root);

            DialogNode ans2 = new DialogNode(dBuilder.ans(10), dBuilder.optWithBrackets(11))
                    .addChild(root);

            DialogNode ans3 = new DialogNode(dBuilder.ans(14), dBuilder.optWithBrackets(16))
                    .addChild(root);

            //如何获得神谕
            DialogNode ans4 = new DialogNode(dBuilder.ans(22), dBuilder.optWithBrackets(18))
                    .addChild(root);

            root.addChild(ans2).addChild(ans4).addChild(ans1).addChild(ans3);

            treeBuilder.setRoot(root);
            return treeBuilder;
        } else {
            DialogNode root = new DialogNode(dBuilder.ans(0), dBuilder.optWithBrackets(0));//开场白 | 返回

            DialogNode ans1 = new DialogNode(dBuilder.ans(1), dBuilder.optWithBrackets(1))
                    .addChild(root);

            DialogNode ans2 = new DialogNode(dBuilder.ans(2), dBuilder.optWithBrackets(3))
                    .addChild(root);

            DialogNode ans3 = new DialogNode(dBuilder.ans(3), dBuilder.optWithBrackets(4))
                    .addChild(root);

            if (compoundTag.getBoolean("pillager_kill")) {
                ans3 = new DialogNode(dBuilder.ans(4), dBuilder.optWithBrackets(7))
                        .addChild(root);
                root.addChild(new DialogNode(dBuilder.ans(6), dBuilder.optWithBrackets(8))
                        .addChild(root));
            }

            root.addChild(ans1).addChild(ans2).addChild(ans3);

            treeBuilder.setRoot(root);
        }
        return treeBuilder;
    }

    @Override
    public void handleNpcInteraction(ServerPlayer player, int code) {
        if(code == 4) {
            level().playSound(null, getX(), getY(), getZ(), SoundEvents.WITCH_AMBIENT, SoundSource.BLOCKS, 1.0F, 1.0F);
        }

        if (code == 3) {
            ServerLevel wraithonLevel = player.server.getLevel(WraithonDimensions.SANCTUM_OF_THE_WRAITHON_LEVEL_KEY);
            player.changeDimension(wraithonLevel, new WraithonFieldTeleporter());
            if(!TCRDimSaveData.get(wraithonLevel).isBossSummoned() && wraithonLevel.getEntities(WraithonEntities.WRAITHON.get(), LivingEntity::isAlive).isEmpty()) {
                EpicFightCapabilities.getUnparameterizedEntityPatch(player, ServerPlayerPatch.class).ifPresent(serverPlayerPatch -> {
                    serverPlayerPatch.playAnimationSynchronized(WraithonAnimations.BIPE_COME, 0);
                });
                WraithonEntity wraithonEntity = WraithonEntities.WRAITHON.get().spawn(wraithonLevel, WraithonMod.WRAITHON_SPAWN_POS, MobSpawnType.MOB_SUMMONED);
                EpicFightCapabilities.getUnparameterizedEntityPatch(wraithonEntity, WraithonEntityPatch.class).ifPresent(wraithonEntityPatch -> {
                    wraithonEntityPatch.playAnimationSynchronized(WraithonAnimations.WRAITHON_BEGIN, 0);
                });
                TCRDimSaveData.get(wraithonLevel).setBossSummoned(true);
            }
            player.displayClientMessage(TCRCoreMod.getInfo("wraithon_start_tip"), false);
        }

        //对下面的补充，对话结束再说
        if(code == 1) {
            TCRAdvancementData.finishAdvancement("mark_map", player);
            int stage = PlayerDataManager.stage.getInt(player);
            if(stage <= 3) {
                player.displayClientMessage(TCRCoreMod.getInfo("unlock_new_ftb_page"), false);
            }
            if(stage <= 5) {
                player.connection.send(new ClientboundSetTitleTextPacket(TCRCoreMod.getInfo("press_to_open_map")));
            }
            spawnParticleTimer = particleCount;
        }

        if (code == 2) {
            //揭示预言，即解锁新玩法。根据记录的id解锁，初始阶段0， 1解锁时装和武器 2解锁盔甲和boss图鉴，3解锁附魔地狱末地，具体在FTB看
            //同时按阶段来解锁boss提示
            int stage = PlayerDataManager.stage.getInt(player);
            int newStage = stage + 1;
            if(newStage > 5) {
                this.setConversingPlayer(null);
                return;
            }
            TCRTaskManager.GIVE_ORACLE_TO_KEEPER.finish(player);
            TCRAdvancementData.finishAdvancement("stage" + (newStage), player);
            PlayerDataManager.stage.put(player, ((double) newStage));
            level().playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.UI_TOAST_CHALLENGE_COMPLETE, SoundSource.PLAYERS, 1.0F, 1.0F);
            if (player.getMainHandItem().is(TCRItems.ANCIENT_ORACLE_FRAGMENT.get())) {
                player.getMainHandItem().shrink(1);
            } else if (player.getOffhandItem().is(TCRItems.ANCIENT_ORACLE_FRAGMENT.get())){
                player.getOffhandItem().shrink(1);
            }

            if(!PlayerDataManager.mapMarked.get(player)){
                ItemUtil.addItem(player, FTBQuestsItems.BOOK.get(), 1);
                PlayerDataManager.mapMarked.put(player, true);
            }
            Vec2i pos = null;
            if(newStage == 1) {
                pos = WorldUtil.getNearbyStructurePos(player, WorldUtil.SKY_ISLAND);//天空岛
                if (pos != null) {
                    WaypointUtil.sendWaypoint(player, TCRCoreMod.getInfoKey("storm_pos"), new BlockPos(pos.x, 230, pos.y), WaypointColor.AQUA);
                }
            }

            if(newStage == 2) {
                pos = WorldUtil.getNearbyStructurePos(player, WorldUtil.COVES);//隐秘水湾
                if (pos != null) {
                    BlockPos covesPos = new BlockPos(pos.x, 156, pos.y);
                    TCRMainLevelSaveData.get(player.serverLevel()).setAbyssPos(covesPos);
                    WaypointUtil.sendWaypoint(player, TCRCoreMod.getInfoKey("abyss_pos"), covesPos, WaypointColor.DARK_BLUE);
                }
            }

            if(newStage == 4) {
                pos = WorldUtil.getNearbyStructurePos(player, WorldUtil.CURSED);//船长
                if (pos != null) {
                    WaypointUtil.sendWaypoint(player, TCRCoreMod.getInfoKey("cursed_pos"), new BlockPos(pos.x, 64, pos.y), WaypointColor.BLUE);
                }
            }

            if(newStage == 3) {
                pos = WorldUtil.getNearbyStructurePos(player, WorldUtil.SAND);//奇美拉
                if (pos != null) {
                    WaypointUtil.sendWaypoint(player, TCRCoreMod.getInfoKey("desert_pos"), new BlockPos(pos.x, 64, pos.y), WaypointColor.YELLOW);
                }
            }

            if(newStage == 5) {
                pos = WorldUtil.getNearbyStructurePos(player, WorldUtil.FIRE);
                if (pos != null) {
                    WaypointUtil.sendWaypoint(player, TCRCoreMod.getInfoKey("flame_pos"), new BlockPos(pos.x, 95, pos.y), WaypointColor.RED);
                }

                //召唤龙
                if(player.getRandom().nextBoolean()) {
                    net.alp.monsterexpansion.entity.ModEntities.SKRYTHE.get().spawn(player.serverLevel(), new BlockPos(WorldUtil.GOLEM_CENTER_POS_VEC3I.above(10)), MobSpawnType.MOB_SUMMONED);
                } else {
                    net.alp.monsterexpansion.entity.ModEntities.RHYZA.get().spawn(player.serverLevel(), new BlockPos(WorldUtil.GOLEM_CENTER_POS_VEC3I.above(4)), MobSpawnType.MOB_SUMMONED);
                }
            }

            if(pos != null) {
                from = player.getEyePosition();
                Vec3 target = new Vec3(pos.x, player.getEyeY(), pos.y);
                dir = target.subtract(from).normalize();
            }
            player.level().playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.END_PORTAL_SPAWN, player.getSoundSource(), 1.0F, 1.0F);
            return;
        }

        if(!PlayerDataManager.pillagerKilled.get(player)) {
            TCRTaskManager.KILL_PILLAGER.start(player);
            DialogueComponentBuilder dBuilder = new DialogueComponentBuilder(this);
            player.displayClientMessage(dBuilder.buildDialogue(this, dBuilder.ans(3)), false);
        }
        this.setConversingPlayer(null);
    }

    @Override
    public void setConversingPlayer(@Nullable Player player) {
        this.conversingPlayer = player;
    }

    @Override
    public @Nullable Player getConversingPlayer() {
        return conversingPlayer;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, this::deployAnimController));
    }

    protected <E extends GuiderEntity> PlayState deployAnimController(final AnimationState<E> state) {
        return state.setAndContinue(IDLE);
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }
}
