package oop;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import oop.Gui.AnimalRepresentative;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class SimulationEngine implements Runnable{

    private final Settings config;
    private Stage myStage;
    private Set<Animal> animals = new HashSet<>();
    private AnimalRepresentative animalRepresentative;
    private final AbstractWorldMap map;
    private Set<IStateObserver> observers;

    private boolean stopped;

    private boolean working;

    private Button end;

    private Button stop;

    public HBox buttons;


    public SimulationEngine(Settings config,AbstractWorldMap map,Stage stage){
        this.myStage = stage;
        this.config = config;
        this.map = map;
        this.observers = new HashSet<>();
        this.animalRepresentative = new AnimalRepresentative();
        end = new Button("END");
        end.setOnAction(e -> end());
        stop = new Button("STOP");
        stop.setOnAction(e -> stop());
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

        int day = 1;

        try (FileWriter writer = new FileWriter("data.csv")) {
            writer.write("day;amountOfAnimals;amountOfPlants;amountOfFreePlaces;mostPopularGenome;avgAnimalEnergy;avgLifeTime");
            writer.write(System.lineSeparator());

        while(true){
            //petla obslugujaca zatrzymanie sumulacj i wybor zwierzecia do sledzenia
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
            removeAnimals(toRemove);
            toAdd = map.checkReproduction();
            addAnimals(toAdd);
            map.checkEating();
            spawnGrass();
            if(animals.isEmpty()){
                break;
                }
            toRemove =  map.checkDying();
            removeAnimals(toRemove);

            if(!working){break;}
            moveDelay();
            if(!working){break;}
            notifyObservers();
            if(!working){break;}

            String data = day + ";" + map.getAmountAnimals() + ";" + map.getAmountPlants() + ";" + map.getAmountFreePlaces() + ";" + Arrays.toString(map.getMostPopularGenomes()) + ";" +
                    map.getAvgEnergy() + ";" + map.getAvgLifeDeadAnimals();

            writer.write(data);
            writer.write(System.lineSeparator());
            day++;
        }}
        catch (IOException ex) {
            System.out.println(ex.getMessage());
        }



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
        observers.remove(observer);
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
            this.working = false;
            myStage.close();
    }

    public void stop(){
        this.stopped = !this.stopped;
    }


}






