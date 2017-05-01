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

/**
 * Cette classe gère l'affichage et 
 * la boucle de jeu
 */
public class Afficheur extends JPanel implements Runnable 
{
	private Graphics2D graphics;
	private boolean running = false;
	private Thread thread;
	private long frames = 0;
	private Timer keyTimer;
	
	// "écrans" à charger
	private GameManager gameManager;

	// Constructeur
	public Afficheur()
	{
		this.setPreferredSize(new Dimension(Core.WIDTH, Core.HEIGHT));
		this.setDoubleBuffered(true);
		
		gameManager = new GameManager();
	}
	
	// Lancement du thread
	public synchronized void start()
	{
		if (running)
			return;
		
		running = true;
		thread = new Thread(this);
		thread.start();
		
		gameManager.init();
	}
	
	// Mise à jour
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
	
	// Affichage
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
	
	// Retourne le graphics2D (pour l'affichage)
	public Graphics2D getGraphics()
	{
		return graphics;
	}
	
	// Permet de "dessiner" sur le JPanel
	public void paintComponent(Graphics g)
	{
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
			
			// Mise à jour + affichage
			update();
			this.repaint();
			
			timeElapsed = (double) System.currentTimeMillis() - nowTime;
			
			// System.out.printf("%.2f \n", 
			// (float) (1000 / (1000 / Core.WANTED_FPS) - (timeElapsed)));
			
			if ((1000 / Core.WANTED_FPS) > (timeElapsed))
			{
				try {
					Thread.sleep(
							(long) ((1000 / Core.WANTED_FPS) - (timeElapsed)));
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
