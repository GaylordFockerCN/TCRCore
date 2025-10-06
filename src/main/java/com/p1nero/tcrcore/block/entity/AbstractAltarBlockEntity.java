package com.p1nero.tcrcore.block.entity;

import com.github.L_Ender.cataclysm.init.ModItems;
import com.p1nero.cataclysm_dimension.worldgen.CataclysmDimensions;
import com.p1nero.cataclysm_dimension.worldgen.portal.CDNetherTeleporter;
import com.p1nero.cataclysm_dimension.worldgen.portal.CDTeleporter;
import com.p1nero.tcrcore.TCRCoreMod;
import com.p1nero.tcrcore.capability.PlayerDataManager;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.network.protocol.game.ClientboundSoundPacket;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public abstract class AbstractAltarBlockEntity extends BlockEntity {
    protected final Item itemInnate;
    protected boolean isActivated;

    public AbstractAltarBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState blockState, Item item) {
        super(type, pos, blockState);
        this.itemInnate = item;
    }

    @Override
    public void load(@NotNull CompoundTag tag) {
        isActivated = tag.getBoolean("isActivated");
        super.load(tag);
    }

    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public @NotNull CompoundTag getUpdateTag() {
        CompoundTag compoundTag = super.getUpdateTag();
        saveAdditional(compoundTag);
        return compoundTag;
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag tag) {
        tag.putBoolean("isActivated", isActivated);
        super.saveAdditional(tag);
    }

    public boolean isActivated() {
        return isActivated;
    }

    public Item getItemInnate() {
        return itemInnate;
    }

    public void sync() {
        if (this.level != null) {
            this.level.sendBlockUpdated(this.getBlockPos(), this.getBlockState(), this.getBlockState(), 3);
        }
        setChanged();
    }

    /**
     * 玩家右键，即激活或者准备进入
     */
    public void onPlayerInteract(@NotNull BlockState pState, @NotNull Level pLevel, @NotNull BlockPos pPos, @NotNull Player pPlayer, @NotNull InteractionHand pHand, @NotNull BlockHitResult pHit) {
        if(pLevel.isClientSide) {
            return;
        }
        ServerLevel serverLevel = (ServerLevel) level;
        MinecraftServer minecraftServer = pPlayer.getServer();
        if(minecraftServer == null) {
            return;
        }
        if(this.isActivated){
            if(!checkEyeFound(pPlayer)) {
                pPlayer.displayClientMessage(TCRCoreMod.getInfo("can_not_enter_before_finish"), false);
                return;
            }

            ItemStack defaultInstance = this.itemInnate.getDefaultInstance();
            boolean flag = true;
            if(defaultInstance.is(ModItems.ABYSS_EYE.get())) {
                ServerLevel level = minecraftServer.getLevel(CataclysmDimensions.CATACLYSM_ABYSSAL_DEPTHS_LEVEL_KEY);
                pPlayer.changeDimension(level, new CDTeleporter(new BlockPos(0, 200, 0)));
            } else if(defaultInstance.is(ModItems.MECH_EYE.get())) {
                ServerLevel level = minecraftServer.getLevel(CataclysmDimensions.CATACLYSM_FORGE_OF_AEONS_LEVEL_KEY);
                pPlayer.changeDimension(level, new CDTeleporter(new BlockPos(0, 150, 0)));
            } else if(defaultInstance.is(ModItems.FLAME_EYE.get())) {
                ServerLevel level = minecraftServer.getLevel(CataclysmDimensions.CATACLYSM_INFERNOS_MAW_LEVEL_KEY);
                pPlayer.changeDimension(level, new CDNetherTeleporter(new BlockPos(0, 64, 0)));
            } else if(defaultInstance.is(ModItems.VOID_EYE.get())) {
                ServerLevel level = minecraftServer.getLevel(CataclysmDimensions.CATACLYSM_BASTION_LOST_LEVEL_KEY);
                pPlayer.changeDimension(level, new CDTeleporter(new BlockPos(0, 150, 0)));
            } else if(defaultInstance.is(ModItems.MONSTROUS_EYE.get())) {
                ServerLevel level = minecraftServer.getLevel(CataclysmDimensions.CATACLYSM_SOULS_ANVIL_LEVEL_KEY);
                pPlayer.changeDimension(level, new CDNetherTeleporter(new BlockPos(0, 64, 0)));
            } else if(defaultInstance.is(ModItems.DESERT_EYE.get())) {
                ServerLevel level = minecraftServer.getLevel(CataclysmDimensions.CATACLYSM_PHARAOHS_BANE_LEVEL_KEY);
                pPlayer.changeDimension(level, new CDTeleporter(new BlockPos(0, 200, 0), 400));
            } else if(defaultInstance.is(ModItems.CURSED_EYE.get())) {
                ServerLevel level = minecraftServer.getLevel(CataclysmDimensions.CATACLYSM_ETERNAL_FROSTHOLD_LEVEL_KEY);
                pPlayer.changeDimension(level, new CDTeleporter(new BlockPos(0, 200, 0), 400));
            } else if(defaultInstance.is(ModItems.STORM_EYE.get())) {
                ServerLevel level = minecraftServer.getLevel(CataclysmDimensions.CATACLYSM_SANCTUM_FALLEN_LEVEL_KEY);
                pPlayer.changeDimension(level, new CDTeleporter(new BlockPos(0, 200, 0)));
            } else {
                flag = false;
            }
            if(flag) {
                if(pPlayer instanceof ServerPlayer player) {
                    player.connection.send(new ClientboundSoundPacket(BuiltInRegistries.SOUND_EVENT.wrapAsHolder(SoundEvents.PORTAL_TRAVEL), SoundSource.PLAYERS, player.getX(), player.getY(), player.getZ(), 1.0F, 1.0F, player.getRandom().nextInt()));
                }
                return;
            }
        }

        ItemStack mainHandItem = pPlayer.getItemInHand(pHand);
        //激活
        if(mainHandItem.is(this.itemInnate) && !this.isActivated) {
            onActive(pPlayer, mainHandItem, serverLevel, pPos);
        }
    }

    public static <T extends BlockEntity> void tick(Level pLevel, BlockPos pPos, BlockState state, T t) {
        if(t instanceof AbstractAltarBlockEntity abstractAltarBlockEntity) {
            if(pLevel.isClientSide) {
                if(abstractAltarBlockEntity.isActivated) {
                    if(pLevel.getGameTime() % 10 == 0) {
                        double rx = pPos.getX() + pLevel.getRandom().nextFloat();
                        double ry = pPos.getY() + pLevel.getRandom().nextFloat();
                        double rz = pPos.getZ() + pLevel.getRandom().nextFloat();
                        pLevel.addParticle(abstractAltarBlockEntity.getSpawnerParticle(), rx, ry, rz ,0.0D, 0.0D, 0.0D);

                        Vec3 center = pPos.getCenter();
                        pLevel.getEntitiesOfClass(Player.class, (new AABB(center, center)).inflate(5.0F)).forEach(player ->
                                player.displayClientMessage(TCRCoreMod.getInfo("enter_dimension_tip"), true));
                    }
                } else {
                    if(pLevel.getGameTime() % 10 == 0) {
                        Vec3 center = pPos.getCenter();
                        pLevel.getEntitiesOfClass(Player.class, (new AABB(center, center)).inflate(5.0F)).forEach(player ->
                                player.displayClientMessage(TCRCoreMod.getInfo("use_true_eye_tip", abstractAltarBlockEntity.itemInnate.getDescription().copy().withStyle(ChatFormatting.GOLD)), true));
                    }
                }
            } else if(pLevel.getGameTime() % 120 == 0 && abstractAltarBlockEntity.isActivated){
                pLevel.playSound(null, pPos.getX(), pPos.getY(), pPos.getZ(), SoundEvents.BEACON_AMBIENT, SoundSource.BLOCKS, 1.0F, 1.0F);
            }
        }
    }

    protected void onActive(Player pPlayer, ItemStack mainHandItem, ServerLevel pLevel, BlockPos pPos) {
        this.isActivated = true;
        this.sync();
        if(!pPlayer.isCreative()) {
            mainHandItem.shrink(1);
        }
        pLevel.playSound(null, pPos.getX(), pPos.getY(), pPos.getZ(), SoundEvents.BEACON_ACTIVATE, SoundSource.BLOCKS, 1.0F, 1.0F);
    }

    protected ParticleOptions getSpawnerParticle(){
        return ParticleTypes.FLAME;
    }

    public abstract boolean checkBossKilled(Player player);
    public abstract boolean checkEyeFound(Player player);

    public abstract int getColor();
    public float getAlpha(){
        return 1.0F;
    }

}
