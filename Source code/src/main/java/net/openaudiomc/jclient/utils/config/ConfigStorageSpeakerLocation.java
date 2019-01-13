package net.openaudiomc.jclient.utils.config;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ConfigStorageSpeakerLocation {

    private UUID id;
    private String world;
    private Double x;
    private Double y;
    private Double z;
    private String sound;

    public List<String> serialize() {
        List<String> list = new ArrayList<>();
        list.add("    " + id + ":");
        list.add("      world: '" + world + "'");
        list.add("      x: '" + x + "'");
        list.add("      y: '" + y + "'");
        list.add("      z: '" + z + "'");
        list.add("      sound: '" + sound + "'");
        return list;
    }
    
    public void setId(UUID fromString) {
		id = fromString;
	}
    
    public void setWorld(String string) {
		world = string;
	}
    
    public void setX(double Double) {
		x = Double;
	}
    
    public void setY(double Double) {
		y = Double;
	}
    
    public void setZ(double Double) {
		z = Double;
	}
    
    public void setSound(String string) {
		sound = string;
	}

	public UUID getId() {
		return id;
	}
	
	public String getWorld() {
		return world;
	}
    
    public double getX() {
		return x;
	}
    
    public double getY() {
    	return y;
	}
    
    public double getZ() {
		return z;
	}
    
    public String getSound() {
		return sound;
	}
}