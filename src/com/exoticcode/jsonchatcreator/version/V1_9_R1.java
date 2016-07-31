package com.exoticcode.jsonchatcreator.version;

import java.util.Set;

import org.bukkit.craftbukkit.v1_9_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import net.minecraft.server.v1_9_R1.IChatBaseComponent.ChatSerializer;

public class V1_9_R1 implements Version {

	@Override
	public void broadcastJsonMessage(Set<Player> players, String text) {
		players.stream().filter(player -> player instanceof CraftPlayer)
				.forEach(player -> ((CraftPlayer) player).getHandle().sendMessage(ChatSerializer.a(text)));
	}

	@Override
	public void sendJsonMessage(Player player, String text) {
		if (player instanceof CraftPlayer)
			((CraftPlayer) player).getHandle().sendMessage(ChatSerializer.a(text));
	}

}
