package com.example.mana;

import com.example.attachment.ModAttachments;
import net.minecraft.server.level.ServerPlayer;

import java.util.ArrayList;
import java.util.List;

public class ManaManager {
    private static final double FIRST_MANA_BOOST = 0.5;
    private static final double GROWTH_DECAY = 0.9;
    private static final double MANA_MAX_MULTIPLIER = 1;
    private static final double GROWTH_UP_MULTIPLIER = 1.5;

    public static boolean tryToSpend(ServerPlayer player, double manaToSpend) {
        ManaData playerMana = player.getAttachedOrCreate(ModAttachments.MANA);
        if (manaToSpend > playerMana.currentMana()) {
            return false;
        }
        else {
            double newCurrentMana = playerMana.currentMana() - manaToSpend;
            double newSpentTowardsGrowth = playerMana.spentTowardsGrowth() + manaToSpend;
            int newgrowthCount = playerMana.growthCount();
            double newMaxMana = playerMana.maxMana();
            while (newSpentTowardsGrowth >= getGrowthThreshold(newMaxMana)) {
                newSpentTowardsGrowth = newSpentTowardsGrowth - getGrowthThreshold(newMaxMana);
                newMaxMana = newMaxMana * (MANA_MAX_MULTIPLIER + FIRST_MANA_BOOST * Math.pow(GROWTH_DECAY, newgrowthCount));
                newgrowthCount++;
            }
                ManaData newPlayerMana = new ManaData(newMaxMana, newCurrentMana, newgrowthCount, newSpentTowardsGrowth);

                player.setAttached(ModAttachments.MANA, newPlayerMana);
                return true;
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

}
