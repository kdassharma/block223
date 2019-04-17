package block;

import ca.mcgill.ecse223.block.application.Block223Application;
import ca.mcgill.ecse223.block.controller.Block223Controller;
import ca.mcgill.ecse223.block.model.*;
import ca.mcgill.ecse223.block.persistence.Block223Persistence;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CreateGameAndDefineGameSettings {

    Block223 block223 = null;

    @Before
    public void setUp() {
        Block223Persistence.setFilename("data.block.tests");
        block223 = Block223Application.getBlock223();
        Block223Application.setCurrentUserRole(new Admin("password", block223));
    }

    @Test
    public void testCreateGameWrongParam() {
        Block223Application.resetBlock223();
        Block223Persistence.clearSave();

        String NullString = null;
        String EmptyString = "";

        //name errors
        assertTrue(testForException(NullString));
        assertTrue(testForException(EmptyString));
        
        Block223Application.setCurrentUserRole(new Player("password", block223));
        
        //Admin errors
        assertTrue(testForAdminException("test"));
        assertTrue(testForAdminException("test2"));
        
        Block223Application.setCurrentUserRole(new Admin("password", block223));

       
    }

    private static boolean testForException(String name){
        try{
            Block223Controller.createGame(name);
        }catch(Exception e){
            return true;
        }
        return false;
    }
    
    private static boolean testForAdminException(String name) {
    	
    	try {
    		Block223Controller.createGame(name);
    	} catch (Exception e) {
    		return true;
    	}
    	return false;
    	
    }


    /*@Test
    public void testSignUpWrongUsernameAndPassword() {
        Block223Application.resetBlock223();
        Block223Persistence.clearSave();

        String NullString = null;
        String EmptyString = "";

        //Username errors
        assertTrue(testRegisterForException(NullString, "test", "test2"));
        assertTrue(testRegisterForException(EmptyString, "test", "test2"));

        //Password errors
        assertTrue(testRegisterForException("test", NullString, "test2"));
        assertTrue(testRegisterForException("test2", EmptyString, "test2"));

        //These should work
        assertFalse(testRegisterForException("test3", "test", NullString));
        assertFalse(testRegisterForException("test4", "test", EmptyString));

        //Duplicate passwords
        assertTrue(testRegisterForException("test", "test", "test"));
    }

    private static boolean testRegisterForException(String username, String playerPassword, String adminPassword){
        try{
            Block223Controller.register(username, playerPassword, adminPassword);
        }catch(Exception e){
            return true;
        }
        return false;
    }

    @Test
    public void testSignUpDuplicateUsernames() {
        Block223Application.resetBlock223();
        try {
            //Username errors
            Block223Controller.register("duplicate", "test", "test2");
            Block223Controller.register("duplicate", "test", "test2");

        } catch (Exception e) {
            return;
        }

        fail();
    }

    @Test
    public void testSignUpThenSignIn() {
        Block223Application.resetBlock223();
        UserRole user = null;
        UserRole admin = null;
        try {
            //Username errors
            Block223Controller.register("user1", "test", "test2");
            Block223Controller.login("user1", "test");
            user = Block223Application.getCurrentUserRole();

            Block223Controller.logout();
            Block223Controller.login("user1", "test2");
            admin = Block223Application.getCurrentUserRole();

        } catch (Exception e) {
            fail();
        }

        assertTrue(user instanceof Player);
        assertTrue(admin instanceof Admin);
    }*/
}
