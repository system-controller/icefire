package com.ikalon.icefire.registry;

import com.ikalon.icefire.IceFire;
import com.ikalon.icefire.recipes.OrbRecipe;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModRecipes {
    public static void registerRecipes() {
        Registry.register(Registry.RECIPE_SERIALIZER, new Identifier(IceFire.MOD_ID, OrbRecipe.Serializer.ID), OrbRecipe.Serializer.INSTANCE);
    }
}
