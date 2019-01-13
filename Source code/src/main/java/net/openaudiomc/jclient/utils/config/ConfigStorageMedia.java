package net.openaudiomc.jclient.utils.config;

import java.util.ArrayList;
import java.util.List;

public class ConfigStorageMedia {

    private String name;
    private Long length;

    public List<String> serialize() {
        List<String> list = new ArrayList<>();
        list.add("    " + name + ":");
        list.add("      length: " + length);
        return list;
    }
    
    public void setName(String mediaConfig) {
		name = mediaConfig;
	}
    
    public void setLength(long Long) {
		length = Long;
	}

	public String getName() {
		return name;
	}
	
	public long getLength() {
		return length;
	}
}