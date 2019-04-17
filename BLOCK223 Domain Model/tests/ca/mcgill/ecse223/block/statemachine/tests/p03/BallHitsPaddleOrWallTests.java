package ca.mcgill.ecse223.block.statemachine.tests.p03;

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

public class BallHitsPaddleOrWallTests {

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

	@Test(timeout = 2000)
	public void testHitPaddleAndHitTopWall() throws InvalidInputException {
		int defaultDirectionY = game.getBall().getMinBallSpeedY();

		Map<Integer, String> playerInput = new HashMap<>();
		// Pause the game just after the ball has bounced on the paddle
		playerInput.put((155 + 1), " ");
		// Pause the game just after the ball has bounced on the top wall
		// Explanation on the added numbers beyond 155 + 1:
		// * + 1 is needed because a takeInput() is discarded once the game is resumed
		// * + 344 represents the ball needs to travel until hitting the wall (1 pixel
		// out of 345 was done before pausing)
		// * + 1 is for actually bouncing back
		playerInput.put((155 + 1 + 1 + 344 + 1), " ");

		Block223PlayModeTest uiMock = new Block223PlayModeTest(playerInput);
		Block223Controller.startGame(uiMock);

		// hitPaddle
		assertEquals(0.1, playedGame.getBallDirectionX(), 0.00001);
		assertEquals((-1) * defaultDirectionY, playedGame.getBallDirectionY(), 0.00001);
		assertEquals(195 + 0.1, playedGame.getCurrentBallX(), 0.00001);
		assertEquals(195 + 154, playedGame.getCurrentBallY(), 0.00001);

		Block223Controller.startGame(uiMock);

		// hitWall
		assertEquals(0.2, playedGame.getBallDirectionX(), 0.00001);
		assertEquals(defaultDirectionY, playedGame.getBallDirectionY(), 0.00001);
		assertEquals(195 + 34.5 + 0.2, playedGame.getCurrentBallX(), 0.00001);
		assertEquals(6, playedGame.getCurrentBallY(), 0.00001);
		assertEquals(PlayedGame.PlayStatus.Paused, playedGame.getPlayStatus());
	}

	// bounce on paddle

	@Test
	public void testHitPaddleZoneB() throws InvalidInputException {
		double x = 179;
		double y = 356;
		double dirX = 1;
		double dirY = 1;
		perpareBallAndStepGameTwice(x, y, dirX, dirY);

		verifyBounce(x, y + 2 + dirX / 10, -dirX, computeNewDir(dirY, dirX));
	}

	@Test
	public void testHitPaddleZoneERight() throws InvalidInputException {
		double x = 181.5;
		double y = 351;
		double dirX = -0.5;
		double dirY = 1;
		perpareBallAndStepGameTwice(x, y, dirX, dirY);

		verifyBounce(x - 1 - dirY / 10, y, computeNewDir(dirX, dirY), -dirY);
	}

	@Test
	public void testHitPaddleZoneELeft() throws InvalidInputException {
		double x = 180;
		double y = 352.5;
		double dirX = 1;
		double dirY = -0.5;
		perpareBallAndStepGameTwice(x, y, dirX, dirY);

		verifyBounce(x, y - 1 - dirX / 10, -dirX, computeNewDir(dirY, dirX));
	}

	@Test
	public void testHitPaddleZoneA() throws InvalidInputException {
		double x = 194;
		double y = 349;
		double dirX = 1;
		double dirY = 1;
		perpareBallAndStepGameTwice(x, y, dirX, dirY);

		verifyBounce(x + 2 + dirY / 10, y, computeNewDir(dirX, dirY), -dirY);
	}

	@Test
	public void testHitPaddleZoneFRight() throws InvalidInputException {
		double x = 210;
		double y = 351.5;
		double dirX = -1;
		double dirY = 0.5;
		perpareBallAndStepGameTwice(x, y, dirX, dirY);

		verifyBounce(x, y + 1 - dirX / 10, -dirX, computeNewDir(dirY, dirX));
	}

	@Test
	public void testHitPaddleZoneFLeft() throws InvalidInputException {
		double x = 208.5;
		double y = 351;
		double dirX = 0.5;
		double dirY = 1;
		perpareBallAndStepGameTwice(x, y, dirX, dirY);

		verifyBounce(x + 1 + dirY / 10, y, computeNewDir(dirX, dirY), -dirY);
	}

	@Test
	public void testHitPaddleZoneC() throws InvalidInputException {
		double x = 211;
		double y = 356;
		double dirX = -1;
		double dirY = 1;
		perpareBallAndStepGameTwice(x, y, dirX, dirY);

		verifyBounce(x, y + 2 - dirX / 10, -dirX, computeNewDir(dirY, dirX));
	}

	// bounce on wall

	@Test
	public void testHitLeftWall() throws InvalidInputException {
		double x = 6;
		double y = 195;
		double dirX = -1;
		double dirY = 1;
		perpareBallAndStepGameTwice(x, y, dirX, dirY);

		verifyBounce(x, y + 2 - dirX / 10, -dirX, computeNewDir(dirY, dirX));
	}

	@Test
	public void testHitTopLeftCornerWall() throws InvalidInputException {
		double x = 6;
		double y = 6;
		double dirX = -1;
		double dirY = -1;
		perpareBallAndStepGameTwice(x, y, dirX, dirY);

		verifyBounce(6, 6, 1, 1);
	}

	@Test
	public void testHitTopWall() throws InvalidInputException {
		double x = 100;
		double y = 6;
		double dirX = 1;
		double dirY = -1;
		perpareBallAndStepGameTwice(x, y, dirX, dirY);

		verifyBounce(x + 2 - dirY / 10, y, computeNewDir(dirX, dirY), -dirY);
	}

	@Test
	public void testHitTopRightCornerWall() throws InvalidInputException {
		double x = 384;
		double y = 6;
		double dirX = 1;
		double dirY = -1;
		perpareBallAndStepGameTwice(x, y, dirX, dirY);

		verifyBounce(384, 6, -1, 1);
	}

	@Test
	public void testHitRightWall() throws InvalidInputException {
		double x = 384;
		double y = 195;
		double dirX = 1;
		double dirY = 1;
		perpareBallAndStepGameTwice(x, y, dirX, dirY);

		verifyBounce(x, y + 2 + dirX / 10, -dirX, computeNewDir(dirY, dirX));
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
