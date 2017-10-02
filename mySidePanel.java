package application;


import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;


public class mySidePanel 
{

    GridPane grid = new GridPane();
    TextBoxClass hold = new TextBoxClass();
    ToggleButton line = new ToggleButton();
    ToggleButton boxwa = new ToggleButton();
    
    public GridPane addSidePanel() 
    {
		
		grid.setAlignment(Pos.TOP_LEFT);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(10, 10, 0, 10));
                
                ToggleGroup tg = new ToggleGroup();

		/*
		 * Select button with icon
		 */
		Image imageSelect = new Image(getClass().getResourceAsStream("select.png"));
		ImageView imageView = new ImageView(imageSelect);
		imageView.setFitWidth(30);
		imageView.setFitHeight(30);
		
		Button select = new Button();
		select.getStyleClass().add("delete");
		select.setGraphic(new ImageView(imageSelect));
		select.setGraphic(imageView);
		
		HBox hbBtn = new HBox();
		hbBtn.setAlignment(Pos.TOP_LEFT);
		hbBtn.getChildren().add(select);
		grid.add(hbBtn, 0, 0);

		/*
		 * Line button with icon
		 */
		Image imageLine = new Image(getClass().getResourceAsStream("drawLine.png"));
	        line.getStyleClass().add("line");
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
    	 //setting the line button boolean toggle 
         public boolean lineBtnToggled()
  	 {
      	        return line.isSelected();
 	 }
      	
	 //setting the box button to be click event spawnable while box button selected
    	 public Pane box(Pane pane)
   	 {
		Image imageBox = new Image(getClass().getResourceAsStream("textBox.png"));
		Button textBox = new Button();
		textBox.setGraphic(new ImageView(imageBox));
                textBox.setAlignment(Pos.CENTER);
		grid.add(textBox, 0, 1);
		//setting click event to spawn box 
                textBox.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> 
                {
                           TextBoxClass hold = new TextBoxClass();
                           hold.spawn(pane);
		});
             return pane;
    	 }
}
