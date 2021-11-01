package me.playwithnathan.events;

import me.playwithnathan.util.BountyUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class KillPlayer implements Listener {
    @EventHandler
    public void onKillPlayer(PlayerDeathEvent e) {
        Player player = e.getEntity();
        Player killer = player.getKiller();

        if(killer == null) return;
        if(!BountyUtil.has(player)) return;
        if(player.equals(killer)) return;

        double money = Double.parseDouble(BountyUtil.get(player).split(":")[1]);

        BountyUtil.remove(player, killer, money);
    }
}
