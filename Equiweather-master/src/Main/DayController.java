package Main;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class DayController {

    @FXML
    private Label chosenDayLabel, homeLabel, settingsLabel;

    //This wil set the chosen day label to the required value so that the user can make a decision
    @FXML
    private void initialize() {
        if (Settings.getInstance().getDay()  == 0) chosenDayLabel.setText("Today");
        else if (Settings.getInstance().getDay() == 1) chosenDayLabel.setText("Tomorrow");
        else chosenDayLabel.setText("Day After");
    }


    /*
    The next 3 functions are the event handler that occur when different types of events occur
    chosenToday basically sets day today whenever the today label or rectangle is clicked
     */
    //When the user taps/clicks on the today label then we set the day to today
    @FXML
    private void chosenToday() {
        System.out.println("Today");
        chosenDayLabel.setText("Today");
        Settings.getInstance().setDay(0);
    }

    //when the user choses the tomorrow then it sets the day to tomorrow
    @FXML
    private void chosenTom() {
        System.out.println("Tomorrow");
        chosenDayLabel.setText("Tomorrow");
        Settings.getInstance().setDay(1);
    }

    //when the user clicks the day after option sets the day to be so
    @FXML
    private void chosenDayAfter() {
        System.out.println("Day After");
        chosenDayLabel.setText("Day After");
        Settings.getInstance().setDay(2);
    }

    //As the name suggests this function is the event handler when the home icon/label is clicked
    @FXML
    private void changeToHome() throws IOException {
        FXMLLoader homeLoader = new FXMLLoader(getClass().getResource("Home.fxml"));
        HomeController hc = new HomeController();
        Stage window = (Stage) chosenDayLabel.getScene().getWindow();

        homeLoader.setController(hc);
        Parent home = homeLoader.load();

        window.setScene(new Scene(home, 406, 800));
        Settings.getInstance().hc.initialize();
        window.show();
    }

    //This will change from the day to Settings scene when the settings icon(horse)/label is clicked
    @FXML
    private void changeToSettings() throws IOException{
        FXMLLoader settingsLoader = new FXMLLoader(getClass().getResource("SettingsScene.fxml"));
        Stage window = (Stage) chosenDayLabel.getScene().getWindow();

        SettingController sc = new SettingController();
        settingsLoader.setController(sc);
        Parent settings = settingsLoader.load();

        sc.dothis();

        window.setScene(new Scene(settings, 406, 800));
        window.show();
    }

}
