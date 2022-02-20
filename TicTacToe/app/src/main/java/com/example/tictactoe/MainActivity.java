package com.example.tictactoe;

import android.graphics.Color;
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
        setContentView(R.layout.activity_main);
        final View rootView = findViewById(android.R.id.content);
        clearAndResetAll(rootView);
    }

    public void clickCellHandler(View view) {
        if (!isThereWinner) {
            final String tagValue = view.getTag().toString();
            final ImageView clickedImage = (ImageView) view.findViewWithTag(tagValue);
            if (!clickedImage.isSelected()) {
                clickedImage.setSelected(true);
                final String cellNumber = tagValue.replaceAll("[^0-9]", "");
                final String cellClicked = tagValue.split(cellNumber)[0].concat(cellNumber);
                if (this.currentColorMove.equals("Orange")) {
                    this.setCurrentColorMove("Blue");
                    final ImageView clickedBlueImageView = (ImageView) view.findViewWithTag(cellClicked + "Blue");
                    clickedBlueImageView.setImageAlpha(255);
//                    clickedBlueImageView.animate().alpha(1).setDuration(500);
                    clickedBlueImageView.setSelected(true);
                } else {
                    this.setCurrentColorMove("Orange");
                    final ImageView clickedOrangeImageView = (ImageView) view.findViewWithTag(cellClicked + "Orange");
                    clickedOrangeImageView.setImageAlpha(255);
//                    clickedOrangeImageView.animate().alpha(1).setDuration(500);
                    clickedOrangeImageView.setSelected(true);
                }

                if (tagValue.contains("Orange")) {
                    ticTacToeOrangeMoves.add("cell" + cellNumber);
                } else {
                    ticTacToeBlueMoves.add("cell" + cellNumber);
                }
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

    public void setThereWinner(boolean isThereWinner) {
        this.isThereWinner = isThereWinner;
    }

    public void setCurrentColorMove(String currentColorMove) {
        this.currentColorMove = currentColorMove;
    }

    private void winningHandler(final View view, final List<String> winnerCombination, final String winnerColor) {
        final TextView winnerMessage = view.findViewById(R.id.winnerMessage);
        winnerMessage.setTextColor(winnerColor.equals("Blue") ? Color.rgb(56, 137, 199) : Color.rgb(238, 131, 49));
        winnerMessage.setText(String.format("%s has won the game!", winnerColor));
        setThereWinner(true);
    }

    private void clearAndResetAll(final View view) {
        final TextView winnerMessage = view.findViewById(R.id.winnerMessage);
        winnerMessage.setText("");
        setThereWinner(false);
        final String blueSuffix = "Blue";
        final String orangeSuffix = "Orange";
        this.setCurrentColorMove(Math.random() > 0.5 ? "Blue" : "Orange");
        for (int i = 1; i <= 9; i++) {
            final ImageView blueView = view.findViewWithTag("cell" + i + blueSuffix);
            final ImageView orangeView = view.findViewWithTag("cell" + i + orangeSuffix);
            blueView.setImageAlpha(0);
//            blueView.animate().alpha(0);
            orangeView.setImageAlpha(0);
//            orangeView.animate().alpha(0);
            blueView.setOnClickListener(this::clickCellHandler);

            orangeView.setOnClickListener(this::clickCellHandler);
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