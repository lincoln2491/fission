package fission.core;

import ai.interfaces.AbstractEvaluationFunction;
import ai.interfaces.AbstractMove;
import ai.interfaces.ComputerPlayerIf;
import ai.interfaces.GameIf;

public class AlfaBetaPlayer implements ComputerPlayerIf {
	GameIf game;
	AbstractEvaluationFunction evaluationFunction;
	int depthOfSearching;

	public AlfaBetaPlayer(GameIf aGame,
			AbstractEvaluationFunction aEvaluationFunction,
			int aDepthOfSearching) {
		game = aGame;
		evaluationFunction = aEvaluationFunction;
		depthOfSearching = aDepthOfSearching;
	}

	@Override
	public AbstractMove getNextMove(boolean aIsForWhitePlayer) {
		// TODO Auto-generated method stub
		return null;
	}

}
