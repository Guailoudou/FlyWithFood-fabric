package com.gldhn.flywithfood.command;

import com.gldhn.flywithfood.config.FlyConfig;
import com.gldhn.flywithfood.data.PlayerFlyData;
import com.gldhn.flywithfood.permission.PermissionManager;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

public class FlyCommand {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("fly")
            .executes(FlyCommand::toggleFly)
            .then(Commands.literal("on")
                .executes(FlyCommand::enableFly))
            .then(Commands.literal("off")
                .executes(FlyCommand::disableFly))
            .then(Commands.literal("reload")
                .requires(source -> source.getEntity() instanceof ServerPlayer)
                .executes(FlyCommand::reloadConfig)));
    }

    private static boolean checkPermission(ServerPlayer player, String permission, int opLevel) {
        return PermissionManager.hasPermission(player, permission, opLevel);
    }

    private static int enableFly(CommandContext<CommandSourceStack> context) {
        ServerPlayer player = context.getSource().getPlayer();
        if (player == null) return 0;

        if (!checkPermission(player, PermissionManager.PERMISSION_USE, 0)) {
            player.sendSystemMessage(Component.literal("§c[FlyWithFood] 你没有权限使用此命令！"));
            return 0;
        }

        PlayerFlyData.setFlyEnabled(player, true);
        player.getAbilities().mayfly = true;
        player.onUpdateAbilities();
        player.sendSystemMessage(Component.literal(FlyConfig.messageFlyEnabled));
        return 1;
    }

    private static int disableFly(CommandContext<CommandSourceStack> context) {
        ServerPlayer player = context.getSource().getPlayer();
        if (player == null) return 0;

        if (!checkPermission(player, PermissionManager.PERMISSION_USE, 0)) {
            player.sendSystemMessage(Component.literal("§c[FlyWithFood] 你没有权限使用此命令！"));
            return 0;
        }

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

        if (!checkPermission(player, PermissionManager.PERMISSION_USE, 0)) {
            player.sendSystemMessage(Component.literal("§c[FlyWithFood] 你没有权限使用此命令！"));
            return 0;
        }

        if (PlayerFlyData.isFlyEnabled(player)) {
            return disableFly(context);
        } else {
            return enableFly(context);
        }
    }

    private static int reloadConfig(CommandContext<CommandSourceStack> context) {
        ServerPlayer player = context.getSource().getPlayer();
        if (player != null && !checkPermission(player, PermissionManager.PERMISSION_RELOAD, 2)) {
            player.sendSystemMessage(Component.literal("§c[FlyWithFood] 你没有权限执行此命令！"));
            return 0;
        }
        
        FlyConfig.reload();
        context.getSource().sendSuccess(() -> Component.literal(FlyConfig.messageConfigReloaded), true);
        return 1;
    }
}
