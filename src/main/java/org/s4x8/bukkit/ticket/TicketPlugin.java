
package org.s4x8.bukkit.ticket;

import org.bukkit.DyeColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.Material;
import org.bukkit.plugin.java.JavaPlugin;

import org.s4x8.bukkit.ticket.chip.TicketChipIndex;

import org.tal.redstonechips.RedstoneChips;

import java.util.ArrayList;

public class TicketPlugin extends JavaPlugin {
	private void registerCrafting() {
		ItemStack result = (new TicketStack()).getItemStack();
		ShapelessRecipe recipe = new ShapelessRecipe(result);
		recipe.addIngredient(Material.INK_SACK, DyeColor.RED.getDyeData());
		recipe.addIngredient(Material.PAPER);
		getServer().addRecipe(recipe);
	};

	private void registerCommands() {
		(new TicketMarkCommand(this)).register();
	};
	
	private void registerEvents() {
		new TicketBuildBlocker(this);
	};
	
	private void registerChip() {
		RedstoneChips.addCircuitLibrary(new TicketChipIndex());
	};
	
	public void onLoad() {
		registerChip();
	};
	
	public void onEnable() {
		registerEvents();
		registerCrafting();
		registerCommands();
	};
};
