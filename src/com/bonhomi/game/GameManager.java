package com.bonhomi.game;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.Timer;
import java.util.TimerTask;

import com.bonhomi.main.Core;
import com.bonhomi.main.Loopable;
import com.bonhomi.main.SpriteLoader;
import com.bonhomi.main.SpriteOccurence;
import com.bonhomi.sounds.SoundSystemMaster;


/**
 * Cette classe gere le jeu en lui meme, le joueur,
 * les enemis, les niveaux...
 */
public class GameManager implements Loopable {

	private boolean initialized = false;

	protected Player player1;
	protected BadGuys[] ennemis;
	protected NavMesh nav_mesh;
	private Timer damageTimer;
	
	protected GameUI GUI;
	
	// Constructeur
	public GameManager()
	{
		this.GUI = new GameUI();
		damageTimer = new Timer("GestionDegats");
		nav_mesh = new NavMesh();
		nav_mesh.add( new Rectangle(100, 100, Core.WIDTH-100, Core.HEIGHT-100).getBounds2D());
		
		init();
	}
	
	private void playerDamage(Rectangle ennemi)
	{
		//gestion des degats avec un chronometre qui inflige toutes les 0.3s
		damageTimer.scheduleAtFixedRate(
			new TimerTask()
			{
				@Override
				public void run() 
				{
					/* on retire des point de vie au joueur si il touche un
					 * ennmis et qu'il lui reste des vies
					 */
					if (player1.intersects(ennemi) && (player1.getVie() > 0))
					{
						player1.perdreVie();
						SoundSystemMaster.getInstance().ouille();
					}
					else
						this.cancel();
				}
			},
			0,
			Core.DELAIS_INVULNERABILITE
		);
	}
	
	@Override
	public void init() 
	{
		player1 = new Player(500, 400, 2);
		
		player1.init();
		GUI.init();
		damageTimer.purge();
		
		//on selectionne le joueur 1 comme celui a surveiller
		GUI.setPlayerFocus(player1);
		
		ennemis = new BadGuys[1];
		ennemis[0] = new BadGuys(100, 100, 2);
		
		for(BadGuys b_g : ennemis)
		{
			b_g.init();
			playerDamage(b_g);
		}
		
		initialized = true;
	}

	// Affichage
	@Override
	public void draw(Graphics2D g) 
	{
		player1.draw(g);
		GUI.draw(g);
		
		for(BadGuys b_g : ennemis)
		{
			b_g.draw(g);
		}
	}
	
	/**
	 * mise a jour.
	 */
	public void update() 
	{
		if(!initialized) 
			throw new IllegalStateException("Class Updated before Init!");
		
		player1.update();
		GUI.update();
		
		for(BadGuys b_g : ennemis)
		{
			//les ennemis sont dans la fenetre de jeu avant traitement
			if (nav_mesh.contains(b_g.npc.getTrackingPoint()))
			{
				if (b_g.Cible != null)
					b_g.Cible.setLocation(player1.getCenterX(), player1.getCenterY());
				else
					b_g.Cible = new Point((int) player1.getCenterX(), (int) player1.getCenterY());
				b_g.update();
			}
			else
				//Core.out.println("b_g stopped");
				b_g.Cible = null;
		}
	}

	@Override
	public void terminate() 
	{
		GUI.terminate();
		player1.terminate();
		
		initialized = false;
	}

}
