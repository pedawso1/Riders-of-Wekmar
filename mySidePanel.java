package RidersOfWekmar;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

//Creates a side panel to house the buttons to create the TextClassBoxes and drawLines 
//to form the UML diagram
public class mySidePanel {

	GridPane grid = new GridPane();
	ToggleButton select = new ToggleButton();
	ToggleButton line = new ToggleButton();
	ToggleButton delete = new ToggleButton();
	ToggleButton boxwa = new ToggleButton();
	Pane centerPane = new Pane();

	// Force centerPane integration to reduce main class clutter
	public mySidePanel(Pane cp) {
		centerPane = cp;
	}

	public GridPane addSidePanel() {

		grid.setAlignment(Pos.TOP_LEFT);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(10, 10, 0, 10));

		ToggleGroup tg = new ToggleGroup();

		/*
		 * Delete button with icon
		 */
		Image imageDelete = new Image(getClass().getResourceAsStream("delete.png"));
		delete.getStyleClass().add("delete");
		delete.setToggleGroup(tg);
		delete.setGraphic(new ImageView(imageDelete));
		delete.setAlignment(Pos.CENTER);
		delete.setTooltip(new Tooltip("Delete"));
		grid.add(delete, 0, 0);

		// TextBox button
		Image imageBox = new Image(getClass().getResourceAsStream("textBox.png"));
		Button textBox = new Button();
		textBox.setGraphic(new ImageView(imageBox));
		textBox.setAlignment(Pos.CENTER);
		textBox.setTooltip(new Tooltip("Textbox"));
		grid.add(textBox, 0, 1);
		// setting click event to spawn box
		textBox.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
			TextBoxClass hold = new TextBoxClass(this);
			hold.spawn(centerPane);
		});

		/*
		 * Text button
		 */
		Button text = new Button("Text");
		text.getStyleClass().add("text");
		text.setAlignment(Pos.CENTER);
		text.setTooltip(new Tooltip("Text"));
		grid.add(text, 0, 2);

		/*
		 * Line button with icon
		 */
		Image imageLine = new Image(getClass().getResourceAsStream("drawLine.png"));
		line.getStyleClass().add("line");
		line.setToggleGroup(tg);
		line.setGraphic(new ImageView(imageLine));
		line.setAlignment(Pos.CENTER);
		line.setTooltip(new Tooltip("Draw Line"));
		grid.add(line, 0, 3);
		
		//where i am trying to make a drop down menu of different lines to use
		//having a hard time implementings the drawline
		MenuButton lineOpts = new MenuButton();
		MenuItem gen = new MenuItem("General");
		gen.setOnAction(event -> {
			line.setToggleGroup(tg);
		});
		MenuItem basicAgg = new MenuItem("Basic Aggregation");
		MenuItem compAgg = new MenuItem("Composition Aggregation");

		lineOpts.setGraphic(new ImageView(imageLine));
		lineOpts.getItems().addAll(gen, basicAgg, compAgg);

		lineOpts.setTooltip(new Tooltip("Select Line"));
		lineOpts.setAlignment(Pos.CENTER);
		grid.add(lineOpts, 0, 4);
		
		
		return grid;
	}

	// setting the line button boolean toggle
	public boolean lineBtnToggled() {
		return line.isSelected();
	}

	public boolean deleteBtnToggled() {
		return delete.isSelected();
	}
}
