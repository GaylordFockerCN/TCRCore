package com.p1nero.tcrcore.datagen;

import com.p1nero.tcrcore.TCRCoreMod;
import com.p1nero.tcrcore.capability.TCRTaskManager;
import com.p1nero.tcrcore.datagen.lang.TCRENLangGenerator;
import com.p1nero.tcrcore.datagen.lang.TCRZHLangGenerator;
import com.p1nero.tcrcore.datagen.loot.TCRLootTableProvider;
import com.p1nero.tcrcore.datagen.sound.TCRSoundGenerator;
import com.p1nero.tcrcore.datagen.tags.TCRBlockTagGenerator;
import com.p1nero.tcrcore.datagen.tags.TCREntityTagGenerator;
import com.p1nero.tcrcore.datagen.tags.TCRItemTagGenerator;
import com.p1nero.tcrcore.datagen.tags.TCRPoiTypeTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.concurrent.CompletableFuture;

@Mod.EventBusSubscriber(modid = TCRCoreMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class TCRDataGenerators {
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void gatherData(GatherDataEvent event) {
        TCRTaskManager.init();

        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        ExistingFileHelper helper = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        //Client
        generator.addProvider(event.includeClient(), new TCRBlockStateProvider(output, helper));
        generator.addProvider(event.includeClient(), new TCRItemModelProvider(output, helper));
        generator.addProvider(event.includeClient(), new TCRENLangGenerator(output));
        generator.addProvider(event.includeClient(), new TCRZHLangGenerator(output));
        generator.addProvider(event.includeClient(), new TCRSoundGenerator(output, helper));

        //Server
        generator.addProvider(event.includeServer(), new TCRRecipeGenerator(output));
        generator.addProvider(event.includeServer(), TCRLootTableProvider.create(output));
        generator.addProvider(event.includeServer(), new TCRAdvancementData(output, lookupProvider, helper));
        generator.addProvider(event.includeServer(), new TCREntityTagGenerator(output, lookupProvider, helper));

        TCRBlockTagGenerator blockTagGenerator = generator.addProvider(event.includeServer(),
                new TCRBlockTagGenerator(output, lookupProvider, helper));
        generator.addProvider(event.includeServer(), new TCRItemTagGenerator(output, lookupProvider, blockTagGenerator.contentsGetter(), helper));
        generator.addProvider(event.includeServer(), new TCRPoiTypeTagsProvider(output, lookupProvider, helper));

    }
}
