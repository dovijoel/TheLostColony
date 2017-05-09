/**
 * 
 */
package acsse.csc2a.game.model;

import java.io.Serializable;

import javafx.scene.canvas.Canvas;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

/**
 * @author Joel, DM, 201071264
 *
 */
public class MapPane extends ScrollPane{
	private GridPane mapGrid;
	public MapPane() {
		mapGrid = new GridPane();
		setPrefViewportHeight(1024); 
		setPrefViewportWidth(1024);
		mapGrid.setGridLinesVisible(true);
		mapGrid.setPrefSize(64*100, 64*100);
		for (int i = 0; i < 100; i++) {
			ColumnConstraints mapColumnContraints = new ColumnConstraints();
			mapColumnContraints.setPercentWidth(100 / 100);
			RowConstraints mapRowConstraints = new RowConstraints();
			mapRowConstraints.setPercentHeight(100/100);
			mapGrid.getColumnConstraints().add(mapColumnContraints);
			mapGrid.getRowConstraints().add(mapRowConstraints);
		}
		
		setContent(mapGrid);
		
	}
	
	/**
	 * @return the mapGrid
	 */
	public GridPane getMapGrid() {
		return mapGrid;
	}
	/**
	 * @param mapGrid the mapGrid to set
	 */
	public void setMapGrid(GridPane mapGrid) {
		this.mapGrid = mapGrid;
	}
}
