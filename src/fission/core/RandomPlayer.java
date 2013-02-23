package fission.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import ai.interfaces.AbstractMove;
import ai.interfaces.ComputerPlayerIf;
import ai.interfaces.GameIf;

public class RandomPlayer implements ComputerPlayerIf {
	GameIf game;

	public RandomPlayer(GameIf aGame) {
		game = aGame;
	}

	@Override
	public AbstractMove getNextMove() {
		ArrayList<AbstractMove> moves;
		moves = game.getAllMoves();
		Random r = new Random();
		int index = r.nextInt(moves.size());
		return moves.get(index);
	}

}
