package library;

import javafx.beans.property.SimpleStringProperty;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class Book implements Comparable<Book> {
    private SimpleStringProperty bookId, title, author, category, publishedYear, ISBN;

    public Book() { // no arg constructor to initialize the values
        bookId = new SimpleStringProperty();
        title = new SimpleStringProperty();
        author = new SimpleStringProperty();
        category = new SimpleStringProperty();
        publishedYear = new SimpleStringProperty();
        ISBN = new SimpleStringProperty();
    }

    // arg constructor
    public Book(SimpleStringProperty bookId, SimpleStringProperty title, SimpleStringProperty author, SimpleStringProperty category, SimpleStringProperty publishedYear, SimpleStringProperty ISBN) throws InvalidException, NullException,IllegalArgumentException {
        this();
        this.bookId = bookId;
        setTitle(title.get());
        setAuthor(author.get());
        setCategory(category.get());
        setPublishedYear(publishedYear.get());
        setISBN(ISBN.get());
    }

    // setters and getters
    public String getBookId() {
        return bookId.get();
    }

    public SimpleStringProperty bookIdProperty() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId.set(bookId);
    }

    public String getTitle() {
        return title.get();
    }

    public SimpleStringProperty titleProperty() {
        return title;
    }

    public void setTitle(String title) throws NullException, InvalidException {
        if (isNull(title))
            throw new NullException("Title cannot be empty");

        if (!title.matches("^[\\w\\W]+$"))
            throw new InvalidException("Title cannot be empty");

        this.title.set(title);
    }

    public String getAuthor() {
        return author.get();
    }

    public SimpleStringProperty authorProperty() {
        return author;
    }

    public void setAuthor(String author) throws NullException, InvalidException {
        if (isNull(author))
            throw new NullException("Author cannot be empty");

        if (!author.matches("^[a-zA-Z ]+$"))
            throw new InvalidException("Author must contain only letters");

        this.author.set(author);
    }

    public String getCategory() {
        return category.get();
    }

    public SimpleStringProperty categoryProperty() {
        return category;
    }

    public void setCategory(String category) throws NullException, InvalidException {
        if (isNull(category))
            throw new NullException("Category cannot be empty");

        if (!category.matches("^[a-zA-Z ]+$"))
            throw new InvalidException("Category must contain only letters");

        this.category.set(category);
    }

    public String getPublishedYear() {
        return publishedYear.get();
    }

    public SimpleStringProperty publishedYearProperty() {
        return publishedYear;
    }

    public void setPublishedYear(String publishedYear) throws NullException, InvalidException {
        if (isNull(publishedYear))
            throw new NullException("Published Year cannot be empty");

        publishedYear = publishedYear.trim();

        if (!publishedYear.matches("^[0-9]+$"))
            throw new InvalidException("Published Year must contain only numbers");

        if (Integer.parseInt(publishedYear) > new GregorianCalendar().get(Calendar.YEAR))
            throw new IllegalArgumentException("Published Year cannot be greater than this year");

        this.publishedYear.set(publishedYear);
    }

    public String getISBN() {
        return ISBN.get();
    }

    public SimpleStringProperty ISBNProperty() {
        return ISBN;
    }

    public void setISBN(String ISBN) throws NullException, InvalidException {
        if (isNull(ISBN))
            throw new NullException("ISBN cannot be empty");

        if (!ISBN.matches("^[0-9-]+$"))
            throw new InvalidException("ISBN must contain only numbers and hyphen (-)");

        this.ISBN.set(ISBN);
    }
    public boolean isNull(String text) {
        return text.isEmpty();
    }

    @Override
    public int compareTo(Book o) {
        return Integer.parseInt(this.getBookId()) - Integer.parseInt(o.getBookId());
    }
}
