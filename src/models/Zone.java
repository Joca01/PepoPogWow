package models;

public class Zone {
    private int ID;
    private int lineAmount;
    //Maybe this is inside the Line
    private MyQueue<Vehicle> queue;

    public Zone(int ID, int lines) {
        this.ID = ID;
        this.lineAmount = lines;
        this.queue = new MyQueue<Vehicle>();
    }

    public void addToQueue(Vehicle vehicle){
        queue.add(vehicle);
    }

    public Vehicle popFromQueue(){
        return queue.pop();
    }



    //TODO fazer as linhas
}
