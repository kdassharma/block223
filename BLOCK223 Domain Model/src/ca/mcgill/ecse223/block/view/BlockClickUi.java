package ca.mcgill.ecse223.block.view;

import javax.swing.*;

import ca.mcgill.ecse223.block.controller.Block223Controller;
import ca.mcgill.ecse223.block.controller.InvalidInputException;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

public class BlockClickUi {
	
	static int clickLocationX;
	static int clickLocationY;
	
	static int xCoord;
	static int yCoord;
	private static int BlockSize = 25;
	private static int BlockVertical = 22;
	
	static JFrame colorFrame;
	private static String error = null;
	static JLabel errorMessage;
	
	public BlockClickUi(JButton button){
        run(button);
    }

    private static void run(JButton button) {

    	AdminUi.closeError();
		//BlockFrame for choice of color change
		colorFrame = new JFrame();
		colorFrame.setSize(200, 170);
		
		AdminUi.turnOffWelcomeBlock();
		
		//blockConfiguration
		button.addActionListener(new ActionListener()
		{
			//changes color of block when clicked
			@Override
			public void actionPerformed(ActionEvent a) 
			{
				
				//for admin to know which block he is pressing on
				button.setForeground(Color.RED);
				
				//if(button.getText().equals(intermediate) || button.getText().equals(filled)) 
				//{
				//button.setText("");
				clickLocationX = button.getX();
				clickLocationY = button.getY();
				
				error = "";
				xCoord = clickLocationX + AdminUi.getCenterWidth() - AdminUi.getCenterWidth() / 2 + 30;
				yCoord = clickLocationY + AdminUi.getCenterHeight() - AdminUi.getCenterHeight() / 2;
				
				colorFrame.setLocation(xCoord, yCoord);
				
				//Intermediate button pressing for when clicking block
				JPanel panelInter = new JPanel();
				panelInter.setBackground(Color.DARK_GRAY);
				panelInter.setOpaque(true);
				panelInter.setVisible(true);
				panelInter.setLayout(null);
				panelInter.setBounds(0, 0, 200, 170);
				colorFrame.add(panelInter);
				
				// elements for error message
				errorMessage = new JLabel();
				errorMessage.setForeground(Color.RED);
				errorMessage.setText("");
				errorMessage.setBounds(0, 120, 200, 15);
				errorMessage.setBackground(Color.WHITE);
				errorMessage.setOpaque(true);
				errorMessage.setVisible(false);
				errorMessage.setFont(new Font("Courier", Font.PLAIN,7));
				
				panelInter.add(errorMessage);
				
				colorFrame.setVisible(true);

				
				JButton btnChangeBlock = new JButton("AddBlock");
				btnChangeBlock.setHorizontalAlignment(SwingConstants.CENTER);
				btnChangeBlock.setVisible(true);
				btnChangeBlock.setBounds(50, 8, 100, 20);
				panelInter.add(btnChangeBlock);
			
				btnChangeBlock.addActionListener(new ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent b) {
						JFrame jFrame1 = new JFrame();
						jFrame1.setSize(250,  140);
						jFrame1.setLocation(xCoord - 50, yCoord);
						jFrame1.setVisible(true);
						//button.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
						ViewManager.runView("ChangeBlock", jFrame1, button);
					}
				});
				JButton btnMoveBlock = new JButton("MoveBlock");
				btnMoveBlock.setHorizontalAlignment(SwingConstants.CENTER);
				btnMoveBlock.setVisible(true);
				btnMoveBlock.setBounds(50, 36, 100, 20);
				panelInter.add(btnMoveBlock);
				
				
				btnMoveBlock.addActionListener(new ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent b) {
							JFrame jFrame1 = new JFrame();
							jFrame1.setSize(260,  190);
							jFrame1.setLocation(xCoord - 60, yCoord - 20);
							jFrame1.setVisible(true);
							ViewManager.runView("MoveBlock", jFrame1, clickLocationX, clickLocationY);
					}
				});
//controllers needed to move block
					
				JButton btnDeleteBlock = new JButton("DeleteBlock");
				btnDeleteBlock.setHorizontalAlignment(SwingConstants.CENTER);
				btnDeleteBlock.setVisible(true);
				btnDeleteBlock.setBounds(50, 64, 100, 20);
				panelInter.add(btnDeleteBlock);
				
				btnDeleteBlock.addActionListener(new ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent b) {
						try {
							Block223Controller.removeBlock(DetailsPanel.getCurrentLevel(), getCoordsFromX(clickLocationX), getCoordsFromY(clickLocationY));
							button.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
							button.setForeground(Color.BLACK);
							button.setText("+");
							DetailsPanel.setNumBlocks(DetailsPanel.getNumBlocks() - 1);
							DetailsPanel.setNumBlocksLeft(DetailsPanel.getNumBlocksLeft()+1);
							RefreshData.refreshData("panel");
						} catch (InvalidInputException e) {
							errorMessage.setVisible(true);
							error = e.getMessage();
							errorMessage.setText(error);;
						}
						catch (NullPointerException e) {
							
							errorMessage.setVisible(true);
							error = "Please select a block";
							errorMessage.setText(error);;
							
						}
					}
				    public int getCoordsFromX(int x) {
				    	return (x-10)/BlockSize + 1;
				    }
				    
				    public int getCoordsFromY(int y) {
				    	return (y-10)/BlockVertical + 1;
				    }
				    
				});
					
				/*JButton returnButton = new JButton("Return");
				returnButton.setHorizontalAlignment(SwingConstants.CENTER);
				returnButton.setVisible(true);
				returnButton.setBounds(50, 92, 100, 20);
				panelInter.add(returnButton);
				
				//colorFrame.setVisible(false);
				returnButton.addActionListener(new ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent c) {
						if(button.getText() == "" || button.getText() == "+") {
							button.setForeground(Color.BLACK);
							closeFrame();
						}
					}
				});	*/
			}
		});
    }
    

    
    
    //button coordinate for x
    public static int getButtonCoordHorizontal()
    {
    	return clickLocationX;
    }
    
    //button coordinate for y
    public static int getButtonCoordVertical()
    {
    	return clickLocationY;
    }
    
    public static void closeFrame()
	{
	 	 colorFrame.dispatchEvent(new WindowEvent(colorFrame, WindowEvent.WINDOW_CLOSING));
	}
}
