package com.bonhomi.main;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;

import javax.imageio.ImageIO;

public class Animation 
{
	protected String spritePath;
	protected String name;
	protected boolean animated;
	protected long delay;
	
	protected ArrayList<BufferedImage> images;
	protected Timer animationTimer;
	
	public Animation(String spritePath, String name, boolean animated, long delay)
	{
		this.spritePath = spritePath;
		this.name = name;
		this.animated = animated;
		this.delay = delay;
		
		images = new ArrayList<BufferedImage>();
		animationTimer = new Timer();
		
		load();
	}
	
	protected void load()
	{
		if (animated == true)
		{
			ArrayList<String> files = 
					searchAnimation(name, this.findFiles(spritePath));
			
			for (int i = 0; i < files.size(); i++)
			{
				BufferedImage img = null;
				try 
				{
					img = ImageIO.read(new File(files.get(i)));
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
		
		for (int i = 0; i < files.size(); i++)
		{
			String actualFile = files.get(i);
			String[] tab1 = actualFile.split(".");
			actualFile = tab1[0];
			String[] tab2 = actualFile.split("_");
			if (tab2[0].equals(aName))
			{
				newFiles.add(spritePath + files.get(i));
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
					"Le fichier/répertoire '" + directoryPath + "' n'existe pas");
		}
		else if (!directory.isDirectory())
		{
			System.out.println(
					"Le chemin '" + directoryPath + 
					"' correspond à un fichier et non à un répertoire");
		}
		else
		{
			File[] subFiles = directory.listFiles();
			for(int i = 0 ; i < subFiles.length; i++)
			{
				filesName.add(subFiles[i].getName());
			}
		}
		
		return filesName;
	}
}
