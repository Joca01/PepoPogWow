package models;

import java.util.concurrent.atomic.AtomicBoolean;

public class Line implements Runnable{
    private Erlang erlang;
    private Vehicle model;
    private Zone zone;
    private long timeAlive;
    private long startingTime;
    private long endingTime;
    private long startBreak;
    private long endBreak;
    private long totalBreak;
    private AtomicBoolean simulationNotOver;

    public Line(Zone zone, AtomicBoolean simulationNotOver){
        this.zone = zone;
        this.simulationNotOver = simulationNotOver;
    }

    @Override
    public void run() {
       this.startingTime = System.currentTimeMillis();
       takeBreak();
       while (simulationNotOver.get()){
           if(!zone.queueEmpty()){
               this.model = zone.popFromQueue();

               model.stopPause();
               model.updateTotalPause();

               Float timeOnZone = model.getZoneAndTimeAt(zone);
               try {
                   stopBreak();
                   updateTotalBreak();
                   Thread.sleep(erlang.getValue(timeOnZone));
               } catch (InterruptedException e) {
                   e.printStackTrace();
               }
               model.stampZone();
               if (model.isVehicleComplete()){
                   model.isFinished();
               }else{
                   model.takePause();
                   Zone newZone = model.getNextZone();
                   model.setZone(newZone);
                   newZone.addToQueue(model);
               }
               takeBreak();
           }
           System.out.println("trabalhando");
       }
       this.endingTime = System.currentTimeMillis();
       updateTimeAlive();
        System.out.println("Time Update");
    }

    private void updateTimeAlive() {
        timeAlive = endingTime - startingTime;
    }

    private void updateTotalBreak() {
        totalBreak = endBreak - startBreak;
    }

    private void stopBreak() {
        endBreak = System.currentTimeMillis();
    }

    public ItemTimeInformation getTimeInformation(){
        return new ItemTimeInformation(timeAlive,totalBreak);
    }

    private void takeBreak() {
        this.startBreak = System.currentTimeMillis();
    }

    public double getTimeWorkingPercentage(){
        return ((timeAlive-totalBreak) * 100)/24000d;
    }

}