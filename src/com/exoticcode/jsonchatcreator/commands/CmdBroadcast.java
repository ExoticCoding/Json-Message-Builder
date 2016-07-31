package com.exoticcode.jsonchatcreator.commands;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.exoticcode.jsonchatcreator.api.chat.ClickEvent;
import com.exoticcode.jsonchatcreator.api.chat.ClickEvent.ClickAction;
import com.exoticcode.jsonchatcreator.api.chat.HoverEvent;
import com.exoticcode.jsonchatcreator.api.chat.HoverEvent.HoverAction;
import com.exoticcode.jsonchatcreator.api.chat.JsonColor;
import com.exoticcode.jsonchatcreator.api.chat.TextComponent;

public class CmdBroadcast extends Command {

	public CmdBroadcast() {
		super("broadcast", "jsonmessage.broadcast", false,
				new TextComponent("/jsonmessage broadcast <key> [permission]", JsonColor.GREEN)
						.setHoverEvent(new HoverEvent(HoverAction.SHOW_TEXT,
								"Broadcasts a message to the server."
										+ "\nIf there is a permission use \\\"broadcast key perm:permission\\\""))
						.setClickEvent(new ClickEvent(ClickAction.SUGGEST_COMMAND, "/jsonmessage broadcast key")),
				1, "bc", "broadc", "bcast", "brodcast", "brodcst");
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		Player player = (Player) sender;
		if (args[args.length - 1].startsWith("perm:")) {
			if (args.length == 1) {
				sender.sendMessage(getMessage("empty-key"));
				return;
			}
			String permission = args[args.length - 1].substring(5);
			String key = compileKey(args, args.length - 1);
			String message = getApi().getMessagesConfig().getString(player.getUniqueId().toString() + "." + key);
			if (checkMessage(player, message, key)) {
				broadcast(message, true, permission);
			}
			return;
		}
		String key = compileKey(args, args.length);
		String message = getApi().getMessagesConfig().getString(player.getUniqueId().toString() + "." + key);
		if (checkMessage(player, message, key)) {
			broadcast(message, false, null);
		}
	}

	public boolean checkMessage(Player player, String message, String key) {
		if (message == null) {
			player.sendMessage(getMessage("invalid-message", key));
			return false;
		}
		return true;
	}

	public String compileKey(String[] args, int last) {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < last; i++) {
			builder.append(args[i] + " ");
		}
		return builder.deleteCharAt(builder.length() - 1).toString();
	}

	public void broadcast(String message, boolean permission, String perm) {
		Set<Player> players = new HashSet<Player>();
		for (Player player : Bukkit.getOnlinePlayers()) {
			if (!permission || player.hasPermission(perm)) {
				players.add(player);
			}
		}
		getApi().broadcastJsonMessage(players, message);
	}

}
