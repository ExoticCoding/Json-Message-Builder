package com.exoticcode.jsonchatcreator.api.anvilgui;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import com.exoticcode.jsonchatcreator.JsonChat;
import com.exoticcode.jsonchatcreator.api.GUISet;
import com.exoticcode.jsonchatcreator.version.Version;

public class AnvilGUI implements Listener {

	private final Player holder;
	private final ItemStack insert;
	private final AnvilRunnable runnable;

	private final Version version;
	private final int containerId;
	private final Inventory inventory;

	private boolean open = false;

	public AnvilGUI(Version version, Plugin plugin, Player holder, String insert, AnvilRunnable runnable) {
		this.holder = holder;
		this.runnable = runnable;

		final ItemStack paper = new ItemStack(Material.PAPER);
		final ItemMeta paperMeta = paper.getItemMeta();
		paperMeta.setDisplayName(insert);
		paper.setItemMeta(paperMeta);
		this.insert = paper;

		this.version = version;

		this.version.handleInventoryCloseEvent(holder);
		this.version.setActiveContainerDefault(holder);

		Bukkit.getPluginManager().registerEvents(this, plugin);

		final Object container = this.version.newContainerAnvil(holder);

		inventory = this.version.toBukkitInventory(container);
		inventory.setItem(Slot.INPUT_LEFT, this.insert);

		containerId = this.version.getNextContainerId(holder);
		this.version.sendPacketOpenWindow(holder, containerId);
		this.version.setActiveContainer(holder, container);
		this.version.setActiveContainerId(container, containerId);
		this.version.addActiveContainerSlotListener(container, holder);

		open = true;
	}

	public void closeInventory() {
		open = false;

		this.version.setActiveContainerDefault(holder);

		HandlerList.unregisterAll(this);
	}

	@EventHandler
	public void onInventoryClick(InventoryClickEvent e) {
		if (e.getInventory().equals(inventory)) {
			e.setCancelled(true);
			if (e.getRawSlot() == Slot.OUTPUT) {
				final ItemStack clicked = inventory.getItem(e.getRawSlot());
				if (!clicked.hasItemMeta() || !clicked.getItemMeta().hasDisplayName())
					return;
				runnable.setText(clicked.getItemMeta().getDisplayName());
				runnable.run();
				closeInventory();
			}
		}
	}

	@EventHandler
	public void onInventoryClose(InventoryCloseEvent e) {
		if (open && e.getInventory().equals(inventory)) {
			GUISet set = JsonChat.getApi().getInMenu().get(e.getPlayer().getUniqueId());
			if (set != null) {
				set.handleAnvilClose();
			}
			closeInventory();
		}
	}

	public class Slot {

		public static final int INPUT_LEFT = 0;
		public static final int OUTPUT = 2;

	}

}
