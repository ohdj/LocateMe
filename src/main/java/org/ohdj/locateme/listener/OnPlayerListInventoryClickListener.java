package org.ohdj.locateme.listener;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.ohdj.locateme.holder.OnlinePlayersHolder;

public class OnPlayerListInventoryClickListener implements Listener {
    @EventHandler
    public void OnPlayerListInventoryClick(InventoryClickEvent event) {
        // 当前的物品栏
        Inventory currentInv = event.getInventory();
        // 获取点击的玩家
        Player player = Bukkit.getPlayerExact(event.getWhoClicked().getName());

        // 判断是不是远程的自定义箱子
        Inventory remoteInv = OnlinePlayersHolder.getInventory(player);
        if (remoteInv == null) {
            // 为null，肯定不是
            return;
        } else {
            // 非空，再判断
            if (remoteInv == currentInv) {
                // 两者是同一对象
                event.setCancelled(true);
            }
        }
    }
}
