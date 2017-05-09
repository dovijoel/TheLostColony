/**
 * 
 */
package acsse.csc2a.game.model;

import java.io.Serializable;

/**
 * @author Joel, DM, 201071264
 *
 */
public class Colonist extends GameElement implements IEvaluatable, Serializable{
	
	private Integer health;
	private Integer happiness;
	
	//TODO getters and setters

	//TODO validate position: position for colonist must represent a house. do at placement?

	public Colonist() {
		health = 100;
		happiness = 100;
	}
	
	/**
	 * ***VISITOR DESIGN PATTERN***
	 * @param visitor visitor to evaluate
	 */
	@Override
	public void accept(IEvaluateVisitor visitor) {
		visitor.evaluate(this);
	}

	/**
	 * @return the health
	 */
	public Integer getHealth() {
		return health;
	}

	/**
	 * @param health the health to set
	 */
	public void setHealth(Integer health) {
		this.health = health;
	}

	/**
	 * @return the happiness
	 */
	public Integer getHappiness() {
		return happiness;
	}

	/**
	 * @param happiness the happiness to set
	 */
	public void setHappiness(Integer happiness) {
		this.happiness = happiness;
	}

}
