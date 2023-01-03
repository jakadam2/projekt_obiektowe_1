package oop;

import java.util.HashSet;
import java.util.Set;

public class SimulationEngine {

    Settings config;

    Set<Animal> animals = new HashSet<>();

    AbstractWorldMap map;

    public SimulationEngine(Settings config){
        this.config = config;

    }



    public void run(){
        if(config.getMapType() == MapType.NETHER){
            map = new Nether(config);
        }
        else {
            map = new Earth(config);
        }
        Set<Animal> toAdd;
        Set<Animal> toRemove;

        for(int i = 0; i < config.getStartAnimal(); i++){
            animals.add(new Animal(config,map));
        }
        for(int i = 0; i < config.getDailyPlant();i ++){map.addGrass();}
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

    }
}






