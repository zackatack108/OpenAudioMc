package net.openaudiomc.jclient.utils.config;

import java.util.ArrayList;
import java.util.List;

public class ConfigKey {

    private String header;

    private String publicKey;
    private String privateKey;

    public List<String> serialize() {
        List<String> list = new ArrayList<>();
        list.add(header);
        list.add("key:");
        list.add("  public: '" + publicKey + "'");
        list.add("  private: '" + privateKey + "'");
        return list;
    }
    
    public void setHeader(String string) {
    	header = string;
    }

	public void setPublicKey(String string) {
		publicKey = string;
	}
	
	public void setPrivateKey(String string) {
		privateKey = string;
	}
	
	public String getHeader() {
		return header;
	}
	
	public String getPublicKey() {
		return publicKey;
	}
	
	public String getPrivateKey() {
		return privateKey;
	}
}