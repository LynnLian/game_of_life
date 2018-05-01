package application;

// A mouse tracking application

import javafx.application.*;
import javafx.geometry.Insets;
import javafx.scene.*;
import javafx.stage.*;
import javafx.scene.layout.*;


public class GOL extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	public void start(Stage myStage) {

		// Setup the stage
		myStage.setTitle("Game of Life");

		// Setup the graphical elements
		GOLCanvas golCanvas = new GOLCanvas();
		GOLControllerPane golPane = new GOLControllerPane(golCanvas);

		// Create the HBox. 
		HBox rootNode = new HBox(10);

		rootNode.setPadding(new Insets(10, 10, 10, 10));

		rootNode.getChildren().addAll(golPane, golCanvas);

		// Create a scene.  
		Scene myScene = new Scene(rootNode, rootNode.getMinWidth(), rootNode.getMinHeight());

		// Set the scene on the stage.  
		myStage.setScene(myScene);
		myStage.setResizable(false);

		// Show the stage and its scene.
		myStage.show();

	}
	
}
