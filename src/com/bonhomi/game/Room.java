package com.bonhomi.game;

public class Room implements DoorsPosition
{
	private int[] doors;
	private int posI, posJ;
	
	public Room()
	{
		doors = new int[4];
		for (int i = 0; i < 4; i++)
		{
			doors[i] = CLOSED;
		}
	}
	
	public Room(int top, int bot, int left, int right)
	{
		doors[TOP] = top;
		doors[BOT] = bot;
		doors[LEFT] = left;
		doors[RIGHT] = right;
	}
	
	public void update()
	{
		
	}
	
	public void draw()
	{
		
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
}
