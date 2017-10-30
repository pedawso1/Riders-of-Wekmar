package RidersOfWekmar;

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

        //creating objects
        BorderPane border = new BorderPane();
        Pane centerPane = new Pane();
        centerPane.getStyleClass().add("centerPane");
        mySidePanel sidePanel = new mySidePanel(centerPane);
        myTopMenu bar = new myTopMenu();
      
        //layout of editor
        border.setTop(bar.addMenuBar());
        border.setLeft(sidePanel.addSidePanel());
        border.setCenter(centerPane);
        BorderPane.setMargin(centerPane, new Insets(10, 10, 10, 10));
        
        //features
        lineDrawer L = new lineDrawer(sidePanel, centerPane);

        Scene scene = new Scene(border, 800, 800);
        primaryStage.setScene(scene);
        scene.getStylesheets().add(RidersOfWekmar.class.getResource("application.css").toExternalForm());
        primaryStage.show();
    }
}
