package org.cordell.com.anizottieconomy.listeners;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.cordell.com.anizottieconomy.db.Prices;
import org.cordell.com.cordelldb.common.Tuple;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.util.Dictionary;
import java.util.Hashtable;


public class CommandListener implements CommandExecutor {
    public CommandListener(){
        Locations = new Hashtable<>();
    }

    private final Dictionary<Player, Tuple<Location, Location>> Locations;

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        var player = (Player) commandSender;
        var previous = Locations.get(player);

        switch (command.getName()) {
            case "fp":
                if (previous == null) {
                    Locations.put(player, new Tuple<>(player.getLocation(), null));
                    break;
                }

                Locations.put(player, new Tuple<>(player.getLocation(), previous.y));
                break;

            case "sp":
                if (previous == null) {
                    Locations.put(player, new Tuple<>(null, player.getLocation()));
                    break;
                }

                Locations.put(player, new Tuple<>(previous.x, player.getLocation()));
                break;

            case "info":
                player.sendMessage(
                        "Inflation: " + Prices.Inflation + "%\n",
                        "Weeks: " + Prices.GetWeeksDuration(LocalDate.of(2023, 5, 20)) + "\n"
                );
                break;

            case "price_calculate":
                if (previous == null) break;

                var firstLocation = Locations.get(player).x;
                var secondLocation = Locations.get(player).y;
                if (firstLocation == null || secondLocation == null) break;

                var totalCost = 0d;
                var world = player.getWorld();

                var minX = Math.min(firstLocation.getBlockX(), secondLocation.getBlockX());
                var minY = Math.min(firstLocation.getBlockY(), secondLocation.getBlockY());
                var minZ = Math.min(firstLocation.getBlockZ(), secondLocation.getBlockZ());
                var maxX = Math.max(firstLocation.getBlockX(), secondLocation.getBlockX());
                var maxY = Math.max(firstLocation.getBlockY(), secondLocation.getBlockY());
                var maxZ = Math.max(firstLocation.getBlockZ(), secondLocation.getBlockZ());

                for (int x = minX; x <= maxX; x++) {
                    for (int y = minY; y <= maxY; y++) {
                        for (int z = minZ; z <= maxZ; z++) {
                            var block = world.getBlockAt(x, y, z);
                            var material = block.getType();
                            var price = org.cordell.com.anizottieconomy.db.Prices.GetPrice(material);

                            totalCost += price;
                        }
                    }
                }

                player.sendMessage("Total cost of the area: " + totalCost + " HAD");
                break;
        }

        return true;
    }
}
