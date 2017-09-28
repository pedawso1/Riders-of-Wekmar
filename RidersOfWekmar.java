package application;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

public class RidersOfWekmar extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Riders of Wekmar Editor");

        //--- layout of panel
        BorderPane border = new BorderPane();
        TryAgain4 box2 = new TryAgain4();
        mySidePanel sidePanel = new mySidePanel();
        myTopMenu bar = new myTopMenu();
      
        Pane centerPane = new Pane();
        Pane overlay = new Pane();
    
        


        
        
        border.setTop(bar.addMenuBar());
        border.setLeft(sidePanel.addSidePanel());
        border.setCenter(box2.paneWork());
        BorderPane.setMargin(box2.paneWork(), new Insets(10, 10, 10, 10));
        
        lineDrawer L = new lineDrawer();
        L.initialize(sidePanel, centerPane);

        //---
        Scene scene = new Scene(border, 800, 800);
        primaryStage.setScene(scene);
        scene.getStylesheets().add(RidersOfWekmar.class.getResource("application.css").toExternalForm());
        primaryStage.show();
    }
}
