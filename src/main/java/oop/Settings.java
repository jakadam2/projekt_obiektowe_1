package oop;

public class Settings {

    private int mapHeight;
    private int mapWidth;
    private MapType mapType;
    private int startPlant;
    private int energyPlant;
    private int dailyPlant;
    private PlantType plantType;
    private int startAnimal;
    private int startEnergyAnimal;
    private int breedEnergy;
    private float breedLoseEnergy;
    private int maxMutation;
    private int minMutation;
    private MutationType mutationType;
    private int genomLength;
    private MoveType moveType;

    private AbstractWorldMap map;

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

    public float getBreedLoseEnergy() {
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

    public AbstractWorldMap getMap() {
        return map;
    }
}
