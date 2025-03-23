package library;

import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

import static library.MainInterFace.setTabStyle;

public class BaseTabs { // main interface tabs
    protected TabPane tabPane;
    protected Tab main, statistics;

    public BaseTabs() { // initialize tabs
        main = new Tab("Book");
        statistics = new Tab("Statistics");
        setTabStyle(main);
        setTabStyle(statistics);
        tabPane = new TabPane(main, statistics);
        tabPane.getSelectionModel().select(main);
    }
}

