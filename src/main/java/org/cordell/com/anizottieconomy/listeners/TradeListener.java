package org.cordell.com.anizottieconomy.listeners;

import io.papermc.paper.event.player.PlayerTradeEvent;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.cordell.com.anizottieconomy.AnizottiEconomy_EXMPL;

import java.io.IOException;
import java.time.LocalDate;


public class TradeListener implements Listener {
    @EventHandler
    public void onTrade(PlayerTradeEvent event) {
        var earns = event.getTrade().getResult();
        if (earns.getType() != Material.EMERALD) return;

        var currentDate = LocalDate.now();
        var day = String.valueOf(currentDate.getDayOfMonth());
        var month = String.valueOf(currentDate.getMonthValue());
        var year = String.valueOf(currentDate.getYear());

        var currentKey = day + "." + month + "." + year;
        var manager = AnizottiEconomy_EXMPL.dataManager;

        try {
            var value = manager.getRecord(currentKey);
            if (value == null) manager.addRecord(currentKey, earns.getAmount() + "");
            else  manager.setRecord(currentKey, (Integer.parseInt(value.z) + earns.getAmount()) + "");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
