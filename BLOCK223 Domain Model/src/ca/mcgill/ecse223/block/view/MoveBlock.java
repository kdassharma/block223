package ca.mcgill.ecse223.block.view;

import ca.mcgill.ecse223.block.controller.Block223Controller;
import ca.mcgill.ecse223.block.controller.InvalidInputException;
import ca.mcgill.ecse223.block.controller.TOGridCell;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MoveBlock {

    static int oldXCoords, oldYCoords, oldX, oldY;
	// UI elements
 	private static JLabel errorMessage;
 	private static JLabel titleLabel = new JLabel();
 	// add block
 	private static JTextField xTextField;
 	private JLabel xLabel;
 	private static JTextField yTextField;
 	private JLabel yLabel;
 	private JButton moveBlockButton;
	private static int BlockSize = 25;
	private static int BlockVertical = 22;
	
 	// data elements
 	private static String error = null;

    public MoveBlock(JFrame jframe, int x, int y){
        run(jframe, x, y);
    }

    private void run(JFrame jFrame, int x, int y) {
    	
    	oldXCoords = getCoordsFromX(x);
    	oldYCoords = getCoordsFromY(y);
    	oldX = x;
    	oldY = y;
    	jFrame.getContentPane().removeAll();
        JFrame frame =jFrame;
         
        frame.setTitle("Block223 Game");
        
    	// elements for error message
 		errorMessage = new JLabel();
 		errorMessage.setForeground(Color.RED);
 		errorMessage.setFont(new Font("Courier", Font.BOLD,8));
    
    	// elements for add block
 		titleLabel.setText("Move Block");
 		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
    	// red
 		xTextField = new JTextField("");
 		xLabel = new JLabel();
 		xLabel.setText("X Coordinate: ");
 		xLabel.setHorizontalAlignment(SwingConstants.RIGHT);
 		// blue
 		yTextField = new JTextField("");
 		yLabel = new JLabel();
 		yLabel.setText("Y Coordinate: ");
 		yLabel.setHorizontalAlignment(SwingConstants.RIGHT);
 		
 		moveBlockButton = new JButton();
 		moveBlockButton.setText("Move Block");
 		
 		// action listener for button
 	 		moveBlockButton.addActionListener(new java.awt.event.ActionListener() {
 				public void actionPerformed(java.awt.event.ActionEvent evt) {
 					moveBlockActionPerformed(evt);
 				}
 			});
 	 	// layout
        titleLabel.setBounds(0, 8, 260, 25);
        titleLabel.setFont(new Font("Courier", Font.BOLD,15));
        titleLabel.setBackground(Color.GREEN);
        titleLabel.setOpaque(true);
        
 		xLabel.setBounds(0, 40, 160, 20);
 		xLabel.setBackground(Color.LIGHT_GRAY);
 		xLabel.setOpaque(true);
 		
 		xTextField.setBounds(180, 40, 50, 20);
 		
 		yLabel.setBounds(0, 70, 160, 20);
 		yLabel.setBackground(Color.LIGHT_GRAY);
 		yLabel.setOpaque(true);
 		
 		yTextField.setBounds(180, 70, 50, 20);
 		
 		moveBlockButton.setBounds(115, 100, 130, 25);
 		moveBlockButton.setSize(100, 40);
 		moveBlockButton.setBorder(BorderFactory.createLineBorder(Color.GREEN,3));
 		moveBlockButton.setOpaque(true);
 		
 		errorMessage.setText("");
 		errorMessage.setBounds(0, 145, 3000, 18);
 		errorMessage.setBackground(Color.WHITE);
 		errorMessage.setForeground(Color.RED);
 		errorMessage.setOpaque(true);

 		errorMessage.setVisible(false);
 		errorMessage.setFont(new Font("Courier", Font.BOLD,8));
        error = "";
        
        //Return button
   	 	JButton returnButton = new JButton();
        returnButton.setFont(new Font("Courier", Font.PLAIN,10));
		returnButton.setSize(50, 20);
		returnButton.setText("<--");
        returnButton.setLocation(45, 110);
        
        //return button to User screen
        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	frame.setVisible(false);
            }
        });
        
      //panel for easy sorting on the frame
        JPanel panel = new JPanel();
        panel.setBackground(Color.DARK_GRAY);
        panel.setSize(300, 200);
        panel.setLayout(null);
        panel.setLocation(0, 0);
        panel.setOpaque(true);
        panel.add(titleLabel);
        panel.add(xLabel);
        panel.add(xTextField);
        panel.add(yLabel);
        panel.add(yTextField);
        panel.add(errorMessage); 
        panel.add(moveBlockButton);
        panel.add(returnButton);

        panel.setVisible(true);
        
    	frame.add(panel);       
        frame.setLayout(null);
        frame.setVisible(true);
        frame.setResizable(false);

    }
    
    public void moveBlockActionPerformed(ActionEvent e) {
    	String xCoordText = xTextField.getText();
    	String yCoordText = yTextField.getText();
    	if(xCoordText.isEmpty()  || yCoordText.isEmpty()) {
    		 errorMessage.setText("Please enter coordinates");
    		 errorMessage.setVisible(true);
    	}
    	else {
	        int xCoord = Integer.parseInt(xCoordText);
	        int yCoord = Integer.parseInt(yCoordText);
	        int xPixel = BlockGeneration.getXFromCoords(xCoord);
	        int yPixel = BlockGeneration.getYFromCoords(yCoord);
	        
	        try {
	        	TOGridCell oldBlock = Block223Controller.getBlock(DetailsPanel.getCurrentLevel(), oldXCoords, oldYCoords);
	            Block223Controller.moveBlock(DetailsPanel.getCurrentLevel(), oldXCoords, oldYCoords, xCoord, yCoord);
	            Color colour = new Color(oldBlock.getRed(), oldBlock.getGreen(), oldBlock.getBlue());
	            BlockGeneration.updateBlock(xPixel, yPixel, colour, "", 10);
	            BlockGeneration.updateBlock(oldX, oldY, Color.BLACK, "+", 1);
	            errorMessage.setText("Block moved successfully!");
	            errorMessage.setBackground(null);
	            errorMessage.setForeground(Color.GREEN);
	            errorMessage.setVisible(true);
	            refreshData();
	        }catch(InvalidInputException exc){
	            errorMessage.setText(exc.getMessage());
	            errorMessage.setForeground(Color.RED);
	            errorMessage.setBackground(Color.WHITE);
	            errorMessage.setVisible(true);
	        }
    	}
    }
    
    public static int getCoordsFromX(int x) {
    	return (x-10)/BlockSize + 1;
    }
    
    public static int getCoordsFromY(int y) {
    	return (y-10)/BlockVertical + 1;
    }
    
    public static void refreshData() {
			xTextField.setText("");
			yTextField.setText("");
    }
    

}