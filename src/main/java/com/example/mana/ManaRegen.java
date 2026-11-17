package com.example.mana;

import com.example.attachment.ModAttachments;
import com.example.effects.ModEffects;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.SharedConstants;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;

import static com.example.mana.ManaManager.getPlayerMaxMana;


public class ManaRegen {
    private static final double[] REGEN_MULTIPLIER_BY_EXHAUSTION = {1.0, 0.5, 0};
    private static final double SPEED_COEFFICIENT = 0.1667;

    public static void register() {
        ServerTickEvents.END_SERVER_TICK.register((server) -> {
            server.getPlayerList().getPlayers().forEach(player -> {
                ManaData playerMana = player.getAttachedOrCreate(ModAttachments.MANA);
                double playerMaxMana = getPlayerMaxMana(player);
                if (playerMana.currentMana() < playerMaxMana) {
                    double manaRegen = (ManaManager.getMultiplierByExhaustionEffectLevel(player, REGEN_MULTIPLIER_BY_EXHAUSTION)
                            * SPEED_COEFFICIENT * Math.sqrt(playerMaxMana))/SharedConstants.TICKS_PER_SECOND;
                    double newPlayerCurrentMana = Math.min(playerMaxMana, playerMana.currentMana() + manaRegen);
                    ManaData newPlayerMana = new ManaData(playerMana.maxMana(), newPlayerCurrentMana, playerMana.growthCount(), playerMana.spentTowardsGrowth());
                    player.setAttached(ModAttachments.MANA, newPlayerMana);
                }
                else if (playerMana.currentMana() > playerMaxMana) {
                    ManaData newPlayerMana = new ManaData(playerMana.maxMana(), playerMaxMana, playerMana.growthCount(), playerMana.spentTowardsGrowth());
                    player.setAttached(ModAttachments.MANA, newPlayerMana);
                }
            });
        });
    }
}
