package org.ohdj.locateme.listener;

import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.plugin.java.JavaPlugin;

import static org.bukkit.Bukkit.getLogger;
import static org.bukkit.Bukkit.getScheduler;

public class OnPlayerListInventoryClickListener implements Listener {
    JavaPlugin plugin;

    public OnPlayerListInventoryClickListener(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void OnPlayerListInventoryClick(InventoryClickEvent event) {
        if (event.getView().getTitle().equals("Online Players")) {
            // 如果点击的是玩家自己的物品栏，则不处理
            if (event.getClickedInventory().getType() == InventoryType.CHEST) {
                event.setCancelled(true);
            }

            ItemStack clickedItem = event.getCurrentItem();
            if (clickedItem != null && clickedItem.getType() == Material.PLAYER_HEAD) {
                SkullMeta meta = (SkullMeta) clickedItem.getItemMeta();
                if (meta != null && meta.hasOwner()) {
                    Player clickedPlayer = Bukkit.getPlayer(meta.getOwningPlayer().getUniqueId());
                    if (clickedPlayer != null) {
                        Location loc = clickedPlayer.getLocation();
                        String message = ChatColor.GOLD + clickedPlayer.getName() + "'s location: "
                                + "X: " + loc.getBlockX() + " Y: " + loc.getBlockY() + " Z: " + loc.getBlockZ();
                        event.getWhoClicked().sendMessage(message);
                        ((Player) event.getWhoClicked()).playSound(clickedPlayer.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0f, 1.0f);
                        getScheduler().runTask(plugin, () -> event.getWhoClicked().closeInventory());
                    }
                }
            }
        }
    }
}
