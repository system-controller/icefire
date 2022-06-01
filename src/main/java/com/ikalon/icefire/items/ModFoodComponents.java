package com.ikalon.icefire.items;

import net.minecraft.item.FoodComponent;

public class ModFoodComponents {
    public static final FoodComponent FROZEN_FISH =
            new FoodComponent.Builder()
                    .hunger(6)
                    .saturationModifier(0.6f)
                    .build();
}
