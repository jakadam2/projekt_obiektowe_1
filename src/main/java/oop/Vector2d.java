package oop;

import java.util.Objects;

public class Vector2d{

    public final int x;

    public final int y;

    public Vector2d(int x,int y){
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString(){
        return "(" + x + "," + y + ")";}

    public Vector2d add(Vector2d other){
        return new Vector2d(this.x + other.x,this.y + other.y);
    }

    @Override
    public boolean equals(Object other){
        if(other.getClass() != Vector2d.class){return false;}
        Vector2d clone = (Vector2d) other;
        return this.x == clone.x && this.y == clone.y;
    }

    @Override
    public int hashCode(){
        return Objects.hash(x,y);
    }
}
