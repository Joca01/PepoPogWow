package models;

import java.util.ArrayList;
import java.util.HashMap;
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
    private HashMap<String,Float> linePercentages;
    private ArrayList<Line> linesList;

    public Zone(int ID, int lines, AtomicBoolean simulationNotOver) {
        this.ID = ID;
        this.lineAmount = lines;
        this.queue = new LinkedBlockingQueue<Vehicle>();
        this.lines = Executors.newFixedThreadPool(lines);
        this.simulationNotOver = simulationNotOver;
        this.linesList = new ArrayList<Line>();
        populateLines();
    }

    private int getLineNumber(){
        return this.lineAmount;
    }

    private void populateLines() {
        for(int i = 0 ; i<lineAmount;i++){
            Line line = new Line(this,simulationNotOver);
            linesList.add(line);
        }
    }

    public void startWorkingLines(){
        for(Line line : linesList){
            this.lines.submit(line);
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

    public HashMap<String,Double> getLinesWorkPercentage(){
        HashMap<String,Double> returnHash = new HashMap<>();
        for (int i = 0 ; i < linesList.size();i++){
            Line line = linesList.get(i);
            String name = "L"+(i+1);
            returnHash.put(name,line.getTimeWorkingPercentage());
        }
        return  returnHash;
    }

    public String getID() {
        return "Z"+this.ID;
    }
}



