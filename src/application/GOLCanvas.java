package application;

import javafx.scene.canvas.*;
import javafx.scene.paint.*;

import foundation.CellState;
import foundation.GOLBoard;

public class GOLCanvas extends Canvas {

    private static final int CELLSIZE = 5;
    private static final int GAP = 2;

    private GraphicsContext gc = null;

    public GOLCanvas() {
        super(GOLBoard.CELLSHORIZONTAL * (CELLSIZE + GAP) + GAP, GOLBoard.CELLSVERTICAL * (CELLSIZE + GAP) + GAP);

        // Get the graphics context for the canvas & clear.
        gc = getGraphicsContext2D();
        clear();

    }

    // shows the contents of 'board' on the canvas

    public void show(GOLBoard board) {

        for (int y = 0; y < GOLBoard.CELLSHORIZONTAL; y++) {
            for (int x = 0; x < GOLBoard.CELLSVERTICAL; x++) {
                if (board.getCellState(y, x) == CellState.LIVE) {
                    gc.setFill(Color.BLACK);
                    gc.fillRect( GAP + (GAP + CELLSIZE) * y, GAP + (GAP + CELLSIZE)* x, CELLSIZE, CELLSIZE);
                }
                // Test
//				 else {
//				 gc.setStroke(Color.BLACK);
//				 gc.strokeRect(2+7*y, 2+7*x, CELLSIZE, CELLSIZE);
//				 }
            }
        }

    }

    public void clear() {
        gc.clearRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
        gc.setStroke(Color.LIGHTSLATEGRAY);
        gc.strokeRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
    }

}
