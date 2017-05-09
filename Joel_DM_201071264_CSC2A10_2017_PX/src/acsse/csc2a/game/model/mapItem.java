/**
 * 
 */
package acsse.csc2a.game.model;

import java.io.Serializable;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/** 
 * @author Joel, DM, 201071264
 *
 */
public class mapItem implements IDrawMapVisitable, Serializable{
	private EStructure type;
	private Boolean isDomed;
	private int XPos;
	private int YPos;
	private transient Image image;
	private Structure structure;
	
	/**
	 * @return the structure
	 */
	public Structure getStructure() {
		return structure;
	}

	/**
	 * @param structure the structure to set
	 */
	public void setStructure(Structure structure) {
		this.structure = structure;
	}

	/**
	 * @param type
	 * @param isDomed
	 */
	public mapItem(EStructure type, Boolean isDomed) {
		this.type = type;
		this.isDomed = isDomed;
		updateImage();
	}
	
	private void updateImage() {
		image = type.toImage();
		
	}
	
	public void accept(IDrawMapVisitor visitor) {
		visitor.render(this);
	}
	/**
	 * @return the type
	 */
	public EStructure getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(EStructure type) {
		this.type = type;
		updateImage();
	}
	/**
	 * @return the isDomed
	 */
	public Boolean getIsDomed() {
		return isDomed;
	}
	/**
	 * @param isDomed the isDomed to set
	 */
	public void setIsDomed(Boolean isDomed) {
		this.isDomed = isDomed;
	}

	/**
	 * @return the xPos
	 */
	public int getXPos() {
		return XPos;
	}

	/**
	 * @param xPos the xPos to set
	 */
	public void setXPos(int xPos) {
		XPos = xPos;
	}

	/**
	 * @return the yPos
	 */
	public int getYPos() {
		return YPos;
	}

	/**
	 * @param yPos the yPos to set
	 */
	public void setYPos(int yPos) {
		YPos = yPos;
	}

	/**
	 * @return the image
	 */
	public Image getImage() {
		return image;
	}

	/**
	 * @param image the image to set
	 */
	public void setImage(Image image) {
		this.image = image;
	}
	
	
}
