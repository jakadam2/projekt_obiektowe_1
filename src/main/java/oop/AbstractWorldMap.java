package oop;

import java.util.HashMap;
import java.util.Map;

abstract class AbstractWorldMap implements IPositionObserver {
    private final int x;//włącznie
    private final int y;//włącznie

    private Map<Vector2d,MapElementBox> boxes = new HashMap<>();

    public AbstractWorldMap(int x, int y){
        this.x = x;
        this.y = y;
    }

    public void place(IMapElement element){
        if(this.canMoveTo(element.getPosition())){
            if(this.boxExistAt(element.getPosition())){
                this.boxAt(element.getPosition()).add(element);
            }
            else{
                MapElementBox box = new MapElementBox(element.getPosition());
                boxes.put(element.getPosition(),box);
                box.add(element);
            }
        }
    }

    public void remove(IMapElement element){// mozna tu rzucic wyjatek gdyby jakims cudem to zwierze bylo zdjete
        this.boxAt(element.getPosition()).remove(element);
        if(boxAt(element.getPosition()).isEmpty()){
            boxes.remove(element.getPosition());
        }
    }

    @Override
    public void update(Vector2d oldPosition, Vector2d newPosition, Animal animal) {//bo tak naprawde to mogę tylko zwierzeta przesuwac, trawe usuwam
        this.boxAt(oldPosition).remove(animal);//odszukuje go po starej
        //tu niech usuwa ewntualnie pusty box
        this.place(animal);//tu juz ma nowa pozycje wiec chyba git
    }

    public boolean canMoveTo(Vector2d cand){
        return cand.x >= 0 && cand.x <= this.x && cand.y >= 0 && cand.y <= this.y;
    }

    private boolean boxExistAt(Vector2d position){
        return boxes.get(position) != null;
    }

    private MapElementBox boxAt(Vector2d position){
        return boxes.get(position);
    }

    private void checkEating(){
        for(MapElementBox box: boxes.values()){
            if(box.includePlant){

            }
        }
    }


}
