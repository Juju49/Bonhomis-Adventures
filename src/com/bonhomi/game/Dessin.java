package com.bonhomi.game;

import java.awt.Color;
import java.awt.Graphics2D;

import com.bonhomi.main.Core;

public class Dessin 
{
	public static void rect(Graphics2D g, int x, int y, int width, int height, Color color)
	{
		g.setColor(color);
		g.fillRect(x, y, width, height);
	}
	
	public static void clear(Graphics2D g, Color color)
	{
		g.setColor(color);
		g.fillRect(0, 0, Core.WIDTH, Core.HEIGHT);
	}
}
