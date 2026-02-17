package com.gldhn.flywithfood.handler;

import com.gldhn.flywithfood.config.FlyConfig;
import com.gldhn.flywithfood.data.PlayerFlyData;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;

public class FlyTickHandler {
    private static int tickCounter = 0;
    // private static final Set<UUID> warnedPlayers = new HashSet<>();

    public static void onServerTick(MinecraftServer server) {
        tickCounter++;
        
        if (tickCounter < FlyConfig.hungerDrainIntervalTicks) return;
        tickCounter = 0;

        for (ServerPlayer player : server.getPlayerList().getPlayers()) {
            PlayerFlyData.checkIsFly(player);
            if (!PlayerFlyData.isFlyEnabled(player)) continue;
            checkSaturationEffect(player);
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

    private static void checkSaturationEffect(ServerPlayer player) {
        MobEffectInstance saturationEffect = player.getEffect(MobEffects.SATURATION);
        
        if (saturationEffect != null) {
            int durationTicks = saturationEffect.getDuration();
            int durationSeconds = durationTicks / 20;
            int warningSeconds = FlyConfig.saturationWarningSeconds;
            
            if (durationSeconds >= 0 && durationSeconds <= warningSeconds) {
                String message = FlyConfig.messageSaturationWarning.replace("%time%", String.valueOf(durationSeconds));
                player.displayClientMessage(Component.literal(message), true);
            }
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
