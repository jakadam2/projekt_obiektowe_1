package oop;
import java.util.*;

abstract class AbstractWorldMap implements IPositionObserver {
    private final int x;//włącznie 500 0 - 499
    private final int y;//włącznie
    private final int plantPerDay;
    private Map<Vector2d,MapElementBox> boxes = new HashMap<>();

    private SortedMap<Vector2d,Integer> toxic;

    private SortedMap<Vector2d,Integer> neutral;
    private int plantEnergy;
    private MyRandom generator = new MyRandom();
    private final PlantType plantType;

    public AbstractWorldMap(Settings config){
        this.x = config.getMapWidth();
        this.y = config.getMapHeight();
        this.plantPerDay = config.getDailyPlant();
        this.plantType = config.getPlantType();
        this.plantEnergy = config.getEnergyPlant();
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
        if(element.getClass() == Animal.class){
            ((Animal) element).addObserver(this);
        }
    }

    abstract Vector2d checkFinalPosition(Animal animal,Vector2d cand);

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

    void checkEating(){
        for(MapElementBox box: boxes.values()){
            if(box.includePlant()){
                Set<Animal> animals = box.getAnimals();
                if (animals.size() == 0){break;}
                Iterator animalIterator  = animals.iterator();
                Animal strongest = (Animal) animalIterator.next();
                for(Animal animal:animals){
                    if (animal.getEnergy() > strongest.getEnergy()){strongest = animal;}
                }
                strongest.eatPlant(box.getPlant());
                this.remove(box.getPlant());
            }
        }
    }
    public void remove(IMapElement element){// mozna tu rzucic wyjatek gdyby jakims cudem to zwierze bylo zdjete
        this.boxAt(element.getPosition()).remove(element);
        if(boxAt(element.getPosition()).isEmpty()){
            boxes.remove(element.getPosition());
        }
    }

    public MapElementBox boxAt(Vector2d position){
        return boxes.get(position);
    }

    Set<Animal> checkReproduction(){
        Set<Animal> toAdd = new HashSet<>();
        for(MapElementBox box: boxes.values()){
            if(box.includeAnimal()){
                Set<Animal> animals = box.getAnimals();
                if(animals.size() > 1){
                    Iterator animalIterator  = animals.iterator();
                    Animal strongest = (Animal) animalIterator.next();
                    for(Animal animal:animals){
                        if (animal.getEnergy() > strongest.getEnergy()){strongest = animal;}
                    }
                    Animal stronger = (Animal) animalIterator.next();
                    while(strongest.equals(stronger)){
                    stronger = (Animal) animalIterator.next();}
                    for(Animal animal:animals){
                        if (animal.getEnergy() > stronger.getEnergy() && !animal.equals(stronger)){stronger = animal;}
                    }
                    toAdd.add(strongest.breed(stronger));
                }
            }
        }
        return toAdd;
    }

    public void addGrass(){
        if (plantType == PlantType.JUNGLE){
            int grassField = (int) (0.2 * this.y);
            int middle = this.y/2;
            int start = middle - (grassField/2);
            int stop = middle + (grassField/2);
            int random = generator.nextInt(10);
            Vector2d position;
            if(random < 2){
                int secondRandom = generator.nextInt(2);
                if(secondRandom == 0){
                    position = new Vector2d(generator.nextInt(x), generator.nextInt(start) );
                }
                else{
                    position = new Vector2d(generator.nextInt(x),generator.nextInt(start) + stop );
                }

            }
            else {
                position = new Vector2d(generator.nextInt(x), start +generator.nextInt(grassField));
                place(new Plant(position,plantEnergy));
            }
            place(new Plant(position,plantEnergy));
        }

        else {
            //tu trzeba cos napisac
        }
    }

  Set<Animal> checkDying(){
        Set<Animal> toRemove = new HashSet<>();
        for(MapElementBox box: boxes.values()){
            if(box.includeAnimal()){
                Set<Animal> animals = box.getAnimals();
                for(Animal animal: animals){
                    if(animal.isDead()){
                        this.remove(animal);
                        toRemove.add(animal);
                    }
                }

            }
        }
        return toRemove;
    }
}
