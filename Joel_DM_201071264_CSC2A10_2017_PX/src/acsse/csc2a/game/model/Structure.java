/**
 * 
 */
package acsse.csc2a.game.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Joel, DM, 201071264
 *
 */
public class Structure extends GameElement implements IEvaluatable, ISubject, Serializable {

	private EStructure type; //type of building
	private Double efficiency; //efficiency rating, calculated by what it needs and what is available
	private Coordinates point; //2D coordinate for placement
	
	
	
	
	/**
	 * @param point location of structure
	 * @param type type of structure
	 */
	public Structure(Coordinates point, EStructure type) {
		this.point = point;
		this.type = type;
		efficiency = 1.0;
		//TODO evaluate efficiency
	}
	
	public static Boolean validatePosition(Coordinates point) {
		//TODO validation algorithm
		return true;
	}
	
	//TODO calculate efficiency: use visitor?
	/**
	 * ***VISITOR DESIGN PATTERN***
	 * @param visitor visitor to evaluate
	 */
	@Override
	public void accept(IEvaluateVisitor visitor) {
		visitor.evaluate(this);
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
	}

	/**
	 * @return the efficiency
	 */
	public Double getEfficiency() {
		return efficiency;
	}

	/**
	 * @param efficiency the efficiency to set
	 */
	public void setEfficiency(Double efficiency) {
		this.efficiency = efficiency;
	}

	/**
	 * @return the point
	 */
	public Coordinates getPoint() {
		return point;
	}

	/**
	 * @param point the point to set
	 */
	public void setPoint(Coordinates point) {
		this.point = point;
	}
	
	//TODO getters and setters
	
	

	
}
