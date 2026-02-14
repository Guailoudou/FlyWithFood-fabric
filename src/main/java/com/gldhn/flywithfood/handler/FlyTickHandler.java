package com.gldhn.flywithfood.handler;

import com.gldhn.flywithfood.FlyWithFoodMod;
import com.gldhn.flywithfood.config.FlyConfig;
import com.gldhn.flywithfood.data.PlayerFlyData;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.network.chat.Component;

public class FlyTickHandler {
    private static int tickCounter = 0;

    public static void onServerTick(MinecraftServer server) {
        tickCounter++;
        
        if (tickCounter < 20) return;
        tickCounter = 0;

        for (ServerPlayer player : server.getPlayerList().getPlayers()) {
            if (!PlayerFlyData.isFlyEnabled(player)) continue;
            
            if (!player.getAbilities().flying) continue;

            int foodLevel = player.getFoodData().getFoodLevel();
            
            if (foodLevel <= FlyConfig.minHungerToFly) {
                disableFlight(player);
                continue;
            }

            player.getFoodData().setFoodLevel(
                Math.max(0, foodLevel - (int) FlyConfig.hungerDrainPerSecond)
            );
        }
    }

    private static void disableFlight(ServerPlayer player) {
        PlayerFlyData.setFlyEnabled(player, false);
        player.getAbilities().mayfly = false;
        player.getAbilities().flying = false;
        player.onUpdateAbilities();
        player.sendSystemMessage(Component.literal(FlyConfig.messageHungerLow));
    }
}
