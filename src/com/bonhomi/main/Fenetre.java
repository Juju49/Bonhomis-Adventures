package com.bonhomi.main;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

import com.bonhomi.sounds.SoundSystemMaster;

import com.bonhomi.game.InputManager;

public class Fenetre extends JFrame
{
	private Afficheur afficheur;
	private InputManager inputManager;
	private SoundSystemMaster soundSystem;
	
	public Fenetre()
	{
		this.setTitle("Bonhomi's Adventure");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ImageIcon img = new ImageIcon("/src/Sprites/Icons/winIcon.png");//this.Class.getResource("winIcon.png"));//
		this.setIconImage(img.getImage());
		this.setVisible(true);
		
		afficheur = new Afficheur();
		afficheur.start();
		
		this.setContentPane(afficheur);
		this.pack();
		this.setLocationRelativeTo(null);
		this.setResizable(false);

	    this.addWindowListener(new WindowAdapter() 
		{	
	    	public void windowOpened(WindowEvent e) 
			{ 
				requestFocus();	
			}
		});
		
		inputManager = new InputManager();
		this.addKeyListener(inputManager);

		if(MainClass.getHasSound()) {
			soundSystem = new SoundSystemMaster();
			soundSystem.start();
		}
	}
	
	public SoundSystemMaster getSoundSystem() {
		return soundSystem;
	}
	
	public Afficheur getAfficheur() {
		return afficheur;
	}
}
