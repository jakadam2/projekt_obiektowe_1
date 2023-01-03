package oop;

import javafx.application.Application;
import oop.Gui.App;

import java.util.HashSet;
import java.util.Set;

public class World {

    public static void main(String[] args){
        Settings config = new Settings();
        SimulationEngine engine = new SimulationEngine(config);
        engine.run();
        Application.launch(App.class, args);

    }

}
