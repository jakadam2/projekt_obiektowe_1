package oop;

import java.util.Random;

public class MyRandom extends Random {
    private Random generator = new Random();

    public MapDirection nextDirection() {
        int dir = generator.nextInt(8);
        return switch (dir) {
            case 0 -> MapDirection.NORTH;
            case 1 -> MapDirection.NORTH_EAST;
            case 2 -> MapDirection.EAST;
            case 3 -> MapDirection.SOUTH_EAST;
            case 4 -> MapDirection.SOUTH;
            case 5 -> MapDirection.SOUTH_WEST;
            case 6 -> MapDirection.WEST;
            case 7 -> MapDirection.NORTH_WEST;
            default -> throw new ArithmeticException("WRONG RNG");
        };
    }

    public int[] nextGenom(int genomLength) {
        int[] ans = new int[genomLength];
        for (int i = 0; i < genomLength; i++) {
            ans[i] = generator.nextInt(8);
        }
        return ans;
    }

    public Vector2d nextPosition(int maxX, int maxY) {
        return new Vector2d(generator.nextInt(maxX), generator.nextInt(maxY));
    }
}
