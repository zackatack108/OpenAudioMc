package net.openaudiomc.jclient.utils.adapters;

import com.google.gson.annotations.SerializedName;

public class SnowYt {

    @SerializedName("mediacomplete")
    private String fullEndpoint = "";

	public String getFullEndpoint() {
		return fullEndpoint;
	}
}
