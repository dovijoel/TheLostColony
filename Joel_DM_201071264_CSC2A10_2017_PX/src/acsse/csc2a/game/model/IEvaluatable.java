/**
 * Interface for evaluatable objects
 */
package acsse.csc2a.game.model;

public interface IEvaluatable {
	public void accept(IEvaluateVisitor visitor);
}
