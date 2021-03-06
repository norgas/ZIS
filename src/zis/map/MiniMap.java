/**
Copyright (c) 2012 Babin Philippe
All rights reserved.

Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:

    Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.
    Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.*/

package zis.map;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.Graphics;

import zis.CONST;

public class MiniMap {
	
	
	private Image miniMap = null;
	
	private Graphics mapGraphics;
	
	private Vector2f p;
	
	public MiniMap(Vector2f p){
		this.p = p;
	}
	
	public void updateMiniMap( WorldMap bigMap) throws SlickException{
		
		miniMap = new Image( (int)(CONST.MAP_WIDTH * 0.5), (int)(CONST.MAP_HEIGHT* 0.5));

		mapGraphics = miniMap.getGraphics();
		mapGraphics.setColor( new Color( 49, 145, 19));
    	for(int y = 0; y < CONST.MAP_HEIGHT; y++){
    		for(int x = 0; x < CONST.MAP_WIDTH; x++){
	    		if( bigMap.isSolid( x, y)){
	    			//mapGraphics.drawRect( x, y, 0.1f, 0.1f);
	    			mapGraphics.drawRect( (float)Math.floor( x * 0.5f),  (float)Math.floor(y * 0.5f), 0.1f, 0.1f);
	    		}
	    	}
		}
		mapGraphics.flush();
	}
	
	public Vector2f onClick( Vector2f cursor, Vector2f cam){
		Vector2f newCam = new Vector2f();
		if( cursor.x >= p.x && cursor.y >= p.y && cursor.x <= p.x + miniMap.getWidth() && cursor.y <= p.y + miniMap.getHeight()){
			newCam.x = 2 * CONST.TILE_WIDTH * (cursor.x - p.x) - CONST.SCREEN_WIDTH / 2;
			newCam.y = 2 * CONST.TILE_HEIGHT * (cursor.y - p.y) - CONST.SCREEN_HEIGHT / 2;
		}
		else{
			newCam = cam;
		}
		return newCam;
	}
	
	public void render(GameContainer gc, StateBasedGame sb, Graphics gr, Vector2f cam){

		Rectangle background =  new Rectangle( 
				p.x - 4,
				p.y - 4,
				CONST.MAP_WIDTH / 2 + 8,
				CONST.MAP_HEIGHT / 2 + 8);
		Rectangle camRect =  new Rectangle( 
				p.x + cam.x / (2 * CONST.TILE_WIDTH),
				p.y + cam.y / (2 * CONST.TILE_HEIGHT),
				CONST.SCREEN_WIDTH / (2 * CONST.TILE_WIDTH),
				CONST.SCREEN_HEIGHT / (2 * CONST.TILE_HEIGHT));
		
		gr.setColor( new Color( 4, 23, 3, 200));
		gr.fill( background);
		gr.setColor( Color.white);


		//gr.fill( camRect);
//		gr.drawImage( miniMap.getSubImage( (int)cam.x / 5 - 25, (int)cam.y / 5 -25,
//				CONST.SCREEN_WIDTH / 5 + 48,
//				CONST.SCREEN_HEIGHT / 5 + 48),
//				p.x, p.y);
		gr.drawImage( miniMap, p.x, p.y);

		gr.setColor( Color.white);
		
		gr.setLineWidth(3);
		gr.draw( background);
		gr.setLineWidth(1);
		
		gr.draw( camRect);
	}
	
}
