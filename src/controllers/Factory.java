package controllers;

import models.*;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class Factory {
    private HashMap<String,ArrayList<Vehicle>> vehicles;
    private HashMap<Integer,Zone> zones;
    private final AtomicBoolean simulationNotOver;
    private final Map<String, Map<String, ArrayList<Item>>> defaultModels;
    private ExecutorService orderPool = Executors.newCachedThreadPool();

    public Factory() {
        simulationNotOver = new AtomicBoolean(true);
        zones = new HashMap<Integer, Zone>();
        defaultModels = new HashMap<String, Map<String, ArrayList<Item>>>();
        vehicles = new HashMap<String, ArrayList<Vehicle>>();
        populateDefaultZones();
        populateDefaultModels();
    }

    public Map<Integer,Zone> getZones(){
       return zones;
    }

    public void addZone(int ID , int lines) {
        Zone zone = new Zone(ID, lines, this.simulationNotOver);
        zones.put(ID, zone);
    }

    public synchronized void createVehicle(String modelName){
        Map<String,ArrayList<Item>> model = defaultModels.get(modelName);
        ItemTimePeriod orderTime = (ItemTimePeriod) model.get("TimetoOrder").get(0);

        ArrayList<Item> zones = model.get("Zones");

        Zone firstZone = (Zone) zones.get(0).getFirstField();

        Vehicle vehicle = new Vehicle(zones,orderTime);

        if(!vehicles.containsKey(modelName)){
            ArrayList<Vehicle> array = new ArrayList<>();
            this.vehicles.put(modelName,array);
        }
            vehicles.get(modelName).add(vehicle);

        vehicle.setZone(firstZone);
        firstZone.addToQueue(vehicle);

    }



    public HashMap<String,HashMap<String,Double>> getLinesPercentageWork(){
        HashMap<String,HashMap<String ,Double>> returnHash = new HashMap<>();

        for (Zone zone : zones.values()){
            returnHash.put(zone.getID(),zone.getLinesWorkPercentage());
        }
        return returnHash;

    }

    public ArrayList<ItemModelAverage> getModelsAverageWaitingTime() {
        ArrayList<ItemModelAverage> returnArray = new ArrayList<ItemModelAverage>();
        for (String model : defaultModels.keySet()) {
            long time = 0;
            int amount = 0;
            for (Vehicle vehicle : (ArrayList<Vehicle>) vehicles.get(model)) {
                time += vehicle.getWaitingTime();
                amount++;
            }
            double average = time/amount;
            returnArray.add(new ItemModelAverage(model,average));

        }
        return returnArray;
    }

    public ArrayList<ItemModelAverage> getModelsAverageBuildingTime() {
        ArrayList<ItemModelAverage> returnArray = new ArrayList<ItemModelAverage>();
        for (String model : defaultModels.keySet()) {
            long time = 0;
            int amount = 0;
            for (Vehicle vehicle : (ArrayList<Vehicle>) vehicles.get(model)) {
                time += vehicle.getTimeToFinish();
                amount++;
            }
            float average = time / amount;
            returnArray.add(new ItemModelAverage(model, average));
        }
        return returnArray;
    }

    public int getModelCount() {
        return defaultModels.keySet().size();
    }

    public void startSimulation() {
        //need to start the zones
        //put an order for each model
        Collection<Zone> zones = this.zones.values();
        for (Zone zone : zones) {
            zone.startWorkingLines();
        }

        //orderPool = Executors.newFixedThreadPool(defaultModels.keySet().size());
        for(String key : this.defaultModels.keySet()){
            orderPool.submit(new Order(key, this, simulationNotOver));
        }

        try{
            Thread.sleep(30*1000);
        }catch (InterruptedException e){

        }

        endSimulation();

    }


   public void endSimulation(){
       simulationNotOver.set(false);
       orderPool.shutdown();
       try {
           orderPool.awaitTermination(1,TimeUnit.MILLISECONDS);
       } catch (InterruptedException e) {

       }


   }

    public void addNewModel(String name, ArrayList<Item> zones,ItemTimePeriod orderTime){

        Map<String, ArrayList<Item>> infoModel = new HashMap<String, ArrayList<Item>>();
        infoModel.put("TimetoOrder", new ArrayList<Item>(List.of(orderTime)));
        infoModel.put("Zones", zones);
        defaultModels.put(name, infoModel);
    }

    private void populateDefaultZones(){
        addZone(1,3);
        addZone(2,2);
        addZone(3,4);
        addZone(4,3);
        addZone(5,1);
    }



    private void populateDefaultModels() {
        //Create Info Maps
        Map<String, ArrayList<Item>> infoM1 = new HashMap<String, ArrayList<Item>>();
        Map<String, ArrayList<Item>> infoM2 = new HashMap<String, ArrayList<Item>>();
        Map<String, ArrayList<Item>> infoM3 = new HashMap<String, ArrayList<Item>>();
        //Add info to Models
        infoM1.put("TimetoOrder", new ArrayList<Item>(List.of(new ItemTimePeriod(3, 7))));
        infoM1.put("Zones", new ArrayList<Item>(List.of(new ItemZoneTime((Zone) zones.get(4), 1.10f),
                new ItemZoneTime((Zone) zones.get(1), 0.80f), new ItemZoneTime((Zone) zones.get(3), 0.75f))));

        infoM2.put("TimetoOrder", new ArrayList<Item>(List.of(new ItemTimePeriod(4, 6))));
        infoM2.put("Zones", new ArrayList<Item>(List.of(new ItemZoneTime((Zone) zones.get(3), 0.50f),
                new ItemZoneTime((Zone) zones.get(1), 0.60f), new ItemZoneTime((Zone) zones.get(2), 0.85f),
                new ItemZoneTime((Zone) zones.get(5), 0.50f))));

        infoM3.put("TimetoOrder",new ArrayList<Item>(List.of(new ItemTimePeriod(2,5))));
        infoM3.put("Zones",new ArrayList<Item>(List.of(new ItemZoneTime((Zone) zones.get(2), 1.20f),
                    new ItemZoneTime((Zone) zones.get(5),0.25f), new ItemZoneTime((Zone) zones.get(1), 0.70f),
                    new ItemZoneTime((Zone) zones.get(4), 0.90f), new ItemZoneTime((Zone) zones.get(3),1.0f))));


        defaultModels.put("M1", infoM1);
        defaultModels.put("M2", infoM2);
        defaultModels.put("M3", infoM3);
    }

    public Zone getZone(Integer zoneID) {
        return (Zone) zones.get(zoneID);
    }

    public void updateZone(int zoneID, int lines) {
        getZone(zoneID).setLines(lines);
    }

    public Map<String, Map<String, ArrayList<Item>>> getDefaultModels() {
        return this.defaultModels;
    }


    @Override
    public String toString() {
        return "Factory{" +
                "vehicles=" + vehicles +
                '}';
    }
}
