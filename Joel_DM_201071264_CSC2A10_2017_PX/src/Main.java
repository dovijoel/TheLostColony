import java.io.File;

import acsse.csc2a.game.file.FileReadWrite;
import acsse.csc2a.game.model.Game;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.FileChooser;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * 
 */

/**
 * With help from tutorial at: https://gamedevelopment.tutsplus.com/tutorials/introduction-to-javafx-for-game-development--cms-23835
 * @author Joel, DM, 201071264
 *
 */
public class Main extends Application {
	
	private Game game = null;
	/**
	 * Starting point for javafx application
	 */
	@Override
	public void start(Stage primaryStage) {
		//create the initial scene
		//root pane
		GridPane root = new GridPane();
		ColumnConstraints columnDetails = new ColumnConstraints();
		columnDetails.setPercentWidth(40);
		ColumnConstraints columnMap = new ColumnConstraints();
		columnMap.setPercentWidth(60);
		root.getColumnConstraints().addAll(columnDetails, columnMap);
		
		
		//menubar setup
		MenuBar menubar = new MenuBar();
		root.add(menubar, 0, 0);
		Menu fileMenu = new Menu("File");
		Menu helpMenu = new Menu("Help");
		MenuItem saveItem = new MenuItem("Save Game");
		saveItem.setDisable(true);
		MenuItem loadItem = new MenuItem("Load Game");
		MenuItem newItem = new MenuItem("New Game");
		
		MenuItem quitItem = new MenuItem("Quit");
		MenuItem aboutItem = new MenuItem("About");
		MenuItem helpItem = new MenuItem("Help");
		
		fileMenu.getItems().addAll(newItem, saveItem, loadItem, quitItem);
		helpMenu.getItems().addAll(helpItem, aboutItem);
		
		menubar.getMenus().addAll(fileMenu, helpMenu);
		Scene scene = new Scene(root);
		
		primaryStage.setTitle("The Lost Colony");
		primaryStage.setMaximized(true);
		primaryStage.setScene(scene);
		primaryStage.show();
		
		Group wonGroup = new Group();
		Scene wonScene = new Scene(wonGroup);
		Stage wonStage = new Stage();
		
		Canvas wonCanvas = new Canvas(500,150);
		wonGroup.getChildren().add(wonCanvas);
		GraphicsContext gc = wonCanvas.getGraphicsContext2D();
		newItem.setOnAction(e -> {
			game = new Game("./data/settings.txt");
			saveItem.setDisable(false);
			root.add(game.getDetailsPane(), 0, 1);
			root.add(game.getMapPane(), 1, 1);
			//check if game conditions are met
			new AnimationTimer() {

				@Override
				public void handle(long currentNanoTime) {
					if (game.isHasWon()) {
						gc.setFill(Color.BLACK);
						gc.fillRect(0, 0, 500, 150);
						gc.setFill( Color.GREEN );
					    gc.setLineWidth(2);
					    Font theFont = Font.font( 48 );
					    gc.setFont( theFont );
					    gc.fillText( "Well Done, You Have Won! :)",0, wonCanvas.getHeight() / 2 ,500);
					    wonStage.setTitle("You won!");
					    //wonStage.setMaximized(true);
						wonStage.setScene(wonScene);
					    wonStage.show();
					    stop();
					}
					
					if (game.isHasLost()) {
						gc.setFill(Color.BLACK);
						gc.fillRect(0, 0, 500, 150);
						gc.setFill( Color.RED );
					    gc.setLineWidth(2);
					    Font theFont = Font.font( 48 );
					    gc.setFont( theFont );
					    gc.fillText( "Sorry you lost :( Better luck next time!", 0, wonCanvas.getHeight() / 2, 500 );
					    wonStage.setTitle("You lost :(");
					    //wonStage.setMaximized(true);
						wonStage.setScene(wonScene);
					    wonStage.show();
					    stop();
					}
					
				}
				
			}.start();
		
		});
		
		saveItem.setOnAction(e -> {
			if (game != null) {
				FileChooser fileChooser = new FileChooser();
				fileChooser.setTitle("Choose Save Location");
				File file = fileChooser.showSaveDialog(primaryStage);
				game.saveGame(file.getAbsolutePath());
				
			}
		});
		
		loadItem.setOnAction(e -> {
			if (game != null) game.shutdownAndAwaitTermination(); //if another game is running, need to shut down those threads
			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("Choose Game to Load");
			File file = fileChooser.showOpenDialog(primaryStage);
			game = FileReadWrite.loadSave(file.getAbsolutePath());
			if (game != null) {
				game.loadGame();
				saveItem.setDisable(false);
				root.add(game.getDetailsPane(), 0, 1);
				root.add(game.getMapPane(), 1, 1);
				//check if game conditions are met
				new AnimationTimer() {

					@Override
					public void handle(long currentNanoTime) {
						if (game.isHasWon()) {
							gc.setFill(Color.BLACK);
							gc.fillRect(0, 0, 500, 150);
							gc.setFill( Color.GREEN );
						    gc.setLineWidth(2);
						    Font theFont = Font.font( 48 );
						    gc.setFont( theFont );
						    gc.fillText( "Well Done, You Have Won! :)",0, wonCanvas.getHeight() / 2 ,500);
						    wonStage.setTitle("You won!");
						    //wonStage.setMaximized(true);
							wonStage.setScene(wonScene);
						    wonStage.show();
						    stop();
						}
						
						if (game.isHasLost()) {
							gc.setFill(Color.BLACK);
							gc.fillRect(0, 0, 500, 150);
							gc.setFill( Color.RED );
						    gc.setLineWidth(2);
						    Font theFont = Font.font( 48 );
						    gc.setFont( theFont );
						    gc.fillText( "Sorry you lost :( Better luck next time!", 0, wonCanvas.getHeight() / 2, 500 );
						    wonStage.setTitle("You lost :(");
						    wonStage.setMaximized(true);
							wonStage.setScene(wonScene);
						    wonStage.show();
						    stop();
						}
						
					}
					
				}.start();
			}
			
		});
		
		helpItem.setOnAction(e -> {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("How to play this game");
			alert.setHeaderText("The Lost Colonists");
			String info = String.format("Your spaceship has crash-landed on an alien planet.%n"
					+ "Unfortunately, your Faster-Than-Light (FTL) drive, cryogenic storage to keep your colonists safe, and ship shields need to be repaired.%n"
					+ "In order to repair these items, you need to build Research facilites, where you will earn tech points whihc you spend on fixing the ship.%n%n"
					+ "To win, you need to repair all the items in your ship!%n%n"
					+ "If you have less than 15 colonists left, you will lose. Colonists die if they lose all their health. They lose health if they don't have enough air,%n"
					+ "water, happiness, or if they are overcrowded.%n"
					+ "Colonists are born automatically, therefore, you need to build houses, environmental facilities, farms and power stations.%n%n"
					+ "To build new structures, you need material, which you get from mining facilites.%n%n"
					+ "You can only build next to roads, and inside the dome. As you build more dome generators, your livable area will increase with a radius of 10 map tiles.%n%n"
					+ "Have Fun!");
					
			alert.setContentText(info);

			alert.showAndWait();
		});
		
		aboutItem.setOnAction(e -> {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("About");
			alert.setHeaderText("The Lost Colonists");
			String info = String.format("Game created by Dovi Joel, student number: 201071264.%n"
					+ "Graphics created by Lee-at Joel, under the Creative Commons License.");
					
			alert.setContentText(info);

			alert.showAndWait();
		});
		
		quitItem.setOnAction(e -> {
			Platform.exit();
		});
	}
		
		
	 
	/**
	 * when application closes, need to close all the threads
	 */
	@Override
	public void stop() {
		if (game != null) {
			game.shutdownAndAwaitTermination();
		}
	}

	/**
	 * the main method is for compatibility purposes
	 * @param args
	 */
	public static void main(String[] args) {
		//game = new Game(100);
		launch(args);
	}

}
