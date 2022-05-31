package com.ikalon.icefire.items;

import com.ikalon.icefire.IceFire;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;


public class ModItems {
    public static final Item TEST_ITEM = registerItem("test_item",
            new Item(new FabricItemSettings().group(ModItemGroup.ICEFIRE_ITEMGROUP)));

    public static final Item FROZEN_FISH = registerItem("frozen_fish",
            new Item(new FabricItemSettings().group(ModItemGroup.ICEFIRE_ITEMGROUP)));

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registry.ITEM,
                new Identifier(IceFire.MOD_ID, name),
                item);
    }

    public static void registerModItems() {
        IceFire.LOGGER.info("Registering Mod Items for " + IceFire.MOD_ID);
    }
}
