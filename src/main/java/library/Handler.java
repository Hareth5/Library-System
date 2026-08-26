package library;

import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

import static library.Main.books;

public class Handler {
    protected BaseDesign action;
    public Handler(BaseDesign action) {
        this.action = action;
    }

    public void handler(String read[]) { // to handle books addition or books updating
        String[] arr;
        if (read != null)
            arr = read; // to handle books read from file
        else // get books information from text fields
            arr = new String[]{action.getBookId().getText().trim(), action.getTitle().getText().trim(), action.getAuthor().getText().trim(),
                    action.getCategory().getText().trim(), action.getPublishedYear().getText().trim(), action.getISBN().getText().trim()};

        try {
            // crete new book
            Book book = new Book(
                    new SimpleStringProperty(arr[0]), new SimpleStringProperty(arr[1]), new SimpleStringProperty(arr[2]),
                    new SimpleStringProperty(arr[3]), new SimpleStringProperty(arr[4]), new SimpleStringProperty(arr[5]));
            if (action instanceof Add) {
                if (isISBNExist(arr[5])) {
                    books.add(book);

                    if (read == null) {
                        alert("Success", "Book added successfully", Alert.AlertType.CONFIRMATION);
                        ((Add) action).setEmpty();
                    }
                    BaseView.bookList.setAll(books);
                } else {
                    if (read == null)
                        alert("Information", "This book is already exist", Alert.AlertType.INFORMATION);
                }
                action.getBookId().setText(String.valueOf(books.size() + 1000));

            } else {
                int index = Integer.parseInt(arr[0]) - 1000;
                books.set(index, book);
                BaseView.bookList.setAll(books);
                alert("Success", "Book updated successfully", Alert.AlertType.CONFIRMATION);
            }

        } catch (NullException | InvalidException | IllegalArgumentException e) {
            alert("Error", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    public boolean isISBNExist(String ISBN) { // a method to check if ISBN exist or not, to avoid book repetition
        int size = Main.books.size();
        for (int i = 0; i < size; i++) {
            if (ISBN.equals(Main.books.get(i).getISBN()))
                return false;
        }
        return true;
    }

    public static boolean alert(String head, String message, Alert.AlertType type) { // a method to show alerts for hole program
        Alert alert = new Alert(type);
        alert.setTitle(head);
        alert.setContentText(message);
        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;
    }
}