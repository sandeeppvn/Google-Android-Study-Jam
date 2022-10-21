package com.example.sandeep.scarnesdice;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.util.Log;
import java.util.Random;
import java.util.logging.LogRecord;

public class MainActivity extends AppCompatActivity {
    private String TAG="Error here";
   static int userScore=0;
    static int  computerScore=0;
    static int local=0;

    private Handler handler=new Handler();

    Button roll;
    Button hold;
    Button reset;

    TextView user;
    TextView comp;
    TextView loc;

    ImageView img;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        roll = (Button)findViewById(R.id.roll);
        hold = (Button)findViewById(R.id.hold);
        reset = (Button)findViewById(R.id.reset);

        user=(TextView)findViewById(R.id.userScore);
        comp=(TextView)findViewById(R.id.computerScore);

        loc=(TextView)findViewById(R.id.localScore);

        loc.setText("Local Score:" + String.valueOf(local));
        user.setText("User Score:" + String.valueOf(userScore));
        comp.setText("Computer Score: "+String.valueOf(computerScore));


        img= (ImageView)findViewById(R.id.pic);

        userPlay();

    }
    public int roll(){
            Random rnd = new Random();
            int temp = rnd.nextInt(5) + 1;
            String str = "dice" + (temp);
            loc.setText("Local Score:" + String.valueOf(local));
            user.setText("User Score:" + String.valueOf(userScore));
            comp.setText("Computer Score: "+String.valueOf(computerScore));
            img.setImageResource(getResources().getIdentifier(str, "drawable", getPackageName()));

//          Log.i(TAG,"roll 1");
        return temp;

        }


    public void stop()
    {

    }

    public void hold(){

        user.setText("User Score:" + String.valueOf(userScore));
        comp.setText("Computer Score: "+String.valueOf(computerScore));
            userScore += local;
            local = 0;
        loc.setText("Local Score:" + String.valueOf(local));

            if (userScore >= 100) {
                loc.setText("USER WINS");
                stop();
            }
    }
    public void reset(){
        userScore=0;
        local=0;
        computerScore=0;
        loc.setText("Local Score:"+String.valueOf(local));
        user.setText("User Score:"+String.valueOf(userScore));
        comp.setText("Computer Score:"+String.valueOf(computerScore));
    }

    int val;

    public void comp()
    {
        local=0;
        while(local<10)
        {

            val=roll();
            if (val == 1) {
                local = 0;
                loc.setText("Local Score:" + String.valueOf(local));
                user.setText("User Score:" + String.valueOf(userScore));
                comp.setText("Computer Score: " + String.valueOf(computerScore));
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        userPlay();
                    }
                }, 500);

            } else {
                local += val;

                loc.setText("Local Score:" + String.valueOf(local));
                user.setText("User Score:" + String.valueOf(userScore));
                comp.setText("Computer Score: " + String.valueOf(computerScore));
            }

            handler.postDelayed(new Runnable() {
                @Override
                public void run() {

                    loc.setText("Local Score:" + String.valueOf(local));
                    user.setText("User Score:" + String.valueOf(userScore));
                    comp.setText("Computer Score: " + String.valueOf(computerScore));

                }
            }, 500);
        }
        computerScore+=local;
        local=0;
        loc.setText("Local Score:" + String.valueOf(local));
        user.setText("User Score:" + String.valueOf(userScore));
        comp.setText("Computer Score: "+String.valueOf(computerScore));
    }

    public void userPlay()
    {
        local=0;
        roll.setOnClickListener(
                new View.OnClickListener()
                {

                    public void onClick(View v)
                    {
                        int val=roll();
                        if(val==1) {
                            local = 0;
                            loc.setText("Local Score:" + String.valueOf(local));
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    comp();
                                }
                            }, 500);
                        }
                        else
                        {
                            local+=val;
                            loc.setText("Local Score:" + String.valueOf(local));

                        }

                    }
                }
        );


        hold.setOnClickListener(
                new View.OnClickListener()
                {
                    public void onClick(View v)
                    {
                        userScore+=local;
                        local=0;
                        loc.setText("Local Score:" + String.valueOf(local));
                        user.setText("User Score:" + String.valueOf(userScore));
                        comp.setText("Computer Score: "+String.valueOf(computerScore));
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                comp();
                            }
                        }, 500);
                    }
                }
        );

        reset.setOnClickListener(
                new View.OnClickListener()
                {
                    public void onClick(View v)
                    {
                        reset();

                    }
                }
        );

    }



}
