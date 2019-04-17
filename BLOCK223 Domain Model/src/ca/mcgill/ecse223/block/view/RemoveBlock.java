package ca.mcgill.ecse223.block.view;

import ca.mcgill.ecse223.block.controller.Block223Controller;
import ca.mcgill.ecse223.block.controller.InvalidInputException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RemoveBlock {

    static GraphicsConfiguration gc;

    public RemoveBlock(JFrame oldFrame){
        run(oldFrame);
    }

    private static void run(JFrame oldFrame) {
        JFrame frame = new JFrame("Block223");
        frame.setTitle("Block223 Game");
        JLabel titleLabel = new JLabel();
        titleLabel.setText("Remove Block");
        titleLabel.setBounds(150, 20, 500, 60);
        titleLabel.setFont(new Font("Courier", Font.BOLD,40));

        JLabel xCoord = new JLabel();
        xCoord.setText("X: ");
        xCoord.setBounds(200, 125, 130, 25);

        JLabel yCoord = new JLabel();
        yCoord.setText("Y: ");
        yCoord.setBounds(200, 175, 130, 25);

        JTextField xCoordInput = new JTextField();
        xCoordInput.setText("");
        xCoordInput.setBounds(250, 125, 130, 25);

        JTextField yCoordInput = new JTextField();
        yCoordInput.setText("");
        yCoordInput.setBounds(250, 175, 130, 25);

        JButton confirmButton = new JButton("Confirm");
        confirmButton.setBounds(250, 250, 130, 30);

        JLabel errorMessage = new JLabel();
        errorMessage.setText("");
        errorMessage.setBounds(150, 300, 3000, 50);

        frame.add(titleLabel);
        frame.add(xCoord);
        frame.add(yCoord);
        

        frame.add(xCoordInput);
        frame.add(yCoordInput);
        frame.add(errorMessage);
        frame.add(confirmButton);

        frame.setSize(600, 400);
        frame.setLocation(400, 400);
        frame.setLayout(null);
        frame.setVisible(true);
        frame.setResizable(false);

        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int xCoord = Integer.parseInt(xCoordInput.getText());
                int yCoord = Integer.parseInt(yCoordInput.getText());
                //int level = oldFrame.getLevel; (something along those lines)
                int level = 1; //
                errorMessage.setText("Block removed successfully!");
                try {
                    Block223Controller.removeBlock(level, xCoord, yCoord);
                }catch(InvalidInputException exc){
                    errorMessage.setText(exc.getMessage());
                }
                //RefreshData.refreshData();
            }
        });


    }

}
