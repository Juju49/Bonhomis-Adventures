package com.bonhomi.game;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import com.bonhomi.main.InputManager;
import com.bonhomi.main.Loopable;
import com.bonhomi.main.SpriteLoader;
import com.bonhomi.main.SpriteOccurence;


/**
 * Cette classe gere le jeu en lui meme, le joueur,
 * les enemis, les niveaux...
 */
public class GameManager implements Loopable {

	private boolean initialized = false;
	private SpriteOccurence monSprite;
	private SpriteLoader animation1;
	protected Player player1;
	
	// Constructeur
	public GameManager()
	{
		this.player1 = new Player();
		init();
	}
	
	
	@Override
	public void init() 
	{
		monSprite = new SpriteOccurence(null, null, 300, 300, 0, 5, 5, 0, 0);
		animation1 = new SpriteLoader("Icons/", "winIcon", true, true, 250);
		animation1.start();
		
		player1.init();
		
		initialized = true;
	}

	// Affichage
	@Override
	public void draw(Graphics2D g) 
	{
		monSprite.draw(g);
		player1.draw(g);
	}

	/**
	 * mise a jour.
	 */
	public void update() 
	{
		if(!initialized) 
			throw new IllegalStateException("Class Updated before Init!");
		
		player1.update();
		monSprite.setImage(animation1.getActualImage());
	}

	@Override
	public void terminate() 
	{
		player1.terminate();
		
		initialized = false;
	}

}
