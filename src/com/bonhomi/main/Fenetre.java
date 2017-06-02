package com.bonhomi.main;

import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import com.bonhomi.sounds.SoundSystemMaster;

class Fenetre extends JFrame
{
	private Afficheur afficheur;
	private InputManager inputManager;
	
	public Fenetre()
	{
		setTitle("Bonhomi's Adventure");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
        setFocusTraversalKeysEnabled(false);
        setFocusable(true);
		
	    addWindowListener(new WindowAdapter() 
		{	
	    	public void windowOpened(WindowEvent e) 
			{ 
				requestFocus();
			}
		});
	    
	    getContentPane().setLayout(null);
	    
	    inputManager = new InputManager();
	    addKeyListener(inputManager);
		
		afficheur = new Afficheur();
		afficheur.start();
		afficheur.setIgnoreRepaint(true);
		afficheur.setFocusable(true);
		afficheur.addMouseMotionListener(inputManager);
		afficheur.addMouseListener(inputManager);
		
		setContentPane(afficheur);
		
		
		
		pack();
		ImageIcon img = new ImageIcon("src/Sprites/Icons/winIcon_0.png");
		setIconImage(img.getImage());
		setLocationRelativeTo(null);
		setResizable(false);

		/*if(MainClass.getDebugLvl() > 2)
		{
			Core.DebOut = new DebugOutput(3);
			getLayeredPane().add(Core.DebOut, 2);
		}*/
	}
	
	private Afficheur getAfficheur() {
		return afficheur;
	}
}
