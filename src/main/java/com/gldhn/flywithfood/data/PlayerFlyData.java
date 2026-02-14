package com.gldhn.flywithfood.data;

import net.minecraft.world.entity.player.Player;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class PlayerFlyData {
    private static final Map<UUID, Boolean> FLY_ENABLED_MAP = new ConcurrentHashMap<>();

    public static boolean isFlyEnabled(Player player) {
        return FLY_ENABLED_MAP.getOrDefault(player.getUUID(), false);
    }

    public static void setFlyEnabled(Player player, boolean enabled) {
        FLY_ENABLED_MAP.put(player.getUUID(), enabled);
    }

    public static void removePlayer(Player player) {
        FLY_ENABLED_MAP.remove(player.getUUID());
    }
}
