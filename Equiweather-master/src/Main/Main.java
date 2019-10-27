package Main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        //Loading all the scenes as defined in the scene builder
        FXMLLoader homeLoader = new FXMLLoader(getClass().getResource("Home.fxml"));
        FXMLLoader settingLoader = new FXMLLoader(getClass().getResource("SettingsScene.fxml"));
        FXMLLoader chooseDayLoader = new FXMLLoader(getClass().getResource("ChooseDay.fxml"));

        //Setting the controller for each scene here
        //it had to be done dynamically and not statically
        //becuase the controller need to talk to each other and I didn't want to make only one controller class
        homeLoader.setController(new HomeController());
        settingLoader.setController(new SettingController());
        chooseDayLoader.setController(new DayController());

        //Loading all the scenes
        Parent home = homeLoader.load();
        Parent settings = settingLoader.load();
        Parent dc = chooseDayLoader.load();

        //Setting the Home scene to show up when we start the program
        Scene scene = new Scene(home, 406, 800);
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
