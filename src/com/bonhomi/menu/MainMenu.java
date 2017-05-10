package com.bonhomi.menu;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;

import com.bonhomi.main.Loopable;

public class MainMenu implements Loopable {

	private boolean initialized = false;
	private ImageIcon splash;
	
	public MainMenu() {
		init();
	}
	
	@Override
	public void init() {
		splash = new ImageIcon("src/Sprites/UI/bonhomiTitle/Title.png");
		initialized = true;
		
	}

	@Override
	public void update() {
		if(!initialized) throw new IllegalStateException("Class Updated before Init!");
		
	}

	@Override
	public void draw(Graphics2D g) {
		g.setColor(Color.black);
		g.drawImage(splash.getImage(), 200, 150, null);
	}

	@Override
	public void terminate() {
		initialized = false;
	}
}
