package ca.mcgill.ecse223.block.util;

import static ca.mcgill.ecse223.block.util.Block223TestConstants.ADMIN_PASS;
import static ca.mcgill.ecse223.block.util.Block223TestConstants.BALL_SPEED_INCREASE_FACTOR;
import static ca.mcgill.ecse223.block.util.Block223TestConstants.BLOCKS_PER_LEVEL;
import static ca.mcgill.ecse223.block.util.Block223TestConstants.LEVELS;
import static ca.mcgill.ecse223.block.util.Block223TestConstants.MAX_PADDLE_LENGTH;
import static ca.mcgill.ecse223.block.util.Block223TestConstants.MIN_BALL_SPEED_X;
import static ca.mcgill.ecse223.block.util.Block223TestConstants.MIN_BALL_SPEED_Y;
import static ca.mcgill.ecse223.block.util.Block223TestConstants.MIN_PADDLE_LENGTH;
import static ca.mcgill.ecse223.block.util.Block223TestConstants.TEST_GAME_NAME_1;
import static ca.mcgill.ecse223.block.util.Block223TestConstants.USER_NAME;

import ca.mcgill.ecse223.block.application.Block223Application;
import ca.mcgill.ecse223.block.model.Admin;
import ca.mcgill.ecse223.block.model.Block;
import ca.mcgill.ecse223.block.model.Block223;
import ca.mcgill.ecse223.block.model.BlockAssignment;
import ca.mcgill.ecse223.block.model.Game;
import ca.mcgill.ecse223.block.model.PlayedGame;
import ca.mcgill.ecse223.block.model.Player;
import ca.mcgill.ecse223.block.model.User;

public class Block223TestUtil {

	public static Block223 initializeBlock223() {
		Block223 block223 = Block223Application.getBlock223();
		block223.delete();
		return block223;
	}

	public static Admin createAndAssignAdminRoleToBlock223(Block223 block223) {
		Admin testAdminRole = new Admin(ADMIN_PASS, block223);
		Block223Application.setCurrentUserRole(testAdminRole);
		return testAdminRole;
	}

	public static Game initializeGame(Block223 block223, Admin admin) {
		Game aGame = new Game(TEST_GAME_NAME_1, BLOCKS_PER_LEVEL, admin, MIN_BALL_SPEED_X, MIN_BALL_SPEED_Y,
				BALL_SPEED_INCREASE_FACTOR, MAX_PADDLE_LENGTH, MIN_PADDLE_LENGTH, block223);
		for (int i = 0; i < LEVELS; i++) {
			aGame.addLevel();
		}
		Block223Application.setCurrentGame(aGame);
		return aGame;
	}

	public static Game initializeGame(int levels, int minBallSpeedX, int minBallSpeedy, int maxPaddleLength,
			int minPaddleLength, Block223 block223, Admin admin) {
		return initializeGame(levels, BLOCKS_PER_LEVEL, minBallSpeedX, minBallSpeedy, maxPaddleLength, minPaddleLength,
				block223, admin);
	}

	public static Game initializeGame(int levels, int nrBlocksPerLevel, int minBallSpeedX, int minBallSpeedy,
			int maxPaddleLength, int minPaddleLength, Block223 block223, Admin admin) {
		Game aGame = new Game(TEST_GAME_NAME_1, nrBlocksPerLevel, admin, minBallSpeedX, minBallSpeedy,
				BALL_SPEED_INCREASE_FACTOR, maxPaddleLength, minPaddleLength, block223);
		for (int i = 0; i < levels; i++) {
			aGame.addLevel();
		}
		Block223Application.setCurrentGame(aGame);
		return aGame;
	}

	public static Block initializeBlockWithAssignmentAndPublishGame(Game game) {
		Block block = new Block(1, 1, 1, 1, game);
		new BlockAssignment(1, 1, game.getLevel(0), block, game);
		game.setPublished(true);
		return block;
	}

	public static PlayedGame initializePlayedGame(Game game, Player player, Block223 block223) {
		PlayedGame playedGame = new PlayedGame(USER_NAME, game, block223);
		new User(USER_NAME, block223, player);
		playedGame.setPlayer(player);
		playedGame.setWaitTime(0);
		Block223Application.setCurrentUserRole(player);
		Block223Application.setCurrentPlayableGame(playedGame);
		return playedGame;
	}

}
