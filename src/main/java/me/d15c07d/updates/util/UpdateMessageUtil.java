package me.d15c07d.updates.util;

import me.d15c07d.updates.UpdatesPlugin;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Map;

public class UpdateMessageUtil {
    public static void sendPagedUpdates(CommandSender sender, UpdatesPlugin plugin, int page) {
        int perPage = plugin.getConfig().getInt("updates-per-page", 5);
        List<Map<String, Object>> updates = plugin.getUpdateManager().getUpdates();
        int total = updates.size();
        int pages = (int) Math.ceil((double) total / perPage);
        if (pages == 0) pages = 1;
        if (page < 1 || page > pages) page = 1;

        String header = plugin.getConfig().getString("messages.updates-header")
                .replace("{page}", page + "/" + pages)
                .replace("{total}", String.valueOf(total));
        ColorUtil.send(sender, header);

        if (total == 0) {
            ColorUtil.send(sender, plugin.getConfig().getString("messages.no-updates", "&7No updates to show."));
        } else {
            for (int i = (page - 1) * perPage; i < Math.min((page - 1) * perPage + perPage, total); i++) {
                Map<String, Object> update = updates.get(i);
                String msg = (String) update.get("message");
                String author = (String) update.get("author");
                long time = (long) update.get("time");
                String formatted = plugin.getConfig().getString("messages.update-format")
                        .replace("{number}", String.valueOf(total - i))
                        .replace("{message}", msg)
                        .replace("{author}", author)
                        .replace("{time}", String.valueOf(time));
                ColorUtil.send(sender, formatted);
            }
        }

        if (pages > 1) {
            Component nav = Component.empty();
            if (page > 1)
                nav = nav.append(Component.text("<< Prev ").clickEvent(ClickEvent.runCommand("/updates " + (page - 1))));
            nav = nav.append(Component.text(" | Page " + page + "/" + pages + " | "));
            if (page < pages)
                nav = nav.append(Component.text("Next >>").clickEvent(ClickEvent.runCommand("/updates " + (page + 1))));
            if (sender instanceof Player player) {
                player.sendMessage(nav);
            } else {
                sender.sendMessage(net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer.legacySection().serialize(nav));
            }
        }
    }

    public static void sendHelp(CommandSender sender, boolean admin, UpdatesPlugin plugin) {
        ColorUtil.send(sender, plugin.getConfig().getString("messages.help-header"));
        ColorUtil.send(sender, plugin.getConfig().getString("messages.help-player"));
        if (admin) {
            ColorUtil.send(sender, plugin.getConfig().getString("messages.help-admin"));
        }
    }
}