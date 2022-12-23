package oop;

public class Plant implements IMapElement {

    private final Vector2d position;//chyba może być public bo jest final

    public final int energy;//jak wyzej
    //jest jeden rodzaj

    public Plant(Vector2d position, int energy){
        this.position = position;
        this.energy = energy;
    }

    @Override
    public Vector2d getPosition(){return this.position;}
}
