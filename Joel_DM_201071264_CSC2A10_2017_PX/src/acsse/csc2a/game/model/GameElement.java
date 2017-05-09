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
 * Abstract object from which all game elements will be inherited
 */
public abstract class GameElement implements ISubject, Serializable{
	
	
	
	//resource to be removed or added
	private List<State> state = Collections.synchronizedList(new ArrayList<>());
	private Boolean changed = false;
	
	/**
	 * Constructor for GameElement
	 * @param coordinates the coordinates the object must be places in
	 */
	public GameElement()
	{
		changed = false;
	}
	
	/**
	 * @return the state
	 */
	public List<State> getState() {
		return state;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(List<State> state) {
		this.state = state;
	}

	/**
	 * @return the changed
	 */
	public Boolean getChanged() {
		return changed;
	}

	/**
	 * @param changed the changed to set
	 */
	public void setChanged(Boolean changed) {
		this.changed = changed;
	}
	
	@Override
	public void notifyObservers() {
		changed = true;
	}
	
	//TODO abstract evaluate
}
