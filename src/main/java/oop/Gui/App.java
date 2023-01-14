package oop.Gui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import oop.*;
import javafx.geometry.Insets;
import javafx.scene.control.ChoiceBox;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;


public class App extends Application implements IStateObserver {

    private ElementRepresentative representative = new ElementRepresentative();
    Stage window; // modyfikator dostępu?
    Scene settingsMenu;
    Image boundary;
    Button test;

    public void init() {
        try {
            boundary = new Image(new FileInputStream("src/main/resources/boundary.png"));
        } catch (FileNotFoundException exception) {
            System.exit(3);
        }
    }

    public void start(Stage primaryStage) {
        Platform.runLater(() -> {
            window = primaryStage;
            GridPane settingsGrid = new GridPane();
            generateSettingsMenu(settingsGrid);
            settingsMenu = new Scene(settingsGrid, 500, 800);
            window.setScene(settingsMenu);
            window.setTitle("Settings");
            window.show();
        });
    }

    private void drawInterior(GridPane grid, AbstractWorldMap map) {
        MapElementBox box;
        Vector2d currPosition;
        ImageView view;
        int[] popGenome = map.getMostPopularGenomes();
        representative.setPopGenome(popGenome);
        for (int i = 0; i <= map.x; i++) {
            grid.add(new ImageView(boundary), i, 0, 1, 1);
        }
        for (int i = 0; i <= map.y; i++) {
            grid.add(new ImageView(boundary), 0, i, 1, 1);
        }
        for (int i = 0; i <= map.y; i++) {
            grid.add(new ImageView(boundary), map.x + 1, i, 1, 1);
        }
        for (int i = 0; i <= map.x; i++) {
            grid.add(new ImageView(boundary), i, map.y + 1, 1, 1);
        }
        grid.add(new ImageView(boundary), map.x + 1, map.y + 1, 1, 1);
        for (int i = 0; i < map.x; i++) {
            for (int j = 0; j < map.y; j++) {
                currPosition = new Vector2d(i, j);
                if (map.isOccupied(currPosition)) {
                    box = map.boxAt(currPosition);
                    view = representative.getView(box);
                    grid.add(view, i + 1, j + 1, 1, 1);
                }
            }
        }
    }

    private void generateSettingsMenu(GridPane grid) {
        Platform.runLater(() -> {
            grid.setPadding(new Insets(10));
            grid.setVgap(7);
            grid.setHgap(10);

            Label mapTypeText = new Label("Rodzaj mapy: ");
            ChoiceBox<MapType> mapTypeInput = new ChoiceBox<>();
            mapTypeInput.getItems().addAll(MapType.EARTH, MapType.NETHER);
            mapTypeInput.setValue(MapType.EARTH);
            GridPane.setConstraints(mapTypeText, 0, 0);
            GridPane.setConstraints(mapTypeInput, 1, 0);

            Label mapWidthText = new Label("Szerokosc mapy: ");
            TextField mapWidthInput = new TextField();
            GridPane.setConstraints(mapWidthText, 0, 1);
            GridPane.setConstraints(mapWidthInput, 1, 1);

            Label mapHeightText = new Label("Wysokosc mapy: ");
            TextField mapHeightInput = new TextField();
            GridPane.setConstraints(mapHeightText, 0, 2);
            GridPane.setConstraints(mapHeightInput, 1, 2);


            Label plantTypeText = new Label("Rodzaj biomu roslin: ");
            ChoiceBox<PlantType> plantTypeInput = new ChoiceBox<>();
            plantTypeInput.getItems().addAll(PlantType.JUNGLE, PlantType.TOXIC);
            plantTypeInput.setValue(PlantType.JUNGLE);
            GridPane.setConstraints(plantTypeText, 0, 5);
            GridPane.setConstraints(plantTypeInput, 1, 5);

            Label startPlantText = new Label("Startowa liczba roslin: ");
            TextField startPlantInput = new TextField();
            GridPane.setConstraints(startPlantText, 0, 6);
            GridPane.setConstraints(startPlantInput, 1, 6);

            Label dailyPlantText = new Label("Liczba nowych roslin: ");
            TextField dailyPlantInput = new TextField();
            GridPane.setConstraints(dailyPlantText, 0, 7);
            GridPane.setConstraints(dailyPlantInput, 1, 7);

            Label energyPlantText = new Label("Liczba energi roslin: ");
            TextField energyPlantInput = new TextField();
            GridPane.setConstraints(energyPlantText, 0, 8);
            GridPane.setConstraints(energyPlantInput, 1, 8);


            Label startAnimalText = new Label("Startowa liczba zwierzat: ");
            TextField startAnimalInput = new TextField();
            GridPane.setConstraints(startAnimalText, 0, 11);
            GridPane.setConstraints(startAnimalInput, 1, 11);

            Label startEnergyAnimalText = new Label("Startowa energia zwierzat: ");
            TextField startEnergyAnimalInput = new TextField();
            GridPane.setConstraints(startEnergyAnimalText, 0, 12);
            GridPane.setConstraints(startEnergyAnimalInput, 1, 12);

            Label breedEnergyText = new Label("Energia potrzebna do rozmnozenia: ");
            TextField breedEnergyInput = new TextField();
            GridPane.setConstraints(breedEnergyText, 0, 13);
            GridPane.setConstraints(breedEnergyInput, 1, 13);

            Label breedLoseEnergyText = new Label("Ulamek utraty energi po rozmnozeniu: ");
            TextField breedLoseEnergyInput = new TextField();
            GridPane.setConstraints(breedLoseEnergyText, 0, 14);
            GridPane.setConstraints(breedLoseEnergyInput, 1, 14);

            Label genomLengthText = new Label("Dlugosc genomu: ");
            TextField genomLengthInput = new TextField();
            GridPane.setConstraints(genomLengthText, 0, 15);
            GridPane.setConstraints(genomLengthInput, 1, 15);

            Label moveTypeText = new Label("Rodzaj ruchu: ");
            ChoiceBox<MoveType> moveTypeInput = new ChoiceBox<>();
            moveTypeInput.getItems().addAll(MoveType.CORRECT, MoveType.RANDOM);
            moveTypeInput.setValue(MoveType.CORRECT);
            GridPane.setConstraints(moveTypeText, 0, 16);
            GridPane.setConstraints(moveTypeInput, 1, 16);

            Label mutationTypeText = new Label("Rodzaj mutacji: ");
            ChoiceBox<MutationType> mutationTypeInput = new ChoiceBox<>();
            mutationTypeInput.getItems().addAll(MutationType.LITTLE_CORRECTION, MutationType.FULLY_RANDOM);
            mutationTypeInput.setValue(MutationType.LITTLE_CORRECTION);
            GridPane.setConstraints(mutationTypeText, 0, 19);
            GridPane.setConstraints(mutationTypeInput, 1, 19);

            Label minMutationText = new Label("Min liczba mutacji: ");
            TextField minMutationInput = new TextField();
            GridPane.setConstraints(minMutationText, 0, 20);
            GridPane.setConstraints(minMutationInput, 1, 20);

            Label maxMutationText = new Label("Max liczba mutacji: ");
            TextField maxMutationInput = new TextField();
            GridPane.setConstraints(maxMutationText, 0, 21);
            GridPane.setConstraints(maxMutationInput, 1, 21);


            Button preSettings1 = new Button("Mala ziemia");
            preSettings1.setOnAction(e -> {
                File file = new File("./PreSettings/Settings1.txt");
                try {
                    getConfig(file);
                } catch (FileNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
            });
            GridPane.setConstraints(preSettings1, 0, 25);

            Button preSettings2 = new Button("Srednie pieklo");
            preSettings2.setOnAction(e -> {
                File file = new File("./PreSettings/Settings2.txt");
                try {
                    getConfig(file);
                } catch (FileNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
            });
            GridPane.setConstraints(preSettings2, 0, 26);

            Button preSettings3 = new Button("Duza ziemia");
            preSettings3.setOnAction(e -> {
                File file = new File("./PreSettings/Settings3.txt");
                try {
                    getConfig(file);
                } catch (FileNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
            });
            GridPane.setConstraints(preSettings3, 0, 27);

            Button start = new Button("Start");
            start.setOnAction(e -> {
                Settings settings = new Settings();
                settings.setMapHeight(Integer.parseInt(mapHeightInput.getText()));
                settings.setMapWidth(Integer.parseInt(mapWidthInput.getText()));
                settings.setMapType(mapTypeInput.getValue());
                settings.setStartPlant(Integer.parseInt(startPlantInput.getText()));
                settings.setEnergyPlant(Integer.parseInt(energyPlantInput.getText()));
                settings.setDailyPlant(Integer.parseInt(dailyPlantInput.getText()));
                settings.setPlantType(plantTypeInput.getValue());
                settings.setStartAnimal(Integer.parseInt(startAnimalInput.getText()));
                settings.setStartEnergyAnimal(Integer.parseInt(startEnergyAnimalInput.getText()));
                settings.setBreedEnergy(Integer.parseInt(breedEnergyInput.getText()));
                settings.setBreedLoseEnergy(Double.parseDouble(breedLoseEnergyInput.getText()));
                settings.setMinMutation(Integer.parseInt(minMutationInput.getText()));
                settings.setMaxMutation(Integer.parseInt(maxMutationInput.getText()));
                settings.setMutationType(mutationTypeInput.getValue());
                settings.setGenomLength(Integer.parseInt(genomLengthInput.getText()));
                settings.setMoveType(moveTypeInput.getValue());

                startSimulation(settings);
            });
            GridPane.setConstraints(start, 1, 30);

            grid.getChildren().addAll(start, preSettings1, preSettings2, preSettings3, mapHeightText, mapHeightInput, mapWidthText, mapWidthInput, mapTypeText, mapTypeInput, startPlantText, startPlantInput, energyPlantText, energyPlantInput, dailyPlantText, dailyPlantInput, plantTypeText, plantTypeInput, startAnimalText, startAnimalInput, startEnergyAnimalText, startEnergyAnimalInput, breedEnergyText, breedEnergyInput, breedLoseEnergyText, breedLoseEnergyInput, maxMutationText, maxMutationInput, minMutationText, minMutationInput, mutationTypeText, mutationTypeInput, genomLengthText, genomLengthInput, moveTypeText, moveTypeInput);
        });
    }

    private void getConfig(File file) throws FileNotFoundException {
        Settings settings = new Settings();
        Scanner scan = new Scanner(file);
        settings.setMapType(MapType.valueOf(scan.next()));
        settings.setMapWidth(Integer.parseInt(scan.next()));
        settings.setMapHeight(Integer.parseInt(scan.next()));
        settings.setPlantType(PlantType.valueOf(scan.next()));
        settings.setStartPlant(Integer.parseInt(scan.next()));
        settings.setDailyPlant(Integer.parseInt(scan.next()));
        settings.setEnergyPlant(Integer.parseInt(scan.next()));
        settings.setStartAnimal(Integer.parseInt(scan.next()));
        settings.setStartEnergyAnimal(Integer.parseInt(scan.next()));
        settings.setBreedEnergy(Integer.parseInt(scan.next()));
        settings.setBreedLoseEnergy(Double.parseDouble(scan.next()));
        settings.setGenomLength(Integer.parseInt(scan.next()));
        settings.setMoveType(MoveType.valueOf(scan.next()));
        settings.setMutationType(MutationType.valueOf(scan.next()));
        settings.setMinMutation(Integer.parseInt(scan.next()));
        settings.setMaxMutation(Integer.parseInt(scan.next()));
        startSimulation(settings);
    }

    private void startSimulation(Settings settings) {
        AbstractWorldMap map;
        Stage newStage = new Stage();
        if (settings.getMapType() == MapType.NETHER) {
            map = new Nether(settings);
        } else {
            map = new Earth(settings);
        }
        SimulationEngine engine = new SimulationEngine(settings, map, newStage);
        Thread engineThread = new Thread(engine);
        engineThread.start();
        engine.addObserver(this);
    }

    @Override
    public void update(Stage myStage, AbstractWorldMap map, AnimalRepresentative animalRepresentative, SimulationEngine engine) {
        GridPane grid = new GridPane();
        drawInterior(grid, map);
        grid.setGridLinesVisible(true);
        VBox animalInfo = animalRepresentative.getInfo();
        VBox simulationInfo = generateSimulationData(map);
        Platform.runLater(() -> {
            VBox view = new VBox(grid, animalInfo, simulationInfo, engine.buttons);
            view.setAlignment(Pos.CENTER);
            Scene scene = new Scene(view);
            myStage.setScene(scene);
            myStage.show();
        });
    }


    private VBox generateSimulationData(AbstractWorldMap map) {
        GridPane grid = new GridPane();


        Label numberOfAnimals = new Label("Liczba zwierzat: " + map.getAmountAnimals());
        grid.add(numberOfAnimals, 0, 0, 1, 1);

        Label numberOfPlants = new Label("Liczba roslin: " + map.getAmountPlants());
        grid.add(numberOfPlants, 0, 1, 1, 1);

        Label numberOfFreePlaces = new Label("Liczba wolnych pol: " + map.getAmountFreePlaces());
        grid.add(numberOfFreePlaces, 1, 0, 1, 1);

        Label theMostPopularGenome = new Label("Najpopularniejszy genotop: " + Arrays.toString(map.getMostPopularGenomes()));
        grid.add(theMostPopularGenome, 1, 1, 1, 1);

        Label numberOfAvgEnergy = new Label("Sredni poziom energi zyjacych zwierzat: " + map.getAvgEnergy());
        grid.add(numberOfAvgEnergy, 2, 0, 1, 1);

        Label numberOfAvgLife = new Label("Sredni poziom zycia zmarlych zwierzat: " + map.getAvgLifeDeadAnimals());
        grid.add(numberOfAvgLife, 2, 1, 1, 1);

        grid.setVgap(20);
        grid.setHgap(20);
        grid.setAlignment(Pos.CENTER);
        Label label = new Label("INFORMACJE O SYMULACJI");

        VBox box = new VBox(label, grid);
        box.setAlignment(Pos.CENTER);

        return box;
    }
}
