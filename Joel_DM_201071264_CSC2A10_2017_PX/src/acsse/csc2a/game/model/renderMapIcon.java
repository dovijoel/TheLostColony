/**
 * 
 */
package acsse.csc2a.game.model;

import java.io.Serializable;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * @author Joel, DM, 201071264
 *
 */
public class renderMapIcon implements IDrawMapVisitor {
	private GraphicsContext gc;
	
	@Override
	public void render(mapItem m) {
		if (m.getIsDomed()) {
			gc.drawImage(m.getImage(), m.getXPos(), m.getYPos(), 64, 64);
		} else {
			gc.setFill(Color.GREY);
			gc.fillRect(m.getXPos(), m.getYPos(), 64, 64);
		}
		
	}

	/**
	 * @return the gc
	 */
	public GraphicsContext getGc() {
		return gc;
	}

	/**
	 * @param gc the gc to set
	 */
	public void setGc(GraphicsContext gc) {
		this.gc = gc;
	}

}
