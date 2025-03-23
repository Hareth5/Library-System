package library;

import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Main extends Application {
    public static ArrayList<Book> books, hideList, searchList;
    private static BorderPane main = new BorderPane(); // the main borderpane for the program
    public static void changeScene(Node node) { // a method to switch scenes by changing the center of the main borderpane
        main.setCenter(node);
    }

    @Override
    public void start(Stage primaryStage) { // the start method
        books = new ArrayList<>();
        hideList = new ArrayList<>();
        searchList = new ArrayList<>();

        main.setTop(new MainMenu(primaryStage).mainMenuBar()); // set the menu bar on the top for all scenes in the program
        main.setCenter(new MainInterFace(primaryStage).main()); // start the program with the main interface

        Scene scene = new Scene(main, 900, 700);
        primaryStage.setTitle("Library System Management");
        primaryStage.setMaximized(true);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
