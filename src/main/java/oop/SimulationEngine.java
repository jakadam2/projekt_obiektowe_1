package oop;

import java.util.Set;

public class SimulationEngine {

    Settings config;

    Set<Animal> animals;

    AbstractWorldMap map;

    public SimulationEngine(Settings config){
        this.config = config;
        for(int i = 0; i < config.getStartAnimal(); i++){
            animals.add(new Animal(config));
        }
        if(config.getMapType() == MapType.NETHER){
            map = new Nether(config);
        }
        else {
            map = new Earth(config);
        }
    }

    public void run(){
        Set<Animal> toAdd;
        while (true) {
            for (Animal animal : animals) {
                animal.move();
            }
            map.checkEating();
            toAdd = map.checkReproduction();
            for(Animal animal: toAdd){
                animals.add(animal);
            }
        }
    }





}
