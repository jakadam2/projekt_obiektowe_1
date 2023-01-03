package oop;

public class Earth extends AbstractWorldMap{

    private final int width;
    private final int height;

    public Earth(Settings config) {
        super(config);
        width = config.getMapWidth();
        height = config.getMapHeight();
    }

    @Override
    Vector2d checkFinalPosition(Animal animal,Vector2d cand) {

        if (cand.x > width) {
            System.out.println("a");
            return new Vector2d(0, cand.y);

        }
        else if(cand.x < 0) {
            System.out.println("a");
            return new Vector2d(width, cand.y);
        }

        if(cand.y > height || cand.y < 0) {

            animal.orientation = animal.orientation.rotate(4);
            return new Vector2d(cand.x, height);
        }

        return cand;
    }
}