package library;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import static library.Main.books;

public class Manage extends BaseView {
    // declare manage scene components
    private Button edit, write, delete;

    public Manage(Stage stage, boolean hideButton, boolean searchButton) { // initialize the manage scene components
        super(stage, hideButton, searchButton);
        this.stage = stage;
        tabPane.getSelectionModel().select(manage);
        edit = new Button("Edit");
        delete = new Button("Delete");
        write = new Button("Write to file");
        MainInterFace.setButtonsStyle(edit);
        MainInterFace.setButtonsStyle(delete);
        MainInterFace.setButtonsStyle(write);

        actions();
        updateActions();
    }

    public VBox top() { // a method to arrange the top components of the manage scene
        VBox top = new VBox(10, tabPane, sortRadioButtons(), searchButton(), btns());
        return top;
    }

    public HBox btns() { // a method to arrange the buttons of the manage scene
        HBox btns = new HBox(60, back, write, edit, delete);
        btns.setAlignment(Pos.CENTER);
        btns.setPadding(new Insets(40));
        return btns;
    }

    public HBox sortRadioButtons() { // a method to arrange the radio buttons of the manage scene
        HBox sort = new HBox(7);
        sort.setAlignment(Pos.CENTER);
        return sort;
    }

    public HBox searchButton() { // a method to arrange the search button and it's text field of the manage scene
        HBox searchBy = new HBox(15);
        searchBy.setAlignment(Pos.CENTER);
        return searchBy;
    }

    public BorderPane main() { // a method to assemble the add scene components together
        BorderPane main = new BorderPane();
        main.setTop(top());
        main.setCenter(setTableView());
        main.setStyle("-fx-background-color: #D8D8D8;");
        return main;
    }

    private void updateActions() { // a method to fire the actions
        view.setOnSelectionChanged(e -> Main.changeScene(new View(stage, hideButton, searchButton).main()));

        edit.setOnAction(e -> { // edit a book
            Book book = (Book) tableView.getSelectionModel().getSelectedItem();
            if (book == null)
                Handler.alert("Information", "you must select a book to update", Alert.AlertType.INFORMATION);
            else {
                if (!searchButton) {
                    showSearch();
                }

                if (!hideButton) {
                    restoreInactiveAuthors();
                }
                String index = String.valueOf(Integer.parseInt(book.getBookId()));
                Main.changeScene(new Edit(stage, index).main());
            }
        });

        delete.setOnAction(e -> { // delete a book
            Book book = (Book) tableView.getSelectionModel().getSelectedItem();
            if (book == null)
                Handler.alert("Information", "you must select a book to delete", Alert.AlertType.INFORMATION);
            else {
                int index = Integer.parseInt(book.getBookId()) - 1000;
                boolean ok = Handler.alert("Delete a book", "Are you sure ypu want to delete this book?", Alert.AlertType.CONFIRMATION);
                if (ok) {
                    if (!searchButton) {
                        showSearch();
                    }
                    if (!hideButton) {
                        restoreInactiveAuthors();
                    }
                    books.remove(book);
                    bookList.setAll(books);
                    int size = books.size();
                    for (int i = index; i < size; i++) {
                        books.get(i).setBookId(String.valueOf(Integer.parseInt(books.get(i).getBookId()) - 1));
                    }
                }
            }
        });

        write.setOnAction(e -> { // write books to a file
            try (PrintWriter printWriter = new PrintWriter(new File("updatedBooks.txt"))) {
                int size = bookList.size();
                for (int i = 0; i < size; i++) {
                    printWriter.print(bookList.get(i).getBookId() + ", ");
                    printWriter.print(bookList.get(i).getTitle() + ", ");
                    printWriter.print(bookList.get(i).getAuthor() + ", ");
                    printWriter.print(bookList.get(i).getCategory() + ", ");
                    printWriter.print(bookList.get(i).getPublishedYear() + ", ");
                    printWriter.println(bookList.get(i).getISBN());
                }
                Handler.alert("Success", "Books written successfully", Alert.AlertType.CONFIRMATION);
            } catch (FileNotFoundException ex) {
                Handler.alert("Error", "File not found", Alert.AlertType.ERROR);
            }
        });
    }
}
