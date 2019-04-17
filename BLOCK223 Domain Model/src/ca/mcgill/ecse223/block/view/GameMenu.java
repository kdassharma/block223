package ca.mcgill.ecse223.block.view;

import ca.mcgill.ecse223.block.controller.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.HashMap;
import java.util.List;

public class GameMenu extends JPanel{
	
	private static String error = "";
	private static HashMap<Integer, String> games;
	
	private static JFrame frameGame;
	
	static JComboBox<String> gameNameToggleList = new JComboBox();
	
	public GameMenu(JFrame frame){
        run(frame);
    }

    private static void run(JFrame frame) {

    	AdminUi.closeError();
    	
		//initialization
		JLabel errorMessage = new JLabel();;
		errorMessage.setForeground(Color.RED);
		errorMessage.setVisible(false);
		errorMessage.setBounds(0, 230, 3000, 20);
		errorMessage.setBackground(Color.WHITE);
		errorMessage.setText("");
		errorMessage.setOpaque(true);errorMessage.setFont(new Font("Courier", Font.PLAIN,8));
	
		JLabel titleLabel = new JLabel();
		titleLabel = new JLabel();
		titleLabel.setText("What to CHOOSE?");
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		titleLabel.setVisible(true);
		titleLabel.setBounds(0,30, 250, 40);
		titleLabel.setBackground(Color.GREEN);
		titleLabel.setOpaque(true);
		titleLabel.setFont(new Font("Courier", Font.BOLD, 20));
		
		JLabel gameNameToggleLabel = new JLabel();
		gameNameToggleLabel = new JLabel();
		gameNameToggleLabel.setBackground(Color.LIGHT_GRAY);
		gameNameToggleLabel.setOpaque(true);
		gameNameToggleLabel.setText("Select Game: ");
		gameNameToggleLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		gameNameToggleLabel.setVisible(true);
		gameNameToggleLabel.setBounds(0, 81, 110, 20);
		
		gameNameToggleList = new JComboBox<>();
		gameNameToggleList = new JComboBox<String>(new String[0]);
		gameNameToggleList.setVisible(true);
		gameNameToggleList.setBounds(140, 80, 100, 25);




		refreshData();
		error = "";
		
		JButton updateGameButton = new JButton();
		updateGameButton = new JButton();
		updateGameButton.setText("Update");
		updateGameButton.setVisible(true);
		updateGameButton.setBorder(BorderFactory.createLineBorder(Color.GREEN, 2));
		updateGameButton.setOpaque(true);
		updateGameButton.setBounds(75, 180, 100,40);
		
		JButton loadGameButton = new JButton();
		loadGameButton = new JButton();
		loadGameButton.setText("Load");
		loadGameButton.setVisible(true);
		loadGameButton.setBorder(BorderFactory.createLineBorder(Color.GREEN, 2));
		loadGameButton.setOpaque(true);
		loadGameButton.setBounds(10, 130, 100,40);
	
		JButton deleteGameButton = new JButton();
		deleteGameButton = new JButton();
		deleteGameButton.setText("Delete");
		deleteGameButton.setVisible(true);
		deleteGameButton.setBorder(BorderFactory.createLineBorder(Color.GREEN, 2));
		deleteGameButton.setOpaque(true);
		deleteGameButton.setBounds(140, 130, 100, 40);
		
		
		JButton returnButton2 = new JButton("<--");
		returnButton2.setFont(new Font("Courier", Font.PLAIN,10));
		returnButton2.setSize(50, 30);
		
		JPanel panel = new JPanel();
        panel.setBackground(Color.DARK_GRAY);
        panel.setSize(250, 290);
        panel.setLayout(null);
        panel.setLocation(0, 0);
        panel.setOpaque(true);
        panel.add(titleLabel);
        panel.add(gameNameToggleLabel);
        panel.add(gameNameToggleList);
        panel.add(updateGameButton);
        panel.add(loadGameButton);
        panel.add(deleteGameButton);
        panel.add(errorMessage);
        panel.add(returnButton2);
        panel.setVisible(true);
        
		frameGame = new JFrame("Game Menu");
		frameGame.add(panel);
		frameGame.setTitle("Block 223 Game");
		frameGame.setSize(250, 290);
		frameGame.setLocation(AdminUi.getCenterWidth() - 320,AdminUi.getCenterHeight() - 30);
		frameGame.setLayout(null);
		frameGame.setBackground(Color.WHITE);
		frameGame.setOpacity(1);
		frameGame.setVisible(true);
		frameGame.setResizable(false);
		
		// Action Listeners
		
        returnButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	closeFrame();
            }
        });
        
		updateGameButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
			
				int selectedGame = gameNameToggleList.getSelectedIndex();
				if (selectedGame < 0)
				{
					error = "Game needs to be selected for updating!";
					errorMessage.setVisible(true);
					errorMessage.setText(error);	
				}
				else
				{
					JFrame jFrame1 = new JFrame();
					jFrame1.setSize(250,  200);
					jFrame1.setLocation(1042,465);
					closeFrame();
					ViewManager.runView("UpdateGame", jFrame1);
				}
			}
		});
		
		loadGameButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				loadGameButtonActionPerformed(evt);
			}
		});
		
		deleteGameButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) {
				error = "";
				int selectedGame = gameNameToggleList.getSelectedIndex();
				if (selectedGame < 0)
				{
					error = "Game needs to be selected for deletion!";
					errorMessage.setVisible(true);
					errorMessage.setText(error);	
				}

					else if (error.length() == 0) {
						// call the controller
						try {
								Block223Controller.deleteGame(games.get(selectedGame));
								RefreshData.refreshData("delete game");
							} catch (InvalidInputException e1) {
								error = e1.getMessage();
								errorMessage.setVisible(true);
								errorMessage.setText(error);	
							}
					}
				}
			});
    }
    
	private static void loadGameButtonActionPerformed(java.awt.event.ActionEvent evt) {
		error = "";
		int selectedGame = gameNameToggleList.getSelectedIndex();
		if (selectedGame < 0)
			error = "A game needs to be selected to load.";
		if (error.length() == 0) {
			try {
				String name = games.get(selectedGame);
				Block223Controller.selectGame(name);
				DetailsPanel.setCurrentLevel(1);
				DetailsPanel.setCurrentGameName(name);
				TOGame game = Block223Controller.getCurrentDesignableGame();
				List<TOGridCell> blocks = Block223Controller.getBlocksAtLevelOfCurrentDesignableGame(1);
				int numLeft = game.getNrBlocksPerLevel() - blocks.size();
				DetailsPanel.setNumBlocks(blocks.size());
				DetailsPanel.setNumBlocksLeft(numLeft);
				BlockGeneration.regenerateBlocks(blocks);
				RefreshData.refreshData("game");
				RefreshData.refreshData("panel");
			}
			catch (InvalidInputException e) {
				error = e.getMessage();
			}
		}
	}
	
    public static void refreshData() {
    	games = new HashMap<Integer, String>();
		gameNameToggleList.removeAllItems();
		Integer index = 0;
		try {
			List<TOGame> gamesList = Block223Controller.getDesignableGames();
			for (TOGame game : gamesList ) {
				games.put(index, game.getName());
				gameNameToggleList.addItem(game.getName());
				index++;
			}
		} catch (InvalidInputException e) {
			// TODO Auto-generated catch block
			error = e.getMessage();
		};
		gameNameToggleList.setSelectedIndex(-1);
	}
    public static void closeFrame()
	{
		frameGame.dispatchEvent(new WindowEvent(frameGame, WindowEvent.WINDOW_CLOSING));
	}
}
