package ca.mcgill.ecse223.block.statemachine.tests.p06;

import static ca.mcgill.ecse223.block.util.Block223TestConstants.BLUE_2;
import static ca.mcgill.ecse223.block.util.Block223TestConstants.GREEN_2;
import static ca.mcgill.ecse223.block.util.Block223TestConstants.RED_2;
import static ca.mcgill.ecse223.block.util.Block223TestConstants.USER_NAME;
import static ca.mcgill.ecse223.block.util.Block223TestConstants.USER_PASS;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import ca.mcgill.ecse223.block.application.Block223Application;
import ca.mcgill.ecse223.block.controller.Block223Controller;
import ca.mcgill.ecse223.block.controller.InvalidInputException;
import ca.mcgill.ecse223.block.controller.TOHallOfFame;
import ca.mcgill.ecse223.block.model.Admin;
import ca.mcgill.ecse223.block.model.Block;
import ca.mcgill.ecse223.block.model.Block223;
import ca.mcgill.ecse223.block.model.Game;
import ca.mcgill.ecse223.block.model.HallOfFameEntry;
import ca.mcgill.ecse223.block.model.PlayedGame;
import ca.mcgill.ecse223.block.model.Player;
import ca.mcgill.ecse223.block.util.Block223TestUtil;

public class ViewHallOfFameTests {

	private static final int SCORE_2 = 20;
	private static final int SCORE_1 = 10;
	private Game game;
	private Block223 block223;
	private Admin admin;
	private Player player;
	private HallOfFameEntry mostRecentEntry;

	@Before
	public void createGame() {
		block223 = Block223TestUtil.initializeBlock223();
		admin = Block223TestUtil.createAndAssignAdminRoleToBlock223(block223);
		game = Block223TestUtil.initializeGame(1, 0, 1, 20, 10, block223, admin);
		Block223TestUtil.initializeBlockWithAssignmentAndPublishGame(game);
		player = new Player(USER_PASS, block223);
		Block223TestUtil.initializePlayedGame(game, player, block223);

		new HallOfFameEntry(SCORE_1, USER_NAME, player, game, block223);
		mostRecentEntry = new HallOfFameEntry(SCORE_2, USER_NAME, player, game, block223);

		game.setMostRecentEntry(mostRecentEntry);
	}

	// getHallOfFame

	@Test
	public void testGetHallOfFameSuccess() throws InvalidInputException {
		TOHallOfFame hallOfFame = Block223Controller.getHallOfFame(1, 2);
		assertEquals(2, hallOfFame.getEntries().size());
		assertEquals(SCORE_2, hallOfFame.getEntry(0).getScore());
		assertEquals(SCORE_1, hallOfFame.getEntry(1).getScore());
	}

	@Test
	public void testGetHallOfFameFirstEntrySuccess() throws InvalidInputException {
		TOHallOfFame hallOfFame = Block223Controller.getHallOfFame(0, 1);
		assertEquals(1, hallOfFame.getEntries().size());
		assertEquals(SCORE_2, hallOfFame.getEntry(0).getScore());
	}

	@Test
	public void testGetHallOfFameLastEntrySuccess() throws InvalidInputException {
		TOHallOfFame hallOfFame = Block223Controller.getHallOfFame(2, 3);
		assertEquals(1, hallOfFame.getEntries().size());
		assertEquals(SCORE_1, hallOfFame.getEntry(0).getScore());
	}

	@Test
	public void testGetHallOfFameNoEntriesSuccess() throws InvalidInputException {
		Game game2 = new Game("game2", 1, admin, 1, 1, 1.0, 1, 1, block223);
		game2.addLevel();
		Block223Application.setCurrentGame(game2);
		new Block(RED_2, GREEN_2, BLUE_2, SCORE_2, game2);
		game2.setPublished(true);
		Block223Application.setCurrentPlayableGame(new PlayedGame(USER_NAME, game2, block223));
		TOHallOfFame hallOfFame = Block223Controller.getHallOfFame(1, 2);
		assertEquals(0, hallOfFame.getEntries().size());
	}

	@Test
	public void testGetHallOfFameEmptyInterval() throws InvalidInputException {
		TOHallOfFame hallOfFame = Block223Controller.getHallOfFame(10, 11);
		assertEquals(0, hallOfFame.getEntries().size());
	}

	@Test
	public void testGetHallOfFameInvalidInterval() throws InvalidInputException {
		TOHallOfFame hallOfFame = Block223Controller.getHallOfFame(10, 1);
		assertEquals(0, hallOfFame.getEntries().size());
	}

	@Test
	public void testGetHallOfFameNotAPlayer() {
		Block223Application.setCurrentUserRole(admin);
		String errorNotAPlayerStart = "Player privileges are required to access a game";
		String errorNotAPlayerEnd = "s hall of fame.";
		try {
			Block223Controller.getHallOfFame(0, 1);
		} catch (InvalidInputException e) {
			assertTrue(e.getMessage().trim().startsWith(errorNotAPlayerStart));
			assertTrue(e.getMessage().trim().endsWith(errorNotAPlayerEnd));
		}
	}

	@Test
	public void testGetHallOfFameNoSelectedGame() {
		Block223Application.setCurrentPlayableGame(null);
		String errorNoCurrentPlayableGame = "A game must be selected to view its hall of fame.";
		try {
			Block223Controller.getHallOfFame(0, 1);
			fail();
		} catch (InvalidInputException e) {
			assertEquals(errorNoCurrentPlayableGame, e.getMessage().trim());
		}
	}

	// getHallOfFameWithMostRecentEntry

	@Test
	public void testGetHallOfFameWithMostRecentEntrySuccess() throws InvalidInputException {
		TOHallOfFame hallOfFame = Block223Controller.getHallOfFameWithMostRecentEntry(1);
		assertEquals(1, hallOfFame.getEntries().size());
		assertEquals(SCORE_2, hallOfFame.getEntry(0).getScore());
	}

	@Test
	public void testGetHallOfFameWithMostRecentEntriesSuccess() throws InvalidInputException {
		TOHallOfFame hallOfFame = Block223Controller.getHallOfFameWithMostRecentEntry(2);
		assertEquals(2, hallOfFame.getEntries().size());
		assertEquals(SCORE_2, hallOfFame.getEntry(0).getScore());
		assertEquals(SCORE_1, hallOfFame.getEntry(1).getScore());
	}
	
	@Test
	public void testGetHallOfFameMostRecentEntriesNoEntriesSuccess() throws InvalidInputException {
		Game game2 = new Game("game2", 1, admin, 1, 1, 1.0, 1, 1, block223);
		game2.addLevel();
		Block223Application.setCurrentGame(game2);
		new Block(RED_2, GREEN_2, BLUE_2, SCORE_2, game2);
		game2.setPublished(true);
		Block223Application.setCurrentPlayableGame(new PlayedGame(USER_NAME, game2, block223));
		TOHallOfFame hallOfFame = Block223Controller.getHallOfFameWithMostRecentEntry(1);
		assertEquals(0, hallOfFame.getEntries().size());
	}

	@Test
	public void testGetHallOfFameWithMostRecentEntryNotAPlayer() throws InvalidInputException {
		Block223Application.setCurrentUserRole(admin);
		String errorNotAPlayerStart = "Player privileges are required to access a game";
		String errorNotAPlayerEnd = "s hall of fame.";
		try {
			Block223Controller.getHallOfFameWithMostRecentEntry(1);
			fail();
		} catch (InvalidInputException e) {
			assertTrue(e.getMessage().trim().startsWith(errorNotAPlayerStart));
			assertTrue(e.getMessage().trim().endsWith(errorNotAPlayerEnd));
		}
	}

	@Test
	public void testGetHallOfFameWithMostRecentEntryNoSelectedGame() {
		Block223Application.setCurrentPlayableGame(null);
		String errorNoCurrentPlayableGame = "A game must be selected to view its hall of fame.";
		try {
			Block223Controller.getHallOfFameWithMostRecentEntry(1);
			fail();
		} catch (InvalidInputException e) {
			assertEquals(errorNoCurrentPlayableGame, e.getMessage().trim());
		}
	}

}
