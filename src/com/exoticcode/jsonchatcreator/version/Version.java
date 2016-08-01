package com.exoticcode.jsonchatcreator.version;

import java.util.Set;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public interface Version {

	void sendJsonMessage(Player player, String text);

	void broadcastJsonMessage(Set<Player> players, String text);

	boolean canHandleAnvilGUI();

	int getNextContainerId(Player player);

	void sendPacketOpenWindow(Player player, int containerId);

	void handleInventoryCloseEvent(Player player);

	void setActiveContainerDefault(Player player);

	void setActiveContainer(Player player, Object container);

	void setActiveContainerId(Object container, int containerId);

	void addActiveContainerSlotListener(Object container, Player player);

	Inventory toBukkitInventory(Object container);

	Object newContainerAnvil(Player player);

}
