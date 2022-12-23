package oop;

public enum MapDirection {

    NORTH(0),NORTH_EAST(1),EAST(2), SOUTH_EAST(3),SOUTH(4),SOUTH_WEST(5),WEST(6),NORTH_WEST(7);

    private final int dirNr;

    MapDirection(int dirNr){
        this.dirNr = dirNr;
    }

    final private static MapDirection[] dirPositionNr = {NORTH,NORTH_EAST,EAST, SOUTH_EAST,SOUTH,SOUTH_WEST,WEST,NORTH_WEST};

    public Vector2d toUnitVector(){
        int newX = 0;
        int newY = 0;
        switch (this){
            case SOUTH -> newY = -1;
            case NORTH -> newY = 1;
            case EAST -> newX = 1;
            case WEST -> newX = -1;
            case NORTH_EAST -> {newX = 1;newY = 1;}
            case NORTH_WEST -> {newX = -1;newY = 1;}
            case SOUTH_EAST -> {newX = 1;newY = -1;}
            case SOUTH_WEST -> {newX = -1;newY = -1;}
        };
        return new Vector2d(newX,newY);
    }

    public MapDirection rotate(int rotates){
        return dirPositionNr[(this.dirNr + rotates)%dirPositionNr.length];
    }

}
