package com.bonhomi.main;

public class Core 
{
	public static double deltaTime = 0d;
	public static GameState gameState = GameState.GAME;
	public static final int WIDTH = 1024;
	public static final int HEIGHT = 576;
	
	public enum GameState
	{
		MENU,
		GAME
	}
	
	public static void out(Object o)
	{
		System.out.println(o);
	}
	private static void debugPrint(Object[] printed )
	{
		System.out.printf("");
	};
}
