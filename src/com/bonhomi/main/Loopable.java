package com.bonhomi.main;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

public interface Loopable
{
	public void init();
	public void update();
	public void draw(Graphics2D g);
	public void keyIsDown(KeyEvent e);
	public void keyIsUp(KeyEvent e);
}
