package ca.mcgill.ecse223.block.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
/*
 * this class is used to input all the information
 * the MainUi will simply run the operation to the screen
 */
import java.awt.event.ActionListener;

public class MainFrame extends JFrame{
	//adding the detail panel to the application
	
	private DetailsPanel detailsPanel;
	
	public MainFrame(String title)
	{
	
		detailsPanel = new DetailsPanel();
		
		//add swing components to content pane
		Container c = getContentPane();
		//adding the swing objects is really easy. With BorderLayout we need
		//to add them to specific areas.
		c.add(detailsPanel, BorderLayout.SOUTH);
	}
}
