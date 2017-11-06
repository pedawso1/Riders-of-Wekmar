package RidersOfWekmar;

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
 * Creates text boxes stacked via layout coordinates on top of each other, with
 * a box at the top that can be used to drag the collective group. When dragging
 * the box, the release event is tied to a modulo algorithm that snaps the box
 * into place on a grid
 */
public class TextBoxClass {

    Rectangle classBox = new Rectangle();
    double orgSceneX = 0;
    double orgSceneY = 0;
    double orgTranslateX = 0;
    double orgTranslateY = 0;
    mySidePanel sp;

    /**
     * Utilization of launch arguments is not implemented in this version of the
     * Riders of Wekmar UML Editor.
     * @param args 
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * When the TextBoxClass is constructed, the 'mySidePanel' attribute is
     * linked to RidersOfWekmar's current side panel
     * @param sidePanel 
     */
    public TextBoxClass(mySidePanel sidePanel) {
        sp = sidePanel;
    }

//
    /**
     * Returns a box with a box for moving at the top and three text boxes below
     * it, formated via setLayout. Also provides 3 buttons at the top for 
     * increasing, decreasing, and resetting the size of the box.
     * @param box The pane which the class box should be spawned on.
     * @return Returns a Pane with the box on it.
     */
    public Pane spawn(Pane box) {

        Button grow = new Button("+  ");
        Button shrink = new Button("-  ");
        shrink.setLayoutX(30);
        Button setting = new Button("..");
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
        group.getChildren().addAll(text1, text2, text3, classBox, grow, shrink, setting);

        group.setId("Text Box");

///////////////////////////////////////////////////////////////////////////////////////

        grow.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {

            if (text2.getHeight() < 190) {
                classBox.setWidth(classBox.getWidth() + 5);

                text1.setMaxWidth(text1.getMaxWidth() + 5);

                text2.setMaxWidth(text2.getMaxWidth() + 5);
                text2.setMaxHeight(text2.getMaxHeight() + 5);

                text3.setMaxWidth(text3.getMaxWidth() + 5);
                text3.setMaxHeight(text3.getMaxHeight() + 5);

                text2.setLayoutY(text2.getLayoutY() + 5);
            }
        });

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
        shrink.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {

            if (text2.getHeight() > 110) {
                classBox.setWidth(classBox.getWidth() - 5);

                text1.setMaxWidth(text1.getMaxWidth() - 5);

                text2.setMaxWidth(text2.getMaxWidth() - 5);
                text2.setMaxHeight(text2.getMaxHeight() - 5);

                text3.setMaxWidth(text3.getMaxWidth() - 5);
                text3.setMaxHeight(text3.getMaxHeight() - 5);

                text2.setLayoutY(text2.getLayoutY() - 5);
            }

        });

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        setting.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {

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
        group.addEventHandler(MouseEvent.MOUSE_PRESSED, e -> {

            orgSceneX = e.getSceneX();
            orgSceneY = e.getSceneY();
            orgTranslateX = group.getTranslateX();
            orgTranslateY = group.getTranslateY();

        });
	
        //On mouse drag event, compute offset of object and set group to new x and y coordinates
        group.addEventHandler(MouseEvent.MOUSE_DRAGGED, e -> {

            double offsetX = e.getSceneX() - orgSceneX;
            double offsetY = e.getSceneY() - orgSceneY;
            double newTranslateX = orgTranslateX + offsetX;
            double newTranslateY = orgTranslateY + offsetY;

            group.setTranslateX(newTranslateX);
            group.setTranslateY(newTranslateY);

        });
	
        //Gets new x and y coordinates and computes the closest value 
        //divisible by 30 so object can snap to grid
        group.addEventHandler(MouseEvent.MOUSE_RELEASED, e -> {

            Bounds bounds = group.localToParent(group.getBoundsInLocal());
            double firstX = bounds.getMinX();
            double firstY = bounds.getMinY();

            //Making sure objects stay within centerPane 	
            if (firstX >= 0) {
                if (firstX % 30 >= 15) {
                    firstX = (firstX + 30 - firstX % 30);
                } else {
                    firstX = (firstX - firstX % 30);
                }
            } else {
                firstX = 0;
            }

            if (firstY >= 0) {
                if (firstY % 30 >= 15) {
                    firstY = (firstY + 30 - firstY % 30);
                } else {
                    firstY = (firstY - firstY % 30);
                }
            } else {
                firstY = 0;
            }

            group.setTranslateX(firstX);
            group.setTranslateY(firstY);

        });

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
        EventHandler<MouseEvent> delete = (MouseEvent e) -> {
            if (sp.deleteBtnToggled()) {
                box.getChildren().remove(group);
            }
        };
        group.setOnMouseClicked(delete);

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        return group;
    }

}
