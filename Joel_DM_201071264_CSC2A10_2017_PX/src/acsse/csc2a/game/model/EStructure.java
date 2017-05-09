/**
 * 
 */
package acsse.csc2a.game.model;

import javafx.scene.image.Image;

/**
 * @author Joel, DM, 201071264
 * Enum of different structures. Might not use this....check back here
 */
public enum EStructure {
	POWERPLANT(Images.POWERPLANT),
	FABRICATOR(Images.FABRICATOR),
	FOODFACILITY(Images.FOODFACILITY),
	ENVIRO(Images.ENVIRO),
	RESEARCH_FACILITY(Images.RESEARCH_FACILITY),
	DOME_GEN(Images.DOME_GEN),
	HOUSE(Images.HOUSE),
	ENTERTAINMENT(Images.ENTERTAINMENT),
	SPACESHIP(Images.SPACESHIP),
	DIRT(Images.DIRT),
	ROAD(Images.ROAD);

	private Image image;
	
	private EStructure(Image image) {
		this.image = image;
	}
	
	public Image toImage() {
		return image;
	}
	
}
