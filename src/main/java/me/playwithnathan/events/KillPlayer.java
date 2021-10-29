package me.playwithnathan.events;

import me.playwithnathan.Bounties;
import me.playwithnathan.util.DataUtil;
import me.playwithnathan.util.TextUtil;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class KillPlayer implements Listener {
    @EventHandler
    public void onKillPlayer(PlayerDeathEvent e) {
        Economy econ = Bounties.getEconomy();

        Player player = e.getEntity();
        Player killer = player.getKiller();

        if(killer == null) return;
        if(!DataUtil.has(player)) return;
        String bounties = DataUtil.get(player);

        double money = Double.parseDouble(bounties.split(":")[1]);

        // If killer's name equals the data's name
        if(killer.getName().equals(bounties.split(":")[0])) {
            econ.depositPlayer(killer, money);
            DataUtil.remove(player);
            Bukkit.getServer().broadcastMessage(TextUtil.color("&7The bounty of &a$" + money + " &7on &c" + player.getName() + " &7has been claimed by &6" + killer.getName() + "&7."));
        }
    }
}
