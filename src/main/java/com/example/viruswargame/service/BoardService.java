package com.example.viruswargame.service;

import com.example.viruswargame.model.CellState;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import static com.example.viruswargame.utils.Constants.BOARD_SIZE;

public class BoardService extends Parent {
    private final VBox rows = new VBox();

    public BoardService(EventHandler<? super MouseEvent> handler) {
        for (int y = 0; y < BOARD_SIZE; y++) {
            HBox row = new HBox();
            for (int x = 0; x < BOARD_SIZE; x++) {
                Cell cell = new Cell(x, y);
                cell.setOnMouseClicked(handler);
                row.getChildren().add(cell);
            }

            rows.getChildren().add(row);
        }

        getChildren().add(rows);
    }

    public static class Cell extends Rectangle {
        private final int x, y;

        public Cell(int x, int y) {
            super(30, 30);
            this.x = x;
            this.y = y;
            setFill(Color.LIGHTGRAY);
            setStroke(Color.BLACK);
        }

        public void setCellState(CellState cellState) {
            switch(cellState) {
                case NOT_PRESSED -> setFill(Color.LIGHTGRAY);
                case X_PRESSED, O_KILLED -> setFill(Color.RED);
                case O_PRESSED, X_KILLED -> setFill(Color.GREEN);
            }
        }

        public int getXCoord() {
            return x;
        }

        public int getYCoord() {
            return y;
        }
    }

    public void setBoard(CellState[][] board) {
        for(int y = 0; y < BOARD_SIZE; y++) {
            for(int x = 0; x < BOARD_SIZE; x++) {
                HBox row = (HBox)(rows.getChildren().get(y));
                Cell cell = (Cell)row.getChildren().get(x);
                cell.setCellState(board[y + 1][x + 1]);
            }
        }
    }
}
