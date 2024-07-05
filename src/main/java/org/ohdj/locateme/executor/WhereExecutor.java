package org.ohdj.locateme.executor;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.ohdj.locateme.holder.OnlinePlayersHolder;

public class WhereExecutor implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (args.length == 0) {
//                player.sendMessage(ChatColor.RED + "请指定要查询的对象");

                // 打开共享变量存储的“玩家列表物品栏”
                player.openInventory(OnlinePlayersHolder.getInventory(player));
            } else if (args.length == 1) {
                player.sendMessage(args[0]);
            }

            return true;
        }
        return false;
    }
}
