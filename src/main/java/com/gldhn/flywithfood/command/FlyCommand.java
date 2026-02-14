package com.gldhn.flywithfood.command;

import com.gldhn.flywithfood.config.FlyConfig;
import com.gldhn.flywithfood.data.PlayerFlyData;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

public class FlyCommand {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("fly")
            .requires(source -> source.getEntity() instanceof ServerPlayer)
            .then(Commands.literal("on")
                .executes(FlyCommand::enableFly))
            .then(Commands.literal("off")
                .executes(FlyCommand::disableFly))
            .then(Commands.literal("reload")
                .executes(FlyCommand::reloadConfig))
            .executes(FlyCommand::toggleFly));
    }

    private static int enableFly(CommandContext<CommandSourceStack> context) {
        ServerPlayer player = context.getSource().getPlayer();
        if (player == null) return 0;

        PlayerFlyData.setFlyEnabled(player, true);
        player.getAbilities().mayfly = true;
        player.onUpdateAbilities();
        player.sendSystemMessage(Component.literal(FlyConfig.messageFlyEnabled));
        return 1;
    }

    private static int disableFly(CommandContext<CommandSourceStack> context) {
        ServerPlayer player = context.getSource().getPlayer();
        if (player == null) return 0;

        PlayerFlyData.setFlyEnabled(player, false);
        player.getAbilities().mayfly = false;
        player.getAbilities().flying = false;
        player.onUpdateAbilities();
        player.sendSystemMessage(Component.literal(FlyConfig.messageFlyDisabled));
        return 1;
    }

    private static int toggleFly(CommandContext<CommandSourceStack> context) {
        ServerPlayer player = context.getSource().getPlayer();
        if (player == null) return 0;

        if (PlayerFlyData.isFlyEnabled(player)) {
            return disableFly(context);
        } else {
            return enableFly(context);
        }
    }

    private static int reloadConfig(CommandContext<CommandSourceStack> context) {
        FlyConfig.reload();
        context.getSource().sendSuccess(() -> Component.literal(FlyConfig.messageConfigReloaded), true);
        return 1;
    }
}
