package models;

import java.util.ArrayList;
import java.util.List;

public class Vehicle {
    //Should take information from user input and filter it and populate the atributes
    //ItemZoneTime is (Zone , float) -> where Zone is the zone object and float is the time the model takes on it
    private ArrayList<Item> zones;
    private ItemTimePeriod timePeriod;
    private Zone currentZone; //need to know where the current vehicle is at;
    private List<Zone> pastedThroughZones;
    private long initialized;
    private long finished;
    private long startPause;
    private long finishPause;
    private long totalPause;

    public Vehicle(ArrayList<Item> zones , ItemTimePeriod timePeriod ){
        this.initialized = System.currentTimeMillis();
        this.totalPause =0;
       this.zones = zones;
       this.timePeriod = timePeriod;
       this.currentZone = null;
       this.pastedThroughZones = new ArrayList<Zone>();
    }

    //TODO method to mesure in Q time

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
        //TODO chek if there is a better way to do this check or if this one can bugg out if the car is at the zone but hasn't yet complete its time
        if (pastedThroughZones.size() == zones.size()){
            return true;
        }
        return false;
    }

    public Float getZoneAndTimeAt(Zone zone){
        //Returns the Zone and average time from the given postion as an ItemZoneTime , where we can then extract each one;
        for (Item item : zones){
           if (item.getFirstField() == zone){
               return (Float) item.getSecondField();
           }
        }
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

    public long getWaitingTime() {
        return totalPause;
    }
}
