package oop;

import java.util.HashSet;
import java.util.Set;

public class World {

    public static void main(String[] args){
        Settings config = new Settings();
        SimulationEngine engine = new SimulationEngine(config);
        engine.run();

    }

}
