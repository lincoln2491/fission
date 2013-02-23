package fission.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Time;

import javax.swing.JPanel;
import javax.swing.event.MouseInputListener;

import sun.invoke.empty.Empty;

import fission.core.FieldColor;
import fission.core.FissionGame;
import fission.core.FissionMove;
import fission.core.FissionState;

public class BoardPanel extends JPanel implements MouseListener {
	FissionGame game;
	static int BORDERS_SIZE = 50;
	static int ROW_AND_COLUMN_SIZE = 50;
	static int PAWN_RADIUS = 25;
	Point clickedPoint = null;
	Thread twoComputerPlayersThread;
BoardPanel thisPanel;
	public BoardPanel(FissionGame aGame) {
		super();
		game = aGame;

		if (game.getWhitePlayer() != null && game.getBlackPlayer() != null) {
			twoComputerPlayersThread = new Thread(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
					}
					while(game.whoIsWinner()==0){
						long start, stop, diff;
						start = System.currentTimeMillis();
						game.nextComputerPlayerMove();
						stop = System.currentTimeMillis();
						diff = start-stop;
						if(diff <500){
							try {
								Thread.sleep(500-diff);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						thisPanel.repaint();
					}
				}
			});
			twoComputerPlayersThread.start();
		} else if (game.getWhitePlayer() != null && game.isWhitePlayerTurn()) {
			game.nextComputerPlayerMove();
			this.addMouseListener(this);
		} else if (game.getBlackPlayer() != null && !game.isWhitePlayerTurn()) {
			game.nextComputerPlayerMove();
			this.addMouseListener(this);
		}
		thisPanel = this;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		char[] gameStatusCharArray;
		int winner;

		g2d.setColor(Color.BLACK);

		winner = game.whoIsWinner();
		if (winner == 0) {
			if (game.isWhitePlayerTurn()) {
				gameStatusCharArray = "Ruch białego gracza".toCharArray();
			} else {
				gameStatusCharArray = "Ruch czarnego gracza".toCharArray();
			}
		} else if (winner == -1) {
			gameStatusCharArray = "Remis".toCharArray();
		} else if (winner == 1) {
			gameStatusCharArray = "Wygrał biały gracz".toCharArray();
		} else {
			gameStatusCharArray = "Wygrał czarny gracz".toCharArray();
		}

		g2d.drawChars(gameStatusCharArray, 0, gameStatusCharArray.length, 5, 15);

		this.setBackground(Color.ORANGE);

		g2d.drawRect(BORDERS_SIZE, BORDERS_SIZE, 7 * ROW_AND_COLUMN_SIZE,
				7 * ROW_AND_COLUMN_SIZE);

		for (int i = 0; i < 7; i++) {
			g2d.drawLine(BORDERS_SIZE, BORDERS_SIZE + i * ROW_AND_COLUMN_SIZE,
					BORDERS_SIZE + 7 * ROW_AND_COLUMN_SIZE, BORDERS_SIZE + i
							* ROW_AND_COLUMN_SIZE);

			g2d.drawLine(BORDERS_SIZE + i * ROW_AND_COLUMN_SIZE, BORDERS_SIZE,
					BORDERS_SIZE + i * ROW_AND_COLUMN_SIZE, BORDERS_SIZE + 7
							* ROW_AND_COLUMN_SIZE);
		}

		FieldColor board[][] = ((FissionState) game.getActualState())
				.getBoard();
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				FieldColor tmp = board[i][j];
				if (tmp == FieldColor.WHITE) {
					g2d.setColor(Color.WHITE);
					g2d.fillOval(BORDERS_SIZE + i * ROW_AND_COLUMN_SIZE
							- PAWN_RADIUS, BORDERS_SIZE + j
							* ROW_AND_COLUMN_SIZE - PAWN_RADIUS,
							2 * PAWN_RADIUS, 2 * PAWN_RADIUS);
				}
				if (tmp == FieldColor.BLACK) {
					g2d.setColor(Color.BLACK);
					g2d.fillOval(BORDERS_SIZE + i * ROW_AND_COLUMN_SIZE
							- PAWN_RADIUS, BORDERS_SIZE + j
							* ROW_AND_COLUMN_SIZE - PAWN_RADIUS,
							2 * PAWN_RADIUS, 2 * PAWN_RADIUS);
				}
			}
		}
		if (clickedPoint != null) {
			g2d.setColor(new Color(100, 100, 100, 100));

			paintPossibleMove(clickedPoint.x, clickedPoint.y, g2d);

			// sprawdzanie w prawo
			for (int i = clickedPoint.x + 1; i <= 7; i++) {
				if (game.fieldColorAt(i, clickedPoint.y) == FieldColor.EMPTY) {
					paintPossibleMove(i, clickedPoint.y, g2d);
				} else {
					break;
				}
			}
			// sprawdzanie w lewo
			for (int i = clickedPoint.x - 1; i >= 0; i--) {
				if (game.fieldColorAt(i, clickedPoint.y) == FieldColor.EMPTY) {
					paintPossibleMove(i, clickedPoint.y, g2d);
				} else {
					break;
				}
			}
			// sprawdzanie w dół
			for (int i = clickedPoint.y + 1; i <= 7; i++) {
				if (game.fieldColorAt(clickedPoint.x, i) == FieldColor.EMPTY) {
					paintPossibleMove(clickedPoint.x, i, g2d);
				} else {
					break;
				}
			}
			// sprawdzanie w lewo
			for (int i = clickedPoint.y - 1; i >= 0; i--) {
				if (game.fieldColorAt(clickedPoint.x, i) == FieldColor.EMPTY) {
					paintPossibleMove(clickedPoint.x, i, g2d);
				} else {
					break;
				}
			}
			// sprawdzanie w górę i lewo
			for (int i = clickedPoint.x - 1, j = clickedPoint.y - 1; i >= 0
					&& j >= 0; i--, j--) {
				if (game.fieldColorAt(i, j) == FieldColor.EMPTY) {
					paintPossibleMove(i, j, g2d);
				} else {
					break;
				}
			}
			// sprawdzanie w dół i lewo
			for (int i = clickedPoint.x - 1, j = clickedPoint.y + 1; i >= 0
					&& j <= 7; i--, j++) {
				if (game.fieldColorAt(i, j) == FieldColor.EMPTY) {
					paintPossibleMove(i, j, g2d);
				} else {
					break;
				}
			}
			// sprawdzanie w dół i prawo
			for (int i = clickedPoint.x + 1, j = clickedPoint.y + 1; i <= 7
					&& j <= 7; i++, j++) {
				if (game.fieldColorAt(i, j) == FieldColor.EMPTY) {
					paintPossibleMove(i, j, g2d);
				} else {
					break;
				}
			}
			// sprawdzanie w górę i prawo
			for (int i = clickedPoint.x + 1, j = clickedPoint.y - 1; i <= 7
					&& j >= 0; i++, j--) {
				if (game.fieldColorAt(i, j) == FieldColor.EMPTY) {
					paintPossibleMove(i, j, g2d);
				} else {
					break;
				}
			}

		}

	}

	private void paintPossibleMove(int aX, int aY, Graphics2D aG2d) {
		aG2d.fillRect(BORDERS_SIZE - PAWN_RADIUS + aX * ROW_AND_COLUMN_SIZE,
				BORDERS_SIZE - PAWN_RADIUS + aY * ROW_AND_COLUMN_SIZE,
				ROW_AND_COLUMN_SIZE, ROW_AND_COLUMN_SIZE);

	}

	private Point getPointAt(Point aPoint) {
		if (aPoint.x > BORDERS_SIZE - PAWN_RADIUS
				&& aPoint.y > BORDERS_SIZE - PAWN_RADIUS
				&& aPoint.x < BORDERS_SIZE + ROW_AND_COLUMN_SIZE * 7
						+ PAWN_RADIUS
				&& aPoint.y < BORDERS_SIZE + ROW_AND_COLUMN_SIZE * 7
						+ PAWN_RADIUS) {
			Point point;
			int x = (aPoint.x - BORDERS_SIZE + PAWN_RADIUS)
					/ ROW_AND_COLUMN_SIZE;
			int y = (aPoint.y - BORDERS_SIZE + PAWN_RADIUS)
					/ ROW_AND_COLUMN_SIZE;
			point = new Point(x, y);
			return point;
		}
		return null;
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		if ((game.getWhitePlayer() != null && game.getActualState()
				.isWhitePlayerTurn())
				|| (game.getBlackPlayer() != null && !game.getActualState()
						.isWhitePlayerTurn())) {
			clickedPoint = null;
			return;
		}

		Point newClickedPoint = getPointAt(arg0.getPoint());
		if (newClickedPoint == null) {
			clickedPoint = null;
		} else {
			FieldColor board[][] = ((FissionState) game.getActualState())
					.getBoard();
			if (board[newClickedPoint.x][newClickedPoint.y] == FieldColor.WHITE
					&& game.isWhitePlayerTurn() && clickedPoint == null) {
				clickedPoint = newClickedPoint;
			} else if (board[newClickedPoint.x][newClickedPoint.y] == FieldColor.BLACK
					&& !game.isWhitePlayerTurn() && clickedPoint == null) {
				clickedPoint = newClickedPoint;
			} else if (board[newClickedPoint.x][newClickedPoint.y] == FieldColor.EMPTY
					&& clickedPoint != null) {
				int xDif = newClickedPoint.x - clickedPoint.x;
				int yDif = newClickedPoint.y - clickedPoint.y;
				if (xDif == 0 && yDif == 0) {
					// klikniecie w te samo pole
					clickedPoint = null;
				} else if (xDif > 0 && xDif == yDif) {
					// prawo dół
					xDif = 1;
					yDif = 1;
				} else if (xDif < 0 && xDif == yDif) {
					// lewo góra
					xDif = -1;
					yDif = -1;
				} else if (xDif > 0 && xDif == -yDif) {
					// prawo góra
					xDif = 1;
					yDif = -1;
				} else if (xDif < 0 && xDif == -yDif) {
					// lewo dół
					xDif = -1;
					yDif = 1;
				} else if (xDif > 0 && yDif == 0) {
					// prawo
					xDif = 1;
					yDif = 0;
				} else if (xDif < 0 && yDif == 0) {
					// lewo
					xDif = -1;
					yDif = 0;
				} else if (yDif > 0 && xDif == 0) {
					// dół
					xDif = 0;
					yDif = 1;
				} else if (yDif < 0 && xDif == 0) {
					// góra
					xDif = 0;
					yDif = -1;
				}
				if (board[clickedPoint.x + xDif][clickedPoint.y + yDif] == FieldColor.EMPTY) {
					game.doMove(new FissionMove(clickedPoint.x, clickedPoint.y,
							xDif, yDif));
					clickedPoint = null;
				}

			} else if (board[newClickedPoint.x][newClickedPoint.y] != FieldColor.EMPTY) {
				clickedPoint = null;
			}

		}

		this.repaint();

		if (game.whoIsWinner() != 0) {
			this.removeMouseListener(this);
			this.repaint();
			return;
		}

		if (game.isWhitePlayerTurn() && game.getWhitePlayer() != null) {
			game.nextComputerPlayerMove();
		}
		if (!game.isWhitePlayerTurn() && game.getBlackPlayer() != null) {
			game.nextComputerPlayerMove();
		}

		this.repaint();
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
	}

	@Override
	public void mouseExited(MouseEvent arg0) {

	}

	@Override
	public void mousePressed(MouseEvent arg0) {

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {

	}
}
