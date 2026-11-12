package com.example.command;

import com.example.mana.ManaData;
import com.example.attachment.ModAttachments;
import com.example.mana.ManaManager;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;


public class ManaCommands {
    public static void register() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            dispatcher.register(
                    Commands.literal("mana")
                            .executes(context -> {
                                ServerPlayer player = context.getSource().getPlayerOrException();
                                ManaData playerMana = player.getAttachedOrCreate(ModAttachments.MANA);
                                context.getSource().sendSuccess(() ->
                                                Component.translatable("command.tbmm.mana_count",
                                                        Math.round(playerMana.currentMana()),
                                                        Math.round(playerMana.maxMana())),
                                        false);
                                return 1;
                            })
                            .then(Commands.literal("info")
                                    .executes(context -> {
                                        ServerPlayer player = context.getSource().getPlayerOrException();
                                        ManaData playerMana = player.getAttachedOrCreate(ModAttachments.MANA);
                                        context.getSource().sendSuccess(() ->
                                                        Component.translatable("command.tbmm.mana_info",
                                                                playerMana.growthCount(),
                                                                Math.round(playerMana.spentTowardsGrowth()),
                                                                Math.round(ManaManager.getGrowthThreshold(playerMana.maxMana()))),
                                                false);
                                        return 1;
                                    }))
                            .then(Commands.literal("spend")
                                    .then(Commands.argument("amount", DoubleArgumentType.doubleArg(0))
                                            .executes(context ->
                                                    {
                                                ServerPlayer player = context.getSource().getPlayerOrException();
                                                double amount = DoubleArgumentType.getDouble(context, "amount");
                                                if (ManaManager.tryToSpend(player, amount)) {
                                                    context.getSource().sendSuccess(() ->
                                                                    Component.translatable("command.tbmm.spend_mana", amount),
                                                            false);
                                                    return 1;
                                                } else {
                                                    context.getSource().sendFailure(Component.translatable("command.tbmm.cannot_spend_mana"));
                                                    return 0;
                                                }

                                                    }
                                            )
                                    ).requires(source -> source.hasPermission(2))
                            )
                            .then(Commands.literal("set")        // /mana set
                                    .then(Commands.argument("current_mana", DoubleArgumentType.doubleArg(0))
                                            .executes(context -> {
                                                double currentMana = DoubleArgumentType.getDouble(context, "current_mana");
                                                ServerPlayer player = context.getSource().getPlayerOrException();
                                                double settedMana = ManaManager.setPlayerMana(player, currentMana);
                                                context.getSource().sendSuccess(() ->
                                                                Component.translatable("command.tbmm.set_current_mana", settedMana),
                                                        false);
                                                return 1;
                                            })
                                    ).requires(source -> source.hasPermission(2))
                            )


            );
        });
    }
}
