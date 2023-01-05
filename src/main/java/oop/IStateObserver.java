package oop;

import javafx.stage.Stage;

public interface IStateObserver {
    public void update(Stage myStage,AbstractWorldMap map);
}
