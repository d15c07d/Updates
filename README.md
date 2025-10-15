# Updates

A modern Minecraft Paper (1.21.8+) plugin for managing, displaying, and tracking server updates for your players.
Easily post news, patch notes, or changelogs and ensure every player stays informed!

---

## Features

- **/updates** — View updates, newest first, with click-to-navigate pagination.
- **/update `<message>`** — Admins can post new updates. Supports `%nl%` or `\n` for line breaks.
- **/updates clear** — Admins can clear all updates.
- **/updates help** — Shows all commands. Players see only player commands; admins see all.
- **/updates toggle** — Players can toggle unread update join notifications.
- **Unread update tracking:** Players are notified on join if they haven't seen new updates.
- **All messages configurable:** Supports both [MiniMessage](https://docs.advntr.dev/minimessage/format.html) and legacy `&` color codes.
- **Configurable command hooks:** Run one or more server commands automatically when an update is posted (great for Discord integrations).
- **Player data (seen updates, toggle state) stored per-UUID** in a separate file.
- **Updates stored in their own YAML file.**
- **Clean, and modular plugin, feel free to fork and expand as you like!**

---

## Commands & Permissions

| Command                     | Permission         | Description                                |
|-----------------------------|--------------------|--------------------------------------------|
| `/updates`                  | `updates.use`      | View updates (pagination, clickable nav)   |
| `/updates help`             | `updates.use`      | View help panel                            |
| `/updates toggle`           | `updates.use`      | Toggle unread join notification            |
| `/update <message>`         | `updates.admin`    | Post a new update                          |
| `/updates clear`            | `updates.admin`    | Clear all updates                          |

- **Permission `updates.admin`** is required for admin commands.  
- **Players** by default can use `/updates`, `/updates help`, and `/updates toggle`.

---

## Configuration

### config.yml

```yaml
updates-per-page: 5

messages:
  no-permission: "&cYou do not have permission."
  cleared: "&aAll updates cleared."
  update-posted: "&aUpdate posted."
  update-usage: "&eUsage: /update <message>"
  join-unread: "<yellow>You have <green>{amount}</green> unread update(s)! Do <white>/updates</white>."
  updates-header: "<gold>--- Updates (<white>{page}</white> of <white>{total}</white>) ---"
  update-format: "<gray>#<white>{number}</white> <gray>by <green>{author}</green>:<reset>\n<white>{message}</white>"
  toggle-on: "&aYou will no longer see unread join messages."
  toggle-off: "&cYou will now see unread join messages."
  no-updates: "&7No updates to show."
  player-only: "&cThis command can only be used by players."
  unknown-subcommand: "&cInvalid subcommand or page number. Do /updates help"
  help-header: "<gold>--- Updates Help ---"
  help-player: "&e/updates &7- Show updates\n&e/updates help &7- Show this panel\n&e/updates toggle &7- Toggle unread join msg"
  help-admin: "&c/update <msg> &7- Post an update\n&c/updates clear &7- Clear all updates"

on-update-commands:
  - "yourcommand {message}"
  - "yourcommand {author}"
```

#### **on-update-commands**

Add one or more commands to run (from console) _every time an update is posted_.  
Supports `{message}` and `{author}` placeholders.

Example:
```yaml
on-update-commands:
  - "say [Update] {author} posted: {message}"
  - "discordsrv broadcast **Update:** {message} (by {author})"
```

---

## Usage

- **Posting an update:**  
  `/update The server will restart soon%nl%Please finish your activities!`
- **Line breaks:**  
  Use `%nl%` or `\n` in your message.

- **Clearing updates:**  
  `/updates clear`

- **Viewing updates:**  
  `/updates` — includes clickable navigation for multiple pages.

- **Toggling join notifications:**  
  `/updates toggle`

---

## Player Join Experience

- If a player has not seen the latest updates, they receive a join message telling them how many updates are unread and to use `/updates`.
- Once they view the updates, they will not receive the join message again until new updates are posted.
- Players can toggle this join notification with `/updates toggle`.

---

## Requirements

- **Minecraft Paper 1.21.8 or newer**
- Java 17+

---

## Building

1. Clone the repository:
   ```
   git clone https://github.com/d15c07d/Updates.git
   ```
2. Build using Maven or Gradle.
3. Place the plugin jar in your `plugins` folder and restart/reload your server.

---

## Support & Contributions

- Found a bug or want a new feature? [Open an issue](https://github.com/d15c07d/Updates/issues)
- Pull requests are welcome!
- Questions? Ask in issues or discussions.

---

## License

MIT License

---

© d15c07d, 2025
