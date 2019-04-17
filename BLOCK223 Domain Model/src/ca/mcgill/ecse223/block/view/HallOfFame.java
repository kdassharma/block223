package ca.mcgill.ecse223.block.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import ca.mcgill.ecse223.block.controller.Block223Controller;
import ca.mcgill.ecse223.block.controller.InvalidInputException;
import ca.mcgill.ecse223.block.controller.TOHallOfFame;
import ca.mcgill.ecse223.block.controller.TOHallOfFameEntry;

public class HallOfFame{
	static JFrame frame;
	static int maxLabelDisplay = 10;
	static int numberOfPages = 1;
	
	static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	static int centerWidth = (int) (screenSize.getWidth() / 2);
	static int centerHeight = (int) (screenSize.getHeight() / 2);
	private static List<JLabel> users;
	private static List<JLabel> scores;
	private static int currentPage = 1;
	
	static TOHallOfFame hallOfFame;
	
	public HallOfFame(int id)
	{
		run(id);
	}
	
	private static void run(int id)
	{
		users = new ArrayList<JLabel>();
		scores = new ArrayList<JLabel>();
		frame = new JFrame();
		frame.setVisible(true);
		frame.setResizable(false);
		frame.setSize(150,340);
		frame.setLocation(PlayerGameUi.scoreG.getX() + PlayerGameUi.frame.getX() - 15, PlayerGameUi.scoreG.getY() + PlayerGameUi.frame.getY() - 320);
		
		JLabel titleLabelTop = new JLabel();
		titleLabelTop.setText("Game: test1"); //have to update game and level
		titleLabelTop.setHorizontalAlignment(SwingConstants.CENTER);
		titleLabelTop.setBounds(0, 10, 150, 25);
		titleLabelTop.setFont(new Font("Courier", Font.BOLD, 20));
		titleLabelTop.setBackground(Color.GREEN);
		titleLabelTop.setOpaque(true);
		titleLabelTop.setVisible(true);

		JLabel pageLabel = new JLabel();
		pageLabel.setText(currentPage + "/" + numberOfPages); //have to update page
		pageLabel.setHorizontalAlignment(SwingConstants.CENTER);
		pageLabel.setBounds(75 ,284, 75, 20);
		pageLabel.setFont(new Font("Courier", Font.BOLD, 15));
		pageLabel.setBackground(Color.LIGHT_GRAY);
		pageLabel.setOpaque(true);
		pageLabel.setVisible(true);
        
		JPanel panel = new JPanel();
		panel.setBackground(Color.DARK_GRAY);
        panel.setSize(150, 340);
        panel.setLayout(null);
        panel.setLocation(0, 0);
        panel.setOpaque(true);
        panel.setVisible(true);
        
        frame.add(panel);
        panel.add(titleLabelTop);
        panel.add(pageLabel);
  	   		
		try {
			hallOfFame = Block223Controller.getHallOfFameEnd(1, Integer.MAX_VALUE, id);
			numberOfPages = (int) Math.ceil(hallOfFame.numberOfEntries() / 10.0);
		} catch (InvalidInputException e1) {
			//e1.printStackTrace();
		}
		
  	   for(int j = 0; j < maxLabelDisplay; j++)
  	   {
 	 		JLabel scoreUserLabel = new JLabel();
 	 		scoreUserLabel.setHorizontalAlignment(SwingConstants.CENTER);
 	 		scoreUserLabel.setBounds(0, 54 + 20*j, 100, 15);
 	 		scoreUserLabel.setBackground(Color.LIGHT_GRAY);
 	 		scoreUserLabel.setOpaque(true);
 	 		scoreUserLabel.setFont(new Font("Courier", Font.PLAIN,10));
 	 		users.add(scoreUserLabel);
 	 		panel.add(scoreUserLabel);
 	 		
 	 		JLabel scoreLabel = new JLabel();
 	 		scoreLabel.setHorizontalAlignment(SwingConstants.CENTER);
 	 		scoreLabel.setBounds(110, 54 + 20*j, 40, 15);
 	 		scoreLabel.setBackground(Color.LIGHT_GRAY);
 	 		scoreLabel.setFont(new Font("Courier", Font.PLAIN,10));
 	 		scoreLabel.setOpaque(true);
 	 		scores.add(scoreLabel);
 	 		panel.add(scoreLabel);
  	   }
       refreshHoF(currentPage, id);
  	   //next button
	  	JButton nextButton = new JButton("Next");
	  	nextButton.setForeground(Color.BLACK);
	  	nextButton.setFont(new Font("Courier", Font.PLAIN,15));
	  	nextButton.setOpaque(true); 
	  	nextButton.setHorizontalAlignment(SwingConstants.CENTER);
	  	nextButton.setBorder(BorderFactory.createLineBorder(Color.GREEN, 1));
	  	nextButton.setBounds(100, 254 ,45, 20);
		panel.add(nextButton);
	  	nextButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) {
				if(currentPage < numberOfPages) {
					currentPage += 1;
					refreshHoF(currentPage, id);
					pageLabel.setText(currentPage + "/" + numberOfPages);
				}
			}
		});
	  	
	  	JButton backButton = new JButton("Back");
	  	backButton.setForeground(Color.BLACK);
	  	backButton.setFont(new Font("Courier", Font.PLAIN,15));
	  	backButton.setOpaque(true);
	  	backButton.setHorizontalAlignment(SwingConstants.CENTER);
	  	backButton.setBorder(BorderFactory.createLineBorder(Color.GREEN, 1));
	  	backButton.setBounds(5, 254 ,45, 20);
		panel.add(backButton);
		backButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) {
//display the previous round of users and scores
				if(currentPage > 1) {
					currentPage -= 1;
					refreshHoF(currentPage, id);
					pageLabel.setText(currentPage + "/" + numberOfPages);
				}
			}
		});
		
		JButton returnButton = new JButton("<--");
		returnButton.setForeground(Color.BLACK);
		returnButton.setFont(new Font("Courier", Font.PLAIN,15));
		returnButton.setOpaque(true);
		returnButton.setHorizontalAlignment(SwingConstants.CENTER);
		returnButton.setBorder(BorderFactory.createLineBorder(Color.GREEN, 1));
		returnButton.setBounds(5, 284 ,45, 20);
		panel.add(returnButton);
		returnButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) {
				closeFrame();
				PlayerGameUi.activateButtons(true);;
			}
		});
		
	}
	public static void closeFrame()
	{
		frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
	}
	
	public static void refreshHoF(int page, int id) {
			
		int start = (page-1) * maxLabelDisplay;
		TOHallOfFame hof;
		try {
			hof = Block223Controller.getHallOfFameEnd(0, Integer.MAX_VALUE, id);

			
			int remainder = 10;
			if(start >= (int)(hof.numberOfEntries() / maxLabelDisplay)*maxLabelDisplay)
				remainder = hof.numberOfEntries() % maxLabelDisplay;

			int j = 0;
			if(start > hof.numberOfEntries())
				remainder = 0;
			for(int i = start; i < (start+remainder); i++)
			   {
					TOHallOfFameEntry entry = hof.getEntry(i);
			 		JLabel scoreUserLabel = users.get(j);
			 		scoreUserLabel.setText(entry.getPlayername());//text has to be updated to the username
			 		scoreUserLabel.setFont(new Font("Courier", Font.BOLD,12));
			 		JLabel scoreLabel = scores.get(j);
			 		scoreLabel.setText("" + entry.getScore());//text has to be updated to the username
			 		scoreLabel.setFont(new Font("Courier", Font.BOLD,12));
			 		j++;
			   }
			for(int i=remainder; i < maxLabelDisplay; i++) {
				JLabel scoreUserLabel =  users.get(i);
				scoreUserLabel.setText("- No User -");
				JLabel scoreLabel = scores.get(i);
				scoreLabel.setText(" - ");
			}
			
		} catch (InvalidInputException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
		
}



