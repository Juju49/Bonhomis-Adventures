package com.bonhomi.main;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Timer;

import javax.imageio.ImageIO;

public class SpriteLoader 
{
	protected String spritePath;
	protected String name;
	protected boolean animated;
	protected long delay;
	
	protected ArrayList<BufferedImage> images;
	protected Timer animationTimer;
	
	/**
	 * Constructeur pour le chargement d'images.
	 * 
	 * 
	 * @param spritePath  String: Chemin du dossier ou se trouve le dessin.
	 * @param name        String: Nom de l'imaga sans extension.
	 * @param animated    boolean: L'image contient d'autres frame?
	 * @param delay       long: temps en ms entre chaque frame.
	 */
	public SpriteLoader(String spritePath, String name, boolean animated, long delay)
	{
		this.spritePath = spritePath;
		this.name = name;
		this.animated = animated;
		this.delay = delay;
		
		animationTimer = new Timer();
		
		load();
	}
	
	/**
	 * Constructeur pour le chargement d'images.
	 * 
	 * 
	 * @param spritePath  String: Chemin du dossier ou se trouve le dessin.
	 * @param name        String: Nom de l'imaga sans extension.
	 */
	public SpriteLoader(String spritePath, String name)
	{
		this.spritePath = spritePath;
		this.name = name;
		this.animated = false;
		this.delay = 1;
		
		animationTimer = new Timer();
		
		load();
	}
	
	protected void load()
	{
		if (animated == true)
		{
			ArrayList<String> files = 
					searchAnimation(name, this.findFiles(spritePath));
			
			images = new ArrayList<BufferedImage>(files.size());
			
			for (String file: files)
			{
				BufferedImage img = null;
				try 
				{
					img = ImageIO.read(new File(file));
				} 
				catch (IOException e) 
				{
					e.printStackTrace();
				}
				images.add(img);
			}
		}
		else
		{
			BufferedImage image = null;
			try 
			{
				image = ImageIO.read(new File(spritePath + name + ".png"));
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			}
			images.add(image);
		}
	}
	
	public void start()
	{
		
	}
	
	public void stop()
	{
		
	}
	
	public void reset()
	{
		
	}
	
	protected ArrayList<String> searchAnimation(
			String aName, 
			ArrayList<String> files)
	{
		ArrayList<String> newFiles = new ArrayList<String>();
		
		for (String file: files)
		{
			String actualFile = file;
			String[] tab1 = actualFile.split(".");
			actualFile = tab1[0];
			String[] tab2 = actualFile.split("_");
			if (tab2[0].equals(aName))
			{
				newFiles.add(spritePath + file);
			}
		}
		
		if (newFiles.isEmpty())
			throw new Error("AUCUNE ANIMATION TROUVEE");
		
		return newFiles;
	}
	
	protected ArrayList<String> findFiles(String directoryPath) 
	{
		ArrayList<String> filesName = new ArrayList<String>();
		
		File directory = new File(directoryPath);
		
		if (!directory.exists())
		{
			System.out.println(
					"Le fichier/repertoire '" + directoryPath + "' n'existe pas");
		}
		else if (!directory.isDirectory())
		{
			System.out.println(
					"Le chemin '" + directoryPath + 
					"' correspond a un fichier et non a un repertoire");
		}
		else
		{
			File[] subFiles = directory.listFiles();
			for(File sub_file : subFiles)
			{
				filesName.add(sub_file.getName());
			}
		}
		
		return filesName;
	}
}
