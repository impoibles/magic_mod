package com.example.mana;

import com.example.attachment.ModAttachments;
import com.example.effects.ModEffects;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.SharedConstants;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;


public class ManaRegen {
    private static final double[] REGEN_MULTIPLIER_BY_EXHAUSTION = {1.0, 0.5, 0};
    private static final double SPEED_COEFFICIENT = 0.1667;

    public static void register() {
        ServerTickEvents.END_SERVER_TICK.register((server) -> {
            server.getPlayerList().getPlayers().forEach(player -> {
                ManaData playerMana = player.getAttachedOrCreate(ModAttachments.MANA);
                if (playerMana.currentMana() < playerMana.maxMana()) {
                    double manaRegen = (ManaManager.getMultiplierByExhaustionEffectLevel(player, REGEN_MULTIPLIER_BY_EXHAUSTION)
                            * SPEED_COEFFICIENT * Math.sqrt(playerMana.maxMana()))/SharedConstants.TICKS_PER_SECOND;
                    double newPlayerCurrentMana = Math.min(playerMana.maxMana(), playerMana.currentMana() + manaRegen);
                    ManaData newPlayerMana = new ManaData(playerMana.maxMana(), newPlayerCurrentMana, playerMana.growthCount(), playerMana.spentTowardsGrowth());
                    player.setAttached(ModAttachments.MANA, newPlayerMana);
                }
            });
        });
    }
}
