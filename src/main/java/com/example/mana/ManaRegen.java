package com.example.mana;

import com.example.attachment.ModAttachments;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;

public class ManaRegen {

    private static final double SPEED_COEFFICIENT = 0.1667;
    private static final int TICKS_PER_SECOND = 20;

    public static void register() {
        ServerTickEvents.END_SERVER_TICK.register((server) -> {
            server.getPlayerList().getPlayers().forEach(player -> {
                ManaData playerMana = player.getAttachedOrCreate(ModAttachments.MANA);
                if (playerMana.currentMana() < playerMana.maxMana()) {
                    double manaRegen = (SPEED_COEFFICIENT * Math.sqrt(playerMana.maxMana()))/20;
                    double newPlayerCurrentMana = Math.min(playerMana.maxMana(), playerMana.currentMana() + manaRegen);
                    ManaData newPlayerMana = new ManaData(playerMana.maxMana(), newPlayerCurrentMana, playerMana.growthCount(), playerMana.spentTowardsGrowth());
                    player.setAttached(ModAttachments.MANA, newPlayerMana);
                }
            });
        });
    }
}
