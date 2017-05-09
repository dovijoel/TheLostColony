/**
 * 
 */
package acsse.csc2a.game.model;

import java.io.Serializable;

/**
 * @author Joel, DM, 201071264
 * State to be added or removed
 */
public class State implements Serializable {
	private Boolean toAdd;
	private EResource resourceType;
	private Integer quantity;
	/**
	 * @param toAdd
	 * @param resourceType
	 * @param quantity
	 */
	public State(Boolean toAdd, EResource resourceType, Integer quantity) {
		this.toAdd = toAdd;
		this.resourceType = resourceType;
		this.quantity = quantity;
	}
	/**
	 * @return the toAdd
	 */
	public Boolean getToAdd() {
		return toAdd;
	}
	/**
	 * @param toAdd the toAdd to set
	 */
	public void setToAdd(Boolean toAdd) {
		this.toAdd = toAdd;
	}
	/**
	 * @return the resourceType
	 */
	public EResource getResourceType() {
		return resourceType;
	}
	/**
	 * @param resourceType the resourceType to set
	 */
	public void setResourceType(EResource resourceType) {
		this.resourceType = resourceType;
	}
	/**
	 * @return the quantity
	 */
	public Integer getQuantity() {
		return quantity;
	}
	/**
	 * @param quantity the quantity to set
	 */
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

}
