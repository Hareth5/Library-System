package library;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class MainInterFace extends BaseTabs {
    // declare main interface components
    public static Image image;
    private ImageView imageView;
    private Label label;
    private Button add, read, view, manage;
    private Stage stage;

    public MainInterFace(Stage stage) { // initialize main interface components
        this.stage = stage;
        tabPane.getSelectionModel().select(main);
        image = new Image("library.jpg", 650, 500, true, true);
        imageView = new ImageView(image);

        label = new Label("Welcome to your library");
        label.setStyle("-fx-text-fill: #333333; -fx-font-size: 60px;");

        add = new Button("Add");
        read = new Button("Read");
        view = new Button("View");
        manage = new Button("Manage");
        setButtonsStyle(add);
        setButtonsStyle(read);
        setButtonsStyle(view);
        setButtonsStyle(manage);

        actions();
    }

    public Button getRead() { // getter for read button
        return read;
    }

    public static void setButtonsStyle(Button btn) { // a method to style buttons
        btn.setPrefWidth(230);
        btn.setPrefHeight(60);
        btn.setStyle("-fx-font-size: 20px; -fx-text-fill: #333333; -fx-background-color: #2AB7F4;");
    }

    public static void setTabStyle(Tab tab) { //a method to style tabs
        tab.setStyle("-fx-pref-width: 150px; -fx-pref-height: 40px; -fx-font-size: 17px;");
    }

    public VBox center() { // a method to arrange the center components of the main interface
        GridPane btns = new GridPane();
        btns.setVgap(20);
        btns.setHgap(20);
        btns.addColumn(0, add, view);
        btns.addColumn(1, read, manage);
        btns.setAlignment(Pos.CENTER);

        VBox center = new VBox(20, label, imageView, btns);
        center.setAlignment(Pos.CENTER);
        return center;
    }

    public BorderPane main() { // a method to assemble the main interface components together
        BorderPane main = new BorderPane();
        main.setTop(tabPane);
        main.setCenter(center());
        main.setPadding(new Insets(0, 0, 20, 0));
        main.setStyle("-fx-background-color: #D8D8D8;");

        VBox leftSide = new VBox();
        VBox rightSide = new VBox();
        leftSide.setStyle("-fx-background-color: #2AB7F4; -fx-pref-width: 250;");
        rightSide.setStyle("-fx-background-color: #2AB7F4; -fx-pref-width: 250;");
        main.setLeft(leftSide);
        main.setRight(rightSide);

        return main;
    }

    private void actions() { // method for main interface components actions
        statistics.setOnSelectionChanged(e -> Main.changeScene(new Statistics(stage).main()));

        add.setOnAction(e -> Main.changeScene(new Add(stage).main()));

        view.setOnAction(e -> Main.changeScene(new View(stage,true, true).main()));

        manage.setOnAction(e -> Main.changeScene(new Manage(stage,true, true).main()));

        read.setOnAction(e -> { // read from file chosen by file chooser
            FileChooser fc = new FileChooser();
            fc.setTitle("my files");
            fc.setInitialDirectory(new File("C:\\Users\\harit\\OneDrive\\Desktop\\JAVA\\Library Management System\\src"));
            File f = fc.showOpenDialog(stage);
            if (f != null && f.exists()) {
                try (Scanner scanner = new Scanner(f)) {
                    Main.books.clear();

                    while(scanner.hasNextLine()) {
                        String arr[] = scanner.nextLine().split(", ");
                        new Handler(new Add(stage)).handler(arr);
                    }
                    Handler.alert("Success", "Books added successfully", Alert.AlertType.CONFIRMATION);
                } catch(FileNotFoundException ex) {
                    Handler.alert("Error", "File not found", Alert.AlertType.ERROR);
                }
            }
        });
    }
}
