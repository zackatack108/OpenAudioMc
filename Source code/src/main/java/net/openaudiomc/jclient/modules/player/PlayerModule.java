package net.openaudiomc.jclient.modules.player;

import net.openaudiomc.jclient.OpenAudioMc;
import net.openaudiomc.jclient.modules.player.listeners.JoinQuitListener;
import net.openaudiomc.jclient.modules.player.listeners.SpeakerPlaceBreakListener;
import net.openaudiomc.jclient.modules.player.objects.AudioListener;

import java.util.HashMap;
import java.util.Map;

public class PlayerModule {

    private Map<String, AudioListener> listeners = new HashMap<>();

    public PlayerModule(OpenAudioMc plugin) {
        plugin.getServer().getPluginManager().registerEvents(new JoinQuitListener(), plugin);
        plugin.getServer().getPluginManager().registerEvents(new SpeakerPlaceBreakListener(), plugin);
    }

	public Map<String, AudioListener> getListeners() {
		return listeners;
	}
}
