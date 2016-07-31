package com.exoticcode.jsonchatcreator.commands;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.exoticcode.jsonchatcreator.JsonChat;
import com.exoticcode.jsonchatcreator.api.API;
import com.exoticcode.jsonchatcreator.api.chat.TextComponent;

public abstract class Command {

	private final static LinkedList<Command> commands = new LinkedList<Command>();
	private final static Map<String, Command> commandMap = new HashMap<String, Command>();

	public static LinkedList<Command> getCommands() {
		return commands;
	}

	private final API api;
	private final String command;
	private final String permission;
	private final boolean canConsoleUse;
	private final int argsReq;
	private final Collection<String> aliases;
	private TextComponent help;

	public Command(String command, String permission, boolean canConsoleUse, TextComponent help, int argsReq,
			String... aliases) {
		api = JsonChat.getApi();
		this.command = command;
		this.permission = permission;
		this.canConsoleUse = canConsoleUse;
		this.help = help;
		this.argsReq = argsReq;
		this.aliases = Arrays.asList(aliases);
		Command.commands.add(this);
		Command.commandMap.put(command, this);
	}

	public TextComponent getHelp() {
		return help;
	}

	public String getMessage(String key) {
		return getApi().getMessage("commands." + command + "." + key);
	}

	public String getMessage(String key, Object... args) {
		return getApi().getMessage("commands." + command + "." + key, args);
	}

	public API getApi() {
		return api;
	}

	public void setHelp(TextComponent help) {
		this.help = help;
	}

	public abstract void execute(CommandSender sender, String[] args);

	public static boolean runArgs(CommandSender sender, String[] args) {
		String cmd = args[0].toLowerCase();
		String[] fixed = Arrays.asList(args).subList(1, args.length).toArray(new String[args.length - 1]);
		if (commandMap.containsKey(cmd)) {
			runCommand(sender, commandMap.get(cmd), fixed);
			return true;
		}
		for (Command command : commands) {
			if (cmd.equalsIgnoreCase(command.command) || command.aliases.contains(cmd)) {
				runCommand(sender, command, fixed);
				return true;
			}
		}
		return false;
	}

	public static void runCommand(CommandSender sender, Command command, String[] fixed) {
		if (!command.canConsoleUse && !(sender instanceof Player)) {
			sender.sendMessage(JsonChat.getApi().getMessage("general.no-player"));
			return;
		}
		if (!sender.hasPermission(command.permission)) {
			sender.sendMessage(JsonChat.getApi().getMessage("general.no-permission"));
			return;
		}
		if (fixed.length < command.argsReq) {
			sender.sendMessage(JsonChat.getApi().getMessage("general.invalid-syntax", command.help.toConsoleString()));
			return;
		}
		command.execute(sender, fixed);
	}

	public static void register() {
		commands.clear();
		commandMap.clear();
		new CmdHelp();
		new CmdCreate();
		new CmdReload();
		new CmdSend();
		new CmdBroadcast();
	}

}
