package ca.mcgill.ecse223.block.statemachine.tests.p05;

import static ca.mcgill.ecse223.block.util.Block223TestConstants.USER_PASS;
import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import ca.mcgill.ecse223.block.controller.Block223Controller;
import ca.mcgill.ecse223.block.controller.InvalidInputException;
import ca.mcgill.ecse223.block.model.Admin;
import ca.mcgill.ecse223.block.model.Block223;
import ca.mcgill.ecse223.block.model.Game;
import ca.mcgill.ecse223.block.model.Paddle;
import ca.mcgill.ecse223.block.model.PlayedGame;
import ca.mcgill.ecse223.block.model.Player;
import ca.mcgill.ecse223.block.statemachine.tests.Block223PlayModeTest;
import ca.mcgill.ecse223.block.util.Block223TestUtil;

public class BallIsOutOfBoundsTests {

	private Game game;
	private Block223 block223;
	private Admin admin;
	private Player player;
	private PlayedGame playedGame;

	@Before
	public void createGame() {
		block223 = Block223TestUtil.initializeBlock223();
		admin = Block223TestUtil.createAndAssignAdminRoleToBlock223(block223);
		game = Block223TestUtil.initializeGame(1, 0, 1, 20, 10, block223, admin);
		Block223TestUtil.initializeBlockWithAssignmentAndPublishGame(game);
		player = new Player(USER_PASS, block223);
		playedGame = Block223TestUtil.initializePlayedGame(game, player, block223);
	}

	@Test(timeout = 3000)
	public void testBallIsOutOfBounds() throws InvalidInputException {
		Block223PlayModeTest uiMock = new Block223PlayModeTest();

		positionBallAndStartGame(uiMock);

		assertEquals(PlayedGame.NR_LIVES - 1, playedGame.getLives());
		assertEquals(Game.PLAY_AREA_SIDE / 2, playedGame.getCurrentBallX(), 0.00001);
		assertEquals(Game.PLAY_AREA_SIDE / 2, playedGame.getCurrentBallY(), 0.00001);
		assertEquals(game.getBall().getMinBallSpeedX(), playedGame.getBallDirectionX(), 0.00001);
		assertEquals(game.getBall().getMinBallSpeedY(), playedGame.getBallDirectionY(), 0.00001);
		assertEquals(game.getPaddle().getMaxPaddleLength(), playedGame.getCurrentPaddleLength(), 0.00001);
		assertEquals((Game.PLAY_AREA_SIDE - game.getPaddle().getMaxPaddleLength()) / 2, playedGame.getCurrentPaddleX(), 0.00001);
		assertEquals(Game.PLAY_AREA_SIDE - Paddle.VERTICAL_DISTANCE - Paddle.PADDLE_WIDTH, playedGame.getCurrentPaddleY(), 0.00001);

		positionBallAndStartGame(uiMock);

		positionBallAndStartGame(uiMock);

		assertEquals(PlayedGame.PlayStatus.GameOver, playedGame.getPlayStatus());
		assertEquals(1, game.getHallOfFameEntries().size());
	}

	private void positionBallAndStartGame(Block223PlayModeTest uiMock) throws InvalidInputException {
		playedGame.setCurrentBallX(195);
		playedGame.setCurrentBallY(384);
		Block223Controller.startGame(uiMock);
	}

}
