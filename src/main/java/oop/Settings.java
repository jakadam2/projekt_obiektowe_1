package oop;

public class Settings {

    private MapType mapType; // jaki jest sens pól prywatnych, jeśli do wszystkich mamy gettery i settery?
    private int mapWidth;
    private int mapHeight;

    private PlantType plantType;
    private int startPlant;
    private int dailyPlant;
    private int energyPlant;

    private int startAnimal;
    private int startEnergyAnimal;
    private int breedEnergy;
    private double breedLoseEnergy;
    private int genomLength;
    private MoveType moveType;

    private MutationType mutationType;
    private int minMutation;
    private int maxMutation;

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
