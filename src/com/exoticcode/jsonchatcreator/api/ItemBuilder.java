package com.exoticcode.jsonchatcreator.api;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemBuilder {

	private ItemStack item;

	public ItemBuilder(Material mat) {
		this(mat, (byte) 0);
	}

	public ItemBuilder(Material mat, byte damage) {
		item = new ItemStack(mat, 1, damage);
	}

	public ItemBuilder name(String name) {
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
		item.setItemMeta(meta);
		return this;
	}

	public ItemBuilder lore(String... lore) {
		ItemMeta meta = item.getItemMeta();
		List<String> loreList = new ArrayList<>();
		for (int i = 0; i < lore.length; i++)
			loreList.set(i, ChatColor.translateAlternateColorCodes('&', lore[i]));
		meta.setLore(loreList);
		item.setItemMeta(meta);
		return this;
	}

	public ItemStack build() {
		return item.clone();
	}

}
