package oop.Gui;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import oop.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.Set;

public class ElementRepresentative {

    private Image one;
    private Image two;
    private Image three;
    private Image four;
    private Image five;
    private Image grass;

    public ElementRepresentative() {
        try{
        one = new Image(new FileInputStream("src/main/resources/1.png"));
        two = new Image(new FileInputStream("src/main/resources/2.png"));
        three = new Image(new FileInputStream("src/main/resources/3.png"));
        four = new Image(new FileInputStream("src/main/resources/4.png"));
        five = new Image(new FileInputStream("src/main/resources/5.png"));
        grass = new Image(new FileInputStream("src/main/resources/grass.png"));}
        catch (FileNotFoundException exception){
            System.out.println("WRONG FILE PATH");
            System.exit(1);
        }

    }

    private ImageView getReprezentation(IMapElement element){
        if(element.getClass() == Plant.class){return new ImageView(grass);}
        if(element.getClass() != Animal.class){throw new IllegalArgumentException("DRAWING OBJECTS OTHER THAN PLANT OR ANIMAL IS UNAVAILABLE ");}
        Animal animal = (Animal) element;
        Image cimage = switch (animal.getColor()){
            case 1 -> one;
            case 2 -> two;
            case 3 -> three;
            case 4 -> four;
            default -> five;};
        return new ImageView(cimage);
        }


    public ImageView getView(MapElementBox box){
        if(box.isEmpty()){throw new IllegalStateException("BOX CANNOT BE EMPTY");}
        if(box.includePlant()){return getReprezentation(box.getPlant());}
        Set<Animal> animals =  box.getAnimals();
        Iterator iterator = animals.iterator();
        Animal strongest = (Animal) iterator.next();
        for(Animal animal:animals){
            if (animal.getColor() > strongest.getColor()){strongest = animal;}
        }
        return getReprezentation(strongest);
    }


    }

