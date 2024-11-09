package org.alaa.itemcounter;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.UUID;

public class ItemCounterPlugin extends JavaPlugin implements CommandExecutor {

    private final HashMap<UUID, Long> cooldowns = new HashMap<>();
    private long cooldownPeriodMillis;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        loadCooldownFromConfig();

        PluginCommand countCommand = this.getCommand("count");
        if (countCommand != null) {
            countCommand.setExecutor(this);
        }

        getLogger().info("ItemCounterPlugin has been enabled.");
    }

    @Override
    public void onDisable() {
        getLogger().info("ItemCounterPlugin has been disabled.");
    }

    private void loadCooldownFromConfig() {
        FileConfiguration config = getConfig();
        int cooldownPeriodSeconds = config.getInt("cooldown-period", 5);
        cooldownPeriodMillis = cooldownPeriodSeconds * 1000L;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            UUID playerId = player.getUniqueId();
            long currentTime = System.currentTimeMillis();

            // Check if the player is on cooldown
            if (cooldowns.containsKey(playerId) && (currentTime - cooldowns.get(playerId) < cooldownPeriodMillis)) {
                long timeLeft = (cooldownPeriodMillis - (currentTime - cooldowns.get(playerId))) / 1000;
                player.sendMessage(ChatColor.RED + "You must wait " + ChatColor.YELLOW + timeLeft
                        + ChatColor.RED + " more seconds before using " + ChatColor.AQUA + "/count" + ChatColor.RED + " again.");
                return true;
            }

            // Update the cooldown time
            cooldowns.put(playerId, currentTime);

            ItemStack heldItem = player.getInventory().getItemInMainHand();
            if (heldItem != null && heldItem.getType() != Material.AIR) {
                Material itemType = heldItem.getType();
                int totalItemCount = 0;

                // Check for a custom display name
                String itemNameFormatted;
                ItemMeta itemMeta = heldItem.getItemMeta();
                if (itemMeta != null && itemMeta.hasDisplayName()) {
                    itemNameFormatted = itemMeta.getDisplayName();
                } else {
                    // Fallback to default material name with formatting
                    itemNameFormatted = itemType.name().toLowerCase().replace('_', ' ');
                    itemNameFormatted = Character.toUpperCase(itemNameFormatted.charAt(0)) + itemNameFormatted.substring(1);
                }

                // Count all items of the same type in the player's inventory
                for (ItemStack itemStack : player.getInventory().getContents()) {
                    if (itemStack != null && itemStack.getType() == itemType) {
                        totalItemCount += itemStack.getAmount();
                    }
                }

                // Send the count message to the player with color
                player.sendMessage(ChatColor.GREEN + "Total " + ChatColor.AQUA + itemNameFormatted
                        + ChatColor.GREEN + "(s) in inventory: " + ChatColor.YELLOW + totalItemCount);
            } else {
                // Message if no item is held
                player.sendMessage(ChatColor.RED + "You are not holding any item.");
            }
            return true;
        } else {
            sender.sendMessage("This command can only be used by a player.");
            return true;
        }
    }
}
