package com.example.aaronrosec196.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.aaronrosec196.Database.Repository;
import com.example.aaronrosec196.Entities.Course;
import com.example.aaronrosec196.Entities.Term;
import com.example.aaronrosec196.R;
import com.google.android.material.snackbar.Snackbar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class addedit_terms extends AppCompatActivity {
    final Calendar calendar = Calendar.getInstance();
    EditText editTermTitle;
    Button editTermStart;
    Button editTermEnd;
    Button termStartDate;
    Button termEndDate;
    int termId;
    DatePickerDialog.OnDateSetListener startDate;
    DatePickerDialog.OnDateSetListener endDate;
    String name;
    String start;
    String end;
    Repository repository;
    List<Course> courses = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addedit_term);


        repository= new Repository(getApplication());


        termId=getIntent().getIntExtra("id", -1);

        termStartDate=findViewById(R.id.termStartDate);
        termEndDate=findViewById(R.id.termEndDate);
        String dateFormat= "MM/dd/yy";
        SimpleDateFormat sdf =new SimpleDateFormat(dateFormat, Locale.US);


        editTermTitle=findViewById(R.id.assessmentTitle);
        name=getIntent().getStringExtra("name");
        editTermTitle.setText(name);


        editTermStart=findViewById(R.id.termStartDate);
        start=getIntent().getStringExtra("start");
        if(start==null){
            editTermStart.setText("Start Date");
        }
        else {
                editTermStart.setText(start);
        }

        editTermEnd=findViewById(R.id.termEndDate);
        end=getIntent().getStringExtra("end");
        if(end==null){
            editTermEnd.setText("End Date");
        }
        else {

            editTermEnd.setText(end);
        }



        termStartDate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Date date;
                String info=termStartDate.getText().toString();
                try{
                    calendar.setTime(sdf.parse(info));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                new DatePickerDialog(addedit_terms.this,startDate,calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        termEndDate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Date date;
                String info=termEndDate.getText().toString();
                try{
                    calendar.setTime(sdf.parse(info));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                new DatePickerDialog(addedit_terms.this,endDate,calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        startDate = new DatePickerDialog.OnDateSetListener(){

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,int dayOfMonth) {

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
        getMenuInflater().inflate(R.menu.menu_termdetail, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){

        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            case R.id.deleteTerm:
                Term currentTerm = null;
                for (Term term:repository.getAllTerms()) {
                    if (term.getTermId()==termId){
                        currentTerm = term;
                    }

                }
                int numCourses = 0;
                for (Course course: repository.getAllCourses()) {
                    if (course.getTermId()==termId)++numCourses;
                }
                if (numCourses == 0){
                    repository.delete(currentTerm);
                    Toast.makeText(addedit_terms.this, currentTerm.getName()+" was deleted", Toast.LENGTH_LONG).show();
                    Intent intent=new Intent(addedit_terms.this, allTerms.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(addedit_terms.this, "Can't delete a term with courses", Toast.LENGTH_LONG).show();
                }

                return true;
            }
            return true;
    }
    private void updateStartLabel() {
        String dateFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.US);

        termStartDate.setText(sdf.format(calendar.getTime()));
    }
    private void updateEndLabel() {
        String dateFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.US);

        termEndDate.setText(sdf.format(calendar.getTime()));
    }

    public void saveTerm(View view) {
        Term term;
        if (termId == -1){
            int newId = repository.getAllTerms().get(repository.getAllTerms().size() - 1).getTermId()+1;
            term = new Term(newId, editTermTitle.getText().toString(), editTermStart.getText().toString(), editTermEnd.getText().toString());
            repository.insert(term);
        }
        else{
            term = new Term(termId, editTermTitle.getText().toString(), editTermStart.getText().toString(), editTermEnd.getText().toString());
            repository.update(term);
        }

        Intent intent=new Intent(addedit_terms.this, allTerms.class);
        startActivity(intent);
    }

    public void onViewAddCourses(View view) {
        Term term;

        if (termId == -1){
            int newId;
            if (repository.getAllTerms().size() == 0){
                newId = 1;
            }
            else{
            newId = repository.getAllTerms().get(repository.getAllTerms().size() - 1).getTermId()+1;}
            term = new Term(newId, editTermTitle.getText().toString(), editTermStart.getText().toString(), editTermEnd.getText().toString());
            repository.insert(term);
        }
        else{
            term = new Term(termId, editTermTitle.getText().toString(), editTermStart.getText().toString(), editTermEnd.getText().toString());
            repository.update(term);
        }
        allCourses.setRelatedTerm(term);
        Intent intent=new Intent(addedit_terms.this, allCourses.class);
        startActivity(intent);
    }


}