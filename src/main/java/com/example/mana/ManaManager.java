package com.example.mana;
import com.example.attachment.ModAttachments;
import com.example.effects.ModEffects;
import net.minecraft.SharedConstants;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;

import java.util.ArrayList;
import java.util.List;

public class ManaManager {
    private static final double[] COST_MULTIPLIER_BY_EXHAUSTION = {1.0, 1.2, 1.5};
    private static final double FIRST_MANA_BOOST = 0.5;
    private static final double GROWTH_DECAY = 0.9;
    private static final double MANA_MAX_MULTIPLIER = 1;
    private static final double GROWTH_UP_MULTIPLIER = 1.5;
    private static final double PERCENT_OF_MAX_MANA_TO_EXHAUSTION_LEVEL_ONE = 0.1;
    private static final double PERCENT_OF_MAX_MANA_TO_EXHAUSTION_LEVEL_TWO = 0.05;
    private static final int EFFECT_LEVEL_MANA_EXHAUSTION_LEVEL_ONE = 0;
    private static final int EFFECT_LEVEL_MANA_EXHAUSTION_LEVEL_TWO = 1;
    private static final int MANA_EXHAUSTION_LEVEL_ONE_DURATION_SECONDS = 5;
    private static final int MANA_EXHAUSTION_LEVEL_TWO_DURATION_SECONDS = 7;

    public record SpendResult(boolean isSpent, double summaryManaToSpend) {}


    public static SpendResult tryToSpend(ServerPlayer player, double manaToSpend) {
        ManaData playerMana = player.getAttachedOrCreate(ModAttachments.MANA);
        double summaryManaToSpend = manaToSpend * getMultiplierByExhaustionEffectLevel(player, COST_MULTIPLIER_BY_EXHAUSTION);
        if (summaryManaToSpend > playerMana.currentMana()) {
            return new  SpendResult(false, summaryManaToSpend);
        }
        else {
            double newCurrentMana = playerMana.currentMana() - summaryManaToSpend;
            double newSpentTowardsGrowth = playerMana.spentTowardsGrowth() + summaryManaToSpend;
            int newgrowthCount = playerMana.growthCount();
            double newMaxMana = playerMana.maxMana();

            while (newSpentTowardsGrowth >= getGrowthThreshold(newMaxMana)) {
                newSpentTowardsGrowth = newSpentTowardsGrowth - getGrowthThreshold(newMaxMana);
                newMaxMana = newMaxMana * (MANA_MAX_MULTIPLIER + FIRST_MANA_BOOST * Math.pow(GROWTH_DECAY, newgrowthCount));
                newgrowthCount++;
            }

            if (newCurrentMana <= PERCENT_OF_MAX_MANA_TO_EXHAUSTION_LEVEL_TWO * newMaxMana) {
                giveManaExhaustionToPlayer(player, MANA_EXHAUSTION_LEVEL_TWO_DURATION_SECONDS, EFFECT_LEVEL_MANA_EXHAUSTION_LEVEL_TWO);
            } else if (newCurrentMana <= PERCENT_OF_MAX_MANA_TO_EXHAUSTION_LEVEL_ONE * newMaxMana) {
                giveManaExhaustionToPlayer(player, MANA_EXHAUSTION_LEVEL_ONE_DURATION_SECONDS, EFFECT_LEVEL_MANA_EXHAUSTION_LEVEL_ONE);
            }

                ManaData newPlayerMana = new ManaData(newMaxMana, newCurrentMana, newgrowthCount, newSpentTowardsGrowth);

                player.setAttached(ModAttachments.MANA, newPlayerMana);
                return new SpendResult(true, summaryManaToSpend);
        }
    }

    public static double setPlayerMana(ServerPlayer player, double amount) {
        ManaData playerMana = player.getAttachedOrCreate(ModAttachments.MANA);
        double currentMana = Math.min(playerMana.maxMana(), amount);
        ManaData newPlayerMana = new ManaData(playerMana.maxMana(), currentMana, playerMana.growthCount(), playerMana.spentTowardsGrowth());
        player.setAttached(ModAttachments.MANA, newPlayerMana);
        return currentMana;
    }

    public static double getGrowthThreshold(double maxMana) {
        return maxMana * GROWTH_UP_MULTIPLIER ;
    }

    private static void giveManaExhaustionToPlayer(ServerPlayer player, int durationSeconds, int level){
        player.addEffect(new MobEffectInstance(ModEffects.MANA_EXHAUSTION, durationSeconds * SharedConstants.TICKS_PER_SECOND, level));
    }

    public static int getExhaustionEffectLevel(ServerPlayer player) {
        MobEffectInstance exhaustionEffect = player.getEffect(ModEffects.MANA_EXHAUSTION);
        if (exhaustionEffect != null) {
            return exhaustionEffect.getAmplifier()+1;
        }
        return 0;
    }

    public static double getMultiplierByExhaustionEffectLevel(ServerPlayer player, double[] multipliers) {
        int level = Math.min(getExhaustionEffectLevel(player), multipliers.length - 1);
        return multipliers[level];

    }

}
