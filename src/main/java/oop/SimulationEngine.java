package oop;

import javafx.application.Platform;
import javafx.geometry.Pos;
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

    private boolean stopped;

    private boolean working;

    public Button end;

    public Button stop;

    public HBox buttons;

    public SimulationEngine(Settings config,AbstractWorldMap map,Stage stage){
        this.myStage = stage;
        this.config = config;
        this.map = map;
        this.observers = new HashSet<>();
        this.animalRepresentative = new AnimalRepresentative();
        end = new Button("END");
        end.setOnAction(e -> {
            System.out.println("PUSH");
            end();
        });
        stop = new Button("STOP");
        stop.setOnAction(e -> {
            System.out.println("PUSH");
            stop();
        });
        buttons = new HBox(stop,end);
        buttons.setSpacing(100);
        buttons.setAlignment(Pos.CENTER);
    }



    public void run(){
        working = true;
        stopped = false;
        Set<Animal> toAdd;
        Set<Animal> toRemove;
        initAnimals();
        initPlants();
        System.out.println(animals.size());
        while(true){
            while (stopped){
                if(!animalRepresentative.isTracking() || animalRepresentative.hasDeadAnimal()){
                    Iterator animalIterator  = animals.iterator();
                    Animal strongest = (Animal) animalIterator.next();
                    for(Animal animal:animals){
                        if (animal.getEnergy() > strongest.getEnergy()){strongest = animal;}
                    }
                    animalRepresentative.setAnimal(strongest);
                }

                if(!working){break;}
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            moveAnimals();
            toRemove =  map.checkDying();
            if(!working){break;}
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
            moveDelay();
            if(!working){break;}
            notifyObservers();
            if(!working){break;}

        }

        System.out.println("SYMULACJA ZAKONCZONA POPRAWNIE");

    }

    private void moveDelay(){
        try {
            Thread.sleep(180);
        }
        catch (InterruptedException exception){
            System.out.println("DELAY ERROR");
            System.exit(2);
        }
    }

    private void notifyObservers(){
        for(IStateObserver observer:observers ){
            observer.update(myStage,map,animalRepresentative,this);
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

    public void end(){
        System.out.println("END");
            this.working = false;
            myStage.close();
    }

    public void stop(){
        this.stopped = !this.stopped;
    }


}






