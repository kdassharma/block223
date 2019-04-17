package ca.mcgill.ecse223.block.controller;

import ca.mcgill.ecse223.block.application.Block223Application;
import ca.mcgill.ecse223.block.model.*;
import ca.mcgill.ecse223.block.model.PlayedGame.PlayStatus;
import ca.mcgill.ecse223.block.persistence.Block223Persistence;
import ca.mcgill.ecse223.block.view.Block223PlayModeInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Block223Controller {

	// ****************************
	// Modifier methods
	// ****************************
	public static void createGame(String name) throws InvalidInputException {
		
		Block223 b223 = Block223Application.getBlock223();
		UserRole admin = Block223Application.getCurrentUserRole();
		
		if (!(admin instanceof Admin)) {
			
			throw new InvalidInputException("Admin privileges are required to create a game.");
			
		}
		
		try {
			
			 Game game = new Game(name, 1,(Admin)admin, 1,1,1,10,10,b223);
		}
		catch (RuntimeException e) {
			throw new InvalidInputException(e.getMessage());
		}
		
		
	}

	public static void setGameDetails(int nrLevels, int nrBlocksPerLevel, int minBallSpeedX, int minBallSpeedY,
			Double ballSpeedIncreaseFactor, int maxPaddleLength, int minPaddleLength) throws InvalidInputException {
		
		Game currentGame = Block223Application.getCurrentGame();
		UserRole admin = Block223Application.getCurrentUserRole();
		
		if (!(admin instanceof Admin)) {
			
			throw new InvalidInputException("Admin privileges are required to define game settings.");
			
		}
		
		if (currentGame == null) {
			
			throw new InvalidInputException("A game must be selected to define game settings.");
			
		}
		
		if (currentGame.getAdmin() != (Admin)admin) {
			
			throw new InvalidInputException("Only the admin who created the game can define its game settings.");
			
		}
		
		if (nrLevels < 1 || nrLevels > 99) {
			
			throw new InvalidInputException("The number of levels must be between 1 and 99.");
			
		}
		
		if (nrBlocksPerLevel > 225) {
			
			throw new InvalidInputException("The number of blocks per level must be less than 255.");
			
		}
		
		
		try {
			
			currentGame.setNrBlocksPerLevel(nrBlocksPerLevel);
			currentGame.getBall().setMinBallSpeedX(minBallSpeedX);
			currentGame.getBall().setMinBallSpeedY(minBallSpeedY);
			currentGame.getBall().setBallSpeedIncreaseFactor(ballSpeedIncreaseFactor);
			currentGame.getPaddle().setMaxPaddleLength(maxPaddleLength);
			currentGame.getPaddle().setMinPaddleLength(minPaddleLength);
			
			
			
		} catch (RuntimeException e) {
			
			throw new InvalidInputException(e.getMessage());
			
		}
		
		List<Level> levels  = currentGame.getLevels();
		
		int size = levels.size();
		
		while (nrLevels > size) {
			
			currentGame.addLevel();
			size = levels.size();
			
		}
		
		while (nrLevels < size) {
			
			currentGame.getLevel(size - 1).delete();
			size = levels.size();
			
		}
		
	}

	public static void deleteGame(String name) throws InvalidInputException {
		if (!(Block223Application.getCurrentUserRole() instanceof Admin)) {
			throw new InvalidInputException("Admin privileges are required to delete a game.");
		}
		if (!(Block223Application.getCurrentUserRole().equals(Block223Application.getCurrentGame().getAdmin()))) {
			throw new InvalidInputException("Only the admin who created the game can delete the game.");
		}
		
		try {
			Block223 block223 = Block223Application.getBlock223();
			Game game = block223.findGame(name);
			/*if(game.getPublished())
				throw new InvalidInputException("A published game cannot be deleted.");*/
			if (game != null) {
				if(game.getPublished())
					throw new InvalidInputException("A published game cannot be deleted.");
				game.delete();
			}
			Block223Application.setCurrentGame(null); // TODO check this
			Block223Persistence.save(block223);
		}
		catch (Exception e) {
			
			throw e;

		}
	}

	public static void selectGame(String name) throws InvalidInputException {
		UserRole userRole = Block223Application.getCurrentUserRole();
		if (!(userRole instanceof Admin)) {
			throw new InvalidInputException("Admin privileges are required to select a game.");
		}
		Game game = Block223Application.getBlock223().findGame(name);
		if (game == null) {
			throw new InvalidInputException("A game with name " + name + " does not exist.");
		}
		if(Block223Application.getCurrentGame()!=null && Block223Application.getCurrentGame().getPublished())
			throw new InvalidInputException("A published game cannot be changed.");
		Admin admin = game.getAdmin();
		if (!(admin.equals(userRole))) {
			throw new InvalidInputException("Only the admin who created the game can select the game.");
		}
		try {
			Block223Application.setCurrentGame(game);
		}
		catch (Exception e) {
			throw e;
		}
	}
	public static void updateGame(String name, int nrLevels, int nrBlocksPerLevel, int minBallSpeedX, int minBallSpeedY,
								  Double ballSpeedIncreaseFactor, int maxPaddleLength, int minPaddleLength) throws InvalidInputException {
		if (!(Block223Application.getCurrentUserRole() instanceof Admin)) {
			throw new InvalidInputException("Admin privileges are required to define game settings.");
		}
		if (Block223Application.getCurrentGame() == null) {
			throw new InvalidInputException("A game must be selected to define game settings.");
		}
		if (!(Block223Application.getCurrentUserRole().equals(Block223Application.getCurrentGame().getAdmin()))) {
			throw new InvalidInputException("Only the admin who created the game can define its game settings.");
		}
		if(Block223Application.getCurrentGame().getPublished()) {
			throw new InvalidInputException("A published game cannot be changed.");

		}
		Block223 block223 = Block223Application.getBlock223();
		String currentGameName = Block223Application.getCurrentGame().getName();
		
		for(Game game : block223.getGames())
		{
			if(currentGameName.equals(name)) {
				continue;
			}
			if(name == game.getName())
			{
				throw new InvalidInputException("The name of a game must be unique.");
			}
		}
		/*
		String currentGameName = Block223Application.getCurrentGame().getName();
		if (currentGameName == name) {
			throw new InvalidInputException("The name of a game must be unique.");
		}*/
		

		if (name == null || name == "") {
			throw new InvalidInputException("The name of a game must be specified.");
		}

		if (!(Block223Application.getCurrentGame().setName(name))) {
			throw new InvalidInputException("The name of a game must be unique.");
		}

		try {

			Block223Controller.setGameDetails(nrLevels, nrBlocksPerLevel, minBallSpeedX, minBallSpeedY, ballSpeedIncreaseFactor, maxPaddleLength, minPaddleLength);
		}
		catch (Exception e) {
			throw e;
		}
	}

	public static void addBlock(int red, int green, int blue, int points) throws InvalidInputException {
		
		// validation checks
		UserRole userRole = Block223Application.getCurrentUserRole();
		if(!(userRole instanceof Admin)) {
			throw new InvalidInputException("Admin privileges are required to add a block.");
		}
		
		Game game = Block223Application.getCurrentGame();
		if(game == null) {
			throw new InvalidInputException("A game must be selected to add a block.");
		}
	
		if(!userRole.equals(game.getAdmin())) {
			throw new InvalidInputException("Only the admin who created the game can add a block.");
		}
		
		for (Block block : game.getBlocks()) {
			if (block.getRed() == red && block.getBlue() == blue && block.getGreen() == green) {
				throw new InvalidInputException("A block with the same color already exists for the game.");
			}
		}
			
		try {
			game.addBlock(red, green, blue, points);
			
		}
		catch (RuntimeException e) {
			throw new InvalidInputException(e.getMessage());
		}
	}

	public static void deleteBlock(int id) throws InvalidInputException {
		
		// validation checks
		UserRole userRole = Block223Application.getCurrentUserRole();
		if(!(userRole instanceof Admin)) {
			throw new InvalidInputException("Admin privileges are required to delete a block.");
		}
		
		Game game = Block223Application.getCurrentGame();
		if(game == null) {
			throw new InvalidInputException("A game must be selected to delete a block.");
		}
		
		if(!userRole.equals(game.getAdmin())) {
			throw new InvalidInputException("Only the admin who created the game can delete a block.");
		}
		
		Block block = findBlock(id);

		if(block == null)
			return;
		else {
			block.delete();
		}
	}
	
	// Helper method to find a block given an ID
	private static Block findBlock(int id) {
		Block foundBlock = null;
		Game game = Block223Application.getCurrentGame();
		for (Block block : game.getBlocks()) {
			if (block.getId() == id) {
				foundBlock = block;
				break;
			}
		}
		return foundBlock;
	}
	
	public static void updateBlock(int id, int red, int green, int blue, int points) throws InvalidInputException {
		
		UserRole currentUser = Block223Application.getCurrentUserRole();
		//instance of checks to see if the user is an admin
		UserRole userRole = Block223Application.getCurrentUserRole();
		if(!(userRole instanceof Admin)) {
			throw new InvalidInputException("Admin privileges are required to update a block.");
		}
		Game currentGame = Block223Application.getCurrentGame();
		if(currentGame == null) {
			throw new InvalidInputException("A game must be selected to update a block.");
		}
		/*
		if(!userRole.equals(currentGame.getAdmin())) {
			throw new InvalidInputException("Only the admin who created the game can add a block.");
		}
		*/
		Admin admin = currentGame.getAdmin();
		if(admin != (Admin) currentUser)
		{
			throw new InvalidInputException("Only the admin who created the game can update a block.");
		}
	
		List<Block> blocks = currentGame.getBlocks();
		for(Block block : blocks)
		{
			if(points > 1000 || points < 1)
			{
				throw new InvalidInputException("Points must be between 1 and 1000.");
			}
			if(block.getRed() == red && block.getBlue() == blue && block.getGreen() == green)
			{
				throw new InvalidInputException("A block with the same color already exists for the game.");
			}
		}
		//check if current block exists calling the findBlock method from the application
		Block block = findBlock(id);
		if(block == null) 
		{
			throw new InvalidInputException("The block does not exist.");
		}
		try
		{			
			//the method will know which method threw the exception
			block.setRed(red);
			block.setBlue(blue);
			block.setGreen(green);
			block.setPoints(points);
		}
		catch(RuntimeException e)
		//getMessage receives the message defined in the umple code
		//Important to note, the getMessage knows which method failed
		{
			throw new InvalidInputException(e.getMessage());
		}
		
	}

	public static void positionBlock(int id, int level, int gridHorizontalPosition, int gridVerticalPosition)
			throws InvalidInputException {
		level = level - 1;
		UserRole currentUser = Block223Application.getCurrentUserRole();
		//instance of checks to see if the user is an admin
		if(!(currentUser instanceof Admin)) {
			throw new InvalidInputException("Admin privileges are required to position a block.");
		}
		Game currentGame = Block223Application.getCurrentGame();
		if(currentGame == null) {
			throw new InvalidInputException("A game must be selected to position a block.");
		}
		//assuming we are logged into as admin, we check if the admin actually created the game
		Admin admin = currentGame.getAdmin();
		Level levelCheck;
		if(admin != (Admin) currentUser)
		{
			throw new InvalidInputException("Only the admin who created the game can position a block.");
		}
		try
		{
			//Check to see if there exists a level with that index
			levelCheck = currentGame.getLevel(level);
		}
		catch(IndexOutOfBoundsException e)
		{
			//getMessage with fault
			throw new InvalidInputException(e.getMessage());
		}
		//check if current block exists
		int maxNrBlocks = currentGame.getNrBlocksPerLevel();
		//getting the current number of blocks in a game
		//check to see if a blockAssignment exists in the location
		List<BlockAssignment> list = levelCheck.getBlockAssignments();
		if(maxNrBlocks == list.size())
		{
			throw new InvalidInputException("The number of blocks has reached the maximum number (" + maxNrBlocks + ") allowed for this game.");
		}
		for(BlockAssignment blockA : list)
		{
			if(gridHorizontalPosition == blockA.getGridHorizontalPosition() && gridVerticalPosition == blockA.getGridVerticalPosition())
			{
				throw new InvalidInputException("A block already exists at location " + gridHorizontalPosition + "/" + gridVerticalPosition + ".");
			}
		}
		Block block1 = findBlock(id);
		if(block1 == null) 
		{
			throw new InvalidInputException("The block does not exist.");
		}		
		try {
			new BlockAssignment(gridHorizontalPosition, gridVerticalPosition, levelCheck, block1, currentGame);	
		}
		catch(RuntimeException e){
			throw new InvalidInputException(e.getMessage());
		}
		
	}

	public static void moveBlock(int level, int oldGridHorizontalPosition, int oldGridVerticalPosition,
			int newGridHorizontalPosition, int newGridVerticalPosition) throws InvalidInputException {

		if (!(Block223Application.getCurrentUserRole() instanceof Admin)) {
			throw new InvalidInputException("Admin privileges are required to move a block.");
		}
		if (Block223Application.getCurrentGame() == null ) {
			throw new InvalidInputException("A game must be selected to move a block.");
		}

		if (Block223Application.getCurrentUserRole() != Block223Application.getCurrentGame().getAdmin()) {
			throw new InvalidInputException("Only the admin who created the game can move a block.");
		}
		try {
			Level currLevel = Block223Application.getCurrentGame().getLevel(level-1);
		}

		catch (IndexOutOfBoundsException e) {
			throw new InvalidInputException("Level " + level + " does not exist for the game.");
		}

		level = level - 1;
		Game currGame = Block223Application.getCurrentGame();

		Level currLevel = currGame.getLevel(level);
		for (int i = 0; i<currLevel.getBlockAssignments().size();i++) {
			if (currLevel.getBlockAssignments().get(i).getGridVerticalPosition() == newGridVerticalPosition && currLevel.getBlockAssignments().get(i).getGridHorizontalPosition()== newGridHorizontalPosition) {
				throw new InvalidInputException("A block already exists at location " + newGridHorizontalPosition +"/" + newGridVerticalPosition +".");
			}
		}
		BlockAssignment currAssignment = findBlockAssignment(currLevel,oldGridHorizontalPosition,
				oldGridVerticalPosition);
		if (currAssignment == null) {
			throw new InvalidInputException("A block does not exist at location " +  oldGridHorizontalPosition + "/" +
					oldGridVerticalPosition + ".");
		}

		try {
			currAssignment.setGridHorizontalPosition(newGridHorizontalPosition);
		}
		catch(RuntimeException e)
		{
			throw new InvalidInputException("The horizontal position must be between 1 and " + currGame.getMaxNumberOfHorizontalBlocks() + ".");
		}

		try {
			currAssignment.setGridVerticalPosition(newGridVerticalPosition);
		}
		catch(RuntimeException e)
		{
			throw new InvalidInputException("The vertical position must be between 1 and " + currGame.getMaxNumberOfVerticalBlocks() + ".");
		}
	}


	public static void removeBlock(int level, int gridHorizontalPosition, int gridVerticalPosition)
			throws InvalidInputException {

		if (!(Block223Application.getCurrentUserRole() instanceof Admin)) {
			throw new InvalidInputException("Admin privileges are required to remove a block.");
		}
		if (Block223Application.getCurrentGame() == null ) {
			throw new InvalidInputException("A game must be selected to remove a block.");
		}

		if (Block223Application.getCurrentUserRole() != Block223Application.getCurrentGame().getAdmin()) {
			throw new InvalidInputException("Only the admin who created the game can remove a block.");
		}

		Game currGame = Block223Application.getCurrentGame();
		level = level - 1;
		Level currLevel = currGame.getLevel(level);

		BlockAssignment currAssignment = findBlockAssignment(currLevel,gridHorizontalPosition, gridVerticalPosition);
		if(currAssignment != null) {
			currAssignment.delete();
		}
		
	}

	public static void saveGame() throws InvalidInputException {
		UserRole currentUserRole = Block223Application.getCurrentUserRole();
		Game currentGame = Block223Application.getCurrentGame();
		if(!(currentUserRole instanceof Admin))
			throw new InvalidInputException("Admin privileges are required to save a game.");
		if(currentGame==null)
			throw new InvalidInputException("A game must be selected to save it.");
		if(currentGame.getAdmin()!= currentUserRole)
			throw new InvalidInputException("Only the admin who created the game can save it.");

		try {
			Block223 aBlock223 = Block223Application.getBlock223();
			Block223Persistence.save(aBlock223);
		}catch(Exception e){
			throw e;
		}
	}

	public static void register(String username, String playerPassword, String adminPassword)
			throws InvalidInputException {
		if(Block223Application.getCurrentUserRole()!=null)
			throw new InvalidInputException("Cannot register a new user while a user is logged in.");
		if(playerPassword==adminPassword)
			throw new InvalidInputException("The passwords have to be different.");

		Player aPlayer = null;
		try{
			Block223 aBlock223 = Block223Application.getBlock223();
			aPlayer = new Player(playerPassword, aBlock223);
			User aUser = new User(username, aBlock223, aPlayer);
			Admin aAdmin = null;
			if(adminPassword!=null && !adminPassword.equals("")) {
				aAdmin = new Admin(adminPassword, aBlock223);
				aUser.addRole(aAdmin);
			}
			Block223Persistence.save(aBlock223);
		}catch(Exception e){
			if (e.getMessage().contentEquals("Cannot create due to duplicate username")) {
				throw new InvalidInputException("The username has already been taken.");
			}
			throw new InvalidInputException(e.getMessage());
		}
	}

	public static void login(String username, String password) throws InvalidInputException {
		UserRole currentUserRole = Block223Application.getCurrentUserRole();
		if(currentUserRole!=null)
			throw new InvalidInputException("Cannot login a user while a user is already logged in.");
		Block223Application.resetBlock223();
		try{
			User currentUser = User.getWithUsername(username);
			if(currentUser==null)
				throw new InvalidInputException("The username and password do not match.");
			List<UserRole> roles = currentUser.getRoles();
			for(UserRole role: roles){
				String rolePassword = role.getPassword();
				if(password.equals(rolePassword)){
					Block223Application.setCurrentUserRole(role);
					return;
				}
			}
			throw new InvalidInputException("The username and password do not match.");
		}catch(Exception e){
			throw e;
		}
	}

	public static void logout() {
		Block223Application.setCurrentUserRole(null);
	}
	
	// play mode

	public static void selectPlayableGame(String name, int id) throws InvalidInputException  {
		Block223 block223 = Block223Application.getBlock223();
		Game game = block223.findGame(name);
		UserRole player= Block223Application.getCurrentUserRole();
		PlayedGame pgame = null;
		if(game!=null){
			if(!(player instanceof Player))
				throw new InvalidInputException("Player privileges are required to play a game.");

			String username = User.findUsername(player);
			if(username == null)
				throw new InvalidInputException("A game with name " + name + " does not exist.");

			pgame = new PlayedGame(username, game, block223);
			pgame.setPlayer((Player) player);
		}else{
			pgame = block223.findPlayableGame(id);
			if(pgame == null)
				throw new InvalidInputException("The game does not exist.");
			if(!pgame.getPlayer().equals(player))
				throw new InvalidInputException("Only the player that started a game can continue the game.");
		}
		Block223Application.setCurrentPlayableGame(pgame);
	}

	public static void startGame(Block223PlayModeInterface ui) throws InvalidInputException {
		UserRole currentUser = Block223Application.getCurrentUserRole();
		PlayedGame playedGame = Block223Application.getCurrentPlayableGame();
		
		if(currentUser == null)
			throw new InvalidInputException("Player privileges are required to play a game.");
		if(playedGame == null)
			throw new InvalidInputException("A game must be selected to play it.");
		if(currentUser instanceof Admin && !playedGame.getGame().getAdmin().equals(currentUser))
			throw new InvalidInputException("Only the admin of a game can test the game.");
		if(currentUser instanceof Admin && playedGame.getPlayer() != null)
			throw new InvalidInputException("Player privileges are required to play a game.");
		if(currentUser instanceof Player && playedGame.getPlayer() == null)
			throw new InvalidInputException("Admin privileges are required to test a game.");

		Game game = playedGame.getGame();
		playedGame.play();
		ui.takeInputs();

		while (playedGame.getPlayStatus().equals(PlayedGame.PlayStatus.Moving)){
			
			String userInputs = ui.takeInputs();
			updatePaddlePosition(userInputs, playedGame);
			playedGame.move();
			playedGame.setBounce(null);
			if(userInputs.contains(" ")) {
				if(currentUser instanceof Player) {
					Block223 block223 = Block223Application.getBlock223();
					Block223Persistence.save(block223);
				}
				playedGame.pause();
			}
			try {
				Thread.sleep((long) playedGame.getWaitTime());
			}catch(Exception e){}

			if(playedGame.getPlayStatus().equals(PlayedGame.PlayStatus.GameOver))
				break;
			ui.refresh();
		}

		if(playedGame.getPlayStatus().equals(PlayedGame.PlayStatus.GameOver)){
			TOHallOfFame hof = new TOHallOfFame(game.getName());
			TOHallOfFameEntry hofEntry = new TOHallOfFameEntry(0, playedGame.getPlayername(), playedGame.getScore(), hof);
			int nrOfLives;
			Block223Application.setLastPlayedGame(game);
			ui.endGame(playedGame.getLives(), hofEntry);
			Block223Application.setCurrentPlayableGame(null);
		}else{
			if(currentUser instanceof Player) {
				Block223 block223 = Block223Application.getBlock223();
				Block223Persistence.save(block223);
			}
		}
	}

	private static void updatePaddlePosition(String userInputs, PlayedGame playedGame) {
		// TODO Auto-generated method stub
		for(char dir: userInputs.toCharArray()) {
			double currLoc = playedGame.getCurrentPaddleX();
			if(dir == 'l' && (currLoc + PlayedGame.PADDLE_MOVE_LEFT) >= 0)
				playedGame.setCurrentPaddleX(playedGame.getCurrentPaddleX() + PlayedGame.PADDLE_MOVE_LEFT);
			if(dir == 'r' && (currLoc + playedGame.getCurrentPaddleLength() + PlayedGame.PADDLE_MOVE_RIGHT) <= Game.PLAY_AREA_SIDE)
				playedGame.setCurrentPaddleX(playedGame.getCurrentPaddleX() + PlayedGame.PADDLE_MOVE_RIGHT);
		}
		
	}

	public static void testGame(Block223PlayModeInterface ui) throws InvalidInputException {
		if (!(Block223Application.getCurrentUserRole() instanceof Admin)) {
			throw new InvalidInputException("Admin privileges are required to test a game.");
		}
		if (Block223Application.getCurrentGame() == null ) {
			throw new InvalidInputException("A game must be selected to test it.");
		}

		if (Block223Application.getCurrentUserRole() != Block223Application.getCurrentGame().getAdmin()) {
			throw new InvalidInputException("Only the admin who created the game can test it.");
		}

		Game currGame = Block223Application.getCurrentGame();
		UserRole admin = Block223Application.getCurrentUserRole();
		String username = User.findUsername(admin);
		Block223 block223 = Block223Application.getBlock223();
		PlayedGame pgame = new PlayedGame(username,currGame,block223);
		pgame.setPlayer(null);
		Block223Application.setCurrentPlayableGame(pgame);
		ui.refresh();
	}

	public static void publishGame () throws InvalidInputException {
		if (!(Block223Application.getCurrentUserRole() instanceof Admin)) {
			throw new InvalidInputException("Admin privileges are required to publish a game.");
		}
		if (Block223Application.getCurrentGame() == null ) {
			throw new InvalidInputException("A game must be selected to publish it.");
		}
		if (Block223Application.getCurrentUserRole() != Block223Application.getCurrentGame().getAdmin()) {
			throw new InvalidInputException("Only the admin who created the game can publish it.");
		}

		if (Block223Application.getCurrentGame().numberOfBlocks() < 1) {
			throw new InvalidInputException("At least one block must be defined for a game to be published. ");
		}

		Game currGame = Block223Application.getCurrentGame();
		currGame.setPublished(true);
	}

	// ****************************
	// Helper Methods
	// ****************************

	public static BlockAssignment findBlockAssignment(Level currentLevel, int oldGridHorizontalPosition,int oldGridVerticalPosition) {
		List<BlockAssignment> blockAssignments = currentLevel.getBlockAssignments();
		for (int i = 0; i<blockAssignments.size(); i++) {
			if (blockAssignments.get(i).getGridHorizontalPosition() == oldGridHorizontalPosition &&
					blockAssignments.get(i).getGridVerticalPosition() == oldGridVerticalPosition) {
				return blockAssignments.get(i);
			}
		}

		return null;
	}


	// ****************************
	// Query methods
	// ****************************
	public static List<TOGame> getDesignableGames() throws InvalidInputException{
		Block223 block223 = Block223Application.getBlock223();
		UserRole admin = Block223Application.getCurrentUserRole();
		if(!(admin instanceof Admin)) {
			throw new InvalidInputException("Admin privileges are required to access game information.");
		}
		try {
			ArrayList<TOGame> result = new ArrayList<TOGame>();
			for (Game game : block223.getGames()) {
				if (game.getAdmin().equals(admin) && !game.getPublished()) {
					TOGame to = new TOGame(game.getName(), game.getLevels().size(), game.getNrBlocksPerLevel(),
							game.getBall().getMinBallSpeedX(), game.getBall().getMinBallSpeedY(), game.getBall().getBallSpeedIncreaseFactor(),
							game.getPaddle().getMaxPaddleLength(), game.getPaddle().getMinPaddleLength());
					result.add(to);
				}
			}
			return result;
		}
		catch (Exception e) {
			throw e;
		}
	}

	public static TOGame getCurrentDesignableGame() throws InvalidInputException {
		Block223 block223 = Block223Application.getBlock223();
		UserRole admin = Block223Application.getCurrentUserRole();
		if(!(admin instanceof Admin)) {
			throw new InvalidInputException("Admin privileges are required to access game information.");
		}
		Game game = Block223Application.getCurrentGame();
		if(game == null) {
			throw new InvalidInputException("A game must be selected to access its information.");
		}
		if(!admin.equals(game.getAdmin())) {
			throw new InvalidInputException("Only the admin who created the game can access its information.");
		}
		try {
			TOGame to = new TOGame(game.getName(), game.getLevels().size(), game.getNrBlocksPerLevel(),
					game.getBall().getMinBallSpeedX(), game.getBall().getMinBallSpeedY(), game.getBall().getBallSpeedIncreaseFactor(),
					game.getPaddle().getMaxPaddleLength(), game.getPaddle().getMinPaddleLength());
			return to;
		}
		catch (Exception e) {
			throw e;
		}
	}

	public static List<TOBlock> getBlocksOfCurrentDesignableGame() throws InvalidInputException {
		// validation checks
		UserRole userRole = Block223Application.getCurrentUserRole();
		if(!(userRole instanceof Admin)) {
			throw new InvalidInputException("Admin privileges are required to access game information.");
		}
		
		Game game = Block223Application.getCurrentGame();
		if(game == null) {
			throw new InvalidInputException("A game must be selected to access its information.");
		}
		
		if(!userRole.equals(game.getAdmin())) {
			throw new InvalidInputException("Only the admin who created the game can access its information.");
		}
		
		ArrayList<TOBlock> blocks = new ArrayList<TOBlock>();

		for (Block block : game.getBlocks()) {
			TOBlock toBlock = new TOBlock(block.getId(), block.getRed(), block.getGreen(), block.getBlue(), block.getPoints());
			blocks.add(toBlock);
		}
		return blocks;
	}

	public static TOBlock getBlockOfCurrentDesignableGame(int id) throws InvalidInputException {
		
		UserRole currentUser = Block223Application.getCurrentUserRole();
		//instance of checks to see if the user is an admin
		if(!(currentUser instanceof Admin)) {
			throw new InvalidInputException("Admin privileges are required to access game information.");
		}
		Game currentGame = Block223Application.getCurrentGame();
		if(currentGame == null) {
			throw new InvalidInputException("A game must be selected to access its information.");
		}
		//assuming we are logged into as admin, we check if the admin actually created the game
		Admin admin = currentGame.getAdmin();
		if(admin != (Admin) currentUser)
		{
			throw new InvalidInputException("Only the admin who created the game can access its information.");
		}
		//check if current block exists
		Block block = findBlock(id);
		if(block == null) 
		{
			throw new InvalidInputException("The block does not exist.");
		}
		//return the transfer object
		return new TOBlock(block.getId(), block.getRed(), block.getGreen(), block.getBlue(), block.getPoints());	
	}

	public static List<TOGridCell> getBlocksAtLevelOfCurrentDesignableGame(int level) throws InvalidInputException {
		
		level = level - 1;
		UserRole currentUser = Block223Application.getCurrentUserRole();
		//instance of checks to see if the user is an admin
		if(!(currentUser instanceof Admin)) {
			throw new InvalidInputException("Admin privileges are required to access game information.");
		}
		Game currentGame = Block223Application.getCurrentGame();
		if(currentGame == null) {
			throw new InvalidInputException("A game must be selected to access its information.");
		}
		//assuming we are logged into as admin, we check if the admin actually created the game
		Admin admin = currentGame.getAdmin();
		if(admin != (Admin) currentUser)
		{
			throw new InvalidInputException("Only the admin who created the game can access its information.");
		}
		
		try
		{
			Level levelCheck = currentGame.getLevel(level);
		}
		catch(IndexOutOfBoundsException e)
		{
			//getMessage with fault
			throw new InvalidInputException(e.getMessage());
		}
		//instantiate the TOGridCell
		List<TOGridCell> result = new ArrayList<TOGridCell>();
		
		//traversing through each blockAssignment
		Level currentLevel = currentGame.getLevel(level);
		List<BlockAssignment> listAssignments = currentLevel.getBlockAssignments();
		for(BlockAssignment assignment : listAssignments)
		{
			TOGridCell temporary = new TOGridCell(assignment.getGridHorizontalPosition(), assignment.getGridVerticalPosition(), 
					assignment.getBlock().getId(), assignment.getBlock().getRed(), assignment.getBlock().getGreen(), 
					assignment.getBlock().getBlue(), assignment.getBlock().getPoints());
	
			result.add(temporary);
		}
		return result;	
	}

	public static TOUserMode getUserMode() {
		UserRole currentUserRole = Block223Application.getCurrentUserRole();
		if(currentUserRole==null)
			return new TOUserMode(TOUserMode.Mode.None);
		else if(currentUserRole instanceof Player)
			return new TOUserMode(TOUserMode.Mode.Play);
		else
			return new TOUserMode(TOUserMode.Mode.Design);
	}
	
    public static TOGridCell getBlock(int level, int xPos, int yPos) throws InvalidInputException {
    	List<TOGridCell> blocks;
		try {
			blocks = getBlocksAtLevelOfCurrentDesignableGame(level);
		} catch (InvalidInputException e) {
			throw new InvalidInputException(e.getMessage());
		}
		
    	for(TOGridCell block: blocks) {
    		if (block.getGridHorizontalPosition() == xPos && block.getGridVerticalPosition() == yPos) {
    			return block;
    		}
    	}
		return null;
    }
    
 // play mode

 	public static List<TOPlayableGame> getPlayableGames() throws InvalidInputException {
		Block223 block223 = Block223Application.getBlock223();
		UserRole player = Block223Application.getCurrentUserRole();

		if(!(player instanceof Player))
			throw new InvalidInputException("Player privileges are required to play a game.");

		List<TOPlayableGame> result = new ArrayList<TOPlayableGame>();
		List<Game> games = block223.getGames();

		for(Game game: games){
			if(game.isPublished()){
				TOPlayableGame to = new TOPlayableGame(game.getName(), -1, 0);
				result.add(to);
			}
		}

		List<PlayedGame> playedGames = block223.getPlayedGames();

		for(PlayedGame game: playedGames){
			TOPlayableGame to = new TOPlayableGame(game.getGame().getName(), game.getId(), game.getCurrentLevel());
			result.add(to);
		}

		return result;
 	}

 	public static TOCurrentlyPlayedGame getCurrentPlayableGame() throws InvalidInputException {
		if(Block223Application.getCurrentUserRole()==null)
			throw new InvalidInputException("Player privileges are required to play a game.");
		UserRole currentUser = Block223Application.getCurrentUserRole();
		PlayedGame pgame = Block223Application.getCurrentPlayableGame();

		if(pgame==null)
			throw new InvalidInputException("A game must be selected to play it.");
		if(currentUser instanceof  Admin && pgame.getPlayer() != null)
			throw new InvalidInputException("Player privileges are required to play a game.");
		if(currentUser instanceof Admin && currentUser != pgame.getGame().getAdmin())
			throw new InvalidInputException("Only the admin of a game can test the game.");
		if(currentUser instanceof Player && pgame.getPlayer() == null)
			throw new InvalidInputException("Admin privileges are required to test a game.");

		Boolean paused = (pgame.getPlayStatus()== PlayedGame.PlayStatus.Ready) || (pgame.getPlayStatus()== PlayedGame.PlayStatus.Paused);

		TOCurrentlyPlayedGame result = new TOCurrentlyPlayedGame(pgame.getGame().getName(), paused, pgame.getScore(),
				pgame.getLives(), pgame.getCurrentLevel(), pgame.getPlayername(), pgame.getCurrentBallX(),
				pgame.getCurrentBallY(), pgame.getCurrentPaddleLength(), pgame.getCurrentPaddleX());

		List<PlayedBlockAssignment> blocks = pgame.getBlocks();

		for(PlayedBlockAssignment pblock: blocks)
		{
			new TOCurrentBlock(pblock.getBlock().getRed(), pblock.getBlock().getGreen(), pblock.getBlock().getBlue(),
							pblock.getBlock().getPoints(), pblock.getX(), pblock.getY(), result);
		}
		return result;
 	}

	public static TOHallOfFame getHallOfFame(int start, int end) throws InvalidInputException {
		UserRole currentUser = Block223Application.getCurrentUserRole();
		if (!(currentUser instanceof Player)) {
			throw new InvalidInputException("Player privileges are required to access a game's hall of fame.");
		}
		PlayedGame currentPlayableGame = Block223Application.getCurrentPlayableGame();
		if (currentPlayableGame == null) {
			throw new InvalidInputException("A game must be selected to view its hall of fame.");
		}

		PlayedGame pgame = Block223Application.getCurrentPlayableGame();
		Game game = pgame.getGame();
		TOHallOfFame result = new TOHallOfFame(game.getName());

		if(start < 1){
			start = 1;
		}
		if(end >= game.numberOfHallOfFameEntries()) {
			end = game.numberOfHallOfFameEntries();
		} else {
			end = game.numberOfHallOfFameEntries() - end;
		}

		start = game.numberOfHallOfFameEntries() - start;
		end = game.numberOfHallOfFameEntries() - end;


		for(int index = start; index >= end; index--) {
			TOHallOfFameEntry to = new TOHallOfFameEntry(index+1, game.getHallOfFameEntry(index).getPlayername(),
					game.getHallOfFameEntry(index).getScore(), result);
		}
		return result;
	}

	public static TOHallOfFame getHallOfFameWithMostRecentEntry(int numberOfEntries) throws InvalidInputException {
		UserRole currentUser = Block223Application.getCurrentUserRole();
		if (!(currentUser instanceof Player)) {
			throw new InvalidInputException("Player privileges are required to access a game's hall of fame.");
		}
		PlayedGame currentPlayableGame = Block223Application.getCurrentPlayableGame();
		if (currentPlayableGame == null) {
			throw new InvalidInputException("A game must be selected to view its hall of fame.");
		}

		PlayedGame pgame = Block223Application.getCurrentPlayableGame();
		Game game = pgame.getGame();
		TOHallOfFame result = new TOHallOfFame(game.getName());
		HallOfFameEntry mostRecent = game.getMostRecentEntry();
		int indexR = game.indexOfHallOfFameEntry(mostRecent);

		int start = indexR + numberOfEntries / 2;
		int end = start + numberOfEntries - 1;
		if(start > game.numberOfHallOfFameEntries() - 1) {
			start = game.numberOfHallOfFameEntries() - 1;
		}
		end = start - numberOfEntries + 1;
		if(end < 0) {
			end = 0;
		}
		for(int index = start; index >= end; index--) {
			TOHallOfFameEntry to = new TOHallOfFameEntry(index+1, game.getHallOfFameEntry(index).getPlayername(),
					game.getHallOfFameEntry(index).getScore(), result);
		}
		return result;
	}
	
	public static TOHallOfFame getHallOfFameEnd(int start, int end, int id) throws InvalidInputException {
		UserRole currentUser = Block223Application.getCurrentUserRole();
		if (!(currentUser instanceof Player)) {
			throw new InvalidInputException("Player privileges are required to access a game's hall of fame.");
		}
		Game game = null;
		PlayedGame currentPlayableGame = null;
		if(id==1) {
			currentPlayableGame = Block223Application.getCurrentPlayableGame();
			if (currentPlayableGame == null) {
				throw new InvalidInputException("A game must be selected to view its hall of fame.");
			}
			game = currentPlayableGame.getGame();
		}
		else
			game = Block223Application.getLastPlayedGame();
		
		TOHallOfFame result = new TOHallOfFame(game.getName());

		if(start < 1){
			start = 1;
		}
		if(end >= game.numberOfHallOfFameEntries()) {
			end = game.numberOfHallOfFameEntries();
		} else {
			end = game.numberOfHallOfFameEntries() - end;
		}

		start = game.numberOfHallOfFameEntries() - start;
		end = game.numberOfHallOfFameEntries() - end;


		for(int index = start; index >= end; index--) {
			TOHallOfFameEntry to = new TOHallOfFameEntry(index+1, game.getHallOfFameEntry(index).getPlayername(),
					game.getHallOfFameEntry(index).getScore(), result);
		}
		return result;
	}
	
	public static int getNumEntriesInHallOfFame() {
		return Block223Application.getCurrentPlayableGame().getGame().getHallOfFameEntries().size();
	}
	
	public static String getLastPlayedGame() {
		Game game = Block223Application.getLastPlayedGame();
		return game.getName();
	}
	

}