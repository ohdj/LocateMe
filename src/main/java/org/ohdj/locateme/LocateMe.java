package org.ohdj.locateme;

import org.bukkit.plugin.java.JavaPlugin;
import org.ohdj.locateme.executor.HereExecutor;
import org.ohdj.locateme.executor.WhereExecutor;

public final class LocateMe extends JavaPlugin {

    @Override
    public void onEnable() {
        getCommand("here").setExecutor(new HereExecutor());
        getCommand("where").setExecutor(new WhereExecutor());
        getLogger().info("LocateMe已启用~");
    }

    @Override
    public void onDisable() {
        getLogger().warning("LocateMe已卸载~");
    }
}
