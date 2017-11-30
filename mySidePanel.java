package RidersOfWekmar;

import java.util.Stack;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
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
        Button undoBtn;
        Button redoBtn;
	Pane centerPane = new Pane();
        //Stack<Pane> classBoxStack;
        Stack<Node> undoStack;
        Stack<Node> redoStack;
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Force centerPane integration to reduce main class clutter
	public mySidePanel(Pane cp) 
        {
		centerPane = cp;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public GridPane addSidePanel() 
        {
            
            //classBoxStack = new Stack<>();
            undoStack = new Stack();
            redoStack = new Stack();
            LineDrawer lineDrawer = new LineDrawer(this);

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
             * Clear All button 
             */
            Image imageClear = new Image(getClass().getResourceAsStream("icons/clearAll.png"));
            Button clearAll = new Button(/*"Clear All"*/);
            clearAll.setGraphic(new ImageView(imageClear));
            clearAll.setTooltip(new Tooltip("Clear All"));
            grid.add(clearAll, 0, 1);
		
            clearAll.setOnAction((ActionEvent e) -> 
            {
                //lineDrawer.deleteAll();
                //deleteAllClassBoxes();
                while (!undoStack.empty())
                {
                    centerPane.getChildren().remove(undoStack.pop());
                }
            });	
		
		
	    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            /*
             * Delete button with icon
             */
            Image imageDelete = new Image(getClass().getResourceAsStream("icons/delete.png"));
            delete.getStyleClass().add("delete");
            delete.setGraphic(new ImageView(imageDelete));
            delete.setTooltip(new Tooltip("Delete"));
            grid.add(delete, 0, 1);
		
	    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            // TextBox button
            Image imageBox = new Image(getClass().getResourceAsStream("icons/textBox.png"));
            textBox.setGraphic(new ImageView(imageBox));
            //textBox.setAlignment(Pos.CENTER);
            textBox.setTooltip(new Tooltip("Textbox"));
            grid.add(textBox, 0, 2);
		
		
            // setting click event to spawn box
            textBox.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> 
	    {
                    TextBoxClass hold = new TextBoxClass(this);
                    //hold.spawn(centerPane);
                    //classBoxStack.push(hold);
                    //classBoxStack.push(hold.spawn(centerPane)).toFront();
                    Pane classBox = hold.spawn(centerPane);
                    classBox.toFront();
                    pushToUndoStack(classBox);  
            });
		

            /*
             * Text button
             */
            Button text = new Button("Text");
            text.getStyleClass().add("text");
            //text.setAlignment(Pos.CENTER);
            text.setTooltip(new Tooltip("Text"));
            grid.add(text, 0, 4);
		
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
                    lineDrawer.setLineType(1);                
                    System.out.println("drawing General line");
            });

            MenuItem association = new MenuItem("Association");
            association.setOnAction(event -> 
            {
                    line.setSelected(true);
                    lineDrawer.setLineType(2);  
                    System.out.println("drawing Association line");
            });
		
	    MenuItem inher = new MenuItem("Inheritance");
            inher.setOnAction(event -> 
            {
                    line.setSelected(true);
                    lineDrawer.setLineType(4);  
                    System.out.println("drawing Tnheritance line");
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
            depend.setOnAction(event -> 
            {
                    line.setSelected(true);
                    lineDrawer.setLineType(3);
                    System.out.println("drawing dependency line");
            });

            lineOpts.setGraphic(new ImageView(imageLine));
            lineOpts.getItems().addAll(gen, association, inher, basicAgg, compAgg, depend);
            lineOpts.setTooltip(new Tooltip("Select Line"));
            grid.add(lineOpts, 0, 4);
		
	   ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////			
           /*
             * Clear lines button "Designed by Freepik from www.flaticon.com"
             * dysfunctional because lineStack not currently in use
             */
           /*
            Image imageClearLines = new Image(getClass().getResourceAsStream("icons/clearLines.png"));
            Button clearAllLines = new Button(); //"Delete All Lines"
            clearAllLines.setGraphic(new ImageView(imageClearLines));
            clearAllLines.setTooltip(new Tooltip("Delete All Lines"));
            grid.add(clearAllLines, 1, 3);
		
            clearAllLines.setOnAction((ActionEvent e) -> 
            {
                lineDrawer.deleteAll();
            });
            */
		
	    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            /*
             * Clear boxes button
             * dysfunctional because class box stack no longer in use
             */
            /*
            Image imageTxtBox = new Image(getClass().getResourceAsStream("icons/deleteTextBox.png"));
            Button clearAllBoxes = new Button(); //"Delete All Boxes"
            clearAllBoxes.setGraphic(new ImageView(imageTxtBox));
            clearAllBoxes.setTooltip(new Tooltip("Delete All Textboxes"));
            grid.add(clearAllBoxes, 1, 1);
		
            clearAllBoxes.setOnAction((ActionEvent e) -> 
            {
                deleteAllClassBoxes();
            });
            */
		
	    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////            
            /*
             * Undo Line button "Designed by Freepik from www.flaticon.com"
             */
            /*
            Image undoline = new Image(getClass().getResourceAsStream("icons/undo-arrow.png"));
            Button undoLine = new Button(); //"Undo Line"
            undoLine.setGraphic(new ImageView(undoline));
            undoLine.setTooltip(new Tooltip("Undo Line"));
            grid.add(undoLine, 0, 5);
		
            undoLine.setOnAction((ActionEvent e) -> 
            {
                lineDrawer.undo();
            });
            */
		
	    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////            
            /*
             * Undo (Box) button "Designed by Freepik from www.flaticon.com"
             */
            /*
            Image undoTextBox = new Image(getClass().getResourceAsStream("icons/undoTextBox.png"));
            Button undoClassBox = new Button(); //"Undo Class Box"
            undoClassBox.setGraphic(new ImageView(undoTextBox));
            undoClassBox.setTooltip(new Tooltip("Undo Textbox"));
            grid.add(undoClassBox, 1, 5); 
		
            undoClassBox.setOnAction((ActionEvent e) -> 
            {
                if (!classBoxStack.empty())
                {
                    centerPane.getChildren().remove(classBoxStack.pop());
                }                
            });
            */
            //Image undoImg = new Image(getClass().getResourceAsStream("icons/undoTextBox.png"));
            undoBtn = new Button();
            //undoBtn.setGraphic(new ImageView(undoImg));
            undoBtn.setTooltip(new Tooltip("Undo past action"));
            //grid.add(undoBtn, 1, 5);
            undoBtn.setOnAction((ActionEvent e) -> 
            {
                if (!undoStack.empty())
                {
                    Node curNode = undoStack.pop();
                    centerPane.getChildren().remove(curNode);
                    redoStack.push(curNode);
                    
                }
            });

            redoBtn = new Button("Redo");
            redoBtn.setTooltip(new Tooltip("Redo past undone action"));
            //grid.add(redoBtn, 0, 5);
            redoBtn.setOnAction((ActionEvent e) -> 
            {
                if (!redoStack.empty())
                {
                    Node curNode = redoStack.pop();
                    centerPane.getChildren().add(curNode);
                    undoStack.push(curNode);
                    
                }                
            });

            return grid;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
        //deletes all textbox nodes
        //dysfunctional because classbox stack is not in use
        /*
        private void deleteAllClassBoxes()
        {
            while (!classBoxStack.empty())
            {
                centerPane.getChildren().remove(classBoxStack.pop());
            }
        }
	*/
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
       
        public Pane getCenterPane()
        {
            return centerPane;
        }
        
        public void pushToUndoStack(Node n)
        {
            undoStack.push(n);
            redoStack = new Stack();
            /*
            while (!redoStack.empty())
            {
                redoStack.pop();
            }
            */
        }
        
        public void fireUndoBtn(){
            undoBtn.fire();
        }
        
        public void fireRedoBtn()
        {
            redoBtn.fire();
        }
}
