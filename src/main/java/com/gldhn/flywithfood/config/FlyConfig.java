package com.gldhn.flywithfood.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.gldhn.flywithfood.FlyWithFoodMod;
import net.fabricmc.loader.api.FabricLoader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FlyConfig {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Path CONFIG_PATH = FabricLoader.getInstance().getConfigDir().resolve("flywithfood.json");

    public static double hungerDrainPerSecond = 1.0;
    public static int minHungerToFly = 6;
    public static int hungerDrainIntervalTicks = 20;
    
    public static String messageFlyEnabled = "§a[FlyWithFood] 飞行已启用！飞行时会消耗饥饿值。";
    public static String messageFlyDisabled = "§c[FlyWithFood] 飞行已禁用！";
    public static String messageHungerLow = "§c[FlyWithFood] 饥饿值不足，飞行已自动关闭！";
    public static String messageConfigReloaded = "§a[FlyWithFood] 配置文件已重新加载！";

    public static void load() {
        if (!Files.exists(CONFIG_PATH)) {
            save();
            return;
        }

        try {
            String json = Files.readString(CONFIG_PATH);
            ConfigData data = GSON.fromJson(json, ConfigData.class);
            if (data != null) {
                hungerDrainPerSecond = data.hungerDrainPerSecond;
                minHungerToFly = data.minHungerToFly;
                hungerDrainIntervalTicks = data.hungerDrainIntervalTicks;
                if (data.messageFlyEnabled != null) messageFlyEnabled = data.messageFlyEnabled;
                if (data.messageFlyDisabled != null) messageFlyDisabled = data.messageFlyDisabled;
                if (data.messageHungerLow != null) messageHungerLow = data.messageHungerLow;
                if (data.messageConfigReloaded != null) messageConfigReloaded = data.messageConfigReloaded;
            }
        } catch (Exception e) {
            FlyWithFoodMod.LOGGER.error("Failed to load config, using defaults", e);
            save();
        }
    }

    public static void reload() {
        load();
        FlyWithFoodMod.LOGGER.info("Config reloaded");
    }

    public static void save() {
        ConfigData data = new ConfigData();
        data.hungerDrainPerSecond = hungerDrainPerSecond;
        data.minHungerToFly = minHungerToFly;
        data.hungerDrainIntervalTicks = hungerDrainIntervalTicks;
        data.messageFlyEnabled = messageFlyEnabled;
        data.messageFlyDisabled = messageFlyDisabled;
        data.messageHungerLow = messageHungerLow;
        data.messageConfigReloaded = messageConfigReloaded;

        try {
            Files.createDirectories(CONFIG_PATH.getParent());
            Files.writeString(CONFIG_PATH, GSON.toJson(data));
        } catch (IOException e) {
            FlyWithFoodMod.LOGGER.error("Failed to save config", e);
        }
    }

    private static class ConfigData {
        double hungerDrainPerSecond = 1.0;
        int minHungerToFly = 6;
        int hungerDrainIntervalTicks = 20;
        String messageFlyEnabled = "§a[FlyWithFood] 飞行已启用！飞行时会消耗饥饿值。";
        String messageFlyDisabled = "§c[FlyWithFood] 飞行已禁用！";
        String messageHungerLow = "§c[FlyWithFood] 饥饿值不足，飞行已自动关闭！";
        String messageConfigReloaded = "§a[FlyWithFood] 配置文件已重新加载！";
    }
}
