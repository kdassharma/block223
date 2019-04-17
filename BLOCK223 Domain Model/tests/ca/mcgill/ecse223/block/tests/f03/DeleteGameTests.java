package ca.mcgill.ecse223.block.tests.f03;

import static ca.mcgill.ecse223.block.util.Block223TestConstants.ADMIN_PASS;
import static ca.mcgill.ecse223.block.util.Block223TestConstants.BALL_SPEED_INCREASE_FACTOR;
import static ca.mcgill.ecse223.block.util.Block223TestConstants.BLOCKS_PER_LEVEL;
import static ca.mcgill.ecse223.block.util.Block223TestConstants.LEVELS;
import static ca.mcgill.ecse223.block.util.Block223TestConstants.MAX_PADDLE_LENGTH;
import static ca.mcgill.ecse223.block.util.Block223TestConstants.MIN_BALL_SPEED_X;
import static ca.mcgill.ecse223.block.util.Block223TestConstants.MIN_BALL_SPEED_Y;
import static ca.mcgill.ecse223.block.util.Block223TestConstants.MIN_PADDLE_LENGTH;
import static ca.mcgill.ecse223.block.util.Block223TestConstants.MISSING_EXPECTED_EXCEPTION;
import static ca.mcgill.ecse223.block.util.Block223TestConstants.TEST_GAME_NAME_1;
import static ca.mcgill.ecse223.block.util.Block223TestConstants.TEST_GAME_NAME_2;
import static ca.mcgill.ecse223.block.util.Block223TestConstants.USER_NAME;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import ca.mcgill.ecse223.block.application.Block223Application;
import ca.mcgill.ecse223.block.controller.Block223Controller;
import ca.mcgill.ecse223.block.controller.InvalidInputException;
import ca.mcgill.ecse223.block.controller.TOGame;
import ca.mcgill.ecse223.block.model.Admin;
import ca.mcgill.ecse223.block.model.Block223;
import ca.mcgill.ecse223.block.model.Game;
import ca.mcgill.ecse223.block.model.Player;
import ca.mcgill.ecse223.block.util.Block223TestUtil;

public class DeleteGameTests {

	private Block223 block223;
	private Admin admin;
	private Game testGame;

	@Before
	public void setUp() {
		block223 = Block223TestUtil.initializeBlock223();
		admin = Block223TestUtil.createAndAssignAdminRoleToBlock223(block223);
		testGame = Block223TestUtil.initializeGame(block223, admin);
	}

	// getDesignableGames

	@Test
	public void testGetDesignableGamesSuccess() throws InvalidInputException {
		List<TOGame> designableGames = Block223Controller.getDesignableGames();

		assertEquals(1, designableGames.size());

		Object[] gameTOs = designableGames.stream().filter(it -> it.getName().contentEquals(TEST_GAME_NAME_1))
				.toArray();

		assertEquals("The game " + TEST_GAME_NAME_1 + " was not found.", 1, gameTOs.length);

		TOGame toGame = (TOGame) gameTOs[0];

		assertEquals(LEVELS, toGame.getNrLevels());
		assertEquals(BLOCKS_PER_LEVEL, toGame.getNrBlocksPerLevel());
		assertEquals(MIN_BALL_SPEED_X, toGame.getMinBallSpeedX());
		assertEquals(MIN_BALL_SPEED_Y, toGame.getMinBallSpeedY());
		assertEquals(BALL_SPEED_INCREASE_FACTOR, toGame.getBallSpeedIncreaseFactor(), 0.00001);
		assertEquals(MAX_PADDLE_LENGTH, toGame.getMaxPaddleLength());
		assertEquals(MIN_PADDLE_LENGTH, toGame.getMinPaddleLength());

		assertEquals(TEST_GAME_NAME_1, toGame.getName());
	}

	@Test
	public void testGetDesignableGamesNoRights() throws InvalidInputException {
		Player playerRole = new Player(USER_NAME, new Block223());
		Block223Application.setCurrentUserRole(playerRole);
		String errorNoAdminRights = "Admin privileges are required to access game information.";
		try {
			Block223Controller.getDesignableGames();
			fail(MISSING_EXPECTED_EXCEPTION + errorNoAdminRights);
		} catch (InvalidInputException e) {
			assertTrue(e.getMessage().trim().contains(errorNoAdminRights));
		}
	}

	@Test
	public void testGetDesignableGamesDifferentAdmin() throws InvalidInputException {
		Admin admin = new Admin(ADMIN_PASS, new Block223());
		Block223Application.setCurrentUserRole(admin);
		List<TOGame> designableGames = Block223Controller.getDesignableGames();
		assertEquals(0, designableGames.size());
	}

	@Test
	public void testGetDesignableGamesPublishedGame() throws InvalidInputException {
		Game publishedGame = new Game(TEST_GAME_NAME_2, BLOCKS_PER_LEVEL, admin, MIN_BALL_SPEED_X, MIN_BALL_SPEED_Y,
				BALL_SPEED_INCREASE_FACTOR, MAX_PADDLE_LENGTH, MIN_PADDLE_LENGTH, block223);
		publishedGame.setPublished(true);
		List<TOGame> designableGames = Block223Controller.getDesignableGames();
		assertEquals(1, designableGames.size());
	}

	// deleteGame

	@Test
	public void testDeleteGameSuccess() throws InvalidInputException {
		Block223Controller.deleteGame(TEST_GAME_NAME_1);
		Object[] gameTOs = Block223Controller.getDesignableGames().stream()
				.filter(it -> it.getName().contentEquals(TEST_GAME_NAME_1)).toArray();

		assertEquals(0, gameTOs.length);
	}

	@Test
	public void testDeleteGameNoRights() throws InvalidInputException {
		Player playerRole = new Player(USER_NAME, new Block223());
		Block223Application.setCurrentUserRole(playerRole);
		String errorNoAdminRights = "Admin privileges are required to delete a game.";
		try {
			Block223Controller.deleteGame(TEST_GAME_NAME_1);
			fail(MISSING_EXPECTED_EXCEPTION + errorNoAdminRights);
		} catch (InvalidInputException e) {
			assertTrue(e.getMessage().trim().contains(errorNoAdminRights));
		}
	}

	@Test
	public void testDeleteGameDifferentAdmin() throws InvalidInputException {
		Admin admin = new Admin(ADMIN_PASS, new Block223());
		Block223Application.setCurrentUserRole(admin);
		String errorDifferentAdmin = "Only the admin who created the game can delete the game.";
		try {
			Block223Controller.deleteGame(TEST_GAME_NAME_1);
			fail(MISSING_EXPECTED_EXCEPTION + errorDifferentAdmin);
		} catch (InvalidInputException e) {
			assertTrue(e.getMessage().trim().contains(errorDifferentAdmin));
		}
	}

	@Test
	public void testDeleteGameNoSuchGame() throws InvalidInputException {
		try {
			Block223Controller.deleteGame(TEST_GAME_NAME_2);
		} catch (InvalidInputException e) {
			fail("There was an exception while deleting a non-existing game.");
		}
	}
	
	@Test
	public void testDeleteGamePublishedGame() throws InvalidInputException {
		testGame.setPublished(true);
		String errorPublished = "A published game cannot be deleted.";
		try {
			Block223Controller.deleteGame(TEST_GAME_NAME_1);
			fail(MISSING_EXPECTED_EXCEPTION + errorPublished);
		} catch (InvalidInputException e) {
			assertEquals(errorPublished, e.getMessage());
		}
	}

}
