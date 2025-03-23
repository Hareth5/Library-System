package library;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Comparator;

import static library.Main.books;

public class Statistics extends BaseTabs {
    // declare statistics scene components
    private int maxYear, minYear, max, min;
    private String maxAuthor, minAuthor;
    private int maxBooks, minBooks;
    private ComboBox<String> comboCategory, comboAuthor, comboYear;
    private Label l2Category, l2Author, l2Year;
    private Stage stage;

    public Statistics(Stage stage) {
        this.stage = stage;
        actions();
    }

    public void setLabelStyle(Label label) { //a method to style tabs
        label.setStyle("-fx-text-fill: #444; -fx-font-size: 20px;");
    }

    public VBox leftSide() { // a method to arrange the left side components of the statistics page
        Label text = new Label("Year Statistics");
        text.setAlignment(Pos.TOP_CENTER);
        text.setStyle("-fx-text-fill: #444; -fx-font-size: 42px; -fx-font-weight: bold;");

        yearStatistics();

        Label maximumYear = new Label("Max books published in " + maxYear + " with " + max + " books");
        maximumYear.setWrapText(true);
        maximumYear.setAlignment(Pos.CENTER);
        maximumYear.setPadding(new Insets(10));
        setLabelStyle(maximumYear);

        Label minimumYear = new Label("Min books published in " + minYear + " with " + min + " books");
        minimumYear.setWrapText(true);
        minimumYear.setAlignment(Pos.CENTER);
        minimumYear.setPadding(new Insets(10));
        setLabelStyle(minimumYear);

        VBox leftSide = new VBox(30, text, maximumYear, minimumYear);
        leftSide.setStyle("-fx-background-color: linear-gradient(to bottom, #f0f0f0, #ffffff); " +
                "-fx-pref-width: 450; -fx-border-color: #ddd;");
        leftSide.setAlignment(Pos.TOP_CENTER);
        leftSide.setPadding(new Insets(20));
        return leftSide;
    }

    public VBox rightSide() { // a method to arrange the right side components of the statistics page
        Label text = new Label("Authors Statistics");
        text.setAlignment(Pos.TOP_CENTER);
        text.setStyle("-fx-text-fill: #444; -fx-font-size: 42px; -fx-font-weight: bold;");

        authorStatistics();

        ArrayList<String> maxList = new ArrayList<>();
        ArrayList<String> minList = new ArrayList<>();
        for (Book book : books) {
            if (book.getAuthor().equals(maxAuthor))
                maxList.add(book.getTitle());
            if (book.getAuthor().equals(minAuthor))
                minList.add(book.getTitle());
        }

        Label maximumAuthor = new Label("Author with most books: " + maxAuthor + " (" + maxBooks + " books) Titles: " + String.join(", ", maxList));
        maximumAuthor.setWrapText(true);
        maximumAuthor.setAlignment(Pos.CENTER);
        maximumAuthor.setPadding(new Insets(10));
        setLabelStyle(maximumAuthor);

        Label minimumAuthor = new Label("Author with fewest books: " + minAuthor + " (" + minBooks + " books) Titles: " + String.join(", ", minList));
        minimumAuthor.setWrapText(true);
        minimumAuthor.setAlignment(Pos.CENTER);
        minimumAuthor.setPadding(new Insets(10));
        setLabelStyle(minimumAuthor);

        VBox rightSide = new VBox(30, text, maximumAuthor, minimumAuthor);
        rightSide.setStyle("-fx-background-color: linear-gradient(to bottom, #f0f0f0, #ffffff); " +
                "-fx-pref-width: 450; -fx-border-color: #ddd;");
        rightSide.setAlignment(Pos.TOP_CENTER);
        rightSide.setPadding(new Insets(20));
        return rightSide;
    }

    public VBox center() { // a method to arrange the center components of the statistics page
        Label text = new Label("Books Statistics");
        text.setAlignment(Pos.TOP_CENTER);
        text.setStyle("-fx-text-fill: #444; -fx-font-size: 42px; -fx-font-weight: bold;");
        ObservableList<String> category = FXCollections.observableArrayList();

        for (Book book : books) {
            if (!category.contains(book.getCategory()))
                category.add(book.getCategory());
        }

        comboCategory = new ComboBox<>(category);
        comboCategory.setValue(books.get(0).getCategory());
        Label l1Category = new Label("Books with ");
        l2Category = new Label("are " + countCategory(comboCategory.getValue()));
        comboCategory.setOnAction(e -> l2Category.setText("are " + countCategory(comboCategory.getValue())));
        setLabelStyle(l1Category);
        setLabelStyle(l2Category);

        HBox cat = new HBox(5, l1Category, comboCategory, l2Category);
        cat.setAlignment(Pos.CENTER);
        ObservableList<String> author = FXCollections.observableArrayList();

        for (Book book : books) {
            if (!author.contains(book.getAuthor()))
                author.add(book.getAuthor());
        }

        comboAuthor = new ComboBox<>(author);
        comboAuthor.setValue(books.get(0).getAuthor());
        Label l1Author = new Label("Books published by ");
        l2Author = new Label("are " + countAuthor(comboAuthor.getValue()));
        comboAuthor.setOnAction(e -> l2Author.setText("are " + countAuthor(comboAuthor.getValue())));
        setLabelStyle(l1Author);
        setLabelStyle(l2Author);
        HBox auth = new HBox(5, l1Author, comboAuthor, l2Author);
        auth.setAlignment(Pos.CENTER);
        ObservableList<String> year = FXCollections.observableArrayList();

        for (Book book : books) {
            if (!year.contains(book.getPublishedYear()))
                year.add(book.getPublishedYear());
        }

        comboYear = new ComboBox<>(year);
        comboYear.setValue(books.get(0).getPublishedYear());
        Label l1Year = new Label("Books published in ");
        l2Year = new Label("are " + countYear(comboYear.getValue()));
        comboYear.setOnAction(e -> l2Year.setText("are " + countYear(comboYear.getValue())));
        setLabelStyle(l1Year);
        setLabelStyle(l2Year);
        HBox yea = new HBox(5, l1Year, comboYear, l2Year);
        yea.setAlignment(Pos.CENTER);
        VBox center = new VBox(30, text, cat, auth, yea);
        center.setAlignment(Pos.TOP_CENTER);
        center.setPadding(new Insets(20));
        return center;
    }

    public BorderPane main() { // a method to assemble the statistics page components together
        tabPane.getSelectionModel().select(statistics);
        BorderPane main = new BorderPane();
        main.setTop(tabPane);

        if (books.isEmpty()) {
            Label label = new Label("There are no statistics");
            label.setFont(new Font(80));
            main.setCenter(label);

        } else {
            main.setLeft(leftSide());
            main.setRight(rightSide());
            main.setCenter(center());
        }

        main.setStyle("-fx-background-color: #fafafa;");
        return main;
    }

    public void yearStatistics() { // a method to compute year statistics
        Comparator<Book> compareByYear = new Comparator<>() {
            public int compare(Book b1, Book b2) {
                return Integer.parseInt(b1.getPublishedYear()) - Integer.parseInt(b2.getPublishedYear());
            }
        };

        java.util.Collections.sort(books, compareByYear);
        int currentYear = Integer.parseInt(books.get(0).getPublishedYear());
        int counter = 0;
        maxYear = currentYear;
        minYear = currentYear;
        max = 0;
        min = Integer.MAX_VALUE;
        for (Book book : books) {
            if (Integer.parseInt(book.getPublishedYear()) == currentYear) {
                counter++;

            } else {
                if (counter > max) {
                    max = counter;
                    maxYear = currentYear;
                }
                if (counter < min) {
                    min = counter;
                    minYear = currentYear;
                }
                currentYear = Integer.parseInt(book.getPublishedYear());
                counter = 1;
            }
        }
        if (counter > max) {
            max = counter;
            maxYear = currentYear;
        }
        if (counter < min) {
            min = counter;
            minYear = currentYear;
        }
    }

    public void authorStatistics() { // a method to compute authors statistics
        Comparator<Book> compareByAuthor = new Comparator<>() {
            public int compare(Book b1, Book b2) {
                return b1.getAuthor().compareTo(b2.getAuthor());
            }
        };

        java.util.Collections.sort(books, compareByAuthor);
        String currentAuthor = books.get(0).getAuthor();
        int counter = 0;
        maxAuthor = currentAuthor;
        minAuthor = currentAuthor;
        maxBooks = 0;
        minBooks = Integer.MAX_VALUE;

        for (Book book : books) {
            if (book.getAuthor().equals(currentAuthor)) {
                counter++;

            } else {
                if (counter > maxBooks) {
                    maxBooks = counter;
                    maxAuthor = currentAuthor;
                }
                if (counter < minBooks) {
                    minBooks = counter;
                    minAuthor = currentAuthor;
                }
                currentAuthor = book.getAuthor();
                counter = 1;
            }
        }
        if (counter > maxBooks) {
            maxBooks = counter;
            maxAuthor = currentAuthor;
        }
        if (counter < minBooks) {
            minBooks = counter;
            minAuthor = currentAuthor;
        }
    }

    public int countCategory(String category) { // a method to calculate category statistics
        int counter = 0;
        for (Book book : books) {
            if (category.equals(book.getCategory())) counter++;
        }
        return counter;
    }

    public int countAuthor(String author) { // a method to calculate author statistics
        int counter = 0;
        for (Book book : books) {
            if (author.equals(book.getAuthor())) counter++;
        }
        return counter;
    }

    public int countYear(String year) { // a method to calculate year statistics
        int counter = 0;
        for (Book book : books) {
            if (year.equals(book.getPublishedYear())) counter++;
        }
        return counter;
    }

    public void actions() { // a method to set tab action
        main.setOnSelectionChanged(e -> Main.changeScene(new MainInterFace(stage).main()));
    }
}
