package application;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.shape.Rectangle;

public class snapToGrid {

	// Box and global declarations for snap events
	Rectangle classBox = new Rectangle();
	double firstHoldX, firstHoldY;
	double moveHoldX, moveHoldY;

	GridPane grid;

	public GridPane snapGrid() {
		try {
			classBox = new Rectangle();
			classBox.setX(0);
			classBox.setY(0);
			classBox.setWidth(100);
			classBox.setHeight(150);
			// press selects and gets current location
			classBox.setOnMousePressed(boxOnMousePressedEventHandler);
			// drag determines distance to move and translation variables
			classBox.setOnMouseDragged(boxOnMouseDraggedEventHandler);
			// modifies location to align on grid spaces
			classBox.setOnMouseReleased(boxOnMouseReleasedEventHandler);
		} catch (NullPointerException e) {
			e.getMessage();
		}

		int rows = 11;
		int columns = 11;
		// creates column restraints and columns
		grid = new GridPane();
		for (int i = 0; i < columns; i++) {
			ColumnConstraints column = new ColumnConstraints(50);
			grid.getColumnConstraints().add(column);
		}
		// creates row restraints and rows
		for (int i = 0; i < rows; i++) {
			RowConstraints row = new RowConstraints(50);
			grid.getRowConstraints().add(row);
		}

		// adds box object to the grid
		grid.getChildren().add(classBox);
		// THIS IS NOT FOR FINAL RELEASE WE WILL NEED CSS TO DEFINE GRID LINES
		grid.setStyle("-fx-background-color: white; -fx-grid-lines-visible: true");
		// Scene scene = new Scene(grid,(columns * 50),(rows*50),Color.WHITE);
		return grid;

	}

	// Gets location and gives to drag
	EventHandler<MouseEvent> boxOnMousePressedEventHandler = new EventHandler<MouseEvent>() {
		@Override
		public void handle(MouseEvent t) {
			firstHoldX = t.getSceneX();
			firstHoldY = t.getSceneY();
			moveHoldX = ((Rectangle) (t.getSource())).getTranslateX();
			moveHoldY = ((Rectangle) (t.getSource())).getTranslateY();
		}
	};
	// determines new location
	EventHandler<MouseEvent> boxOnMouseDraggedEventHandler = new EventHandler<MouseEvent>() {

		@Override
		public void handle(MouseEvent t) {
			double changeX = t.getSceneX() - firstHoldX;
			double changeY = t.getSceneY() - firstHoldY;
			double moveX = moveHoldX + changeX;
			double moveY = moveHoldY + changeY;

			((Rectangle) (t.getSource())).setTranslateX(moveX);
			((Rectangle) (t.getSource())).setTranslateY(moveY);
		}
	};
	// changes new location to snap to grid
	EventHandler<MouseEvent> boxOnMouseReleasedEventHandler = new EventHandler<MouseEvent>() {

		public void handle(MouseEvent t) {
			double firstX = t.getSceneX();
			double firstY = t.getSceneY();
			firstX = (firstX - firstX % 50);
			firstY = (firstY - firstY % 50);
			((Rectangle) (t.getSource())).setTranslateX(firstX);
			((Rectangle) (t.getSource())).setTranslateY(firstY);
		}
	};
        
        public GridPane getGrid(){
            return grid;
        }

}