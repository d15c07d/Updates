package me.d15c07d.updates.commands;

import me.d15c07d.updates.UpdatesPlugin;
import me.d15c07d.updates.util.ColorUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class UpdateCommand implements CommandExecutor {
    private final UpdatesPlugin plugin;

    public UpdateCommand(UpdatesPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!sender.hasPermission("updates.admin")) {
            ColorUtil.send(sender, plugin.getConfig().getString("messages.no-permission"));
            return true;
        }
        if (args.length == 0) {
            ColorUtil.send(sender, plugin.getConfig().getString("messages.update-usage"));
            return true;
        }
        String msg = String.join(" ", args).replace("%nl%", "\n").replace("\\n", "\n");
        plugin.getUpdateManager().addUpdate(msg, sender.getName());
        ColorUtil.send(sender, plugin.getConfig().getString("messages.update-posted"));
        for (String cmdStr : plugin.getConfig().getStringList("on-update-commands")) {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), cmdStr.replace("{message}", msg).replace("{author}", sender.getName()));
        }
        return true;
    }
}