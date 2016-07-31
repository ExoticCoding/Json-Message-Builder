package com.exoticcode.jsonchatcreator.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.exoticcode.jsonchatcreator.api.chat.ChatBuilder;
import com.exoticcode.jsonchatcreator.api.chat.ClickEvent;
import com.exoticcode.jsonchatcreator.api.chat.ClickEvent.ClickAction;
import com.exoticcode.jsonchatcreator.api.chat.HoverEvent;
import com.exoticcode.jsonchatcreator.api.chat.HoverEvent.HoverAction;
import com.exoticcode.jsonchatcreator.api.chat.JsonColor;
import com.exoticcode.jsonchatcreator.api.chat.TextComponent;

public class CmdHelp extends Command {

	public CmdHelp() {
		super("help", "jsonmessage.help", true,
				new TextComponent("/jsonmessage help", JsonColor.GREEN)
						.setHoverEvent(new HoverEvent(HoverAction.SHOW_TEXT, "Shows the help menu."))
						.setClickEvent(new ClickEvent(ClickAction.SUGGEST_COMMAND, "/jsonmessage help")),
				0, "hlp", "hep", "h");
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		sender.sendMessage(" ");
		String line = ChatColor.DARK_AQUA.toString() + ChatColor.STRIKETHROUGH + "--------";
		sender.sendMessage(line + ChatColor.RESET.toString() + ChatColor.AQUA + " JsonMessages " + line);
		if (!(sender instanceof Player)) {
			super.getCommands().forEach(command -> sender.sendMessage(command.getHelp().toConsoleString() + ""));
			return;
		}
		ChatBuilder builder = new ChatBuilder();
		super.getCommands().forEach(command -> {
			if (builder.getCurrent().getText() == null) {
				builder.setCurrent(command.getHelp());
			} else {
				TextComponent help = command.getHelp().clone();
				builder.next(help.setText("\n" + help.getText()));
			}
		});
		getApi().sendJsonMessage((Player) sender, builder.build());
		sender.sendMessage(" ");
		sender.sendMessage(ChatColor.DARK_GRAY + "Please hover over the messages for descriptions.");
	}

}
