package ca.mcgill.ecse223.block.statemachine.tests.p08;

import static ca.mcgill.ecse223.block.util.Block223TestConstants.ADMIN_PASS;
import static ca.mcgill.ecse223.block.util.Block223TestConstants.USER_PASS;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import ca.mcgill.ecse223.block.application.Block223Application;
import ca.mcgill.ecse223.block.controller.Block223Controller;
import ca.mcgill.ecse223.block.controller.InvalidInputException;
import ca.mcgill.ecse223.block.model.Admin;
import ca.mcgill.ecse223.block.model.Block;
import ca.mcgill.ecse223.block.model.Block223;
import ca.mcgill.ecse223.block.model.Game;
import ca.mcgill.ecse223.block.model.Player;
import ca.mcgill.ecse223.block.util.Block223TestUtil;

public class PublishGameTests {
	private Game game;
	private Block223 block223;
	private Admin admin;
	private Player player;
	private Block block;

	@Before
	public void createGame() {
		block223 = Block223TestUtil.initializeBlock223();
		admin = Block223TestUtil.createAndAssignAdminRoleToBlock223(block223);
		game = Block223TestUtil.initializeGame(1, 0, 1, 20, 10, block223, admin);
		block = Block223TestUtil.initializeBlockWithAssignmentAndPublishGame(game);
		player = new Player(USER_PASS, block223);
		Block223Application.setCurrentUserRole(admin);
	}

	@Test
	public void testPublishGameSuccess() throws InvalidInputException {
		Block223Controller.publishGame();
		assertTrue(game.isPublished());
	}

	@Test
	public void testPublishGameNoRights() {
		Block223Application.setCurrentUserRole(player);
		String errorNoRights = "Admin privileges are required to publish a game.";
		try {
			Block223Controller.publishGame();
			fail();
		} catch (InvalidInputException e) {
			assertEquals(errorNoRights, e.getMessage().trim());
		}
	}

	@Test
	public void testPublishGameNoGameSelected() {
		Block223Application.setCurrentGame(null);
		String errorNoGameSelected = "A game must be selected to publish it.";
		try {
			Block223Controller.publishGame();
			fail();
		} catch (InvalidInputException e) {
			assertEquals(errorNoGameSelected, e.getMessage().trim());
		}
	}

	@Test
	public void testPublishGameDifferentAdmin() {
		Block223Application.setCurrentUserRole(new Admin(ADMIN_PASS, block223));
		String errorDifferentAdmin = "Only the admin who created the game can publish it.";
		try {
			Block223Controller.publishGame();
			fail();
		} catch (InvalidInputException e) {
			assertEquals(errorDifferentAdmin, e.getMessage().trim());
		}
	}

	@Test
	public void testPublishGameNoBlocks() {
		block.delete();
		String errorNoBlocks = "At least one block must be defined for a game to be published.";
		try {
			Block223Controller.publishGame();
			fail();
		} catch (InvalidInputException e) {
			assertEquals(errorNoBlocks, e.getMessage().trim());
		}
	}
}
