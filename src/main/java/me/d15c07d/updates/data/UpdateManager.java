package me.d15c07d.updates.data;

import me.d15c07d.updates.UpdatesPlugin;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class UpdateManager {
    private final UpdatesPlugin plugin;
    private final File file;
    private YamlConfiguration config;

    public UpdateManager(UpdatesPlugin plugin) {
        this.plugin = plugin;
        this.file = new File(plugin.getDataFolder(), "updates.yml");
        reload();
    }

    public void reload() {
        if (!file.exists()) {
            plugin.saveResource("updates.yml", false);
        }
        config = YamlConfiguration.loadConfiguration(file);
    }

    public List<Map<String, Object>> getUpdates() {
        List<Map<String, Object>> list = new ArrayList<>();
        if (config.contains("updates")) {
            list = (List<Map<String, Object>>) config.getList("updates");
        }
        if (list == null) return new ArrayList<>();
        Collections.reverse(list); // Newest first
        return list;
    }

    public int getUpdatesCount() {
        List<Map<String, Object>> list = (List<Map<String, Object>>) config.getList("updates");
        return list != null ? list.size() : 0;
    }

    public void addUpdate(String msg, String author) {
        List<Map<String, Object>> list = (List<Map<String, Object>>) config.getList("updates");
        if (list == null) list = new ArrayList<>();
        Map<String, Object> update = new HashMap<>();
        update.put("message", msg);
        update.put("author", author);
        update.put("time", System.currentTimeMillis());
        list.add(update);
        config.set("updates", list);
        save();
    }

    public void clearUpdates() {
        config.set("updates", new ArrayList<>());
        save();
    }

    private void save() {
        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}