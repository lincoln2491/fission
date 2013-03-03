package fission.core;

import java.util.ArrayList;
import java.util.HashMap;

import ai.interfaces.AbstractEvaluationFunction;
import ai.interfaces.AbstractMove;
import ai.interfaces.AbstractState;
import ai.interfaces.ComputerPlayerIf;
import ai.interfaces.GameIf;

public class AlfaBetaPlayerWithTTPlayer implements ComputerPlayerIf {

	private HashMap<String, TTMove> TTWhite;
	private HashMap<String, TTMove> TTBlack;

	GameIf game;
	AbstractEvaluationFunction evaluationFunction;
	int depthOfSearching;
	boolean isWhitePlayer;
	boolean withTT;
	int numberOfVisited;

	public AlfaBetaPlayerWithTTPlayer(GameIf aGame,
			AbstractEvaluationFunction aEvaluationFunction,
			int aDepthOfSearching, boolean aIsWhitePlayer) {
		game = aGame;
		evaluationFunction = aEvaluationFunction;
		depthOfSearching = aDepthOfSearching;
		isWhitePlayer = aIsWhitePlayer;

		TTWhite = new HashMap<String, TTMove>();
		TTBlack = new HashMap<String, TTMove>();
	}

	@Override
	public AbstractMove getNextMove() {
		numberOfVisited = 0;
		AbstractMove bestMove = null;
		int valueOfBestMove = Integer.MIN_VALUE;

		for (AbstractMove m : game.getAllMoves()) {
			AbstractState state = game.getStateAfterMove(game.getActualState(),
					m);
			int currentValue = -deeperAndDeeper(depthOfSearching - 1, state,
					-Integer.MAX_VALUE, -Integer.MIN_VALUE, isWhitePlayer);

			if (currentValue >= valueOfBestMove && isWhitePlayer) {
				valueOfBestMove = currentValue;
				bestMove = m;
			}
			if (currentValue <= valueOfBestMove && !isWhitePlayer) {
				valueOfBestMove = currentValue;
				bestMove = m;
			}
		}

		System.out.println("Alfabeta TT " + numberOfVisited);
		return bestMove;

	}

	private int deeperAndDeeper(int aDepth, AbstractState aState, int aAlfa,
			int aBeta, boolean aIsWhitePlayer) {
		numberOfVisited++;
		ArrayList<AbstractMove> allMoves;
		int best;
		int prevAlpha = aAlfa;
		AbstractMove bestMove = null;
		AbstractMove moveFromTT = null;
		int valueOfBestMove = Integer.MIN_VALUE;
		if (aDepth == 0) {
			return evaluationFunction.evaluateState(aState);
		}

		TTMove move;
		if (aIsWhitePlayer) {
			move = TTWhite.get(aState.toString());
		} else {
			move = TTBlack.get(aState.toString());
		}

		if (move != null && move.depth >= aDepth) {
			if (move.bound == 'l') {
				aAlfa = Math.max(aAlfa, move.value);
			} else if (move.bound == 'u') {
				aBeta = Math.min(aBeta, move.value);
			} else if (move.bound == 'a') {
				aAlfa = move.value;
				aBeta = move.value;
			}
			if (aAlfa >= aBeta) {
				return move.value;
			}
		}

		allMoves = game.getAllMovesFromState(aState, aIsWhitePlayer);
		if (move != null) {
			moveFromTT = new FissionMove(move.x, move.y, move.xDirection,
					move.yDirection);
			allMoves.add(0, moveFromTT);
		}
		for (AbstractMove m : allMoves) {
			if (m.equals(moveFromTT) && m != moveFromTT) {
				continue;
			}
			int currentValue = -deeperAndDeeper(aDepth - 1,
					game.getStateAfterMove(aState, m), -aBeta, -aAlfa,
					!aIsWhitePlayer);

			if (currentValue > valueOfBestMove) {
				valueOfBestMove = currentValue;
				bestMove = m;
			}
			if (valueOfBestMove >= aBeta) {
				break;
			}
			if (valueOfBestMove > aAlfa) {
				aAlfa = valueOfBestMove;
			}
		}
		saveToTT(aState, valueOfBestMove, aDepth, aAlfa, aBeta, bestMove);
		return valueOfBestMove;
	}

	private void saveToTT(AbstractState aState, int aValue, int aDepth,
			int aAlpha, int aBeta, AbstractMove aMove) {
		if (aMove == null) {
			return;
		}
		TTMove value = new TTMove();
		String key = aState.toString();
		value.depth = aDepth;
		value.value = aValue;
		value.x = ((FissionMove) aMove).getX();
		value.y = ((FissionMove) aMove).getY();
		value.xDirection = ((FissionMove) aMove).getxDirection();
		value.yDirection = ((FissionMove) aMove).getyDirection();

		if (aValue <= aAlpha) {
			value.bound = 'u';
		} else if (aValue >= aBeta) {
			value.bound = 'l';
		} else {
			value.bound = 'a';
		}

		if (aState.isWhitePlayerTurn()) {
			TTWhite.put(key, value);
		} else {
			TTBlack.put(key, value);
		}
	}

	private class TTMove {
		public int x, y, xDirection, yDirection;
		public int value;
		public char bound;
		public int depth;
	}

}
