package ai.interfaces;

public abstract class AbstractEvaluationFunction {
	public abstract int evaluateMove(AbstractState aActualState, AbstractMove aNextMove);
}
