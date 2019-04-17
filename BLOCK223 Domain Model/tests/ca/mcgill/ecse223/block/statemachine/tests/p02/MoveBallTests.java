package ca.mcgill.ecse223.block.statemachine.tests.p02;

import static ca.mcgill.ecse223.block.util.Block223TestConstants.USER_PASS;
import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import ca.mcgill.ecse223.block.controller.Block223Controller;
import ca.mcgill.ecse223.block.controller.InvalidInputException;
import ca.mcgill.ecse223.block.model.Admin;
import ca.mcgill.ecse223.block.model.Block223;
import ca.mcgill.ecse223.block.model.Game;
import ca.mcgill.ecse223.block.model.PlayedGame;
import ca.mcgill.ecse223.block.model.Player;
import ca.mcgill.ecse223.block.statemachine.tests.Block223PlayModeTest;
import ca.mcgill.ecse223.block.util.Block223TestUtil;

public class MoveBallTests {

	private Game game;
	private Block223 block223;
	private Admin admin;
	private Player player;
	private PlayedGame playedGame;

	@Before
	public void createGame() {
		block223 = Block223TestUtil.initializeBlock223();
		admin = Block223TestUtil.createAndAssignAdminRoleToBlock223(block223);
		game = Block223TestUtil.initializeGame(1, 1, 0, 1, 20, 10, block223, admin);
		Block223TestUtil.initializeBlockWithAssignmentAndPublishGame(game);
		player = new Player(USER_PASS, block223);
		playedGame = Block223TestUtil.initializePlayedGame(game, player, block223);
	}

	// doHitNothingAndNotOutOfBounds
	
	@Test
	public void testDoHitNothingAndNotOutOfBoundsSuccess() throws InvalidInputException {
		int defaultDirectionX = game.getBall().getMinBallSpeedX();
		int defaultDirectionY = game.getBall().getMinBallSpeedY();
		
		Map<Integer, String> playerInput = new HashMap<>();
		playerInput.put(1, " ");
		
		Block223Controller.startGame(new Block223PlayModeTest(playerInput));

		assertEquals(Game.PLAY_AREA_SIDE / 2 + defaultDirectionX, playedGame.getCurrentBallX(), 0.00001);
		assertEquals(Game.PLAY_AREA_SIDE / 2 + defaultDirectionY, playedGame.getCurrentBallY(), 0.00001);		
	}
	
}
