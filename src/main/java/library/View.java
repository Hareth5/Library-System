package library;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Comparator;

import static library.Main.*;

public class View extends BaseView {
    // declare view scene components
    private TextField txt;

    public View(Stage stage, boolean hideButton, boolean searchButton) { // initialize the add scene components
        super(stage, hideButton, searchButton);
        tabPane.getSelectionModel().select(view);

        txt = new TextField();
        BaseDesign.setTextFieldStyle(txt);

        actions();
        viewActions();
    }

    public VBox top() { // a method to arrange the top components of the view scene
        VBox top = new VBox(20, tabPane, hBox());
        return top;
    }

    public HBox hBox() {
        HBox hBox = new HBox(90, btns(), search());
        hBox.setAlignment(Pos.CENTER);
        return hBox;
    }

    public HBox btns() { // a method to arrange the buttons of the view scene
        HBox btns = new HBox(60, back, hide);
        btns.setAlignment(Pos.CENTER);
        btns.setPadding(new Insets(40));
        return btns;
    }

    public HBox search() { // a method to arrange the top components of the view scene
        HBox top = new HBox(7, sort, search, searchBtn());
        top.setAlignment(Pos.BASELINE_RIGHT);
        return top;
    }

    public HBox searchBtn() { // a method to arrange the radio buttons of the view scene
        HBox sort = new HBox(7, txt, searchBtn);
        sort.setAlignment(Pos.BASELINE_RIGHT);
        return sort;
    }

    public BorderPane main() { // a method to assemble the add scene components together
        BorderPane main = new BorderPane();
        main.setTop(top());
        main.setCenter(setTableView());
        main.setStyle("-fx-background-color: #D8D8D8;");
        return main;
    }

    public void hideInactiveAuthors() { // a method to hide inactive authors by  put inactive authors into new list (hide list)
        hide.setText("Show all authors");
        hideButton = false;
        search.getItems().setAll("Search by Title", "Search by Author");
        hideList.clear();
        for (int i = 0; i < books.size(); i++) {
            if (Integer.parseInt(books.get(i).getPublishedYear()) < 2020) {
                hideList.add(books.get(i));
                books.remove(i);
                i--;
            }
        }
        bookList.setAll(books);
    }

    public boolean checkTextField(String value, String sortType, String regex) { // check if the search text filed null or invalid
        if (value.isEmpty()) {
            Handler.alert("Error", "You should enter a value to search by " + sortType, Alert.AlertType.ERROR);
            return false;
        }
        if (!value.matches(regex)) {
            Handler.alert("Error", "You should enter a valid " + sortType + " to search", Alert.AlertType.ERROR);
            return false;
        }
        return true;
    }

    private void viewActions() { // method for view components actions
        manage.setOnSelectionChanged(e -> Main.changeScene(new Manage(stage, hideButton, searchButton).main()));

        hide.setOnAction(e -> { // hide inactive authors
            if (!searchButton && hideButton) {
                Handler.alert("Information", "Please cancel search first before hiding inactive authors.",
                        Alert.AlertType.INFORMATION);
                return;
            }

            if (hideButton)
                hideInactiveAuthors();
            else
                restoreInactiveAuthors();
        });

        sort.setOnAction(e -> { // sorting books using comparable and comparator
            if (sort.getValue().equals("Sort by ID")) {
                java.util.Collections.sort(books);
                bookList.setAll(books);

            } else if (sort.getValue().equals("Sort by Title")) {
                Comparator<Book> compareByTitle = new Comparator<>() {
                    @Override
                    public int compare(Book b1, Book b2) {
                        return b1.getTitle().compareTo(b2.getTitle());
                    }
                };
                java.util.Collections.sort(books, compareByTitle);
                bookList.setAll(books);

            } else if (sort.getValue().equals("Sort by Author")) {
                Comparator<Book> compareByAuthor = new Comparator<>() {
                    @Override
                    public int compare(Book b1, Book b2) {
                        return b1.getAuthor().compareTo(b2.getAuthor());
                    }
                };
                java.util.Collections.sort(books, compareByAuthor);
                bookList.setAll(books);

            } else if (sort.getValue().equals("Sort by Published Year")) {
                Comparator<Book> compareByYear = new Comparator<>() {
                    @Override
                    public int compare(Book b1, Book b2) {
                        return Integer.parseInt(b1.getPublishedYear()) - Integer.parseInt(b2.getPublishedYear());
                    }
                };
                java.util.Collections.sort(books, compareByYear);
                bookList.setAll(books);
            }
        });

        searchBtn.setOnAction(e -> { // search for books
            if (!searchButton)
                showSearch();
            else {

                if (!hideButton) {
                    restoreInactiveAuthors();
                }

                String value = txt.getText();
                if (search.getValue().equals("Search by ID")) {
                    if (checkTextField(value, "Id", "^[0-9]+$")) {
                        int idValue = Integer.parseInt(value);
                        int index = idValue - 1000;

                        if (index < 0 || index >= books.size())
                            Handler.alert("Error", "There is no book with this ID", Alert.AlertType.ERROR);
                        else {
                            searchBtn.setText("Cancel search");
                            searchButton = false;
                            Main.searchList.clear();
                            Main.searchList.addAll(books);
                            books.clear();
                            books.add(Main.searchList.get(index));
                            bookList.setAll(books);
                            tableView.setItems(bookList);
                        }
                    }

                } else if (search.getValue().equals("Search by Title")) {
                    if (checkTextField(value, "Title", "^[a-zA-Z0-9 ]+$")) {
                        Main.searchList.clear();
                        Main.searchList.addAll(books);
                        books.clear();
                        int counter = 0;

                        for (Book book : Main.searchList) {
                            if (book.getTitle().toLowerCase().startsWith(value.toLowerCase())) {
                                books.add(book);
                                counter++;
                            }
                        }

                        if (counter == 0) {
                            Handler.alert("Error", "There is no book with this Title", Alert.AlertType.ERROR);
                            showSearch();
                        } else {
                            searchBtn.setText("Cancel search");
                            searchButton = false;
                            bookList.setAll(books);
                            tableView.setItems(bookList);
                        }
                    }

                } else if (search.getValue().equals("Search by Author")) {
                    if (checkTextField(value, "Author", "^[a-zA-Z ]+$")) {
                        Main.searchList.clear();
                        Main.searchList.addAll(books);
                        books.clear();
                        int counter = 0;

                        for (Book book : Main.searchList) {
                            if (book.getAuthor().toLowerCase().startsWith(value.toLowerCase())) {
                                books.add(book);
                                counter++;
                            }
                        }

                        if (counter == 0) {
                            Handler.alert("Error", "There is no book with this Author", Alert.AlertType.ERROR);
                            showSearch();
                        } else {
                            searchBtn.setText("Cancel search");
                            searchButton = false;
                            bookList.setAll(books);
                            tableView.setItems(bookList);
                        }
                    }

                } else {
                    Handler.alert("Error", "You should choose the search type", Alert.AlertType.ERROR);
                }
            }
        });
    }
}
