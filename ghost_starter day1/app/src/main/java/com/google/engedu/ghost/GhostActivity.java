/*

        NAME: SANDEEP PVN
        email:sandeeppvn@gmail.com

 */


package com.google.engedu.ghost;

import android.app.Service;
import android.content.Context;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;

import java.util.Random;


public class GhostActivity extends AppCompatActivity  {
    private static final String COMPUTER_TURN = "Computer's turn";
    private static final String USER_TURN = "Your turn";
    private GhostDictionary dictionary;
    private boolean userTurn = false;
    private Random random = new Random();

    private Handler handler=new Handler();
    private InputMethodManager imm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ghost);
        AssetManager assetManager = getAssets();
        try {
            InputStream inputStream = assetManager.open("words.txt");
            dictionary = new SimpleDictionary(inputStream);
        } catch (IOException e) {
            Toast toast = Toast.makeText(this, "Could not load dictionary", Toast.LENGTH_LONG);
            toast.show();
        }
        imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        onStart(null);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_ghost, menu);
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

    public void reset()
    {
        label.setText("");
        text.setText("");
        if(imm != null){
            imm.toggleSoftInput(0, InputMethodManager.HIDE_IMPLICIT_ONLY);
        }
        challenge.setVisibility(View.INVISIBLE);
        restart.setVisibility(View.INVISIBLE);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                onStart(null);
            }
        },2000);
    }

   public void challenge()
    {
        if(  dictionary.isWord(fragment) ) {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    text.setText(fragment);
                    fin.setText("User Wins!!!");
                    reset();
                }
            }, 500);
        }
        else
        {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    text.setText(fragment);
                    fin.setText("Computer Wins!!!");
                    reset();
                }
            }, 500);
        }

    }

    public void userPlay(char ch)
    {
        restart.setOnClickListener(
                new View.OnClickListener()
                {

                    public void onClick(View v)
                    {
                        reset();
                    }
                }
        );

        challenge.setOnClickListener(
                new View.OnClickListener()
                {

                    public void onClick(View v)
                    {
                        challenge();
                    }
                }
        );


        if(fragment==null)
            fragment=String.valueOf(ch);
        else
            fragment=fragment+String.valueOf(ch);
        text.setText(fragment);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                compPlay();
            }
        },500);
    }
    public void compPlay()
    {

        if( dictionary.isWord(fragment) || (dictionary.getAnyWordStartingWith(fragment)==null) )
        {

                     handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            fin.setText("Computer Wins!!!");
                            text.setText(fragment);
                            reset();
                        }
                    },500);



        }
        else
        {
            String s=dictionary.getAnyWordStartingWith(fragment);
            fragment=fragment+String.valueOf(s.charAt(fragment.length()));

            text.setText(fragment);
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    text.setText(fragment);
                    userTurn=true;
                }
            },500);

        }
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if(keyCode>= event.KEYCODE_A && keyCode<=event.KEYCODE_Z)
        {
            char unicodeChar = (char)event.getUnicodeChar();
            if(userTurn) {
                userTurn = false;
                userPlay(unicodeChar);
            }


            return true;
        }
        else
        {
            fin.setText("INVALID INPUT, START OVER");
            reset();
        }

        return super.onKeyUp(keyCode,event);


    };

    /**
     * Handler for the "Reset" button.
     * Randomly determines whether the game starts with a user turn or a computer turn.
     * @param view
     * @return true
     */
    private String fragment;

    @Override
    public void onSaveInstanceState(Bundle outState) {

        outState.putString("textView", text.getText().toString());
        outState.putString("fragSave", fragment);
        outState.putBoolean("userState", userTurn);

        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        text.setText(savedInstanceState.getString("textView"));
        fragment=savedInstanceState.getString("fragSave");
        userTurn=savedInstanceState.getBoolean("userState");
    }


    TextView text;
    TextView label;
    TextView fin;
    Button challenge;
    Button restart;
    //TextView answer;
    public boolean onStart(View view) {
        if(imm != null){
            imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0);
        }
        //userTurn = random.nextBoolean();
        userTurn=true;
        text= (TextView) findViewById(R.id.ghostText);
        text.setText("");
        label = (TextView) findViewById(R.id.gameStatus);
        label.setText("");
        fin=(TextView) findViewById(R.id.WinText);
        fin.setText("");
        fragment=null;
        challenge=(Button)findViewById(R.id.challenge);
        restart=(Button)findViewById(R.id.restart);
        //answer=(TextView) findViewById(R.id.Answer);
        //answer.setText("");
        challenge.setVisibility(View.VISIBLE);
        restart.setVisibility(View.VISIBLE);

        if (userTurn) {
            label.setText(USER_TURN);

        } else {
            label.setText(COMPUTER_TURN);

        }


        return true;
    }

    private void computerTurn() {
        TextView label = (TextView) findViewById(R.id.gameStatus);
        // Do computer turn stuff then make it the user's turn again
        userTurn = true;
        label.setText(USER_TURN);
    }
}
