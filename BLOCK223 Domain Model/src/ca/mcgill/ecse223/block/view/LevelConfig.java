package ca.mcgill.ecse223.block.view;

import ca.mcgill.ecse223.block.controller.Block223Controller;
import ca.mcgill.ecse223.block.controller.InvalidInputException;
import ca.mcgill.ecse223.block.controller.TOGame;
import ca.mcgill.ecse223.block.controller.TOGridCell;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.List;


public class LevelConfig extends JFrame{
	
	// UI elements
	private static JLabel errorMessage;
	private JLabel titleLabel = new JLabel();
	
	//load block
	private JLabel loadLevel;
	private static JTextField loadLevelField = new JTextField("");
	private JButton loadLevelBtn;
	
	// data elements
	private String error = null;
	private static List<TOGridCell> levelBlocks;
	// toggle sick status

    public LevelConfig(JFrame jFrame){
        run(jFrame);
    }
    

    private void initComponents() {
        
        // elements for error message
 		errorMessage = new JLabel();
    
    	// elements for add block
 		titleLabel.setText("Level Check");
 		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
    	//Load the level
 		loadLevelField = new JTextField();
 		
 		loadLevel = new JLabel();
 		loadLevel.setText("Load Level: ");
 		loadLevel.setHorizontalAlignment(SwingConstants.RIGHT);
 		
 		loadLevelBtn = new JButton("Load");
 		
 		errorMessage.setText("");
 		errorMessage.setBounds(0, 100, 3000, 18);
 		
 		errorMessage.setForeground(Color.RED);
 		errorMessage.setOpaque(true);
 		errorMessage.setVisible(false);
 		
 		loadLevelBtn.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				//loadingLevelListener(evt);
				if(loadLevelField.getText().isEmpty()) {
					errorMessage.setText("Please enter a level number");
					errorMessage.setVisible(true);
				}
				else {
					try {
						TOGame cGame = Block223Controller.getCurrentDesignableGame();
						int levelNumber = (Integer.parseInt(loadLevelField.getText()));
						
						if ((levelNumber > cGame.getNrLevels()) || (levelNumber < 1) ){
							throw new InvalidInputException("Not a level in this game!");
						}
						
						java.util.List<TOGridCell> blocksForCurrentLevel = Block223Controller.getBlocksAtLevelOfCurrentDesignableGame(levelNumber);
						BlockGeneration.regenerateBlocks(blocksForCurrentLevel);
						
						DetailsPanel.setCurrentLevel(levelNumber);
						DetailsPanel.setNumBlocks(blocksForCurrentLevel.size());
						DetailsPanel.setNumBlocksLeft(cGame.getNrBlocksPerLevel() - blocksForCurrentLevel.size());
						
						errorMessage.setText("Level loaded");
				        errorMessage.setBounds(0, 100, 1500, 18);
				        errorMessage.setVisible(true);
				        
				        errorMessage.setOpaque(false);
				        errorMessage.setForeground(Color.GREEN);
						
						RefreshData.refreshData("level");
						RefreshData.refreshData("panel");
						
					} catch (NumberFormatException e) {
						
						
						errorMessage.setText("Not a number");
				        errorMessage.setBounds(0, 100, 1500, 18);
				        errorMessage.setVisible(true);
				        errorMessage.setBackground(Color.WHITE);
				        errorMessage.setOpaque(true);
				        errorMessage.setForeground(Color.RED);
					
					}catch (InvalidInputException e) {
	
				        errorMessage.setText(e.getMessage());
				        errorMessage.setBounds(0, 100, 1500, 18);
				        errorMessage.setVisible(true);
				        errorMessage.setBackground(Color.WHITE);
				        errorMessage.setOpaque(true);
				        errorMessage.setForeground(Color.RED);
				        
					}
				}

			}
		});
         
 		// layout
        titleLabel.setBounds(0, 8, 250, 25);
        titleLabel.setFont(new Font("Courier", Font.BOLD,15));
        titleLabel.setBackground(Color.GREEN);
        titleLabel.setOpaque(true);
        
        //loading objects
        loadLevel.setBackground(Color.LIGHT_GRAY);
        loadLevel.setOpaque(true);
        loadLevel.setBounds(0, 50, 120, 20);
        loadLevel.setVisible(true);
        
        loadLevelField.setBounds(130, 50, 50, 20);
        loadLevelField.setOpaque(true);
        loadLevelField.setVisible(true);
        
        loadLevelBtn.setBounds(190, 50, 50, 20);
        loadLevelBtn.setOpaque(true);
        loadLevelBtn.setVisible(true);
        
    }
    
    private void run(JFrame jFrame) {
    	
    	AdminUi.closeError();
    	jFrame.getContentPane().removeAll();
        JFrame frame =jFrame;
         
//    	JFrame frame = new JFrame("Block223 Create Block!");
        frame.setTitle("Block223 Game");
        
    	initComponents();

    	//Return button
    	 JButton returnButton = new JButton();
         returnButton.setFont(new Font("Courier", Font.PLAIN,10));
 		 returnButton.setSize(50, 20);
 		 returnButton.setText("<--");
         returnButton.setLocation(45, 75);
         
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
        panel.setSize(250, 150);
        panel.setLayout(null);
        panel.setLocation(0, 0);
        panel.setOpaque(true);
        panel.add(titleLabel);
        panel.add(loadLevel);
        panel.add(loadLevelField);
        panel.add(loadLevelBtn);
        panel.add(errorMessage); 
       
        
        panel.add(returnButton);

        panel.setVisible(true);
        
    	frame.add(panel);       
        frame.setLayout(null);
        frame.setVisible(true);
        frame.setResizable(false);

    }
    
	 public static void refreshData() {
		 loadLevelField.setText("");
	}
    

    public static List<TOGridCell> getLevelBlocks(){
    	return levelBlocks;
    }
}


