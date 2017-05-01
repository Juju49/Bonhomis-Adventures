package com.bonhomi.game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashSet;
import java.util.Set;

public class InputManager implements KeyListener
{
	public static Set<Character> keysDown = new HashSet<Character>();
	
	public static boolean isKeyDown(char k)
	{
		if (keysDown.contains(k))
			return true;
		
		return false;
	}
	
	public static boolean isKeyUp(char k)
	{
		if (!isKeyDown(k))
			return true;
		
		return false;
	}

	@Override
	public void keyPressed(KeyEvent e) 
	{
		keysDown.add(e.getKeyChar());
	}

	@Override
	public void keyReleased(KeyEvent e) 
	{
		keysDown.remove(e.getKeyChar());
	}

	@Override
	public void keyTyped(KeyEvent e) 
	{
		// Non utilisé
	}
}
