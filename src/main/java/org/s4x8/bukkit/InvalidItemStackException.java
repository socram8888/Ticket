
package org.s4x8.bukkit;

import org.bukkit.inventory.ItemStack;

public class InvalidItemStackException extends Exception {
	private ItemStack itemStack;

	public InvalidItemStackException() {
		super("Invalid item stack!");
	};

	public InvalidItemStackException(ItemStack itemStack) {
		super("Invalid item stack: " + itemStack);
		this.itemStack = itemStack;
	};

	public ItemStack getItemStack() {
		return itemStack;
	};
};
