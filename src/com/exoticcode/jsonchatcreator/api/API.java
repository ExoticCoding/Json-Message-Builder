package com.exoticcode.jsonchatcreator.api;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;
import com.exoticcode.jsonchatcreator.api.chat.ChatBuilder;
import com.exoticcode.jsonchatcreator.api.chat.JsonColor;
import com.exoticcode.jsonchatcreator.version.Unsupported;
import com.exoticcode.jsonchatcreator.version.V1_10_R1;
import com.exoticcode.jsonchatcreator.version.V1_8_R1;
import com.exoticcode.jsonchatcreator.version.V1_8_R2;
import com.exoticcode.jsonchatcreator.version.V1_8_R3;
import com.exoticcode.jsonchatcreator.version.V1_9_R1;
import com.exoticcode.jsonchatcreator.version.V1_9_R2;
import com.exoticcode.jsonchatcreator.version.Version;

public class API {
	private static API instance = null;

	public static API getAPI(JavaPlugin plugin) throws IllegalAccessException {
		if (instance != null) {
			throw new IllegalAccessException("The API has already been loaded.");
		}
		return (instance = new API(plugin));
	}

	private final Map<UUID, GUISet> inMenu;
	private final Map<String, Inventory> inventories;
	private final JavaPlugin plugin;
	private final Version version;
	private FileConfiguration messagesConfig;
	private LangFile langFile;

	private API(JavaPlugin plugin) {
		this.inMenu = new HashMap<>();
		this.inventories = new HashMap<>();
		this.plugin = plugin;
		String serverVersion = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
		switch (serverVersion) {
		case "v1_10_R1":
			version = new V1_10_R1();
			break;
		case "v1_8_R1":
			version = new V1_8_R1();
			break;
		case "v1_8_R2":
			version = new V1_8_R2();
			break;
		case "v1_8_R3":
			version = new V1_8_R3();
			break;
		case "v1_9_R1":
			version = new V1_9_R1();
			break;
		case "v1_9_R2":
			version = new V1_9_R2();
			break;
		default:
			plugin.getLogger().info("This server's version is unsupported.");
			plugin.getLogger().info("The plugin will attempt to continue but may stop working at any time.");
			version = new Unsupported(plugin, serverVersion);
			return;
		}
	}

	public Version getVersion() {
		return version;
	}

	public void broadcastJsonMessage(Set<Player> players, String serialized) {
		version.broadcastJsonMessage(players, serialized);
	}

	public void sendJsonMessage(Player player, String serialized) {
		version.sendJsonMessage(player, serialized);
	}

	public void openBuilder(Player player) {
		inMenu.put(player.getUniqueId(), new GUISet(player, new ChatBuilder()));
		Inventory base = Bukkit.createInventory(null, 27, ChatColor.DARK_AQUA + "Message Builder");
		base.setContents(inventories.get("main").getContents());
		player.openInventory(base);
	}

	public void addBuilder(Player player, String key, String built) {
		messagesConfig.set(player.getUniqueId().toString() + "." + key, built);
		saveMessagesConfig();
	}

	public void saveMessagesConfig() {
		File file = new File(plugin.getDataFolder().getAbsolutePath(), "messages.yml");
		if (!file.exists())
			plugin.saveResource("messages.yml", false);
		try {
			messagesConfig.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setLangFile(LangFile langFile) {
		this.langFile = langFile;
	}

	public String getMessage(String key) {
		return langFile.getMessage(key);
	}

	public String getMessage(String key, Object... args) {
		return langFile.getMessage(key, args);
	}

	public FileConfiguration getMessagesConfig() {
		return messagesConfig;
	}

	public Map<UUID, GUISet> getInMenu() {
		return inMenu;
	}

	public Map<String, Inventory> getInventories() {
		return inventories;
	}

	public JavaPlugin getPlugin() {
		return plugin;
	}

	public void load() {
		for (UUID id : inMenu.keySet()) {
			Bukkit.getPlayer(id).closeInventory();
		}
		inMenu.clear();
		loadInventories();
		plugin.getDataFolder().mkdirs();
		File file = new File(plugin.getDataFolder().getAbsolutePath(), "messages.yml");
		if (!file.exists())
			plugin.saveResource("messages.yml", false);
		messagesConfig = YamlConfiguration.loadConfiguration(file);
		File langFileRaw = new File(plugin.getDataFolder().getAbsolutePath(), "lang.yml");
		if (!langFileRaw.exists())
			plugin.saveResource("lang.yml", false);
		langFile = new LangFile(YamlConfiguration.loadConfiguration(langFileRaw));
	}

	public void loadInventories() {
		inventories.clear();
		Inventory main = Bukkit.createInventory(null, 27, ChatColor.DARK_AQUA + "Message Builder");
		Inventory colorInv = Bukkit.createInventory(null, 27, ChatColor.DARK_AQUA + "Color Selector");
		Inventory clickEvent = Bukkit.createInventory(null, 27, ChatColor.DARK_AQUA + "Click Event Creator");
		Inventory hoverEvent = Bukkit.createInventory(null, 27, ChatColor.DARK_AQUA + "Hover Event Creator");
		main.setItem(0, new ItemBuilder(Material.BOOK).name("&9Text").build());
		main.setItem(1, new ItemBuilder(Material.BOOK).name("&9Color").build());
		main.setItem(2, new ItemBuilder(Material.BOOK).name("&9Hover Event").build());
		main.setItem(3, new ItemBuilder(Material.BOOK).name("&9Click Event").build());
		main.setItem(18, new ItemBuilder(Material.FEATHER).name("&bNext").build());
		main.setItem(19, new ItemBuilder(Material.NAME_TAG).name("&bCreate").build());
		main.setItem(22, new ItemBuilder(Material.WOOL, (byte) 14).name("&9Bold").build());
		main.setItem(23, new ItemBuilder(Material.WOOL, (byte) 14).name("&9Italic").build());
		main.setItem(24, new ItemBuilder(Material.WOOL, (byte) 14).name("&9Underlined").build());
		main.setItem(25, new ItemBuilder(Material.WOOL, (byte) 14).name("&9Strikethrough").build());
		main.setItem(26, new ItemBuilder(Material.WOOL, (byte) 14).name("&9Obfuscated").build());
		colorInv.setItem(26, new ItemBuilder(Material.BARRIER).name("&cBack").build());
		clickEvent.setItem(9, new ItemBuilder(Material.WOOL, (byte) 14).name("&9Open URL").build());
		clickEvent.setItem(10, new ItemBuilder(Material.WOOL, (byte) 14).name("&9Open File").build());
		clickEvent.setItem(11, new ItemBuilder(Material.WOOL, (byte) 14).name("&9Run Command").build());
		clickEvent.setItem(12, new ItemBuilder(Material.WOOL, (byte) 14).name("&9Suggest Command").build());
		clickEvent.setItem(15, new ItemBuilder(Material.BOOK).name("&9Text").build());
		clickEvent.setItem(22, new ItemBuilder(Material.BARRIER).name("&cBack").build());
		hoverEvent.setItem(11, new ItemBuilder(Material.WOOL, (byte) 14).name("&9Show Text").build());
		hoverEvent.setItem(15, new ItemBuilder(Material.BOOK).name("&9Text").build());
		hoverEvent.setItem(22, new ItemBuilder(Material.BARRIER).name("&cBack").build());
		Arrays.asList(JsonColor.values()).forEach(color -> colorInv.setItem(color.slot(), ItemBuilder.color(color)));
		inventories.put("main", main);
		inventories.put("color", colorInv);
		inventories.put("clickEvent", clickEvent);
		inventories.put("hoverEvent", hoverEvent);
	}
}