package ca.mcgill.ecse223.block.view;

import javax.swing.*;

import ca.mcgill.ecse223.block.controller.TOHallOfFameEntry;

import java.awt.*;

public class ViewManager {

    static GraphicsConfiguration gc;

    public ViewManager() {
        runView("usermenu", new JFrame());
    }

    private static void run(){
        runPopupView("login");
        runPopupView("signup");
    }

    public static void runPopupView(String view){
        if(view=="signup")
            new SignUp();
        if(view=="login")
            new LogIn();
        if(view=="PlayerIntroUi")
        	new PlayerIntroUi();
        if(view=="PlayerGameUi")
        	new PlayerGameUi();
    }
    public static void runView(String view){
    	if(view=="MainUi")
            new AdminUi();	
    }
    public static void runView(String view, TOHallOfFameEntry hof) {
    	if(view=="GameOverUi")
    		new GameOverUi(hof);
    }
    public static void runView(String view, TOHallOfFameEntry hof, int lives) {
    	if(view=="VictoryUi")
    		new VictoryUi(hof, lives);
    }
    public static void runView(String view, int id) {
    	if(view=="HallOfFame")
        	new HallOfFame(id); 
    }
    public static void runView(String view, JFrame jFrame){
        if(view=="usermenu")
            new UserMenu(jFrame);
        if(view=="CreateBlock")
            new CreateBlock(jFrame);
        if(view=="GameMenu")
            new GameMenu(jFrame);
        if(view=="DeleteBlock")
            new DeleteBlock(jFrame);
        if(view=="LevelConfig")
            new LevelConfig(jFrame);
        if(view=="UpdateGame")
            new UpdateGame(jFrame);
        if(view=="CreateGameMenu")
            new CreateGameMenu(jFrame);
        if(view=="UpdateBlock")
        	new UpdateBlock(jFrame); 	
    }
    //block generation
    public static void runView(String view, JPanel jPanel)
    {
        if(view=="BlockGeneration")
            new BlockGeneration(jPanel);
    }
    public static void runView(String view, JPanel jPanel, int id) {
    	  if(view=="GamePlayPanel")
              new GamePlayPanel(jPanel, id);
    }
    public static void runView(String view, JFrame jFrame, JButton button)
    {
        if(view=="ChangeBlock")
            new ChangeBlock(jFrame, button);
    }

    //unique block
    public static void runView(String view, JButton button)
    {
        if(view=="BlockClickUi")
            new BlockClickUi(button);
    }
    public static void runView(String view, JFrame jFrame, int x, int y) {
        if(view=="MoveBlock")
        	new MoveBlock(jFrame, x, y);
    }
}