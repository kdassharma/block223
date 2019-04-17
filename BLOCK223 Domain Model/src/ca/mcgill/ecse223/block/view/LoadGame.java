package ca.mcgill.ecse223.block.view;

import java.awt.Color;
import java.util.HashMap;

import javax.swing.*;
import java.awt.*;
import ca.mcgill.ecse223.block.controller.*;

public class LoadGame {
	
	static GraphicsConfiguration gc;

	// UI Elements
	private static JLabel errorMessage;
	private JLabel titleLabel = new JLabel();
	// pick game
	private JLabel gameNameToggleLabel;
	private static JComboBox<String> gameNameToggleList = new JComboBox();
	// update game
	private JButton updateGameButton;
	// load game
	private JButton loadGameButton;
	// delete game
	private JButton deleteGameButton;
	
	
	// Data Elements
	private static String error = null;
	// toggle existing games
	private static HashMap<Integer, String> games;
	
	public LoadGame() {
		run();
	}
	
	// Initiate Components
	private void initComponents() {
		// elements for error message
		errorMessage = new JLabel();
		errorMessage.setForeground(Color.RED);

		// elements for title
		titleLabel = new JLabel();
		titleLabel.setText("Load Game");
		// toggle existing games
		gameNameToggleLabel = new JLabel();
		gameNameToggleLabel.setText("Select Game:");
		gameNameToggleList = new JComboBox<String>(new String[0]);
		// update game
		updateGameButton = new JButton();
		updateGameButton.setText("Update");
		// delete game
		deleteGameButton = new JButton();
		deleteGameButton.setText("Delete");
		
		loadGameButton = new JButton();
		loadGameButton.setText("Load");
		
		// Action Listeners
		updateGameButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				updateGameButtonActionPerformed(evt);
			}
		});
		loadGameButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				loadGameButtonActionPerformed(evt);
			}
		});
		deleteGameButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				deleteGameButtonActionPerformed(evt);
			}
		});
	
		// Layout
		titleLabel.setBounds(200, 20, 200, 60);
		titleLabel.setFont(new Font("Courier", Font.BOLD, 40));
		gameNameToggleLabel.setBounds(100, 100, 130, 25);
		gameNameToggleList.setBounds(300,  150, 150, 25);
		updateGameButton.setBounds(200, 300, 130, 25);
		deleteGameButton.setBounds(400, 300, 130, 25);
		errorMessage.setBounds(150, 300, 3000, 50);
	}
	
	private void run() {
		
		JFrame frame = new JFrame("Block223 Load Game!");
		frame.setTitle("Block223 Game");
		
		initComponents();
		
		frame.add(titleLabel);
		frame.add(gameNameToggleLabel);
		frame.add(gameNameToggleList);
		frame.add(updateGameButton);
		frame.add(deleteGameButton);
		frame.add(errorMessage);
		
		frame.setSize(600, 400);
		frame.setLocation(400, 400);
		frame.setLayout(null);
		frame.setVisible(true);
		frame.setResizable(false);
		
	}
	
	// Refresh Data
	public static void refreshData() {
		//errorMessage.setText(error);
		//if (error == null || error.length() == 0) {
			games = new HashMap<Integer, String>();
			gameNameToggleList.removeAllItems();
			Integer index = 0;
			try {
				for (TOGame game : Block223Controller.getDesignableGames()) {
					games.put(index, game.getName());
					gameNameToggleList.addItem(game.getName());
					index++;
				}
				gameNameToggleList.setSelectedIndex(-1);
			}catch(Exception e){
				errorMessage.setText(e.getMessage());
			}
		//}
	}
	
	// Action Performed Methods
	private void updateGameButtonActionPerformed(java.awt.event.ActionEvent evt) {
		// when updateGameButton is clicked --> 'UpdateGame' UI pops up
		JFrame frameMenu = new JFrame();
		frameMenu.setVisible(true);
		//ViewManager.runView("UpdateGame", frameMenu);
	}
	
	private void loadGameButtonActionPerformed(java.awt.event.ActionEvent evt) {
		error = "";
		int selectedGame = gameNameToggleList.getSelectedIndex();
		if (selectedGame < 0)
		{
			error = "A game needs to be selected to load.";
			errorMessage.setText(error);
		}
		if (error.length() == 0) {
			try {
				String name = games.get(selectedGame);
				Block223Controller.selectGame(name);
				DetailsPanel.setCurrentLevel(1);
				RefreshData.refreshData("game");
			}
			catch (InvalidInputException e) {
				error = e.getMessage();
			}
		}
		RefreshData.refreshData("game");RefreshData.refreshData("game");
	}
	
	private void deleteGameButtonActionPerformed(java.awt.event.ActionEvent evt) {
		error = "";
		int selectedGame = gameNameToggleList.getSelectedIndex();
		if (selectedGame < 0)
			error = "A game needs to be selected to delete.";
		if (error.length() == 0) {
			try {
				Block223Controller.deleteGame(games.get(selectedGame));
				RefreshData.refreshData("game");
			}
			catch (InvalidInputException e) {
				error = e.getMessage();
			}
		}
	}
}
