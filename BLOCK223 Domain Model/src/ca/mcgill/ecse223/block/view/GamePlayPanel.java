package ca.mcgill.ecse223.block.view;

import ca.mcgill.ecse223.block.controller.Block223Controller;
import ca.mcgill.ecse223.block.controller.InvalidInputException;
import ca.mcgill.ecse223.block.controller.TOCurrentBlock;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/*
 * This is the panel where the ball, paddle and blocks will be located
 */

public class GamePlayPanel {

	private static int MaxBlockHorizontal = 15;
	private static int MaxBlockVertical = 15;
	private static int BlockSize = 25;
	private static int BlockVertical = 22; 
	
	private static int BALL_SIZE = 10;
	private static int PLAY_AREA_SIZE = 390;
	
	private static int PADDLE_VERTICAL = 30;
	private static int PADDLE_WIDTH = 5;
	private static JLabel pauseDisplay;
	private static List<JButton> buttons;
	private static JButton ball;
	private static JButton paddle;
	
	public GamePlayPanel(JPanel panel, int id){
		buttons = new ArrayList<>();
        run(panel, id);
    }

    private static void run(JPanel panel, int id) {
    	
    	//ball
    	ball = new JButton();
    	ball.setBackground(Color.WHITE);
    	ball.setOpaque(true);
    	ball.setVisible(true);
    	ball.setBorder(BorderFactory.createLineBorder(Color.GREEN, 0));
    	ball.setSize(BALL_SIZE, BALL_SIZE);
    	ball.setLocation(PLAY_AREA_SIZE/2 - BALL_SIZE/2, PLAY_AREA_SIZE/2 - BALL_SIZE/2);
    			
    	ball.setIcon(new ImageIcon("purpleBall.png"));
    	
    	//paddle
    	paddle= new JButton();
    	paddle.setBackground(Color.BLUE);
    	paddle.setOpaque(true);
    	paddle.setVisible(true);
    	paddle.setBorder(BorderFactory.createLineBorder(Color.GREEN, 0));
    	
    	panel.add(ball);
    	panel.add(paddle);
    	
    	for(int i = 0; i < MaxBlockHorizontal; i++)
    	{
    	   for(int j = 0; j < MaxBlockVertical; j++)
    	   {
    		   
    		   //Only generate the blocks that are activated for the level
    		   JButton button = new JButton();
    		   button.setBounds(10 + ((i) * BlockSize) , 10 + ((j) * BlockVertical) ,20,20);
    		   button.setVisible(false);
    		   button.setOpaque(true);
    		   button.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
    		   button.setForeground(Color.BLACK);
    		   panel.add(button);
    		   buttons.add(button);
    	    	
    	   }
    	}  
    	
    	try {
    		if(id==1)
    			regenerateBlocks(Block223Controller.getCurrentPlayableGame().getBlocks());
		} catch (InvalidInputException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	pauseDisplay = new JLabel("PAUSED");
    	pauseDisplay.setHorizontalAlignment(SwingConstants.CENTER);
    	pauseDisplay.setForeground(Color.RED);
    	pauseDisplay.setBackground(Color.WHITE);
    	pauseDisplay.setOpaque(true);
    	pauseDisplay.setBounds(0, 365, 390, 25);
    	pauseDisplay.setFont(new Font("Courier", Font.PLAIN,25));
    	pauseDisplay.setVisible(false);
    	
    	panel.add(pauseDisplay);
    }
    public static void setPauseOn()
    {
    	pauseDisplay.setVisible(true);
    }
    public static void setPauseOff()
    {
    	pauseDisplay.setVisible(false);
    }
    public static void refreshBall(double newX, double newY) {
    	ball.setLocation((int)newX - BALL_SIZE/2, (int)newY - BALL_SIZE/2);
    	ball.setVisible(true);
    }
    
    public static void refreshPaddle(double x, double length) {
    	paddle.setLocation((int)x, PLAY_AREA_SIZE - PADDLE_VERTICAL - PADDLE_WIDTH);
    	paddle.setSize((int)length, PADDLE_WIDTH);
    }
    
    public static void refreshBlocks(List<TOCurrentBlock> list) {
    	regenerateBlocks(list);
    }
    
    private static List<Integer> indexOfAll(Integer pos, List<Integer> list) {
        final List<Integer> indexList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            if (pos.equals(list.get(i))) {
                indexList.add(i);
            }
        }
        return indexList;
    }
    
    private static void regenerateBlocks(List<TOCurrentBlock> blocks) {

    	List<Integer> coordsX = new ArrayList<>();
    	List<Integer> coordsY = new ArrayList<>();
    	for(TOCurrentBlock block: blocks) {
    		coordsX.add(block.getX());
    		coordsY.add(block.getY());
    	}

    	for(JButton button: buttons) {
    		int x = button.getX();
    		int y = button.getY();
    		boolean setButton = false;
    		if(coordsX.contains(x) && coordsY.contains(y)) {
    			List<Integer> indecesX = indexOfAll(x, coordsX);
    			List<Integer> indecesY = indexOfAll(y, coordsY);
    			if (indecesX != null && indecesY != null) {
	    			for(Integer indexX : indecesX) {
	    				for(Integer indexY : indecesY) {
	    					if (indexX == indexY) {
	    		    			TOCurrentBlock block = blocks.get(indexX);
	    		    			Color colour = new Color(block.getRed(), block.getGreen(), block.getBlue());
	    		    			button.setForeground(colour);
	    		    			button.setBorder(BorderFactory.createLineBorder(colour, 10));
	    		    			button.setVisible(true);
	    		    			setButton = true;
	    					}	
	    				}
	    			}
	    		}
    		}
    		if(setButton == false) {
    			// change to invisible
    			button.setVisible(false);
    		}
    			
    	}
    }
}
