package ca.mcgill.ecse223.block.statemachine.tests;

import java.util.HashMap;
import java.util.Map;

import ca.mcgill.ecse223.block.controller.TOHallOfFameEntry;
import ca.mcgill.ecse223.block.view.Block223PlayModeInterface;

/**
 * A class that mocks the behavior of the view that will provide the actual user
 * interactions to the controller
 */
public class Block223PlayModeTest implements Block223PlayModeInterface {

	/**
	 * Storage for predefined user inputs - the value with the key 0 will be ignored
	 */
	private Map<Integer, String> inputs;

	/**
	 * Internal counter to keeps track how many times the takeInputs() method was
	 * called
	 */
	private int takeCounter;

	public Block223PlayModeTest() {
		this(new HashMap<>());
	}

	public Block223PlayModeTest(Map<Integer, String> inputs) {
		this.inputs = inputs;
		this.takeCounter = 0;
	}

	@Override
	public String takeInputs() {
		// Get the predefined input that mimics user interaction at loop iteration
		// "takeConter"
		String input = inputs.get(takeCounter++);
		// If there is no predefined input at this stage, return an empty string
		return input != null ? input : "";
	}

	@Override
	public void refresh() {
		// nop
	}


	@Override
	public void endGame(int lives, TOHallOfFameEntry hof) {
		// TODO Auto-generated method stub
		
	}

}
