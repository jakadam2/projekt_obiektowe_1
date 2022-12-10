package oop;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Animal implements IMapElement {

    private int livedDays;
    private int energy;
    private Vector2d position;
    private MapDirection orientation;
    private int[] genom;
    private int activatedGen;

    private final int necessaryEnergy;

    private final int dayWastedEnergy;

    public Set<IPositionObserver> observers = new HashSet<>();

    AbstractWorldMap map;

    private MyRandom generator = new MyRandom();

    public Animal(int startEnergy,int genomLength,int maxX,int maxY,AbstractWorldMap map,int necessaryEnergy,int dayWastedEnergy){//kiedy zwierze jest ddodawane na początku
        this.livedDays = 0;
        this.energy = startEnergy;
        this.orientation = generator.nextDirection();
        this.genom = generator.nextGenom(genomLength);
        this.position = generator.nextPosition(maxX,maxY);
        this.activatedGen = generator.nextInt(genomLength);
        this.map = map;
        this.necessaryEnergy = necessaryEnergy;
        this.dayWastedEnergy = dayWastedEnergy;
    }

    public Animal(int startEnergy,int[] genom, Vector2d position,AbstractWorldMap map,int necessaryEnergy,int dayWastedEnergy){//kiedy zwierzę się rodzi
        this.livedDays = 0;
        this.energy = startEnergy;
        this.position = position;
        this.orientation  = generator.nextDirection();
        this.activatedGen = generator.nextInt(genom.length);
        this.map = map;
        this.necessaryEnergy = necessaryEnergy;
        this.dayWastedEnergy = dayWastedEnergy;
    }

    public Vector2d getPosition(){
        return this.position;
    }

    public void move(){
        this.orientation.rotate(genom[activatedGen]);
        Vector2d cand = this.position.add(this.orientation.toUnitVector());
        if(map.canMoveTo(cand)){
            Vector2d oldPosition = this.position;//tu może być zonk
            this.position = cand;
            this.notifyObservers(oldPosition,this.position);
        }
        this.activatedGen = (activatedGen + 1)%genom.length;
        this.livedDays++;
        this.energy -= dayWastedEnergy;
    }

    public void addObserver(IPositionObserver observer){
        this.observers.add(observer);
    }

    public void removeObserver(IPositionObserver observer){
        this.observers.remove(observer);
    }

    public void notifyObservers(Vector2d oldPosition,Vector2d newPosition){
        for(IPositionObserver observer:observers){
            observer.update(oldPosition,newPosition,this);
        }
    }

    void eatPlant(Plant plant){
        this.energy += plant.energy;
    }

    boolean isDead(){
        return this.energy < this.necessaryEnergy;
    }

    int getEnergy(){
        int ans = this.energy;
        return ans;
    }

}
