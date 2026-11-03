package com.example.mana;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public record ManaData(double maxMana, double currentMana, int growthCount, double spentTowardsGrowth) {
    public static final Codec<ManaData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.DOUBLE.fieldOf("max_mana").forGetter(ManaData::maxMana),
            Codec.DOUBLE.fieldOf("current_mana").forGetter(ManaData::currentMana),
            Codec.INT.fieldOf("growth_count").forGetter(ManaData::growthCount),
            Codec.DOUBLE.fieldOf("spent_towards_growth").forGetter(ManaData::spentTowardsGrowth)
    ).apply(instance, ManaData::new));
}

