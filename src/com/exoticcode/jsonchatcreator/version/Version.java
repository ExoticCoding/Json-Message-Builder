package com.exoticcode.jsonchatcreator.version;

import java.util.Set;

import org.bukkit.entity.Player;

public interface Version {

	void sendJsonMessage(Player player, String text);

	void broadcastJsonMessage(Set<Player> players, String text);

}
