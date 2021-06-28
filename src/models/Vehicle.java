package models;

import java.util.ArrayList;
import java.util.List;

public class Vehicle {
    //Should take information from user input and filter it and populate the atributes
    //ItemZoneTime is (Zone , float) -> where Zone is the zone object and float is the time the model takes on it

    private String modelType;
    private ArrayList<Item> zones;
    private ItemTimePeriod timePeriod;
    private Zone currentZone; //need to know where the current vehicle is at;
    private List<Zone> pastedThroughZones;
    private long initialized;
    private long finished;
    private long startPause;
    private long finishPause;
    private long totalPause;

    public Vehicle( String modelType, ArrayList<Item> zones , ItemTimePeriod timePeriod ){
        this.modelType = modelType;
        this.initialized = System.currentTimeMillis();
        this.totalPause =0;
       this.zones = zones;
       this.timePeriod = timePeriod;
       this.currentZone = null;
       this.pastedThroughZones = new ArrayList<Zone>();
    }

    public long getTimeToFinish(){
       return finished-initialized;
    }

    public void updateTotalPause(){
        totalPause += finishPause-startPause;
    }

    public void isFinished(){
        //To be called by the last Zone from zones Array , after required time has passed
        finished = System.currentTimeMillis();
    }

    public void setZone(Zone zone){
        //needs to be called when a vehicle reaches a zone
        if (currentZone != null){
            //adds current Zone to a record b4 changing it
           pastedThroughZones.add(currentZone);
        }
        currentZone = zone;
    }

    public void stopPause(){
        finishPause = System.currentTimeMillis();
    }

    public void takePause(){
        startPause = System.currentTimeMillis();
    }

    public Zone whereAmI(){
       return currentZone;
    }

    public boolean isVehicleComplete(){
        //Verifies if the Vehicle passed through all zones
        if (pastedThroughZones.size() == zones.size()){
            return true;
        }
        return false;
    }

    public Float getZoneAndTimeAt(Zone zone){
        //Returns the Zone and average time from the given position as an ItemZoneTime , where we can then extract each one;
        Float toReturn = null;
        for (Item item : zones){
           if (item.getFirstField() == zone){
               toReturn = (Float) item.getSecondField();
           }
        }
        return toReturn;
    }

    @Override
    public String toString() {
        return "Vehicle{" +
                "zones=" + zones +
                ", timePeriod=" + timePeriod +
                ", currentZone=" + currentZone +
                ", pastedThroughZones=" + pastedThroughZones +
                '}';
    }

   public Zone getNextZone(){
       return (Zone) zones.get(pastedThroughZones.size() + 1).getFirstField();
   }

    public void stampZone(){
        this.pastedThroughZones.add(this.currentZone);
    }

    public long getWaitingTime() {
        return totalPause;
    }

    public String getModel() {
        return this.modelType;
    }
}
