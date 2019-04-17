package ca.mcgill.ecse223.block.view;

import ca.mcgill.ecse223.block.controller.TOGridCell;
import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class BlockGeneration {
	
	private static int MaxBlockHorizontal = 15;
	private static int MaxBlockVertical = 15;
	private static int BlockSize = 25;
	private static int BlockVertical = 22;
	private static List<JButton> buttons;
	
	public BlockGeneration(JPanel panel){
		buttons = new ArrayList<>();
        run(panel);
    }

    private static void run(JPanel panel) {
		//creating the JButtons in their specific location
		
    	for(int i = 0; i < MaxBlockHorizontal; i++)
    	{
    	   for(int j = 0; j < MaxBlockVertical; j++)
    	   {
    		   JButton button = new JButton();
    		   button.setText("+");
    		   button.setBounds(10 + ((i) * BlockSize), 10 + ((j) * BlockVertical),20,20);
    		   button.setVisible(true);
    		   button.setOpaque(true);
    		   button.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
    		   button.setForeground(Color.BLACK);
    		   panel.add(button);
    		   buttons.add(button);
    	    	
    		   button.addActionListener(new ActionListener() {
    			   @Override
    			   public void actionPerformed(ActionEvent e) {
    				   ViewManager.runView("BlockClickUi", button);
    	           }
    	       });
    	   }
    	}   	
    }
    
    
    public static void updateBlock(int xPos, int yPos, Color colour, String text, int width) {
    	for(JButton button: buttons) {
    		int x = button.getX();
    		int y = button.getY();
    		if(xPos == x && yPos == y) {
    			button.setText(text);
    			button.setForeground(colour);
    			button.setBorder(BorderFactory.createLineBorder(colour, width));
    			button.setOpaque(true);
    			return;
    		}
    	}
    }
    
    public static int getXFromCoords(int x) {
    	return (10+(x-1)*BlockSize);
    }
    
    public static int getYFromCoords(int y) {
    	return (10+(y-1)*BlockVertical);
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
    
    public static void regenerateBlocks(List<TOGridCell> blocks) {

    	List<Integer> coordsX = new ArrayList<>();
    	List<Integer> coordsY = new ArrayList<>();
    	for(TOGridCell block: blocks) {
    		coordsX.add(getXFromCoords(block.getGridHorizontalPosition()));
    		coordsY.add(getYFromCoords(block.getGridVerticalPosition()));
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
	    						TOGridCell block = blocks.get(indexX);
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
    			button.setForeground(Color.BLACK);
    			button.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
				button.setText("+");
    		}
    			
    	}
    }
}