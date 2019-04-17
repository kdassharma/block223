package ca.mcgill.ecse223.block.view;

import ca.mcgill.ecse223.block.controller.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CreateBlock extends JFrame{
	
	// UI elements
	private static JLabel errorMessage;
	private JLabel titleLabel = new JLabel();
	// add block
	private static JTextField redTextField;
	private JLabel redLabel;
	private static JTextField blueTextField;
	private JLabel blueLabel;
	private static JTextField greenTextField;
	private JLabel greenLabel;
	private static JTextField pointsTextField;
	private JLabel pointsLabel;
	private JButton createBlockButton;
	
	// data elements
	private static String error = null;

    public CreateBlock(JFrame jFrame){
        run(jFrame);
    }

    private void initComponents() {
        
        // elements for error message
 		errorMessage = new JLabel();
 		errorMessage.setForeground(Color.RED);
 		errorMessage.setFont(new Font("Courier", Font.BOLD,8));
    
    	// elements for add block
 		titleLabel.setText("Add Block Type");
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
 		createBlockButton = new JButton();
 		createBlockButton.setText("Create Block");
 		
 		// action listener for button
 		createBlockButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				createBlockButtonActionPerformed(evt);
			}
		});
         
 		// layout
        titleLabel.setBounds(0, 8, 250, 25);
        titleLabel.setFont(new Font("Courier", Font.BOLD,15));
        titleLabel.setBackground(Color.GREEN);
        titleLabel.setOpaque(true);
        
 		redLabel.setBounds(0, 40, 160, 20);
 		redLabel.setBackground(Color.LIGHT_GRAY);
 		redLabel.setOpaque(true);
 		
 		redTextField.setBounds(180, 40, 50, 20);
 		
 		blueLabel.setBounds(0, 70, 160, 20);
 		blueLabel.setBackground(Color.LIGHT_GRAY);
 		blueLabel.setOpaque(true);
 		
 		blueTextField.setBounds(180, 70, 50, 20);
 		
 		greenLabel.setBounds(0, 100, 160, 20);
 		greenLabel.setBackground(Color.LIGHT_GRAY);
 		greenLabel.setOpaque(true);
 		
 		greenTextField.setBounds(180, 100, 50, 20);
 		
 		pointsLabel.setBounds(0, 130, 160, 20);
 		pointsLabel.setBackground(Color.LIGHT_GRAY);
 		pointsLabel.setOpaque(true);
 		
 		pointsTextField.setBounds(180, 130, 50, 20);
 		
 		createBlockButton.setBounds(115, 165, 130, 25);
 		createBlockButton.setSize(100, 40);
 		createBlockButton.setBorder(BorderFactory.createLineBorder(Color.GREEN, 4));
 		createBlockButton.setOpaque(true);
 		
 		errorMessage.setText("");
 		errorMessage.setBounds(0, 220, 3000, 18);
 		errorMessage.setBackground(Color.WHITE);
 		errorMessage.setForeground(Color.RED);
 		errorMessage.setOpaque(true);

 		errorMessage.setVisible(false);
 		errorMessage.setFont(new Font("Courier", Font.BOLD,8));
        error = "";
 		
    }
    
    private void run(JFrame jFrame) {
    	
    	AdminUi.closeError();
    	jFrame.getContentPane().removeAll();
        JFrame frame =jFrame;
         
        frame.setTitle("Block223 Game");
        
    	initComponents();

    	//Return button
    	 JButton returnButton = new JButton();
         returnButton.setFont(new Font("Courier", Font.PLAIN,10));
 		 returnButton.setSize(50, 20);
 		 returnButton.setText("<--");
         returnButton.setLocation(45, 175);
         
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
        panel.setSize(250, 280);
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
        panel.add(createBlockButton);
        panel.add(returnButton);

        panel.setVisible(true);
        
    	frame.add(panel);       
        frame.setLayout(null);
        frame.setVisible(true);
        frame.setResizable(false);
    }
    
	// for refreshing the list of available blocks (put in main UI?)
    public static void refreshData() {
			redTextField.setText("");
			blueTextField.setText("");
			greenTextField.setText("");
			pointsTextField.setText("");
    }
    
    private void createBlockButtonActionPerformed(java.awt.event.ActionEvent evt) {
		// clear error message
		error = null;
		// get inputs
    	if(redTextField.getText().isEmpty()  || blueTextField.getText().isEmpty() || greenTextField.getText().isEmpty() || pointsTextField.getText().isEmpty()) {
   		 errorMessage.setText("Please enter values");
   		 errorMessage.setVisible(true);
    	}
    	
    	else {
			int red = Integer.parseInt(redTextField.getText());
			int blue = Integer.parseInt(blueTextField.getText());
			int green = Integer.parseInt(greenTextField.getText());
			int points = Integer.parseInt(pointsTextField.getText());
			// call the controller
			try {
				Block223Controller.addBlock(red, green, blue, points);
				
				errorMessage.setBackground(null);
				errorMessage.setForeground(Color.GREEN);
				errorMessage.setText("Block Created Successfully!");
	            errorMessage.setVisible(true);
	    		RefreshData.refreshData("block");
	    		refreshData();
	            
			} catch (InvalidInputException e) {
				error = e.getMessage();
				errorMessage.setBackground(Color.WHITE);
				errorMessage.setForeground(Color.RED);
				errorMessage.setText(error);
				errorMessage.setVisible(true);
			}
			// update visuals
		}
    }
}


