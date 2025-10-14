package me.d15c07d.updates.util;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.ChatColor;

public class ColorUtil {

    public static void send(CommandSender sender, String msg) {
        if (msg == null || msg.isEmpty()) return;
        boolean looksLikeMiniMessage = msg.contains("<") && msg.contains(">");
        boolean looksLikeLegacy = msg.contains("&");
        if (looksLikeMiniMessage) {
            Component component = MiniMessage.miniMessage().deserialize(msg);
            sendComponent(sender, component);
        } else if (looksLikeLegacy) {
            String legacy = ChatColor.translateAlternateColorCodes('&', msg);
            sendComponent(sender, LegacyComponentSerializer.legacySection().deserialize(legacy));
        } else {
            sendComponent(sender, Component.text(msg));
        }
    }

    public static void sendComponent(CommandSender sender, Component component) {
        if (sender instanceof Player player) {
            player.sendMessage(component);
        } else if (sender instanceof net.kyori.adventure.audience.Audience audience) {
            audience.sendMessage(component);
        } else {
            sender.sendMessage(LegacyComponentSerializer.legacySection().serialize(component));
        }
    }

    public static String color(String msg) {
        if (msg == null) return "";
        return ChatColor.translateAlternateColorCodes('&', msg);
    }

    public static Component miniMessage(String msg) {
        return MiniMessage.miniMessage().deserialize(msg);
    }
}