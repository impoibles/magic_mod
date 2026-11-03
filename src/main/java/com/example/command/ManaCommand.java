package com.example.command;

import com.example.mana.ManaData;
import com.example.attachment.ModAttachments;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

public class ManaCommand {
    public static void register() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            dispatcher.register(
                    Commands.literal("mana")
                            .executes(context -> {
                                ServerPlayer player = context.getSource().getPlayerOrException();
                                ManaData mana = player.getAttachedOrCreate(ModAttachments.MANA);
                                context.getSource().sendSuccess(() ->
                                                Component.translatable("command.modid.mana_count",
                                                        Math.round(mana.currentMana()),
                                                        Math.round(mana.maxMana())),
                                        false);
                                return 1;
                            })
                            .then(Commands.literal("set")        // /mana set
                                    .then(Commands.argument("current_mana", DoubleArgumentType.doubleArg(0))
                                            .executes(context -> {
                                                ServerPlayer player = context.getSource().getPlayerOrException();
                                                ManaData playerMana = player.getAttachedOrCreate(ModAttachments.MANA);
                                                double currentMana = Math.min(playerMana.maxMana(), DoubleArgumentType.getDouble(context, "current_mana"));
                                                ManaData newPlayerMana = new ManaData(playerMana.maxMana(), currentMana, playerMana.growthCount(), playerMana.spentTowardsGrowth());
                                                player.setAttached(ModAttachments.MANA, newPlayerMana);
                                                context.getSource().sendSuccess(() ->
                                                                Component.translatable("command.modid.set_current_mana", currentMana),
                                                        false);
                                                return 1;
                                            })
                                    ).requires(source -> source.hasPermission(2))
                            )

            );
        });
    }
}
