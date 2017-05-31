/**
 * 
 */
package com.bonhomi.game;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.concurrent.ThreadLocalRandom;

import com.bonhomi.main.Core;
import com.bonhomi.main.InputManager;
import com.bonhomi.main.Loopable;
import com.bonhomi.main.SpriteLoader;
import com.bonhomi.main.SpriteOccurence;

/**
 * @author Julian
 *
 */
public class BadGuys extends Rectangle implements Loopable {
	private static final long serialVersionUID = 1L;

	protected boolean initialized = false;
	
	private int life;
	private double speed;
	
	protected SpriteOccurence npc; //(Non Playable Charater) designe les persos joues par l'ordinateur
	protected SpriteLoader[] anims = new SpriteLoader[3];
	protected double scale; //taille de l'occurence par rapport a la taille de l'image d'origine
	
	/**
	 * le centre du npc va se deplacer jusqu'a ce point de l'ecran si il le peut
	 */
	public Point Cible;
	
	/**
	 * 
	 */
	public BadGuys(int x, int y, double scale)
	{
		this.scale = scale;
		this.x = x;
		this.y = y;
		this.life = ThreadLocalRandom.current().nextInt(
				Core.MAX_VIE, //vie mini
				(int) (Core.MAX_VIE * Core.DIFFICULTE)); /*vie maxi possible
				(DIFFICULTE est un double)*/
		
		/**
		 * En pixels par secondes, genere aleatoirement au lancement en 
		 * fonction de la difficulte. Entre 1 et 1+DIFFICULTE
		 */
		this.speed = ThreadLocalRandom.current().nextInt(1, 1 + (int) Core.DIFFICULTE);
	}

	/* (non-Javadoc)
	 * @see com.npc.main.Loopable#init()
	 */
	@Override
	public void init() {
		//on charge les sprites utilises pour le joueur
		anims[0] = new SpriteLoader("Characters/badguys/", "avant", 
				true, true, 100);
		anims[1] = new SpriteLoader("Characters/badguys/", "gauche", 
				true, true, 100);
		anims[2] = new SpriteLoader("Characters/badguys/", "mort");
		
		
		//on charge un gestionnaire d'affichage pour les sprites
		npc = new SpriteOccurence(anims[0].getActualImage(), x, y, 0, scale, scale);
		this.setBounds(this.npc); //colecte des dimensions et coordonnees
		
		//l'initialisation est termine si le gestionnaire de sprites est charge
		initialized = (npc != null ? true : false);
	}

	/* (non-Javadoc)
	 * @see com.npc.main.Loopable#update()
	 */
	@Override
	public void update() {
		if(!initialized) 
			throw new IllegalStateException("Class Updated before Init!");
		
		//index de l'animation a jouer
		int actual_anim = 0;
		//deplacement en pixels du personnage
		int delta_x = 0;
		int delta_y = 0;
		
		/*le perso bouge si il n'a pas atteint sa cible et
		 *  que cette derniere existe
		 */
		if(!npc.contains(Cible) && (Cible != null))
		{
			if(npc.getTrackingPoint().getX() < Cible.getX())
			{
				delta_x += speed;
				actual_anim = 0;
			}
			if(npc.getTrackingPoint().getX() > Cible.getX())
			{
				delta_x -= speed;
				actual_anim = 0;
			}
			if(npc.getTrackingPoint().getY() < Cible.getY())
			{
				delta_y += speed;
				npc.setFlipX(true);
				actual_anim = 1;
			}
			if(npc.getTrackingPoint().getY() > Cible.getY())
			{
				delta_y -= speed;
				npc.setFlipX(false);
				actual_anim = 1;
			}
		}
		
		if((delta_y == 0) && (delta_x == 0))
		{
			if(anims[actual_anim].isPlaying()) anims[actual_anim].stop(true);
		}
		else
		{
			if(!anims[actual_anim].isPlaying()) anims[actual_anim].start();
		}
		
		
		if(this.life <= 0)
		{
			delta_x = 0;
			delta_y = 0;
			npc.setFlipX(false);
			npc.setFlipY(false);
			actual_anim = 2;
		}
		
		npc.newTransforms(x + delta_x, y + delta_y, 0, scale, scale);
		npc.setImage(anims[actual_anim].getActualImage());
		this.setBounds(this.npc);
	}

	/* (non-Javadoc)
	 * @see com.npc.main.Loopable#draw(java.awt.Graphics2D)
	 */
	@Override
	public void draw(Graphics2D g) {
		npc.draw(g);
			
		/*
		Core.out.println("npc: /n pos-npc: ");
		Core.out.println(npc.getTrackingPoint().x );
		Core.out.println(npc.getTrackingPoint().y );
		Core.out.println("pos-target: ");
		Core.out.println(Cible);
		*/
	}

	/* (non-Javadoc)
	 * @see com.npc.main.Loopable#terminate()
	 */
	@Override
	public void terminate() {
		npc = null;
		anims = null;
		
		initialized = false;
	}
}