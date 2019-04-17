package ca.mcgill.ecse223.block.view;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

public class UserMenu {

	static JFrame frame;
    static GraphicsConfiguration gc;

    public UserMenu(JFrame jFrame){
        run(jFrame);
    }

    private static void run(JFrame jFrame) {
        jFrame.getContentPane().removeAll();
        
        frame =jFrame;
        frame.setTitle("Block223 Game");

        JLabel titleLabel = new JLabel();
        titleLabel.setText("Welcome User");
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setBackground(Color.GREEN);
        titleLabel.setOpaque(true);
        titleLabel.setBounds(0, 20,600, 60);
        titleLabel.setFont(new Font("Courier", Font.BOLD,40));
        
        JButton logInButton = new JButton();
        logInButton.setText("Log in");
        logInButton.setBounds(215, 120, 180, 60);
        logInButton.setBorder(BorderFactory.createLineBorder(Color.GREEN, 3));
        logInButton.setOpaque(true);
        
        JButton signUpButton = new JButton();
        signUpButton.setText("signUp");
        signUpButton.setBounds(215, 200, 180, 60);
        signUpButton.setBorder(BorderFactory.createLineBorder(Color.GREEN, 3));
        signUpButton.setOpaque(true);
        
        JButton leave = new JButton();
        leave.setText("Ready to leave?");
        leave.setBounds(215, 280, 180, 60);
        leave.setBorder(BorderFactory.createLineBorder(Color.GREEN, 3));
        leave.setOpaque(true);
        
        JPanel panel = new JPanel();
        panel.setBackground(Color.DARK_GRAY);
        panel.setSize(590, 390);
        panel.setLayout(null);
        panel.setLocation(5, 5);
        panel.setOpaque(true);
        panel.add(titleLabel);
        panel.add(logInButton);
        panel.add(signUpButton);
        panel.add(leave);
        panel.setVisible(true);
        
        frame.setSize(600,400);
        frame.setLocation(AdminUi.getCenterWidth() - 300, AdminUi.getCenterHeight() - 200);
      
        frame.add(panel);
        
        frame.setResizable(false);

        logInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ViewManager.runPopupView("login");
                UserMenu.closeFrame();
            }
        });

        signUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ViewManager.runPopupView("signup");
            	UserMenu.closeFrame();
            }
        });
        
        leave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	System.exit(0);
            }
        });
        frame.setVisible(true);

    }
    public static void closeFrame()
	{
		frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
	}
}