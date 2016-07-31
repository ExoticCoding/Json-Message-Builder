package com.exoticcode.jsonchatcreator.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.exoticcode.jsonchatcreator.JsonChat;
import com.exoticcode.jsonchatcreator.api.chat.ClickEvent;
import com.exoticcode.jsonchatcreator.api.chat.ClickEvent.ClickAction;
import com.exoticcode.jsonchatcreator.api.chat.HoverEvent;
import com.exoticcode.jsonchatcreator.api.chat.HoverEvent.HoverAction;
import com.exoticcode.jsonchatcreator.api.chat.JsonColor;
import com.exoticcode.jsonchatcreator.api.chat.TextComponent;

public class CmdCreate extends Command {

	public CmdCreate() {
		super("create", "jsonmessage.create", false,
				new TextComponent("/jsonmessage create", JsonColor.GREEN)
						.setHoverEvent(new HoverEvent(HoverAction.SHOW_TEXT, "Opens the message builder gui."))
						.setClickEvent(new ClickEvent(ClickAction.SUGGEST_COMMAND, "/jsonmessage create")),
				0, "crt", "c", "crete", "crate", "crte");
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		JsonChat.getApi().openBuilder((Player) sender);
	}

}
