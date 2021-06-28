package models;

import java.util.concurrent.TimeUnit;
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
                   System.out.println("BreakStoped Line da zona" + zone.getID());
                   updateTotalBreak();
                   TimeUnit.MILLISECONDS.sleep(Math.round(model.getZoneAndTimeAt(this.zone)));
                   //Thread.sleep(erlang.getValue(timeOnZone));
               } catch (InterruptedException e) {
                   e.printStackTrace();
               }
               System.out.println("Dei Stamp na zona " + zone.getID() + "no modelo " + model.getModel());
               model.stampZone();
               if (model.isVehicleComplete()){
                   System.out.println("Veiculo do modelo " + model.getModel() + " terminado na zona " + this.zone.getID());
                   model.isFinished();
               }else{
                   model.takePause();
                   Zone newZone = model.getNextZone();
                   model.setZone(newZone);
                   newZone.addToQueue(model);
               }
               takeBreak();
           }
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