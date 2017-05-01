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
 * Cette classe gère l'affichage et 
 * la boucle de jeu
 */
public class Afficheur extends JPanel implements Runnable 
{
	private Graphics2D graphics;
	private boolean running = false;
	private Thread thread;
<<<<<<< HEAD
	private long frames = 0;
	private Timer keyTimer;
=======
	private float frames = 0;
>>>>>>> refs/heads/juju-br
	
	// "écrans" à charger
	private GameManager gameManager;
	private MainMenu mainMenu;
	private PauseMenu pauseMenu;

	// Constructeur
	public Afficheur()
	{
		this.setPreferredSize(new Dimension(Core.WIDTH, Core.HEIGHT));
		this.setDoubleBuffered(true);
		
		mainMenu = new MainMenu();
	}
	
	// Lancement du thread
	public synchronized void start()
	{
		if (running)
			return;
		
		running = true;
		thread = new Thread(this);
		thread.start();
		

		mainMenu.init();
	}
	
<<<<<<< HEAD
	// Mise à jour
=======
	/**
	 * Updates game according to states.
	 * Fired at each frame, he updates his children too.
	 */
>>>>>>> refs/heads/juju-br
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
	
	// Affichage
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
		//Core.draw();
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
			
			// Mise à jour + affichage
			update();
			this.repaint();
			
			timeElapsed = (double) System.currentTimeMillis() - nowTime;
			
<<<<<<< HEAD
			// System.out.printf("%.2f \n", 
			// (float) (1000 / (1000 / Core.WANTED_FPS) - (timeElapsed)));
=======
			frames = (float) (1000 / (1000 / WANTED_FPS) - (timeElapsed));
>>>>>>> refs/heads/juju-br
			
			if ((1000 / Core.WANTED_FPS) > (timeElapsed))
			{
				try {
					Thread.sleep(
<<<<<<< HEAD
							(long) ((1000 / Core.WANTED_FPS) - (timeElapsed)));
=======
							(long) (frames * 1000));
>>>>>>> refs/heads/juju-br
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	// Touche enfoncée
	public void keyIsDown(KeyEvent e)
	{
		switch (Core.gameState)
		{
			case MENU:
				
				break;
			case GAME:
				gameManager.keyIsDown(e);
				break;
			default:
				Core.out("ERREUR");
				break;
		}
	}
	
	// Touche relâchée
	public void keyIsUp(KeyEvent e)
	{
		switch (Core.gameState)
		{
			case MENU:
				
				break;
			case GAME:
				gameManager.keyIsUp(e);
				break;
			default:
				Core.out("ERREUR");
				break;
		}
	}
	
	// 
	public void keyPressed(KeyEvent e)
	{
		if (keyTimer == null)
		{
			keyTimer = new Timer();
			keyTimer.scheduleAtFixedRate(new TimerTask() 
			{
				@Override
				public void run() 
				{
					keyIsDown(e);
				}
			}, 0, (long) (1000 / Core.WANTED_FPS));
		}
	}
	
	public void keyReleased(KeyEvent e)
	{
		Core.out(e);
		if (keyTimer != null)
			keyTimer.cancel();
		
		keyTimer = null;
		keyIsUp(e);
	}
}
