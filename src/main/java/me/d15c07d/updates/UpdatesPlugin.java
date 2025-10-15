package me.d15c07d.updates;

import me.d15c07d.updates.commands.UpdateCommand;
import me.d15c07d.updates.commands.UpdatesCommand;
import me.d15c07d.updates.commands.UpdatesTabCompleter;
import me.d15c07d.updates.listeners.PlayerJoinListener;
import me.d15c07d.updates.data.PlayerDataManager;
import me.d15c07d.updates.data.UpdateManager;
import org.bukkit.plugin.java.JavaPlugin;

public class UpdatesPlugin extends JavaPlugin {
    private UpdateManager updateManager;
    private PlayerDataManager playerDataManager;

    @Override
    public void onEnable() {
        getLogger().info("Enabling Updates v1.0.0 by d15c07d");
        saveDefaultConfig();
        updateManager = new UpdateManager(this);
        playerDataManager = new PlayerDataManager(this);

        getCommand("updates").setExecutor(new UpdatesCommand(this));
        getCommand("updates").setTabCompleter(new UpdatesTabCompleter());
        getCommand("update").setExecutor(new UpdateCommand(this));

        getServer().getPluginManager().registerEvents(new PlayerJoinListener(this), this);
    }

    public UpdateManager getUpdateManager() {
        return updateManager;
    }

    public PlayerDataManager getPlayerDataManager() {
        return playerDataManager;
    }
}