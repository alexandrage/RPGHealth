package health;

import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.EnumWrappers.ChatType;
import com.comphenix.protocol.wrappers.WrappedChatComponent;

public class Scheduler extends Thread {
	Main plugin;

	Scheduler(Main plugin) {
		this.plugin = plugin;
	}

	@Override
	public void run() {
		for (;;) {
			for (Player p : Bukkit.getOnlinePlayers()) {
				int exp = p.getExpToLevel();
				p.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(exp);
				if (p.getHealth() > exp) {
					p.setHealth(exp);
				}
				try {
					PacketContainer chat = new PacketContainer(PacketType.Play.Server.CHAT);
					chat.getChatTypes().write(0, ChatType.GAME_INFO);
					chat.getChatComponents().write(0,
							WrappedChatComponent.fromJson("{\"text\": \"§4§l❤§r " + (int) p.getHealth() + "/"
									+ (int) p.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue() + "\"}"));
					ProtocolLibrary.getProtocolManager().sendServerPacket(p, chat);
				} catch (Exception e) {
				}
			}
			try {
				Thread.sleep(500);
			} catch (Exception e) {
			}
		}
	}
}