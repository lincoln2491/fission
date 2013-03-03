package fission.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.Format;
import java.text.NumberFormat;
import java.util.Random;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import ai.interfaces.ComputerPlayerIf;

import fission.core.AlfaBetaPlayer;
import fission.core.AlfaBetaPlayerWithTTPlayer;
import fission.core.FieldColor;
import fission.core.FissionEvaluateFunction;
import fission.core.FissionGame;
import fission.core.FissionState;
import fission.core.GreedyPlayer;
import fission.core.RandomPlayer;

public class GameMaker extends JFrame {

	JComboBox<String> whitePlayerComboBox;
	JComboBox<String> blackPlayerComboBox;
	JFormattedTextField whitePlayerDeepth;
	JFormattedTextField blackPlayerDeepth;

	JComboBox<String> startingPlayerComboBox;

	ButtonGroup startingStateButtonGroup;
	JRadioButton standardStateRadioButton;
	JRadioButton randomStateRadioButton;
	JRadioButton loadFromFileRadioButton;

	FieldColor loadedBoard[][];
	private JButton loadButton;

	public GameMaker() {
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		createContent();
		this.setVisible(true);
		this.setResizable(false);
		this.pack();
	}

	private void createContent() {
		setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

		createChosePlayerPanels();
		createStartingPlayerPanel();
		createStartingStatusPanel();
		createStartGameButton();

	}

	private void createStartGameButton() {
		JButton startGameButton = new JButton("Zacznij gre");
		startGameButton.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent arg0) {
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
			}

			@Override
			public void mouseClicked(MouseEvent arg0) {
				startGame();
			}
		});
		this.add(startGameButton);
	}

	private void createStartingStatusPanel() {
		Color backgroundColor = Color.YELLOW;
		JPanel tmpPanel;

		JPanel startingStatusPanel = new JPanel();
		startingStatusPanel.setBackground(backgroundColor);
		startingStatusPanel.setLayout(new BorderLayout());
		this.add(startingStatusPanel);

		tmpPanel = new JPanel();
		tmpPanel.setBackground(backgroundColor);
		startingStatusPanel.add(tmpPanel, BorderLayout.EAST);

		loadButton = new JButton("Wczytaj dane");
		tmpPanel.add(loadButton);
		loadButton.setEnabled(false);
		loadButton.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mousePressed(MouseEvent e) {

			}

			@Override
			public void mouseExited(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					loadFromFile(e);
				} catch (IOException e1) {
					System.out.println("zleeee");
					loadedBoard = null;
				}
			}
		});

		startingStateButtonGroup = new ButtonGroup();

		tmpPanel = new JPanel();
		tmpPanel.setBackground(backgroundColor);
		tmpPanel.setLayout(new BoxLayout(tmpPanel, BoxLayout.Y_AXIS));
		startingStatusPanel.add(tmpPanel, BorderLayout.WEST);

		standardStateRadioButton = new JRadioButton("Standardowa gra");
		standardStateRadioButton.setBackground(backgroundColor);
		standardStateRadioButton.setSelected(true);
		startingStateButtonGroup.add(standardStateRadioButton);
		tmpPanel.add(standardStateRadioButton);

		randomStateRadioButton = new JRadioButton("Losowy stan początkowy");
		randomStateRadioButton.setBackground(backgroundColor);
		startingStateButtonGroup.add(randomStateRadioButton);
		tmpPanel.add(randomStateRadioButton);

		loadFromFileRadioButton = new JRadioButton("Wczytaj z pliku");
		loadFromFileRadioButton.setBackground(backgroundColor);
		startingStateButtonGroup.add(loadFromFileRadioButton);

		loadFromFileRadioButton.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent arg0) {
				loadButton.setEnabled(loadFromFileRadioButton.isSelected());
			}
		});

		tmpPanel.add(loadFromFileRadioButton);

	}

	private void createStartingPlayerPanel() {
		Color backgroundColor = Color.LIGHT_GRAY;
		JPanel choseStartingPlayerPanel;

		choseStartingPlayerPanel = new JPanel();
		choseStartingPlayerPanel.setBackground(backgroundColor);
		choseStartingPlayerPanel.setLayout(new BorderLayout());
		this.add(choseStartingPlayerPanel);

		choseStartingPlayerPanel.add(new JLabel(
				"Wybierz, który z graczy ma zaczynać"), BorderLayout.WEST);

		startingPlayerComboBox = new JComboBox<String>();
		DefaultComboBoxModel<String> startingPlayerModel = new DefaultComboBoxModel<String>(
				new String[] { "Biały gracz", "Czarny gracz" });
		startingPlayerComboBox.setModel(startingPlayerModel);
		choseStartingPlayerPanel.add(new JPanel(), BorderLayout.EAST);
		((JPanel) choseStartingPlayerPanel.getComponent(1))
				.setBackground(backgroundColor);
		((JPanel) choseStartingPlayerPanel.getComponent(1))
				.add(startingPlayerComboBox);

	}

	private void createChosePlayerPanels() {
		Color backgroundColor = Color.ORANGE;
		JPanel chosePlayersPanel;

		chosePlayersPanel = new JPanel();
		chosePlayersPanel.setBackground(backgroundColor);
		chosePlayersPanel.setLayout(new BorderLayout());
		this.add(chosePlayersPanel);

		chosePlayersPanel.add(new JLabel("Wybierz białego gracza:"),
				BorderLayout.WEST);

		whitePlayerComboBox = new JComboBox<String>();
		DefaultComboBoxModel<String> playerModel = new DefaultComboBoxModel<String>(
				new String[] { "Człowiek", "Gracz losowy", "Gracz zachłanny",
						"Gracz alfa-beta", "Gracz alfa-beta z TT" });
		whitePlayerComboBox.setModel(playerModel);
		chosePlayersPanel.add(new JPanel(), BorderLayout.EAST);
		((JPanel) chosePlayersPanel.getComponent(1))
				.setBackground(backgroundColor);
		((JPanel) chosePlayersPanel.getComponent(1)).add(whitePlayerComboBox);

		chosePlayersPanel = new JPanel();
		chosePlayersPanel.setBackground(backgroundColor);
		chosePlayersPanel.setLayout(new BorderLayout());
		this.add(chosePlayersPanel);

		chosePlayersPanel.add(new JLabel("Wybierz głębokość poszukiwania:"),
				BorderLayout.WEST);

		whitePlayerDeepth = new JFormattedTextField(NumberFormat.getInstance());
		whitePlayerDeepth.setText("5");
		whitePlayerDeepth.setPreferredSize(new Dimension(140, whitePlayerDeepth
				.getMinimumSize().height));
		whitePlayerDeepth.setMaximumSize(whitePlayerDeepth.getPreferredSize());
		chosePlayersPanel.add(new JPanel(), BorderLayout.EAST);
		((JPanel) chosePlayersPanel.getComponent(1))
				.setBackground(backgroundColor);
		((JPanel) chosePlayersPanel.getComponent(1)).add(whitePlayerDeepth);

		chosePlayersPanel = new JPanel();
		chosePlayersPanel.setBackground(backgroundColor);
		chosePlayersPanel.setLayout(new BorderLayout());
		this.add(chosePlayersPanel);

		chosePlayersPanel.add(new JLabel("Wybierz czarnego gracza:"),
				BorderLayout.WEST);

		blackPlayerComboBox = new JComboBox<String>();
		playerModel = new DefaultComboBoxModel<String>(new String[] {
				"Człowiek", "Gracz losowy", "Gracz zachłanny",
				"Gracz alfa-beta", "Gracz alfa-beta z TT" });
		blackPlayerComboBox.setModel(playerModel);
		chosePlayersPanel.add(new JPanel(), BorderLayout.EAST);
		((JPanel) chosePlayersPanel.getComponent(1))
				.setBackground(backgroundColor);
		((JPanel) chosePlayersPanel.getComponent(1)).add(blackPlayerComboBox,
				BorderLayout.CENTER);

		chosePlayersPanel = new JPanel();
		chosePlayersPanel.setBackground(backgroundColor);
		chosePlayersPanel.setLayout(new BorderLayout());
		this.add(chosePlayersPanel);

		chosePlayersPanel.add(new JLabel("Wybierz głębokość poszukiwania:"),
				BorderLayout.WEST);

		blackPlayerDeepth = new JFormattedTextField(NumberFormat.getInstance());
		blackPlayerDeepth.setText("5");
		blackPlayerDeepth.setPreferredSize(new Dimension(140, whitePlayerDeepth
				.getMinimumSize().height));
		blackPlayerDeepth.setMaximumSize(whitePlayerDeepth.getPreferredSize());
		chosePlayersPanel.add(new JPanel(), BorderLayout.EAST);
		((JPanel) chosePlayersPanel.getComponent(1))
				.setBackground(backgroundColor);
		((JPanel) chosePlayersPanel.getComponent(1)).add(blackPlayerDeepth);
	}

	private void createRandomState() {
		Random random = new Random();

		loadedBoard = new FieldColor[8][8];
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				int r = random.nextInt(3);
				if (r == 0) {
					loadedBoard[i][j] = FieldColor.WHITE;
				} else if (r == 1) {
					loadedBoard[i][j] = FieldColor.BLACK;
				} else {
					loadedBoard[i][j] = FieldColor.EMPTY;
				}
			}
		}
	}

	private void startGame() {
		FissionState startState;
		boolean isWhitePlayerTurn;
		ComputerPlayerIf whitePlayer = null;
		ComputerPlayerIf blackPlayer = null;
		String option;
		FissionGame game;
		GameBoard board;

		option = (String) startingPlayerComboBox.getSelectedItem();
		if (option == "Biały gracz") {
			isWhitePlayerTurn = true;
		} else {
			isWhitePlayerTurn = false;
		}

		if (standardStateRadioButton.isSelected()) {
			game = new FissionGame(isWhitePlayerTurn);
		} else {
			if (randomStateRadioButton.isSelected()) {
				createRandomState();
			}
			startState = new FissionState();
			startState.setBoard(loadedBoard);
			startState.setWhitePlayerTurn(isWhitePlayerTurn);
			game = new FissionGame(startState);
		}

		option = (String) whitePlayerComboBox.getSelectedItem();
		if (option == "Człowiek") {
			whitePlayer = null;
		} else if (option == "Gracz losowy") {
			whitePlayer = new RandomPlayer(game);
		} else if (option == "Gracz zachłanny") {
			whitePlayer = new GreedyPlayer(game, new FissionEvaluateFunction(
					game), true);
		} else if (option == "Gracz alfa-beta") {
			whitePlayer = new AlfaBetaPlayer(game, new FissionEvaluateFunction(
					game), Integer.parseInt(whitePlayerDeepth.getText()), true);
		} else if (option == "Gracz alfa-beta z TT") {
			whitePlayer = new AlfaBetaPlayerWithTTPlayer(game,
					new FissionEvaluateFunction(game),
					Integer.parseInt(whitePlayerDeepth.getText()), true);
		}

		option = (String) blackPlayerComboBox.getSelectedItem();
		if (option == "Człowiek") {
			blackPlayer = null;
		} else if (option == "Gracz losowy") {
			blackPlayer = new RandomPlayer(game);
		} else if (option == "Gracz zachłanny") {
			blackPlayer = new GreedyPlayer(game, new FissionEvaluateFunction(
					game), false);
		} else if (option == "Gracz alfa-beta") {
			blackPlayer = new AlfaBetaPlayer(game, new FissionEvaluateFunction(
					game), Integer.parseInt(blackPlayerDeepth.getText()), false);
		} else if (option == "Gracz alfa-beta z TT") {
			blackPlayer = new AlfaBetaPlayerWithTTPlayer(game,
					new FissionEvaluateFunction(game),
					Integer.parseInt(blackPlayerDeepth.getText()), false);

		}

		game.setWhitePlayer(whitePlayer);
		game.setBlackPlayer(blackPlayer);

		board = new GameBoard(game);
	}

	private void loadFromFile(MouseEvent e) throws IOException {
		File file;
		FileReader fileReader;
		JFileChooser fileChooser = new JFileChooser();

		fileChooser.showOpenDialog(this);
		file = fileChooser.getSelectedFile();
		fileReader = new FileReader(file);

		loadedBoard = new FieldColor[8][8];

		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				char c = (char) fileReader.read();
				if (c == 'e') {
					loadedBoard[i][j] = FieldColor.EMPTY;
				} else if (c == 'b') {
					loadedBoard[i][j] = FieldColor.BLACK;
				} else if (c == 'w') {
					loadedBoard[i][j] = FieldColor.WHITE;
				} else {
					fileReader.close();
					throw new IOException();
				}
			}
		}
		fileReader.close();
	}
}
