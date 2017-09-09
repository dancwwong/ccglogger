package com.kdwong.ccglogger;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import java.sql.Date;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private TextView text;
    private Student student = Student.me();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        text = (TextView) findViewById(R.id.welcome_message);
        text.setText("Welcome "+ student.getName()+"!");
        /*
        Button calendarBtn = (Button) findViewById(R.id.calendar_button);

        calendarBtn.setOnClickListener(
                new View.OnClickListener(){
                    @Override
                    public void onClick(View view) {
                        Intent i = new Intent(getApplicationContext(), Log_form.class);
                        startActivity(i);
                    }
                }
        );*/

        ImageButton check_button = (ImageButton) findViewById(R.id.check_button);
        check_button.setOnClickListener(
                new View.OnClickListener(){
            @Override
                    public void onClick(View view){
                findViewById(R.id.check_button).setBackgroundResource(R.drawable.affirmative_button);


                if (student.missedDays() == 1) {
                    student.incrementPracticeStreak();
                    student.updateLastPracticeDate();
                } else if (student.missedDays() == 0) {
                    AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
                    alertDialog.setTitle("ERROR");
                    alertDialog.setMessage("Practice already submitted for today.");
                    alertDialog.show();
                }else{
                    AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
                    alertDialog.setTitle("ALERT");
                    alertDialog.setMessage("You missed practice days. Ask your parents for help if this is a mistake.");
                    alertDialog.show();

                }



                student.writeToDatabase();
            }
        }


        );
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

    private String getUserId() {
        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            //Go to login
            return null;
        }
        else{
            String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
            return uid;
        }
    }
}
