/**
 * 
 */
package acsse.csc2a.game.model;

import java.io.Serializable;
import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;

/**
 * @author Joel, DM, 201071264
 *
 */
public class TilePane extends GridPane {
	private BorderPane imagePane;
	private Image image;
	private Label lblTitle;
	private Label lblStats = new Label("Stats:");
	private TextArea txtStats;
	private ArrayList<Button> buttons = new ArrayList<Button>();
	Canvas canvas;
	
	private Structure structure;
	
	public TilePane() {
		image = new Image("file:./data/none.jpg");
		lblTitle = new Label("Select a tile for options");
		canvas = new Canvas(128,128);
		imagePane = new BorderPane();
		imagePane.setPadding(new Insets(5, 5, 5, 5));
		imagePane.setLeft(canvas);
		imagePane.setCenter(lblTitle);
		canvas.getGraphicsContext2D().drawImage(image, 0, 0, 128, 128);
		
		
		txtStats = new TextArea();
		txtStats.setEditable(false);
		txtStats.setWrapText(true);
		//set up the layout
		setVgap(10);
		setHgap(10);
		setPadding(new Insets(0, 10, 0, 10));
		add(imagePane, 0, 0);
		add(lblStats, 0, 1);
		add(txtStats, 0, 2);
		
	}
	
	public void setPane(mapItem item, boolean nextToRoad, int material, Game game) {
		//clear the old stuff
		getChildren().clear();
		buttons.clear();
		
		switch (item.getType()) {
		case DIRT:
			image = item.getImage();
			lblTitle.setText("Unused Dirt");
			canvas = new Canvas(128,128);
			imagePane = new BorderPane();
			imagePane.setPadding(new Insets(5, 5, 5, 5));
			imagePane.setLeft(canvas);
			imagePane.setCenter(lblTitle);
			canvas.getGraphicsContext2D().drawImage(image, 0, 0, 128, 128);
			buttons.clear();
			buttons.add(new Button("Road"));
			buttons.add(new Button("Farm"));
			buttons.add(new Button("Research Centre"));
			buttons.add(new Button("Dome Generator"));
			buttons.add(new Button("House"));
			buttons.add(new Button("Mining Facility"));
			buttons.add(new Button("Entertainment Centre"));
			buttons.add(new Button("Environmental Centre"));
			buttons.add(new Button("Powerplant"));
			
			for(Button b : buttons) {
				b.setPrefSize(64, 64);
				b.setFont(new Font(12));
				b.setAlignment(Pos.CENTER);
				b.setDisable(true);
			}
			
			if (nextToRoad) {
				txtStats.setText(String.format("This is unused dirt.%nLuckily, it is next to a road, so you can build something on it!%nIf you have enough material, click on a button below to build something."));
				for(Button b : buttons) {
					switch (b.getText()) {
					case("Road"):
						if (material > 50) {
							b.setDisable(false); 
							b.setOnAction(new EventHandler<ActionEvent>() {

								@Override
								public void handle(ActionEvent arg0) {
									game.newBuilding(EStructure.ROAD, structure.getPoint().getX(), structure.getPoint().getY());
								}
								
							});
							
						}
					
						break;
					case("Farm"):
						if (material > 250) { 
							b.setDisable(false); 
							b.setOnAction(new EventHandler<ActionEvent>() {

								@Override
								public void handle(ActionEvent arg0) {
									game.newBuilding(EStructure.FOODFACILITY, structure.getPoint().getX(), structure.getPoint().getY());
								}
							});
							}
						break;
					case("Research Centre"):
						if (material > 750) { 
							b.setDisable(false); 
							b.setOnAction(new EventHandler<ActionEvent>() {

								@Override
								public void handle(ActionEvent arg0) {
									game.newBuilding(EStructure.RESEARCH_FACILITY, structure.getPoint().getX(), structure.getPoint().getY());
								}
								
							});
							 
							}
						break;
					case("Dome Generator"):
						if (material > 500) { 
							b.setDisable(false); 
							b.setOnAction(new EventHandler<ActionEvent>() {

								@Override
								public void handle(ActionEvent arg0) {
									game.newBuilding(EStructure.DOME_GEN, structure.getPoint().getX(), structure.getPoint().getY());
								}
								
							});
							 
							}
						break;
					case("House"):
						if (material > 450) { 
							b.setDisable(false); 
							b.setOnAction(new EventHandler<ActionEvent>() {

								@Override
								public void handle(ActionEvent arg0) {
									game.newBuilding(EStructure.HOUSE, structure.getPoint().getX(), structure.getPoint().getY());
								}
								
							});
							 
							}
						break;
					case("Mining Facility"):
						if (material > 600) { 
							b.setDisable(false); 
							b.setOnAction(new EventHandler<ActionEvent>() {

								@Override
								public void handle(ActionEvent arg0) {
									game.newBuilding(EStructure.FABRICATOR, structure.getPoint().getX(), structure.getPoint().getY());
								}
								
							});
							 
							}
						break;
					case("Entertainment Centre"):
						if (material > 150) { 
							b.setDisable(false); 
							b.setOnAction(new EventHandler<ActionEvent>() {

								@Override
								public void handle(ActionEvent arg0) {
									game.newBuilding(EStructure.ENTERTAINMENT, structure.getPoint().getX(), structure.getPoint().getY());
								}
								
							});
							 
							}
						break;
					case("Environmental Centre"):
						if (material > 300) { 
							b.setDisable(false); 
							b.setOnAction(new EventHandler<ActionEvent>() {

								@Override
								public void handle(ActionEvent arg0) {
									game.newBuilding(EStructure.ENVIRO, structure.getPoint().getX(), structure.getPoint().getY());
								}
								
							});
							
							}
						break;
					case("Powerplant"):
						if (material > 550) { 
							b.setDisable(false); 
							b.setOnAction(new EventHandler<ActionEvent>() {

								@Override
								public void handle(ActionEvent arg0) {
									game.newBuilding(EStructure.POWERPLANT, structure.getPoint().getX(), structure.getPoint().getY());
								}
								
							});
							
							}
						break;
					}
				
				}
			} else {
				txtStats.setText(String.format("This is unused dirt.%nTo enable this tile for bulding, extend your road until it is adjacent to this point."));
			}
			break;
		case DOME_GEN:
			image = item.getImage();
			lblTitle.setText("Dome Generator");
			canvas = new Canvas(128,128);
			imagePane = new BorderPane();
			imagePane.setPadding(new Insets(5, 5, 5, 5));
			imagePane.setLeft(canvas);
			imagePane.setCenter(lblTitle);
			canvas.getGraphicsContext2D().drawImage(image, 0, 0, 128, 128);
			txtStats.setText(String.format("This is a dome generator, it allows your colonists to live and build on this alien planet."));
			break;
		case ENTERTAINMENT:
			image = item.getImage();
			lblTitle.setText("Entertainment Centre");
			canvas = new Canvas(128,128);
			imagePane = new BorderPane();
			imagePane.setPadding(new Insets(5, 5, 5, 5));
			imagePane.setLeft(canvas);
			imagePane.setCenter(lblTitle);
			canvas.getGraphicsContext2D().drawImage(image, 0, 0, 128, 128);
			txtStats.setText(String.format("This is an entertainment centre, where your colonists can maintain their happiness, which is vital for their health.%n%n"
					+ "An entertainment level of 5 per colonist is needed to maintain happiness.%n%n"
					+ "Current Entertainment per Colonist: %d", 
					(game.getEntertainment()/game.getPopulation())));
			break;
		case ENVIRO:
			image = item.getImage();
			lblTitle.setText("Enviromental Facility");
			canvas = new Canvas(128,128);
			imagePane = new BorderPane();
			imagePane.setPadding(new Insets(5, 5, 5, 5));
			imagePane.setLeft(canvas);
			imagePane.setCenter(lblTitle);
			canvas.getGraphicsContext2D().drawImage(image, 0, 0, 128, 128);
			txtStats.setText(String.format("This is an environmental facility, where clean air and water is generated for your colonists.%n%n"
					+ "An air level of %d and water level of %d per colonist is needed to maintain their health.%n%n"
					+ "Mining facilites consume power.%n"
					+ "Efficiency is determined as power available to the building.%n%n"
					+ "Current Air per Colonist: %d.%n"
					+ "Curent Water per Colonist: %d.%n"
					+ "Current Efficiency: %.1f%%", 
					10, 10, (game.getAir()/game.getPopulation()), (game.getWater()/game.getPopulation()), structure.getEfficiency()*100));
			
			break;
		case FABRICATOR:
			image = item.getImage();
			lblTitle.setText("Mining Facility");
			canvas = new Canvas(128,128);
			imagePane = new BorderPane();
			imagePane.setPadding(new Insets(5, 5, 5, 5));
			imagePane.setLeft(canvas);
			imagePane.setCenter(lblTitle);
			canvas.getGraphicsContext2D().drawImage(image, 0, 0, 128, 128);
			txtStats.setText(String.format("This is a mining facility, where material needed to build new structures is mined.%n%n"
					+ "Mining facilites consume power.%n"
					+ "Efficiency is determined as power available to the building.%n%n"
					+ "Current Efficiency: %.1f%%", 
					structure.getEfficiency()*100));
			
			break;
		case FOODFACILITY:
			image = item.getImage();
			lblTitle.setText("Farm");
			canvas = new Canvas(128,128);
			imagePane = new BorderPane();
			imagePane.setPadding(new Insets(5, 5, 5, 5));
			imagePane.setLeft(canvas);
			imagePane.setCenter(lblTitle);
			canvas.getGraphicsContext2D().drawImage(image, 0, 0, 128, 128);
			txtStats.setText(String.format("This is farm, where food is produced for your colonists, as well as a small amount of air.%n%n"
					+ "An food level of %d per colonist is needed to maintain their health.%n%n"
					+ "Farms consume power and water.%n"
					+ "Efficiency is determined as power available to the building.%n%n"
					+ "Current Food per Colonist: %d.%n"
					+ "Current Efficiency: %.1f%%", 
					10, (game.getFood()/game.getPopulation()), structure.getEfficiency()*100));
			
			break;
		case HOUSE:
			image = item.getImage();
			lblTitle.setText("House");
			canvas = new Canvas(128,128);
			imagePane = new BorderPane();
			imagePane.setPadding(new Insets(5, 5, 5, 5));
			imagePane.setLeft(canvas);
			imagePane.setCenter(lblTitle);
			canvas.getGraphicsContext2D().drawImage(image, 0, 0, 128, 128);
			txtStats.setText(String.format("This is a house, where our colonists live.%n%n"
					+ "If colonists are overcrowded, they become unhappy. They need a maximum of %d colonists per house.%n%n"
					+ "Houses consume power.%n%n"
					+ "Current Colonists per House: %d.%n", 
					10, (game.getPopulation()/game.getHouses())));
			break;
		case POWERPLANT:
			image = item.getImage();
			lblTitle.setText("House");
			canvas = new Canvas(128,128);
			imagePane = new BorderPane();
			imagePane.setPadding(new Insets(5, 5, 5, 5));
			imagePane.setLeft(canvas);
			imagePane.setCenter(lblTitle);
			canvas.getGraphicsContext2D().drawImage(image, 0, 0, 128, 128);
			txtStats.setText(String.format("This is a powerplant, where buildings produce power.%n%n"
					+ "If there isn't enough power produced, efficiency of buildings decreases.%n%n"));
			break;
		case RESEARCH_FACILITY:
			image = item.getImage();
			lblTitle.setText("Research Facility");
			canvas = new Canvas(128,128);
			imagePane = new BorderPane();
			imagePane.setPadding(new Insets(5, 5, 5, 5));
			imagePane.setLeft(canvas);
			imagePane.setCenter(lblTitle);
			canvas.getGraphicsContext2D().drawImage(image, 0, 0, 128, 128);
			txtStats.setText(String.format("This is a research facility, where the tech needed to repair your spaceship is developed.%n%n"
					+ "Once you have enough tech points accumulated, you can spend them at your spaceship.%n%n"
					+ "Reseach Facilities consume power.%n"
					+ "Its efficiency level in dependant on how many colonists are available per research facility.%n%n"
					+ "Current Colonists per Research Facility: %d.%n"
					+ "Current Efficiency: %.1f", 
					(game.getPopulation()/game.getTechs()), structure.getEfficiency()*100));
			break;
		case ROAD:
			image = item.getImage();
			lblTitle.setText("Road");
			canvas = new Canvas(128,128);
			imagePane = new BorderPane();
			imagePane.setPadding(new Insets(5, 5, 5, 5));
			imagePane.setLeft(canvas);
			imagePane.setCenter(lblTitle);
			canvas.getGraphicsContext2D().drawImage(image, 0, 0, 128, 128);
			txtStats.setText(String.format("This is a road. You need it to extend the area where you can build structures."));
			break;
		case SPACESHIP:
			image = item.getImage();
			lblTitle.setText("Spaceship");
			canvas = new Canvas(128,128);
			imagePane = new BorderPane();
			imagePane.setPadding(new Insets(5, 5, 5, 5));
			imagePane.setLeft(canvas);
			imagePane.setCenter(lblTitle);
			canvas.getGraphicsContext2D().drawImage(image, 0, 0, 128, 128);
			buttons.clear();
			buttons.add(new Button("Fix FTL"));
			buttons.add(new Button("Fix Cryogenic Storage"));
			buttons.add(new Button("Fix Shields"));
			
			for(Button b : buttons) {
				b.setPrefSize(64, 64);
				b.setFont(new Font(12));
				b.setAlignment(Pos.CENTER);
				switch (b.getText()) {
					case "Fix FTL":
						if (!game.isFTLFixed() && (game.getTech() > 10000)) {
							b.setDisable(false); 
							b.setOnAction(new EventHandler<ActionEvent>() {

								@Override
								public void handle(ActionEvent arg0) {
									game.setFTLFixed(true);
									structure.getState().add(new State(false, EResource.TECH, 10000));
								}
								
							});
						} else {
							b.setDisable(true);
						}
						break;
					case "Fix Cryogenic Storage":
						if (!game.isCryoFixed() && (game.getTech() > 2500)) {
							b.setDisable(false); 
							b.setOnAction(new EventHandler<ActionEvent>() {

								@Override
								public void handle(ActionEvent arg0) {
									game.setCryoFixed(true);
									structure.getState().add(new State(false, EResource.TECH, 2500));
								}
								
							});
						} else {
							b.setDisable(true);
						}
						break;
					case "Fix Shields":
						if (!game.isShieldsFixed() && (game.getTech() > 5000)) {
							b.setDisable(false); 
							b.setOnAction(new EventHandler<ActionEvent>() {

								@Override
								public void handle(ActionEvent arg0) {
									game.setShieldsFixed(true);
									structure.getState().add(new State(false, EResource.TECH, 5000));
								}
							});
						} else {
							b.setDisable(true);
						}
						break;
				}
				
			}
			String FTL = "Not fixed.";
			String Cryo = "Not fixed.";
			String Shields = "Not fixed.";
			
			if (game.isFTLFixed()) FTL = "Fixed!";
			if (game.isCryoFixed()) Cryo = "Fixed!";
			if (game.isShieldsFixed()) Shields = "Fixed!";
			
			txtStats.setText(String.format("This is the majestic USS Endeavour, endeavouring to go further than any human befor it. "
					+ "Unfortunately, it crashed, and it needs you to fix it.%n"
					+ "To fix the Endeavour, you need to get enough tech points to research and fix the three issue: the FTL drive, the chryogenic storage facilites, and the shields.%n%n"
					+ "Items that need fixing:%n"
					+ "FTL Drive: %s%n"
					+ "Cryogenic Storage: %s%n"
					+ "Shields: %s",
					FTL, Cryo, Shields));
			break;
		default:
			break;
		
		}
		
		//now add all the elements in
		canvas = new Canvas(128,128);
		imagePane = new BorderPane();
		imagePane.setPadding(new Insets(5, 5, 5, 5));
		imagePane.setLeft(canvas);
		imagePane.setCenter(lblTitle);
		canvas.getGraphicsContext2D().drawImage(image, 0, 0, 128, 128);
		
		//set up the layout
		setVgap(10);
		setHgap(10);
		setPadding(new Insets(0, 10, 0, 10));
		add(imagePane, 0, 0);
		add(lblStats, 0, 1);
		add(txtStats, 0, 2);
		
		if (buttons.size() > 0) {
			HBox hButtons = new HBox(5);
			hButtons.getChildren().addAll(buttons);
			add(hButtons, 0, 3);
		}
		
	}

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
}
