package com.example.aaronrosec196.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.aaronrosec196.Database.Repository;
import com.example.aaronrosec196.Entities.Course;
import com.example.aaronrosec196.Entities.Term;
import com.example.aaronrosec196.R;

import java.util.ArrayList;
import java.util.List;

public class allCourses extends AppCompatActivity {
    public static Term relatedTerm = new Term(0, "no term set", "no date set", "no date set");

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_courses);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        RecyclerView recyclerView=findViewById(R.id.courseList);
        TextView courseLabel=findViewById(R.id.assessmentLabel);
        Repository repo=new Repository(getApplication());
        List<Course> courses=repo.getAllCourses();
        List<Course> relatedCourses = new ArrayList<>();
        final courseAdapter adapter=new courseAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));



        if (relatedTerm.getTermId() == 0){
            adapter.setCourses(courses);
            courseLabel.setText("All Courses");
        }
        else{
            courseLabel.setText("Courses for " + relatedTerm.getName());

            for (Course course: repo.getAllCourses()) {
                if (course.getTermId() == relatedTerm.getTermId()){
                    relatedCourses.add(course);
                }
            }
            adapter.setCourses(relatedCourses);
        }

    }

    public void onAddCourse(View view) {
        Intent intent=new Intent(allCourses.this, addedit_courses.class);
        startActivity(intent);
        if (relatedTerm != null) {
            addedit_courses.setTermId(relatedTerm.getTermId());
        }
    }



    public static void setRelatedTerm(Term term){
        relatedTerm = term;
    }

    public static void resetRelatedTerm(){
        relatedTerm = new Term(0, "no term set", "no date set", "no date set");
    }

}
