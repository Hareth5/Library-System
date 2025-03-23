package library;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import static library.Main.books;

public class Edit extends BaseDesign {
    // declare edit scene components
    private Button update, next, prev;
    private Stage stage;
    String index;

    public Edit(Stage stage, String index) { //initialize the edit scene components
        this.index = index;
        text = new Label("Update book");
        text.setStyle("-fx-text-fill: #333333; -fx-font-size: 60px;");

        update = new Button("Update");
        next = new Button("next");
        prev = new Button("previous");
        MainInterFace.setButtonsStyle(next);
        MainInterFace.setButtonsStyle(prev);
        MainInterFace.setButtonsStyle(update);

        if (Integer.parseInt(index) - 1000 == 0)
            prev.setDisable(true);
        else
            prev.setDisable(false);

        if (Integer.parseInt(index) - 999 == Main.books.size())
            next.setDisable(true);
        else
            next.setDisable(false);

        updateActions();
    }

    public HBox bottom() { // a method to arrange the bottom components of the edit scene
        HBox bottom = new HBox(50, back, prev, next, update);
        bottom.setAlignment(Pos.CENTER);
        bottom.setPadding(new Insets(60));
        return bottom;
    }

    public BorderPane main() { // a method to assemble the edit scene components together
        main.setTop(text);
        BorderPane.setAlignment(text, Pos.CENTER);

        main.setBottom(bottom());
        main.setStyle("-fx-background-color: #D8D8D8;");
        fill();

        return main;
    }

    public void fill() { // a method to fill the text fields when open update page
        int listIndex = Integer.parseInt(index) - 1000;

        bookId.setText(index);
        title.setText(books.get(listIndex).getTitle());
        author.setText(books.get(listIndex).getAuthor());
        category.setText(books.get(listIndex).getCategory());
        publishedYear.setText(books.get(listIndex).getPublishedYear());
        ISBN.setText(books.get(listIndex).getISBN());
    }

    public void updateActions() { // method for edit components actions
        update.setOnAction(e -> new Handler(this).handler(null));
        back.setOnAction(e -> Main.changeScene(new Manage(stage, true, true).main()));
        prev.setOnAction(e -> Main.changeScene(new Edit(stage, String.valueOf(Integer.parseInt(index) - 1)).main()));
        next.setOnAction(e -> Main.changeScene(new Edit(stage, String.valueOf(Integer.parseInt(index) + 1)).main()));
    }
}
