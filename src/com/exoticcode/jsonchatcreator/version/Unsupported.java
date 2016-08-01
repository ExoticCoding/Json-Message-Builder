package com.exoticcode.jsonchatcreator.version;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;

public class Unsupported implements Version {

	private final JavaPlugin plugin;
	private final String version;
	private Method serializerInvoker;
	private Method handleRetriever;
	private Method sendMessage;

	public Unsupported(JavaPlugin plugin, String version) {
		this.version = version;
		this.plugin = plugin;
		try {
			serializerInvoker = getNMSClass("IChatBaseComponent$ChatSerializer").getMethod("a", String.class);
			handleRetriever = getCraftClass("entity.CraftPlayer").getMethod("getHandle");
			sendMessage = getNMSClass("EntityPlayer").getMethod("sendMessage", getNMSClass("IChatBaseComponent"));
		} catch (NoSuchMethodException | SecurityException e) {
			plugin.getLogger()
					.warning("Fatal error, your version is not supported! Please contact the author for help.");
			Bukkit.getPluginManager().disablePlugin(plugin);
			return;
		}
	}

	@Override
	public void broadcastJsonMessage(Set<Player> players, String text) {
		players.forEach(player -> sendJsonMessage(player, text));
	}

	@Override
	public void sendJsonMessage(Player player, String text) {
		try {
			sendMessage.invoke(handleRetriever.invoke(player), serializerInvoker.invoke(null, text));
		} catch (IllegalAccessException | InvocationTargetException e) {
			plugin.getLogger()
					.warning("Fatal error, your version is not supported! Please contact the author for help.");
			Bukkit.getPluginManager().disablePlugin(plugin);
			return;
		}
	}

	@Override
	public boolean canHandleAnvilGUI() {
		return false;
	}

	@Override
	public int getNextContainerId(Player player) {
		return 0;
	}

	@Override
	public void handleInventoryCloseEvent(Player player) {
	}

	@Override
	public void sendPacketOpenWindow(Player player, int containerId) {
	}

	@Override
	public void setActiveContainerDefault(Player player) {
	}

	@Override
	public void setActiveContainer(Player player, Object container) {
	}

	@Override
	public void setActiveContainerId(Object container, int containerId) {
	}

	@Override
	public void addActiveContainerSlotListener(Object container, Player player) {
	}

	@Override
	public Inventory toBukkitInventory(Object container) {
		return null;
	}

	@Override
	public Object newContainerAnvil(Player player) {
		return null;
	}

	private Class<?> getCraftClass(String clazz) {
		try {
			return Class.forName("org.bukkit.craftbukkit." + version + "." + clazz);
		} catch (ClassNotFoundException ex) {
			plugin.getLogger()
					.warning("Fatal error, your version is not supported! Please contact the author for help.");
			Bukkit.getPluginManager().disablePlugin(plugin);
			return null;
		}
	}

	private Class<?> getNMSClass(String clazz) {
		try {
			return Class.forName("net.minecraft.server." + version + "." + clazz);
		} catch (ClassNotFoundException ex) {
			plugin.getLogger()
					.warning("Fatal error, your version is not supported! Please contact the author for help.");
			Bukkit.getPluginManager().disablePlugin(plugin);
			return null;
		}
	}

}
