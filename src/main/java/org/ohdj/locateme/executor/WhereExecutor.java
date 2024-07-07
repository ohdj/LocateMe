package org.ohdj.locateme.executor;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.jetbrains.annotations.NotNull;

public class WhereExecutor implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("This command can only be used by players.");
            return true;
        }

        Player player = (Player) sender;
        Inventory inv = Bukkit.createInventory(null, 9, "Online Players");

        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            ItemStack skull = new ItemStack(Material.PLAYER_HEAD);
            SkullMeta meta = (SkullMeta) skull.getItemMeta();
            meta.setOwningPlayer(onlinePlayer);
            meta.setDisplayName(ChatColor.GREEN + onlinePlayer.getName());
            skull.setItemMeta(meta);
            inv.addItem(skull);
        }

        player.openInventory(inv);
        return true;
    }
}
