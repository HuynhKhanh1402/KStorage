package me.khanh.plugins.kstorage.utils;

import me.khanh.plugins.kstorage.KStoragePlugin;

public class LoggerUtils {
    private static final KStoragePlugin plugin = KStoragePlugin.getPlugin(KStoragePlugin.class);

    public static void info(String message){
        plugin.getLogger().info(StringUtils.color(message));
    }

    public static void warning(String message){
        plugin.getLogger().warning(message);
    }

    public static void severe(String message){
        plugin.getLogger().severe(message);
    }
}
