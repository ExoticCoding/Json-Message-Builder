package com.exoticcode.jsonchatcreator.version;

import java.util.Set;

import org.bukkit.craftbukkit.v1_9_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;

import net.minecraft.server.v1_9_R2.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_9_R2.PacketPlayOutChat;

public class V1_9_R2 implements Version {

	@Override
	public void broadcastJsonMessage(Set<Player> players, String text) {
		PacketPlayOutChat packet = new PacketPlayOutChat(ChatSerializer.a(text));
		players.stream().filter(player -> player instanceof CraftPlayer)
				.forEach(player -> ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet));
	}

	@Override
	public void sendJsonMessage(Player player, String text) {
		PacketPlayOutChat packet = new PacketPlayOutChat(ChatSerializer.a(text));
		if (player instanceof CraftPlayer)
			((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
	}

}
