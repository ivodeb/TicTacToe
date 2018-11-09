package com.example.ivode.tictactoe;
import java.io.Serializable;

public class Game implements Serializable {
    final private int BOARD_SIZE = 3;
    public TileState[][] board;

    private Boolean playerOneTurn;  // true if player 1's turn, false if player 2's turn

    public Game() {
        board = new TileState[BOARD_SIZE][BOARD_SIZE];
        for(int i=0; i<BOARD_SIZE; i++)
            for(int j=0; j<BOARD_SIZE; j++)
                board[i][j] = TileState.BLANK;

        playerOneTurn = true;
    }

    public TileState choose(int row, int column) {
        if (board[row][column] == TileState.BLANK) {
            if (playerOneTurn) {
                board[row][column] = TileState.CROSS;
                playerOneTurn = false;
                return TileState.CROSS;
            }
            else {
                board[row][column] = TileState.CIRCLE;
                playerOneTurn = true;
                return TileState.CIRCLE;
            }
        }
        else {
            return TileState.INVALID;
        }
    }
}
