package fission.core;

import java.util.ArrayList;
import java.util.Arrays;

import ai.interfaces.AbstractEvaluationFunction;
import ai.interfaces.AbstractMove;
import ai.interfaces.ComputerPlayerIf;
import ai.interfaces.GameIf;

public class GreedyPlayer implements ComputerPlayerIf {
	GameIf game;
	AbstractEvaluationFunction evaluationFunction;

	public void setEvaluationFunction(
			AbstractEvaluationFunction aEvaluationFunction) {
		evaluationFunction = aEvaluationFunction;
	}

	@Override
	public AbstractMove getNextMove() {
		ArrayList<AbstractMove> moves;
		moves = new ArrayList<AbstractMove>(Arrays.asList(game.getAllMoves()));
		int bestEvaluation = 0;
		AbstractMove bestMove = null;

		for (AbstractMove m : moves) {
			int evaluation;
			if (bestMove == null) {
				bestMove = m;
				bestEvaluation = evaluationFunction.evaluateMove(
						game.getsState(), m);
				continue;
			}
			evaluation = evaluationFunction.evaluateMove(game.getsState(), m);
			if (evaluation > bestEvaluation) {
				bestEvaluation = evaluation;
				bestMove = m;
			}
		}
		return bestMove;
	}

	@Override
	public void setGame(GameIf aGame) {
		game = aGame;
	}

}
