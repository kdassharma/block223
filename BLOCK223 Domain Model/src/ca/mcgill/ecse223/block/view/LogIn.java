package ca.mcgill.ecse223.block.view;

import ca.mcgill.ecse223.block.controller.Block223Controller;
import ca.mcgill.ecse223.block.controller.InvalidInputException;
import ca.mcgill.ecse223.block.controller.TOUserMode;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

public class LogIn {

	static JFrame frame;
    static GraphicsConfiguration gc;

    public LogIn(){
        run();
    }

    private static void run() {    	
    	
        frame = new JFrame("Block223 Login!");
        frame.setTitle("Block223 Game");

        JLabel titleLabel = new JLabel();
        titleLabel.setText("Ready to PLAY!!!");
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setBackground(Color.GREEN);
        titleLabel.setOpaque(true);
        titleLabel.setBounds(0, 20,600, 60);
        titleLabel.setFont(new Font("Courier", Font.BOLD,40));
        
        JLabel usernameLabel = new JLabel();
        usernameLabel.setText("Username: ");
        usernameLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        usernameLabel.setBackground(Color.LIGHT_GRAY);
        usernameLabel.setOpaque(true);
        usernameLabel.setBounds(0, 125, 240, 25);
        
        JLabel passwordLabel = new JLabel();
        passwordLabel.setText("Password: ");
        passwordLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        passwordLabel.setBackground(Color.LIGHT_GRAY);
        passwordLabel.setOpaque(true);
        passwordLabel.setBounds(0, 175, 240, 25);
        
        JLabel adminPasswordLabel = new JLabel();

        JTextField usernameInput = new JTextField();
        usernameInput.setText("");
        usernameInput.setBounds(300, 125, 130, 25);
        
        JTextField passwordInput = new JTextField();
        passwordInput.setText("");
        passwordInput.setBounds(300, 175, 130, 25);
        
        JButton loginButton = new JButton("Login!");
        loginButton.setBounds(300, 250, 130, 60);
        loginButton.setBorder(BorderFactory.createLineBorder(Color.GREEN, 4));
        loginButton.setOpaque(true);
        
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

        JPanel panel = new JPanel();
        panel.setBackground(Color.DARK_GRAY);
        panel.setSize(600, 390);
        panel.setLayout(null);
        panel.setLocation(0, 0);
        panel.setOpaque(true);
        panel.add(titleLabel);
        panel.add(usernameLabel);
        panel.add(passwordLabel);
        panel.add(adminPasswordLabel);
        panel.add(usernameInput);
        panel.add(passwordInput);
        panel.add(errorMessage);
        panel.add(loginButton);
        panel.add(returnButton);

        panel.setVisible(true);
     
        frame.add(panel);
        
        frame.setSize(600,400);
        frame.setLocation(AdminUi.getCenterWidth() - 300, AdminUi.getCenterHeight() - 200);
        frame.setLayout(null);
        frame.setVisible(true);
        frame.setResizable(false);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameInput.getText();
                String playerPassword = passwordInput.getText();
                errorMessage.setText("");
                errorMessage.setVisible(false);
//                TOHallOfFame hof = new TOHallOfFame("easyWin");
//                TOHallOfFameEntry hofEntry = new TOHallOfFameEntry(0, "", 5, hof);
//                ViewManager.runView("GameOverUi", hofEntry);
//                ViewManager.runView("VictoryUi", hofEntry, 0);
                try {
                    Block223Controller.login(username, playerPassword);
                    if(Block223Controller.getUserMode().getMode() == TOUserMode.Mode.Design)
                        ViewManager.runView("MainUi");
                    else {
                    	ViewManager.runPopupView("PlayerIntroUi");
                    	closeFrame();
                    }

                }catch(InvalidInputException exc){
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