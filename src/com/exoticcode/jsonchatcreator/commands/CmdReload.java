package com.exoticcode.jsonchatcreator.commands;

import org.bukkit.command.CommandSender;

import com.exoticcode.jsonchatcreator.api.chat.ClickEvent;
import com.exoticcode.jsonchatcreator.api.chat.ClickEvent.ClickAction;
import com.exoticcode.jsonchatcreator.api.chat.HoverEvent;
import com.exoticcode.jsonchatcreator.api.chat.HoverEvent.HoverAction;
import com.exoticcode.jsonchatcreator.api.chat.JsonColor;
import com.exoticcode.jsonchatcreator.api.chat.TextComponent;

public class CmdReload extends Command {

	public CmdReload() {
		super("reload", "jsonmessage.reload", true,
				new TextComponent("/jsonmessage reload", JsonColor.GREEN)
						.setHoverEvent(new HoverEvent(HoverAction.SHOW_TEXT, "Simply a plugin reload command."))
						.setClickEvent(new ClickEvent(ClickAction.SUGGEST_COMMAND, "/jsonmessage reload")),
				0, "rl", "relod", "reld", "rld");
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		getApi().load();
		sender.sendMessage(getMessage("success"));
	}

}
