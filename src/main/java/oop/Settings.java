package oop;

public class Settings {

    private int mapHeight = 10;
    private int mapWidth = 20;
    private MapType mapType = MapType.EARTH;
    private int startPlant = 5;
    private int energyPlant = 2;
    private int dailyPlant = 5;
    private PlantType plantType = PlantType.JUNGLE;
    private int startAnimal = 5;
    private int startEnergyAnimal = 3;
    private int breedEnergy = 2;
    private double breedLoseEnergy = 0.4;
    private int maxMutation = 2;
    private int minMutation = 0;
    private MutationType mutationType = MutationType.LITTLE_CORRECTION;
    private int genomLength = 10;
    private MoveType moveType = MoveType.CORRECT;

    public int getMapHeight(){
        return mapHeight;
    }

    public int getMapWidth() {
        return mapWidth;
    }

    public MapType getMapType() {
        return mapType;
    }

    public int getStartPlant() {
        return startPlant;
    }

    public int getEnergyPlant() {
        return energyPlant;
    }

    public int getDailyPlant() {
        return dailyPlant;
    }

    public PlantType getPlantType() {
        return plantType;
    }

    public int getStartAnimal() {
        return startAnimal;
    }

    public int getStartEnergyAnimal() {
        return startEnergyAnimal;
    }

    public int getBreedEnergy() {
        return breedEnergy;
    }

    public double getBreedLoseEnergy() {
        return breedLoseEnergy;
    }

    public int getMaxMutation() {
        return maxMutation;
    }

    public int getMinMutation() {
        return minMutation;
    }

    public MutationType getMutationType() {
        return mutationType;
    }

    public int getGenomLength() {
        return genomLength;
    }

    public MoveType getMoveType() {
        return moveType;
    }

}
