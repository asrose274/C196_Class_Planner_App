package com.example.aaronrosec196.Entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.time.LocalDate;

/**
 * @author Aaron Rose
 * C196 Mobile application
 * Assessment Class
 */
@Entity(tableName = "assessments")
public class Assessment {

    @PrimaryKey(autoGenerate = true)
    private int assessmentId;

    private String assessmentName;
    private String assessmentEnd;
    private String assessmentType;
    private int courseId;

    public Assessment(int assessmentId, String assessmentName, String assessmentEnd, String assessmentType, int courseId){

        this.assessmentId = assessmentId;
        this.assessmentName = assessmentName;
        this.assessmentEnd = assessmentEnd;
        this.assessmentType = assessmentType;
        this.courseId = courseId;
    }

    public int getAssessmentId() {
        return assessmentId;
    }

    public void setAssessmentId(int assessmentId) {
        this.assessmentId = assessmentId;
    }

    public String getAssessmentName() {
        return assessmentName;
    }

    public void setAssessmentName(String assessmentName) {
        this.assessmentName = assessmentName;
    }

    public String  getAssessmentEnd() {
        return assessmentEnd;
    }

    public void setAssessmentEnd(String assessmentEnd) {
        this.assessmentEnd = assessmentEnd;
    }

    public String getAssessmentType() {
        return assessmentType;
    }

    public void setAssessmentType(String assessmentType) {
        this.assessmentType = assessmentType;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }


}
