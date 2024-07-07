package org.ohdj.locateme;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.ohdj.locateme.executor.HereExecutor;
import org.ohdj.locateme.executor.WhereExecutor;
import org.ohdj.locateme.listener.OnPlayerListInventoryClickListener;

public final class LocateMe extends JavaPlugin {

    @Override
    public void onEnable() {
        getCommand("here").setExecutor(new HereExecutor());
        getCommand("where").setExecutor(new WhereExecutor());
        // 自定义事件：当玩家试图在自定义物品栏中拿取特殊物品时，会操作失败
        Bukkit.getPluginManager().registerEvents(new OnPlayerListInventoryClickListener(this), this);
        getLogger().info("LocateMe已启用~");
    }

    @Override
    public void onDisable() {
        getLogger().warning("LocateMe已卸载~");
    }
}
