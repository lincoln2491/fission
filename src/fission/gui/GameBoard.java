package fission.gui;

import java.awt.Color;

import javax.swing.JFrame;

import fission.core.FissionGame;

public class GameBoard extends JFrame {
	FissionGame game;
	BoardPanel panel;

	public GameBoard(FissionGame aGame) {
		game = aGame;
		createContent();
		this.setSize(460, 475);
		this.setResizable(false);
		this.setVisible(true);
	}

	private void createContent() {
		panel = new BoardPanel(game);
		this.add(panel);
	}

}
