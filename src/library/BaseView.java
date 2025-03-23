package library;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import static library.Main.*;
import static library.MainInterFace.setTabStyle;

public abstract class BaseView { // this class contains the common components between view scene, and manage scene
    protected TabPane tabPane;
    protected Tab view, manage;
    protected Button back, hide, searchBtn;
    protected ComboBox<String> sort, search;
    protected TableView tableView;
    protected boolean hideButton, searchButton;
    protected Stage stage;
    public static final ObservableList<Book> bookList = FXCollections.observableArrayList(books);

    public BaseView(Stage stage, boolean hideButton, boolean searchButton) { //initialize the common components
        this.hideButton = hideButton;
        this.searchButton = searchButton;
        this.stage = stage;
        view = new Tab("View");
        manage = new Tab("Manage");
        setTabStyle(view);
        setTabStyle(manage);
        tabPane = new TabPane(view, manage);

        back = new Button("Back");
        MainInterFace.setButtonsStyle(back);

        if (hideButton)
            hide = new Button("Hide inactive authors");
        else
            hide = new Button("Show all authors");

        if (searchButton)
            searchBtn = new Button("Search");
        else
            searchBtn = new Button("Cancel Search");

        MainInterFace.setButtonsStyle(hide);
        searchBtn.setPrefWidth(200);
        searchBtn.setPrefHeight(30);
        searchBtn.setStyle("-fx-font-size: 15px; -fx-text-fill: #333333; -fx-background-color: #2AB7F4;");

        sort = new ComboBox<>();
        sort.getItems().addAll("Sort by ID", "Sort by Title", "Sort by Author", "Sort by Published Year");
        sort.setValue("Sort Type");

        search = new ComboBox<>();
        search.getItems().addAll("Search by ID", "Search by Title", "Search by Author");
        search.setValue("Search Type");

        tableView = new TableView();

        actions();
    }

    public void setRadioButtonStyle(RadioButton radioButton) { // a method to style radio buttons
        radioButton.setStyle("-fx-font-size: 15px; -fx-text-fill: #333333;");
    }

    public void setLabelStyle(Label label) { // a method to style labels
        label.setStyle("-fx-text-fill: #333333; -fx-font-size: 30px;");
    }

    public TableView setTableView() { //initialize the table view
        TableColumn<Book, SimpleStringProperty> id = new TableColumn<>("Book id");
        TableColumn<Book, SimpleStringProperty> title = new TableColumn<>("Title");
        TableColumn<Book, SimpleStringProperty> author = new TableColumn<>("Author");
        TableColumn<Book, SimpleStringProperty> category = new TableColumn<>("Category");
        TableColumn<Book, SimpleStringProperty> publishedYear = new TableColumn<>("Published Year");
        TableColumn<Book, SimpleStringProperty> ISBN = new TableColumn<>("ISBN");

        id.setCellValueFactory(new PropertyValueFactory<>("bookId"));
        title.setCellValueFactory(new PropertyValueFactory<>("title"));
        author.setCellValueFactory(new PropertyValueFactory<>("author"));
        category.setCellValueFactory(new PropertyValueFactory<>("category"));
        publishedYear.setCellValueFactory(new PropertyValueFactory<>("publishedYear"));
        ISBN.setCellValueFactory(new PropertyValueFactory<>("ISBN"));

        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tableView.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: #DDDDDD;");

        tableView.getColumns().addAll(id, title, author, category, publishedYear, ISBN);
        tableView.setItems(bookList);
        return tableView;
    }

    protected void restoreInactiveAuthors() { // a method to restore inactive authors from hide list and cancel the hide button
        hide.setText("Hide inactive authors");
        hideButton = true;
        search.getItems().setAll("Search by ID", "Search by Title", "Search by Author");
        books.addAll(hideList);
        hideList.clear();
        java.util.Collections.sort(books);
        bookList.setAll(books);
    }

    protected void showSearch() { // a method to cancel the search  button
        searchBtn.setText("Search");
        searchButton = true;
        books.clear();
        books.addAll(searchList);
        searchList.clear();
        bookList.setAll(books);
    }

    public void actions() { // a method for back button action
        back.setOnAction(e -> {
            if (!hideButton)
            showSearch();
            if (!searchButton)
            restoreInactiveAuthors();
            Main.changeScene(new MainInterFace(stage).main());
        });
    }
}
