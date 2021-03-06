/**
Copyright (c) 2012 Babin Philippe
All rights reserved.

Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:

    Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.
    Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.*/

package zis.map;

import java.util.ArrayList;

import org.newdawn.slick.geom.Rectangle;

import zis.CONST;
import zis.util.Rand;
import zis.util.Vector2i;

/***
 * Randomly generate an Apartment
 * 
 * @author Philippe Babin
 */
public class Apartment {
	private int x, y, W, H;

	/*** Randomizer */
	private Rand rand;
	
	/*** City that contain the Apartment */
	private City city;

	/*** Stack of generated rooms */
	private ArrayList<Rectangle> buildRegion = new ArrayList<Rectangle>();

	/*** List of the sleeping rooms */
	private ArrayList<Room> rooms = new ArrayList<Room>();
	
	/*** Name of the Apartment */
	private String name = "Apartments at good price";
	
	/***
	 * Constructor of an Apartment
	 * @param city Reference to the City
	 * @param seed Seed of the Apartment
	 * @param r Rectangle of the Apartment
	 */
	public Apartment( City city, int seed, Rectangle r){
		this.city = city;
		this.x = (int) r.getX();
		this.y = (int) r.getY();
		this.W = (int) r.getWidth();
		this.H = (int) r.getHeight();
		rand = new Rand( seed);
		
		generateApartment();
	}
	/***
	 * Constructor of a office Apartment
	 * @param city Reference to the City
	 * @param seed Seed of the Apartment
	 * @param x Position X of the Apartment
	 * @param y Position Y of the Apartment
	 * @param W Width of the Apartment
	 * @param H Height of the Apartment
	 */
	public Apartment( City city, int seed, int x, int y, int W, int H){
		this.city = city;
		this.x = x;
		this.y = y;
		this.W = W;
		this.H = H;
		rand = new Rand( seed);

		generateApartment();
	}
	
	/***
	 * Procedurally generate a Apartment in the target region
	 */
	public void generateApartment(){
		generateFloor();
	}
	
	/***
	 * Procedurally generate a floor
	 */
	public void generateFloor(){
		buildRegion.clear();
		rooms.clear();

		//temRegion.add( new Rectangle( x, y, W, H));
		//city.drawOutline( temRegion.get( 0), 3);
		
		int offsetX = (int) ( W % (2 * CONST.APARTMENT_WIDTH + 3)* 0.5);
		int offsetY = (int) ( H % (CONST.APARTMENT_HEIGHT + 1) * 0.5);
		
		int nbrApartWidth = (W - 2 * offsetX) / ( 2 * CONST.APARTMENT_WIDTH + 3);
		int nbrApartHeight = (H - 2 * offsetY) / CONST.APARTMENT_HEIGHT;
		Rectangle newChamber;
		for( int g = 0; g < nbrApartWidth; g++){
			for( int v = 0; v < nbrApartHeight; v++){
				newChamber = new Rectangle(
						x + offsetX + g * (2 * CONST.APARTMENT_WIDTH  + 3),
						y + offsetY + v * CONST.APARTMENT_HEIGHT,
						CONST.APARTMENT_WIDTH + 1, 
						CONST.APARTMENT_HEIGHT + 1);
				generateChamber( newChamber, false, v == nbrApartHeight - 1, v != 0 && v != nbrApartHeight - 1);

				newChamber = new Rectangle(
						x + offsetX + g * (2 * CONST.APARTMENT_WIDTH  + 3) + CONST.APARTMENT_WIDTH,
						y + offsetY + v * CONST.APARTMENT_HEIGHT,
						CONST.APARTMENT_WIDTH + 1, 
						CONST.APARTMENT_HEIGHT + 1);
				generateChamber( newChamber, true, v == nbrApartHeight - 1, v != 0 && v != nbrApartHeight - 1);
			}
			
		}
		
		/** Add the outline of then room */
		int j = 0;
		for(Rectangle r : buildRegion){
		//	rooms.add( new Room( r));
			
			j++;
		}
	}
	
	public void generateChamber( Rectangle r, boolean miroirX, boolean miroirY, boolean center){
		ArrayList<Rectangle> newRooms = new ArrayList<Rectangle>();
		ArrayList<Vector2i> newDoors = new ArrayList<Vector2i>();
		
		if( center){
			newRooms.add( new Rectangle( 4, 0, 7, 7));
			newRooms.add( new Rectangle( 0, 0, 5, 5));
			newDoors.add( new Vector2i( 4, 2));
		}
		else{
			newRooms.add( new Rectangle( 2, 0, 7, 7));
			newRooms.add( new Rectangle( 8, 2, 5, 5));
			newDoors.add( new Vector2i( 8, 4));
		}
		
		newDoors.add( new Vector2i( 6, 6));
		newDoors.add( new Vector2i( 10, 7));
		newDoors.add( new Vector2i( 8, 8));
		newDoors.add( new Vector2i( 5, 8));
		newDoors.add( new Vector2i( 6, 10));
		newDoors.add( new Vector2i( 7, 13));
		
		newRooms.add( new Rectangle( 0, 6, 6, 5));
		newRooms.add( new Rectangle( 1, 10, 7, 5));
		newRooms.add( new Rectangle( 7, 8, 4, 4));
		newRooms.add( new Rectangle( 7, 11, 4, 4));
		
		Rectangle theRoom;

		boolean ver = false;
		if( !miroirX && !miroirY){
			for( Rectangle ro : newRooms){
				theRoom = new Rectangle( 
						ro.getX() + r.getX(),
						ro.getY() + r.getY(),
						ro.getWidth(),
						ro.getHeight());
				buildRegion.add( theRoom);
				city.drawOutline( theRoom, 3);
			}
			for( Vector2i d : newDoors){
				city.addDoor( new Vector2i( r.getX() + d.x, r.getY() + d.y), ver);
				ver = !ver;
			}
		}
		if( miroirX && !miroirY){
			for( Rectangle ro : newRooms){
				theRoom = new Rectangle( 
						r.getX() - ro.getX() + r.getWidth() - ro.getWidth(),
						ro.getY() + r.getY(),
						ro.getWidth(),
						ro.getHeight());
				buildRegion.add( theRoom);
				city.drawOutline( theRoom, 3);
			}
			for( Vector2i d : newDoors){
				city.addDoor( new Vector2i( r.getX() + r.getWidth() - d.x - 1, r.getY() + d.y), ver);
				ver = !ver;
			}
		}
		if( !miroirX && miroirY){
			for( Rectangle ro : newRooms){
				theRoom = new Rectangle( 
						ro.getX() + r.getX(),
						r.getY() - ro.getY() + r.getHeight() - ro.getHeight(),
						ro.getWidth(),
						ro.getHeight());
				buildRegion.add( theRoom);
				city.drawOutline( theRoom, 3);
			}
			for( Vector2i d : newDoors){
				city.addDoor( new Vector2i( r.getX() + d.x, r.getY() + r.getHeight() - d.y - 1), ver);
				ver = !ver;
			}
		}
		if( miroirX && miroirY){
			for( Rectangle ro : newRooms){
				theRoom = new Rectangle( 
						r.getX() - ro.getX() + r.getWidth() - ro.getWidth(),
						r.getY() - ro.getY() + r.getHeight() - ro.getHeight(),
						ro.getWidth(),
						ro.getHeight());
				buildRegion.add( theRoom);
				city.drawOutline( theRoom, 3);
			}
			for( Vector2i d : newDoors){
				city.addDoor( new Vector2i( r.getX() + r.getWidth() - d.x - 1, r.getY() + r.getHeight() - d.y - 1), ver);
				ver = !ver;
			}
		}
		
		rooms.add( new Room( buildRegion.get( buildRegion.size() - 3)));
		rooms.add( new Room( buildRegion.get( buildRegion.size() - 5)));
	}
	
	/***
	 * Return the Rooms of the Apartment
	 * @return Apartment's Rooms
	 */
	public ArrayList< Room> getRooms() {
		return rooms;
	}
}
