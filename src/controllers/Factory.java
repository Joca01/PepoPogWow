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

        Vehicle vehicle = new Vehicle(modelName, zones,orderTime);

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
        Collection<Zone> zones = this.zones.values();
        for (Zone zone : zones) {
            System.out.println(zone.getLineNumber());
            zone.startWorkingLines();
        }

        //orderPool = Executors.newFixedThreadPool(defaultModels.keySet().size());
        for(String key : this.defaultModels.keySet()){
            orderPool.submit(new Order(key, this, simulationNotOver));
        }


       Thread p = new Thread(new EndSim(this));
        p.start();
        try {
            p.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }


   public void endSimulation(){

       System.out.println("Simulation is ending");
       try {
           orderPool.awaitTermination(1,TimeUnit.MILLISECONDS);
       } catch (InterruptedException e) {

       }
       orderPool.shutdown();
       simulationNotOver.set(false);

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

//TODO tentar fazer um populate Models em que sem usar o list OF
    private void populateDefaultModels(){
            ArrayList<Item> M1ZT = new ArrayList<>();
            ArrayList<Item> M2ZT = new ArrayList<>();
            ArrayList<Item> M3ZT = new ArrayList<>();


        //Create Info Maps
            Map<String, ArrayList<Item>> infoM1 = new HashMap<String, ArrayList<Item>>();
            Map<String, ArrayList<Item>> infoM2 = new HashMap<String, ArrayList<Item>>();
            Map<String, ArrayList<Item>> infoM3 = new HashMap<String, ArrayList<Item>>();
            //Add info to Models
            infoM1.put("TimetoOrder", new ArrayList<Item>(List.of(new ItemTimePeriod(3, 7))));
            ItemZoneTime M1Z1 = new ItemZoneTime(zones.get(4),1.10f);
            ItemZoneTime M1Z2 = new ItemZoneTime(zones.get(1),0.80f);
            ItemZoneTime M1Z3 = new ItemZoneTime(zones.get(3),0.75f);
            M1ZT.add(M1Z1); M1ZT.add(M1Z2); M1ZT.add(M1Z3);
            infoM1.put("Zones",M1ZT);

        infoM2.put("TimetoOrder", new ArrayList<Item>(List.of(new ItemTimePeriod(4, 6))));
        ItemZoneTime M2Z1 = new ItemZoneTime(zones.get(3),0.50f);
        ItemZoneTime M2Z2 = new ItemZoneTime(zones.get(1),0.60f);
        ItemZoneTime M2Z3 = new ItemZoneTime(zones.get(2),0.85f);
        ItemZoneTime M2Z4 = new ItemZoneTime(zones.get(5),0.50f);
        M2ZT.add(M2Z1); M2ZT.add(M2Z2); M2ZT.add(M2Z3); M2ZT.add(M2Z4);
        infoM2.put("Zones",M2ZT);

        infoM3.put("TimetoOrder", new ArrayList<Item>(List.of(new ItemTimePeriod(2, 5))));
        ItemZoneTime M3Z1 = new ItemZoneTime(zones.get(2),1.20f);
        ItemZoneTime M3Z2 = new ItemZoneTime(zones.get(5),0.25f);
        ItemZoneTime M3Z3 = new ItemZoneTime(zones.get(1),0.70f);
        ItemZoneTime M3Z4 = new ItemZoneTime(zones.get(4),0.90f);
        ItemZoneTime M3Z5 = new ItemZoneTime(zones.get(3),1.00f);
        M3ZT.add(M3Z1); M3ZT.add(M3Z2); M3ZT.add(M3Z3); M3ZT.add(M3Z4);M3ZT.add(M3Z5);
        infoM3.put("Zones",M3ZT);

        defaultModels.put("M1", infoM1);
        defaultModels.put("M2", infoM2);
        defaultModels.put("M3", infoM3);
    }
/*
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
*/
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

    public ArrayList<Vehicle> getVehicles(String name) {
        return this.vehicles.get(name);
    }
}
