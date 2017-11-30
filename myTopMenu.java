package RidersOfWekmar;

import java.io.File;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.function.UnaryOperator;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
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
                
                MenuItem settings = new MenuItem("Settings");
                settings.setOnAction((ActionEvent e) -> 
                {
                    Insets insets = new Insets(15, 10, 10, 10);
                    LineDrawer lineDrawer = sidePanel.getLineDrawer();
                    Stage settingsStage = new Stage();
                    settingsStage.setTitle("Settings");
                    VBox basePane = new VBox();
                    GridPane gridPane = new GridPane();                     
                    //gridPane.setPadding(new Insets(10, 10, 10, 10));
                    gridPane.setPadding(insets);
                    Scene settingsScene = new Scene(basePane, 216, 125);
                    settingsStage.setScene(settingsScene);              
                    
                    Text lineWidthText = new Text("Line width: ");  
                    TextField lineWidthField = new TextField();   
                    lineWidthField.setAlignment(Pos.CENTER_RIGHT);
                    lineWidthField.setText(String.valueOf(lineDrawer.getStrokeWidth()));                    

                    UnaryOperator<TextFormatter.Change> doubleFilter = new UnaryOperator<TextFormatter.Change>() {
                        @Override
                        public TextFormatter.Change apply(TextFormatter.Change c) 
                        {
                            //Ensure added characters include one decimal and numeric characters only
                            if (c.isAdded()) {
                                if (c.getControlText().contains(".")) {
                                    if (c.getText().matches("[^0-9]")) {
                                        c.setText("");
                                    }
                                } else if (c.getText().matches("[^0-9.]")) {
                                    c.setText("");
                                }
                            }
                            
                            //Ensure text is not replaced by nonnumeric characters
                            if (c.isReplaced())
                            {
                                if(c.getText().matches("[^0-9]"))
                                {c.setText(c.getControlText().substring(c.getRangeStart(), c.getRangeEnd()));}
                            } 
                            
                            return c;
                        }
                    };
                    
                    TextFormatter doubleFormatter = new TextFormatter<>(doubleFilter);
                    
                    lineWidthField.setTextFormatter(doubleFormatter);

                    //lineWidthField.setTextFormatter();
                    
                    Text testText = new Text("Test Text: ");
                    TextField testField = new TextField();
                    
                    VBox texts = new VBox(5);
                    VBox textFields = new VBox(5);
                    
                    texts.setAlignment(Pos.CENTER);
                    //texts.setPrefWidth(100);
                    textFields.setAlignment(Pos.CENTER);
                    textFields.setMaxWidth(50);
                    texts.getChildren().addAll(lineWidthText, testText);
                    textFields.getChildren().addAll(lineWidthField, testField);                    
                    gridPane.add(texts, 0,0);
                    gridPane.add(textFields, 1, 0);                    
                    ColumnConstraints column1 = new ColumnConstraints();
                    column1.setPercentWidth(50);
                    ColumnConstraints column2 = new ColumnConstraints();
                    column2.setPercentWidth(50);
                    gridPane.getColumnConstraints().addAll(column1, column2);
                    //gridPane.setGridLinesVisible(true);                    
                    Button OK = new Button("OK");
                    Button Cancel = new Button("Cancel");                    
                    HBox buttons = new HBox(10);
                    buttons.setAlignment(Pos.CENTER);                    
                    buttons.setPrefWidth(100);
                    buttons.getChildren().addAll(OK, Cancel);
                    basePane.getChildren().addAll(gridPane, buttons);
                    
                    OK.setOnAction((ActionEvent f) -> 
                    {
                        lineDrawer.setStrokeWidth(Double.parseDouble(lineWidthField.getText()));
                        settingsStage.hide();
                    });
                    Cancel.setOnAction((ActionEvent f) ->
                    {
                        settingsStage.hide();
                    });
                    
                    settingsStage.show();
                });
                
                menuEdit.getItems().addAll(undo, redo, settings);

		// gives an information about the program
		Menu menuHelp = new javafx.scene.control.Menu("About");
		MenuItem about = new MenuItem("Welcome");
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("About");
		alert.setHeaderText("About The Riders Of Wekmar Editor");
		String s = "This program is created to produce uml diagrams. There are resizale textboxes with three sections"
				+ " name the class, instance variables and attributes. User has a choice of different lines to pick from to connect textboxes."
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
