package com.exoticcode.jsonchatcreator.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.exoticcode.jsonchatcreator.api.chat.ClickEvent;
import com.exoticcode.jsonchatcreator.api.chat.ClickEvent.ClickAction;
import com.exoticcode.jsonchatcreator.api.chat.HoverEvent;
import com.exoticcode.jsonchatcreator.api.chat.HoverEvent.HoverAction;
import com.exoticcode.jsonchatcreator.api.chat.JsonColor;
import com.exoticcode.jsonchatcreator.api.chat.TextComponent;

public class CmdSend extends Command {

	public CmdSend() {
		super("send", "jsonmessage.send", false,
				new TextComponent("/jsonmessage send <player> <key of message>", JsonColor.GREEN)
						.setHoverEvent(new HoverEvent(HoverAction.SHOW_TEXT, "Sends a message to a specified player."))
						.setClickEvent(new ClickEvent(ClickAction.SUGGEST_COMMAND, "/jsonmessage send player key")),
				2, "s", "snd", "sed");
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		Player from = (Player) sender;
		Player to = Bukkit.getPlayerExact(args[0]);
		if (to == null) {
			from.sendMessage(getMessage("invalid-player", args[0]));
			return;
		}
		StringBuilder compiler = new StringBuilder();
		for (int i = 1; i < args.length; i++)
			compiler.append(args[i] + " ");
		String key = compiler.deleteCharAt(compiler.length() - 1).toString();
		String message = getApi().getMessagesConfig().getString(from.getUniqueId().toString() + "." + key);
		if (message == null) {
			from.sendMessage(getMessage("invalid-message", key));
			return;
		}
		getApi().sendJsonMessage(to, message);
	}

}
