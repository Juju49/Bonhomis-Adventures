package com.bonhomi.game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import com.bonhomi.main.Core;
import com.bonhomi.main.Loopable;
import com.bonhomi.main.SpriteLoader;
import com.bonhomi.main.SpriteOccurence;

public class Room implements DoorsPosition, Loopable
{
	private final boolean[] doors;
	private final Point location;
	
	private SpriteOccurence salleOccurence;
	private final SpriteLoader salleSprite;
	
	private SpriteOccurence[] doorOccurences; 
	private final SpriteLoader doorSprite;
	
	private final ArrayList<Entity> entites;
	
	Room()
	{
		location = new Point(-1,-1);
		
		doorOccurences = new SpriteOccurence[4];
		doors = new boolean[4];
		
		salleSprite = new SpriteLoader("Rooms/", "background");
		salleOccurence = new SpriteOccurence(salleSprite.getActualImage(), 
				0, 0, 0, 1.0, 1.0);
		
		doorSprite = new SpriteLoader("Rooms/", "door");
		//init des portes
		for (int i = 0; i < 4; i++)
		{
			doors[i] = CLOSED;
			
			doorOccurences[i] = new SpriteOccurence(doorSprite.getActualImage(), 
					0, 0, Math.toRadians(i*90), 1.0, 1.0);
		}
		
		entites = new ArrayList<Entity>(4);
		
		init();
	}
	
	@Override
	public void init() {
		//positionnement des portes
		final int widthPortes = (int) doorSprite.getActualImage().getWidth();
		final int heightPortes = (int) doorSprite.getActualImage().getHeight();
		
		//top door
		doorOccurences[TOP].newTransforms(Core.WIDTH/2 - widthPortes/2, OFFSET_MURS - heightPortes,
				0, 1.0, 1.0);
		//left door
		doorOccurences[LEFT].newTransforms(OFFSET_MURS - heightPortes, Core.HEIGHT/2 - widthPortes/2,
				Math.PI/2, 1.0, 1.0);
		
		//bottom door
		doorOccurences[BOT].newTransforms(Core.WIDTH/2 - widthPortes/2, Core.HEIGHT - OFFSET_MURS,
				0, 1.0, 1.0);
		doorOccurences[BOT].setFlipY(true);
		
		//right door
		doorOccurences[RIGHT].newTransforms(Core.WIDTH - OFFSET_MURS, Core.HEIGHT/2 - widthPortes/2,
				Math.PI/2, 1.0, 1.0);
		doorOccurences[RIGHT].setFlipX(true);
		
		//création des ennemis
		entites.clear();
		int maxEnnemies = ThreadLocalRandom.current().nextInt(0, (int) (1*Core.DIFFICULTE));
		
		for (int i = 0; i < maxEnnemies; i++) 
		{
			int x = ThreadLocalRandom.current().nextInt(OFFSET_MURS, Core.WIDTH  - OFFSET_MURS - 128);
			int y =	ThreadLocalRandom.current().nextInt(OFFSET_MURS, Core.HEIGHT - OFFSET_MURS - 128);
			
			entites.add(new BadGuys(x, y, 1.5));
		}
		
		
		
		
		for (Entity e : entites)
		{
			e.init();
		}
	}
	
	@Override
	public void update()
	{
		//utilisation du sol
		final Shape[] compo_navigation = new Shape[5];
		compo_navigation[0] = new Rectangle(
				OFFSET_MURS, OFFSET_MURS, 
				Core.WIDTH  - OFFSET_MURS*2, Core.HEIGHT - OFFSET_MURS*2)
				.getBounds2D();
		
		//utilisation des portes
		for (int i=1; i < doors.length; i++)
		{
			if (doors[i] == OPENED)
				compo_navigation[i] = (doorOccurences[i].getBounds2D());
		}
		
		//on ajoute le tout au nav_mesh
		GameManager.nav_mesh.addNav(compo_navigation);
		
		
		for (Entity e : entites)
		{
			//colisions des entités
			GameManager.nav_mesh.addObs(e.ObsComp());
			
			if(e.isEnnemy())
			{
				BadGuys b_g = (BadGuys) e;
				
				Point suivre_joueur = new Point(
						(int) GameManager.player1.getCenterX(), 
						(int) GameManager.player1.getCenterY());
				
				if (b_g.Cible != null)
					b_g.Cible.setLocation(suivre_joueur);
				else
					b_g.Cible = suivre_joueur;
			}
			
			e.update();
		}
	}
	



	@Override
	public void draw(Graphics2D g) 
	{

		salleOccurence.draw(g);
		
		for (int i=0; i < doors.length; i++)
		{
			if (doors[i] == OPENED)
				g.setColor(Color.white);
				doorOccurences[i].draw(g);
		}
		
		for (Entity e : entites)
		{
			g.setColor(Color.red);
			e.draw(g);
		}
	}
	
	
	
	synchronized void setDoorsOpened(boolean top, boolean bot, boolean left, boolean right)
	{
		doors[TOP] = top;
		doors[BOT] = bot;
		doors[LEFT] = left;
		doors[RIGHT] = right;
	}
	
	boolean isDoorOpened(int door)
	{
		return doors[door];
	}
	
	public boolean[] getDoorsOpened()
	{
		return doors;
	}
	
	void setDoorOpened(int door, boolean value)
	{
		doors[door] = value;
	}
	
	Point getLocation()
	{
		return location;
	}
	synchronized void setLocation(int x, int y)
	{
		this.location.setLocation(x, y);
	}	
	synchronized void setLocation(Point p)
	{
		this.location.setLocation(p);
	}
	
	
	synchronized void newEntity(Entity e)
	{
		entites.add(e);
	}
	synchronized void delEntity(Entity e)
	{
		entites.remove(e);
	}
	Entity[] getEntityList()
	{
		Entity[] a = new Entity[entites.size()];
		entites.toArray(a);
		return a;
	}

	
	public int getNDoorsOpened()
	{
		int n = 0;
		
		for (int i = 0; i < doors.length; i++)
		{
			if (doors[i] == OPENED)
			{
				n++;
			}
		}
		
		return n;
	}


	@Override
	public void terminate() {
		for (int i = 0; i < 4; i++)
		{
			doors[i] = CLOSED;
			
			doorOccurences[i] = new SpriteOccurence(doorSprite.getActualImage(), 
					0, 0, Math.toRadians(i*90), 1.0, 1.0);
		}
		
		for (Entity e : entites)
		{
			e.terminate();
		}
		
		entites.clear();
	}


}
