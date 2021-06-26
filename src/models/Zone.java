package models;

import java.util.concurrent.LinkedBlockingQueue;

public class Zone {
    private int ID;
    private int lineAmount;
    //Maybe this is inside the Line
    private LinkedBlockingQueue<Vehicle> queue;

    public Zone(int ID, int lines) {
        this.ID = ID;
        this.lineAmount = lines;
        this.queue = new LinkedBlockingQueue<Vehicle>();
    }

    public void addToQueue(Vehicle vehicle) throws InterruptedException {
        try {
            queue.put(vehicle);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    public void setLines(int amount){
        this.lineAmount = amount;
    }

    public Vehicle popFromQueue(){
        //TODO check if its correct to have intiallized as null
        Vehicle vehicle = null;
        try {
            vehicle = queue.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
       return vehicle;
    }



    //TODO fazer as linhas
}
