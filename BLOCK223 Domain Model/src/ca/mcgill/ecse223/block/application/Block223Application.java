package ca.mcgill.ecse223.block.application;

import ca.mcgill.ecse223.block.model.Block223;
import ca.mcgill.ecse223.block.model.Game;
import ca.mcgill.ecse223.block.model.PlayedGame;
import ca.mcgill.ecse223.block.model.UserRole;
import ca.mcgill.ecse223.block.persistence.Block223Persistence;
import ca.mcgill.ecse223.block.view.ViewManager;

public class Block223Application {

    private static Block223 currentBlock223 = null;
    private static UserRole currentUserRole = null;
    private static Game     currentGame     = null;
    private static PlayedGame currentPlayableGame = null;
    private static Game lastPlayedGame = null;

    public static void main(String[] args) {
        // load game
    	getBlock223();
        // start UI
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ViewManager(); //This will be the main UI page
            }
        });
        return;
    }

    public static Block223 getBlock223() {
        if (currentBlock223 == null) {
            // load model
            currentBlock223 = Block223Persistence.load();
        }
        return currentBlock223;
    }

    public static Block223 resetBlock223() {
        if(currentBlock223!=null)
            currentBlock223.delete();
        setCurrentGame(null);
        currentBlock223 = Block223Persistence.load();
        return currentBlock223;
    }

    public static void setCurrentUserRole(UserRole aUserRole) {
        currentUserRole = aUserRole;
    }

    public static UserRole getCurrentUserRole() {
        return currentUserRole;
    }

    public static void setCurrentGame(Game aGame) {
        currentGame = aGame;
    }

    public static Game getCurrentGame() {
        return currentGame;
    }

    public static PlayedGame getCurrentPlayableGame() {
    	return currentPlayableGame;
    }

    public static void setCurrentPlayableGame(PlayedGame aPGame) {
    	currentPlayableGame = aPGame;
    }
    
    public static void setLastPlayedGame(Game game) {
    	lastPlayedGame = game;
    }
    
    public static Game getLastPlayedGame() {
    	return lastPlayedGame;
    }
}
