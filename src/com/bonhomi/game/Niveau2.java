package com.bonhomi.game;

import java.awt.Graphics2D;
import java.awt.Point;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import com.bonhomi.main.Core;
import com.bonhomi.main.Loopable;

public class Niveau2 implements DoorsPosition, Loopable
{
	private Room[][] map;
	private ArrayList<Room> rooms;
	
	private Point actualRoom;
	private boolean isMapFinished;
	private final int roomsCount;
	
	private final Thread generationNiveau;
	protected static ThreadLocalRandom randGen = ThreadLocalRandom.current();
	
	Niveau2()
	{
		map = new Room[Core.MAP_WIDTH][Core.MAP_HEIGHT];
		actualRoom = new Point(randGen.nextInt(Core.MAP_WIDTH), randGen.nextInt(Core.MAP_HEIGHT));
		
		roomsCount = Core.MAP_HEIGHT * Core.MAP_WIDTH;
		
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
		
		for(int x=0; x < Core.MAP_WIDTH; x++)
			for(int y=0; y < Core.MAP_HEIGHT; y++)
				map[x][y].init();
	}
	
	public Room getActualRoom()
	{
		if(!isMapFinished) return null;
			
		return map[actualRoom.x][actualRoom.y];
		
	}
	
	private Room getRoom(Point p)
	{
		return map[p.x][p.y];
	}
	
	public void changementSalle(int x, int y, int appearFrom)
	{
		actualRoom = map[x][y].getLocation();
		map[x][y].setPlayerAtDoor(GameManager.player1, appearFrom);
	}
	
	@Override
	public void update()
	{
		if(!isMapFinished) return;
		
		map[actualRoom.x][actualRoom.y].update();
	}
	
	@Override
	public void draw(Graphics2D g)
	{
		if(!isMapFinished) return;
		
		Core.out.println("actual room: x:" + actualRoom.x + "y:" + actualRoom.y);
		map[actualRoom.x][actualRoom.y].draw(g);
	}
	
	private class NivGen implements Runnable
	{
		final private Random rand = ThreadLocalRandom.current();
		private int numRooms = 0;
		final private Point roomPos = new Point();
		
		private void genRoomPosAleat()
		{
			roomPos.setLocation(rand.nextInt(Core.MAP_WIDTH), rand.nextInt(Core.MAP_HEIGHT));
		}
		
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
			
			isMapFinished = false;
			
			for(;numRooms < roomsCount;)
			{
				creerSalle();
			}
			assert numRooms == roomsCount;
			
			
			/*on tourne aléatoirement sur des salles pour les lier tant que
			 * toutes les salles n'ont pas au moins 1 porte
			 */
			boolean navigable = false;
			while(!navigable)
			{
				navigable = false;
				for (int y = 0; y < Core.MAP_HEIGHT; y++)
				{
					for (int x = 0; x < Core.MAP_WIDTH; x++)
					{
						if(map[x][y].getNDoorsOpened() == 0);
							navigable = false;
					}
				}
				genRoomPosAleat();
				linkSalles(roomPos);
			}
			
			
			placerItemsVictoire();
			
			isMapFinished = true;
		}
		
		private void placerItemsVictoire()
		{
			int nbItems = 0;
			
			ArrayList<Point> nombresExclus = new ArrayList<Point>();
			Room room;
			
			//on exclu la salle de départ
			nombresExclus.add(actualRoom);
			
			//on veut placer 4 items
			while(nbItems < 4)
			{
				genRoomPosAleat();
				
				if(nombresExclus.contains(roomPos))
					continue;
				
				room = getRoom(roomPos);
				if(room.getNDoorsOpened() == 1)
				{
					//TODO victory
					//e = VictoryItem(nbItems);
					//room.newEntity(e);
					nbItems++;
					Core.out.println("item de victoire placé: n°" + nbItems);
					Core.out.println("                 room x: " + roomPos.x + "y: " + roomPos.y);
				}
				
				nombresExclus.add(roomPos);
				
			}
		}
		
		private void creerSalle()
		{	
			Room room = new Room();
			
			if (numRooms > 0)
			{
				int choice = 0;
				choice = rand.nextInt(rooms.size());
				room = rooms.get(choice);
			}
			else
			{
				genRoomPosAleat();
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
				
				map[roomPos.x][roomPos.y] = newRoom;
				numRooms++;
				
				Core.out.println("Created room x: " + roomPos.x + "y: " + roomPos.y);
			}
		}
		
		
		private void linkSalles(Point salle)
		{
			Core.out.println("Linking rooms:");
			Core.out.println("room1 x: " + salle.x + "y: " + salle.y);
			//on récupère la salle
			Room room = getRoom(salle);

			
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
			

			
			switch(wall)
			{
				case TOP:
					roomPos.y--;
					Core.out.print("top");
					break;
					
				case LEFT:
					roomPos.x--;
					Core.out.print("left");
					break;
					
				case BOT:
					roomPos.y++;
					Core.out.print("bot");
					break;
	
				case RIGHT:
					roomPos.x++;
					Core.out.print("right");
					break;
					
				default:
			}
			Core.out.println(" of");
			
			if (canCreateRoom(roomPos) == true)
			{
				room.setDoorOpened(wall, OPENED);
				map[roomPos.x][roomPos.y].setDoorOpened(invertWall(wall), OPENED);


				Core.out.println("room2 x: " + roomPos.x + "y: " + roomPos.y);
			}
		}
		
		private boolean canCreateRoom(Point p)
		{
			//évite d'utiliser un try/catch hors des limites
			if (((p.y < 0) || (p.y >= Core.MAP_HEIGHT)) ||
					((p.x < 0) || (p.x >= Core.MAP_WIDTH)))
				return false;
	
			if (map[p.x][p.y] == null)
				return true;
			
			return false;
		}
		
		private boolean canLinkRoom(Point p)
		{
			//évite d'utiliser un try/catch hors des limites
			if (((p.y < 0) || (p.y >= Core.MAP_HEIGHT)) ||
					((p.x < 0) || (p.x >= Core.MAP_WIDTH)))
				return false;
	
			if (map[p.x][p.y] != null)
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
