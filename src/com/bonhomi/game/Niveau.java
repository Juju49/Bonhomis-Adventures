package com.bonhomi.game;

import java.awt.Graphics2D;
import java.awt.Point;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Random;

import com.bonhomi.main.Core;
import com.bonhomi.main.Loopable;

public class Niveau implements DoorsPosition, Loopable
{
	private Room[][] map;
	private ArrayList<Room> rooms;
	
	private int actualRoom;
	private boolean isMapFinished;
	private final int roomsCount;
	
	private final Thread generationNiveau;
	
	Niveau()
	{
		map = new Room[Core.MAP_WIDTH][Core.MAP_HEIGHT];
		
		actualRoom = 0;
		roomsCount = Core.MAP_HEIGHT * Core.MAP_WIDTH;
		rooms = new ArrayList<Room>(roomsCount);
		isMapFinished = false;
		
		NivGen ng = new NivGen();
		generationNiveau = new Thread(ng, "LevelGenerator");
	}
	
	public void startLoading()
	{
		if (!generationNiveau.isAlive() && !isMapFinished)
			generationNiveau.start();
	}
	public boolean isLoading()
	{
		return generationNiveau.isAlive();
	}
	public boolean isLoaded()
	{
		return isMapFinished;
	}
	
	@Override
	public void init()
	{
		if(!isMapFinished) return;
		
		for( Room room : rooms)
			room.init();
	}
	
	public Room getActualRoom()
	{
		if(!isMapFinished) return null;
			
		if (rooms.get(actualRoom) == null)
			actualRoom = 0;
		
		return rooms.get(actualRoom);
		
	}
	@Override
	public void update()
	{
		if(!isMapFinished) return;
		
		if (rooms.get(actualRoom) == null)
			actualRoom = 0;
		
		rooms.get(actualRoom).update();
	}
	
	@Override
	public void draw(Graphics2D g)
	{
		if(!isMapFinished) return;
		
		Core.out.println("actual room: " + rooms.get(actualRoom));
		if (rooms.get(actualRoom) != null)
		{
			rooms.get(actualRoom).draw(g);
		}
	}
	
	private class NivGen implements Runnable
	{

		@Override
		public void run() {
			creerNiveau();
			return;
		}
		
		private void creerNiveau()
		{
			for (int y = 0; y < Core.MAP_HEIGHT; y++)
			{
				for (int x = 0; x < Core.MAP_WIDTH; x++)
				{
					map[x][y] = null;
				}
			}
			rooms.clear();
			isMapFinished = false;
			
			for(;rooms.size() < roomsCount;)
			{
				creerSalle();
			}
			assert rooms.size() == roomsCount;
			
			placerItemsVictoire();
			
			isMapFinished = true;
		}
		
		private void placerItemsVictoire()
		{
			int nbItems = 0;
			
			Random rand = new Random();
			ArrayList<Integer> nombresExclus = new ArrayList<Integer>();
			Room room;
			
			nombresExclus.add(0);
			
			while(nbItems < 4)
			{
				int hasard = rand.nextInt(roomsCount);
				
				if(nombresExclus.contains(hasard))
					continue;
				
				room = rooms.get(hasard);
				if(room.getNDoorsOpened() == 1)
				{
					//TODO victory
					//e = VictoryItem(nbItems);
					//room.newEntity(e);
					nbItems++;
				}
				
				nombresExclus.add(hasard);
				
			}
		}
		
		private void creerSalle()
		{	

	
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
				room.setLocation(rand.nextInt(Core.MAP_WIDTH), rand.nextInt(Core.MAP_HEIGHT));
			}
			
			boolean canCreate = false;
			int wall = 0;
			
			if (room.getNDoorsOpened() == 4)
				return;
			
			while (!canCreate)
			{
				wall = rand.nextInt(4);
				
				assert ((wall >= 0) && (wall <= 4));
				
				if (room.isDoorOpened(wall) == CLOSED)
					canCreate = true;
			}
			
			Point roomPos = room.getLocation();
			
			switch(wall)
			{
				case TOP:
					roomPos.y--;
					break;
					
				case LEFT:
					roomPos.x--;
					break;
					
				case BOT:
					roomPos.y++;
					break;
	
				case RIGHT:
					roomPos.x++;
					break;
					
				default:
			}
			
			if (canCreateRoom(roomPos) == true)
			{
				Room newRoom = new Room();
				newRoom.setLocation(roomPos);
				
				room.setDoorOpened(wall, OPENED);
				newRoom.setDoorOpened(invertWall(wall), OPENED);
				
				rooms.add(newRoom);
				map[roomPos.x][roomPos.y] = newRoom;
			}
		}
		
		public boolean canCreateRoom(Point p)
		{
			//évite d'utiliser un try/catch hors des limites
			if (((p.y < 0) || (p.y >= Core.MAP_HEIGHT)) ||
					((p.x < 0) || (p.x >= Core.MAP_WIDTH)))
				return false;
	
			if (map[p.x][p.y] == null)
				return true;
			
			return false;
		}
		
	}
	public void printMap()
	{
		if(!isMapFinished) return;
		
		for (int y = 0; y < Core.MAP_HEIGHT; y++)
		{
			for (int x = 0; x < Core.MAP_WIDTH; x++)
			{
				if (map[x][y] == null)
				{
					System.out.print("x");
				}
				else
				{
					Room room = map[x][y];
					if (room.isDoorOpened(TOP) == OPENED)
					{
						System.out.print("t");
					}
					if (room.isDoorOpened(BOT) == OPENED)
					{
						System.out.print("b");
					}
					if (room.isDoorOpened(LEFT) == OPENED)
					{
						System.out.print("l");
					}
					if (room.isDoorOpened(RIGHT) == OPENED)
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
		switch (wall)
		{
		case TOP:
			return BOT;

		case BOT :
			return TOP;

		case LEFT :
			return RIGHT;

		case RIGHT :
			return LEFT;

		default :
			Core.out.println("erreur de wall invalide");
		}
		
		return -1;
	}
	@Override
	public void terminate() {
		if(!isMapFinished) return;
		
		for( Room room : rooms)
			room.terminate();
	}
}
