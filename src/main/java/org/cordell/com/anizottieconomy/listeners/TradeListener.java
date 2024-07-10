package org.cordell.com.anizottieconomy.listeners;

import io.papermc.paper.event.player.PlayerPurchaseEvent;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.cordell.com.anizottieconomy.AnizottiEconomy_EXMPL;

import java.io.IOException;
import java.time.LocalDate;


public class TradeListener implements Listener {
    @EventHandler
    public void onTrade(PlayerPurchaseEvent event) {
        System.out.println(event.getPlayer().getName() + " buy something!");

        var earns = event.getTrade().getResult();
        if (earns.getType() != Material.EMERALD) return;

        var currentDate = LocalDate.now();
        var day = String.valueOf(currentDate.getDayOfMonth());
        var month = String.valueOf(currentDate.getMonthValue());
        var year = String.valueOf(currentDate.getYear());

        var currentKey = day + "." + month + "." + year;
        var manager = AnizottiEconomy_EXMPL.dataManager;

        System.out.println("Today is " + currentKey);

        try {
            var value = manager.GetValue(currentKey);
            if (value == null) manager.AddKey(currentKey, earns.getAmount() + "");
            else  manager.SetValue(currentKey, (Integer.parseInt(value.z) + earns.getAmount()) + "");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
