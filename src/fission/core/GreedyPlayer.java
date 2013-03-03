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
	boolean isWhitePlayer;

	public GreedyPlayer(GameIf aGame,
			AbstractEvaluationFunction aEvaluationFunction,
			boolean aIsWhitePlayer) {
		game = aGame;
		evaluationFunction = aEvaluationFunction;
		isWhitePlayer = aIsWhitePlayer;
	}

	@Override
	public AbstractMove getNextMove() {
		ArrayList<AbstractMove> moves;
		moves = game.getAllMoves();
		int bestEvaluation = 0;
		AbstractMove bestMove = null;

		for (AbstractMove m : moves) {
			int evaluation;
			if (bestMove == null) {
				bestMove = m;
				bestEvaluation = evaluationFunction.evaluateState(
						game.getActualState(), isWhitePlayer);
				continue;
			}
			evaluation = evaluationFunction.evaluateState(
					game.getActualState(), isWhitePlayer);
			if (isWhitePlayer) {
				if (evaluation > bestEvaluation) {
					bestEvaluation = evaluation;
					bestMove = m;
				}
			} else {
				if (evaluation < bestEvaluation) {
					bestEvaluation = evaluation;
					bestMove = m;
				}
			}

		}
		return bestMove;
	}

}
