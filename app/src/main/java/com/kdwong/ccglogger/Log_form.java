package com.kdwong.ccglogger;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class Log_form extends AppCompatActivity {

    String time;
    RadioButton practiceBoolean_button;
    private RadioButton yes_button, no_button;
    public int selectedId;
    final Context context = this;
    public boolean practiceBoolean;
    CalendarView calendar;
    public long streakString;
    public int streak;

    public String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();


    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference mRootRef = database.getReference();
    DatabaseReference mPracticeStreak = mRootRef.child("students").child(uid).child("practiceStreak");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_form);

        yes_button = (RadioButton) findViewById(R.id.yes_button1);
        no_button = (RadioButton) findViewById(R.id.no_button1);
        final RadioGroup logpopup_radiogroup = (RadioGroup) findViewById(R.id.logpopup_radiogroup1);

        final EditText practiceLength_editText = (EditText) findViewById(R.id.practiceLength_editText1);


        Button logBtn = (Button) findViewById(R.id.log_form_submit);


        mPracticeStreak.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                streakString = (Long) dataSnapshot.getValue();
                Logger logger = Logger.getInstance();
//                logger.updateStreak(streakString);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(),"An unexpected error occurred",Toast.LENGTH_LONG).show();

            }
        });

            streak=(int) streakString;


        logBtn.setOnClickListener(
                new View.OnClickListener(){
                    @Override
                    public void onClick(View view) {
                        time = practiceLength_editText.getText().toString();
                        //Toast.makeText(getApplicationContext(),time,Toast.LENGTH_LONG).show();

                        selectedId = logpopup_radiogroup.getCheckedRadioButtonId();
                        //Log.d("Blah",String.valueOf(((RadioButton) findViewById(R.id.yes_button)).getId()));



                        /*
                        practiceBoolean_button = (RadioButton)findViewById(selectedId);
                        CharSequence practiceBoolean_answer = ((RadioButton)findViewById(selectedId)).getText();

                        //Toast.makeText(getApplicationContext(), practiceBoolean_answer, Toast.LENGTH_SHORT).show();

*/


                        if(selectedId == R.id.yes_button1){
                            Toast.makeText(getApplicationContext(),"yes",Toast.LENGTH_LONG).show();
                            practiceBoolean =true;
                            streak++;
                            mPracticeStreak.setValue(streak);

                        } else if(selectedId == R.id.no_button1){
                            Toast.makeText(getApplicationContext(),"no",Toast.LENGTH_LONG).show();
                            practiceBoolean = false;
                            streak =0;
                            mPracticeStreak.setValue(streak);

                        }else{

                        }
                    }
                }
        );
    }
}

