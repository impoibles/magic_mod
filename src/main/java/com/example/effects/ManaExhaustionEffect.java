package com.example.effects;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public class ManaExhaustionEffect extends MobEffect {

    private static final float PLAYER_EXHAUSTION = 0.06F;

    public ManaExhaustionEffect() {
        super(MobEffectCategory.HARMFUL, 9154528);
    }

    @Override
    public boolean applyEffectTick(LivingEntity livingEntity, int amplifier) {
        if (livingEntity instanceof Player player) {
            player.causeFoodExhaustion(PLAYER_EXHAUSTION * (amplifier + 1));
        }

        return true;
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return true;
    }
}
