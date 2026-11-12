package com.example.effects;

import com.example.ExampleMod;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class ModEffects {

    public static Holder<MobEffect> MANA_EXHAUSTION;

    private static Holder.Reference<MobEffect> registerEffect(String name, MobEffect effect) {
        return Registry.registerForHolder(BuiltInRegistries.MOB_EFFECT, ExampleMod.id(name), effect);
    }

    public static void register() {
        MANA_EXHAUSTION = registerEffect(
                "mana_exhaustion",
                (new ManaExhaustionEffect())
                        .addAttributeModifier(Attributes.MOVEMENT_SPEED,
                            ExampleMod.id("mana_exhaustion_speed"),
                            -0.15,
                            AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)
                        .addAttributeModifier(
                                Attributes.ATTACK_DAMAGE,
                                ExampleMod.id("mana_exhaustion_damage"),
                                -4.0,
                                AttributeModifier.Operation.ADD_VALUE
                        )

        );
    }
}
