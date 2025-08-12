package com.example.examplemod;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.concurrent.CompletableFuture;

@Mod.EventBusSubscriber(modid = ExampleMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ExampleModDataGenerator {
    
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
        
        generator.addProvider(event.includeServer(), new ModRecipeProvider(packOutput, lookupProvider));
    }
    
    public static class ModRecipeProvider extends RecipeProvider {
        
        public ModRecipeProvider(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> pRegistries) {
            super(pOutput, pRegistries);
        }
        
        @Override
        protected void buildRecipes(RecipeOutput recipeOutput) {
            // Gigantic Bread recipe - 3x3 pattern with wheat and bread
            ShapedRecipeBuilder.shaped(RecipeCategory.FOOD, ExampleMod.GIGANTIC_BREAD.get(), 1)
                .pattern("WWW")
                .pattern("BBB") 
                .pattern("WWW")
                .define('W', Items.WHEAT)
                .define('B', Items.BREAD)
                .unlockedBy("has_bread", has(Items.BREAD))
                .unlockedBy("has_wheat", has(Items.WHEAT))
                .save(recipeOutput);
                
            // Simple test recipe - bread to diamond (for testing)
            ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Items.DIAMOND, 1)
                .pattern(" B ")
                .pattern("   ")
                .pattern("   ")
                .define('B', Items.BREAD)
                .unlockedBy("has_bread", has(Items.BREAD))
                .save(recipeOutput, "examplemod:test_diamond_recipe");
        }
    }
}