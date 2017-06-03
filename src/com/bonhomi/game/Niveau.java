package com.bonhomi.game;

import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import com.bonhomi.main.Core;
import com.bonhomi.main.Loopable;

public class Niveau implements DoorsPosition, Loopable
{
	static protected Room[][] map;
	static private Room actualRoom;
	
	private Thread tNivGen;
	
	
	Niveau()
	{
		map = new Room[Core.MAP_WIDTH][Core.MAP_HEIGHT];
		
		for (int x = 0; x < Core.MAP_WIDTH; x++)
		{
			for (int y = 0; y < Core.MAP_HEIGHT; y++)
			{
				map[x][y] = null;
			}
		}
		
		NivGen ng = new NivGen();
		tNivGen = new Thread(ng, "level generator");
	}
	
	@Override
	public void init() {
		for (int x = 0; x < Core.MAP_WIDTH; x++)
		{
			for (int y = 0; y < Core.MAP_HEIGHT; y++)
			{
				map[x][y].init();
			}
		}
	}
	
	@Override
	public void update()
	{
		if (actualRoom != null)
			actualRoom.update();
	}
	
	@Override
	public void draw(Graphics2D g)
	{
		//Core.out.println("actual room: " + actualRoom);
		if (actualRoom != null)
			actualRoom.draw(g);
	}
	
	@Override
	public void terminate() {

		
		
		for (int x = 0; x < Core.MAP_WIDTH; x++)
		{
			for (int y = 0; y < Core.MAP_HEIGHT; y++)
			{
				try 
				{
					map[x][y].terminate();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				map[x][y] = null;
			}
		}
	}
	
	
	
	
	public Room getActualRoom() {
		return actualRoom;
	}
	
	public void setActualRoom(int x, int y, DoorSide appearFrom)
	{
		actualRoom = map[x][y];
		
		if (actualRoom != null)
			map[x][y].setPlayerAtDoor(GameManager.player1, appearFrom);
		else
			throw new IllegalArgumentException("Room (" + x + ";" + y + ") doesn't exist! ");
	}
	
	public void printMap()
	{
		for (int x = 0; x < Core.MAP_WIDTH; x++)
		{
			for (int y = 0; y < Core.MAP_HEIGHT; y++)
			{
				if (map[x][y] == null)
				{
					System.out.print("x");
				}
				else
				{
					Room room = map[x][y];
					if (room.isDoorOpened(DoorSide.TOP) == OPENED)
					{
						System.out.print("t");
					}
					if (room.isDoorOpened(DoorSide.BOT) == OPENED)
					{
						System.out.print("b");
					}
					if (room.isDoorOpened(DoorSide.LEFT) == OPENED)
					{
						System.out.print("l");
					}
					if (room.isDoorOpened(DoorSide.RIGHT) == OPENED)
					{
						System.out.print("r");
					}
				}
				System.out.print(" ");
			}
			
			System.out.print("\n\n");
		}
	}
	
	static DoorSide invertWall(DoorSide wall)
	{
		switch(wall)
		{
			case TOP:
				return DoorSide.BOT;
				
			case BOT:
				return DoorSide.TOP;
				
			case LEFT:
				return DoorSide.RIGHT;
				
			case RIGHT:
				return DoorSide.LEFT;
				
			default:
				throw new IllegalArgumentException(wall + " is not a valid wall index!");
		}
	}

//compatibilité avec "Niveau2"
	public boolean isLoaded() 
	{
		//la carte est-elle chargée?
		boolean unloadedRooms = false;
		for (int x = 0; x < Core.MAP_WIDTH; x++)
		{
			for (int y = 0; y < Core.MAP_HEIGHT; y++)
			{
				//on passe unloadedRooms à "vrai" si au moins une salle n'est pas chargée
				unloadedRooms = (map[x][y] == null ? true : unloadedRooms);
			}
		}
		
		return (!tNivGen.isAlive() && !unloadedRooms);
	}

	public void startLoading() 
	{
		tNivGen.start();
	}
	
	
	
	
	
	
	
	
	
	private class NivGen implements Runnable
	{
		private boolean finished = false;
		private Random rand = ThreadLocalRandom.current();
		
		private int roomsCount = Core.MAP_HEIGHT * Core.MAP_WIDTH;
		private ArrayList<Room> rooms = new ArrayList<Room>(roomsCount);
		private Room[][] genMap = new Room[Core.MAP_WIDTH][Core.MAP_HEIGHT];
		
		@Override
		public void run()
		{
			genMap = new Room[Core.MAP_WIDTH][Core.MAP_HEIGHT];
			
			for (int x = 0; x < Core.MAP_WIDTH; x++)
			{
				for (int y = 0; y < Core.MAP_HEIGHT; y++)
				{
					genMap[x][y] = null;
				}
			}
			
			creerNiveau();
			setMap();
			Core.out.println("----------------< MAP LOADED >----------------\n");
			return;
		}
		
		private void setMap() {
			Niveau.map = genMap;
		}

		private void creerNiveau()
		{
			finished = false;
			
			while (!finished)
			{
				creerSalle();
			}
			
			actualRoom = genMap[rand.nextInt(Core.MAP_WIDTH-1)][rand.nextInt(Core.MAP_HEIGHT-1)];
			
			placerItemsVictoire();
		}
		
		private void creerSalle()
		{	
			if (rooms.size() >= roomsCount)
			{
				finished = true;
				assert rooms.size() == roomsCount;
				
				Core.out.println("map ------------ FINISHED ----------------\n");
				return;
			}
	
			Room room = new Room();
			int x;
			int y;
			
			if (rooms.size() > 0)
			{
				int choice = 0;
				choice = rand.nextInt(rooms.size());
				room = rooms.get(choice);
				
				x = room.getLocation().x;
				y = room.getLocation().y;
			}
			else
			{
				x = rand.nextInt(Core.MAP_WIDTH);
				y = rand.nextInt(Core.MAP_HEIGHT);
				
				room.setLocation(x, y);
				
				//première salle
				rooms.add(room);
				genMap[x][y] = room;
				
				Core.out.println("room n°0");
			}
			
			boolean canCreate = false;
			DoorSide wall = DoorSide.TOP;
			
			if (room.getNDoorsOpened() >= 4)
				return;
			
			while (!canCreate)
			{
				wall = DoorSide.values()[rand.nextInt(4)];
				
				if (room.isDoorOpened(wall) == CLOSED)
					canCreate = true;
			}
			
			Core.out.println("Original room y: " + x + "  x: " + y);
			
			
			switch(wall)
			{
				case TOP:
					y--;
					break;
				case BOT:
					y++;
					break;
				case LEFT:
					x--;
					break;
				case RIGHT:
					x++;
					break;
				default:
			}
			
			//nouvelle salle
			if (canCreateRoom(x, y))
			{
				Room newRoom = new Room();
				newRoom.setLocation(x, y);
				
				room.setDoorOpened(wall, OPENED);
				newRoom.setDoorOpened(invertWall(wall), OPENED);
				
				rooms.add(newRoom);
				genMap[x][y] = newRoom;
				Core.out.println("--------------> Created room x: " + x + "  y: " + y);
			}
		}
		
		public boolean canCreateRoom(int x, int y)
		{
			boolean can = false;
			
			if ((x < 0 || x >= Core.MAP_WIDTH )||
				(y < 0 || y >= Core.MAP_HEIGHT))
			{
				can = false;
				return can;
			}
	
			if (genMap[x][y] == null)
				can = true;
			
			return can;
		}
		
		private void placerItemsVictoire()
		{
			int nbItems = 0;
			
			final ArrayList<Point> oneDoorRoomCoords = new ArrayList<Point>();
			Room room;
			
			//on exclu la salle de départ
			oneDoorRoomCoords.add(actualRoom.getLocation());
			
	
			//on vérifie que au moins 4 salles n'ont qu'une seule porte
			int numRoomsWithOneDoor = 0;
			for(Room r : rooms)
			{
				//on ajoute les salles qui n'ont qu'une seule porte au registre des salles intéresssantes
				if(r.getNDoorsOpened() == 1)
				{
					oneDoorRoomCoords.add(r.getLocation());
					numRoomsWithOneDoor++;
				}
			}
			Core.out.println("items victoire: rooms w 1door: " + numRoomsWithOneDoor);
			
			if(numRoomsWithOneDoor <= VictoryItem.getMaxItems())
			{
				for (int x = 0; x < Core.MAP_WIDTH; x++)
				{
					for (int y = 0; y < Core.MAP_HEIGHT; y++)
					{
						if((genMap[x][y].getNDoorsOpened() == 1) || nbItems > numRoomsWithOneDoor)
						{
							final Entity e = new VictoryItem(nbItems);
							genMap[x][y].newEntity(e);
							
							nbItems++;
							
							Core.out.println("item de victoire placé: n°" + nbItems);
							Core.out.println("                 room x: " + x + "  y: " + y);
						}
					}
				}
			}
			else
			{
				//on veut placer 4 items
				for(int k=0;(nbItems < VictoryItem.getMaxItems()) && (k < oneDoorRoomCoords.size()); k++)
				{
					/* On prend une salle au pif de celles intéressantes dans la
					 * limite ou il reste assez de salles pour placer les items restants.
					 */
					if(rand.nextBoolean() 
							&& !((oneDoorRoomCoords.size() - nbItems) == (VictoryItem.getMaxItems() - nbItems)))
						continue;
						
					//on choisi une salle qui a l'air valide
					room = genMap[oneDoorRoomCoords.get(k).x]
							[oneDoorRoomCoords.get(k).y];

					final Entity e = new VictoryItem(nbItems);
					room.newEntity(e);
					
					nbItems++;
					
					Core.out.println("item de victoire placé: n°" + nbItems);
					Core.out.println("                 room x: " + 
							room.getLocation().x + "  y: " + room.getLocation().y);
				}
			}
			
			assert nbItems == VictoryItem.getMaxItems();
			Core.out.println("item de victoire - FINISHED ---------------- \n");
		}
	}
}
