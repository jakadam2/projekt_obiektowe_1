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
        for(int i = 0; i < config.getStartAnimal(); i++){
            animals.add(new Animal(config,map));
        }
        //while true
        for(Animal animal:animals){
            animal.move();
            System.out.println(animal.getPosition().toString());
        }
        //map.addGrass();
        /*toAdd = map.checkReproduction();
        map.checkReproduction();
        for(Animal animal: toAdd){
            animals.add(animal);
        }*/
        map.checkEating();


    }
}






