package block;

import ca.mcgill.ecse223.block.application.Block223Application;
import ca.mcgill.ecse223.block.controller.Block223Controller;
import ca.mcgill.ecse223.block.model.*;
import ca.mcgill.ecse223.block.persistence.Block223Persistence;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class SignInAndUp {

    Block223 block223 = null;

    @Before
    public void setUp() {
        Block223Persistence.setFilename("data.block.tests");
        block223 = Block223Application.getBlock223();
        Block223Application.setCurrentUserRole(null);
    }

    @Test
    public void testLogInWrongUsernameAndPassword() {
        Block223Application.resetBlock223();
        Block223Persistence.clearSave();

        String NullString = null;
        String EmptyString = "";

        //Username errors
        assertTrue(testLoginForException(NullString, "test"));
        assertTrue(testLoginForException(EmptyString, "test"));

        //Password errors
        assertTrue(testLoginForException("test", NullString));
        assertTrue(testLoginForException("test2", EmptyString));

        try{
            //Someone already logged in
            Block223Application.setCurrentUserRole(new Admin("test", block223));
            Block223Controller.login("test3","test");

        }catch(Exception e){
            return;
        }

        fail();
    }

    private static boolean testLoginForException(String username, String password){
        try{
            Block223Controller.login(username, password);
        }catch(Exception e){
            return true;
        }
        return false;
    }


    @Test
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
    }
}
