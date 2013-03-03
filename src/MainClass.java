import java.util.ArrayList;

import ai.interfaces.AbstractEvaluationFunction;
import ai.interfaces.AbstractMove;
import ai.interfaces.ComputerPlayerIf;
import ai.interfaces.GameIf;

import fission.core.AlfaBetaPlayer;
import fission.core.AlfaBetaPlayerWithTTPlayer;
import fission.core.FieldColor;
import fission.core.FissionEvaluateFunction;
import fission.core.FissionGame;
import fission.core.FissionMove;
import fission.core.FissionState;
import fission.core.GreedyPlayer;
import fission.gui.GameMaker;

public class MainClass {

	public static void test() {
		FissionState s = new FissionState();
		FieldColor board[][] = new FieldColor[8][8];
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				board[i][j] = FieldColor.EMPTY;
			}
		}
		FissionGame g = new FissionGame(true);
		AbstractEvaluationFunction ef = new FissionEvaluateFunction(g);

		board[0][0] = FieldColor.WHITE;
		board[0][1] = FieldColor.WHITE; //
		// board[0][2] = FieldColor.WHITE; // board[0][3]= FieldColor.WHITE; //
		// board[0][4] = FieldColor.WHITE; // board[0][5]= FieldColor.WHITE; //
		// board[0][6] = FieldColor.WHITE; // board[0][7]= FieldColor.WHITE;

		board[7][0] = FieldColor.BLACK;

		board[7][1] = FieldColor.BLACK;

		s.setBoard(board);
		s.setWhitePlayerTurn(true);
		g.setState(s);

		ComputerPlayerIf c = new AlfaBetaPlayerWithTTPlayer(g, ef, 2, true);
		ComputerPlayerIf c2 = new GreedyPlayer(g, ef, false);
		//while (g.whoIsWinner() == 0) {
			g.printState();
			AbstractMove m = c.getNextMove();
			System.out.println(m);
			g.doMove(m);
			//if (g.whoIsWinner() != 0) {
				//break;
			//}
			g.printState();
			//m = c2.getNextMove();
			//System.out.println(m);
			//g.doMove(m);
		//}
		//g.printState();

		// board[1][1]= FieldColor.BLACK; // board[1][0]= FieldColor.BLACK;

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		 GameMaker gameMaker = new GameMaker();
		//test();
	}

}
