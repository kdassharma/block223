package ca.mcgill.ecse223.block.view;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import ca.mcgill.ecse223.block.controller.InvalidInputException;

public class Block223PlayModeExampleListener implements KeyListener {

	/**
	 * 'String input from keyboard - marked as volatile since it is shared by two
	 * threads
	 */
	private volatile String keyString = "";
	
	@Override
	public synchronized void keyPressed(KeyEvent e) {
		try {
			keyInputs(e);
		} catch (InvalidInputException e1) {
			System.out.print(e1);
		}
	}

	private synchronized String keyInputs(KeyEvent e) throws InvalidInputException {
		int location = e.getKeyCode();
		
		//prevents double clicking
		if(e.getID() == KeyEvent.KEY_PRESSED)
		{
			if (location == KeyEvent.VK_LEFT) {
				keyString += "l"; 
			} else if (location == KeyEvent.VK_RIGHT) {
				keyString += "r"; 
			} else if (location == KeyEvent.VK_SPACE) {
				keyString += " "; 
			} else {
				// ignore all other keys
			}
		}
		return keyString;
	}

	/**
	 * Takes key inputs and clears the input string. marked as synchronized to make
	 * the key inputs as thread-safe
	 */
	public synchronized String takeInputs() {
		String passString = keyString;
		keyString = "";
		return passString;
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

}
