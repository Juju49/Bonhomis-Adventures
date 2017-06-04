package com.bonhomi.sounds;
import java.applet.Applet;
import java.applet.AudioClip;
import java.net.URL;
import java.util.ArrayList;
 

public class SoundSystemMaster  {

	protected static ArrayList<AudioClip> loaded_sounds = new ArrayList<AudioClip>();
	
	private SoundSystemMaster()
	{
		
	}
	
	//gestion de la classe comme singleton
	private static class soundSysHolder
	{
		private static final SoundSystemMaster sono = new SoundSystemMaster();
	}
	public static SoundSystemMaster getInstance()
	{
		return soundSysHolder.sono;
	}
	
	protected int addSound(String name)
	{
		URL url = getClass().getResource("/Sounds/" + name + ".wav" );
		
		AudioClip ac = Applet.newAudioClip(url);
		
		if(!loaded_sounds.contains(ac))
			loaded_sounds.add(ac);
		
		//jamais '-1' car le son est forcement charge a cet endroit
		return loaded_sounds.indexOf(ac);
		
	}
	
	void terminate()
	{
		loaded_sounds.clear();
	}
}
