package ridersofwekmar;

import java.util.Stack;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Cursor;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.transform.Rotate;


//Creates a line that has endpoints that will snap to the higher or lower value divisible by 10
//to be used to connect the TextBoxClass objects, later implementations will add different end shapes
//to describe the different types of UML connections
public class LineDrawer 
{
    double orgSceneX, orgSceneY;
    Line line = new Line();
    Boolean endPoint = false;
    double xS, yS, xE, yE;
    Pane pane;
    double angleRadians;
    double angleDegrees; 

    Rotate rot = new Rotate();
    ImageView temp; 
    SnapshotParameters imgHold;
    Image rotated;
    mySidePanel sidePanel;
    Stack<Line> lineStack;
    Image arrow = new Image(getClass().getResourceAsStream("icons/arrow.png"));
    Image diamond = new Image(getClass().getResourceAsStream("icons/diamond.png"));
    Image clearArrow = new Image(getClass().getResourceAsStream("icons/cleararrow.png"));
    Image clearDiamond = new Image(getClass().getResourceAsStream("icons/cleardiamond.png"));
    Circle circle = new Circle();
    Circle circle2 = new Circle();
    double strokeWidth;
    double triangleHeight, triangleWidth, diamondHeight, diamondWidth;
    Pane centerPane;
    DoubleProperty xProp,yProp;
    /**
     * Current lineTypes:
     * 1: Plain line
     * 2: Line with arrow
     * 3: Dependency (dotted) line
     */
    String lineType;
    

        BooleanProperty dragging = new SimpleBooleanProperty();
        BooleanProperty draggingOverRect2 = new SimpleBooleanProperty();

    //Initializes mouse event handlers and allows drawLine() to see the mySidePanel and Pane
    public LineDrawer(mySidePanel sp) 
    {
        
        lineType = "Association";
        strokeWidth = 2;
        triangleHeight = 20;
        triangleWidth = 40;
        diamondHeight = 15;
        diamondWidth = 25;
        pane = sp.getCenterPane();
        pane.setOnMousePressed(press);
        pane.setOnMouseReleased(release);
        sidePanel = sp;

        
   
    }
    
        //Creates line object
    private void drawLine( Circle circle1, Circle circle2) 
    {

        line.setMouseTransparent(true);
//        lineStack.push(temp);
        pane.getChildren().add(circle1);
        circle1.toBack();
        pane.getChildren().add(circle2);
        circle2.toFront();

    }
 
    public void drawFromLoad(double x1, double y1, double x2, double y2, String lineType)
    {
        Circle c1 = new Circle();
        Circle c2 = new Circle();
        Line line = new Line(x1,y1,x2,y2);
        c1 = createCircleEnd(x1,y1);
        c2 = createCircleEnd(x2,y2);
           switch (lineType)
                {   
                 case "Line":
                    line = line;
                     line.setId("Line");
                    endPoint = false;
                    break;
                 case "Association": 
                    c1.setRadius(4);
             
                    c2.setRadius(8);
              
                    //circle.setFill(new ImagePattern(arrow));
                    temp = new ImageView(arrow);
                    temp.setRotate(180);
                    imgHold = new SnapshotParameters();
                    imgHold.setFill(Color.TRANSPARENT);
                    rotated = temp.snapshot(imgHold, null);
                    c2.setFill(new ImagePattern(rotated));
                     line.setId("Association");
                    endPoint = false;
                    break;
                 case "Inheritance": 
                    line.getStrokeDashArray().addAll(10d, 7d);
                     line.setId("Inheritance");
                    endPoint = false;
                    break;
                 case "Aggregation":
                    c1.setRadius(4);
                    c2.setRadius(14);
                  //  circle.setFill(new ImagePattern(clearArrow));
                    temp = new ImageView(clearArrow);
                    temp.setRotate(180);
                    imgHold = new SnapshotParameters();
                    imgHold.setFill(Color.TRANSPARENT);
                    rotated = temp.snapshot(imgHold, null);
                    c2.setFill(new ImagePattern(rotated));
                    endPoint = true;
                     line.setId("Aggregation");
                    break;
                 case "Composition":
                    c1.setRadius(4);
                    c2.setRadius(8); 
                   // circle.setFill(new ImagePattern(diamond));
                    temp = new ImageView(diamond);
                    temp.setRotate(180);
                    imgHold = new SnapshotParameters();
                    imgHold.setFill(Color.TRANSPARENT);
                    rotated = temp.snapshot(imgHold, null);
                    c2.setFill(new ImagePattern(rotated));
                    endPoint = true;
                     line.setId("Composition");
                    break;
                 case "Dependency": 
                    c1.setRadius(4);
                    c2.setRadius(10);
                   // circle.setFill(new ImagePattern(clearDiamond));
                     line.setId("Dependency");
                    c2.setFill(new ImagePattern(clearDiamond));
                    endPoint = true;
                    break;
                }
       
         //line.setId("Line" + "" + lineType);
        sidePanel.pushToUndoStack(line);
        pane.getChildren().add(line);
        line.toBack();
        connect(line,c1,c2);
        drawLine(c1,c2);
     
        
    }
    public void undo()
    {
        if (!lineStack.empty())
        {
            Line l = lineStack.pop();
            pane.getChildren().remove(l);
        }
    }

    private Circle createCircleStart() {
    Circle circle = new Circle(xS,yS, 4, Color.TRANSPARENT);

    circle.setCursor(Cursor.HAND);

    circle.setOnMousePressed((t) -> {
      orgSceneX = t.getSceneX();
      orgSceneY = t.getSceneY();

      Circle c = (Circle) (t.getSource());
      c.toBack();
    });
    circle.setOnMouseDragged((MouseEvent t) -> {
      double offsetX = t.getSceneX() - orgSceneX;
      double offsetY = t.getSceneY() - orgSceneY;

      Circle c = (Circle) (t.getSource());

      c.setCenterX(c.getCenterX() + offsetX);
      c.setCenterY(c.getCenterY() + offsetY);
      
      
      orgSceneX = t.getSceneX();
      orgSceneY = t.getSceneY();
       
    });
    return circle;
  }
     private Circle createCircleEnd(double x, double y) {
        
    Circle circle = new Circle(x, y, 4, Color.TRANSPARENT);

    circle.setCursor(Cursor.HAND);

    circle.setOnMousePressed((t) -> {
      orgSceneX = t.getSceneX();
      orgSceneY = t.getSceneY();

      Circle c = (Circle) (t.getSource());
      c.toFront();
    });
    
    circle.setOnMouseDragged((t) -> {
      
      double offsetX = t.getSceneX() - orgSceneX;
      double offsetY = t.getSceneY() - orgSceneY;

      Circle c = (Circle) (t.getSource());
      
              
      c.setCenterX(c.getCenterX() + offsetX);
      c.setCenterY(c.getCenterY() + offsetY);
       
    orgSceneX = t.getSceneX();
    orgSceneY = t.getSceneY();
       
    });
  

    return circle;
  }

     EventHandler<MouseEvent> press = (MouseEvent e) ->
    {
    
             line = new Line();
             imgHold = new SnapshotParameters();
             temp = new ImageView();
      

             circle = new Circle();
             circle2 = new Circle();
          
            pane.setOnDragDetected(event -> {
                   if (sidePanel.lineBtnToggled()) 
        {
          
            pane.startFullDrag();
            Point2D mouseSceneCoords = new Point2D(event.getSceneX(), event.getSceneY());
            Point2D mousePaneCoords = pane.sceneToLocal(mouseSceneCoords);
            line.setStartX(mousePaneCoords.getX());
            line.setStartY(mousePaneCoords.getY());
            xS = mousePaneCoords.getX();
            yS = mousePaneCoords.getY();  

            circle = createCircleStart();
            line.setEndX(mousePaneCoords.getX());
            line.setEndY(mousePaneCoords.getY());
            
            xE = mousePaneCoords.getX();
            yE = mousePaneCoords.getY(); 
          
            circle2 = createCircleEnd(xE,yE);
            switch (lineType)
                {   
                 case "Line":
                    line = line;
                     line.setId("Line");
                    endPoint = false;
                    break;
                 case "Association": 
                    circle.setRadius(4);
             
                    circle2.setRadius(8);
              
                    //circle.setFill(new ImagePattern(arrow));
                    temp = new ImageView(arrow);
                    temp.setRotate(180);
                    imgHold = new SnapshotParameters();
                    imgHold.setFill(Color.TRANSPARENT);
                    rotated = temp.snapshot(imgHold, null);
                    circle2.setFill(new ImagePattern(rotated));
                     line.setId("Association");
                    endPoint = false;
                    break;
                 case "Dependency": 
                    line.getStrokeDashArray().addAll(10d, 7d);
                     line.setId("Dependency");
                    endPoint = false;
                    break;
                 case "Inhertance":
                    circle.setRadius(4);
                    circle2.setRadius(14);
                  //  circle.setFill(new ImagePattern(clearArrow));
                    temp = new ImageView(clearArrow);
                    temp.setRotate(180);
                    imgHold = new SnapshotParameters();
                    imgHold.setFill(Color.TRANSPARENT);
                    rotated = temp.snapshot(imgHold, null);
                    circle2.setFill(new ImagePattern(rotated));
                    endPoint = true;
                     line.setId("Inheritance");
                    break;
                 case "Aggregation":
                    circle.setRadius(4);
                    circle2.setRadius(8); 
                   // circle.setFill(new ImagePattern(diamond));
                    temp = new ImageView(diamond);
                    temp.setRotate(180);
                    imgHold = new SnapshotParameters();
                    imgHold.setFill(Color.TRANSPARENT);
                    rotated = temp.snapshot(imgHold, null);
                    circle2.setFill(new ImagePattern(rotated));
                    endPoint = true;
                     line.setId("Aggregation");
                    break;
                 case "Composition": 
                    circle.setRadius(4);
                    circle2.setRadius(10);
                   // circle.setFill(new ImagePattern(clearDiamond));
                     line.setId("Composition");
                    circle2.setFill(new ImagePattern(clearDiamond));
                    endPoint = true;
                    break;
                }
       
      
                connect(line, circle,circle2);
                drawLine(circle,circle2); 
            
            dragging.set(true);
           
        }
         
        });
                   // connect(line,circle,circle2);
        pane.setOnMouseDragged(event -> {
            if (dragging.get()) 
            {
                double firstX = event.getX();
                double firstY = event.getY();
               if(event.getX() >= 0) 
                	{
		   		if(event.getX()%30 >= 15) 
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
		
		
			if(event.getY() >= 0) 
                	{
		   		if(event.getY()%30 >= 15) 
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
                line.setEndX(firstX);
                line.setEndY(firstY);
                 
            
            }
        });
   

        sidePanel.pushToUndoStack(line);
        pane.getChildren().add(line);
        line.toBack();
                  
        
    };
      EventHandler<MouseEvent> release = (MouseEvent e) ->
    {
  
        pane.setOnMouseReleased(event -> {
    
            dragging.set(false);
          
            
        });
        
     
    };

     public void connect(Line line, Circle c1, Circle c2){
         
             line.startXProperty().bindBidirectional(c1.centerXProperty());
             line.startYProperty().bindBidirectional(c1.centerYProperty());
             line.endXProperty().bindBidirectional(c2.centerXProperty());
             line.endYProperty().bindBidirectional(c2.centerYProperty());
     }

    public void delete(Line l)
    {  
       pane.getChildren().remove(l); 
    }
        EventHandler<MouseEvent> delete = (MouseEvent e) -> 
    {
        delete((Line) e.getSource());
    };
    
    
    public void setLineType(String n)
    {
        this.lineType = n;
    }

    public void deleteAll()
    {
        while (!lineStack.empty())
        {
            Line l = lineStack.pop();
            delete(l);
        }
    }   

}
