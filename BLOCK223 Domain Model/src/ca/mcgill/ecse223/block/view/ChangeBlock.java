package ca.mcgill.ecse223.block.view;

import ca.mcgill.ecse223.block.controller.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import ca.mcgill.ecse223.block.controller.Block223Controller;
import ca.mcgill.ecse223.block.controller.InvalidInputException;
import ca.mcgill.ecse223.block.controller.TOBlock;

public class ChangeBlock extends JFrame{
	// UI elements
	private static JLabel errorMessage = new JLabel("");
	private static JLabel titleLabel = new JLabel();
	private static JButton changeBlockButton;
	private static JComboBox<String> gameNameToggleList = new JComboBox();
	private static String error = null;
	private static HashMap<Integer, Integer> blocks;
	private static TOBlock blockType;
	private JButton button;
	
	private static int BlockSize = 25;
	private static int BlockVertical = 22;
	
	private static JFrame frame;
	
    public ChangeBlock(JFrame jFrame, JButton button){
        run(jFrame, button);
    }

    private void initComponents() {
        
        // elements for error message

 		errorMessage = new JLabel();
 		errorMessage.setForeground(Color.RED);
 		errorMessage.setText("");
 		errorMessage.setBounds(0, 80, 3000, 18);
 		errorMessage.setBackground(Color.WHITE);
 		errorMessage.setOpaque(true);
 		errorMessage.setVisible(false);
 		errorMessage.setFont(new Font("Courier", Font.PLAIN,7));
    
    	// elements for add block
 		titleLabel.setText("Change existing block");
 		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

 		titleLabel.setBounds(0, 8, 260, 25);

        titleLabel.setFont(new Font("Courier", Font.BOLD,15));

        titleLabel.setBackground(Color.GREEN);
        titleLabel.setOpaque(true);
        
 		// button
 		changeBlockButton = new JButton();
 		changeBlockButton.setText("Change");
 		changeBlockButton.setBounds(160, 45, 90, 30);
 		
 		gameNameToggleList = new JComboBox<String>(new String[0]);
		gameNameToggleList.setVisible(true);
		gameNameToggleList.setBounds(70, 45, 80, 30);
 		
    }
    
    private void run(JFrame jFrame, JButton button) {
    	
    	jFrame.getContentPane().removeAll();
        frame =jFrame;
        this.button = button;
         
//    	JFrame frame = new JFrame("Block223 Create Block!");
        frame.setTitle("Block223 Game");
        
    	initComponents();

    	//Return button
    	 JButton returnButton = new JButton();
         returnButton.setFont(new Font("Courier", Font.PLAIN,10));
 		 returnButton.setSize(50, 30);
 		 returnButton.setText("<--");
         returnButton.setLocation(10, 45);
         
         //return button to User screen
         returnButton.addActionListener(new ActionListener() {
             @Override
             public void actionPerformed(ActionEvent e) {
             	 ViewManager.runView("BlockClickUi", button);
            	 closeFrame();
             }
         });
         
         
    	//panel for easy sorting on the frame

        JPanel panel = new JPanel();
        panel.setBackground(Color.DARK_GRAY);
        panel.setSize(250, 140);
        panel.setLayout(null);
        panel.setLocation(0, 0);
        panel.setOpaque(true);
        panel.add(titleLabel);

        panel.add(errorMessage); 
        panel.add(changeBlockButton);
        panel.add(gameNameToggleList);

        panel.add(returnButton);

        panel.setVisible(true);

        frame.add(panel);
        frame.setLayout(null);
        frame.setVisible(true);
        frame.setResizable(false);
        
        //action listener
        changeBlockButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
     			// clear error message and basic input validation
     			error = "";
     			errorMessage.setVisible(false);
     			
     			int selectedBlock = gameNameToggleList.getSelectedIndex();
     			if (selectedBlock < 0)
     				error = "Block needs to be selected for deletion!";
     				//errorMessage.setVisible(true);
     			if (error.length() == 0) {
     				// call the controller
     				int x = BlockClickUi.getButtonCoordHorizontal();
     				int y = BlockClickUi.getButtonCoordVertical();
     				try {
     					blockType = Block223Controller.getBlockOfCurrentDesignableGame(blocks.get(selectedBlock));
     					Block223Controller.positionBlock(blockType.getId(), DetailsPanel.getCurrentLevel(), getCoordsFromX(x), getCoordsFromY(y));
     					Color colour = new Color(blockType.getRed(), blockType.getGreen(), blockType.getBlue());
     					BlockGeneration.updateBlock(x, y, colour, "", 10);
     					TOGame game = Block223Controller.getCurrentDesignableGame();
     					int numBlocks = Block223Controller.getBlocksAtLevelOfCurrentDesignableGame(DetailsPanel.getCurrentLevel()).size();
     					DetailsPanel.setNumBlocks(numBlocks);
     					DetailsPanel.setNumBlocksLeft(game.getNrBlocksPerLevel() - numBlocks);
     					RefreshData.refreshData("block");
     					RefreshData.refreshData("panel");
     					errorMessage.setText("Successfully added!");
     					errorMessage.setBackground(null);
     					errorMessage.setForeground(Color.GREEN);
     					errorMessage.setVisible(true);
     				} catch (InvalidInputException e) {
     					errorMessage.setVisible(true);
     					errorMessage.setForeground(Color.RED);
     					errorMessage.setBackground(Color.WHITE);
     					error = e.getMessage();
     					errorMessage.setText(error);
     				}
         		}
            }
        });

        refreshData();
    }
    
    public static int getCoordsFromX(int x) {
    	return (x-10)/BlockSize + 1;
    }
    
    public static int getCoordsFromY(int y) {
    	return (y-10)/BlockVertical + 1;
    }
    
	// for refreshing the list of available blocks (put in main UI?)
    public static void refreshData(){
    	
    	//errorMessage.setText(error);

		//if (error == null || error.length() == 0) {
			// populate popup with data
	
			blocks = new HashMap<Integer, Integer>();
			gameNameToggleList.removeAllItems();
			Integer index = 0;
			try {
				 java.util.List<TOBlock> blockList = Block223Controller.getBlocksOfCurrentDesignableGame();
				 if(blockList.size() > 0) {
					for (TOBlock block : blockList) {
						blocks.put(index, block.getId());
						gameNameToggleList.addItem("Colour: (" + block.getRed() + ", " + 
								block.getGreen() + ", "+ block.getBlue() + ") Points: " + block.getPoints());
						index++;
					}
				}
			} catch (InvalidInputException e) {
				error = e.getMessage();
				errorMessage.setText(error);
				
			};
			gameNameToggleList.setSelectedIndex(-1);	
		//}

    }
    
	public static TOBlock getBlockType() {
		 return blockType;
	}
	public static void closeFrame()
	{
		 frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
	}
}

