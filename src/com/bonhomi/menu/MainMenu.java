package com.bonhomi.menu;

import java.awt.Color;
import java.awt.Graphics2D;

import com.bonhomi.main.Loopable;

public class MainMenu implements Loopable {

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

}
