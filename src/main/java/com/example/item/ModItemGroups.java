package com.example.item;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import com.example.ExampleMod;
import com.example.item.ModItems;


public class ModItemGroups {
    public static void register() {
        Registry.register(
                BuiltInRegistries.CREATIVE_MODE_TAB,
                ExampleMod.id("magic"),
                FabricItemGroup.builder()
                        .title(Component.translatable("itemGroup.modid.magic"))
                        .icon(() -> new ItemStack(ModItems.FIRE_CRYSTAL))
                        .displayItems((params, output) -> {
                            output.accept(ModItems.FIRE_CRYSTAL);
                            output.accept(ModItems.EARTH_CRYSTAL);
                            output.accept(ModItems.WATER_CRYSTAL);
                            output.accept(ModItems.AIR_CRYSTAL);
                            output.accept(ModItems.LIGHT_CRYSTAL);
                            output.accept(ModItems.DARK_CRYSTAL);
                        })
                        .build()
        );
    }
}