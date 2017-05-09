/**
 * 
 */
package acsse.csc2a.game.model;

import java.io.Serializable;

/**
 * @author Joel, DM, 201071264
 * Position of game element
 */
public class Coordinates implements Serializable{
	//x and y coordinates of game object
	private Integer x;
	private Integer y;
	
	/**
	 * 
	 * @param x x coordinate
	 * @param y y coordinate
	 */
	public Coordinates(Integer x, Integer y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * @return the x
	 */
	public Integer getX() {
		return x;
	}

	/**
	 * @param x the x to set
	 */
	public void setX(Integer x) {
		this.x = x;
	}

	/**
	 * @return the y
	 */
	public Integer getY() {
		return y;
	}

	/**
	 * @param y the y to set
	 */
	public void setY(Integer y) {
		this.y = y;
	}
}
