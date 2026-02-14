package com.gldhn.flywithfood.permission;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;

public class DefaultPermissionHandler implements PermissionHandler {

    @Override
    public boolean hasPermission(ServerPlayer player, String permission, int defaultOpLevel) {
        if (defaultOpLevel == 0) {
            return true;
        }
        MinecraftServer server = player.level().getServer();
        if (server == null) {
            return false;
        }
        String[] opNames = server.getPlayerList().getOps().getUserList();
        String playerName = player.getName().getString();
        for (String opName : opNames) {
            if (opName.equalsIgnoreCase(playerName)) {
                return true;
            }
        }
        return false;
    }
}
