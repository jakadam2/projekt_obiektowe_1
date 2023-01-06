package oop;

import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import oop.Gui.AnimalRepresentative;
import oop.Gui.ElementRepresentative;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class SimulationEngine implements Runnable{

    Settings config;
    Stage myStage;
    Set<Animal> animals = new HashSet<>();

    AnimalRepresentative animalRepresentative;
    AbstractWorldMap map;
    Set<IStateObserver> observers;

    Button waitButton;

    Button endButton;

    public SimulationEngine(Settings config,AbstractWorldMap map,Stage stage){
        this.myStage = stage;
        this.config = config;
        this.map = map;
        this.observers = new HashSet<>();
        this.animalRepresentative = new AnimalRepresentative();
    }



    public void run(){
        Set<Animal> toAdd;
        Set<Animal> toRemove;
        initAnimals();
        Iterator<Animal> animalIterator  = animals.iterator();
        animalRepresentative.setAnimal(animalIterator.next());
        initPlants();
        System.out.println(animals.size());
        while(true){
            moveAnimals();
            toRemove =  map.checkDying();
            removeAnimals(toRemove);
            toAdd = map.checkReproduction();
            addAnimals(toAdd);
            map.checkEating();
            spawnGrass();
            if(animals.isEmpty()){
                System.out.println("end");
                break;
                }
            toRemove =  map.checkDying();
            removeAnimals(toRemove);
            notifyObservers();
            moveDelay();

        }

    }

    private void moveDelay(){
        try {
            Thread.sleep(100);
        }
        catch (InterruptedException exception){
            System.out.println("DELAY ERROR");
            System.exit(2);
        }
    }

    private void notifyObservers(){
        for(IStateObserver observer:observers ){
            observer.update(myStage,map,animalRepresentative,getButtons());
        }
    }

    public void addObserver(IStateObserver observer){
        observers.add(observer);
    }

    public void removeObserver(IStateObserver observer){
        observers.remove(observer);//tu mozna rzucic wyjatek jak go nie ma
    }

    private void initPlants(){
        for(int i = 0; i < config.getStartPlant(); i++){map.addGrass();}
    }

    private void initAnimals(){
        for (int i = 0; i < config.getStartAnimal(); i++){
            animals.add(new Animal(config,map));
        }
    }

    private void moveAnimals(){
        for(Animal animal:animals){
            animal.move();
        }
    }

    private void removeAnimals(Set<Animal> toRemove){
        for(Animal animal:toRemove){
            animals.remove(animal);
        }
        //System.out.println(animals.size());
    }

    private void addAnimals(Set<Animal> toAdd){
        for(Animal animal:toAdd){
            animals.add(animal);
        }
    }

    private void spawnGrass(){
        for (int i = 0; i < config.getDailyPlant(); i++){
            map.addGrass();
        }
    }

    public void setWaitButton(Button button){
        waitButton = button;
    }

    public Button getWaitButton(){
        return waitButton;
    }

    public void setEndButton(Button button){
        endButton = button;
    }

    public Button getEndButton(){
        return endButton;
    }

    public HBox getButtons(){

        return new HBox(endButton,waitButton);
    }


}






