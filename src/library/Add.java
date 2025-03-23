package library;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class Add extends BaseDesign {
    // declare add scene components
    private Button add, clear;
    private Stage stage;

    public Add(Stage stage) { //initialize the add scene components
        this.stage = stage;
        text = new Label("Add new Book");
        text.setStyle("-fx-text-fill: #333333; -fx-font-size: 60px;");

        add = new Button("Add");
        clear = new Button("Clear");
        MainInterFace.setButtonsStyle(add);
        MainInterFace.setButtonsStyle(clear);

        addActions();
    }

    public HBox bottom() { // a method to arrange the bottom components of the add scene
        HBox bottom = new HBox(50, back, clear, add);
        bottom.setAlignment(Pos.CENTER);
        bottom.setPadding(new Insets(60));
        return bottom;
    }

    public BorderPane main() { // a method to assemble the add scene components together
        main.setTop(text);
        BorderPane.setAlignment(text, Pos.CENTER);

        main.setBottom(bottom());
        main.setStyle("-fx-background-color: #D8D8D8;");
        return main;
    }

    public void setEmpty() { // a method to set all text fields to null
        title.setText("");
        author.setText("");
        category.setText("");
        publishedYear.setText("");
        ISBN.setText("");
    }

    private void addActions() { // a method to fire the actions
        back.setOnAction(e -> Main.changeScene(new MainInterFace(stage).main()));
        clear.setOnAction(e -> setEmpty());
        add.setOnAction(e -> new Handler(this).handler(null));
    }

}

