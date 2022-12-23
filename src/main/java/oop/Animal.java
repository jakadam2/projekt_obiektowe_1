package oop;

public class Animal implements IMapElement{
    private MyRandom generator = new MyRandom();
    private Vector2d position;
    private int[] genom;
    private int energy;
    private int livedDays;
    private int activeGen;
    private int eatenPlant;
    private int child;
    //private int deathDate;
    private MoveType moveType;
    private MutationType mutationType;
    private AbstractWorldMap map;

    private float loseBreedEnergy;
    private int breedEnergy;
    private MapDirection orientation;

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
        map.place(this);

    }

    public void move(){
        Vector2d nextPosition = this.position.add(orientation.toUnitVector());
        Vector2d confPosition = map.checkFinalPosition(nextPosition);
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
        int newMotherEnergy = (int) (this.energy * this.loseBreedEnergy);
        int newFatherEnergy = (int) (partner.energy * this.loseBreedEnergy);
        int childEnergy = (partner.energy - newFatherEnergy) + (this.energy - newMotherEnergy);
        partner.energy = newFatherEnergy;
        this.energy = newMotherEnergy;
        int[] childGenom = new int[genom.length];
        int rand = generator.nextInt(2);
        if(rand == 0){//silniejszy parter ma od lewej
            if(this.energy > partner.energy){
                int genPart = (this.energy/(this.energy + partner.energy)) * genom.length;



            }
        }


    }
}
