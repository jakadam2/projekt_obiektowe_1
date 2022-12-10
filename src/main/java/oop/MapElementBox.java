package oop;

import java.util.HashSet;
import java.util.Set;

public class MapElementBox {

    private Plant plant;
    Set<Animal> animals = new HashSet<>();
    private final Vector2d position;
    private boolean includePlant = false;

    public MapElementBox(Vector2d position){
        this.position = position;
    }

    public void add(IMapElement element){
        if(element.getClass() == Animal.class){
            animals.add((Animal) element);
        }
        else{includePlant = true;
            plant = (Plant) element;}
    }

    public void remove(IMapElement element){
        if(element.getClass() == Plant.class){
            this.includePlant = false;
            plant = null;
        }
        else {
            animals.remove((Animal) element);
        }
    }

    public Animal[] getAnimals(){
        return (Animal[]) animals.toArray();
    }

    public Vector2d getPosition(){
        return this.position;
    }

    public boolean isEmpty(){
        return animals.isEmpty() && !includePlant;
    }

    public Plant getPlant(){return this.plant;}

    public boolean includePlant(){
        boolean ans = this.includePlant;
        return ans;
    }

    public boolean includeAnimal(){
        return !animals.isEmpty();
    }
}
