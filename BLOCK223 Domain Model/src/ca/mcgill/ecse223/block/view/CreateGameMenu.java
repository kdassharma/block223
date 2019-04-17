package ca.mcgill.ecse223.block.view;

import javax.swing.*;

import ca.mcgill.ecse223.block.application.Block223Application;
import ca.mcgill.ecse223.block.controller.Block223Controller;
import ca.mcgill.ecse223.block.controller.InvalidInputException;
import ca.mcgill.ecse223.block.controller.TOGridCell;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.List;

public class CreateGameMenu {
	
	static JFrame frame;
	static GraphicsConfiguration gc;

    public CreateGameMenu(JFrame jFrame){
        run(jFrame);
    }

    private static void run(JFrame jFrame) {
    	
    	frame = jFrame;
    	AdminUi.closeError();
    	
        frame.setTitle("Block223 Game");
        
		//turning off welcome message
		AdminUi.turnOffWelcomeCreate();
		
        JLabel titleLabel = new JLabel();
        titleLabel.setText("New Game Creator");
        titleLabel.setBounds(0, 20, 600, 60);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setFont(new Font("Courier", Font.BOLD, 40));
        titleLabel.setBackground(Color.GREEN);
        titleLabel.setOpaque(true);
        
        JLabel nameLabel = new JLabel();
        nameLabel.setText("Game Name: ");
        nameLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        nameLabel.setBounds(0, 104, 370, 18);
        nameLabel.setBackground(Color.LIGHT_GRAY);
        nameLabel.setOpaque(true);
        
        JLabel numLevelsLabel = new JLabel();
        numLevelsLabel.setText("Number of Levels: ");
        numLevelsLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        numLevelsLabel.setBounds(0, 129, 370, 18);
        numLevelsLabel.setBackground(Color.LIGHT_GRAY);
        numLevelsLabel.setOpaque(true);
        
        JLabel numBlocksLevelLabel = new JLabel();
        numBlocksLevelLabel.setText("Number of Blocks/Level: ");
        numBlocksLevelLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        numBlocksLevelLabel.setBounds(0, 154, 370, 18);
        numBlocksLevelLabel.setBackground(Color.LIGHT_GRAY);
        numBlocksLevelLabel.setOpaque(true);
        
        JLabel minBallXLabel = new JLabel();
        minBallXLabel.setText("Minimum Speed of Ball Horizontal: ");
        minBallXLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        minBallXLabel.setBounds(0, 179, 370, 18);
        minBallXLabel.setBackground(Color.LIGHT_GRAY);
        minBallXLabel.setOpaque(true);
        
        JLabel minBallYLabel = new JLabel();
        minBallYLabel.setText("Minimum Speed of Ball Vertical: ");
        minBallYLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        minBallYLabel.setBounds(0, 204, 370, 18);
        minBallYLabel.setBackground(Color.LIGHT_GRAY);
        minBallYLabel.setOpaque(true);
        
        JLabel ballIncreaseFactorLabel = new JLabel();
        ballIncreaseFactorLabel.setText("Ball Increase Factor: ");
        ballIncreaseFactorLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        ballIncreaseFactorLabel.setBounds(0, 229, 370, 18); 
        ballIncreaseFactorLabel.setBackground(Color.LIGHT_GRAY);
        ballIncreaseFactorLabel.setOpaque(true);
        
        JLabel maxPaddleLengthLabel = new JLabel();
        maxPaddleLengthLabel.setText("Maximum Length of the Paddle: ");
        maxPaddleLengthLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        maxPaddleLengthLabel.setBounds(0, 254, 370, 18); 
        maxPaddleLengthLabel.setBackground(Color.LIGHT_GRAY);
        maxPaddleLengthLabel.setOpaque(true);
        
        JLabel minPaddleLengthLabel = new JLabel();
        minPaddleLengthLabel.setText("Minimum Length of the Paddle: ");
        minPaddleLengthLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        minPaddleLengthLabel.setBounds(0, 279, 370, 18); 
        minPaddleLengthLabel.setBackground(Color.LIGHT_GRAY);
        minPaddleLengthLabel.setOpaque(true);

        JTextField nameInput = new JTextField();
        nameInput.setText("");
        nameInput.setBounds(400, 100, 130, 25);
        
        JTextField numLevelInput = new JTextField();
        numLevelInput.setText("");
        numLevelInput.setBounds(400, 125, 130, 25);
        
        JTextField numBlocksLevelInput = new JTextField();
        numBlocksLevelInput.setText("");
        numBlocksLevelInput.setBounds(400, 150, 130, 25);
        
        JTextField minBallXInput = new JTextField();
        minBallXInput.setText("");
        minBallXInput.setBounds(400, 175, 130, 25);
        
        JTextField minBallYInput = new JTextField();
        minBallYInput.setText("");
        minBallYInput.setBounds(400, 200, 130, 25);
        
        JTextField ballIncreaseInput = new JTextField();
        ballIncreaseInput.setText("");
        ballIncreaseInput.setBounds(400, 225, 130, 25);
        
        JTextField maxPaddleInput = new JTextField();
        maxPaddleInput.setText("");
        maxPaddleInput.setBounds(400, 250, 130, 25);
        
        JTextField minPaddleInput = new JTextField();
        minPaddleInput.setText("");
        minPaddleInput.setBounds(400, 275, 130, 25);
        
        JButton confirmButton = new JButton("Confirm!");
        confirmButton.setBounds(400, 308, 130, 60);
        confirmButton.setBorder(BorderFactory.createLineBorder(Color.GREEN, 4));
        confirmButton.setOpaque(true);

        JLabel errorMessage = new JLabel();
        errorMessage.setText("");
        errorMessage.setBounds(0, 330, 400, 20);
        errorMessage.setVisible(false);
        errorMessage.setBackground(Color.WHITE);
        errorMessage.setOpaque(true);
        errorMessage.setForeground(Color.RED);

		//return button
		JButton returnButton = new JButton();
        returnButton.setFont(new Font("Courier", Font.PLAIN,10));
		returnButton.setSize(50, 20);
		returnButton.setText("<--");
        returnButton.setLocation(322,325);
        
        //return button to User screen
        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	closeFrame();
            }
        });
        
        JPanel panel = new JPanel();
        panel.setBackground(Color.DARK_GRAY);
        panel.setSize(600, 400);
        panel.setLayout(null);
        panel.setLocation(0, 0);
        panel.setOpaque(true);
        
        panel.add(titleLabel);
        panel.add(nameLabel);
        
        panel.add(numLevelsLabel);
        panel.add(numBlocksLevelLabel);
        panel.add(minBallXLabel);
        panel.add(minBallYLabel);
        panel.add(ballIncreaseFactorLabel);
        panel.add(errorMessage); 
        panel.add(maxPaddleLengthLabel);
        panel.add(minPaddleLengthLabel);
        
        panel.add(nameInput);
        panel.add(numLevelInput);
        panel.add(numBlocksLevelInput);
        panel.add(minBallXInput);
        panel.add(minBallYInput);
        panel.add(ballIncreaseInput);
        panel.add(maxPaddleInput);
        panel.add(minPaddleInput);
        panel.add(returnButton);
        panel.add(confirmButton);
        panel.add(errorMessage);
        
        frame.add(panel);
        
        frame.setSize(600, 400);
        frame.setLocation(AdminUi.getCenterWidth() - 300, AdminUi.getCenterHeight() - 200);
        frame.setLayout(null);
        frame.setVisible(true);
        frame.setResizable(false);

        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameInput.getText();
            	if(numLevelInput.getText().isEmpty() || numBlocksLevelInput.getText().isEmpty() 
            			|| minBallXInput.getText().isEmpty() || minBallYInput.getText().isEmpty() || ballIncreaseInput.getText().isEmpty()
            			|| maxPaddleInput.getText().isEmpty() || minPaddleInput.getText().isEmpty()) {
              		 errorMessage.setText("Please enter values");
              		 errorMessage.setVisible(true);
               	}
            	else {
                try{
                	int numLevel = Integer.parseInt(numLevelInput.getText());
                	int numBlocksLevel = Integer.parseInt(numBlocksLevelInput.getText());
                    int minBallX = Integer.parseInt(minBallXInput.getText());
                    int minBallY = Integer.parseInt(minBallYInput.getText());
                    double ballIncrease = Double.parseDouble(ballIncreaseInput.getText());
                    int maxPaddle = Integer.parseInt(maxPaddleInput.getText());
                    int minPaddle =Integer.parseInt(minPaddleInput.getText());
                    Block223Application.setCurrentGame(null);
                    Block223Controller.createGame(name);
                    Block223Controller.selectGame(name);
                    Block223Controller.setGameDetails(numLevel, numBlocksLevel, minBallX, minBallY, ballIncrease, maxPaddle, minPaddle);
                    
                    errorMessage.setText("Game Created Successfully!");
                    errorMessage.setVisible(true);
                    frame.setVisible(false);
                    DetailsPanel.setCurrentGameName(name);
                    DetailsPanel.setCurrentLevel(1);
                    DetailsPanel.setNumBlocks(0);
                    DetailsPanel.setNumBlocksLeft(numBlocksLevel);
                    RefreshData.refreshData("game");
                    List<TOGridCell> blocks = Block223Controller.getBlocksAtLevelOfCurrentDesignableGame(1);
                    BlockGeneration.regenerateBlocks(blocks);
                    RefreshData.refreshData("panel");
                } 
                
                catch (NumberFormatException ex) {
                	errorMessage.setVisible(true);
                	errorMessage.setText("Could not format a number!");
                    
                }catch(InvalidInputException exc){
                	errorMessage.setVisible(true);
                    errorMessage.setText(exc.getMessage());
                }
                

            	}
            }
        });
    }
    public static void closeFrame()
	{
		frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
	}
}
