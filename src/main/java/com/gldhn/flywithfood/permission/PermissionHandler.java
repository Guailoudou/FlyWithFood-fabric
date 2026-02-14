package com.gldhn.flywithfood.permission;

import net.minecraft.server.level.ServerPlayer;

public interface PermissionHandler {
    boolean hasPermission(ServerPlayer player, String permission, int defaultOpLevel);
}
