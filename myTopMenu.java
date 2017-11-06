package RidersOfWekmar;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

//myTopMenu is an object to add file options through drop down menus
//currently none of the drop down options are functional, will be implemented for iteration 2
public class myTopMenu {

    MenuBar menuBar = new MenuBar();
    ArrayList<Node> nodes = new ArrayList<Node>();
    TextBoxClass textBox;
    String addingToFile = "";
    
    
    public MenuBar addMenuBar(Stage primary, Pane centerPane) {    
        javafx.scene.control.Menu menuFile = new javafx.scene.control.Menu("File");
        MenuItem newFile = new MenuItem("New");
        MenuItem open = new MenuItem("Open");
        MenuItem save = new MenuItem("Save");
        
        save.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                FileChooser fc = new FileChooser();
                fc.setTitle("Save Text");
                nodes = getAllNodes(centerPane);
                for (int i = 0; i < nodes.size(); i++) {
                	ArrayList<Double> list = new ArrayList<Double>();
                	list = getBounds(nodes.get(i));
                	System.out.println(i + " " + nodes.get(i).getId() + " " + list);
                	addingToFile = addingToFile + (i + " " + nodes.get(i).getId() + " " + list).toString() + "\n";
                	
                }
                FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
                fc.getExtensionFilters().add(extFilter);
                File file = fc.showSaveDialog(primary);
                if (file != null) {
                    SaveFile(addingToFile , file);
                }
                addingToFile = "";
            }
        });  
                
                
        MenuItem exit = new MenuItem("Exit");
        menuFile.getItems().addAll(newFile, open, save, exit);

        javafx.scene.control.Menu menuEdit = new javafx.scene.control.Menu("Edit");
        javafx.scene.control.Menu menuView = new javafx.scene.control.Menu("View");
        javafx.scene.control.Menu menuHelp = new javafx.scene.control.Menu("Help");

        menuBar.getMenus().addAll(menuFile, menuEdit, menuView, menuHelp);

        return menuBar;
           
    }
    
    private void SaveFile(String content, File file){
        try {
            FileWriter fileWriter = null;
             
            fileWriter = new FileWriter(file);
            fileWriter.write(content);
            fileWriter.close();
        } catch (IOException ex) {
            Logger.getLogger(myTopMenu.class.getName()).log(Level.SEVERE, null, ex);
        }
         
    }
    public static ArrayList<Double> getBounds(Node node) {
    	Bounds bounds;
    	ArrayList<Double> list = new ArrayList<Double>();
    	bounds = node.localToParent(node.getBoundsInLocal());
    	list.add(bounds.getMinX());
    	list.add(bounds.getMaxX());
    	list.add(bounds.getMinY());
    	list.add(bounds.getMaxY());
    	return list;
    }
    
    public static ArrayList<Node> getAllNodes(Parent root) {
        ArrayList<Node> nodes = new ArrayList<Node>();
        addAllDescendents(root, nodes);
        return nodes;
    }

    
    private static void addAllDescendents(Parent parent, ArrayList<Node> nodes) {
        for (Node node : parent.getChildrenUnmodifiable()) {
            nodes.add(node);
            //if (node instanceof Parent)
            //    addAllDescendents((Parent)node, nodes);
        }        
    }
           
}
