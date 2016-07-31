package com.exoticcode.jsonchatcreator.version;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class Unsupported implements Version {

	private final JavaPlugin plugin;
	private final String version;
	private Constructor<?> packetCreator;
	private Method serializerInvoker;
	private Method handleRetriever;
	private Method sendPacket;
	private Field playerConnectionField;

	public Unsupported(JavaPlugin plugin, String version) {
		this.version = version;
		this.plugin = plugin;
		try {
			packetCreator = getNMSClass("PacketPlayOutChat").getConstructor(getNMSClass("IChatBaseComponent"));
			serializerInvoker = getNMSClass("IChatBaseComponent$ChatSerializer").getMethod("a", String.class);
			handleRetriever = getCraftClass("entity.CraftPlayer").getMethod("getHandle");
			playerConnectionField = getNMSClass("EntityPlayer").getField("playerConnection");
			sendPacket = getNMSClass("PlayerConnection").getMethod("sendPacket", getNMSClass("Packet"));
		} catch (NoSuchMethodException | SecurityException | NoSuchFieldException e) {
			plugin.getLogger()
					.warning("Fatal error, your version is not supported! Please contact the author for help.");
			Bukkit.getPluginManager().disablePlugin(plugin);
			return;
		}
	}

	@Override
	public void broadcastJsonMessage(Set<Player> players, String text) {
		try {
			Object packet = packetCreator.newInstance(serializerInvoker.invoke(null, text));
			for (Player player : players) {
				Object playerConnection = playerConnectionField.get(handleRetriever.invoke(player));
				sendPacket.invoke(playerConnection, packet);
			}
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			plugin.getLogger()
					.warning("Fatal error, your version is not supported! Please contact the author for help.");
			Bukkit.getPluginManager().disablePlugin(plugin);
			return;
		}
	}

	@Override
	public void sendJsonMessage(Player player, String text) {
		try {
			Object packet = packetCreator.newInstance(serializerInvoker.invoke(null, text));
			Object playerConnection = playerConnectionField.get(handleRetriever.invoke(player));
			sendPacket.invoke(playerConnection, packet);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			plugin.getLogger()
					.warning("Fatal error, your version is not supported! Please contact the author for help.");
			Bukkit.getPluginManager().disablePlugin(plugin);
			return;
		}
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
