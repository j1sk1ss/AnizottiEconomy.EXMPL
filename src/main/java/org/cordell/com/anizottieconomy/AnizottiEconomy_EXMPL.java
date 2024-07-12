package org.cordell.com.anizottieconomy;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.cordell.com.anizottieconomy.db.Prices;
import org.cordell.com.anizottieconomy.listeners.CommandListener;
import org.cordell.com.anizottieconomy.listeners.TradeListener;
import org.cordell.com.cordelldb.manager.Manager;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;


public final class AnizottiEconomy_EXMPL extends JavaPlugin {
    public static Manager dataManager;

    @Override
    public void onEnable() {
        dataManager = new Manager("", "anizottieconomy.txt");
        Prices.UpdatePrices();

        for (var listener : List.of(new TradeListener()))
            Bukkit.getPluginManager().registerEvents(listener, this);

        var command_manager = new CommandListener();
        for (var command : Arrays.asList("buy", "g2c", "g2s", "fp", "sp", "price_calculate", "info"))
            Objects.requireNonNull(getCommand(command)).setExecutor(command_manager);
    }

    @Override
    public void onDisable() { }
}
