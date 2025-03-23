package library;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

import static library.Main.books;

public abstract class BaseDesign { // this class contains the common components between add scene, edit scene, and remove scene
    // declare the common components
    protected Label lBookId, lTitle, lAuthor, lCategory, lPublishedYear, lISBN, text;
    protected TextField bookId, title, author, category, publishedYear, ISBN;
    protected Button back;
    protected BorderPane main;
    public BaseDesign() { //initialize the common components
        lBookId = new Label("Book id:");
        lTitle = new Label("Title:");
        lAuthor = new Label("Author:");
        lCategory = new Label("Category:");
        lPublishedYear = new Label("Published Year:");
        lISBN = new Label("ISBN:");
        text = new Label();
        setLabelStyle(lBookId);
        setLabelStyle(lTitle);
        setLabelStyle(lAuthor);
        setLabelStyle(lCategory);
        setLabelStyle(lPublishedYear);
        setLabelStyle(lISBN);

        bookId = new TextField();
        title = new TextField();
        author = new TextField();
        category = new TextField();
        publishedYear = new TextField();
        ISBN = new TextField();

        setTextFieldStyle(bookId);
        setTextFieldStyle(title);
        setTextFieldStyle(author);
        setTextFieldStyle(category);
        setTextFieldStyle(publishedYear);
        setTextFieldStyle(ISBN);

        bookId.setText(String.valueOf(books.size() + 1000));
        bookId.setEditable(false);
        bookId.setStyle("-fx-text-fill: #AFAEB1; -fx-border-width: 1; -fx-background-color: white;");

        back = new Button("Back");
        MainInterFace.setButtonsStyle(back);

        GridPane left = new GridPane();
        left.setHgap(10);
        left.setVgap(20);
        left.addColumn(0, lBookId, lTitle, lAuthor, lCategory, lPublishedYear, lISBN);
        left.addColumn(1, bookId, title, author, category, publishedYear, ISBN);
        left.setPadding(new Insets(20));

        main = new BorderPane();
        main.setLeft(left);
    }

    // getters for components
    public TextField getBookId() {
        return bookId;
    }

    public TextField getTitle() {
        return title;
    }

    public TextField getAuthor() {
        return author;
    }

    public TextField getCategory() {
        return category;
    }

    public TextField getPublishedYear() {
        return publishedYear;
    }

    public TextField getISBN() {
        return ISBN;
    }

    public void setLabelStyle(Label label) { // a method to style labels
        label.setStyle("-fx-text-fill: #333333; -fx-font-size: 20px;");
    }

    public static void setTextFieldStyle(TextField txt) { // a method to style text fields
        txt.setPrefWidth(200);
        txt.setStyle("-fx-text-fill: black; -fx-background-color: white;");
    }
}

