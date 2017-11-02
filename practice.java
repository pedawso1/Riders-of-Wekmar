
package application;



import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class drawPlease extends Application{

	GridPane pane = new GridPane();
	 Scene scene = new Scene(pane,800,500);
	//what you draw one
	Canvas canvas = new Canvas(800,500);
	
	Button button = new Button("clear");
	Button Square = new Button("Add Square");
	
	Button tools = new Button("tools");
	
	GridPane toolbar = new GridPane();
	Scene toolScene = new Scene(toolbar,200,200);
	
	Stage New= new Stage();
	
	
 
	
	GraphicsContext gc;
	
	
	public void start (Stage stage){
		try{
			gc =canvas.getGraphicsContext2D();
			gc.setStroke(Color.BLACK);
			gc.setLineWidth(4);
			
			//see where you are clicking
			scene.setOnMousePressed(e->{
				gc.beginPath();
				gc.lineTo(e.getSceneX(), e.getSceneY());
				gc.stroke();
				
			});
			scene.setOnMouseDragged(e->{
				gc.lineTo(e.getSceneX(), e.getSceneY());
				gc.stroke();
			});
			
			
			
			pane.getChildren().addAll(canvas,tools);
			
			
			toolbar.getChildren().addAll(button);
			toolbar.add(Square,3,4);
			stage.setScene(scene);
			stage.setTitle("Riders of Wekmar");
			stage.show();
			
			button.setOnAction(e->{
				gc.clearRect(0,0,800,500);
				
			
			});
			
			tools.setOnAction(e->{
				
				
				New.setScene(toolScene);
				New.show();
				
				
				
			});
			
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}
	
	
	
	
	
	
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		launch(args);
		
	}

}
