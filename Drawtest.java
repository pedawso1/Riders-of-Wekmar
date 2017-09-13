
package csci420;


 
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.util.Pair;
 
/**
 *
 * @author Pete
 */
public class Drawtest extends Application {
    private Pair<Double, Double> holdCoord;
    
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) {
        Group root = new Group();
        Canvas canvas = new Canvas(300,250);
            Canvas canvas2 = new Canvas(150,150);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        drawShapes(gc);
      
           canvas.addEventHandler(MouseEvent.MOUSE_PRESSED, 
            new EventHandler<MouseEvent>() {
           @Override
           public void handle(MouseEvent e) {
              holdCoord = new Pair<>(e.getSceneX(), e.getSceneY());
           }
              });
             canvas.addEventHandler(MouseEvent.MOUSE_CLICKED, 
                new EventHandler<MouseEvent>(){
               @Override
                 public void handle(MouseEvent g)
                 {
                    gc.strokeLine(holdCoord.getKey(),holdCoord.getValue(), 
                            g.getSceneX(),g.getSceneY());     
                 } 
         });
             
             
        root.getChildren().add(canvas);
        Scene scene = new Scene(root);
        primaryStage.setTitle("SHAAAPPPEEESSSS!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void drawShapes(GraphicsContext gc) {
        //gc.setFill(Color.GREEN);
       // gc.setStroke(Color.BLACK);
      //  gc.setLineWidth(2);
       // gc.strokeLine(40,10,10,40);
        /*
        gc.fillOval(10,60,30,30);
        gc.strokeOval(60,60,30,30);
        gc.fillRoundRect(110,60,30,30,10,10);
        gc.fillArc(110,110,30,30,45,240,ArcType.OPEN);
      
*/
    }

    
}
