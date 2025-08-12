package com.example.examplemod;

import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;
import net.minecraft.tags.TagKey;
import net.minecraft.tags.BlockTags;

public class PowerSword extends SwordItem {
    
    public static final Tier POWER_TIER = new Tier() {
        @Override
        public int getUses() {
            return 2000; // Durability
        }

        @Override
        public float getSpeed() {
            return 12.0F; // Mining speed
        }

        @Override
        public float getAttackDamageBonus() {
            return 0F; // Base damage (1 + 29 = 30 total damage)
        }

        @Override
        public TagKey<Block> getIncorrectBlocksForDrops() {
            return BlockTags.INCORRECT_FOR_NETHERITE_TOOL;
        }

        @Override
        public int getEnchantmentValue() {
            return 15; // Enchantability
        }

        @Override
        public Ingredient getRepairIngredient() {
            return Ingredient.of(net.minecraft.world.item.Items.NETHERITE_INGOT);
        }
    };

    public PowerSword() {
        super(POWER_TIER, new Properties()
            .attributes(SwordItem.createAttributes(POWER_TIER, 29, 9.0F)) // 30 damage, 10 attack speed (9.0F + 1.0F base = 10.0F total)
        );
    }
}