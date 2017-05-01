package com.bonhomi.game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import com.bonhomi.main.Core;
import com.bonhomi.main.Dessin;
import com.bonhomi.main.InputManager;
import com.bonhomi.main.Loopable;


/**
 * Cette classe gere le jeu en lui meme, le joueur,
 * les enemis, les niveaux...
 */
public class GameManager implements Loopable {

	private boolean initialized = false;
	
	// Constructeur
	public GameManager()
	{
		this.init();
	}
	
	
	@Override
	public void init() {
		initialized = true;
	}

	// Affichage
	@Override
	public void draw(Graphics2D g) 
	{
		Dessin.clear(g, Color.white);
	}

	/**
	 * mise a jour.
	 */
	public void update() {
		if(!initialized) throw new IllegalStateException("Class Updated before Init!");
		
		if (InputManager.isKeyDown('e') && InputManager.isKeyDown('a'))
			Core.out("EEEEEEEEE");
		
	}

	@Override
	public void terminate() {
		initialized = false;
		
	}

}
