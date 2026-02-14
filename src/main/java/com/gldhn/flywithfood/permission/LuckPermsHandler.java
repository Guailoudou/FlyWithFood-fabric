package com.gldhn.flywithfood.permission;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.user.User;
import net.luckperms.api.node.Node;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;

public class LuckPermsHandler implements PermissionHandler {

    private LuckPerms luckPerms;

    public LuckPermsHandler() {
        this.luckPerms = LuckPermsProvider.get();
    }

    @Override
    public boolean hasPermission(ServerPlayer player, String permission, int defaultOpLevel) {
        try {
            User user = luckPerms.getPlayerAdapter(ServerPlayer.class).getUser(player);
            if (user != null) {
                return user.getCachedData().getPermissionData().checkPermission(permission).asBoolean();
            }
        } catch (Exception e) {
            com.gldhn.flywithfood.FlyWithFoodMod.LOGGER.warn("Failed to check permission via LuckPerms, falling back to OP check", e);
        }
        
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
