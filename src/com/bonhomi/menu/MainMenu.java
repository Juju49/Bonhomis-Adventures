package com.bonhomi.menu;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;

import com.bonhomi.main.Core;
import com.bonhomi.main.Core.GameState;
import com.bonhomi.main.InputManager;
import com.bonhomi.main.Loopable;
import com.bonhomi.main.SpriteOccurence;

public class MainMenu implements Loopable {

	private boolean initialized = false;
	private SpriteOccurence mainImg;
	
	public MainMenu() {
		init();
	}
	
	@Override
	public void init() {
		mainImg = new SpriteOccurence(new ImageIcon("src/Sprites/UI/bonhomiTitle/Title.png").getImage(),
				200, 100,
				0d,
				0.5d, 0.5d,
				0d, 0d);
		
		initialized = true;
	}

	@Override
	public void update() {
		if(!initialized) throw new IllegalStateException("Class Updated before Init!");
		
		if(InputManager.mouseLeftCliked())
		{
			Core.gameState = GameState.GAME;
		}
		
	}

	@Override
	public void draw(Graphics2D g) {
		mainImg.draw(g);
	}

	@Override
	public void terminate() {
		initialized = false;
	}
}
