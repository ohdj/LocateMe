package org.ohdj.locateme;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public final class LocateMe extends JavaPlugin {

    @Override
    public void onEnable() {
        Objects.requireNonNull(this.getCommand("here")).setExecutor(this);
        getLogger().info("LocateMe已启用~");
    }

    @Override
    public void onDisable() {
        getLogger().warning("LocateMe已卸载~");
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
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
        String dimensionInfo;
        ChatColor dimensionColor;

        // 根据维度设置对应的颜色和信息
        switch (dimension) {
            case NORMAL:
                dimensionColor = ChatColor.GOLD; // 主世界橙色
                dimensionInfo = "主世界";
                break;
            case NETHER:
                dimensionColor = ChatColor.DARK_RED; // 地狱深红色
                dimensionInfo = "地狱";
                break;
            case THE_END:
                dimensionColor = ChatColor.GRAY; // 末地灰色
                dimensionInfo = "末地";
                break;
            default:
                dimensionColor = ChatColor.RESET; // 未知白色
                dimensionInfo = "未知";
                break;
        }

        // 生成并广播消息
        String messageFormat = ChatColor.AQUA + "[Here] " + ChatColor.YELLOW + "%s" + ChatColor.RESET + " 在 " + dimensionColor + "%s" + ChatColor.RESET + " 的 [ " + ChatColor.RED + "%.1f" + ChatColor.RESET + " , " + ChatColor.GREEN + "%.1f" + ChatColor.RESET + " , " + ChatColor.BLUE + "%.1f" + ChatColor.RESET;

        // 使用玩家的名字、维度信息和位置信息格式化消息
        String message = String.format(messageFormat, player.getName(), dimensionInfo, x, y, z);

        // 如果玩家在主世界或地狱，添加一个转换到另一个维度的坐标
        if (dimension == World.Environment.NORMAL || dimension == World.Environment.NETHER) {
            String targetDimensionColor;
            double factor;

            if (dimension == World.Environment.NORMAL) {
                targetDimensionColor = ChatColor.DARK_RED + "地狱";
                factor = 1 / 8.0;
            } else {
                targetDimensionColor = ChatColor.GOLD + "主世界";
                factor = 8.0;
            }

            String transitionFormat = " ] => " + targetDimensionColor + ChatColor.RESET + " [ " + ChatColor.RED + "%.1f" + ChatColor.RESET + " , " + ChatColor.BLUE + "%.1f" + ChatColor.RESET;
            message += String.format(transitionFormat, x * factor, z * factor);
        }

        // 添加消息的结束部分
        message += " ]";

        // 广播消息
        Bukkit.broadcastMessage(message);

        // 将使用该指令的玩家添加勾边高亮效果，持续10秒
        player.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, 200, 1));

        // 返回true表示命令执行成功
        return true;
    }
}
