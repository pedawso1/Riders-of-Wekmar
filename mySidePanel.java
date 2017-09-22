package application;


import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;


public class mySidePanel {

    GridPane grid = new GridPane();
    ToggleButton line = new ToggleButton();
    
    public GridPane addSidePanel() {
		
		grid.setAlignment(Pos.TOP_LEFT);
		grid.setHgap(10);
		grid.setVgap(10);
                //is this supposed to be 0? (insets are top, left, bottom, right)
		grid.setPadding(new Insets(10, 10, 0, 10));
                
                ToggleGroup tg = new ToggleGroup();

		/*
		 * Delete button with icon
		 */
		Image imageDelete = new Image(getClass().getResourceAsStream("bin.png"));
		Button delete = new Button();
		delete.setGraphic(new ImageView(imageDelete));
                delete.setAlignment(Pos.CENTER);
		grid.add(delete, 0, 0);

		/*
		 * Box button with icon
		 */
		Image imageBox = new Image(getClass().getResourceAsStream("square.png"));
		Button box = new Button();
		box.setGraphic(new ImageView(imageBox));
                box.setAlignment(Pos.CENTER);
		grid.add(box, 0, 1);

		/*
		 * Line button with icon
		 */
		Image imageLine = new Image(getClass().getResourceAsStream("drawLine.png"));
		//ToggleButton line = new ToggleButton();
                line.setToggleGroup(tg);
		line.setGraphic(new ImageView(imageLine));
                line.setAlignment(Pos.CENTER);
		grid.add(line, 0, 2);

		/*
		 * Text button
		 */
		Button text = new Button("Text");
		text.getStyleClass().add("text");
                text.setAlignment(Pos.CENTER);
                grid.add(text, 0, 3);
                
                


		return grid;
	}
    
    public boolean lineBtnToggled(){
        return line.isSelected();
    }
}