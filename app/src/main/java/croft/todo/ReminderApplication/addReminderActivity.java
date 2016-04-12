package croft.todo.ReminderApplication;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import croft.todo.R;

public class addReminderActivity extends AppCompatActivity {


    private Button addReminder;
    private EditText inputTitle;
    private Button inputDate;
    private EditText inputDescription;
    private Button calendarDialogButton;
    static Calendar calendar = Calendar.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_reminder);
        setTitle("Add Reminder");

        addReminder = (Button) findViewById(R.id.addReminderButton);
        addReminder.setEnabled(false);
        addReminder.setHighlightColor(Color.LTGRAY);

        inputTitle = (EditText) findViewById(R.id.inputTitle);
        inputDate = (Button) findViewById(R.id.inputDate);
        inputDescription = (EditText) findViewById(R.id.inputDescription);

        inputTitle.addTextChangedListener(w);
        inputDescription.addTextChangedListener(w);


    }

    private final TextWatcher w = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            //TODO: set limits for the number of characters for input and output.
            if (inputTitle.getText().toString().length() > 0 &&
                    inputDate.getText().toString() != "Press to choose date" &&
                    inputDescription.getText().toString().length() > 0) {
                addReminder.setEnabled(true);
                addReminder.setHighlightColor(Color.CYAN);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {
            //allows if requirements are met


        }
    };

    public void onClick(View v) {
        Toast t;

        switch(v.getId()) {
            case R.id.addReminderButton:
                String submittedtitle = inputTitle.getText().toString();
                String submitteddescription = inputDescription.getText().toString();
                String submittedDate = inputDate.getText().toString();

                Reminder submittedReminder = new Reminder(submittedtitle, submitteddescription, submittedDate);

                ViewListActivity listView = new ViewListActivity();
                listView.getReminderList().add(submittedReminder);

                t = Toast.makeText(getApplicationContext(), "Reminder created", Toast.LENGTH_SHORT);

                Intent i = new Intent(this, ViewListActivity.class);
                i.putExtra("restroom", submittedReminder);
                startActivity(i);
                finish();

                break;


            case R.id.viewCompleteCheck:
               /* if(v.isSelected()){
                    Reminder.totalIncomplete ++;
                }else{
                    Reminder.totalIncomplete --;
                }
                ViewListActivity a = new ViewListActivity();
                a.updateIncompleteCount();
                break; */
                t = Toast.makeText(getApplicationContext(), Integer.toString(v.getId()), Toast.LENGTH_SHORT);
                t.show();
                break;

            case R.id.inputDate:
                final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear,
                                          int dayOfMonth) {

                        calendar.set(Calendar.YEAR, year);
                        calendar.set(Calendar.MONTH, monthOfYear);
                        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        updateLabel();

                    }
                };

                inputDate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        new DatePickerDialog(addReminderActivity.this, date, calendar
                                .get(Calendar.YEAR),
                                calendar.get(Calendar.MONTH),
                                calendar.get(Calendar.DAY_OF_MONTH)).show();
                    }
                });
                break;

            default:
                t = Toast.makeText(getApplicationContext(), "Unassigned button", Toast.LENGTH_SHORT);
                t.show();


        }



    }


    private void updateLabel() {

        String dateFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.ENGLISH);

        inputDate.setText(sdf.format(calendar.getTime()));
    }

    public void createReminder(){



        //Toast toast = Toast.makeText(context, text, duration);
        // toast.show();
    }
}
