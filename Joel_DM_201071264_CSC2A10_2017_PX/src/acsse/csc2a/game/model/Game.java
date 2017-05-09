/**
 * This is the main game object, which will be instantiated for each game
 */
package acsse.csc2a.game.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import acsse.csc2a.game.file.FileReadWrite;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

/**
 * @author Joel, DM, 201071264
 *
 */

public class Game implements IObserver, Serializable{
	//stats of resources
	private Integer tech; //tech points for building
	private Integer air; //available air
	private Integer water; //available water
	private Integer power; //available electricity
	private Integer material; //available material for building
	private Integer population; //current population
	private Integer food; //available food
	private Integer size; //available size
	private Integer entertainment;
	private Integer houses;
	private Integer techs;
	private Integer days;
	private Integer domeCost, enterCost, enviroCost, fabCost, farmCost, houseCost, powerCost, researchCost, roadCost;
	
	//win/flags
	private boolean shieldsFixed = false;
	private boolean FTLFixed = false;
	private boolean cryoFixed = false;
	private boolean hasLost = false;
	private boolean hasWon = false;
	
	private transient MapPane mapPane = new MapPane();
	private transient DetailsPane detailsPane = new DetailsPane();
	
	private transient renderMapIcon drawMap = new renderMapIcon();
	
	private transient ScheduledExecutorService scheduler  = Executors.newScheduledThreadPool(5); //scheduled thread pool to update game stats
	private StandardEvaluator stdEval = new StandardEvaluator(); //visitor to evaluate
	
	private mapItem[][] mapTable; //2D array for placement of icons on map. need to be synchronised?
	
	private List<GameElement> world = Collections.synchronizedList(new ArrayList<GameElement>()); //synchronized (thread-safe) ArrayList of everything in the world

	//random generator
	public static Random random = new Random();
	
	public Game(String fileName) {
		FileReadWrite.loadSettings(fileName, this);
		initGame();
	}
	
	/**
	 * Initialises game
	 */
	private void initGame() {
		days = 0;
		
		//set size of the map array
		mapTable = new mapItem[size][size];
		//initialise map to dirt
		for(int col = 0; col < size; col++) {
			for (int row = 0; row < size; row++) {
				mapTable[col][row] = new mapItem(EStructure.DIRT, false);
				mapTable[col][row].setStructure(new Structure(new Coordinates(col, row), EStructure.DIRT));
			}
		}
		
		//initialise world array and place spaceship and population
		//place ship in the middle of the map
		Coordinates shipLoc = new Coordinates(size/2, size/2);
		Structure spaceship = new Structure(shipLoc, EStructure.SPACESHIP);
		world.add(spaceship);
		//need the starting roads
		world.add(new Structure(new Coordinates(shipLoc.getX()+1,shipLoc.getY()), EStructure.ROAD));
		world.add(new Structure(new Coordinates(shipLoc.getX()-1,shipLoc.getY()), EStructure.ROAD));
		world.add(new Structure(new Coordinates(shipLoc.getX(),shipLoc.getY()+1), EStructure.ROAD));
		world.add(new Structure(new Coordinates(shipLoc.getX(),shipLoc.getY()-1), EStructure.ROAD));
		
		
		//initially, the starting population lives in the ship
		for (int p = 0; p < population; p++) {
			Colonist c = new Colonist();
			world.add(c);
		}
		
		//add ship and roads to world map
		//perhaps better way of doing this with visitor pattern?
		for (GameElement g : world) {
			if (g instanceof Structure) {
				mapTable[((Structure) g).getPoint().getX()][ ((Structure) g).getPoint().getY()].setType(((Structure) g).getType());
				mapTable[((Structure) g).getPoint().getX()][ ((Structure) g).getPoint().getY()].setStructure((Structure) g);
			}
		}
		
		//set the dome area for the ship
		setDome(shipLoc.getX(), shipLoc.getY(), 5);
		
		//draw the initial map
		for (int c = 0; c < 100; c++) {
			for (int r = 0; r < 100; r++) {
				Canvas mapIcon = new Canvas(64,64);
				mapPane.getMapGrid().add(mapIcon, c, r);
				drawMap.setGc(mapIcon.getGraphicsContext2D());
				drawMap.render(mapTable[c][r]);
				int row = r;
				int col = c;
				mapIcon.setOnMouseClicked(e -> {
					detailsPane.getTilePane().setStructure(mapTable[col][row].getStructure());
					detailsPane.getTilePane().setPane(mapTable[col][row], isNextToRoad(col, row), material, this);
				});
			}
		}
		
		//centre the map
		mapPane.setHvalue((mapPane.getHmax()-mapPane.getHmin())/2);
		mapPane.setVvalue((mapPane.getVmax()-mapPane.getVmin())/2);
		
		detailsPane.getStatsPane().getTxtAir().setText(air.toString());
		detailsPane.getStatsPane().getTxtTech().setText(tech.toString());
		detailsPane.getStatsPane().getTxtPop().setText(population.toString());
		detailsPane.getStatsPane().getTxtPower().setText(power.toString());
		detailsPane.getStatsPane().getTxtFood().setText(food.toString());
		detailsPane.getStatsPane().getTxtEntertainment().setText(entertainment.toString());
		detailsPane.getStatsPane().getTxtHouses().setText(houses.toString());
		detailsPane.getStatsPane().getTxtPeopleperHouse().setText(String.format("%d", (population / houses)));
		//set the initial in dome area
		//System.out.format("ship: %d %d", shipLoc.getX(), shipLoc.getY());
		
		//start the evaluation threads
		update();
	}
	
	/**
	 * Update thread loop for evaluating the game
	 */
	public void update() {
		//need to close schedule at end of game - done with the shutdown function
		//increment days since crashed - every 10 seconds
		scheduler.scheduleAtFixedRate(new Runnable () {

			@Override
			public void run() {
				days++;
				detailsPane.getStatsPane().getTxtDay().setText(days.toString());
			}
			
		}, 0, 10, TimeUnit.SECONDS);
		//the updating task at fixed rate of every 10 seconds
		synchronized (this) {
			scheduler.scheduleAtFixedRate(new Runnable () {
		
			public void run() {
				//int counter = 0;
				int newColonists = 0;
				int deadColonists = 0;
				int newFood = 0;
				int usedFood = 0;
				int newPower = 0;
				int usedPower = 0;
				int newAir = 0;
				int usedAir = 0;
				int usedMaterial = 0;
				int newMaterial = 0;
				int newTech = 0;
				int usedTech = 0;
				int newWater = 0;
				int usedWater = 0;
				//System.out.println("Updating!: " + new java.util.Date());
				//go through the world array and check all structures for changes
				synchronized (world) {
					Iterator<GameElement> i  = world.iterator();
					while (i.hasNext()) {
						//System.out.println(counter++);
						GameElement g = i.next();
						//System.out.println("next");
							//if a structure has notified that there is a change then implement all changes
							if (g.getChanged()) {
/*								if (g instanceof Structure){
									switch (((Structure) g).getType()) {
									case DIRT:
										System.out.println("dirt");
										break;
									case DOME_GEN:
										System.out.println("dome");
										break;
									case ENTERTAINMENT:
										System.out.println("entertainment");
										break;
									case ENVIRO:
										System.out.println("enviro");
										break;
									case FABRICATOR:
										System.out.println("fab");
										break;
									case FOODFACILITY:
										System.out.println("food");
										break;
									case HOUSE:
										System.out.println("house");
										break;
									case POWERPLANT:
										System.out.println("power");
										break;
									case RESEARCH_FACILITY:
										System.out.println("research");
										break;
									case ROAD:
										System.out.println("road");
										break;
									case SPACESHIP:
										System.out.println("ship");
										break;
									default:
										break;
										
									}
								} else if (g instanceof Colonist) { System.out.println("colonist"); }
								//System.out.println("change stats");
*/								g.setChanged(false);
								for (State s : g.getState()) {
									if (s != null) {
										switch (s.getResourceType()) {
										case AIR:
//											System.out.println("air");
											if (s.getToAdd()) {
												air += s.getQuantity();
												newAir += s.getQuantity();
											} else {
											 air -= s.getQuantity();
											 usedAir += s.getQuantity();
											}
											break;
										case MATERIAL:
//											System.out.println("material");
											if (s.getToAdd()) {
												material += s.getQuantity();
												newMaterial += s.getQuantity();
											} else {
												material -= s.getQuantity();
												usedMaterial += s.getQuantity();
											}
											break;
										case FOOD:
//											System.out.println("food");
											if (s.getToAdd()) {
												food += s.getQuantity();
												newFood += s.getQuantity();
											} else {
												food -= s.getQuantity();
												usedFood += s.getQuantity();
											}
											break;
										case HAPPINESS:
//											System.out.println("happiness");
											break;
										case POWER:
//											System.out.println("power");
											if (s.getToAdd()) {
												power += s.getQuantity();
												newPower += s.getQuantity();
											} else {
												power -= s.getQuantity();
												usedPower += s.getQuantity();
											}
											break;
										case TECH:
//											System.out.println("tech");
											if (s.getToAdd()) {
												tech += s.getQuantity();
												newTech += s.getQuantity();
											} else {
												tech -= s.getQuantity();
												usedTech += s.getQuantity();
											}
											break;
										case WATER:
//											System.out.println("water");
											if (s.getToAdd()) {
												water += s.getQuantity();
												newWater += s.getQuantity();
											} else {
												water -= s.getQuantity();
												usedWater += s.getQuantity();
											}
											break;
										case COLONIST:
//											System.out.println("colonist");
											if (s.getToAdd()) {
												//can't add to List while iterating over it
												newColonists++;
											} else if (!s.getToAdd()) {
												i.remove(); //colonist dies
												population--;
												deadColonists++;
											}
											break;
										case HOUSES:
											if (s.getToAdd()) { 
												houses++; 
											} else {
												houses--; 
											}
											break;
										case TECHS:
											if (s.getToAdd()) { 
												techs++; 
											} else {
												techs--; 
											}
											break;
										default:
											break;
										}
									}
								}
								//reset the new state changes list and the isChanged variable
								ArrayList<State> blankList= new ArrayList<>();
								g.setState(blankList);
								g.setChanged(false);
							}
							
					} 
						
					
					//add new colonists
					for (int c = 0; c <newColonists; c++) {
						world.add(new Colonist());
//						System.out.println("Colonist added!");
					}
					population += newColonists;
//					System.out.println("Population: " + population);
				}
				detailsPane.getStatsPane().getTxtAir().setText(air.toString());
				detailsPane.getStatsPane().getTxtAirChange().setText(usedAir + " / " + newAir + ": Nett = " + (newAir-usedAir));
				detailsPane.getStatsPane().getTxtTech().setText(tech.toString());
				detailsPane.getStatsPane().getTxtTechChange().setText(usedTech + " / " + newTech + ": Nett = " + (newTech-usedTech));
				detailsPane.getStatsPane().getTxtPop().setText(population.toString());
				detailsPane.getStatsPane().getTxtPopChange().setText(newColonists + " / " + deadColonists + ": Nett = " + (newColonists-deadColonists));
				detailsPane.getStatsPane().getTxtPower().setText(power.toString());
				detailsPane.getStatsPane().getTxtPowerChange().setText(usedPower + " / " + newPower + ": Nett = " + (newPower-usedPower));
				detailsPane.getStatsPane().getTxtFood().setText(food.toString());
				detailsPane.getStatsPane().getTxtFoodChange().setText(usedFood + " / " + newFood + ": Nett = " + (newFood-usedFood));
				detailsPane.getStatsPane().getTxtEntertainment().setText(entertainment.toString());
				detailsPane.getStatsPane().getTxtEnterPerCapita().setText((String.format("%d", (entertainment / population))));
				detailsPane.getStatsPane().getTxtHouses().setText(houses.toString());
				detailsPane.getStatsPane().getTxtPeopleperHouse().setText(String.format("%d", (population / houses)));
				detailsPane.getStatsPane().getTxtMaterial().setText(material.toString());
				//detailsPane.getStatsPane().getTxtMaterialChanged().setText(String.format("%d", (population / houses)));
				
				
			}
		}, 6, 5, TimeUnit.SECONDS);
		}
		
		
		//schedule the stats update of buildings and people
		synchronized (this) {
			scheduler.scheduleAtFixedRate(new Runnable() {
				public void run() {
//					System.out.println("evaluating");
					synchronized (world) {
						Iterator<GameElement> i = world.iterator();
						while(i.hasNext()) {
							GameElement g = i.next();
							//check which type and get the gameElement to accept the visitor
							if (g instanceof Structure) { ((Structure) g).accept(stdEval); }
							if (g instanceof Colonist) { 
								stdEval.setFood(food);
								stdEval.setPopulation(population);
								stdEval.setEntertainment(entertainment);
								stdEval.setAir(air);
								stdEval.setWater(water);
								((Colonist) g).accept(stdEval); }
						}
						
					}
				}
			}, 0, 5, TimeUnit.SECONDS);
			
			
		}
		
		//evaluate if game is over every millisecond
		scheduler.scheduleAtFixedRate(new Runnable() {
			public void run() {
				if(FTLFixed && cryoFixed && shieldsFixed) hasWon = true;
				if(population <= 15) hasLost = true;
				if (hasWon || hasLost) {
					shutdownAndAwaitTermination();
					
				}
			}
		}, 0, 1, TimeUnit.MILLISECONDS);
		
	}
	
	/**
	 * Shuts down the pool
	 * Code is from JavaDocs 
	 * https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/ExecutorService.html
	 */
	public void shutdownAndAwaitTermination() {
		   scheduler.shutdown(); // Disable new tasks from being submitted
		   try {
		     // Wait a while for existing tasks to terminate
		     if (!scheduler.awaitTermination(60, TimeUnit.SECONDS)) {
		    	 scheduler.shutdownNow(); // Cancel currently executing tasks
		       // Wait a while for tasks to respond to being cancelled
		       if (!scheduler.awaitTermination(60, TimeUnit.SECONDS))
		           System.err.println("Pool did not terminate");
		     }
		   } catch (InterruptedException ie) {
		     // (Re-)Cancel if current thread also interrupted
			   scheduler.shutdownNow();
		     // Preserve interrupt status
		     Thread.currentThread().interrupt();
		   }
		 }
	
	private boolean isNextToRoad(int col, int row) {
		boolean nextToRoad = false;
		if (col-1 >= 0) {
			if (mapTable[col-1][row].getType() == EStructure.ROAD) nextToRoad = true; }
		if (col+1 < size) {
			if (mapTable[col+1][row].getType() == EStructure.ROAD) nextToRoad = true; }
		if  (row+1 < size) {
			if (mapTable[col][row+1].getType() == EStructure.ROAD) nextToRoad = true; }
		if (row-1 >= 0) {
		if (mapTable[col][row-1].getType() == EStructure.ROAD) nextToRoad = true; }
		return nextToRoad;
	}
	/**
	 * 
	 * @param type type of building to build
	 * @param row row to place buliding
	 * @param col column to place building
	 * @return whether building was successfully built
	 */
	public void newBuilding(EStructure type, int col, int row) {
		//add structure to world list
		//synchronized for thread safety
		synchronized (world) {
			int materialCost = 0;
			//calculate cost of new building
			switch (type) {
			case DIRT:
				break;
			case DOME_GEN:
				materialCost = 500;
				break;
			case ENTERTAINMENT:
				materialCost = 150;
				break;
			case ENVIRO:
				materialCost = 300;
				break;
			case FABRICATOR:
				materialCost = 600;
				break;
			case FOODFACILITY:
				materialCost = 250;
				break;
			case HOUSE:
				materialCost = 450;
				break;
			case POWERPLANT:
				materialCost = 550;
				break;
			case RESEARCH_FACILITY:
				materialCost = 750;
				break;
			case ROAD:
				materialCost = 50;
				break;
			case SPACESHIP:
				break;
			default:
				break;
			
			}
			Structure structure = new Structure(new Coordinates(col, row), type); //declare new structure
			structure.getState().add(new State(false, EResource.MATERIAL, materialCost)); //cost of building
			if (type == EStructure.HOUSE) structure.getState().add(new State(true, EResource.HOUSES, 1));
			//if (type == EStructure.RESEARCH_FACILITY) structure.getState().add(new State(true, EResource.TECHS, 1));
			world.add(structure);
			mapTable[col][row].setType(type); //add structure to mapTable
			mapTable[col][row].setStructure(structure);
			if (type == EStructure.DOME_GEN) {
				setDome(col, row, 5);
			}
			
		}
		
		//render structure on map
		Canvas canvas = new Canvas(64,64);
		GraphicsContext gc =  canvas.getGraphicsContext2D();
		mapPane.getMapGrid().add(canvas, col, row);
		drawMap.setGc(gc);
		mapTable[col][row].accept(drawMap);
		canvas.setOnMouseClicked(e -> {
			detailsPane.getTilePane().setStructure(mapTable[col][row].getStructure());
			detailsPane.getTilePane().setPane(mapTable[col][row], isNextToRoad(col, row), material, this);
		});
			
	}
	/**
	 * @return the tech
	 */
	public Integer getTech() {
		return tech;
	}

	/**
	 * @param tech the tech to set
	 */
	public void setTech(Integer tech) {
		this.tech = tech;
	}

	/**
	 * @return the air
	 */
	public Integer getAir() {
		return air;
	}

	/**
	 * @param air the air to set
	 */
	public void setAir(Integer air) {
		this.air = air;
	}

	/**
	 * @return the water
	 */
	public Integer getWater() {
		return water;
	}

	/**
	 * @param water the water to set
	 */
	public void setWater(Integer water) {
		this.water = water;
	}

	/**
	 * @return the power
	 */
	public Integer getPower() {
		return power;
	}

	/**
	 * @param power the power to set
	 */
	public void setPower(Integer power) {
		this.power = power;
	}

	/**
	 * @return the material
	 */
	public Integer getMaterial() {
		return material;
	}

	/**
	 * @param material the material to set
	 */
	public void setMaterial(Integer material) {
		this.material = material;
	}

	/**
	 * @return the population
	 */
	public Integer getPopulation() {
		return population;
	}

	/**
	 * @param population the population to set
	 */
	public void setPopulation(Integer population) {
		this.population = population;
	}

	/**
	 * @return the food
	 */
	public Integer getFood() {
		return food;
	}

	/**
	 * @param food the food to set
	 */
	public void setFood(Integer food) {
		this.food = food;
	}

	/**
	 * @return the size
	 */
	public Integer getSize() {
		return size;
	}

	/**
	 * @param size the size to set
	 */
	public void setSize(Integer size) {
		this.size = size;
	}

	/**
	 * @return the mapTable
	 */
	public mapItem[][] getMapTable() {
		return mapTable;
	}

	/**
	 * @param mapTable the mapTable to set
	 */
	public void setMapTable(mapItem[][] mapTable) {
		this.mapTable = mapTable;
	}
	
	/**
	 * 
	 * @param i index of world element to get
	 * @return element of world at index i
	 */
	public GameElement getWorldElement(int i) {
		return world.get(i);
	}
	
	/**
	 * adds a new element to the world
	 * @param g element to add
	 */
	public void addWorldElement(GameElement g) {
		world.add(g);
	}

	/**
	 * @return the world
	 */
	public List<GameElement> getWorld() {
		return world;
	}

	/**
	 * @param world the world to set
	 */
	public void setWorld(List<GameElement> world) {
		this.world = world;
	}

	/**
	 * @return the entertainment
	 */
	public Integer getEntertainment() {
		return entertainment;
	}

	/**
	 * @param entertainment the entertainment to set
	 */
	public void setEntertainment(Integer entertainment) {
		this.entertainment = entertainment;
	}

	/**
	 * Loads a game from a file
	 * @param fileName location of file to load
	 */
	public void loadGame() {
		mapPane = new MapPane();
		detailsPane = new DetailsPane();
		scheduler  = Executors.newScheduledThreadPool(5);
		drawMap = new renderMapIcon();
		for(int col = 0; col < size; col++) {
			for (int row = 0; row < size; row++) {
				mapTable[col][row].setImage(mapTable[col][row].getType().toImage());
				Canvas mapIcon = new Canvas(64,64);
				mapPane.getMapGrid().add(mapIcon, col, row);
				drawMap.setGc(mapIcon.getGraphicsContext2D());
				mapTable[col][row].accept(drawMap);
				int r = row;
				int c = col;
				mapIcon.setOnMouseClicked(e -> {
					detailsPane.getTilePane().setStructure(mapTable[c][r].getStructure());
					detailsPane.getTilePane().setPane(mapTable[c][r], isNextToRoad(c, r), material, this);
				});
			}
		}
		//centre the map
		mapPane.setHvalue((mapPane.getHmax()-mapPane.getHmin())/2);
		mapPane.setVvalue((mapPane.getVmax()-mapPane.getVmin())/2);
		update();
	}
	
	/**
	 * Saves a game to a file
	 */
	public boolean saveGame(String filePath) {
		return FileReadWrite.saveGame(filePath, this);
	}
	
	private void setDome(int centreX, int centreY, int radius) {
		for (int i = centreX - radius; i < centreX + radius; i++) { //increment through radius
			for (int j = centreY - 10; j < centreY + 10; j++) {
				int a = i - centreX;
				int b = j - centreY;
				if ((a*a) + (b*b) <= (radius*radius)) {
					if (i >= 0 && i < size && j >= 0 && j < size) {//check that values are inside dome radius
						if (!mapTable[i][j].getIsDomed()) { //check if already domed
							mapTable[i][j].setIsDomed(true);
							//render structure on map
							Canvas canvas = new Canvas(64,64);
							GraphicsContext gc =  canvas.getGraphicsContext2D();
							mapPane.getMapGrid().add(canvas, i, j);
							drawMap.setGc(gc);
							mapTable[i][j].accept(drawMap);
							//System.out.println("Area domed: " + i + "and " + j);
						}
					}
				}
			}
		}
	}

	/**
	 * @return the mapPane
	 */
	public MapPane getMapPane() {
		return mapPane;
	}

	/**
	 * @param mapPane the mapPane to set
	 */
	public void setMapPane(MapPane mapPane) {
		this.mapPane = mapPane;
	}

	/**
	 * @return the detailsPane
	 */
	public DetailsPane getDetailsPane() {
		return detailsPane;
	}

	/**
	 * @param detailsPane the detailsPane to set
	 */
	public void setDetailsPane(DetailsPane detailsPane) {
		this.detailsPane = detailsPane;
	}

	/**
	 * @return the hasWon
	 */
	public boolean isHasWon() {
		return hasWon;
	}

	/**
	 * @param hasWon the hasWon to set
	 */
	public void setHasWon(boolean hasWon) {
		this.hasWon = hasWon;
	}

	/**
	 * @return the houses
	 */
	public Integer getHouses() {
		return houses;
	}

	/**
	 * @param houses the houses to set
	 */
	public void setHouses(Integer houses) {
		this.houses = houses;
	}

	/**
	 * @return the techs
	 */
	public Integer getTechs() {
		return techs;
	}

	/**
	 * @param techs the techs to set
	 */
	public void setTechs(Integer techs) {
		this.techs = techs;
	}

	/**
	 * @return the shieldsFixed
	 */
	public boolean isShieldsFixed() {
		return shieldsFixed;
	}

	/**
	 * @param shieldsFixed the shieldsFixed to set
	 */
	public void setShieldsFixed(boolean shieldsFixed) {
		this.shieldsFixed = shieldsFixed;
	}

	/**
	 * @return the fTLFixed
	 */
	public boolean isFTLFixed() {
		return FTLFixed;
	}

	/**
	 * @param fTLFixed the fTLFixed to set
	 */
	public void setFTLFixed(boolean fTLFixed) {
		FTLFixed = fTLFixed;
	}

	/**
	 * @return the cryoFixed
	 */
	public boolean isCryoFixed() {
		return cryoFixed;
	}

	/**
	 * @param cryoFixed the cryoFixed to set
	 */
	public void setCryoFixed(boolean cryoFixed) {
		this.cryoFixed = cryoFixed;
	}

	/**
	 * @return the days
	 */
	public Integer getDays() {
		return days;
	}

	/**
	 * @param days the days to set
	 */
	public void setDays(Integer days) {
		this.days = days;
	}

	/**
	 * @return the hasLost
	 */
	public boolean isHasLost() {
		return hasLost;
	}

	/**
	 * @param hasLost the hasLost to set
	 */
	public void setHasLost(boolean hasLost) {
		this.hasLost = hasLost;
	}

	/**
	 * @return the domeCost
	 */
	public Integer getDomeCost() {
		return domeCost;
	}

	/**
	 * @param domeCost the domeCost to set
	 */
	public void setDomeCost(Integer domeCost) {
		this.domeCost = domeCost;
	}

	/**
	 * @return the enterCost
	 */
	public Integer getEnterCost() {
		return enterCost;
	}

	/**
	 * @param enterCost the enterCost to set
	 */
	public void setEnterCost(Integer enterCost) {
		this.enterCost = enterCost;
	}

	/**
	 * @return the enviroCost
	 */
	public Integer getEnviroCost() {
		return enviroCost;
	}

	/**
	 * @param enviroCost the enviroCost to set
	 */
	public void setEnviroCost(Integer enviroCost) {
		this.enviroCost = enviroCost;
	}

	/**
	 * @return the fabCost
	 */
	public Integer getFabCost() {
		return fabCost;
	}

	/**
	 * @param fabCost the fabCost to set
	 */
	public void setFabCost(Integer fabCost) {
		this.fabCost = fabCost;
	}

	/**
	 * @return the farmCost
	 */
	public Integer getFarmCost() {
		return farmCost;
	}

	/**
	 * @param farmCost the farmCost to set
	 */
	public void setFarmCost(Integer farmCost) {
		this.farmCost = farmCost;
	}

	/**
	 * @return the houseCost
	 */
	public Integer getHouseCost() {
		return houseCost;
	}

	/**
	 * @param houseCost the houseCost to set
	 */
	public void setHouseCost(Integer houseCost) {
		this.houseCost = houseCost;
	}

	/**
	 * @return the powerCost
	 */
	public Integer getPowerCost() {
		return powerCost;
	}

	/**
	 * @param powerCost the powerCost to set
	 */
	public void setPowerCost(Integer powerCost) {
		this.powerCost = powerCost;
	}

	/**
	 * @return the researchCost
	 */
	public Integer getResearchCost() {
		return researchCost;
	}

	/**
	 * @param researchCost the researchCost to set
	 */
	public void setResearchCost(Integer researchCost) {
		this.researchCost = researchCost;
	}

	/**
	 * @return the roadCost
	 */
	public Integer getRoadCost() {
		return roadCost;
	}

	/**
	 * @param roadCost the roadCost to set
	 */
	public void setRoadCost(Integer roadCost) {
		this.roadCost = roadCost;
	}

}
