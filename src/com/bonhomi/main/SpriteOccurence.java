package com.bonhomi.main;

import java.awt.AlphaComposite;
import java.awt.Composite;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

public class SpriteOccurence extends Rectangle
{
	protected Graphics2D g = null; //ref vers les graphics de l'afficheur
	protected BufferedImage image = null;
	
	//transformations matricielles
	protected AffineTransform transf = new AffineTransform();
	
	//decalage du centre de rotation:
	protected double ancre_x = 0;
    protected double ancre_y = 0;
    
    //mirroir x et y
    protected byte[] flip = {1, 1};
	
    
	/**
	 * Construit une occurence d'une image et garde l'objet en memoire.
	 * 
	 * 
	 * @param g Graphics2D de l'afficheur
	 * @param image Image à afficher
	 * @param coord_x
	 * @param coord_y
	 * @param rotation en radians autour du centre
	 * @param ech_x
	 * @param ech_y
	 * @param ancre_x position du centre
	 * @param ancre_y
	 */
	public SpriteOccurence(Graphics2D g, BufferedImage image, 
			int coord_x, int coord_y, 
			double rotation,
			double ech_x, double ech_y,
			int ancre_x, int ancre_y)
	{
		this(g, image, 
				new AffineTransform(ech_x, 0, 0, ech_y, coord_x, coord_y), 
				new Point(ancre_x, ancre_y));
		this.transf.rotate(-rotation, ancre_x*ech_x, ancre_y*ech_y);
	}
	
	/**
	 * Construit une occurence d'une image et garde l'objet en memoire.
	 * 
	 * 
	 * @param image Image à afficher
	 * @param coord_x double
	 * @param coord_y double
	 * @param rotation double en degre autour du centre
	 * @param ech_x double
	 * @param ech_y double
	 * @param ancre_x double position du centre
	 * @param ancre_y double
	 */
	public SpriteOccurence(BufferedImage image, 
			int coord_x, int coord_y, 
			double rotation,
			double ech_x, double ech_y,
			int ancre_x, int ancre_y)
	{
		this(null, image, 
				coord_x, coord_y, 
				rotation,
				ech_x, ech_y,
				ancre_x, ancre_y);
	}
	
	/**
	 * Construit une occurence d'une image et garde l'objet en memoire.
	 * Constructeur par defaut.
	 * 
	 * 
	 * @param g       Graphics2D de l'afficheur.
	 * @param image   Image.
	 * @param transf  AffineTransform de l'image.
	 * @param ancre   Point position du centre.
	 */
	public SpriteOccurence(Graphics2D g, BufferedImage image, 
			AffineTransform transf, Point ancre)
	{
		this.g = g;
		this.image = image;
		this.ancre_x = ancre.getX();
		this.ancre_y = ancre.getY();
		this.transf = transf;;
		this.comRect();
	}

	
	/**
	 * Change l'image affichee par l'occurence de Sprite.
	 * 
	 * @param newImage <code>Image</code> a afficher
	 */
	public void setImage(BufferedImage newImage)
	{
		image = newImage;
		comRect();
	}
	
	/**
	 * Permet de manipuler l'AffineTransforme de l'occurence sans la redefinir.
	 * On y entre les meme parametre qu'a la creation.
	 * 
	 * 
	 * @param coord_x int ; correspond au graphics2D utilise.
	 * @param coord_y int ; correspond au graphics2D utilise.
	 * @param rotation double ; en radians dans le sens trigonmetrique
	 * @param ech_x double ; 1.0 pour ne pas appliquer de transformation
	 * @param ech_y double ; 1.0 pour ne pas appliquer de transformation
	 * @param ancre_x double; centre de rotation à partir du coin sup' gauche
	 * @param ancre_y double; centre de rotation à partir du coin sup' gauche
	 */
	public void newTransforms(
			int coord_x, int coord_y,
			double rotation,
			double ech_x, double ech_y,
			double ancre_x, double ancre_y)
	{
		transf.setTransform(ech_x, 0, 0, ech_y, coord_x, coord_y);
		transf.rotate(-rotation, ancre_x*ech_x, ancre_y*ech_y);
		comRect();
	}
	
	/**
	 * Permet de manipuler l'AffineTransforme de l'occurence sans la redefinir.
	 * 
	 * 
	 * @return AffineTransform du sprite
	 */
	public AffineTransform getTransforms()
	{
		return transf;
	}
	
	/**
	 * Assigne un nouvel objet Graphics2D pour que l'occurence dessine.
	 * 
	 * 
	 * @param g Graphics2D sur lequel dessiner
	 */
	public void setGraphicsContext(Graphics2D g)
	{
		this.g = g;
	}
	
	
	public void setFlipX(boolean flip)
	{
		this.flip[0] = (byte) (flip ? -1 : 1);
	}
	
	public void setFlipY(boolean flip)
	{
		this.flip[1] = (byte) (flip ? -1 : 1);
	}
	
	
	public void draw(Graphics2D g)
	{
		if(image == null)
			return;
		
		//fait des trous la ou l'image est transparente:
		Composite comp = 
				AlphaComposite.getInstance(AlphaComposite.SRC_OVER);
		
		//permet de recuperer la composition initiale:
		Composite restore_composite = g.getComposite();
		
		g.setComposite(comp);
		
		//on inverse: fait un mirroir x y sur l'image et on la deplace
		AffineTransform temptransform = AffineTransform.getScaleInstance(
				(int) flip[0], 
				(int) flip[1]);
		temptransform.translate(
				(flip[0] < 0 ? -image.getWidth(null)  : 0), 
				(flip[1] < 0 ? -image.getHeight(null) : 0));
		
		//on cree une nouvelle image retournee
		AffineTransformOp op = new AffineTransformOp(temptransform, 
				AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
		Image disp_image = op.filter(image, null);
		
		//on affiche la nouvelle image
		g.drawImage(disp_image, transf, null);
		
		//on restaure la composition des graphics2D
		g.setComposite(restore_composite);
		
		if(MainClass.getDebugLvl() > 2)
			g.draw(this);
	}
	
	public void drawAutonome()
	{
		if (g == null) 
			throw new Error("Pas de contexte de dessin pour le Sprite");
		draw(g);
	
	}
	
	protected void comRect()
	{
		if(image != null)
		{
		setRect(transf.getTranslateX(), 
				transf.getTranslateY(), 
				image.getWidth(null)*transf.getScaleX(), 
				image.getHeight(null)*transf.getScaleY());
		}
		else
		{
			setRect(transf.getTranslateX(), 
					transf.getTranslateY(), 
					0, 
					0);
		}
	}
}
