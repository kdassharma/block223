package ca.mcgill.ecse223.block.view;

import javax.swing.*;

import ca.mcgill.ecse223.block.controller.Block223Controller;
import ca.mcgill.ecse223.block.controller.InvalidInputException;
import ca.mcgill.ecse223.block.controller.TOPlayableGame;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.HashMap;
import java.util.List;

public class PlayerIntroUi{

	static JFrame frame;
	static JLabel errorMessage;
	static String error = "";
	
	static JComboBox<String> newGameList;
	static JComboBox<String> continueGameList;
	private static HashMap<Integer, Integer> resumeGames;
	private static HashMap<Integer, String> availableGames;
	
	public PlayerIntroUi()
	{
		run();
	}
    
    static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	static int centerWidth = (int) (screenSize.getWidth() / 2);
	static int centerHeight = (int) (screenSize.getHeight() / 2);

    public static void run() {  
    	frame = new JFrame();
    	frame.setResizable(true);		
		frame.setSize(410, 600);
		frame.setLocation(centerWidth - 205, centerHeight - 300);
		frame.setVisible(true);	
		frame.setBackground(Color.DARK_GRAY);		
		
		JPanel panelBackground = new JPanel();
		panelBackground.setLayout(null);
		//panelBackground.setBorder(BorderFactory.createTitledBorder(""));
		panelBackground.setBackground(Color.DARK_GRAY);
		panelBackground.setVisible(true);
		panelBackground.setBounds(0, 0, 410, 600);
		
        // elements for error message
 		errorMessage = new JLabel();
 		errorMessage.setForeground(Color.RED);
 		errorMessage.setText("");
 		errorMessage.setBounds(0, 530, 3000, 15);
 		errorMessage.setBackground(Color.WHITE);
 		errorMessage.setOpaque(true);
 		errorMessage.setVisible(false);
 		errorMessage.setFont(new Font("Courier", Font.PLAIN,12));
 		
 		JLabel titleLabel = new JLabel();
		titleLabel.setText("Block223");
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		titleLabel.setBounds(0, 120, 410, 80);
        titleLabel.setBackground(Color.GREEN);
        titleLabel.setOpaque(true);
        titleLabel.setFont(new Font("Courier", Font.BOLD,60));
        
        JLabel loginWelcome = new JLabel();
        loginWelcome.setText("Welcome");
        loginWelcome.setHorizontalAlignment(SwingConstants.CENTER);
        loginWelcome.setBounds(0, 210, 410, 30);
        loginWelcome.setBackground(Color.GREEN);
        loginWelcome.setOpaque(true);
        loginWelcome.setFont(new Font("Courier", Font.BOLD,30));
 		
		JButton continueGame = new JButton("Continue Game");
		continueGame.setForeground(Color.BLACK);
		continueGame.setFont(new Font("Courier", Font.PLAIN,25));
		continueGame.setOpaque(true);
		continueGame.setBorder(BorderFactory.createLineBorder(Color.GREEN, 1));
		continueGame.setVisible(true);
		continueGame.setBackground(Color.LIGHT_GRAY);
		continueGame.setBounds(20, 300 ,370, 30);
	
//continue game button

		JButton creditButton = new JButton("Credits");
		creditButton.setForeground(Color.BLACK);
		creditButton.setFont(new Font("Courier", Font.PLAIN,25));
		creditButton.setOpaque(true);
		creditButton.setBorder(BorderFactory.createLineBorder(Color.GREEN, 1));
		creditButton.setVisible(true);
		creditButton.setBackground(Color.LIGHT_GRAY);
		creditButton.setBounds(20, 410 ,370,30);		

		JButton logoutButton = new JButton("logout");
		logoutButton.setForeground(Color.BLACK);
		logoutButton.setFont(new Font("Courier", Font.PLAIN,25));
		logoutButton.setOpaque(true);
		logoutButton.setBorder(BorderFactory.createLineBorder(Color.GREEN, 1));
		logoutButton.setVisible(true);
		logoutButton.setBackground(Color.LIGHT_GRAY);
		logoutButton.setBounds(20, 465 ,370,30);
		
		logoutButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					Block223Controller.logout();
				}
				catch(Exception e5){
					error = e5.getMessage();
					errorMessage.setText(error);
					errorMessage.setVisible(true);
				}
				try
				{
					closeFrame();
					ViewManager.runView("usermenu", new JFrame());
				}
				catch(Exception evet)
				{

				}
			}
		});

		JButton backButton = new JButton("Back");
		backButton.setForeground(Color.BLACK);
		backButton.setFont(new Font("Courier", Font.PLAIN,25));
		backButton.setOpaque(true);
		backButton.setBorder(BorderFactory.createLineBorder(Color.GREEN, 1));
		backButton.setVisible(false);
		backButton.setBackground(Color.LIGHT_GRAY);
		backButton.setBounds(20, 465 ,370,30);
		
		//Start new game
		JButton newGame = new JButton("New Game");
		newGame.setForeground(Color.BLACK);
		newGame.setFont(new Font("Courier", Font.PLAIN,25));
		newGame.setBackground(Color.LIGHT_GRAY);
		newGame.setOpaque(true);
		newGame.setBorder(BorderFactory.createLineBorder(Color.GREEN, 1));
		newGame.setVisible(true);
		newGame.setBounds(20, 355 ,370, 30);
		
		//list for the new game
	
		newGameList = new JComboBox<String>(new String[0]);
		newGameList.setVisible(false);
		newGameList.setBounds(40, 250, 330, 25);
		
		continueGameList = new JComboBox<String>(new String[0]);
		continueGameList.setVisible(false);
		continueGameList.setBounds(40, 250, 330, 25);

		
		JButton confirmNewGame = new JButton("Confirm Choice");
		confirmNewGame.setForeground(Color.BLACK);
		confirmNewGame.setFont(new Font("Courier", Font.PLAIN,25));
		confirmNewGame.setBackground(Color.LIGHT_GRAY);
		confirmNewGame.setOpaque(true);
		confirmNewGame.setBorder(BorderFactory.createLineBorder(Color.GREEN, 1));
		confirmNewGame.setVisible(false);
		confirmNewGame.setBounds(20, 410 , 370, 30);

		confirmNewGame.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) {
//CONFIRM NEW GAME BUTTON
				int index = newGameList.getSelectedIndex();
				if (index < 0)
				{
					error = "Game needs to be selected to play!";
					errorMessage.setVisible(true);
					errorMessage.setText(error);
		 		}
				else {
					try {
						Block223Controller.selectPlayableGame(availableGames.get(index), -1);
						ViewManager.runPopupView("PlayerGameUi");
						closeFrame();
					}
					catch(InvalidInputException e2){
						error = e2.getMessage();
						errorMessage.setVisible(true);
					}
				}	
			}
		});
		
		JButton confirmContinueGame = new JButton("Confirm Choice");
		confirmContinueGame.setForeground(Color.BLACK);
		confirmContinueGame.setFont(new Font("Courier", Font.PLAIN,25));
		confirmContinueGame.setBackground(Color.LIGHT_GRAY);
		confirmContinueGame.setOpaque(true);
		confirmContinueGame.setBorder(BorderFactory.createLineBorder(Color.GREEN, 1));
		confirmContinueGame.setVisible(false);
		confirmContinueGame.setBounds(20, 410 , 370, 30);
		
		confirmContinueGame.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) {
//CONTINUE GAME BUTTON
					int index = continueGameList.getSelectedIndex();
					if (index < 0)
					{
						error = "Game needs to be selected to continue!";
						errorMessage.setVisible(true);
						errorMessage.setText(error);
			 		}
					else {
						try {
							Block223Controller.selectPlayableGame("", resumeGames.get(index));
							ViewManager.runPopupView("PlayerGameUi");
							closeFrame();
						}
						catch(InvalidInputException e2){
							error = e2.getMessage();
							errorMessage.setVisible(true);
						}
					}	
			}
		});
		
		continueGame.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) {
				//setting up all the buttons
				titleLabel.setText("Continue");
				newGame.setVisible(false);
				creditButton.setVisible(false);
				continueGame.setVisible(false);
				logoutButton.setVisible(false);
				backButton.setVisible(true);
				confirmContinueGame.setVisible(true);
				//refreshLists();
				refreshToggleLists();
				continueGameList.setVisible(true);
				
				backButton.addActionListener(new ActionListener()				
				{
					@Override
					public void actionPerformed(ActionEvent e) {
						titleLabel.setText("Block223");
						newGame.setVisible(true);;
						creditButton.setVisible(true);
						continueGame.setVisible(true);
						logoutButton.setVisible(true);
						backButton.setVisible(false);
						confirmContinueGame.setVisible(false);
						continueGameList.setVisible(false);
						errorMessage.setVisible(false);
					}
				});			
			}
		});
		
		newGame.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) {
				//setting up all the buttons
				titleLabel.setText("New Game");
				newGame.setVisible(false);
				creditButton.setVisible(false);
				continueGame.setVisible(false);
				logoutButton.setVisible(false);
				backButton.setVisible(true);
				confirmNewGame.setVisible(true);
				//refreshLists();
				refreshToggleLists();
				newGameList.setVisible(true);
			
				backButton.addActionListener(new ActionListener()				
				{
					@Override
					public void actionPerformed(ActionEvent e) {
						titleLabel.setText("Block223");
						newGame.setVisible(true);;
						creditButton.setVisible(true);
						continueGame.setVisible(true);
						logoutButton.setVisible(true);
						backButton.setVisible(false);
						confirmNewGame.setVisible(false);
						newGameList.setVisible(false);
						errorMessage.setVisible(false);
					}
				});			
			}
		});
		
/////////////////////			Everything below here is for credits				////////////////////
		
		
		JLabel creditNames1 = new JLabel();
		creditNames1.setText("Taylor Lynn Curtis");
		creditNames1.setHorizontalAlignment(SwingConstants.CENTER);
		creditNames1.setBackground(Color.LIGHT_GRAY);
		creditNames1.setOpaque(true);
		creditNames1.setFont(new Font("Courier", Font.PLAIN,15));
		creditNames1.setBounds(100, 240, 210, 25);
		creditNames1.setVisible(false);
		creditNames1.setBorder(BorderFactory.createLineBorder(Color.RED, 1));

		JLabel creditNames2 = new JLabel();
		creditNames2.setText("Alexander Gruenwald");
		creditNames2.setHorizontalAlignment(SwingConstants.CENTER);
		creditNames2.setBackground(Color.LIGHT_GRAY);
		creditNames2.setOpaque(true);
		creditNames2.setFont(new Font("Courier", Font.PLAIN,15));
		creditNames2.setBounds(100, 270, 210, 25);
		creditNames2.setVisible(false);
		creditNames2.setBorder(BorderFactory.createLineBorder(Color.CYAN, 1));
		
		JLabel creditNames3 = new JLabel();
		creditNames3.setText("Chelsea M-c");
		creditNames3.setHorizontalAlignment(SwingConstants.CENTER);
		creditNames3.setBackground(Color.LIGHT_GRAY);
		creditNames3.setOpaque(true);
		creditNames3.setFont(new Font("Courier", Font.PLAIN,15));
		creditNames3.setBounds(100, 300, 210, 25);
		creditNames3.setVisible(false);
		creditNames3.setBorder(BorderFactory.createLineBorder(Color.GREEN, 1));

		JLabel creditNames4 = new JLabel();
		creditNames4.setText("Yoan Poulmarc'k");
		creditNames4.setHorizontalAlignment(SwingConstants.CENTER);
		creditNames4.setBackground(Color.LIGHT_GRAY);
		creditNames4.setOpaque(true);
		creditNames4.setFont(new Font("Courier", Font.PLAIN,15));
		creditNames4.setBounds(100,330, 210, 25);
		creditNames4.setVisible(false);
		creditNames4.setBorder(BorderFactory.createLineBorder(Color.BLUE, 1));

		JLabel creditNames5 = new JLabel();
		creditNames5.setText("Kaustav Das Sharma");
		creditNames5.setHorizontalAlignment(SwingConstants.CENTER);
		creditNames5.setBackground(Color.LIGHT_GRAY);
		creditNames5.setOpaque(true);
		creditNames5.setFont(new Font("Courier", Font.PLAIN,15));
		creditNames5.setBounds(100,360, 210, 25);
		creditNames5.setVisible(false);
		creditNames5.setBorder(BorderFactory.createLineBorder(Color.magenta, 1));
		
		JLabel creditNames6 = new JLabel();
		creditNames6.setText("Sean Tan");
		creditNames6.setHorizontalAlignment(SwingConstants.CENTER);
		creditNames6.setBackground(Color.LIGHT_GRAY);
		creditNames6.setOpaque(true);
		creditNames6.setFont(new Font("Courier", Font.PLAIN,15));
		creditNames6.setBounds(100,390, 210, 25);
		creditNames6.setVisible(false);
		creditNames6.setBorder(BorderFactory.createLineBorder(Color.ORANGE, 1));
		
		creditButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) {
				titleLabel.setText("Introducing the TEAM");
				titleLabel.setFont(new Font("Courier", Font.BOLD,30));
				newGame.setVisible(false);
				creditButton.setVisible(false);
				continueGame.setVisible(false);
				logoutButton.setVisible(false);
				backButton.setVisible(true);
				
				loginWelcome.setVisible(false);
				
				//set all labels true
				creditNames1.setVisible(true);
				creditNames2.setVisible(true);
				creditNames3.setVisible(true);
				creditNames4.setVisible(true);
				creditNames5.setVisible(true);
				creditNames6.setVisible(true);
				
				backButton.addActionListener(new ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent e) {
						titleLabel.setText("Block223");
						titleLabel.setFont(new Font("Courier", Font.BOLD,60));
						newGame.setVisible(true);
						creditButton.setVisible(true);
						continueGame.setVisible(true);
						logoutButton.setVisible(true);
						backButton.setVisible(false);
						loginWelcome.setVisible(true);
						
						creditNames1.setVisible(false);
						creditNames2.setVisible(false);
						creditNames3.setVisible(false);
						creditNames4.setVisible(false);
						creditNames5.setVisible(false);
						creditNames6.setVisible(false);

					}
				});		
			}
		});
		
		frame.add(panelBackground);
		panelBackground.add(newGame);
		panelBackground.add(continueGame);
		panelBackground.add(creditButton);
		panelBackground.add(logoutButton);
		panelBackground.add(errorMessage);
		panelBackground.add(titleLabel);
		panelBackground.add(backButton);
		panelBackground.add(confirmNewGame);
		panelBackground.add(confirmContinueGame);
		panelBackground.add(creditNames1);
		panelBackground.add(creditNames2);
		panelBackground.add(creditNames3);
		panelBackground.add(creditNames4);
		panelBackground.add(creditNames5);
		panelBackground.add(creditNames6);
		panelBackground.add(loginWelcome);
		panelBackground.add(newGameList);
		panelBackground.add(continueGameList);
    }
    public static void closeFrame()
	{
		frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
	}
    
    public static void refreshToggleLists(){
    	
	    //	errorMessage.setText(error);

			//if (error == null || error.length() == 0) {
				// populate popup with data
		
				resumeGames = new HashMap<Integer, Integer>();
				availableGames = new HashMap<Integer, String>();
				newGameList.removeAllItems();
	    		continueGameList.removeAllItems();
				Integer indexNew = 0;
				Integer indexContinue = 0;
				try {
					List<TOPlayableGame> pgames = Block223Controller.getPlayableGames();
					if(pgames.size() > 0) {
						for (TOPlayableGame pgame : pgames) {
							if(pgame.getNumber() == -1) {
								availableGames.put(indexNew, pgame.getName());
								newGameList.addItem(pgame.getName());
								indexNew++;
							}
							else {
								resumeGames.put(indexContinue, pgame.getNumber());
								continueGameList.addItem(pgame.getName() + " Lv:" + pgame.getCurrentLevel() + 
										" id:" + pgame.getNumber());
								indexContinue++;
							}
						}
					}
				} catch (InvalidInputException e) {
					error = e.getMessage();
					errorMessage.setText(error);
					errorMessage.setVisible(true);
				};
				continueGameList.setSelectedIndex(-1);	
				newGameList.setSelectedIndex(-1);
			//}
	    }
    
    public static void refreshLists() {
    	try
		{
    		newGameList.removeAllItems();
    		continueGameList.removeAllItems();
			List<TOPlayableGame> games = Block223Controller.getPlayableGames();
			int[] id = new int[games.size()];
			int counter = 0;
			for (TOPlayableGame game : games) {
				if(game.getNumber()==-1)
					newGameList.addItem(game.getName());
				else
					continueGameList.addItem( game.getName() + " Lv:" + game.getCurrentLevel());
					id[counter] = game.getNumber();

			}
		}catch(Exception e)
		{
			errorMessage.setText(e.toString());
		}
    	continueGameList.setSelectedIndex(-1);	
		newGameList.setSelectedIndex(-1);
    }
    
}
