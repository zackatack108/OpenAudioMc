package net.openaudiomc.jclient.utils.config;

import java.util.ArrayList;
import java.util.List;

public class ConfigCommands {

    private String header;

    private List<String> commands;

    public List<String> serialize() {
        List<String> list = new ArrayList<>();
        list.add(header);
        list.add("commands:");
        for(String command : commands) {
            list.add("  - \"" + command + "\"");
        }
        return list;
    }
    
    public void setHeader(String string) {
		header = string;
	}

	public void setCommands(List<String> stringList) {
		commands = stringList;
	}

	public List<String> getCommands() {
		return commands;
	}
	
}