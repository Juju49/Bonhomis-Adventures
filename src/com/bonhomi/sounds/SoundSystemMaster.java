package com.bonhomi.sounds;
import java.applet.Applet;
import java.applet.AudioClip;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
 

public class SoundSystemMaster implements Runnable  {

	private boolean running = false;
	private Thread thread;
	

	public static void main(String[] args) {
		File fichier = new File("sfx/explosion.wav");
		URI uri = fichier.toURI();
		URL url = null;
		try {
			url = uri.toURL();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		AudioClip ac = Applet.newAudioClip(url);
		ac.play();
	 
	}
	@Override
	public void run() 
	{
		while(running) {
			update();
		}
	}
	
	public synchronized void start()
	{
		if (running)
			return;
		
		running = true;
		thread = new Thread(this);
		thread.start();
		
		this.init();
	}
	
	
	private void init() {
		
	}
	
	public void update() {

	}
}
