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
import com.p1nero.tcrcore.TCRCoreMod;
import com.p1nero.tcrcore.capability.PlayerDataManager;
import com.p1nero.tcrcore.events.PlayerEventListeners;
import com.p1nero.tcrcore.events.SafeNetherTeleporter;
import com.p1nero.tcrcore.events.SafeOverworldTeleporter;
import com.p1nero.tcrcore.item.TCRItems;
import com.p1nero.tcrcore.utils.ItemUtil;
import com.p1nero.tcrcore.utils.WorldUtil;
import com.talhanation.smallships.client.option.ModGameOptions;
import com.yesman.epicskills.client.gui.screen.SkillTreeScreen;
import net.blay09.mods.waystones.block.ModBlocks;
import net.genzyuro.uniqueaccessories.item.UAUniqueCurioItem;
import net.genzyuro.uniqueaccessories.registry.UAItems;
import net.kenddie.fantasyarmor.item.FAItems;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.core.BlockPos;
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
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.Merchant;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.item.trading.MerchantOffers;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.loading.FMLEnvironment;
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
import yesman.epicfight.client.input.EpicFightKeyMappings;
import yesman.epicfight.client.world.capabilites.entitypatch.player.LocalPlayerPatch;
import yesman.epicfight.world.item.EpicFightItems;

import java.util.ArrayList;
import java.util.List;

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
    private final MerchantOffers offersArtifact = new MerchantOffers();
    private final List<Item> rareItems;

    public GirlEntity(EntityType<? extends PathfinderMob> p_21683_, Level p_21684_) {
        super(p_21683_, p_21684_);
        rareItems = List.of(artifacts.registry.ModItems.CRYSTAL_HEART.get(),
                artifacts.registry.ModItems.FERAL_CLAWS.get(),
                artifacts.registry.ModItems.VAMPIRIC_GLOVE.get(),
                artifacts.registry.ModItems.POWER_GLOVE.get(),
                artifacts.registry.ModItems.NOVELTY_DRINKING_HAT.get(),
                artifacts.registry.ModItems.PLASTIC_DRINKING_HAT.get(),
                UAItems.SUN_STONE.get(),
                UAItems.MOON_STONE.get(),
                UAItems.HERO_EMBLEM.get(),
                UAItems.SHINY_STONE.get());
        initMerchant();
    }

    @Override
    public void tick() {
        super.tick();
        if(!level().isClientSide) {
            if(tickCount % 100 == 0) {
                BlockPos myPos = this.getOnPos();
                if(myPos.getX() != WorldUtil.GIRL_POS.getX() || myPos.getZ() != WorldUtil.GIRL_POS.getZ()) {
                    this.setPos(new BlockPos(WorldUtil.GIRL_POS).getCenter());
                }
            }
            if(conversingPlayer != null && (conversingPlayer.isRemoved() || conversingPlayer.isDeadOrDying() || conversingPlayer.distanceTo(this) > 5)) {
                conversingPlayer = null;
            }
        }
    }

    private void initMerchant() {
        offers.clear();
        offersArmor.clear();
        offersWeapon.clear();
        offersArtifact.clear();
        offersArtifact.add(new MerchantOffer(
                new ItemStack(Items.ENDER_EYE, 1),
                new ItemStack(ModBlocks.waystone, 1),
                142857, 0, 0.02f));

        ForgeRegistries.ITEMS.getValues().forEach(item -> {
            if(PlayerEventListeners.illegalItems.contains(item)) {
                return;
            }
            if(item instanceof ArtifactItem || item instanceof UAUniqueCurioItem) {
                if(rareItems.contains(item)) {
                    offersArtifact.add(new MerchantOffer(
                            new ItemStack(TCRItems.RARE_ARTIFACT_TICKET.get(), 1),
                            new ItemStack(item, 1),
                            142857, 0, 0.02f));
                } else {
                    offersArtifact.add(new MerchantOffer(
                            new ItemStack(TCRItems.ARTIFACT_TICKET.get(), 1),
                            new ItemStack(item, 1),
                            142857, 0, 0.02f));
                }
            }
        });
//        offersWeapon.add(new MerchantOffer(
//                new ItemStack(SGItems.GOLEM_HEART.get(), 1),
//                new ItemStack(EpicFightItems.UCHIGATANA.get(), 1),
//                new ItemStack(EFNItem.SWORD_OF_PIONEER.get(), 1),
//                142857, 0, 0.02f));
//        offersWeapon.add(new MerchantOffer(
//                new ItemStack(SGItems.GOLEM_HEART.get(), 1),
//                new ItemStack(EpicFightItems.GOLDEN_DAGGER.get(), 1),
//                new ItemStack(EFNItem.NF_SHORT_SWORD.get(), 1),
//                142857, 0, 0.02f));
//        offersWeapon.add(new MerchantOffer(
//                new ItemStack(ModItems.CORAL_CHUNK.get(), 1),
//                new ItemStack(EFNItem.NF_CLAW.get(), 1),
//                142857, 0, 0.02f));
//        offersWeapon.add(new MerchantOffer(
//                new ItemStack(com.github.dodo.dodosmobs.init.ModItems.CHIERA_CLAW.get(), 1),
//                new ItemStack(EFNItem.FIRE_EXSILIUMGLADIUS.get(), 1),
//                142857, 0, 0.02f));
//        offersWeapon.add(new MerchantOffer(
//                new ItemStack(ModItems.KOBOLEDIATOR_SKULL.get(), 1),
//                new ItemStack(EFNItem.EXSILIUMGLADIUS.get(), 1),
//                142857, 0, 0.02f));
//        offersWeapon.add(new MerchantOffer(
//                new ItemStack(EFNItem.DEEPDARK_HEART.get(), 1),
//                new ItemStack(EFNItem.AETHERIAL_DUSK_DUALSWORD.get(), 1),
//                142857, 0, 0.02f));
//        offersWeapon.add(new MerchantOffer(
//                new ItemStack(Items.BOW, 1),
//                new ItemStack(ModItems.CURSIUM_INGOT.get(), 1),
//                new ItemStack(EFBowItems.MORTIS.get(), 1),
//                142857, 0, 0.02f));
//        offersWeapon.add(new MerchantOffer(
//                new ItemStack(BlockFactorysBossesModItems.DRAGON_BONE.get(), 4),
//                new ItemStack(EFNItem.AIR_TACHI.get(), 1),
//                142857, 0, 0.02f));
//        offersWeapon.add(new MerchantOffer(
//                new ItemStack(BlockFactorysBossesModItems.DRAGON_BONE.get(), 4),
//                new ItemStack(EFNItem.CO_TACHI.get(), 1),
//                142857, 0, 0.02f));
//        offersWeapon.add(new MerchantOffer(
//                new ItemStack(BlockFactorysBossesModItems.KNIGHT_SWORD.get(), 1),
//                new ItemStack(EFNItem.RUINSGREATSWORD.get(), 1),
//                142857, 0, 0.02f));
//        offersWeapon.add(new MerchantOffer(
//                new ItemStack(Items.NETHER_STAR, 1),
//                new ItemStack(EFNItem.MEEN_SPEAR.get(), 1),
//                142857, 0, 0.02f));
//        offersWeapon.add(new MerchantOffer(
//                new ItemStack(ModItems.CURSIUM_BLOCK.get(), 1),
//                new ItemStack(Items.DRAGON_EGG, 1),
//                new ItemStack(EFNItem.YAMATO_DMC_IN_SHEATH.get(), 1),
//                142857, 0, 0.02f));
//        offersWeapon.add(new MerchantOffer(
//                new ItemStack(ModItems.IGNITIUM_BLOCK.get(), 1),
//                new ItemStack(Items.NETHER_STAR, 1),
//                new ItemStack(EFNItem.YAMATO_DMC4_IN_SHEATH.get(), 1),
//                142857, 0, 0.02f));
//
//        offersArmor.add(new MerchantOffer(
//                new ItemStack(Items.NETHERITE_INGOT, 1),
//                new ItemStack(Items.DIAMOND, 4),
//                new ItemStack(Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE, 1),
//                142857, 0, 0.02f));
//        offersArmor.add(new MerchantOffer(
//                new ItemStack(FAItems.MOON_CRYSTAL.get(), 1),
//                new ItemStack(Items.DIAMOND_HELMET, 1),
//                142857, 0, 0.02f));
//        offersArmor.add(new MerchantOffer(
//                new ItemStack(FAItems.MOON_CRYSTAL.get(), 1),
//                new ItemStack(Items.DIAMOND_CHESTPLATE, 1),
//                142857, 0, 0.02f));
//        offersArmor.add(new MerchantOffer(
//                new ItemStack(FAItems.MOON_CRYSTAL.get(), 1),
//                new ItemStack(Items.DIAMOND_LEGGINGS, 1),
//                142857, 0, 0.02f));
//        offersArmor.add(new MerchantOffer(
//                new ItemStack(FAItems.MOON_CRYSTAL.get(), 1),
//                new ItemStack(Items.DIAMOND_BOOTS, 1),
//                142857, 0, 0.02f));
//        offersArmor.add(new MerchantOffer(
//                new ItemStack(ItemRegistry.CRIMSON_SHELL.get(), 1),
//                new ItemStack(Items.DIAMOND_HELMET, 1),
//                new ItemStack(ItemRegistry.CRUST_HEAD.get(), 1),
//                142857, 0, 0.02f));
//        offersArmor.add(new MerchantOffer(
//                new ItemStack(ItemRegistry.CRIMSON_SHELL.get(), 1),
//                new ItemStack(Items.DIAMOND_CHESTPLATE, 1),
//                new ItemStack(ItemRegistry.CRUST_CHEST.get(), 1),
//                142857, 0, 0.02f));
//        offersArmor.add(new MerchantOffer(
//                new ItemStack(ItemRegistry.CRIMSON_SHELL.get(), 1),
//                new ItemStack(Items.DIAMOND_LEGGINGS, 1),
//                new ItemStack(ItemRegistry.CRUST_LEGGINGS.get(), 1),
//                142857, 0, 0.02f));
//        offersArmor.add(new MerchantOffer(
//                new ItemStack(ItemRegistry.BOTTLE_OF_BLOOD.get(), 1),
//                new ItemStack(Items.DIAMOND_HELMET, 1),
//                new ItemStack(ItemRegistry.DIABOLIUM_HEAD.get(), 1),
//                142857, 0, 0.02f));
//        offersArmor.add(new MerchantOffer(
//                new ItemStack(ItemRegistry.BOTTLE_OF_BLOOD.get(), 1),
//                new ItemStack(Items.DIAMOND_CHESTPLATE, 1),
//                new ItemStack(ItemRegistry.DIABOLIUM_CHEST.get(), 1),
//                142857, 0, 0.02f));
//        offersArmor.add(new MerchantOffer(
//                new ItemStack(ItemRegistry.BOTTLE_OF_BLOOD.get(), 1),
//                new ItemStack(Items.DIAMOND_LEGGINGS, 1),
//                new ItemStack(ItemRegistry.DIABOLIUM_LEGGINGS.get(), 1),
//                142857, 0, 0.02f));
//        offersArmor.add(new MerchantOffer(
//                new ItemStack(ItemRegistry.BOTTLE_OF_BLOOD.get(), 1),
//                new ItemStack(Items.DIAMOND_BOOTS, 1),
//                new ItemStack(ItemRegistry.DIABOLIUM_BOOTS.get(), 1),
//                142857, 0, 0.02f));
//        offersArmor.add(new MerchantOffer(
//                new ItemStack(AquamiraeItems.FIN.get(), 1),
//                new ItemStack(Items.DIAMOND_HELMET, 1),
//                new ItemStack(AquamiraeItems.TERRIBLE_HELMET.get(), 1),
//                142857, 0, 0.02f));
//        offersArmor.add(new MerchantOffer(
//                new ItemStack(AquamiraeItems.FIN.get(), 1),
//                new ItemStack(Items.DIAMOND_CHESTPLATE, 1),
//                new ItemStack(AquamiraeItems.TERRIBLE_CHESTPLATE.get(), 1),
//                142857, 0, 0.02f));
//        offersArmor.add(new MerchantOffer(
//                new ItemStack(AquamiraeItems.FIN.get(), 1),
//                new ItemStack(Items.DIAMOND_LEGGINGS, 1),
//                new ItemStack(AquamiraeItems.TERRIBLE_LEGGINGS.get(), 1),
//                142857, 0, 0.02f));
//        offersArmor.add(new MerchantOffer(
//                new ItemStack(AquamiraeItems.FIN.get(), 1),
//                new ItemStack(Items.DIAMOND_BOOTS, 1),
//                new ItemStack(AquamiraeItems.TERRIBLE_BOOTS.get(), 1),
//                142857, 0, 0.02f));
//        offersArmor.add(new MerchantOffer(
//                new ItemStack(AquamiraeItems.ABYSSAL_AMETHYST.get(), 1),
//                new ItemStack(Items.DIAMOND_HELMET, 1),
//                new ItemStack(AquamiraeItems.ABYSSAL_HEAUME.get(), 1),
//                142857, 0, 0.02f));
//        offersArmor.add(new MerchantOffer(
//                new ItemStack(AquamiraeItems.ABYSSAL_AMETHYST.get(), 1),
//                new ItemStack(Items.DIAMOND_CHESTPLATE, 1),
//                new ItemStack(AquamiraeItems.ABYSSAL_BRIGANTINE.get(), 1),
//                142857, 0, 0.02f));
//        offersArmor.add(new MerchantOffer(
//                new ItemStack(AquamiraeItems.ABYSSAL_AMETHYST.get(), 1),
//                new ItemStack(Items.DIAMOND_LEGGINGS, 1),
//                new ItemStack(AquamiraeItems.ABYSSAL_LEGGINGS.get(), 1),
//                142857, 0, 0.02f));
//        offersArmor.add(new MerchantOffer(
//                new ItemStack(AquamiraeItems.ABYSSAL_AMETHYST.get(), 1),
//                new ItemStack(Items.DIAMOND_BOOTS, 1),
//                new ItemStack(AquamiraeItems.ABYSSAL_BOOTS.get(), 1),
//                142857, 0, 0.02f));
//        offersArmor.add(new MerchantOffer(
//                new ItemStack(ModItems.KOBOLEDIATOR_SKULL.get(), 1),
//                new ItemStack(Items.NETHERITE_HELMET, 1),
//                new ItemStack(ModItems.BONE_REPTILE_HELMET.get(), 1),
//                142857, 0, 0.02f));
//        offersArmor.add(new MerchantOffer(
//                new ItemStack(ModItems.KOBOLETON_BONE.get(), 1),
//                new ItemStack(Items.NETHERITE_CHESTPLATE, 1),
//                new ItemStack(ModItems.BONE_REPTILE_CHESTPLATE.get(), 1),
//                142857, 0, 0.02f));
//
//        offersArmor.add(new MerchantOffer(
//                new ItemStack(ModItems.ESSENCE_OF_THE_STORM.get(), 1),
//                new ItemStack(Items.NETHERITE_HELMET, 1),
//                new ItemStack(EFNItem.RUINFIGHTER_HELMET.get(), 1),
//                142857, 0, 0.02f));
//
//        offersArmor.add(new MerchantOffer(
//                new ItemStack(ModItems.ESSENCE_OF_THE_STORM.get(), 1),
//                new ItemStack(Items.NETHERITE_CHESTPLATE, 1),
//                new ItemStack(EFNItem.RUINFIGHTER_CHESTPLATE.get(), 1),
//                142857, 0, 0.02f));
//
//        offersArmor.add(new MerchantOffer(
//                new ItemStack(ModItems.ESSENCE_OF_THE_STORM.get(), 1),
//                new ItemStack(Items.NETHERITE_LEGGINGS, 1),
//                new ItemStack(EFNItem.RUINFIGHTER_LEGGINGS.get(), 1),
//                142857, 0, 0.02f));
//
//        offersArmor.add(new MerchantOffer(
//                new ItemStack(ModItems.ESSENCE_OF_THE_STORM.get(), 1),
//                new ItemStack(Items.NETHERITE_BOOTS, 1),
//                new ItemStack(EFNItem.RUINFIGHTER_BOOTS.get(), 1),
//                142857, 0, 0.02f));
//
//        offersArmor.add(new MerchantOffer(
//                new ItemStack(ModItems.IGNITIUM_INGOT.get(), 1),
//                new ItemStack(Items.NETHERITE_HELMET, 1),
//                new ItemStack(ModItems.IGNITIUM_HELMET.get(), 1),
//                142857, 0, 0.02f));
//        offersArmor.add(new MerchantOffer(
//                new ItemStack(ModItems.IGNITIUM_INGOT.get(), 1),
//                new ItemStack(Items.NETHERITE_CHESTPLATE, 1),
//                new ItemStack(ModItems.IGNITIUM_CHESTPLATE.get(), 1),
//                142857, 0, 0.02f));
//        offersArmor.add(new MerchantOffer(
//                new ItemStack(ModItems.IGNITIUM_INGOT.get(), 1),
//                new ItemStack(Items.NETHERITE_LEGGINGS, 1),
//                new ItemStack(ModItems.IGNITIUM_LEGGINGS.get(), 1),
//                142857, 0, 0.02f));
//        offersArmor.add(new MerchantOffer(
//                new ItemStack(ModItems.IGNITIUM_INGOT.get(), 1),
//                new ItemStack(Items.NETHERITE_BOOTS, 1),
//                new ItemStack(ModItems.IGNITIUM_BOOTS.get(), 1),
//                142857, 0, 0.02f));
//
//        offersArmor.add(new MerchantOffer(
//                new ItemStack(ModItems.MONSTROUS_HORN.get(), 1),
//                new ItemStack(Items.NETHERITE_HELMET, 1),
//                new ItemStack(EFNItem.DUSKFIRE_HELMET.get(), 1),
//                142857, 0, 0.02f));
//
//        offersArmor.add(new MerchantOffer(
//                new ItemStack(ModItems.MONSTROUS_HORN.get(), 1),
//                new ItemStack(Items.NETHERITE_CHESTPLATE, 1),
//                new ItemStack(EFNItem.DUSKFIRE_CHESTPLATE.get(), 1),
//                142857, 0, 0.02f));
//
//        offersArmor.add(new MerchantOffer(
//                new ItemStack(ModItems.MONSTROUS_HORN.get(), 1),
//                new ItemStack(Items.NETHERITE_LEGGINGS, 1),
//                new ItemStack(EFNItem.DUSKFIRE_LEGGINGS.get(), 1),
//                142857, 0, 0.02f));
//
//        offersArmor.add(new MerchantOffer(
//                new ItemStack(ModItems.MONSTROUS_HORN.get(), 1),
//                new ItemStack(Items.NETHERITE_BOOTS, 1),
//                new ItemStack(EFNItem.DUSKFIRE_BOOTS.get(), 1),
//                142857, 0, 0.02f));
//
//        offersArmor.add(new MerchantOffer(
//                new ItemStack(ModItems.CURSIUM_INGOT.get(), 1),
//                new ItemStack(Items.NETHERITE_HELMET, 1),
//                new ItemStack(ModItems.CURSIUM_HELMET.get(), 1),
//                142857, 0, 0.02f));
//        offersArmor.add(new MerchantOffer(
//                new ItemStack(ModItems.CURSIUM_INGOT.get(), 1),
//                new ItemStack(Items.NETHERITE_CHESTPLATE, 1),
//                new ItemStack(ModItems.CURSIUM_CHESTPLATE.get(), 1),
//                142857, 0, 0.02f));
//        offersArmor.add(new MerchantOffer(
//                new ItemStack(ModItems.CURSIUM_INGOT.get(), 1),
//                new ItemStack(Items.NETHERITE_LEGGINGS, 1),
//                new ItemStack(ModItems.CURSIUM_LEGGINGS.get(), 1),
//                142857, 0, 0.02f));
//        offersArmor.add(new MerchantOffer(
//                new ItemStack(ModItems.CURSIUM_INGOT.get(), 1),
//                new ItemStack(Items.NETHERITE_BOOTS, 1),
//                new ItemStack(ModItems.CURSIUM_BOOTS.get(), 1),
//                142857, 0, 0.02f));
    }

    @Override
    public boolean hurt(@NotNull DamageSource damageSource, float p_21017_) {
        if(damageSource.getEntity() instanceof Player player && player.isCreative()) {
            player.displayClientMessage(Component.translatable("/summon " + ForgeRegistries.ENTITY_TYPES.getKey(this.getType())).withStyle(ChatFormatting.RED), false);
            this.discard();
        }
        return false;
    }

    @Override
    protected @NotNull InteractionResult mobInteract(@NotNull Player player, @NotNull InteractionHand hand) {
        initMerchant();
        if (player instanceof ServerPlayer serverPlayer) {
            CompoundTag tag = new CompoundTag();
            tag.putBoolean("boat", PlayerDataManager.boatGet.get(serverPlayer));
            tag.putBoolean("nether_dim_unlock", PlayerDataManager.netherEntered.get(player));
            tag.putBoolean("end_dim_unlock", PlayerDataManager.endEntered.get(player));
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

//            //武器
//            DialogNode ans2 = new DialogNode.FinalNode(dBuilder.optWithBrackets(2), 1);
//            //盔甲
//            DialogNode ans3 = new DialogNode.FinalNode(dBuilder.optWithBrackets(3), 2);
            //技能
            DialogNode ans4 = new DialogNode(dBuilder.ans(5, I18n.get("item.epicskills.ability_stone"), I18n.get("item.epicskills.ability_stone"), EpicFightKeyMappings.SKILL_EDIT.getTranslatedKeyMessage()), dBuilder.optWithBrackets(4))
                    .addChild(new DialogNode.FinalNode(dBuilder.optWithBrackets(5), -1, (s) -> {
                        LocalPlayerPatch localPlayerPatch = ClientEngine.getInstance().getPlayerPatch();
                        if(localPlayerPatch != null) {
                            Minecraft.getInstance().setScreen(new SkillTreeScreen(localPlayerPatch));
                        }
                    }));
            //饰品
            DialogNode ans7 = new DialogNode.FinalNode(dBuilder.optWithBrackets(9), 7);
            root.addChild(ans1)
//                    .addChild(ans2)
//                    .addChild(ans3)
                    .addChild(ans7)
                    .addChild(ans4);
            root.addLeaf(dBuilder.optWithBrackets(10), 8);
            treeBuilder.setRoot(root);
        } else {
            DialogNode root = new DialogNode(dBuilder.ans(0), dBuilder.optWithBrackets(0));//开场白 | 返回
            //你是何人
            DialogNode ans1 = new DialogNode(dBuilder.ans(1), dBuilder.optWithBrackets(1))
                    .addChild(root);

//            //武器
//            DialogNode ans2 = new DialogNode.FinalNode(dBuilder.optWithBrackets(2), 1);
//            //盔甲
//            DialogNode ans3 = new DialogNode.FinalNode(dBuilder.optWithBrackets(3), 2);
            //技能
            DialogNode ans4 = new DialogNode(dBuilder.ans(5, I18n.get("item.epicskills.ability_stone"), I18n.get("item.epicskills.ability_stone"), EpicFightKeyMappings.SKILL_EDIT.getTranslatedKeyMessage()), dBuilder.optWithBrackets(4))
                    .addChild(new DialogNode.FinalNode(dBuilder.optWithBrackets(5), -1, (s) -> {
                        LocalPlayerPatch localPlayerPatch = ClientEngine.getInstance().getPlayerPatch();
                        if(localPlayerPatch != null) {
                            Minecraft.getInstance().setScreen(new SkillTreeScreen(localPlayerPatch));
                        }
                    }));
            //饰品
            DialogNode ans7 = new DialogNode.FinalNode(dBuilder.optWithBrackets(9), 7);

            root.addChild(ans1)
//                    .addChild(ans2)
//                    .addChild(ans3)
                    .addChild(ans7)
                    .addChild(ans4);

            if(compoundTag.getBoolean("nether_dim_unlock")) {
                DialogNode ans5 = new DialogNode(dBuilder.ans(4), dBuilder.optWithBrackets(6))
                        .addChild(new DialogNode.FinalNode(dBuilder.optWithBrackets(8), 5))
                        .addChild(root);
                root.addChild(ans5);
            }

            if(compoundTag.getBoolean("end_dim_unlock")) {
                DialogNode ans6 = new DialogNode(dBuilder.ans(4), dBuilder.optWithBrackets(7))
                        .addChild(new DialogNode.FinalNode(dBuilder.optWithBrackets(8), 6))
                        .addChild(root);
                root.addChild(ans6);
            }

            root.addLeaf(dBuilder.optWithBrackets(10), 8);

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
        if(i == 8) {
            if(PlayerDataManager.wayStoneInteracted.get(serverPlayer)){
                //传送主世界
                ServerLevel level = serverPlayer.server.getLevel(Level.OVERWORLD);
                serverPlayer.changeDimension(level, new SafeOverworldTeleporter());
            } else {
                serverPlayer.displayClientMessage(TCRCoreMod.getInfo("need_to_unlock_waystone").withStyle(ChatFormatting.RED), false);
            }
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
            offers.addAll(offersArtifact);
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
