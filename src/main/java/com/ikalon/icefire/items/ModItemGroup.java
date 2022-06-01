package com.ikalon.icefire.items;

import com.ikalon.icefire.IceFire;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;

public class ModItemGroup {
    public static final ItemGroup ICEFIRE_ITEMGROUP = FabricItemGroupBuilder
            .build(new Identifier(IceFire.MOD_ID, "icefire_itemgroup"),
                    () -> new ItemStack(Items.REDSTONE_BLOCK));

    public static void registerModItemGroup() {
        IceFire.LOGGER.info("Registering Mod ItemGroup for " + IceFire.MOD_ID);
    }
}
