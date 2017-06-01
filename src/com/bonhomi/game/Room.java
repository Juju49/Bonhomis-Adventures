package com.bonhomi.game;

import java.awt.Graphics2D;import com.bonhomi.main.Core;
import com.bonhomi.main.Loopable;
import com.bonhomi.main.SpriteLoader;
import com.bonhomi.main.SpriteOccurence;

public class Room implements DoorsPosition, Loopable
{
	private int[] doors;
	private int posI, posJ;
	
	private SpriteOccurence salleOccurence;
	private SpriteLoader salleSprite;
	
	private SpriteOccurence[] doorOccurences; 
	private SpriteLoader doorSprite;
	
	public Room()
	{
		
		doorOccurences = new SpriteOccurence[4];
		doors = new int[4];
		
		salleSprite = new SpriteLoader("Rooms/", "background");
		salleOccurence = new SpriteOccurence(salleSprite.getActualImage(), 
				0, 0, 0, 1.0, 1.0);
		
		doorSprite = new SpriteLoader("Rooms/", "door");
		for (int i = 0; i < 4; i++)
		{
			doors[i] = CLOSED;
			doorOccurences[i] = new SpriteOccurence(doorSprite.getActualImage(), 
					0, 0, Math.toRadians(i*90), 1.0, 1.0);
		}
		
		init();
	}
	
	@Override
	public void init() {
		final int offsetMurs = 100;
		final int widthPortes = (int) doorOccurences[0].getWidth();
		
		doorOccurences[0].newTransforms(Core.WIDTH/2 - widthPortes/2, 0, 0, 1.0, 1.0);
		doorOccurences[1].newTransforms(0, Core.HEIGHT/2 - widthPortes/2, Math.PI/2, 1.0, 1.0);
		doorOccurences[2].newTransforms(Core.WIDTH/2 - widthPortes/2, Core.HEIGHT-offsetMurs, -Math.PI, 1.0, 1.0);
		doorOccurences[3].newTransforms(Core.WIDTH-offsetMurs, Core.HEIGHT/2 - widthPortes/2, -Math.PI/2, 1.0, 1.0);
		
	}
	
	@Override
	public void update()
	{
		
	}
	



	@Override
	public void draw(Graphics2D g) 
	{

		salleOccurence.draw(g);
		
		if (doors[TOP] == OPENED)
		{
			doorOccurences[TOP].draw(g);
		}
		if (doors[BOT] == OPENED)
		{
			doorOccurences[BOT].draw(g);
		}
		if (doors[LEFT] == OPENED)
		{
			doorOccurences[LEFT].draw(g);
		}
		if (doors[RIGHT] == OPENED)
		{
			doorOccurences[RIGHT].draw(g);
		}
	}


	
	public void setDoors(int top, int bot, int left, int right)
	{
		doors[TOP] = top;
		doors[BOT] = bot;
		doors[LEFT] = left;
		doors[RIGHT] = right;
	}
	
	public void setDoor(int door, int value)
	{
		doors[door] = value;
	}
	
	public void setPos(int i, int j)
	{
		this.posI = i;
		this.posJ = j;
	}
	
	public void setPosI(int i)
	{
		posI = i;
	}
	
	public void setPosJ(int j)
	{
		posJ = j;
	}
	
	public int getPosI()
	{
		return posI;
	}
	
	public int getPosJ()
	{
		return posJ;
	}
	
	public int[] getDoors()
	{
		return doors;
	}
	
	public int getDoor(int door)
	{
		return doors[door];
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
		// TODO Auto-generated method stub
		
	}
}
