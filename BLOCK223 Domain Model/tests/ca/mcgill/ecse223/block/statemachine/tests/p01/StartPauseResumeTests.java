package ca.mcgill.ecse223.block.statemachine.tests.p01;

import static ca.mcgill.ecse223.block.util.Block223TestConstants.ADMIN_PASS;
import static ca.mcgill.ecse223.block.util.Block223TestConstants.TEST_GAME_NAME_1;
import static ca.mcgill.ecse223.block.util.Block223TestConstants.USER_NAME;
import static ca.mcgill.ecse223.block.util.Block223TestConstants.USER_NAME_2;
import static ca.mcgill.ecse223.block.util.Block223TestConstants.USER_PASS;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import ca.mcgill.ecse223.block.application.Block223Application;
import ca.mcgill.ecse223.block.controller.Block223Controller;
import ca.mcgill.ecse223.block.controller.InvalidInputException;
import ca.mcgill.ecse223.block.controller.TOCurrentlyPlayedGame;
import ca.mcgill.ecse223.block.controller.TOPlayableGame;
import ca.mcgill.ecse223.block.model.Admin;
import ca.mcgill.ecse223.block.model.Block223;
import ca.mcgill.ecse223.block.model.Game;
import ca.mcgill.ecse223.block.model.Paddle;
import ca.mcgill.ecse223.block.model.PlayedGame;
import ca.mcgill.ecse223.block.model.Player;
import ca.mcgill.ecse223.block.model.User;
import ca.mcgill.ecse223.block.statemachine.tests.Block223PlayModeTest;
import ca.mcgill.ecse223.block.util.Block223TestUtil;

public class StartPauseResumeTests {

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

	// getPlayableGames

	@Test
	public void testGetPlayableGamesSuccess() throws InvalidInputException {
		Block223Application.setCurrentPlayableGame(null);
		List<TOPlayableGame> playableGames = Block223Controller.getPlayableGames();
		// We expect two entries: one for the published Game object, one for the
		// PlayedGame object
		assertEquals(2, playableGames.size());
		assertEquals(TEST_GAME_NAME_1, playableGames.get(0).getName());
		assertEquals(-1, playableGames.get(0).getNumber());
		assertEquals(0, playableGames.get(0).getCurrentLevel());
		assertEquals(TEST_GAME_NAME_1, playableGames.get(1).getName());
		assertNotEquals(-1, playableGames.get(1).getNumber());
		assertEquals(1, playableGames.get(1).getCurrentLevel());
	}

	@Test
	public void testGetPlayableGamesNoRights() {
		Block223Application.setCurrentPlayableGame(null);
		Block223Application.setCurrentUserRole(admin);
		try {
			Block223Controller.getPlayableGames();
			fail();
		} catch (InvalidInputException e) {
			assertEquals("Player privileges are required to play a game.", e.getMessage().trim());
		}
	}

	// selectPlayableGame

	@Test
	public void testSelectPlayableGameSuccess() throws InvalidInputException {
		Block223Application.setCurrentPlayableGame(null);
		int id = playedGame.getId();
		Block223Controller.selectPlayableGame(null, id);
		assertEquals(playedGame, Block223Application.getCurrentPlayableGame());
		assertEquals(id, Block223Application.getCurrentPlayableGame().getId());
		assertEquals(USER_NAME, Block223Application.getCurrentPlayableGame().getPlayername());
	}

	@Test
	public void testSelectPlayableGameNewGameSuccess() throws InvalidInputException {
		Block223Application.setCurrentPlayableGame(null);
		Block223Controller.selectPlayableGame(TEST_GAME_NAME_1, -1);
		assertEquals(2, game.getPlayedGames().size());
	}

	@Test
	public void testSelectPlayableGameNoRights() {
		Block223Application.setCurrentPlayableGame(null);
		int id = playedGame.getId();
		Block223Application.setCurrentUserRole(admin);
		try {
			Block223Controller.selectPlayableGame(TEST_GAME_NAME_1, id);
			fail();
		} catch (InvalidInputException e) {
			assertEquals("Player privileges are required to play a game.", e.getMessage().trim());
		}
	}

	@Test
	public void testSelectPlayableGameNonExisting() {
		Block223Application.setCurrentPlayableGame(null);
		try {
			Block223Controller.selectPlayableGame("NotAGameName", -1);
			fail();
		} catch (InvalidInputException e) {
			assertEquals("The game does not exist.", e.getMessage().trim());
		}
	}

	@Test
	public void testSelectPlayableGameByDifferentUser() {
		Block223Application.setCurrentPlayableGame(null);
		int id = playedGame.getId();
		Player player2 = new Player(USER_PASS, block223);
		new User(USER_NAME_2, block223, player2);
		Block223Application.setCurrentUserRole(player2);
		try {
			Block223Controller.selectPlayableGame("NotAGame", id);
			fail();
		} catch (InvalidInputException e) {
			assertEquals("Only the player that started a game can continue the game.", e.getMessage().trim());
		}
	}

	// startGame

	@Test(timeout = 1000)
	public void testStartGameHitPaddleAndMovePaddleLeft() throws InvalidInputException {

		// Initializing fake user inputs
		Map<Integer, String> inputs = new HashMap<Integer, String>();
		// Create input to move the paddle
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < 20; i++) {
			builder.append("l");
		}
		// The input at iteration 0 is used up by the takeInputs() call before the game
		// loop in the startGame() method. The ball should just touch the paddle after
		// moving down 155 pixels (iterations 1-155). After moving one more pixel (i.e.,
		// iteration 156), the ball should bounce back. In the next iteration (i.e.,
		// iteration 157), the paddle is moved.
		inputs.put(157, builder.toString());

		// Provide the inputs to the test object substituting the actual UI object
		Block223PlayModeTest testInputProvider = new Block223PlayModeTest(inputs);

		// Save the initial number of lives
		int livesBefore = playedGame.getLives();

		Block223Controller.startGame(testInputProvider);

		// Since the ball is expected to have fallen down, there should be one life less
		assertEquals(livesBefore - 1, playedGame.getLives());

		// Furthermore, we check if the ball and paddle have been reset
		// Ball
		assertEquals(Game.PLAY_AREA_SIDE / 2, playedGame.getCurrentBallX(), 0.00001);
		assertEquals(Game.PLAY_AREA_SIDE / 2, playedGame.getCurrentBallY(), 0.00001);
		assertEquals(game.getBall().getMinBallSpeedX(), playedGame.getBallDirectionX(), 0.00001);
		assertEquals(game.getBall().getMinBallSpeedY(), playedGame.getBallDirectionY(), 0.00001);
		// Paddle
		assertEquals(game.getPaddle().getMaxPaddleLength(), playedGame.getCurrentPaddleLength(), 0.00001);
		assertEquals((Game.PLAY_AREA_SIDE - game.getPaddle().getMaxPaddleLength()) / 2, playedGame.getCurrentPaddleX(),
				0.00001);
		assertEquals(Game.PLAY_AREA_SIDE - Paddle.VERTICAL_DISTANCE - Paddle.PADDLE_WIDTH,
				playedGame.getCurrentPaddleY(), 0.00001);
	}

	@Test(timeout = 1000)
	public void testStartGameMovePaddleRight() throws InvalidInputException {
		int paddleOffsetToTheRight = 50;
		Map<Integer, String> inputs = new HashMap<Integer, String>();
		// Create input to move the paddle right paddleOffsetToTheRight pixels
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < paddleOffsetToTheRight / 5; i++) {
			builder.append("r");
		}
		builder.append(" ");
		// At the first processed input, we simulate that the user presses 'r'
		// paddleOffsetToTheRight times and also pauses the game
		inputs.put(1, builder.toString());

		// Provide the inputs to the test object substituting the actual UI object
		Block223PlayModeTest testInputProvider = new Block223PlayModeTest(inputs);

		Block223Controller.startGame(testInputProvider);

		assertEquals(Game.PLAY_AREA_SIDE / 2, playedGame.getCurrentBallX(), 0.00001);
		assertEquals(Game.PLAY_AREA_SIDE / 2 + 1, playedGame.getCurrentBallY(), 0.00001);
		assertEquals(game.getBall().getMinBallSpeedX(), playedGame.getBallDirectionX(), 0.00001);
		assertEquals(game.getBall().getMinBallSpeedY(), playedGame.getBallDirectionY(), 0.00001);
		assertEquals(game.getPaddle().getMaxPaddleLength(), playedGame.getCurrentPaddleLength(), 0.00001);
		assertEquals((Game.PLAY_AREA_SIDE - game.getPaddle().getMaxPaddleLength()) / 2 + paddleOffsetToTheRight,
				playedGame.getCurrentPaddleX(), 0.00001);
		assertEquals(Game.PLAY_AREA_SIDE - Paddle.VERTICAL_DISTANCE - Paddle.PADDLE_WIDTH,
				playedGame.getCurrentPaddleY(), 0.00001);
		assertEquals(PlayedGame.PlayStatus.Paused, playedGame.getPlayStatus());
	}

	@Test(timeout = 1000)
	public void testStartGameMovePaddleRightBeyondLimits() throws InvalidInputException {
		int paddleOffsetToTheRight = 190;
		Map<Integer, String> inputs = new HashMap<Integer, String>();
		// Create input to move the paddle right paddleOffsetToTheRight pixels
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < paddleOffsetToTheRight / 5; i++) {
			builder.append("r");
		}
		builder.append(" ");
		// At the first processed input, we simulate that the user presses 'r'
		// paddleOffsetToTheRight times and also pauses the game
		inputs.put(1, builder.toString());

		// Provide the inputs to the test object substituting the actual UI object
		Block223PlayModeTest testInputProvider = new Block223PlayModeTest(inputs);

		Block223Controller.startGame(testInputProvider);

		assertEquals(Game.PLAY_AREA_SIDE / 2, playedGame.getCurrentBallX(), 0.00001);
		assertEquals(Game.PLAY_AREA_SIDE / 2 + 1, playedGame.getCurrentBallY(), 0.00001);
		assertEquals(game.getBall().getMinBallSpeedX(), playedGame.getBallDirectionX(), 0.00001);
		assertEquals(game.getBall().getMinBallSpeedY(), playedGame.getBallDirectionY(), 0.00001);
		assertEquals(game.getPaddle().getMaxPaddleLength(), playedGame.getCurrentPaddleLength(), 0.00001);
		assertEquals(370, playedGame.getCurrentPaddleX(), 0.00001);
		assertEquals(Game.PLAY_AREA_SIDE - Paddle.VERTICAL_DISTANCE - Paddle.PADDLE_WIDTH,
				playedGame.getCurrentPaddleY(), 0.00001);
		assertEquals(PlayedGame.PlayStatus.Paused, playedGame.getPlayStatus());
	}

	@Test(timeout = 1000)
	public void testStartGameMovePaddleLeft() throws InvalidInputException {
		int paddleOffsetToTheLeft = 50;
		Map<Integer, String> inputs = new HashMap<Integer, String>();
		// Create input to move the paddle left paddleOffsetToTheRight pixels
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < paddleOffsetToTheLeft / 5; i++) {
			builder.append("l");
		}
		builder.append(" ");
		// At the first processed input, we simulate that the user presses 'l'
		// paddleOffsetToTheLeft times and also pauses the game
		inputs.put(1, builder.toString());

		// Provide the inputs to the test object substituting the actual UI object
		Block223PlayModeTest testInputProvider = new Block223PlayModeTest(inputs);

		Block223Controller.startGame(testInputProvider);

		assertEquals(Game.PLAY_AREA_SIDE / 2, playedGame.getCurrentBallX(), 0.00001);
		assertEquals(Game.PLAY_AREA_SIDE / 2 + 1, playedGame.getCurrentBallY(), 0.00001);
		assertEquals(game.getBall().getMinBallSpeedX(), playedGame.getBallDirectionX(), 0.00001);
		assertEquals(game.getBall().getMinBallSpeedY(), playedGame.getBallDirectionY(), 0.00001);
		assertEquals(game.getPaddle().getMaxPaddleLength(), playedGame.getCurrentPaddleLength(), 0.00001);
		assertEquals((Game.PLAY_AREA_SIDE - game.getPaddle().getMaxPaddleLength()) / 2 - paddleOffsetToTheLeft,
				playedGame.getCurrentPaddleX(), 0.00001);
		assertEquals(Game.PLAY_AREA_SIDE - Paddle.VERTICAL_DISTANCE - Paddle.PADDLE_WIDTH,
				playedGame.getCurrentPaddleY(), 0.00001);
		assertEquals(PlayedGame.PlayStatus.Paused, playedGame.getPlayStatus());
	}

	@Test(timeout = 1000)
	public void testStartGameMovePaddleLeftBeyondLimits() throws InvalidInputException {
		int paddleOffsetToTheLeft = 190;
		Map<Integer, String> inputs = new HashMap<Integer, String>();
		// Create input to move the paddle left paddleOffsetToTheRight pixels
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < paddleOffsetToTheLeft / 5; i++) {
			builder.append("l");
		}
		builder.append(" ");
		// At the first processed input, we simulate that the user presses 'l'
		// paddleOffsetToTheLeft times and also pauses the game
		inputs.put(1, builder.toString());

		// Provide the inputs to the test object substituting the actual UI object
		Block223PlayModeTest testInputProvider = new Block223PlayModeTest(inputs);

		Block223Controller.startGame(testInputProvider);

		assertEquals(Game.PLAY_AREA_SIDE / 2, playedGame.getCurrentBallX(), 0.00001);
		assertEquals(Game.PLAY_AREA_SIDE / 2 + 1, playedGame.getCurrentBallY(), 0.00001);
		assertEquals(game.getBall().getMinBallSpeedX(), playedGame.getBallDirectionX(), 0.00001);
		assertEquals(game.getBall().getMinBallSpeedY(), playedGame.getBallDirectionY(), 0.00001);
		assertEquals(game.getPaddle().getMaxPaddleLength(), playedGame.getCurrentPaddleLength(), 0.00001);
		assertEquals(0, playedGame.getCurrentPaddleX(), 0.00001);
		assertEquals(Game.PLAY_AREA_SIDE - Paddle.VERTICAL_DISTANCE - Paddle.PADDLE_WIDTH,
				playedGame.getCurrentPaddleY(), 0.00001);
		assertEquals(PlayedGame.PlayStatus.Paused, playedGame.getPlayStatus());
	}

	@Test(timeout = 3000)
	public void testStartGameReachGameOver() throws InvalidInputException {
		for (int i = 0; i < PlayedGame.NR_LIVES; i++) {
			testStartGameHitPaddleAndMovePaddleLeft();
		}
		assertEquals(PlayedGame.PlayStatus.GameOver, playedGame.getPlayStatus());
		assertNull(Block223Application.getCurrentPlayableGame());
	}

	@Test
	public void testStartGameUnsetCurrentUserRole() {
		Block223Application.setCurrentUserRole(null);
		try {
			Block223Controller.startGame(new Block223PlayModeTest());
			fail();
		} catch (InvalidInputException e) {
			assertEquals("Player privileges are required to play a game.", e.getMessage().trim());
		}
	}

	@Test
	public void testStartGameUnsetCurrentPlayableGame() {
		Block223Application.setCurrentPlayableGame(null);
		try {
			Block223Controller.startGame(new Block223PlayModeTest());
			fail();
		} catch (InvalidInputException e) {
			assertEquals("A game must be selected to play it.", e.getMessage().trim());
		}
	}

	@Test
	public void testStartGameNoRights() {
		Block223Application.setCurrentUserRole(admin);
		Block223Application.setCurrentPlayableGame(playedGame);
		try {
			Block223Controller.startGame(new Block223PlayModeTest());
			fail();
		} catch (InvalidInputException e) {
			assertEquals("Player privileges are required to play a game.", e.getMessage().trim());
		}
	}

	@Test
	public void testStartGameTestWithDifferentAdmin() {
		playedGame.setPlayer(null);
		Block223Application.setCurrentUserRole(new Admin(ADMIN_PASS, block223));
		Block223Application.setCurrentPlayableGame(playedGame);
		try {
			Block223Controller.startGame(new Block223PlayModeTest());
			fail();
		} catch (InvalidInputException e) {
			assertEquals("Only the admin of a game can test the game.", e.getMessage().trim());
		}
	}

	@Test
	public void testStartGameTestWithPlayer() {
		playedGame.setPlayer(null);
		Block223Application.setCurrentUserRole(player);
		Block223Application.setCurrentPlayableGame(playedGame);
		try {
			Block223Controller.startGame(new Block223PlayModeTest());
			fail();
		} catch (InvalidInputException e) {
			assertEquals("Admin privileges are required to test a game.", e.getMessage().trim());
		}
	}

	// doSetup

	@Test
	public void testDoSetupSuccess() {
		assertEquals(PlayedGame.NR_LIVES, playedGame.getLives());
		assertEquals(Game.PLAY_AREA_SIDE / 2, playedGame.getCurrentBallX(), 0.00001);
		assertEquals(Game.PLAY_AREA_SIDE / 2, playedGame.getCurrentBallY(), 0.00001);
		assertEquals(game.getBall().getMinBallSpeedX(), playedGame.getBallDirectionX(), 0.00001);
		assertEquals(game.getBall().getMinBallSpeedY(), playedGame.getBallDirectionY(), 0.00001);
		assertEquals(1, playedGame.getBlocks().size());
		assertEquals(game.getPaddle().getMaxPaddleLength(), playedGame.getCurrentPaddleLength(), 0.00001);
		assertEquals((Game.PLAY_AREA_SIDE - game.getPaddle().getMaxPaddleLength()) / 2, playedGame.getCurrentPaddleX(),
				0.00001);
		assertEquals(Game.PLAY_AREA_SIDE - Paddle.VERTICAL_DISTANCE - Paddle.PADDLE_WIDTH,
				playedGame.getCurrentPaddleY(), 0.00001);
	}

	@Test
	public void testDoSetupRandomBlocksSuccess() {
		int nrOfBlocks = 3;
		game.setNrBlocksPerLevel(nrOfBlocks);
		PlayedGame playedGame2 = new PlayedGame(USER_PASS, game, block223);
		assertEquals(nrOfBlocks, playedGame2.getBlocks().size());
	}

	// getCurrentPlayableGame

	@Test
	public void testGetCurrentPlayableGameSuccess() throws InvalidInputException {
		TOCurrentlyPlayedGame cpg = Block223Controller.getCurrentPlayableGame();

		assertEquals(game.getName(), cpg.getGamename());
		assertTrue(cpg.isPaused());
		assertEquals(0, cpg.getScore());
		assertEquals(PlayedGame.NR_LIVES, cpg.getLives());
		assertEquals(1, cpg.getCurrentLevel());
		assertEquals(USER_NAME, cpg.getPlayername());
		assertEquals(1, cpg.getBlocks().size());
		assertEquals(Game.PLAY_AREA_SIDE / 2, cpg.getCurrentBallX(), 0.00001);
		assertEquals(Game.PLAY_AREA_SIDE / 2, cpg.getCurrentBallY(), 0.00001);
		assertEquals(game.getPaddle().getMaxPaddleLength(), cpg.getCurrentPaddleLength(), 0.00001);
		assertEquals((Game.PLAY_AREA_SIDE - game.getPaddle().getMaxPaddleLength()) / 2, cpg.getCurrentPaddleX(),
				0.00001);
	}

	@Test
	public void testGetCurrentPlayableGameUnsetCurrentUserRole() {
		Block223Application.setCurrentUserRole(null);
		try {
			Block223Controller.getCurrentPlayableGame();
			fail();
		} catch (InvalidInputException e) {
			assertEquals("Player privileges are required to play a game.", e.getMessage().trim());
		}
	}

	@Test
	public void testGetCurrentPlayableGameUnsetCurrentPlayableGame() {
		Block223Application.setCurrentPlayableGame(null);
		try {
			Block223Controller.getCurrentPlayableGame();
			fail();
		} catch (InvalidInputException e) {
			assertEquals("A game must be selected to play it.", e.getMessage().trim());
		}
	}

	@Test
	public void testGetCurrentPlayableGameNoRights() {
		Block223Application.setCurrentUserRole(admin);
		try {
			Block223Controller.getCurrentPlayableGame();
			fail();
		} catch (InvalidInputException e) {
			assertEquals("Player privileges are required to play a game.", e.getMessage().trim());
		}
	}

	@Test
	public void testGetCurrentPlayableGameTestWithDifferentAdmin() {
		playedGame.setPlayer(null);
		Block223Application.setCurrentUserRole(new Admin(ADMIN_PASS, block223));
		Block223Application.setCurrentPlayableGame(playedGame);
		try {
			Block223Controller.getCurrentPlayableGame();
			fail();
		} catch (InvalidInputException e) {
			assertEquals("Only the admin of a game can test the game.", e.getMessage().trim());
		}
	}

	@Test
	public void testGetCurrentPlayableGameTestWithPlayer() {
		playedGame.setPlayer(null);
		Block223Application.setCurrentUserRole(player);
		Block223Application.setCurrentPlayableGame(playedGame);
		try {
			Block223Controller.getCurrentPlayableGame();
			fail();
		} catch (InvalidInputException e) {
			assertEquals("Admin privileges are required to test a game.", e.getMessage().trim());
		}
	}

}
