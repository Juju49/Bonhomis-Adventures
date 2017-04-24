package com.bonhomi.main;

import java.awt.Dimension;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class Afficheur extends JPanel implements Runnable{
	
	private Graphics2D graphics;
	private boolean running = false;
	private Thread thread;

	public Afficheur()
	{
		this.setPreferredSize(new Dimension(500, 500));
	}
	
	public synchronized void start()
	{
		if (running)
			return;
		
		running = true;
		thread = new Thread(this);
		thread.start();
	}
	
	public void update(long deltaTime)
	{
		
	}
	
	public void draw()
	{
		
	}
	
	public Graphics2D getGraphics()
	{
		return graphics;
	}

	@Override
	public void run() 
	{
		long lastTime = System.nanoTime();
		long nowTime = System.nanoTime();
		long deltaTime = 0;
		
		// Horloge du jeu
		while (running)
		{
			nowTime = System.nanoTime();
			deltaTime = lastTime - nowTime;
			lastTime = nowTime;
			
			update(deltaTime);
			draw();
		}
	}
}
