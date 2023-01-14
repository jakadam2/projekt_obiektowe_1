package oop;

public class Plant implements IMapElement {

    private final Vector2d position;
    public final int energy;

    public Plant(Vector2d position, int energy) {
        this.position = position;
        this.energy = energy;
    }

    @Override
    public Vector2d getPosition() {
        return this.position;
    }
}
