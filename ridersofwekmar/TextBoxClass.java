
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
*/
 package ridersofwekmar;

import static javafx.application.Application.launch;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author pedawso1
 */
//Creates text boxes stacked via layout on top of eachother with a box shape as 
//a head portion of the box that can be used to grab, the boxes release event triggers
//a modulo algorithm that snaps the box in place on a grid
public class TextBoxClass 
{
    
            Rectangle classBox = new Rectangle();
            double orgSceneX = 0;
	    double orgSceneY = 0;
	    double orgTranslateX = 0;
	    double orgTranslateY = 0;
            mySidePanel sp;
	
	////////////////////////////////////////////////////////////////////////////////       
	public static void main(String[] args)
	{
		launch(args);	
	}
	
	////////////////////////////////////////////////////////////////////////////////
	//@Override
	public TextBoxClass (mySidePanel sidePanel)
	{
    		sp = sidePanel;
	}

	//Returns a box with a box for moving at the top and three text boxes below it, formated via setLayout
	//box also has resize buttons for growing, shrinking or return to preset
	public Pane spawn(Pane box) 
	{
		//resizing buttons
		Button grow = new Button("+");
		grow.getStyleClass().add("txtbxSize");
		Button shrink = new Button("-");
		shrink.getStyleClass().add("txtbxSize");
		shrink.setLayoutX(30);
		Button setting = new Button("..");
		setting.getStyleClass().add("txtbxSize");
		setting.setLayoutX(60);
		
		//creating box to grab for TextBox movement
		classBox = new Rectangle();
		classBox.setLayoutX(70);
		classBox.setLayoutY(0);
		classBox.setWidth(80);
		classBox.setHeight(25);
		
		//Text fields for Class,Attributes, Instance Variables
		TextArea text1 = new TextArea("Class Name");
		text1.setMaxSize(150, 25);
		text1.setLayoutY(25);
		
		TextArea text2 = new TextArea("Instance Varibles");
		text2.setMaxHeight(100);
		text2.setMaxWidth(150);
		text2.setLayoutY(125);
	
		TextArea text3 = new TextArea("Attributes!!!!");
		text3.setMaxWidth(150);
		text3.setMaxHeight(75);
		text3.setLayoutY(50);
	    
		Pane group = new Pane();
		StackPane stackp = new StackPane();
		stackp.getChildren().add(group);
		box.getChildren().add(group);
		group.getChildren().addAll(text1,text2,text3,classBox,grow,shrink,setting);
		
		group.setId("Text Box");
			
		///////////////////////////////////////////////////////////////////////////////////////
		//Growth increments
		grow.addEventHandler(MouseEvent.MOUSE_CLICKED, e->
        	{		
			if(text2.getHeight()<190)
                	{
                    		classBox.setWidth(classBox.getWidth()+5);
			
                    		text1.setMaxWidth(text1.getMaxWidth()+5);
		
                    		text2.setMaxWidth(text2.getMaxWidth()+5);
                    		text2.setMaxHeight(text2.getMaxHeight()+5);
			
                    		text3.setMaxWidth(text3.getMaxWidth()+5);
                		text3.setMaxHeight(text3.getMaxHeight()+5);
			
                		text2.setLayoutY(text2.getLayoutY()+5);
			}
		});
		
		////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        	//Shrink increments
		shrink.addEventHandler(MouseEvent.MOUSE_CLICKED, e->
        	{
 			
 			if(text2.getHeight()>110)
                	{
 		    		classBox.setWidth(classBox.getWidth()-5);  
 		    		text1.setMaxWidth(text1.getMaxWidth()-5);
 				
                    		text2.setMaxWidth(text2.getMaxWidth()-5);
                    		text2.setMaxHeight(text2.getMaxHeight()-5);
 				
                		text3.setMaxWidth(text3.getMaxWidth()-5);
                    		text3.setMaxHeight(text3.getMaxHeight()-5);	
 				
                    		text2.setLayoutY(text2.getLayoutY()-5);
 			}
 			
 		});
	
		///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        	//return to preset
		setting.addEventHandler(MouseEvent.MOUSE_CLICKED, e->
        	{
  		   
                	classBox.setWidth(80);
                	classBox.setHeight(25);

                	text1.setMaxSize(150, 25);
		
                	text2.setMaxHeight(100);
                	text2.setMaxWidth(150);
                	text2.setLayoutY(125);
	
                	text3.setMaxWidth(150);
                	text3.setMaxHeight(75);

		});
	
		////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
		//On mouse press event get current scene coordinates for x and y and store them in global variables
		group.addEventHandler(MouseEvent.MOUSE_PRESSED, e -> 
        	{		
			
			orgSceneX = e.getSceneX();
			orgSceneY = e.getSceneY();
			orgTranslateX = group.getTranslateX();
			orgTranslateY = group.getTranslateY();
		
		});
	
		////////////////////////////////////////////////////////////////////////////////////////////////////////////////////		  
		//On mouse drag event, compute offset of object and set group to new x and y coordinates
	
		group.addEventHandler(MouseEvent.MOUSE_DRAGGED, e -> 
        	{
				
			double offsetX = e.getSceneX() - orgSceneX;
			double offsetY = e.getSceneY() - orgSceneY;
			double newTranslateX = orgTranslateX + offsetX;
			double newTranslateY = orgTranslateY + offsetY;
		
			group.setTranslateX(newTranslateX);
			group.setTranslateY(newTranslateY);
		
        	});
               
		////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
		//Gets new x and y coordinates and computes the closest value 
		//divisible by 30 so object can snap to grid
		 
		group.addEventHandler(MouseEvent.MOUSE_RELEASED, e -> 
        	{
		
			Bounds bounds = group.localToParent(group.getBoundsInLocal());
			double firstX = bounds.getMinX();
			double firstY = bounds.getMinY();
                    
			//Making sure objects stay within centerPane 	
			if(firstX >= 0) 
                	{
		   		if(firstX%30 >= 15) 
                   		{
					firstX = (firstX + 30 - firstX%30);
		   		}
                   		else
                   		{
					firstX = (firstX - firstX%30);
                   		}
			}
                	else
                	{
			   	firstX = 0;
                	}
		
		
			if(firstY >= 0) 
                	{
		   		if(firstY%30 >= 15) 
                   		{
					firstY = (firstY + 30 - firstY%30);
		   		}
                   		else
                   		{
					firstY = (firstY - firstY%30);
                   		}
			}
                	else
                	{
		   		firstY = 0;
                	}
		
               
           	  	group.setTranslateX(firstX);
          	  	group.setTranslateY(firstY);

		});
  	      
		////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
        	//deletes textbox node
		EventHandler<MouseEvent> delete = (MouseEvent e) -> 
        	{
	    		if (sp.deleteBtnToggled()) 
            		{
	       			 box.getChildren().remove(group);
	    		}
		};
        
        	group.setOnMouseClicked(delete);
        	return group;
    	}

}
