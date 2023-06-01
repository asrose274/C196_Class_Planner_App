package com.example.aaronrosec196.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.aaronrosec196.Database.Repository;
import com.example.aaronrosec196.Entities.Term;
import com.example.aaronrosec196.R;

public class MainActivity extends AppCompatActivity {
    public static int numAlert;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Repository repo=new Repository(getApplication());
        Term term =new Term(1,"Term1", "1/1/2022", "4/1/2022");
        repo.insert(term);
    }

    public void termsOnClick(View view) {
        Intent intent=new Intent(MainActivity.this, allTerms.class);
        startActivity(intent);
    }

    public void assessmentsOnClicked(View view) {
        Intent intent=new Intent(MainActivity.this, allAssessments.class);
        startActivity(intent);
        allAssessments.resetRelatedCourse();
    }

    public void coursesOnClicked(View view) {
        Intent intent=new Intent(MainActivity.this, allCourses.class);
        startActivity(intent);
        allCourses.resetRelatedTerm();
    }
}