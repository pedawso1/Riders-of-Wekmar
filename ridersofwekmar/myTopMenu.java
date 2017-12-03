package ridersofwekmar;

import java.io.File;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//myTopMenu is an object to add file options through drop down menus
public class myTopMenu {

	MenuBar menuBar = new MenuBar();
	ArrayList<Node> nodes = new ArrayList<Node>();
	TextBoxClass textBox;
	String addingToFile = "";
        mySidePanel sidePanel;

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public MenuBar addMenuBar(Stage primary, mySidePanel sp) {
                sidePanel = sp;
                Pane centerPane = sidePanel.getCenterPane();
		Menu menuFile = new javafx.scene.control.Menu("File");
		MenuItem newFile = new MenuItem("New");
		MenuItem open = new MenuItem("Open");
		MenuItem save = new MenuItem("Save");

		//creates a new stage/window to make a new file 
		newFile.setOnAction((ActionEvent event) -> {
			Stage secondStage = new Stage();
			BorderPane border = new BorderPane();
			Pane centerPane2 = new Pane();
			centerPane2.getStyleClass().add("centerPane");
			mySidePanel sidePanel2 = new mySidePanel(centerPane2);
			myTopMenu bar = new myTopMenu();

			// layout of editor
			border.setLeft(sidePanel2.addSidePanel());
                        border.setTop(bar.addMenuBar(secondStage, sidePanel2));
			border.setCenter(centerPane2);

			Scene secondScene = new Scene(border, 800, 800);
			secondStage.setTitle("Riders of Wekmar Editor");
			secondStage.setScene(secondScene);
			secondScene.getStylesheets().add(RidersOfWekmar.class.getResource("application.css").toExternalForm());

			secondStage.show();
		});

		//saves in a text file
		save.setOnAction(new EventHandler<ActionEvent>() {
			// We have a functional save aspect that will store node type and location to a
			// text file, load function is planned but not functional
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
					SaveFile(addingToFile, file);
				}
				addingToFile = "";
			}
		});

		// exiting the program
		MenuItem exit = new MenuItem("Exit", null);
		exit.setOnAction(actionEvent -> Platform.exit());

		// what is in the file tab
		menuFile.getItems().addAll(newFile, open, save, exit);

		// edit should contain the undos, redos and clear
		Menu menuEdit = new javafx.scene.control.Menu("Edit");
                MenuItem undo = new MenuItem("Undo");
                undo.setOnAction((ActionEvent e) -> 
                {
                    sidePanel.fireUndoBtn();
                });
                
                MenuItem redo = new MenuItem("Redo");
                redo.setOnAction((ActionEvent e) -> 
                {
                    sidePanel.fireRedoBtn();
                });
                
                
                menuEdit.getItems().addAll(undo, redo);

		// gives an information about the program
		Menu menuHelp = new javafx.scene.control.Menu("About");
		MenuItem about = new MenuItem("Welcome");
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("About");
		alert.setHeaderText("About The Riders Of Wekmar Editor");
		String s = "This program is created to produce uml diagrams. There are resizale textboxes with three sections"
				+ " name the class, instance variables and attributes. User has a choice of different lines to pick from to connect textboxes. "
				+ "Text button adds text any where in the working space, when text is double tapped it can be can be edited."
				+ "\n\n"
				+ "The main purpose to create the program was for a Software Engineering course by Ashley Camacho, Kailash Sayal, Kyle Marten, Peter Dawson and Samuel Aungst."
				+ "The Students attend Millersville University.";
		alert.setContentText(s);
		about.setOnAction(actionEvent -> alert.show());
		menuHelp.getItems().addAll(about);

		menuBar.getMenus().addAll(menuFile, menuEdit, /* menuView, */ menuHelp);

		return menuBar;

	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	private void SaveFile(String content, File file) {
		try {
			FileWriter fileWriter = null;

			fileWriter = new FileWriter(file);
			fileWriter.write(content);
			fileWriter.close();
		} catch (IOException ex) {
			Logger.getLogger(myTopMenu.class.getName()).log(Level.SEVERE, null, ex);
		}

	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Fuction to return Arraylist of only top left corner of nodes
	// intended use is for snapping Lines to text boxes within a predetermined
	// proximity, currently not used
	public static ArrayList<Double> getMinBounds(Node node) {
		Bounds bounds;
		ArrayList<Double> list = new ArrayList<Double>();
		bounds = node.localToParent(node.getBoundsInLocal());
		list.add(bounds.getMinX());
		list.add(bounds.getMinY());
		return list;
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Returns the bounds for all nodes in scene, returning both the min
	// and max X and Y coordinates too record both sides of lines and
	// also because we can resize our text boxes we need the diagonal coordinates
	// to determine location and size for save
	public static ArrayList<Double> getBounds(Node node) {
		Bounds bounds;
		ArrayList<Double> list = new ArrayList<Double>();
		bounds = node.localToParent(node.getBoundsInLocal());
		list.add(bounds.getMinX());
		list.add(bounds.getMinY());
		list.add(bounds.getMaxX());
		list.add(bounds.getMaxY());
		return list;
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Returns all nodes from "root" and descendents
	public static ArrayList<Node> getAllNodes(Parent root) {
		ArrayList<Node> nodes = new ArrayList<Node>();
		addAllDescendents(root, nodes);
		return nodes;
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Returns all descendents from parent node
	private static void addAllDescendents(Parent parent, ArrayList<Node> nodes) {
		for (Node node : parent.getChildrenUnmodifiable()) {
			nodes.add(node);
		}
	}

}
