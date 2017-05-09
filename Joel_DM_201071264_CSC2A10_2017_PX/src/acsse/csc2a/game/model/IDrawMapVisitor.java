/**
 * 
 */
package acsse.csc2a.game.model;

import javafx.scene.canvas.GraphicsContext;

/**
 * @author Joel, DM, 201071264
 *
 */
public interface IDrawMapVisitor {
	public void render(mapItem m);
}
