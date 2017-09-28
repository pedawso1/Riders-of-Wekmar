
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
*/
 package application;

import static javafx.application.Application.launch;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author pedawso1
 */
public class TextBoxClass {
    
            Rectangle classBox = new Rectangle();
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


 public Pane spawn(Pane box){
	          classBox = new Rectangle();
                  classBox.setLayoutX(0);
                  classBox.setLayoutY(0);
                  classBox.setWidth(150);
                  classBox.setHeight(25);
		
		TextField text1 = new TextField("Class Name");
	 	text1.setMaxWidth(150);
		text1.setLayoutY(25);
		
                TextArea text3 = new TextArea("Attributes!!!!");
		text3.setMaxSize(200, 75);
		text3.setMaxWidth(150);
		text3.setLayoutY(50);
		
		TextArea text2 = new TextArea("Instance Varibles");
		text2.setMaxSize(200, 100);
		text2.setMaxWidth(150);
		text2.setLayoutY(125);
		
               
                
		Pane group = new Pane();
		StackPane stackp = new StackPane();
		stackp.getChildren().add(group);
		box.getChildren().add(group);
		group.getChildren().addAll(text1,text2,text3,classBox);
		
		
		
			

 group.addEventHandler(MouseEvent.MOUSE_PRESSED, e -> {

	            orgSceneX = e.getSceneX();
	            orgSceneY = e.getSceneY();
                    System.out.println(orgSceneY);
	            orgTranslateX = group.getTranslateX();
	            orgTranslateY = group.getTranslateY();

               
	        });
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////		
		 group.addEventHandler(MouseEvent.MOUSE_DRAGGED, e -> {

	            double offsetX = e.getSceneX() - orgSceneX;
	            double offsetY = e.getSceneY() - orgSceneY;
	            double newTranslateX = orgTranslateX + offsetX;
	            double newTranslateY = orgTranslateY + offsetY;

	            group.setTranslateX(newTranslateX);
	            group.setTranslateY(newTranslateY);
	        });
               
                   group.addEventHandler(MouseEvent.MOUSE_RELEASED, e -> {
 
      
                       double firstX = e.getSceneX();
                       double firstY = e.getSceneY();
                       if(firstX >= 0)
                       firstX = (firstX-firstX%50);
                       else
                           firstX = 0;
                       
                       if(firstY >= 0)
                       firstY = (firstY-firstY%50);
                       else
                       firstY = 0;
                                   
                       group.setTranslateX(firstX);
                       group.setTranslateY(firstY);
                      

    });
                return box;
 }
}
