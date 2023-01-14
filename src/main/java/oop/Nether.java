package oop;

public class Nether extends AbstractWorldMap {

    private final int width;
    private final int height;
    private final double loseTeleportationEnergy;

    private final MyRandom generator = new MyRandom();

    public Nether(Settings config) {
        super(config);
        width = config.getMapWidth();
        height = config.getMapHeight();
        loseTeleportationEnergy = config.getBreedLoseEnergy();
    }

    @Override
    Vector2d checkFinalPosition(Animal animal, Vector2d cand) {
        if (cand.x > width || cand.x < 0 || cand.y > height || cand.y < 0) { // polecam precedes/follows
            animal.energy = (int) (animal.energy * (1.0 - loseTeleportationEnergy)); // energia nie powinna być zmieniana z zewnątrz
            animal.orientation = animal.orientation.rotate(generator.nextInt(8)); // jw.
            return new Vector2d(generator.nextInt(width + 1), generator.nextInt(height + 1));
        }
        return cand;
    }
}
