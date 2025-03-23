package library;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

import static library.Main.changeScene;

public class MainMenu {
    // declare menu bar components
    protected Menu book, statistics, close;
    protected MenuItem add, view, read, manage, statisticsPage, exit;
    protected MenuBar menuBar;
    private Stage stage;

    public MainMenu(Stage stage) { //initialize menu bar components
        this.stage = stage;
        add = new MenuItem("Add");
        read = new MenuItem("Read");
        view = new MenuItem("View");
        manage = new MenuItem("Manage");
        statisticsPage = new MenuItem("Statistics Page");
        exit = new MenuItem("Exit");

        book = new Menu("Book");
        book.getItems().addAll(add, read, view, manage);

        statistics = new Menu("Statistics");
        statistics.getItems().add(statisticsPage);

        close = new Menu("Close");
        close.getItems().add(exit);

        menuBar = new MenuBar(book, statistics, close);

        actions();
    }

    public MenuBar mainMenuBar() {
        return menuBar;
    }

    public void actions() { // a method fot tabs actions
        add.setOnAction(e -> changeScene(new Add(stage).main()));

        read.setOnAction(e -> new MainInterFace(stage).getRead().fire());

        view.setOnAction(e -> changeScene(new View(stage, true, true).main()));

        manage.setOnAction(e -> changeScene(new Manage(stage, true, true).main()));

        statisticsPage.setOnAction(e -> changeScene(new Statistics(stage).main()));

        close.setOnAction(e -> stage.close());
    }
}