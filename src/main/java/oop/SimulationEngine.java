package oop;

import javafx.stage.Stage;

import java.util.HashSet;
import java.util.Set;

public class SimulationEngine implements Runnable{

    Settings config;
    Stage myStage;

    Set<Animal> animals = new HashSet<>();

    AbstractWorldMap map;
    Set<IStateObserver> observers;

    public SimulationEngine(Settings config,AbstractWorldMap map,Stage stage){
        this.myStage = stage;
        this.config = config;
        this.map = map;
        this.observers = new HashSet<>();
    }



    public void run(){

        Set<Animal> toAdd;
        Set<Animal> toRemove;


        for(int i = 0; i < config.getStartAnimal(); i++){
            animals.add(new Animal(config,map));
        }
        for(int i = 0; i < config.getStartPlant();i ++){map.addGrass();}

        while (true){
        //for(int i = 0; i < config.getDailyPlant();i ++){map.addGrass();}
            System.out.println("ok");
        map.checkEating();
        toAdd = map.checkReproduction();
        toRemove = map.checkDying();
        for(Animal animal:animals){
            animal.move();
        }
        for(Animal animal: toAdd){
            animals.add(animal);
        }
        for(Animal animal: toRemove){
            animals.remove(animal);
        }
        for(int i = 0; i < config.getDailyPlant();i ++){map.addGrass();}
        moveDelay();
        notifyObservers();
        }

    }

    private void moveDelay(){
        try {
            Thread.sleep(300);
        }
        catch (InterruptedException exception){
            System.out.println("DELAY ERROR");
            System.exit(2);
        }
    }

    private void notifyObservers(){
        for(IStateObserver observer:observers ){
            observer.update(myStage,map);
        }
    }

    public void addObserver(IStateObserver observer){
        observers.add(observer);
    }

    public void removeObserver(IStateObserver observer){
        observers.remove(observer);//tu mozna rzucic wyjatek jak go nie ma
    }
}






