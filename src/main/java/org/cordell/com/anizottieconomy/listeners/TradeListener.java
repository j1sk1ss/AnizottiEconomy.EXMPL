package org.cordell.com.anizottieconomy.listeners;

import com.google.common.collect.Lists;
import io.papermc.paper.event.player.PlayerTradeEvent;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.VillagerAcquireTradeEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.MerchantRecipe;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.cordell.com.anizottieconomy.AnizottiEconomy_EXMPL;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;


public class TradeListener implements Listener {
    @EventHandler
    public void onTrade(PlayerTradeEvent event) {
        var earns = event.getTrade().getResult();
        if (earns.getType() != Material.EMERALD) return;

        var currentDate = LocalDate.now();
        var day   = String.valueOf(currentDate.getDayOfMonth());
        var month = String.valueOf(currentDate.getMonthValue());
        var year  = String.valueOf(currentDate.getYear());

        var currentKey = day + "." + month + "." + year;
        var manager = AnizottiEconomy_EXMPL.dataManager;

        try {
            var value = manager.getRecord(currentKey);
            if (value == null) manager.setInt(currentKey, earns.getAmount());
            else  manager.setInt(currentKey, value.z.asInteger() + earns.getAmount());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @EventHandler
    public void onVillagerAcquireTrade(VillagerAcquireTradeEvent event) {
        var trade = event.getRecipe();
        var ingredients = trade.getIngredients();

        for (var i = 0; i < ingredients.size(); i++) {
            if (ingredients.get(i).getType() == Material.EMERALD) {
                var newIngredient = new ItemStack(Material.EMERALD_BLOCK, ingredients.get(i).getAmount());
                ingredients.set(i, newIngredient);

                var newTrade = new MerchantRecipe(trade.getResult(), trade.getMaxUses());
                newTrade.setIngredients(ingredients);

                event.setRecipe(newTrade);
            }
        }
    }

    @EventHandler
    public void onPlayerInteractWithVillager(PlayerInteractEntityEvent event) {
        if (event.getRightClicked().getType() == EntityType.VILLAGER) {
            var villager = (Villager) event.getRightClicked();
            var recipes = villager.getRecipes();
            var modifiedRecipes = new ArrayList<MerchantRecipe>();

            for (var recipe : recipes) {
                var result = recipe.getResult();
                var ingredients = recipe.getIngredients();
                var newIngredients = new ArrayList<ItemStack>();

                if (result.getType() == Material.EMERALD) result = new ItemStack(Material.EMERALD_BLOCK, result.getAmount());
                for (var ingredient : ingredients) {
                    if (ingredient != null && ingredient.getType() == Material.EMERALD) newIngredients.add(new ItemStack(Material.EMERALD_BLOCK, ingredient.getAmount()));
                    else newIngredients.add(ingredient);
                }

                var modifiedRecipe = new MerchantRecipe(result, recipe.getMaxUses());
                modifiedRecipe.setIngredients(newIngredients);
                modifiedRecipe.setVillagerExperience(recipe.getVillagerExperience());
                modifiedRecipe.setPriceMultiplier(recipe.getPriceMultiplier());
                modifiedRecipes.add(modifiedRecipe);
            }

            villager.setRecipes(modifiedRecipes);
        }
    }
}
