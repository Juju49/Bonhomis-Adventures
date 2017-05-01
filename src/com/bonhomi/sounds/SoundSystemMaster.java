package com.bonhomi.sounds;

public class SoundSystemMaster implements Runnable  {

	private boolean running = false;
	private Thread thread;
	
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
