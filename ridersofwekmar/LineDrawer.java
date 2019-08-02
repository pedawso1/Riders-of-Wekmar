package ridersofwekmar;

import java.util.Stack;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
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
    Boolean lineStop;
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
    int lineType;
    

        BooleanProperty dragging = new SimpleBooleanProperty();
        BooleanProperty draggingOverRect2 = new SimpleBooleanProperty();

    //Initializes mouse event handlers and allows drawLine() to see the mySidePanel and Pane
    public LineDrawer(mySidePanel sp) 
    {
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
    private void drawLine(Line temp, Circle circle1, Circle circle2) 
    {

        line.setMouseTransparent(true);
//        lineStack.push(temp);
        pane.getChildren().add(circle1);
        circle1.toBack();
        pane.getChildren().add(circle2);
        circle2.toFront();

    }
      private void drawLine(Line temp, Circle circle1, Circle circle2, Polygon shape) 
    {

        line.setMouseTransparent(true);
//        lineStack.push(temp);
        pane.getChildren().add(circle1);
        circle1.toBack();
        pane.getChildren().add(circle2);
        circle2.toBack();
        pane.getChildren().add(shape);
        shape.toBack();


    }
    /*
    private Polygon drawArrowLine()
    {
        //Line head logic        
        double angleRadians = Math.atan2(yE - yS, xE - xS);
        double angleDegrees = angleRadians * (180 / Math.PI);
        Polygon triangle = new Polygon();
        triangle.getPoints().setAll(
                0.0, 0.0,
                0.0, triangleWidth,
                triangleHeight, triangleWidth / 2
        );
        //triangle.setTranslateX(xE - triangleHeight / 2);
       // triangle.setTranslateY(yE - triangleWidth / 2);
        triangle.setRotate(angleDegrees);
        

    triangle.setCursor(Cursor.HAND);

     triangle.setOnMousePressed((t) -> {
      orgSceneX = t.getSceneX();
      orgSceneY = t.getSceneY();

     Polygon c = (Polygon) (t.getSource());
      c.toBack();
    });
    
     triangle.setOnMouseDragged((t) -> {
      double offsetX = t.getSceneX() - orgSceneX;
      double offsetY = t.getSceneY() - orgSceneY;

      Polygon c = (Polygon) (t.getSource());

      c.setLayoutX(c.getLayoutX() + offsetX);
      c.setLayoutY(c.getLayoutY() + offsetY);

      orgSceneX = t.getSceneX();
      orgSceneY = t.getSceneY();
    });
    
    return  triangle;
  }

    
    
     private Polygon drawClearArrowLine()
    {
        //Line head logic        
        double angleRadians = Math.atan2(yE - yS, xE - xS);
        double angleDegrees = angleRadians * (180 / Math.PI);
        Polygon triangle2 = new Polygon();
        triangle2.getPoints().setAll(
                0.0, 0.0,
                0.0, triangleWidth,
                triangleHeight, triangleWidth / 2
        );
      //  triangle2.setTranslateX(xE - triangleHeight / 2);
     //   triangle2.setTranslateY(yE - triangleWidth / 2);
        triangle2.setFill(Color.WHITE);
        triangle2.setStroke(Color.BLACK);
        triangle2.setStrokeWidth(2);
       // triangle2.setRotate(angleDegrees);
        
   triangle2.setCursor(Cursor.HAND);

     triangle2.setOnMousePressed((t) -> {
      orgSceneX = t.getSceneX();
      orgSceneY = t.getSceneY();

     Polygon c = (Polygon) (t.getSource());
      c.toBack();
    });
    
     triangle2.setOnMouseDragged((t) -> {
      double offsetX = t.getSceneX() - orgSceneX;
      double offsetY = t.getSceneY() - orgSceneY;

      Polygon c = (Polygon) (t.getSource());

      c.setLayoutX(c.getLayoutX() + offsetX);
      c.setLayoutY(c.getLayoutY() + offsetY);

      orgSceneX = t.getSceneX();
      orgSceneY = t.getSceneY();
    });
    
    return  triangle2;
  }
    
     private Polygon drawDiamondLine()
    {
        //Line head logic        
        double angleRadians = Math.atan2(yE - yS, xE - xS);
        double angleDegrees = angleRadians * (180 / Math.PI);
        Polygon diamond = new Polygon();
        diamond.getPoints().addAll(new Double[]{
        		0.0, 0.0,
        		diamondWidth, diamondHeight, 0.0, diamondHeight * 2, 
               diamondWidth * - 1, diamondHeight
        });
        
      //  diamond.setTranslateX(xE - diamondHeight / 2);
       // diamond.setTranslateY(yE - diamondWidth / 2);
        diamond.setFill(Color.WHITE);
        diamond.setStroke(Color.BLACK);
        diamond.setStrokeWidth(2);
      //  diamond.setRotate(angleDegrees);
        
     diamond.setCursor(Cursor.HAND);

    diamond.setOnMousePressed((t) -> {
      orgSceneX = t.getSceneX();
      orgSceneY = t.getSceneY();

     Polygon c = (Polygon) (t.getSource());
      c.toBack();
    });
    
    diamond.setOnMouseDragged((t) -> {
      double offsetX = t.getSceneX() - orgSceneX;
      double offsetY = t.getSceneY() - orgSceneY;

      Polygon c = (Polygon) (t.getSource());

      c.setLayoutX(c.getLayoutX() + offsetX);
      c.setLayoutY(c.getLayoutY() + offsetY);

      orgSceneX = t.getSceneX();
      orgSceneY = t.getSceneY();
    });
    
    return diamond;
  }
    
    private Polygon drawClearDiamondLine()
    {

        //Line head logic      
        Polygon diamond = new Polygon();
        double angleRadians = Math.atan2(yE - yS, xE - xS);
        double angleDegrees = angleRadians * (180 / Math.PI);
     
        diamond.getPoints().addAll(new Double[]{
        		0.0, 0.0,
        		diamondWidth, diamondHeight, 0.0, diamondHeight * 2, 
               diamondWidth * - 1, diamondHeight
        });
        
       // diamond.setTranslateX(xE - diamondHeight / 2);
       // diamond.setTranslateY(yE - diamondWidth / 2);
       // diamond.setRotate(angleDegrees);
        
        
       diamond.setCursor(Cursor.HAND);

    diamond.setOnMousePressed((t) -> {
      orgSceneX = t.getSceneX();
      orgSceneY = t.getSceneY();

     Polygon c = (Polygon) (t.getSource());
      c.toBack();
    });
    
    diamond.setOnMouseDragged((t) -> {
      double offsetX = t.getSceneX() - orgSceneX;
      double offsetY = t.getSceneY() - orgSceneY;

      Polygon c = (Polygon) (t.getSource());

      c.setLayoutX(c.getLayoutX() + offsetX);
      c.setLayoutY(c.getLayoutY() + offsetY);

      orgSceneX = t.getSceneX();
      orgSceneY = t.getSceneY();
    });
    
    return diamond;
  }
*/
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
      angleRadians = Math.atan2(yE - yS, xE - xS);
      angleDegrees = angleRadians * (180 / Math.PI);
      
      orgSceneX = t.getSceneX();
      orgSceneY = t.getSceneY();
       
    });
    return circle;
  }
     private Circle createCircleEnd(double x, double y) {
    Circle circle = new Circle(x,y, 4, Color.TRANSPARENT);

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
                 case 1:
                    line = line;
                    endPoint = false;
                    break;
                 case 2: 
                    circle.setRadius(4);
             
                    circle2.setRadius(8);
              
                    //circle.setFill(new ImagePattern(arrow));
                    temp = new ImageView(arrow);
                    temp.setRotate(180);
                    imgHold = new SnapshotParameters();
                    imgHold.setFill(Color.TRANSPARENT);
                    rotated = temp.snapshot(imgHold, null);
                    circle2.setFill(new ImagePattern(rotated));
               
                    endPoint = false;
                    break;
                 case 3: 
                    line.getStrokeDashArray().addAll(10d, 7d);
                    endPoint = false;
                    break;
                 case 4:
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
                    break;
                 case 5:
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
                    break;
                 case 6: 
                    circle.setRadius(4);
                    circle2.setRadius(10);
                   // circle.setFill(new ImagePattern(clearDiamond));
                
                    circle2.setFill(new ImagePattern(clearDiamond));
                    endPoint = true;
                    break;
                }
      
                connect(line, circle,circle2);
                drawLine(line,circle,circle2); 
            
            dragging.set(true);
           
        }
         
        });
                   // connect(line,circle,circle2);
        pane.setOnMouseDragged(event -> {
            if (dragging.get()) 
            {
                line.setEndX(event.getX());
                line.setEndY(event.getY());
                 
            
            }
        });
   
        
        line.setId("Line");
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
/*
     public void connect(Line line, Circle c1, Circle c2,Polygon shape, Polygon shape2){
         
            line.startXProperty().bindBidirectional(c1.centerXProperty());
             line.startYProperty().bindBidirectional(c1.centerYProperty());
             line.endXProperty().bindBidirectional(c2.centerXProperty());
             line.endYProperty().bindBidirectional(c2.centerYProperty());
             
       c1.centerXProperty().bindBidirectional(shape.layoutXProperty());
       c1.centerYProperty().bindBidirectional(shape.layoutYProperty());
       c2.centerXProperty().bindBidirectional(shape2.layoutXProperty());
       c2.centerYProperty().bindBidirectional(shape2.layoutYProperty());
     }
*/
    public void delete(Line l)
    {  
       pane.getChildren().remove(l); 
    }
        EventHandler<MouseEvent> delete = (MouseEvent e) -> 
    {
        delete((Line) e.getSource());
    };
    
    
    public void setLineType(int n)
    {
        lineType = n;
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
