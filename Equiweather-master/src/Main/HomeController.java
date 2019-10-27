package Main;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.FileInputStream;

import java.io.IOException;
import java.util.List;

public class HomeController {

    private DayController dayController;





    public HomeController() {

        if (Settings.getInstance().hc == null) {
            Settings.getInstance().hc = this;
        }
        dayController = new DayController();
    }

    //The labels at the top which tell us about the location, day, age and clipping of the horse
    @FXML private Label dayLabel, ageLabel, locLabel, clippedLabel;


    //this function is called implicitly by FXMLLoader.load() fucntion and thus we are making our changes here
    @FXML
    public void initialize() {
        implementChanges();
    }

    //Changing the Labels and then calling a function that does the list view
    @FXML
    public void implementChanges() {
        try {
            int day = Settings.getInstance().getDay();
//            System.out.println(day + " day");
            if (day == 0) {
                dayLabel.setText("Day: Today");
            } else if (day == 1) {
                dayLabel.setText("Day: Tomorrow");
            } else if (day == 2) {
                dayLabel.setText("Day: Day After");
            }

            ageLabel.setText("Age: " + Settings.getInstance().getAge() + " y/o");
            locLabel.setText("Location: " +Settings.getInstance().intToLoc.get(Settings.getInstance().getLocation()));

            if (Settings.getInstance().isClipped()) clippedLabel.setText("Clipped");
            else clippedLabel.setText("Not Clipped");
            implementListView();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    @FXML
    private ListView<HBox> datesList;

    //Implementing the ListView in our scene
    public void implementListView() throws IOException {
        Backend backend = new Backend();
        List<WeatherItem> weatherData = backend.getWeather(); //getting the data from the backend
        datesList.getItems().clear();

        Label from, temp, wind, conditions, rug, safety; //all the labels that will contain relevant information in the HBoxes

        for (int x = 0; x<weatherData.size(); x++) { //Iterating over all the weather items
            WeatherItem item = weatherData.get(x);
            //Initializing all the variable
            from = new Label();
            temp = new Label();
            wind = new Label();
            conditions = new Label();
            rug = new Label();
            safety = new Label();

            //Settings the basic structure within the HBox
            VBox left = new VBox(from, temp, wind);
            VBox middle = new VBox();
            VBox right = new VBox(safety, rug);
            HBox hBox = new HBox(left, middle, right);

            //setting prefered sizes and spacing
            left.setPrefSize(160, 75);
            middle.setPrefSize(110, 75);
            right.setPrefSize(90, 75);

            left.setSpacing(10);
            right.setSpacing(20);

            //Setting the text on the labels which do not require any conditions
            rug.setText(item.getRug());
            temp.setText("Temperature: " + item.getTemperature());
            wind.setText("Wind Speed: " + item.getWind());
            from.setText("from " + item.getTime());

            conditions.setText(item.getCondition());
//            System.out.println(item.getCondition() + "**************************************");
            String current = new java.io.File(".").getCanonicalPath();
            if (item.getCondition().toLowerCase().startsWith("sun")) {
                ImageView sunnyImage = new ImageView(new Image(getClass().getResourceAsStream("sunny.png")));
                sunnyImage.setFitHeight(50);
                sunnyImage.setFitWidth(50);
                middle.getChildren().addAll(conditions, sunnyImage);
            } else if (item.getCondition().toLowerCase().startsWith("rain")) {
                ImageView rainyImage = new ImageView(new Image(getClass().getResourceAsStream("rainy.png")));
                rainyImage.setFitHeight(50);
                rainyImage.setFitWidth(50);
                middle.getChildren().addAll(conditions, rainyImage);
            } else if (item.getCondition().toLowerCase().startsWith("clou")){
                ImageView cloudImage = new ImageView(new Image(getClass().getResourceAsStream("cloudy.png")));
                cloudImage.setFitHeight(50);
                cloudImage.setFitWidth(50);
                middle.getChildren().addAll(conditions, cloudImage);
            } else if (item.getCondition().toLowerCase().startsWith("win")) {
                //src/Main/windy.png
                ImageView windyImage = new ImageView(new Image(getClass().getResourceAsStream("windy.png")));
                windyImage.setFitHeight(50);
                windyImage.setFitWidth(50);
                middle.getChildren().addAll(conditions, windyImage);
            }

            //Finally setting the Safety labela and putting a border around HBoxes

            if (item.isSafe()) {
                safety.setStyle("-fx-background-color: #00ff00;" +
                        "-fx-font-size: 20");
                safety.setPrefWidth(50);
                safety.setText("Safe");
                hBox.setStyle("-fx-padding: 10;" +
                        "-fx-border-style: solid;" +
                        "-fx-border-width: 3;" +
                        "fx-border-color: green;" +
                        "-fx-border-radius: 3");
            } else {
                safety.setStyle("-fx-background-color: #ff0000");
                safety.setText("Not Safe");
                hBox.setStyle("-fx-padding: 10;" +
                        "-fx-border-style: dashed;" +
                        "-fx-border-width: 3;" +
                        "fx-border-color: red;" +
                        "-fx-border-radius: 3");
            }

            datesList.getItems().add(hBox);
        }

    }

    @FXML
    private ImageView settingsImage;


    //Changes the scene to Settings
    @FXML
    private void changeScene() throws IOException {

//        System.out.println(currentInfo.age + " " + currentInfo.location+ " " +currentInfo.clipped);

        Stage window = (Stage) ageLabel.getScene().getWindow();

        FXMLLoader settingLoader = new FXMLLoader(getClass().getResource("SettingsScene.fxml"));
        SettingController sc = new SettingController();

        settingLoader.setController(sc);
        Parent setting = settingLoader.load();
        sc.dothis();

        window.setScene(new Scene(setting, 406, 800));
        window.show();

    }

    //Changes the Scene to Day
    @FXML
    private void changeToDay() throws IOException {
        Stage window = (Stage) ageLabel.getScene().getWindow();

        FXMLLoader dayLoader = new FXMLLoader(getClass().getResource("ChooseDay.fxml"));
        DayController dc = new DayController();

        dayLoader.setController(dc);
        Parent day = dayLoader.load();

        window.setScene(new Scene(day, 406, 800));
        window.show();

    }

}
