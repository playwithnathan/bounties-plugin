package me.playwithnathan.classes;

import me.playwithnathan.util.BountyUtil;
import me.playwithnathan.util.ConsentUtil;
import me.playwithnathan.util.TextUtil;
import org.bukkit.Bukkit;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;

public class ServerAssigner implements Runnable {

    @Override
    public void run() {
        Player target = null;
        double highestScore = 0;

        // Find player with the highest score
        for(Player player : Bukkit.getOnlinePlayers()) {
            // Ignore if consent hasn't been given
            if(ConsentUtil.noHave(player)) continue;

            // Ignore if already has bounty
            if(BountyUtil.has(player)) continue;

            // Ignore hidden players
            if(player.spigot().getHiddenPlayers().contains(player)) continue;

            int playTime = player.getStatistic(Statistic.TOTAL_WORLD_TIME);
            int kills = player.getStatistic(Statistic.PLAYER_KILLS);
            kills = kills == 0 ? 1 : kills;

            // Not enough playtime to get a server assigned bounty
            if(playTime < 20*60*2) continue;

            // Calculate the player's score
            double score = (double) playTime / kills;

            // Get the highest score
            if(score > highestScore) {
                highestScore = score;
                target = player;
            }
        }

        // If no targets, just stop...
        if(target == null) return;

        // Determine money value
        int money;
        int percent = TextUtil.random(0, 9);

        if(percent == 0) {
            money = TextUtil.random(40000, 50000);
        } else if(percent == 1 || percent == 2) {
            money = TextUtil.random(25000, 40000);
        } else if(percent > 2 && percent < 6) {
            money = TextUtil.random(10000, 25000);
        } else {
            money = TextUtil.random(1000, 10000);
        }

        // Set bounty on player
        BountyUtil.set(target, "server", money);
    }
}
