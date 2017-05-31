package com.bonhomi.main;

import java.io.PrintStream;

//import javax.swing.text.JTextComponent;

/**
 * Cette classe poss�de des fonctions
 * et variables statiques utilisable
 * dans l'ensemble des fichiers
 */
public final class Core 
{
	public static DebugOutput DebOut;
	public static double deltaTime = 0d;
	public static GameState gameState = GameState.GAME;
	
	
	public static final int WIDTH = 1024;
	public static final int HEIGHT = 576;
	public static final double WANTED_FPS = 60d;
	public static final int MAP_WIDTH = 4;
	public static final int MAP_HEIGHT = 3;

	
	public enum GameState
	{
		MENU,
		GAME,
		PAUSE
	}
	
	public static PrintStream out = DebugOutput.debugOutputPS;
	
	/**
	 * Prints output following severity settings.
	 * 
	 * 
	 * @param severity int specifies priority of the message 0=info; 1=warning; 2=error.
	 */
	public static void debugPrint(int severity, Object... printed )
	{
		if(severity >= MainClass.getDebugLvl()) {
			out.println(printed.toString());
		}
	}
	
	
}
