package ca.mcgill.ecse223.block.view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import ca.mcgill.ecse223.block.application.Block223Application;
import ca.mcgill.ecse223.block.controller.Block223Controller;
import ca.mcgill.ecse223.block.controller.InvalidInputException;
import ca.mcgill.ecse223.block.controller.TOCurrentlyPlayedGame;
import ca.mcgill.ecse223.block.controller.TOHallOfFameEntry;
import ca.mcgill.ecse223.block.controller.TOUserMode;

public class PlayerGameUi implements Block223PlayModeInterface{
	static JFrame frame;
	static JLabel errorMessage;
	static String error = "";
	
	static int PLAY_AREA_SIZE = 390;
	static int PADDLE_LENGTH = 30;
	static int PADDLE_VERTICAL = 30;
	static int PADDLE_WIDTH = 5;
	
	public static JButton scoreG;
	public static JButton startButton;
	public static JButton mp3ButtonOff;
	public static JButton mp3ButtonOn;
	public static JButton returnButton;
	private static JLabel scoreLabel;
	private static JLabel livesLabel;
	private static JLabel blocksLeftLabel;
	private static JLabel titleLabelGame;
	private static JLabel titleLabelLevel;
	static Block223PlayModeExampleListener bp;
	
	static JButton mp3ButtonPause;
	static JComboBox<String> gameNameToggleList;
	static JButton mp3ButtonPick;
	static JButton mp3ButtonStart;
	
	static Boolean mp3On = false;
	
	static String musicChoice = "";
	
	static MusicClass clip;
	static Boolean start = false;
	
	private static int id = 1;
	
    public PlayerGameUi()
    {
    	id = 1;
        run();
    }
    
    public PlayerGameUi(int id) {
    	this.id = id;
    	run();
    }
    
    static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	static int centerWidth = (int) (screenSize.getWidth() / 2);
	static int centerHeight = (int) (screenSize.getHeight() / 2);

    public void run() {  
    	frame = new JFrame();
    				
    	//QUESTION: should we use two panels to store individual information?
    	
		frame.setSize(410, 620);
		frame.setLocation(centerWidth - 205, centerHeight - 300);
		frame.setLayout(null);
		frame.setResizable(false);
		
 		titleLabelGame = new JLabel();
 		titleLabelGame.setText("Game: "); // current game 
 		titleLabelGame.setHorizontalAlignment(SwingConstants.CENTER);
 		titleLabelGame.setBounds(0, 5, 410, 20);
 		titleLabelGame.setBackground(Color.GREEN);
 		titleLabelGame.setOpaque(true);
 		titleLabelGame.setFont(new Font("Courier", Font.BOLD, 20));

 		titleLabelLevel = new JLabel();
 		titleLabelLevel.setText("Level: "); // current level
 		titleLabelLevel.setHorizontalAlignment(SwingConstants.CENTER);
 		titleLabelLevel.setBounds(0, 30, 410, 15);
 		titleLabelLevel.setBackground(Color.GREEN);
 		titleLabelLevel.setOpaque(true);
 		titleLabelLevel.setFont(new Font("Courier", Font.BOLD, 15));
        
        // elements for error message
 		errorMessage = new JLabel();
 		errorMessage.setForeground(Color.RED);
 		errorMessage.setText("");
 		errorMessage.setBounds(0, 0, 390, 8);
 		errorMessage.setBackground(Color.WHITE);
 		errorMessage.setOpaque(true);
 		errorMessage.setVisible(false);
 		errorMessage.setHorizontalAlignment(SwingConstants.CENTER);
 		errorMessage.grabFocus();
 		errorMessage.setFont(new Font("Courier", Font.PLAIN,8));
 		
		//parent panel
		JPanel panelParent = new JPanel();
		panelParent.setLayout(null);
		panelParent.setBackground(Color.DARK_GRAY);
		panelParent.setBounds(0, 0, 410, 660);
		frame.add(panelParent);
		
		//panelGamePlay is where the blocks will be displayed
		JPanel panelGamePlay = new JPanel();
		panelGamePlay.setLayout(null);
		panelGamePlay.setBackground(Color.WHITE);
		panelGamePlay.setVisible(true);
		panelGamePlay.setBounds(10, 50, 390, 390);
		
		//this is where the live info will be displayed
		JPanel panelLiveDisplay = new JPanel();
		panelLiveDisplay.setLayout(null);
		panelLiveDisplay.setBackground(Color.LIGHT_GRAY);
		panelLiveDisplay.setVisible(true);
		panelLiveDisplay.setBounds(10, 480, 390, 95);
		
		scoreLabel = new JLabel("Score: " );//updated score
		scoreLabel.setHorizontalAlignment(SwingConstants.CENTER);
		scoreLabel.setBackground(Color.DARK_GRAY);
		scoreLabel.setForeground(Color.WHITE);
		scoreLabel.setOpaque(true);
		scoreLabel.setBounds(0, 5, 390, 25);
		scoreLabel.setFont(new Font("Courier", Font.BOLD,20));
		
		livesLabel = new JLabel("Lives: " );//updated score
		livesLabel.setHorizontalAlignment(SwingConstants.CENTER);
		livesLabel.setBackground(Color.DARK_GRAY);
		livesLabel.setForeground(Color.WHITE);
		livesLabel.setOpaque(true);
		livesLabel.setBounds(0, 35, 390, 25);
		livesLabel.setFont(new Font("Courier", Font.BOLD,20));
		
		blocksLeftLabel = new JLabel("Blocks left: " );//updated score
		blocksLeftLabel.setHorizontalAlignment(SwingConstants.CENTER);
		blocksLeftLabel.setBackground(Color.DARK_GRAY);
		blocksLeftLabel.setForeground(Color.WHITE);
		blocksLeftLabel.setOpaque(true);
		blocksLeftLabel.setBounds(0, 65, 390, 25);
		blocksLeftLabel.setFont(new Font("Courier", Font.BOLD,20));
		
		panelLiveDisplay.add(livesLabel);
		panelLiveDisplay.add(blocksLeftLabel);
		panelLiveDisplay.add(scoreLabel);
		
		panelParent.add(panelLiveDisplay);
		//panelParent.add(panelGamePlay);
	
		returnButton= new JButton("Leave");
		returnButton.setForeground(Color.BLACK);
		returnButton.setFont(new Font("Courier", Font.PLAIN,15));
		returnButton.setOpaque(true);
		returnButton.setBorder(BorderFactory.createLineBorder(Color.GREEN, 1));
		returnButton.setVisible(true);
		returnButton.setBounds(10, 450 ,90, 20);
		
		returnButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) {
			if(!mp3On) {	
				if(Block223Controller.getUserMode().getMode() == TOUserMode.Mode.Design) {
					try {
						TOCurrentlyPlayedGame pgameTO = Block223Controller.getCurrentPlayableGame();
						Block223Controller.selectGame(pgameTO.getGamename());
						DetailsPanel.setCurrentLevel(1);
						Block223Application.setCurrentPlayableGame(null);
						ViewManager.runView("MainUi");
					}catch(Exception exc){
						ViewManager.runView("MainUi");
					}
				}
				else
					ViewManager.runPopupView("PlayerIntroUi");
				closeFrame();
				} else {
					errorMessage.setVisible(true);
					errorMessage.setText("Please close MP3 before leaving");
				}
			}
		});
		
		scoreG = new JButton("Scores");
		scoreG.setForeground(Color.BLACK);
		scoreG.setFont(new Font("Courier", Font.PLAIN,15));
		scoreG.setOpaque(true);
		scoreG.setBorder(BorderFactory.createLineBorder(Color.GREEN, 1));
		scoreG.setVisible(true);
		scoreG.setBounds(310, 450 ,90, 20);
		
		//Start game
		startButton = new JButton("Start");
		startButton.setForeground(Color.BLACK);
		startButton.setFont(new Font("Courier", Font.PLAIN,15));
		startButton.setOpaque(true);
		startButton.setBorder(BorderFactory.createLineBorder(Color.GREEN, 1));
		startButton.setVisible(true);
		startButton.setBounds(210, 450 , 90, 20);
		
		startButton.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e) {

				errorMessage.setVisible(false);
				mp3ButtonPick.setEnabled(false);
				
				bp = new Block223PlayModeExampleListener();
				 Runnable r1 = new Runnable() {
				 	@Override
				 	public void run() {
				 		// in the actual game, add keyListener to the game window
				 		// Not sure what to add key listener to 
				 		frame.addKeyListener(bp);
				 	}
				 };
				 Thread t1 = new Thread(r1);
				 t1.start();
				 // to be on the safe side use join to start executing thread t1 before executing
				 // the next thread
				 try {
				 	t1.join();
				 } catch (InterruptedException e1) {
				 }

				 // initiating a thread to start the game loop
				 Runnable r2 = new Runnable() {
				 	@Override
				 	public void run() {
				 		try {
				 			start = true;
				 			Block223Controller.startGame(PlayerGameUi.this);
				 		} catch (InvalidInputException e2) {
				 			errorMessage.setText(e2.getMessage());
				 		}
				 	}
				 };
				 Thread t2 = new Thread(r2);
				 t2.start();
				 
				 
				scoreG.setEnabled(false);
				startButton.setEnabled(false);
				mp3ButtonOn.setEnabled(false);
				mp3ButtonOff.setEnabled(false);
				returnButton.setEnabled(false);
//This is the start button for the given game		

				KeyEventDispatcher keyEvent = new KeyEventDispatcher() {
					@Override
					public boolean dispatchKeyEvent(KeyEvent e) {
							bp.keyPressed(e);
							return false;
					}
				};
				KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(keyEvent);
				
			}
		});
	
		mp3ButtonOn= new JButton("Mp3 On");
		mp3ButtonOn.setForeground(Color.BLACK);
		mp3ButtonOn.setFont(new Font("Courier", Font.PLAIN,15));
		mp3ButtonOn.setOpaque(true);
		mp3ButtonOn.setBorder(BorderFactory.createLineBorder(Color.GREEN, 1));
		mp3ButtonOn.setVisible(true);
		mp3ButtonOn.setBounds(110, 450 ,90, 20);

		mp3ButtonOff= new JButton("Mp3 Off");
		mp3ButtonOff.setForeground(Color.BLACK);
		mp3ButtonOff.setFont(new Font("Courier", Font.PLAIN,15));
		mp3ButtonOff.setOpaque(true);
		mp3ButtonOff.setBorder(BorderFactory.createLineBorder(Color.GREEN, 1));
		mp3ButtonOff.setVisible(false);
		mp3ButtonOff.setBounds(110, 450 ,90, 20);

		mp3ButtonStart= new JButton("Play");
		mp3ButtonStart.setForeground(Color.BLACK);
		mp3ButtonStart.setFont(new Font("Courier", Font.PLAIN,15));
		mp3ButtonStart.setOpaque(true);
		mp3ButtonStart.setBorder(BorderFactory.createLineBorder(Color.GREEN, 1));
		mp3ButtonStart.setVisible(false);
		mp3ButtonStart.setBounds(110, 580 ,90, 20);
		mp3ButtonStart.setEnabled(false);
		
		mp3ButtonPause= new JButton("Pause");
		mp3ButtonPause.setForeground(Color.BLACK);
		mp3ButtonPause.setFont(new Font("Courier", Font.PLAIN,15));
		mp3ButtonPause.setOpaque(true);
		mp3ButtonPause.setBorder(BorderFactory.createLineBorder(Color.GREEN, 1));
		mp3ButtonPause.setVisible(false);
		mp3ButtonPause.setBounds(210, 580 ,90, 20);
		mp3ButtonPause.setEnabled(false);
		
		mp3ButtonPick = new JButton("Pick");
		mp3ButtonPick.setForeground(Color.BLACK);
		mp3ButtonPick.setFont(new Font("Courier", Font.PLAIN,15));
		mp3ButtonPick.setOpaque(true);
		mp3ButtonPick.setBorder(BorderFactory.createLineBorder(Color.GREEN, 1));
		mp3ButtonPick.setVisible(false);
		mp3ButtonPick.setBounds(10, 580 ,90, 20);
		
		JButton mp3ButtonReset= new JButton("Reset");
		mp3ButtonReset.setForeground(Color.BLACK);
		mp3ButtonReset.setFont(new Font("Courier", Font.PLAIN,15));
		mp3ButtonReset.setOpaque(true);
		mp3ButtonReset.setBorder(BorderFactory.createLineBorder(Color.GREEN, 1));
		mp3ButtonReset.setVisible(false);
		mp3ButtonReset.setBounds(310, 580 ,90, 20);
		mp3ButtonReset.setEnabled(false);
	
		//list of songs
		gameNameToggleList = new JComboBox<>();
 		gameNameToggleList.setBounds(130,  610, 150, 20);
 		gameNameToggleList.addItem("GuitarFlick.wav");
		gameNameToggleList.addItem("OldTownRoad.wav");
 		gameNameToggleList.addItem("Tempo.wav");
 		gameNameToggleList.addItem("Epic.wav");
 		gameNameToggleList.addItem("Pumping.wav");
 		gameNameToggleList.addItem("NeedAnA.wav");
 		gameNameToggleList.addItem("Stevie.wav");
 		gameNameToggleList.addItem("Peace.wav");
 		gameNameToggleList.addItem("WhatWeAreDoing.wav");
 		gameNameToggleList.addItem("Paradise.wav");
 		gameNameToggleList.setVisible(true);
 		gameNameToggleList.setEnabled(false);
 		
 		//we can add a victory sound
		
		mp3ButtonOn.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) {
				mp3ButtonOn.setVisible(false);
				mp3ButtonOff.setVisible(true);
				frame.setSize(410, 660);
				mp3On = true;
				
				startButton.setEnabled(false);
				
				mp3ButtonStart.setVisible(true);
				mp3ButtonPause.setVisible(true);
				mp3ButtonPick.setVisible(true);
				mp3ButtonPick.setEnabled(true);
				
				mp3ButtonReset.setVisible(true);
				
				gameNameToggleList.setEnabled(true);
			}
		});

		mp3ButtonOff.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) {
				mp3ButtonOff.setVisible(false);
				mp3ButtonOn.setVisible(true);
				frame.setSize(410, 620);
				mp3On = false;

				errorMessage.setVisible(false);
				
				mp3ButtonStart.setVisible(false);
				mp3ButtonPause.setVisible(false);
				mp3ButtonPick.setVisible(false);
				mp3ButtonReset.setVisible(false);
				
				gameNameToggleList.setEnabled(false);
				
				startButton.setEnabled(true);
			}
		});
		
		//checking to make sure not an admin
		if(!(Block223Controller.getUserMode().getMode() == TOUserMode.Mode.Design)) {
			scoreG.addActionListener(new ActionListener()
			{
				@Override
				public void actionPerformed(ActionEvent e) {
					ViewManager.runView("HallOfFame", 1);	
				}
			});	
		}
		panelParent.setVisible(true);
		frame.setVisible(true);
		panelParent.add(panelGamePlay);
		
		panelParent.add(startButton);
		panelParent.add(scoreG);
		panelParent.add(titleLabelGame);
		panelParent.add(titleLabelLevel);
		panelParent.add(returnButton);
		
		panelGamePlay.add(errorMessage);
		
		panelParent.add(mp3ButtonOn);
		panelParent.add(mp3ButtonOff);
		
		panelParent.add(mp3ButtonStart);
		panelParent.add(gameNameToggleList);
		panelParent.add(mp3ButtonPause);
		panelParent.add(mp3ButtonPick);
		panelParent.add(mp3ButtonReset);

		ViewManager.runView("GamePlayPanel", panelGamePlay, id);
		
		//all the mp3 actionListeners
		//pick sets the song chosen by the player
		mp3ButtonPick.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) {
				musicChoice = gameNameToggleList.getItemAt(gameNameToggleList.getSelectedIndex());
				clip = new MusicClass(musicChoice);
				
				mp3ButtonOff.setEnabled(false);
				mp3ButtonStart.setEnabled(true);
				mp3ButtonPick.setEnabled(false);
				mp3ButtonReset.setEnabled(true);
				gameNameToggleList.setEnabled(false);
				
				startButton.setEnabled(true);
			}
		});
		//start begins the music
		mp3ButtonStart.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) {
				mp3ButtonReset.setEnabled(false);
				mp3ButtonPause.setEnabled(true);
				mp3ButtonStart.setEnabled(false);
				
				if(clip != null) 
					clip.playMusic();
			}
		});
		
		mp3ButtonReset.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) {
				mp3ButtonOff.setEnabled(true);
				
				mp3ButtonPick.setEnabled(true);
				mp3ButtonReset.setEnabled(false);
				mp3ButtonPause.setEnabled(false);
				mp3ButtonStart.setEnabled(false);
				
				gameNameToggleList.setEnabled(true);

				if(clip != null) 
					clip.endMusic();
			}
		});
		
		mp3ButtonPause.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) {
				mp3ButtonReset.setEnabled(true);
				mp3ButtonPause.setEnabled(false);
				mp3ButtonStart.setEnabled(true);
				
				if(clip != null) 
					clip.stopMusic();
			}
		});
		
		if(id==1)
			refresh(); 
    }
	public static void closeFrame()
	{
		frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
	}
	public static void activateButtons(Boolean activate)
	{
		scoreG.setEnabled(activate);
		startButton.setEnabled(activate);
		returnButton.setEnabled(activate);
		mp3ButtonOn.setEnabled(activate);
	}

	@Override
	public String takeInputs() {
		// TODO Auto-generated method stub
		if (bp == null) {
			return "";
		}
		return bp.takeInputs();
	}

	@Override
	public void refresh() {
		// TODO Auto-generated method stub
		try {
			
			TOCurrentlyPlayedGame pgameTO = Block223Controller.getCurrentPlayableGame();
			// change lives
			livesLabel.setText("Lives: " + pgameTO.getLives());
			// change score
			scoreLabel.setText("Score: " + pgameTO.getScore());
			// change blocks left
			blocksLeftLabel.setText("Blocks left: " + pgameTO.getBlocks().size());
			// change names
			pgameTO.getPlayername();
			titleLabelGame.setText("Game: " + pgameTO.getGamename());
			// change level
			titleLabelLevel.setText("Level: " + pgameTO.getCurrentLevel());
			// display everything
				
			GamePlayPanel.refreshBall(pgameTO.getCurrentBallX(), pgameTO.getCurrentBallY());
			GamePlayPanel.refreshPaddle(pgameTO.getCurrentPaddleX(), pgameTO.getCurrentPaddleLength());
			GamePlayPanel.refreshBlocks(pgameTO.getBlocks());
	
			if(start) {
				if(pgameTO.getPaused()) {
					// show paused
					GamePlayPanel.setPauseOn();
					errorMessage.setText("Press continue when ready");
					startButton.setEnabled(true);
					mp3ButtonOn.setEnabled(true);
					returnButton.setEnabled(true);
					startButton.setText("Continue");
					scoreG.setEnabled(true);
					errorMessage.setVisible(true);
					errorMessage.grabFocus();
				}
				else {
					// remove paused
					GamePlayPanel.setPauseOff();
					startButton.setEnabled(false);
					errorMessage.setVisible(false);
					mp3ButtonOn.setEnabled(false);
					returnButton.setEnabled(false);
					scoreG.setEnabled(false);
				}
			}
		} catch (InvalidInputException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Override
	public void endGame(int lives, TOHallOfFameEntry hof) {

		// TODO Auto-generated method stub
		if (lives == 0) {
			ViewManager.runView("GameOverUi", hof);
		}
		else {
			if(!(Block223Controller.getUserMode().getMode() == TOUserMode.Mode.Design))
				ViewManager.runView("VictoryUi", hof, lives);
		}
			
		closeFrame();
	}
}
