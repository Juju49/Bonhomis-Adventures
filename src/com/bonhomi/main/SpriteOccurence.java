package com.bonhomi.main;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class SpriteOccurence
{
	protected Graphics2D g = null; //ref vers les graphics de l'afficheur
	protected Image image = null;
	
	//transformations matricielles
	protected AffineTransform transf = new AffineTransform();
	
	//decalage du centre de rotation:
    double ancre_x = 0;
    double ancre_y = 0;
	
    
	/**
	 * Construit une occurence d'une image et garde l'objet en memoire.
	 * 
	 * 
	 * @param g Graphics2D de l'afficheur
	 * @param image BufferedImage à afficher
	 * @param coord_x double
	 * @param coord_y double
	 * @param rotation double en degre autour du centre
	 * @param ech_x double
	 * @param ech_y double
	 * @param ancre_x double position du centre
	 * @param ancre_y double
	 */
	public SpriteOccurence(Graphics2D g, Image image, 
			int coord_x, int coord_y, 
			double rotation,
			double ech_x, double ech_y,
			double ancre_x, double ancre_y)
	{
		this.g = g;
		this.image = image;
		this.ancre_x = ancre_x;
		this.ancre_y = ancre_y;
		transf = new AffineTransform(ech_x, 0, 0, ech_y, coord_x, coord_y);
		transf.setToRotation(rotation, ancre_x, ancre_y);
	}
	
	/**
	 * Construit une occurence d'une image et garde l'objet en memoire.
	 * 
	 * 
	 * @param g Graphics2D de l'afficheur.
	 * @param image BufferedImage.
	 * @param transf AffineTransform de l'image.
	 * @param ancre_x double position du centre.
	 * @param ancre_y
	 */
	public SpriteOccurence(Graphics2D g, Image image, 
			AffineTransform transf,
			double ancre_x, double ancre_y)
	{
		this.g = g;
		this.image = image;
		this.ancre_x = ancre_x;
		this.ancre_y = ancre_y;
		this.transf = transf;
	}
	
	/**
	 * Construit une occurence d'une image et garde l'objet en memoire.
	 * 
	 * 
	 * @param image BufferedImage à afficher
	 * @param coord_x double
	 * @param coord_y double
	 * @param rotation double en degre autour du centre
	 * @param ech_x double
	 * @param ech_y double
	 * @param ancre_x double position du centre
	 * @param ancre_y double
	 */
	public SpriteOccurence(Image image, 
			int coord_x, int coord_y, 
			double rotation,
			double ech_x, double ech_y,
			double ancre_x, double ancre_y)
	{
		this.image = image;
		this.ancre_x = ancre_x;
		this.ancre_y = ancre_y;
		transf = new AffineTransform(ech_x, 0, 0, ech_y, coord_x, coord_y);
		transf.setToRotation(rotation, ancre_x, ancre_y);
	}
	
	public void newTransforms(
			int coord_x, int coord_y,
			double rotation,
			double ech_x, double ech_y,
			double ancre_x, double ancre_y)
	{
		transf.setTransform(ech_x, 0, 0, ech_y, coord_x, coord_y);
		transf.setToRotation(rotation, ancre_x, ancre_y);
	}
	
	public AffineTransform getTransforms()
	{
		return transf;
	}
	
	public void setGraphicsContext(Graphics2D g)
	{
		this.g = g;
	}
	
	public void draw() throws Exception
	{
		if (g == null) throw new Exception("Pas de contexte de dessin pour le Sprite");
		g.drawImage(image, transf, null);
	}
	
	public void draw(Graphics2D g)
	{
		g.drawImage(image, transf, null);
	}
	
	public void main(String... args)
	{
		try {
			draw();
		} catch (Exception e) {
			Core.out("no graphics!");
			e.printStackTrace();
		}
	}
}
