package oop;

public class Settings {

    private MapType mapType = MapType.EARTH;
    private int mapWidth = 5;
    private int mapHeight = 5;

    private PlantType plantType = PlantType.JUNGLE;
    private int startPlant = 3;
    private int dailyPlant = 3;
    private int energyPlant = 2;

    private int startAnimal = 4;
    private int startEnergyAnimal = 100;
    private int breedEnergy = 4;
    private double breedLoseEnergy = 0.6;
    private int genomLength = 7;
    private MoveType moveType = MoveType.CORRECT;

    private MutationType mutationType = MutationType.LITTLE_CORRECTION;
    private int minMutation = 1;
    private int maxMutation = 3;

    public MapType getMapType() {
        return mapType;
    }

    public void setMapType(MapType mapType) {
        this.mapType = mapType;
    }

    public int getMapWidth() {
        return mapWidth;
    }

    public void setMapWidth(int mapWidth) {
        this.mapWidth = mapWidth;
    }

    public int getMapHeight() {
        return mapHeight;
    }

    public void setMapHeight(int mapHeight) {
        this.mapHeight = mapHeight;
    }

    public PlantType getPlantType() {
        return plantType;
    }

    public void setPlantType(PlantType plantType) {
        this.plantType = plantType;
    }

    public int getStartPlant() {
        return startPlant;
    }

    public void setStartPlant(int startPlant) {
        this.startPlant = startPlant;
    }

    public int getDailyPlant() {
        return dailyPlant;
    }

    public void setDailyPlant(int dailyPlant) {
        this.dailyPlant = dailyPlant;
    }

    public int getEnergyPlant() {
        return energyPlant;
    }

    public void setEnergyPlant(int energyPlant) {
        this.energyPlant = energyPlant;
    }

    public int getStartAnimal() {
        return startAnimal;
    }

    public void setStartAnimal(int startAnimal) {
        this.startAnimal = startAnimal;
    }

    public int getStartEnergyAnimal() {
        return startEnergyAnimal;
    }

    public void setStartEnergyAnimal(int startEnergyAnimal) {
        this.startEnergyAnimal = startEnergyAnimal;
    }

    public int getBreedEnergy() {
        return breedEnergy;
    }

    public void setBreedEnergy(int breedEnergy) {
        this.breedEnergy = breedEnergy;
    }

    public double getBreedLoseEnergy() {
        return breedLoseEnergy;
    }

    public void setBreedLoseEnergy(double breedLoseEnergy) {
        this.breedLoseEnergy = breedLoseEnergy;
    }

    public int getGenomLength() {
        return genomLength;
    }

    public void setGenomLength(int genomLength) {
        this.genomLength = genomLength;
    }

    public MoveType getMoveType() {
        return moveType;
    }

    public void setMoveType(MoveType moveType) {
        this.moveType = moveType;
    }

    public MutationType getMutationType() {
        return mutationType;
    }

    public void setMutationType(MutationType mutationType) {
        this.mutationType = mutationType;
    }

    public int getMinMutation() {
        return minMutation;
    }

    public void setMinMutation(int minMutation) {
        this.minMutation = minMutation;
    }

    public int getMaxMutation() {
        return maxMutation;
    }

    public void setMaxMutation(int maxMutation) {
        this.maxMutation = maxMutation;
    }
}
