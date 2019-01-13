package net.openaudiomc.jclient.utils.config;

import java.util.ArrayList;
import java.util.List;

public class ConfigStorageRegion {

    private String name;
    private String source;

    public List<String> serialize() {
        List<String> list = new ArrayList<>();
        list.add("    " + name + ":");
        list.add("      src: '" + source + "'");
        return list;
    }

    public void setName(String regionConfig) {
		name = regionConfig;
	}
    
    public void setSource(String string) {
		source = string;
	}
    
	public String getName() {
		return name;
	}
	
	public String getSource() {
		return source;
	}
}