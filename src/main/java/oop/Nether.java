package oop;
    public class Nether extends AbstractWorldMap {

        private final int width;
        private final int height;
        private final MyRandom generator = new MyRandom();

        public Nether(Settings config) {
            super(config);
            width = config.getMapWidth();
            height = config.getMapHeight();
        }

        @Override
        Vector2d checkFinalPosition(Vector2d curr, Vector2d cand) {
            if (cand.x > width || cand.x < 0 || cand.y > height || cand.y < 0) {
                return new Vector2d(generator.nextInt(width+1),generator.nextInt(height+1));
            }
            return cand;
        }
    }

