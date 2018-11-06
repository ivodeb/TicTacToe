package com.example.ivode.tictactoe;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    // create game and initialize variables
    Game game;
    Integer[] buttons = {R.id.button1, R.id.button2, R.id.button3, R.id.button4, R.id.button5,
            R.id.button6, R.id.button7, R.id.button8, R.id.button9};
    int row = 0;
    int column = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // preserve state using serializable class
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        game = new Game();
        if (savedInstanceState != null) {
            game = (Game) savedInstanceState.getSerializable("Game");
        }
    }

    public void tileClicked(View view) {
        if (won() != GameState.IN_PROGRESS) {
            resetClicked(findViewById(R.id.reset));
        }
        int id = view.getId();
        for (int button_id : buttons) {
            if (id == button_id) {
                row = Arrays.asList(buttons).indexOf(button_id) / 3;
                column = Arrays.asList(buttons).indexOf(button_id) % 3;
            }
        }

        TileState state = game.choose(row, column);
        switch (state) {
            case CROSS:
                ((Button) view).setText("X");
                break;
            case CIRCLE:
                ((Button) view).setText("O");
                break;
            case INVALID:
                break;
        }
        Button button = findViewById(R.id.reset);
        if (won() == GameState.PLAYER_ONE) {
            button.setText("Player 1 won!");
        }
        if (won() == GameState.PLAYER_TWO) {
            button.setText("Player 2 won!");
        }
        if (won() == GameState.DRAW) {
            button.setText("It was a draw!");
        }
    }

    public GameState won() {
        int filled_buttons = 0;
        for(int i = 0; i < 9; i++) {
            row = i / 3;
            column = i % 3;
            if (game.board[row][column] == TileState.CROSS &&
                    game.board[row][(column + 1) % 3] == TileState.CROSS &&
                    game.board[row][(column + 2) % 3] == TileState.CROSS) {
                return GameState.PLAYER_ONE;
            }
            else if (game.board[row][column] == TileState.CROSS &&
                    game.board[(row + 1) % 3][column] == TileState.CROSS &&
                    game.board[(row + 2) % 3][column] == TileState.CROSS) {
                return GameState.PLAYER_ONE;
            }
            else if (game.board[0][0] == TileState.CROSS && game.board[1][1] == TileState.CROSS
                    && game.board[2][2] == TileState.CROSS) {
                return GameState.PLAYER_ONE;
            }
            else if (game.board[2][0] == TileState.CROSS && game.board[1][1] == TileState.CROSS
                    && game.board[0][2] == TileState.CROSS) {
                return GameState.PLAYER_ONE;
            }
            else if (game.board[row][column] == TileState.CIRCLE &&
                    game.board[row][(column + 1) % 3] == TileState.CIRCLE &&
                    game.board[row][(column + 2) % 3] == TileState.CIRCLE) {
                return GameState.PLAYER_TWO;
            }
            else if (game.board[row][column] == TileState.CIRCLE &&
                    game.board[(row + 1) % 3][column] == TileState.CIRCLE &&
                    game.board[(row + 2) % 3][column] == TileState.CIRCLE) {
                return GameState.PLAYER_TWO;
            }
            else if (game.board[0][0] == TileState.CIRCLE && game.board[1][1] == TileState.CIRCLE
                    && game.board[2][2] == TileState.CIRCLE) {
                return GameState.PLAYER_TWO;
            }
            else if (game.board[2][0] == TileState.CIRCLE && game.board[1][1] == TileState.CIRCLE
                    && game.board[0][2] == TileState.CIRCLE) {
                return GameState.PLAYER_TWO;
            }
            else if (game.board[row][column] == TileState.CROSS ||
                    game.board[row][column] == TileState.CIRCLE) {
                filled_buttons++;
            }
        }
        if (filled_buttons == 9) {
            return GameState.DRAW;
        }
        return GameState.IN_PROGRESS;
    }


    public void resetClicked(View view) {
        for (int button_id : buttons) {
            Button button = findViewById(button_id);
            button.setText("");
        }
        ((Button) view).setText("RESET");
        game = new Game();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("Game", game);
    }
}
