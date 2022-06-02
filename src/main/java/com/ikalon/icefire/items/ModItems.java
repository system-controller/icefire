package com.ikalon.icefire.items;

import com.ikalon.icefire.IceFire;
import com.ikalon.icefire.items.custom.FireSwordItem;
import com.ikalon.icefire.items.custom.IceSwordItem;
import com.ikalon.icefire.items.custom.NetherOrb;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;


public class ModItems {

    public static final Item FIRE_SWORD = registerItem("fire_sword",
            new FireSwordItem(ModToolMaterials.FIRE, 3, -2.5f,
                    new FabricItemSettings().group(ModItemGroup.ICEFIRE_ITEMGROUP)));

    public static final Item FROZEN_FISH = registerItem("frozen_fish",
            new Item(new FabricItemSettings()
                    .group(ModItemGroup.ICEFIRE_ITEMGROUP)
                    .food(ModFoodComponents.FROZEN_FISH)));

    public static final Item ICE_CUBE = registerItem("ice_cube",
            new Item(new FabricItemSettings().group(ModItemGroup.ICEFIRE_ITEMGROUP)));

    public static final Item ICE_SHARD = registerItem("ice_shard",
            new Item(new FabricItemSettings().group(ModItemGroup.ICEFIRE_ITEMGROUP)));

    public static final Item ICE_SWORD = registerItem("ice_sword",
            new IceSwordItem(ModToolMaterials.ICE, 3, -2.5f,
                    new FabricItemSettings().group(ModItemGroup.ICEFIRE_ITEMGROUP)));

    public static final Item NETHER_ORB = registerItem("nether_orb",
            new NetherOrb(new FabricItemSettings()
                    .maxDamage(100)
                    .group(ModItemGroup.ICEFIRE_ITEMGROUP)));

    public static final Item TEST_ITEM = registerItem("test_item",
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
