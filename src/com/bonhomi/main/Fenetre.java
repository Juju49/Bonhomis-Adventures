package com.bonhomi.main;

import javax.swing.JFrame;

public class Fenetre extends JFrame {
	
	private Afficheur afficheur;
	
	public Fenetre()
	{
		this.setTitle("Bonhomi's Adventure");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		
		afficheur = new Afficheur();
		this.setContentPane(afficheur);
		this.pack();
		this.setLocationRelativeTo(null);
	}
}
