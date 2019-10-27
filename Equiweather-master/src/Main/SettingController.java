package Main;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

//import static com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type.Node;

public class SettingController {

    //This is the list that is used to choose a location
    private ObservableList<String> locations = FXCollections.observableArrayList(
            "London",
            "Cambridge",
            "Oxford"
    );

    public SettingController(){
        if (Settings.getInstance().sc == null) {
            Settings.getInstance().sc = this;
        }
    }

    //Note: was experimenting with this and soon it became intergral so...
    //basically it is similar to initialize except it doesn't use that
    public void dothis() {
        LocChoiceBox.setItems(locations);
        currentAgeLabel.setText("" + Settings.getInstance().getAge());
        clipedButton.setText((Settings.getInstance().isClipped()) ? "Clipped":"Not Clipped");
        enterAgeButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                enterButtonClicked();
            }
        });
    }

    @FXML
    private Button enterAgeButton, clipedButton;


    @FXML
    private TextField enterAgeText;

    @FXML
    private Label currentAgeLabel;

    @FXML
    private ChoiceBox<String> LocChoiceBox;

    //I do have the initialize function was only checking to see which one was called earlier
    @FXML
    private void initialize() {
//        clippedCheckBox.setSelected(currentInfo.clipped);
        LocChoiceBox.setItems(locations);
        LocChoiceBox.setOnAction(e-> locationSelector());
    }

    //event handler for the choice box
    //whenever clicked we set the location according to some id
    @FXML
    private void locationSelector() {
        Settings.getInstance().setLocation(Settings.getInstance().locToInt.get(LocChoiceBox.getValue()));
    }

    //This is the event handler for clipped button used whenever it is clicked
    @FXML
    private void whenSelected() {
        Settings.getInstance().setClipped(!Settings.getInstance().isClipped());
        if(Settings.getInstance().isClipped()) clipedButton.setText("Clipped");
        else clipedButton.setText("Not Clipped");
    }

    //event handler for whenever the enter age button is clicked
    //checks if the input is of the interger or not
    @FXML
    private void enterButtonClicked() {
        String enteredAge = enterAgeText.getText();
        try {
            int age = Integer.parseInt(enteredAge);
            currentAgeLabel.setText(enteredAge);
            Settings.getInstance().setAge(age);
        } catch (NumberFormatException e) {

        }
    }

    //Changes the Scene to Home
    @FXML
    private void changeScene() throws IOException {

        FXMLLoader homeLoader = new FXMLLoader(getClass().getResource("Home.fxml"));
        HomeController hc = Settings.getInstance().hc;
        Stage window =(Stage) enterAgeButton.getScene().getWindow();

        homeLoader.setController(hc);
        Parent home = homeLoader.load();



        window.setScene(new Scene(home, 406, 800));
        Settings.getInstance().hc.initialize();
        window.show();
    }

    //Changes the Scene to Choose Day
    @FXML
    private void changeToDay() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ChooseDay.fxml"));
        Stage window = (Stage) enterAgeButton.getScene().getWindow();
        HomeController hc = new HomeController();
        DayController dc = new DayController();

        loader.setController(dc);
        Parent settings = loader.load();

        window.setScene(new Scene(settings, 406, 800));
        window.show();
    }

//    @FXML
//    private void close() {
//        ((Stage) enterAgeButton.getScene().getWindow()).close();
//    }
}
