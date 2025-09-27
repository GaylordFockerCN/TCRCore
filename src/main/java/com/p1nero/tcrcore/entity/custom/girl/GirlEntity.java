package com.p1nero.tcrcore.entity.custom.girl;

import artifacts.item.ArtifactItem;
import com.github.L_Ender.cataclysm.init.ModItems;
import com.hm.efn.registries.EFNItem;
import com.merlin204.sg.item.SGItems;
import com.obscuria.aquamirae.registry.AquamiraeItems;
import com.p1nero.dialog_lib.api.IEntityNpc;
import com.p1nero.dialog_lib.api.component.DialogueComponentBuilder;
import com.p1nero.dialog_lib.api.component.DialogNode;
import com.p1nero.dialog_lib.api.goal.LookAtConservingPlayerGoal;
import com.p1nero.dialog_lib.client.screen.DialogueScreenBuilder;
import com.p1nero.epicfightbow.item.EFBowItems;
import com.p1nero.tcrcore.capability.PlayerDataManager;
import com.p1nero.tcrcore.events.SafeNetherTeleporter;
import com.p1nero.tcrcore.item.TCRItems;
import com.p1nero.tcrcore.utils.ItemUtil;
import com.talhanation.smallships.client.option.ModGameOptions;
import com.yesman.epicskills.client.gui.screen.SkillTreeScreen;
import net.genzyuro.uniqueaccessories.item.UAUniqueCurioItem;
import net.kenddie.fantasyarmor.item.FAItems;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.Merchant;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.item.trading.MerchantOffers;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.ForgeRegistries;
import net.sonmok14.fromtheshadows.server.utils.registry.ItemRegistry;
import net.unusual.blockfactorysbosses.init.BlockFactorysBossesModItems;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;
import yesman.epicfight.client.ClientEngine;
import yesman.epicfight.client.world.capabilites.entitypatch.player.LocalPlayerPatch;
import yesman.epicfight.world.item.EpicFightItems;

public class GirlEntity extends PathfinderMob implements IEntityNpc, GeoEntity, Merchant {
    protected static final RawAnimation IDLE = RawAnimation.begin().thenLoop("idle");
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    @Nullable
    private Player conversingPlayer;
    @Nullable
    private Player tradingPlayer;
    private MerchantOffers offers = new MerchantOffers();
    private final MerchantOffers offersWeapon = new MerchantOffers();
    private final MerchantOffers offersArmor = new MerchantOffers();
    private final MerchantOffers artifacts = new MerchantOffers();

    public GirlEntity(EntityType<? extends PathfinderMob> p_21683_, Level p_21684_) {
        super(p_21683_, p_21684_);
        initMerchant();
    }

    private void initMerchant() {
        offers.clear();
        offersArmor.clear();
        offersWeapon.clear();
        artifacts.clear();
        ForgeRegistries.ITEMS.getValues().forEach(item -> {
            if(item instanceof ArtifactItem || item instanceof UAUniqueCurioItem) {
                artifacts.add(new MerchantOffer(
                        new ItemStack(TCRItems.ARTIFACT_TICKET.get(), 1),
                        new ItemStack(item, 1),
                        142857, 0, 0.02f));
            }
        });
        offersWeapon.add(new MerchantOffer(
                new ItemStack(SGItems.GOLEM_HEART.get(), 1),
                new ItemStack(EpicFightItems.UCHIGATANA.get(), 1),
                new ItemStack(EFNItem.SWORD_OF_PIONEER.get(), 1),
                142857, 0, 0.02f));
        offersWeapon.add(new MerchantOffer(
                new ItemStack(SGItems.GOLEM_HEART.get(), 1),
                new ItemStack(EpicFightItems.GOLDEN_DAGGER.get(), 1),
                new ItemStack(EFNItem.NF_SHORT_SWORD.get(), 1),
                142857, 0, 0.02f));
        offersWeapon.add(new MerchantOffer(
                new ItemStack(ModItems.CORAL_CHUNK.get(), 1),
                new ItemStack(EFNItem.NF_CLAW.get(), 1),
                142857, 0, 0.02f));
        offersWeapon.add(new MerchantOffer(
                new ItemStack(com.github.dodo.dodosmobs.init.ModItems.CHIERA_CLAW.get(), 1),
                new ItemStack(EFNItem.FIRE_EXSILIUMGLADIUS.get(), 1),
                142857, 0, 0.02f));
        offersWeapon.add(new MerchantOffer(
                new ItemStack(ModItems.KOBOLEDIATOR_SKULL.get(), 1),
                new ItemStack(EFNItem.EXSILIUMGLADIUS.get(), 1),
                142857, 0, 0.02f));
        offersWeapon.add(new MerchantOffer(
                new ItemStack(EFNItem.DEEPDARK_HEART.get(), 1),
                new ItemStack(EFNItem.AETHERIAL_DUSK_DUALSWORD.get(), 1),
                142857, 0, 0.02f));
        offersWeapon.add(new MerchantOffer(
                new ItemStack(Items.BOW, 1),
                new ItemStack(ModItems.BLACK_STEEL_INGOT.get(), 1),
                new ItemStack(EFBowItems.MORTIS.get(), 1),
                142857, 0, 0.02f));
        offersWeapon.add(new MerchantOffer(
                new ItemStack(BlockFactorysBossesModItems.DRAGON_BONE.get(), 4),
                new ItemStack(EFNItem.AIR_TACHI.get(), 1),
                142857, 0, 0.02f));
        offersWeapon.add(new MerchantOffer(
                new ItemStack(BlockFactorysBossesModItems.DRAGON_BONE.get(), 4),
                new ItemStack(EFNItem.CO_TACHI.get(), 1),
                142857, 0, 0.02f));
        offersWeapon.add(new MerchantOffer(
                new ItemStack(BlockFactorysBossesModItems.KNIGHT_SWORD.get(), 1),
                new ItemStack(EFNItem.RUINSGREATSWORD.get(), 1),
                142857, 0, 0.02f));
        offersWeapon.add(new MerchantOffer(
                new ItemStack(Items.NETHER_STAR, 1),
                new ItemStack(EFNItem.MEEN_SPEAR.get(), 1),
                142857, 0, 0.02f));
        offersWeapon.add(new MerchantOffer(
                new ItemStack(Items.DRAGON_EGG, 1),
                new ItemStack(EFNItem.YAMATO_DMC_IN_SHEATH.get(), 1),
                142857, 0, 0.02f));
        offersWeapon.add(new MerchantOffer(
                new ItemStack(Items.DRAGON_EGG, 1),
                new ItemStack(EFNItem.YAMATO_DMC4_IN_SHEATH.get(), 1),
                142857, 0, 0.02f));

        offersArmor.add(new MerchantOffer(
                new ItemStack(Items.NETHERITE_INGOT, 1),
                new ItemStack(Items.DIAMOND, 4),
                new ItemStack(Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE, 1),
                142857, 0, 0.02f));
        offersArmor.add(new MerchantOffer(
                new ItemStack(FAItems.MOON_CRYSTAL.get(), 1),
                new ItemStack(Items.DIAMOND_HELMET, 1),
                142857, 0, 0.02f));
        offersArmor.add(new MerchantOffer(
                new ItemStack(FAItems.MOON_CRYSTAL.get(), 1),
                new ItemStack(Items.DIAMOND_CHESTPLATE, 1),
                142857, 0, 0.02f));
        offersArmor.add(new MerchantOffer(
                new ItemStack(FAItems.MOON_CRYSTAL.get(), 1),
                new ItemStack(Items.DIAMOND_LEGGINGS, 1),
                142857, 0, 0.02f));
        offersArmor.add(new MerchantOffer(
                new ItemStack(FAItems.MOON_CRYSTAL.get(), 1),
                new ItemStack(Items.DIAMOND_BOOTS, 1),
                142857, 0, 0.02f));
        offersArmor.add(new MerchantOffer(
                new ItemStack(ItemRegistry.CRIMSON_SHELL.get(), 1),
                new ItemStack(Items.DIAMOND_HELMET, 1),
                new ItemStack(ItemRegistry.CRUST_HEAD.get(), 1),
                142857, 0, 0.02f));
        offersArmor.add(new MerchantOffer(
                new ItemStack(ItemRegistry.CRIMSON_SHELL.get(), 1),
                new ItemStack(Items.DIAMOND_CHESTPLATE, 1),
                new ItemStack(ItemRegistry.CRUST_CHEST.get(), 1),
                142857, 0, 0.02f));
        offersArmor.add(new MerchantOffer(
                new ItemStack(ItemRegistry.CRIMSON_SHELL.get(), 1),
                new ItemStack(Items.DIAMOND_LEGGINGS, 1),
                new ItemStack(ItemRegistry.CRUST_LEGGINGS.get(), 1),
                142857, 0, 0.02f));
        offersArmor.add(new MerchantOffer(
                new ItemStack(ItemRegistry.BOTTLE_OF_BLOOD.get(), 1),
                new ItemStack(Items.DIAMOND_HELMET, 1),
                new ItemStack(ItemRegistry.DIABOLIUM_HEAD.get(), 1),
                142857, 0, 0.02f));
        offersArmor.add(new MerchantOffer(
                new ItemStack(ItemRegistry.BOTTLE_OF_BLOOD.get(), 1),
                new ItemStack(Items.DIAMOND_CHESTPLATE, 1),
                new ItemStack(ItemRegistry.DIABOLIUM_CHEST.get(), 1),
                142857, 0, 0.02f));
        offersArmor.add(new MerchantOffer(
                new ItemStack(ItemRegistry.BOTTLE_OF_BLOOD.get(), 1),
                new ItemStack(Items.DIAMOND_LEGGINGS, 1),
                new ItemStack(ItemRegistry.DIABOLIUM_LEGGINGS.get(), 1),
                142857, 0, 0.02f));
        offersArmor.add(new MerchantOffer(
                new ItemStack(ItemRegistry.BOTTLE_OF_BLOOD.get(), 1),
                new ItemStack(Items.DIAMOND_BOOTS, 1),
                new ItemStack(ItemRegistry.DIABOLIUM_BOOTS.get(), 1),
                142857, 0, 0.02f));
        offersArmor.add(new MerchantOffer(
                new ItemStack(AquamiraeItems.FIN.get(), 1),
                new ItemStack(Items.DIAMOND_HELMET, 1),
                new ItemStack(AquamiraeItems.TERRIBLE_HELMET.get(), 1),
                142857, 0, 0.02f));
        offersArmor.add(new MerchantOffer(
                new ItemStack(AquamiraeItems.FIN.get(), 1),
                new ItemStack(Items.DIAMOND_CHESTPLATE, 1),
                new ItemStack(AquamiraeItems.TERRIBLE_CHESTPLATE.get(), 1),
                142857, 0, 0.02f));
        offersArmor.add(new MerchantOffer(
                new ItemStack(AquamiraeItems.FIN.get(), 1),
                new ItemStack(Items.DIAMOND_LEGGINGS, 1),
                new ItemStack(AquamiraeItems.TERRIBLE_LEGGINGS.get(), 1),
                142857, 0, 0.02f));
        offersArmor.add(new MerchantOffer(
                new ItemStack(AquamiraeItems.FIN.get(), 1),
                new ItemStack(Items.DIAMOND_BOOTS, 1),
                new ItemStack(AquamiraeItems.TERRIBLE_BOOTS.get(), 1),
                142857, 0, 0.02f));
        offersArmor.add(new MerchantOffer(
                new ItemStack(AquamiraeItems.ABYSSAL_AMETHYST.get(), 1),
                new ItemStack(Items.DIAMOND_HELMET, 1),
                new ItemStack(AquamiraeItems.ABYSSAL_HEAUME.get(), 1),
                142857, 0, 0.02f));
        offersArmor.add(new MerchantOffer(
                new ItemStack(AquamiraeItems.ABYSSAL_AMETHYST.get(), 1),
                new ItemStack(Items.DIAMOND_CHESTPLATE, 1),
                new ItemStack(AquamiraeItems.ABYSSAL_BRIGANTINE.get(), 1),
                142857, 0, 0.02f));
        offersArmor.add(new MerchantOffer(
                new ItemStack(AquamiraeItems.ABYSSAL_AMETHYST.get(), 1),
                new ItemStack(Items.DIAMOND_LEGGINGS, 1),
                new ItemStack(AquamiraeItems.ABYSSAL_LEGGINGS.get(), 1),
                142857, 0, 0.02f));
        offersArmor.add(new MerchantOffer(
                new ItemStack(AquamiraeItems.ABYSSAL_AMETHYST.get(), 1),
                new ItemStack(Items.DIAMOND_BOOTS, 1),
                new ItemStack(AquamiraeItems.ABYSSAL_BOOTS.get(), 1),
                142857, 0, 0.02f));
        offersArmor.add(new MerchantOffer(
                new ItemStack(ModItems.KOBOLEDIATOR_SKULL.get(), 1),
                new ItemStack(Items.DIAMOND_HELMET, 1),
                new ItemStack(ModItems.BONE_REPTILE_HELMET.get(), 1),
                142857, 0, 0.02f));
        offersArmor.add(new MerchantOffer(
                new ItemStack(ModItems.KOBOLETON_BONE.get(), 1),
                new ItemStack(Items.DIAMOND_CHESTPLATE, 1),
                new ItemStack(ModItems.BONE_REPTILE_CHESTPLATE.get(), 1),
                142857, 0, 0.02f));
        offersArmor.add(new MerchantOffer(
                new ItemStack(ModItems.IGNITIUM_INGOT.get(), 1),
                new ItemStack(Items.DIAMOND_HELMET, 1),
                new ItemStack(ModItems.IGNITIUM_HELMET.get(), 1),
                142857, 0, 0.02f));
        offersArmor.add(new MerchantOffer(
                new ItemStack(ModItems.IGNITIUM_INGOT.get(), 1),
                new ItemStack(Items.DIAMOND_CHESTPLATE, 1),
                new ItemStack(ModItems.IGNITIUM_CHESTPLATE.get(), 1),
                142857, 0, 0.02f));
        offersArmor.add(new MerchantOffer(
                new ItemStack(ModItems.IGNITIUM_INGOT.get(), 1),
                new ItemStack(Items.DIAMOND_LEGGINGS, 1),
                new ItemStack(ModItems.IGNITIUM_LEGGINGS.get(), 1),
                142857, 0, 0.02f));
        offersArmor.add(new MerchantOffer(
                new ItemStack(ModItems.IGNITIUM_INGOT.get(), 1),
                new ItemStack(Items.DIAMOND_BOOTS, 1),
                new ItemStack(ModItems.IGNITIUM_BOOTS.get(), 1),
                142857, 0, 0.02f));
        offersArmor.add(new MerchantOffer(
                new ItemStack(ModItems.CURSIUM_INGOT.get(), 1),
                new ItemStack(Items.DIAMOND_HELMET, 1),
                new ItemStack(ModItems.CURSIUM_HELMET.get(), 1),
                142857, 0, 0.02f));
        offersArmor.add(new MerchantOffer(
                new ItemStack(ModItems.CURSIUM_INGOT.get(), 1),
                new ItemStack(Items.DIAMOND_CHESTPLATE, 1),
                new ItemStack(ModItems.CURSIUM_CHESTPLATE.get(), 1),
                142857, 0, 0.02f));
        offersArmor.add(new MerchantOffer(
                new ItemStack(ModItems.CURSIUM_INGOT.get(), 1),
                new ItemStack(Items.DIAMOND_LEGGINGS, 1),
                new ItemStack(ModItems.CURSIUM_LEGGINGS.get(), 1),
                142857, 0, 0.02f));
        offersArmor.add(new MerchantOffer(
                new ItemStack(ModItems.CURSIUM_INGOT.get(), 1),
                new ItemStack(Items.DIAMOND_BOOTS, 1),
                new ItemStack(ModItems.CURSIUM_BOOTS.get(), 1),
                142857, 0, 0.02f));
    }

    @Override
    public boolean hurt(DamageSource p_21016_, float p_21017_) {
        return false;
    }

    @Override
    protected @NotNull InteractionResult mobInteract(@NotNull Player player, @NotNull InteractionHand hand) {
        initMerchant();
        if (player instanceof ServerPlayer serverPlayer) {
            CompoundTag tag = new CompoundTag();
            tag.putBoolean("boat", PlayerDataManager.boatGet.get(serverPlayer));
            tag.putBoolean("dim_unlock", PlayerDataManager.stage.getInt(serverPlayer) >= 3);
            this.sendDialogTo(serverPlayer, tag);
        }
        return InteractionResult.sidedSuccess(level().isClientSide);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new LookAtConservingPlayerGoal<>(this));
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(2, new LookAtPlayerGoal(this, Player.class, 8.0F));
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public DialogueScreenBuilder getDialogueBuilder(CompoundTag compoundTag) {
        DialogueScreenBuilder treeBuilder = new DialogueScreenBuilder(this);
        DialogueComponentBuilder dBuilder = new DialogueComponentBuilder(this);

        if(!compoundTag.getBoolean("boat")) {
            DialogNode root = new DialogNode(dBuilder.ans(0), dBuilder.optWithBrackets(0));//开场白 | 返回
            //你是何人
            DialogNode ans1 = new DialogNode(dBuilder.ans(3, ModGameOptions.SAIL_KEY.getTranslatedKeyMessage().copy().withStyle(ChatFormatting.GOLD)), dBuilder.optWithBrackets(1))
                    .addChild(new DialogNode.FinalNode(dBuilder.optWithBrackets(8), 3));

            //武器
            DialogNode ans2 = new DialogNode.FinalNode(dBuilder.optWithBrackets(2), 1);
            //盔甲
            DialogNode ans3 = new DialogNode.FinalNode(dBuilder.optWithBrackets(3), 2);
            //技能
            DialogNode ans4 = new DialogNode(dBuilder.ans(2), dBuilder.optWithBrackets(4))
                    .addChild(new DialogNode.FinalNode(dBuilder.optWithBrackets(5), 3, (s) -> {
                        LocalPlayerPatch localPlayerPatch = ClientEngine.getInstance().getPlayerPatch();
                        if(localPlayerPatch != null) {
                            Minecraft.getInstance().setScreen(new SkillTreeScreen(localPlayerPatch));
                        }
                    }));
            //饰品
            DialogNode ans7 = new DialogNode.FinalNode(dBuilder.optWithBrackets(9), 7);
            root.addChild(ans1).addChild(ans2).addChild(ans3).addChild(ans7).addChild(ans4);
            treeBuilder.setRoot(root);
        } else {
            DialogNode root = new DialogNode(dBuilder.ans(0), dBuilder.optWithBrackets(0));//开场白 | 返回
            //你是何人
            DialogNode ans1 = new DialogNode(dBuilder.ans(1), dBuilder.optWithBrackets(1))
                    .addChild(root);

            //武器
            DialogNode ans2 = new DialogNode.FinalNode(dBuilder.optWithBrackets(2), 1);
            //盔甲
            DialogNode ans3 = new DialogNode.FinalNode(dBuilder.optWithBrackets(3), 2);
            //技能
            DialogNode ans4 = new DialogNode(dBuilder.ans(2), dBuilder.optWithBrackets(4))
                    .addChild(new DialogNode.FinalNode(dBuilder.optWithBrackets(5), -1, (s) -> {
                        LocalPlayerPatch localPlayerPatch = ClientEngine.getInstance().getPlayerPatch();
                        if(localPlayerPatch != null) {
                            Minecraft.getInstance().setScreen(new SkillTreeScreen(localPlayerPatch));
                        }
                    }));
            //饰品
            DialogNode ans7 = new DialogNode.FinalNode(dBuilder.optWithBrackets(9), 7);

            root.addChild(ans1).addChild(ans2).addChild(ans3).addChild(ans7).addChild(ans4);

            if(compoundTag.getBoolean("dim_unlock")) {
                DialogNode ans5 = new DialogNode(dBuilder.ans(4), dBuilder.optWithBrackets(6))
                        .addChild(new DialogNode.FinalNode(dBuilder.optWithBrackets(8), 5))
                        .addChild(root);

                DialogNode ans6 = new DialogNode(dBuilder.ans(4), dBuilder.optWithBrackets(7))
                        .addChild(new DialogNode.FinalNode(dBuilder.optWithBrackets(8), 6))
                        .addChild(root);

                root.addChild(ans5).addChild(ans6);
            }

            treeBuilder.setRoot(root);
        }
        return treeBuilder;
    }

    @Override
    public void handleNpcInteraction(ServerPlayer serverPlayer, int i) {
        if(i == 5) {
            //传送地狱
            ServerLevel level = serverPlayer.server.getLevel(Level.NETHER);
            serverPlayer.changeDimension(level, new SafeNetherTeleporter());
        }
        if(i == 6) {
            //传送末地
            ServerLevel level = serverPlayer.server.getLevel(Level.END);
            serverPlayer.changeDimension(level);
        }
        if(i == 3) {
            if(!PlayerDataManager.boatGet.get(serverPlayer)) {
                ItemUtil.addItemEntity(serverPlayer, ForgeRegistries.ITEMS.getValue(ResourceLocation.parse("smallships:oak_cog")).getDefaultInstance());
                PlayerDataManager.boatGet.put(serverPlayer, true);
            }
        }

        //武器
        if(i == 1) {
            offers.addAll(offersWeapon);
            startTrade(serverPlayer);
        }
        //盔甲
        if(i == 2) {
            offers.addAll(offersArmor);
            startTrade(serverPlayer);
        }

        if(i == 7) {
            offers.addAll(artifacts);
            startTrade(serverPlayer);
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

    protected <E extends GirlEntity> PlayState deployAnimController(final AnimationState<E> state) {
        return state.setAndContinue(IDLE);
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    /**
     * 开始交易
     * 需要改变交易表则去重写 {@link #getOffers()}
     */
    public void startTrade(ServerPlayer serverPlayer){
        setTradingPlayer(serverPlayer);
        openTradingScreen(serverPlayer, Component.empty(), 1);
    }

    @Override
    public void setTradingPlayer(@Nullable Player player) {
        tradingPlayer = player;
    }

    @Nullable
    @Override
    public Player getTradingPlayer() {
        return tradingPlayer;
    }

    @Override
    public @NotNull MerchantOffers getOffers() {
        return offers == null ? new MerchantOffers() : offers;
    }

    @Override
    public void overrideOffers(@NotNull MerchantOffers merchantOffers) {

    }

    @Override
    public void notifyTrade(@NotNull MerchantOffer merchantOffer) {

    }

    @Override
    public void notifyTradeUpdated(@NotNull ItemStack itemStack) {

    }

    @Override
    public int getVillagerXp() {
        return 0;
    }

    @Override
    public void overrideXp(int i) {

    }

    @Override
    public boolean showProgressBar() {
        return false;
    }

    @Override
    public @NotNull SoundEvent getNotifyTradeSound() {
        return SoundEvents.EXPERIENCE_ORB_PICKUP;
    }

    @Override
    public boolean isClientSide() {
        return level().isClientSide;
    }

    @Override
    public boolean removeWhenFarAway(double p_21542_) {
        return false;
    }

    @Override
    public boolean isPushable() {
        return false;
    }
}
