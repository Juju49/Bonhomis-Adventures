package com.bonhomi.main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import com.bonhomi.game.GameManager;

public class Afficheur extends JPanel implements Runnable {
	
	private Graphics2D graphics;
	private boolean running = false;
	private Thread thread;
	private long frames = 0;
	
	private GameManager gameManager;

	public Afficheur()
	{
		this.setPreferredSize(new Dimension(Core.WIDTH, Core.HEIGHT));
		this.setDoubleBuffered(true);
		
		gameManager = new GameManager();
	}
	
	public synchronized void start()
	{
		if (running)
			return;
		
		running = true;
		thread = new Thread(this);
		thread.start();
		
		gameManager.init();
	}
	
	public void update()
	{
		switch (Core.gameState)
		{
			case MENU:
				
				break;
			case GAME:
				gameManager.update();
				break;
			default:
				Core.out("ERREUR");
				break;
		}
	}
	
	public void draw(Graphics g)
	{
		graphics = (Graphics2D) g;
		graphics.setColor(Color.white);
		graphics.fillRect(0, 0, this.getWidth(), this.getHeight());
		
		switch (Core.gameState)
		{
			case MENU:
				
				break;
			case GAME:
				gameManager.draw(graphics);
				break;
			default:
				Core.out("ERREUR");
				break;
		}
	}
	
	public Graphics2D getGraphics()
	{
		return graphics;
	}
	
	public void paintComponent(Graphics g)
	{
		draw(g);
	}

	@Override
	public void run() 
	{
		double lastTime = (double) System.currentTimeMillis();
		double nowTime = (double) System.currentTimeMillis();
		double timeElapsed = 0d;
		final double WANTED_FPS = 60d;
		
		// Horloge du jeu
		while (running)
		{
			nowTime = System.currentTimeMillis();
			Core.deltaTime = nowTime - lastTime;
			lastTime = nowTime;
			
			update();
			this.repaint();
			
			timeElapsed = (double) System.currentTimeMillis() - nowTime;
			
			System.out.printf("%.2f \n", 
					(float) (1000 / (1000 / WANTED_FPS) - (timeElapsed)));
			
			if ((1000 / WANTED_FPS) > (timeElapsed))
			{
				try {
					Thread.sleep(
							(long) ((1000 / WANTED_FPS) - (timeElapsed)));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
