package me.khanh.plugins.kstorage.utils;

import me.clip.placeholderapi.PlaceholderAPI;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {
    public static final Pattern HEX_PATTERN = Pattern.compile("&(#[a-f0-9]{6})", Pattern.CASE_INSENSITIVE);

    private static boolean isHookedPlaceholderAPI() {
        return Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI");
    }

    public static String color(String input) {
        if (input.isEmpty()) {
            return input;
        }
        Matcher m = HEX_PATTERN.matcher(input);
        try {
            ChatColor.class.getDeclaredMethod("of", String.class);
            while (m.find())
                input = input.replace(m.group(), ChatColor.of(m.group(1)).toString());
        } catch (Exception e) {
            while (m.find())
                input = input.replace(m.group(), "");
        }
        return ChatColor.translateAlternateColorCodes('&', input);
    }

    public static String setPlaceholder(Player player, String s) {
        if (s == null || s.isEmpty()){
            return s;
        }

        if (isHookedPlaceholderAPI()) {
            return color(PlaceholderAPI.setPlaceholders(player, s));
        } else {
            return color(s);
        }
    }

    public static String setPlaceholder(CommandSender sender, String s){
        if (s == null || s.isEmpty()){
            return s;
        }

        if (isHookedPlaceholderAPI()){
            if (sender instanceof Player){
                return setPlaceholder((Player) sender, s);
            }
            try {
                return color(PlaceholderAPI.setPlaceholders(null, s));
            } catch (NullPointerException e){
                return color(s);
            }
        }
        return color(s);
    }
}
