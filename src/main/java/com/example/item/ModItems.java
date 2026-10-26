package com.example.item;

import com.example.ExampleMod;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;

public class ModItems {

    public static Item FIRE_CRYSTAL; 
    public static Item EARTH_CRYSTAL;
    public static Item WATER_CRYSTAL;
    public static Item AIR_CRYSTAL;
    public static Item LIGHT_CRYSTAL;
    public static Item DARK_CRYSTAL;

    private static Item registerItem(String name) {
        return Registry.register(
            BuiltInRegistries.ITEM,
            ExampleMod.id(name),
            new Item(new Item.Properties())
        );
    }

   public static void register() {
        FIRE_CRYSTAL = registerItem("fire_crystal");
        EARTH_CRYSTAL = registerItem("earth_crystal");
        WATER_CRYSTAL = registerItem("water_crystal");
        AIR_CRYSTAL = registerItem("air_crystal");
        LIGHT_CRYSTAL = registerItem("light_crystal");
        DARK_CRYSTAL = registerItem("dark_crystal");
    }
}