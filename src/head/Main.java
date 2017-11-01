package head;

import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
	public void onEnable() {
		new Packet(this).hack();
		new Scheduler(this).start();
	}
}