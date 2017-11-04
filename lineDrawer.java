package RidersOfWekmar;

import java.util.Stack;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;


//Creates a line that has endpoints that will snap to the higher or lower value divisible by 10
//to be used to connect the TextBoxClass objects, later implementations will add different end shapes
//to describe the different types of UML connections
public class LineDrawer 
{

    double x1, y1, x2, y2;
    Pane pane;
    mySidePanel sidePanel;
    Stack<Line> lineStack;

    //Initializes mouse event handlers and allows drawLine() to see the mySidePanel and Pane
    public LineDrawer(mySidePanel sp, Pane centerPane) 
    {
        centerPane.setOnMousePressed(press);
        centerPane.setOnMouseReleased(release);
        lineStack = new Stack<>();
        pane = centerPane;
        sidePanel = sp;
    }
    
        //Creates line object
    private void drawLine() 
    {
        Line l = new Line(x1, y1, x2, y2);
        l.setStrokeWidth(3);
        l.setOnMouseClicked(delete);
        pane.getChildren().add(l);
        lineStack.push(l);
    }
    
    public void undo()
    {
        if (!lineStack.empty())
        {
            Line l = lineStack.pop();
            pane.getChildren().remove(l);
        }
    }
    
    /*
    private void drawRightAngleLine()
    {
        Line l1 = new Line(x1, y1, x2, y1);
        Line l2 = new Line(x2, y1, x2, y2);
        l1.setStrokeWidth(3);
        l1.setOnMouseClicked(delete);
        l2.setStrokeWidth(3);
        l2.setOnMouseClicked(delete);
        pane.getChildren().addAll(l1, l2);
    }
    */

    public void delete(Line l)
    {  
       pane.getChildren().remove(l); 
    }
    
    public void deleteAll()
    {
        while (!lineStack.empty())
        {
            Line l = lineStack.pop();
            delete(l);
        }
    }   

    //On mouse click, get first set of coordinates and snap them to grid
    EventHandler<MouseEvent> press = (MouseEvent e) ->
    {
        //Specifies that depending on the location the line points are located
        //the line ends will either snap to the higher or lower value divisible by 10
        //10 was chosen instead of 50 for the line, for flexibility in the UML diagram
        if (sidePanel.lineBtnToggled()) 
        {
            double x = e.getX();
            double y = e.getY();
            //System.out.println(x + " , " + y);
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
    };

    //On mouse release, get second set of coordinates, snap them to grid, and drawLine()
    EventHandler<MouseEvent> release = (MouseEvent e) ->
    {
        if (sidePanel.lineBtnToggled()) 
        {
            double x = e.getX();
            double y = e.getY();
            if (x % 10 < 5)  {x2 = x - x % 10;} 
            else {x2 = x + 10 - x % 10;}
            if (y % 10 < 5) {y2 = y - y % 10;} 
            else {y2 = y + 10 - y % 10;}
            if (x2 < 0) {x2 = 0;}
            if (y2 < 0) {y2 = 0;}
            drawLine();
            //drawRightAngleLine();
        }
    };


    EventHandler<MouseEvent> delete = (MouseEvent e) -> {
        delete((Line) e.getSource());
    };
}
