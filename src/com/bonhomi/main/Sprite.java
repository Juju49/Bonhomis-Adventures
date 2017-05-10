package com.bonhomi.main;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Sprite 
{
	public float x, y;
	protected BufferedImage image;
	
	public Sprite()
	{
		
	}
	
	public void update()
	{
		
	}
	
	public void draw(Graphics2D g)
	{
		g.drawImage(image, null, (int) x, (int) y);
	}
}
