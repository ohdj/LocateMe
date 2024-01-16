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
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "只有玩家可以使用这个命令！");
            return true;
        }

        Player player = (Player) sender;

        // 获取玩家的位置信息，将坐标显示为1位小数
        double x = Math.round(player.getLocation().getX() * 10.0) / 10.0;
        double y = Math.round(player.getLocation().getY() * 10.0) / 10.0;
        double z = Math.round(player.getLocation().getZ() * 10.0) / 10.0;

        // 获取玩家所在的维度
        World.Environment dimension = player.getWorld().getEnvironment();
        String dimensionInfo;
        ChatColor dimensionColor;

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

        // 在服务器公屏显示提示信息
        Bukkit.broadcastMessage(ChatColor.AQUA + "[Here] " + ChatColor.YELLOW + player.getName() + ChatColor.RESET + " 在 " + dimensionColor + dimensionInfo +
                ChatColor.RESET + " 的 [ " +
                ChatColor.RED + x + ChatColor.RESET + " , " +
                ChatColor.GREEN + y + ChatColor.RESET + " , " +
                ChatColor.BLUE + z + ChatColor.RESET + (dimension == World.Environment.NORMAL ? " ] => " + ChatColor.DARK_RED + "地狱" + ChatColor.RESET + " [ " +
                ChatColor.RED + Math.round(x / 8 * 10.0) / 10.0 + ChatColor.RESET + " , " +
                ChatColor.BLUE + Math.round(z / 8 * 10.0) / 10.0 + ChatColor.RESET : dimension == World.Environment.NETHER ? " ] => " + ChatColor.GOLD + "主世界" + ChatColor.RESET + " [ " +
                ChatColor.RED + Math.round(x * 8 * 10.0) / 10.0 + ChatColor.RESET + " , " +
                ChatColor.BLUE + Math.round(z * 8 * 10.0) / 10.0 + ChatColor.RESET : "") + " ]");

        // 将使用该指令的玩家添加勾边高亮效果，持续10秒
        player.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, 200, 1));

        return true;
    }
}
