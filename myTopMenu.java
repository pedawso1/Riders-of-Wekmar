package application;

import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

public class myTopMenu {

    MenuBar menuBar = new MenuBar();
    
     public MenuBar addMenuBar() {
            
            javafx.scene.control.Menu menuFile = new javafx.scene.control.Menu("File");
            MenuItem newFile = new MenuItem("New");
            MenuItem open = new MenuItem("Open");
            MenuItem save = new MenuItem("Save");
            MenuItem exit = new MenuItem("Exit");
            menuFile.getItems().addAll(newFile, open, save, exit);

            javafx.scene.control.Menu menuEdit = new javafx.scene.control.Menu("Edit");
            javafx.scene.control.Menu menuView = new javafx.scene.control.Menu("View");
            javafx.scene.control.Menu menuHelp = new javafx.scene.control.Menu("Help");
            
            menuBar.getMenus().addAll(menuFile, menuEdit, menuView, menuHelp);
            
            return menuBar;
            
        }
}
