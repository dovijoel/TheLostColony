/**
 * 
 */
package acsse.csc2a.game.model;

import java.io.Serializable;

import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

/**
 * @author Joel, DM, 201071264
 * Pane for details of stats, and map tile details and actions
 */
public class DetailsPane extends VBox{
	private StatsPane statsPane;
	private TilePane tilePane;
	
	/**
	 * Default constructor
	 */
	public DetailsPane() {
		statsPane = new StatsPane();
		tilePane = new TilePane();
		
		getChildren().addAll(statsPane, tilePane);		
	}

	/**
	 * @return the statsPane
	 */
	public StatsPane getStatsPane() {
		return statsPane;
	}

	/**
	 * @param statsPane the statsPane to set
	 */
	public void setStatsPane(StatsPane statsPane) {
		this.statsPane = statsPane;
	}

	/**
	 * @return the tilePane
	 */
	public TilePane getTilePane() {
		return tilePane;
	}

	/**
	 * @param tilePane the tilePane to set
	 */
	public void setTilePane(TilePane tilePane) {
		this.tilePane = tilePane;
	}

}
