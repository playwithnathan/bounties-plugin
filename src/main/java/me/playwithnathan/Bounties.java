package me.playwithnathan;

import me.playwithnathan.classes.ServerAssigner;
import me.playwithnathan.commands.CommandManager;
import me.playwithnathan.events.InvClick;
import me.playwithnathan.events.InvClose;
import me.playwithnathan.events.KillPlayer;
import me.playwithnathan.classes.TabComplete;
import me.playwithnathan.util.TextUtil;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class Bounties extends JavaPlugin {
    private static Bounties instance;
    private static Permission perms;
    private static Economy econ = null;

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;

        // Dependencies
        createDepend("Vault");
        createDepend("PlaceholderAPI");

        RegisteredServiceProvider<Permission> rspPerms = getServer().getServicesManager().getRegistration(Permission.class);
        if(rspPerms == null) throw new RuntimeException("Disabled because a Permission plugin wasn't found");
        perms = rspPerms.getProvider();

        RegisteredServiceProvider<Economy> rspEcon = getServer().getServicesManager().getRegistration(Economy.class);
        if(rspEcon == null) throw new RuntimeException("Disabled because an Economy plugin wasn't found");
        econ = rspEcon.getProvider();

        // Commands
        Objects.requireNonNull(this.getCommand("bounties")).setExecutor(new CommandManager());
        Objects.requireNonNull(this.getCommand("bounties")).setTabCompleter(new TabComplete());

        // Events
        registerEvent(new InvClick());
        registerEvent(new InvClose());
        registerEvent(new KillPlayer());

        // Schedulers
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new ServerAssigner(), 20*60 * 20, 20*60 * 20); // 20*60 = 1min

        // Plugin is on
        getLogger().info(TextUtil.color("&aPlugin Enabled"));
    }

    /**
     * Create a dependent plugin
     * @param plugin the plugin we depend on
     */
    private void createDepend(String plugin) {
        if(Bukkit.getPluginManager().getPlugin(plugin) == null) throw new RuntimeException("Disabled because " + plugin + " wasn't found");
    }

    /**
     * Register an event
     * @param event the file
     */
    private void registerEvent(Listener event) {
        Bukkit.getPluginManager().registerEvents(event, this);
    }

    public static Bounties getInstance() {
        return instance;
    }
    public static Permission getPermissions() {
        return perms;
    }
    public static Economy getEconomy() {
        return econ;
    }

    @Override
    public void onDisable() {
        // Plugin is off
        getLogger().info(TextUtil.color("&aPlugin Disabled"));
    }
}
