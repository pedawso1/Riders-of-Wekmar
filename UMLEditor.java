/*
 * My attempt at JavaFX stuff
 */
package umleditor;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 *
 * @author Kyle
 */
public class UMLEditor extends Application {

    int x, y;
    int cornerX, cornerY;

    @Override
    public void start(Stage primaryStage) {

        primaryStage.setTitle("RoW UML Editor");

        //Main-Pane properties
        GridPane grid = new GridPane();
        //grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(10, 10, 10, 10));
        //grid.getColumnConstraints().add(new ColumnConstraints(100));
        ColumnConstraints column1 = new ColumnConstraints(100);
        ColumnConstraints column2 = new ColumnConstraints();
        grid.getColumnConstraints().addAll(column1, column2);
        //DEBUG ONLY
        //grid.setGridLinesVisible(true);

        //Draw a line
        //Line line1 = new Line(0, 100, 100, 0);
        //grid.add(line1, 0, 1);
        
        //Buttons
        ToggleGroup tg = new ToggleGroup();
        ToggleButton lineButton = new ToggleButton("Line Tool");    
        ToggleButton selectButton = new ToggleButton("Move Tool");
        ToggleButton squareButton = new ToggleButton("Square Button");
        ToggleButton delSquareButton = new ToggleButton("Delete Square Button");
        //tg.getToggles().addAll(selectButton, squareButton, delSquareButton);
        lineButton.setToggleGroup(tg);
        selectButton.setToggleGroup(tg);
        squareButton.setToggleGroup(tg);
        delSquareButton.setToggleGroup(tg);
        Button clearButton = new Button("Clear");
        VBox buttonBox = new VBox(15);
        buttonBox.getChildren().addAll(lineButton, selectButton, squareButton, delSquareButton, clearButton);
        buttonBox.setAlignment(Pos.CENTER);
        grid.add(buttonBox, 0, 0);

        //Canvas setup
        int columns = 6;
        int rows = 5;
        Canvas canvas = new Canvas(50 * columns, 50 * rows);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, 50 * columns, 50 * rows);
        drawGrid(gc, columns, rows);
        grid.add(canvas, 1, 0);
        
        //bool array of squares created by square tool
        boolean[][] isOccupied = new boolean[columns][rows];

        //Mouse events
        EventHandler<MouseEvent> mouseEventHandler = (MouseEvent e) -> {
            if (e.getEventType() == MouseEvent.MOUSE_PRESSED) {
                int gotX = (int) e.getX();
                int gotY = (int) e.getY();
                x = (int) gotX / 50;
                y = (int) gotY / 50;
                if (gotX % 25 < 13){cornerX = gotX - gotX % 25;}
                else {cornerX = gotX + (25 - gotX % 25);}
                if (gotY % 25 < 13){cornerY = gotY - gotY % 25;}
                else {cornerY = gotY + (25 - gotY % 25);}
                //System.out.println(x + " , " + y);
                //System.out.println(cornerX + " , " + cornerY);
            } else if (e.getEventType() == MouseEvent.MOUSE_CLICKED) {
                if (squareButton.isSelected()) {
                    gc.setFill(Color.BLACK);
                    gc.fillRect(e.getX() - e.getX() % 50, e.getY() - e.getY() % 50, 50, 50);
                    isOccupied[x][y] = true;
                } else if (delSquareButton.isSelected()) {
                    gc.setFill(Color.WHITE);
                    gc.fillRect(e.getX() - e.getX() % 50 + 1, e.getY() - e.getY() % 50 + 1, 48, 48);
                    isOccupied[x][y] = false;
                }
            } else if (e.getEventType() == MouseEvent.MOUSE_DRAGGED) {
                if (selectButton.isSelected()) {
                    if (isOccupied[x][y]) {
                        gc.setFill(Color.WHITE);
                        gc.fillRect(x * 50 + 1, y * 50 + 1, 48, 48);
                    }
                }
            } else if (e.getEventType() == MouseEvent.MOUSE_RELEASED) {
                if (lineButton.isSelected()) {
                    int curGotX = (int) e.getX();
                    int curGotY = (int) e.getY();
                    int curCornerX;
                    int curCornerY;
                    if (curGotX % 25 < 13){curCornerX = curGotX - curGotX % 25;}
                    else {curCornerX = curGotX + (25 - curGotX % 25);}
                    if (curGotY % 25 < 13){curCornerY = curGotY - curGotY % 25;}
                    else {curCornerY = curGotY + (25 - curGotY % 25);}
                    gc.strokeLine(cornerX, cornerY, curCornerX, curCornerY);
                } else if (selectButton.isSelected()) {
                    if (isOccupied[x][y]) {
                        gc.setFill(Color.BLACK);
                        gc.fillRect(e.getX() - e.getX() % 50, e.getY() - e.getY() % 50, 50, 50);
                        x = (int) e.getX() / 50;
                        y = (int) e.getY() / 50;
                        isOccupied[x][y] = true;
                    }
                }
            }
        };
        canvas.addEventFilter(MouseEvent.ANY, mouseEventHandler);
        clearButton.setOnAction((ActionEvent e) -> {
            for (int i = 0; i < rows; ++i) {
                for (int j = 0; j < rows; ++j) {
                    isOccupied[i][j] = false;
                }
            }
            gc.setFill(Color.WHITE);
            gc.fillRect(0, 0, 50 * columns, 50 * rows);
            drawGrid(gc, columns, rows);
        });

        //outputMouseCoords(grid);

        //Finalization
        Scene scene = new Scene(grid, 440, 280);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    //Draw grid on canvas
    private void drawGrid(GraphicsContext gc, int columns, int rows) {
        for (int i = 0; i <= columns; i++) {
            gc.strokeLine(50 * i, 0, 50 * i, 50 * rows);
        }
        for (int i = 0; i <= rows; i++) {
            gc.strokeLine(0, 50 * i, 50 * columns, 50 * i);
        }
    }
    
    //Test method for outputting current mouse coordinates    
    private void outputMouseCoords(Pane grid) {
        EventHandler<MouseEvent> fullWindowMouseEventHandler = (MouseEvent e) -> {
            if (e.getEventType() == MouseEvent.MOUSE_CLICKED) {
                System.out.println(e.getX() + " , " + e.getY());
            }
        };
        grid.addEventFilter(MouseEvent.ANY, fullWindowMouseEventHandler);
    }

    public static void main(String[] args) {
        launch(args);
    }

}
