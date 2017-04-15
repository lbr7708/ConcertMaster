package team_f.client.gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import team_f.client.configuration.Configuration;
import team_f.client.controls.sidebar.MenuSection;
import team_f.client.controls.sidebar.MenuSectionItem;
import team_f.client.controls.sidebar.Sidebar;
import team_f.client.singletons.BrowserSingleton;
import team_f.client.singletons.HomeScreenSingleton;

public class NavigationBar {
    public static Sidebar getNavigationBar(BorderPane pane, Configuration configuration) {
        Sidebar sidebar = new Sidebar();
        MenuSection menuSection;
        MenuSectionItem menuSectionItem;
        ToggleGroup toggleGroup = new ToggleGroup();
        sidebar.setStyle("-fx-background-color:   #e0e0d1");

        menuSection = new MenuSection("Home", "/homeM.png", null);
        menuSection.setAnimated(false);
        menuSection.setCollapsible(false);
        menuSection.setOnMouseClicked(event -> pane.setCenter(HomeScreenSingleton.getInstance()));
        sidebar.add(menuSection);

        menuSection = new MenuSection("Service Schedule", "/calendarM.png", toggleGroup);
        menuSectionItem = new MenuSectionItem("Show Schedules");
        menuSectionItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                BrowserSingleton.getInstance().getBrowser().loadURL("http://localhost:8080/Calendar");
                pane.setCenter(BrowserSingleton.getInstance());
            }
        });
        menuSection.add(menuSectionItem);
        menuSectionItem = new MenuSectionItem("Schedule Management");
        menuSection.add(menuSectionItem);
        sidebar.add(menuSection);

        menuSection = new MenuSection("Services", "/dutyM.png", toggleGroup);
        menuSectionItem = new MenuSectionItem("Service Schedules");
        menuSection.add(menuSectionItem);
        menuSectionItem = new MenuSectionItem("Service Management");
        menuSection.add(menuSectionItem);
        sidebar.add(menuSection);

        menuSection = new MenuSection("Musician", "/orchestraiconM.png", toggleGroup);
        menuSectionItem = new MenuSectionItem("Musician Management");
        menuSection.add(menuSectionItem);
        menuSectionItem = new MenuSectionItem("Musician List");
        menuSection.add(menuSectionItem);
        sidebar.add(menuSection);

        menuSection = new MenuSection("Compositions", "/musicfolderM.png", toggleGroup);
        sidebar.add(menuSection);

        menuSection = new MenuSection("Inventory", "/inventaryM.png", toggleGroup);
        menuSectionItem = new MenuSectionItem("Show Inventory");
        menuSection.add(menuSectionItem);
        menuSectionItem = new MenuSectionItem("Add Item");
        menuSection.add(menuSectionItem);
        sidebar.add(menuSection);

        menuSection = new MenuSection("User", "/userM.png", toggleGroup);
        menuSectionItem = new MenuSectionItem("Section Management");
        menuSection.add(menuSectionItem);
        menuSectionItem = new MenuSectionItem("Musician Management");
        menuSection.add(menuSectionItem);
        sidebar.add(menuSection);

        menuSection = new MenuSection("Administration", "/settingsM.png", toggleGroup);
        menuSectionItem = new MenuSectionItem("Section Management");
        menuSection.add(menuSectionItem);
        menuSectionItem = new MenuSectionItem("Musician Management");
        menuSection.add(menuSectionItem);
        sidebar.add(menuSection);

        return sidebar;
    }
}
