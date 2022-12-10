package oop;

import java.util.HashSet;
import java.util.Set;

public class MapElementBox {

    Set<IMapElement> elements = new HashSet<>();

    private final Vector2d position;

    public boolean includePlant = false;
    public MapElementBox(Vector2d position){
        this.position = position;
    }

    public void add(IMapElement element){
        elements.add(element);
        if(element.getClass() == Plant.class){this.includePlant = true;}
    }

    public void remove(IMapElement element){
        if(element.getClass() == Plant.class){this.includePlant = false;}
        elements.remove(element);
    }

    public IMapElement[] getElements(){
        return (IMapElement[]) elements.toArray();//napewno moge zrzutować bo elements ma tylko mapelementy, a taki getter jest chyba ok
    }

    public Vector2d getPosition(){
        return this.position;
    }

    public boolean isEmpty(){
        return elements.isEmpty();
    }
}
