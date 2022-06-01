package com.ikalon.icefire.recipes;

import com.ikalon.icefire.IceFire;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModRecipes {
    public static void registerRecipes() {
        Registry.register(Registry.RECIPE_SERIALIZER, new Identifier(IceFire.MOD_ID,OrbRecipe.Serializer.ID), OrbRecipe.Serializer.INSTANCE);
    }
}
