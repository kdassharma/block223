package ca.mcgill.ecse223.block.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
//this class deals with all the buttons in the border section

//This class will be used for the GridBagLayout - the most useful.

public class DetailsPanel extends JPanel{
	
	private String error = "";
	private static int currentLevel = 1;
	private static String currentGameName = "";
	private static int numBlocks = 0;
	private static int numBlocksLeft = 0;

	JComboBox<String> gameNameToggleList;
	
	public DetailsPanel()
	{
		//the panel can have it's size and dimensions changed
		Dimension size = getPreferredSize();
		
		//setting the size of the panel - width
		size.width = 1500;
		size.height = 150;
		setPreferredSize(size);
		
		//border from borderFactory with a title
		setBorder(BorderFactory.createTitledBorder(""));
		

		JButton returnButton5 = new JButton("<--");
		returnButton5.setFont(new Font("Courier", Font.PLAIN,10));
		returnButton5.setSize(50, 30);
	
		//for image validation
		validate();
		
		//button initialization with additional features
		JButton btnCreateGame = new JButton(" CreateGame ");
		btnCreateGame.setForeground(Color.BLACK);
		btnCreateGame.setBackground(Color.BLACK);
		btnCreateGame.setFont(new Font("Courier", Font.PLAIN,30));
		btnCreateGame.setBackground(Color.DARK_GRAY);
		btnCreateGame.setOpaque(true);
		
		JButton btnGameMenu = new JButton("  GameMenu  ");
		btnGameMenu.setForeground(Color.BLACK);
		btnGameMenu.setFont(new Font("Courier", Font.PLAIN,30));
		btnGameMenu.setBackground(Color.DARK_GRAY);
		btnGameMenu.setOpaque(true);
		
		JButton btnAddBlock = new JButton(" BlockMenu ");  
		btnAddBlock.setForeground(Color.BLACK);
		btnAddBlock.setFont(new Font("Courier", Font.PLAIN,30));
		btnAddBlock.setBackground(Color.DARK_GRAY);
		btnAddBlock.setOpaque(true);
					
		JButton btnNewLevel = new JButton(" LevelMenu ");
		btnNewLevel.setForeground(Color.BLACK);
		btnNewLevel.setFont(new Font("Courier", Font.PLAIN,30));
		btnNewLevel.setBackground(Color.DARK_GRAY);
		btnNewLevel.setOpaque(true);
		
		//setting a new grid bag layout
		setLayout(new GridBagLayout());
		
		//the grid bag constraints allows us to put the labels ANYWHERE we want
		GridBagConstraints gc = new GridBagConstraints();

		///// first column ///////////////////////////
		
		//anchor is a way to tell the UI where you want to stick to - default
		gc.anchor = GridBagConstraints.CENTER;
		
		//weight relates to how much space is allocated to the JSwing.
		gc.weightx = 1;
		gc.weighty = 1;
		
		//setting up the x and y coordinates
		
	///// First Column //////
		gc.gridx = 0;
		gc.gridy = 0;
		add(btnCreateGame, gc);
		
		btnCreateGame.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) {
				JFrame frameMenu = new JFrame();
				frameMenu.setVisible(true);
								
				ViewManager.runView("CreateGameMenu", frameMenu);
			}
		});
		
		

		gc.gridx = 0;
		gc.gridy = 1;
		add(btnGameMenu, gc);
		
		//ActionListener for the start game button
		btnGameMenu.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) {
								
				JFrame frameGame = new JFrame("Game Menu");
				
				ViewManager.runView("GameMenu", frameGame);
			}	
		});		
	///// Second Column ///////////////////////////////////////////////////////////////
		
		gc.anchor = GridBagConstraints.CENTER;
		gc.gridx = 1;
		gc.gridy = 0;
		add(btnAddBlock, gc);

		
		//Creating of the intermediate frame
		JFrame frameAddBlock = new JFrame();
		frameAddBlock.setVisible(false);
				
		//default UI size??
		frameAddBlock.setSize(250,  250);
				
		JPanel panelAddBlock1 = new JPanel();
		panelAddBlock1.setLayout(null);
		panelAddBlock1.setSize(250, 250);
		panelAddBlock1.setBackground(Color.DARK_GRAY);
		panelAddBlock1.setOpaque(true);
		
		frameAddBlock.add(panelAddBlock1);
		
		//Setting the frameLevel on top of frame button
		//location and visibility of the return button
		frameAddBlock.setLocation(AdminUi.getCenterWidth() + 80,AdminUi.getCenterHeight() - 10);			
		frameAddBlock.add(returnButton5);
		frameAddBlock.setUndecorated(true);
		
		JLabel titleLabel = new JLabel();
		titleLabel = new JLabel();
		titleLabel.setText("Block Menu");
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		titleLabel.setVisible(true);
		titleLabel.setBounds(0,30, 250, 30);
		titleLabel.setBackground(Color.GREEN);
		titleLabel.setOpaque(true);
		titleLabel.setFont(new Font("Courier", Font.BOLD, 20));

		JButton btnAddBlock1 = new JButton(" New Block ");
		btnAddBlock1.setVisible(true);
		btnAddBlock1.setLocation(25, 80);
		btnAddBlock1.setSize(200, 40);
		btnAddBlock1.setBorder(BorderFactory.createLineBorder(Color.GREEN, 4));
		btnAddBlock1.setOpaque(true);
		
		JButton btnUpdateBlock1 = new JButton(" Update Block ");
		btnUpdateBlock1.setVisible(true);
		btnUpdateBlock1.setLocation(25, 130);
		btnUpdateBlock1.setSize(200, 40);
		btnUpdateBlock1.setBorder(BorderFactory.createLineBorder(Color.GREEN, 4));
		btnUpdateBlock1.setOpaque(true);
		
		JButton btnAddBlock2 = new JButton(" Delete Block ");
		btnAddBlock2.setVisible(true);
		btnAddBlock2.setLocation(25, 180);
		btnAddBlock2.setSize(200, 40);
		btnAddBlock2.setBorder(BorderFactory.createLineBorder(Color.GREEN, 4));
		btnAddBlock2.setOpaque(true);
		
		panelAddBlock1.add(btnAddBlock1);
		panelAddBlock1.add(btnUpdateBlock1);
		panelAddBlock1.add(btnAddBlock2);
		panelAddBlock1.add(returnButton5);
		panelAddBlock1.add(titleLabel);
		
		returnButton5.setVisible(true);
		returnButton5.setIcon(new ImageIcon("ReturnButton.png"));
			
		
		//actionLdistener for btnAddBlock1
		btnAddBlock1.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) {
				JFrame jFrame1 = new JFrame();
				frameAddBlock.setVisible(false);
				jFrame1.setSize(250,  280);
				jFrame1.setLocation(AdminUi.getCenterWidth() + 80,AdminUi.getCenterHeight() - 90);
				jFrame1.setVisible(true);
								
				ViewManager.runView("CreateBlock", jFrame1);
			}
		});
		
		// actionListener for btnUpdateBlock1
		btnUpdateBlock1.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) {
				JFrame jFrame1 = new JFrame();
				frameAddBlock.setVisible(false);
				jFrame1.setSize(250,  300);
				jFrame1.setLocation(AdminUi.getCenterWidth() + 80,AdminUi.getCenterHeight() - 90);
				jFrame1.setVisible(true);
								
				ViewManager.runView("UpdateBlock", jFrame1);
			}
		});
		
		//actionListener for btnAddBlock2
		btnAddBlock2.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) {
				JFrame jFrame1 = new JFrame();
				frameAddBlock.setVisible(false);
				jFrame1.setSize(250,  200);
				jFrame1.setLocation(AdminUi.getCenterWidth() + 80,AdminUi.getCenterHeight() - 10);
				jFrame1.setVisible(true);
				
				ViewManager.runView("DeleteBlock", jFrame1);
			}
		});
	
		btnAddBlock.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) {
				frameAddBlock.setVisible(true);			
						
				returnButton5.addActionListener(new ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent e) {
						frameAddBlock.setVisible(false);
					}
				});
			}
		});
		
		gc.gridx = 1;
		gc.gridy = 1;
		add(btnNewLevel, gc);
		
		//actionListener for the new level button
		btnNewLevel.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) {
				JFrame frameLevel = new JFrame();
				frameLevel.setSize(250, 150);
				frameLevel.setLocation(AdminUi.getCenterWidth() + 80,AdminUi.getCenterHeight() + 60);			
				
				ViewManager.runView("LevelConfig", frameLevel);
				
			}
		});
	}

	public static int getCurrentLevel(){
		return currentLevel;
	}
	public static void setCurrentLevel(int level){
		currentLevel = level;
	}
	
	public static String getCurrentGameName(){
		return currentGameName;
	}
	public static void setCurrentGameName(String name){
		currentGameName = name;
	}
	public static int getNumBlocks(){
		return numBlocks;
	}
	public static void setNumBlocks(int num){
		numBlocks = num;
	}
	
	public static int getNumBlocksLeft(){
		return numBlocksLeft;
	}
	public static void setNumBlocksLeft(int num){
		numBlocksLeft = num;
	}
	
}
