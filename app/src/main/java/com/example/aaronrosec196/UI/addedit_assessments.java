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
import com.example.aaronrosec196.Entities.Assessment;
import com.example.aaronrosec196.Entities.Course;
import com.example.aaronrosec196.Entities.Term;
import com.example.aaronrosec196.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class addedit_assessments extends AppCompatActivity {

    EditText assessmentNameText;
    Button assessmentEndDateButton;
    Spinner assessmentTypeSelector;
    Spinner courseSelector;
    DatePickerDialog.OnDateSetListener endDate;
    final Calendar calendar = Calendar.getInstance();
    Repository repository = new Repository(getApplication());

    String dateFormat= "MM/dd/yy";
    SimpleDateFormat sdf =new SimpleDateFormat(dateFormat, Locale.US);

    int assessmentId;
    String assessmentName;
    String assessmentEndDate;
    String assessmentType;
    public static int relatedCourseId;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addedit_assessments);

        //Find all gui elements
        courseSelector = findViewById(R.id.cousreSelectSpinner);
        assessmentTypeSelector = findViewById(R.id.assessmentTypeSpinner);
        assessmentNameText = findViewById(R.id.assessmentTitle);
        assessmentTypeSelector = findViewById(R.id.assessmentTypeSpinner);
        assessmentEndDateButton=findViewById(R.id.endDateButton);
        ArrayAdapter<CharSequence> typeAdapter=ArrayAdapter.createFromResource(this,R.array.assessment_type, android.R.layout.simple_spinner_item);
        assessmentTypeSelector.setAdapter(typeAdapter);
        ArrayList<Course> allCourses = (ArrayList<Course>) repository.getAllCourses();
        ArrayAdapter<Course> adapter =new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,allCourses);adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        courseSelector.setAdapter(adapter);
        if (relatedCourseId != -1){
            for (Course course: allCourses) {
                if (course.getCourseId() == relatedCourseId){
                    courseSelector.setSelection(adapter.getPosition(course));
                }

            }
        }

        //retrieving existing data
        assessmentId=getIntent().getIntExtra("assessmentId", -1);
        assessmentName=getIntent().getStringExtra("name");
        assessmentEndDate=getIntent().getStringExtra("end");
        assessmentType=getIntent().getStringExtra("type");
        relatedCourseId=getIntent().getIntExtra("courseId", -1);

        //setting existing data to gui
        assessmentNameText.setText(assessmentName);
        if(assessmentEndDate==null){
            assessmentEndDateButton.setText("End Date");
        }
        else {
            assessmentEndDateButton.setText(assessmentEndDate);
        }

        if (relatedCourseId != -1){
            for (Course course: allCourses) {
                if (course.getCourseId() == relatedCourseId) {
                    courseSelector.setSelection(adapter.getPosition(course));
                    break;
                }
            }
        }

        assessmentTypeSelector.setSelection(typeAdapter.getPosition(assessmentType));










        assessmentEndDateButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Date date;
                String info=assessmentEndDateButton.getText().toString();
                try{
                    calendar.setTime(sdf.parse(info));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                new DatePickerDialog(addedit_assessments.this,endDate,calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

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
    private void updateEndLabel() {
        String dateFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.US);

        assessmentEndDateButton.setText(sdf.format(calendar.getTime()));
    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_assessmentdetail, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){

        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            case R.id.deleteAssessment:
                Assessment currentAssessment = null;
                for (Assessment assessment:repository.getAllAssessments()) {
                    if (assessment.getAssessmentId() == assessmentId){
                        currentAssessment = assessment;
                        repository.delete(currentAssessment);
                        Toast.makeText(addedit_assessments.this, assessmentName + " has been deleted", Toast.LENGTH_LONG);
                        Intent intent=new Intent(addedit_assessments.this, allAssessments.class);
                        startActivity(intent);
                    }
                }
                return true;
            case R.id.notifyAssessment:
                String notifyEndDate = assessmentEndDateButton.getText().toString();
                Date notifyEnd = null;
                try {
                    notifyEnd= sdf.parse(notifyEndDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Long endTrigger=notifyEnd.getTime();
                Intent endIntent=new Intent(addedit_assessments.this, MyReceiver.class);
                endIntent.putExtra("key", assessmentNameText.getText() + " ends today");
                PendingIntent endSender = PendingIntent.getBroadcast(addedit_assessments.this, MainActivity.numAlert++ ,endIntent, 0);
                AlarmManager alarmManagerEnd= (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                alarmManagerEnd.set(AlarmManager.RTC_WAKEUP, endTrigger, endSender);
                return true;
        }
        return true;
    }

    public static void setRelatedCourseId(int id){
        relatedCourseId = id;
    }

    public void addUpdateAssessment(View view) {
        Assessment assessment;
        Course relatedCourse = (Course) courseSelector.getSelectedItem();
        int courseId = relatedCourse.getCourseId();
        if(assessmentId == -1){
            int newId;
            if (repository.getAllAssessments().size() == 0){
                newId = 1;
            }
            else{
                newId = repository.getAllAssessments().get(repository.getAllAssessments().size() - 1).getAssessmentId()+1;
            }
            assessment = new Assessment(newId, assessmentNameText.getText().toString(), assessmentEndDateButton.getText().toString(), assessmentTypeSelector.getSelectedItem().toString(), courseId);
            repository.insert(assessment);

        }
        else{
            assessment = new Assessment(assessmentId, assessmentNameText.getText().toString(), assessmentEndDateButton.getText().toString(), assessmentTypeSelector.getSelectedItem().toString(), courseId);
            repository.update(assessment);
        }

        Intent intent=new Intent(addedit_assessments.this, allAssessments.class);
        startActivity(intent);
    }
}