package org.cordell.com.anizottieconomy.listeners;

import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.cordell.com.anizottieconomy.db.Prices;


public class PlayerListener implements Listener {
    @EventHandler
    public void onPlayerClick(PlayerInteractEvent event) {
        var block = event.getClickedBlock();
        if (block == null) return;

        var player = event.getPlayer();
        if (!player.getInventory().getItemInMainHand().getType().equals(Material.STICK)) return;
        if (!event.getAction().isRightClick()) return;

        if (block.getType().equals(Material.CHEST)) {
            var price = 0d;
            for (var item : ((Chest)block.getState()).getInventory())
                price += Prices.GetPrice(item.getType());

            player.sendMessage("Current cost of chest is: " + price + " HAD");
            return;
        }

        player.sendMessage("Current cost of block is: " + Prices.GetPrice(block.getType()) + " HAD");
    }
}
