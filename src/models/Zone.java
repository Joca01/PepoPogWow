package models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

public class Zone {
    private int ID;
    private int lineAmount;
    //Maybe this is inside the Line
    private LinkedList<Vehicle> queue;
    private ExecutorService lines;
    private AtomicBoolean simulationNotOver;
    private HashMap<String,Float> linePercentages;
    private ArrayList<Line> linesList;

    public Zone(int ID, int lines, AtomicBoolean simulationNotOver) {
        this.ID = ID;
        this.lineAmount = lines;
        this.queue = new LinkedList<>();
        this.lines = Executors.newFixedThreadPool(lines);
        this.simulationNotOver = simulationNotOver;
        this.linesList = new ArrayList<Line>();
        populateLines();
    }

    public int getLineNumber(){
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

public synchronized void addToQueue(Vehicle vehicle) { queue.add(vehicle);
    }

    public void setLines(int amount){
        this.lineAmount = amount;
        this.lines = Executors.newFixedThreadPool(amount);
    }

    public synchronized Vehicle popFromQueue(){
        Vehicle vehicle = null;

        vehicle = queue.remove();

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

    public boolean queueEmpty() {
        return this.queue.isEmpty();
    }
}



