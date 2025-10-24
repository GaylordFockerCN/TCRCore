package com.p1nero.tcrcore.capability;

import com.github.L_Ender.cataclysm.init.ModItems;
import com.p1nero.fast_tpa.network.PacketRelay;
import com.p1nero.tcrcore.TCRCoreMod;
import com.p1nero.tcrcore.datagen.TCRAdvancementData;
import com.p1nero.tcrcore.item.TCRItems;
import com.p1nero.tcrcore.network.TCRPacketHandler;
import com.p1nero.tcrcore.network.packet.clientbound.OpenEndScreenPacket;
import com.p1nero.tcrcore.network.packet.clientbound.SyncTCRPlayerPacket;
import com.p1nero.tcrcore.save_data.TCRMainLevelSaveData;
import com.p1nero.tcrcore.utils.ItemUtil;
import com.p1nero.tcrcore.utils.WaypointUtil;
import com.p1nero.tcrcore.utils.WorldUtil;
import dev.ftb.mods.ftbquests.item.FTBQuestsItems;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundSetTitleTextPacket;
import net.minecraft.network.protocol.game.ClientboundSoundPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.shelmarow.nightfall_invade.entity.spear_knight.Arterius;
import org.jetbrains.annotations.Nullable;
import org.merlin204.wraithon.entity.WraithonEntities;
import org.merlin204.wraithon.entity.wraithon.WraithonEntity;
import org.merlin204.wraithon.worldgen.WraithonDimensions;
import xaero.hud.minimap.waypoint.WaypointColor;
import yesman.epicfight.world.entity.ai.attribute.EpicFightAttributes;

import java.util.ArrayList;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class TCRPlayer {
    public static final String PLAYER_NAME = "player_name";
    private CompoundTag data = new CompoundTag();
    private double healthAdder = 0;
    private int tickAfterBossDieLeft;
    private int tickAfterBless;
    private int tickAfterTpToOverworld;
    private int tickAfterStartArterius;
    private boolean needToMarkMapInOverworld;
    private Arterius arterius;
    private BlockPos blessPos;
    private Item blessItem;
    //=======指路粒子=====
    private int spawnParticleTimer = 0;
    private final int particleCount = 20;
    private Vec3 from = Vec3.ZERO;
    private Vec3 dir = Vec3.ZERO;
    //===================


    public void setTickAfterTpToOverworld(int tickAfterTpToOverworld) {
        this.tickAfterTpToOverworld = tickAfterTpToOverworld;
    }

    public void setTickAfterStartArterius(int tickAfterStartArterius) {
        this.tickAfterStartArterius = tickAfterStartArterius;
    }

    public void setArterius(Arterius arterius) {
        this.arterius = arterius;
    }

    public void setTickAfterBless(int tickAfterBless) {
        this.tickAfterBless = tickAfterBless;
    }

    public void setBlessItem(Item blessItem) {
        this.blessItem = blessItem;
    }

    public void setNeedToMarkMapInOverworld(boolean needToMarkMapInOverworld) {
        this.needToMarkMapInOverworld = needToMarkMapInOverworld;
    }

    public boolean inBlessing() {
        return this.tickAfterBless > 1;
    }

    public void setBlessPos(BlockPos blessPos) {
        this.blessPos = blessPos;
    }

    public void setTickAfterBossDieLeft(int tickAfterBossDieLeft) {
        this.tickAfterBossDieLeft = tickAfterBossDieLeft;
    }

    public int getTickAfterBossDieLeft() {
        return tickAfterBossDieLeft;
    }

    private PathfinderMob currentTalkingEntity;

    public void setCurrentTalkingEntity(@Nullable PathfinderMob currentTalkingEntity) {
        this.currentTalkingEntity = currentTalkingEntity;
    }

    public @Nullable PathfinderMob getCurrentTalkingEntity() {
        return currentTalkingEntity;
    }

    public boolean getBoolean(String key) {
        return data.getBoolean(key);
    }

    public double getDouble(String key) {
        return data.getDouble(key);
    }

    public String getString(String key) {
        return data.getString(key);
    }

    public void putBoolean(String key, boolean value) {
        data.putBoolean(key, value);
    }

    public void putDouble(String key, double v) {
        data.putDouble(key, v);
    }

    public void putString(String k, String v) {
        data.putString(k, v);
    }

    public CompoundTag getData() {
        return data;
    }
    public CompoundTag saveNBTData(CompoundTag tag) {
        tag.put("customDataManager", data);
        tag.putDouble("healthAdder", healthAdder);
        tag.putInt("tickAfterBossDieLeft", tickAfterBossDieLeft);
        tag.putInt("tickAfterTpToOverworld", tickAfterTpToOverworld);
        tag.putInt("tickAfterBless", tickAfterBless);
        tag.putBoolean("needToMarkMapInOverworld", needToMarkMapInOverworld);
        return tag;
    }

    public void loadNBTData(CompoundTag tag) {
        data = tag.getCompound("customDataManager");
        healthAdder = tag.getDouble("healthAdder");
        tickAfterBossDieLeft = tag.getInt("tickAfterBossDieLeft");
        tickAfterBless = tag.getInt("tickAfterBless");
        tickAfterTpToOverworld = tag.getInt("tickAfterTpToOverworld");
        needToMarkMapInOverworld = tag.getBoolean("needToMarkMapInOverworld");
    }

    public void copyFrom(TCRPlayer old) {
        this.data = old.data;
        this.healthAdder = old.healthAdder;
        this.tickAfterBossDieLeft = old.tickAfterBossDieLeft;
        this.tickAfterBless = old.tickAfterBless;
        this.needToMarkMapInOverworld = old.needToMarkMapInOverworld;
        this.tickAfterTpToOverworld = old.tickAfterTpToOverworld;
    }

    /**
     * 移动到这延迟进行
     */
    public void tryMarkMapInOverworld(ServerPlayer serverPlayer) {
        if (!PlayerDataManager.pillagerKilled.get(serverPlayer)) {
            TCRTaskManager.clearTask(serverPlayer);
            TCRTaskManager.KILL_PILLAGER.start(serverPlayer);
        }

        if (needToMarkMapInOverworld && serverPlayer.serverLevel().dimension() == Level.OVERWORLD) {
            // 揭示预言，即解锁新玩法。根据记录的id解锁，初始阶段0，1解锁时装和武器 2解锁盔甲和boss图鉴，3解锁附魔地狱末地，具体在FTB看
            // 同时按阶段来解锁boss提示
            int stage = PlayerDataManager.stage.getInt(serverPlayer);
            int newStage = stage + 1;
            if (newStage > 5) {
                return;
            }

            TCRAdvancementData.finishAdvancement("stage" + (newStage), serverPlayer);
            PlayerDataManager.stage.put(serverPlayer, ((double) newStage));

            if (!PlayerDataManager.mapMarked.get(serverPlayer)) {
                ItemUtil.addItem(serverPlayer, FTBQuestsItems.BOOK.get(), 1);
                PlayerDataManager.mapMarked.put(serverPlayer, true);
            }

            // 异步执行耗时的结构查找
            CompletableFuture.supplyAsync(() -> {
                        BlockPos pos = null;
                        try {
                            pos = switch (newStage) {
                                case 1 -> WorldUtil.getNearbyStructurePos(serverPlayer, WorldUtil.SKY_ISLAND, 230); // 天空岛
                                case 2 -> WorldUtil.getNearbyStructurePos(serverPlayer, WorldUtil.COVES, 145); // 隐秘水湾
                                case 3 -> WorldUtil.getNearbyStructurePos(serverPlayer, WorldUtil.SAND, 64); // 奇美拉
                                case 4 -> WorldUtil.getNearbyStructurePos(serverPlayer, WorldUtil.CURSED, 64); // 船长
                                case 5 -> WorldUtil.getNearbyStructurePos(serverPlayer, WorldUtil.FIRE, 95);
                                default -> pos;
                            };
                        } catch (Exception e) {
                            // 记录异常但不要崩溃
                            System.err.println("TCRCore : Error finding structure for stage " + newStage + ": " + e.getMessage());
                        }
                        return pos;
                    })
                    .thenAccept(pos -> {

                        // 根据阶段设置路径点
                        if (pos != null) {
                            switch (newStage) {
                                case 1:
                                    WaypointUtil.sendWaypoint(serverPlayer, TCRCoreMod.getInfoKey("storm_pos"), pos, WaypointColor.AQUA);
                                    break;
                                case 2:
                                    TCRMainLevelSaveData.get(serverPlayer.serverLevel()).setAbyssPos(pos);
                                    WaypointUtil.sendWaypoint(serverPlayer, TCRCoreMod.getInfoKey("abyss_pos"), pos, WaypointColor.DARK_BLUE);
                                    break;
                                case 3:
                                    WaypointUtil.sendWaypoint(serverPlayer, TCRCoreMod.getInfoKey("desert_pos"), pos, WaypointColor.YELLOW);
                                    break;
                                case 4:
                                    WaypointUtil.sendWaypoint(serverPlayer, TCRCoreMod.getInfoKey("cursed_pos"), pos, WaypointColor.BLUE);
                                    break;
                                case 5:
                                    WaypointUtil.sendWaypoint(serverPlayer, TCRCoreMod.getInfoKey("flame_pos"), pos, WaypointColor.RED);
                                    break;
                            }

                            // 设置方向和粒子效果
                            from = serverPlayer.getEyePosition();
                            Vec3 target = new Vec3(pos.getX(), serverPlayer.getEyeY(), pos.getY());
                            dir = target.subtract(from).normalize();
                        }

                        if (stage <= 5) {
                            serverPlayer.connection.send(new ClientboundSetTitleTextPacket(TCRCoreMod.getInfo("press_to_open_map")));
                        }

                        serverPlayer.level().playSound(null, serverPlayer.getX(), serverPlayer.getY(), serverPlayer.getZ(),
                                SoundEvents.END_PORTAL_SPAWN, serverPlayer.getSoundSource(), 1.0F, 1.0F);
                        spawnParticleTimer = particleCount;
                        needToMarkMapInOverworld = false;
                        if(stage <= 3) {
                            serverPlayer.displayClientMessage(TCRCoreMod.getInfo("unlock_new_ftb_page"), false);
                        }
                        TCRTaskManager.GO_TO_OVERWORLD.finish(serverPlayer);
                    });
        }
    }

    public void syncToClient(ServerPlayer serverPlayer) {
        PacketRelay.sendToPlayer(TCRPacketHandler.INSTANCE, new SyncTCRPlayerPacket(saveNBTData(new CompoundTag())), serverPlayer);
    }

    public void tick(Player player) {
        if(player.isLocalPlayer()) {

        } else if(player instanceof ServerPlayer serverPlayer){
            ServerLevel serverLevel = serverPlayer.serverLevel();
            handleTalking(serverPlayer);
            handleAfterBossFight(serverPlayer);
            handleBless(serverLevel, serverPlayer);
            handleArtelus(serverPlayer);
            handleMarkMap(serverPlayer);
            handleParticle(serverPlayer);
        }
    }

    private void handleMarkMap(ServerPlayer serverPlayer) {
        if(tickAfterTpToOverworld > 0) {
            tickAfterTpToOverworld--;
            if(tickAfterTpToOverworld == 0) {
                tryMarkMapInOverworld(serverPlayer);
            }
        }
    }

    private void handleParticle(ServerPlayer serverPlayer) {
        double step = 5.0 / particleCount;
        if(spawnParticleTimer > 0) {
            spawnParticleTimer--;
            for (int i = particleCount - spawnParticleTimer; i <= particleCount; i++) {
                ParticleOptions particle = ParticleTypes.END_ROD;
                double distance = i * step;
                Vec3 particlePos = from.add(dir.scale(distance).add(0, i * 0.1, 0));
                serverPlayer.serverLevel().sendParticles(
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

    private void handleArtelus(ServerPlayer player) {
        if(tickAfterStartArterius > 0) {
            tickAfterStartArterius--;
            if(arterius != null) {
                arterius.getLookControl().setLookAt(player);
            } else {
                return;
            }
            if(tickAfterStartArterius % 20 == 0) {
                if(tickAfterStartArterius / 20 == 0) {
                    player.connection.send(new ClientboundSetTitleTextPacket(Component.literal("Go!").withStyle(ChatFormatting.RED)));
                    player.connection.send(new ClientboundSoundPacket(BuiltInRegistries.SOUND_EVENT.wrapAsHolder(SoundEvents.ANVIL_LAND), SoundSource.PLAYERS, player.getX(), player.getY(), player.getZ(), 1.0F, 1.0F, player.getRandom().nextInt()));
                } else {
                    player.connection.send(new ClientboundSetTitleTextPacket(Component.literal("" + tickAfterStartArterius / 20).withStyle(ChatFormatting.RED)));
                    player.connection.send(new ClientboundSoundPacket(BuiltInRegistries.SOUND_EVENT.wrapAsHolder(SoundEvents.ANVIL_LAND), SoundSource.PLAYERS, player.getX(), player.getY(), player.getZ(), 1.0F, 1.0F, player.getRandom().nextInt()));
                }
            }

            if(tickAfterStartArterius == 0) {
                arterius.setInBattle(true);
                arterius.setTarget(player);
            }

        }
    }

    private void handleTalking(ServerPlayer player) {
        if (this.currentTalkingEntity != null && this.currentTalkingEntity.isAlive()) {
            this.currentTalkingEntity.getLookControl().setLookAt(player);
            this.currentTalkingEntity.getNavigation().stop();
            if (this.currentTalkingEntity.distanceTo(player) > 8) {
                this.currentTalkingEntity = null;
            }
        }
    }

    private void handleAfterBossFight(ServerPlayer player) {
        //Boss战后的返回倒计时
        if (tickAfterBossDieLeft > 0) {
            tickAfterBossDieLeft--;
            //保险
            new ArrayList<WraithonEntity>(player.server.getLevel(WraithonDimensions.SANCTUM_OF_THE_WRAITHON_LEVEL_KEY).getEntities(WraithonEntities.WRAITHON.get(), (wraithonEntity -> !wraithonEntity.isDead())))
                    .forEach(Entity::discard);
            if (tickAfterBossDieLeft % 40 == 0) {
                player.level().playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ANVIL_LAND, SoundSource.BLOCKS, 0.8F, 0.5F + tickAfterBossDieLeft / 400.0F);
                player.level().playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.BELL_BLOCK, SoundSource.BLOCKS, 0.8F, 0.5F + tickAfterBossDieLeft / 400.0F);
            }
            player.displayClientMessage(TCRCoreMod.getInfo("second_after_boss_die_left", tickAfterBossDieLeft / 20).withStyle(ChatFormatting.BOLD, ChatFormatting.RED), true);
            if (tickAfterBossDieLeft == 0) {
                PacketRelay.sendToPlayer(TCRPacketHandler.INSTANCE, new OpenEndScreenPacket(), player);
            }
        }
    }

    private void handleBless(ServerLevel serverLevel, ServerPlayer serverPlayer) {
        //女神像祈福
        if(tickAfterBless > 0) {
            tickAfterBless--;
            if(tickAfterBless % 10 == 0) {
                // 计算进度比例 (从100到1，所以进度是1.0到0.01)
                float progress = 1.0f - (tickAfterBless / 100.0f);

                // 播放紫水晶音效，音调随进度逐渐增高
                float pitch = 0.5f + progress * 1.5f; // 从0.5到2.0
                serverLevel.playSound(null, blessPos, SoundEvents.AMETHYST_BLOCK_CHIME,
                        SoundSource.AMBIENT, 3.0F, pitch);

                // 如果进度较高，额外播放一个音效增加史诗感
                if(progress > 0.7f) {
                    serverLevel.playSound(null, blessPos, SoundEvents.BEACON_AMBIENT,
                            SoundSource.AMBIENT, 3.0F, 0.8f + progress * 0.4f);
                }

                double centerX = blessPos.getX() + 0.5;
                double centerY = blessPos.getY() + 1.0;
                double centerZ = blessPos.getZ() + 0.5;

                // 根据进度计算粒子半径和数量（随进度增加而增大）
                double baseRadius = 2.0;
                double maxRadius = 6.0;
                double currentRadius = baseRadius + (maxRadius - baseRadius) * progress;

                int baseParticleCount = 8;
                int maxParticleCount = 32;
                int currentParticleCount = baseParticleCount + (int)((maxParticleCount - baseParticleCount) * progress);

                // 在水平面上创建圆形粒子效果
                for(int i = 0; i < currentParticleCount; i++) {
                    double angle = 2 * Math.PI * i / currentParticleCount;

                    double x = centerX + currentRadius * Math.cos(angle);
                    double z = centerZ + currentRadius * Math.sin(angle);
                    double y = centerY + serverLevel.random.nextDouble() * 2.0;

                    // 从圆周向中心发射粒子
                    double speedX = (centerX - x) * 0.1;
                    double speedZ = (centerZ - z) * 0.1;
                    double speedY = 0.1;

                    serverLevel.sendParticles(ParticleTypes.END_ROD,
                            x, y, z,
                            1,
                            speedX, speedY, speedZ,
                            0.05);
                }

                // 在垂直方向上也创建粒子效果，数量也随进度增加
                int baseVerticalCount = 4;
                int maxVerticalCount = 12;
                int currentVerticalCount = baseVerticalCount + (int)((maxVerticalCount - baseVerticalCount) * progress);

                double verticalRadius = 1.5 + progress * 2.5; // 垂直粒子半径也从1.5到4.0

                for(int i = 0; i < currentVerticalCount; i++) {
                    double angle = 2 * Math.PI * i / currentVerticalCount;

                    double x = centerX + verticalRadius * Math.cos(angle);
                    double z = centerZ + verticalRadius * Math.sin(angle);

                    // 从底部向上发射粒子
                    serverLevel.sendParticles(ParticleTypes.END_ROD,
                            x, centerY - 1, z,
                            1,
                            0, 0.2, 0,
                            0.02);
                }

                // 在中心位置添加一些随机粒子，数量也随进度增加
                int baseRandomCount = 3;
                int maxRandomCount = 10;
                int currentRandomCount = baseRandomCount + (int)((maxRandomCount - baseRandomCount) * progress);

                for(int i = 0; i < currentRandomCount; i++) {
                    double offsetX = (serverLevel.random.nextDouble() - 0.5) * 2.0;
                    double offsetY = serverLevel.random.nextDouble() * 2.0;
                    double offsetZ = (serverLevel.random.nextDouble() - 0.5) * 2.0;

                    serverLevel.sendParticles(ParticleTypes.END_ROD,
                            centerX + offsetX, centerY + offsetY, centerZ + offsetZ,
                            1,
                            0, 0, 0,
                            0.1);
                }
            }
            //给神谕，加血加耐
            if(tickAfterBless == 0) {
                final double oldAdder = healthAdder;
                ItemStack oracle = TCRItems.ANCIENT_ORACLE_FRAGMENT.get().getDefaultInstance();
                oracle.getOrCreateTag().putString(PLAYER_NAME, serverPlayer.getGameProfile().getName());
                ItemStack item = blessItem == null ? serverPlayer.getMainHandItem() : blessItem.getDefaultInstance();
                if(item.is(ModItems.STORM_EYE.get()) && PlayerDataManager.stormEyeTraded.get(serverPlayer) && !PlayerDataManager.stormEyeBlessed.get(serverPlayer)) {
                    ItemUtil.addItemEntity(serverPlayer, oracle, 1, ChatFormatting.LIGHT_PURPLE.getColor().intValue());
                    healthAdder += 2.0;
                    PlayerDataManager.stormEyeBlessed.put(serverPlayer, true);
                } else if(item.is(ModItems.ABYSS_EYE.get()) && PlayerDataManager.abyssEyeTraded.get(serverPlayer) && !PlayerDataManager.abyssEyeBlessed.get(serverPlayer)) {
                    ItemUtil.addItemEntity(serverPlayer, oracle, 1, ChatFormatting.LIGHT_PURPLE.getColor().intValue());
                    healthAdder += 2.0;
                    PlayerDataManager.abyssEyeBlessed.put(serverPlayer, true);
                } else if(item.is(ModItems.DESERT_EYE.get()) && PlayerDataManager.desertEyeTraded.get(serverPlayer) && !PlayerDataManager.desertEyeBlessed.get(serverPlayer)) {
                    ItemUtil.addItemEntity(serverPlayer, oracle, 1, ChatFormatting.LIGHT_PURPLE.getColor().intValue());
                    healthAdder += 2.0;
                    PlayerDataManager.desertEyeBlessed.put(serverPlayer, true);
                    PlayerDataManager.canEnterNether.put(serverPlayer, true);
                    serverPlayer.connection.send(new ClientboundSoundPacket(BuiltInRegistries.SOUND_EVENT.wrapAsHolder(SoundEvents.END_PORTAL_SPAWN), SoundSource.PLAYERS, serverPlayer.getX(), serverPlayer.getY(), serverPlayer.getZ(), 1.0F, 1.0F, serverPlayer.getRandom().nextInt()));
                    serverPlayer.connection.send(new ClientboundSetTitleTextPacket(TCRCoreMod.getInfo("nether_unlock").withStyle(ChatFormatting.RED)));
                } else if(item.is(ModItems.CURSED_EYE.get()) && PlayerDataManager.cursedEyeTraded.get(serverPlayer) && !PlayerDataManager.cursedEyeBlessed.get(serverPlayer)) {
                    ItemUtil.addItemEntity(serverPlayer, oracle, 1, ChatFormatting.LIGHT_PURPLE.getColor().intValue());
                    healthAdder += 2.0;
                    PlayerDataManager.cursedEyeBlessed.put(serverPlayer, true);
                    PlayerDataManager.canEnterEnd.put(serverPlayer, true);
                    serverPlayer.connection.send(new ClientboundSoundPacket(BuiltInRegistries.SOUND_EVENT.wrapAsHolder(SoundEvents.END_PORTAL_SPAWN), SoundSource.PLAYERS, serverPlayer.getX(), serverPlayer.getY(), serverPlayer.getZ(), 1.0F, 1.0F, serverPlayer.getRandom().nextInt()));
                    serverPlayer.connection.send(new ClientboundSetTitleTextPacket(TCRCoreMod.getInfo("end_unlock").withStyle(ChatFormatting.LIGHT_PURPLE)));
                } else if(item.is(ModItems.FLAME_EYE.get()) && PlayerDataManager.flameEyeTraded.get(serverPlayer) && !PlayerDataManager.flameEyeBlessed.get(serverPlayer)) {
                    healthAdder += 2.0;
                    serverPlayer.connection.send(new ClientboundSoundPacket(BuiltInRegistries.SOUND_EVENT.wrapAsHolder(SoundEvents.END_PORTAL_SPAWN), SoundSource.PLAYERS, serverPlayer.getX(), serverPlayer.getY(), serverPlayer.getZ(), 1.0F, 1.0F, serverPlayer.getRandom().nextInt()));
                    PlayerDataManager.flameEyeBlessed.put(serverPlayer, true);
                } else if(item.is(ModItems.MONSTROUS_EYE.get()) && !PlayerDataManager.monstEyeBlessed.get(serverPlayer)) {
                    healthAdder += 4.0;
                    serverPlayer.connection.send(new ClientboundSoundPacket(BuiltInRegistries.SOUND_EVENT.wrapAsHolder(SoundEvents.END_PORTAL_SPAWN), SoundSource.PLAYERS, serverPlayer.getX(), serverPlayer.getY(), serverPlayer.getZ(), 1.0F, 1.0F, serverPlayer.getRandom().nextInt()));
                    PlayerDataManager.monstEyeBlessed.put(serverPlayer, true);
                } else if(item.is(ModItems.MECH_EYE.get()) && !PlayerDataManager.mechEyeBlessed.get(serverPlayer)) {
                    healthAdder += 4.0;
                    serverPlayer.connection.send(new ClientboundSoundPacket(BuiltInRegistries.SOUND_EVENT.wrapAsHolder(SoundEvents.END_PORTAL_SPAWN), SoundSource.PLAYERS, serverPlayer.getX(), serverPlayer.getY(), serverPlayer.getZ(), 1.0F, 1.0F, serverPlayer.getRandom().nextInt()));
                    PlayerDataManager.mechEyeBlessed.put(serverPlayer, true);
                } else if(item.is(ModItems.VOID_EYE.get()) && !PlayerDataManager.voidEyeBlessed.get(serverPlayer)) {
                    healthAdder += 4.0;
                    serverPlayer.connection.send(new ClientboundSoundPacket(BuiltInRegistries.SOUND_EVENT.wrapAsHolder(SoundEvents.END_PORTAL_SPAWN), SoundSource.PLAYERS, serverPlayer.getX(), serverPlayer.getY(), serverPlayer.getZ(), 1.0F, 1.0F, serverPlayer.getRandom().nextInt()));
                    PlayerDataManager.voidEyeBlessed.put(serverPlayer, true);
                }
                if(oldAdder < healthAdder) {
                    updateHealth(serverPlayer, true, oldAdder);
                    if(PlayerDataManager.isAllEyeGet(serverPlayer) && !TCRMainLevelSaveData.get(serverLevel).isAllFinish()) {
                        TCRTaskManager.LIGHT_ALL_ALTAR.start(serverPlayer);
                    }
                } else {
                    serverPlayer.displayClientMessage(TCRCoreMod.getInfo("nothing_happen_after_bless"), false);
                }
            }
        }
    }

    public void updateHealth(ServerPlayer serverPlayer, boolean showTip, double originalAdder) {
        final UUID HEALTH_MODIFIER_UUID = UUID.fromString("11451419-1981-0234-1234-123456789abc");
        float preHealth = serverPlayer.getHealth();
        float preMaxHealth = serverPlayer.getMaxHealth();
        AttributeInstance maxHealthAttr = serverPlayer.getAttribute(Attributes.MAX_HEALTH);
        if (maxHealthAttr != null) {
            maxHealthAttr.removeModifier(HEALTH_MODIFIER_UUID);
            AttributeModifier healthModifier = new AttributeModifier(
                    HEALTH_MODIFIER_UUID,
                    "health_boost",
                    healthAdder,
                    AttributeModifier.Operation.ADDITION
            );
            maxHealthAttr.addPermanentModifier(healthModifier);
            serverPlayer.setHealth(preHealth * serverPlayer.getMaxHealth() / preMaxHealth);
            if(showTip) {
                serverPlayer.displayClientMessage(Component.translatable(Attributes.MAX_HEALTH.getDescriptionId()).withStyle(ChatFormatting.RED).append(" + " + (healthAdder - originalAdder)), false);
            }
        }
        AttributeInstance staminaAttr = serverPlayer.getAttribute(EpicFightAttributes.MAX_STAMINA.get());
        if (staminaAttr != null) {
            staminaAttr.removeModifier(HEALTH_MODIFIER_UUID);
            AttributeModifier staminaModifier = new AttributeModifier(
                    HEALTH_MODIFIER_UUID,
                    "stamina_boost",
                    (healthAdder / 2.5F),
                    AttributeModifier.Operation.ADDITION
            );
            staminaAttr.addPermanentModifier(staminaModifier);
            if(showTip) {
                serverPlayer.displayClientMessage(Component.translatable(EpicFightAttributes.MAX_STAMINA.get().getDescriptionId()).withStyle(ChatFormatting.GOLD).append(String.format(" + %.1f", ((healthAdder - originalAdder) / 2.5F))), false);
            }
        }
    }

}
