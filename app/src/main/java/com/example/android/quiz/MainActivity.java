package com.example.android.quiz;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import org.w3c.dom.Text;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    String userName, results;
    private int totalPoints = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method operates when SUBMIT button is clicked
     */
    public void onSubmit(View view) {
        boolean hasName = nameEntry();
        //To check whether the user has entered their name
        if (!hasName) {
            Toast.makeText(MainActivity.this, "Please fill in your name ", Toast.LENGTH_SHORT).show();
            return;
        }
        checkAnswer1();
        checkAnswer2();
        checkAnswer3();
        checkAnswer4();
        checkAnswer5();
        results = "Your total points is " + totalPoints + " out of 5";
        Toast.makeText(MainActivity.this, results, Toast.LENGTH_LONG).show();
        changeButtonToEmail();
    }

    /**
     * This method checks whether the user entered the name
     */
    public boolean nameEntry() {
        EditText name = (EditText) findViewById(R.id.name_edit_text);
        userName = String.valueOf(name.getText());
        boolean nameEntered = false;
        if (userName.trim().length() > 0) {
            nameEntered = true;
        }
        return nameEntered;
    }

    /**
     * This method checks whether the question1 is correct
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    private void checkAnswer1() {
        EditText question1 = (EditText) findViewById(R.id.user_answer1);
        String userAnswer1 = (question1.getText().toString());
        Log.v("MainActivity:", "user Answer 1:" + userAnswer1);
        if (userAnswer1.trim().equalsIgnoreCase(getString(R.string.correct_answer1))) {
                TextView displayCorrect1 = (TextView) findViewById(R.id.correct_answer1);
            displayCorrect1.setText(getString(R.string.display_correct));
            displayCorrect1.setTextColor(Color.GREEN);
            totalPoints = totalPoints + 1;
        } else {
            TextView displayWrong1 = (TextView) findViewById(R.id.correct_answer1);
            displayWrong1.setText("Wrong!:(   Ans: International Business Machines");
            displayWrong1.setTextColor(Color.RED);
        }
    }

    /**
     * This method checks whether the question 2  is correct
     */
    private void checkAnswer2() {
        EditText question2 = (EditText) findViewById(R.id.user_answer2);
        String userAnswer2 = String.valueOf(question2.getText());
        Log.v("MainActivity:", "user Answer 2:" + userAnswer2);
        if (userAnswer2.trim().equalsIgnoreCase(getString(R.string.correct_answer2))) {
            TextView displayCorrect2 = (TextView) findViewById(R.id.correct_answer2);
            displayCorrect2.setText(getString(R.string.display_correct));
            displayCorrect2.setTextColor(Color.GREEN);
            totalPoints = totalPoints + 1;
        } else {
            TextView displayWrong2 = (TextView) findViewById(R.id.correct_answer2);
            displayWrong2.setText("Wrong!:(   Ans:World Wide Web");
            displayWrong2.setTextColor(Color.RED);
        }
    }

    /**
     * This method checks whether the question 3  is correct
     */
    private void checkAnswer3() {
        RadioButton q3option2 = (RadioButton) findViewById(R.id.q3_opt2_radio_button);
        if (q3option2.isChecked()) {
            TextView displayCorrect3 = (TextView) findViewById(R.id.correct_answer3);
            displayCorrect3.setText(getString(R.string.display_correct));
            displayCorrect3.setTextColor(Color.GREEN);
            totalPoints = totalPoints + 1;
        } else {
            TextView displayWrong3 = (TextView) findViewById(R.id.correct_answer3);
            displayWrong3.setText("Wrong! :( Ans:Lewis Carroll");
            displayWrong3.setTextColor(Color.RED);
        }
    }

    /**
     * This method checks whether the question 4  is correct
     */
    private void checkAnswer4() {
        RadioButton q4option2 = (RadioButton) findViewById(R.id.q4_opt2_radio_button);
        if (q4option2.isChecked()) {
            TextView displayCorrect4 = (TextView) findViewById(R.id.correct_answer4);
            displayCorrect4.setText(getString(R.string.display_correct));
            displayCorrect4.setTextColor(Color.GREEN);
            totalPoints = totalPoints + 1;
        } else {
            TextView displayWrong4 = (TextView) findViewById(R.id.correct_answer4);
            displayWrong4.setText("Wrong! :( Ans:No");
            displayWrong4.setTextColor(Color.RED);
        }
    }

    /**
     * This method checks whether the question 5  is correct
     */
    private void checkAnswer5() {
        CheckBox q5option1 = (CheckBox) findViewById(R.id.q5_option1_check_box);
        CheckBox q5option2 = (CheckBox) findViewById(R.id.q5_option2_check_box);
        if (q5option1.isChecked() && q5option2.isChecked()) {
            TextView displayCorrect5 = (TextView) findViewById(R.id.correct_answer5);
            displayCorrect5.setText(getString(R.string.display_correct));
            displayCorrect5.setTextColor(Color.GREEN);
            totalPoints = totalPoints + 1;
        } else {
            TextView displayWrong5 = (TextView) findViewById(R.id.correct_answer5);
            displayWrong5.setText("Wrong! :( Ans:Whale , Elephant");
            displayWrong5.setTextColor(Color.RED);
        }
    }
    /**
     * This method changes the Submit button to Email Results button.
     */

    private void changeButtonToEmail() {
        final Button buttonChange = (Button) findViewById(R.id.submit_button);
        buttonChange.setTag(1);
        buttonChange.setText("Email Results");
        buttonChange.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                final int status = (Integer) view.getTag();
                if (status == 1) {
                    sendEmail();
                }
            }
        });

    }

    /**
     * This method sends the email results when the Email Results Button is clicked
     */
    public void sendEmail() {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        String subject = "Quiz results of " + userName;
        String message = "Total points is " + totalPoints + " out of 5";
        TextView answer1 = (TextView) findViewById(R.id.correct_answer1);
        String ans1 = (String) answer1.getText();
        TextView answer2 = (TextView) findViewById(R.id.correct_answer2);
        String ans2 = (String) answer2.getText();
        TextView answer3 = (TextView) findViewById(R.id.correct_answer3);
        String ans3 = (String) answer3.getText();
        TextView answer4 = (TextView) findViewById(R.id.correct_answer4);
        String ans4 = (String) answer4.getText();
        TextView answer5 = (TextView) findViewById(R.id.correct_answer5);
        String ans5 = (String) answer5.getText();
        message = message + "\n\nQuestion 1:" + getString(R.string.question1_description) + "\n -" + ans1
                + "\n\nQuestion 2: " + getString(R.string.question2_description) + "\n -" + ans2
                + "\n\nQuestion 3: " + getString(R.string.question3_description) + "\n -" + ans3
                + "\n\nQuestion 4: " + getString(R.string.question4_description) + "\n -" + ans4
                + "\n\nQuestion 5: " + getString(R.string.question5_description) + "\n -" + ans5;
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, message);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }


}
