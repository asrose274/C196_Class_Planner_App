package com.example.aaronrosec196.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


import com.example.aaronrosec196.Database.Repository;
import com.example.aaronrosec196.Entities.Assessment;
import com.example.aaronrosec196.Entities.Course;
import com.example.aaronrosec196.Entities.Term;
import com.example.aaronrosec196.R;

import java.util.ArrayList;
import java.util.List;

public class allAssessments extends AppCompatActivity {
    static Course relatedCourse = null;
    Repository repository = new Repository(getApplication());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_assessment);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        RecyclerView recyclerView=findViewById(R.id.assessmentList);
        TextView assessmentLabel=findViewById(R.id.assessmentLabel);
        Repository repo=new Repository(getApplication());
        List<Assessment> allAssessments=repo.getAllAssessments();
        List<Assessment> relatedAssessments = new ArrayList<>();
        final assessmentAdapter adapter=new assessmentAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        if(relatedCourse.getCourseId() == 0){
            adapter.setAssessments(allAssessments);
            assessmentLabel.setText("All Assessments");
        }
        else{
            for (Assessment assessment: allAssessments) {
                if (assessment.getCourseId() == relatedCourse.getCourseId()){
                    relatedAssessments.add(assessment);
                }
            }
            assessmentLabel.setText("Assessments for " + relatedCourse.getCourseName());
            adapter.setAssessments(relatedAssessments);
        }



    }

    public void onAddAssessment(View view) {
        addedit_assessments.setRelatedCourseId(relatedCourse.getCourseId());
        Intent intent=new Intent(allAssessments.this, addedit_assessments.class);
        startActivity(intent);
    }

    public static void setRelatedCourse(Course course){
        relatedCourse = course;
    }

    public static void resetRelatedCourse(){
        relatedCourse = new Course(0, "no name", "na", "na", "na", "na", "na", "na", "na", 0);
    }
}
