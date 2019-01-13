package net.openaudiomc.jclient.utils.config;

import java.util.ArrayList;
import java.util.List;

public class ConfigMessages {

    private String header;

    private String provideUrl;
    private String connected;
    private String disconnected;
    private String volumefail;
    private String volume;

    public List<String> serialize() {
        List<String> list = new ArrayList<>();
        list.add(header);
        list.add("messages:");
        list.add("  provide_url: '" + provideUrl + "'");
        list.add("  connected: '" + connected + "'");
        list.add("  disconnected: '" + disconnected + "'");
        list.add("  setvolumefail: '" + volumefail + "'");
        list.add("  setvolume: '" + volume + "'");
        return list;
    }
    
    public void setHeader(String string) {
		header = string;
	}

	public void setProvideUrl(String string) {
		provideUrl = string;		
	}

	public void setConnected(String string) {
		connected = string;		
	}
	
	public void setDisconnected(String string) {
		disconnected = string;
	}
	
	public void setvolumefail(String string) {
		volumefail = string;
	}
	
	public void setvolume(String string) {
		volume = string;
	}
	
	public String getProvideUrl() {
		return provideUrl;	
	}

	public String getConnected() {
		return connected;	
	}
	
	public String getDisconnected() {
		return disconnected;
	}
	
	public String getvolumefail() {
		return volumefail;
	}
	
	public String getvolume() {
		return volume;
	}
}