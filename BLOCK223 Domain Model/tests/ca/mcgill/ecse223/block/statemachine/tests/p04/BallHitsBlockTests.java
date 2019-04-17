package ca.mcgill.ecse223.block.statemachine.tests.p04;

import static ca.mcgill.ecse223.block.util.Block223TestConstants.USER_PASS;
import static ca.mcgill.ecse223.block.util.Block223TestConstants.BALL_SPEED_INCREASE_FACTOR;
import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import ca.mcgill.ecse223.block.controller.Block223Controller;
import ca.mcgill.ecse223.block.controller.InvalidInputException;
import ca.mcgill.ecse223.block.model.Admin;
import ca.mcgill.ecse223.block.model.Block;
import ca.mcgill.ecse223.block.model.Block223;
import ca.mcgill.ecse223.block.model.BlockAssignment;
import ca.mcgill.ecse223.block.model.Game;
import ca.mcgill.ecse223.block.model.Paddle;
import ca.mcgill.ecse223.block.model.PlayedGame;
import ca.mcgill.ecse223.block.model.Player;
import ca.mcgill.ecse223.block.statemachine.tests.Block223PlayModeTest;
import ca.mcgill.ecse223.block.util.Block223TestUtil;

public class BallHitsBlockTests {

	private static final int POINTS = 1;
	private Game game;
	private Block223 block223;
	private Admin admin;
	private Player player;
	private Block block;
	private PlayedGame playedGame;

	private void createGame(int nrLevels, int nrBlockAssignments) {
		block223 = Block223TestUtil.initializeBlock223();
		admin = Block223TestUtil.createAndAssignAdminRoleToBlock223(block223);
		game = Block223TestUtil.initializeGame(nrLevels, nrBlockAssignments, 0, 1, 20, 10, block223, admin);
		block = new Block(1, 1, 1, POINTS, game);
		// Block that is to be hit + an extra block if needed
		for (int i = 0; i < nrBlockAssignments; i++) {
			new BlockAssignment(9 + i, 1, game.getLevel(0), block, game);
		}
		game.setPublished(true);
		player = new Player(USER_PASS, block223);
		playedGame = Block223TestUtil.initializePlayedGame(game, player, block223);
	}

	private void createGameForBounceChecks() {
		block223 = Block223TestUtil.initializeBlock223();
		admin = Block223TestUtil.createAndAssignAdminRoleToBlock223(block223);
		game = Block223TestUtil.initializeGame(1, 2, 0, 1, 20, 10, block223, admin);
		block = new Block(1, 1, 1, POINTS, game);
		// Block that is to be hit + an extra block if needed
		new BlockAssignment(9, 2, game.getLevel(0), block, game);
		new BlockAssignment(5, 5, game.getLevel(0), block, game);
		game.setPublished(true);
		player = new Player(USER_PASS, block223);
		playedGame = Block223TestUtil.initializePlayedGame(game, player, block223);
	}

	@Test(timeout = 1000)
	public void testHitLastBlockAndLastLevel() throws InvalidInputException {
		createGame(1, 1);

		Block223PlayModeTest uiMock = new Block223PlayModeTest();
		Block223Controller.startGame(uiMock);

		assertEquals(POINTS, playedGame.getScore());
		assertEquals(PlayedGame.PlayStatus.GameOver, playedGame.getPlayStatus());
		assertEquals(1, game.getHallOfFameEntries().size());
		assertEquals(POINTS, game.getMostRecentEntry().getScore());
	}

	@Test(timeout = 2000)
	public void testHitLastBlockAndNotLastLevel() throws InvalidInputException {
		createGame(2, 1);

		Block223PlayModeTest uiMock = new Block223PlayModeTest();
		Block223Controller.startGame(uiMock);

		assertEquals(POINTS, playedGame.getScore());
		assertEquals(PlayedGame.PlayStatus.Ready, playedGame.getPlayStatus());
		assertEquals(2, playedGame.getCurrentLevel());
		assertEquals(PlayedGame.NR_LIVES, playedGame.getLives());
		assertEquals(Game.PLAY_AREA_SIDE / 2, playedGame.getCurrentBallX(), 0.00001);
		assertEquals(Game.PLAY_AREA_SIDE / 2, playedGame.getCurrentBallY(), 0.00001);
		assertEquals(game.getBall().getMinBallSpeedX(), playedGame.getBallDirectionX(), 0.00001);
		assertEquals(game.getBall().getMinBallSpeedY(), playedGame.getBallDirectionY(), 0.00001);
		assertEquals(PlayedGame.INITIAL_WAIT_TIME * BALL_SPEED_INCREASE_FACTOR, playedGame.getWaitTime(), 0.00001);
		assertEquals(game.getPaddle().getMinPaddleLength(), playedGame.getCurrentPaddleLength(), 0.00001);
		assertEquals((Game.PLAY_AREA_SIDE - game.getPaddle().getMinPaddleLength()) / 2, playedGame.getCurrentPaddleX(),
				0.00001);
		assertEquals(Game.PLAY_AREA_SIDE - Paddle.VERTICAL_DISTANCE - Paddle.PADDLE_WIDTH,
				playedGame.getCurrentPaddleY(), 0.00001);
	}

	@Test(timeout = 1000)
	public void testHitNotLastBlockOnLevel() throws InvalidInputException {
		createGame(1, 2);

		Map<Integer, String> userInput = new HashMap<>();
		// Let the ball fall down to bounce from the paddle, then
		// let it hit a block, then
		// wait one iteration to see if it actually bounced back and pause the game
		userInput.put((155 + 315 + 1), " ");
		Block223PlayModeTest uiMock = new Block223PlayModeTest(userInput);
		Block223Controller.startGame(uiMock);

		assertEquals(POINTS, playedGame.getScore());
		assertEquals(PlayedGame.PlayStatus.Paused, playedGame.getPlayStatus());
		assertEquals(1, playedGame.getCurrentLevel());
		assertEquals(PlayedGame.NR_LIVES, playedGame.getLives());
		assertEquals((195 + 31.5 + 0.2), playedGame.getCurrentBallX(), 0.00001);
		assertEquals((10 + 20 + 5 + 1), playedGame.getCurrentBallY(), 0.00001);
		assertEquals(0.2, playedGame.getBallDirectionX(), 0.00001);
		assertEquals(1, playedGame.getBallDirectionY(), 0.00001);
		assertEquals(0.0, playedGame.getWaitTime(), 0.00001);
		assertEquals(1, playedGame.getBlocks().size());
		assertEquals(game.getPaddle().getMaxPaddleLength(), playedGame.getCurrentPaddleLength(), 0.00001);
		assertEquals((Game.PLAY_AREA_SIDE - game.getPaddle().getMaxPaddleLength()) / 2, playedGame.getCurrentPaddleX(),
				0.00001);
		assertEquals(Game.PLAY_AREA_SIDE - Paddle.VERTICAL_DISTANCE - Paddle.PADDLE_WIDTH,
				playedGame.getCurrentPaddleY(), 0.00001);
	}

	// bounce checks

	@Test(timeout = 1000)
	public void testHitBlockZoneB() throws InvalidInputException {
		createGameForBounceChecks();

		double x = 204;
		double y = 33;
		double dirX = 1;
		double dirY = 1;
		perpareBallAndStepGameTwice(x, y, dirX, dirY);

		verifyBounce(x, y + 2 + dirX / 10, -dirX, computeNewDir(dirY, dirX));
	}

	@Test
	public void testHitBlockZoneERight() throws InvalidInputException {
		createGameForBounceChecks();

		double x = 206.5;
		double y = 28;
		double dirX = -0.5;
		double dirY = 1;
		perpareBallAndStepGameTwice(x, y, dirX, dirY);

		verifyBounce(x - 1 - dirY / 10, y, computeNewDir(dirX, dirY), -dirY);
	}

	@Test
	public void testHitBlockZoneELeft() throws InvalidInputException {
		createGameForBounceChecks();

		double x = 205;
		double y = 29.5;
		double dirX = 1;
		double dirY = -0.5;
		perpareBallAndStepGameTwice(x, y, dirX, dirY);

		verifyBounce(x, y - 1 - dirX / 10, -dirX, computeNewDir(dirY, dirX));
	}

	@Test
	public void testHitBlockZoneA() throws InvalidInputException {
		createGameForBounceChecks();

		double x = 219;
		double y = 26;
		double dirX = 1;
		double dirY = 1;
		perpareBallAndStepGameTwice(x, y, dirX, dirY);

		verifyBounce(x + 2 + dirY / 10, y, computeNewDir(dirX, dirY), -dirY);
	}

	@Test
	public void testHitBlockZoneFRight() throws InvalidInputException {
		createGameForBounceChecks();

		double x = 235;
		double y = 28.5;
		double dirX = -1;
		double dirY = 0.5;
		perpareBallAndStepGameTwice(x, y, dirX, dirY);

		verifyBounce(x, y + 1 - dirX / 10, -dirX, computeNewDir(dirY, dirX));
	}

	@Test
	public void testHitBlockZoneFLeft() throws InvalidInputException {
		createGameForBounceChecks();

		double x = 233.5;
		double y = 28;
		double dirX = 0.5;
		double dirY = 1;
		perpareBallAndStepGameTwice(x, y, dirX, dirY);

		verifyBounce(x + 1 + dirY / 10, y, computeNewDir(dirX, dirY), -dirY);
	}

	@Test
	public void testHitBlockZoneC() throws InvalidInputException {
		createGameForBounceChecks();

		double x = 236;
		double y = 33;
		double dirX = -1;
		double dirY = 1;
		perpareBallAndStepGameTwice(x, y, dirX, dirY);

		verifyBounce(x, y + 2 - dirX / 10, -dirX, computeNewDir(dirY, dirX));
	}

	@Test
	public void testHitBlockZoneHRight() throws InvalidInputException {
		createGameForBounceChecks();

		double x = 235;
		double y = 54.5;
		double dirX = -1;
		double dirY = 0.5;
		perpareBallAndStepGameTwice(x, y, dirX, dirY);

		verifyBounce(x, y + 1 - dirX / 10, -dirX, computeNewDir(dirY, dirX));
	}

	@Test
	public void testHitBlockZoneHLeft() throws InvalidInputException {
		createGameForBounceChecks();

		double x = 233.5;
		double y = 56;
		double dirX = 0.5;
		double dirY = -1;
		perpareBallAndStepGameTwice(x, y, dirX, dirY);

		verifyBounce(x + 1 - dirY / 10, y, computeNewDir(dirX, dirY), -dirY);
	}

	@Test
	public void testHitBlockZoneD() throws InvalidInputException {
		createGameForBounceChecks();

		double x = 221;
		double y = 58;
		double dirX = -1;
		double dirY = -1;
		perpareBallAndStepGameTwice(x, y, dirX, dirY);

		verifyBounce(x - 2 + dirY / 10, y, computeNewDir(dirX, dirY), -dirY);
	}

	@Test
	public void testHitBlockZoneGRight() throws InvalidInputException {
		createGameForBounceChecks();

		double x = 206.5;
		double y = 56;
		double dirX = -0.5;
		double dirY = -1;
		perpareBallAndStepGameTwice(x, y, dirX, dirY);

		verifyBounce(x - 1 + dirY / 10, y, computeNewDir(dirX, dirY), -dirY);
	}

	@Test
	public void testHitBlockZoneGLeft() throws InvalidInputException {
		createGameForBounceChecks();

		double x = 205;
		double y = 55.5;
		double dirX = 1;
		double dirY = -0.5;
		perpareBallAndStepGameTwice(x, y, dirX, dirY);

		verifyBounce(x, y - 1 - dirX / 10, -dirX, computeNewDir(dirY, dirX));
	}

	// extracted methods

	private double computeNewDir(double dir1, double dir2) {
		return dir1 + Math.signum(dir1) * 0.1 * Math.abs(dir2);
	}

	private void verifyBounce(double expectedX, double expectedY, double expectedDirX, double expectedDirY) {
		assertEquals(expectedDirX, playedGame.getBallDirectionX(), 0.00001);
		assertEquals(expectedDirY, playedGame.getBallDirectionY(), 0.00001);
		assertEquals(expectedX, playedGame.getCurrentBallX(), 0.00001);
		assertEquals(expectedY, playedGame.getCurrentBallY(), 0.00001);
		assertEquals(PlayedGame.PlayStatus.Paused, playedGame.getPlayStatus());
	}

	private void perpareBallAndStepGameTwice(double x, double y, double dirX, double dirY)
			throws InvalidInputException {
		playedGame.setCurrentBallX(x);
		playedGame.setCurrentBallY(y);
		playedGame.setBallDirectionX(dirX);
		playedGame.setBallDirectionY(dirY);
		Map<Integer, String> inputs = new HashMap<>();
		inputs.put(2, " ");
		Block223PlayModeTest uiMock = new Block223PlayModeTest(inputs);
		Block223Controller.startGame(uiMock);
	}

}
