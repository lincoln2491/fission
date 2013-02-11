package fission.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import ai.interfaces.AbstractMove;
import ai.interfaces.ComputerPlayerIf;
import ai.interfaces.GameIf;

public class RandomPlayer implements ComputerPlayerIf {
	GameIf game;

	@Override
	public AbstractMove getNextMove() {
		ArrayList<AbstractMove> moves;
		moves = new ArrayList<AbstractMove>(Arrays.asList(game.getAllMoves()));
		Random r = new Random();
		int index = r.nextInt(moves.size());
		return moves.get(index);
	}

	@Override
	public void setGame(GameIf aGame) {
		game = aGame;
	}

}
