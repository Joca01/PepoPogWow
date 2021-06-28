package views;

import controllers.Factory;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import models.Item;
import models.Zone;

import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URL;
import java.util.*;

import static java.lang.String.valueOf;


public class GUI extends Application implements Initializable {

    private final HashMap<String, ArrayList<String>> modelList = new HashMap<String, ArrayList<String>>();
    private final TreeMap<Integer, Integer> zonePlanner = new TreeMap<>();

    @FXML
    TextField zoneOrder;
    @FXML
    TextField averageTimeInZone;
    @FXML
    TextField minDay;
    @FXML
    TextField maxDay;
    @FXML
    TextField numberOfLinesInZone;
    @FXML
    TextField zoneId;
    @FXML
    TextField newNumberOfLinesInZone;
    @FXML
    TextArea results1;
    @FXML
    TextArea results2;
    @FXML
    TextArea results3;

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


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }


    //FIXME In order to work with Factory addNewModel method, modelList should be a HashMap containing a String Name as Key and an ArrayList containing zones, average time in zones,
    //FIXME minDay and MaxDay
    public void addToModelList(ActionEvent actionEvent) {
        if (!valueOf(zoneOrder.getText()).equals("") && !valueOf(averageTimeInZone.getText()).equals("") && !valueOf(minDay.getText()).equals("") && !valueOf(minDay.getText()).equals("")) {
            modelList.put(valueOf(zoneOrder.getText()), (valueOf(averageTimeInZone.getText()), "teste", "test2"));
        }
        zoneOrder.clear();
        averageTimeInZone.clear();
    }

    public void addZone(ActionEvent actionEvent) {
        if (!valueOf(numberOfLinesInZone.getText()).equals("")) {
            if (zonePlanner.size() != 0) {
                zonePlanner.put(zonePlanner.lastKey()+1, Integer.parseInt(numberOfLinesInZone.getText()));
            }
            else {
                zonePlanner.put(1, Integer.parseInt(numberOfLinesInZone.getText()));
            }
        }
        numberOfLinesInZone.clear();
    }

    public void changeNumberOfLinesInZone(ActionEvent actionEvent) {
        if (zonePlanner.containsKey(Integer.valueOf(zoneId.getText()))) {
            zonePlanner.replace(Integer.parseInt(zoneId.getText()), Integer.parseInt(newNumberOfLinesInZone.getText()));
        }
        zoneId.clear();
        newNumberOfLinesInZone.clear();
    }

    public void simulate() throws IOException {

        Factory factory = new Factory();

        zonePlanner.forEach(factory::addZone);          //Creates zones based on zonePlaner Map (key = id, value = lines)

        //FIXME Should work after finishing addToModelList method
        //modelList.forEach(factory::addNewModel);        //Creates models based on modelList Map
        
        showResults();
    }

    public void terminalDebugger() {
        System.out.println("keys" + modelList.keySet());
        System.out.println("values " + modelList.values());
        System.out.println("zones" + zonePlanner);
    }

    public void showResults() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/results.fxml"));
        Parent root = fxmlLoader.load();
        Stage window = new Stage();
        window.setTitle("Resultados");
        window.setScene(new Scene(root, 800, 300));
        window.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
