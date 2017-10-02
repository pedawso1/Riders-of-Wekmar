package application;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;

public class lineDrawer 
{

    int x1, y1, x2, y2;
    mySidePanel sp;
    Pane pane;

    //Initializes mouse event handlers and allows drawLine() to see the mySidePanel and Pane
    public void initialize(mySidePanel sidePanel, Pane centerPane) 
    {
        centerPane.setOnMousePressed(click);
        centerPane.setOnMouseReleased(release);
        sp = sidePanel;
        pane = centerPane;
    }

    //On mouse click, get first set of coordinates and snap them to grid
    EventHandler<MouseEvent> click = new EventHandler<MouseEvent>() 
    {
        @Override
        public void handle(MouseEvent e) 
        {
            if (sp.lineBtnToggled()) 
            {
                int x = (int) e.getX();
                int y = (int) e.getY();
                //System.out.println(x + " , " + y);
                if (x % 25 < 13) 
                {
                    x1 = x - x % 25;
                } 
                else 
                {
                    x1 = x + 25 - x % 25;
                }
                if (y % 25 < 13) 
                {
                    y1 = y - y % 25;
                }
                else
                {
                    y1 = y + 25 - y % 25;
                }
            }
        }
    };

    //On mouse release, get second set of coordinates, snap them to grid, and drawLine()
    EventHandler<MouseEvent> release = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent e) 
        {
            if (sp.lineBtnToggled()) 
            {
                int x = (int) e.getX();
                int y = (int) e.getY();
                if (x % 25 < 13) 
                {
                    x2 = x - x % 25;
                } 
                else 
                {
                    x2 = x + 25 - x % 25;
                }
                if (y % 25 < 13) 
                {
                    y2 = y - y % 25;
                } 
                else 
                {
                    y2 = y + 25 - y % 25;
                }
                drawLine();
            }
        }
    };

    //Creates line object
    private void drawLine() 
    {
        //if no ints are below 0
        if (!(x1 < 0 || x2 < 0 || y1 < 0 || y2 < 0)) 
        {
            Line l = new Line(x1, y1, x2, y2);
            pane.getChildren().add(l);
        } 
        else
        {
            if (x1 < 0) {x1 = 0;}
            if (x2 < 0) {x2 = 0;}
            if (y1 < 0) {y1 = 0;}
            if (y2 < 0) {y2 = 0;}
            Line l = new Line(x1, y1, x2, y2);
            pane.getChildren().add(l);
        }
    }
}
