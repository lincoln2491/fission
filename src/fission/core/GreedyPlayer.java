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

	public GreedyPlayer(GameIf aGame,
			AbstractEvaluationFunction aEvaluationFunction) {
		game = aGame;
		evaluationFunction = aEvaluationFunction;
	}

	@Override
	public AbstractMove getNextMove(boolean aIsForWhitePlayer) {
		ArrayList<AbstractMove> moves;
		moves = game.getAllMoves();
		int bestEvaluation = 0;
		AbstractMove bestMove = null;

		for (AbstractMove m : moves) {
			int evaluation;
			if (bestMove == null) {
				bestMove = m;
				bestEvaluation = evaluationFunction.evaluateState(game
						.getActualState());
				continue;
			}
			evaluation = evaluationFunction
					.evaluateState(game.getActualState());
			if (evaluation > bestEvaluation) {
				bestEvaluation = evaluation;
				bestMove = m;
			}
		}
		return bestMove;
	}

}
