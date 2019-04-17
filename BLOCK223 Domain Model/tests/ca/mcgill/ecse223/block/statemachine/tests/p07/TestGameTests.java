package ca.mcgill.ecse223.block.statemachine.tests.p07;

import static ca.mcgill.ecse223.block.util.Block223TestConstants.ADMIN_PASS;
import static ca.mcgill.ecse223.block.util.Block223TestConstants.USER_NAME;
import static ca.mcgill.ecse223.block.util.Block223TestConstants.USER_PASS;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import ca.mcgill.ecse223.block.application.Block223Application;
import ca.mcgill.ecse223.block.controller.Block223Controller;
import ca.mcgill.ecse223.block.controller.InvalidInputException;
import ca.mcgill.ecse223.block.model.Admin;
import ca.mcgill.ecse223.block.model.Block223;
import ca.mcgill.ecse223.block.model.Game;
import ca.mcgill.ecse223.block.model.Player;
import ca.mcgill.ecse223.block.model.User;
import ca.mcgill.ecse223.block.statemachine.tests.Block223PlayModeTest;
import ca.mcgill.ecse223.block.util.Block223TestUtil;

public class TestGameTests {

	private Game game;
	private Block223 block223;
	private Admin admin;
	private Player player;

	@Before
	public void createGame() {
		block223 = Block223TestUtil.initializeBlock223();
		admin = Block223TestUtil.createAndAssignAdminRoleToBlock223(block223);
		game = Block223TestUtil.initializeGame(1, 0, 1, 20, 10, block223, admin);
		Block223TestUtil.initializeBlockWithAssignmentAndPublishGame(game);
		game.setPublished(false);
		player = new Player(USER_PASS, block223);
		new User(USER_NAME, block223, player, admin);
		Block223Application.setCurrentUserRole(admin);
		Block223Application.setCurrentPlayableGame(null);
	}

	@Test(timeout = 2000)
	public void testTestGameSuccess() throws InvalidInputException {
		Map<Integer, String> userInput = new HashMap<>();
		// Simply pause the game at the first takeInput() call
		userInput.put(1, " ");
		Block223Controller.testGame(new Block223PlayModeTest(userInput));

		assertNotNull(Block223Application.getCurrentPlayableGame());
		assertEquals(game, Block223Application.getCurrentPlayableGame().getGame());
		assertNull(Block223Application.getCurrentPlayableGame().getPlayer());
		assertEquals(USER_NAME, Block223Application.getCurrentPlayableGame().getPlayername());
	}

	@Test(timeout = 1000)
	public void testTestGameNoRights() {
		Block223Application.setCurrentUserRole(player);
		String errorNoAdminRights = "Admin privileges are required to test a game.";
		try {
			Block223Controller.testGame(new Block223PlayModeTest());
			fail();
		} catch (InvalidInputException e) {
			assertEquals(errorNoAdminRights, e.getMessage().trim());
		}
	}

	@Test(timeout = 1000)
	public void testTestGameNoSelectedGame() {
		Block223Application.setCurrentGame(null);
		String errorNoSelectedGame = "A game must be selected to test it.";
		try {
			Block223Controller.testGame(new Block223PlayModeTest());
			fail();
		} catch (InvalidInputException e) {
			assertEquals(errorNoSelectedGame, e.getMessage().trim());
		}
	}

	@Test(timeout = 1000)
	public void testTestGameDifferentAdmin() {
		Block223Application.setCurrentUserRole(new Admin(ADMIN_PASS, block223));
		String errorDifferentAdmin = "Only the admin who created the game can test it.";
		try {
			Block223Controller.testGame(new Block223PlayModeTest());
			fail();
		} catch (InvalidInputException e) {
			assertEquals(errorDifferentAdmin, e.getMessage().trim());
		}
	}

}
