package application;



import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class TryAgain4{
           Pane box = new Pane();
           TextBoxClass fresh = new TextBoxClass();
///////////////////////////////////////////////////////////////////////////////    
	
////////////////////////////////////////////////////////////////////////////////
	
	public Pane paneWork() {
		// TODO Auto-generated method stub
                Button btn = new Button("Add Textbox");
		box.getChildren().add(btn);
	
	
		//Node textAreaContent = group.lookup(".content");

////////////////////////////////////////////////////////////////////////////////		 
		 btn.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
                           TextBoxClass hold = new TextBoxClass();
                           hold.spawn(box);
                           
                           

		        });
                 return box;
	}
}
