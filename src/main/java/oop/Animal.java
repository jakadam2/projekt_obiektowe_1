package oop;

import java.util.*;

public class Animal implements IMapElement{
    private MyRandom generator = new MyRandom();
    private Vector2d position;
    private int[] genom;
    private int energy;
    private int livedDays;
    private int activeGen;

    private Set<IPositionObserver> observers = new HashSet<>();
    private int eatenPlant;
    private int child;
    //private int deathDate;
    private MoveType moveType;
    private MutationType mutationType;
    private AbstractWorldMap map;

    private float loseBreedEnergy;
    private int breedEnergy;
    private MapDirection orientation;

    private int minMutation;
    private int maxMutation;

    public Animal(Settings config){
        position = generator.nextPosition(config.getMapWidth(),config.getMapHeight());
        genom = generator.nextGenom(config.getGenomLength());
        energy = config.getStartEnergyAnimal();
        livedDays = 0;
        eatenPlant = 0;
        child = 0;
        activeGen = generator.nextInt(config.getGenomLength());
        moveType = config.getMoveType();
        mutationType = config.getMutationType();
        map = config.getMap();
        orientation = generator.nextDirection();
        breedEnergy = config.getBreedEnergy();
        loseBreedEnergy = config.getBreedLoseEnergy();
        maxMutation = config.getMaxMutation();
        minMutation = config.getMinMutation();
        map.place(this);

    }

    public Animal(Animal mother, int[] genom, int energy) {
        this.position = mother.position;
        this.genom = genom;
        this.energy = energy;
        livedDays = 0;
        eatenPlant = 0;
        child = 0;
        activeGen = generator.nextInt(mother.genom.length);
        moveType = mother.moveType;
        mutationType = mother.mutationType;
        maxMutation = mother.maxMutation;
        minMutation = mother.minMutation;
        map = mother.map;
        orientation = generator.nextDirection();
        breedEnergy = mother.breedEnergy;
        loseBreedEnergy = mother.loseBreedEnergy;
        map.place(this);
    }

    public void move(){
        Vector2d nextPosition = this.position.add(orientation.toUnitVector());
        Vector2d confPosition = map.checkFinalPosition(this.position,nextPosition);
        if(confPosition.equals(this.position)){
            this.orientation = orientation.rotate(4);
        }
        this.position = confPosition;
        livedDays ++;
        energy --;
        this.activeGen = (this.activeGen + 1)%genom.length;
        if(moveType == MoveType.RANDOM){
            int rand = generator.nextInt(10);
            if(rand < 2){
                this.activeGen = generator.nextInt(genom.length);
            }
        }
        this.orientation = orientation.rotate(genom[activeGen]);
    }

    public Vector2d getPosition() {
        return position;
    }

    public Animal breed(Animal partner){
        int motherLoseEnergy = (int) (this.energy * this.loseBreedEnergy);
        int fatherLoseEnergy = (int) (partner.energy * this.loseBreedEnergy);
        int energy = motherLoseEnergy + fatherLoseEnergy;
        this.energy = this.energy - motherLoseEnergy;
        partner.energy = partner.energy - fatherLoseEnergy;
        int separator = Math.round( (float) this.energy / (float) (this.energy + partner.energy) * genom.length);
        int[] genome = new int[genom.length];
        int rand = generator.nextInt(2);
        Animal stronger;
        Animal weaker;
        if(this.energy > partner.energy) {
            stronger = this;
            weaker = partner;
        }
        else {
            stronger = partner;
            weaker = this;
        }
        if(rand == 0) {
            for(int i = 0; i < separator; i++) genome[i] = stronger.genom[i];
            for(int i = separator; i < genom.length; i++) genome[i] = weaker.genom[i];
        }
        else {
            for(int i = 0; i < separator; i++) genome[i] = weaker.genom[i];
            for(int i = separator; i < genom.length; i++) genome[i] = stronger.genom[i];
        }
        // !!mutacje genów

        int howManyMutations = generator.nextInt(maxMutation - minMutation) + minMutation;

        // przygotowuje tablice indeksow genomu
        Integer[] prepareToMutate = new Integer[genom.length];
        for(int i = 0; i < genom.length; i++) prepareToMutate[i] = i;

//        // mieszam tablice indeksow aby nastepnie wziac sobie pierwszą x ilość jako te wylosowane geny do mutacji
        List<Integer> shuffledList = Arrays.asList(prepareToMutate);
        Collections.shuffle(shuffledList);
        shuffledList.toArray(prepareToMutate);


        int[] changeTheseGens = new int[howManyMutations];
        for(int i = 0; i < howManyMutations; i++) changeTheseGens[i] = prepareToMutate[i];

        if(mutationType == MutationType.LITTLE_CORRECTION) {
            for(int i = 0; i < howManyMutations; i++) {
                rand = generator.nextInt(2);

                // do góry o 1
                if(rand == 0) {
                    if( (genome[changeTheseGens[i]] + 1) > 7 ) genome[changeTheseGens[i]] = 0;
                    else genome[changeTheseGens[i]] = genome[changeTheseGens[i]] + 1;
                }
                // do dołu o 1
                else {
                    if( (genome[changeTheseGens[i]] - 1) < 0 ) genome[changeTheseGens[i]] = 7;
                    else genome[changeTheseGens[i]] = genome[changeTheseGens[i]] - 1;
                }
            }
        }
        else for(int i = 0; i < howManyMutations; i++) genome[changeTheseGens[i]] = generator.nextInt(8);

        // co z tym configiem tu
        return new Animal(this, genome, energy);
    }

    private void notifyObservers(Vector2d oldPosition,Vector2d newPosition){
        for (IPositionObserver observer:observers){observer.update(oldPosition,newPosition,this);}
    }

    public void addObserver(IPositionObserver observer){
        observers.add(observer);
    }

    public void removeObserver(IPositionObserver observer){
        observers.remove(observer);
    }

    public int getEnergy(){return energy;}

    public void eatPlant(Plant plant){
        energy += plant.energy;
    }
}
