package com.bonhomi.main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashSet;
import java.util.Set;

public class InputManager implements KeyListener
{
	protected static Set<Integer> keysDown = new HashSet<Integer>();
    
	static String getKeySetasString(){
		StringBuilder str = new StringBuilder();
		for(int key : new HashSet<Integer>(keysDown)){
			
			str.append(KeyEvent.getKeyText(key));
			str.append("; ");
			
		}
		System.out.println(str.length());
		return str.toString();
	}
	
	public static boolean isKeyDown(int k)
	{
		return keysDown.contains(k);
	}
	
	
	public static boolean isKeyUp(int k)
	{
		return (!isKeyDown(k));
	}

	
	@Override
	public void keyPressed(KeyEvent e) 
	{
		keysDown.add(e.getKeyCode());
	}
	
	
	@Override
	public void keyReleased(KeyEvent e) 
	{
		keysDown.remove(e.getKeyCode());
	}
	
	
	@Override
	public void keyTyped(KeyEvent e) 
	{
		// Non utilise
	}
}
