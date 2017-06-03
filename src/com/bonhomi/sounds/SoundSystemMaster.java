package com.bonhomi.sounds;
import java.applet.Applet;
import java.applet.AudioClip;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

import com.bonhomi.main.Core;
import com.bonhomi.main.Loopable;
 

public class SoundSystemMaster  {

	private static ArrayList<AudioClip> loaded_sounds = new ArrayList<AudioClip>();
	
	private SoundSystemMaster()
	{
		
	}
	
	//gestion de la classe comme singleton
	private static class soundSysHolder
	{
		private static final SoundSystemMaster son = new SoundSystemMaster();
	}
	public static SoundSystemMaster getInstance()
	{
		return soundSysHolder.son;
	}
	
	private int addSound(String name)
	{
		
		
		URL url = getClass().getResource("/sfx/" + name + ".wav" );
		if( url == null )
		{
			Core.out.println("url is null");
			try {
				final URI uri = new File("src/Sounds/" + name + ".wav").toURI();
				url = uri.toURL();
			} 
			catch (MalformedURLException e) 
			{
				e.printStackTrace();
			}
		}
		
		AudioClip ac = Applet.newAudioClip(url);
		
		if(!loaded_sounds.contains(ac))
			loaded_sounds.add(ac);
		
		//jamais '-1' car le son est forcement charge a cet endroit
		return loaded_sounds.indexOf(ac);
		
	}
	
	public void ouille()
	{
		//sons a utiliser
		int[] son = {
				addSound("sfx/ouille_0"),
				addSound("sfx/ouille_1"),
		};
		
		//nombre possibles 0 et 1
		int nombre_aleat = ThreadLocalRandom.current().nextInt(0, 2);
		
		loaded_sounds.get(son[nombre_aleat]).play();
	}
	
	public void ennemyHit()
	{
		//sons a utiliser
		int son = addSound("sfx/ennemyHit_0");
		
		loaded_sounds.get(son).play();
	}
	
	public void terminate() {

	}
}
