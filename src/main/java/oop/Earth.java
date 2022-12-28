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
    Vector2d checkFinalPosition(Vector2d curr,Vector2d cand) {


        if (cand.x > width) {
            //cand.x = 0;
            return new Vector2d(0,cand.y);
        }
        else if(cand.x < 0) {
            //cand.x = width;
            return new Vector2d(width, cand.y);
        }

        if(cand.y > height || cand.y < height) {
            return curr;
        }

        return cand;
    }
}
