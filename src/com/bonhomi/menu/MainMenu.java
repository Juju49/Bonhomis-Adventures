package com.bonhomi.menu;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import com.bonhomi.main.Core;
import com.bonhomi.main.Core.GameState;
import com.bonhomi.main.InputManager;
import com.bonhomi.main.Loopable;
import com.bonhomi.main.SpriteOccurence;

public class MainMenu implements Loopable {

	private boolean initialized = false;
	private SpriteOccurence mainImg;
	
	public MainMenu() 
	{
		init();
	}
	
	@Override
	public void init() 
	{
		try {
			mainImg = new SpriteOccurence(null, 
					(BufferedImage) ImageIO.read(new File("src/Sprites/UI/bonhomiTitle/Title.png")),
					200, 100,
					0d,
					0.5d, 0.5d,
					0, 0);
		} catch (IOException e) {
			mainImg = new SpriteOccurence(null, 
					null,
					200, 100,
					0d,
					0.5d, 0.5d,
					0, 0);
			e.printStackTrace();
		}
		
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
