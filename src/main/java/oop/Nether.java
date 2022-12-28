package oop;

public class Nether extends AbstractWorldMap {

    private final int width;
    private final int height;
    private final float loseTeleportationEnergy;

    private final MyRandom generator = new MyRandom();

    public Nether(Settings config) {
        super(config);
        width = config.getMapWidth();
        height = config.getMapHeight();
        loseTeleportationEnergy = config.getBreedLoseEnergy();
    }

    @Override
    Vector2d checkFinalPosition(Animal animal, Vector2d cand) {
        if (cand.x > width || cand.x < 0 || cand.y > height || cand.y < 0) {
            animal.energy = (int) (animal.energy * (1.0 - loseTeleportationEnergy));
            animal.orientation = animal.orientation.rotate(generator.nextInt(8));
            return new Vector2d(generator.nextInt(width+1),generator.nextInt(height+1));
        }
        return cand;
    }
}
