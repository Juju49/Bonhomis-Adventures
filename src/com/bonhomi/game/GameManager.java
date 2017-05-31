package com.bonhomi.game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.GeneralPath;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.util.Timer;
import java.util.TimerTask;

import com.bonhomi.main.Core;
import com.bonhomi.main.Loopable;
import com.bonhomi.main.MainClass;
import com.bonhomi.main.SpriteLoader;
import com.bonhomi.main.SpriteOccurence;
import com.bonhomi.sounds.SoundSystemMaster;


/**
 * Cette classe gere le jeu en lui meme, le joueur,
 * les enemis, les niveaux...
 */
public class GameManager implements Loopable {

	static private boolean initialized = false;
	static final Rectangle window = new Rectangle(0, 0, Core.WIDTH, Core.HEIGHT);

	static Player player1;
	static BadGuys[] ennemis;
	static NavMesh nav_mesh;
	
	static private Timer damageTimer;
	
	static GameUI GUI;
	
	//création d'un nav_mesh pour le niveau
	private static final int offsetMurs = 100;
	private static final int widthPortes = 75;
	private static final Shape[] compo_navigation = {
			//salle 0
			new Rectangle(offsetMurs, offsetMurs, Core.WIDTH-2*offsetMurs, Core.HEIGHT-2*offsetMurs)
			.getBounds2D(),
			
			//porte haute 1
			new Rectangle(Core.WIDTH/2 - widthPortes/2, 0, widthPortes, offsetMurs)
			.getBounds2D(),
			//porte basse 2
			new Rectangle(Core.WIDTH/2 - widthPortes/2, Core.HEIGHT-offsetMurs, widthPortes, offsetMurs)
			.getBounds2D(),
			
			//porte gauche 3
			new Rectangle(0, Core.HEIGHT/2 - widthPortes/2, offsetMurs, widthPortes)
			.getBounds2D(),
			//porte droite 4
			new Rectangle(Core.WIDTH-offsetMurs, Core.HEIGHT/2 - widthPortes/2, offsetMurs, widthPortes)
			.getBounds2D(),
	};
			
	private static final Shape[] compo_obstacle = {
			//pillier central 0
			new RoundRectangle2D.Double(
					Core.WIDTH/2 - widthPortes, Core.HEIGHT/2 - widthPortes, 
					widthPortes*2, widthPortes*2, 
					widthPortes*2, widthPortes*2)
	};
	
	// Constructeur
	public GameManager()
	{
		this.GUI = new GameUI();
		damageTimer = new Timer("GestionDegats");
		nav_mesh = new NavMesh();
		
		init();
	}
	
	/**
	 * Procedure gerant les degats sur le joueur avec un chronometre et des 
	 * frames d'invincibilite.
	 */
	private void playerDamage()
	{
		//gestion des degats avec un chronometre qui inflige toutes les 0.3s
		damageTimer.scheduleAtFixedRate(
			new TimerTask()
			{
				@Override
				public void run() 
				{
					for (BadGuys ennemi : ennemis)
					{
						/* on retire des point de vie au joueur si il touche un
						 * ennmis et qu'il lui reste des vies
						 */
						if (player1.intersects(ennemi) && (player1.getVie() > 0))
						{
							player1.perdreVie();
							SoundSystemMaster.getInstance().ouille();
						}
					}
				}
			},
			0,
			Core.DELAIS_INVULNERABILITE
		);
	}
	
	@Override
	public void init()
	{
		player1 = new Player(200, 300, 1);
		
		//initialisations...
		player1.init();
		GUI.init();
		damageTimer.purge();
		nav_mesh.purge();

		//on ajoute le tout au nav_mesh
		nav_mesh.addNav(compo_navigation);
		nav_mesh.addObs(compo_obstacle);
		
		//on selectionne le joueur 1 en tant qu'entité a surveiller avec l'UI
		GUI.setPlayerFocus(player1);
		
		//on cree les ennemis sur le terrain
		ennemis = new BadGuys[2];
		ennemis[0] = new BadGuys(200, 110, 1);
		ennemis[1] = new BadGuys(400, 110, 1);
		
		for(BadGuys b_g : ennemis)
		{
			b_g.init();
		}
		
		//on lance le fil d'execution gerant les degats:
		playerDamage();
		
		initialized = true;
	}

	// Affichage
	@Override
	public void draw(Graphics2D g) 
	{
		if( MainClass.getDebugLvl() > 2)
		{
			g.setColor(Color.yellow);
			g.fill(nav_mesh);
			g.setColor(Color.orange);
			g.draw(nav_mesh);

		}
		
		g.setColor(Color.red);
		for(BadGuys b_g : ennemis)
		{
			b_g.draw(g);
		}
		
		g.setColor(Color.green);
		player1.draw(g);
		

		
		g.setColor(Color.blue);
		GUI.draw(g);
	}
	
	/**
	 * mise a jour.
	 */
	public void update() 
	{
		if(!initialized) 
			throw new IllegalStateException("Class Updated before Init!");
		
		player1.update();

		//on purge le nav_mesh ici pour que les ennemis puisse toucher le joueur.
		nav_mesh.purge();
		//on ajoute le tout au nav_mesh et la case obstacle du joueur
		nav_mesh.addNav(compo_navigation);
		nav_mesh.addObs(compo_obstacle);
		nav_mesh.addObs(player1.ObsComp());
		
		GUI.update();
		
		for(BadGuys b_g : ennemis)
		{
			//les ennemis sont dans la fenetre de jeu avant traitement
			if (window.contains(NavMesh.getNavPoint(b_g)))
			{
				//on fait en sorte que les ennemis ne se supperposent pas
				nav_mesh.addObs(b_g.ObsComp());
				
				Point suivre_joueur = new Point((int) player1.getCenterX(), (int) player1.getCenterY());
				
				if (b_g.Cible != null)
					b_g.Cible.setLocation(suivre_joueur);
				else
					b_g.Cible = suivre_joueur;
				
				b_g.update();
			}
			else
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
