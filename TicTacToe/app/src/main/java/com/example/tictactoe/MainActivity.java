package com.example.tictactoe;

import android.graphics.Color;

import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.tictactoe.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private boolean isThereWinner;
    private final List<String> ticTacToeOrangeMoves = new ArrayList<>();
    private final List<String> ticTacToeBlueMoves = new ArrayList<>();
    private String currentColorMove;
    private final List<List<String>> winningCombinations = Arrays.asList(
            Arrays.asList("cell1", "cell2", "cell3"),
            Arrays.asList("cell4", "cell5", "cell6"),
            Arrays.asList("cell7", "cell8", "cell9"),
            Arrays.asList("cell1", "cell4", "cell7"),
            Arrays.asList("cell2", "cell5", "cell8"),
            Arrays.asList("cell3", "cell6", "cell9"),
            Arrays.asList("cell1", "cell5", "cell9"),
            Arrays.asList("cell3", "cell5", "cell7"));

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRootViewZAndReset();
    }

    public void playAgainHandler(View view){
        setRootViewZAndReset();
    }

    public void setRootViewZAndReset(){
        setContentView(R.layout.activity_main);
        final View rootView = findViewById(android.R.id.content);
        clearAndResetAll(rootView);
    }

    public void clickCellHandler(View view) {
//        System.out.println(view.getTag().toString());
        ImageView imageView = (ImageView) view;
        String tagValue = String.valueOf(imageView.getTag());
        if (!isThereWinner) {
            if (!imageView.isSelected()) {
                imageView.setSelected(true);
                if (this.currentColorMove.equals("Orange")) {
                    imageView.setImageResource(R.drawable.blue_circle);
                    this.setCurrentColorMove("Blue");
                    ticTacToeBlueMoves.add(tagValue);
                } else if (this.currentColorMove.equals("Blue")) {
                    imageView.setImageResource(R.drawable.orange_circle);
                    this.setCurrentColorMove("Orange");
                    ticTacToeOrangeMoves.add(tagValue);
                }

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    winningCombinations.forEach(combination -> {
                        if (ticTacToeBlueMoves.containsAll(combination)) {
                            winningHandler(view, combination, "Blue");
                        }
                        if (ticTacToeOrangeMoves.containsAll(combination)) {
                            winningHandler(view, combination, "Orange");
                        }
                    });
                }
            }
        }
    }

    public void setThereWinner(boolean isThereWinner) {
        this.isThereWinner = isThereWinner;
    }

    public void setCurrentColorMove(String currentColorMove) {
        this.currentColorMove = currentColorMove;
    }

    private void winningHandler(final View view, final List<String> winnerCombination, final String winnerColor) {
        setContentView(R.layout.activity_main);
        final TextView winnerMessage = findViewById(R.id.winnerMessage);
        winnerMessage.setText(String.format("%s has won the game!", winnerColor));
        winnerMessage.setTextColor(winnerColor.equals("Blue") ? Color.rgb(56, 137, 199) : Color.rgb(238, 131, 49));
        setThereWinner(true);
    }

    private void clearAndResetAll(final View view) {
        ticTacToeOrangeMoves.removeIf(Objects::nonNull);
        ticTacToeBlueMoves.removeIf(Objects::nonNull);
        final TextView winnerMessage = view.findViewById(R.id.winnerMessage);
        winnerMessage.setText("");
        setThereWinner(false);
        this.setCurrentColorMove("Blue");
        for (int i = 1; i <= 9; i++) {
            final ImageView imageView = view.findViewWithTag("cell" + i);
            imageView.setOnClickListener(this::clickCellHandler);
            imageView.setSelected(false);
            imageView.setImageDrawable(null);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }
}