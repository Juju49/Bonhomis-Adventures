package com.bonhomi.main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;

public class Fenetre extends JFrame implements KeyListener 
{
	private Afficheur afficheur;
	
	public Fenetre()
	{
		this.setTitle("Bonhomi's Adventure");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
		this.addKeyListener(this);
	}

	@Override
	public void keyPressed(KeyEvent e) 
	{
		afficheur.keyPressed(e);
	}

	@Override
	public void keyReleased(KeyEvent e) 
	{
		afficheur.keyReleased(e);
	}

	@Override
	public void keyTyped(KeyEvent e) 
	{
		
	}
}
