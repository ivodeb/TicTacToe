package com.example.ivode.tictactoe;
import java.io.Serializable;

/** Game class determines board size and state of each tile when chosen. */
class Game implements Serializable {
    TileState[][] board;
    private Boolean playerOneTurn;  // true if player 1's turn, false if player 2's turn

    Game() {
        final int BOARD_SIZE = 3;
        board = new TileState[BOARD_SIZE][BOARD_SIZE];
        for(int i=0; i<BOARD_SIZE; i++)
            for(int j=0; j<BOARD_SIZE; j++)
                board[i][j] = TileState.BLANK;

        playerOneTurn = true;
    }

    TileState choose(int row, int column) {
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
