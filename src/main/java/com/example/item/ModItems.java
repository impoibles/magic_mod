package com.example.item;

import com.example.ExampleMod;
import com.example.item.crystal.AirCrystalItem;
import com.example.item.crystal.DarkCrystalItem;
import com.example.item.crystal.EarthCrystalItem;
import com.example.item.crystal.FireCrystalItem;
import com.example.item.crystal.LightCrystalItem;
import com.example.item.crystal.WaterCrystalItem;
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

    private static Item registerItem(String name, Item item) {
        return Registry.register(
            BuiltInRegistries.ITEM,
            ExampleMod.id(name),
            item
        );
    }

   public static void register() {
        FIRE_CRYSTAL = registerItem("fire_crystal", new FireCrystalItem(new Item.Properties()));
        EARTH_CRYSTAL = registerItem("earth_crystal", new EarthCrystalItem(new Item.Properties()));
        WATER_CRYSTAL = registerItem("water_crystal", new WaterCrystalItem(new Item.Properties()));
        AIR_CRYSTAL = registerItem("air_crystal", new AirCrystalItem(new Item.Properties()));
        LIGHT_CRYSTAL = registerItem("light_crystal", new LightCrystalItem(new Item.Properties()));
        DARK_CRYSTAL = registerItem("dark_crystal", new DarkCrystalItem(new Item.Properties()));
    }
}
