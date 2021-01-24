package wildbingo.wildbingo;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandExecutor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

public final class WildBingo extends JavaPlugin implements Listener {
    private static CommandManager cm;

    @Override
    public void onEnable() {
        System.out.println("The WildBingo is ENABLED!");
        initEvent();
        initCommand();
        FileManager.setDefaultMaterial();

    }

    @Override
    public void onDisable() {
        System.out.println("The WildBingo is DISABLED!");

    }

    public void initEvent(){
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new EventManager(this), this);
        pm.registerEvents(this,this);

    }

    public void initCommand() {
        cm = new CommandManager(this);
        getCommand("wb").setExecutor(cm);
    }
}
