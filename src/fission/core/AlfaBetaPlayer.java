package fission.core;

import java.util.ArrayList;
import java.util.HashMap;

import ai.interfaces.AbstractEvaluationFunction;
import ai.interfaces.AbstractMove;
import ai.interfaces.AbstractState;
import ai.interfaces.ComputerPlayerIf;
import ai.interfaces.GameIf;

public class AlfaBetaPlayer implements ComputerPlayerIf {
	GameIf game;
	AbstractEvaluationFunction evaluationFunction;
	int depthOfSearching;
	boolean isWhitePlayer;
	boolean withTT;
	private HashMap<String, Integer> TT;

	public AlfaBetaPlayer(GameIf aGame,
			AbstractEvaluationFunction aEvaluationFunction,
			int aDepthOfSearching, boolean aIsWhitePlayer, boolean aWithTT) {
		game = aGame;
		evaluationFunction = aEvaluationFunction;
		depthOfSearching = aDepthOfSearching;
		isWhitePlayer = aIsWhitePlayer;
		withTT = aWithTT;
		if (withTT) {
			TT = new HashMap<String, Integer>();
		}
	}

	@Override
	public AbstractMove getNextMove() {
		AbstractMove bestMove = null;
		int valueOfBestMove;
		if (isWhitePlayer) {
			valueOfBestMove = Integer.MIN_VALUE;
		} else {
			valueOfBestMove = Integer.MAX_VALUE;
		}

		for (AbstractMove m : game.getAllMoves()) {
			AbstractState state = game.getStateAfterMove(game.getActualState(),
					m);
			int currentValue = deeperAndDeeper(depthOfSearching - 1, state,
					Integer.MIN_VALUE, Integer.MAX_VALUE, isWhitePlayer);
			if (currentValue >= valueOfBestMove && isWhitePlayer) {
				valueOfBestMove = currentValue;
				bestMove = m;
			}
			if (currentValue <= valueOfBestMove && !isWhitePlayer) {
				valueOfBestMove = currentValue;
				bestMove = m;
			}
		}
		return bestMove;
	}

	private void saveToTT(AbstractState aState, int aValue, int aDepth,
			int aAlpha, int aBeta) {
		String key;
		Integer value;
		value = aValue;
		key = aState.toString() + "-" + Integer.toString(aDepth) + "-";
		if (value <= aAlpha) {
			key += "u";
		} else if (value >= aBeta) {
			key += "l";
		} else {
			key += "a";
		}

		TT.put(key, value);
	}
	

	private int deeperAndDeeper(int aDepth, AbstractState aState, int aAlfa,
			int aBeta, boolean aIsWhitePlayer) {
		ArrayList<AbstractMove> allMoves;
		if (aDepth == 0) {
			return evaluationFunction.evaluateState(aState);
		}
		
		if(withTT){
			
		}
		
		if (aIsWhitePlayer) {
			allMoves = game.getAllMovesFromState(aState, true);
			
			
			if (allMoves.size() == 0) {
				return evaluationFunction.evaluateState(aState);
			}
			for (AbstractMove m : allMoves) {
				int currentValue = deeperAndDeeper(aDepth - 1,
						game.getStateAfterMove(aState, m), aAlfa, aBeta, false);

				aAlfa = Math.max(currentValue, aAlfa);
				if (aAlfa >= aBeta) {
					return aBeta;
				}

			}
			return aAlfa;
		} else {
			allMoves = game.getAllMovesFromState(aState, false);
			if (allMoves.size() == 0) {
				return evaluationFunction.evaluateState(aState);
			}
			for (AbstractMove m : allMoves) {
				int currentValue = deeperAndDeeper(aDepth - 1,
						game.getStateAfterMove(aState, m), aAlfa, aBeta, true);

				aBeta = Math.max(currentValue, aBeta);
				if (aAlfa >= aBeta) {
					return aAlfa;
				}

			}
			return aBeta;
		}
	}
	
	private class TTMove{
		int x, y, xDirection, yDirection;
		int value;
		char bound;
		int depth;
		
	}
}
