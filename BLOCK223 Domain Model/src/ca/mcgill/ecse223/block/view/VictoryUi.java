package ca.mcgill.ecse223.block.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import ca.mcgill.ecse223.block.controller.Block223Controller;
import ca.mcgill.ecse223.block.controller.InvalidInputException;

import ca.mcgill.ecse223.block.controller.TOHallOfFameEntry;


import ca.mcgill.ecse223.block.controller.TOUserMode;

public class VictoryUi {
	static JFrame frame;
	static BufferedImage image;
	
	static int frameX = 410;
	static int frameY = 610;
	
	static MusicClass clip1;
	
	public VictoryUi(TOHallOfFameEntry hof, int lives)
	{
		run(hof, lives);
	}

    static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	static int centerWidth = (int) (screenSize.getWidth() / 2);
	static int centerHeight = (int) (screenSize.getHeight() / 2);

    public void run(TOHallOfFameEntry hof, int lives) {  
    	
    	frame = new JFrame();
    	frame.setUndecorated(true);
		frame.setSize(frameX, frameY);
		frame.setLocation(centerWidth - frameX/2, centerHeight - frameY/2);
		frame.setLayout(null);
		frame.setResizable(false);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, frameX, frameY);
		panel.setLayout(null);
		panel.setBackground(Color.BLACK);
		
		JLabel scoreLabel = new JLabel();
		JLabel livesLabel = new JLabel();
		
		
		scoreLabel.setText("Your Score: " + hof.getScore());
		livesLabel.setText("Lives Left: " + lives);
		
	    scoreLabel.setBounds(frameX/2 - 100, frameY/2, 200, 40);
	    scoreLabel.setVisible(true);
	    scoreLabel.setOpaque(true);
	    scoreLabel.setForeground(Color.WHITE);
	    scoreLabel.setFont(new Font("Bostella", Font.PLAIN, 20));
	    scoreLabel.setBackground(Color.BLACK);
	    scoreLabel.setHorizontalAlignment(SwingConstants.CENTER);
	    
	    livesLabel.setBounds(frameX/2 - 100, frameY/2 + 50, 200, 40);
	    livesLabel.setVisible(true);
	    livesLabel.setOpaque(true);
	    livesLabel.setForeground(Color.WHITE);
	    livesLabel.setFont(new Font("Bostella", Font.PLAIN, 20));
	    livesLabel.setBackground(Color.BLACK);
	    livesLabel.setHorizontalAlignment(SwingConstants.CENTER);
	    		
	    JButton youWinLabel = new JButton();
	    youWinLabel.setIcon(new ImageIcon("victoryPic.jpg"));
	    youWinLabel.setBounds(0, 120, frameX, 180);
	    panel.add(youWinLabel);
	    
		JButton returnButton= new JButton("Leave");
		returnButton.setForeground(Color.BLACK);
		returnButton.setFont(new Font("Courier", Font.PLAIN,20));
		returnButton.setOpaque(true);
		returnButton.setVisible(true);
		returnButton.setBounds(frameX/2 - 90, 500 ,180, 40);
		returnButton.grabFocus();
		returnButton.setBorder(BorderFactory.createLineBorder(Color.GREEN, 3));
		returnButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) {
				if(Block223Controller.getUserMode().getMode() == TOUserMode.Mode.Design)
					ViewManager.runView("MainUi");
				else
					ViewManager.runPopupView("PlayerIntroUi");
				closeFrame();	
			}
		});
		

		JButton playAgainButton= new JButton("Play again");
		playAgainButton.setForeground(Color.BLACK);
		playAgainButton.setFont(new Font("Courier", Font.PLAIN,20));
		playAgainButton.setOpaque(true);
		playAgainButton.setVisible(true);
		playAgainButton.setBounds(frameX/2 - 90, 550 ,180, 40);
		playAgainButton.grabFocus();
		playAgainButton.setBorder(BorderFactory.createLineBorder(Color.GREEN, 3));
		playAgainButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) {
				//restart game
				try {
					String name = Block223Controller.getLastPlayedGame();
					Block223Controller.selectPlayableGame(name, -1);
				} catch (InvalidInputException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				ViewManager.runPopupView("PlayerGameUi");

				closeFrame();	
			}
		});
		
		JButton scoreButton= new JButton("See My Score");
		scoreButton.setForeground(Color.BLACK);
		scoreButton.setFont(new Font("Courier", Font.PLAIN,20));
		scoreButton.setOpaque(true);
		scoreButton.setVisible(true);
		scoreButton.setBounds(frameX/2 - 90, 450 ,180, 40);
		scoreButton.grabFocus();
		scoreButton.setBorder(BorderFactory.createLineBorder(Color.GREEN, 3));
		scoreButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) {
				
				
//bring up the hall of fame
				ViewManager.runView("HallOfFame", 0);
				
				
			}
		});
		
		panel.add(playAgainButton);
		panel.add(returnButton);
		panel.add(scoreLabel);
		panel.add(scoreButton);
		panel.add(livesLabel);
	    
	    frame.add(panel);
	    frame.setVisible(true);
	    panel.setVisible(true);

	    VictoryAndGameoverMusic.startMusic("victory");
    }
    
    public static void closeFrame()
	{
		frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
	}
}
