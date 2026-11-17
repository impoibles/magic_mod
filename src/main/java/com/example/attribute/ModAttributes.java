package com.example.attribute;

import com.example.ExampleMod;
import com.example.mana.ManaManager;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;

public class ModAttributes{
    public static final Holder<Attribute> MAX_MANA_ATTRIBUTE = registerAttribute("max_mana_attribute",
            new RangedAttribute("attribute.tbmm.max_mana_attribute", 100, 1, 50000).setSyncable(true)
    );;
    public static final Holder<Attribute> MANA_REGEN_SPEED_ATTRIBUTE = registerAttribute("regen_speed_mana_attribute",
            new RangedAttribute("attribute.tbmm.regen_speed_mana_attribute", 1, 0, 5).setSyncable(true)
    );;

    private static Holder<Attribute> registerAttribute(String name, Attribute  attribute){
       return  Registry.registerForHolder(
                BuiltInRegistries.ATTRIBUTE, ExampleMod.id(name), attribute
        );
    }

    public static void register(){
    }

}
