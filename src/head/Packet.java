package head;

import java.util.List;

import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.plugin.Plugin;

import com.comphenix.packetwrapper.WrapperPlayServerEntityMetadata;
import com.comphenix.packetwrapper.WrapperPlayServerUpdateAttributes;
import com.comphenix.packetwrapper.WrapperPlayServerUpdateHealth;
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.WrappedAttribute;
import com.comphenix.protocol.wrappers.WrappedWatchableObject;

public class Packet {
	Plugin plugin;
	Packet(Plugin plugin) {
		this.plugin = plugin;
	}
	
	void hack() {
		ProtocolManager manager = ProtocolLibrary.getProtocolManager();
		manager.addPacketListener(new PacketAdapter(this.plugin, PacketType.Play.Server.UPDATE_HEALTH) {
		    @Override
		    public void onPacketSending(PacketEvent event) {
		    	WrapperPlayServerUpdateHealth packet = new WrapperPlayServerUpdateHealth(event.getPacket());
   			    float hp = (float) (event.getPlayer().getHealth() * 20 / (int)event.getPlayer().getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue());
   			    packet.setHealth(hp);
		    }
		});
		
		manager.addPacketListener(new PacketAdapter(this.plugin, PacketType.Play.Server.UPDATE_ATTRIBUTES) {
		    @Override
		    public void onPacketSending(PacketEvent event) {
		    	WrapperPlayServerUpdateAttributes packet = new WrapperPlayServerUpdateAttributes(event.getPacket());
		    	Entity e = packet.getEntity(event);
		    	if(e.getType() == EntityType.PLAYER) {
		    		List<WrappedAttribute> list = packet.getAttributes();
		    		for(int i = 0; i<list.size(); i++) {
		    			WrappedAttribute atr = list.get(i);
		    			if(atr.getAttributeKey().equals("generic.maxHealth")) {
		    				list.remove(atr);
		    			}
		    		}
		    		packet.setAttributes(list);
		    	}
		    }
		});
		
		manager.addPacketListener(new PacketAdapter(this.plugin, PacketType.Play.Server.ENTITY_METADATA) {
		    @Override
		    public void onPacketSending(PacketEvent event) {
		    	WrapperPlayServerEntityMetadata packet = new  WrapperPlayServerEntityMetadata(event.getPacket());
		    	Entity e = packet.getEntity(event);
		    	if(e.getType() == EntityType.PLAYER) {
			    	for(WrappedWatchableObject meta : packet.getEntityMetadata()) {
			    		if(meta.getIndex() == 7) {
			    			 float hp = (float) (event.getPlayer().getHealth() * 20 / (int)event.getPlayer().getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue());
                             meta.setValue(hp);
			    		}
			    	}
		    	}
		    }
		});
	}
}