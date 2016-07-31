package com.exoticcode.jsonchatcreator;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import com.exoticcode.jsonchatcreator.api.API;
import com.exoticcode.jsonchatcreator.commands.Command;

public class JsonChat extends JavaPlugin {

	private static API api = null;

	@Override
	public void onEnable() {
		try {
			api = API.getAPI(this);
		} catch (IllegalAccessException e) {
			getLogger().warning("API already loaded, please use the integrated reload command");
			return;
		}
		api.load();
		Bukkit.getPluginManager().registerEvents(new MainHandler(), this);
		Command.register();
	}

	@Override
	public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {
		if (args.length == 0 || !Command.runArgs(sender, args)) {
			Bukkit.dispatchCommand(sender, "jsonmessage help");
		}
		return true;
	}

	public static API getApi() {
		return api;
	}

}
