package com.bonhomi.main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import com.bonhomi.game.GameManager;
import com.bonhomi.menu.MainMenu;
import com.bonhomi.menu.PauseMenu;

public class Afficheur extends JPanel implements Runnable {
	
	private Graphics2D graphics;
	private boolean running = false;
	private Thread thread;
	private float frames = 0;
	
	private GameManager gameManager;
	private MainMenu mainMenu;
	private PauseMenu pauseMenu;

	public Afficheur()
	{
		this.setPreferredSize(new Dimension(Core.WIDTH, Core.HEIGHT));
		this.setDoubleBuffered(true);
		
		mainMenu = new MainMenu();
	}
	
	public synchronized void start()
	{
		if (running)
			return;
		
		running = true;
		thread = new Thread(this);
		thread.start();
		

		mainMenu.init();
	}
	
	/**
	 * Updates game according to states.
	 * Fired at each frame, he updates his children too.
	 */
	public void update()
	{
		switch (Core.gameState)
		{
			case MENU:
				//load mainMenu
				if(mainMenu == null) {
					mainMenu = new MainMenu();
					mainMenu.init();
					
				//shuting game down entirely
				} else if ((gameManager != null) || (pauseMenu != null)) {
					gameManager.terminate();
					pauseMenu.terminate();
					gameManager = null;
					pauseMenu = null;
					
				//update mainMenu
				} else {
					mainMenu.update();
				}
				break;
				
			case PAUSE:
				/*can only be invoked from GAME state,
				nothing must unload now.*/
				if(pauseMenu == null) {
					pauseMenu = new PauseMenu();
					pauseMenu.init();
					
				} else {
					pauseMenu.update();
				}
				break;
				
			case GAME:
				/* closing mainMenu;
				 * pauseMenu is not unloaded,
				 * it might serve under short notice*/
				if(mainMenu != null) {
					mainMenu.terminate();
					mainMenu = null;
					
				//start game
				} else if(gameManager == null){
					gameManager = new GameManager();
					gameManager.init();
					
				//update game
				} else {
					gameManager.update();
				}
				break;
				
			default:
				throw new Error("invalid gameState");
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
				mainMenu.draw(graphics);
				break;
			case PAUSE:
				pauseMenu.draw(graphics);
				break;
			case GAME:
				gameManager.draw(graphics);
				break;
			default:
				throw new Error("invalid gameState");
		}
		if(MainClass.getDebugLvl() > 0) {
			
		}
		if(MainClass.getDisplayFps()) {
			graphics.setColor(Color.green);
			graphics.drawString(String.valueOf(frames), 4, 10);
		}
	}
	
	public Graphics2D getGraphics()
	{
		return graphics;
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		draw(g);
		//Core.draw();
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
			
			frames = (float) (1000 / (1000 / WANTED_FPS) - (timeElapsed));
			
			if ((1000 / WANTED_FPS) > (timeElapsed))
			{
				try {
					Thread.sleep(
							(long) (frames * 1000));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
