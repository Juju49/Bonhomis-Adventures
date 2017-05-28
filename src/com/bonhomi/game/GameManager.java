package com.bonhomi.game;

import java.awt.Graphics2D;
import java.util.Timer;
import java.util.TimerTask;

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
	
	private SpriteOccurence monSprite;
	private SpriteLoader animation1;
	
	protected Player player1;
	private Timer damageTimer;
	
	protected GameUI GUI;
	
	// Constructeur
	public GameManager()
	{
		this.player1 = new Player(300, 300, 2);
		this.GUI = new GameUI();
		damageTimer = new Timer();
		
		init();
	}
	
	
	@Override
	public void init() 
	{
		monSprite = new SpriteOccurence(null, null, 100, 100, 0, 5, 5, 0, 0);
		animation1 = new SpriteLoader("Icons/", "winIcon", true, true, 250);
		animation1.start();
		
		player1.init();
		GUI.init();
		
		//on selectionne le joueur 1 comme celui a surveiller
		GUI.setPlayerFocus(player1);
		
		//gestion des degats avec un chronometre qui inflige toutes les 0.5s
		damageTimer = new Timer();
		damageTimer.scheduleAtFixedRate(
			new TimerTask()
			{
				@Override
				public void run() 
				{
					/* on retire des point de vie au joueur si il touche un
					 * ennmis et qu'il lui reste des vies
					 */
					if (player1.intersects(monSprite) && (player1.getVie() > 0))
					{
						player1.perdreVie();
						SoundSystemMaster.getInstance().ouille();
					}
					/*else
						damageTimer.cancel();*/
				}
			},
			0,
			300
		);
		
		initialized = true;
	}

	// Affichage
	@Override
	public void draw(Graphics2D g) 
	{
		monSprite.draw(g);
		player1.draw(g);
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
		GUI.update();
	
		monSprite.setImage(animation1.getActualImage());
	}

	@Override
	public void terminate() 
	{
		GUI.terminate();
		player1.terminate();
		
		initialized = false;
	}

}
