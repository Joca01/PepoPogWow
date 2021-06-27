package models;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

public class Zone {
    private int ID;
    private int lineAmount;
    //Maybe this is inside the Line
    private LinkedBlockingQueue<Vehicle> queue;
    private ExecutorService lines;
    private AtomicBoolean simulationNotOver;


    public Zone(int ID, int lines, AtomicBoolean simulationNotOver) {
        this.ID = ID;
        this.lineAmount = lines;
        this.queue = new LinkedBlockingQueue<Vehicle>();
        this.lines = Executors.newFixedThreadPool(lines);
        this.simulationNotOver = simulationNotOver;
    }

    public void startWorkingLines(){
        for(int i = 0; i<lineAmount; i++){
            this.lines.submit(new Line(this, this.simulationNotOver));
        }
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
        this.lines = Executors.newFixedThreadPool(amount);
    }

    public Vehicle popFromQueue(){
        Vehicle vehicle = null;
        try {
            vehicle = queue.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
       return vehicle;
    }



}
