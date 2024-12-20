package Display;

import Model.ScoreboardModel;
import View.*;
import Controller.MainMenuController;


public class GameLauncher {

    public static void main(String[] args) {
        ScoreboardModel scoreboardModel = new ScoreboardModel(); // Create the ScoreboardModel
        MainMenu mainMenu = new MainMenu(); // Create the MainMenu view
        new MainMenuController(mainMenu, scoreboardModel); // Pass the model to the controller
    }
}
