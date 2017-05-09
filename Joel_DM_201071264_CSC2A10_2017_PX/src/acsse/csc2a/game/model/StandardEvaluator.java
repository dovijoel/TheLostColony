/**
 * 
 */
package acsse.csc2a.game.model;

import java.awt.List;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * @author Joel, DM, 201071264
 *
 */
public class StandardEvaluator implements IEvaluateVisitor, Serializable {
	private int food;
	private int entertainment;
	private int population;
	private int air;
	private int water;
	
	@Override
	public void evaluate(Colonist colonist) {
		ArrayList<State> list = new ArrayList<State>();
		int r = Game.random.nextInt(1000);
		//System.out.println(r);
		if (r < 10 ) { //less than 0.5% chance of new population
			//System.out.println("New colonist!");
			list.add(new State(true, EResource.COLONIST, 1));
		}
		//colonists use air, food and water
		list.add(new State(false, EResource.FOOD, 1));
		list.add(new State(false, EResource.AIR, 1));
		list.add(new State(false, EResource.WATER, 1));
		
		//if there is too little food, air, water or entertainment per person (less than 10), colonist gets unhappy
		if ((air / population) < 10) { 
			colonist.setHappiness(colonist.getHappiness() - 10); 
			} else { colonist.setHappiness(colonist.getHappiness()+10); }
		if ((water / population) < 10) { 
			colonist.setHappiness(colonist.getHappiness() - 5); 
			} else { colonist.setHappiness(colonist.getHappiness()+5); }
		if ((food / population) < 10) { 
			colonist.setHappiness(colonist.getHappiness() - 5); 
			} else { colonist.setHappiness(colonist.getHappiness()+5); }
		if ((entertainment / population) < 5) { 
			colonist.setHappiness(colonist.getHappiness() - 2); 
			} else { colonist.setHappiness(colonist.getHappiness()+2); }
		
		//System.out.format("Health: %d, Happiness: %d%n, Air: %d, Water: %d, Food: %d, Entertain: %d%n", colonist.getHealth(), colonist.getHappiness(),air/population, water / population, food / population, entertainment / population);
		//if happiness is below 50 they lose health, if above 50 they regain health
		if (colonist.getHappiness() < 50 && colonist.getHappiness() > 25) {
			colonist.setHealth(colonist.getHealth() - 10);
		} else if (colonist.getHappiness() <= 25) {
			colonist.setHealth(colonist.getHealth() - 15);
		} else if ((colonist.getHappiness() >= 50) && (colonist.getHealth() < 85))
		{
			colonist.setHealth(colonist.getHealth() + 15);
		}
		
		if (colonist.getHealth() <= 0) { //if the colonist has 0 health, they die
			list.add(new State(false, EResource.COLONIST, 1));
		}
		//add to list synchronized as it might be iterated over in another thread
		synchronized (colonist.getState()) {
			for (State s : list) {
				colonist.getState().add(s);
			}
		}

		colonist.setChanged(true);
	}

	@Override
	public void evaluate(Structure structure) {
		//evaluate according to structure type
		ArrayList<State> list = new ArrayList<State>();
		
		switch (structure.getType()) {
		case DIRT:
			//dirt does nothing
			break;
		case DOME_GEN:
			if (!structure.getChanged()) structure.setChanged(true);
			list.add(new State(false, EResource.POWER, 5));
			break;
		case ENTERTAINMENT:
			if (!structure.getChanged()) structure.setChanged(true);
			list.add(new State(false, EResource.POWER, 5));
			list.add(new State(false, EResource.FOOD, 1));
			break;
		case ENVIRO:
			if (!structure.getChanged()) structure.setChanged(true);
			list.add(new State(true, EResource.AIR, (int) (20 * structure.getEfficiency())));
			list.add(new State(true, EResource.WATER, (int) (20 * structure.getEfficiency())));
			list.add(new State(false, EResource.POWER, 5)); //uses power
			break;
		case FABRICATOR:
			if (!structure.getChanged()) structure.setChanged(true);
			list.add(new State(true, EResource.MATERIAL, (int) (100 * structure.getEfficiency())));
			list.add(new State(false, EResource.POWER, 5)); //uses power
			break;
		case FOODFACILITY:
			if (!structure.getChanged()) structure.setChanged(true);
			list.add(new State(true, EResource.FOOD, (int) (20 * structure.getEfficiency()))); //food facility makes food
			list.add(new State(true, EResource.AIR, (int) (1 * structure.getEfficiency()))); //plants make oxygen
			list.add(new State(false, EResource.POWER, 5)); //uses power
			list.add(new State(false, EResource.WATER, 5)); //uses water
			break;
		case HOUSE: 
			if (!structure.getChanged()) structure.setChanged(true);
			list.add(new State(false, EResource.POWER, 1));
			break;
		case POWERPLANT:
			if (!structure.getChanged()) structure.setChanged(true);
			list.add(new State(true, EResource.POWER, 20)); //makes power
			break;
		case RESEARCH_FACILITY:
			if (!structure.getChanged()) structure.setChanged(true);
			list.add(new State(true, EResource.TECH, (int) (10*structure.getEfficiency())));
			list.add(new State(false, EResource.POWER, 5)); //uses power
			break;
		case ROAD:
			break;
		case SPACESHIP:
			//spaceship is always efficient
			if (!structure.getChanged()) structure.setChanged(true);
			list.add(new State(true, EResource.FOOD, 100));
			list.add(new State(true, EResource.MATERIAL, 100));
			list.add(new State(true, EResource.AIR, 100));
			list.add(new State(true, EResource.WATER, 100));
			break;
		}
		
		//add to list synchronized as it might be iterated over in another thread
		synchronized (structure.getState()) {
			for (State s : list) {
				structure.getState().add(s);
			}
		}
		
	}

	/**
	 * @return the food
	 */
	public int getFood() {
		return food;
	}

	/**
	 * @param food the food to set
	 */
	public void setFood(int food) {
		this.food = food;
	}

	/**
	 * @return the population
	 */
	public int getPopulation() {
		return population;
	}

	/**
	 * @param population the population to set
	 */
	public void setPopulation(int population) {
		this.population = population;
	}

	/**
	 * @return the entertainment
	 */
	public int getEntertainment() {
		return entertainment;
	}

	/**
	 * @param entertainment the entertainment to set
	 */
	public void setEntertainment(int entertainment) {
		this.entertainment = entertainment;
	}
	
	/**
	 * @return the air
	 */
	public int getAir() {
		return air;
	}

	/**
	 * @param air the air to set
	 */
	public void setAir(int air) {
		this.air = air;
	}

	/**
	 * @return the water
	 */
	public int getWater() {
		return water;
	}

	/**
	 * @param water the water to set
	 */
	public void setWater(int water) {
		this.water = water;
	}

}
