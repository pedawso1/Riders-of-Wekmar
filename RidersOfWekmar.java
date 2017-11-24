package RidersOfWekmar;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

/**
 * The Riders of Wekmar UML Editor implements JavaFX panes and nodes to present
 * a graphical user interface that may be used for creating and designing UML
 * class diagrams.
 * 
 * @author Riders of Wekmar - Ashley Camacho, Kailash Sayal, Peter Dawson, Sam Aungst, Kyle Marten
 * @version 2.0
 */

public class RidersOfWekmar extends Application 
{

    /**
     * Utilization of launch arguments is not implemented in this version of the
     * Riders of Wekmar UML Editor.
     * @param args 
     */
    public static void main(String[] args) 
    {
        launch(args);
    }

    /**
     * Override start() to set up the main sections of the interface, including 
     * the menu bar, side panel, and center pane. The desired layout of these
     * sections is achieved by adding them to a BorderPane.
     * @param primaryStage 
     */
    @Override  
    public void start(Stage primaryStage) 
    {
        primaryStage.setTitle("Riders of Wekmar Editor");

        //creating objects
        BorderPane border = new BorderPane();
        Pane centerPane = new Pane();
        centerPane.getStyleClass().add("centerPane");
        mySidePanel sidePanel = new mySidePanel(centerPane);
        myTopMenu bar = new myTopMenu();
      
        //layout of editor
        border.setLeft(sidePanel.addSidePanel());
        border.setTop(bar.addMenuBar(primaryStage, sidePanel));
  
        border.setCenter(centerPane);

        Scene scene = new Scene(border, 800, 800);
        primaryStage.setScene(scene);
        scene.getStylesheets().add(RidersOfWekmar.class.getResource("application.css").toExternalForm());
        primaryStage.show();
    }
}
