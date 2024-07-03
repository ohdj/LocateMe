package org.ohdj.locateme.executor;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

import java.text.MessageFormat;

public class HereExecutor implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        // 判断命令发送者是否为玩家
        if (!(sender instanceof Player)) {
            // 如果命令发送者不是玩家，向其发送一条消息并返回
            sender.sendMessage(ChatColor.RED + "只有玩家可以使用这个命令！");
            return true;
        }

        // 将命令发送者转换为玩家
        Player player = (Player) sender;

        // 获取玩家的位置信息
        double x = player.getLocation().getX();
        double y = player.getLocation().getY();
        double z = player.getLocation().getZ();

        // 获取玩家所在的维度
        World.Environment dimension = player.getWorld().getEnvironment();
        String dimensionWithColor;

        // 根据维度设置对应的颜色和信息
        switch (dimension) {
            case NORMAL:
                dimensionWithColor = ChatColor.GOLD + "主世界";
                break;
            case NETHER:
                dimensionWithColor = ChatColor.DARK_RED + "地狱";
                break;
            case THE_END:
                dimensionWithColor = ChatColor.GRAY + "末地";
                break;
            default:
                dimensionWithColor = ChatColor.RESET + "未知";
                break;
        }

        String messageTemp = "&b[Here] &e{0}&r 在 {1} &r的 [ &c{2}&r , &a{3}&r , &9{4}&r ]";
        // 使用玩家的名字、维度信息和位置信息格式化消息
        String messageFormat = MessageFormat.format(messageTemp,
                player.getName(),
                dimensionWithColor,
                String.format("%.1f", x),
                String.format("%.1f", y),
                String.format("%.1f", z));
        String message = ChatColor.translateAlternateColorCodes('&', messageFormat);

        // 如果玩家在主世界或地狱，添加一个转换到另一个维度的坐标
        if (dimension == World.Environment.NORMAL || dimension == World.Environment.NETHER) {
            String targetDimensionWithColor;
            double factor;

            if (dimension == World.Environment.NORMAL) {
                targetDimensionWithColor = ChatColor.DARK_RED + "地狱";
                factor = 1 / 8.0;
            } else {
                targetDimensionWithColor = ChatColor.GOLD + "主世界";
                factor = 8.0;
            }

            String transitionTemp = " => {0}&r [ &c{1}&r , &9{2}&r ]";
            String transitionFormat = MessageFormat.format(transitionTemp,
                    targetDimensionWithColor,
                    String.format("%.1f", x * factor),
                    String.format("%.1f", z * factor)
            );
            message += ChatColor.translateAlternateColorCodes('&', transitionFormat);
        }

        // 广播消息
        Bukkit.broadcastMessage(message);

        // 将使用该指令的玩家添加勾边高亮效果，持续10秒
        player.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, 200, 1));

        // 返回true表示命令执行成功
        return true;
    }
}
