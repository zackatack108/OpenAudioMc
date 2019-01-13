package net.openaudiomc.jclient;

import net.openaudiomc.jclient.modules.media.exceptions.InvalidColorCodeException;
import net.openaudiomc.jclient.modules.media.objects.AudioRegion;
import net.openaudiomc.jclient.modules.media.objects.AudioSpeaker;
import net.openaudiomc.jclient.modules.media.objects.HueState;
import net.openaudiomc.jclient.modules.media.objects.Media;
import net.openaudiomc.jclient.modules.player.objects.AudioListener;
import net.openaudiomc.jclient.modules.socket.enums.PacketCommand;
import net.openaudiomc.jclient.modules.socket.objects.OaPacket;

import org.bukkit.Location;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.json.JSONObject;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import com.sk89q.worldguard.protection.regions.RegionQuery;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class OpenAudioApi {

	public void play(Media m, Player p, CommandSender s) {
		AudioListener l = OpenAudioMc.getInstance().getPlayerModule().getListeners().get(p.getName());
		l.sendPacket(m.getHandle(l));
	}

	public void play(Media m, String o, CommandSender s) {
		for (AudioListener l : handleOpperator(o, s)) {
			l.sendPacket(m.getHandle(l));
		}
	}

	public void stop(Player p, CommandSender s) {
		AudioListener l = OpenAudioMc.getInstance().getPlayerModule().getListeners().get(p.getName());
		l.sendPacket(new OaPacket().setCommand(PacketCommand.STOP));
	}

	public void stop(String o, CommandSender s) {
		for (AudioListener l : handleOpperator(o, s)) {
			l.sendPacket(new OaPacket().setCommand(PacketCommand.STOP));
		}
	}

	public void stopId(Player p, String id) {
		AudioListener l = OpenAudioMc.getInstance().getPlayerModule().getListeners().get(p.getName());
		l.sendPacket(new OaPacket().setCommand(PacketCommand.STOP_SPECIAL).setValue(id));
	}

	public void stopId(String o, String id, CommandSender s) {
		for (AudioListener l : handleOpperator(o, s)) {
			l.sendPacket(new OaPacket().setCommand(PacketCommand.STOP_SPECIAL).setValue(id));
		}
	}

	public void stopRegion(AudioListener listener, String region) {
		stopId(listener.getPlayer(), "region_" + region);
	}

	public void startRegion(AudioListener listener, String region) {
		AudioRegion reg = OpenAudioMc.getInstance().getMediaModule().getRegions().get(region);
		if (reg != null) {
			reg.play(listener);
		}
	}

	public void setVolume(Player p, int volume) {
		AudioListener l = OpenAudioMc.getInstance().getPlayerModule().getListeners().get(p.getName());
		OaPacket packet = new OaPacket();
		packet.setCommand(PacketCommand.SETVOLUME);
		packet.setValue(volume + "");
		l.sendPacket(packet);
	}

	public void setVolume(AudioListener l, int volume) {
		OaPacket packet = new OaPacket();
		packet.setCommand(PacketCommand.SETVOLUME);
		packet.setValue(volume + "");
		l.sendPacket(packet);
	}

	public void stopSpeaker(AudioListener listener, String speaker) {
		stopId(listener.getPlayer(), "speaker_" + speaker);
	}

	public void startSpeaker(AudioListener l, String id, int volume) {
		AudioSpeaker s = OpenAudioMc.getInstance().getMediaModule().getSpeakerMedia().get(id);
		if (s.getMedia() != null) {
			l.sendPacket(s.getMedia().getHandle(l, volume));
		}
	}

	public void setSpeakerVolume(AudioListener l, String id, int volume) {
		try {
			OaPacket p = new OaPacket();
			p.setCommand(PacketCommand.SET_SPEAKER_VOLUME);
			p.setPlayer(l);
			JSONObject tags = new JSONObject();
			tags.put("id", id);
			tags.put("volume", volume);
			p.setValue(tags.toString());
			l.sendPacket(p);
		} catch (Exception THISWILLNEVERHAPPEN) {
		}
	}

	public void hueColor(String o, String rgba, CommandSender s) throws InvalidColorCodeException {
		HueState hueState = new HueState();
		hueState.fromRgba(rgba);

		for (AudioListener a : handleOpperator(o, s)) {
			a.sendPacket(hueState.getHandle(a));
		}
	}

	public void hueColor(String o, int red, int blue, int green, int brightness, CommandSender s) {
		HueState hueState = new HueState();
		hueState.setRed(red);
		hueState.setGreen(green);
		hueState.setBlue(blue);
		hueState.setBrightness(brightness);

		for (AudioListener a : handleOpperator(o, s)) {
			a.sendPacket(hueState.getHandle(a));
		}
	}

	public void hueColor(AudioListener l, String rgba) throws InvalidColorCodeException {
		HueState hueState = new HueState();
		hueState.fromRgba(rgba);

		l.sendPacket(hueState.getHandle(l));
	}

	public OpenAudioMc getMain() {
		return OpenAudioMc.getInstance();
	}

	public Boolean isConnected(Player player) {
		if (getMain().getPlayerModule().getListeners().get(player.getName()) != null) return getMain().getPlayerModule().getListeners().get(player.getName()).getIsConnected();
		return true;
	}

	public void hueColor(AudioListener l, int red, int blue, int green, int brightness) {
		HueState hueState = new HueState();
		hueState.setRed(red);
		hueState.setGreen(green);
		hueState.setBlue(blue);
		hueState.setBrightness(brightness);

		l.sendPacket(hueState.getHandle(l));
	}

	private List<AudioListener> handleOpperator(String o, CommandSender s) {
		List<AudioListener> list = new ArrayList<>();
		if (o.startsWith("@a")) {

			if(o.length() > 2) {

				int radius = 3000000;
				float x = 3000001, y = 257, z = 3000001;

				String arguments = o.replace("@a[", "");
				arguments = arguments.replace("]", "");

				ArrayList<String> tagList = new ArrayList<String>();

				for(String tag : arguments.split(",")) {					
					tagList.add(tag);					
				}

				for(int index = 0; index < tagList.size(); index++) {

					if(tagList.get(index).startsWith("distance=..")) {						
						radius = Integer.parseInt(tagList.get(index).replace("distance=..", ""));

					}
					if(tagList.get(index).startsWith("x=")) {						
						x = Float.parseFloat(tagList.get(index).replace("x=", ""));

					} 
					if(tagList.get(index).startsWith("y=")) {						
						y = Float.parseFloat(tagList.get(index).replace("y=", ""));

					} 
					if(tagList.get(index).startsWith("z=")) {			
						z = Float.parseFloat(tagList.get(index).replace("z=", ""));

					}					
				}

				if(s instanceof BlockCommandSender) {
					BlockCommandSender cmdBlock = (BlockCommandSender) s;

					Location location = cmdBlock.getBlock().getLocation();

					if(x < 3000001 && x > -3000001) {
						location.setX(x);
					}

					if(y < 257 && y > -1) {
						location.setY(y);
					}

					if(z < 3000001 && z > -3000001) {
						location.setZ(z);
					}					

					for (AudioListener l : OpenAudioMc.getInstance().getPlayerModule().getListeners().values()) {

						if(l.getPlayer().getLocation().distance(location) <= radius) {
							list.add(l);
						}
					}

				} else if(s instanceof Player) {

					Player player = (Player) s;

					Location location = player.getLocation();

					if(x < 3000001 && x > -3000001) {
						location.setX(x);
					}

					if(y < 257 && y > -1) {
						location.setY(y);
					}

					if(z < 3000001 && z > -3000001) {
						location.setZ(z);
					}

					for (AudioListener l : OpenAudioMc.getInstance().getPlayerModule().getListeners().values()) {
						if(l.getPlayer().getLocation().distance(location) <= radius) {
							list.add(l);
						}
					}
				}

			} else {

				for (AudioListener l : OpenAudioMc.getInstance().getPlayerModule().getListeners().values()) {
					list.add(l);
				}

			}
		}

		if (o.startsWith("@p")) {

			if(o.length() > 2) {

				int radius = 10;
				float x = 3000001, y = 257, z = 3000001;

				String arguments = o.replace("@p[", "");
				arguments = arguments.replace("]", "");

				ArrayList<String> tagList = new ArrayList<String>();

				for(String tag : arguments.split(",")) {					
					tagList.add(tag);					
				}

				for(int index = 0; index < tagList.size(); index++) {

					if(tagList.get(index).startsWith("distance=..")) {						
						radius = Integer.parseInt(tagList.get(index).replace("distance=..", ""));

					}
					if(tagList.get(index).startsWith("x=")) {						
						x = Float.parseFloat(tagList.get(index).replace("x=", ""));

					} 
					if(tagList.get(index).startsWith("y=")) {						
						y = Float.parseFloat(tagList.get(index).replace("y=", ""));

					} 
					if(tagList.get(index).startsWith("z=")) {			
						z = Float.parseFloat(tagList.get(index).replace("z=", ""));

					}					
				}

				if(s instanceof BlockCommandSender) {
					BlockCommandSender cmdBlock = (BlockCommandSender) s;

					Location location = cmdBlock.getBlock().getLocation();

					if(x < 3000001 && x > -3000001) {
						location.setX(x);
					}

					if(y < 257 && y > -1) {
						location.setY(y);
					}

					if(z < 3000001 && z > -3000001) {
						location.setZ(z);
					}

					for (AudioListener l : OpenAudioMc.getInstance().getPlayerModule().getListeners().values()) {

						if(l.getPlayer().getLocation().distance(cmdBlock.getBlock().getLocation()) <= radius) {
							list.add(l);
							return list;
						}
					}

				} else if(s instanceof Player) {

					Player player = (Player) s;

					Location location = player.getLocation();

					if(x < 3000001 && x > -3000001) {
						location.setX(x);
					}

					if(y < 257 && y > -1) {
						location.setY(y);
					}

					if(z < 3000001 && z > -3000001) {
						location.setZ(z);
					}

					for (AudioListener l : OpenAudioMc.getInstance().getPlayerModule().getListeners().values()) {
						if(l.getPlayer().getLocation().distance(player.getLocation()) <= radius) {
							list.add(l);
							return list;
						}
					}

				}

			}

			if(s instanceof BlockCommandSender) {
				BlockCommandSender cmdBlock = (BlockCommandSender) s;				

				for (AudioListener l : OpenAudioMc.getInstance().getPlayerModule().getListeners().values()) {

					if(l.getPlayer().getLocation().distance(cmdBlock.getBlock().getLocation()) <= 10) {
						list.add(l);
						return list;
					}

				}
			} else if(s instanceof Player) {

				for (AudioListener l : OpenAudioMc.getInstance().getPlayerModule().getListeners().values()) {

					if(l.getPlayer().getUniqueId().equals(((Player) s).getPlayer().getUniqueId())) {
						list.add(l);
						return list;
					}
				}
			}
		}

		if (o.startsWith("region:")) {
			for (AudioListener l : OpenAudioMc.getInstance().getPlayerModule().getListeners().values()) {
				String id = o.replace("region:", "");
				List<String> regions = new ArrayList<>();
				for(ProtectedRegion r : getRegionSet(l)) {
					regions.add(r.getId());
				}
				if (regions.contains(id)) {
					list.add(l);
				}
			}
		}

		if (OpenAudioMc.getInstance().getPlayerModule().getListeners().get(o) != null) {
			list.add(OpenAudioMc.getInstance().getPlayerModule().getListeners().get(o));
		}

		return list;
	}

	private Set<ProtectedRegion> getRegionSet(AudioListener l) {

		Location location = l.getPlayer().getLocation();

		RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
		RegionQuery query = container.createQuery();
		ApplicableRegionSet set = query.getApplicableRegions(BukkitAdapter.adapt(location));
		return set.getRegions();		
	}

}
