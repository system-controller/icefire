package com.ikalon.icefire.recipes;

import com.google.gson.JsonObject;
import com.ikalon.icefire.items.custom.NetherOrb;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;

public class OrbRecipe extends ShapelessRecipe {


    public OrbRecipe(ShapelessRecipe original)
    {
        super(original.getId(),
                original.getGroup(),
                original.getOutput(),
                original.getIngredients());
    }

    @Override
    public boolean fits(int width, int height) {
        return true;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public DefaultedList<ItemStack> getRemainder(CraftingInventory inventory) {
        DefaultedList<ItemStack> defaultedList = DefaultedList.ofSize(inventory.size(), ItemStack.EMPTY);

        for (int i = 0; i < defaultedList.size(); ++i) {
            ItemStack stack = inventory.getStack(i);
            Item item = stack.getItem();
            if (item instanceof NetherOrb) {
                int newDamage = stack.getDamage() + 1;
                if (newDamage < stack.getMaxDamage()) {
                    stack = stack.copy();
                    stack.setDamage(newDamage);
                    defaultedList.set(i, stack);
                }
            } else if (item.hasRecipeRemainder()) {
                defaultedList.set(i, new ItemStack(item.getRecipeRemainder()));
            }
        }

        return defaultedList;
    }
    @Override
    public RecipeType<?> getType() {
        return Type.CRAFTING;
    }

    public static class Type implements RecipeType<OrbRecipe>
    {
        private Type() {}
        public static final Type INSTANCE = new Type();
        public static final String ID = "orb_recipe";
    }

    public static class Serializer extends ShapelessRecipe.Serializer {
        public static final Serializer INSTANCE = new Serializer();
        public static final String ID = "orb_recipe";
        @Override
        public ShapelessRecipe read(Identifier id, JsonObject json) {
            return new OrbRecipe(super.read(id, json));
        }

        @Override
        public ShapelessRecipe read(Identifier id, PacketByteBuf buf) {
            return new OrbRecipe(super.read(id, buf));
        }
    }
}
