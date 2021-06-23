package controllers;

import models.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Factory {
    private final Map vehicles;
    private final Map zones;
    private final Map<String, Map<String, ArrayList<Item>>> defaultModels;
    public Factory() {
        zones = new HashMap<Integer, Zone>();
        defaultModels = new HashMap<String, Map<String, ArrayList<Item>>>();
        vehicles = new HashMap<String, Vehicle>();
        populateDefaultZones();
        populateDefaultModels();
    }

    public void addZone(int ID, int lines) {
        Zone zone = new Zone(ID, lines);
        zones.put(ID, zone);
    }

    public void createVehicle(String modelName){
        Map<String,ArrayList<Item>> model = defaultModels.get(modelName);
        ItemTimePeriod orderTime = (ItemTimePeriod) model.get("TimeToOrder").get(0);
        ArrayList<Item> zones = model.get("Zones");
        Vehicle vehicle = new Vehicle(zones,orderTime);
        vehicles.put(modelName,vehicle);

    }

    public void startSimulation(){
        
    }

   public ArrayList<HashMap<String,Float>> getAverageWaitingTimeVehicle(){

   }


    public void addNewModel(String name, ArrayList<Item> zones,ItemTimePeriod orderTime){

        Map<String,ArrayList<Item>> infoModel = new HashMap<String,ArrayList<Item>>();
        infoModel.put("TimetoOrder",new ArrayList<Item>(List.of(orderTime)));
        infoModel.put("Zones",zones);
        defaultModels.put(name, infoModel);

    }
    private void populateDefaultZones() {
        addZone(1, 3);
        addZone(2, 2);
        addZone(3, 4);
        addZone(4, 3);
        addZone(5, 1);
    }

    private void populateDefaultModels(){
        //Create Info Maps
        Map<String,ArrayList<Item>> infoM1 = new HashMap<String,ArrayList<Item>>();
        Map<String,ArrayList<Item>> infoM2 = new HashMap<String,ArrayList<Item>>();
        Map<String,ArrayList<Item>> infoM3 = new HashMap<String,ArrayList<Item>>();
        //Add info to Models
        infoM1.put("TimetoOrder",new ArrayList<Item>(List.of(new ItemTimePeriod(3,7))));
        infoM1.put("Zones",new ArrayList<Item>(List.of(new ItemZoneTime((Zone) zones.get(4), 1.10f),
                new ItemZoneTime((Zone) zones.get(1),0.80f), new ItemZoneTime((Zone) zones.get(3), 0.75f))));

        infoM2.put("TimetoOrder",new ArrayList<Item>(List.of(new ItemTimePeriod(4,6))));
        infoM2.put("Zones",new ArrayList<Item>(List.of(new ItemZoneTime((Zone) zones.get(3), 0.50f),
                    new ItemZoneTime((Zone) zones.get(1),0.60f), new ItemZoneTime((Zone) zones.get(2), 0.85f),
                    new ItemZoneTime((Zone) zones.get(5), 0.50f))));

        infoM3.put("TimetoOrder",new ArrayList<Item>(List.of(new ItemTimePeriod(4,6))));
        infoM3.put("Zones",new ArrayList<Item>(List.of(new ItemZoneTime((Zone) zones.get(2), 1.20f),
                    new ItemZoneTime((Zone) zones.get(5),0.25f), new ItemZoneTime((Zone) zones.get(1), 0.70f),
                    new ItemZoneTime((Zone) zones.get(4), 0.90f), new ItemZoneTime((Zone) zones.get(3),1.0f))));


            defaultModels.put("M1", infoM1);
            defaultModels.put("M2", infoM2);
            defaultModels.put("M3", infoM3);
    }
}
