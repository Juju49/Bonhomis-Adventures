/**
 * 
 */
package com.bonhomi.game;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;

import com.bonhomi.main.Core;
import com.bonhomi.main.InputManager;
import com.bonhomi.main.Loopable;
import com.bonhomi.main.SpriteLoader;
import com.bonhomi.main.SpriteOccurence;

/**
 * @author Julian
 *
 */
public final class Player extends Rectangle implements Loopable {
	
	private static final long serialVersionUID = 1L;

	private boolean initialized = false;
	
	private int life = 3;
	
	private SpriteOccurence bonhomi;
	private SpriteLoader[] anims = new SpriteLoader[3];
	private int actual_anim;
	private double scale;
	
	public Player(int x, int y, double scale)
	{
		this.scale = scale;
		this.x = x;
		this.y = y;
	}
	
	public void perdreVie()
	{
		if (life > 0) 
		{
			this.life--;
		}
		else
		{
			Core.out.println("Bonhomi est mort!");
		}
	}
	
	public void extraVie()
	{
		this.life = 3;
	}
	
	/* (non-Javadoc)
	 * @see com.bonhomi.main.Loopable#init()
	 */
	@Override
	public void init() {
		
		bonhomi = new SpriteOccurence(null, x, y, 0, scale, scale, 32, 32);
		this.setBounds(this.bonhomi);
		
		anims[0] = new SpriteLoader("Characters/bonhomi/", "avant", 
				true, true, 100);
		anims[1] = new SpriteLoader("Characters/bonhomi/", "gauche", 
				true, true, 100);
		anims[2] = new SpriteLoader("Characters/bonhomi/", "mort");
		
		initialized = (bonhomi != null ? true : false);
	}

	/* (non-Javadoc)
	 * @see com.bonhomi.main.Loopable#update()
	 */
	@Override
	public void update() {
		if(!initialized) 
			throw new IllegalStateException("Class Updated before Init!");
		
		int delta_x = 0;
		int delta_y = 0;
		
		if(InputManager.isKeyDown(KeyEvent.VK_S))
		{
			delta_y += 5;
			actual_anim = 0;
		}
		if(InputManager.isKeyDown(KeyEvent.VK_Z))
		{
			delta_y -= 5;
			actual_anim = 0;
		}
		if(InputManager.isKeyDown(KeyEvent.VK_D))
		{
			delta_x += 5;
			bonhomi.setFlipX(true);
			actual_anim = 1;
		}
		if(InputManager.isKeyDown(KeyEvent.VK_Q))
		{
			delta_x -= 5;
			bonhomi.setFlipX(false);
			actual_anim = 1;
		}
		
		if((delta_y == 0) && (delta_x == 0))
		{
			if(anims[actual_anim].isPlaying()) anims[actual_anim].stop(true);
		}
		else
		{
			if(!anims[actual_anim].isPlaying()) anims[actual_anim].start();
		}
		
		if(this.life <= 0)
		{
			delta_x = 0;
			delta_y = 0;
			bonhomi.setFlipX(false);
			bonhomi.setFlipY(false);
			actual_anim = 2;
		}
		
		bonhomi.newTransforms(x + delta_x, y + delta_y, 0, scale, scale, 32, 32);
		bonhomi.setImage(anims[actual_anim].getActualImage());
		this.setBounds(this.bonhomi);
	}

	/* (non-Javadoc)
	 * @see com.bonhomi.main.Loopable#draw(java.awt.Graphics2D)
	 */
	@Override
	public void draw(Graphics2D g) {
		bonhomi.draw(g);
	}

	/* (non-Javadoc)
	 * @see com.bonhomi.main.Loopable#terminate()
	 */
	@Override
	public void terminate() {
		bonhomi = null;
		anims = null;
		
		initialized = false;
	}
}
