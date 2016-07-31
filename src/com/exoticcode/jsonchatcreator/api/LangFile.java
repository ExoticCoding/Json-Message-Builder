package com.exoticcode.jsonchatcreator.api;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;

public class LangFile {

	private final FileConfiguration config;

	public LangFile(FileConfiguration config) {
		this.config = config;
	}

	public String getMessage(String key) {
		return ChatColor.translateAlternateColorCodes('&', config.getString(key, "Invalid message at: &c" + key));
	}

	public String getMessage(String key, Object... args) {
		String message = getMessage(key);
		for (int i = 0; i < args.length; i++) {
			message = message.replace("{" + i + "}", String.valueOf(args[i]));
		}
		return message;
	}

}
