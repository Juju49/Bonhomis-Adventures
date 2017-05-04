package com.bonhomi.main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.security.Key;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JPanel;
import javax.xml.crypto.dsig.keyinfo.KeyInfo;

import com.bonhomi.game.GameManager;
import com.bonhomi.menu.MainMenu;
import com.bonhomi.menu.PauseMenu;

/**
 * Cette classe g�re l'affichage et 
 * la boucle de jeu
 */
public class Afficheur extends JPanel implements Runnable 
{
	private Graphics2D graphics;
	private boolean running = false;
	private Thread thread;
	private float frames = 0;
	
	// "�crans" � charger
	private GameManager gameManager;
	private MainMenu mainMenu;
	private PauseMenu pauseMenu;
	
	private InputManager inputManager;
	private String debug_keys;
	private String debug_mouse;

	// Constructeur
	public Afficheur()
	{
		setPreferredSize(new Dimension(Core.WIDTH, Core.HEIGHT));
		setDoubleBuffered(true);
		
		//inputManager = new InputManager();
		//addMouseMotionListener(inputManager);
	}
	
	// Lancement du thread
	public synchronized void start()
	{
		if (running)
			return;
		
		running = true;
		thread = new Thread(this);
		thread.start();
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
				// Chargement mainMenu
				if(mainMenu == null) 
				{
					mainMenu = new MainMenu();
					mainMenu.init();
				} 
				else if ((gameManager != null) || (pauseMenu != null)) 
				{
					gameManager.terminate();
					pauseMenu.terminate();
					gameManager = null;
					pauseMenu = null;
				}
				// Update mainMenu
				else 
				{
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
				if(mainMenu != null) 
				{
					mainMenu.terminate();
					mainMenu = null;
				} 
				else if(gameManager == null)
				{
					gameManager = new GameManager();
					gameManager.init();
				} 
				else 
				{
					gameManager.update();
				}
				break;
				
			default:
				throw new Error("invalid gameState");
		}
		if(MainClass.getDisplayFps()) 
		{
			debug_keys = InputManager.getKeySetAsString();
			debug_mouse = InputManager.getMouseSetAsString();
		}
	}
	
	// Affichage
	public void draw(Graphics g)
	{
		graphics = (Graphics2D) g;
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
		if(MainClass.getDebugLvl() < 3) {
			
		}
		if(MainClass.getDisplayFps()) {
			graphics.setColor(Color.blue);
			graphics.drawString("FPS  : " + String.valueOf(1000/frames), 4, 10);
			graphics.drawString(("Keys : [" + debug_keys + "]"), 4, 20);
			graphics.drawString(("Mouse : [" + debug_mouse + "]"), 4, 30);
		}
	}
	
	// Retourne le graphics2D (pour l'affichage)
	public Graphics2D getGraphics()
	{
		return graphics;
	}
	
	// Permet de "dessiner" sur le JPanel
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		draw(g);
	}

	// Boucle de jeu
	@Override
	public void run() 
	{
		// Variable de temps
		double lastTime = (double) System.currentTimeMillis();
		double nowTime = (double) System.currentTimeMillis();
		double timeElapsed = 0d;
		
		// Horloge du jeu
		while (running)
		{
			nowTime = System.currentTimeMillis();
			Core.deltaTime = nowTime - lastTime;
			lastTime = nowTime;
			
			// Mise a jour + affichage
			update();
			repaint();
			
			timeElapsed = (double) System.currentTimeMillis() - nowTime;
			
			frames = 
					(float) ((float) (1000 / Core.WANTED_FPS) - 
							(timeElapsed));
			
			if ((1000 / Core.WANTED_FPS) > (timeElapsed))
			{
				try {
					Thread.sleep(
							(long) (frames));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
}