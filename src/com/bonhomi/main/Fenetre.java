package com.bonhomi.main;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import com.bonhomi.sounds.SoundSystemMaster;

public class Fenetre extends JFrame {
	
	private Afficheur afficheur;
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
