package com.example.ivode.tictactoe;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    // create game and initialize variables
    Game game;
    Integer[] buttons = {R.id.button1, R.id.button2, R.id.button3, R.id.button4, R.id.button5,
            R.id.button6, R.id.button7, R.id.button8, R.id.button9};
    int row = 0;
    int column = 0;
    boolean vs_computer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // preserve state using serializable class
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // check if vs computer or 2-player game
        Intent intent = getIntent();
        vs_computer = intent.getBooleanExtra("game", false);
        TextView versus_text = findViewById(R.id.versus_text);
        if (vs_computer) {
            versus_text.setText("Game versus computer");
        }
        else {
            versus_text.setText("Two-player game");
        }
        game = new Game();

        // state restoration
        if (savedInstanceState != null) {
            game = (Game) savedInstanceState.getSerializable("Game");
            for (int button_id : buttons) {
                Button button = findViewById(button_id);
                String button_text = savedInstanceState.getString("" + button_id);
                button.setText(button_text);
                if (button_text == "X") {
                    button.setTextColor(Color.RED);
                }
                else if (button_text == "O") {
                    button.setTextColor(Color.BLUE);
                }
            }
            Button reset = findViewById(R.id.reset);
            reset.setText(savedInstanceState.getString("reset"));
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
                ((Button) view).setTextColor(Color.RED);
                // if vs computer, let AI do the CIRCLE moves
                if (vs_computer && won() == GameState.IN_PROGRESS) {
                    for (int i = 0; i < 9; i++) {
                        Button button = findViewById(buttons[i]);
                        if (button.getText() == "") {
                            game.choose(i / 3, i % 3);
                            button.setText("O");
                            button.setTextColor(Color.BLUE);
                            break;
                        }
                    }
                }
                break;
            case CIRCLE:
                ((Button) view).setText("O");
                ((Button) view).setTextColor(Color.BLUE);
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

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(MainActivity.this, StartActivity.class);
        startActivity(intent);
    }

    // all possible end game states
    public GameState won() {
        int filled_buttons = 0;
        for(int i = 0; i < 9; i++) {
            row = i / 3;
            column = i % 3;
            // check if player one has won
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
            // check if player two has won
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
        // check if it's a draw
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
        for (int button_id : buttons) {
            Button button = findViewById(button_id);
            outState.putString(""+button_id, (String) button.getText());
        }
        Button reset = findViewById(R.id.reset);
        outState.putString("reset", (String) reset.getText());
    }
}