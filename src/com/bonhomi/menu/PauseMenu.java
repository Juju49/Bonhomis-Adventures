package com.bonhomi.menu;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import com.bonhomi.main.Loopable;

public class PauseMenu implements Loopable {
	
	private boolean initialized = false;
	
	@Override
	public void init() {
		initialized = true;
	}

	@Override
	public void update() {
		if(!initialized) return;
	}

	@Override
	public void draw(Graphics2D g) {
		g.setColor(Color.black);
	}

	@Override
	public void terminate() {
		initialized = false;
	}

	@Override
	public void keyIsDown(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyIsUp(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}
