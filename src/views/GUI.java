package views;

import controllers.Factory;
//import javafx.application.Application;
//import javafx.event.ActionEvent;
//import javafx.fxml.FXML;
//import javafx.fxml.FXMLLoader;
//import javafx.fxml.Initializable;
//import javafx.scene.Parent;
//import javafx.scene.Scene;
//import javafx.scene.control.TextArea;
//import javafx.scene.control.TextField;
//import javafx.scene.image.Image;
//import javafx.stage.Stage;
//import models.Item;
//import models.ItemTimePeriod;
//import models.ItemZoneTime;
//import models.Zone;

/*import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URL;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static java.lang.String.valueOf;


public class GUI extends Application implements Initializable {

    private final TreeMap<String, HashMap<String, ArrayList<Item>>> modelList = new TreeMap<>();
    private final TreeMap<Integer, Integer> zonePlanner = new TreeMap<>();

    Factory factory = new Factory();
    ExecutorService threadPool = Executors.newCachedThreadPool();

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


    public void addToModelList(ActionEvent actionEvent) {
        String[] zonesArray = zoneOrder.getText().split(" ");
        ArrayList<String> averageTimeInZoneArray = new ArrayList<>(Arrays.asList(averageTimeInZone.getText().split(" ")));
        ArrayList<Item> itemArrayList = new ArrayList<>();

        for (int i = 0; i < averageTimeInZoneArray.size(); i++) {
            itemArrayList.add(new ItemZoneTime(factory.getZone(Integer.parseInt(zonesArray[i])), Float.parseFloat(averageTimeInZoneArray.get(i))));
        }

        factory.addNewModel("M" + factory.getDefaultModels().size()+3, itemArrayList, new ItemTimePeriod(Integer.parseInt(minDay.getText()), Integer.parseInt(maxDay.getText())));

        zoneOrder.clear();
        averageTimeInZone.clear();
        minDay.clear();
        maxDay.clear();
    }

    public void addZone(ActionEvent actionEvent) {
        factory.addZone(factory.getZones().size()+1, Integer.parseInt(numberOfLinesInZone.getText()));
        numberOfLinesInZone.clear();
    }

    public void changeNumberOfLinesInZone(ActionEvent actionEvent) {
        factory.updateZone(Integer.parseInt(zoneId.getText()), Integer.parseInt(newNumberOfLinesInZone.getText()));
        newNumberOfLinesInZone.clear();
        zoneId.clear();
    }

    public void simulate() {
        factory.startSimulation();
        try {
            showResults();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void terminalDebugger() throws IOException {
        ArrayList<String> tester = new ArrayList<>();
        factory.getZones().values().forEach(zone -> tester.add(zone.getLineNumber() + " "));
        System.out.println(tester);
        showResults();
    }

    public void showResults() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/results.fxml"));
        Parent root = fxmlLoader.load();
        Stage window = new Stage();
        window.setTitle("Resultados");
        window.setScene(new Scene(root, 800, 300));
        window.show();

        //results1.setText("bah");
    }

    public static void main(String[] args) {
        launch(args);
    }
}
*/