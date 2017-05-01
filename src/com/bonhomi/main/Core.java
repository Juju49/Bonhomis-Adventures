package com.bonhomi.main;

import java.io.PrintStream;

//import javax.swing.text.JTextComponent;

public final class Core 
{
	public static double deltaTime = 0d;
	public static GameState gameState = GameState.MENU;
	
	
	//private static JTextComponent debugTextBlock;
	
	public static final int WIDTH = 1024;
	public static final int HEIGHT = 576;

	
	public enum GameState
	{
		MENU,
		GAME,
		PAUSE
	}
	
	/*private static JTextComponent getDebugTextBlock()
	 {
		if(debugTextBlock == null) {
			//debugTextBlock = new JTextComponent();
		}
		return debugTextBlock;
	 }*/
	
	public static void out(Object... o)
	{
		System.out.println(o);
	}
	
	/**
	 * Prints output following severity settings.
	 * 
	 * 
	 * @param severity int specifies priority of the message 0=info; 1=warning; 2=error.
	 */
	public static void debugPrint(int severity, Object... printed )
	{
		if(severity >= MainClass.getDebugLvl()) {
			System.out.println(printed);
		}
	}
	
	
}
