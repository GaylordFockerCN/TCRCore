package com.p1nero.tcrcore.mixin;

import com.obscuria.aquamirae.AquamiraeUtils;
import com.obscuria.aquamirae.common.entities.CaptainCornelia;
import com.obscuria.aquamirae.common.items.ShellHornItem;
import com.obscuria.aquamirae.registry.AquamiraeEntities;
import com.obscuria.aquamirae.registry.AquamiraeSounds;
import com.obscuria.obscureapi.api.utils.Icons;
import com.obscuria.obscureapi.util.PlayerUtils;
import com.obscuria.obscureapi.util.TextUtils;
import com.p1nero.tcrcore.capability.PlayerDataManager;
import com.p1nero.tcrcore.utils.EntityUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ShellHornItem.class)
public class ShellHornItemMixin extends Item {

    public ShellHornItemMixin(Properties p_41383_) {
        super(p_41383_);
    }

    @Inject(method = "use", at = @At("HEAD"), cancellable = true)
    private void tcr$use(@NotNull Level world, @NotNull Player player, @NotNull InteractionHand hand, CallbackInfoReturnable<InteractionResultHolder<ItemStack>> cir) {
        InteractionResultHolder<ItemStack> ar = super.use(world, player, hand);
        if (player.level() instanceof ServerLevel level)
            level.playSound(null, player.blockPosition().above(),
                    AquamiraeSounds.ITEM_SHELL_HORN_USE.get(), SoundSource.PLAYERS, 3, 1);
        ItemStack stack = ar.getObject();
        player.swing(InteractionHand.MAIN_HAND, true);
        player.getCooldowns().addCooldown(stack.getItem(), 120);
        boolean summon = false;
        BlockPos pos = new BlockPos(0, 0, 0);
        waterSearch : for (int ix = -6; ix <= 6; ix++) {
            final int sx = player.getBlockX() + ix;
            for (int iz = -6; iz <= 6; iz++) {
                final int sz = player.getBlockZ() + iz;
                if (AquamiraeUtils.isInIceMaze(player)) {
                    if ((player.level().getBlockState(new BlockPos(sx, 62, sz))).getBlock() == Blocks.WATER
                            && (player.level().getBlockState(new BlockPos(sx, 58, sz))).getBlock() == Blocks.WATER
                            && (player.level().getBlockState(new BlockPos(sx - 1, 62, sz))).getBlock() == Blocks.WATER
                            && (player.level().getBlockState(new BlockPos(sx + 1, 62, sz))).getBlock() == Blocks.WATER
                            && (player.level().getBlockState(new BlockPos(sx, 62, sz - 1))).getBlock() == Blocks.WATER
                            && (player.level().getBlockState(new BlockPos(sx, 62, sz + 1))).getBlock() == Blocks.WATER) {
                        summon = true;
                        pos = new BlockPos(sx, 58, sz);
                        player.getCooldowns().addCooldown(stack.getItem(), 1200);
                        break waterSearch;
                    }
                }
            }
        }
        if(!PlayerDataManager.desertEyeBlessed.get(player)) {
            summon = false;
        }
        new Object() {
            private int ticks = 0;
            private float waitTicks;
            private Player summoner;
            private BlockPos pos;
            private boolean summon;

            public void start(int waitTicks, Player summoner, BlockPos pos, boolean summon) {
                this.waitTicks = waitTicks;
                this.summoner = summoner;
                this.pos = pos;
                this.summon = summon;
                MinecraftForge.EVENT_BUS.register(this);
            }

            @SubscribeEvent
            public void tick(TickEvent.ServerTickEvent event) {
                if (event.phase == TickEvent.Phase.END) {
                    this.ticks += 1;
                    if (this.ticks >= this.waitTicks) {
                        if (summon) { spawn();
                        } else if (!summoner.level().isClientSide()) {
                            PlayerUtils.sendMessage(summoner, Icons.BOSS + TextUtils.translation("info.captain_spawn_fail"));
                        }
                        MinecraftForge.EVENT_BUS.unregister(this);
                    }
                }
            }

            private void spawn() {
                if (summoner.level() instanceof ServerLevel server) {
                    Mob cornelia = new CaptainCornelia(AquamiraeEntities.CAPTAIN_CORNELIA.get(), server);
                    cornelia.moveTo(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, summoner.level().getRandom().nextFloat() * 360F, 0);
                    cornelia.finalizeSpawn(server, summoner.level().getCurrentDifficultyAt(cornelia.blockPosition()), MobSpawnType.MOB_SUMMONED,
                            null, null);
                    summoner.level().addFreshEntity(cornelia);
                }
                if (!summoner.level().isClientSide()) {
                    PlayerUtils.sendMessage(summoner, Icons.BOSS.get() + TextUtils.translation("info.captain_spawn"));
                }
            }
        }.start(60, player, pos, summon);
        cir.setReturnValue(ar);
    }

}
