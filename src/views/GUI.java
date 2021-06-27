package views;

import controllers.Factory;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

public class GUI extends Application implements Initializable {

    private HashMap<String, String> modelList = new HashMap<>();

    @FXML
    TextField zoneOrder;
    @FXML
    TextField averageTimeInZone;
    @FXML
    TextField minDay;
    @FXML
    TextField maxDay;
    @FXML
    TextField numberOfLineInZone;
    @FXML
    TextField zoneId;
    @FXML
    TextField newNumberOfLines;


    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/menu.fxml"));
        Parent root = fxmlLoader.load();
        stage.setTitle("projectoSimulação");
        stage.setScene(new Scene(root, 420, 275));


        stage.setResizable(false);
        stage.show();
    }


    public void simulate() {

        for (String key : modelList.keySet()) {
            System.out.println(key);
        }
        for (String value : modelList.values()) {
            System.out.println(value);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void addToModelList(ActionEvent actionEvent) {
        if (zoneOrder.getText() != null && averageTimeInZone.getText() != null) {
            modelList.put(String.valueOf(zoneOrder.getText()), String.valueOf(averageTimeInZone.getText()));
        }
        zoneOrder.clear();
        averageTimeInZone.clear();
    }
}
