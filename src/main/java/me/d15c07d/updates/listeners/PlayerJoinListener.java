package me.d15c07d.updates.listeners;

import me.d15c07d.updates.UpdatesPlugin;
import me.d15c07d.updates.util.ColorUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {
    private final UpdatesPlugin plugin;

    public PlayerJoinListener(UpdatesPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        if (!plugin.getPlayerDataManager().shouldShowJoinMsg(player.getUniqueId())) return;
        int unread = plugin.getUpdateManager().getUpdatesCount() - plugin.getPlayerDataManager().getSeenNumber(player.getUniqueId());
        if (unread > 0) {
            String msg = plugin.getConfig().getString("messages.join-unread")
                    .replace("{amount}", String.valueOf(unread));
            ColorUtil.send(player, msg);
        }
    }
}