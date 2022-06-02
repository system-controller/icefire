package com.ikalon.icefire.blocks;

import com.ikalon.icefire.IceFire;
import com.ikalon.icefire.items.ModItemGroup;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModBlocks {

    public static final Block CRACKED_ICE = registerBlock("cracked_ice", new Block(FabricBlockSettings.of(Material.ICE)), ModItemGroup.ICEFIRE_ITEMGROUP);

    public static final Block DEEP_FREEZE_ICE = registerBlock("deep_freeze_ice", new Block(FabricBlockSettings.of(Material.ICE)), ModItemGroup.ICEFIRE_ITEMGROUP);

    public static final Block FAST_ICE = registerBlock("fast_ice", new Block(FabricBlockSettings.of(Material.ICE).slipperiness(1.02f)), ModItemGroup.ICEFIRE_ITEMGROUP);

    public static final Block SLOW_ICE = registerBlock("slow_ice", new Block(FabricBlockSettings.of(Material.ICE).velocityMultiplier(0.8f).slipperiness(0.98f)), ModItemGroup.ICEFIRE_ITEMGROUP);


    private static Block registerBlock(String name, Block block, ItemGroup group)
    {
        registerBlockItem(name, block, group);
        return Registry.register(Registry.BLOCK, new Identifier(IceFire.MOD_ID, name), block);
    }
    private static Item registerBlockItem(String name, Block block, ItemGroup group)
    {
        return Registry.register(Registry.ITEM, new Identifier(IceFire.MOD_ID, name), new BlockItem(block, new FabricItemSettings().group(group)));
    }
    public static void registerModBlocks(){
        IceFire.LOGGER.info("registering mod blocks");
    }
}
