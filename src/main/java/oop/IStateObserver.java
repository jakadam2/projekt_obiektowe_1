package oop;

import javafx.stage.Stage;
import oop.Gui.AnimalRepresentative;

public interface IStateObserver {
    public void update(Stage myStage, AbstractWorldMap map, AnimalRepresentative animalRepresentative);
}
