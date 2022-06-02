package com.ikalon.icefire;

import com.ikalon.icefire.blocks.ModBlocks;
import com.ikalon.icefire.items.ModItemGroup;
import com.ikalon.icefire.items.ModItems;
import com.ikalon.icefire.recipes.ModRecipes;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.BiomeModification;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.loot.v1.FabricLootPoolBuilder;
import net.fabricmc.fabric.api.loot.v1.event.LootTableLoadingCallback;
import net.minecraft.item.Items;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.placementmodifier.CountPlacementModifier;
import net.minecraft.world.gen.placementmodifier.HeightRangePlacementModifier;
import net.minecraft.world.gen.placementmodifier.SquarePlacementModifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

public class IceFire implements ModInitializer {
	public static final String MOD_ID = "icefire";

	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	private static ConfiguredFeature<?, ?> OVERWORLD_ICE_CONFIGURED_FEATURE = new
			ConfiguredFeature<>(Feature.ORE, new OreFeatureConfig(
			OreConfiguredFeatures.STONE_ORE_REPLACEABLES, ModBlocks.CRACKED_ICE.getDefaultState(),
			9
	));

	public static PlacedFeature OVERWORLD_ICE_PLACED_FEATURE = new PlacedFeature(
			RegistryEntry.of(OVERWORLD_ICE_CONFIGURED_FEATURE),
			Arrays.asList(
					CountPlacementModifier.of(20),
					SquarePlacementModifier.of(),
					HeightRangePlacementModifier.uniform(YOffset.getBottom(),YOffset.fixed(64))
			));

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.
		LOGGER.info("Begin loading " + MOD_ID);
		Registry.register(BuiltinRegistries.CONFIGURED_FEATURE,
				new Identifier("icefire","cracked_ice"),
				OVERWORLD_ICE_CONFIGURED_FEATURE);
		Registry.register(BuiltinRegistries.PLACED_FEATURE,
				new Identifier("icefire", "overworld_cracked_ice"),
				OVERWORLD_ICE_PLACED_FEATURE);
		BiomeModifications.addFeature(BiomeSelectors.foundInOverworld(),
				GenerationStep.Feature.UNDERGROUND_ORES,
				RegistryKey.of(Registry.PLACED_FEATURE_KEY, new Identifier("icefire", "overworld_cracked_ice")));

		ModItems.registerModItems();
		ModItemGroup.registerModItemGroup();
		ModBlocks.registerModBlocks();
		ModRecipes.registerRecipes();
		LOGGER.info("Finished loading " + MOD_ID);
	}
}
