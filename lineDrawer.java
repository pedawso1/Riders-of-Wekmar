package application;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Line;

public class lineDrawer {

    int x1, y1, x2, y2;
    boolean isSelected;
    mySidePanel sp;
    GridPane grid;

    //Initializes mouse event handlers and allows drawLine() to see SP and grid
    public void initialize(mySidePanel sidePanel, snapToGrid snap) {
        snap.getGrid().setOnMousePressed(click);
        snap.getGrid().setOnMouseReleased(release);
        sp = sidePanel;
        grid = snap.getGrid();
    }

    //Get first set of coordinates
    EventHandler<MouseEvent> click = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent e) {
            if (sp.lineBtnToggled()) {
                int x = (int) e.getX();
                int y = (int) e.getY();
                //System.out.println(x + " , " + y);
                if (x % 25 < 13) {
                    x1 = x - x % 25;
                } else {
                    x1 = x + 25 - x % 25;
                }
                if (y % 25 < 13) {
                    y1 = y - y % 25;
                } else {
                    y1 = y + 25 - y % 25;
                }
            }
        }
    };

    //Get second set of coordinates and drawLine()
    EventHandler<MouseEvent> release = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent e) {
            if (sp.lineBtnToggled()) {
                int x = (int) e.getX();
                int y = (int) e.getY();
                if (x % 25 < 13) {
                    x2 = x - x % 25;
                } else {
                    x2 = x + 25 - x % 25;
                }
                if (y % 25 < 13) {
                    y2 = y - y % 25;
                } else {
                    y2 = y + 25 - y % 25;
                }
                drawLine();
            }
        }
    };

    //Creates line object - Currently right size, wrong position
    private void drawLine() {
        Line l = new Line(x1, y1, x2, y2);
        grid.getChildren().add(l);
    }
}
