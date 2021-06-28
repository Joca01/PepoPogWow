package models;

import controllers.Factory;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class Order implements Runnable{
    private Factory factory;
    private String modelName;
    private int minOrder;
    private int maxOrder;
    private AtomicBoolean simulationNotOver;
    public Order(String modelName, Factory factory , AtomicBoolean simulationNotOver){
        this.simulationNotOver = simulationNotOver;
        this.modelName = modelName;
        this.factory = factory;
        ItemTimePeriod orderTime = (ItemTimePeriod) this.factory.getDefaultModels().get(modelName).get("TimetoOrder").get(0);
        this.minOrder = orderTime.getFirstField();
        this.maxOrder = orderTime.getSecondField();
    }

    @Override
    public void run() {
        while(simulationNotOver.get()){
            double min = timeTranslator(minOrder);
            double max = timeTranslator(maxOrder);
            double orderTime = ThreadLocalRandom.current().nextDouble(min,max);
            try {
                TimeUnit.MILLISECONDS.sleep(Math.round(orderTime * 1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            factory.createVehicle(modelName);
            System.out.println( "Temos " + factory.getVehicles(modelName).size() + " Veiculos " +modelName);
        }
    }

    private double timeTranslator(int value){
        return value * (27d/ 365d);
    }
}
