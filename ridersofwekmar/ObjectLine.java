package ridersofwekmar;


import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author User One
 */
public class ObjectLine {
   double x1, y1, x2, y2;
    DoubleProperty startX,startY,endX,endY = new SimpleDoubleProperty();
    Pane centerPane;
    List<Double> list = new ArrayList<Double>();
    List<Node> nodeList = new ArrayList();
    Node line;
    mySidePanel sidePanel;
    Line l;
    Rectangle box;
    //Stack<Line> lineStack;
    Pane grid;
    

    /**
     * Initialize the class so that components of the class may check sidePanel 
     * toggles and perform actions on the centerPane
     * @param sp application's side panel
     * @param centerPane application's center centerPane
     */
    public ObjectLine(mySidePanel sp, Pane grid) 
    {
        this.grid = grid;
        centerPane = sp.getCenterPane();
        centerPane.setOnMousePressed(press);
       // grid.setOnMousePressed(presses);
        centerPane.setOnMouseReleased(release);
        //lineStack = new Stack<>();
        centerPane = centerPane;
  
        sidePanel = sp;

    }
public void setRec(Rectangle rec){
    box = rec;
}
    public void boundLine()
    {
        Line line = new Line(x1, y1, x2, y2);              
        line.setStrokeWidth(3);
        line.setId("Line");
        centerPane.getChildren().add(line);
        line.toBack();
        sidePanel.pushToUndoStack(line);
    
    }
    // EventHandler<MouseEvent> presses = (MouseEvent e) ->
  //  {
   //     System.out.println(e.getSource());
  //  };
  EventHandler<MouseEvent> press = (MouseEvent e) ->
    {
      //  System.out.println(e.getSource());
        //Specifies that depending on the location the line points are located
        //the line ends will either snap to the higher or lower value divisible by 10
        //10 was chosen instead of 50 for the line, for flexibility in the UML diagram
        if (sidePanel.lineBtnToggled()) 
        {
          
            double x = e.getX();
            double y = e.getY();
          
            if (x % 10 < 5) 
            {
                x1 = x - x % 10;
            } 
            else 
            {
                x1 = x + 10 - x % 10;
            }
            if (y % 10 < 5) 
            {
                y1 = y - y % 10;
            }
            else
            {
                y1 = y + 10 - y % 10;
            }

        }
          System.out.println(" ");
    };

    /**
     * On mouse release, get second set of coordinates
     */
    EventHandler<MouseEvent> release = (MouseEvent e) ->
    {
        System.out.println(e.getSource());
        if (sidePanel.lineBtnToggled() ) 
        {
            double x = e.getX();
            double y = e.getY();
            if (x % 10 < 5)  {x2 = x - x % 10;} 
            else {x2 = x + 10 - x % 10;}
            if (y % 10 < 5) {y2 = y - y % 10;} 
            else {y2 = y + 10 - y % 10;}
            if (x2 < 0) {x2 = 0;}
            if (y2 < 0) {y2 = 0;}
            boundLine();
            //drawRightAngleLine();
        }
       // if(e.getSource() == "Text Box"){
            
        //}
    };

    /**
     * Delete a line when it is clicked on
     */

}

