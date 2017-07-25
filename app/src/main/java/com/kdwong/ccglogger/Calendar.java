package com.kdwong.ccglogger;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.icu.util.GregorianCalendar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class Calendar extends AppCompatActivity {

    String time;
    RadioButton practiceBoolean_button;
    private RadioButton yes_button, no_button;
    public int selectedId;
    final Context context = this;
    public boolean practiceBoolean;
    CalendarView calendar;

    @TargetApi(24)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        yes_button = (RadioButton) findViewById(R.id.yes_button);
        no_button = (RadioButton) findViewById(R.id.no_button);

        calendar = (CalendarView) findViewById(R.id.calendarView);
        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener(){

            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {

                //Toast.makeText(getApplicationContext(), dayOfMonth +"/"+month+"/"+ year,Toast.LENGTH_LONG).show();
                GregorianCalendar date = new GregorianCalendar(year,month,dayOfMonth);

                LayoutInflater inflater = getLayoutInflater();
                View alertLayout = inflater.inflate(R.layout.logpopup_layout, null);
                final RadioGroup logpopup_radiogroup = (RadioGroup) alertLayout.findViewById(R.id.logpopup_radiogroup);

                final EditText practiceLength_editText = (EditText) alertLayout.findViewById(R.id.practiceLength_editText);

                AlertDialog.Builder alert = new AlertDialog.Builder(context);
                alert.setTitle("Practice Log");
                alert.setView(alertLayout);

                alert.setPositiveButton("Enter", new DialogInterface.OnClickListener(){

                    @Override
                    public void onClick(DialogInterface dialog, int which){
                        time = practiceLength_editText.getText().toString();
                        Toast.makeText(getApplicationContext(),time,Toast.LENGTH_LONG).show();

                        selectedId = logpopup_radiogroup.getCheckedRadioButtonId();
                        //Log.d("Blah",String.valueOf(((RadioButton) findViewById(R.id.yes_button)).getId()));



                        /*
                        practiceBoolean_button = (RadioButton)findViewById(selectedId);
                        CharSequence practiceBoolean_answer = ((RadioButton)findViewById(selectedId)).getText();

                        //Toast.makeText(getApplicationContext(), practiceBoolean_answer, Toast.LENGTH_SHORT).show();

*/
                        if(selectedId == R.id.yes_button){
                            Toast.makeText(getApplicationContext(),"yes",Toast.LENGTH_LONG).show();
                            practiceBoolean =true;
                        } else if(selectedId == R.id.no_button){
                            Toast.makeText(getApplicationContext(),"no",Toast.LENGTH_LONG).show();
                            practiceBoolean = false;
                        }else{

                        }
                    }
                });
                AlertDialog dialog = alert.create();
                dialog.show();


            }});
    }
}
