package models;

import controllers.Factory;

public class EndSim implements Runnable{
    Factory factory;
    public EndSim(Factory factory){
        this.factory = factory;
    }
    @Override
    public void run() {
        try {
            Thread.sleep(30*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        factory.endSimulation();

    }
}
