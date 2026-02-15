package com.gldhn.flywithfood.permission;

import com.gldhn.flywithfood.FlyWithFoodMod;
import me.lucko.fabric.api.permissions.v0.Permissions;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.server.level.ServerPlayer;

public class PermissionManager {

    public static final String PERMISSION_USE = "flywithfood.use";
    public static final String PERMISSION_RELOAD = "flywithfood.reload";

    public static void init() {
        boolean luckPermsLoaded = FabricLoader.getInstance().isModLoaded("luckperms");
        if (luckPermsLoaded) {
            FlyWithFoodMod.LOGGER.info("LuckPerms detected, using Fabric Permissions API with LuckPerms backend");
        } else {
            FlyWithFoodMod.LOGGER.info("Using Fabric Permissions API with default OP-based permission management");
        }
    }

    public static boolean hasPermission(ServerPlayer player, String permission, int defaultOpLevel) {
        return Permissions.check(player, permission, defaultOpLevel == 0);
    }
}
