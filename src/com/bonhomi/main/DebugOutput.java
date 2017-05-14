/**
 * 
 */
package com.bonhomi.main;

import java.awt.Dimension;
import java.awt.Insets;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * @author Julian
 *
 */
public final class DebugOutput extends JPanel
{
	
    private static JLabel[] ctx_text_field;
    private static JTextArea textArea;
    
	protected static PrintStream debugOutputPS = System.out;
	private static FileOutputStream logOutput;
	private static ByteArrayOutputStream debugPipe;
	//protected PipedInputStream debugPipeIn;
	
	private static Thread thread;
	private static boolean running = false;
	private static PrintStream stdout = System.out;

	public static boolean isRunning()
	{
		return running;
	}
	
 	public DebugOutput(int num_isolated_displays) 
	{
		//debugPipeIn = new PipedInputStream();
		try
		{
			//debugOutput = new PrintStream(new PipedOutputStream(this.debugPipeIn), true);
			debugPipe = new ByteArrayOutputStream();
			logOutput = new FileOutputStream(new File("log.txt"), true);
			debugOutputPS = new PrintStream(debugPipe, true);
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		};
		
		System.setOut(debugOutputPS);
		
		ctx_text_field = new JLabel[num_isolated_displays];//le nombre de labels est variable
		
		setLayout(null); //positionnement absolu des entites
        Insets insets = getInsets(); //taille des contours
        
        int num_ligne = 1; //utilise pour faire un décalage vertical des panneaux
        
		for(JLabel labs : ctx_text_field)
		{
			labs = new JLabel("Empty");
			add(labs);
	        labs.setBounds(10 + insets.left, 15*num_ligne + insets.top,
	                     10, 80);
	        num_ligne++;
		}
		
		//on cree une zone de sortie des infos de debug
        textArea = new JTextArea(20, 90);
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        
        //on place la zone de texte dans un paneau defilant
        JScrollPane scrollPane = new JScrollPane(textArea);
        add(scrollPane);
        
        //on place le panneau contenant la zone de texte
        Dimension size = scrollPane.getPreferredSize();
        scrollPane.setBounds(10 + insets.left,
        		15 * num_ligne + 30 + insets.top, 
        		size.width,
        		size.height);
        
        //on dimensionne ce paneau de debug:
        setSize(100 + insets.left + insets.right,
                      550 + insets.top + insets.bottom);
     
        running = true;

	}
	
	public static void update()
	{
		if (running)
		{
			
			//ne pas aller chercher de texte si il n'y a rien a ecrire:
			if(debugPipe.size() == 0)
			{
				return;
			}
			
			//decodage
			byte[] b = debugPipe.toByteArray();
			String content = new String(b, StandardCharsets.UTF_8);
			
			//impression console
			textArea.append(content);
			textArea.setCaretPosition(textArea.getDocument().getLength());
			
			//impression fichier
			try 
			{
				logOutput.write(b);
				logOutput.flush();
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			}
		}
		//running == false ici; cet affichage va etre detruit:
		//fermeture des tuyaux et de log.txt
		try 
		{
			debugPipe.close();
			logOutput.close();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		//restore stdout:
		System.setOut(stdout);
	}

	public static void inputLabel(int index, String txt)
	{
		if(running)
			try
			{
				ctx_text_field[index].setText(txt);
			}
			catch (IndexOutOfBoundsException e)
			{
				e.printStackTrace(stdout);
			};
	}
}

