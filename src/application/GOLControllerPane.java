package application;

import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.scene.control.*;
import javafx.event.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import foundation.GOLBoard;
import foundation.GOLFileException;

public class GOLControllerPane extends VBox {

    private static final int BUTTONWIDTH = 60;

    private GOLBoard currentBoard = new GOLBoard();

    public GOLControllerPane(GOLCanvas canvas) {
        super(10);

        // Create the button.
        Button btnLoad = new Button("Load");
        btnLoad.setMinWidth(BUTTONWIDTH);
        // set the handler of the ActionEvent to select a GOL file
        // and then reconstruct the 'currentBoard' with the
        // filename selected with appropriate handling of
        // GOLFileException (Alert)
        // If a new board has been created, it should be shwn using
        // the show method of the canvas

        btnLoad.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent ae) {

                // choose a file
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Load File");
                fileChooser.setInitialDirectory(new File(System.getProperty("user.dir")));
                fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("GOL", "*.gol"),
                        new FileChooser.ExtensionFilter("All Files", "*.*"));
                File file = fileChooser.showOpenDialog(btnLoad.getScene().getWindow());
                if (file == null)
                    return;

                // open and read a file
                String fileText = "";
                String line = "";
                BufferedReader in = null;

                try {
                    // open the file
                    in = new BufferedReader(new FileReader(file.getAbsolutePath()));

                    // read loop
                    while ((line = in.readLine()) != null) { // while not EOF

                        fileText += line + '\n';
                    }
                    GOLBoard currentBoard = new GOLBoard(fileText);

                    canvas.clear();
                    canvas.show(currentBoard);

                } catch (FileNotFoundException fnfe) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Severe Error");
                    alert.setHeaderText("FileNotFoundException has occurred");
                    alert.showAndWait();
                } catch (IOException ioe) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Severe Error");
                    alert.setHeaderText("IOException has occurred");
                    alert.showAndWait();
                } catch (GOLFileException gole) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("File Error");
                    alert.setHeaderText(gole.getMessage());
                    alert.setContentText("Please load a correct file.");
                    alert.showAndWait();


                } finally {
                    if (in != null) {
                        try {
                            in.close();
                        } catch (IOException e) {
                        }
                    }
                }

            }

        });

        // Add them to the box.
        getChildren().addAll(btnLoad);
        canvas.show(currentBoard);
    }

}
