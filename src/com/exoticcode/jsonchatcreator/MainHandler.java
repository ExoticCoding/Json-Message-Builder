package com.exoticcode.jsonchatcreator;

import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.scheduler.BukkitRunnable;

import com.exoticcode.jsonchatcreator.api.API;
import com.exoticcode.jsonchatcreator.api.GUISet;
import com.exoticcode.jsonchatcreator.api.GUISet.GUIState;

import net.md_5.bungee.api.ChatColor;

public class MainHandler implements Listener {

	@EventHandler
	public void onClick(InventoryClickEvent event) {
		if (JsonChat.getApi().getInMenu().containsKey(event.getWhoClicked().getUniqueId())) {
			Player player = (Player) event.getWhoClicked();
			GUISet set = JsonChat.getApi().getInMenu().get(player.getUniqueId());
			event.setCancelled(true);
			int slot = event.getRawSlot();
			switch (set.getState()) {
			case MAIN:
				set.doMainClick(slot);
				break;
			case CLICK_EVENT:
				set.doClickClick(slot);
				break;
			case COLOR:
				set.doColorClick(slot);
				break;
			case HOVER_EVENT:
				set.doHoverClick(slot);
				break;
			default:
				break;
			}
		}
	}

	@EventHandler
	public void onCommandPreProcess(PlayerCommandPreprocessEvent event) {
		Player player = event.getPlayer();
		String message = event.getMessage();
		API api = JsonChat.getApi();
		if (api.getInMenu().containsKey(player.getUniqueId())) {
			event.setCancelled(true);
			GUISet set = api.getInMenu().get(player.getUniqueId());
			if (set.getState() == GUIState.CLICK_EVENT_TEXT) {
				set.addClickText(message);
			}
		}
	}

	@EventHandler
	public void onChat(AsyncPlayerChatEvent event) {
		Player player = event.getPlayer();
		String message = event.getMessage();
		API api = JsonChat.getApi();
		if (api.getInMenu().containsKey(player.getUniqueId())) {
			event.setCancelled(true);
			GUISet set = api.getInMenu().get(player.getUniqueId());
			switch (set.getState()) {
			case CLICK_EVENT_TEXT:
				set.addClickText(message);
				break;
			case HOVER_EVENT_TEXT:
				set.addHoverText(message);
				break;
			case TEXT:
				set.addMainText(message);
				break;
			case FINISHING:
				String built = set.getBuilder().build();
				api.addBuilder(player, message, built);
				api.getInMenu().remove(player.getUniqueId());
				player.sendMessage(ChatColor.BLUE + "You have saved the message:");
				api.sendJsonMessage(player, built);
				break;
			default:
				break;
			}
		}
	}

	@EventHandler
	public void onDrag(InventoryDragEvent event) {
		if (JsonChat.getApi().getInMenu().containsKey(event.getWhoClicked().getUniqueId()))
			event.setCancelled(true);
	}

	@EventHandler
	public void onClose(final InventoryCloseEvent event) {
		if (JsonChat.getApi().getInMenu().containsKey(event.getPlayer().getUniqueId())) {
			new BukkitRunnable() {
				public void run() {
					HumanEntity player = event.getPlayer();
					GUISet set = JsonChat.getApi().getInMenu().get(player.getUniqueId());
					if (player.getOpenInventory().getType() == InventoryType.CHEST || set.isSafe())
						return;
					JsonChat.getApi().getInMenu().remove(event.getPlayer().getUniqueId());
					player.closeInventory();
				}
			}.runTaskLater(JsonChat.getApi().getPlugin(), 1);
		}
	}

}