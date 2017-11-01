package head;

import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
public class Scheduler extends Thread {
	Main plugin;
	Scheduler(Main plugin) {
		this.plugin = plugin;
	}

	@Override
	public void run() {
		for(;;) {
			for(Player p : Bukkit.getOnlinePlayers()) {
				try {
			        PacketContainer chat = new PacketContainer(PacketType.Play.Server.CHAT);
			        chat.getBytes().write(0, (byte)2);
			        chat.getChatComponents().write(0, WrappedChatComponent.fromJson("{\"text\": \"§4§l❤§r "+(int)p.getHealth()+"/"+p.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue()+"\"}"));
			        ProtocolLibrary.getProtocolManager().sendServerPacket(p, chat);
				} catch(Exception e) {}
			}
			try {
				Thread.sleep(500);
			} catch (Exception e) {}
		}
	}
}