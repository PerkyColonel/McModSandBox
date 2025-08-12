package com.example.examplemod;

import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;

public class GiganticBread extends Item {
    
    public GiganticBread() {
        super(new Properties().food(new FoodProperties.Builder()
            .nutrition(15)
            .saturationModifier(1.2f)
            .build()));
    }
}