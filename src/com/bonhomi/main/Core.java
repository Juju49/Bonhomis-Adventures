package com.bonhomi.main;

<<<<<<< HEAD
/**
 * Cette classe poss�de des fonctions
 * et variables statiques utilisable
 * dans l'ensemble des fichiers
 */
public class Core 
=======
import java.io.PrintStream;

//import javax.swing.text.JTextComponent;

public final class Core 
>>>>>>> refs/heads/juju-br
{
	public static double deltaTime = 0d;
	public static GameState gameState = GameState.MENU;
	
	
	//private static JTextComponent debugTextBlock;
	
	public static final int WIDTH = 1024;
	public static final int HEIGHT = 576;
<<<<<<< HEAD
	public static final double WANTED_FPS = 60d;
=======

>>>>>>> refs/heads/juju-br
	
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
