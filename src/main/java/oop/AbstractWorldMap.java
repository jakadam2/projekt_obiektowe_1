package oop;

import java.util.*;

public abstract class AbstractWorldMap implements IPositionObserver {
    public final int x;//włącznie 500 0 - 499
    public final int y;//włącznie
    private final int plantPerDay;
    private Map<Vector2d,MapElementBox> boxes = new HashMap<>();
    private List<Animal> deadAnimals = new ArrayList<>();
    private HashMap<Vector2d, Integer> deadPlaces = new HashMap<>();
    private HashMap<int[], Integer> genomes = new HashMap<>();
    private final int plantEnergy;
    private MyRandom generator = new MyRandom();
    private final PlantType plantType;
    private final int breedEnergy;

    public AbstractWorldMap(Settings config){
        this.x = config.getMapWidth();
        this.y = config.getMapHeight();
        this.plantPerDay = config.getDailyPlant();
        this.plantType = config.getPlantType();
        this.plantEnergy = config.getEnergyPlant();
        this.breedEnergy = config.getBreedEnergy();
    }

    public void place(IMapElement element){
        if(!(element.getPosition().x <= this.x + 1 && element.getPosition().y <= this.y + 1)){throw new IllegalArgumentException("WRONG POSITION");}
        if(boxes.get(element.getPosition()) == null){
            MapElementBox newBox = new MapElementBox(element.getPosition());
            newBox.add(element);
            boxes.put(element.getPosition(),newBox);
        }
        else{
            boxes.get(element.getPosition()).add(element);
        }
        if(element.getClass() == Animal.class){
            Animal animal = (Animal) element;
            animal.addObserver(this);

            if(genomes.containsKey(animal.getGenom())) {
                genomes.put(animal.getGenom(), genomes.get(animal.getGenom()) + 1);
            }
            else {
                genomes.put(animal.getGenom(), 1);
            }
        }
    }

    abstract Vector2d checkFinalPosition(Animal animal,Vector2d cand);

    @Override
    public void update(Vector2d oldPosition, Vector2d newPosition, Animal animal) {
        this.boxes.get(oldPosition).remove(animal);
        if(this.boxes.get(oldPosition).isEmpty()){this.boxes.remove(oldPosition);}
        if(boxes.get(newPosition) == null){
            MapElementBox newBox = new MapElementBox(newPosition);
            newBox.add(animal);
            boxes.put(newPosition,newBox);
        }
        else{
            boxes.get(newPosition).add(animal);
        }
    }

    void checkEating(){
        Set<Plant> toRemove = new HashSet<>();
        for(MapElementBox box: boxes.values()){
            if(box.includePlant()){
                Set<Animal> animals = box.getAnimals();
                if (animals.size() == 0){break;}
                Iterator<Animal> animalIterator  = animals.iterator();
                Animal strongest = animalIterator.next();
                for(Animal animal:animals){
                    if (animal.getEnergy() > strongest.getEnergy()){strongest = animal;}
                }
                strongest.eatPlant(box.getPlant());
                toRemove.add(box.getPlant());
            }
        }
        for(Plant plant: toRemove){
            remove(plant);
        }
    }

    public void remove(IMapElement element){
        moveAnimalToKilled(element);
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
                    Iterator<Animal> animalIterator  = animals.iterator();
                    if(animalIterator.hasNext()){
                        Animal strongest =  animalIterator.next();
                        Animal stronger = strongest;
                        for(Animal animal:animals){
                            if (animal.getEnergy() > strongest.getEnergy()){strongest = animal;}
                        }
                        if(strongest.equals(stronger)){stronger = animalIterator.next();}
                        for(Animal animal:animals){
                            if (animal.getEnergy() > stronger.getEnergy() && !animal.equals(stronger)){stronger = animal;}
                        }
                        if(strongest.getEnergy() > breedEnergy && stronger.getEnergy() > breedEnergy){
                            toAdd.add(strongest.breed(stronger));}}
                }
            }
        }
        for(Animal animal:toAdd){
            place(animal);
        }
        return toAdd;
    }

    public void addGrass(){
        if (plantType == PlantType.JUNGLE){
            int grassField = (int) (0.2 * this.y);
            //wyznaczam srodek mapy
            int middle = this.y/2;
            //wyznaczam poczatek i koniec pasa zieleni
            int start = middle - (grassField/2);
            int stop = middle + (grassField/2);
            int random = generator.nextInt(10);
            Vector2d position;
            //roslina rosnie poza pasem
            if(random < 2){
                //losuje czy nad pasem czy pod
                int secondRandom = generator.nextInt(2);
                if(secondRandom == 0){
                    position = new Vector2d(generator.nextInt(x), generator.nextInt(start) );
                }
                else{
                    position = new Vector2d(generator.nextInt(x),generator.nextInt(start) + stop );
                }
            }
            //roslina rosnie w pasie
            else {
                position = new Vector2d(generator.nextInt(x), start + generator.nextInt(grassField));
                place(new Plant(position,plantEnergy));
            }
            place(new Plant(position,plantEnergy));
        }

        else {
            // Rozlozenie mapy na dwie tablicy w sposob taki aby indeksy sie zgadzaly
            Vector2d[] positionsAll = new Vector2d[(int) (deadPlaces.size())];
            Integer[] values = new Integer[(int) (deadPlaces.size())];
            int index = 0;
            for(Map.Entry<Vector2d, Integer> mapEntry: deadPlaces.entrySet()) {
                positionsAll[index] = mapEntry.getKey();
                values[index] = mapEntry.getValue();
                index++;
            }

            // Sortowanie tablic z pozycjami na podstawie kluczy(czestotliwosci ich wystepowania w kierunku rosnacym)
            final List<Vector2d> positionsAllListCopy = Arrays.asList(positionsAll);
            ArrayList<Vector2d> sortedPositions = new ArrayList<>(positionsAllListCopy);
            sortedPositions.sort(Comparator.comparing(s -> values[positionsAllListCopy.indexOf(s)]));
            Arrays.sort(values);

            // Ile pól bierzemy pod uwage w przypadku corpses oraz te pola na ktorych najrzadziej umieraja wkladam do tablicy:)
            int numbers = (int) (0.2 * deadPlaces.size());
            Vector2d[] positions = Arrays.copyOfRange(positionsAll, 0, numbers);

            int random = generator.nextInt(10);

            Vector2d pos;
            if(random < 2 || numbers <= 0){
                do {
                    int posX = generator.nextInt(x);
                    int posY = generator.nextInt(y);
                    pos = new Vector2d(posX, posY);
                } while(Arrays.asList(positions).contains(pos));
            }
            else {
                // losowanie w przedziale <min;max>:  rand(max - min + 1) + min

                int max = positions.length - 1;
                int min = 0;

                int generate = generator.nextInt(max - min + 1) + min;
                pos = new Vector2d(positions[generate].x, positions[generate].y);
            }
            place(new Plant(pos, plantEnergy));
        }
    }

    Set<Animal> checkDying(){
        Set<Animal> toRemove = new HashSet<>();
        for(MapElementBox box: boxes.values()){
            if(box.includeAnimal()){
                Set<Animal> animals = box.getAnimals();
                for(Animal animal: animals){
                    if(animal.isDead()){
                        toRemove.add(animal);
                    }
                }

            }
        }
        for (Animal animal: toRemove){
            remove(animal);
        }
        //System.out.println("enddiying");
        return toRemove;
    }

    public boolean isOccupied(Vector2d position){
        if(boxes.get(position) == null){return false;}
        return true;
    }

    private void moveAnimalToKilled(Object object) {
        if(object instanceof Animal) {
            Animal animal = (Animal) object;
            this.deadAnimals.add(animal);

            if(deadPlaces.containsKey(animal.getPosition())) {
                deadPlaces.put(animal.getPosition(), deadPlaces.get(animal.getPosition()) + 1);
            }
            else {
                deadPlaces.put(animal.getPosition(), 1);
            }
        }
    }

    public int getAmountAnimals() {
        int result = 0;
        for (MapElementBox box : boxes.values()) {
            if (box.includeAnimal()) {
                result += box.getAnimals().size();
            }
        }
        return result;
    }

    public int getAmountPlants() {
        int result = 0;
        for (MapElementBox box : boxes.values()) {
            if (box.includePlant()) {
                result += 1;
            }
        }
        return result;
    }

    public int getAmountFreePlaces() {
        int result = this.x * this.y;
        result = result - boxes.size();
        return result;
    }

    public int getAvgEnergy() {
        int result = 0;
        for (MapElementBox box : boxes.values()) {
            if (box.includeAnimal()) {
                Set<Animal> animals = box.getAnimals();
                for(Animal animal: animals) {
                    result += animal.energy;
                }
            }
        }
        return result / getAmountAnimals();
    }

    public int[] getMostPopularGenomes() {
        return Collections.max(genomes.entrySet(), Comparator.comparingInt(Map.Entry::getValue)).getKey();
    }

    public int getAvgLifeDeadAnimals() {
        if(deadAnimals.isEmpty()) return 0;
        int result = 0;
        for (Animal animal : deadAnimals) {
            result += animal.getLivedDays();
        }
        result = result / deadAnimals.size();
        return result;
    }
}
