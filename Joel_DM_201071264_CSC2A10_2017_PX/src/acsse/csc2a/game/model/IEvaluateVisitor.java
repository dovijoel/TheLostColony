/**
 * 
 */
package acsse.csc2a.game.model;

/**
 * @author Joel, DM, 201071264
 * Interface for evaluators
 */
public interface IEvaluateVisitor {
	public void evaluate(Colonist colonist);
	public void evaluate(Structure structure);

}
