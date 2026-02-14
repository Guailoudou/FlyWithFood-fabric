package com.gldhn.flywithfood.permission;

import com.gldhn.flywithfood.FlyWithFoodMod;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.server.level.ServerPlayer;

public class PermissionManager {

    public static final String PERMISSION_USE = "flywithfood.use";
    public static final String PERMISSION_RELOAD = "flywithfood.reload";

    private static boolean luckPermsLoaded = false;
    private static PermissionHandler permissionHandler;

    public static void init() {
        luckPermsLoaded = FabricLoader.getInstance().isModLoaded("luckperms");
        
        if (luckPermsLoaded) {
            try {
                Class<?> handlerClass = Class.forName("com.gldhn.flywithfood.permission.LuckPermsHandler");
                permissionHandler = (PermissionHandler) handlerClass.getDeclaredConstructor().newInstance();
                FlyWithFoodMod.LOGGER.info("LuckPerms detected, using LuckPerms for permission management");
            } catch (Exception e) {
                FlyWithFoodMod.LOGGER.warn("Failed to initialize LuckPerms handler, falling back to OP check", e);
                permissionHandler = new DefaultPermissionHandler();
            }
        } else {
            permissionHandler = new DefaultPermissionHandler();
            FlyWithFoodMod.LOGGER.info("Using default OP-based permission management");
        }
    }

    public static boolean hasPermission(ServerPlayer player, String permission, int defaultOpLevel) {
        return permissionHandler.hasPermission(player, permission, defaultOpLevel);
    }

    public static boolean isLuckPermsLoaded() {
        return luckPermsLoaded;
    }
}
