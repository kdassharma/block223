package ca.mcgill.ecse223.block.view;

import ca.mcgill.ecse223.block.controller.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;

public class UpdateBlock extends JFrame{
	
	// UI elements
	private static JLabel errorMessage;
	private JLabel titleLabel = new JLabel();
	// add block
	private static JTextField redTextField = new JTextField("");
	private JLabel redLabel;
	private static JTextField blueTextField = new JTextField("");
	private JLabel blueLabel;
	private static JTextField greenTextField = new JTextField("");
	private JLabel greenLabel;
	private static JTextField pointsTextField = new JTextField("");
	private JLabel pointsLabel;
	private JButton updateBlockButton;
	private static JComboBox<String> blockToggleList = new JComboBox();
	private static JLabel blockToggleLabel;
	
	// data elements
	private static String error = null;
	// toggle sick status
	private static HashMap<Integer, Integer> blocks;

    public UpdateBlock(JFrame jFrame){
        run(jFrame);
    }

    private void initComponents() {
        
        // elements for error message
 		errorMessage = new JLabel();
 		errorMessage.setForeground(Color.RED);
 		errorMessage.setFont(new Font("Courier", Font.BOLD,8));
    
    	// elements for add block
 		titleLabel.setText("Update Block Type");
 		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
    	// red
 		redTextField = new JTextField();
 		redLabel = new JLabel();
 		redLabel.setText("Red Component: ");
 		redLabel.setHorizontalAlignment(SwingConstants.RIGHT);
 		// blue
 		blueTextField = new JTextField();
 		blueLabel = new JLabel();
 		blueLabel.setText("Blue Component: ");
 		blueLabel.setHorizontalAlignment(SwingConstants.RIGHT);
 		// green
 		greenTextField = new JTextField();
 		greenLabel = new JLabel();
 		greenLabel.setText("Green Component: ");
 		greenLabel.setHorizontalAlignment(SwingConstants.RIGHT);
 		// points
 		pointsTextField = new JTextField();
 		pointsLabel = new JLabel();
 		pointsLabel.setText("Number of Points: ");
 		pointsLabel.setHorizontalAlignment(SwingConstants.RIGHT);
 		// button
 		updateBlockButton = new JButton();
 		updateBlockButton.setText("Update Block");
 		
 		blockToggleList = new JComboBox<String>(new String[0]);
 		blockToggleLabel = new JLabel();
 		blockToggleLabel.setText("Select Block:");
 		blockToggleLabel.setHorizontalAlignment(SwingConstants.RIGHT);
 		
 		// action listener for button
 		updateBlockButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				updateBlockButtonActionPerformed(evt);
			}
		});
         
 		// layout
        titleLabel.setBounds(0, 8, 250, 25);
        titleLabel.setFont(new Font("Courier", Font.BOLD,15));
        titleLabel.setBackground(Color.GREEN);
        titleLabel.setOpaque(true);
        updateBlockButton.setBounds(115, 165, 130, 25);
		//blockToggleLabel.setBounds(0, 60, 140, 20);
        blockToggleLabel.setBounds(0, 50, 140, 20);
        blockToggleLabel.setBackground(Color.LIGHT_GRAY);
        blockToggleLabel.setOpaque(true);
        
        blockToggleList.setBounds(155, 50, 80, 20);
         
 		redLabel.setBounds(0, 80, 160, 20);
 		redLabel.setBackground(Color.LIGHT_GRAY);
 		redLabel.setOpaque(true);
 		
 		redTextField.setBounds(180, 80, 50, 20);
 		
 		blueLabel.setBounds(0, 110, 160, 20);
 		blueLabel.setBackground(Color.LIGHT_GRAY);
 		blueLabel.setOpaque(true);
 		
 		blueTextField.setBounds(180, 110, 50, 20);
 		
 		greenLabel.setBounds(0, 140, 160, 20);
 		greenLabel.setBackground(Color.LIGHT_GRAY);
 		greenLabel.setOpaque(true);
 		
 		greenTextField.setBounds(180, 140, 50, 20);
 		
 		pointsLabel.setBounds(0, 170, 160, 20);
 		pointsLabel.setBackground(Color.LIGHT_GRAY);
 		pointsLabel.setOpaque(true);
 		
 		pointsTextField.setBounds(180, 170, 50, 20);
 		
 		updateBlockButton.setBounds(115, 195, 130, 25);
 		updateBlockButton.setSize(100, 40);
 		updateBlockButton.setBackground(Color.GREEN);
 		updateBlockButton.setOpaque(true);
 		
 		errorMessage.setText("");
 		errorMessage.setBounds(0, 250, 3000, 18);
 		errorMessage.setBackground(Color.WHITE);
 		errorMessage.setForeground(Color.RED);
 		errorMessage.setOpaque(true);

 		errorMessage.setVisible(false);
 		errorMessage.setFont(new Font("Courier", Font.BOLD,8));
        error = "";
 		
    }
    
    private void run(JFrame jFrame) {
    	
    	jFrame.getContentPane().removeAll();
        JFrame frame =jFrame;
         
        frame.setTitle("Block223 Game");
        
    	initComponents();

    	//Return button
    	 JButton returnButton = new JButton();
         returnButton.setFont(new Font("Courier", Font.PLAIN,10));
 		 returnButton.setSize(50, 20);
 		 returnButton.setText("<--");
         returnButton.setLocation(45, 200);
         
         //return button to User screen
         returnButton.addActionListener(new ActionListener() {
             @Override
             public void actionPerformed(ActionEvent e) {
             	frame.setVisible(false);
             }
         });
         
        refreshData();
         
    	//panel for easy sorting on the frame
        JPanel panel = new JPanel();
        panel.setBackground(Color.DARK_GRAY);
        panel.setSize(250, 300);
        panel.setLayout(null);
        panel.setLocation(0, 0);
        panel.setOpaque(true);
        panel.add(titleLabel);
        panel.add(redLabel);
        panel.add(redTextField);
        panel.add(blueLabel);
        panel.add(blueTextField);
        panel.add(greenTextField);
        panel.add(greenLabel);
        panel.add(errorMessage); 
        panel.add(pointsLabel);
        panel.add(pointsTextField);
        panel.add(updateBlockButton);
        panel.add(blockToggleLabel);
        panel.add(blockToggleList);
        panel.add(returnButton);

        panel.setVisible(true);
        
    	frame.add(panel);       
        frame.setLayout(null);
        frame.setVisible(true);
        frame.setResizable(false);
    }
    
	// for refreshing the list of available blocks (put in main UI?)
    public static void refreshData() {

		//errorMessage.setText(error);

	//	if (error == null || error.length() == 0) {
			// populate popup with data
			redTextField.setText("");
			blueTextField.setText("");
			greenTextField.setText("");
			pointsTextField.setText("");
			
			blocks = new HashMap<Integer, Integer>();
			blockToggleList.removeAllItems();
			Integer index = 0;
			try {
				for (TOBlock block : Block223Controller.getBlocksOfCurrentDesignableGame()) {
					blocks.put(index, block.getId());
					blockToggleList.addItem("Colour: (" + block.getRed() + ", " + 
							block.getGreen() + ", "+ block.getBlue() + ") Points: " + block.getPoints());
					index++;
				}
			} catch (InvalidInputException e) {
				error = e.getMessage();
				errorMessage.setText(error);
				errorMessage.setVisible(true);
			};
			blockToggleList.setSelectedIndex(-1);	
		//}
    }
    
    private void updateBlockButtonActionPerformed(java.awt.event.ActionEvent evt) {
		// clear error message
		error = "";
		// get inputs
		int selectedBlock = blockToggleList.getSelectedIndex();
		if (selectedBlock < 0)
		{
			error = "Block needs to be selected for update!";
			errorMessage.setText(error);
			errorMessage.setVisible(true);
		}
		// call the controller
		else if(redTextField.getText().isEmpty()  || blueTextField.getText().isEmpty() || greenTextField.getText().isEmpty() || pointsTextField.getText().isEmpty()) {
				error = "Please enter values";
				errorMessage.setText(error);
				errorMessage.setVisible(true);
       	}
		else if (error.length() == 0) {
			int red = Integer.parseInt(redTextField.getText());
			int blue = Integer.parseInt(blueTextField.getText());
			int green = Integer.parseInt(greenTextField.getText());
			int points = Integer.parseInt(pointsTextField.getText());
			try {
				Block223Controller.updateBlock(blocks.get(selectedBlock), red, green, blue, points);
				errorMessage.setForeground(Color.GREEN);
				errorMessage.setBackground(Color.DARK_GRAY);
				errorMessage.setText("Block Updated Successfully!");
	            errorMessage.setVisible(true);
	            
	            TOGame game = Block223Controller.getCurrentDesignableGame();
				List<TOGridCell> blocks = Block223Controller.getBlocksAtLevelOfCurrentDesignableGame(DetailsPanel.getCurrentLevel());
				int numLeft = game.getNrBlocksPerLevel() - blocks.size();
				DetailsPanel.setNumBlocks(blocks.size());
				DetailsPanel.setNumBlocksLeft(numLeft);
				BlockGeneration.regenerateBlocks(blocks);
				
	    		RefreshData.refreshData("block");
			} catch (InvalidInputException e) {
				error = e.getMessage();
				errorMessage.setText(error);
				errorMessage.setVisible(true);
			}
		}
		// update visuals
	}
}


