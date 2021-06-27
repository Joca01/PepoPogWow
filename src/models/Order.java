package models;

import controllers.Factory;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class Order implements Runnable{
    private Factory factory;
    private String modelName;
    private int minOrder;
    private int maxOrder;

    public Order(String modelName, Factory factory){
        this.modelName = modelName;
        this.factory = factory;
        ItemTimePeriod orderTime = (ItemTimePeriod) this.factory.getDefaultModels().get(modelName).get("TimetoOrder").get(0);
        this.minOrder = orderTime.getFirstField();
        this.maxOrder = orderTime.getSecondField();
    }

    @Override
    public void run() {
        double orderTime = ThreadLocalRandom.current().nextDouble( timeTranslator(this.minOrder), timeTranslator(this.maxOrder));
        try {
            TimeUnit.MILLISECONDS.sleep(Math.round(orderTime * 1000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.factory.createVehicle(modelName);
    }

    private double timeTranslator(int value){
        return value * (27/ 365);
    }
}
