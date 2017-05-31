package com.bonhomi.game;

import java.awt.Point;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

class NavMesh {
	private ArrayList<Rectangle2D> aires;
	
	
	public NavMesh() {
		aires = new ArrayList<Rectangle2D>();
	}

	/**
	 * Ajoute un rectangle à l'aire de navigation des ennemis et du joueur.
	 * Si le rectangle est deja present, renvoies l'index du rectangle
	 * 
	 * @param rect
	 * @return index du rectangle pour manipulation ulterieure
	 */
	public void add(Rectangle2D rect)
	{
		if (!aires.contains(rect))
		{
			aires.add(rect);
		}
	}
	
	/**
	 * Ajoute des rectangles à l'aire de navigation des ennemis et du joueur.
	 * Si des rectangles sont deja present, renvoies l'index des rectangles
	 * 
	 * @param rects Liste des rectangles2D a ajouter
	 * @return indexes des rectangles pour manipulation ulterieure
	 */
	public void add(Rectangle2D[] rects)
	{
		for( int i = 0; i < rects.length; i++)
		{
			add(rects[i]);
		}
		
	}
	
	public int get(Rectangle2D rect)
	{
		return aires.indexOf(rect);
	}
	
	public void remove(Rectangle2D rect)
	{
		if(aires.contains(rect))
			aires.remove(aires.indexOf(rect));
	}

	public boolean contains(Point p)
	{
		boolean is_contained = false;
		
		for (Rectangle2D rect : aires)
		{
			if(rect.contains(p))
				is_contained = true;
		}
		
		return is_contained;
	}
	
	public boolean contains(double x, double y)
	{
		boolean is_contained = false;
		
		for (Rectangle2D rect : aires)
		{
			if(rect.contains(x, y))
				is_contained = true;
		}
		
		return is_contained;
	}
	
	public boolean contains(Rectangle2D r)
	{
		boolean is_contained = false;
		
		for (Rectangle2D rect : aires)
		{
			if(rect.contains(r))
				is_contained = true;
		}
		
		return is_contained;
	}
	
	public boolean contains(double x, double y, double w, double h)
	{
		boolean is_contained = false;
		
		for (Rectangle2D rect : aires)
		{
			if(rect.contains(x, y, w, h))
				is_contained = true;
		}
		
		return is_contained;
	}
	
	public boolean isEmpty() {
		return aires.isEmpty();
	}

}
