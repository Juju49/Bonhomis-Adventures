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
	
	protected Niveau niveau1;
	
	// Constructeur
	public GameManager()
	{
		this.player1 = new Player(300, 300, 1);
		this.niveau1 = new Niveau();
		init();
	}
	
	
	@Override
	public void init() 
	{
		monSprite = new SpriteOccurence(null, null, 100, 100, 0, 1, 1, 0, 0);
		animation1 = new SpriteLoader("Icons/", "winIcon", true, true, 250);
		animation1.start();
		
		player1.init();
		niveau1.printMap();
		
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
		niveau1.printMap();
		
		if (player1.intersects(monSprite))
		{
			player1.perdreVie();
		}
		
		monSprite.setImage(animation1.getActualImage());
	}

	@Override
	public void terminate() 
	{
		player1.terminate();
		
		initialized = false;
	}

}
