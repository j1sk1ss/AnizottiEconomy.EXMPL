package org.cordell.com.anizottieconomy;

import org.bukkit.plugin.java.JavaPlugin;
import org.cordell.com.anizottieconomy.db.DataManager;
import org.cordell.com.anizottieconomy.db.Prices;
import org.cordell.com.anizottieconomy.listeners.CommandListener;

import java.util.Arrays;
import java.util.Objects;

public final class AnizottiEconomy_EXMPL extends JavaPlugin {
    public static DataManager dataManager;

    @Override
    public void onEnable() {
        dataManager = new DataManager("", "anizottieconomy.txt");
        Prices.UpdatePrices();

        var command_manager = new CommandListener();
        for (var command : Arrays.asList("buy", "g2c", "g2s", "fp", "sp", "price_calculate", "info"))
            Objects.requireNonNull(getCommand(command)).setExecutor(command_manager);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
