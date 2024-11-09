# ItemCounterPlugin

A simple Minecraft plugin that counts how many of a specific item a player has in their inventory when they hold that item. Players can use the `/count` command to see the total count of the item in their inventory, with a cooldown to prevent spam. This plugin is compatible with Minecraft 1.21 and uses Spigot API.

## Features

- Counts the total number of a specific item in a player's inventory when they hold that item and use the `/count` command.
- Checks for custom item names (from anvils or other plugins) and uses them if available.
- Configurable cooldown period to prevent spam from using the `/count` command too frequently.
- Customizable, color-coded messages for a polished experience.

## Requirements

- Minecraft Server: Version 1.21 or above
- Java: JDK 17 or above
- Spigot API

## Installation

1. Download the latest release of the ItemCounterPlugin from the [Releases](https://github.com/your-username/ItemCounterPlugin/releases) page.
2. Place the `ItemCounterPlugin.jar` file in your server's `plugins` folder.
3. Start or restart your Minecraft server.

## Usage

- Hold an item in your hand.
- Use the `/count` command to display the total amount of that item in your inventory.
- If the item has a custom name, the plugin will display that name; otherwise, it will use the default item type name.

### Commands

- `/count` - Displays the total count of the held item in the player’s inventory.

### Permissions

- `itemcounter.use` - Allows players to use the `/count` command (default: true for all players).

## Configuration

The plugin includes a simple configuration file, `config.yml`, located in the `plugins/ItemCounterPlugin` folder. 
Edit the cooldown-period to set the cooldown duration between command uses.

```yaml
cooldown-period: 5  # The cooldown period in seconds between uses of the /count command
```

## License
This project is licensed under the MIT License. See the LICENSE file for details.

**Enjoy using the ItemCounterPlugin, and feel free to contribute or open issues for any improvements or bug reports!**
You can dm me thrrough Discord --> add me --> Mr.Alaa#0938
