package ca.mcgill.ecse223.block.view;

import ca.mcgill.ecse223.block.controller.Block223Controller;
import ca.mcgill.ecse223.block.controller.InvalidInputException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

public class SignUp {

	static JFrame frame;
    static GraphicsConfiguration gc;

    public SignUp(){
        run();
    }

    private static void run() {
    	
        frame = new JFrame("Block223 User sign up!");
        frame.setTitle("Block223 Game");

        JLabel titleLabel = new JLabel();
        titleLabel.setText("Sign Up TIME!!!");
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setBounds(0, 20, 600, 60);
        titleLabel.setBackground(Color.GREEN);
        titleLabel.setOpaque(true);
        titleLabel.setFont(new Font("Courier", Font.BOLD,40));
        
        JLabel usernameLabel = new JLabel();
        usernameLabel.setText("Username: ");
        usernameLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        usernameLabel.setBounds(0, 100, 258, 25);
        usernameLabel.setBackground(Color.LIGHT_GRAY);
        usernameLabel.setOpaque(true);
        
        JLabel playerPasswordLabel = new JLabel();
        playerPasswordLabel.setText("Player password: ");
        playerPasswordLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        playerPasswordLabel.setBounds(0, 150, 258, 25);
        playerPasswordLabel.setBackground(Color.LIGHT_GRAY);
        playerPasswordLabel.setOpaque(true);
        
        JLabel adminPasswordLabel = new JLabel();
        adminPasswordLabel.setText("Admin password: ");
        adminPasswordLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        adminPasswordLabel.setBackground(Color.LIGHT_GRAY);
        adminPasswordLabel.setOpaque(true);
        adminPasswordLabel.setBounds(0, 200, 258, 25);
        
        JTextField usernameInput = new JTextField();
        usernameInput.setText("");
        usernameInput.setBounds(300, 100, 130, 25);
        
        JTextField playerPasswordInput = new JTextField();
        playerPasswordInput.setText("");
        playerPasswordInput.setBounds(300, 150, 130, 25);
        
        JTextField adminPasswordInput = new JTextField();
        adminPasswordInput.setText("");
        adminPasswordInput.setBounds(300, 200, 130, 25);
        
        JButton submitUserButton = new JButton("Signup user!");
        submitUserButton.setBounds(300, 250, 130, 60);
        submitUserButton.setBorder(BorderFactory.createLineBorder(Color.GREEN, 4));
        submitUserButton.setOpaque(true);
        
        JLabel errorMessage = new JLabel();
        errorMessage.setText("");
        errorMessage.setBounds(0, 330, 3000, 20);
        errorMessage.setVisible(false);
        errorMessage.setBackground(Color.WHITE);
        errorMessage.setForeground(Color.RED);
        errorMessage.setOpaque(true);
        
        JButton returnButton = new JButton();
        returnButton.setFont(new Font("Courier", Font.PLAIN,10));
		returnButton.setSize(50, 60);
		returnButton.setText("<--");
        returnButton.setBorder(BorderFactory.createLineBorder(Color.GREEN, 4));
        returnButton.setOpaque(true);
        returnButton.setLocation(190,250);
        
        //return button to User screen
        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	ViewManager.runView("usermenu", new JFrame());
            	closeFrame();
            }
        });

        //panel for easy sorting on the frame
        JPanel panel = new JPanel();
        panel.setBackground(Color.DARK_GRAY);
        panel.setSize(600, 390);
        panel.setLayout(null);
        panel.setLocation(0, 0);
        panel.setOpaque(true);
        panel.add(titleLabel);
        panel.add(usernameLabel);
        panel.add(playerPasswordLabel);
        panel.add(playerPasswordInput);
        panel.add(usernameInput);
        panel.add(adminPasswordLabel);
        panel.add(adminPasswordInput);
        panel.add(errorMessage); 
        panel.add(returnButton);
        panel.add(submitUserButton);

        panel.setVisible(true);
     
        frame.add(panel);
      
        frame.setSize(600,400);
        frame.setLocation(AdminUi.getCenterWidth() - 300, AdminUi.getCenterHeight() - 200);
        frame.setLayout(null);
        frame.setVisible(true);
        frame.setResizable(false);

        submitUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameInput.getText();
                String playerPassword = playerPasswordInput.getText();
                String adminPassword = adminPasswordInput.getText();
                errorMessage.setText("User signed up successfully");
                errorMessage.setForeground(Color.GREEN);
                errorMessage.setBackground(null);
                errorMessage.setVisible(true);
                try {
                    Block223Controller.register(username, playerPassword, adminPassword);
                }catch(InvalidInputException exc){
                	errorMessage.setBackground(Color.WHITE);
                	errorMessage.setForeground(Color.RED);
                    errorMessage.setText(exc.getMessage());
                    errorMessage.setVisible(true);
                }
            }
        });
    }
    public static void closeFrame()
	{
		frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
	}
}