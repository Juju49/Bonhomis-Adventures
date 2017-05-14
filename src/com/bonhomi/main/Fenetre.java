package com.bonhomi.main;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import com.bonhomi.sounds.SoundSystemMaster;

public class Fenetre extends JFrame
{
	private Afficheur afficheur;
	private InputManager inputManager;
	private SoundSystemMaster soundSystem;
	
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
		ImageIcon img = new ImageIcon("src/Sprites/Icons/winIcon.png");
		setIconImage(img.getImage());
		setLocationRelativeTo(null);
		setResizable(false);

		if(MainClass.getHasSound()) {
			soundSystem = new SoundSystemMaster();
			soundSystem.start();
		}
		/*if(MainClass.getDebugLvl() > 2)
		{
			Core.DebOut = new DebugOutput(3);
			getLayeredPane().add(Core.DebOut, 2);
		}*/
	}
	
	public SoundSystemMaster getSoundSystem() {
		return soundSystem;
	}
	
	public Afficheur getAfficheur() {
		return afficheur;
	}
}
