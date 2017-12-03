import javafx.application.Application;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class DragAndDropWithLine extends Application {

    @Override
    public void start(Stage primaryStage) {
        Pane pane = new Pane();
        Rectangle rect1 = new Rectangle(50, 50, 50, 100);
        rect1.setFill(Color.YELLOW);

        Rectangle rect2 = new Rectangle(200, 50, 50, 100);
        rect2.setFill(Color.BLUE);

        Line line = new Line();
        line.setMouseTransparent(true);

        pane.getChildren().addAll(rect1, rect2);

        BooleanProperty dragging = new SimpleBooleanProperty();
        BooleanProperty draggingOverRect2 = new SimpleBooleanProperty();

        pane.setOnDragDetected(event -> {
            pane.startFullDrag();
            Point2D mouseSceneCoords = new Point2D(event.getSceneX(), event.getSceneY());
            Point2D mousePaneCoords = pane.sceneToLocal(mouseSceneCoords);
            line.setStartX(mousePaneCoords.getX());
            line.setStartY(mousePaneCoords.getY());
            line.setEndX(mousePaneCoords.getX());
            line.setEndY(mousePaneCoords.getY());
            pane.getChildren().add(line);
            dragging.set(true);
        });

        pane.setOnMouseDragged(event -> {
            if (dragging.get()) {
                line.setEndX(event.getX());
                line.setEndY(event.getY());
            }
        });

        pane.setOnMouseReleased(event -> {
            if (draggingOverRect2.get()) {
                 line.setEndX(event.getX());
                 line.setEndY(event.getY());
                  pane.getChildren().add(line);
            }
            dragging.set(false);
            draggingOverRect2.set(false);
            pane.getChildren().remove(line);
        });

        rect2.setOnMouseDragEntered(event -> {
            if (dragging.get()) {
                draggingOverRect2.set(true);
            }
        });

        rect2.setOnMouseDragExited(event -> draggingOverRect2.set(false));


        primaryStage.setScene(new Scene(pane, 300, 200));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}