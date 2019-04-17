package block;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.sql.Date;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;


import ca.mcgill.ecse223.block.application.Block223Application;
import ca.mcgill.ecse223.block.controller.Block223Controller;
import ca.mcgill.ecse223.block.controller.InvalidInputException;
import ca.mcgill.ecse223.block.model.*;
import ca.mcgill.ecse223.block.persistence.Block223Persistence;

public class AddAndDeleteBlockTest {
	
	@BeforeClass
	public static void setUpOnce() {
		String filename = "testdata.block223";
		Block223Persistence.setFilename(filename);
		File f = new File(filename);
		f.delete();
	}
	
	@Before
	public void setUp() {
		// clear all data
		Block223 b223 = Block223Application.getBlock223();
		b223.delete();
	}
	
	@Test
    public void testAddBlock() {
		Block223 b223 = Block223Application.getBlock223();
		Admin admin = new Admin("password", b223);
		User user = new User("chelsea", b223, admin);
		Block223Application.setCurrentUserRole(admin);
		Game game = new Game("name", 1,(Admin)admin, 1,1,1,10,10,b223);
		admin.addGame(game);
		Block223Application.setCurrentGame(game);
		
		// test for success
		assertEquals(testCreateBlockSuccess(), true);
		// test for invalid parameters
		assertEquals(testCreateBlockBadParams(-1, 1, 1, 1), "Invalid input for block: red must be positive and under 255");
		assertEquals(testCreateBlockBadParams(300, 1, 1, 1), "Invalid input for block: red must be positive and under 255");
		assertEquals(testCreateBlockBadParams(1, -1, 1, 1), "Invalid input for block: green must be positive and under 255");
		assertEquals(testCreateBlockBadParams(1, 300, 1, 1), "Invalid input for block: green must be positive and under 255");
		assertEquals(testCreateBlockBadParams(1, 1, -1, 1), "Invalid input for block: blue must be positive and under 255");
		assertEquals(testCreateBlockBadParams(1, 1, 300, 1), "Invalid input for block: blue must be positive and under 255");
		assertEquals(testCreateBlockBadParams(10, 10, 10, -1), "Invalid input for block: points must be between 1 and 1000");
		assertEquals(testCreateBlockBadParams(10, 10, 10, 1000000), "Invalid input for block: points must be between 1 and 1000");
		// test for repeated colour
		assertEquals(testCreateBlockBadParams(1, 1, 1, 1), "A block with the same colour already exists for this game");
		// test for admin role
		Player player = new Player("password", b223);
		user.addRole(player);
		Block223Application.setCurrentUserRole(player);
		assertEquals(testCreateBlockBadParams(10, 10, 10, 10), "Admin priviledges are required to add a block");
		// test for wrong admin
		Admin admin2 = new Admin("password2", b223);
		Block223Application.setCurrentUserRole(admin2);
		assertEquals(testCreateBlockBadParams(10, 10, 10, 10), "Only the admin who created the game can add a block");
		// test for null game
		Block223Application.setCurrentUserRole(admin);
		Game game2 = null;
		Block223Application.setCurrentGame(game2);
		assertEquals(testCreateBlockBadParams(10, 10, 10, 10), "A game must be selected to add a block");
		// test for deleting
		Block223Application.setCurrentGame(game);
		int id = game.getBlock(0).getId();
		assertEquals(testDeleteBlock(id), null);
		// test for non-existant block
		assertEquals(testDeleteBlock(id), "Cannot delete block that does not exist");
		// test for Admin priviledges
		testCreateBlockSuccess();
		id = game.getBlock(0).getId();
		Block223Application.setCurrentUserRole(player);
		assertEquals(testDeleteBlock(id), "Admin priviledges are required to delete a block");
		// test for correct admin
		Block223Application.setCurrentUserRole(admin2);
		assertEquals(testDeleteBlock(id), "Only the admin who created the game can delete a block");
		// test for non-null game
		Block223Application.setCurrentUserRole(admin);
		Block223Application.setCurrentGame(game2);
		assertEquals(testDeleteBlock(id), "A game must be selected to delete a block");
		
		// try to select game through application
		try {
			Block223Controller.createGame("gamename");
			Block223Controller.selectGame("gamename");
		} catch (InvalidInputException e) {
			e.printStackTrace();
		}
		assertEquals(testCreateBlockBadParams(10,10,11,11), null);
		
	}
	
	private static boolean testCreateBlockSuccess() {
		try {
			Block223Controller.addBlock(1,  1, 1, 1);
		}
		catch(InvalidInputException e){
			return false;
		}
		return true;
	}
	
	private static String testCreateBlockBadParams(int red, int green, int blue, int points) {
		try {
			Block223Controller.addBlock(red, green, blue, points);
		}
		catch(InvalidInputException e){
			return e.getMessage();
		}
		return null;
	}
	
	private static String testDeleteBlock(int id) {
		try {
			Block223Controller.deleteBlock(id);
		}
		catch(InvalidInputException e){
			return e.getMessage();
		}
		return null;
	}
}
