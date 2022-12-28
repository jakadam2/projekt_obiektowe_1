package oop;
import java.util.HashMap;
import java.util.Map;

abstract class AbstractWorldMap implements IPositionObserver {
    private final int x;//włącznie 500 0 - 499
    private final int y;//włącznie
    private final int plantPerDay;
    private Map<Vector2d,MapElementBox> boxes = new HashMap<>();

    public AbstractWorldMap(Settings config){
        this.x = config.getMapWidth();
        this.y = config.getMapHeight();
        this.plantPerDay = config.getDailyPlant();
    }

    public void place(IMapElement element){
        if(!(element.getPosition().x <= this.x && element.getPosition().y <= this.y)){throw new IllegalArgumentException("WRONG POSITION");}
        if(boxes.get(element.getPosition()) == null){
            MapElementBox newBox = new MapElementBox(element.getPosition());//wywalic jak nie korzysta z pozycji
            newBox.add(element);
            boxes.put(element.getPosition(),newBox);
        }
        else{
            boxes.get(element.getPosition()).add(element);
        }
    }

    abstract Vector2d checkFinalPosition(Vector2d curr,Vector2d cand);

    @Override
    public void update(Vector2d oldPosition, Vector2d newPosition, Animal animal) {
        this.boxes.get(oldPosition).remove(animal);
        if(this.boxes.get(oldPosition).isEmpty()){this.boxes.remove(oldPosition);}
        if(boxes.get(newPosition) == null){
            MapElementBox newBox = new MapElementBox(newPosition);//wywalic jak nie korzysta z pozycji
            newBox.add(animal);
            boxes.put(newPosition,newBox);
        }
        else{
            boxes.get(newPosition).add(animal);
        }
    }

    /*public void remove(IMapElement element){// mozna tu rzucic wyjatek gdyby jakims cudem to zwierze bylo zdjete
        this.boxAt(element.getPosition()).remove(element);
        if(boxAt(element.getPosition()).isEmpty()){
            boxes.remove(element.getPosition());
        }
    }




   /* void checkEating(){
        for(MapElementBox box: boxes.values()){
            if(box.includePlant()){
                Animal[] animals = box.getAnimals();
                if (animals.length == 0){break;}
                Animal strongest = animals[0];
                for(Animal animal:animals){
                    if (animal.getEnergy() > strongest.getEnergy()){strongest = animal;}
                }
                strongest.eatPlant(box.getPlant());
                this.remove(box.getPlant());
            }
        }
    }

    void checkReproduction(){
        for(MapElementBox box: boxes.values()){
            if(box.includeAnimal()){
                Animal[] animals = box.getAnimals();
                if(animals.length > 1){
                    //tu trzeba posortowac i wziac pierwsze 2
                    Animal strongest = animals[0];
                    Animal stronger = animals[1];
                    AnimalReproducer reproducer = new AnimalReproducer(strongest,stronger,mutationType,maxMutationQuantity,minMutationQuanity);
                    Animal child = reproducer.createAnimal();
                    this.place(child);
                }
            }
        }
    }
*/
}
