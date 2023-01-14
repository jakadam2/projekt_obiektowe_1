package oop.Gui;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import oop.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Set;

public class ElementRepresentative {

    private Image one; // czy to by nie mogło być statyczne?
    private Image two;
    private Image three;
    private Image four;
    private Image five;
    private Image grass;

    private Image popular;

    private int[] popGenome;

    public ElementRepresentative() {
        try {
            one = new Image(new FileInputStream("src/main/resources/1.png"));
            two = new Image(new FileInputStream("src/main/resources/2.png"));
            three = new Image(new FileInputStream("src/main/resources/3.png"));
            four = new Image(new FileInputStream("src/main/resources/4.png"));
            five = new Image(new FileInputStream("src/main/resources/5.png"));
            grass = new Image(new FileInputStream("src/main/resources/grass.png"));
            popular = new Image(new FileInputStream("src/main/resources/popular.png"));
        } catch (FileNotFoundException exception) {
            System.out.println("WRONG FILE PATH");
            System.exit(1); // może lepiej przepuścić ten wyjątek?
        }

    }

    private ImageView getReprezentation(IMapElement element) { // to s czy z?
        if (element.getClass() == Plant.class) {
            return new ImageView(grass);
        }
        if (element.getClass() != Animal.class) {
            throw new IllegalArgumentException("DRAWING OBJECTS OTHER THAN PLANT OR ANIMAL IS UNAVAILABLE ");
        }
        Animal animal = (Animal) element;
        Image cimage = switch (animal.getColor()) {
            case 1 -> one;
            case 2 -> two;
            case 3 -> three;
            case 4 -> four;
            default -> five;
        };
        return new ImageView(cimage);
    }


    public ImageView getView(MapElementBox box) {
        if (box.isEmpty()) {
            throw new IllegalStateException("BOX CANNOT BE EMPTY");
        }
        if (box.includeAnimal()) {

            Set<Animal> animals = box.getAnimals();
            Iterator<Animal> iterator = animals.iterator();
            Animal strongest = iterator.next();
            for (Animal animal : animals) {
                if (animal.getColor() > strongest.getColor()) {
                    strongest = animal;
                }
                if (Arrays.equals(popGenome, animal.getGenom())) {
                    return new ImageView(popular);
                }
            }
            return getReprezentation(strongest);
        } else {
            return getReprezentation(box.getPlant());
        }

    }

    public void setPopGenome(int[] genome) { // skrót Pop nie będzie czytelny za 2 tygodnie
        this.popGenome = genome;

    }


}

