package ca.mcgill.ecse223.block.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;

import javax.swing.JFrame;
import javax.swing.JPanel;

import ca.mcgill.ecse223.block.controller.Block223Controller;
import ca.mcgill.ecse223.block.controller.InvalidInputException;
import ca.mcgill.ecse223.block.controller.TOHallOfFameEntry;
import ca.mcgill.ecse223.block.controller.TOUserMode;

public class GameOverUi {
	static JFrame frame;
	
	static int frameX = 410;
	static int frameY = 610;
	
	public GameOverUi(TOHallOfFameEntry hof)
	{
		run(hof);
	}

    static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	static int centerWidth = (int) (screenSize.getWidth() / 2);
	static int centerHeight = (int) (screenSize.getHeight() / 2);

    public void run(TOHallOfFameEntry hof) {  
    	
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
		panel.setForeground(Color.BLACK);
		panel.setOpaque(true);
	
		JButton gameOverLabel = new JButton();
		gameOverLabel.setBounds(0, 40, frameX, frameY/2 + 100);
		gameOverLabel.setLayout(null);
		gameOverLabel.setIcon(new ImageIcon("gameOver.jpg"));
		gameOverLabel.setBackground(Color.BLACK);

		JButton returnButton= new JButton();
		if(Block223Controller.getUserMode().getMode() == TOUserMode.Mode.Design) {
			returnButton.setText("Back to Testing");
			returnButton.setFont(new Font("Courier", Font.PLAIN, 17));
		} else {
			returnButton.setText("Give Up");
			returnButton.setFont(new Font("Courier", Font.PLAIN,20));
		}
		returnButton.setForeground(Color.BLACK);
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
		

		JButton tryAgainButton= new JButton("Try again");
		if(Block223Controller.getUserMode().getMode() == TOUserMode.Mode.Design)
			tryAgainButton.setEnabled(false);
		tryAgainButton.setForeground(Color.BLACK);
		tryAgainButton.setFont(new Font("Courier", Font.PLAIN,20));
		tryAgainButton.setOpaque(true);
		tryAgainButton.setVisible(true);
		tryAgainButton.setBounds(frameX/2 - 90, 550 ,180, 40);
		tryAgainButton.grabFocus();
		tryAgainButton.setBorder(BorderFactory.createLineBorder(Color.GREEN, 3));
		tryAgainButton.addActionListener(new ActionListener()
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
		
		JButton revealScoreButton= new JButton("Reveal Score");
		if(Block223Controller.getUserMode().getMode() == TOUserMode.Mode.Design)
			revealScoreButton.setEnabled(false);
		revealScoreButton.setForeground(Color.BLACK);
		revealScoreButton.setFont(new Font("Courier", Font.PLAIN,20));
		revealScoreButton.setOpaque(true);
		revealScoreButton.setVisible(true);
		revealScoreButton.setBounds(frameX/2 - 90, 450 ,180, 40);
		revealScoreButton.grabFocus();
		revealScoreButton.setBorder(BorderFactory.createLineBorder(Color.GREEN, 3));
		revealScoreButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) {
				ViewManager.runView("HallOfFame", 0);		
			}
		});
		
		panel.add(tryAgainButton);
		panel.add(returnButton);
		panel.add(gameOverLabel);
		panel.add(revealScoreButton);
	    
	    frame.add(panel);
	    frame.setVisible(true);
	    panel.setVisible(true);
	    
	    VictoryAndGameoverMusic.startMusic("gameover");
	}
    
    public static void closeFrame()
	{
		frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
	}
}
