package head;

import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;

public class Scheduler extends Thread {
	Main plugin;

	Scheduler(Main plugin) {
		this.plugin = plugin;
	}

	@Override
	public void run() {
		for (;;) {
			for (Player p : Bukkit.getOnlinePlayers()) {
				p.setHealthScaled(true);
				p.spigot().sendMessage(ChatMessageType.ACTION_BAR,
						TextComponent.fromLegacyText("§4§l❤§r " + (int) p.getHealth() + "/"
								+ (int) p.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue()));
			}
			try {
				Thread.sleep(500);
			} catch (Exception e) {
			}
		}
	}
}