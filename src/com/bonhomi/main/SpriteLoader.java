package com.bonhomi.main;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;

public class SpriteLoader 
{
	protected String spritePath;
	protected String name;
	protected boolean animated;
	protected boolean repeat;
	protected long delay;
	
	protected ArrayList<BufferedImage> images;
	protected int actualIndex;
	protected Timer animationTimer;
	
	/**
	 * Charge des images et les transforme en animation a partir du system de fichiers.
	 * <p>
	 * Les chemins commencent à "src/Sprites/" et le nom doit etre precise sans extension.
	 * Le format *.png est supporte.
	 * <p>
	 * lorsque repeat est active, l'animation est jouee en boucle. Le nombre d'images 
	 * est calculé automatiquement. Delay est le temps d'affichage de chaque image a 
	 * l'écran lors de l'animation en milllisecondes.
	 * <p>
	 * Les animations doivent etre au format <code>nom_index.png</code> 
	 * dans les hierarchies.
	 * 
	 * 
	 * @param spritePath chemin du dossier avec un slash a la fin.
	 * @param name       nom sans extension de l'image.
	 * @param animated   rechercher les animations ?
	 * @param repeat     boucler les animations ?
	 * @param delay      temps d'affichage par image.
	 */
	public SpriteLoader(
			String spritePath, 
			String name, 
			boolean animated, 
			boolean repeat,
			long delay)
	{
		this.spritePath = "src/Sprites/" + spritePath; //tous les sprites sont dans src/sprites/
		this.name = name;
		this.animated = animated;
		this.repeat = repeat;
		this.delay = delay;
		this.actualIndex = 0;
		
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
		if (animated == true)
		{
			animationTimer = new Timer();
			
			animationTimer.scheduleAtFixedRate(
				new TimerTask() 
				{
					@Override
					public void run() 
					{
						//Core.out.println(actualIndex + "   " + (images.size() - 1));
						if (actualIndex == images.size() - 1)
						{
							actualIndex = 0;
							if (repeat == false)
								stop(true);
						}
						else
							actualIndex++;
					}
				}, 0, this.delay
			);
		}
	}
	
	/**
	 * Stop anim execution.
	 * 
	 * @param reset resets anim to frame 0.
	 */
	public void stop(boolean reset)
	{
		animationTimer.cancel();
		animationTimer = null;
		actualIndex = (reset ? 0 : actualIndex);
	}
	
	public boolean isPlaying()
	{
		return (animationTimer == null ? false : true);
	}
	
	public BufferedImage getActualImage()
	{
		return images.get(actualIndex);
	}
	
	protected ArrayList<String> searchAnimation(
			String aName, 
			ArrayList<String> files)
	{
		ArrayList<String> newFiles = new ArrayList<String>();
		
		for (int i = 0; i < files.size(); i++)
		{
			String actualFile = files.get(i);
			String[] tab1 = actualFile.split("\\.");
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
			for(int i = 0 ; i < subFiles.length; i++)
			{
				filesName.add(subFiles[i].getName());
			}
		}
		
		return filesName;
	}
}
