package ca.mcgill.ecse223.block.view;

import ca.mcgill.ecse223.block.controller.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.HashMap;
import java.util.List;

public class DeleteBlock extends JFrame{
	
	
	// UI elements
	private static JLabel errorMessage;
	private static JLabel titleLabel = new JLabel();
	private static JComboBox<String> blockToggleList = new JComboBox();
	private static JLabel blockToggleLabel;
	private static JButton deleteBlockButton;
	private static JFrame frame;
	// data elements
	private static String error = null;
	// toggle sick status
	private static HashMap<Integer, Integer> blocks;

	 public DeleteBlock(JFrame jframe){
	        run(jframe);
	 }
	 
	 private void initComponents() {
//    	JFrame frame = new JFrame("Block223 Delete Block");
//        frame.setTitle("Block223 Game");
        
        // elements for error message
  		errorMessage = new JLabel();
  		errorMessage.setText("");
  		errorMessage.setForeground(Color.RED);
  		errorMessage.setBackground(Color.WHITE);
  		errorMessage.setForeground(Color.RED);
  		errorMessage.setOpaque(true);
  		errorMessage.setVisible(false);
  		errorMessage.setBounds(0, 150, 1000, 15);
  		errorMessage.setFont(new Font("Courier", Font.PLAIN,8));
  		
  		titleLabel.setText("Delete a block");
  		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // elements for delete block
 		blockToggleList = new JComboBox<String>(new String[0]);
 		blockToggleLabel = new JLabel();
 		blockToggleLabel.setText("Select Block:");
 		blockToggleLabel.setHorizontalAlignment(SwingConstants.RIGHT);
 		deleteBlockButton = new JButton();
 		deleteBlockButton.setText("Delete Block");
 		
 		// action listener
		deleteBlockButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				deleteBlockButtonActionPerformed(evt);
			}
		});
		
		titleLabel.setBounds(0, 10, 250, 20);
        titleLabel.setFont(new Font("Courier", Font.BOLD, 20));
        titleLabel.setBackground(Color.GREEN);
        titleLabel.setOpaque(true);
        
        blockToggleLabel.setBounds(0, 60, 140, 20);
        blockToggleLabel.setBackground(Color.LIGHT_GRAY);
        blockToggleLabel.setOpaque(true);
        
        blockToggleList.setBounds(155, 60, 80, 20);
        
        deleteBlockButton.setBounds(120, 100, 100, 40);
        deleteBlockButton.setBorder(BorderFactory.createLineBorder(Color.GREEN, 3));
        deleteBlockButton.setOpaque(true); 
	 }
	 
	 private void run(JFrame jFrame) {
	        
		jFrame.getContentPane().removeAll();
	    frame =jFrame;
	    
		initComponents();
		 
		//return button
		JButton returnButton = new JButton();
        returnButton.setFont(new Font("Courier", Font.PLAIN,10));
		returnButton.setSize(50, 20);
		returnButton.setText("<--");
        returnButton.setLocation(50,110);
        
        //return button to User screen
        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	closeFrame();
            }
        });
        
        refreshData();
        
        //panel for easy sorting on the frame
        JPanel panel = new JPanel();
        panel.setBackground(Color.DARK_GRAY);
        panel.setSize(250, 200);
        panel.setLayout(null);
        panel.setLocation(0, 0);
        panel.setOpaque(true);
        panel.add(titleLabel);
        panel.add(deleteBlockButton);
        panel.add(blockToggleLabel);
        panel.add(blockToggleList);
        panel.add(errorMessage);       
        panel.add(returnButton);

        panel.setVisible(true);

        frame.add(panel);
        frame.setLayout(null);
        frame.setResizable(false);
	 }
	 
	// for refreshing the list of available blocks (put in main UI?)
	 public static void refreshData(){
	    	
	    //	errorMessage.setText(error);

			//if (error == null || error.length() == 0) {
				// populate popup with data
		
				blocks = new HashMap<Integer, Integer>();
				blockToggleList.removeAllItems();
				Integer index = 0;
				try {
					java.util.List<TOBlock> blockList = Block223Controller.getBlocksOfCurrentDesignableGame();
					if(blockList.size() > 0) {
						for (TOBlock block : blockList) {
							blocks.put(index, block.getId());
							blockToggleList.addItem("Colour: (" + block.getRed() + ", " + 
									block.getGreen() + ", "+ block.getBlue() + ") Points: " + block.getPoints());
							index++;
						}
					}
				} catch (InvalidInputException e) {
					error = e.getMessage();
					errorMessage.setText(error);
					errorMessage.setVisible(true);
				};
				blockToggleList.setSelectedIndex(-1);	
			//}
	    }
	 
	 private void deleteBlockButtonActionPerformed(java.awt.event.ActionEvent evt){
			// clear error message and basic input validation
			error = "";
			int selectedBlock = blockToggleList.getSelectedIndex();
			if (selectedBlock < 0)
			{
				error = "Block needs to be selected for deletion!";
				errorMessage.setVisible(true);
	 		}
			
			else if (error.length() == 0) {
				// call the controller
				try {
					Block223Controller.deleteBlock(blocks.get(selectedBlock));
					errorMessage.setForeground(Color.GREEN);
					errorMessage.setBackground(Color.DARK_GRAY);
					errorMessage.setText("Block Deleted Successfully!");
		            errorMessage.setVisible(true);
		            
		            TOGame game = Block223Controller.getCurrentDesignableGame();
					List<TOGridCell> blocks = Block223Controller.getBlocksAtLevelOfCurrentDesignableGame(DetailsPanel.getCurrentLevel());
					int numLeft = game.getNrBlocksPerLevel() - blocks.size();
					DetailsPanel.setNumBlocks(blocks.size());
					DetailsPanel.setNumBlocksLeft(numLeft);
					BlockGeneration.regenerateBlocks(blocks);
					
					RefreshData.refreshData("block");
				} catch (InvalidInputException e) {
					errorMessage.setVisible(true);
					error = e.getMessage();
					errorMessage.setText(error);
				}
			}
		}
	 public static void closeFrame()
	 {
	 	 frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
	 }
}
