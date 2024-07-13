package org.cordell.com.anizottieconomy.db;

import org.bukkit.Material;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;


public class Prices {
    public static Map<Material, Double> Prices = new HashMap<>();

    public static Double Inflation = 0d;

    public static int GetWeeksDuration(LocalDate startDate) {
        var currentDate = LocalDate.now();
        return (int) ChronoUnit.WEEKS.between(startDate, currentDate);
    }

    public static void UpdateInflation() {
        Inflation = 1 - (1 * Math.pow((1 - 0.05), GetWeeksDuration(LocalDate.of(2023, 5, 20))));
    }

    public static void UpdatePrices() {
        UpdateInflation();
        Prices.clear();

        // Камень
        Prices.put(Material.COBBLESTONE, (1 + 1 * Inflation) / 8);
        Prices.put(Material.STONE, (1 + 1 * Inflation) / 8);
        Prices.put(Material.SMOOTH_STONE, (1 + 1 * Inflation) / 8);

        // Древесина
        Prices.put(Material.ACACIA_WOOD, (1 + 1 * Inflation) / 8);
        Prices.put(Material.BIRCH_WOOD, (1 + 1 * Inflation) / 8);
        Prices.put(Material.CHERRY_WOOD, (1 + 1 * Inflation) / 8);
        Prices.put(Material.DARK_OAK_WOOD, (1 + 1 * Inflation) / 4);
        Prices.put(Material.JUNGLE_WOOD, (1 + 1 * Inflation) / 8);
        Prices.put(Material.MANGROVE_WOOD, (1 + 1 * Inflation) / 8);
        Prices.put(Material.OAK_WOOD, (1 + 1 * Inflation) / 8);
        Prices.put(Material.SPRUCE_WOOD, (1 + 1 * Inflation) / 8);

        // Металлы
        Prices.put(Material.IRON_BLOCK, (9 + 9 * Inflation) / 3);
        Prices.put(Material.IRON_INGOT, (1 + 1 * Inflation) / 3);

        Prices.put(Material.COPPER_BLOCK, (9 + 9 * Inflation) / 3);
        Prices.put(Material.COPPER_INGOT, (1 + 1 * Inflation) / 3);

        Prices.put(Material.GOLD_BLOCK, (9 + 9 * Inflation) / 3);
        Prices.put(Material.GOLD_INGOT, (1 + 1 * Inflation) / 3);

        // Алмазы
        Prices.put(Material.DIAMOND_BLOCK, 9 + 9 * Inflation);
        Prices.put(Material.DIAMOND, 1 + 1 * Inflation);

        // Песок
        Prices.put(Material.SAND, (1 + 1 * Inflation) / 12);

        // Стекло
        Prices.put(Material.GLASS, (1 + 1 * Inflation) / 4);
        Prices.put(Material.GLASS_PANE, (1 + 1 * Inflation) / 4);

        // Слизь
        Prices.put(Material.SLIME_BLOCK, (36 + 36 * Inflation) / 9);
        Prices.put(Material.SLIME_BALL, (4 + 4 * Inflation) / 9);
    }

    public static Double GetPrice(Material material) {
        var price = Prices.get(material);
        if (price == null) return 0.5;
        return price;
    }
}
