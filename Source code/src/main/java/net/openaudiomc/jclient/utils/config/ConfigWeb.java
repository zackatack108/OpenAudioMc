package net.openaudiomc.jclient.utils.config;

import java.util.ArrayList;
import java.util.List;

public class ConfigWeb {

	private String header;

	private String url;
	private String title;
	private String background;
	private String startSound;
	private String ambianceSound;
	private Long speakerRadius;

	public List<String> serialize() {
		List<String> list = new ArrayList<>();
		list.add(header);
		list.add("web:");
		list.add("  url: '" + url + "'");
		list.add("  title: '" + title + "'");
		list.add("  background: '" + background + "'");
		list.add("  startsound: '" + startSound + "'");
		list.add("  ambiancesound: '" + ambianceSound + "'");
		list.add("  speaker_radius: " + speakerRadius);
		return list;
	}
	
	public void setHeader(String string) {
		header = string;
	}

	public void setUrl(String string) {
		url = string;
	}

	public void setTitle(String string) {
		title = string;
	}
	
	public void setBackground(String string) {
		background = string;
	}
	
	public void setStartSound(String string) {
		startSound = string;
	}
	
	public void setAmbianceSound(String string) {
		ambianceSound = string;
	}
	
	public void setSpeakerRadius(long Long) {
		speakerRadius = Long;
	}	
	
	public String getUrl() {
		return url;
	}

	public String getTitle() {
		return title;
	}
	
	public String getBackground() {
		return background;
	}
	
	public String getStartSound() {
		return startSound;
	}
	
	public String getAmbianceSound() {
		return ambianceSound;
	}
	
	public long getSpeakerRadius() {
		return speakerRadius;
	}
}