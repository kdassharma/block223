package ca.mcgill.ecse223.block.view;
import ca.mcgill.ecse223.block.controller.Block223Controller;
import ca.mcgill.ecse223.block.controller.InvalidInputException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class AdminUi extends JFrame{
	static JFrame frame;
	
	static JLabel welcomeMessageCreateGameLine1;
	static JLabel welcomeMessageCreateGameLine2;
	static JLabel welcomeMessageDetails;
	static JLabel errorMessage;
	static JLabel errorMessageSuccess;
	
	static JLabel welcomePressBlock;
	static JLabel levelLabel;
	static JLabel gameName;
	static JLabel blockNumber;
	static JLabel blockMax;
	
	private static String error = "";
	static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	static int centerWidth = (int) (screenSize.getWidth() / 2);
	static int centerHeight = (int) (screenSize.getHeight() / 2);

	public AdminUi()
	{
		run();
	}
	
	public static void run()
	{
		//this is a special swing method that creates a string thread 
		SwingUtilities.invokeLater(new Runnable()
		{
		
			public void run()
			{
				//closing login
				LogIn.closeFrame();
				
				frame = new MainFrame("Block223");				
				frame.setTitle("Block223");
				frame.setUndecorated(true);

				//top and bottom labels for main ui
				JLabel titleLabel = new JLabel();
				titleLabel.setText("--- Block 223 ---");
				titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
				titleLabel.setBounds(0, 10, 800, 20);
		        titleLabel.setBackground(Color.GREEN);
		        titleLabel.setOpaque(true);
		        titleLabel.setFont(new Font("Courier", Font.BOLD,20));
		        
		        JLabel titleLabelBot = new JLabel();
				titleLabelBot.setText("");
		        titleLabelBot.setBounds(0, 460, 2000, 10);
		        titleLabelBot.setBackground(Color.GREEN);
		        titleLabelBot.setOpaque(true);
		        titleLabelBot.setFont(new Font("Courier", Font.BOLD,20));
				
		        
		        // elements for error message
		 		errorMessage = new JLabel();
		 		errorMessage.setForeground(Color.RED);
		 		errorMessage.setText("");
		 		errorMessage.setBounds(0, 40, 3000, 15);
		 		errorMessage.setBackground(Color.WHITE);
		 		errorMessage.setOpaque(true);
		 		errorMessage.setVisible(false);
		 		errorMessage.setFont(new Font("Courier", Font.PLAIN,12));
		 		
		 		errorMessageSuccess = new JLabel();
		 		errorMessageSuccess.setForeground(Color.GREEN);
		 		errorMessageSuccess.setText("");
		 		errorMessageSuccess.setBounds(0, 40, 800, 15);
		 		errorMessageSuccess.setBackground(Color.WHITE);
		 		errorMessageSuccess.setHorizontalAlignment(SwingConstants.CENTER);
		 		errorMessageSuccess.setOpaque(true);
		 		errorMessageSuccess.setVisible(false);
		 		errorMessageSuccess.setFont(new Font("Courier", Font.PLAIN,12));
		 		
				//setting the main UI to be full on any computer
				frame.setSize(800, 650);
				frame.setLocation(centerWidth - 400, centerHeight - 325);
				frame.setVisible(true);	
				
				//adding logout 
				JButton btnLogout = new JButton("LogOut");
				btnLogout.setForeground(Color.BLACK);
				btnLogout.setFont(new Font("Courier", Font.PLAIN,15));
				btnLogout.setOpaque(true);
				btnLogout.setBorder(BorderFactory.createLineBorder(Color.GREEN, 1));


				btnLogout.setBounds(60, 70 ,80, 20);
						
//errorMESSAGE
				btnLogout.addActionListener(new ActionListener()
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
							errorMessage.setVisible(true);
							errorMessage.setText("Did not close properly");
						}
					}
				});
				JButton testGame = new JButton("Test");
				JButton saveGame = new JButton("Save");
				saveGame.setForeground(Color.BLACK);
				saveGame.setFont(new Font("Courier", Font.PLAIN,15));
				saveGame.setOpaque(true);
				saveGame.setBorder(BorderFactory.createLineBorder(Color.GREEN, 1));
				saveGame.setBounds(60, 100 ,80, 20);
				
				saveGame.addActionListener(new ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent e) {
						errorMessage.setVisible(false);
						try 
						{
							Block223Controller.saveGame();
							errorMessageSuccess.setVisible(true);
							errorMessageSuccess.setText("Successfully saved the game!");
						}
						catch(Exception exc){
							error = exc.getMessage();
							errorMessage.setText(error);
							errorMessage.setVisible(true); 
						}
					}
				});

//test game
				testGame.setForeground(Color.BLACK);
				testGame.setFont(new Font("Courier", Font.PLAIN,15));
				testGame.setOpaque(true);
				testGame.setBorder(BorderFactory.createLineBorder(Color.GREEN, 1));
				testGame.setBounds(60, 130 ,80, 20);
				testGame.setEnabled(true);
				testGame.addActionListener(new ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent e) {
						try 
						{
							if(Block223Controller.getCurrentDesignableGame() != null && !(Block223Controller.getBlocksOfCurrentDesignableGame().size() < 1)) {
								Block223Controller.testGame(new PlayerGameUi(0));
								AdminUi.closeFrame();
							} else {
								errorMessage.setText("Please create a block before testing.");
								errorMessage.setVisible(true); 
							}
						}
						catch(InvalidInputException exc){
							error = exc.getMessage();
							errorMessage.setText(error);
							errorMessage.setVisible(true); 
						}
					}
				});
				
				JButton publishGame = new JButton("Publish");
				publishGame.setForeground(Color.BLACK);
				publishGame.setFont(new Font("Courier", Font.PLAIN,15));
				publishGame.setOpaque(true);
				publishGame.setBorder(BorderFactory.createLineBorder(Color.GREEN, 1));

				publishGame.setBounds(60, 160 ,80, 20);
				publishGame.addActionListener(new ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent e) {
//publish game	
						try
						{
							Block223Controller.publishGame();
							errorMessageSuccess.setText("Game published properly!");
							errorMessageSuccess.setVisible(true);
						} catch (InvalidInputException e1) {
							error = e1.getMessage();
							errorMessage.setText(error);
							errorMessage.setVisible(true);
						}
						

					}
				});
				
				//setting up panel
				JPanel panel = new JPanel();
				panel.setLayout(null);
				panel.setBackground(Color.DARK_GRAY);
				panel.setOpaque(true);
				panel.add(titleLabel);
				panel.add(titleLabelBot);
				panel.add(saveGame);
				panel.add(testGame);
				panel.add(publishGame);
				panel.add(errorMessage);
				
				//welcome message
				welcomeMessageCreateGameLine1 = new JLabel("To get started,");
				welcomeMessageCreateGameLine1.setOpaque(true);
				welcomeMessageCreateGameLine1.setBackground(Color.DARK_GRAY);
				welcomeMessageCreateGameLine1.setForeground(Color.WHITE);
				welcomeMessageCreateGameLine1.setBounds(5, 220, 200, 30);
				welcomeMessageCreateGameLine1.setVisible(true);
				welcomeMessageCreateGameLine1.setFont(new Font("Courier", Font.PLAIN,17));
				welcomeMessageCreateGameLine1.setHorizontalAlignment(SwingConstants.CENTER);
				
				welcomeMessageCreateGameLine2 = new JLabel("click on create game");
				welcomeMessageCreateGameLine2.setOpaque(true);
				welcomeMessageCreateGameLine2.setBackground(Color.DARK_GRAY);
				welcomeMessageCreateGameLine2.setForeground(Color.WHITE);
				welcomeMessageCreateGameLine2.setBounds(5, 260, 200, 30);
				welcomeMessageCreateGameLine2.setVisible(true);
				welcomeMessageCreateGameLine2.setFont(new Font("Courier", Font.PLAIN,17));
				
				welcomePressBlock = new JLabel("now, double click block");
				welcomePressBlock.setOpaque(true);
				welcomePressBlock.setBackground(Color.DARK_GRAY);
				welcomePressBlock.setForeground(Color.WHITE);
				welcomePressBlock.setBounds(10, 260, 200, 30);
				welcomePressBlock.setVisible(true);
				welcomePressBlock.setFont(new Font("Courier", Font.PLAIN,14));
				
				welcomeMessageDetails = new JLabel("Game Details ->");
				welcomeMessageDetails.setOpaque(true);
				welcomeMessageDetails.setBackground(Color.DARK_GRAY);
				welcomeMessageDetails.setForeground(Color.WHITE);
				welcomeMessageDetails.setBounds(607, 230, 150, 30);
				welcomeMessageDetails.setVisible(true);
				welcomeMessageDetails.setFont(new Font("Courier", Font.PLAIN,17));
				welcomeMessageDetails.setHorizontalAlignment(SwingConstants.CENTER);
				
				
				panel.add(welcomeMessageCreateGameLine1);
				panel.add(welcomeMessageCreateGameLine2);
				panel.add(welcomePressBlock);
				panel.add(welcomeMessageDetails);
				panel.add(errorMessageSuccess);
				panel.add(btnLogout);
				
				//buttonDetail setup
				JButton buttonDetail = new JButton();
				buttonDetail.setText(":");
				buttonDetail.setFont(new Font("Courier", Font.CENTER_BASELINE,20));
		
				//adding to frame
				panel.add(buttonDetail);
				//buttonDetail.setPreferredSize(new Dimension(20, 100));
				buttonDetail.setBounds(760, 130, 40 , 200);
				buttonDetail.setBorderPainted(true);
				buttonDetail.setBorder(BorderFactory.createLineBorder(Color.GREEN, 4));
				buttonDetail.setOpaque(true);
				buttonDetail.setVisible(true);
				
				//adding panel to frame
				frame.add(panel);
				
					levelLabel = new JLabel("  Level: " + DetailsPanel.getCurrentLevel());
					levelLabel.setFont(new Font("Courier", Font.PLAIN,20));

					gameName = new JLabel("  GameName: " + DetailsPanel.getCurrentGameName());
					gameName.setFont(new Font("Courier", Font.PLAIN,20));

					blockNumber = new JLabel("  BlocksInGame: " + DetailsPanel.getNumBlocks());
					blockNumber.setFont(new Font("Courier", Font.PLAIN,20));
	
					blockMax = new JLabel("  BlocksLeft: " + DetailsPanel.getNumBlocksLeft());
					blockMax.setFont(new Font("Courier", Font.PLAIN,20));
				
				
					panel.add(levelLabel);
					levelLabel.setVisible(false);
					levelLabel.setBorder(BorderFactory.createLineBorder(Color.GREEN,3));
					levelLabel.setBackground(Color.LIGHT_GRAY);
					levelLabel.setOpaque(true);
					levelLabel.setBounds(510, 180, 250, 50);
					
					panel.add(gameName);
					gameName.setVisible(false);
					gameName.setBorder(BorderFactory.createLineBorder(Color.GREEN, 3));
					gameName.setOpaque(true);
					gameName.setBackground(Color.LIGHT_GRAY);
					gameName.setBounds(510, 130, 250, 50);
					
					panel.add(blockNumber);
					blockNumber.setVisible(false);
					blockNumber.setBorder(BorderFactory.createLineBorder(Color.GREEN, 3));
					blockNumber.setOpaque(true);
					blockNumber.setBackground(Color.LIGHT_GRAY);
					blockNumber.setBounds(510, 230, 250, 50);
					
					panel.add(blockMax);
					blockMax.setVisible(false);
					blockMax.setBorder(BorderFactory.createLineBorder(Color.GREEN, 3));
					blockMax.setOpaque(true);
					blockMax.setBackground(Color.LIGHT_GRAY);
					blockMax.setBounds(510, 280, 250, 50);
					
					//listening to hovering over button
					buttonDetail.addMouseListener((MouseListener) new MouseAdapter()
					{
						public void mouseEntered(MouseEvent e)
						{
							closeError();
							levelLabel.setVisible(true);
							gameName.setVisible(true);
							blockNumber.setVisible(true);
							blockMax.setVisible(true);
							welcomeMessageDetails.setVisible(false);
						}
						
						public void mouseExited(MouseEvent e)
						{
							levelLabel.setVisible(false);
							gameName.setVisible(false);
							blockNumber.setVisible(false);
							blockMax.setVisible(false);
						}
					});
				
						
				//creating the panel to add the blocks
				JPanel panelBorder = new JPanel();
				panelBorder.setLayout(null);
				panelBorder.setBorder(BorderFactory.createTitledBorder(""));
				panelBorder.setBackground(Color.WHITE);
				panelBorder.setVisible(true);
				panelBorder.setBounds(210, 65, 390, 390);
				
				//create all required blocks on panel
				ViewManager.runView("BlockGeneration", panelBorder);
				
				panel.add(panelBorder);
				
			}
		});
		
	}
	public static void refreshPanel() {

		levelLabel.setText("  Level: " + DetailsPanel.getCurrentLevel()); 
		levelLabel.setFont(new Font("Courier", Font.PLAIN,20));

		gameName.setText("  GameName: " + DetailsPanel.getCurrentGameName());
		gameName.setFont(new Font("Courier", Font.PLAIN,20));
		
		blockNumber.setText("  BlocksInGame: " + DetailsPanel.getNumBlocks());//  + numBlocksInGame);
		blockNumber.setFont(new Font("Courier", Font.PLAIN,20));
		
		blockMax.setText("  BlocksLeft: " + DetailsPanel.getNumBlocksLeft());// + numBlocksLeft);
		blockMax.setFont(new Font("Courier", Font.PLAIN,20));
	}
	public static void closeFrame()
	{
		frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
	}
	//get methods to find center of screen
	public static int getCenterWidth()
	{
		return centerWidth;
	}
	
	public static int getCenterHeight()
	{
		return centerHeight;
	}
	//turn welcome message off
	public static void turnOffWelcomeCreate()
	{
		welcomeMessageCreateGameLine1.setVisible(false);
		welcomeMessageCreateGameLine2.setVisible(false);
	}
	public static void turnOffWelcomeBlock()
	{
		welcomePressBlock.setVisible(false);
	}
	public static void closeError()
	{
		errorMessage.setVisible(false);
		errorMessageSuccess.setVisible(false);
	}
}
