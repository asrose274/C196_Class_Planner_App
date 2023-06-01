package com.example.aaronrosec196.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.aaronrosec196.Database.Repository;
import com.example.aaronrosec196.Entities.Course;
import com.example.aaronrosec196.Entities.Term;
import com.example.aaronrosec196.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class addedit_courses extends AppCompatActivity {
    Repository repository = new Repository(getApplication());


    final Calendar calendar = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener startDate;
    DatePickerDialog.OnDateSetListener endDate;
    Spinner termSpinner;
    EditText courseName;
    Spinner courseStatus;
    EditText instructorName;
    EditText instructorPhone;
    EditText instructorEmail;
    EditText notes;
    Button courseStart;
    Button courseEnd;

    int courseId;
    String editCourseName;
    String editCourseStatus;
    String editStart;
    String editEnd;
    String editInstructorName;
    String editInstructorPhone;
    String editInstructorEmail;
    String editNote;
    public static int currentTermId;
    String dateFormat = "MM/dd/yy";
    SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.US);





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addedit_course);



        courseId=getIntent().getIntExtra("courseId", -1);
        editCourseName=getIntent().getStringExtra("courseName");
        editCourseStatus=getIntent().getStringExtra("status");
        editStart=getIntent().getStringExtra("start");
        editEnd=getIntent().getStringExtra("end");
        editInstructorName=getIntent().getStringExtra("instructor");
        editInstructorPhone=getIntent().getStringExtra("instructor_phone");
        editInstructorEmail=getIntent().getStringExtra("instructor_email");
        editNote=getIntent().getStringExtra("note");
        currentTermId=getIntent().getIntExtra("termId", -1);



        //Finding all GUI elements
        courseName = findViewById(R.id.courseTitle);
        courseStatus = findViewById(R.id.statusSpinner);
        instructorName = findViewById(R.id.instructorName);
        instructorPhone = findViewById(R.id.instructorPhone);
        instructorEmail = findViewById(R.id.instructorEmail);
        notes = findViewById(R.id.courseNote);
        courseStart = findViewById(R.id.courseStart);
        courseEnd = findViewById(R.id.courseEnd);
        termSpinner = (Spinner) findViewById(R.id.termSelectSpinner);

        //setting existing values if they exist
        courseName.setText(editCourseName);
        instructorName.setText(editInstructorName);
        instructorPhone.setText(editInstructorPhone);
        instructorEmail.setText(editInstructorEmail);
        notes.setText(editNote);


        if(editStart==null){
            courseStart.setText("Start Date");
        }
        else {
            courseStart.setText(editStart);
        }


        if(editEnd==null){
            courseEnd.setText("End Date");
        }
        else {
            courseEnd.setText(editEnd);
        }


        ArrayList<Term> allTerms = (ArrayList<Term>) repository.getAllTerms();
        ArrayAdapter<Term> adapter =new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,allTerms);adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        termSpinner.setAdapter(adapter);
        if (currentTermId != -1){
            for (Term term: allTerms) {
                if (term.getTermId() == currentTermId) {
                    termSpinner.setSelection(adapter.getPosition(term));
                    break;
                }
            }

        }

        ArrayAdapter<CharSequence> statusAdapter=ArrayAdapter.createFromResource(this,R.array.course_status, android.R.layout.simple_spinner_item);
        courseStatus.setAdapter(statusAdapter);
        if (currentTermId != -1){
            courseStatus.setSelection(statusAdapter.getPosition(editCourseStatus));
        }



        courseStart.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Date date;
                String info=courseStart.getText().toString();
                try{
                    calendar.setTime(sdf.parse(info));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                new DatePickerDialog(addedit_courses.this,startDate,calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        courseEnd.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Date date;
                String info=courseEnd.getText().toString();
                try{
                    calendar.setTime(sdf.parse(info));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                new DatePickerDialog(addedit_courses.this,endDate,calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        startDate = new DatePickerDialog.OnDateSetListener(){

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, monthOfYear);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);


                updateStartLabel();
            }
        };
        endDate = new DatePickerDialog.OnDateSetListener(){

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,int dayOfMonth) {

                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, monthOfYear);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                updateEndLabel();
            }
        };

    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_coursedetail, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){

        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            case R.id.shareCourse:

                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, notes.getText().toString());
                sendIntent.putExtra(Intent.EXTRA_TITLE, "Notes for " + courseName.getText());
                sendIntent.setType("text/plain");
                Intent shareIntent=Intent.createChooser(sendIntent, null);
                startActivity(shareIntent);
                return true;
            case R.id.notifyCourse:
                String notifyStartDate = courseStart.getText().toString();
                String notifyEndDate = courseEnd.getText().toString();
                Date notifyStart = null;
                Date notifyEnd = null;
                try {
                    notifyStart = sdf.parse(notifyStartDate);
                    notifyEnd= sdf.parse(notifyEndDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Long startTrigger=notifyStart.getTime();
                Long endTrigger=notifyEnd.getTime();
                Intent startIntent=new Intent(addedit_courses.this, MyReceiver.class);
                Intent endIntent=new Intent(addedit_courses.this, MyReceiver.class);
                startIntent.putExtra("key", courseName.getText() + " starts today");
                endIntent.putExtra("key", courseName.getText() + " ends today");
                PendingIntent startSender = PendingIntent.getBroadcast(addedit_courses.this, MainActivity.numAlert++ ,startIntent, 0);
                PendingIntent endSender = PendingIntent.getBroadcast(addedit_courses.this, MainActivity.numAlert++ ,endIntent, 0);
                AlarmManager alarmManagerStart=(AlarmManager) getSystemService(Context.ALARM_SERVICE);
                AlarmManager alarmManagerEnd= (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                alarmManagerStart.set(AlarmManager.RTC_WAKEUP, startTrigger, startSender);
                alarmManagerEnd.set(AlarmManager.RTC_WAKEUP, endTrigger, endSender);
                return true;
            case R.id.deleteCourse:
                Course currentCourse = null;
                for (Course course: repository.getAllCourses()) {
                    if (course.getCourseId() == courseId){
                        currentCourse = course;
                    }
                    repository.delete(currentCourse);
                    Toast.makeText(addedit_courses.this, currentCourse.getCourseName() + " has been deleted", Toast.LENGTH_LONG).show();
                    Intent intent=new Intent(addedit_courses.this, allCourses.class);
                    startActivity(intent);
                }
                return true;

        }

        return super.onOptionsItemSelected(item);
    }

    public static void setTermId(int termId){
        currentTermId = termId;
    }

    private void updateStartLabel() {
        String dateFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.US);

        courseStart.setText(sdf.format(calendar.getTime()));
    }
    private void updateEndLabel() {
        String dateFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.US);

        courseEnd.setText(sdf.format(calendar.getTime()));
    }

    public void addUpdateCourse(View view) {
        Course course;
        Term relatedTerm = (Term) termSpinner.getSelectedItem();
        int termId = relatedTerm.getTermId();
        if (courseId == -1){
            int newId;
            if (repository.getAllCourses().size() == 0){
                newId = 1;
            }
            else{
            newId = repository.getAllCourses().get(repository.getAllCourses().size() - 1).getCourseId()+1;}
            course = new Course(newId, courseName.getText().toString(), courseStart.getText().toString(), courseEnd.getText().toString(), courseStatus.getSelectedItem().toString(), instructorName.getText().toString(), instructorPhone.getText().toString(), instructorEmail.getText().toString(), notes.getText().toString(), termId);
            repository.insert(course);
        }
        else{
            course = new Course(courseId, courseName.getText().toString(), courseStart.getText().toString(), courseEnd.getText().toString(), courseStatus.getSelectedItem().toString(), instructorName.getText().toString(), instructorPhone.getText().toString(), instructorEmail.getText().toString(), notes.getText().toString(), termId);
            repository.update(course);
        }
        Intent intent=new Intent(addedit_courses.this, allCourses.class);
        startActivity(intent);

    }

    public void onViewAssessments(View view) {
        Course course;
        Term relatedTerm = (Term) termSpinner.getSelectedItem();
        int termId = relatedTerm.getTermId();
        if (courseId == -1){
            int newId;
            if (repository.getAllCourses().size() == 0){
                newId = 1;
            }
            else{
                newId = repository.getAllCourses().get(repository.getAllCourses().size() - 1).getCourseId()+1;}
            course = new Course(newId, courseName.getText().toString(), courseStart.getText().toString(), courseEnd.getText().toString(), courseStatus.getSelectedItem().toString(), instructorName.getText().toString(), instructorPhone.getText().toString(), instructorEmail.getText().toString(), notes.getText().toString(), termId);
            repository.insert(course);
        }
        else{
            course = new Course(courseId, courseName.getText().toString(), courseStart.getText().toString(), courseEnd.getText().toString(), courseStatus.getSelectedItem().toString(), instructorName.getText().toString(), instructorPhone.getText().toString(), instructorEmail.getText().toString(), notes.getText().toString(), termId);
            repository.update(course);
        }
        allAssessments.setRelatedCourse(course);
        Intent intent=new Intent(addedit_courses.this, allAssessments.class);
        startActivity(intent);
    }
}