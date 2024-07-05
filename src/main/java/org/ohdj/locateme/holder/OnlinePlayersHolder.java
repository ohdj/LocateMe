package org.ohdj.locateme.holder;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

public class OnlinePlayersHolder {
    public static Inventory getInventory(Player player) {
        Inventory inventory = Bukkit.createInventory(null, 9, "玩家列表");

        Bukkit.getOnlinePlayers().forEach(onlinePlayer -> {

            // 创建玩家头颅 ItemStack
            ItemStack skull = new ItemStack(Material.PLAYER_HEAD);

            SkullMeta meta = (SkullMeta) skull.getItemMeta();
            meta.setOwningPlayer(player);           // 设置玩家名称
            meta.setDisplayName(player.getName());  // 设置显示名称

            skull.setItemMeta(meta);

            // 将头颅添加到容器中
            inventory.addItem(skull);
        });

        return inventory;
    }
}
