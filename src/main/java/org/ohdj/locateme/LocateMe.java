package org.ohdj.locateme;

import org.bukkit.plugin.java.JavaPlugin;
import org.ohdj.locateme.executor.HereExecutor;

public final class LocateMe extends JavaPlugin {

    @Override
    public void onEnable() {
        getCommand("here").setExecutor(new HereExecutor());
        getLogger().info("LocateMe已启用~");
    }

    @Override
    public void onDisable() {
        getLogger().warning("LocateMe已卸载~");
    }
}
