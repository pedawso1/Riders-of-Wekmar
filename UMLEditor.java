/*
 * My attempt at JavaFX stuff
 */
package umleditor;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

/**
 *
 * @author Kyle
 */
public class UMLEditor extends Application {
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("RoW - UML Diagram Editor");
        GridPane grid = new GridPane();
        //grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(10, 10, 10, 10));
        
        ToggleGroup tg = new ToggleGroup();
        ToggleButton squareButton = new ToggleButton("Square Button");
        /*
        tb1.setOnAction((ActionEvent e) -> {
           System.out.println("test message"); 
        });
        */
        grid.add(squareButton, 0, 0);
        ToggleButton delSquareButton = new ToggleButton("Delete Square Button");
        tg.getToggles().addAll(squareButton, delSquareButton);
        delSquareButton.setAlignment(Pos.TOP_CENTER);
        squareButton.setAlignment(Pos.BOTTOM_CENTER);
        grid.add(delSquareButton, 0, 1);
        
        
        
        Line line1 = new Line(0, 100, 100, 0);
        grid.add(line1, 0, 2);
        
        Rectangle r = new Rectangle(0, 0, 50, 50);
        r.setStroke(Color.BLACK);
        r.setFill(null);
        r.setStrokeWidth(3);
        grid.add(r, 0, 3);
        
        Canvas canvas = new Canvas(250, 250);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, 250, 250);
        grid.add(canvas, 1, 0);
        
        
        //Rectangle dragBox = new Rectangle(0, 0, 0, 0);
        //dragBox.setVisible(false);
        //grid.add(dragBox, 1, 0);
        
        EventHandler<MouseEvent> mouseEventHandler = (MouseEvent e) -> {
            //System.out.println("Hello World");

            if (e.getEventType() == MouseEvent.MOUSE_CLICKED) {
                if(squareButton.isSelected()){
                    gc.setFill(Color.BLACK);
                    gc.fillRect(e.getX() - 25, e.getY() - 25, 50, 50);
                } else if (delSquareButton.isSelected()) {
                    gc.setFill(Color.WHITE);
                    gc.fillRect(e.getX() - 25, e.getY() - 25, 50, 50);
                    //delStartX = e.getX();
                    //delStartY = e.getY();
                }
            }
            /*
            if (e.getEventType() == MouseEvent.MOUSE_RELEASED) {
                if (delSquareButton.isSelected()) {
                    gc.setFill(Color.WHITE);
                    //gc.fillRect(delStartX, delStartY, e.getX(), e.getY());
                }
            }
            */
        };
        
        /*
        EventHandler<MouseEvent> mouseDragEventHandler = (MouseEvent e) -> {
            if (delSquareButton.isSelected()){
                gc.clearRect(e.getX(), e.getY(), 0, 0);
            }
        };
        */

        canvas.addEventFilter(MouseEvent.ANY, mouseEventHandler);
        //canvas.addEventFilter(MouseEvent.MOUSE_DRAGGED, mouseDragEventHandler);
        
        
        Scene scene = new Scene(grid, 600, 700);
        primaryStage.setScene(scene);
        
        //this one shows the actual window
        primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
    
}
