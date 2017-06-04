/**
 * 
 */
package com.bonhomi.sounds;

import java.util.concurrent.ThreadLocalRandom;

/**
 * @author BORDECIEL
 *
 */
public class SoundOccurence {
	private int[] son;
	
	
	public SoundOccurence(String... strings)
	{
		//sons a utiliser
		son = new int[strings.length];
		
		for (int i = 0; i < son.length; i++)
			son[i] = SoundSystemMaster.getInstance().addSound(strings[i]);
	}
	
	
	/**
	 * @param args
	 */
	public void Play(boolean looped) {
		//nombre possibles 0 et nbr sons
		int nombre_aleat = ThreadLocalRandom.current().nextInt(son.length);
		//jouer le son
		if(looped)
			SoundSystemMaster.loaded_sounds.get(son[nombre_aleat]).loop();
		else
			SoundSystemMaster.loaded_sounds.get(son[nombre_aleat]).play();
	}
	
	public void Stop() {
		for (int i : son)
			SoundSystemMaster.loaded_sounds.get(i).stop();
	}
}
