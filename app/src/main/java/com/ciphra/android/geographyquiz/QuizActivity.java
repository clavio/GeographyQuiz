package com.ciphra.android.geographyquiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends AppCompatActivity {


    private static final String TAG = "QuizActivity";
    private static final String KEY_INDEX = "index";
    private static final int REQUEST_CODE_CHEAT = 0;

    private Button mTrueButton;
    private Button mFalseButton;
    private ImageButton mNextButton;
    private ImageButton mPreviousButton;
    private Button mCheatButton;

    private TextView mQuestionTextView;

    private Question[] mQuestionBank = new Question[] {
      new Question(R.string.question_oceans, true),
      new Question(R.string.question_mideast, false),
      new Question(R.string.question_africa, false),
      new Question(R.string.question_americas, true),
      new Question(R.string.question_asia, true),

    };

    private boolean[] disabledQuestions;

    private int correctAnswers = 0;
    private int incorrectAnswers = 0;

    private int mCurrentIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate(Bundle) called");
        setContentView(R.layout.activity_quiz);

        disabledQuestions = new boolean[mQuestionBank.length];
        if (savedInstanceState != null) {
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0);

        }
        mTrueButton = (Button) findViewById(R.id.true_button);
        mFalseButton = (Button) findViewById(R.id.false_button);
        mCheatButton = (Button) findViewById(R.id.cheat_button);
        mCheatButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();
                Intent intent = CheatActivity.newIntent(QuizActivity.this, answerIsTrue);
                startActivityForResult(intent, REQUEST_CODE_CHEAT);
            }
        });

       // checkDisabled();
        mQuestionTextView = (TextView) findViewById(R.id.question_text_view);
        mQuestionTextView.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                nextQuestion();
            }
        });
        updateQuestion();
        mTrueButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
               checkAnswer(true);

            }
        });
        mFalseButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
               checkAnswer(false);
            }
        });

        mNextButton = (ImageButton) findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                nextQuestion();
            }
        });

        mPreviousButton = (ImageButton) findViewById(R.id.previous_button);
        mPreviousButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                previousQuestion();
            }
        });
    }

    @Override
    public void onStart(){
        super.onStart();
        Log.d(TAG, "onStart() called");
    }
    @Override
    public void onResume(){
        super.onResume();
        Log.d(TAG, "onResume() called");
    }

    @Override
    public void onPause(){
        super.onPause();
        Log.d(TAG, "onPause() called");
    }

    @Override
    public void onStop(){
        super.onStop();
        Log.d(TAG, "onStop() called");
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        Log.d(TAG, "onDestroy() called");
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);
        Log.i(TAG, "onSaveInstanceState");
        savedInstanceState.putInt(KEY_INDEX, mCurrentIndex);

    }

    private void nextQuestion() {
        mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
        updateQuestion();
    }

    private void previousQuestion(){
        mCurrentIndex--;
        if(mCurrentIndex < 0){
            mCurrentIndex = mCurrentIndex + mQuestionBank.length;
        }
        updateQuestion();
    }

    private void updateQuestion(){
        int question = mQuestionBank[mCurrentIndex].getTextResId();
        mQuestionTextView.setText(question);
     //   checkDisabled();
    }

    private void checkAnswer(boolean userPressedTrue){
        boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();
        int messageResId = 0;
        disabledQuestions[mCurrentIndex] = true;
      //  checkDisabled();
        if(userPressedTrue == answerIsTrue){
            ++correctAnswers;
            messageResId = R.string.correct_toast;
        }
        else{
            ++incorrectAnswers;
            messageResId = R.string.incorrect_toast;
        }
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT);
      //  grade();
    }

    public void grade(){
        if(correctAnswers + incorrectAnswers  == mQuestionBank.length){
            double percentage = correctAnswers/mQuestionBank.length;
            Toast.makeText(this, String.valueOf(percentage), Toast.LENGTH_SHORT);
            Log.e("adsfdsa0", String.valueOf(percentage));

        }
    }

    private void checkDisabled(){
        if(disabledQuestions[mCurrentIndex]){
            mTrueButton.setEnabled(false);
            mFalseButton.setEnabled(false);
        }
        else{
            mTrueButton.setEnabled(true);
            mFalseButton.setEnabled(true);
        }
    }
}


