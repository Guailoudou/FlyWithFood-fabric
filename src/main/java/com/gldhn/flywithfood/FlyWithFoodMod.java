package com.gldhn.flywithfood;

import com.gldhn.flywithfood.command.FlyCommand;
import com.gldhn.flywithfood.config.FlyConfig;
import com.gldhn.flywithfood.handler.FlyTickHandler;
import com.gldhn.flywithfood.permission.PermissionManager;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FlyWithFoodMod implements ModInitializer {
    public static final String MOD_ID = "flywithfood";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        FlyConfig.load();
        PermissionManager.init();
        
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            FlyCommand.register(dispatcher);
        });

        ServerTickEvents.END_SERVER_TICK.register(FlyTickHandler::onServerTick);

        LOGGER.info("FlyWithFood mod initialized!");
    }
}
