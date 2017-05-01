package com.bonhomi.game;

import java.awt.Color;
import java.awt.Graphics2D;

import com.bonhomi.main.Core;
import com.bonhomi.main.Loopable;

public class GameManager implements Loopable {

	private boolean initialized = false;
	
	@Override
	public void init() {
		initialized = true;
	}

	@Override
	public void update() {
		if(!initialized) {
			Core.debugPrint(2, "Class Updated before Init!");
			assert false;
		}
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
