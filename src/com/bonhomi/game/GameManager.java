package com.bonhomi.game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import com.bonhomi.main.Core;
<<<<<<< HEAD
import com.bonhomi.main.Dessin;
=======
>>>>>>> refs/heads/juju-br
import com.bonhomi.main.Loopable;

<<<<<<< HEAD
/**
 * Cette classe gère le jeu en lui même, le joueur,
 * les enemis, les niveaux...
 */
public class GameManager implements Loopable 
{
	// Constructeur
	public GameManager()
	{
		
	}
	
	// Initialisation
=======
public class GameManager implements Loopable {

	private boolean initialized = false;
	
>>>>>>> refs/heads/juju-br
	@Override
<<<<<<< HEAD
	public void init() 
	{
		
=======
	public void init() {
		initialized = true;
>>>>>>> refs/heads/juju-br
	}

	// Mise à jour
	@Override
<<<<<<< HEAD
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

	// Touche enfoncée
	@Override
	public void keyIsDown(KeyEvent e) 
	{
		
=======
	public void update() {
		if(!initialized) {
			Core.debugPrint(2, "Class Updated before Init!");
			assert false;
		}
>>>>>>> refs/heads/juju-br
	}

	// Touche relâchée
	@Override
	public void keyIsUp(KeyEvent e) 
	{
		
	}

	@Override
	public void terminate() {
		initialized = false;
		
	}

}
