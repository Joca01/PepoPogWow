package models;

import java.util.concurrent.atomic.AtomicBoolean;

public class Line implements Runnable{
    //TODO Get working vs Idle time
    private Erlang erlang;
    private Vehicle model;
    private Zone zone;
    private AtomicBoolean simulationNotOver;

    public Line(Zone zone, AtomicBoolean simulationNotOver){
        this.zone = zone;
        this.simulationNotOver = simulationNotOver;
    }

    @Override
    public void run() {
        //TODO check model in zone , say its ready , swap to new zone Q
       while (simulationNotOver.get()){
           this.model = zone.popFromQueue();


           Float timeOnZone = model.getZoneAndTimeAt(zone);
           try {
               Thread.sleep(erlang.getValue(timeOnZone));
           } catch (InterruptedException e) {
               e.printStackTrace();
           }
           model.takePause();

       }
    }
}