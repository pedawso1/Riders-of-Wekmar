package RidersOfWekmar;

import java.util.Stack;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//Creates a side panel to house the buttons to create the TextClassBoxes and drawLines 
//to form the UML diagram
public class mySidePanel 
{

	GridPane grid = new GridPane();
	ToggleButton select = new ToggleButton();
	ToggleButton line = new ToggleButton();
	ToggleButton delete = new ToggleButton();
        ToggleButton textBox = new ToggleButton();
	Pane centerPane = new Pane();
        Stack objectStack;

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Force centerPane integration to reduce main class clutter
	public mySidePanel(Pane cp) 
        {
		centerPane = cp;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public GridPane addSidePanel() 
        {
            
            objectStack = new Stack();
            LineDrawer lineDrawer = new LineDrawer(this, centerPane);

            grid.setAlignment(Pos.TOP_LEFT);
            grid.setHgap(10);
            grid.setVgap(10);
            grid.setPadding(new Insets(10, 10, 0, 10));

            ToggleGroup tg = new ToggleGroup();
            select.setToggleGroup(tg);
            line.setToggleGroup(tg);
            delete.setToggleGroup(tg);
            textBox.setToggleGroup(tg);
		
	    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            /*
             * Delete button with icon
             */
            Image imageDelete = new Image(getClass().getResourceAsStream("icons/delete.png"));
            delete.getStyleClass().add("delete");
            delete.setGraphic(new ImageView(imageDelete));
            delete.setTooltip(new Tooltip("Delete"));
            grid.add(delete, 0, 0);
		
	    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            // TextBox button
            Image imageBox = new Image(getClass().getResourceAsStream("icons/textBox.png"));
            textBox.setGraphic(new ImageView(imageBox));
            //textBox.setAlignment(Pos.CENTER);
            textBox.setTooltip(new Tooltip("Textbox"));
            grid.add(textBox, 0, 1);
		
		
            // setting click event to spawn box
            textBox.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> 
	    {
                    TextBoxClass hold = new TextBoxClass(this);
                    //hold.spawn(centerPane);
                    //objectStack.push(hold);
                    objectStack.push(hold.spawn(centerPane)); //.toFront();
            });
		

            /*
             * Text button
             */
            Button text = new Button("Text");
            text.getStyleClass().add("text");
            //text.setAlignment(Pos.CENTER);
            text.setTooltip(new Tooltip("Text"));
            grid.add(text, 0, 2);
		
	    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            /*
             * Line button with icon, menu options for creating different line types
             */
            Image imageLine = new Image(getClass().getResourceAsStream("icons/drawLine.png"));
            MenuButton lineOpts = new MenuButton();
		
            MenuItem gen = new MenuItem("General");
            gen.setOnAction(event -> 
            {
                    line.setSelected(true);
                    System.out.println("drawing General line");
            });

            MenuItem basicAgg = new MenuItem("Basic Aggregation");
            basicAgg.setOnAction(event -> 
            {
                    System.out.println("drawing BasicAGG line");
            });

            MenuItem compAgg = new MenuItem("Composition Aggregation");
            compAgg.setOnAction(event -> 
            {
                    System.out.println("drawing CompositionAGG line");
            });

            MenuItem depend = new MenuItem("Dependency");
            compAgg.setOnAction(event -> 
            {
                    System.out.println("drawing dependency line");
            });

            lineOpts.setGraphic(new ImageView(imageLine));
            lineOpts.getItems().addAll(gen, basicAgg, compAgg, depend);
            lineOpts.setTooltip(new Tooltip("Select Line"));
            grid.add(lineOpts, 0, 3);
		
	   ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////			
           /*
             * Clear lines button "Designed by Freepik from www.flaticon.com"
             */
            Image imageClearLines = new Image(getClass().getResourceAsStream("icons/clearLines.png"));
            Button clearAllLines = new Button(/*"Delete All Lines"*/);
            clearAllLines.setGraphic(new ImageView(imageClearLines));
            clearAllLines.setTooltip(new Tooltip("Delete All Lines"));
            grid.add(clearAllLines, 1, 3);
		
            clearAllLines.setOnAction((ActionEvent e) -> 
            {
                lineDrawer.deleteAll();
            });
		
	    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            /*
             * Clear boxes button
             */
            Image imageTxtBox = new Image(getClass().getResourceAsStream("icons/deleteTextBox.png"));
            Button clearAllBoxes = new Button(/*"Delete All Boxes"*/);
            clearAllBoxes.setGraphic(new ImageView(imageTxtBox));
            clearAllBoxes.setTooltip(new Tooltip("Delete All Textboxes"));
            grid.add(clearAllBoxes, 1, 1);
		
            clearAllBoxes.setOnAction((ActionEvent e) -> 
            {
                deleteAllClassBoxes();
            });
		
	    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////            
            /*
             * Undo Line button "Designed by Freepik from www.flaticon.com"
             */
            Image undoline = new Image(getClass().getResourceAsStream("icons/undo-arrow.png"));
            Button undoLine = new Button(/*"Undo Line"*/);
            undoLine.setGraphic(new ImageView(undoline));
            undoLine.setTooltip(new Tooltip("Undo Line"));
            grid.add(undoLine, 0, 5);
		
            undoLine.setOnAction((ActionEvent e) -> 
            {
                lineDrawer.undo();
            });
		
	    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////            
            /*
             * Undo Box button "Designed by Freepik from www.flaticon.com"
             */
            Image undoTextBox = new Image(getClass().getResourceAsStream("icons/undoTextBox.png"));
            Button undoClassBox = new Button(/*"Undo Class Box"*/);
            undoClassBox.setGraphic(new ImageView(undoTextBox));
            undoClassBox.setTooltip(new Tooltip("Undo Textbox"));
            grid.add(undoClassBox, 1, 5); 
		
            undoClassBox.setOnAction((ActionEvent e) -> 
            {
                if (!objectStack.empty())
                {
                    centerPane.getChildren().remove(objectStack.pop());
                }                
            });
		
	    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////           
            /*
             * Clear All button 
             */
            Image imageClear = new Image(getClass().getResourceAsStream("icons/clearAll.png"));
            Button clearAll = new Button(/*"Clear All"*/);
            clearAll.setGraphic(new ImageView(imageClear));
            clearAll.setTooltip(new Tooltip("Clear All"));
            grid.add(clearAll, 1, 0);
		
            clearAll.setOnAction((ActionEvent e) -> 
            {
               lineDrawer.deleteAll();
               deleteAllClassBoxes();
            });

            return grid;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
        //deletes all textbox nodes
        private void deleteAllClassBoxes()
        {
            while (!objectStack.empty())
            {
                centerPane.getChildren().remove(objectStack.pop());
            }
        }
	
 	 ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////      
	// setting the line button boolean toggle
	public boolean lineBtnToggled() 
        {
	    return line.isSelected();
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
	//setting the delete button boolean toggle
	public boolean deleteBtnToggled() 
        {
	    return delete.isSelected();
	}
        
        public Stack getObjStack()
        {
            return objectStack;
        }
}
