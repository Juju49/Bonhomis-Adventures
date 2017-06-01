package com.bonhomi.game;

import java.util.ArrayList;
import java.util.Random;

import com.bonhomi.main.Core;

public class Niveau implements DoorsPosition
{
	private Room[][] map;
	private ArrayList<Room> rooms;
	
	private Room actualRoom;
	private boolean finished;
	private int roomsCount;
	
	Niveau()
	{
		map = new Room[Core.MAP_HEIGHT][Core.MAP_WIDTH];
		rooms = new ArrayList<Room>();
		finished = false;
		roomsCount = Core.MAP_HEIGHT * Core.MAP_WIDTH;
		
		for (int i = 0; i < Core.MAP_HEIGHT; i++)
		{
			for (int j = 0; j < Core.MAP_WIDTH; j++)
			{
				map[i][j] = null;
			}
		}
		
		creerNiveau();
	}
	
	public void update()
	{
		if (actualRoom != null)
		{
			actualRoom.update();
		}
	}
	
	public void draw()
	{
		if (actualRoom != null)
		{
			actualRoom.draw();
		}
	}
	
	private void creerNiveau()
	{
		finished = false;
		
		while (!finished)
		{
			creerSalle();
		}
	}
	
	private void creerSalle()
	{	
		if (rooms.size() >= roomsCount)
		{
			finished = true;
			return;
		}
		assert rooms.size() == roomsCount;
		
		Random rand = new Random();
		
		Room room = new Room();
		
		if (rooms.size() != 0)
		{
			int choice = 0;
			choice = rand.nextInt(rooms.size());
			room = rooms.get(choice);
		}
		else
		{
			room.setPos(rand.nextInt(Core.MAP_HEIGHT), rand.nextInt(Core.MAP_WIDTH));
		}
		
		boolean canCreate = false;
		int wall = 0;
		
		if (room.getNDoorsOpened() == 4)
			return;
		
		while (!canCreate)
		{
			wall = rand.nextInt(4);
			if (room.getDoor(wall) == CLOSED)
				canCreate = true;
		}
		
		int ni = room.getPosI(), nj = room.getPosJ();
		
		switch(wall)
		{
			case TOP:
				ni--;
				break;
			case BOT:
				ni++;
				break;
			case LEFT:
				nj--;
				break;
			case RIGHT:
				nj++;
				break;
			default:
				
				break;
		}
		
		if (canCreateRoom(ni, nj) == true)
		{
			Room newRoom = new Room();
			newRoom.setPos(ni, nj);
			
			System.out.println(ni+ "    " + nj + "   " + wall);
			room.setDoor(wall, OPENED);
			newRoom.setDoor(invertWall(wall), OPENED);
			
			rooms.add(newRoom);
			map[ni][nj] = newRoom;
		}
	}
	
	public boolean canCreateRoom(int i, int j)
	{
		boolean can = false;
		
		if (i < 0 || i >= Core.MAP_HEIGHT ||
				j < 0 || j >= Core.MAP_WIDTH)
		{
			can = false;
			return can;
		}

		if (map[i][j] == null)
			can = true;
		
		System.out.println(map[i][j]);
		
		return can;
	}
	
	public void printMap()
	{
		for (int i = 0; i < Core.MAP_HEIGHT; i++)
		{
			for (int j = 0; j < Core.MAP_WIDTH; j++)
			{
				if (map[i][j] == null)
				{
					System.out.print("x");
				}
				else
				{
					Room room = map[i][j];
					if (room.getDoor(TOP) == OPENED)
					{
						System.out.print("t");
					}
					if (room.getDoor(BOT) == OPENED)
					{
						System.out.print("b");
					}
					if (room.getDoor(LEFT) == OPENED)
					{
						System.out.print("l");
					}
					if (room.getDoor(RIGHT) == OPENED)
					{
						System.out.print("r");
					}
				}
				System.out.print(" ");
			}
			
			System.out.print("\n\n");
		}
	}
	
	private int invertWall(int wall)
	{
		if (wall == TOP)
		{
			return BOT;
		}
		else if (wall == BOT)
		{
			return TOP;
		}
		else if (wall == LEFT)
		{
			return RIGHT;
		}
		else if (wall == RIGHT)
		{
			return LEFT;
		}
		else
		{
			Core.out.println("erreur");
		}
		
		return 0;
	}
}
