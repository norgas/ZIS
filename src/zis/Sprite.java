/**
Copyright (c) 2012 Babin Philippe
All rights reserved.

Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:

    Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.
    Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.*/

package zis;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

import zis.util.Vector2i;

/**
 * Movable and scalable image
 * 
 * @author Philippe Babin
 *
 */
public class Sprite {
	/** Animation of the sprite  */
	protected final Animation aniSprite;
	
	/** Position of the sprite  */
	protected Vector2i p;
	
	/** Scale of the sprite  */
	protected float scale;

	/** Dimension of the sprite  */
	protected int h, w;
	
	/**
	 * Constructor of the Sprite
	 * @param pSprite Image of the sprite
	 */
	public Sprite(Animation pSprite){
		aniSprite = pSprite;
		w = aniSprite.getWidth();
		h = aniSprite.getHeight();
		
		p = new Vector2i( 0, 0);
	}
	
	/**
	 * Constructor of the Sprite
	 * @param pSprite Image of the sprite
	 * @param nX Position X of the sprite
	 * @param nY Position Y of the sprite
	 */
	public Sprite(Animation pSprite, int nX, int nY){
		aniSprite=pSprite;
		h = aniSprite.getHeight();
		w = aniSprite.getWidth();
		
		p = new Vector2i(nX,nY);
	}
	/**
	 * Sprite render function
	 * @param gc Game Container
	 * @param sb State Based Game
	 * @param gr Graphics
	 */
	public void render(GameContainer gc, StateBasedGame sb, Graphics gr, Vector2f cam){
		aniSprite.draw( p.x * CONST.TILE_WIDTH - cam.x, p.y * CONST.TILE_HEIGHT - cam.y);
	}
	
	/**
	 * Sprite update function
	 * @param gc Game Container
	 * @param sb State Based Game
	 * @param delta Time between frame
	 */
    public void update(GameContainer gc, StateBasedGame sbg, int delta){
    	
    }

    /**
     * Move the Sprite to a new position
     * @param pX 
     * @param pY 
     */
	public void move( int pX, int pY){
		p = new Vector2i( p.x + pX,  p.y + pY);
	}
	
    /**
     * Move simple Sprite to new position
     * @param pM Translation vector
     */
	public void move( Vector2i pM){
		p = new Vector2i( p.x + pM.x,  p.y + pM.y);
	}
	
    /**
     * Move simple Sprite in target direction
     * @param d Movement direction 
     */
	public void move( int d){
		if( d == CONST.NORTH) move( 0, -1);
		if( d == CONST.EAST) move( 1, 0);
		if( d == CONST.SOUTH) move( 0, 1);
		if( d == CONST.WEST) move( -1, 0);
	}
	
	/**
	 * Return Sprite position
	 * @return Position of the Sprite
	 */
	public Vector2i getPosition() {
		return p;
	}
	
	/**
	 * Return Sprite X position
	 * @return X Position of the Sprite
	 */
	public int getX(){
		return p.x;
	}
	
	/**
	 * Return Sprite Y position
	 * @return Y Position of the Sprite
	 */
	public int getY(){
		return p.y;
	}
	/**
	 * Return Sprite scale
	 * @return Sprite's scale
	 */
    public float getScale(){
    	return scale;
    }
    
	/**
	 * Return Sprite's animation
	 * @return Sprite's image
	 */
	public Animation getAnimation() {
		return aniSprite;
	}

	/**
	 * Return Sprite x position
	 * @return New position X
	 */
	public void setX(int x) {
		p.x = x;
	}

	/**
	 * Return Sprite y position
	 * @return New position Y
	 */
	public void setY(int y) {
		p.y = y;
	}
	
	/**
	 * Set Sprite scale
	 * @param nScale New Sprite's scale
	 */
	public void setScale(float nScale){
    	scale = nScale;
    }
	
}
