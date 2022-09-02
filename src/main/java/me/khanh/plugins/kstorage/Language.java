package me.khanh.plugins.kstorage;

import lombok.Getter;
import lombok.SneakyThrows;
import me.khanh.plugins.kstorage.utils.LoggerUtils;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import revxrsal.commands.locales.LocaleReader;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Locale;
import java.util.Objects;

public class Language implements LocaleReader {
    @Getter
    private final KStoragePlugin plugin;
    @Getter
    private final String language;
    private final Locale locale;
    @Getter
    private Path langFile;
    @Getter
    private final FileConfiguration configuration;

    public Language(KStoragePlugin plugin, String language){
        this.plugin = plugin;
        this.language = language;

        if (language.contains("_")){
            String[] split = language.split("_");
            locale = new Locale(split[0], split[1]);
        } else {
            locale = new Locale(language);
        }

        setupLanguageFolder();

        configuration = YamlConfiguration.loadConfiguration(langFile.toFile());
    }
    @Override
    public boolean containsKey(String key) {
        return configuration.contains(key);
    }

    @Override
    public String get(String key) {
        return configuration.getString(key);
    }

    @Override
    public Locale getLocale() {
        return locale;
    }

    @SneakyThrows
    private void setupLanguageFolder(){
        Path langFolder = Paths.get(plugin.getDataFolder().getPath(), "languages");
        if (Files.notExists(langFolder)){
            Files.createDirectory(langFolder);
        }

        langFile = Paths.get(langFolder.toString(), language + ".yml");
        if (Files.notExists(langFile)){
            if (plugin.getResource(language + ".yml") == null){
                LoggerUtils.warning(String.format("The language file %s.yml was not found in the languages folder", language));
                LoggerUtils.info(String.format("&aCreating a file %s.yml with English language", language));
                Files.copy(Objects.requireNonNull(plugin.getResource("en_US.yml")), langFile);
            } else {
                Files.copy(Objects.requireNonNull(plugin.getResource(language + ".yml")), langFile);
            }
        }
    }
}
