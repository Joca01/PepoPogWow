package views;

import controllers.Factory;
import models.Item;
import models.ItemTimePeriod;
import models.ItemZoneTime;

import java.util.ArrayList;
import java.util.Scanner;

public class Cli{
    public void Cli(){
        start();
    }

    public void start(){
        Factory factory = new Factory();
        Scanner scanner = new Scanner(System.in);
        String line;
        boolean looping = true;
        System.out.println("Simulação deverá demorar aproximadamente 27 segundos");
        while(looping){
        line = scanner.nextLine();
        String[] command = line.split(" ");
        switch (command[0]){
            case "AM":
                String modelName = "M" + Integer.toString(factory.getModelCount());
                int minTime = Integer.parseInt(command[1]);
                int maxTime = Integer.parseInt(command[2]);
                ItemTimePeriod orderTime = new ItemTimePeriod(minTime,maxTime);
                ArrayList<Item> zoneAndTime = new ArrayList<>();
                while (true){
                    String line2 = scanner.nextLine();
                    String[] zones= line2.split(" ");
                    if(zones[0].equals("")){
                        break;
                    }
                    Integer zoneID = Integer.parseInt(zones[0]);
                    float averageTime= Float.parseFloat(zones[1]);
                    ItemZoneTime zoneTime = new ItemZoneTime(factory.getZone(zoneID),averageTime);
                   zoneAndTime.add(zoneTime);
                }
                //adicionar Modelo
                factory.addNewModel(modelName,zoneAndTime,orderTime);
                System.out.println("Modelo "+modelName+" adicionado.");
                break;
            case "AZ":
               //AZ_ZONEID_4
                factory.addZone(Integer.parseInt(command[1]),Integer.parseInt(command[2]));
                System.out.println("Zona" + command[1]+" criada com " +command[2]+" linhas.");
                break;
            case "MZ":
                //MZ_ZONEID_4
                factory.updateZone(Integer.parseInt(command[1]),Integer.parseInt(command[2]));
                System.out.println("Zona" + command[1]+" passou a ter" +command[2]+" linhas.");
                break;
            case "Start":
                System.out.println("Simulação iniciada...");
                //Começar simulação
                System.out.println("Resultados: ");
                break;
            case "":
                looping = false;
            default:
                System.out.println("Comando não reconhecido.");

        }

        }

    }
}
