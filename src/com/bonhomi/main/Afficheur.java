package com.bonhomi.main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;
import com.bonhomi.game.GameManager;
import com.bonhomi.menu.MainMenu;
import com.bonhomi.menu.PauseMenu;

/**
 * Cette classe gere l'affichage et 
 * la boucle de jeu
 */
public class Afficheur extends JPanel implements Runnable 
{
	private Graphics2D graphics;
	private boolean running = false;
	private Thread thread;
	private float frames = 0;
	
	// "ecrans" a charger
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
				} 
				else 
				{
					gameManager.update();
				}
				break;
				
			default:
				throw new Error("invalid gameState");
		}
		//affichage de debug:
		if((MainClass.getDebugLvl() >= 3) && (Core.DebOut != null)) 
		{
			/*Core.DebOut.inputLabel(0, ("FPS   :  " + String.valueOf(1000/frames)));
			Core.DebOut.inputLabel(1, ("Keys  : [" + InputManager.getKeySetAsString() + "]"));
			Core.DebOut.inputLabel(2, ("Mouse : [" + InputManager.getMouseSetAsString() + "]"));
			Core.DebOut.update();*/
		}
	}
	
	// Affichage
	public void draw(Graphics g)
	{
		graphics = (Graphics2D) g;
		switch (Core.gameState)
		{
			case MENU:
				if (mainMenu != null)
				mainMenu.draw(graphics);
				break;
			case PAUSE:
				if (pauseMenu != null)
				pauseMenu.draw(graphics);
				break;
			case GAME:
				if (gameManager != null)
				gameManager.draw(graphics);
				break;
			default:
				throw new Error("invalid gameState");
		}
	}
	
	// Permet de "dessiner" sur le JPanel
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		draw(g);
		
		if(MainClass.getDebugLvl() >= 3) 
		{
			g.setColor(Color.blue);
			g.drawString("FPS  : " + String.valueOf(1000/frames), 4, 10);
			g.drawString(("Keys : [" + InputManager.getKeySetAsString() + "]"), 4, 20);
			g.drawString(("Mouse : [" + InputManager.getMouseSetAsString() + "]"), 4, 30);
		}
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
				try 
				{
					Thread.sleep((long) (frames));
				} 
				catch (InterruptedException e) 
				{
					e.printStackTrace();
				}
			}
		}
	}
}