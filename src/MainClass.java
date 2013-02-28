import java.util.ArrayList;

import ai.interfaces.AbstractMove;

import fission.core.FieldColor;
import fission.core.FissionEvaluateFunction;
import fission.core.FissionGame;
import fission.core.FissionMove;
import fission.core.FissionState;
import fission.gui.GameMaker;

public class MainClass {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		GameMaker gameMaker = new GameMaker();
		/*FissionState s = new FissionState();
		FieldColor board[][] = new FieldColor[8][8];
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				board[i][j] = FieldColor.EMPTY;
			}
		}
		board[0][0]= FieldColor.WHITE;
		//board[1][1]= FieldColor.WHITE;
		//board[1][2]= FieldColor.WHITE;
		/*board[1][3]= FieldColor.WHITE;
		board[1][4]= FieldColor.WHITE;
		board[1][5]= FieldColor.WHITE;
		board[1][6]= FieldColor.WHITE;
		board[1][7]= FieldColor.WHITE;
		
		//board[2][1]= FieldColor.BLACK;

		//board[0][1]= FieldColor.BLACK;
		//board[1][1]= FieldColor.BLACK;
		//board[1][0]= FieldColor.BLACK;
		ArrayList<AbstractMove> m;
		s.setBoard(board);
		FissionGame g = new FissionGame(s);
		g.setWhitePlayerTurn(true);
		m = g.getAllMoves();
		System.out.println(g.whoIsWinner());
		for(AbstractMove a :m){
			System.out.println(a);
		}
		FissionEvaluateFunction e = new FissionEvaluateFunction(g);
		
		System.out.println(e.evaluateState(s));
		*/
	}

}
