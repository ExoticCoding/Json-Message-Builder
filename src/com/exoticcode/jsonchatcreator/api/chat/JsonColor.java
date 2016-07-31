package com.exoticcode.jsonchatcreator.api.chat;

import org.bukkit.ChatColor;

public enum JsonColor {

	BLACK(0, ChatColor.BLACK, "Black"),
	DARK_BLUE(1, ChatColor.DARK_BLUE, "Dark Blue"),
	DARK_GREEN(2, ChatColor.DARK_GREEN, "Dark Green"),
	DARK_AQUA(3, ChatColor.DARK_AQUA, "Dark Aqua"),
	DARK_RED(4, ChatColor.DARK_RED, "Dark Red"),
	DARK_PURPLE(5, ChatColor.DARK_PURPLE, "Dark Purple"),
	GOLD(6, ChatColor.GOLD, "Gold"),
	GRAY(7, ChatColor.GRAY, "Gray"),
	DARK_GRAY(8, ChatColor.DARK_GRAY, "Dark Gray"),
	BLUE(9, ChatColor.BLUE, "Blue"),
	GREEN(10, ChatColor.GREEN, "Green"),
	AQUA(11, ChatColor.AQUA, "Aqua"),
	RED(12, ChatColor.RED, "Red"),
	LIGHT_PURPLE(13, ChatColor.LIGHT_PURPLE, "Light Purple"),
	YELLOW(14, ChatColor.YELLOW, "Yellow"),
	WHITE(15, ChatColor.WHITE, "None");

	private final int slot;
	private final ChatColor color;
	private final String name;

	private JsonColor(int slot, ChatColor color, String name) {
		this.slot = slot;
		this.color = color;
		this.name = name;
	}

	public int slot() {
		return slot;
	}

	public String jName() {
		return name;
	}

	public ChatColor color() {
		return color;
	}

	@Override
	public String toString() {
		return jName().toLowerCase().replace(' ', '_');
	}

	public static JsonColor getBySlot(int slot) {
		for (JsonColor color : JsonColor.values())
			if (color.slot == slot)
				return color;
		return null;
	}

}
