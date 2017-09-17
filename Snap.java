
package csci420;

 
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

/**
 *
 * @author Pete
 */

public class Snap extends Application {
    Rectangle classBox = new Rectangle();
    double firstHoldX, firstHoldY;
    double moveHoldX, moveHoldY;

    @Override
    public void start(Stage primaryStage) throws Exception {
      try{
        classBox = new Rectangle();
        classBox.setX(0);
        classBox.setY(0);
        classBox.setWidth(100);
        classBox.setHeight(150);
        classBox.setOnMousePressed(boxOnMousePressedEventHandler);
        classBox.setOnMouseDragged(boxOnMouseDraggedEventHandler);
        classBox.setOnMouseReleased(boxOnMouseReleasedEventHandler);
       }catch(NullPointerException e){
            e.getMessage();
        }
         
        int rows = 11;
        int columns = 11;
        GridPane grid = new GridPane();
          for(int i = 0; i < columns; i++) {
            ColumnConstraints column = new ColumnConstraints(50);
            grid.getColumnConstraints().add(column);
        }

        for(int i = 0; i < rows; i++) {
            RowConstraints row = new RowConstraints(50);
            grid.getRowConstraints().add(row);
        }

        grid.getChildren().add(classBox);       
        grid.setStyle("-fx-background-color: white; -fx-grid-lines-visible: true");
        Scene scene = new Scene(grid,(columns * 50),(rows*50),Color.WHITE);
        primaryStage.setTitle("SnapGrid");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    public static void main(String[] args) {
      launch(args);
    }
    
        EventHandler<MouseEvent> boxOnMousePressedEventHandler = 
        new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent t) {
            firstHoldX = t.getSceneX();
            firstHoldY = t.getSceneY();
            moveHoldX = ((Rectangle)(t.getSource())).getTranslateX();
            moveHoldY = ((Rectangle)(t.getSource())).getTranslateY();
        }
    };
         
    EventHandler<MouseEvent> boxOnMouseDraggedEventHandler = 
        new EventHandler<MouseEvent>() {
 
        @Override
        public void handle(MouseEvent t) {
            double changeX = t.getSceneX() - firstHoldX;
            double changeY = t.getSceneY() - firstHoldY;
            double moveX = moveHoldX + changeX;
            double moveY = moveHoldY + changeY;
             
            ((Rectangle)(t.getSource())).setTranslateX(moveX);
            ((Rectangle)(t.getSource())).setTranslateY(moveY);
        }
    };
 
        EventHandler<MouseEvent> boxOnMouseReleasedEventHandler = 
        new EventHandler<MouseEvent>() {
 
        @Override
        public void handle(MouseEvent t) {
            double firstX = t.getSceneX();
            double firstY = t.getSceneY();
            firstX = (firstX-firstX%50);
            firstY = (firstY-firstY%50);
           ((Rectangle)(t.getSource())).setTranslateX(firstX);
           ((Rectangle)(t.getSource())).setTranslateY(firstY);
        }
    };

}
