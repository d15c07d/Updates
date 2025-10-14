package me.d15c07d.updates.data;

import me.d15c07d.updates.UpdatesPlugin;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class PlayerDataManager {
    private final UpdatesPlugin plugin;
    private final File file;
    private YamlConfiguration config;

    public PlayerDataManager(UpdatesPlugin plugin) {
        this.plugin = plugin;
        this.file = new File(plugin.getDataFolder(), "playerdata.yml");
        reload();
    }

    public void reload() {
        if (!file.exists()) {
            try { file.createNewFile(); }
            catch (IOException ignored) {}
        }
        config = YamlConfiguration.loadConfiguration(file);
    }

    public int getSeenNumber(UUID uuid) {
        return config.getInt(uuid + ".seen", 0);
    }

    public void markAllSeen(UUID uuid, int seen) {
        config.set(uuid + ".seen", seen);
        save();
    }

    public boolean shouldShowJoinMsg(UUID uuid) {
        return !config.getBoolean(uuid + ".toggle", false);
    }

    public boolean toggleJoinMsg(UUID uuid) {
        boolean toggled = !config.getBoolean(uuid + ".toggle", false);
        config.set(uuid + ".toggle", toggled);
        save();
        return toggled;
    }

    private void save() {
        try { config.save(file); } catch (IOException ignored) {}
    }
}