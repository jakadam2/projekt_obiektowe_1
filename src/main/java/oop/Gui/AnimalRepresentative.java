package oop.Gui; // pakiety raczej małymi literami albo camelCase

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import oop.*;

import java.util.Arrays;

public class AnimalRepresentative {

    private Animal animal;

    public AnimalRepresentative(Animal animal) {
        this.animal = animal;
    }

    public AnimalRepresentative() {
        this.animal = null; // czy to ma sens?
    }

    public VBox getInfo() {
        GridPane grid = new GridPane();
        grid.add(getChildLabel(), 0, 0, 1, 1);
        grid.add(getEnergyLabel(), 0, 1, 1, 1);
        grid.add(getGenomLabel(), 1, 0, 1, 1);
        grid.add(getActiveGenLabel(), 1, 1, 1, 1);
        grid.add(getEatenPlantsLabel(), 2, 0, 1, 1);
        grid.add(getLivedDaysLabel(), 2, 1, 1, 1);
        grid.setHgap(20);
        grid.setVgap(20);
        grid.setAlignment(Pos.CENTER);
        Label label = new Label("INFORMACJE O ZWIERZECIU");
        VBox box = new VBox(label, grid);
        box.setAlignment(Pos.CENTER);

        return box;

    }

    private Label getChildLabel() {
        if (animal == null) {
            return new Label("-||-");
        }
        return new Label("L.dzieci: " + animal.getChild());
    }

    private Label getGenomLabel() {
        if (animal == null) {
            return new Label("-||-");
        }
        return new Label("Genom: " + Arrays.toString(animal.getGenom()));
    }

    private Label getLivedDaysLabel() {
        if (animal == null) {
            return new Label("-||-");
        }
        return new Label("Zyje juz: " + animal.getLivedDays());
    }

    private Label getActiveGenLabel() {
        if (animal == null) {
            return new Label("-||-");
        }
        return new Label("Aktywny gen: " + animal.getActiveGen());
    }

    private Label getEatenPlantsLabel() {
        if (animal == null) {
            return new Label("-||-");
        }
        return new Label("Zjedzone rosliny: " + animal.getEatenPlant());
    }

    private Label getEnergyLabel() {
        if (animal == null) {
            return new Label("-||-");
        }
        return new Label("Energia:" + Integer.toString(animal.getEnergy()));
    }

    public void setAnimal(Animal animal) { // można zmienić zwierzę w trakcie życia tego obiektu?
        this.animal = animal;
    }

    public boolean isTracking() {
        return animal != null;
    }

    public boolean hasDeadAnimal() {
        return animal.getEnergy() <= 0;
    }

}