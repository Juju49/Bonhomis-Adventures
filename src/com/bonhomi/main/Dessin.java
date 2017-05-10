package com.bonhomi.main;

import java.awt.Color;
import java.awt.Graphics2D;

public class Dessin 
{
	public static void rect(Graphics2D g, int x, int y, int width, int height, Color color)
	{
		g.setColor(color);
		g.fillRect(x, y, width, height);
	}
	
	/**
	 * fonction inutile qui dessine un gros rectangle sur un ecran propre...
	 * @param g      Graphics2D utilise pour l'afficheur
	 * @param color  Color      du background...
	 */
	public static void clear(Graphics2D g, Color color)
	{
		g.setColor(color);
		g.fillRect(0, 0, Core.WIDTH, Core.HEIGHT);
	}
}
