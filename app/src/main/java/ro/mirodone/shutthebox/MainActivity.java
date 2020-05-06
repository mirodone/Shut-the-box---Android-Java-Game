package ro.mirodone.shutthebox;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    Button b_player1_1, b_player1_2, b_player1_3, b_player1_4, b_player1_5, b_player1_6, b_player1_7, b_player1_8, b_player1_9;
    Button b_player2_1, b_player2_2, b_player2_3, b_player2_4, b_player2_5, b_player2_6, b_player2_7, b_player2_8, b_player2_9;

    List<Button> player1Buttons;
    List<Button> player2Buttons;

    Button b_player1_roll1, b_player1_roll2;
    Button b_player2_roll1, b_player2_roll2;

    ImageView mViewDice1, mViewDice2;

    TextView tv_p1_points, tv_p2_points;
    TextView tv_status;

    int player1Points = 1 + 2 + 3 + 4 + 5 + 6 + 7 + 8 + 9;
    int player2Points = 1 + 2 + 3 + 4 + 5 + 6 + 7 + 8 + 9;
    int dice1Value, dice2Value, diceSum;


    List<Integer> combinations;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        b_player1_1 = findViewById(R.id.btn_player1_1);
        b_player1_2 = findViewById(R.id.btn_player1_2);
        b_player1_3 = findViewById(R.id.btn_player1_3);
        b_player1_4 = findViewById(R.id.btn_player1_4);
        b_player1_5 = findViewById(R.id.btn_player1_5);
        b_player1_6 = findViewById(R.id.btn_player1_6);
        b_player1_7 = findViewById(R.id.btn_player1_7);
        b_player1_8 = findViewById(R.id.btn_player1_8);
        b_player1_9 = findViewById(R.id.btn_player1_9);

        b_player1_1.setTag("1");
        b_player1_2.setTag("2");
        b_player1_3.setTag("3");
        b_player1_4.setTag("4");
        b_player1_5.setTag("5");
        b_player1_6.setTag("6");
        b_player1_7.setTag("7");
        b_player1_8.setTag("8");
        b_player1_9.setTag("9");

        player1Buttons = new ArrayList<>();
        player1Buttons.add(b_player1_1);
        player1Buttons.add(b_player1_2);
        player1Buttons.add(b_player1_3);
        player1Buttons.add(b_player1_4);
        player1Buttons.add(b_player1_5);
        player1Buttons.add(b_player1_6);
        player1Buttons.add(b_player1_7);
        player1Buttons.add(b_player1_8);
        player1Buttons.add(b_player1_9);


        b_player2_1 = findViewById(R.id.btn_player2_1);
        b_player2_2 = findViewById(R.id.btn_player2_2);
        b_player2_3 = findViewById(R.id.btn_player2_3);
        b_player2_4 = findViewById(R.id.btn_player2_4);
        b_player2_5 = findViewById(R.id.btn_player2_5);
        b_player2_6 = findViewById(R.id.btn_player2_6);
        b_player2_7 = findViewById(R.id.btn_player2_7);
        b_player2_8 = findViewById(R.id.btn_player2_8);
        b_player2_9 = findViewById(R.id.btn_player2_9);

        b_player2_1.setTag("1");
        b_player2_2.setTag("2");
        b_player2_3.setTag("3");
        b_player2_4.setTag("4");
        b_player2_5.setTag("5");
        b_player2_6.setTag("6");
        b_player2_7.setTag("7");
        b_player2_8.setTag("8");
        b_player2_9.setTag("9");

        player2Buttons = new ArrayList<>();
        player2Buttons.add(b_player2_1);
        player2Buttons.add(b_player2_2);
        player2Buttons.add(b_player2_3);
        player2Buttons.add(b_player2_4);
        player2Buttons.add(b_player2_5);
        player2Buttons.add(b_player2_6);
        player2Buttons.add(b_player2_7);
        player2Buttons.add(b_player2_8);
        player2Buttons.add(b_player2_9);

        b_player1_roll1 = findViewById(R.id.btn_player1_roll_1);
        b_player1_roll2 = findViewById(R.id.btn_player1_roll_2);
        b_player2_roll1 = findViewById(R.id.btn_player2_roll_1);
        b_player2_roll2 = findViewById(R.id.btn_player2_roll_2);

        mViewDice1 = findViewById(R.id.dice_1);
        mViewDice2 = findViewById(R.id.dice_2);

        tv_p1_points = findViewById(R.id.tv_player1_points);
        tv_p2_points = findViewById(R.id.tv_player2_points);

        tv_status = findViewById(R.id.tv_status);

        combinations =new ArrayList<>();

        //disable player 1/player2 buttons

        for (Button button : player1Buttons) {
            button.setEnabled(false);
        }

        for (Button button : player2Buttons) {
            button.setEnabled(false);
        }

        //disable roll buttons except roll 2 button

        b_player1_roll1.setEnabled(false);
        b_player2_roll1.setEnabled(false);
        b_player2_roll2.setEnabled(false);

        for (Button button : player1Buttons) {
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //remove the tile
                    view.setVisibility(View.INVISIBLE);

                    // disable all tiles

                    for (Button button : player1Buttons) {
                        button.setEnabled(false);
                    }

                    //remove the tile value from the player points and from the current dice sum

                    diceSum = diceSum - Integer.parseInt(view.getTag().toString());
                    player1Points = player1Points - Integer.parseInt(view.getTag().toString());

                    //calculate the remaining tiles

                    calculateTiles(diceSum, player1Buttons);

                    //check if player finished

                    if (diceSum == 0) {
                        mViewDice1.setVisibility(View.GONE);
                        mViewDice2.setVisibility(View.GONE);

                        // enable only one or both dice according to the rules
                        if (b_player1_7.getVisibility() == View.VISIBLE ||
                                b_player1_8.getVisibility() == View.VISIBLE ||
                                b_player1_9.getVisibility() == View.VISIBLE) {
                            b_player1_roll1.setEnabled(false);
                            b_player1_roll2.setEnabled(true);
                        } else {
                            b_player1_roll1.setEnabled(true);
                            b_player1_roll2.setEnabled(true);
                        }

                        for (Button button : player1Buttons) {
                            button.setEnabled(false);
                        }

                    } else {
                        checkPlayer1End();
                    }

                }
            });
        }

        for (Button button : player2Buttons) {
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    //remove the tile
                    view.setVisibility(View.INVISIBLE);

                    // disable all tiles

                    for (Button button : player2Buttons) {
                        button.setEnabled(false);
                    }

                    //remove the tile value from the player points and from the current dice sum

                    diceSum = diceSum - Integer.parseInt(view.getTag().toString());
                    player2Points = player2Points - Integer.parseInt(view.getTag().toString());

                    //calculate the remaining tiles

                    calculateTiles(diceSum, player2Buttons);

                    //check if player finished

                    if (diceSum == 0) {
                        mViewDice1.setVisibility(View.GONE);
                        mViewDice2.setVisibility(View.GONE);

                        // enable only one or both dice according to the rules
                        if (b_player2_7.getVisibility() == View.VISIBLE ||
                                b_player2_8.getVisibility() == View.VISIBLE ||
                                b_player2_9.getVisibility() == View.VISIBLE) {
                            b_player2_roll1.setEnabled(false);
                            b_player2_roll2.setEnabled(true);
                        } else {
                            b_player2_roll1.setEnabled(true);
                            b_player2_roll2.setEnabled(true);
                        }

                        for (Button button : player2Buttons) {
                            button.setEnabled(false);
                        }

                    } else {
                        checkPlayer2End();
                    }

                }
            });
        }

        b_player1_roll1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                b_player1_roll1.setEnabled(false);
                b_player1_roll2.setEnabled(false);

                //show dice

                Random random = new Random();
                dice1Value = random.nextInt(6) + 1;
                diceSum = dice1Value;
                mViewDice1.setVisibility(View.VISIBLE);
                setDiceImages(dice1Value, mViewDice1);

                //calculate tiles
                calculateTiles(diceSum, player1Buttons);

                //check if the player is the winner
                checkPlayer1End();

            }
        });

        b_player1_roll2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                b_player1_roll1.setEnabled(false);
                b_player1_roll2.setEnabled(false);

                //show dice

                Random random = new Random();
                dice1Value = random.nextInt(6) + 1;
                dice2Value = random.nextInt(6) + 1;
                diceSum = dice1Value + dice2Value;
                mViewDice1.setVisibility(View.VISIBLE);
                mViewDice2.setVisibility(View.VISIBLE);
                setDiceImages(dice1Value, mViewDice1);
                setDiceImages(dice2Value, mViewDice2);
                //calculate tiles
                calculateTiles(diceSum, player1Buttons);

                //check if the player is the winner
                checkPlayer1End();

            }
        });

        b_player2_roll1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                b_player2_roll1.setEnabled(false);
                b_player2_roll2.setEnabled(false);

                //show dice

                Random random = new Random();
                dice1Value = random.nextInt(6) + 1;
                diceSum = dice1Value;
                mViewDice1.setVisibility(View.VISIBLE);
                setDiceImages(dice1Value, mViewDice1);

                //calculate tiles
                calculateTiles(diceSum, player2Buttons);

                //check if the player is the winner
                checkPlayer2End();
            }
        });

        b_player2_roll2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                b_player2_roll1.setEnabled(false);
                b_player2_roll2.setEnabled(false);

                //show dice

                Random random = new Random();
                dice1Value = random.nextInt(6) + 1;
                dice2Value = random.nextInt(6) + 1;
                diceSum = dice1Value + dice2Value;
                mViewDice1.setVisibility(View.VISIBLE);
                mViewDice2.setVisibility(View.VISIBLE);
                setDiceImages(dice1Value, mViewDice1);
                setDiceImages(dice2Value, mViewDice2);
                //calculate tiles
                calculateTiles(diceSum, player2Buttons);

                //check if the player is the winner
                checkPlayer2End();

            }
        });
    }

    private void setDiceImages(int number, ImageView imageView) {

        switch (number) {
            case 1:
                imageView.setImageResource(R.drawable.dice_1);
                break;
            case 2:
                imageView.setImageResource(R.drawable.dice_2);
                break;
            case 3:
                imageView.setImageResource(R.drawable.dice_3);
                break;
            case 4:
                imageView.setImageResource(R.drawable.dice_4);
                break;
            case 5:
                imageView.setImageResource(R.drawable.dice_5);
                break;
            case 6:
                imageView.setImageResource(R.drawable.dice_6);
                break;
        }

    }

    private void showDigits(ArrayList<Integer> numbers, int target, ArrayList<Integer> partial) {

        int s = 0;

        for (int x : partial) {
            s += x;
        }

        if (s == target) {
            for (int num : partial) {
                if (!combinations.contains(num)) {
                    combinations.add(num);
                }
            }
        }

        if (s >= target) {
            return;
        }

        for (int i = 0; i < numbers.size(); i++) {
            ArrayList<Integer> remaining = new ArrayList<>();
            int n = numbers.get(i);
            for (int j = i + 1; j < numbers.size(); j++) {
                remaining.add(numbers.get(j));
            }
            ArrayList<Integer> partial_rec = new ArrayList<>(partial);
            partial_rec.add(n);
            showDigits(remaining, target, partial_rec);
        }

    }


    private void showNumbers(ArrayList<Integer> numbers, int target) {
        combinations.clear();
        showDigits(numbers, target, new ArrayList<Integer>());
    }

    private void calculateTiles(int number, List<Button> buttons) {
        //save available tiles if they are enabled
        ArrayList<Integer> numbers = new ArrayList<>();
        for (Button button : buttons) {
            if (button.getVisibility() == View.VISIBLE) {
                numbers.add(Integer.parseInt(button.getTag().toString()));
            }
        }
        //calculate tiles
        showNumbers(numbers, number);

        //enable available tiles
        for (int num : combinations) {
            buttons.get(num - 1).setEnabled(true);
        }
    }


    private void checkPlayer1End() {
        //check if there are available tiles for click
        boolean player1Trun = true;
        for (Button button : player1Buttons) {
            if (button.isEnabled()) {
                player1Trun = false;
            }
        }

        //swithc turn if no available tiles
        if (player1Trun) {
            tv_p1_points.setText("" + player1Points);
            tv_status.setText("Player 2 roll the dice !");

            b_player2_roll2.setEnabled(true);
        }

        //check if player 1 wins the game directly
        boolean checkForWin = true;
        for (Button button : player1Buttons) {
            if (button.getVisibility() == View.VISIBLE) {
                checkForWin = false;
            }
        }

        if (checkForWin) {
            tv_status.setText("Player 1 wins !");
        }

    }

    private void checkPlayer2End() {
        //check if there are available tiles for click
        boolean playerTrun = true;
        for (Button button : player2Buttons) {
            if (button.isEnabled()) {
                playerTrun = false;
            }
        }

        //end game if no available tiles to click
        if (playerTrun) {
            tv_p2_points.setText("" + player2Points);
            if (player1Points < player2Points) {
                tv_status.setText("Player 1 wins !");
            } else if (player2Points < player1Points) {
                tv_status.setText("Player 2 wins !");
            } else {
                tv_status.setText("Draw");
            }
        }

        //check if player 2 wins the game directly
        boolean checkForWin = true;
        for (Button button : player2Buttons) {
            if (button.getVisibility() == View.VISIBLE) {
                checkForWin = false;
            }
        }

        if (checkForWin) {
            tv_status.setText("Player 2 wins !");
        }

    }
}
