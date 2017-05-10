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
		init();
	}
	
	
	@Override
	public void init() 
	{
		initialized = true;
	}

	// Affichage
	@Override
	public void draw(Graphics2D g) 
	{
		//inutile, déjà nettoyé par le paintComponent de Afficheur.
		//Dessin.clear(g, Color.white);
	}

	/**
	 * mise a jour.
	 */
	public void update() 
	{
		if(!initialized) 
			throw new IllegalStateException("Class Updated before Init!");
		
		if (InputManager.isKeyDown(KeyEvent.VK_A))
			System.out.println("BLBLBLBL");
		if (InputManager.mouseLeftCliked())
			System.out.println("RERERERERE");
	}

	@Override
	public void terminate() {
		initialized = false;
		
	}

}
