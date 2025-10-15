package com.p1nero.tcrcore.capability;

import com.p1nero.tcrcore.TCRCoreMod;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.*;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Mod.EventBusSubscriber(modid = TCRCoreMod.MOD_ID)
public class TCRCapabilityProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {

    public static Capability<TCRPlayer> TCR_PLAYER = CapabilityManager.get(new CapabilityToken<>() {});

    private TCRPlayer TCRPlayer = null;

    private final LazyOptional<TCRPlayer> optional = LazyOptional.of(this::createTCRPlayer);

    private TCRPlayer createTCRPlayer() {
        if(this.TCRPlayer == null){
            this.TCRPlayer = new TCRPlayer();
        }

        return this.TCRPlayer;
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> capability, @Nullable Direction direction) {
        if(capability == TCR_PLAYER){
            return optional.cast();
        }

        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        createTCRPlayer().saveNBTData(tag);
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag tag) {
        createTCRPlayer().loadNBTData(tag);
    }

    @SubscribeEvent
    public static void attachEntityCapabilities(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof Player player) {
            if(!player.getCapability(TCRCapabilityProvider.TCR_PLAYER).isPresent()){
                event.addCapability(ResourceLocation.fromNamespaceAndPath(TCRCoreMod.MOD_ID, "tcr_player"), new TCRCapabilityProvider());
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerCloned(PlayerEvent.Clone event) {
        event.getOriginal().reviveCaps();
        if(event.isWasDeath()) {
            event.getOriginal().getCapability(TCRCapabilityProvider.TCR_PLAYER).ifPresent(oldStore -> {
                event.getEntity().getCapability(TCRCapabilityProvider.TCR_PLAYER).ifPresent(newStore -> {
                    newStore.copyFrom(oldStore);
                    if(event.getEntity() instanceof ServerPlayer serverPlayer) {
                        newStore.updateHealth(serverPlayer, false);
                    }
                    newStore.syncToClient(((ServerPlayer) event.getEntity()));
                });
            });
        }
    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if(event.phase.equals(TickEvent.Phase.END)) {
            getTCRPlayer(event.player).tick(event.player);
        }
    }

    @SubscribeEvent
    public static void onRegisterCapabilities(RegisterCapabilitiesEvent event) {
        event.register(TCRPlayer.class);
    }

    public static TCRPlayer getTCRPlayer(Player player) {
        if(player == null) {
            return new TCRPlayer();
        }
        return player.getCapability(TCRCapabilityProvider.TCR_PLAYER).orElse(new TCRPlayer());
    }

    public static void syncPlayerDataToClient(ServerPlayer serverPlayer) {
        getTCRPlayer(serverPlayer).syncToClient(serverPlayer);
    }

}
