package RidersOfWekmar;

import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;



/**
 * Independent class for handling lines and their creation on centerPane. Line 
 * endpoints snap to a 10x10 grid.
 */
public class LineDrawer 
{
    double strokeWidth;
    double triangleHeight, triangleWidth, diamondHeight, diamondWidth;
    double x1, y1, x2, y2;
    Pane centerPane;
    mySidePanel sidePanel;
    /**
     * Current lineTypes:
     * 1: Plain line
     * 2: Line with arrow
     * 3: Dependency (dotted) line
     */
    int lineType;
    

    /**
     * Initialize the class so that components of the class may check sidePanel 
     * toggles and perform actions on the centerPane
     * @param sp application's side panel
     * @param centerPane application's center centerPane
     */
    public LineDrawer(mySidePanel sp) 
    {
        strokeWidth = 3;
        triangleHeight = 20;
        triangleWidth = 40;
        diamondHeight = 15;
        diamondWidth = 25;
        centerPane = sp.getCenterPane();
        centerPane.setOnMousePressed(press);
        centerPane.setOnMouseReleased(release);
        //lineStack = new Stack<>();
        centerPane = centerPane;
        sidePanel = sp;
    }

    /**
     * Create new line object and add it as a child to the center centerPane after the
     * coordinates have been acquired.
     */
    private void drawLine() 
    {
        Line l = new Line(x1, y1, x2, y2);
        l.setStrokeWidth(strokeWidth);
        if (lineType == 1)
        {
            pushLine(l);
        } else if (lineType == 2)
        {
            drawArrowLine(l);
        } else if (lineType == 3)
        {
            drawDashedLine(l);
        }else if (lineType == 4) {
        	
        	drawClearArrowLine(l);
        }else if (lineType == 5) {
        		drawClearDiamondLine(l);
        }
        else if (lineType == 6) {
    			drawDiamondLine(l);
        }
    }
    
    private void drawArrowLine(Line l) 
    {
        //Line head logic        
        double angleRadians = Math.atan2(y2 - y1, x2 - x1);
        double angleDegrees = angleRadians * (180 / Math.PI);
        Polygon triangle = new Polygon();
        triangle.getPoints().setAll(
                0.0, 0.0,
                0.0, triangleWidth,
                triangleHeight, triangleWidth / 2
        );
        triangle.setTranslateX(x2 - triangleHeight / 2);
        triangle.setTranslateY(y2 - triangleWidth / 2);
        triangle.setRotate(angleDegrees);
        
        Group g = new Group();
        g.getChildren().addAll(l, triangle);
        pushLine(g);
    }
    
    private void drawDashedLine(Line l)
    {
        l.getStrokeDashArray().addAll(10d, 7d);
        pushLine(l);
    }
    
     private void drawClearArrowLine(Line l) 
    {
        //Line head logic        
        double angleRadians = Math.atan2(y2 - y1, x2 - x1);
        double angleDegrees = angleRadians * (180 / Math.PI);
        Polygon triangle2 = new Polygon();
        triangle2.getPoints().setAll(
                0.0, 0.0,
                0.0, triangleWidth,
                triangleHeight, triangleWidth / 2
        );
        triangle2.setTranslateX(x2 - triangleHeight / 2);
        triangle2.setTranslateY(y2 - triangleWidth / 2);
        triangle2.setFill(Color.WHITE);
        triangle2.setStroke(Color.BLACK);
        triangle2.setStrokeWidth(3);
        triangle2.setRotate(angleDegrees);
        
        Group g = new Group();
        g.getChildren().addAll(l, triangle2);
        pushLine(g);
    }
    
     private void drawDiamondLine(Line l) 
    {
        //Line head logic        
        double angleRadians = Math.atan2(y2 - y1, x2 - x1);
        double angleDegrees = angleRadians * (180 / Math.PI);
        Polygon diamond = new Polygon();
        diamond.getPoints().addAll(new Double[]{
        		0.0, 0.0,
        		diamondWidth, diamondHeight, 0.0, diamondHeight * 2, 
               diamondWidth * - 1, diamondHeight
        });
        
        diamond.setTranslateX(x2 - diamondHeight / 2);
        diamond.setTranslateY(y2 - diamondWidth / 2);
        diamond.setFill(Color.WHITE);
        diamond.setStroke(Color.BLACK);
        diamond.setStrokeWidth(3);
        diamond.setRotate(angleDegrees);
        
        Group g = new Group();
        g.getChildren().addAll(l, diamond);
        pushLine(g);
    }
    
    private void drawClearDiamondLine(Line l) 
    {
        //Line head logic        
        double angleRadians = Math.atan2(y2 - y1, x2 - x1);
        double angleDegrees = angleRadians * (180 / Math.PI);
        Polygon diamond = new Polygon();
        diamond.getPoints().addAll(new Double[]{
        		0.0, 0.0,
        		diamondWidth, diamondHeight, 0.0, diamondHeight * 2, 
               diamondWidth * - 1, diamondHeight
        });
        
        diamond.setTranslateX(x2 - diamondHeight / 2);
        diamond.setTranslateY(y2 - diamondWidth / 2);
        diamond.setRotate(angleDegrees);
        
        Group g = new Group();
        g.getChildren().addAll(l, diamond);
        pushLine(g);
    }
    
    private void pushLine(Node l)
    {
        l.setOnMouseClicked(delete);
        l.setId("Line");
        l.toBack();
        sidePanel.pushToUndoStack(l);
        centerPane.getChildren().add(l);  
    }
    
    
    /**
     * Undo the creation the last line (delete last created line)
     * Line stack not currently used
     */
    /*
    public void undo()
    {
        if (!lineStack.empty())
        {
            Line l = lineStack.pop();
            centerPane.getChildren().remove(l);
        }
    }
    */
    
    /**
     * Prototype function for drawing right-angle lines (WIP)
     */
    /*
    private void drawRightAngleLine()
    {
        Line l1 = new Line(x1, y1, x2, y1);
        Line l2 = new Line(x2, y1, x2, y2);
        l1.setStrokeWidth(3);
        l1.setOnMouseClicked(delete);
        l2.setStrokeWidth(3);
        l2.setOnMouseClicked(delete);
        centerPane.getChildren().addAll(l1, l2);
    }
    */

    /**
     * Remove passed in Line object as a child of center centerPane
     * @param l 
     */
    public void delete(Line l)
    {  
       centerPane.getChildren().remove(l); 
    }
    
    /**
     * Perform delete on all lines by clearing the line stack
     * dysfunct because line stack no longer in use
     */
    /*
    public void deleteAll()
    {
        while (!lineStack.empty())
        {
            Line l = lineStack.pop();
            delete(l);
        }
    }   
    */

    /**
     * On mouse click, get the first set of coordinates and snap them to grid
     */
    EventHandler<MouseEvent> press = (MouseEvent e) ->
    {
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
    };

    /**
     * On mouse release, get second set of coordinates
     */
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
        }
    };

    /**
     * Delete a line when it is clicked on
     */
    EventHandler<MouseEvent> delete = (MouseEvent e) -> 
    {
        delete((Line) e.getSource());
    };
    
    
    public void setLineType(int n)
    {
        lineType = n;
    }
}
