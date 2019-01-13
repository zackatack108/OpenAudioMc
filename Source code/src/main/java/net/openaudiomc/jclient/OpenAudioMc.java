package net.openaudiomc.jclient;

import net.openaudiomc.jclient.modules.commands.CommandsModule;
import net.openaudiomc.jclient.modules.media.MediaModule;
import net.openaudiomc.jclient.modules.player.PlayerModule;
import net.openaudiomc.jclient.modules.socket.SocketModule;
import net.openaudiomc.jclient.modules.socket.objects.ApiEndpoints;
import net.openaudiomc.jclient.utils.config.Config;
import net.openaudiomc.jclient.utils.Reflection;

import org.bukkit.plugin.java.JavaPlugin;

public final class OpenAudioMc extends JavaPlugin {

    private static OpenAudioMc instance;

    private Config conf;

    private PlayerModule playerModule;
    private SocketModule socketModule;
    private CommandsModule commandsModule;
    private ApiEndpoints apiEndpoints;
    private Reflection reflection;
    private MediaModule mediaModule;
    
    @Override
    public void onEnable() {
        instance = this;

        conf = new Config();
        conf.load();

        apiEndpoints = new ApiEndpoints();

        playerModule = new PlayerModule(this);
        socketModule = new SocketModule(this);
        commandsModule = new CommandsModule(this);
        reflection = new Reflection(this);
        mediaModule = new MediaModule(this);
    }

    @Override
    public void onDisable() {
        socketModule.closeConnection();
    }
    
    public Config getConf() {
    	return conf;
    }

	public static OpenAudioMc getInstance() {
		return instance;
	}

	public PlayerModule getPlayerModule() {
		return playerModule;
	}
	
	public SocketModule getSocketModule() {
		return socketModule;
	}
	
	public CommandsModule getCommandsModule() {
		return commandsModule;
	}
	
	public ApiEndpoints getApiEndpoints() {
		return apiEndpoints;
	}
	
	public Reflection getReflection() {
		return reflection;
	}

	public MediaModule getMediaModule() {
		return mediaModule;
	}
	
}
