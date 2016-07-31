package com.exoticcode.jsonchatcreator.api;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.exoticcode.jsonchatcreator.JsonChat;
import com.exoticcode.jsonchatcreator.api.chat.ChatBuilder;
import com.exoticcode.jsonchatcreator.api.chat.ClickEvent;
import com.exoticcode.jsonchatcreator.api.chat.ClickEvent.ClickAction;
import com.exoticcode.jsonchatcreator.api.chat.HoverEvent;
import com.exoticcode.jsonchatcreator.api.chat.HoverEvent.HoverAction;
import com.exoticcode.jsonchatcreator.api.chat.JsonColor;
import com.exoticcode.jsonchatcreator.api.chat.TextComponent;

public class GUISet {

	private API api;
	private ChatBuilder builder;
	private GUIState state;
	private Player player;
	private boolean safe = false;

	public GUISet(Player player, ChatBuilder builder) {
		this.api = JsonChat.getApi();
		this.builder = builder;
		this.state = GUIState.MAIN;
		this.player = player;
	}

	public boolean isSafe() {
		return safe;
	}

	public Inventory getClickInventory() {
		ClickEvent event = builder.getCurrent().getClickEvent();
		if (event == null)
			event = new ClickEvent(ClickAction.SUGGEST_COMMAND).setValue("null");
		builder.getCurrent().setClickEvent(event);
		Inventory from = api.getInventories().get("clickEvent");
		Inventory to = Bukkit.createInventory(null, 27, from.getTitle());
		to.setContents(from.getContents());
		ItemStack textItem = to.getItem(15);
		ItemMeta textItemMeta = textItem.getItemMeta();
		textItemMeta.setLore(Arrays.asList(event.getValue()));
		textItem.setItemMeta(textItemMeta);
		to.setItem(15, textItem);
		switch (event.getAction()) {
		case OPEN_FILE:
			to.getItem(9).setDurability((short) 5);
			break;
		case OPEN_URL:
			to.getItem(10).setDurability((short) 5);
			break;
		case RUN_COMMAND:
			to.getItem(11).setDurability((short) 5);
			break;
		case SUGGEST_COMMAND:
			to.getItem(12).setDurability((short) 5);
			break;
		}
		return to;
	}

	public Inventory getHoverInventory() {
		HoverEvent event = builder.getCurrent().getHoverEvent();
		if (event == null)
			event = new HoverEvent(HoverAction.SHOW_TEXT).setValue("null");
		builder.getCurrent().setHoverEvent(event);
		Inventory from = api.getInventories().get("hoverEvent");
		Inventory to = Bukkit.createInventory(null, 27, from.getTitle());
		to.setContents(from.getContents());
		ItemStack textItem = to.getItem(15);
		ItemMeta textItemMeta = textItem.getItemMeta();
		textItemMeta.setLore(Arrays.asList(event.getValue()));
		textItem.setItemMeta(textItemMeta);
		to.setItem(15, textItem);
		switch (event.getAction()) {
		case SHOW_TEXT:
			to.getItem(11).setDurability((short) 5);
			break;
		}
		return to;
	}

	public Inventory getMainInventory() {
		TextComponent component = builder.getCurrent();
		Inventory from = api.getInventories().get("main");
		Inventory base = Bukkit.createInventory(null, 27, from.getTitle());
		base.setContents(from.getContents());
		if (component.getText() != null) {
			ItemStack textStack = base.getItem(0);
			ItemMeta meta = textStack.getItemMeta();
			meta.setLore(Arrays.asList(ChatColor.WHITE + component.getText()));
			textStack.setItemMeta(meta);
			base.setItem(0, textStack);
		}
		if (component.getColor() != null) {
			ItemStack colorStack = base.getItem(1);
			ItemMeta meta = colorStack.getItemMeta();
			meta.setLore(Arrays.asList(ChatColor.WHITE + component.getColor().toString()));
			colorStack.setItemMeta(meta);
			base.setItem(1, colorStack);
		}
		if (component.getHoverEvent() != null) {
			ItemStack hoverStack = base.getItem(2);
			ItemMeta meta = hoverStack.getItemMeta();
			HoverEvent event = component.getHoverEvent();
			meta.setLore(
					Arrays.asList(ChatColor.WHITE + event.getAction().toString(), ChatColor.WHITE + event.getValue()));
			hoverStack.setItemMeta(meta);
			base.setItem(2, hoverStack);
		}
		if (component.getClickEvent() != null) {
			ItemStack clickStack = base.getItem(3);
			ItemMeta meta = clickStack.getItemMeta();
			ClickEvent event = component.getClickEvent();
			meta.setLore(
					Arrays.asList(ChatColor.WHITE + event.getAction().toString(), ChatColor.WHITE + event.getValue()));
			clickStack.setItemMeta(meta);
			base.setItem(3, clickStack);
		}
		if (component.isBold()) {
			base.getItem(22).setDurability((short) 5);
		}
		if (component.isItalic()) {
			base.getItem(23).setDurability((short) 5);
		}
		if (component.isUnderlined()) {
			base.getItem(24).setDurability((short) 5);
		}
		if (component.isStrikethrough()) {
			base.getItem(25).setDurability((short) 5);
		}
		if (component.isObfuscated()) {
			base.getItem(26).setDurability((short) 5);
		}
		return base;
	}

	public void addClickText(String text) {
		this.builder.getCurrent().getClickEvent().setValue(text);
		player.openInventory(getClickInventory());
		this.safe = false;
		this.state = GUIState.CLICK_EVENT;
	}

	public void addHoverText(String text) {
		this.builder.getCurrent().getHoverEvent().setValue(text);
		player.openInventory(getHoverInventory());
		this.safe = false;
		this.state = GUIState.HOVER_EVENT;
	}

	public void addMainText(String text) {
		builder.getCurrent().setText(text);
		safe = false;
		player.openInventory(getMainInventory());
		this.state = GUIState.MAIN;
	}

	public void doClickClick(int slot) {
		Inventory inventory = player.getOpenInventory().getTopInventory();
		int i = 9;
		switch (slot) {
		case 9:
			while (i < 13) {
				inventory.getItem(i).setDurability((short) 14);
				i++;
			}
			inventory.getItem(slot).setDurability((short) 5);
			builder.getCurrent().getClickEvent().setAction(ClickAction.OPEN_URL);
			break;
		case 10:
			while (i < 13) {
				inventory.getItem(i).setDurability((short) 14);
				i++;
			}
			inventory.getItem(slot).setDurability((short) 5);
			builder.getCurrent().getClickEvent().setAction(ClickAction.OPEN_FILE);
			break;
		case 11:
			while (i < 13) {
				inventory.getItem(i).setDurability((short) 14);
				i++;
			}
			inventory.getItem(slot).setDurability((short) 5);
			builder.getCurrent().getClickEvent().setAction(ClickAction.RUN_COMMAND);
			break;
		case 12:
			while (i < 13) {
				inventory.getItem(i).setDurability((short) 14);
				i++;
			}
			inventory.getItem(slot).setDurability((short) 5);
			builder.getCurrent().getClickEvent().setAction(ClickAction.SUGGEST_COMMAND);
			break;
		case 15:
			askForText();
			this.state = GUIState.CLICK_EVENT_TEXT;
			break;
		case 22:
			player.openInventory(getMainInventory());
			this.state = GUIState.MAIN;
			break;
		}
	}

	public void doHoverClick(int slot) {
		switch (slot) {
		case 15:
			askForText();
			this.state = GUIState.HOVER_EVENT_TEXT;
			break;
		case 22:
			player.openInventory(getMainInventory());
			this.state = GUIState.MAIN;
			break;
		}
	}

	public void askForText() {
		player.sendMessage(ChatColor.RED + "Please type the text you'd like in chat.");
		safe = true;
		player.closeInventory();
	}

	public void doColorClick(int slot) {
		JsonColor color = JsonColor.getBySlot(slot);
		if (color != null) {
			builder.getCurrent().setColor(color);
			player.openInventory(getMainInventory());
			this.state = GUIState.MAIN;
		} else if (slot == 26) {
			player.openInventory(getMainInventory());
			this.state = GUIState.MAIN;
		}
	}

	public void doMainClick(int slot) {
		TextComponent current = builder.getCurrent();
		switch (slot) {
		case 0:
			this.state = GUIState.TEXT;
			askForText();
			return;
		case 1:
			this.state = GUIState.COLOR;
			player.openInventory(api.getInventories().get("color"));
			return;
		case 2:
			this.state = GUIState.HOVER_EVENT;
			player.openInventory(getHoverInventory());
			return;
		case 3:
			this.state = GUIState.CLICK_EVENT;
			player.openInventory(getClickInventory());
			return;
		case 22:
			current.setBold(!current.isBold());
			player.getOpenInventory().getItem(22).setDurability(current.isBold() ? (short) 5 : (short) 14);
			break;
		case 23:
			current.setItalic(!current.isItalic());
			player.getOpenInventory().getItem(23).setDurability(current.isItalic() ? (short) 5 : (short) 14);
			break;
		case 24:
			current.setUnderlined(!current.isUnderlined());
			player.getOpenInventory().getItem(24).setDurability(current.isUnderlined() ? (short) 5 : (short) 14);
			break;
		case 25:
			current.setStrikethrough(!current.isStrikethrough());
			player.getOpenInventory().getItem(25).setDurability(current.isStrikethrough() ? (short) 5 : (short) 14);
			break;
		case 26:
			current.setObfuscated(!current.isObfuscated());
			player.getOpenInventory().getItem(26).setDurability(current.isObfuscated() ? (short) 5 : (short) 14);
			break;
		case 18:
			next();
			player.openInventory(getMainInventory());
			return;
		case 19:
			this.state = GUIState.FINISHING;
			player.sendMessage(ChatColor.GREEN + "Please type the message key for usage.");
			safe = true;
			player.closeInventory();
			return;
		}
		builder.setCurrent(current);
	}

	public void next() {
		this.builder.next();
	}

	public ChatBuilder getBuilder() {
		return builder;
	}

	public GUIState getState() {
		return state;
	}

	public void setBuilder(ChatBuilder builder) {
		this.builder = builder;
	}

	public void setState(GUIState state) {
		this.state = state;
	}

	public enum GUIState {
		MAIN, COLOR, HOVER_EVENT, CLICK_EVENT, TEXT, HOVER_EVENT_TEXT, CLICK_EVENT_TEXT, FINISHING
	}

}
