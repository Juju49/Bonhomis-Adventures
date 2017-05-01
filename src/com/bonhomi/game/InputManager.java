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
		return keysDown.contains(k);
	}
	
	
	public static boolean isKeyUp(char k)
	{
		return (!isKeyDown(k));
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
		// Non utilise
	}
}
