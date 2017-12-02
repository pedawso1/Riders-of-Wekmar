
import static javafx.application.Application.launch;


import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;







public class label {
	
	Label label= new Label("click to edit");
	double fontNumber;
	double orgSceneX = 0;
    double orgSceneY = 0;
    double orgTranslateX = 0;
    double orgTranslateY = 0;
    
	
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stu
		launch(args);	
	}
////////////////////////////////////////////////////////////////////////////////
//@Override
	
	 public void spawn(Pane box){
		 
		 Stage window = new Stage();
		 window.setTitle("Enter Label text");
		 
		 //Text for the text label
		 TextArea lblInput =new TextArea("");
		 lblInput.setMaxWidth(300);
		 
		 
		 Label enterText = new Label("Enter Text:");
		 Label enterFont = new Label("Font Size:");
		 
		 //Text for Fontsize
		 TextField fontSize =new TextField("25");
		 fontSize.setMaxWidth(50);
		 
		 
		 label.setLayoutY(200);
		 VBox layout = new VBox(10);
		 Button exit = new Button("Save and Close");
		 layout.getChildren().addAll(enterText,lblInput,enterFont,fontSize,exit);
		 Scene scene = new Scene(layout);
		 
		 
		 
		 
		 window.setWidth(400);
		 window.setHeight(400);
		 window.setScene(scene);
		 window.show();
		 Group text = new Group();
		///////////////////////////////////////////////////////////////////// 
		 
		 exit.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
		 box.getChildren().remove(text);
		 text.getChildren().remove(label);
		  
		  String x = lblInput.getText();
		  String y = fontSize.getText();
		  
		  
		  fontNumber =Integer.parseInt(y);
		  label.setText(x);
		  label.setFont(Font.font("Verdana",FontWeight.EXTRA_BOLD,fontNumber));
		 
		  text.getChildren().add(label);
		  box.getChildren().add(text);
		  
		 window.close();
		 
		 
		 });
		 
	    ///////////////////////////////////////////////////////////////////////////////	
		 
		 
		 text.addEventHandler(MouseEvent.MOUSE_PRESSED, e -> {

	            orgSceneX = e.getSceneX();
	            orgSceneY = e.getSceneY();
                 System.out.println(orgSceneY);
	            orgTranslateX = text.getTranslateX();
	            orgTranslateY = text.getTranslateY();

	         
	        });
		 
		 
		 
		 ////////////////////////////////////////////////////////////////////////////////
		 
		 text.addEventHandler(MouseEvent.MOUSE_DRAGGED, e -> {

	            double offsetX = e.getSceneX() - orgSceneX;
	            double offsetY = e.getSceneY() - orgSceneY;
	            double newTranslateX = orgTranslateX + offsetX;
	            double newTranslateY = orgTranslateY + offsetY;

	            text.setTranslateX(newTranslateX);
	            text.setTranslateY(newTranslateY);
	        });
		 
		 
		/////////////////////////////////////////////////////////////////////////////////////////
		 text.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
			 
  
			 if(e.getClickCount() == 2) 
			 { 
				box.getChildren().remove(text);
				//text.getChildren().remove(label);
			    window.show();
			    
			    
					 
			    
					 
					 
					 
					 
					 
			
					 
					 
					
			    
			    
			    
			    
			    
			 } 
			

	         
	        });
		 
		 
		 
		 
		 
		 
		 
		 
		 
		 
		 
		 
	 }
}
		 
		 
	 
	 
	 
	
	
	
	


