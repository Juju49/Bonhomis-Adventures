package com.bonhomi.game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import com.bonhomi.main.Core;
import com.bonhomi.main.Dessin;
import com.bonhomi.main.Loopable;

/**
 * Cette classe g�re le jeu en lui m�me, le joueur,
 * les enemis, les niveaux...
 */
public class GameManager implements Loopable 
{
	// Constructeur
	public GameManager()
	{
		
	}
	
	// Initialisation
	@Override
	public void init() 
	{
		
	}

	// Mise � jour
	@Override
	public void update() 
	{
		if (InputManager.isKeyDown('e') && InputManager.isKeyDown('a'))
			Core.out("EEEEEEEEE");
	}

	// Affichage
	@Override
	public void draw(Graphics2D g) 
	{
		Dessin.clear(g, Color.white);
	}

	// Touche enfonc�e
	@Override
	public void keyIsDown(KeyEvent e) 
	{
		
	}

	// Touche rel�ch�e
	@Override
	public void keyIsUp(KeyEvent e) 
	{
		
	}

}
