package com.example.mana;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

public record ManaData(double maxMana, double currentMana, int growthCount, double spentTowardsGrowth) {
    public static final Codec<ManaData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.DOUBLE.fieldOf("max_mana").forGetter(ManaData::maxMana),
            Codec.DOUBLE.fieldOf("current_mana").forGetter(ManaData::currentMana),
            Codec.INT.fieldOf("growth_count").forGetter(ManaData::growthCount),
            Codec.DOUBLE.fieldOf("spent_towards_growth").forGetter(ManaData::spentTowardsGrowth)
    ).apply(instance, ManaData::new));

    public static final StreamCodec<ByteBuf, ManaData> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.DOUBLE, ManaData::maxMana,
            ByteBufCodecs.DOUBLE, ManaData::currentMana,
            ByteBufCodecs.INT, ManaData::growthCount,
            ByteBufCodecs.DOUBLE, ManaData::spentTowardsGrowth,
            ManaData::new
    );
}

