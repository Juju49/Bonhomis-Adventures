package com.bonhomi.game;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Random;

import com.bonhomi.main.Core;
import com.bonhomi.main.Loopable;

public class Niveau implements DoorsPosition, Loopable
{
	static private Room[][] map;
	private ArrayList<Room> rooms;
	private boolean[] roomsUsed;
	
	private Room actualRoom;
	private boolean finished;
	private int roomsCount;
	
	Niveau()
	{
		map = new Room[Core.MAP_HEIGHT][Core.MAP_WIDTH];
		finished = false;
		roomsCount = Core.MAP_HEIGHT * Core.MAP_WIDTH;
		
		rooms = new ArrayList<Room>(roomsCount);
		roomsUsed = new boolean[roomsCount];
		
		for (int i = 0; i < Core.MAP_HEIGHT; i++)
		{
			for (int j = 0; j < Core.MAP_WIDTH; j++)
			{
				map[i][j] = null;
			}
		}
		
		creerNiveau();
	}
	
	@Override
	public void init() {
		for (int i = 0; i < Core.MAP_HEIGHT; i++)
		{
			for (int j = 0; j < Core.MAP_WIDTH; j++)
			{
				map[i][j].init();
			}
		}
	}
	
	@Override
	public void update()
	{
		if (actualRoom == null)
		{
			actualRoom = map[Core.MAP_HEIGHT/2][Core.MAP_WIDTH/2];
		}
		actualRoom.update();
	}
	
	@Override
	public void draw(Graphics2D g)
	{
		Core.out.println("actual room: " + actualRoom);
		if (actualRoom != null)
		{
			actualRoom.draw(g);
		}
	}
	
	@Override
	public void terminate() {
		for (int i = 0; i < Core.MAP_HEIGHT; i++)
		{
			for (int j = 0; j < Core.MAP_WIDTH; j++)
			{
				map[i][j].terminate();
			}
		}
		
	}
	
	
	
	
	
	
	public Room getActualRoom() {
		return actualRoom;
	}
	
	public void changementSalle(int j, int i, int appearFrom)
	{
		actualRoom = map[i][j];
		map[i][j].setPlayerAtDoor(GameManager.player1, appearFrom);
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
		//boolean ratioSallesFaite = rooms.size() >= roomsCount*0.1;
		
		if (rooms.size() >= roomsCount)
		{
			finished = true;
			assert rooms.size() == roomsCount;
			
			Core.out.println("---------------- FINISHED ----------------");
			return;
		}

		
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
			room.setLocation(rand.nextInt(Core.MAP_HEIGHT), rand.nextInt(Core.MAP_WIDTH));
			Core.out.println("room n°0");
		}
		
		boolean canCreate = false;
		int wall = 0;
		
		if (room.getNDoorsOpened() >= 4)
			return;
		
		while (!canCreate)
		{
			wall = rand.nextInt(4);
			
			if (room.isDoorOpened(wall) == CLOSED)
				canCreate = true;
		}
		
		int ni = room.getLocation().y;
		int nj = room.getLocation().x;
		Core.out.println("Original room x: " + nj + " y: " + ni);
		
		
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
		
		//nouvelle salle
		if (canCreateRoom(ni, nj))
		{
			Room newRoom = new Room();
			newRoom.setLocation(ni, nj);
			
			room.setDoorOpened(wall, OPENED);
			newRoom.setDoorOpened(invertWall(wall), OPENED);
			
			rooms.add(newRoom);
			map[ni][nj] = newRoom;
			Core.out.println("--> Created room x: " + nj + " y: " + ni);
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
		
		//System.out.println(map[i][j]);
		
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
	
	protected static int invertWall(int wall)
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

//compatibilité avec "Niveau2"
	public boolean isLoaded() {
		return true;
	}

	public void startLoading() {
	}
}
