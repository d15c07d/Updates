package me.d15c07d.updates.commands;

import me.d15c07d.updates.UpdatesPlugin;
import me.d15c07d.updates.util.ColorUtil;
import me.d15c07d.updates.util.UpdateMessageUtil;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

public class UpdatesCommand implements CommandExecutor {
    private final UpdatesPlugin plugin;

    public UpdatesCommand(UpdatesPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length == 0) {
            // Show updates (paged)
            UpdateMessageUtil.sendPagedUpdates(sender, plugin, 1);
            if (sender instanceof Player player) {
                plugin.getPlayerDataManager().markAllSeen(player.getUniqueId(), plugin.getUpdateManager().getUpdatesCount());
            }
            return true;
        }
        switch (args[0].toLowerCase()) {
            case "help" -> {
                UpdateMessageUtil.sendHelp(sender, sender.hasPermission("updates.admin"));
                return true;
            }
            case "clear" -> {
                if (!sender.hasPermission("updates.admin")) {
                    sender.sendMessage(ColorUtil.color(plugin.getConfig().getString("messages.no-permission")));
                    return true;
                }
                plugin.getUpdateManager().clearUpdates();
                sender.sendMessage(ColorUtil.color(plugin.getConfig().getString("messages.cleared")));
                return true;
            }
            case "toggle" -> {
                if (!(sender instanceof Player player)) {
                    sender.sendMessage("Players only.");
                    return true;
                }
                boolean toggled = plugin.getPlayerDataManager().toggleJoinMsg(player.getUniqueId());
                sender.sendMessage(ColorUtil.color(plugin.getConfig().getString(
                        toggled ? "messages.toggle-on" : "messages.toggle-off")));
                return true;
            }
            default -> {
                // page navigation /updates <page>
                try {
                    int page = Integer.parseInt(args[0]);
                    UpdateMessageUtil.sendPagedUpdates(sender, plugin, page);
                } catch (NumberFormatException e) {
                    sender.sendMessage(ColorUtil.color("&cInvalid subcommand or page number."));
                }
                return true;
            }
        }
    }
}