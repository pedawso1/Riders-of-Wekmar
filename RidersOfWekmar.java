package application;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

public class RidersOfWekmar extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Riders of Wekmar Editor");

        //--- layout of panel
        BorderPane border = new BorderPane();
        mySidePanel sidePanel = new mySidePanel();
        myTopMenu bar = new myTopMenu();
        snapToGrid snap = new snapToGrid();

        border.setTop(bar.addMenuBar());
        border.setLeft(sidePanel.addSidePanel());
        border.setCenter(snap.snapGrid());
        border.setMargin(snap.getGrid(), new Insets(10, 10, 10, 10));
        
        lineDrawer L = new lineDrawer();
        L.initialize(sidePanel, snap);

        //---
        Scene scene = new Scene(border, 800, 800);
        primaryStage.setScene(scene);
        scene.getStylesheets().add(RidersOfWekmar.class.getResource("application.css").toExternalForm());
        primaryStage.show();
    }
}
